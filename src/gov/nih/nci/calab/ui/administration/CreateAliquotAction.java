package gov.nih.nci.calab.ui.administration;

/**
 * This class saves user entered new aliquot information 
 * into the database.
 * 
 * @author pansu
 */

/* CVS $Id: CreateAliquotAction.java,v 1.5 2006-03-28 23:01:33 pansu Exp $ */

import gov.nih.nci.calab.service.administration.ManageAliquotService;
import gov.nih.nci.calab.ui.core.AbstractBaseAction;

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
import org.apache.struts.validator.DynaValidatorForm;

public class CreateAliquotAction extends AbstractBaseAction {
	private static Logger logger = Logger.getLogger(CreateAliquotAction.class);

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ActionMessages messages=new ActionMessages();
		try {
			// TODO fill in details for aliquot information */
			DynaValidatorForm theForm = (DynaValidatorForm) form;
			String sampleId = (String) theForm.get("sampleId");
			String parentAliquotId = (String) theForm.get("parentAliquotId");
			String comments=(String)theForm.get("generalComments");
			if (session.getAttribute("aliquotMatrix") != null) {
				List aliquotMatrix = (List) session
						.getAttribute("aliquotMatrix");
				ManageAliquotService manageAliquotService=new ManageAliquotService();
				manageAliquotService.saveAliquots(sampleId, parentAliquotId, aliquotMatrix, comments);
				ActionMessages msgs = new ActionMessages();
				ActionMessage msg = new ActionMessage("message.createAliquot");
				msgs.add("message", msg);
				saveMessages(request, msgs);
				forward = mapping.findForward("success");
			} else {
				logger
						.error("Session containing the aliquot matrix either is expired or doesn't exist");
				ActionMessage error = new ActionMessage(
						"errors.createAliquot.nomatrix");
				messages.add("error", error);
				saveMessages(request, messages);
				forward = mapping.getInputForward();
			}

		} catch (Exception e) {
			ActionMessage error=new ActionMessage("error.createAliquot");
			messages.add("error", error);
			saveMessages(request, messages);
			logger.error("Caught exception when creating aliquots", e);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public boolean loginRequired() {
		// temporarily set to false until login module is working
		return false;
		// return true;
	}
}
