/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.domain.nano.characterization;

import java.io.Serializable;

public class DerivedBioAssayDataCategory implements Serializable {
	private static final long serialVersionUID = 1234567890L;

	private Long id;

	private String characterizationName;

	private String name;

	public String getCharacterizationName() {
		return this.characterizationName;
	}

	public void setCharacterizationName(String characterizationName) {
		this.characterizationName = characterizationName;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
