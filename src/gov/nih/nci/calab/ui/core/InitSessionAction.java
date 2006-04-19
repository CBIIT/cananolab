package gov.nih.nci.calab.ui.core;

/**
 * This class initializes session data to prepopulate the drop-down lists required
 * in different view pages.
 *
 * @author pansu
 */

/* CVS $Id: InitSessionAction.java,v 1.18 2006-04-19 21:03:34 pansu Exp $ */

import gov.nih.nci.calab.dto.administration.AliquotBean;
import gov.nih.nci.calab.dto.administration.ContainerInfoBean;
import gov.nih.nci.calab.dto.security.SecurityBean;
import gov.nih.nci.calab.dto.workflow.ExecuteWorkflowBean;
import gov.nih.nci.calab.service.administration.ManageAliquotService;
import gov.nih.nci.calab.service.administration.ManageSampleService;
import gov.nih.nci.calab.service.common.LookupService;
import gov.nih.nci.calab.service.search.SearchSampleService;
import gov.nih.nci.calab.service.search.SearchWorkflowService;
import gov.nih.nci.calab.service.util.CalabConstants;
import gov.nih.nci.calab.service.util.StringUtils;
import gov.nih.nci.calab.service.workflow.ExecuteWorkflowService;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

public class InitSessionAction extends AbstractBaseAction {
	private static Logger logger = Logger.getLogger(InitSessionAction.class);

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		ActionForward forward = null;
		String forwardPage = null;
		String urlPrefix = request.getContextPath();

