/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.characterization;

/**
 * This class represents an instrument used for characterization
 * @author pansu
 *
 */
public class InstrumentBean {
	private String id;
	private String type;
	private String description;
	private String manufacturer;
	private String abbreviation;
	
	public InstrumentBean() {
		
	}
	
	public InstrumentBean(String type, String description, String manufacturer, String abbreviation) {
		super();	
		this.type = type;
		this.description = description;
		this.manufacturer = manufacturer;
		this.abbreviation = abbreviation;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}
}