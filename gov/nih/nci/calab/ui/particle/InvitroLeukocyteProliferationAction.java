package gov.nih.nci.calab.ui.particle;

/**
 * This class sets up input form for InVitro LeukocyteProliferation characterization. 
 *  
 * @author beasleyj
 */

import gov.nih.nci.calab.dto.characterization.CharacterizationBean;
import gov.nih.nci.calab.service.particle.SubmitNanoparticleService;
import gov.nih.nci.calab.ui.core.BaseCharacterizationAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class InvitroLeukocyteProliferationAction extends
		BaseCharacterizationAction {

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
		ActionForward forward = null;

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean charBean = super.prepareCreate(request, theForm);
		SubmitNanoparticleService service = new SubmitNanoparticleService();
		service.addLeukocyteProliferation(charBean);
		CharacterizationBean[] otherChars = super.prepareCopy(request, theForm,
				service);
		for (CharacterizationBean acharBean : otherChars) {
			service.addLeukocyteProliferation(acharBean);
		}
		super.postCreate(request, theForm);

		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage(
				"message.addInvitroLeukocyteProliferation");
		msgs.add("message", msg);
		saveMessages(request, msgs);
		forward = mapping.findForward("success");
		return forward;
	}
}
