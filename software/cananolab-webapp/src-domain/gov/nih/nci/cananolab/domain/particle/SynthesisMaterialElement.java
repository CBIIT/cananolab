package gov.nih.nci.cananolab.domain.particle;
// Generated Apr 3, 2019, 8:32:55 PM by Hibernate Tools 5.4.2.Final


import gov.nih.nci.cananolab.domain.common.File;
import gov.nih.nci.cananolab.domain.common.Supplier;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SynthesisMaterialElement generated by hbm2java
 */
public class SynthesisMaterialElement implements Serializable {

	private long synthesisMaterialElementPkId;
	private SynthesisMaterial synthesisMaterial;

	public SynthesisMaterial getSynthesisMaterial() {
		return synthesisMaterial;
	}

	public void setSynthesisMaterial(SynthesisMaterial synthesisMaterial) {
		this.synthesisMaterial = synthesisMaterial;
	}

	private String molecularFormula;
	private String molecularFormulaType;
	private String description;
	private String createdBy;
	private Date createdDate;
	private String chemicalName;
	private Float value;
	private String valueUnit;
	private String pubChemDatasourceName;
	private Long pubChemId;
	private Set<SmeInherentFunction> SmeInherentFunction = new HashSet<SmeInherentFunction>(0);
	private Set<File> files = new HashSet<File>(0);
	private Supplier supplier;

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public SynthesisMaterialElement() {
	}

	public SynthesisMaterialElement(long synthesisMaterialElementPkId, SynthesisMaterial synthesisMaterial,
			String createdBy, Date createdDate) {
		this.synthesisMaterialElementPkId = synthesisMaterialElementPkId;
		this.synthesisMaterial = synthesisMaterial;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public SynthesisMaterialElement(long synthesisMaterialElementPkId, SynthesisMaterial synthesisMaterial,
			String molecularFormula, String molecularFormulaType, String description, String createdBy,
			Date createdDate, String chemicalName, Float value, String valueUnit, String pubChemDatasourceName,
			Long pubChemId, Set<SmeInherentFunction> SmeInherentFunction, Set<File> files) {
		this.synthesisMaterialElementPkId = synthesisMaterialElementPkId;
		this.synthesisMaterial = synthesisMaterial;
		this.molecularFormula = molecularFormula;
		this.molecularFormulaType = molecularFormulaType;
		this.description = description;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.chemicalName = chemicalName;
		this.value = value;
		this.valueUnit = valueUnit;
		this.pubChemDatasourceName = pubChemDatasourceName;
		this.pubChemId = pubChemId;
		this.SmeInherentFunction = SmeInherentFunction;
		this.files = files;
	}

	public long getId() {
		return this.synthesisMaterialElementPkId;
	}

	public void setId(long synthesisMaterialElementPkId) {
		this.synthesisMaterialElementPkId = synthesisMaterialElementPkId;
	}


	public String getMolecularFormula() {
		return this.molecularFormula;
	}

	public void setMolecularFormula(String molecularFormula) {
		this.molecularFormula = molecularFormula;
	}

	public String getMolecularFormulaType() {
		return this.molecularFormulaType;
	}

	public void setMolecularFormulaType(String molecularFormulaType) {
		this.molecularFormulaType = molecularFormulaType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getChemicalName() {
		return this.chemicalName;
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	public Float getValue() {
		return this.value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public String getValueUnit() {
		return this.valueUnit;
	}

	public void setValueUnit(String valueUnit) {
		this.valueUnit = valueUnit;
	}

	public String getPubChemDatasourceName() {
		return this.pubChemDatasourceName;
	}

	public void setPubChemDatasourceName(String pubChemDatasourceName) {
		this.pubChemDatasourceName = pubChemDatasourceName;
	}

	public Long getPubChemId() {
		return this.pubChemId;
	}

	public void setPubChemId(Long pubChemId) {
		this.pubChemId = pubChemId;
	}

	public Set getSmeInherentFunctions() {
		return this.SmeInherentFunction;
	}

	public void setSmeInherentFunctions(Set SmeInherentFunction) {
		this.SmeInherentFunction = SmeInherentFunction;
	}

	public Set<File> getFiles() {
		return this.files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

}
