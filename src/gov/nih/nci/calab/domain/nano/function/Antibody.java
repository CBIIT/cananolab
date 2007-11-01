package gov.nih.nci.calab.domain.nano.function;


public class Antibody extends Agent {
	private static final long serialVersionUID = 1234567890L;

	private String name;

	private String species;

	public Antibody() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}
}