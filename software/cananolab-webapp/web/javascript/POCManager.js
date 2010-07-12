/* set the submit point of contact form */
function clearPointOfContact() {
	// go to server and clean form bean
	POCManager.resetThePointOfContact(populatePointOfContact);
	// reset visibility group drop down for POC form
	POCManager.resetVisibilityGroups(function(data) {
		if (data != null) {
			dwr.util.removeAllOptions("visibilityGroups");
			dwr.util.addOptions("visibilityGroups", data);
		}
	});
	// reset visibility group drop down for sample form
	POCManager.resetSampleVisibilityGroups(function(data) {
		if (data != null) {
			dwr.util.removeAllOptions("sampleVisibilityGroups");
			dwr.util.addOptions("sampleVisibilityGroups", data);
		}
	});
	hide("deletePointOfContact");
}

function setThePointOfContact(id, isPrimary) {
	// remove org from visibility group
	POCManager.removeOrgFromVisibilityGroupsByPocId(id, isPrimary, function(
			data) {
		if (data != null) {
			dwr.util.removeAllOptions("visibilityGroups");
			dwr.util.addOptions("visibilityGroups", data);
		}
	});
	// add a timeout to allow correct refresh order
	window.setTimeout("fillPointOfContact(" + id + ", " + isPrimary + ")", 100);
}

function fillPointOfContact(id, isPrimary) {
	POCManager.getPointOfContactById(id, isPrimary, populatePointOfContact);
	// openSubmissionForm("PointOfContact");
	show("addPointOfContact");
	show("newPointOfContact");
}

function openOnePointOfContact(id, isPrimary) {
	setThePointOfContact(id, isPrimary);
	window.setTimeout("show('addPointOfContact')", 200);
}

function populatePointOfContact(poc) {
	if (poc != null) {
		dwr.util.setValues(poc);
		if (poc.domain.id != null && poc.primaryStatus == false) {
			show("deletePointOfContact");
		} else {
			hide("deletePointOfContact");
		}
		if (poc.primaryStatus) {
			show("primaryTitle");
			hide("secondaryTitle");
		} else {
			hide("primaryTitle");
			show("secondaryTitle");
		}
	} else {
		sessionTimeout();
	}
}
function addPointOfContact(actionName) {
	submitAction(document.forms[0], actionName, "savePointOfContact", 3);
}
function removePointOfContact(actionName) {
	submitAction(document.forms[0], actionName, "removePointOfContact", 3);
}
function updateOrganizationInfo() {
	var orgName = dwr.util.getValue("domain.organization.name");
	POCManager.getOrganizationByName(orgName, populateOrganization);
}
function populateOrganization(organization) {
	if (organization != null) {
		dwr.util.setValue("domain.organization.streetAddress1",
				organization.streetAddress1);
		dwr.util.setValue("domain.organization.streetAddress2",
				organization.streetAddress2);
		dwr.util.setValue("domain.organization.city", organization.city);
		dwr.util.setValue("domain.organization.state", organization.state);
		dwr.util.setValue("domain.organization.postalCode",
				organization.postalCode);
		dwr.util.setValue("domain.organization.country", organization.country);
	} else {
		dwr.util.setValue("domain.organization.streetAddress1", "");
		dwr.util.setValue("domain.organization.streetAddress2", "");
		dwr.util.setValue("domain.organization.city", "");
		dwr.util.setValue("domain.organization.state", "");
		dwr.util.setValue("domain.organization.postalCode", "");
		dwr.util.setValue("domain.organization.country", "");
	}
}
function removeOrgFromVisibilityGroups(styleId) {
	var orgName = dwr.util.getValue(styleId);
	if (orgName != null) {
		POCManager.removeOrgFromVisibilityGroupsByOrgName(orgName, function(
				data) {
			dwr.util.removeAllOptions("visibilityGroups");
			dwr.util.addOptions("visibilityGroups", data);
		});
	}
}
function removeOrgFromSampleVisibilityGroups(styleId) {
	var primaryStatus = dwr.util.getValue("primaryStatus");
	// remove org name from sample visibility only when POC is primary POC
	if (primaryStatus == "true") {
		var orgName = dwr.util.getValue(styleId);
		if (orgName != null) {
			POCManager.removeOrgFromVisibilityGroupsByOrgName(orgName,
					function(data) {
						dwr.util.removeAllOptions("sampleVisibilityGroups");
						dwr.util.addOptions("sampleVisibilityGroups", data);
					});
		}
	}
}

function updatePersonInfo() {
	var firstName = dwr.util.getValue("domain.firstName");
	var lastName = dwr.util.getValue("domain.lastName");
	var orgName = dwr.util.getValue("domain.organization.name");
	// only load if either first name or last name and orgName is not null
	if ((firstName.length > 0 || lastName.length > 0) && orgName.length > 0) {
		POCManager.getPointOfContactByNameAndOrg(firstName, lastName, orgName,
				function(data) {
					if (data != null) {
						dwr.util.setValues(data);
					}
				});
	}
}
/* end of submit point of contact form */
