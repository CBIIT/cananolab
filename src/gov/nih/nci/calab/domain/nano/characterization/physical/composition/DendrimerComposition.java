package gov.nih.nci.calab.domain.nano.characterization.physical.composition;

import gov.nih.nci.calab.domain.nano.particle.Nanoparticle;
import gov.nih.nci.calab.service.util.CananoConstants;

import java.util.ArrayList;
import java.util.Collection;

public class DendrimerComposition implements ParticleComposition {
	private Long id;
	private String source;
	private String description;
	private String identificationName;
	private String classification;
	private String name;
	private Collection<Nanoparticle> nanoparticleCollection;
	private Collection<ComposingElement> composingElementCollection = new ArrayList<ComposingElement>();
	private String branch;
	private Float generation;
	private String molecularFormula;
	private String repeatUnit;
	
	private Collection<SurfaceGroup> surfaceGroupCollection=new ArrayList<SurfaceGroup>();
	public DendrimerComposition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Float getGeneration() {
		return generation;
	}

	public void setGeneration(Float generation) {
		this.generation = generation;
	}

	public String getMolecularFormula() {
		return molecularFormula;
	}

	public void setMolecularFormula(String molecularFormula) {
		this.molecularFormula = molecularFormula;
	}

	public String getRepeatUnit() {
		return repeatUnit;
	}

	public void setRepeatUnit(String repeatUnit) {
		this.repeatUnit = repeatUnit;
	}

	public Collection<SurfaceGroup> getSurfaceGroupCollection() {
		return surfaceGroupCollection;
	}

	public void setSurfaceGroupCollection(
			Collection<SurfaceGroup> surfaceGroupCollection) {
		this.surfaceGroupCollection = surfaceGroupCollection;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return this.source;
	}

	public String getClassification() {
		return CananoConstants.PHYSICAL_CHARACTERIZATION;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdentificationName() {
		return identificationName;
	}

	public void setIdentificationName(String identificationName) {
		this.identificationName = identificationName;
	}

	public String getName() {
		return CananoConstants.PHYSICAL_COMPOSITION;
	}

	public void setNanoparticleCollection(Collection<Nanoparticle> particleCollection) {
		this.nanoparticleCollection = particleCollection;
	}

	public Collection<Nanoparticle> getNanoparticleCollection() {
		return this.nanoparticleCollection;
	}

	public void setComposingElementCollection(Collection<ComposingElement> element){
		this.composingElementCollection = element;
	}
	
	public Collection<ComposingElement> getComposingElementCollection(){
		return this.composingElementCollection;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public void setName(String name) {
		this.name = name;
	}

}
