package gov.nih.nci.calab.service.particle;

import gov.nih.nci.calab.db.HibernateUtil;
import gov.nih.nci.calab.domain.Instrument;
import gov.nih.nci.calab.domain.InstrumentConfiguration;
import gov.nih.nci.calab.domain.Keyword;
import gov.nih.nci.calab.domain.LabFile;
import gov.nih.nci.calab.domain.LookupType;
import gov.nih.nci.calab.domain.MeasureType;
import gov.nih.nci.calab.domain.MeasureUnit;
import gov.nih.nci.calab.domain.OutputFile;
import gov.nih.nci.calab.domain.ProtocolFile;
import gov.nih.nci.calab.domain.nano.characterization.Characterization;
import gov.nih.nci.calab.domain.nano.characterization.CharacterizationFileType;
import gov.nih.nci.calab.domain.nano.characterization.DatumName;
import gov.nih.nci.calab.domain.nano.characterization.DerivedBioAssayData;
import gov.nih.nci.calab.domain.nano.characterization.DerivedBioAssayDataCategory;
import gov.nih.nci.calab.domain.nano.characterization.invitro.CFU_GM;
import gov.nih.nci.calab.domain.nano.characterization.invitro.Caspase3Activation;
import gov.nih.nci.calab.domain.nano.characterization.invitro.CellLineType;
import gov.nih.nci.calab.domain.nano.characterization.invitro.CellViability;
import gov.nih.nci.calab.domain.nano.characterization.invitro.Chemotaxis;
import gov.nih.nci.calab.domain.nano.characterization.invitro.Coagulation;
import gov.nih.nci.calab.domain.nano.characterization.invitro.ComplementActivation;
import gov.nih.nci.calab.domain.nano.characterization.invitro.CytokineInduction;
import gov.nih.nci.calab.domain.nano.characterization.invitro.EnzymeInduction;
import gov.nih.nci.calab.domain.nano.characterization.invitro.Hemolysis;
import gov.nih.nci.calab.domain.nano.characterization.invitro.LeukocyteProliferation;
import gov.nih.nci.calab.domain.nano.characterization.invitro.NKCellCytotoxicActivity;
import gov.nih.nci.calab.domain.nano.characterization.invitro.OxidativeBurst;
import gov.nih.nci.calab.domain.nano.characterization.invitro.OxidativeStress;
import gov.nih.nci.calab.domain.nano.characterization.invitro.Phagocytosis;
import gov.nih.nci.calab.domain.nano.characterization.invitro.PlasmaProteinBinding;
import gov.nih.nci.calab.domain.nano.characterization.invitro.PlateletAggregation;
import gov.nih.nci.calab.domain.nano.characterization.physical.MolecularWeight;
import gov.nih.nci.calab.domain.nano.characterization.physical.Morphology;
import gov.nih.nci.calab.domain.nano.characterization.physical.MorphologyType;
import gov.nih.nci.calab.domain.nano.characterization.physical.Purity;
import gov.nih.nci.calab.domain.nano.characterization.physical.Shape;
import gov.nih.nci.calab.domain.nano.characterization.physical.ShapeType;
import gov.nih.nci.calab.domain.nano.characterization.physical.Size;
import gov.nih.nci.calab.domain.nano.characterization.physical.Solubility;
import gov.nih.nci.calab.domain.nano.characterization.physical.SolventType;
import gov.nih.nci.calab.domain.nano.characterization.physical.Surface;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.CarbonNanotubeComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.ComplexComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.DendrimerComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.EmulsionComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.FullereneComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.LiposomeComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.MetalParticleComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.ParticleComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.PolymerComposition;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.QuantumDotComposition;
import gov.nih.nci.calab.domain.nano.characterization.toxicity.Cytotoxicity;
import gov.nih.nci.calab.domain.nano.function.Agent;
import gov.nih.nci.calab.domain.nano.function.Attachment;
import gov.nih.nci.calab.domain.nano.function.BondType;
import gov.nih.nci.calab.domain.nano.function.Function;
import gov.nih.nci.calab.domain.nano.function.ImageContrastAgent;
import gov.nih.nci.calab.domain.nano.function.ImageContrastAgentType;
import gov.nih.nci.calab.domain.nano.function.Linkage;
import gov.nih.nci.calab.domain.nano.particle.Nanoparticle;
import gov.nih.nci.calab.dto.characterization.CharacterizationBean;
import gov.nih.nci.calab.dto.characterization.DatumBean;
import gov.nih.nci.calab.dto.characterization.DerivedBioAssayDataBean;
import gov.nih.nci.calab.dto.characterization.composition.CarbonNanotubeBean;
import gov.nih.nci.calab.dto.characterization.composition.CompositionBean;
import gov.nih.nci.calab.dto.characterization.composition.DendrimerBean;
import gov.nih.nci.calab.dto.characterization.composition.EmulsionBean;
import gov.nih.nci.calab.dto.characterization.composition.FullereneBean;
import gov.nih.nci.calab.dto.characterization.composition.LiposomeBean;
import gov.nih.nci.calab.dto.characterization.composition.PolymerBean;
import gov.nih.nci.calab.dto.characterization.invitro.CytotoxicityBean;
import gov.nih.nci.calab.dto.characterization.physical.MorphologyBean;
import gov.nih.nci.calab.dto.characterization.physical.ShapeBean;
import gov.nih.nci.calab.dto.characterization.physical.SolubilityBean;
import gov.nih.nci.calab.dto.characterization.physical.SurfaceBean;
import gov.nih.nci.calab.dto.common.InstrumentBean;
import gov.nih.nci.calab.dto.common.InstrumentConfigBean;
import gov.nih.nci.calab.dto.common.LabFileBean;
import gov.nih.nci.calab.dto.common.ProtocolFileBean;
import gov.nih.nci.calab.dto.function.FunctionBean;
import gov.nih.nci.calab.dto.particle.ParticleBean;
import gov.nih.nci.calab.exception.CalabException;
import gov.nih.nci.calab.service.common.FileService;
import gov.nih.nci.calab.service.security.UserService;
import gov.nih.nci.calab.service.util.CaNanoLabConstants;
import gov.nih.nci.calab.service.util.PropertyReader;
import gov.nih.nci.calab.service.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * This class includes service calls involved in creating nanoparticle general
 * info and adding functions and characterizations for nanoparticles, as well as
 * creating reports.
 * 
 * @author pansu
 * 
 */
