package gov.nih.nci.cananolab.service.sample.impl;

import gov.nih.nci.cananolab.domain.common.ExperimentConfig;
import gov.nih.nci.cananolab.domain.common.Finding;
import gov.nih.nci.cananolab.domain.common.Instrument;
import gov.nih.nci.cananolab.domain.common.Technique;
import gov.nih.nci.cananolab.domain.particle.Characterization;
import gov.nih.nci.cananolab.domain.particle.Sample;
import gov.nih.nci.cananolab.dto.common.ExperimentConfigBean;
import gov.nih.nci.cananolab.dto.common.FileBean;
import gov.nih.nci.cananolab.dto.common.FindingBean;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationSummaryViewBean;
import gov.nih.nci.cananolab.exception.CharacterizationException;
import gov.nih.nci.cananolab.exception.CompositionException;
import gov.nih.nci.cananolab.exception.ExperimentConfigException;
import gov.nih.nci.cananolab.exception.NoAccessException;
import gov.nih.nci.cananolab.service.common.FileService;
import gov.nih.nci.cananolab.service.common.helper.FileServiceHelper;
import gov.nih.nci.cananolab.service.common.impl.FileServiceLocalImpl;
import gov.nih.nci.cananolab.service.sample.CharacterizationService;
import gov.nih.nci.cananolab.service.sample.helper.CharacterizationServiceHelper;
import gov.nih.nci.cananolab.service.security.AuthorizationService;
import gov.nih.nci.cananolab.system.applicationservice.CustomizedApplicationService;
import gov.nih.nci.cananolab.util.Comparators;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.DateUtils;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Service methods involving local characterizations
 *
 * @author tanq, pansu
 *
 */
