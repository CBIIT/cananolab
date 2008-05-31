package gov.nih.nci.cananolab.service.particle.impl;

import gov.nih.nci.cananolab.domain.common.DerivedBioAssayData;
import gov.nih.nci.cananolab.domain.common.Instrument;
import gov.nih.nci.cananolab.domain.particle.NanoparticleSample;
import gov.nih.nci.cananolab.domain.particle.characterization.Characterization;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationBean;
import gov.nih.nci.cananolab.dto.particle.characterization.DerivedBioAssayDataBean;
import gov.nih.nci.cananolab.exception.DuplicateEntriesException;
import gov.nih.nci.cananolab.exception.ParticleCharacterizationException;
import gov.nih.nci.cananolab.service.common.impl.FileServiceLocalImpl;
import gov.nih.nci.cananolab.service.particle.NanoparticleCharacterizationService;
import gov.nih.nci.cananolab.service.particle.helper.NanoparticleCharacterizationServiceHelper;
import gov.nih.nci.cananolab.system.applicationservice.CustomizedApplicationService;
import gov.nih.nci.cananolab.util.CaNanoLabComparators;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * Service methods involving local characterizations
 * 
 * @author tanq, pansu
 * 
 */
public class NanoparticleCharacterizationServiceLocalImpl extends
		NanoparticleCharacterizationServiceBaseImpl implements
		NanoparticleCharacterizationService {
	private static Logger logger = Logger
			.getLogger(NanoparticleCharacterizationServiceLocalImpl.class);
	private NanoparticleCharacterizationServiceHelper helper = new NanoparticleCharacterizationServiceHelper();

	public NanoparticleCharacterizationServiceLocalImpl() {
		fileService = new FileServiceLocalImpl();
	}

	public void saveCharacterization(NanoparticleSample particleSample,
			Characterization achar) throws Exception {
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			if (achar.getId() != null) {
				Characterization dbChar = (Characterization) appService.load(
						Characterization.class, achar.getId());
				if (dbChar == null) {
					String err = "Object doesn't exist in the database anymore.  Please log in again.";
					logger.error(err);
					throw new ParticleCharacterizationException(err);
				}
			}
			// check for existing view title
			Boolean redundantViewTitle = checkRedundantViewTitle(
					particleSample, achar);
			if (redundantViewTitle) {
				throw new DuplicateEntriesException();
			}

			if (particleSample.getCharacterizationCollection() != null) {
				particleSample.getCharacterizationCollection().clear();
			} else {
				particleSample
						.setCharacterizationCollection(new HashSet<Characterization>());
			}
			achar.setNanoparticleSample(particleSample);
			particleSample.getCharacterizationCollection().add(achar);

			// load existing instrument
			if (achar.getInstrumentConfiguration() != null
					&& achar.getInstrumentConfiguration().getInstrument() != null) {
				Instrument dbInstrument = findInstrumentBy(
						achar.getInstrumentConfiguration().getInstrument()
								.getType(), achar.getInstrumentConfiguration()
								.getInstrument().getManufacturer());
				if (dbInstrument != null) {
					achar.getInstrumentConfiguration().setInstrument(
							dbInstrument);
				}
			}

			if (achar.getDerivedBioAssayDataCollection() != null) {
				for (DerivedBioAssayData bioassay : achar
						.getDerivedBioAssayDataCollection()) {
					if (bioassay.getLabFile() != null) {
						fileService.prepareSaveFile(bioassay.getLabFile());
					}
				}
			}
			appService.saveOrUpdate(achar);
		} catch (DuplicateEntriesException e) {
			throw e;
		} catch (Exception e) {
			String err = "Problem in saving the characterization.";
			logger.error(err, e);
			throw new ParticleCharacterizationException(err, e);
		}
	}

	public Characterization findCharacterizationById(String charId)
			throws ParticleCharacterizationException {
		try {
			Characterization achar = helper.findCharacterizationById(charId);
			return achar;
		} catch (Exception e) {
			logger.error("Problem finding the characterization by id: "
					+ charId, e);
			throw new ParticleCharacterizationException();
		}
	}

	private Boolean checkRedundantViewTitle(NanoparticleSample particleSample,
			Characterization chara) throws ParticleCharacterizationException {
		Boolean exist = false;
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(
					Characterization.class).add(
					Property.forName("identificationName").eq(
							chara.getIdentificationName()));
			crit.createAlias("nanoparticleSample", "sample").add(
					Restrictions.eq("sample.name", particleSample.getName()));
			crit
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List results = appService.query(crit);
			if (!results.isEmpty()) {
				for (Object obj : results) {
					Characterization achar = (Characterization) obj;
					// same characterization class with different IDs can't have
					// the same view titles.
					if (achar.getClass().getCanonicalName().equals(
							chara.getClass().getCanonicalName())
							&& !achar.getId().equals(chara.getId())) {
						return true;
					}
				}
			}
			return exist;
		} catch (Exception e) {
			logger
					.error("Problem checking whether the view title already exists.");
			throw new ParticleCharacterizationException();
		}
	}

	public SortedSet<String> findAllCharacterizationSources()
			throws ParticleCharacterizationException {
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
					.error("Problem to retrieve all Characterization Sources.",
							e);
			throw new ParticleCharacterizationException(
					"Problem to retrieve all Characterization Sources ");
		}
		sources.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_CHARACTERIZATION_SOURCES));

		return sources;
	}

	public List<Instrument> findAllInstruments()
			throws ParticleCharacterizationException {
		List<Instrument> instruments = new ArrayList<Instrument>();

		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(Instrument.class)
					.add(Restrictions.isNotNull("type"));
			List results = appService.query(crit);
			for (Object obj : results) {
				Instrument instrument = (Instrument) obj;
				instruments.add(instrument);
			}
		} catch (Exception e) {
			String err = "Problem to retrieve all instruments.";
			logger.error(err, e);
			throw new ParticleCharacterizationException(err);
		}
		return instruments;
	}

	public Instrument findInstrumentBy(String instrumentType,
			String manufacturer) throws ParticleCharacterizationException {
		Instrument instrument = null;
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			DetachedCriteria crit = DetachedCriteria.forClass(Instrument.class)
					.add(Property.forName("type").eq(instrumentType)).add(
							Property.forName("manufacturer").eq(manufacturer));
			crit
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List results = appService.query(crit);
			for (Object obj : results) {
				instrument = (Instrument) obj;

			}
			return instrument;
		} catch (Exception e) {
			String err = "Problem to retrieve instrument.";
			logger.error(err, e);
			throw new ParticleCharacterizationException(err);
		}
	}

	protected SortedSet<Characterization> findParticleCharacterizationsByClass(
			String particleName, String className)
			throws ParticleCharacterizationException {

		try {
			Collection<Characterization> charas = helper
					.findParticleCharacterizationsByClass(particleName,
							className);
			SortedSet<Characterization> sortedChars = new TreeSet<Characterization>(
					new CaNanoLabComparators.CharacterizationDateComparator());
			sortedChars.addAll(charas);
			return sortedChars;
		} catch (Exception e) {
			String err = "Error getting " + particleName
					+ " characterizations of type " + className;
			logger.error(err, e);
			throw new ParticleCharacterizationException(err, e);
		}
	}

	// set lab file visibility of a characterization
	public void retrieveVisiblity(CharacterizationBean charBean, UserBean user)
			throws ParticleCharacterizationException {
		try {
			for (DerivedBioAssayDataBean bioAssayData : charBean
					.getDerivedBioAssayDataList()) {
				fileService.retrieveVisibility(bioAssayData.getLabFileBean(),
						user);
			}
			fileService
					.retrieveVisibility(charBean.getProtocolFileBean(), user);
		} catch (Exception e) {
			String err = "Error setting visiblity for characterization "
					+ charBean.getViewTitle();
			logger.error(err, e);
			throw new ParticleCharacterizationException(err, e);
		}
	}

	public void deleteCharacterization(Characterization chara)
			throws ParticleCharacterizationException {
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			appService.delete(chara);
		} catch (Exception e) {
			String err = "Error deleting characterization "
					+ chara.getIdentificationName();
			logger.error(err, e);
			throw new ParticleCharacterizationException(err, e);
		}
	}

	public List<Characterization> findCharsByParticleSampleId(String particleId)
			throws ParticleCharacterizationException {
		throw new ParticleCharacterizationException(
				"Not implemented for local service");
	}
}