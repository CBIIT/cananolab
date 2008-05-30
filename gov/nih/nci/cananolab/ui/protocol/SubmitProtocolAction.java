package gov.nih.nci.cananolab.ui.protocol;

import gov.nih.nci.cananolab.domain.common.ProtocolFile;
import gov.nih.nci.cananolab.dto.common.ProtocolFileBean;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.exception.CaNanoLabSecurityException;
import gov.nih.nci.cananolab.service.common.FileService;
import gov.nih.nci.cananolab.service.common.impl.FileServiceLocalImpl;
import gov.nih.nci.cananolab.service.protocol.ProtocolService;
import gov.nih.nci.cananolab.service.protocol.impl.ProtocolServiceLocalImpl;
import gov.nih.nci.cananolab.service.security.AuthorizationService;
import gov.nih.nci.cananolab.ui.core.AbstractDispatchAction;
import gov.nih.nci.cananolab.ui.security.InitSecuritySetup;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;

import java.util.List;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Create or update protocol file and protocol
 * 
 * @author pansu
 * 
 */
public class SubmitProtocolAction extends AbstractDispatchAction {

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		ProtocolFileBean pfileBean = (ProtocolFileBean) theForm.get("file");
		pfileBean.setupDomainFile(CaNanoLabConstants.FOLDER_PROTOCOL, user
				.getLoginName());
		ProtocolService service = new ProtocolServiceLocalImpl();
		service.saveProtocolFile((ProtocolFile) pfileBean.getDomainFile(),
				pfileBean.getNewFileData());
		// set visibility
		AuthorizationService authService = new AuthorizationService(
				CaNanoLabConstants.CSM_APP_NAME);
		authService.assignVisibility(pfileBean.getDomainFile().getId()
				.toString(), pfileBean.getVisibilityGroups());

		InitProtocolSetup.getInstance().persistProtocolDropdowns(request,
				pfileBean);
		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage("message.submitProtocol.file",
				pfileBean.getDomainFile().getTitle());
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveMessages(request, msgs);
		forward = mapping.findForward("success");
		return forward;
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InitProtocolSetup.getInstance().setProtocolDropdowns(request);
		return mapping.getInputForward();
	}

	public ActionForward setupUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InitProtocolSetup.getInstance().setProtocolDropdowns(request);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String fileId = request.getParameter("fileId");
		ProtocolService service = new ProtocolServiceLocalImpl();
		ProtocolFileBean pfileBean = service.findProtocolFileById(fileId);
		theForm.set("file", pfileBean);
		String selectedProtocolType = ((ProtocolFile) pfileBean.getDomainFile())
				.getProtocol().getType();
		String selectedProtocolName = ((ProtocolFile) pfileBean.getDomainFile())
				.getProtocol().getName();
		SortedSet<String> protocolNames = service
				.getProtocolNames(selectedProtocolType);
		request.getSession().setAttribute("protocolNamesByType", protocolNames);
		List<ProtocolFileBean> pFiles = service.findProtocolFilesBy(
				selectedProtocolType, selectedProtocolName, null);
		request.getSession().setAttribute("protocolFilesByTypeName", pFiles);
		FileService fileService = new FileServiceLocalImpl();
		fileService.retrieveVisibility(pfileBean, user);
		return mapping.getInputForward();
	}

	public boolean loginRequired() {
		return true;
	}

	public boolean canUserExecute(UserBean user)
			throws CaNanoLabSecurityException {
		return InitSecuritySetup.getInstance().userHasCreatePrivilege(user,
				CaNanoLabConstants.CSM_PG_PROTOCOL);
	}
}
