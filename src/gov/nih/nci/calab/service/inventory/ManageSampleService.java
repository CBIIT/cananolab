package gov.nih.nci.calab.service.inventory;

import gov.nih.nci.calab.db.DataAccessProxy;
import gov.nih.nci.calab.db.IDataAccess;
import gov.nih.nci.calab.domain.Sample;
import gov.nih.nci.calab.domain.SampleContainer;
import gov.nih.nci.calab.domain.SampleSOP;
import gov.nih.nci.calab.domain.SampleType;
import gov.nih.nci.calab.domain.Source;
import gov.nih.nci.calab.domain.StorageElement;
import gov.nih.nci.calab.dto.inventory.ContainerBean;
import gov.nih.nci.calab.dto.inventory.SampleBean;
import gov.nih.nci.calab.exception.DuplicateEntriesException;
import gov.nih.nci.calab.service.common.LookupService;
import gov.nih.nci.calab.service.util.CalabConstants;
import gov.nih.nci.calab.service.util.PropertyReader;
import gov.nih.nci.calab.service.util.StringUtils;

import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

/* CVS $Id: ManageSampleService.java,v 1.2 2006-07-31 21:44:12 pansu Exp $ 
 */
public class ManageSampleService {
	private static Logger logger = Logger.getLogger(ManageSampleService.class);

	/**
	 * 
	 * @return auto-generated default value for sample name prefix
	 */
	public String getDefaultSampleNamePrefix() throws Exception {
		// TODO: Get configurable sample format string
		String sampleNamePrefix = PropertyReader.getProperty(CalabConstants.CALAB_PROPERTY,"samplePrefix");
		long seqId = 0;
		IDataAccess ida = (new DataAccessProxy()).getInstance(IDataAccess.HIBERNATE);
		try {
			ida.open();
			String hqlString = "select max(sample.sampleSequenceId) from Sample sample";
			List results = ida.search(hqlString);
			logger.debug("ManageSampleService.getSampleSequenceId(): results.size = " + results.size());
			if (results.iterator().hasNext()) {
				Object obj = results.iterator().next();
				logger.debug("ManageSampleService.getSampleSequenceId(): obj = " + obj);
				if (obj != null){
					seqId = (Long)obj;					
				}
			}
			logger.debug("ManageSampleService.getSampleSequenceId(): current seq id = " + seqId);
		} catch (Exception e) {
			logger.error("Problem in retrieving default sample ID prefix.");
			throw new RuntimeException("Problem in retrieving default sample ID prefix.");
		} finally {
			ida.close();
		}

		return sampleNamePrefix + (seqId+1);
	}

	public Long getUserDefinedSequenceId(String userDefinedPrefix) {
		String newSequence = userDefinedPrefix.substring(userDefinedPrefix.lastIndexOf("-")+1);
		
		return StringUtils.convertToLong(newSequence);
		
	}
	/**
	 * 
	 * @param sampleNamePrefix
	 * @param lotId
	 * @return sampleName from sampleName prefix and lotId
	 */
	public String getSampleName(String sampleNamePrefix, String lotId) {
		if (lotId.equals(CalabConstants.EMPTY)) {
			return sampleNamePrefix;
		}
		return sampleNamePrefix + "-" + lotId;
	}

	/**
	 * 
	 * @return the default lot Id
	 */
	public String getDefaultLotId() {
		String lotId = CalabConstants.EMPTY;
		return lotId;
	}

