/**
 * 
 */
package gov.nih.nci.calab.domain.nano.characterization.physical.composition;

/**
 * @author Zeng
 * 
 */
public class ComposingElement implements java.io.Serializable {
	private static final long serialVersionUID = 1234567890L;

	private Long id;

	private String chemicalName;

	private String elementType;

	private ParticleComposition particleComposition;

	private String description;

	/**
	 * 
	 */
	public ComposingElement() {
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChemicalName() {
		return chemicalName;
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public ParticleComposition getParticleComposition() {
		return particleComposition;
	}

	public void setParticleComposition(ParticleComposition composition) {
		this.particleComposition = composition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