public class SubmitNanoparticleService {
	private static Logger logger = Logger
			.getLogger(SubmitNanoparticleService.class);

	// remove existing visibilities for the data
	private UserService userService;

	public SubmitNanoparticleService() throws Exception {
		this.userService = new UserService(CaNanoLabConstants.CSM_APP_NAME);
	}

	/**
	 * Update keywords and visibilities for the particle with the given name and
	 * type
	 * 
	 * @param particleType
	 * @param particleName
	 * @param keywords
	 * @param visibilities
	 * @throws Exception
	 */
	public ParticleBean addParticleGeneralInfo(String particleType,
			String particleName, String[] keywords, String[] visibilities)
			throws Exception {
		Nanoparticle particle = null;
		ParticleBean particleBean = null;
		// save nanoparticle to the database
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();
			// get the existing particle from database created during sample
			// creation
			List results = session.createQuery(
					"from Nanoparticle where name='" + particleName
							+ "' and type='" + particleType + "'").list();
			for (Object obj : results) {
				particle = (Nanoparticle) obj;
			}
			if (particle == null) {
				throw new CalabException("No such particle in the database");
			}

			particle.getKeywordCollection().clear();
			if (keywords != null) {
				for (String keyword : keywords) {
					Keyword keywordObj = new Keyword();
					if (keyword.length() > 0) {
						keywordObj.setName(keyword);
						particle.getKeywordCollection().add(keywordObj);
					}
				}
			}
			if (particle != null) {
				particleBean = new ParticleBean(particle);
			}

			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("Problem saving particle general information. ", e);
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}