public class CharacterizationServiceLocalImpl implements
		CharacterizationService {
	private static Logger logger = Logger
			.getLogger(CharacterizationServiceLocalImpl.class);
	private CharacterizationServiceHelper helper = new CharacterizationServiceHelper();

	public CharacterizationServiceLocalImpl() {
	}

	public void saveCharacterization(Sample sample,
			CharacterizationBean charBean, UserBean user)
			throws CharacterizationException, NoAccessException {
		if (user == null || !user.isCurator()) {
			throw new NoAccessException();
		}
		try {
			Characterization achar = charBean.getDomainChar();
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			if (achar.getId() != null) {
				Characterization dbChar = (Characterization) appService.load(
						Characterization.class, achar.getId());
				if (dbChar == null) {
					String err = "Object doesn't exist in the database anymore.  Please log in again.";
					logger.error(err);
					throw new CharacterizationException(err);
				}
			}
			// if (sample.getCharacterizationCollection() != null) {
			// sample.getCharacterizationCollection().clear();
			// } else {
			// sample
			// .setCharacterizationCollection(new HashSet<Characterization>());
			// }
			achar.setSample(sample);
			// sample.getCharacterizationCollection().add(achar);

			// save file data to file system and assign visibility
			FileService fileService = new FileServiceLocalImpl();
			for (FindingBean findingBean : charBean.getFindings()) {
				for (FileBean fileBean : findingBean.getFiles()) {
					fileService.prepareSaveFile(fileBean.getDomainFile(), user);
					fileService.writeFile(fileBean, user);
				}
			}
			appService.saveOrUpdate(achar);

			// set public visibility
			AuthorizationService authService = new AuthorizationService(
					Constants.CSM_APP_NAME);
			List<String> accessibleGroups = authService.getAccessibleGroups(
					sample.getName(), Constants.CSM_READ_PRIVILEGE);
			if (accessibleGroups != null
					&& accessibleGroups.contains(Constants.CSM_PUBLIC_GROUP)) {
				helper.assignPublicVisibility(charBean.getDomainChar());
			}
		} catch (Exception e) {
			String err = "Problem in saving the characterization.";
			logger.error(err, e);
			throw new CharacterizationException(err, e);
		}
	}

	public CharacterizationBean findCharacterizationById(String charId,
			UserBean user) throws CharacterizationException {
		try {
			Characterization achar = null;
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(
					Characterization.class).add(
					Property.forName("id").eq(new Long(charId)));
			// fully load characterization
			crit.setFetchMode("sample", FetchMode.JOIN);
			crit.setFetchMode("pointOfContact", FetchMode.JOIN);
			crit.setFetchMode("pointOfContact.organization", FetchMode.JOIN);
			crit.setFetchMode("protocol", FetchMode.JOIN);
			crit.setFetchMode("protocol.file", FetchMode.JOIN);
			crit
					.setFetchMode("protocol.file.keywordCollection",
							FetchMode.JOIN);
			crit.setFetchMode("experimentConfigCollection", FetchMode.JOIN);
			crit.setFetchMode("experimentConfigCollection.technique",
					FetchMode.JOIN);
			crit.setFetchMode(
					"experimentConfigCollection.instrumentCollection",
					FetchMode.JOIN);
			crit.setFetchMode("findingCollection", FetchMode.JOIN);
			crit.setFetchMode("findingCollection.datumCollection",
					FetchMode.JOIN);
			crit.setFetchMode(
					"findingCollection.datumCollection.conditionCollection",
					FetchMode.JOIN);
			crit.setFetchMode("findingCollection.fileCollection",
					FetchMode.JOIN);
			crit.setFetchMode(
					"findingCollection.fileCollection.keywordCollection",
					FetchMode.JOIN);
			crit
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			List result = appService.query(crit);
			CharacterizationBean charBean = null;
			if (!result.isEmpty()) {
				achar = (Characterization) result.get(0);
				if (helper.getAuthService().checkReadPermission(user,
						achar.getSample().getName())) {
					charBean = new CharacterizationBean(achar);
				} else {
					throw new NoAccessException("User doesn't have access to the sample");
				}
			}
			return charBean;
		} catch (Exception e) {
			logger.error("Problem finding the characterization by id: "
					+ charId, e);
			throw new CharacterizationException();
		}
	}

	// private Boolean checkRedundantViewTitle(Sample
	// sample,
	// Characterization chara) throws CharacterizationException {
	// Boolean exist = false;
	// try {
	// CustomizedApplicationService appService = (CustomizedApplicationService)
	// ApplicationServiceProvider
	// .getApplicationService();
	// DetachedCriteria crit = DetachedCriteria.forClass(
	// Characterization.class).add(
	// Property.forName("identificationName").eq(
	// chara.getIdentificationName()));
	// crit.createAlias("nanosample", "sample").add(
	// Restrictions.eq("sample.name", sample.getName()));
	// crit
	// .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	// List results = appService.query(crit);
	// if (!results.isEmpty()) {
	// for (Object obj : results) {
	// Characterization achar = (Characterization) obj;
	// // same characterization class with different IDs can't have
	// // the same view titles.
	// if (achar.getClass().getCanonicalName().equals(
	// chara.getClass().getCanonicalName())
	// && !achar.getId().equals(chara.getId())) {
	// return true;
	// }
	// }
	// }
	// return exist;
	// } catch (Exception e) {
	// logger
	// .error("Problem checking whether the view title already exists.");
	// throw new CharacterizationException();
	// }
	// }

	public SortedSet<String> findAllCharacterizationSources(UserBean user)
			throws CharacterizationException {
		SortedSet<String> sources = new TreeSet<String>();

		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			HQLCriteria crit = new HQLCriteria(
					"select distinct achar.source from gov.nih.nci.cananolab.domain.particle.characterization.Characterization achar where achar.source is not null");
			List results = appService.query(crit);
			for (Object obj : results) {
				String source = (String) obj;
				sources.add(source);
			}
		} catch (Exception e) {
			logger
					.error(
							"Problem to retrieve all Characterization PrimaryOrganizations.",
							e);
			throw new CharacterizationException(
					"Problem to retrieve all Characterization Sources ");
		}
		sources.addAll(Arrays
				.asList(Constants.DEFAULT_CHARACTERIZATION_SOURCES));

		return sources;
	}

	private void retrieveFilesVisibilities(Collection<FileBean> fileBeans,
			UserBean user) throws CompositionException {
		try {
			FileServiceHelper fileServiceHelper = new FileServiceHelper();
			for (FileBean fileBean : fileBeans) {
				fileServiceHelper.retrieveVisibility(fileBean, user);
			}
		} catch (Exception e) {
			String err = "Error setting visiblity for files ";
			logger.error(err, e);
			throw new CompositionException(err, e);
		}
	}

	public void deleteCharacterization(Characterization chara, UserBean user)
			throws CharacterizationException, NoAccessException {
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			appService.delete(chara);
			helper.removePublicVisibility(chara);

		} catch (Exception e) {
			String err = "Error deleting characterization " + chara.getId();
			logger.error(err, e);
			throw new CharacterizationException(err, e);
		}
	}

	public List<CharacterizationBean> findCharsBySampleId(String sampleId,
			UserBean user) throws CharacterizationException {
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria
					.forClass(Characterization.class);
			crit.createAlias("sample", "sample");
			crit.add(Property.forName("sample.id").eq(new Long(sampleId)));
			// fully load characterization
			crit.setFetchMode("pointOfContact", FetchMode.JOIN);
			crit.setFetchMode("pointOfContact.organization", FetchMode.JOIN);
			crit.setFetchMode("protocol", FetchMode.JOIN);
			crit.setFetchMode("protocol.file", FetchMode.JOIN);
			crit
					.setFetchMode("protocol.file.keywordCollection",
							FetchMode.JOIN);
			crit.setFetchMode("experimentConfigCollection", FetchMode.JOIN);
			crit.setFetchMode("experimentConfigCollection.technique",
					FetchMode.JOIN);
			crit.setFetchMode(
					"experimentConfigCollection.instrumentCollection",
					FetchMode.JOIN);
			crit.setFetchMode("findingCollection", FetchMode.JOIN);
			crit.setFetchMode("findingCollection.datumCollection",
					FetchMode.JOIN);
			crit.setFetchMode(
					"findingCollection.datumCollection.conditionCollection",
					FetchMode.JOIN);
			crit.setFetchMode("findingCollection.fileCollection",
					FetchMode.JOIN);
			crit.setFetchMode(
					"findingCollection.fileCollection.keywordCollection",
					FetchMode.JOIN);
			crit
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List results = appService.query(crit);
			List<CharacterizationBean> chars = new ArrayList<CharacterizationBean>();
			for (Object obj : results) {
				Characterization chara = (Characterization) obj;
				chars.add(new CharacterizationBean(chara));
			}
			return chars;
		} catch (Exception e) {
			String err = "Error finding characterization by sample ID "
					+ sampleId;
			logger.error(err, e);
			throw new CharacterizationException(err);
		}
	}

	public FindingBean findFindingById(String findingId, UserBean user)
			throws CharacterizationException {
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(Finding.class)
					.add(Property.forName("id").eq(new Long(findingId)));
			crit.setFetchMode("datumCollection", FetchMode.JOIN);
			crit.setFetchMode("datumCollection.conditionCollection",
					FetchMode.JOIN);
			crit.setFetchMode("fileCollection", FetchMode.JOIN);
			crit.setFetchMode("fileCollection.keywordCollection",
					FetchMode.JOIN);
			List result = appService.query(crit);
			Finding finding = null;
			FindingBean findingBean = null;
			if (!result.isEmpty()) {
				finding = (Finding) result.get(0);
				findingBean = new FindingBean(finding);
			}
			return findingBean;
		} catch (Exception e) {
			String err = "Error getting finding of ID " + findingId;
			logger.error(err, e);
			throw new CharacterizationException(err, e);
		}
	}

	public void saveFinding(FindingBean finding, UserBean user)
			throws CharacterizationException, NoAccessException {
		if (user == null || !user.isCurator()) {
			throw new NoAccessException();
		}
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			// save file data to file system and assign visibility
			FileService fileService = new FileServiceLocalImpl();
			for (FileBean fileBean : finding.getFiles()) {
				fileService.prepareSaveFile(fileBean.getDomainFile(), user);
				fileService.writeFile(fileBean, user);
			}
			appService.saveOrUpdate(finding);
			// TODO assign visibility

		} catch (Exception e) {
			String err = "Error saving characterization result finding. ";
			logger.error(err, e);
			throw new CharacterizationException(err, e);
		}
	}

	public void deleteFinding(Finding finding, UserBean user)
			throws CharacterizationException, NoAccessException {
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			AuthorizationService authService = new AuthorizationService(
					Constants.CSM_APP_NAME);
			authService.removePublicVisibility(finding.getId().toString());
			appService.delete(finding);

		} catch (Exception e) {
			String err = "Error deleting finding " + finding.getId();
			logger.error(err, e);
			throw new CharacterizationException(err, e);
		}
	}

	public void saveExperimentConfig(ExperimentConfigBean configBean,
			UserBean user) throws ExperimentConfigException, NoAccessException {
		if (user == null || !user.isCurator()) {
			throw new NoAccessException();
		}
		try {
			ExperimentConfig config = configBean.getDomain();
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			// get existing createdDate and createdBy
			if (config.getId() != null) {
				ExperimentConfig dbConfig = helper.findExperimentConfigById(
						config.getId().toString(), user);
				if (dbConfig != null) {
					config.setCreatedBy(dbConfig.getCreatedBy());
					config.setCreatedDate(dbConfig.getCreatedDate());
				}
			}
			Technique technique = config.getTechnique();
			// check if technique already exists;
			Technique dbTechnique = findTechniqueByType(technique.getType());
			if (dbTechnique != null) {
				technique.setId(dbTechnique.getId());
				technique.setCreatedBy(dbTechnique.getCreatedBy());
				technique.setCreatedDate(dbTechnique.getCreatedDate());
			} else {
				technique.setCreatedBy(config.getCreatedBy());
				technique.setCreatedDate(new Date());
			}
			// check if instrument already exists;
			if (config.getInstrumentCollection() != null) {
				Collection<Instrument> instruments = new HashSet<Instrument>(
						config.getInstrumentCollection());
				config.setInstrumentCollection(new HashSet<Instrument>());
				int i = 0;
				for (Instrument instrument : instruments) {
					Instrument dbInstrument = findInstrumentBy(instrument
							.getType(), instrument.getManufacturer(),
							instrument.getModelName());
					if (dbInstrument != null) {
						instrument.setId(dbInstrument.getId());
						instrument.setCreatedBy(dbInstrument.getCreatedBy());
						instrument
								.setCreatedDate(dbInstrument.getCreatedDate());
					} else {
						instrument.setId(null);
					}
					config.getInstrumentCollection().add(instrument);
				}
			}
			appService.saveOrUpdate(config);

			// TODO assign visibility

		} catch (Exception e) {
			String err = "Error in saving the technique and associated instruments.";
			logger.error(err, e);
			throw new ExperimentConfigException(err, e);
		}
	}

	public void deleteExperimentConfig(ExperimentConfig config, UserBean user)
			throws ExperimentConfigException, NoAccessException {
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			// get existing createdDate and createdBy
			if (config.getId() != null) {
				ExperimentConfig dbConfig = helper.findExperimentConfigById(
						config.getId().toString(), user);
				if (dbConfig != null) {
					config.setCreatedBy(dbConfig.getCreatedBy());
					config.setCreatedDate(dbConfig.getCreatedDate());
				}
			}
			Technique technique = config.getTechnique();
			// check if technique already exists;
			Technique dbTechnique = findTechniqueByType(technique.getType());
			if (dbTechnique != null) {
				technique.setId(dbTechnique.getId());
				technique.setCreatedBy(dbTechnique.getCreatedBy());
				technique.setCreatedDate(dbTechnique.getCreatedDate());
			} else {
				technique.setCreatedBy(config.getCreatedBy());
				technique.setCreatedDate(new Date());
				// need to save the transient object before deleting the
				// experiment config
				appService.saveOrUpdate(technique);
			}
			// check if instrument already exists;
			if (config.getInstrumentCollection() != null) {
				Collection<Instrument> instruments = new HashSet<Instrument>(
						config.getInstrumentCollection());
				config.getInstrumentCollection().clear();
				int i = 0;
				for (Instrument instrument : instruments) {
					Instrument dbInstrument = findInstrumentBy(instrument
							.getType(), instrument.getManufacturer(),
							instrument.getModelName());
					if (dbInstrument != null) {
						instrument.setId(dbInstrument.getId());
						instrument.setCreatedBy(dbInstrument.getCreatedBy());
						instrument
								.setCreatedDate(dbInstrument.getCreatedDate());
					} else {
						instrument.setCreatedBy(config.getCreatedBy());
						instrument.setCreatedDate(DateUtils
								.addSecondsToCurrentDate(i));
						// need to save the transient object before deleting the
						// experiment config
						appService.saveOrUpdate(instrument);
					}
					config.getInstrumentCollection().add(instrument);
				}
			}

			appService.delete(config);
		} catch (Exception e) {
			String err = "Error in deleting the technique and associated instruments";
			logger.error(err, e);
			throw new ExperimentConfigException(err, e);
		}
	}

	public List<Technique> findAllTechniques() throws ExperimentConfigException {
		List<Technique> techniques = new ArrayList<Technique>();
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(Technique.class);
			List results = appService.query(crit);
			for (Object obj : results) {
				Technique technique = (Technique) obj;
				techniques.add(technique);
			}
			Collections.sort(techniques, new Comparators.TechniqueComparator());
		} catch (Exception e) {
			String err = "Problem to retrieve all techniques.";
			logger.error(err, e);
			throw new ExperimentConfigException(err);
		}
		return techniques;
	}

	public List<String> getAllManufacturers() throws ExperimentConfigException {
		List<String> manufacturers = new ArrayList<String>();
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(Instrument.class)
					.setProjection(
							Projections.distinct(Property
									.forName("manufacturer")));
			;
			List results = appService.query(crit);
			for (Object obj : results) {
				String manufacturer = (String) obj;
				if (manufacturer != null && manufacturer.trim().length() > 0) {
					manufacturers.add(manufacturer);
				}
			}
			Collections.sort(manufacturers);
		} catch (Exception e) {
			String err = "Problem to retrieve all manufacturers.";
			logger.error(err, e);
			throw new ExperimentConfigException(err);
		}
		return manufacturers;
	}

	public Technique findTechniqueByType(String type)
			throws ExperimentConfigException {
		Technique technique = null;
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(Technique.class)
					.add(Property.forName("type").eq(new String(type)));
			List results = appService.query(crit);
			for (Object obj : results) {
				technique = (Technique) obj;
			}
		} catch (Exception e) {
			String err = "Problem to retrieve technique by type.";
			logger.error(err, e);
			throw new ExperimentConfigException(err);
		}
		return technique;
	}

	public Instrument findInstrumentBy(String type, String manufacturer,
			String modelName) throws ExperimentConfigException {
		Instrument instrument = null;
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(Instrument.class);
			if (type != null && type.length() > 0) {
				crit.add(Restrictions.eq("type", type));
			}
			if (manufacturer != null && manufacturer.length() > 0) {
				crit.add(Restrictions.eq("manufacturer", manufacturer));
			}
			if (modelName != null && modelName.length() > 0) {
				crit.add(Restrictions.eq("modelName", modelName));
			}
			List results = appService.query(crit);
			for (Object obj : results) {
				instrument = (Instrument) obj;
			}
		} catch (Exception e) {
			String err = "Problem to retrieve instrument by type, manufacturer, and model name.";
			logger.error(err, e);
			throw new ExperimentConfigException(err);
		}
		return instrument;
	}

	/**
	 * Export sample characterization summary report as Excel spread sheet.
	 *
	 * @param summaryBean
	 *            CharacterizationSummaryViewBean
	 * @param out
	 *            OutputStream
	 * @throws IOException
	 *             if error occurred.
	 */
	public void exportSummary(CharacterizationSummaryViewBean summaryBean,
			HttpServletRequest request, OutputStream out)
			throws CharacterizationException {
		try {
			helper.exportSummary(summaryBean, request, out);
		} catch (Exception e) {
			String err = "Error exporting Characterization Summary View report.";
			logger.error(err, e);
			throw new CharacterizationException(err, e);
		}
	}
}