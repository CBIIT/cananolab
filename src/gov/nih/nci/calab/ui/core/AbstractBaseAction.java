/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

/*
 The caLAB Software License, Version 0.5

 Copyright 2006 SAIC. This software was developed in conjunction with the National 
 Cancer Institute, and so to the extent government employees are co-authors, any 
 rights in such works shall be subject to Title 17 of the United States Code, 
 section 105.

 */
package gov.nih.nci.calab.ui.core;

/**
 * This abstract class is the basis for all other action classes to extend in
 * caLAB. It includes common operations need to be included in all caLAB actions
 * to reduce redundancy.
 * 
 * @author pansu
 */

/* CVS $Id: AbstractBaseAction.java,v 1.14 2007-11-08 20:40:52 pansu Exp $ */

import gov.nih.nci.calab.dto.common.UserBean;
import gov.nih.nci.calab.exception.InvalidSessionException;
import gov.nih.nci.calab.exception.NoAccessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class AbstractBaseAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		if (!loginRequired()) {
			return executeTask(mapping, form, request, response);
		}

		if (user != null) {
			boolean accessStatus = canUserExecute(user);
			if (!accessStatus) {
				throw new NoAccessException("You don't have access to class: "
						+ this.getClass().getName());
			}
		} else {
			throw new InvalidSessionException();
		}

		return executeTask(mapping, form, request, response);
	}

	/**
	 * Provide implementation of this abstract method in each subclass.
	 */
	public abstract ActionForward executeTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * Provide implementation of the abstract method in each subclass.
	 */
	public abstract boolean loginRequired();

	/**
	 * Check whether the current user in the session can perform the action
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public abstract boolean canUserExecute(UserBean user) throws Exception;
}