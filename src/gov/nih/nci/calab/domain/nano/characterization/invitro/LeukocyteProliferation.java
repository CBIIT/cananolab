package gov.nih.nci.calab.domain.nano.characterization.invitro;

import gov.nih.nci.calab.domain.nano.characterization.toxicity.ImmunoToxicity;

public class LeukocyteProliferation extends ImmunoToxicity {
	private static final long serialVersionUID = 1234567890L;

	public LeukocyteProliferation() {

	}

	public String getImmunotoxiticyType() {
		return IMMUNE_CELL_FUNCTION_IMMUNOTOXICITY_CHARACTERIZATION;
	}

	public String getClassification() {
		return INVITRO_CHARACTERIZATION;
	}

	public String getName() {
		return IMMUNOCELLFUNCTOX_LEUKOCYTE_PROLIFERATION;
	}
}
