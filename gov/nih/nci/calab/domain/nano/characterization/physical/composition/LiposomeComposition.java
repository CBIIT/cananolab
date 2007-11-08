package gov.nih.nci.calab.domain.nano.characterization.physical.composition;

public class LiposomeComposition extends ParticleComposition {

	private static final long serialVersionUID = 1234567890L;

	private Boolean polymerized;

	private String polymerName;

	public LiposomeComposition() {

	}

	public Boolean getPolymerized() {
		return this.polymerized;
	}

	public void setPolymerized(Boolean polymerized) {
		this.polymerized = polymerized;
	}

	public String getPolymerName() {
		return this.polymerName;
	}

	public void setPolymerName(String polymerName) {
		this.polymerName = polymerName;
	}

	public String getClassification() {
		return PHYSICAL_CHARACTERIZATION;
	}

	public String getName() {
		return PHYSICAL_COMPOSITION;
	}
}
