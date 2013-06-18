/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.cananolab.ui.core;

import gov.nih.nci.cananolab.exception.NoAccessException;
import gov.nih.nci.cananolab.exception.SecurityException;
import gov.nih.nci.cananolab.service.security.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

public abstract class AbstractForwardAction extends ForwardAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveToken(request);
		if (loginRequired()) {
			HttpSession session = request.getSession();
			UserBean user = (UserBean) session.getAttribute("user");
			if (user == null) {
				throw new NoAccessException("Log in is required");
			}
		}
		return super.execute(mapping, form, request, response);
	}

	/**
	 * Check whether the current user can execute the action with the specified
	 * dispatch
	 *
	 * @param user
	 * @return
	 * @throws SecurityException
	 */
	public abstract boolean loginRequired();
}