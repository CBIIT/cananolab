/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.ui.administration;

/**
 * This class saves user entered new aliquot information 
 * into the database.
 * 
 * @author pansu
 */

/* CVS $Id: CreateAliquotAction.java,v 1.16 2006-05-03 19:35:20 pansu Exp $ */

import gov.nih.nci.calab.dto.administration.AliquotBean;
import gov.nih.nci.calab.exception.CalabException;
import gov.nih.nci.calab.service.administration.ManageAliquotService;
import gov.nih.nci.calab.ui.core.AbstractBaseAction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class CreateAliquotAction extends AbstractBaseAction {
	
	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		
		// TODO fill in details for aliquot information */
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String sampleName = (String) theForm.get("sampleName");
		String parentAliquotName = (String) theForm.get("parentAliquotName");
		String parentName = (parentAliquotName.length() == 0) ? "Sample "
				+ sampleName : "Aliquot " + parentAliquotName;
		request.setAttribute("parentName", parentName);
		if (session.getAttribute("aliquotMatrix") != null) {
			List<AliquotBean[]> aliquotMatrix = new ArrayList<AliquotBean[]>(
					(List<? extends AliquotBean[]>) session
							.getAttribute("aliquotMatrix"));
			ManageAliquotService manageAliquotService = new ManageAliquotService();
			manageAliquotService.saveAliquots(sampleName, parentAliquotName,
					aliquotMatrix);
			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage("message.createAliquot");
			msgs.add("message", msg);
			saveMessages(request, msgs);

			// set a flag to indicate that new aliquots have been created so
			// session can
			// be refreshed in initSession.do
			session.setAttribute("newAliquotCreated", "yes");

			forward = mapping.findForward("success");
		} else {
			throw new CalabException("Can't find the aliquot matrix to save.  Please click on 'Update Aliquots' button before submitting");
		}
		return forward;
	}

	public boolean loginRequired() {
		return true;
	}
}
