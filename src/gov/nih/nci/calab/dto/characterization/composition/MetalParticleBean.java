/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

/**
 * 
 */
package gov.nih.nci.calab.dto.characterization.composition;

import gov.nih.nci.calab.domain.nano.characterization.physical.composition.MetalParticleComposition;

/**
 * This class represents properties of a Metal Particle composition to be shown
 * in the view page.
 * 
 * @author pansu
 */
public class MetalParticleBean extends BaseCoreShellCoatingBean {

	public MetalParticleBean() {
		super();
	}

	public MetalParticleBean(MetalParticleComposition doComp) {
		super(doComp);
	}
	
	public MetalParticleComposition getDomainObj() {
		MetalParticleComposition doComp = new MetalParticleComposition();
		super.updateDomainObj(doComp);
		return doComp;
	}
}
