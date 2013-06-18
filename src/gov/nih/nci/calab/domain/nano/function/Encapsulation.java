/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.domain.nano.function;

public class Encapsulation extends Linkage {
	private static final long serialVersionUID = 1234567890L;

	// private Long id;
	//
	// private String description;
	//
	private String localization;

	// private Agent agent;

	public Encapsulation() {

	}

	// public Long getId() {
	// return id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }

	public String getLocalization() {
		return this.localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}

	// public String getDescription() {
	// return description;
	// }
	//
	// public void setDescription(String description) {
	// this.description = description;
	// }
	//
	// public Agent getAgent() {
	// return agent;
	// }
	//
	// public void setAgent(Agent agent) {
	// this.agent = agent;
	// }

}