/**
 * 
 */
package gov.nih.nci.calab.domain.nano.characterization.invitro;

import gov.nih.nci.calab.domain.nano.characterization.toxicity.ImmunoToxicity;

/**
 * @author zengje
 *
 */
public class CytokineInduction extends ImmunoToxicity {
	private static final long serialVersionUID = 1234567890L;
	/**
	 * 
	 */
	public CytokineInduction() {
		
		
	}
	public String getImmunotoxiticyType() {
		return IMMUNE_CELL_FUNCTION_IMMUNOTOXICITY_CHARACTERIZATION;
	}


	public String getClassification() {
		return INVITRO_CHARACTERIZATION;
	}

	public String getName() {
		return IMMUNOCELLFUNCTOX_CYTOKINE_INDUCTION;
	}
}
