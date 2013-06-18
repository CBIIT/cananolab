/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.function;

import gov.nih.nci.calab.domain.nano.function.Peptide;

/**
 * This class represents properties of Peptide to be shown the function view pages.
 * 
 * @author pansu
 * 
 */
public class PeptideBean extends BaseAgentBean{
	
	private String sequence;

	public PeptideBean() {	
	}
	public PeptideBean(Peptide peptide) {
		super(peptide);
		this.sequence=peptide.getSequence();
	}
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public void updateDomainObj(Peptide peptide) {
		// super has been called in AgentBean level, so no need to call again here.
//		super.updateDomainObj(peptide);
		peptide.setSequence(sequence);		
	}
}
