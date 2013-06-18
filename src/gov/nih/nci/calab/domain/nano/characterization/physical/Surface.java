/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.domain.nano.characterization.physical;

import gov.nih.nci.calab.domain.Measurement;
import gov.nih.nci.calab.domain.nano.characterization.Characterization;

import java.util.ArrayList;
import java.util.Collection;

public class Surface extends Characterization {

	private static final long serialVersionUID = 1234567890L;

	private Measurement surfaceArea;

	private Measurement zetaPotential;

	private Measurement charge;

	private Boolean isHydrophobic;

	private Collection<SurfaceChemistry> surfaceChemistryCollection = new ArrayList<SurfaceChemistry>();

	public Surface() {

	}

	public String getClassification() {
		return PHYSICAL_CHARACTERIZATION;
	}

	public String getName() {
		return PHYSICAL_SURFACE;
	}

	public Measurement getCharge() {
		return this.charge;
	}

	public void setCharge(Measurement charge) {
		this.charge = charge;
	}

	public Boolean getIsHydrophobic() {
		return this.isHydrophobic;
	}

	public void setIsHydrophobic(Boolean isHydrophobic) {
		this.isHydrophobic = isHydrophobic;
	}

	public Measurement getSurfaceArea() {
		return this.surfaceArea;
	}

	public void setSurfaceArea(Measurement surfaceArrea) {
		this.surfaceArea = surfaceArrea;
	}

	// public Measurement getSurfaceCharge() {
	// return surfaceCharge;
	// }
	//
	// public void setSurfaceCharge(Measurement surfaceCharge) {
	// this.surfaceCharge = surfaceCharge;
	// }

	/*
	 * public Measurement getZetaPotential() { return zetaPotential; }
	 * 
	 * public void setZetaPotential(Measurement zetaPotential) {
	 * this.zetaPotential = zetaPotential; }
	 */
	public Collection<SurfaceChemistry> getSurfaceChemistryCollection() {
		return this.surfaceChemistryCollection;
	}

	public void setSurfaceChemistryCollection(
			Collection<SurfaceChemistry> surfaceChemistryCollection) {
		this.surfaceChemistryCollection = surfaceChemistryCollection;
	}

	public Measurement getZetaPotential() {
		return this.zetaPotential;
	}

	public void setZetaPotential(Measurement zetaPotential) {
		this.zetaPotential = zetaPotential;
	}
}
