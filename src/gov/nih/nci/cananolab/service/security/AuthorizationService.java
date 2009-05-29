package gov.nih.nci.cananolab.service.security;

import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.exception.SecurityException;
import gov.nih.nci.cananolab.system.applicationservice.CustomizedApplicationService;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.StringUtils;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Application;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroupRoleContext;
import gov.nih.nci.security.authorization.domainobjects.Role;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.dao.ProtectionElementSearchCriteria;
import gov.nih.nci.security.dao.ProtectionGroupSearchCriteria;
import gov.nih.nci.security.dao.RoleSearchCriteria;
import gov.nih.nci.security.dao.SearchCriteria;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

/**
 * This class takes care of authentication and authorization of a user and group
 *
 * @author Pansu
 *
 */
public class AuthorizationService {
	private Logger logger = Logger.getLogger(AuthorizationService.class);

	private AuthenticationManager authenticationManager = null;

	private AuthorizationManager authorizationManager = null;

	private UserProvisioningManager userManager = null;

	private String applicationName = null;

	private CustomizedApplicationService appService = null;

	public AuthorizationService(String applicationName)
			throws SecurityException {
		try {
			this.applicationName = applicationName;
			this.authenticationManager = SecurityServiceProvider
					.getAuthenticationManager(applicationName);
			this.authorizationManager = SecurityServiceProvider
					.getAuthorizationManager(applicationName);
			this.userManager = SecurityServiceProvider
					.getUserProvisioningManager(applicationName);
			this.appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
		} catch (Exception e) {
			logger.error(e);
			throw new SecurityException();
		}
	}

	public UserBean getUserBean(String userLogin) {
		User user = this.authorizationManager.getUser(userLogin);
		return new UserBean(user); // or
		// userManger.getUser(userLoginId);
	}

	/**
	 * Check whether the given user is the admin of the application.
	 *
	 * @param user
	 * @return
	 */
	public boolean isAdmin(String user) {
		boolean adminStatus = this.authorizationManager.checkOwnership(user,
				this.applicationName);
		return adminStatus;
	}

