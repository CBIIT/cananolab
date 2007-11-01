/**
 *
 */
package gov.nih.nci.calab.domain.nano.characterization.physical.composition;

/**
 * @author Zeng
 *
 */
public class SurfaceGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1234567890L;

	private Long id;

	private String name;

	private String modifier;

	/**
	 *
	 */
	public SurfaceGroup() {
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
