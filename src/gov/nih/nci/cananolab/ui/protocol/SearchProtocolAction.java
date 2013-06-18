/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.cananolab.ui.protocol;

import gov.nih.nci.cananolab.dto.common.ProtocolBean;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.exception.SecurityException;
import gov.nih.nci.cananolab.service.protocol.ProtocolService;
import gov.nih.nci.cananolab.service.protocol.impl.ProtocolServiceLocalImpl;
import gov.nih.nci.cananolab.service.protocol.impl.ProtocolServiceRemoteImpl;
import gov.nih.nci.cananolab.ui.core.BaseAnnotationAction;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.ui.security.InitSecuritySetup;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Search protocol file and protocol
 *
 * @author pansu
 *
 */
public class SearchProtocolAction extends BaseAnnotationAction {
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String fileTitle = (String) theForm.get("fileTitle");
		// strip wildcards if entered by user
		fileTitle = StringUtils.stripWildcards(fileTitle);
		String titleOperand = (String) theForm.get("titleOperand");
		if (titleOperand.equals(Constants.STRING_OPERAND_CONTAINS)
				&& !StringUtils.isEmpty(fileTitle)) {
			fileTitle = "*" + fileTitle + "*";
		}
		String protocolType = (String) theForm.get("protocolType");
		String protocolName = (String) theForm.get("protocolName");
		// strip wildcards if entered by user
		protocolName = StringUtils.stripWildcards(protocolName);

		String nameOperand = (String) theForm.get("nameOperand");
		if (nameOperand.equals(Constants.STRING_OPERAND_CONTAINS)
				&& !StringUtils.isEmpty(protocolName)) {
			protocolName = "*" + protocolName + "*";
		}
		String protocolAbbreviation = (String) theForm
				.get("protocolAbbreviation");
		// strip wildcards if entered by user
		protocolAbbreviation = StringUtils.stripWildcards(protocolAbbreviation);

		String abbreviationOperand = (String) theForm
				.get("abbreviationOperand");
		if (abbreviationOperand.equals(Constants.STRING_OPERAND_CONTAINS)
				&& !StringUtils.isEmpty(protocolAbbreviation)) {
			protocolAbbreviation = "*" + protocolAbbreviation + "*";
		}
		String[] searchLocations = new String[0];
		if (theForm.get("searchLocations") != null) {
			searchLocations = (String[]) theForm.getStrings("searchLocations");
		}
		if (searchLocations.length == 0) {
			searchLocations = new String[1];
			searchLocations[0] = Constants.APP_OWNER;
		}
		String gridNodeHostStr = (String) request
				.getParameter("searchLocations");
		if (searchLocations[0].indexOf("~") != -1 && gridNodeHostStr != null
				&& gridNodeHostStr.trim().length() > 0) {
			searchLocations = gridNodeHostStr.split("~");
		}
		List<ProtocolBean> allProtocols = new ArrayList<ProtocolBean>();
		ProtocolService service = null;
		for (String location : searchLocations) {
			if (location.equals(Constants.LOCAL_SITE)) {
				service = new ProtocolServiceLocalImpl(user);
			} else {
				String serviceUrl = InitSetup.getInstance().getGridServiceUrl(
						request, location);
				service = new ProtocolServiceRemoteImpl(serviceUrl);
			}
			List<ProtocolBean> protocols = service
					.findProtocolsBy(protocolType, protocolName,
							protocolAbbreviation, fileTitle);
			for (ProtocolBean protocol : protocols) {
				protocol.setLocation(location);
				if (protocol.getFileBean() != null) {
					protocol.getFileBean().setLocation(location);
				}
				allProtocols.add(protocol);
			}
		}
		if (allProtocols != null && !allProtocols.isEmpty()) {
			request.getSession().setAttribute("protocols", allProtocols);
			forward = mapping.findForward("success");
		} else {
			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage(
					"message.searchProtocol.noresult");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// InitSetup.getInstance().getGridNodesInContext(request);
		String[] selectedLocations = new String[] { Constants.LOCAL_SITE };
		String gridNodeHostStr = (String) request
				.getParameter("searchLocations");
		if (!StringUtils.isEmpty(gridNodeHostStr)) {
			selectedLocations = gridNodeHostStr.split("~");
		}
		if (Constants.LOCAL_SITE.equals(selectedLocations[0])
				&& selectedLocations.length == 1) {
			InitProtocolSetup.getInstance().setLocalSearchDropdowns(request);
		} else {
			InitProtocolSetup.getInstance().setRemoteSearchDropdowns(request);
		}
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		theForm.set("searchLocations", selectedLocations);
		return mapping.getInputForward();
	}

	public Boolean canUserExecutePrivateDispatch(UserBean user)
			throws SecurityException {
		return InitSecuritySetup.getInstance().userHasCreatePrivilege(user,
				Constants.CSM_PG_PROTOCOL);
	}
}
