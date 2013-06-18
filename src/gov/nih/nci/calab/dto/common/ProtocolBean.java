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
package gov.nih.nci.calab.dto.common;

import gov.nih.nci.calab.domain.Protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengje
 * 
 */
public class ProtocolBean implements Comparable {
	private String id;

	private String name;

	private String type;

	public ProtocolBean(Protocol protocol) {
		this.id = protocol.getId().toString();
		this.name = protocol.getName();
		this.type = protocol.getType();
	}

	private List<ProtocolFileBean> fileBeanList = new ArrayList<ProtocolFileBean>();

	/**
	 * 
	 */
	public ProtocolBean() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<ProtocolFileBean> getFileBeanList() {
		return this.fileBeanList;
	}

	public void setFileBeanList(List<ProtocolFileBean> fileBeanList) {
		this.fileBeanList = fileBeanList;
	}

	public int compareTo(Object obj) {
		if (obj instanceof ProtocolBean) {
			ProtocolBean inPb = (ProtocolBean) obj;
			int comparison = this.getName().compareTo(inPb.getName());
			if (comparison == 0) {
				comparison = this.getId().compareTo(inPb.getId());
			}
			return comparison;
		}
		return -1;
	}

	public boolean equals(Object obj) {
		// boolean eq = false;
		// if (obj instanceof ProtocolBean) {
		// ProtocolBean c = (ProtocolBean) obj;
		// String thisId = this.getId();
		// // String name = this.getName();
		// if (thisId != null && thisId.equals(c.getId())) { // &&
		// // name != null && name.equals(c.getName())) {
		// eq = true;
		// }
		// }
		// return eq;
		if (this.compareTo(obj) == 0) {
			return true;
		} else {
			return false;
		}
	}
}
