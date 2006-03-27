package gov.nih.nci.calab.ui.workflow;

import gov.nih.nci.calab.ui.core.AbstractBaseAction;
import gov.nih.nci.calab.service.workflow.MaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;


/** 
* The MastAction sets is a generalized MaskAction class that is designed to Mask any caLAB object.
* The strMaskType checks the hidden field on a Mask form and uses this to determine the appropriate action. 
* @author      doswellj 
*/
public class MaskAction extends AbstractBaseAction
{

	private static Logger logger = Logger.getLogger(MaskAction.class);

	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionForward forward = null;
		String strMaskType=null;
		String strId = null;
		ActionMessage msg = null;
		try 
		{
			DynaValidatorForm theForm = (DynaValidatorForm) form;
			strMaskType = (String) theForm.get("maskType");
			if (strMaskType.equals("aliquot"))
			{
			    strId = (String) theForm.get("aliquotId");
			}
			if (strMaskType.equals("file"))
			{
				strId = (String) theForm.get("fileId");
			}
			String strDescription= (String) theForm.get("description");
			
           //Call MaskService to Mask Aliquot, Mask File, etc.
			MaskService maskservice = new MaskService();
			maskservice.setMask(strMaskType, strId, strDescription);
			ActionMessages msgs = new ActionMessages();
			if (strMaskType.equals("aliquot"))
			{
			       msg = new ActionMessage("message.maskAliquot", strId);
			}
			if (strMaskType.equals("file"))
			{
				  msg = new ActionMessage("message.maskFile", strId);
			}
			msgs.add("message", msg);
			saveMessages(request, msgs);
			forward = mapping.findForward("success");
			
			
		} catch (Exception e) {
			logger.error("Error Authenticating the user", e);
			ActionMessage error = null;
			ActionMessages errors = new ActionMessages();
			
			if (strMaskType.equals("aliquot"))
			{
				error = new ActionMessage("error.useAliquot", strId);
			}
			if (strMaskType.equals("file"))
			{
				error = new ActionMessage("error.useAliquot", strId);
			}
			errors.add("error", error);
			saveMessages(request, errors);
			forward = mapping.getInputForward();
			forward = mapping.findForward("failure");			
		}
		return forward;
	}

	public boolean loginRequired() 
	{
		// temporarily set to false until login module is working
		return false;
		// return true;
	}
	
}
