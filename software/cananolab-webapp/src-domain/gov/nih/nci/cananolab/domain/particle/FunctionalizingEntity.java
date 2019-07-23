package gov.nih.nci.cananolab.domain.particle;
// Generated Oct 17, 2017 2:18:23 PM by Hibernate Tools 4.0.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import gov.nih.nci.cananolab.domain.common.File;

/**
 * FunctionalizingEntity generated by hbm2java
 */
public class FunctionalizingEntity extends gov.nih.nci.cananolab.domain.particle.AssociatedElement
		implements java.io.Serializable {

	private Set<File> fileCollection = new HashSet<File>(0);
	private SampleComposition sampleComposition;
	private ActivationMethod activationMethod;
	private Set<Function> functionCollection = new HashSet<Function>(0);

	public FunctionalizingEntity() {
	}

	public FunctionalizingEntity(String createdBy, Date createdDate, String description, String molecularFormula,
			String molecularFormulaType, String name, float value, String valueUnit, String pubChemDataSourceName,
			long pubChemId, Set chemicalAssociationACollection, Set chemicalAssociationBCollection, Set fileCollection,
			SampleComposition sampleComposition, ActivationMethod activationMethod, Set functionCollection) {
		super(createdBy, createdDate, description, molecularFormula, molecularFormulaType, name, value, valueUnit,
				pubChemDataSourceName, pubChemId, chemicalAssociationACollection, chemicalAssociationBCollection);
		this.fileCollection = fileCollection;
		this.sampleComposition = sampleComposition;
		this.activationMethod = activationMethod;
		this.functionCollection = functionCollection;
	}

	public Set<File> getFileCollection() {
		return this.fileCollection;
	}

	public void setFileCollection(Set<File> fileCollection) {
		this.fileCollection = fileCollection;
	}

	public SampleComposition getSampleComposition() {
		return this.sampleComposition;
	}

	public void setSampleComposition(SampleComposition sampleComposition) {
		this.sampleComposition = sampleComposition;
	}

	public ActivationMethod getActivationMethod() {
		return this.activationMethod;
	}

	public void setActivationMethod(ActivationMethod activationMethod) {
		this.activationMethod = activationMethod;
	}

	public Set<Function> getFunctionCollection() {
		return this.functionCollection;
	}

	public void setFunctionCollection(Set<Function> functionCollection) {
		this.functionCollection = functionCollection;
	}

}