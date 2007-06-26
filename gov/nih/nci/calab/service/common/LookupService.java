package gov.nih.nci.calab.service.common;

import gov.nih.nci.calab.db.DataAccessProxy;
import gov.nih.nci.calab.db.HibernateDataAccess;
import gov.nih.nci.calab.db.IDataAccess;
import gov.nih.nci.calab.domain.Aliquot;
import gov.nih.nci.calab.domain.Instrument;
import gov.nih.nci.calab.domain.MeasureUnit;
import gov.nih.nci.calab.domain.Protocol;
import gov.nih.nci.calab.domain.ProtocolFile;
import gov.nih.nci.calab.domain.Sample;
import gov.nih.nci.calab.domain.SampleContainer;
import gov.nih.nci.calab.domain.StorageElement;
import gov.nih.nci.calab.dto.characterization.CharacterizationTypeBean;
import gov.nih.nci.calab.dto.common.InstrumentBean;
import gov.nih.nci.calab.dto.common.LabFileBean;
import gov.nih.nci.calab.dto.common.ProtocolBean;
import gov.nih.nci.calab.dto.common.ProtocolFileBean;
import gov.nih.nci.calab.dto.common.UserBean;
import gov.nih.nci.calab.dto.inventory.AliquotBean;
import gov.nih.nci.calab.dto.inventory.ContainerBean;
import gov.nih.nci.calab.dto.inventory.ContainerInfoBean;
import gov.nih.nci.calab.dto.inventory.SampleBean;
import gov.nih.nci.calab.dto.workflow.AssayBean;
import gov.nih.nci.calab.service.security.UserService;
import gov.nih.nci.calab.service.util.CaNanoLabComparators;
import gov.nih.nci.calab.service.util.CaNanoLabConstants;
import gov.nih.nci.calab.service.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.collection.PersistentSet;

/**
 * The service to return prepopulated data that are shared across different
 * views.
 * 
 * @author zengje
 * 
 */
/* CVS $Id: LookupService.java,v 1.113 2007-06-26 15:55:50 pansu Exp $ */

public class LookupService {
	private static Logger logger = Logger.getLogger(LookupService.class);

	/**
	 * Retrieving all unmasked aliquots for use in views create run, use aliquot
	 * and create aliquot.
	 * 
	 * @return a Map between sample name and its associated unmasked aliquots
	 * @throws Exception
	 */

	public Map<String, SortedSet<AliquotBean>> getUnmaskedSampleAliquots()
			throws Exception {
		SortedSet<AliquotBean> aliquots = null;
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		Map<String, SortedSet<AliquotBean>> sampleAliquots = new HashMap<String, SortedSet<AliquotBean>>();
		try {
			ida.open();
			String hqlString = "select aliquot.id, aliquot.name, aliquot.sample.name from Aliquot aliquot where aliquot.dataStatus is null order by aliquot.name";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				Object[] info = (Object[]) obj;
				AliquotBean aliquot = new AliquotBean(StringUtils
						.convertToString(info[0]), StringUtils
						.convertToString(info[1]),
						CaNanoLabConstants.ACTIVE_STATUS);
				String sampleName = (String) info[2];
				if (sampleAliquots.get(sampleName) != null) {
					aliquots = (SortedSet<AliquotBean>) sampleAliquots
							.get(sampleName);
				} else {
					aliquots = new TreeSet<AliquotBean>(
							new CaNanoLabComparators.AliquotBeanComparator());
					sampleAliquots.put(sampleName, aliquots);
				}
				aliquots.add(aliquot);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all aliquot Ids and names", e);
			throw new RuntimeException(
					"Error in retrieving all aliquot Ids and names");
		} finally {
			ida.close();
		}
		return sampleAliquots;
	}

	/**
	 * 
	 * @return a map between sample name and its sample containers
	 * @throws Exception
	 */
	public Map<String, SortedSet<ContainerBean>> getAllSampleContainers()
			throws Exception {
		SortedSet<ContainerBean> containers = null;
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		Map<String, SortedSet<ContainerBean>> sampleContainers = new HashMap<String, SortedSet<ContainerBean>>();
		try {
			ida.open();
			String hqlString = "select container, container.sample.name from SampleContainer container";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				Object[] info = (Object[]) obj;
				if (!(info[0] instanceof Aliquot)) {
					ContainerBean container = new ContainerBean(
							(SampleContainer) info[0]);

					String sampleName = (String) info[1];
					if (sampleContainers.get(sampleName) != null) {
						containers = (SortedSet<ContainerBean>) sampleContainers
								.get(sampleName);
					} else {
						containers = new TreeSet<ContainerBean>(
								new CaNanoLabComparators.ContainerBeanComparator());
						sampleContainers.put(sampleName, containers);
					}
					containers.add(container);
				}
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all containers", e);
			throw new RuntimeException("Error in retrieving all containers");
		} finally {
			ida.close();
		}
		return sampleContainers;
	}

