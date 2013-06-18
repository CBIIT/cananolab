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
package gov.nih.nci.calab.domain;

import gov.nih.nci.calab.domain.nano.particle.Nanoparticle;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author zengje
 * 
 */
public class Report extends LabFile {
	private static final long serialVersionUID = 1234567890L;

	private Collection<Nanoparticle> particleCollection = new HashSet<Nanoparticle>();

	/**
	 * 
	 */
	public Report() {

	}

	public Collection<Nanoparticle> getParticleCollection() {
		return this.particleCollection;
	}

	public void setParticleCollection(
			Collection<Nanoparticle> particleCollection) {
		this.particleCollection = particleCollection;
	}

}
