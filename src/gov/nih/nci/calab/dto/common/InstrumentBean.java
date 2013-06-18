/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.common;

import gov.nih.nci.calab.domain.Instrument;
import gov.nih.nci.calab.service.util.StringUtils;

/**
 * This class represents an instrument associated with instrument configuration
 * 
 * @author pansu
 * 
 */
public class InstrumentBean {
	private String id;

	private String type;

	private String manufacturer;

	private String abbreviation;

	public InstrumentBean() {

	}

	public InstrumentBean(String type, String description, String manufacturer,
			String abbreviation) {

		this.type = type;
		this.manufacturer = manufacturer;
		this.abbreviation = abbreviation;
	}

	public InstrumentBean(Instrument instrument) {
		this.id = StringUtils.convertToString(instrument.getId());
		this.type = StringUtils.convertToString(instrument.getType());
		this.manufacturer = StringUtils.convertToString(instrument
				.getManufacturer());
		this.abbreviation = StringUtils.convertToString(instrument
				.getAbbreviation());
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Instrument getDomainObject() {
		Instrument instrument = new Instrument();
		if (this.id != null && this.id.length() > 0) {
			instrument.setId(new Long(this.id));
		}
		instrument.setType(this.type);
		instrument.setManufacturer(this.manufacturer);
		instrument.setAbbreviation(this.abbreviation);
		return instrument;
	}
}