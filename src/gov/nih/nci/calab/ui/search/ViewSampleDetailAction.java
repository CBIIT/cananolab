package gov.nih.nci.calab.ui.search;

/**
 * This class prepares data to show sample detail page after sample search. 
 * 
 * @author pansu
 */

/* CVS $Id: ViewSampleDetailAction.java,v 1.1 2006-03-28 23:04:21 pansu Exp $ */

import gov.nih.nci.calab.dto.administration.SampleBean;
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
import org.apache.struts.action.DynaActionForm;

public class ViewSampleDetailAction extends AbstractBaseAction {
	private static Logger logger = Logger.getLogger(ViewSampleDetailAction.class);

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ActionMessages messages = new ActionMessages();
		try {
			DynaActionForm theForm = (DynaActionForm) form;
			int sampleNum = Integer.parseInt((String) theForm.get("sampleNum"));
			int containerNum = Integer.parseInt((String) theForm.get("containerNum"));
			if (session.getAttribute("samples") != null) {
				List samples = (List) session
						.getAttribute("samples");
				SampleBean sample = ((SampleBean) samples.get(sampleNum));
				request.setAttribute("sample", sample);
				request.setAttribute("containerNum", containerNum);
				forward = mapping.findForward("success");
			} else {
				logger
						.error("Session containing the searched sample results either is expired or doesn't exist");
				ActionMessage error = new ActionMessage(
						"errors.viewSampleDetails.nosamples");
				messages.add("error", error);
				saveMessages(request, messages);
				forward = mapping.getInputForward();
			}

		} catch (Exception e) {
			ActionMessages errors=new ActionMessages();
			ActionMessage error=new ActionMessage("error.viewSampleDetails");
			errors.add("error", error);
			saveMessages(request, errors);
			logger.error("Caught exception when showing sample detail page", e);
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
