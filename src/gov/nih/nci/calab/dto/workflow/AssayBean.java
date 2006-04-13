package gov.nih.nci.calab.dto.workflow;

import java.util.Set;

public class AssayBean {
	
	private String assayId;
	private String assayName;
	private String assayType;
	private String assayStr;
	private Set runBeans;
	
	public AssayBean() {
	
	}
	public AssayBean(String assayId, String assayName, String assayType) {
		super();
		// TODO Auto-generated constructor stub
		this.assayId = assayId;
		this.assayName = assayName;
		this.assayType = assayType;
	}
	public String getAssayId() {
		return assayId;
	}
	public void setAssayId(String assayId) {
		this.assayId = assayId;
	}
	public String getAssayName() {
		return assayName;
	}
	public void setAssayName(String assayName) {
		this.assayName = assayName;
	}
	public String getAssayType() {
		return assayType;
	}
	public void setAssayType(String assayType) {
		this.assayType = assayType;
	}
	public String getAssayStr() {
		return this.assayType + " : " + this.assayName;
	}
//	public void setAssayStr(String assayStr) {
//		this.assayStr = assayStr;
//	}
	public Set getRunBeans() {
		return runBeans;
	}
	public void setRunBeans(Set runBeans) {
		this.runBeans = runBeans;
	}
	
	

}
