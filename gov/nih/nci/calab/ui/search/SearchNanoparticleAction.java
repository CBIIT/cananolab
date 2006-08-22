package gov.nih.nci.calab.ui.search;

/**
 * This class searches nanoparticle metadata based on user supplied criteria
 * 
 * @author pansu
 */

/* CVS $Id: SearchNanoparticleAction.java,v 1.5 2006-08-22 17:39:36 pansu Exp $ */

import gov.nih.nci.calab.dto.common.UserBean;
import gov.nih.nci.calab.dto.particle.ParticleBean;
import gov.nih.nci.calab.service.search.SearchNanoparticleService;
import gov.nih.nci.calab.service.security.UserService;
import gov.nih.nci.calab.service.util.CalabConstants;
import gov.nih.nci.calab.ui.core.AbstractDispatchAction;
import gov.nih.nci.calab.ui.core.InitSessionSetup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class SearchNanoparticleAction extends AbstractDispatchAction {
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String particleSource = (String) theForm.get("particleSource");
		String particleType = (String) theForm.get("particleType");
		String[] functionTypes = (String[]) theForm.get("functionTypes");
		String[] characterizations = (String[]) theForm.get("characterizations");
		String keywords = (String) theForm.get("keywords");
		String[] keywordList = keywords.split("\r\n");

		SearchNanoparticleService searchParticleService = new SearchNanoparticleService();
		List<ParticleBean> particles = searchParticleService.basicSearch(
				particleSource, particleType, functionTypes, characterizations,
				keywordList, user);

		request.setAttribute("particles", particles);
		/*
		 * ActionMessages msgs = new ActionMessages(); ActionMessage msg = new
		 * ActionMessage( "message.searchNanoparticle.secure",
		 * filteredParticles.size(), samples.size(), particleType);
		 * msgs.add("message", msg); saveMessages(request, msgs);
		 */
		forward = mapping.findForward("success");
		return forward;
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		InitSessionSetup.getInstance().setAllParticleSources(session);
		InitSessionSetup.getInstance().setAllParticleTypeParticles(session);
		InitSessionSetup.getInstance().setAllParticleFunctionTypes(session);
		InitSessionSetup.getInstance()
				.setCharacterizationTypeCharacterizations(session);
		InitSessionSetup.getInstance().clearWorkflowSession(session);
		InitSessionSetup.getInstance().clearInventorySession(session);

		return mapping.getInputForward();
	}

	public boolean loginRequired() {
		return true;
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
				"nanoparticle search");
		boolean searchStatus = InitSessionSetup.getInstance()
				.canUserExecuteClass(session, this.getClass());
		if (nanoSearchStatus || searchStatus) {
			return true;
		} else {
			return false;
		}
	}
}