	/**
	 * Saves the sample into the database
	 * 
	 * @throws Exception
	 */
	public void saveSample(SampleBean sample, ContainerBean[] containers)
	throws Exception{
	  
		boolean createNewSampleType = false;
		
		// Get existing sampleType to compare
		LookupService lookupService = new LookupService();
		List<String> existingSampleTypes = lookupService.getAllSampleTypes();

	  // check if the smaple is exist
	  // For NCL, sampleId + lotId is unique -- in SampleBean, sampleId issampleName
	      IDataAccess ida = (new DataAccessProxy()).getInstance(IDataAccess.HIBERNATE);
    	  try {
			ida.open();
			String hqlString = "select count(*) from Sample sample where sample.name = '" + sample.getSampleName()+"'";
			List results = ida.search(hqlString);
			if (results.iterator().hasNext())
			{
				Object obj = results.iterator().next();
				logger.debug("ManageSampleService.saveSample(): return object type = "
									+ obj.getClass().getName() + " | object value = " + obj);
				  // Yes, throws exception
				if (((Integer)obj).intValue() > 0)
				{
					throw new DuplicateEntriesException("The same sample already exists in the system.");
				}
			}
			// No, save it
			// Create Sample Object, save source, save sample, save storageElement, save SampleContainer, 
			// set storageElement in sampleContainer, save sampleContainer again
			
			// Create sample object
			Sample doSample = new Sample();
			
			// Front end source is a plain text, so just save the source object
			String sampleSourceName = sample.getSampleSource();
			if (( sampleSourceName != null) && (sampleSourceName.trim().length() > 0)) {
				List existedSources = ida.search("from Source source where source.organizationName = '" + sample.getSampleSource() + "'");
				
				Source source = null;
				if (existedSources.size() > 0){
					source = (Source)existedSources.get(0);
				} else {
					source = new Source();
					source.setOrganizationName(sample.getSampleSource());
					ida.store(source);
				}
				// Create releationship between this source and this sample
				doSample.setSource(source);
			}
			
			doSample.setComments(sample.getGeneralComments());
			doSample.setCreatedBy(sample.getSampleSubmitter());
			doSample.setCreatedDate(sample.getAccessionDate());
			doSample.setDescription(sample.getSampleDescription());
			doSample.setLotDescription(sample.getLotDescription());
			doSample.setLotId(sample.getLotId());
			doSample.setName(sample.getSampleName());
			if (sample.getSampleType().equalsIgnoreCase(CalabConstants.OTHER)) {
				doSample.setType(sample.getOtherSampleType());
				if (!existingSampleTypes.contains(sample.getOtherSampleType())) {
					createNewSampleType = true;					
				}
			} else {
				doSample.setType(sample.getSampleType());
			}
			// TODO: ReceivedBy and Date are not in the wireframe.

			doSample.setReceivedBy("");
			doSample.setReceivedDate(sample.getDateReceived());
			doSample.setSampleSequenceId(getUserDefinedSequenceId(sample.getSampleNamePrefix()));
			doSample.setSolubility(sample.getSolubility());
			doSample.setSourceSampleId(sample.getSourceSampleId());
			// TODO: Fill in the sample SOP info, if sampleBean can pass the primary key......
			String sopName = sample.getSampleSOP();
			if ( (sopName != null) && (sopName.length() > 0))
			{
				List existedSOP = ida.search("from SampleSOP sop where sop.name = '" + sopName + "'");					
				SampleSOP  sop = (SampleSOP)existedSOP.get(0);
				doSample.setSampleSOP(sop);	
			}

			//Save Sample
			ida.createObject(doSample);

			// Container list
			for (int i=0; i<containers.length;i++)
			{
				SampleContainer doSampleContainer = new SampleContainer();
				// use Hibernate Hilo algorithm to generate the id
				doSampleContainer.setComments(containers[i].getContainerComments());
				doSampleContainer.setConcentration(StringUtils.convertToFloat(containers[i].getConcentration()));
				doSampleContainer.setConcentrationUnit(containers[i].getConcentrationUnit());
				
				if ((containers[i].getContainerType().equals(CalabConstants.OTHER))) {
					doSampleContainer.setContainerType((containers[i].getOtherContainerType()));
				} else {
					doSampleContainer.setContainerType((containers[i].getContainerType()));
				}

				// Container is created by the same person who creates sample
				doSampleContainer.setCreatedBy(sample.getSampleSubmitter());
				doSampleContainer.setCreatedDate(sample.getAccessionDate());
				doSampleContainer.setDiluentsSolvent(containers[i].getSolvent());
				doSampleContainer.setQuantity(StringUtils.convertToFloat(containers[i].getQuantity()));
				doSampleContainer.setQuantityUnit(containers[i].getQuantityUnit());
				doSampleContainer.setSafetyPrecaution(containers[i].getSafetyPrecaution());
				doSampleContainer.setStorageCondition(containers[i].getStorageCondition());
				doSampleContainer.setVolume(StringUtils.convertToFloat(containers[i].getVolume()));
				doSampleContainer.setVolumeUnit(containers[i].getVolumeUnit());
				// TODO: set name is it suppose to be sequence number?
				doSampleContainer.setName(containers[i].getContainerName());
				
				// TODO: relationship with storage need to be added too.
				HashSet<StorageElement> storages = new HashSet<StorageElement>();
				
				String boxValue = containers[i].getStorageLocation().getBox();
				if (( boxValue != null)&&(boxValue.trim().length()>0))
				{
					List existedSE = ida.search("from StorageElement se where se.type = '" + CalabConstants.STORAGE_BOX + 
													 "' and se.location = '" + boxValue + "'");					
					StorageElement box = null;
					if (existedSE.size() > 0){
						box = (StorageElement)existedSE.get(0);
					} else {
						box = new StorageElement();
						box.setLocation(boxValue);
						box.setType(CalabConstants.STORAGE_BOX);
						ida.store(box);
					}
					// Create releationship between this source and this sample
					storages.add(box);			
				}
				
				String shelfValue = containers[i].getStorageLocation().getShelf();
				if ((shelfValue != null)&&(shelfValue.trim().length()>0))
				{
					List existedSE = ida.search("from StorageElement se where se.type = '" + CalabConstants.STORAGE_SHELF + 
													 "' and se.location = '" + shelfValue + "'");					
					StorageElement shelf = null;
					if (existedSE.size() > 0){
						shelf = (StorageElement)existedSE.get(0);
					} else {
						shelf = new StorageElement();
						shelf.setLocation(shelfValue);
						shelf.setType(CalabConstants.STORAGE_SHELF);
						ida.store(shelf);
					}
					// Create releationship between this source and this sample
					storages.add(shelf);			
				}
				
				String freezerValue = containers[i].getStorageLocation().getFreezer();
				if ( (freezerValue != null)&& (freezerValue.length()>0))
				{
					List existedSE = ida.search("from StorageElement se where se.type = '" + CalabConstants.STORAGE_FREEZER + 
													 "' and se.location = '" + freezerValue + "'");					
					StorageElement freezer = null;
					if (existedSE.size() > 0){
						freezer = (StorageElement)existedSE.get(0);
					} else {
						freezer = new StorageElement();
						freezer.setLocation(freezerValue);
						freezer.setType(CalabConstants.STORAGE_FREEZER);
						ida.store(freezer);
					}
					// Create releationship between this source and this sample
					storages.add(freezer);			
				}

				String roomValue = containers[i].getStorageLocation().getRoom();
				if ((roomValue != null)&&(roomValue.length()>0))
				{
					List existedSE = ida.search("from StorageElement se where se.type = '" + CalabConstants.STORAGE_ROOM + 
													 "' and se.location = '" + roomValue + "'");					
					StorageElement room = null;
					if (existedSE.size() > 0){
						room = (StorageElement)existedSE.get(0);
					} else {
						room = new StorageElement();
						room.setLocation(roomValue);
						room.setType(CalabConstants.STORAGE_ROOM);
						ida.store(room);
					}
					// Create releationship between this source and this sample
					storages.add(room);			
				}
							
				doSampleContainer.setSample(doSample);
				ida.store(doSampleContainer);
				
				logger.debug("ManageSampleService.saveSample(): same again with storage info");
				doSampleContainer.setStorageElementCollection(storages);
				ida.store(doSampleContainer);				
			}
			// save new sample type info if needed
			if (createNewSampleType) {
				SampleType newSampleType = new SampleType();
				newSampleType.setName(sample.getOtherSampleType());
				ida.store(newSampleType);
			}

		} catch (DuplicateEntriesException ce) {
    		throw ce; 		
    	} catch (Exception e){
			e.printStackTrace();
			logger.error("Problem saving the sample.");
			ida.rollback();
			throw e;
		} finally {
			ida.close();
		}
	  }
}
