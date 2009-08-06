package gov.nih.nci.cananolab.ui.core;

import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.exception.InvalidSessionException;
import gov.nih.nci.cananolab.exception.NoAccessException;
import gov.nih.nci.cananolab.exception.SecurityException;
import gov.nih.nci.cananolab.util.Constants;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public abstract class AbstractDispatchAction extends DispatchAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		String dispatch = request.getParameter("dispatch");
		// private dispatch in public actions
		boolean privateDispatch = false;
		if (dispatch != null) {
			for (String theDispatch : Constants.PRIVATE_DISPATCHES) {
				if (dispatch.startsWith(theDispatch)) {
					privateDispatch = true;
					break;
				}
			}
		}
		if (session.isNew() && (dispatch == null || privateDispatch)) {
			throw new InvalidSessionException();
		}
		if (!loginRequired()) {
			return super.execute(mapping, form, request, response);
		} else {
			UserBean user = (UserBean) session.getAttribute("user");
			if (user != null) {
				// check whether user have access to the class
				boolean accessStatus = canUserExecute(user);
				if (accessStatus) {
					return super.execute(mapping, form, request, response);
				} else {
					request.getSession().removeAttribute("user");
					throw new NoAccessException();
				}
			} else {
				throw new InvalidSessionException();
			}
		}
	}

	public abstract boolean loginRequired();

	/**
	 * Check whether the current user can execute the action
	 * 
	 * @param user
	 * @return
	 * @throws SecurityException
	 */
	public abstract boolean canUserExecute(UserBean user)
			throws SecurityException;

	/**
	 * Get the page number used in display tag library pagination
	 * @param request
	 * @return
	 */
	public int getDisplayPage(HttpServletRequest request) {
		int page = 0;
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String name = (String) paramNames.nextElement();
			if (name != null && name.startsWith("d-") && name.endsWith("-p")) {
				String pageValue = request.getParameter(name);
				if (pageValue != null) {
					page = Integer.parseInt(pageValue) - 1;
				}
			}
		}
		return page;
	}
	
	public String getBrowserDispatch(HttpServletRequest request) {
		String dispatch = request.getParameter("dispatch");
		// get the dispatch value from the URL in the browser address bar
		// used in case of validation
		if (dispatch != null
				&& request
						.getAttribute("javax.servlet.forward.query_string") != null) {
			String browserQueryString = request.getAttribute(
					"javax.servlet.forward.query_string").toString();
			if (browserQueryString != null) {
				String browserDispatch = browserQueryString.replaceAll(
						"dispatch=(.+)&(.+)", "$1");
				return browserDispatch;
			}			
		}
		return null;
	}
}