/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.ui.workflow;

import gov.nih.nci.calab.service.workflow.MaskService;
import gov.nih.nci.calab.ui.core.AbstractBaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * The MastAction sets is a generalized MaskAction class that is designed to
 * Mask any caLAB object. The strMaskType checks the hidden field on a Mask form
 * and uses this to determine the appropriate action.
 * 
 * @author doswellj
 */
public class MaskAction extends AbstractBaseAction {

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;

		String strId = null;

		HttpSession session = request.getSession();
		DynaValidatorForm theForm = (DynaValidatorForm) form;

		String strMaskType = (String) theForm.get("maskType");
		String strDescription = theForm.getString("description");

		// Check mask type
		if (strMaskType.equals("aliquot")) {
			strId = (String) theForm.get("aliquotId");
		}
		if (strMaskType.equals("file")) {
			strId = (String) theForm.get("fileId");
		}

		// 1.Call MaskService to mask caLab component based on type(e.g.,
		// Aliquot, File, etc.)
		// 2.Display message that masking was successful
		MaskService maskservice = new MaskService();

		strDescription = strDescription + "   Masked by "
				+ session.getAttribute("creator");
		maskservice.setMask(strMaskType, strId, strDescription);

		if (strMaskType.equals("aliquot")) {
			ActionMessages msgs=new ActionMessages();
			ActionMessage msg = new ActionMessage("message.maskAliquot", (String) theForm
					.get("aliquotName"));
			msgs.add("message", msg);
			saveMessages(request, msgs);
			session.setAttribute("newAliquotCreated", "true");
			forward = mapping.findForward("success");
		}
		if (strMaskType.equals("file")) {
			theForm.set("method", "setup");
			theForm.set("inout", (String) theForm.get("inout"));
			request.setAttribute("inout", (String) theForm.get("inout"));
			forward = mapping.findForward("success");
		}
		session.setAttribute("newWorkflowCreated", "true");
		forward = mapping.findForward("success");
		return forward;
	}

	public boolean loginRequired() {
		return true;
	}

}
