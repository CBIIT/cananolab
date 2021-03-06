package gov.nih.nci.cananolab.restful.view.edit;

import gov.nih.nci.cananolab.dto.common.FileBean;
import gov.nih.nci.cananolab.dto.common.ProtocolBean;

public class SimpleProtocol {
	//Proto needs:
			//property="achar.protocolBean.fileBean.domainFile.uri" 
			//property="achar.protocolBean.domain.id"
			//characterizationForm.map.achar.protocolBean.fileBean.domainFile.id
			//characterizationForm.map.achar.protocolBean.fileBean.domainFile.uri
	
	Long domainId;
	Long domainFileId;
	String domainFileUri;
	String displayName;
	
	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public Long getDomainFileId() {
		return domainFileId;
	}

	public void setDomainFileId(Long domainFileId) {
		this.domainFileId = domainFileId;
	}

	public String getDomainFileUri() {
		return domainFileUri;
	}

	public void setDomainFileUri(String domainFileUri) {
		this.domainFileUri = domainFileUri;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	
	
	public void transferFromProtocolBean(ProtocolBean protoBean) {
		if (protoBean == null) return;
		
		if (protoBean.getDomain().getId() != null)
			domainId = protoBean.getDomain().getId();
		FileBean domainFile = protoBean.getFileBean();
		if (domainFile != null && domainFile.getDomainFile() != null) {
			if (domainFile.getDomainFile().getId() != null)
				domainFileId = domainFile.getDomainFile().getId();
			domainFileUri = domainFile.getDomainFile().getUri();
		}
		displayName = protoBean.getDisplayName();
	}

	public void transferFromSimpleSubmitProtocol(SimpleSubmitProtocolBean simpleSubmitProtocolBean){
		if(simpleSubmitProtocolBean == null) return;


		domainId = simpleSubmitProtocolBean.getId();
		domainFileId = simpleSubmitProtocolBean.getFileId();
		domainFileUri = simpleSubmitProtocolBean.getUri();
		displayName = simpleSubmitProtocolBean.getName();
	}
}

