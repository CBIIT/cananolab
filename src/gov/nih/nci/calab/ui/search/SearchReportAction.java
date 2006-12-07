package gov.nih.nci.calab.ui.search;

/**
 * This class searches nanoparticle report based on user supplied criteria
 * 
 * @author pansu
 */

/* CVS $Id: SearchReportAction.java,v 1.1 2006-12-07 17:47:38 pansu Exp $ */

import gov.nih.nci.calab.dto.common.LabFileBean;
import gov.nih.nci.calab.dto.common.UserBean;
import gov.nih.nci.calab.service.search.SearchNanoparticleService;
import gov.nih.nci.calab.service.security.UserService;
import gov.nih.nci.calab.service.submit.SubmitNanoparticleService;
import gov.nih.nci.calab.service.util.CalabConstants;
import gov.nih.nci.calab.service.util.PropertyReader;
import gov.nih.nci.calab.ui.core.AbstractDispatchAction;
import gov.nih.nci.calab.ui.core.InitSessionSetup;

import java.io.File;
import java.io.FileInputStream;
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

public class SearchReportAction extends AbstractDispatchAction {
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String reportTitle = (String) theForm.get("reportTitle");
		String particleType = (String) theForm.get("particleType");
		String[] functionTypes = (String[]) theForm.get("functionTypes");

		SearchNanoparticleService searchParticleService = new SearchNanoparticleService();
		List<LabFileBean> reports = searchParticleService.searchReports(
				reportTitle, particleType, functionTypes, user);

		if (reports != null && !reports.isEmpty()) {
			request.setAttribute("reports", reports);
			forward = mapping.findForward("success");
		} else {

			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage(
					"message.searchReport.noresult");
			msgs.add("message", msg);
			saveMessages(request, msgs);

			forward = mapping.getInputForward();
		}

		return forward;
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		InitSessionSetup.getInstance().setAllParticleTypeParticles(session);
		InitSessionSetup.getInstance().setAllParticleFunctionTypes(session);
		InitSessionSetup.getInstance().clearWorkflowSession(session);
		InitSessionSetup.getInstance().clearInventorySession(session);

		return mapping.getInputForward();
	}

	public boolean loginRequired() {
		return true;
	}

	/**
	 * Download action to handle report downloading and viewing
	 * 
	 * @param
	 * @return
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String fileId = request.getParameter("fileId");
		SubmitNanoparticleService service = new SubmitNanoparticleService();
		LabFileBean fileBean = service.getFile(fileId);
		String fileRoot = PropertyReader.getProperty(
				CalabConstants.FILEUPLOAD_PROPERTY, "fileRepositoryDir");
		File dFile = new File(fileRoot + File.separator + fileBean.getPath());
		if (dFile.exists()) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileBean.getName());
			response.setHeader("Cache-Control", "no-cache");

			java.io.InputStream in = new FileInputStream(dFile);
			java.io.OutputStream out = response.getOutputStream();

			byte[] bytes = new byte[32768];

			int numRead = 0;
			while ((numRead = in.read(bytes)) > 0) {
				out.write(bytes, 0, numRead);
			}
			out.close();
		} else {
			throw new Exception("ERROR: file not found.");
		}
		return null;
	}

	/*
	 * overwrite the one in AbstractDispatchAction because the tab 'Nanoparticle
	 * Search' also links to this action
	 */

	public boolean canUserExecute(HttpSession session) throws Exception {
		// check whether user has privilege to execute nanoparticle search pe or
		// execute search pe
		UserBean user = (UserBean) session.getAttribute("user");
		UserService userService = new UserService(CalabConstants.CSM_APP_NAME);
		boolean nanoSearchStatus = userService.checkExecutePermission(user,
				"public search");
		boolean searchStatus = InitSessionSetup.getInstance()
				.canUserExecuteClass(session, this.getClass());
		if (nanoSearchStatus || searchStatus) {
			return true;
		} else {
			return false;
		}
	}
}
