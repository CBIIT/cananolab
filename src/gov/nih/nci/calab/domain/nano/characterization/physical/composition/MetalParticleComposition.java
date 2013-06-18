/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.domain.nano.characterization.physical.composition;


public class MetalParticleComposition extends ParticleComposition {

	private static final long serialVersionUID = 1234567890L;

	public MetalParticleComposition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getClassification() {
		return PHYSICAL_CHARACTERIZATION;
	}

	public String getName() {
		return PHYSICAL_COMPOSITION;
	}
}
