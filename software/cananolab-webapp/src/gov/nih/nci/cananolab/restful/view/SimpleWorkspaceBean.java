package gov.nih.nci.cananolab.restful.view;

import java.util.List;

public class SimpleWorkspaceBean {
	
	List<SimpleWorkspaceItem> samples;
	List<SimpleWorkspaceItem> protocols;
	List<SimpleWorkspaceItem> publications;
	
	public List<SimpleWorkspaceItem> getSamples() {
		return samples;
	}
	public void setSamples(List<SimpleWorkspaceItem> samples) {
		this.samples = samples;
	}
	public List<SimpleWorkspaceItem> getProtocols() {
		return protocols;
	}
	public void setProtocols(List<SimpleWorkspaceItem> protocols) {
		this.protocols = protocols;
	}
	public List<SimpleWorkspaceItem> getPublications() {
		return publications;
	}
	public void setPublications(List<SimpleWorkspaceItem> publications) {
		this.publications = publications;
	}

}
