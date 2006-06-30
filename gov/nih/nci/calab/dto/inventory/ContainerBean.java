package gov.nih.nci.calab.dto.inventory;

import gov.nih.nci.calab.domain.SampleContainer;
import gov.nih.nci.calab.domain.StorageElement;
import gov.nih.nci.calab.dto.common.SortableName;
import gov.nih.nci.calab.service.util.CalabConstants;
import gov.nih.nci.calab.service.util.StringUtils;

import java.util.Set;

/**
 * This class represents all properties of a container that need to be viewed
 * and saved.
 * 
 * @author pansu
 * 
 */
/* CVS $Id: ContainerBean.java,v 1.1 2006-06-30 20:52:51 pansu Exp $ */

public class ContainerBean {
	private String containerId="";
	
	private String containerName = "";

	private String containerType = "";

	private String otherContainerType = "";

	private String quantity = "";

	private String quantityUnit = "";

	private String concentration = "";

	private String concentrationUnit = "";

	private String volume = "";

	private String volumeUnit = "";

	private String solvent = "";

	private String safetyPrecaution = "";

	private String storageCondition = "";

	private StorageLocation storageLocation;
	
	private String containerComments = "";

	private SampleBean sample;
	
    private int containerNumber=-1;

	public SampleBean getSample() {
		return sample;
	}

	public void setSample(SampleBean sample) {
		this.sample = sample;
	}

	public ContainerBean() {
		storageLocation = new StorageLocation();
	}

	public ContainerBean(ContainerBean container) {
		containerName = container.getContainerName();
		containerType = container.getContainerType();
		otherContainerType = container.getOtherContainerType();
		quantity = container.getQuantity();
		quantityUnit =container.getQuantityUnit();
		concentration = container.getConcentration();
		concentrationUnit =container.getConcentrationUnit();
		volume = container.getVolume();
		volumeUnit = container.getVolumeUnit();
		solvent = container.getSolvent();
		safetyPrecaution = container.getSafetyPrecaution();
		storageCondition = container.getStorageCondition();
		storageLocation=new StorageLocation(container.getStorageLocation());
		containerComments = container.getContainerComments();
	}

	public ContainerBean(String containerType, String otherContainerType,
			String quantity, String quantityUnit, String concentration,
			String concentrationUnit, String volume, String volumeUnit,
			String solvent, String safetyPrecaution, String storageCondition,
			StorageLocation storageLocation, String containerComments) {
		super();
		// TODO Auto-generated constructor stub
		this.containerType = containerType;
		this.otherContainerType = otherContainerType;
		this.quantity = quantity;
		this.quantityUnit = quantityUnit;
		this.concentration = concentration;
		this.concentrationUnit = concentrationUnit;
		this.volume = volume;
		this.volumeUnit = volumeUnit;
		this.solvent = solvent;
		this.safetyPrecaution = safetyPrecaution;
		this.storageCondition = storageCondition;
		this.storageLocation = storageLocation;
		this.containerComments = containerComments;
	}

	public ContainerBean(SampleContainer container) {
		this.containerId=StringUtils.convertToString(container.getId());
		this.containerName = StringUtils.convertToString(container.getName());
		this.containerType = StringUtils.convertToString(container
				.getContainerType());
		this.quantity = StringUtils.convertToString(container.getQuantity());
		this.quantityUnit = StringUtils.convertToString(container
				.getQuantityUnit());
		this.concentration = StringUtils.convertToString(container
				.getConcentration());
		this.concentrationUnit = StringUtils.convertToString(container
				.getConcentrationUnit());
		this.volume = StringUtils.convertToString(container.getVolume());
		this.volumeUnit = StringUtils
				.convertToString(container.getVolumeUnit());
		this.solvent = StringUtils.convertToString(container
				.getDiluentsSolvent());
		this.safetyPrecaution = StringUtils.convertToString(container
				.getSafetyPrecaution());
		this.storageCondition = StringUtils.convertToString(container
				.getStorageCondition());
		this.containerComments = StringUtils.convertToString(container
				.getComments());

		Set storageElements = (Set) container.getStorageElementCollection();
		String lab = null, room = null, freezer = null, shelf = null, rack = null, box = null;
		if (storageElements != null) {
			for (Object obj : storageElements) {
				StorageElement element = (StorageElement) obj;
				String location = element.getLocation();
				if (element.getType().equals(CalabConstants.STORAGE_LAB)) {
					lab = location;
				} else if (element.getType()
						.equals(CalabConstants.STORAGE_ROOM)) {
					room = location;
				} else if (element.getType().equals(
						CalabConstants.STORAGE_FREEZER)) {
					freezer = location;
				} else if (element.getType().equals(
						CalabConstants.STORAGE_SHELF)) {
					shelf = location;
				} else if (element.getType()
						.equals(CalabConstants.STORAGE_RACK)) {
					rack = location;
				} else if (element.getType().equals(CalabConstants.STORAGE_BOX)) {
					box = location;
				}
			}
		}
		this.storageLocation = new StorageLocation(lab, room, freezer, shelf,
				rack, box);
	}

	public String getConcentration() {
		return concentration;
	}

	public void setConcentration(String concentration) {
		this.concentration = concentration;
	}

	public String getConcentrationUnit() {
		return concentrationUnit;
	}

	public void setConcentrationUnit(String concentrationUnit) {
		this.concentrationUnit = concentrationUnit;
	}

	public String getContainerComments() {
		return containerComments;
	}

	public void setContainerComments(String containerComments) {
		this.containerComments = containerComments;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getOtherContainerType() {
		return otherContainerType;
	}

	public void setOtherContainerType(String otherContainerType) {
		this.otherContainerType = otherContainerType;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public String getSafetyPrecaution() {
		return safetyPrecaution;
	}

	public void setSafetyPrecaution(String safetyPrecaution) {
		this.safetyPrecaution = safetyPrecaution;
	}

	public String getSolvent() {
		return solvent;
	}

	public void setSolvent(String solvent) {
		this.solvent = solvent;
	}

	public String getStorageCondition() {
		return storageCondition;
	}

	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getVolumeUnit() {
		return volumeUnit;
	}

	public void setVolumeUnit(String volumeUnit) {
		this.volumeUnit = volumeUnit;
	}

	public StorageLocation getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(StorageLocation storageLocation) {
		this.storageLocation = storageLocation;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	/**
	 * Assume containerName has a sequenceNumber at the end
	 * 
	 * @return the sequence number for a container
	 */
	public Integer getContainerNumber() {
		
		if (containerName.matches("\\D*(\\d+)")) {
			try {
				containerNumber = Integer.parseInt(containerName.replaceAll(
						"\\D*(\\d+)", "$1"));
			} catch (Exception e) {
				return -1;
			}
		}
		return containerNumber;
	}
	
	//used for display tag
	public SortableName getSortableName() {
		return new SortableName(containerName);
	}

	public String getContainerId() {
		return containerId;
	}
}
