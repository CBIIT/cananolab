package gov.nih.nci.calab.domain.nano.characterization.invitro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.nih.nci.calab.domain.Instrument;
import gov.nih.nci.calab.domain.nano.characterization.Characterization;
import gov.nih.nci.calab.domain.nano.characterization.CharacterizationTable;
import gov.nih.nci.calab.domain.nano.particle.Nanoparticle;
import gov.nih.nci.calab.service.util.CananoConstants;

public class OxidativeStress implements Characterization {

	private static final long serialVersionUID = 1234567890L;

	private Long id;
	private String source;
	private String classification;
	private String description;
	private String identificationName;
	private String name;
	private String createdBy;
	private Date createdDate;
	private Collection<Nanoparticle> nanoparticleCollection;
	private Collection<CharacterizationTable> characterizationTableCollection = new ArrayList<CharacterizationTable>();
	private Instrument instrument;

	public OxidativeStress() {
		super();
		// TODO Auto-generated constructor stub
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

	public void setIdentificationName(String identificationName) {
		this.identificationName = identificationName;
	}

	public String getName() {
		return CananoConstants.TOXICITY_OXIDATIVE_STRESS;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNanoparticleCollection(Collection<Nanoparticle> particles) {
		this.nanoparticleCollection = particles;
	}

	public Collection<Nanoparticle> getNanoparticleCollection() {
		return this.nanoparticleCollection;
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
