package gov.nih.nci.calab.dto.function;

import gov.nih.nci.calab.domain.nano.function.Peptide;

/**
 * This class represents properties of Peptide to be shown the function view
 * pages.
 * 
 * @author pansu
 * 
 */
public class PeptideBean extends BaseAgentBean {

	private String sequence;

	public PeptideBean() {
	}

	public PeptideBean(Peptide peptide) {
		super(peptide);
		this.sequence = peptide.getSequence();
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void updateDomainObj(Peptide peptide) {
		// super has been called in AgentBean level, so no need to call again
		// here.
		// super.updateDomainObj(peptide);
		peptide.setSequence(this.sequence);
	}
}
