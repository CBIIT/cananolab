package gov.nih.nci.cananolab.dto.particle.composition;

import gov.nih.nci.cananolab.domain.particle.samplecomposition.Antigen;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.Gene;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.OtherTarget;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.Receptor;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.Target;
import gov.nih.nci.cananolab.util.ClassUtils;

/**
 * Represents the view bean for the Target domain object
 * 
 * @author pansu
 * 
 */
public class TargetBean {
	private String type;

	private String name;

	private String description;

	private Antigen antigen = new Antigen();

	private Receptor receptor = new Receptor();

	private Gene gene = new Gene();

	private OtherTarget otherTarget = new OtherTarget();

	private String className;

	private Target domainTarget;

	public TargetBean() {
		
	}
	
	public TargetBean(Target target) {
		if (target instanceof Antigen) {
			antigen = (Antigen) target;
		} else if (target instanceof Receptor) {
			receptor = (Receptor) target;
		} else if (target instanceof Gene) {
			gene = (Gene) target;
		} else if (target instanceof OtherTarget) {
			otherTarget = (OtherTarget) target;
		}
		className = ClassUtils.getShortClassName(target.getClass()
				.getCanonicalName());
		domainTarget = target;
	}

	public Antigen getAntigen() {
		return antigen;
	}

	public void setAntigen(Antigen antigen) {
		this.antigen = antigen;
		antigen.setDescription(description);
		antigen.setName(name);
		domainTarget = antigen;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Gene getGene() {
		return gene;
	}

	public void setGene(Gene gene) {
		this.gene = gene;
		gene.setDescription(description);
		gene.setName(name);
		domainTarget = gene;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Receptor getReceptor() {
		return receptor;
	}

	public void setReceptor(Receptor receptor) {
		this.receptor = receptor;
		receptor.setDescription(description);
		receptor.setName(name);
		domainTarget = receptor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public OtherTarget getOtherTarget() {
		return otherTarget;
	}

	public void setOtherTarget(OtherTarget otherTarget) {
		this.otherTarget = otherTarget;
		otherTarget.setDescription(description);
		otherTarget.setName(name);
		otherTarget.setType(type);
		domainTarget = otherTarget;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Target getDomainTarget() {
		return domainTarget;
	}
	
}
