/**
 * 
 */
package gov.nih.nci.calab.dto.characterization.physical;

import gov.nih.nci.calab.domain.nano.characterization.physical.SurfaceChemistry;

/**
 * @author zengje
 * 
 */
public class SurfaceChemistryBean {
	private String id;

	private String moleculeName;

	private String numberOfMolecules;

	private String molecularFormulaType;

	/**
	 * 
	 */
	public SurfaceChemistryBean() {
	}

	public SurfaceChemistryBean(SurfaceChemistry surfaceChemistry) {
		this.id = surfaceChemistry.getId().toString();
		this.moleculeName = surfaceChemistry.getMoleculeName();
		this.numberOfMolecules = (surfaceChemistry.getNumberOfMolecule() == null) ? "0"
				: surfaceChemistry.getNumberOfMolecule().toString();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMoleculeName() {
		return this.moleculeName;
	}

	public void setMoleculeName(String molecule) {
		this.moleculeName = molecule;
	}

	public void updateDomainObj(SurfaceChemistry surfaceChemistry) {
		surfaceChemistry
				.setNumberOfMolecule((getNumberOfMolecules().length() > 0) ? Integer
						.parseInt(getNumberOfMolecules())
						: null);
		surfaceChemistry.setMoleculeName(getMoleculeName());
		if (getId() != null && getId().length() > 0) {
			surfaceChemistry.setId(new Long(getId()));
		}
	}

	public String getNumberOfMolecules() {
		return this.numberOfMolecules;
	}

	public void setNumberOfMolecules(String numberOfMolecule) {
		this.numberOfMolecules = numberOfMolecule;
	}

	public String getMolecularFormulaType() {
		return this.molecularFormulaType;
	}

	public void setMolecularFormulaType(String molecularFormulaType) {
		this.molecularFormulaType = molecularFormulaType;
	}
}
