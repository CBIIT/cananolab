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
 * This class calls the Struts ForwardAction to forward to a page, aslo extends
 * AbstractBaseAction to inherit the user authentication features.
 * 
 * @author pansu
 */

/* CVS $Id: BaseForwardAction.java,v 1.7 2007-11-08 20:41:35 pansu Exp $ */

import gov.nih.nci.calab.dto.common.UserBean;
import gov.nih.nci.calab.exception.InvalidSessionException;
import gov.nih.nci.calab.service.security.UserService;
import gov.nih.nci.calab.service.util.CaNanoLabConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

public class BaseForwardAction extends AbstractBaseAction {
	public ActionForward executeTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.isNew()) {
			throw new InvalidSessionException(
					"Session timed out.  Please start again.");
		}
		ForwardAction forwardAction = new ForwardAction();
		UserService userService = new UserService(
				CaNanoLabConstants.CSM_APP_NAME);
		UserBean user = (UserBean) request.getSession().getAttribute("user");

		boolean createProtocol = userService.checkCreatePermission(user,
				CaNanoLabConstants.CSM_PG_PROTOCOL);
		session.setAttribute("canCreateProtocol", createProtocol);
		boolean createReport = userService.checkCreatePermission(user,
				CaNanoLabConstants.CSM_PG_REPORT);
		session.setAttribute("canCreateReport", createReport);
		boolean createParticle = userService.checkCreatePermission(user,
				CaNanoLabConstants.CSM_PG_PARTICLE);
		session.setAttribute("canCreateNanoparticle", createParticle);
		boolean createSample = userService.checkCreatePermission(user,
				CaNanoLabConstants.CSM_PG_SAMPLE);
		session.setAttribute("canCreateSample", createSample);

		return forwardAction.execute(mapping, form, request, response);
	}

	public boolean loginRequired() {
		return false;
	}

	public boolean canUserExecute(UserBean user) {
		return true;
	}
}
