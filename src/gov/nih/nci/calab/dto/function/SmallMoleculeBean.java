/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.function;

import gov.nih.nci.calab.domain.nano.function.SmallMolecule;

/**
 * This class represents properties of SmallMolecule to be shown the function
 * view pages.
 * 
 * @author pansu
 * 
 */
public class SmallMoleculeBean extends BaseAgentBean {
	private String name;

	private String compoundName;

	public SmallMoleculeBean() {
	}

	public SmallMoleculeBean(SmallMolecule smallMolecule) {
		super(smallMolecule);
		this.name = smallMolecule.getName();
		this.compoundName = smallMolecule.getCompoundName();
	}

	public String getCompoundName() {
		return this.compoundName;
	}

	public void setCompoundName(String compoundName) {
		this.compoundName = compoundName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void updateDomainObj(SmallMolecule smallMolecule) {
		// super has been called in AgentBean level, so no need to call again
		// here.
		// super.updateDomainObj(smallMolecule);
		smallMolecule.setName(this.name);
		smallMolecule.setCompoundName(this.compoundName);
	}
}