		this.userService.setVisiblity(particleName, visibilities);
		return particleBean;
	}

	/**
	 * Save characterization to the database.
	 * 
	 * @param particleType
	 * @param particleName
	 * @param achar
	 * @throws Exception
	 */
	private void addParticleCharacterization(Characterization achar,
			CharacterizationBean charBean) throws Exception {

		// if ID is not set save to the database otherwise update
		Nanoparticle particle = null;
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();

			// check if viewTitle is already used the same type of
			// characterization for the same particle
			boolean viewTitleUsed = isCharacterizationViewTitleUsed(session,
					achar, charBean);
			if (viewTitleUsed) {
				throw new RuntimeException(
						"The view title is already in use.  Please enter a different one.");
			} else {
				// if ID exists, load from database
				if (charBean.getId() != null) {
					// check if ID is still valid
					achar = (Characterization) session.get(
							Characterization.class, new Long(charBean.getId()));
					if (achar == null)
						throw new Exception(
								"This characterization is no longer in the database.  Please log in again to refresh.");
				}
				// update domain object
				if (achar instanceof Shape) {
					((ShapeBean) charBean).updateDomainObj((Shape) achar);
				} else if (achar instanceof Morphology) {
					((MorphologyBean) charBean)
							.updateDomainObj((Morphology) achar);
				} else if (achar instanceof Solubility) {
					((SolubilityBean) charBean)
							.updateDomainObj((Solubility) achar);
				} else if (achar instanceof Surface) {
					((SurfaceBean) charBean).updateDomainObj((Surface) achar);
				} else if (achar instanceof Cytotoxicity) {
					((CytotoxicityBean) charBean)
							.updateDomainObj((Cytotoxicity) achar);
				} else
					charBean.updateDomainObj(achar);

				addProtocolFile(charBean.getProtocolFileBean(), achar, session);
				// store instrumentConfig and instrument
				addInstrumentConfig(charBean.getInstrumentConfigBean(), achar,
						session);

				if (charBean.getId() == null) {
					List results = session
							.createQuery(
									"from Nanoparticle particle left join fetch particle.characterizationCollection where particle.name='"
											+ charBean.getParticleName()
											+ "' and particle.type='"
											+ charBean.getParticleType() + "'")
							.list();

					for (Object obj : results) {
						particle = (Nanoparticle) obj;
					}

					if (particle != null) {
						particle.getCharacterizationCollection().add(achar);
					}
				}
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("Problem saving characterization. ", e);
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			HibernateUtil.closeSession();
			// skip if there is database error above and achar has not
			// been persisted
			if (achar.getId() != null) {
				// save file to the file system
				// if this block of code is inside the db try block,
				// hibernate
				// doesn't persist derivedBioAssayData
				if (!achar.getDerivedBioAssayDataCollection().isEmpty()) {
					int count = 0;
					for (DerivedBioAssayData derivedBioAssayData : achar
							.getDerivedBioAssayDataCollection()) {
						// skip if there is database error above and
						// derivedBioAssayData has not been persisted
						if (derivedBioAssayData.getId() != null) {
							DerivedBioAssayDataBean derivedBioAssayDataBean = new DerivedBioAssayDataBean(
									derivedBioAssayData);
							// assign visibility
							DerivedBioAssayDataBean unsaved = charBean
									.getDerivedBioAssayDataList().get(count);
							derivedBioAssayDataBean.setVisibilityGroups(unsaved
									.getVisibilityGroups());
							saveCharacterizationFile(derivedBioAssayDataBean);
							count++;
						}
					}
				}
			}
		}
	}

	public void addNewCharacterizationDataDropdowns(
			CharacterizationBean charBean, String characterizationName)
			throws Exception {
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();

			if (!charBean.getDerivedBioAssayDataList().isEmpty()) {
				for (DerivedBioAssayDataBean derivedBioAssayDataBean : charBean
						.getDerivedBioAssayDataList()) {
					// add new characterization file type if necessary
					if (derivedBioAssayDataBean.getType().length() > 0) {
						CharacterizationFileType fileType = new CharacterizationFileType();
						addLookupType(session, fileType,
								derivedBioAssayDataBean.getType());
					}
					// add new derived data cateory
					for (String category : derivedBioAssayDataBean
							.getCategories()) {
						addDerivedDataCategory(session, category,
								characterizationName);
					}
					// add new datum name, measure type, and unit
					for (DatumBean datumBean : derivedBioAssayDataBean
							.getDatumList()) {
						addDatumName(session, datumBean.getName(),
								characterizationName);
						MeasureType measureType = new MeasureType();
						addLookupType(session, measureType, datumBean
								.getStatisticsType());
						addMeasureUnit(session, datumBean.getUnit(),
								characterizationName);
					}
				}
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger
					.error("Problem saving characterization data drop downs. ",
							e);
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/*
	 * check if viewTitle is already used the same type of characterization for
	 * the same particle
	 */
	private boolean isCharacterizationViewTitleUsed(Session session,
			Characterization achar, CharacterizationBean charBean)
			throws Exception {
		String viewTitleQuery = "";
		if (charBean.getId() == null) {
			viewTitleQuery = "select count(achar.identificationName) from Nanoparticle particle join particle.characterizationCollection achar where particle.name='"
					+ charBean.getParticleName()
					+ "' and particle.type='"
					+ charBean.getParticleType()
					+ "' and achar.identificationName='"
					+ charBean.getViewTitle()
					+ "' and achar.name='"
					+ achar.getName() + "'";
		} else {
			viewTitleQuery = "select count(achar.identificationName) from Nanoparticle particle join particle.characterizationCollection achar where particle.name='"
					+ charBean.getParticleName()
					+ "' and particle.type='"
					+ charBean.getParticleType()
					+ "' and achar.identificationName='"
					+ charBean.getViewTitle()
					+ "' and achar.name='"
					+ achar.getName() + "' and achar.id!=" + charBean.getId();
		}
		List viewTitleResult = session.createQuery(viewTitleQuery).list();

		int existingViewTitleCount = -1;
		for (Object obj : viewTitleResult) {
			existingViewTitleCount = ((Integer) (obj)).intValue();
		}
		if (existingViewTitleCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Save the file to the file system
	 * 
	 * @param fileBean
	 */
	public void saveCharacterizationFile(DerivedBioAssayDataBean fileBean)
			throws Exception {

		byte[] fileContent = fileBean.getFileContent();
		String rootPath = PropertyReader.getProperty(
				CaNanoLabConstants.FILEUPLOAD_PROPERTY, "fileRepositoryDir");
		if (fileContent != null) {
			FileService fileService = new FileService();
			fileService.writeFile(fileContent, rootPath + File.separator
					+ fileBean.getUri());
		}
		this.userService.setVisiblity(fileBean.getId(), fileBean
				.getVisibilityGroups());
	}

	private void addInstrumentConfig(InstrumentConfigBean instrumentConfigBean,
			Characterization doChar, Session session) throws Exception {
		InstrumentBean instrumentBean = instrumentConfigBean
				.getInstrumentBean();

		if (instrumentBean.getType() == null
				&& instrumentBean.getManufacturer() == null
				&& instrumentConfigBean.getDescription() == null) {
			return;
		}
		if (instrumentBean.getType() != null
				&& instrumentBean.getType().length() == 0
				&& instrumentBean.getManufacturer().length() == 0) {
			doChar.setInstrumentConfiguration(null);
			return;
		}

		// check if instrument is already in database based on type and
		// manufacturer
		Instrument instrument = null;
		List instrumentResults = session.createQuery(
				"select instrument from Instrument instrument where instrument.type='"
						+ instrumentBean.getType()
						+ "' and instrument.manufacturer='"
						+ instrumentBean.getManufacturer() + "'").list();

		for (Object obj : instrumentResults) {
			instrument = (Instrument) obj;
		}
		// if not in the database, create one
		if (instrument == null) {
			instrument = new Instrument();
			instrument.setType(instrumentBean.getType());
			instrument.setManufacturer(instrumentBean.getManufacturer());
			session.save(instrument);
		}

		InstrumentConfiguration instrumentConfig = null;
		// new instrumentConfig
		if (instrumentConfigBean.getId() == null) {
			instrumentConfig = new InstrumentConfiguration();
			doChar.setInstrumentConfiguration(instrumentConfig);

		} else {
			instrumentConfig = doChar.getInstrumentConfiguration();
		}
		instrumentConfig.setDescription(instrumentConfigBean.getDescription());
		instrumentConfig.setInstrument(instrument);
		session.saveOrUpdate(instrumentConfig);
	}

	private void addProtocolFile(ProtocolFileBean protocolFileBean,
			Characterization doChar, Session session) throws Exception {
		if (protocolFileBean.getId() != null
				&& protocolFileBean.getId().length() > 0) {
			ProtocolFile protocolFile = (ProtocolFile) session.get(
					ProtocolFile.class, new Long(protocolFileBean.getId()));
			doChar.setProtocolFile(protocolFile);
		}
	}

	/**
	 * Saves the particle composition to the database
	 * 
	 * @param composition
	 * @param compositionType
	 * @throws Exception
	 */
	public void addParticleComposition(CompositionBean composition,
			String compositionType) throws Exception {
		ParticleComposition doComp = new ParticleComposition();		
		if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_COMPLEX_PARTICLE_TYPE)) {
			doComp = new ComplexComposition();
		} else if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_METAL_PARTICLE_TYPE)) {
			doComp = new MetalParticleComposition();
		} else if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_QUANTUM_DOT_TYPE)) {
			doComp = new QuantumDotComposition();
		} else if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_CARBON_NANOTUBE_TYPE)) {
			doComp=new CarbonNanotubeComposition();
		} else if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_DENDRIMER_TYPE)) {
			doComp=new DendrimerComposition();
		} else if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_EMULSION_TYPE)) {
			doComp=new EmulsionComposition();
		} else if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_FULLERENE_TYPE)) {
			doComp=new FullereneComposition();
		} else if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_LIPOSOME_TYPE)) {
			doComp=new LiposomeComposition();
		} else if (compositionType
				.equals(CaNanoLabConstants.COMPOSITION_POLYMER_TYPE)) {
			doComp=new PolymerComposition();
		}
		// if ID is not set save to the database otherwise update
		Nanoparticle particle = null;
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();

			// check if viewTitle is already used the same type of
			// characterization for the same particle
			boolean viewTitleUsed = isCharacterizationViewTitleUsed(session,
					doComp, composition);
			if (viewTitleUsed) {
				throw new RuntimeException(
						"The view title is already in use.  Please enter a different one.");
			} else {
				// if ID exists, load from database
				if (composition.getId() != null) {
					// check if ID is still valid
					doComp = (ParticleComposition) session.get(
							ParticleComposition.class, new Long(composition
									.getId()));
					if (doComp == null)
						throw new Exception(
								"This composition is no longer in the database.  Please log in again to refresh.");
				}
				// update domain object
				if (doComp instanceof DendrimerComposition) {					
					((DendrimerBean) composition)
							.updateDomainObj((DendrimerComposition) doComp);
				} else if (doComp instanceof CarbonNanotubeComposition) {					
					((CarbonNanotubeBean) composition)
							.updateDomainObj((CarbonNanotubeComposition) doComp);
				} else if (doComp instanceof EmulsionComposition) {
					((EmulsionBean) composition)
							.updateDomainObj((EmulsionComposition) doComp);
				} else if (doComp instanceof FullereneComposition) {
					((FullereneBean) composition)
							.updateDomainObj((FullereneComposition) doComp);
				} else if (doComp instanceof LiposomeComposition) {
					((LiposomeBean) composition)
							.updateDomainObj((LiposomeComposition) doComp);
				} else if (doComp instanceof PolymerComposition) {
					((PolymerBean) composition)
							.updateDomainObj((PolymerComposition) doComp);
				} else {
					composition.updateDomainObj(doComp);
				}

				if (composition.getId() == null) {
					List results = session
							.createQuery(
									"from Nanoparticle particle left join fetch particle.characterizationCollection where particle.name='"
											+ composition.getParticleName()
											+ "' and particle.type='"
											+ composition.getParticleType()
											+ "'").list();

					for (Object obj : results) {
						particle = (Nanoparticle) obj;
					}

					if (particle != null) {
						particle.getCharacterizationCollection().add(doComp);
					}
				}
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("Problem saving characterization. ", e);
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Saves the size characterization to the database
	 * 
	 * @param size
	 * @throws Exception
	 */
	public void addParticleSize(CharacterizationBean size) throws Exception {
		Size doSize = new Size();
		addParticleCharacterization(doSize, size);
	}

	/**
	 * Saves the size characterization to the database
	 * 
	 * @param particleType
	 * @param particleName
	 * @param surface
	 * @throws Exception
	 */
	public void addParticleSurface(CharacterizationBean surface)
			throws Exception {
		Surface doSurface = new Surface();
		addParticleCharacterization(doSurface, surface);
		// addMeasureUnit(doSurface.getCharge().getUnitOfMeasurement(),
		// CaNanoLabConstants.UNIT_TYPE_CHARGE);
		// addMeasureUnit(doSurface.getSurfaceArea().getUnitOfMeasurement(),
		// CaNanoLabConstants.UNIT_TYPE_AREA);
		// addMeasureUnit(doSurface.getZetaPotential().getUnitOfMeasurement(),
		// CaNanoLabConstants.UNIT_TYPE_ZETA_POTENTIAL);

	}

	private void addLookupType(Session session, LookupType lookupType,
			String type) throws Exception {
		String className = lookupType.getClass().getSimpleName();
		if (type != null && type.length() > 0) {
			List results = session.createQuery(
					"select count(distinct name) from " + className
							+ " type where name='" + type + "'").list();
			lookupType.setName(type);
			int count = -1;
			for (Object obj : results) {
				count = ((Integer) (obj)).intValue();
			}
			if (count == 0) {
				session.save(lookupType);
			}
		}
	}

	private void addDatumName(Session session, String name,
			String characterizationName) throws Exception {
		if (name != null && name.length() > 0) {
			List results = session.createQuery(
					"select count(distinct name) from DatumName"
							+ " where characterizationName='"
							+ characterizationName + "'" + " and name='" + name
							+ "'").list();
			DatumName datumName = new DatumName();
			datumName.setName(name);
			datumName.setCharacterizationName(characterizationName);
			datumName.setDatumParsed(false);
			int count = -1;
			for (Object obj : results) {
				count = ((Integer) (obj)).intValue();
			}
			if (count == 0) {
				session.save(datumName);
			}
		}
	}

	private void addDerivedDataCategory(Session session, String name,
			String characterizationName) throws Exception {
		if (name != null && name.length() > 0) {
			List results = session.createQuery(
					"select count(distinct name) from DerivedBioAssayDataCategory"
							+ " where characterizationName='"
							+ characterizationName + "'" + " and name='" + name
							+ "'").list();
			DerivedBioAssayDataCategory category = new DerivedBioAssayDataCategory();
			category.setName(name);
			category.setCharacterizationName(characterizationName);
			int count = -1;
			for (Object obj : results) {
				count = ((Integer) (obj)).intValue();
			}
			if (count == 0) {
				session.save(category);
			}
		}
	}

	private void addLookupType(LookupType lookupType, String type)
			throws Exception {
		if (type != null && type.length() > 0) {
			// if ID is not set save to the database otherwise update
			String className = lookupType.getClass().getSimpleName();
			try {
				Session session = HibernateUtil.currentSession();
				HibernateUtil.beginTransaction();
				List results = session.createQuery(
						"select count(distinct name) from " + className
								+ " type where name='" + type + "'").list();
				lookupType.setName(type);
				int count = -1;
				for (Object obj : results) {
					count = ((Integer) (obj)).intValue();
				}
				if (count == 0) {
					session.save(lookupType);
				}
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
				logger.error("Problem saving look up type: " + type, e);
				throw e;
			} finally {
				HibernateUtil.closeSession();
			}
		}
	}

	private void addMeasureUnit(Session session, String unit, String type)
			throws Exception {
		if (unit == null || unit.length() == 0) {
			return;
		}
		List results = session.createQuery(
				"select count(distinct name) from "
						+ " MeasureUnit where name='" + unit + "' and type='"
						+ type + "'").list();
		int count = -1;
		for (Object obj : results) {
			count = ((Integer) (obj)).intValue();
		}
		MeasureUnit measureUnit = new MeasureUnit();
		if (count == 0) {
			measureUnit.setName(unit);
			measureUnit.setType(type);
			session.save(measureUnit);
		}
	}

	/**
	 * Saves the molecular weight characterization to the database
	 * 
	 * @param molecularWeight
	 * @throws Exception
	 */
	public void addParticleMolecularWeight(CharacterizationBean molecularWeight)
			throws Exception {
		MolecularWeight doMolecularWeight = new MolecularWeight();
		addParticleCharacterization(doMolecularWeight, molecularWeight);
	}

	/**
	 * Saves the morphology characterization to the database
	 * 
	 * @param morphology
	 * @throws Exception
	 */
	public void addParticleMorphology(MorphologyBean morphology)
			throws Exception {
		Morphology doMorphology = new Morphology();
		addParticleCharacterization(doMorphology, morphology);
		MorphologyType morphologyType = new MorphologyType();
		addLookupType(morphologyType, morphology.getType());
	}

	/**
	 * Saves the shape characterization to the database
	 * 
	 * @param shape
	 * @throws Exception
	 */
	public void addParticleShape(ShapeBean shape) throws Exception {
		Shape doShape = new Shape();
		addParticleCharacterization(doShape, shape);
		ShapeType shapeType = new ShapeType();
		addLookupType(shapeType, shape.getType());
	}

	/**
	 * Saves the purity characterization to the database
	 * 
	 * @param purity
	 * @throws Exception
	 */
	public void addParticlePurity(CharacterizationBean purity) throws Exception {
		Purity doPurity = new Purity();
		addParticleCharacterization(doPurity, purity);
	}

	/**
	 * Saves the solubility characterization to the database
	 * 
	 * @param solubility
	 * @throws Exception
	 */
	public void addParticleSolubility(SolubilityBean solubility)
			throws Exception {
		Solubility doSolubility = new Solubility();
		addParticleCharacterization(doSolubility, solubility);
		SolventType solventType = new SolventType();
		addLookupType(solventType, solubility.getSolvent());
		// addMeasureUnit(solubility.getCriticalConcentration()
		// .getUnitOfMeasurement(),
		// CaNanoLabConstants.UNIT_TYPE_CONCENTRATION);
	}

	/**
	 * Saves the invitro hemolysis characterization to the database
	 * 
	 * @param hemolysis
	 * @throws Exception
	 */
	public void addHemolysis(CharacterizationBean hemolysis) throws Exception {
		Hemolysis doHemolysis = new Hemolysis();
		addParticleCharacterization(doHemolysis, hemolysis);
	}

	/**
	 * Saves the invitro coagulation characterization to the database
	 * 
	 * @param coagulation
	 * @throws Exception
	 */
	public void addCoagulation(CharacterizationBean coagulation)
			throws Exception {
		Coagulation doCoagulation = new Coagulation();
		addParticleCharacterization(doCoagulation, coagulation);
	}

	/**
	 * Saves the invitro plate aggregation characterization to the database
	 * 
	 * @param plateletAggregation
	 * @throws Exception
	 */
	public void addPlateletAggregation(CharacterizationBean plateletAggregation)
			throws Exception {
		PlateletAggregation doPlateletAggregation = new PlateletAggregation();
		addParticleCharacterization(doPlateletAggregation, plateletAggregation);
	}

	/**
	 * Saves the invitro chemotaxis characterization to the database
	 * 
	 * @param chemotaxis
	 * @throws Exception
	 */
	public void addChemotaxis(CharacterizationBean chemotaxis) throws Exception {
		Chemotaxis doChemotaxis = new Chemotaxis();
		addParticleCharacterization(doChemotaxis, chemotaxis);
	}

	/**
	 * Saves the invitro NKCellCytotoxicActivity characterization to the
	 * database
	 * 
	 * @param nkCellCytotoxicActivity
	 * @throws Exception
	 */
	public void addNKCellCytotoxicActivity(
			CharacterizationBean nkCellCytotoxicActivity) throws Exception {
		NKCellCytotoxicActivity doNKCellCytotoxicActivity = new NKCellCytotoxicActivity();
		addParticleCharacterization(doNKCellCytotoxicActivity,
				nkCellCytotoxicActivity);
	}

	/**
	 * Saves the invitro LeukocyteProliferation characterization to the database
	 * 
	 * @param leukocyteProliferation
	 * @throws Exception
	 */
	public void addLeukocyteProliferation(
			CharacterizationBean leukocyteProliferation) throws Exception {
		LeukocyteProliferation doLeukocyteProliferation = new LeukocyteProliferation();
		addParticleCharacterization(doLeukocyteProliferation,
				leukocyteProliferation);
	}

	/**
	 * Saves the invitro CFU_GM characterization to the database
	 * 
	 * @param cfu_gm
	 * @throws Exception
	 */
	public void addCFU_GM(CharacterizationBean cfu_gm) throws Exception {
		CFU_GM doCFU_GM = new CFU_GM();
		addParticleCharacterization(doCFU_GM, cfu_gm);
	}

	/**
	 * Saves the invitro Complement Activation characterization to the database
	 * 
	 * @param complementActivation
	 * @throws Exception
	 */
	public void addComplementActivation(
			CharacterizationBean complementActivation) throws Exception {
		ComplementActivation doComplementActivation = new ComplementActivation();
		addParticleCharacterization(doComplementActivation,
				complementActivation);
	}

	/**
	 * Saves the invitro OxidativeBurst characterization to the database
	 * 
	 * @param oxidativeBurst
	 * @throws Exception
	 */
	public void addOxidativeBurst(CharacterizationBean oxidativeBurst)
			throws Exception {
		OxidativeBurst doOxidativeBurst = new OxidativeBurst();
		addParticleCharacterization(doOxidativeBurst, oxidativeBurst);
	}

	/**
	 * Saves the invitro Phagocytosis characterization to the database
	 * 
	 * @param phagocytosis
	 * @throws Exception
	 */
	public void addPhagocytosis(CharacterizationBean phagocytosis)
			throws Exception {
		Phagocytosis doPhagocytosis = new Phagocytosis();
		addParticleCharacterization(doPhagocytosis, phagocytosis);
	}

	/**
	 * Saves the invitro CytokineInduction characterization to the database
	 * 
	 * @param cytokineInduction
	 * @throws Exception
	 */
	public void addCytokineInduction(CharacterizationBean cytokineInduction)
			throws Exception {
		CytokineInduction doCytokineInduction = new CytokineInduction();
		addParticleCharacterization(doCytokineInduction, cytokineInduction);
	}

	/**
	 * Saves the invitro plasma protein binding characterization to the database
	 * 
	 * @param plasmaProteinBinding
	 * @throws Exception
	 */
	public void addProteinBinding(CharacterizationBean plasmaProteinBinding)
			throws Exception {
		PlasmaProteinBinding doProteinBinding = new PlasmaProteinBinding();
		addParticleCharacterization(doProteinBinding, plasmaProteinBinding);
	}

	/**
	 * Saves the invitro binding characterization to the database
	 * 
	 * @param cellViability
	 * @throws Exception
	 */
	public void addCellViability(CytotoxicityBean cellViability)
			throws Exception {
		CellViability doCellViability = new CellViability();
		addParticleCharacterization(doCellViability, cellViability);
		CellLineType cellLineType = new CellLineType();
		addLookupType(cellLineType, cellViability.getCellLine());
	}

	/**
	 * Saves the invitro EnzymeInduction binding characterization to the
	 * database
	 * 
	 * @param enzymeInduction
	 * @throws Exception
	 */
	public void addEnzymeInduction(CharacterizationBean enzymeInduction)
			throws Exception {
		EnzymeInduction doEnzymeInduction = new EnzymeInduction();
		addParticleCharacterization(doEnzymeInduction, enzymeInduction);
	}

	/**
	 * Saves the invitro OxidativeStress characterization to the database
	 * 
	 * @param oxidativeStress
	 * @throws Exception
	 */
	public void addOxidativeStress(CharacterizationBean oxidativeStress)
			throws Exception {
		OxidativeStress doOxidativeStress = new OxidativeStress();
		addParticleCharacterization(doOxidativeStress, oxidativeStress);
	}

	/**
	 * Saves the invitro Caspase3Activation characterization to the database
	 * 
	 * @param caspase3Activation
	 * @throws Exception
	 */
	public void addCaspase3Activation(CytotoxicityBean caspase3Activation)
			throws Exception {
		Caspase3Activation doCaspase3Activation = new Caspase3Activation();
		addParticleCharacterization(doCaspase3Activation, caspase3Activation);
		CellLineType cellLineType = new CellLineType();
		addLookupType(cellLineType, caspase3Activation.getCellLine());
	}

	/**
	 * 
	 */
	public void addParticleFunction(String particleType, String particleName,
			FunctionBean function) throws Exception {

		// if ID is not set save to the database otherwise update
		Nanoparticle particle = null;
		Function doFunction = new Function();
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();

			boolean viewTitleUsed = isFunctionViewTitleUsed(session,
					particleType, particleName, function);

			if (viewTitleUsed) {
				throw new RuntimeException(
						"The view title is already in use.  Please enter a different one.");
			} else {
				// if function already exists in the database, load it first
				if (function.getId() != null) {
					doFunction = (Function) session.get(Function.class,
							new Long(function.getId()));
					function.updateDomainObj(doFunction);
				} else {
					function.updateDomainObj(doFunction);
					List results = session
							.createQuery(
									"select particle from Nanoparticle particle left join fetch particle.functionCollection where particle.name='"
											+ particleName
											+ "' and particle.type='"
											+ particleType + "'").list();
					for (Object obj : results) {
						particle = (Nanoparticle) obj;
					}
					if (particle != null) {
						particle.getFunctionCollection().add(doFunction);
					}
				}
			}

			// persist bondType and image contrast agent type drop-down
			for (Linkage linkage : doFunction.getLinkageCollection()) {
				if (linkage instanceof Attachment) {
					String bondType = ((Attachment) linkage).getBondType();
					BondType lookup = new BondType();
					addLookupType(session, lookup, bondType);
				}
				Agent agent = linkage.getAgent();
				if (agent instanceof ImageContrastAgent) {
					String agentType = ((ImageContrastAgent) agent).getType();
					ImageContrastAgentType lookup = new ImageContrastAgentType();
					addLookupType(session, lookup, agentType);
				}
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("Problem saving function: ", e);
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/*
	 * check if viewTitle is already used the same type of function for the same
	 * particle
	 */
	private boolean isFunctionViewTitleUsed(Session session,
			String particleType, String particleName, FunctionBean function)
			throws Exception {
		// check if viewTitle is already used the same type of
		// function for the same particle
		String viewTitleQuery = "";
		if (function.getId() == null) {
			viewTitleQuery = "select count(function.identificationName) from Nanoparticle particle join particle.functionCollection function where particle.name='"
					+ particleName
					+ "' and particle.type='"
					+ particleType
					+ "' and function.identificationName='"
					+ function.getViewTitle()
					+ "' and function.type='"
					+ function.getType() + "'";
		} else {
			viewTitleQuery = "select count(function.identificationName) from Nanoparticle particle join particle.functionCollection function where particle.name='"
					+ particleName
					+ "' and particle.type='"
					+ particleType
					+ "' and function.identificationName='"
					+ function.getViewTitle()
					+ "' and function.id!="
					+ function.getId()
					+ " and function.type='"
					+ function.getType() + "'";
		}
		List viewTitleResult = session.createQuery(viewTitleQuery).list();
		int existingViewTitleCount = -1;
		for (Object obj : viewTitleResult) {
			existingViewTitleCount = ((Integer) (obj)).intValue();
		}
		if (existingViewTitleCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Load the file for the given fileId from the database
	 * 
	 * @param fileId
	 * @return
	 */
	public LabFileBean getFile(String fileId) throws Exception {
		LabFileBean fileBean = null;
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();
			LabFile file = (LabFile) session.load(LabFile.class, StringUtils
					.convertToLong(fileId));
			fileBean = new LabFileBean(file);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("Problem getting file with file ID: " + fileId, e);
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		// get visibilities
		UserService userService = new UserService(
				CaNanoLabConstants.CSM_APP_NAME);
		List<String> accessibleGroups = userService.getAccessibleGroups(
				fileBean.getId(), CaNanoLabConstants.CSM_READ_ROLE);
		String[] visibilityGroups = accessibleGroups.toArray(new String[0]);
		fileBean.setVisibilityGroups(visibilityGroups);
		return fileBean;
	}

	/**
	 * Load the derived data file for the given fileId from the database
	 * 
	 * @param fileId
	 * @return
	 */
	public DerivedBioAssayDataBean getDerivedBioAssayData(String fileId)
			throws Exception {

		DerivedBioAssayDataBean fileBean = null;
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();
			DerivedBioAssayData file = (DerivedBioAssayData) session.load(
					DerivedBioAssayData.class, StringUtils
							.convertToLong(fileId));
			// load keywords
			file.getKeywordCollection();
			fileBean = new DerivedBioAssayDataBean(file,
					CaNanoLabConstants.OUTPUT);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("Problem getting file with file ID: " + fileId, e);
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		// get visibilities
		UserService userService = new UserService(
				CaNanoLabConstants.CSM_APP_NAME);
		List<String> accessibleGroups = userService.getAccessibleGroups(
				fileBean.getId(), CaNanoLabConstants.CSM_READ_ROLE);
		String[] visibilityGroups = accessibleGroups.toArray(new String[0]);
		fileBean.setVisibilityGroups(visibilityGroups);
		return fileBean;
	}

	/**
	 * Get the list of all run output files associated with a particle
	 * 
	 * @param particleName
	 * @return
	 * @throws Exception
	 */
	public List<LabFileBean> getAllRunFiles(String particleName)
			throws Exception {
		List<LabFileBean> runFiles = new ArrayList<LabFileBean>();
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();
			String query = "select distinct outFile from Run run join run.outputFileCollection outFile join run.runSampleContainerCollection runContainer where runContainer.sampleContainer.sample.name='"
					+ particleName + "'";
			List results = session.createQuery(query).list();

			for (Object obj : results) {
				OutputFile file = (OutputFile) obj;
				// active status only
				if (file.getDataStatus() == null) {
					LabFileBean fileBean = new LabFileBean();
					fileBean.setId(file.getId().toString());
					fileBean.setName(file.getFilename());
					fileBean.setUri(file.getUri());
					runFiles.add(fileBean);
				}
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("Problem getting run files for particle: "
					+ particleName, e);
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return runFiles;
	}

	/**
	 * Update the meta data associated with a file stored in the database
	 * 
	 * @param fileBean
	 * @throws Exception
	 */
	public void updateFileMetaData(LabFileBean fileBean) throws Exception {
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();
			LabFile file = (LabFile) session.load(LabFile.class, StringUtils
					.convertToLong(fileBean.getId()));

			file.setTitle(fileBean.getTitle().toUpperCase());
			file.setDescription(fileBean.getDescription());
			file.setComments(fileBean.getComments());
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("Problem updating file meta data: ", e);
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}

		this.userService.setVisiblity(fileBean.getId(), fileBean
				.getVisibilityGroups());
	}

	/**
	 * Delete the characterizations
	 */
	public void deleteCharacterizations(String particleName,
			String particleType, String[] charIds) throws Exception {
		// if ID is not set save to the database otherwise update
		try {
			Session session = HibernateUtil.currentSession();
			HibernateUtil.beginTransaction();

			// Get ID
			for (String strCharId : charIds) {

				Long charId = Long.parseLong(strCharId);
				Object charObj = session.load(Characterization.class, charId);
				// deassociate first
				String hqlString = "from Nanoparticle particle where particle.characterizationCollection.id = '"
						+ strCharId + "'";
				List results = session.createQuery(hqlString).list();
				for (Object obj : results) {
					Nanoparticle particle = (Nanoparticle) obj;
					particle.getCharacterizationCollection().remove(charObj);
				}
				// then delete
				// session.delete(charObj);
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("Problem deleting characterization: ", e);
			throw new Exception(
					"The characterization is no longer exist in the database, please login again to refresh the view.");
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
