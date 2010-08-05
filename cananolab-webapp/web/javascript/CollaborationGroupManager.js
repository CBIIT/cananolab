var userAccessCache = {};
var currentGroup = {};
var numberOfUserAccesses = 0; // number of user accesses in the cache,

function setTheCollaborationGroup(groupId) {
	numberOfUserAccesses = 0;
	CollaborationGroupManager.getCollaborationGroupById(groupId,
			populateCollaborationGroup);
	show("deleteCollaborationGroup");
	hide("newCollaborationGroupLabel");
	document.getElementById("")
	openSubmissionForm("CollaborationGroup");
	// Feature request [26487] Deeper Edit Links.
	window.setTimeout("openOneUserAccess()", 500);
}
// Populate user submission form and auto open it for user.
function openOneUserAccess() {
	if (currentGroup != null && currentGroup.userAccessibilities.length == 1) {
		var userAccess = currentGroup.userAccessibilities[0];
		populateUserAccessForm(userAccess);
	} else {
		hide("newUser");
	}
}
// Populate the Collaboration Group form.
function populateCollaborationGroup(group) {
	if (group != null) {
		currentGroup = group;
		if (group.name != null) {
			dwr.util.setValue("groupName", group.name);
			dwr.util.setValue("groupDescription", group.description);
			dwr.util.setValue("groupId", group.id);
			if (group.ownerName!=null) {
				show("userSection");
				show("userLabel");
			}
			else {
				hide("userSection");
				hide("userLabel");
			}
		}
		// clear the cache for each new collaborationGroup
		userAccessCache = {};
		populateUserAccesses();
	} else {
		sessionTimeout();
	}
}
// Populate the user access table inside collaboration group form.
function populateUserAccesses() {
	var userAccesses = currentGroup.userAccessibilities;
	dwr.util.removeAllRows("userRows", {
		filter : function(tr) {
			return (tr.id != "pattern" && tr.id != "patternHeader");
		}
	});
	var userAccess, id;
	if (userAccesses.length > 0) {
		show("userTable");
	} else {
		hide("userTable");
	}
	for ( var i = 0; i < userAccesses.length; i++) {
		userAccess = userAccesses[i];
		id = userAccess.userBean.loginName;
		dwr.util.cloneNode("pattern", {
			idSuffix : id
		});
		dwr.util.setValue("rowUserLoginName" + id,
				userAccess.userBean.loginName);
		dwr.util.setValue("rowRoleName" + id, userAccess.roleDisplayName);
		$("pattern" + id).style.display = "";
		if (userAccessCache[id] == null) {
			numberOfUserAccesses++;
		}
		userAccessCache[id] = userAccess;
	}
	closeSubmissionForm('User');
}

function clearCollaborationGroup() {
	// go to server and clean form bean
	CollaborationGroupManager
			.resetTheCollaborationGroup(populateCollaborationGroup);
	hide("deleteCollaborationGroup");
	hide("newUser");
	numberOfUserAccesses = 0;
}

function deleteTheCollaborationGroup() {
	var answer = confirmDelete("collaboration group");
	if (answer != 0) {
		submitAction(document.forms[0], "collaborationGroup", "delete", 2);
	}
}

function addUserAccess() {
	var userBean = {
		loginName : null
	};
	var userAccess = {
		userBean : userBean,
		roleName : null,
		accessBy : "user"
	};

	dwr.util.getValues(userAccess);
	if (userAccess.userBean.loginName != "" && userAccess.roleName != "") {
		CollaborationGroupManager.addUserAccess(userAccess, function(group) {
			if (group == null) {
				sessionTimeout();
			}
			currentGroup = group;
		});
		window.setTimeout("populateUserAccesses()", 200);
		return true;
	} else {
		alert("Please fill in both fields.");
		return false;
	}
}

function clearUserAccess() {
	dwr.util.setValue("userBean.loginName", "");
	dwr.util.setValue("roleName", "");
	hide("deleteUser");
	enableButtons( [ "submitButton", "deleteCollaborationGroup" ]);
}

function editUserAccess(userLoginName) {
	var userAccess = userAccessCache[userLoginName.substring(4)];
	populateUserAccessForm(userAccess);
	disableButtons( [ "submitButton", "deleteCollaborationGroup" ]);
}

function populateUserAccessForm(userAccess) {
	dwr.util.setValues(userAccess);
	show("deleteUser");
	show("newUser");
}
function deleteTheUserAccess() {
	var userLoginName = dwr.util.getValue("userBean.loginName");
	if (userLoginName != "") {
		var userAccess = userAccessCache[userLoginName];
		if (confirm("Are you sure you want to delete access for '"
				+ userLoginName + "'?")) {
			CollaborationGroupManager.deleteUserAccess(userAccess, function(
					group) {
				if (group == null) {
					sessionTimeout();
				}
				currentGroup = group;
			});
			window.setTimeout("populateUserAccesses()", 200);
			hide("newUser");
		}
	}
}

function showMatchedUserDropdown() {
	// display progress.gif while waiting for the response.
	show("loaderImg");
	hide("matchedUserNameSelect");
	hide("cancelBrowse");
	var selected = dwr.util.getValue("matchedUserNameSelect");
	var loginName = dwr.util.getValue("userBean.loginName");
	CollaborationGroupManager.getMatchedUsers(loginName, function(data) {
		dwr.util.removeAllOptions("matchedUserNameSelect");
		dwr.util.addOptions("matchedUserNameSelect", data, "loginName", "fullName");
		dwr.util.setValue("matchedUserNameSelect", selected);
		hide("loaderImg");
		show("matchedUserNameSelect");
		show("cancelBrowse");
	});
}

function updateUserLoginName() {
	var selected = dwr.util.getValue("matchedUserNameSelect");
	dwr.util.setValue("userBean.loginName", selected);
	hide("matchedUserNameSelect");
	hide("cancelBrowse");
}


function cancelBrowseSelect() {
	hide("matchedUserNameSelect");
	hide("cancelBrowse");
}