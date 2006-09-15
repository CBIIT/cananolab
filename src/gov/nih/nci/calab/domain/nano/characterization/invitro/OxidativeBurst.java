package gov.nih.nci.calab.domain.nano.characterization.invitro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.nih.nci.calab.domain.Instrument;
import gov.nih.nci.calab.domain.nano.characterization.CharacterizationTable;
import gov.nih.nci.calab.domain.nano.characterization.physical.composition.ComposingElement;
import gov.nih.nci.calab.domain.nano.characterization.toxicity.ImmunoToxicity;
import gov.nih.nci.calab.domain.nano.particle.Nanoparticle;
import gov.nih.nci.calab.service.util.CananoConstants;

public class OxidativeBurst implements ImmunoToxicity {

	public OxidativeBurst() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Long id;
	private String source;
	private String description;
	private String identificationName;
	private String classification;
	private String name;
	private String createdBy;
	private Date createdDate;
	private Collection<Nanoparticle> nanoparticleCollection;
	private Collection<ComposingElement> composingElementCollection = new ArrayList<ComposingElement>();
	private Collection<CharacterizationTable> characterizationTableCollection = new ArrayList<CharacterizationTable>();
	private Instrument instrument;
	
	private String immunotoxicityType;


	/* (non-Javadoc)
	 * @see gov.nih.nci.calab.domain.nano.characterization.toxicity.ImmunoToxicity#getImmunotoxiticyType()
	 */
	public String getImmunotoxiticyType() {
		return CananoConstants.IMMUNE_CELL_FUNCTION_IMMUNOTOXICITY_CHARACTERIZATION;
	}

	/* (non-Javadoc)
	 * @see gov.nih.nci.calab.domain.nano.characterization.toxicity.ImmunoToxicity#setImmunotoxiticyType(java.lang.String)
	 */
	public void setImmunotoxiticyType(String type) {
		// TODO Auto-generated method stub
		this.immunotoxicityType = type;

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

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getClassification() {
		return CananoConstants.INVITRO_CHARACTERIZATION;
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

	public String getName() {
		return CananoConstants.IMMUNOCELLFUNCTOX_OXIDATIVE_BURST;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIdentificationName(String identificationName) {
		this.identificationName = identificationName;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Collection<CharacterizationTable> getCharacterizationTableCollection() {
		return characterizationTableCollection;
	}

	public void setCharacterizationTableCollection(
			Collection<CharacterizationTable> characterizationTableCollection) {
		this.characterizationTableCollection = characterizationTableCollection;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}


}
