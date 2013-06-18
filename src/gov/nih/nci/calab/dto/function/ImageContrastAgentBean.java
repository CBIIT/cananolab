/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.function;

import gov.nih.nci.calab.domain.nano.function.ImageContrastAgent;

/**
 * This class represents properties of ImageContrastAgent to be shown the
 * function view pages.
 * 
 * @author pansu
 * 
 */
public class ImageContrastAgentBean extends BaseAgentBean {
	private String name;

	private String type;

	public ImageContrastAgentBean() {
	}

	public ImageContrastAgentBean(ImageContrastAgent imageContrastAgent) {
		super(imageContrastAgent);
		this.name = imageContrastAgent.getName();
		this.type = imageContrastAgent.getType();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void updateDomainObj(ImageContrastAgent imageContrastAgent) {
		// super has been called in AgentBean level, so no need to call again
		// here.
		// super.updateDomainObj(imageContrastAgent);
		imageContrastAgent.setName(this.name);
		imageContrastAgent.setType(this.type);
	}
}
