/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.ui.submit;

/**
 * This class associates a assay result file with a characterization.  
 *  
 * @author pansu
 */

/* CVS $Id: LoadDerivedBioAssayDataAction.java,v 1.11 2007-01-04 23:21:58 pansu Exp $ */

import gov.nih.nci.calab.dto.common.LabFileBean;
import gov.nih.nci.calab.service.submit.SubmitNanoparticleService;
import gov.nih.nci.calab.service.util.CaNanoLabConstants;
import gov.nih.nci.calab.ui.core.AbstractDispatchAction;
import gov.nih.nci.calab.ui.core.InitSessionSetup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

public class LoadDerivedBioAssayDataAction extends AbstractDispatchAction {
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String particleName = (String) theForm.get("particleName");
		String fileSource = (String) theForm.get("fileSource");
		LabFileBean fileBean = (LabFileBean) theForm.get("file");
		String fileNumber = (String) theForm.get("fileNumber");
		String characterizationName = (String) theForm
				.get("characterizationName");
		SubmitNanoparticleService service = new SubmitNanoparticleService();
		LabFileBean savedFileBean = null;
		if (fileSource.equals("new")) {
			FormFile uploadedFile = (FormFile) theForm.get("uploadedFile");
			savedFileBean = service.saveCharacterizationFile(particleName,
					uploadedFile, characterizationName, fileBean);
		} else {
			// updating existingFileBean with form data
			if (fileBean.getId() != null) {
				LabFileBean existingFileBean = service.getFile(
						fileBean.getId(), CaNanoLabConstants.OUTPUT);
				existingFileBean.setTitle(fileBean.getTitle());
				existingFileBean.setDescription(fileBean.getDescription());
				existingFileBean.setVisibilityGroups(fileBean
						.getVisibilityGroups());
				existingFileBean.setKeywords(fileBean.getKeywords());
				savedFileBean = service
						.saveCharacterizationFile(existingFileBean);
			}
			else {
				throw new Exception("Please upload a new file if existing file drop-down list is empty or select a file from the drop-down list.");
			}
		}
		request.getSession().setAttribute("characterizationFile" + fileNumber,
				savedFileBean);

		String forwardPage = (String) theForm.get("forwardPage");
		forward = mapping.findForward(forwardPage);

		return forward;
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		InitSessionSetup.getInstance().clearWorkflowSession(session);
		InitSessionSetup.getInstance().clearSearchSession(session);
		InitSessionSetup.getInstance().clearInventorySession(session);
		String particleName = request.getParameter("particleName");
		InitSessionSetup.getInstance().setAllRunFiles(session, particleName);
		String fileNumber = request.getParameter("fileNumber");
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		theForm.set("particleName", particleName);
		theForm.set("fileNumber", fileNumber);
		theForm.set("forwardPage", (String) request
				.getAttribute("loadFileForward"));
		theForm.set("characterizationName", (String) request
				.getAttribute("characterization"));
		return mapping.getInputForward();
	}

	public ActionForward setupUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		InitSessionSetup.getInstance().clearWorkflowSession(session);
		InitSessionSetup.getInstance().clearSearchSession(session);
		InitSessionSetup.getInstance().clearInventorySession(session);
		String fileId = request.getParameter("fileId");
		SubmitNanoparticleService service = new SubmitNanoparticleService();
		LabFileBean fileBean = service.getDerivedDataFile(fileId);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		theForm.set("file", fileBean);
		String actionName = request.getParameter("actionName");
		String formName = request.getParameter("formName");
		request.setAttribute("actionName", actionName);
		request.setAttribute("formName", formName);
		return mapping.getInputForward();
	}

	public ActionForward setupView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return setupUpdate(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		LabFileBean fileBean = (LabFileBean) theForm.get("file");
		SubmitNanoparticleService service = new SubmitNanoparticleService();
		service.updateDerivedDataFileMetaData(fileBean);

		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage(
				"message.updateDerivedBioAssayData", fileBean.getPath());

		msgs.add("message", msg);
		saveMessages(request, msgs);
		return mapping.getInputForward();
	}

	public boolean loginRequired() {
		return true;
	}
}
