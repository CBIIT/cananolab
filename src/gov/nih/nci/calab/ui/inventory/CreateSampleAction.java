/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.ui.inventory;

/**
 * This class saves user entered sample and container information 
 * into the database.
 * 
 * @author pansu
 */

/* CVS $Id: CreateSampleAction.java,v 1.13 2007-07-06 15:28:49 zengje Exp $ */

import gov.nih.nci.calab.dto.inventory.ContainerBean;
import gov.nih.nci.calab.dto.inventory.ContainerInfoBean;
import gov.nih.nci.calab.dto.inventory.SampleBean;
import gov.nih.nci.calab.service.inventory.ManageSampleService;
import gov.nih.nci.calab.service.security.UserService;
import gov.nih.nci.calab.service.util.CaNanoLabConstants;
import gov.nih.nci.calab.service.util.PropertyReader;
import gov.nih.nci.calab.service.util.StringUtils;
import gov.nih.nci.calab.ui.core.AbstractDispatchAction;
import gov.nih.nci.calab.ui.core.InitSessionSetup;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class CreateSampleAction extends AbstractDispatchAction {
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();

		// TODO fill in details for sample information */
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String sampleNamePrefix = (String) theForm.get("sampleNamePrefix");

		// read from properties file first
		String samplePrefix = PropertyReader.getProperty(
				CaNanoLabConstants.CANANOLAB_PROPERTY, "samplePrefix");
		// if not available, use the default
		if (samplePrefix == null)
			samplePrefix = CaNanoLabConstants.DEFAULT_SAMPLE_PREFIX;

		// throw an error is the sample name prefix doesn't start with the
		// preconfigured prefix.
		if (!sampleNamePrefix.startsWith(samplePrefix)) {
			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage(
					"error.createSample.SampleIDFormat", samplePrefix);
			msgs.add("error", msg);
			saveMessages(request, msgs);

			forward = mapping.findForward("input");

			return forward;
		}
		String sampleType = (String) theForm.get("sampleType");
		String sampleSOP = (String) theForm.get("sampleSOP");
		String sampleDescription = (String) theForm.get("sampleDescription");
		String sampleSource = (String) theForm.get("sampleSource");
		String sourceSampleId = (String) theForm.get("sourceSampleId");
		String dateReceivedStr = (String) theForm.get("dateReceived");
		Date dateReceived = StringUtils.convertToDate(dateReceivedStr,
				CaNanoLabConstants.ACCEPT_DATE_FORMAT);

		String solubility = (String) theForm.get("solubility");
		String lotId = (String) theForm.get("lotId");
		String lotDescription = (String) theForm.get("lotDescription");
		String numContainers = (String) theForm.get("numberOfContainers");
		String generalComments = (String) theForm.get("generalComments");
		ManageSampleService manageSampleService = new ManageSampleService();
		String sampleName = manageSampleService.getSampleName(sampleNamePrefix,
				lotId);

		// get user information from session
		String sampleSubmitter = (String) session.getAttribute("creator");
		ContainerBean[] containers = (ContainerBean[]) theForm
				.get("containers");
		// update container name to be full container name
		String containerPrefix = manageSampleService.getContainerPrefix(
				sampleNamePrefix, lotId);
		for (ContainerBean container : containers) {
			container.setContainerName(containerPrefix + "-"
					+ container.getContainerName());
		}
		Date creationDate = new Date();
		SampleBean sample = new SampleBean(sampleNamePrefix, sampleName,
				sampleType, sampleSOP, sampleDescription, sampleSource,
				sourceSampleId, dateReceived, solubility, lotId,
				lotDescription, numContainers, generalComments,
				sampleSubmitter, creationDate, containers);
		request.setAttribute("sample", sample);
		manageSampleService.saveSample(sample, containers);

		// create a new user group for the source specified
		UserService userService = new UserService(
				CaNanoLabConstants.CSM_APP_NAME);
		userService.createAGroup(sampleSource);

		// set a flag to indicate that new sample have been created so session
		// can be refreshed in initSession.do
		session.setAttribute("newSampleCreated", "yes");
		session.setAttribute("newSampleSourceCreated", "true");

		forward = mapping.findForward("success");

		return forward;
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		int numContainers = Integer.parseInt((String) theForm
				.get("numberOfContainers"));
		ContainerBean[] origContainers = (ContainerBean[]) theForm
				.get("containers");
		ContainerBean[] containers = new ContainerBean[numContainers];

		// reuse containers from the previous request
		// set other containers to have values from the first container
		if (origContainers.length < numContainers) {
			for (int i = 0; i < origContainers.length; i++) {
				containers[i] = new ContainerBean(origContainers[i]);
			}
			for (int i = origContainers.length; i < numContainers; i++) {
				if (origContainers.length > 0) {
					containers[i] = new ContainerBean(origContainers[0]);
				}
				// if no containers from previous request, set them new
				else {
					containers[i] = new ContainerBean();
				}
			}

		} else {
			for (int i = 0; i < numContainers; i++) {
				containers[i] = new ContainerBean(origContainers[i]);
			}
		}
		theForm.set("containers", containers);
		// update editable drop-down lists to include new entries.
		updateAllEditables(request.getSession(), theForm);
		return mapping.findForward("setup");
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		InitSessionSetup.getInstance().clearWorkflowSession(session);
		InitSessionSetup.getInstance().clearSearchSession(session);
		InitSessionSetup.getInstance().setAllSampleSources(session);
		InitSessionSetup.getInstance().setAllSampleSOPs(session);
		InitSessionSetup.getInstance().setAllSampleContainerTypes(session);
		InitSessionSetup.getInstance().setAllSampleContainerInfo(session);
		InitSessionSetup.getInstance().setCurrentUser(session);

		ManageSampleService mangeSampleService = new ManageSampleService();
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		theForm.getMap().clear();

		theForm.set("lotId", mangeSampleService.getDefaultLotId());
		theForm.set("numberOfContainers", "1");
		theForm.set("sampleNamePrefix", mangeSampleService
				.getDefaultSampleNamePrefix());
		theForm.set("configuredSampleNamePrefix", PropertyReader.getProperty(
				CaNanoLabConstants.CANANOLAB_PROPERTY, "samplePrefix"));
		ContainerBean[] containers = new ContainerBean[] { new ContainerBean() };
		theForm.set("containers", containers);
		return mapping.findForward("setup");
	}

	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		updateAllEditables(request.getSession(), theForm);
		return mapping.findForward("setup");
	}

	public boolean loginRequired() {
		return true;
	}

	private void updateAllEditables(HttpSession session,
			DynaValidatorForm theForm) throws Exception {
		InitSessionSetup.getInstance().updateEditableDropdown(session,
				theForm.getString("sampleSource"), "allSampleSources");
		ContainerBean[] origContainers = (ContainerBean[]) theForm
				.get("containers");
		ContainerInfoBean containerInfo = (ContainerInfoBean) session
				.getAttribute("sampleContainerInfo");

		for (ContainerBean containerBean : origContainers) {
			InitSessionSetup.getInstance()
					.updateEditableDropdown(session,
							containerBean.getContainerType(),
							"allSampleContainerTypes");
			String newRoom = containerBean.getStorageLocation().getRoom();
			String newFreezer = containerBean.getStorageLocation().getFreezer();
			String newShelf = containerBean.getStorageLocation().getShelf();
			String newBox = containerBean.getStorageLocation().getBox();

			containerInfo.getStorageRooms().add(newRoom);
			containerInfo.getStorageFreezers().add(newFreezer);
			containerInfo.getStorageShelves().add(newShelf);
			containerInfo.getStorageBoxes().add(newBox);
		}
	}
}