	/**
	 * Retrieving all sample types.
	 * 
	 * @return a list of all sample types
	 */
	public List<String> getAllSampleTypes() throws Exception {
		// Detail here
		// Retrieve data from Sample_Type table
		List<String> sampleTypes = new ArrayList<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select sampleType.name from SampleType sampleType order by sampleType.name";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				sampleTypes.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all sample types", e);
			throw new RuntimeException("Error in retrieving all sample types");
		} finally {
			ida.close();
		}

		return sampleTypes;
	}

	/**
	 * 
	 * @return the default sample container information in a form of
	 *         ContainerInfoBean
	 */
	public ContainerInfoBean getSampleContainerInfo() throws Exception {

		List<MeasureUnit> units = getAllMeasureUnits();
		List<StorageElement> storageElements = getAllStorageElements();
		List<String> quantityUnits = new ArrayList<String>();
		List<String> concentrationUnits = new ArrayList<String>();
		List<String> volumeUnits = new ArrayList<String>();
		List<String> rooms = new ArrayList<String>();
		List<String> freezers = new ArrayList<String>();
		List<String> shelves = new ArrayList<String>();
		List<String> boxes = new ArrayList<String>();

		for (MeasureUnit unit : units) {
			if (unit.getType().equalsIgnoreCase("Quantity")) {
				quantityUnits.add(unit.getName());
			} else if (unit.getType().equalsIgnoreCase("Volume")) {
				volumeUnits.add(unit.getName());
			} else if (unit.getType().equalsIgnoreCase("Concentration")) {
				concentrationUnits.add(unit.getName());
			}
		}

		for (StorageElement storageElement : storageElements) {
			if (storageElement.getType().equalsIgnoreCase("Room")
					&& !rooms.contains(storageElement.getLocation())) {
				rooms.add((storageElement.getLocation()));
			} else if (storageElement.getType().equalsIgnoreCase("Freezer")
					&& !freezers.contains(storageElement.getLocation())) {
				freezers.add((storageElement.getLocation()));
			} else if (storageElement.getType().equalsIgnoreCase("Shelf")
					&& !shelves.contains(storageElement.getLocation())) {
				shelves.add((storageElement.getLocation()));
			} else if (storageElement.getType().equalsIgnoreCase("Box")
					&& !boxes.contains(storageElement.getLocation())) {
				boxes.add((storageElement.getLocation()));
			}
		}

		// set labs and racks to null for now
		ContainerInfoBean containerInfo = new ContainerInfoBean(quantityUnits,
				concentrationUnits, volumeUnits, null, rooms, freezers,
				shelves, boxes);

		return containerInfo;
	}

	public List<String> getAllSampleContainerTypes() throws Exception {
		SortedSet<String> containerTypes = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct container.containerType from SampleContainer container order by container.containerType";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				containerTypes.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all sample container types", e);
			throw new RuntimeException(
					"Error in retrieving all sample container types.");
		} finally {
			ida.close();
		}
		containerTypes.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_CONTAINER_TYPES));
		List<String> containerTypeList = new ArrayList<String>(containerTypes);

		return containerTypeList;
	}

	public List<String> getAllAliquotContainerTypes() throws Exception {
		SortedSet<String> containerTypes = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct aliquot.containerType from Aliquot aliquot order by aliquot.containerType";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				containerTypes.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all aliquot container types", e);
			throw new RuntimeException(
					"Error in retrieving all aliquot container types.");
		} finally {
			ida.close();
		}
		containerTypes.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_CONTAINER_TYPES));
		List<String> containerTypeList = new ArrayList<String>(containerTypes);
		return containerTypeList;
	}

	public List<MeasureUnit> getAllMeasureUnits() throws Exception {
		List<MeasureUnit> units = new ArrayList<MeasureUnit>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "from MeasureUnit";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				units.add((MeasureUnit) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all measure units", e);
			throw new RuntimeException("Error in retrieving all measure units.");
		} finally {
			ida.close();
		}

		return units;
	}

	private List<StorageElement> getAllStorageElements() throws Exception {
		List<StorageElement> storageElements = new ArrayList<StorageElement>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "from StorageElement where type in ('Room', 'Freezer', 'Shelf', 'Box') and location!='Other'";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				storageElements.add((StorageElement) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all rooms and freezers", e);
			throw new RuntimeException(
					"Error in retrieving all rooms and freezers.");
		} finally {
			ida.close();
		}
		return storageElements;
	}

	/**
	 * 
	 * @return the default sample container information in a form of
	 *         ContainerInfoBean
	 */
	public ContainerInfoBean getAliquotContainerInfo() throws Exception {
		return getSampleContainerInfo();
	}

	/**
	 * Get all samples in the database
	 * 
	 * @return a list of SampleBean containing sample Ids and names DELETE
	 */
	public List<SampleBean> getAllSamples() throws Exception {
		List<SampleBean> samples = new ArrayList<SampleBean>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);

		try {
			ida.open();
			String hqlString = "select sample.id, sample.name from Sample sample";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				Object[] sampleInfo = (Object[]) obj;
				samples.add(new SampleBean(StringUtils
						.convertToString(sampleInfo[0]), StringUtils
						.convertToString(sampleInfo[1])));
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all sample IDs and names", e);
			throw new RuntimeException(
					"Error in retrieving all sample IDs and names");
		} finally {
			ida.close();
		}
		Collections.sort(samples,
				new CaNanoLabComparators.SampleBeanComparator());
		return samples;
	}

	/**
	 * Retrieve all Assay Types from the system
	 * 
	 * @return A list of all assay type
	 */
	public List getAllAssayTypes() throws Exception {
		List<String> assayTypes = new ArrayList<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select assayType.name from AssayType assayType order by assayType.executeOrder";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				assayTypes.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all assay types", e);
			throw new RuntimeException("Error in retrieving all assay types");
		} finally {
			ida.close();
		}
		return assayTypes;
	}

	/**
	 * 
	 * @return a map between assay type and its assays
	 * @throws Exception
	 */
	public Map<String, SortedSet<AssayBean>> getAllAssayTypeAssays()
			throws Exception {
		Map<String, SortedSet<AssayBean>> assayTypeAssays = new HashMap<String, SortedSet<AssayBean>>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select assay.id, assay.name, assay.assayType from Assay assay";
			List results = ida.search(hqlString);
			SortedSet<AssayBean> assays = null;
			for (Object obj : results) {
				Object[] objArray = (Object[]) obj;
				AssayBean assay = new AssayBean(
						((Long) objArray[0]).toString(), (String) objArray[1],
						(String) objArray[2]);
				if (assayTypeAssays.get(assay.getAssayType()) != null) {
					assays = (SortedSet<AssayBean>) assayTypeAssays.get(assay
							.getAssayType());
				} else {
					assays = new TreeSet<AssayBean>(
							new CaNanoLabComparators.AssayBeanComparator());
					assayTypeAssays.put(assay.getAssayType(), assays);
				}
				assays.add(assay);
			}
		} catch (Exception e) {
			// TODO: temp for debuging use. remove later
			e.printStackTrace();
			logger.error("Error in retrieving all assay beans. ", e);
			throw new RuntimeException("Error in retrieving all assays beans. ");
		} finally {
			ida.close();
		}
		return assayTypeAssays;
	}

	/**
	 * 
	 * @return all sample sources
	 */
	public List<String> getAllSampleSources() throws Exception {
		List<String> sampleSources = new ArrayList<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select source.organizationName from Source source order by source.organizationName";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				sampleSources.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all sample sources", e);
			throw new RuntimeException("Error in retrieving all sample sources");
		} finally {
			ida.close();
		}

		return sampleSources;
	}

	/**
	 * 
	 * @return a map between sample source and samples with unmasked aliquots
	 * @throws Exception
	 */
	public Map<String, SortedSet<SampleBean>> getSampleSourceSamplesWithUnmaskedAliquots()
			throws Exception {
		Map<String, SortedSet<SampleBean>> sampleSourceSamples = new HashMap<String, SortedSet<SampleBean>>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct aliquot.sample from Aliquot aliquot where aliquot.dataStatus is null";
			List results = ida.search(hqlString);
			SortedSet<SampleBean> samples = null;
			for (Object obj : results) {
				SampleBean sample = new SampleBean((Sample) obj);
				if (sampleSourceSamples.get(sample.getSampleSource()) != null) {
					// TODO need to make sample source a required field
					if (sample.getSampleSource().length() > 0) {
						samples = (SortedSet<SampleBean>) sampleSourceSamples
								.get(sample.getSampleSource());
					}
				} else {
					samples = new TreeSet<SampleBean>(
							new CaNanoLabComparators.SampleBeanComparator());
					if (sample.getSampleSource().length() > 0) {
						sampleSourceSamples.put(sample.getSampleSource(),
								samples);
					}
				}
				samples.add(sample);
			}
		} catch (Exception e) {
			logger.error(
					"Error in retrieving sample beans with unmasked aliquots ",
					e);
			throw new RuntimeException(
					"Error in retrieving all sample beans with unmasked aliquots. ");
		} finally {
			ida.close();
		}
		return sampleSourceSamples;
	}

	public List<String> getAllSampleSOPs() throws Exception {
		List<String> sampleSOPs = new ArrayList<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select sampleSOP.name from SampleSOP sampleSOP where sampleSOP.description='sample creation'";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				sampleSOPs.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all Sample SOPs.");
			throw new RuntimeException("Problem to retrieve all Sample SOPs. ");
		} finally {
			ida.close();
		}
		return sampleSOPs;
	}

	/**
	 * 
	 * @return all methods for creating aliquots
	 */
	public List<LabelValueBean> getAliquotCreateMethods() throws Exception {
		List<LabelValueBean> createMethods = new ArrayList<LabelValueBean>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select sop.name, file.path from SampleSOP sop join sop.sampleSOPFileCollection file where sop.description='aliquot creation'";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				String sopName = (String) ((Object[]) obj)[0];
				String sopURI = (String) ((Object[]) obj)[1];
				String sopURL = (sopURI == null) ? "" : sopURI;
				createMethods.add(new LabelValueBean(sopName, sopURL));
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all sample sources", e);
			throw new RuntimeException("Error in retrieving all sample sources");
		} finally {
			ida.close();
		}
		return createMethods;
	}

	/**
	 * 
	 * @return all source sample IDs
	 */
	public List<String> getAllSourceSampleIds() throws Exception {
		List<String> sourceSampleIds = new ArrayList<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct sample.sourceSampleId from Sample sample order by sample.sourceSampleId";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				sourceSampleIds.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Error in retrieving all source sample IDs", e);
			throw new RuntimeException(
					"Error in retrieving all source sample IDs");
		} finally {
			ida.close();
		}

		return sourceSampleIds;
	}

	public Map<String, SortedSet<String>> getAllParticleTypeParticles()
			throws Exception {
		// TODO fill in actual database query.
		Map<String, SortedSet<String>> particleTypeParticles = new HashMap<String, SortedSet<String>>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		UserService userService = new UserService(
				CaNanoLabConstants.CSM_APP_NAME);

		try {
			ida.open();
			// String hqlString = "select particle.type, particle.name from
			// Nanoparticle particle";
			String hqlString = "select particle.type, particle.name from Nanoparticle particle where size(particle.characterizationCollection) = 0";
			List results = ida.search(hqlString);
			SortedSet<String> particleNames = null;
			for (Object obj : results) {
				Object[] objArray = (Object[]) obj;
				String particleType = (String) objArray[0];
				String particleName = (String) objArray[1];

				if (particleType != null) {
					// check if the particle already has visibility group
					// assigned, if yes, do NOT add to the list
					List<String> groups = userService.getAccessibleGroups(
							particleName, CaNanoLabConstants.CSM_READ_ROLE);
					if (groups.size() == 0) {
						if (particleTypeParticles.get(particleType) != null) {
							particleNames = (SortedSet<String>) particleTypeParticles
									.get(particleType);
						} else {
							particleNames = new TreeSet<String>(
									new CaNanoLabComparators.SortableNameComparator());
							particleTypeParticles.put(particleType,
									particleNames);
						}
						particleNames.add(particleName);
					}
				}
			}
		} catch (Exception e) {
			logger
					.error("Error in retrieving all particle type particles. ",
							e);
			throw new RuntimeException(
					"Error in retrieving all particle type particles. ");
		} finally {
			ida.close();
		}
		return particleTypeParticles;
	}

	public String[] getAllDendrimerCores() {
		String[] cores = new String[] { "Diamine", "Ethyline" };
		return cores;
	}

	public String[] getAllDendrimerSurfaceGroupNames() throws Exception {
		SortedSet<String> names = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct surfaceGroup.name from SurfaceGroup surfaceGroup where surfaceGroup.name is not null";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				names.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all Surface Group name.");
			throw new RuntimeException(
					"Problem to retrieve all Surface Group name. ");
		} finally {
			ida.close();
		}
		names.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_SURFACE_GROUP_NAMES));

		return (String[]) names.toArray(new String[0]);
	}

	public String[] getAllDendrimerBranches() throws Exception {
		SortedSet<String> branches = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct dendrimer.branch from DendrimerComposition dendrimer where dendrimer.branch is not null";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				branches.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all Dendrimer Branches.");
			throw new RuntimeException(
					"Problem to retrieve all Dendrimer Branches. ");
		} finally {
			ida.close();
		}
		branches.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_DENDRIMER_BRANCHES));

		return (String[]) branches.toArray(new String[0]);
	}

	public String[] getAllDendrimerGenerations() throws Exception {
		SortedSet<String> generations = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct dendrimer.generation from DendrimerComposition dendrimer where dendrimer.generation is not null ";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				generations.add(obj.toString());
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all Dendrimer Generations.");
			throw new RuntimeException(
					"Problem to retrieve all Dendrimer Generations. ");
		} finally {
			ida.close();
		}
		generations.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_DENDRIMER_GENERATIONS));

		return (String[]) generations.toArray(new String[0]);
	}

	public String[] getAllMetalCompositions() {
		String[] compositions = new String[] { "Gold", "Sliver", "Iron oxide" };
		return compositions;
	}

	public String[] getAllPolymerInitiators() throws Exception {
		SortedSet<String> initiators = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct polymer.initiator from PolymerComposition polymer where polymer.initiator is not null ";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				initiators.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all Polymer Initiator.");
			throw new RuntimeException(
					"Problem to retrieve all Polymer Initiator. ");
		} finally {
			ida.close();
		}
		initiators.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_POLYMER_INITIATORS));

		return (String[]) initiators.toArray(new String[0]);
	}

	public List<String> getAllParticleSources() throws Exception {
		// TODO fill in db code
		return getAllSampleSources();
	}

	public String getParticleClassification(String particleType) {
		String classification = CaNanoLabConstants.PARTICLE_CLASSIFICATION_MAP
				.get(particleType);
		return classification;
	}

	/**
	 * 
	 * @return a map between a characterization type and its child
	 *         characterizations.
	 */
	public Map<String, List<String>> getCharacterizationTypeCharacterizations()
			throws Exception {
		Map<String, List<String>> charTypeChars = new HashMap<String, List<String>>();
		HibernateDataAccess hda = HibernateDataAccess.getInstance();
		try {
			hda.open();
			List<String> chars = null;
			String query = "select distinct a.category, a.name from def_characterization_category a "
					+ "where a.name not in (select distinct b.category from characterization_category b) "
					+ "order by a.category, a.name";
			SQLQuery queryObj = hda.getNativeQuery(query);
			queryObj.addScalar("CATEGORY", Hibernate.STRING);
			queryObj.addScalar("NAME", Hibernate.STRING);
			List results = queryObj.list();
			for (Object obj : results) {
				Object[] objarr = (Object[]) obj;
				String type = objarr[0].toString();
				String name = objarr[1].toString();
				if (charTypeChars.get(type) != null) {
					chars = (List<String>) charTypeChars.get(type);
				} else {
					chars = new ArrayList<String>();
					charTypeChars.put(type, chars);
				}
				chars.add(name);
			}
		} catch (Exception e) {
			logger
					.error("Problem to retrieve all characterization type characterizations. "
							+ e);
			throw new RuntimeException(
					"Problem to retrieve all characteriztaion type characterizations. ");
		} finally {
			hda.close();
		}
		return charTypeChars;
	}

	public List<LabelValueBean> getAllInstrumentTypeAbbrs() throws Exception {
		List<LabelValueBean> instrumentTypeAbbrs = new ArrayList<LabelValueBean>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct instrumentType.name, instrumentType.abbreviation from InstrumentType instrumentType where instrumentType.name is not null";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				if (obj != null) {
					Object[] objs = (Object[]) obj;
					String label = "";
					if (objs[1] != null) {
						label = (String) objs[0] + "(" + objs[1] + ")";
					} else {
						label = (String) objs[0];
					}
					instrumentTypeAbbrs.add(new LabelValueBean(label,
							(String) objs[0]));
				}
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all instrumentTypes. " + e);
			throw new RuntimeException(
					"Problem to retrieve all intrument types. ");
		} finally {
			ida.close();
		}
		instrumentTypeAbbrs.add(new LabelValueBean(CaNanoLabConstants.OTHER,
				CaNanoLabConstants.OTHER));

		return instrumentTypeAbbrs;
	}

	public Map<String, SortedSet<String>> getAllInstrumentManufacturers()
			throws Exception {
		Map<String, SortedSet<String>> instrumentManufacturers = new HashMap<String, SortedSet<String>>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();

			String hqlString = "select distinct instrumentType.name, manufacturer.name from InstrumentType instrumentType join instrumentType.manufacturerCollection manufacturer ";
			List results = ida.search(hqlString);
			SortedSet<String> manufacturers = null;
			for (Object obj : results) {
				String instrumentType = ((Object[]) obj)[0].toString();
				String manufacturer = ((Object[]) obj)[1].toString();
				if (instrumentManufacturers.get(instrumentType) != null) {
					manufacturers = (SortedSet<String>) instrumentManufacturers
							.get(instrumentType);
				} else {
					manufacturers = new TreeSet<String>();
					instrumentManufacturers.put(instrumentType, manufacturers);
				}
				manufacturers.add(manufacturer);
			}
			List allResult = ida
					.search("select manufacturer.name from Manufacturer manufacturer where manufacturer.name is not null");
			SortedSet<String> allManufacturers = null;
			allManufacturers = new TreeSet<String>();
			for (Object obj : allResult) {
				String name = (String) obj;
				allManufacturers.add(name);
			}
			instrumentManufacturers.put(CaNanoLabConstants.OTHER,
					allManufacturers);

		} catch (Exception e) {
			logger
					.error("Problem to retrieve manufacturers for intrument types "
							+ e);
			throw new RuntimeException(
					"Problem to retrieve manufacturers for intrument types.");
		} finally {
			ida.close();
		}
		return instrumentManufacturers;
	}

	public String[] getAllMorphologyTypes() throws Exception {

		SortedSet<String> morphologyTypes = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct morphology.type from Morphology morphology";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				morphologyTypes.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all morphology types.");
			throw new RuntimeException(
					"Problem to retrieve all morphology types.");
		} finally {
			ida.close();
		}
		morphologyTypes.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_MORPHOLOGY_TYPES));
		return (String[]) morphologyTypes.toArray(new String[0]);
	}

	public String[] getAllShapeTypes() throws Exception {
		SortedSet<String> shapeTypes = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct shape.type from Shape shape where shape.type is not null";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				shapeTypes.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all shape types.");
			throw new RuntimeException("Problem to retrieve all shape types.");
		} finally {
			ida.close();
		}
		shapeTypes
				.addAll(Arrays.asList(CaNanoLabConstants.DEFAULT_SHAPE_TYPES));

		return (String[]) shapeTypes.toArray(new String[0]);
	}

	public Map<ProtocolBean, List<ProtocolFileBean>> getAllProtocolNameVersionByType(
			String type) throws Exception {
		Map<ProtocolBean, List<ProtocolFileBean>> nameVersions = new HashMap<ProtocolBean, List<ProtocolFileBean>>();
		Map<Protocol, ProtocolBean> keyMap = new HashMap<Protocol, ProtocolBean>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select protocolFile, protocolFile.protocol from ProtocolFile protocolFile"
					+ " where protocolFile.protocol.type = '" + type + "'";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				Object[] array = (Object[]) obj;
				Object key = null;
				Object value = null;
				for (int i = 0; i < array.length; i++) {
					if (array[i] instanceof Protocol) {
						key = array[i];
					} else if (array[i] instanceof ProtocolFile) {
						value = array[i];
					}
				}

				if (keyMap.containsKey((Protocol) key)) {
					ProtocolBean pb = keyMap.get((Protocol) key);
					List<ProtocolFileBean> localList = nameVersions.get(pb);
					ProtocolFileBean fb = new ProtocolFileBean();
					fb.setVersion(((ProtocolFile) value).getVersion());
					fb.setId(((ProtocolFile) value).getId().toString());
					localList.add(fb);
				} else {
					List<ProtocolFileBean> localList = new ArrayList<ProtocolFileBean>();
					ProtocolFileBean fb = new ProtocolFileBean();
					fb.setVersion(((ProtocolFile) value).getVersion());
					fb.setId(((ProtocolFile) value).getId().toString());
					localList.add(fb);
					ProtocolBean protocolBean = new ProtocolBean();
					Protocol protocol = (Protocol) key;
					protocolBean.setId(protocol.getId().toString());
					protocolBean.setName(protocol.getName());
					protocolBean.setType(protocol.getType());
					nameVersions.put(protocolBean, localList);
					keyMap.put((Protocol) key, protocolBean);
				}
			}
		} catch (Exception e) {
			logger
					.error("Problem to retrieve all protocol names and their versions by type "
							+ type);
			throw new RuntimeException(
					"Problem to retrieve all protocol names and their versions by type "
							+ type);
		} finally {
			ida.close();
		}
		return nameVersions;
	}

	public SortedSet<String> getAllProtocolTypes() throws Exception {
		SortedSet<String> protocolTypes = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct protocol.type from Protocol protocol where protocol.type is not null";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				protocolTypes.add((String) obj);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all protocol types.");
			throw new RuntimeException(
					"Problem to retrieve all protocol types.");
		} finally {
			ida.close();
		}
		return protocolTypes;
	}

	public SortedSet<ProtocolBean> getAllProtocols(UserBean user)
			throws Exception {
		SortedSet<ProtocolBean> protocolBeans = new TreeSet<ProtocolBean>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "from Protocol as protocol left join fetch protocol.protocolFileCollection";

			List results = ida.search(hqlString);
			for (Object obj : results) {
				Protocol p = (Protocol) obj;
				ProtocolBean pb = new ProtocolBean();
				pb.setId(p.getId().toString());
				pb.setName(p.getName());
				pb.setType(p.getType());
				PersistentSet set = (PersistentSet) p
						.getProtocolFileCollection();
				// HashSet hashSet = set.
				if (!set.isEmpty()) {
					List<ProtocolFileBean> list = new ArrayList<ProtocolFileBean>();
					for (Iterator it = set.iterator(); it.hasNext();) {
						ProtocolFile pf = (ProtocolFile) it.next();
						ProtocolFileBean pfb = new ProtocolFileBean();
						pfb.setId(pf.getId().toString());
						pfb.setVersion(pf.getVersion());
						list.add(pfb);
					}
					pb.setFileBeanList(filterProtocols(list, user));
				}
				if (!pb.getFileBeanList().isEmpty())
					protocolBeans.add(pb);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all protocol names and types.");
			throw new RuntimeException(
					"Problem to retrieve all protocol names and types.");
		} finally {
			ida.close();
		}
		return protocolBeans;
	}

	private List<ProtocolFileBean> filterProtocols(
			List<ProtocolFileBean> protocolFiles, UserBean user)
			throws Exception {
		UserService userService = new UserService(
				CaNanoLabConstants.CSM_APP_NAME);
		List<LabFileBean> tempList = new ArrayList<LabFileBean>();
		for (ProtocolFileBean pfb : protocolFiles) {
			tempList.add((LabFileBean) pfb);
		}
		List<LabFileBean> filteredProtocols = userService.getFilteredFiles(
				user, tempList);
		protocolFiles.clear();

		if (filteredProtocols == null || filteredProtocols.isEmpty())
			return protocolFiles;
		for (LabFileBean lfb : filteredProtocols) {
			protocolFiles.add((ProtocolFileBean) lfb);
		}
		return protocolFiles;
	}

	public String[] getAllStressorTypes() {
		String[] stressorTypes = new String[] { "Thermal", "PH", "Freeze thaw",
				"Photo", "Centrifugation", "Lyophilization", "Chemical",
				"Other" };
		return stressorTypes;
	}

	public String[] getAllAreaMeasureUnits() {
		String[] areaUnit = new String[] { "sq nm" };
		return areaUnit;
	}

	public String[] getAllChargeMeasureUnits() {
		String[] chargeUnit = new String[] { "a.u", "aC", "Ah", "C", "esu",
				"Fr", "statC" };
		return chargeUnit;
	}

	public String[] getAllDensityMeasureUnits() {
		String[] densityUnits = new String[] { "kg/L" };
		return densityUnits;
	}

	public String[] getAllControlTypes() {
		String[] controlTypes = new String[] { " ", "Positive", "Negative" };
		return controlTypes;
	}

	public String[] getAllConditionTypes() {
		String[] conditionTypes = new String[] { "Particle Concentration",
				"Temperature", "Time" };
		return conditionTypes;
	}

	public Map<String, String[]> getAllAgentTypes() {
		Map<String, String[]> agentTypes = new HashMap<String, String[]>();

		String[] therapeuticsAgentTypes = new String[] { "Peptide",
				"Small Molecule", "Antibody", "DNA", "Probe",
				"Image Contrast Agent", "Other" };
		agentTypes.put("Therapeutic", therapeuticsAgentTypes);
		String[] targetingAgentTypes = new String[] { "Peptide",
				"Small Molecule", "Antibody", "DNA", "Probe",
				"Image Contrast Agent", "Other" };
		agentTypes.put("Targeting", targetingAgentTypes);
		String[] imagingAgentTypes = new String[] { "Peptide",
				"Small Molecule", "Antibody", "DNA", "Probe",
				"Image Contrast Agent", "Other" };
		agentTypes.put("Imaging", imagingAgentTypes);
		String[] reportingAgentTypes = new String[] { "Peptide",
				"Small Molecule", "Antibody", "DNA", "Probe",
				"Image Contrast Agent", "Other" };
		agentTypes.put("Reporting", reportingAgentTypes);
		return agentTypes;
	}

	public Map<String, String[]> getAllAgentTargetTypes() {
		Map<String, String[]> agentTargetTypes = new HashMap<String, String[]>();
		String[] targetTypes = new String[] { "Receptor", "Antigen", "Other" };
		String[] targetTypes1 = new String[] { "Receptor", "Other" };
		String[] targetTypes2 = new String[] { "Other" };

		agentTargetTypes.put("Small Molecule", targetTypes2);
		agentTargetTypes.put("Peptide", targetTypes1);
		agentTargetTypes.put("Antibody", targetTypes);
		agentTargetTypes.put("DNA", targetTypes1);
		agentTargetTypes.put("Probe", targetTypes1);
		agentTargetTypes.put("Other", targetTypes2);
		agentTargetTypes.put("Image Contrast Agent", targetTypes2);
		return agentTargetTypes;
	}

	public String[] getAllTimeUnits() {
		String[] timeUnits = new String[] { "hours", "days", "months" };
		return timeUnits;
	}

	public String[] getAllTemperatureUnits() {
		String[] temperatureUnits = new String[] { "degrees celsius",
				"degrees fahrenhiet" };
		return temperatureUnits;
	}

	public String[] getAllConcentrationUnits() {
		String[] concentrationUnits = new String[] { "g/ml", "mg/ml", "pg/ml",
				"ug/ml", "ug/ul" };
		return concentrationUnits;
	}

	public Map<String, String[]> getAllConditionUnits() {
		Map<String, String[]> conditionTypeUnits = new HashMap<String, String[]>();
		String[] concentrationUnits = new String[] { "g/ml", "mg/ml", "pg/ml",
				"ug/ml", "ug/ul", };
		String[] temperatureUnits = new String[] { "degrees celsius",
				"degrees fahrenhiet" };
		String[] timeUnits = new String[] { "hours", "days", "months" };
		conditionTypeUnits.put("Particle Concentration", concentrationUnits);
		conditionTypeUnits.put("Time", timeUnits);
		conditionTypeUnits.put("Temperature", temperatureUnits);
		return conditionTypeUnits;
	}

	public String[] getAllCellLines() throws Exception {

		SortedSet<String> cellLines = new TreeSet<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select distinct cellViability.cellLine, caspase.cellLine from CellViability cellViability, Caspase3Activation caspase";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				// cellLines.add((String) obj);
				Object[] objects = (Object[]) obj;
				for (Object object : objects) {
					if (object != null) {
						cellLines.add((String) object);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all Cell lines.");
			throw new RuntimeException("Problem to retrieve all Cell lines.");
		} finally {
			ida.close();
		}
		cellLines.addAll(Arrays.asList(CaNanoLabConstants.DEFAULT_CELLLINES));
		return (String[]) cellLines.toArray(new String[0]);

	}

	public String[] getAllActivationMethods() {
		String[] activationMethods = new String[] { "NMR", "MRI", "Radiation",
				"Ultrasound", "Ultraviolet Light" };
		return activationMethods;
	}

	public List<LabelValueBean> getAllSpecies() throws Exception {
		List<LabelValueBean> species = new ArrayList<LabelValueBean>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		species.add(new LabelValueBean("", ""));
		try {
			for (int i = 0; i < CaNanoLabConstants.SPECIES_COMMON.length; i++) {
				String specie = CaNanoLabConstants.SPECIES_COMMON[i];
				species.add(new LabelValueBean(specie, specie));
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all species. " + e);
			throw new RuntimeException(
					"Problem to retrieve all intrument types. ");
		} finally {
			ida.close();
		}

		return species;
	}

	public String[] getAllReportTypes() {
		String[] allReportTypes = new String[] { CaNanoLabConstants.REPORT,
				CaNanoLabConstants.ASSOCIATED_FILE };
		return allReportTypes;
	}

	/**
	 * Get other particles from the given particle source
	 * 
	 * @param particleSource
	 * @param particleName
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public SortedSet<String> getOtherParticles(String particleSource,
			String particleName, UserBean user) throws Exception {

		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		UserService userService = new UserService(
				CaNanoLabConstants.CSM_APP_NAME);
		SortedSet<String> otherParticleNames = new TreeSet<String>();
		try {
			ida.open();
			String hqlString = "select particle.name from Nanoparticle particle where particle.source.organizationName='"
					+ particleSource
					+ "' and particle.name !='"
					+ particleName
					+ "'";

			List results = ida.search(hqlString);

			for (Object obj : results) {
				String otherParticleName = (String) obj;
				// check if user can see the particle
				boolean status = userService.checkReadPermission(user,
						otherParticleName);
				if (status) {
					otherParticleNames.add(otherParticleName);
				}
			}
		} catch (Exception e) {
			logger
					.error("Error in retrieving all particle type particles. ",
							e);
			throw new RuntimeException(
					"Error in retrieving all particle type particles. ");
		} finally {
			ida.close();
		}
		return otherParticleNames;
	}

	public List<InstrumentBean> getAllInstruments() throws Exception {
		List<InstrumentBean> instruments = new ArrayList<InstrumentBean>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "from Instrument instrument where instrument.type is not null and instrument.manufacturer is not null order by instrument.type";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				Instrument instrument = (Instrument) obj;
				instruments.add(new InstrumentBean(instrument));
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all instruments. " + e);
			throw new RuntimeException("Problem to retrieve all intruments. ");
		} finally {
			ida.close();
		}
		return instruments;
	}

	public List<String> getAllDerivedDataFileTypes() throws Exception {
		List<String> fileTypes = new ArrayList<String>();
		// TODO query from database
		fileTypes.addAll(Arrays
				.asList(CaNanoLabConstants.DEFAULT_DERIVED_DATA_FILE_TYPES));
		return fileTypes;
	}

	public Map<String, List<String>> getAllDerivedDataCategories()
			throws Exception {
		Map<String, List<String>> categoryMap = new HashMap<String, List<String>>();
		// TODO query from database
		List<String> categories = new ArrayList<String>();
		categories.add("Volume Distribution");
		categories.add("Intensity Distribution");
		categories.add("Number Distribution");
		categoryMap.put("Size", categories);
		return categoryMap;
	}

	public List<String> getAllFunctionTypes() throws Exception {
		List<String> types = new ArrayList<String>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select funcType.name from FunctionType funcType order by funcType.name";
			List results = ida.search(hqlString);
			for (Object obj : results) {
				String type = (String) obj;
				types.add(type);
			}
		} catch (Exception e) {
			logger.error("Problem to retrieve all function types. " + e);
			throw new RuntimeException(
					"Problem to retrieve all function types. ");
		} finally {
			ida.close();
		}
		return types;
	}

	public List<CharacterizationTypeBean> getAllCharacterizationTypes()
			throws Exception {
		List<CharacterizationTypeBean> charTypes = new ArrayList<CharacterizationTypeBean>();
		HibernateDataAccess hda = HibernateDataAccess.getInstance();
		try {
			hda.open();
			String query = "select distinct category, has_action, indent_level, category_order from def_characterization_category order by category_order";
			SQLQuery queryObj = hda.getNativeQuery(query);
			queryObj.addScalar("CATEGORY", Hibernate.STRING);
			queryObj.addScalar("HAS_ACTION", Hibernate.INTEGER);
			queryObj.addScalar("INDENT_LEVEL", Hibernate.INTEGER);
			queryObj.addScalar("CATEGORY_ORDER", Hibernate.INTEGER);
			List results = queryObj.list();
			for (Object obj : results) {
				Object[] objarr = (Object[]) obj;
				String type = objarr[0].toString();
				boolean hasAction = ((Integer) objarr[1] == 0) ? false : true;
				int indentLevel = (Integer) objarr[2];
				CharacterizationTypeBean charType = new CharacterizationTypeBean(
						type, indentLevel, hasAction);
				charTypes.add(charType);
			}
		} catch (Exception e) {
			logger
					.error("Problem to retrieve all characterization types. "
							+ e);
			throw new RuntimeException(
					"Problem to retrieve all characteriztaion types. ");
		} finally {
			hda.close();
		}
		return charTypes;
	}

	public Map<String, SortedSet<String>> getDerivedDataCategoryMap(
			String characterizationName) throws Exception {
		Map<String, SortedSet<String>> categoryMap = new HashMap<String, SortedSet<String>>();
		IDataAccess ida = (new DataAccessProxy())
				.getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select category.name, datumName.name from DerivedBioAssayDataCategory category left join category.datumNameCollection datumName where datumName.datumParsed=false and category.characterizationName='"
					+ characterizationName + "'";
			List results = ida.search(hqlString);
			SortedSet<String> datumNames = null;
			for (Object obj : results) {
				String categoryName = ((Object[]) obj)[0].toString();
				String datumName = ((Object[]) obj)[1].toString();
				if (categoryMap.get(categoryName) != null) {
					datumNames = categoryMap.get(categoryName);
				} else {
					datumNames = new TreeSet<String>();
					categoryMap.put(categoryName, datumNames);
				}
				datumNames.add(datumName);
			}

		} catch (Exception e) {
			logger
					.error("Problem to retrieve all derived bioassay data categories. "
							+ e);
			throw new RuntimeException(
					"Problem to retrieve all derived bioassay data categories.");

		} finally {
			ida.close();
		}
		return categoryMap;
	}
}