	/**
	 * Check whether the given user belongs to the given group.
	 *
	 * @param user
	 * @param groupName
	 * @return
	 * @throws SecurityException
	 */
	public boolean isUserInGroup(UserBean user, String groupName)
			throws SecurityException {
		try {
			Set groups = this.userManager.getGroups(user.getUserId());
			for (Object obj : groups) {
				Group group = (Group) obj;
				if (group.getGroupName().equalsIgnoreCase(groupName)
						|| group.getGroupName().startsWith(groupName)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			logger.error("Error in checking if user is in the group.", e);
			throw new SecurityException();
		}
	}

	/**
	 * Check whether the given user has the given privilege on the given
	 * protection element
	 *
	 * @param user
	 * @param protectionElementObjectId
	 * @param privilege
	 * @return
	 * @throws SecurityException
	 */
	public boolean checkPermission(UserBean user,
			String protectionElementObjectId, String privilege)
			throws SecurityException {
		try {
			boolean status = false;
			if (user == null) {
				return status;
			}
			status = this.authorizationManager.checkPermission(user
					.getLoginName(), protectionElementObjectId, privilege);
			return status;
		} catch (Exception e) {
			logger.error("Error in checking user permission.", e);
			throw new SecurityException();
		}
	}

	public boolean checkCreatePermission(UserBean user,
			String protectionElementObjectId) throws SecurityException {
		return checkPermission(user, protectionElementObjectId,
				Constants.CSM_CREATE_PRIVILEGE);
	}

	/**
	 * Check whether the given user has execute privilege on the given
	 * protection element
	 *
	 * @param user
	 * @param protectionElementObjectId
	 * @return
	 * @throws SecurityException
	 */
	public boolean checkExecutePermission(UserBean user,
			String protectionElementObjectId) throws SecurityException {
		return checkPermission(user, protectionElementObjectId,
				Constants.CSM_EXECUTE_PRIVILEGE);
	}

	/**
	 * Check whether the given user has read privilege on the given protection
	 * element
	 *
	 * @param user
	 * @param protectionElementObjectId
	 * @return
	 * @throws SecurityException
	 */
	public boolean checkReadPermission(UserBean user,
			String protectionElementObjectId) throws Exception {
		return checkPermission(user, protectionElementObjectId,
				Constants.CSM_READ_PRIVILEGE);
	}

	public List<String> getPublicDataSlow() throws SecurityException {
		List<String> publicData = new ArrayList<String>();
		try {
			Group publicGroup = getGroup(Constants.CSM_PUBLIC_GROUP);
			try {
				Set ctxs = userManager
						.getProtectionGroupRoleContextForGroup(publicGroup
								.getGroupId().toString());
				for (Object obj : ctxs) {
					ProtectionGroupRoleContext ctx = (ProtectionGroupRoleContext) obj;
					for (Object r : ctx.getRoles()) {
						if (((Role) r).getName()
								.equals(Constants.CSM_READ_ROLE)) {
							publicData.add(ctx.getProtectionGroup()
									.getProtectionGroupName());
							break;
						}
					}
				}
				return publicData;

			} catch (Exception e) {
				return publicData;
			}

		} catch (Exception e) {
			throw new SecurityException();
		}
	}

	/**
	 * Check whether the given user has delete privilege on the given protection
	 * element
	 *
	 * @param user
	 * @param protectionElementObjectId
	 * @return
	 * @throws SecurityException
	 */
	public boolean checkDeletePermission(UserBean user,
			String protectionElementObjectId) throws SecurityException {
		return checkPermission(user, protectionElementObjectId,
				Constants.CSM_DELETE_PRIVILEGE);
	}

	/**
	 * Get all user groups in the application
	 *
	 * @return
	 * @throws SecurityException
	 */
	public List<String> getAllGroups() throws SecurityException {
		try {
			List<String> groups = new ArrayList<String>();
			Group dummy = new Group();
			dummy.setGroupName("*");
			SearchCriteria sc = new GroupSearchCriteria(dummy);
			List results = this.userManager.getObjects(sc);
			for (Object obj : results) {
				Group doGroup = (Group) obj;
				groups.add(doGroup.getGroupName());
			}
			return groups;
		} catch (Exception e) {
			logger.error("Error in getting all groups.", e);
			throw new SecurityException();
		}
	}

	public List<Group> getGroups() throws SecurityException {
		try {
			List<Group> groups = new ArrayList<Group>();
			Group dummy = new Group();
			dummy.setGroupName("*");
			SearchCriteria sc = new GroupSearchCriteria(dummy);
			List results = this.userManager.getObjects(sc);
			for (Object obj : results) {
				Group doGroup = (Group) obj;
				groups.add(doGroup);
			}
			return groups;
		} catch (Exception e) {
			logger.error("Error in getting all groups.", e);
			throw new SecurityException();
		}
	}

	/**
	 * Get all user visiblity groups in the application (filtering out all
	 * groups starting with APP_OWNER).
	 *
	 * @return
	 * @throws SecurityException
	 */
	public List<String> getAllVisibilityGroups() throws SecurityException {
		List<String> groups = getAllGroups();
		List<String> filteredGroups = new ArrayList<String>();
		// filter out the ones starting with APP_OWNER
		List<String> notShownGroups = Arrays.asList(Constants.VISIBLE_GROUPS);
		for (String groupName : groups) {
			if (!notShownGroups.contains(groupName)
					&& !groupName.equals(Constants.CSM_ADMIN)) {
				if (!filteredGroups.contains(groupName)) {
					filteredGroups.add(groupName);
				}
			}
		}
		Collections.sort(filteredGroups);
		// put Public group in the beginning
		if (groups.contains(Constants.CSM_PUBLIC_GROUP)) {
			groups.remove(Constants.CSM_PUBLIC_GROUP);
		}
		filteredGroups.add(0, Constants.CSM_PUBLIC_GROUP);
		return filteredGroups;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public AuthenticationManager getAuthenticationManager() {
		return this.authenticationManager;
	}

	public AuthorizationManager getAuthorizationManager() {
		return this.authorizationManager;
	}

	public UserProvisioningManager getUserManager() {
		return this.userManager;
	}

	/**
	 * Get a Group object for the given groupName.
	 *
	 * @param groupName
	 * @return
	 */
	public Group getGroup(String groupName) {
		Group group = new Group();
		group.setGroupName(groupName);
		SearchCriteria sc = new GroupSearchCriteria(group);
		List results = this.userManager.getObjects(sc);
		Group doGroup = null;
		for (Object obj : results) {
			doGroup = (Group) obj;
			break;
		}
		return doGroup;
	}

	/**
	 * Create a user group in the CSM database if it's not already created
	 *
	 * @param groupName
	 * @throws SecurityException
	 */
	public void createAGroup(String groupName) throws SecurityException {
		try {
			Group doGroup = getGroup(groupName);
			if (doGroup == null) {
				doGroup = new Group();
				doGroup.setGroupName(groupName);
				this.userManager.createGroup(doGroup);
			}
		} catch (Exception e) {
			logger.error("Error in creating a group.", e);
			throw new SecurityException();
		}
	}

	/**
	 * Get a Role object for the given roleName.
	 *
	 * @param roleName
	 * @return
	 */
	public Role getRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		SearchCriteria sc = new RoleSearchCriteria(role);
		List results = this.userManager.getObjects(sc);
		Role doRole = null;
		for (Object obj : results) {
			doRole = (Role) obj;
			break;
		}
		return doRole;
	}

	/**
	 * Get a ProtectionElement object for the given objectId.
	 *
	 * @param objectId
	 * @return
	 * @throws SecurityException
	 */
	public ProtectionElement getProtectionElement(String objectId)
			throws SecurityException {
		try {
			ProtectionElement pe = new ProtectionElement();
			pe.setObjectId(objectId);
			pe.setProtectionElementName(objectId);
			SearchCriteria sc = new ProtectionElementSearchCriteria(pe);
			List results = this.userManager.getObjects(sc);
			ProtectionElement doPE = null;
			for (Object obj : results) {
				doPE = (ProtectionElement) obj;
				break;
			}
			if (doPE == null) {
				this.authorizationManager.createProtectionElement(pe);
				return pe;
			}
			return doPE;
		} catch (Exception e) {
			logger.error("Error in creating protection element", e);
			throw new SecurityException();
		}

	}

	/**
	 * Get a ProtectionGroup object for the given protectionGroupName.
	 *
	 * @param protectionGroupName
	 * @return
	 * @throws SecurityException
	 */
	public ProtectionGroup getProtectionGroup(String protectionGroupName)
			throws SecurityException {

		ProtectionGroup pg = new ProtectionGroup();
		pg.setProtectionGroupName(protectionGroupName);
		try {
			SearchCriteria sc = new ProtectionGroupSearchCriteria(pg);
			List results = this.userManager.getObjects(sc);
			ProtectionGroup doPG = null;
			for (Object obj : results) {
				doPG = (ProtectionGroup) obj;
				break;
			}
			if (doPG == null) {
				this.userManager.createProtectionGroup(pg);
				return pg;
			}
			return doPG;
		} catch (Exception e) {
			logger.error("Error in getting protection group.", e);
			throw new SecurityException();
		}
	}

	/**
	 * Assign a ProtectionElement to a ProtectionGroup if not already assigned.
	 *
	 * @param pe
	 * @param pg
	 * @throws SecurityException
	 */
	public void assignProtectionElementToProtectionGroup(ProtectionElement pe,
			ProtectionGroup pg) throws SecurityException {
		try {
			Set<ProtectionGroup> assignedPGs = new HashSet<ProtectionGroup>(
					this.authorizationManager.getProtectionGroups(pe
							.getProtectionElementId().toString()));
			// check to see if the assignment is already made to ignore CSM
			// exception.

			// contains doesn't work because CSM didn't implement hashCode in
			// ProtectionGroup.
			// if (assignedPGs.contains(pg)) {
			// return;
			// }
			for (ProtectionGroup aPg : assignedPGs) {
				if (aPg.equals(pg)) {
					return;
				}
			}
			this.authorizationManager.assignProtectionElement(pg
					.getProtectionGroupName(), pe.getObjectId());
		} catch (Exception e) {
			logger
					.error(
							"Error in assigning protection element to protection group",
							e);
			throw new SecurityException();
		}
	}

	/**
	 * Direct CSM schema query to improve performance. Get the existing role IDs
	 * from database
	 *
	 * @param objectName
	 * @param groupName
	 * @return
	 * @throws SecurityException
	 */
	public List<String> getExistingRoleIds(ProtectionGroup pg, Group group)
			throws SecurityException {
		List<String> roleIds = new ArrayList<String>();

		String query = "select distinct role_id from csm_user_group_role_pg "
				+ "where protection_group_id=" + pg.getProtectionGroupId()
				+ " and group_id=" + group.getGroupId();
		try {
			CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
					.getApplicationService();
			String[] columns = new String[] { "role_id" };
			Object[] columnTypes = new Object[] { Hibernate.STRING };
			List results = appService.directSQL(query, columns, columnTypes);
			for (Object obj : results) {
				String roleId = (String) obj;
				roleIds.add(roleId);
			}
		} catch (Exception e) {
			logger
					.error("Error in getting existing roles from CSM database",
							e);
			throw new SecurityException();
		}

		return roleIds;
	}

	/**
	 * Get the existing role IDs from database
	 *
	 * @param objectName
	 * @param groupName
	 * @return
	 * @throws SecurityException
	 */
	public List<String> getExistingRoleIdsSlow(ProtectionGroup pg, Group group)
			throws SecurityException {
		List<String> roleIds = new ArrayList<String>();
		Set existingRoles = null;
		try {
			Set contexts = this.userManager
					.getProtectionGroupRoleContextForGroup(group.getGroupId()
							.toString());
			for (Object obj : contexts) {
				ProtectionGroupRoleContext context = (ProtectionGroupRoleContext) obj;
				if (context.getProtectionGroup().equals(pg)) {
					existingRoles = context.getRoles();
					break;
				}
			}
			if (existingRoles != null) {
				for (Object obj : existingRoles) {
					Role aRole = (Role) obj;
					roleIds.add(aRole.getId().toString());
				}
			}
			return roleIds;
		} catch (Exception e) {
			logger.error("Error in getting role IDs", e);
			throw new SecurityException();
		}
	}

	/**
	 * Assign the given objectName to the given groupName with the given
	 * roleName. Add to existing roles the object has for the group.
	 *
	 * @param objectName
	 * @param groupName
	 * @param roleName
	 * @throws SecurityException
	 */
	public void secureObject(String objectName, String groupName,
			String roleName) throws SecurityException {
		try {
			// create protection element
			ProtectionElement pe = getProtectionElement(objectName);

			// create protection group
			ProtectionGroup pg = getProtectionGroup(objectName);

			// assign protection element to protection group if not already
			// exists
			assignProtectionElementToProtectionGroup(pe, pg);

			// get group and role
			Group group = getGroup(groupName);
			Role role = getRole(roleName);

			// this will remove exising roles assigned. In caNanoLab, this is
			// not an
			// issue since
			// only the R role has been assigned from the application.

			if (group == null) {
				group = new Group();
				group.setGroupName(groupName);
				userManager.createGroup(group);
			}
			if (role == null) {
				role = new Role();
				role.setName(roleName);
				userManager.createRole(role);
			}
			this.userManager.assignGroupRoleToProtectionGroup(pg
					.getProtectionGroupId().toString(), group.getGroupId()
					.toString(), new String[] { role.getId().toString() });
		} catch (Exception e) {
			logger.error("Error in securing objects", e);
			throw new SecurityException();
		}
	}

	public List<String> getAccessibleGroups(String objectName,
			String privilegeName) throws SecurityException {
		List<String> groupNames = new ArrayList<String>();
		try {
			List groups = authorizationManager.getAccessibleGroups(objectName,
					Constants.CSM_READ_PRIVILEGE);
			for (Object obj : groups) {
				Group group = (Group) obj;
				groupNames.add(group.getGroupName());
			}
		} catch (Exception e) {
			logger.error("Error in getting accessible groups", e);
			throw new SecurityException();
		}
		return groupNames;
	}

	public void removeExistingVisibleGroups(String objectName, String roleName)
			throws SecurityException {
		try {
			List<Group> groups = getGroups();
			ProtectionGroup pg = getProtectionGroup(objectName);
			Role role = getRole(roleName);
			for (Group group : groups) {
				userManager.removeGroupRoleFromProtectionGroup(pg
						.getProtectionGroupId().toString(), group.getGroupId()
						.toString(), new String[] { role.getId().toString() });
			}
		} catch (Exception e) {
			throw new SecurityException();
		}
	}

	public void removePublicVisibility(String objectName)
			throws SecurityException {
		try {
			Group group = getGroup(Constants.CSM_PUBLIC_GROUP);
			Role role = getRole(Constants.CSM_READ_ROLE);
			ProtectionGroup pg = getProtectionGroup(objectName);
			userManager.removeGroupRoleFromProtectionGroup(pg
					.getProtectionGroupId().toString(), group.getGroupId()
					.toString(), new String[] { role.getId().toString() });
		} catch (Exception e) {
			throw new SecurityException();
		}
	}

	public void assignVisibility(String dataToProtect, String[] visibleGroups,
			String owningGroup) throws SecurityException {
		try {
			removeExistingVisibleGroups(dataToProtect, Constants.CSM_READ_ROLE);

			if (Arrays.asList(visibleGroups).contains(
					Constants.CSM_PUBLIC_GROUP)) {
				// only need to assign public visibilities
				secureObject(dataToProtect, Constants.CSM_PUBLIC_GROUP,
						Constants.CSM_READ_ROLE);
			} else {
				// set new visibilities
				for (String group : visibleGroups) {
					secureObject(dataToProtect, group, Constants.CSM_READ_ROLE);
				}
				// set default visibilities
				for (String group : Constants.VISIBLE_GROUPS) {
					secureObject(dataToProtect, group, Constants.CSM_READ_ROLE);
				}
				if (owningGroup != null) {
					secureObject(dataToProtect, owningGroup,
							Constants.CSM_READ_ROLE);
				}
			}
		} catch (Exception e) {
			logger.error("Error in setting visibility", e);
			throw new SecurityException();
		}
	}

	public void assignPublicVisibility(String dataToProtect)
			throws SecurityException {
		try {
			removeExistingVisibleGroups(dataToProtect, Constants.CSM_READ_ROLE);
			// set public visibilities
			secureObject(dataToProtect, Constants.CSM_PUBLIC_GROUP,
					Constants.CSM_READ_ROLE);
		} catch (Exception e) {
			logger.error("Error in setting visibility", e);
			throw new SecurityException();
		}
	}

	public void assignGroupToProtectionGroupWithRole(String groupName,
			String protectionGroupName, String roleName)
			throws SecurityException {
		try {
			Role role = getRole(roleName);
			ProtectionGroup pg = getProtectionGroup(protectionGroupName);
			Group group = getGroup(groupName);
			this.userManager.assignGroupRoleToProtectionGroup(pg
					.getProtectionGroupId().toString(), group.getGroupId()
					.toString(), new String[] { role.getId().toString() });
		} catch (Exception e) {
			logger
					.error(
							"Error in assigning group to protection group with role",
							e);
			throw new SecurityException();
		}
	}

	/**
	 * Check whether user has read access to the data
	 *
	 * @param data
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean isUserAllowed(String data, UserBean user) throws Exception {
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		List<String> publicData = appService.getPublicData();
		if (data != null && StringUtils.containsIgnoreCase(publicData, data)) {
			return true;
		} else if (user != null && checkReadPermission(user, data)) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether user is allowed to at least one data in the collection of
	 * data.
	 *
	 * @param auth
	 * @param dataCollection
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean isAllowedAtLeastOne(AuthorizationService auth,
			Collection<String> dataCollection, UserBean user) throws Exception {
		CustomizedApplicationService appService = (CustomizedApplicationService) ApplicationServiceProvider
				.getApplicationService();
		List<String> publicData = appService.getPublicData();
		for (String data : publicData) {
			if (StringUtils.containsIgnoreCase(dataCollection, data)) {
				return true;
			}
		}

		if (user != null) {
			for (String data : dataCollection) {
				if (auth.checkReadPermission(user, data)) {
					return true;
				}
			}
		}
		return false;
	}

	public void updateDatabaseConnectionForCSMApplications(String dbDialect,
			String dbDriver, String dbURL, String dbUserName, String dbPassword) {
		try {
			Application caNanoLabApp = authorizationManager
					.getApplication(Constants.CSM_APP_NAME);
			caNanoLabApp.setDatabaseURL(dbURL);
			caNanoLabApp.setDatabaseDialect(dbDialect);
			caNanoLabApp.setDatabaseDriver(dbDriver);
			caNanoLabApp.setDatabaseUserName(dbUserName);
			caNanoLabApp.setDatabasePassword(dbPassword);
			authorizationManager.modifyApplication(caNanoLabApp);

			Application csmupt = authorizationManager.getApplication("csmupt");
			csmupt.setDatabaseURL(dbURL);
			csmupt.setDatabaseDialect(dbDialect);
			csmupt.setDatabaseDriver(dbDriver);
			csmupt.setDatabaseUserName(dbUserName);
			csmupt.setDatabasePassword(dbPassword);
			authorizationManager.modifyApplication(csmupt);
		} catch (Exception e) {
			logger
					.error("Can't update database connections for CSM applications");
		}
	}

	public static void main(String[] args) {
		try {
			AuthorizationService service = new AuthorizationService(
					Constants.CSM_APP_NAME);
			service.updateDatabaseConnectionForCSMApplications(args[0],
					args[1], args[2], args[3], args[4]);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