		try {
			DynaActionForm theForm = (DynaActionForm) form;
			forwardPage = (String) theForm.get("forwardPage");

			// retrieve from sesssion first if available assuming these values
			// are not likely to change within the same session. If changed, the
			// session should be updated.
			LookupService lookupService = new LookupService();
			if (forwardPage.equals("useAliquot")) {
				String runId = (String) request.getParameter("runId");
				session.setAttribute("runId", runId);
				setUseAliquotSession(session, lookupService);
			} else if (forwardPage.equals("createSample")) {
				setCreateSampleSession(session, lookupService);

			} else if (forwardPage.equals("createAliquot")) {
				setCreateAliquotSession(session, lookupService, urlPrefix);
			} else if (forwardPage.equals("searchWorkflow")) {
				setSearchWorkflowSession(session, lookupService);
			} else if (forwardPage.equals("searchSample")) {
				setSearchSampleSession(session, lookupService);
			} else if (forwardPage.equals("createRun") || forwardPage.equals("createAssayRun")) {
				setCreateRunSession(session, lookupService);
			} else if (forwardPage.equals("workflowMessage")) {
				setWorkflowMessageSession(session);
			}
			// get user and date information
			String creator = "";
			if (session.getAttribute("user") != null) {
				SecurityBean user = (SecurityBean) session.getAttribute("user");
				creator = user.getLoginId();
			}
			String creationDate = StringUtils.convertDateToString(new Date(),
					CalabConstants.DATE_FORMAT);
			session.setAttribute("creator", creator);
			session.setAttribute("creationDate", creationDate);
			forward = mapping.findForward(forwardPage);

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			ActionMessage error = new ActionMessage("error.initSession",
					forwardPage);
			errors.add("error", error);
			saveMessages(request, errors);
			logger.error(
					"Caught exception loading initial drop-down lists data", e);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public boolean loginRequired() {
		return true;
	}

	/**
	 * Set up session attributes for use aliquot page
	 * 
	 * @param session
	 * @param lookupService
	 */
	private void setUseAliquotSession(HttpSession session,
			LookupService lookupService) throws Exception {
		if (session.getAttribute("allUnmaskedAliquots") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			List<AliquotBean> allAliquots = lookupService.getUnmaskedAliquots();
			session.setAttribute("allUnmaskedAliquots", allAliquots);
		}
		ExecuteWorkflowService executeWorkflowService = new ExecuteWorkflowService();
		if (session.getAttribute("workflow") == null
				|| session.getAttribute("newWorkflowCreated") != null) {
			ExecuteWorkflowBean workflowBean = executeWorkflowService
					.getExecuteWorkflowBean();
			session.setAttribute("workflow", workflowBean);
		}
		// clear the new aliquote created flag
		session.removeAttribute("newAliquotCreated");
		// clear the new workflow created flag
		session.removeAttribute("newWorkflowcreated");
	}

	/**
	 * Set up session attributes for create sample page
	 * 
	 * @param session
	 * @param lookupService
	 */
	private void setCreateSampleSession(HttpSession session,
			LookupService lookupService) {
		ManageSampleService mangeSampleService = new ManageSampleService();
		// if values don't exist in the database or if no new samples created.
		// call the service
		if (session.getAttribute("allSampleTypes") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List sampleTypes = lookupService.getAllSampleTypes();
			session.setAttribute("allSampleTypes", sampleTypes);

		}
		if (session.getAttribute("allSampleSOPs") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List sampleSOPs = mangeSampleService.getAllSampleSOPs();
			session.setAttribute("allSampleSOPs", sampleSOPs);
		}
		if (session.getAttribute("sampleContainerInfo") == null
				|| session.getAttribute("newSampleCreated") != null) {
			ContainerInfoBean containerInfo = lookupService
					.getSampleContainerInfo();
			session.setAttribute("sampleContainerInfo", containerInfo);
		}
		// clear the form in the session
		if (session.getAttribute("createSampleForm") != null
				|| session.getAttribute("newSampleCreated") != null) {
			session.removeAttribute("createSampleForm");
		}
		// clear the new sample created flag
		session.removeAttribute("newSampleCreated");
	}

	/**
	 * Set up session attributes for create aliquot page
	 * 
	 * @param session
	 * @param lookupService
	 */
	private void setCreateAliquotSession(HttpSession session,
			LookupService lookupService, String urlPrefix) {
		ManageAliquotService manageAliquotService = new ManageAliquotService();

		if (session.getAttribute("allSamples") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List samples = lookupService.getAllSamples();
			session.setAttribute("allSamples", samples);
		}
		if (session.getAttribute("allUnmaskedAliquots") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			List aliquots = lookupService.getUnmaskedAliquots();
			session.setAttribute("allUnmaskedAliquots", aliquots);
		}
		if (session.getAttribute("aliquotContainerInfo") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			ContainerInfoBean containerInfo = lookupService
					.getAliquotContainerInfo();
			session.setAttribute("aliquotContainerInfo", containerInfo);
		}
		if (session.getAttribute("aliquotCreateMethods") == null) {
			List methods = manageAliquotService
					.getAliquotCreateMethods(urlPrefix);
			session.setAttribute("aliquotCreateMethods", methods);
		}
		// clear the form in the session
		if (session.getAttribute("createAliquotForm") != null) {
			session.removeAttribute("createAliquotForm");
		}
		if (session.getAttribute("aliquotMatrix") != null) {
			session.removeAttribute("aliquotMatrix");
		}
		// clear new aliquot created flag and new sample created flag
		session.removeAttribute("newAliquotCreated");
		session.removeAttribute("newSampleCreated");
	}

	/**
	 * Set up session attributes for search workflow page
	 * 
	 * @param session
	 * @param lookupService
	 */
private void setSearchWorkflowSession(HttpSession session,
			LookupService lookupService) {
		SearchWorkflowService searchWorkflowService = new SearchWorkflowService();

		if (session.getAttribute("allUnmaskedAliquots") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			List<AliquotBean> allAliquots = lookupService.getUnmaskedAliquots();
			session.setAttribute("allUnmaskedAliquots", allAliquots);
		}		
		if (session.getAttribute("allAssayTypes") == null) {
			List assayTypes = lookupService.getAllAssayTypes();
			session.setAttribute("allAssayTypes", assayTypes);
		}
		if (session.getAttribute("allFileSubmitters") == null) {
			List submitters = searchWorkflowService.getAllFileSubmitters();
			session.setAttribute("allFileSubmitters", submitters);
		}
		// clear the new aliquote created flag
		session.removeAttribute("newAliquotCreated");
	}
	/**
	 * Set up session attributes for search sample page
	 * 
	 * @param session
	 * @param lookupService
	 */
	private void setSearchSampleSession(HttpSession session,
			LookupService lookupService) {
		SearchSampleService searchSampleService = new SearchSampleService();
		if (session.getAttribute("allSamples") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List samples = lookupService.getAllSamples();
			session.setAttribute("allSamples", samples);
		}
		if (session.getAttribute("allAliquots") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			List aliquots = lookupService.getAliquots();
			session.setAttribute("allAliquots", aliquots);
		}
		if (session.getAttribute("allSampleTypes") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List sampleTypes = lookupService.getAllSampleTypes();
			session.setAttribute("allSampleTypes", sampleTypes);
		}
		if (session.getAttribute("allSampleSources") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List sampleSources = searchSampleService.getAllSampleSources();
			session.setAttribute("allSampleSources", sampleSources);
		}
		if (session.getAttribute("allSourceSampleIds") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List sourceSampleIds = searchSampleService.getAllSourceSampleIds();
			session.setAttribute("allSourceSampleIds", sourceSampleIds);
		}
		if (session.getAttribute("allSampleSubmitters") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List submitters = searchSampleService.getAllSampleSubmitters();
			session.setAttribute("allSampleSubmitters", submitters);
		}
		if (session.getAttribute("sampleContainerInfo") == null
				|| session.getAttribute("newSampleCreated") != null) {
			ContainerInfoBean containerInfo = lookupService
					.getSampleContainerInfo();
			session.setAttribute("sampleContainerInfo", containerInfo);
		}
		if (session.getAttribute("aliquotContainerInfo") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			ContainerInfoBean containerInfo = lookupService
					.getAliquotContainerInfo();
			session.setAttribute("aliquotContainerInfo", containerInfo);
		}

		// clear new aliquot created flag and new sample created flag
		session.removeAttribute("newAliquotCreated");
		session.removeAttribute("newSampleCreated");

		// clear session attributes created during search sample
		session.removeAttribute("samples");
		session.removeAttribute("aliquots");
	}

	/**
	 * Set up session attributes for create Run
	 * 
	 * @param session
	 * @param lookupService
	 */
	private void setCreateRunSession(HttpSession session,
			LookupService lookupService) throws Exception {
		ExecuteWorkflowService executeWorkflowService = new ExecuteWorkflowService();
		if (session.getAttribute("allAssayTypes") == null) {
			List assayTypes = lookupService.getAllAssayTypes();
			session.setAttribute("allAssayTypes", assayTypes);
		}

		if (session.getAttribute("workflow") == null
				|| session.getAttribute("newWorkflowCreated") != null) {
			ExecuteWorkflowBean workflowBean = executeWorkflowService
					.getExecuteWorkflowBean();
			session.setAttribute("workflow", workflowBean);
		}
		if (session.getAttribute("allUnmaskedAliquots") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			List aliquots = lookupService.getUnmaskedAliquots();
			session.setAttribute("allUnmaskedAliquots", aliquots);
		}
		if (session.getAttribute("allAssignedAliquots") == null) {
			List allAssignedAliquots = lookupService.getAllAssignedAliquots();
			session.setAttribute("allAssignedAliquots", allAssignedAliquots);
		}
		if (session.getAttribute("allInFiles") == null) {
			List allInFiles = lookupService.getAllInFiles();
			session.setAttribute("allInFiles", allInFiles);
		}
		if (session.getAttribute("allOutFiles") == null) {
			List allOutFiles = lookupService.getAllOutFiles();
			session.setAttribute("allOutFiles", allOutFiles);
		}

		if (session.getAttribute("allAssayBeans") == null) {
			List allAssayBeans = lookupService.getAllAssayBeans();
			session.setAttribute("allAssayBeans", allAssayBeans);
		}
		session.removeAttribute("newWorkflowCreated");
		session.removeAttribute("newAliquotCreated");

	}

	private void setWorkflowMessageSession(HttpSession session)
			throws Exception {

		ExecuteWorkflowService executeWorkflowService = new ExecuteWorkflowService();
		if (session.getAttribute("workflow") == null
				|| session.getAttribute("newWorkflowCreated") != null) {
			ExecuteWorkflowBean workflowBean = executeWorkflowService
					.getExecuteWorkflowBean();
			session.setAttribute("workflow", workflowBean);
		}
		session.removeAttribute("newWorkflowCreated");
	}
}
