package gov.nih.nci.calab.domain.nano.function;


public class Probe extends Agent {
	private static final long serialVersionUID = 1234567890L;

	private String name;

	private String type;

	public Probe() {
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}