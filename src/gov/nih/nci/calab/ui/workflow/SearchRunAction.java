package gov.nih.nci.calab.ui.workflow;

/**
 * This Action class searches for assay runs for the given parameters.
 *  
 * @author pansu
 */

import gov.nih.nci.calab.dto.workflow.RunBean;
import gov.nih.nci.calab.service.util.CalabConstants;
import gov.nih.nci.calab.service.util.StringUtils;
import gov.nih.nci.calab.service.workflow.ExecuteWorkflowService;
import gov.nih.nci.calab.ui.core.AbstractDispatchAction;
import gov.nih.nci.calab.ui.core.InitSessionSetup;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class SearchRunAction extends AbstractDispatchAction {

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaValidatorForm theForm = (DynaValidatorForm) form;

		// Get Parameters from form elements
		// Run info
		String sampleSource = (String) theForm.get("sampleSource");
		String assayName = (String) theForm.get("assayName");
		String runBy = (String) theForm.get("runBy");
		String runDateStr = (String) theForm.get("runDate");
		Date runDate=StringUtils.convertToDate(runDateStr, CalabConstants.ACCEPT_DATE_FORMAT);
		ExecuteWorkflowService executeWorkflowService = new ExecuteWorkflowService();
		List<RunBean>runs = executeWorkflowService.searchRun(sampleSource, assayName, runBy, runDate);
		request.setAttribute("selectedRuns", runs);		

		return mapping.findForward("success");
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session=request.getSession();
		InitSessionSetup.getInstance().setAllSampleSources(session);
		InitSessionSetup.getInstance().setAllAssayTypeAssays(session);
		InitSessionSetup.getInstance().setAllUsers(session);
		return mapping.getInputForward();
	}
	
	public boolean loginRequired() {
		return true;
	}
}
