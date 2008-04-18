package gov.nih.nci.cananolab.ui.particle;

/**
 * This class sets up different input forms for different types of physical composition,
 * and allow users to submit data for physical compositions and update composing elements of each
 * physical composition.
 *  
 * @author pansu
 */

/* CVS $Id: NanoparticleEntityAction.java,v 1.8 2008-04-18 14:17:28 pansu Exp $ */

import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.ParticleBean;
import gov.nih.nci.cananolab.dto.particle.composition.ComposingElementBean;
import gov.nih.nci.cananolab.dto.particle.composition.NanoparticleEntityBean;
import gov.nih.nci.cananolab.exception.CaNanoLabSecurityException;
import gov.nih.nci.cananolab.service.particle.NanoparticleCompositionService;
import gov.nih.nci.cananolab.service.particle.NanoparticleSampleService;
import gov.nih.nci.cananolab.ui.core.AbstractDispatchAction;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.ui.security.InitSecuritySetup;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class NanoparticleEntityAction extends AbstractDispatchAction {

	/**
	 * Add or update the data to database
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		NanoparticleCompositionService compositionService = new NanoparticleCompositionService();
		ParticleBean particleBean = initSetup(theForm, request);
		NanoparticleEntityBean entityBean = (NanoparticleEntityBean) theForm
				.get("entity");
		
		Date now = new Date();
		entityBean.getDomainEntity().setCreatedBy(user.getLoginName());
		entityBean.getDomainEntity().setCreatedDate(now);
		for (ComposingElementBean compElementBean : entityBean
				.getComposingElements()) {
			compElementBean.getDomainComposingElement().setCreatedBy(
					user.getLoginName());
			compElementBean.getDomainComposingElement().setCreatedDate(now);
		}
		
		compositionService.saveNanoparticleEntity(particleBean, entityBean);
		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage(
				"message.addNanoparticleEntity");
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveMessages(request, msgs);
		ActionForward forward = mapping.findForward("success");
		request.setAttribute("updateDataTree", "true");
		InitNanoparticleSetup.getInstance().getDataTree(particleBean, request);
		return forward;
	}

	public ParticleBean initSetup(DynaValidatorForm theForm,
			HttpServletRequest request) throws Exception {
		String particleId = request.getParameter("particleId");
		if (particleId == null) {
			particleId = theForm.getString("particleId");
		}
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		NanoparticleSampleService service = new NanoparticleSampleService();
		ParticleBean particleBean = service.findNanoparticleSampleBy(
				particleId, user);
		request.setAttribute("theParticle", particleBean);
		theForm.set("particleId", particleId);
		return particleBean;
	}

	/**
	 * Set up the input form for adding new nanoparticle entity
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		ParticleBean particleBean = initSetup(theForm, request);
		request.setAttribute("updateDataTree", "true");
		InitNanoparticleSetup.getInstance().getDataTree(particleBean, request);
		InitNanoparticleSetup.getInstance().setNanoparticleEntityTypes(request);
		InitNanoparticleSetup.getInstance().getEmulsionComposingElementTypes(
				request);
		InitNanoparticleSetup.getInstance().getComposingElementTypes(request);
		InitNanoparticleSetup.getInstance().setFunctionTypes(request);
		return mapping.getInputForward();
	}

	public ActionForward setupUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		ParticleBean particleBean = initSetup(theForm, request);
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		String entityId = request.getParameter("dataId");
		NanoparticleCompositionService compService = new NanoparticleCompositionService();
		NanoparticleEntityBean entityBean = compService
				.findNanoparticleEntityBy(entityId, user);
		String entityType = InitSetup.getInstance().getDisplayName(
				entityBean.getClassName(), session.getServletContext());
		entityBean.setType(entityType);
		theForm.set("entity", entityBean);
		request.setAttribute("updateDataTree", "true");
		InitNanoparticleSetup.getInstance().getDataTree(particleBean, request);
		InitNanoparticleSetup.getInstance().setNanoparticleEntityTypes(request);
		InitNanoparticleSetup.getInstance().getEmulsionComposingElementTypes(
				request);
		InitNanoparticleSetup.getInstance().getComposingElementTypes(request);
		InitNanoparticleSetup.getInstance().setFunctionTypes(request);
		return mapping.getInputForward();
	}

	public boolean loginRequired() {
		return false;
	}

	public boolean canUserExecute(UserBean user)
			throws CaNanoLabSecurityException {
		return InitSecuritySetup.getInstance().userHasCreatePrivilege(user,
				CaNanoLabConstants.CSM_PG_PARTICLE);
	}
}
