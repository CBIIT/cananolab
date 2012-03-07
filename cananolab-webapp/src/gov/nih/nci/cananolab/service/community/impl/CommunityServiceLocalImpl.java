package gov.nih.nci.cananolab.service.community.impl;

import gov.nih.nci.cananolab.dto.common.AccessibilityBean;
import gov.nih.nci.cananolab.dto.common.CollaborationGroupBean;
import gov.nih.nci.cananolab.exception.CommunityException;
import gov.nih.nci.cananolab.exception.DuplicateEntriesException;
import gov.nih.nci.cananolab.exception.NoAccessException;
import gov.nih.nci.cananolab.service.BaseServiceLocalImpl;
import gov.nih.nci.cananolab.service.community.CommunityService;
import gov.nih.nci.cananolab.service.security.SecurityService;
import gov.nih.nci.cananolab.service.security.UserBean;
import gov.nih.nci.cananolab.util.StringUtils;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.dao.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommunityServiceLocalImpl extends BaseServiceLocalImpl implements
		CommunityService {
	private AuthorizationManager authManager;

	public CommunityServiceLocalImpl() throws CommunityException {
		super();
		try {
			this.authManager = SecurityServiceProvider
					.getAuthorizationManager(AccessibilityBean.CSM_APP_NAME);
		} catch (Exception e) {
			logger.error(e);
			throw new CommunityException(e);
		}
	}

	public CommunityServiceLocalImpl(UserBean user) throws CommunityException {
		super(user);
		try {
			this.authManager = SecurityServiceProvider
					.getAuthorizationManager(AccessibilityBean.CSM_APP_NAME);
		} catch (Exception e) {
			logger.error(e);
			throw new CommunityException(e);
		}
	}

	public CommunityServiceLocalImpl(SecurityService securityService)
			throws CommunityException {
		super(securityService);
		try {
			this.authManager = SecurityServiceProvider
					.getAuthorizationManager(AccessibilityBean.CSM_APP_NAME);
		} catch (Exception e) {
			logger.error(e);
			throw new CommunityException(e);
		}
	}

	public void saveCollaborationGroup(CollaborationGroupBean collaborationGroup)
			throws CommunityException, NoAccessException,
			DuplicateEntriesException {
		if (user == null) {
			throw new NoAccessException();
		}
		try {
			Group doGroup = securityService.getGroup(collaborationGroup
					.getName());
			// group name already exists by another id
			if (doGroup != null
					&& !doGroup.getGroupId().toString().equals(
							collaborationGroup.getId())) {
				throw new DuplicateEntriesException(
						"Group name is already in use");
			}

			// create a new group if none exists.
			if (StringUtils.isEmpty(collaborationGroup.getId())) {
				doGroup = new Group();
				doGroup.setGroupName(collaborationGroup.getName());
				doGroup.setGroupDesc(collaborationGroup.getDescription());
				authManager.createGroup(doGroup);
				collaborationGroup.setId(doGroup.getGroupId().toString());
				// assign CURD access to user who created the group if user is
				// not curator
				if (!user.isCurator()) {
					AccessibilityBean ownerAccess = new AccessibilityBean(
							AccessibilityBean.ACCESS_BY_USER);
					ownerAccess.setUserBean(user);
					ownerAccess.setRoleName(AccessibilityBean.CSM_CURD_ROLE);
					saveAccessibility(ownerAccess,
							AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
									+ doGroup.getGroupId());
					collaborationGroup.getUserAccesses().add(ownerAccess);
				}
				// assign CURD access to Curator group
				saveAccessibility(AccessibilityBean.CSM_DEFAULT_ACCESS,
						AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
								+ doGroup.getGroupId());
				// assign current user to be owner of the collaboration group
				accessUtils.assignOwner(
						AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
								+ doGroup.getGroupId(), user.getLoginName());
				collaborationGroup.getGroupAccesses().add(
						AccessibilityBean.CSM_DEFAULT_ACCESS);
			}
			// update existing group
			else {
				// if user has access to update the group
				if (securityService
						.checkCreatePermission(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
								+ collaborationGroup.getId())) {	
					String oldGroupName=null;
					if (doGroup == null) {
						// update name to a new name not exist in the database
						doGroup = authManager.getGroupById(collaborationGroup
								.getId());
						oldGroupName=doGroup.getGroupName();						
						doGroup.setGroupName(collaborationGroup.getName());
					}
					doGroup.setGroupDesc(collaborationGroup.getDescription());
					authManager.modifyGroup(doGroup);
					//remove old group name from user's groups.
					user.getGroupNames().remove(oldGroupName);
					CollaborationGroupBean existingGroup = findCollaborationGroupById(collaborationGroup
							.getId());
					// update user access if user is the owner of the group or
					// curator
					// if (user.isCurator()
					// || securityService
					// .isOwner(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
					// + doGroup.getGroupId())) {
					List<AccessibilityBean> existingAccess = existingGroup
							.getUserAccesses();
					List<AccessibilityBean> accessToRemove = new ArrayList<AccessibilityBean>(
							existingAccess);
					accessToRemove.removeAll(collaborationGroup
							.getUserAccesses());
					for (AccessibilityBean access : accessToRemove) {
						authManager.removeUserFromGroup(doGroup.getGroupId()
								.toString(), access.getUserBean().getUserId());
						accessUtils
								.removeSecureObjectForUser(
										AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
												+ doGroup.getGroupId(), access
												.getUserBean().getLoginName(),
										AccessibilityBean.CSM_CURD_ROLE);
					}
					// } else {
					// throw new NoAccessException(
					// "You'd have to the curator or the owner of the group to manage users.");
					// }
				} else {
					throw new NoAccessException(
							"You don't have access to update the collaboration group");
				}
			}
			// check if user is the owner the group
			// if (user.isCurator()
			// || securityService
			// .isOwner(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
			// + doGroup.getGroupId())) {
			// check if user can update the group
			if (securityService
					.checkCreatePermission(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
							+ collaborationGroup.getId())) {
				// if the current user is not a curator, he/she should already
				// be in
				// the userIds
				List<String> userIds = new ArrayList<String>();
				for (AccessibilityBean access : collaborationGroup
						.getUserAccesses()) {
					saveAccessibility(access,
							AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
									+ doGroup.getGroupId());
					User user = authManager.getUser(access.getUserBean()
							.getLoginName());
					String userId = user.getUserId().toString();
					userIds.add(userId);
				}
				// if the current user is a curator and is the owner
				if (user.isCurator()
						&& securityService
								.isOwner(
										user.getLoginName(),
										AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
												+ collaborationGroup.getId())) {
					userIds.add(user.getUserId().toString());
				}
				authManager.addUsersToGroup(doGroup.getGroupId().toString(),
						userIds.toArray(new String[0]));
				// update userBean's associated group
				if (!user.getGroupNames().contains(doGroup.getGroupName())) {
					user.getGroupNames().add(doGroup.getGroupName());
				}
			}
		} catch (NoAccessException e) {
			throw e;
		} catch (DuplicateEntriesException e) {
			throw e;
		} catch (Exception e) {
			String error = "Error finding existing collaboration groups accessible by the user";
			throw new CommunityException(error, e);
		}
	}

	public List<CollaborationGroupBean> findCollaborationGroups()
			throws CommunityException {

		List<CollaborationGroupBean> collaborationGroups = new ArrayList<CollaborationGroupBean>();
		try {
			Group dummy = new Group();
			dummy.setGroupName("*");
			SearchCriteria sc = new GroupSearchCriteria(dummy);
			List results = authManager.getObjects(sc);
			for (Object obj : results) {
				Group doGroup = (Group) obj;
				CollaborationGroupBean cGroup = new CollaborationGroupBean(
						doGroup);
				if (securityService
						.checkCreatePermission(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
								+ cGroup.getId())) {
					setAccesses(cGroup);
					collaborationGroups.add(cGroup);
				}
			}

		} catch (Exception e) {
			String error = "Error finding existing collaboration groups accessible by the user";
			throw new CommunityException(error, e);
		}
		return collaborationGroups;
	}

	public void assignOwner(String collaborationGroupId, String ownerLogin)
			throws CommunityException, NoAccessException {
		// user needs to be both curator and admin
		if (!(user.isCurator() && user.isAdmin())) {
			throw new NoAccessException();
		}
		try {
			this.accessUtils.assignOwner(
					AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
							+ collaborationGroupId, ownerLogin);
			// add new owner to the colloborationGroup
			User user = authManager.getUser(ownerLogin);
			authManager.addUsersToGroup(collaborationGroupId,
					new String[] { user.getUserId().toString() });
			// if ownerLogin is not a curator, save owner access

		} catch (Exception e) {
			String error = "Error assigning an owner to the collaboration group by Id "
					+ collaborationGroupId;
			logger.error(error, e);
			throw new CommunityException(error, e);
		}
	}

	// set user accessibilities
	private void setAccesses(CollaborationGroupBean cGroup)
			throws Exception {
		// set user accessibilities
		Set<User> users = authManager.getUsers(cGroup.getId().toString());
		List<String> userNames = new ArrayList<String>(users.size());
		for (User user : users) {
			userNames.add(user.getLoginName());
		}
		Map<String, String> userRoles = securityService
				.getAllUserRoles(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
						+ cGroup.getId());
		List<AccessibilityBean> userAccesses = new ArrayList<AccessibilityBean>();
		for (User aUser : users) {
			if (userRoles.get(aUser.getLoginName()) != null) {
				AccessibilityBean accessibility = new AccessibilityBean(
						AccessibilityBean.ACCESS_BY_USER);
				accessibility.setGroupName(cGroup.getName());
				accessibility.setRoleName(userRoles.get(aUser.getLoginName()));
				accessibility.setUserBean(new UserBean(aUser));
				userAccesses.add(accessibility);
			}
		}
		cGroup.setUserAccesses(userAccesses);
		List<AccessibilityBean> groupAccesses = new ArrayList<AccessibilityBean>();
		if (user.isCurator()) {
			groupAccesses.add(AccessibilityBean.CSM_DEFAULT_ACCESS);
			cGroup.setGroupAccesses(groupAccesses);
		}
		cGroup.setUser(user);
		List<String> ownerNames = accessUtils
				.getOwnerNames(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
						+ cGroup.getId());
		cGroup.setOwnerName(StringUtils.join(ownerNames, ","));
	}

	public CollaborationGroupBean findCollaborationGroupById(String id)
			throws CommunityException {
		CollaborationGroupBean collaborationGroup = null;
		try {
			Group group = authManager.getGroupById(id);
			collaborationGroup = new CollaborationGroupBean(group);
			String pe = AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
					+ collaborationGroup.getId();
			if (securityService.checkCreatePermission(pe)) {
				setAccesses(collaborationGroup);
			} else {
				String error = "User has no access to the collaboration group "
						+ collaborationGroup.getName();
				logger.info(error);
			}
		} catch (Exception e) {
			String error = "Error retrieving the collaboration group by name";
			throw new CommunityException(error, e);
		}
		return collaborationGroup;
	}

	public void deleteCollaborationGroup(
			CollaborationGroupBean collaborationGroup)
			throws CommunityException, NoAccessException {
		if (user == null) {
			throw new NoAccessException();
		}
		try {
			// check if user has access to delete the group
			if (!securityService
					.checkDeletePermission(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
							+ collaborationGroup.getId())) {
				throw new NoAccessException();
			} else {
				super
						.removeAllAccesses(AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
								+ collaborationGroup.getId());
				authManager.removeGroup(collaborationGroup.getId());
				// update user group list
				user.getGroupNames().remove(collaborationGroup.getName());
			}
		} catch (NoAccessException e) {
			throw e;
		} catch (Exception e) {
			String error = "Error deleting the collaboration group";
			throw new CommunityException(error, e);
		}
	}

	public List<String> findCollaborationGroupIdsByOwner(String owner)
			throws CommunityException {
		List<String> groupIds = new ArrayList<String>();
		try {
			User user = authManager.getUser(owner);
			user.getUserId();
			Set groups = authManager.getGroups(user.getUserId().toString());
			for (Object obj : groups) {
				Group group = (Group) obj;
				String groupId = group.getGroupId().toString();
				if (securityService.isOwner(owner,
						AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
								+ groupId)) {
					groupIds.add(groupId);
				}
			}
		} catch (Exception e) {
			String error = "Error finding CollaborationGroupByOwner: " + owner;
			logger.error(error, e);
			throw new CommunityException(error, e);
		}

		return groupIds;
	}

	public void assignAccessibility(AccessibilityBean access,
			String collaborationGroupId) throws CommunityException,
			NoAccessException {
		try {
			if (!user.isCurator()
					&& !securityService.isOwner(user.getLoginName(),
							AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
									+ collaborationGroupId)) {
				throw new NoAccessException();
			}
			this.saveAccessibility(access,
					AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
							+ collaborationGroupId);
		} catch (NoAccessException e) {
			throw e;
		} catch (Exception e) {
			String error = "Error assigning accessibility to collaboration group: "
					+ collaborationGroupId;
			throw new CommunityException(error, e);
		}
	}

	public void removeAccessibility(AccessibilityBean access,
			String collaborationGroupId) throws CommunityException,
			NoAccessException {
		try {
			if (!user.isCurator()
					&& !securityService.isOwner(user.getLoginName(),
							AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
									+ collaborationGroupId)) {
				throw new NoAccessException();
			}
			this.deleteAccessibility(access,
					AccessibilityBean.CSM_COLLABORATION_GROUP_PREFIX
							+ collaborationGroupId);
		} catch (NoAccessException e) {
			throw e;
		} catch (Exception e) {
			String error = "Error removing accessibility to collaboration group: "
					+ collaborationGroupId;
			throw new CommunityException(error, e);
		}
	}
}