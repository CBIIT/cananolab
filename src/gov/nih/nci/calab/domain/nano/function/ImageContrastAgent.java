/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.domain.nano.function;

import java.util.ArrayList;
import java.util.Collection;

public class ImageContrastAgent extends Agent {
	private static final long serialVersionUID = 1234567890L;

	private Long id;

	private String description;

	private String name;

	private String type;

	private Collection<AgentTarget> agentTargetCollection = new ArrayList<AgentTarget>();

	public ImageContrastAgent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<AgentTarget> getAgentTargetCollection() {
		return agentTargetCollection;
	}

	public void setAgentTargetCollection(
			Collection<AgentTarget> agentTargetCollection) {
		this.agentTargetCollection = agentTargetCollection;
	}

}