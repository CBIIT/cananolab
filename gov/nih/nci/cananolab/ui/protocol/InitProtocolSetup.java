package gov.nih.nci.cananolab.ui.protocol;

import gov.nih.nci.cananolab.domain.common.ProtocolFile;
import gov.nih.nci.cananolab.domain.particle.characterization.Characterization;
import gov.nih.nci.cananolab.domain.particle.characterization.invitro.InvitroCharacterization;
import gov.nih.nci.cananolab.domain.particle.characterization.physical.PhysicalCharacterization;
import gov.nih.nci.cananolab.dto.common.ProtocolFileBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationBean;
import gov.nih.nci.cananolab.service.protocol.ProtocolService;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.ui.security.InitSecuritySetup;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;
import gov.nih.nci.cananolab.util.ClassUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * This class sets up session level or servlet context level variables to be
 * used in various actions during the setup of query forms.
 * 
 * @author pansu
 * 
 */
public class InitProtocolSetup {
	private InitProtocolSetup() {
	}

	public static InitProtocolSetup getInstance() {
		return new InitProtocolSetup();
	}

	public void setProtocolDropdowns(HttpServletRequest request)
			throws Exception {
		InitSetup.getInstance().getDefaultAndOtherLookupTypes(request,
				"protocolTypes", "Protocol", "type", "otherType", true);
		InitSecuritySetup.getInstance().getAllVisibilityGroups(request);
	}

	public List<ProtocolFileBean> getProtocolFilesByChar(
			HttpServletRequest request, CharacterizationBean charBean)
			throws Exception {
		String protocolType = null;
		//create a new instance of type className
		Class clazz = ClassUtils.getFullClass(charBean.getClassName());
		Characterization achar = (Characterization) clazz.newInstance();
		if (achar instanceof PhysicalCharacterization) {
			protocolType = CaNanoLabConstants.PHYSICAL_ASSAY_PROTOCOL;
		} else if (achar instanceof InvitroCharacterization) {
			protocolType = CaNanoLabConstants.INVITRO_ASSAY_PROTOCOL;
		} else {
			protocolType = null; // update if in vivo is implemented
		}
		ProtocolService service = new ProtocolService();
		List<ProtocolFileBean> protocolFiles = service.findProtocolFilesBy(
				protocolType, null, null);
		request.getSession().setAttribute("characterizationProtocolFiles",
				protocolFiles);
		return protocolFiles;
	}

	public void persistProtocolDropdowns(HttpServletRequest request,
			ProtocolFileBean protocolFile) throws Exception {
		InitSetup.getInstance().persistLookup(
				request,
				"Protocol",
				"type",
				"otherType",
				((ProtocolFile) protocolFile.getDomainFile()).getProtocol()
						.getType());
		setProtocolDropdowns(request);
	}
}
