package gov.nih.nci.cananolab.domain.particle;
// Generated Apr 3, 2019, 8:32:55 PM by Hibernate Tools 5.4.2.Final



import java.util.HashSet;
import java.util.Set;

/**
 * SynthesisFunctionalization generated by hbm2java
 */
public class SynthesisFunctionalization implements java.io.Serializable {

	private long synthesisFuntionalizationPkId;
	private Synthesis synthesis;
	private Set synthesisPurifications = new HashSet(0);

	public SynthesisFunctionalization() {
	}

	public SynthesisFunctionalization(long synthesisFuntionalizationPkId) {
		this.synthesisFuntionalizationPkId = synthesisFuntionalizationPkId;
	}

	public SynthesisFunctionalization(long synthesisFuntionalizationPkId, Synthesis synthesis,
			Set synthesisFuncPurifications) {
		this.synthesisFuntionalizationPkId = synthesisFuntionalizationPkId;
		this.synthesis = synthesis;
		this.synthesisPurifications = synthesisFuncPurifications;
	}

	public long getId() {
		return this.synthesisFuntionalizationPkId;
	}

	public void setId(long synthesisFuntionalizationPkId) {
		this.synthesisFuntionalizationPkId = synthesisFuntionalizationPkId;
	}

	public Synthesis getSynthesis() {
		return this.synthesis;
	}

	public void setSynthesis(Synthesis synthesis) {
		this.synthesis = synthesis;
	}

	public Set getSynthesisPurifications() {
		return this.synthesisPurifications;
	}

	public void setSynthesisPurifications(Set synthesisFuncPurifications) {
		this.synthesisPurifications = synthesisFuncPurifications;
	}

}
