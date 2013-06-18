/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

/**
 * 
 */
package gov.nih.nci.calab.domain;

import java.io.Serializable;

/**
 * @author pansu
 * 
 */
public class InstrumentConfiguration implements Serializable {
	private static final long serialVersionUID = 1234567890L;

	private Long id;

	private Instrument instrument;

	private String description;

	/**
	 * 
	 */
	public InstrumentConfiguration() {

	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instrument getInstrument() {
		return this.instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
}
