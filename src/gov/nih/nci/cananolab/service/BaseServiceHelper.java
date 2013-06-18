/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.cananolab.service;

import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.service.security.AuthorizationService;
import gov.nih.nci.cananolab.util.Constants;

import java.util.List;

import org.apache.log4j.Logger;

public class BaseServiceHelper {
	private AuthorizationService authService;
	protected Logger logger = Logger.getLogger(BaseServiceHelper.class);
	protected List<String> accessibleData;
	private UserBean user;

	public BaseServiceHelper() {
		try {
			authService = new AuthorizationService(Constants.CSM_APP_NAME);
		} catch (Exception e) {
			logger.error("Can't create authorization service: " + e);
		}
	}

	public BaseServiceHelper(UserBean user) {
		this.user = user;
		try {
			authService = new AuthorizationService(Constants.CSM_APP_NAME);
		} catch (Exception e) {
			logger.error("Can't create authorization service: " + e);
		}
	}

	public BaseServiceHelper(AuthorizationService authService) {
		this.authService = authService;
	}

	public BaseServiceHelper(AuthorizationService authService, UserBean user) {
		this.authService = authService;
		this.user = user;
		try {
			authService = new AuthorizationService(Constants.CSM_APP_NAME);
		} catch (Exception e) {
			logger.error("Can't create authorization service: " + e);
		}
	}

	public AuthorizationService getAuthService() {
		return authService;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	protected List<String> getAccessibleData() throws Exception {
		if (accessibleData == null) {
			// when user is null, accessible data are public data
			accessibleData = authService.getAllUserAccessibleData(user);
		}
		return accessibleData;
	}
}
