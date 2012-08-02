package gov.nih.nci.cananolab.ui.publication;

/**
 * This class submits publication and assigns visibility
 *
 * @author tanq, pansu
 */

import gov.nih.nci.cananolab.domain.common.Publication;
import gov.nih.nci.cananolab.dto.common.AccessibilityBean;
import gov.nih.nci.cananolab.dto.common.DataReviewStatusBean;
import gov.nih.nci.cananolab.dto.common.PublicationBean;
import gov.nih.nci.cananolab.dto.common.PublicationSummaryViewBean;
import gov.nih.nci.cananolab.dto.particle.SampleBean;
import gov.nih.nci.cananolab.service.publication.PublicationService;
import gov.nih.nci.cananolab.service.publication.impl.PublicationExporter;
import gov.nih.nci.cananolab.service.publication.impl.PublicationServiceLocalImpl;
import gov.nih.nci.cananolab.service.sample.SampleService;
import gov.nih.nci.cananolab.service.sample.impl.SampleServiceLocalImpl;
import gov.nih.nci.cananolab.service.security.SecurityService;
import gov.nih.nci.cananolab.service.security.UserBean;
import gov.nih.nci.cananolab.ui.core.BaseAnnotationAction;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.ui.sample.InitSampleSetup;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.DateUtils;
import gov.nih.nci.cananolab.util.ExportUtils;
import gov.nih.nci.cananolab.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class PublicationAction extends BaseAnnotationAction {

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!validateToken(request)) {
			return mapping.findForward("publicationMessage");
		}
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		PublicationBean publicationBean = (PublicationBean) theForm
				.get("publication");
		Boolean newPub = true;
		if (publicationBean.getDomainFile().getId() != null
				&& publicationBean.getDomainFile().getId() > 0) {
			newPub = false;
		}
		if (!this.savePublication(request, theForm)) {
			return input(mapping, form, request, response);
		}

		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String sampleId = (String) theForm.get("sampleId");
		ActionMessages messages = new ActionMessages();

		// retract from public if updating an existing public record and not
		// curator
		if (!newPub && !user.isCurator() && publicationBean.getPublicStatus()) {
			retractFromPublic(theForm, request, publicationBean.getDomainFile()
					.getId().toString(),
					((Publication) publicationBean.getDomainFile()).getTitle(),
					"publication");
			ActionMessage msg = null;
			msg = new ActionMessage(
					"message.updatePublication.retractFromPublic");
			messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, messages);
		} else {
			ActionMessage msg = new ActionMessage("message.submitPublication",
					publicationBean.getDomainFile().getTitle());
			messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, messages);
		}
		resetToken(request);
		if (!StringUtils.isEmpty(sampleId)) {
			return summaryEdit(mapping, form, request, response);
		} else {
			return mapping.findForward("success");
		}
	}

	private Boolean savePublication(HttpServletRequest request,
			DynaValidatorForm theForm) throws Exception {
		PublicationBean publicationBean = (PublicationBean) theForm
				.get("publication");
		PublicationService service = this.setServicesInSession(request);
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String sampleId = (String) theForm.get("sampleId");
		ActionMessages msgs = new ActionMessages();
		// validate publication file
		if (!validatePublicationFile(publicationBean)) {
			ActionMessage msg = new ActionMessage("publication.fileRequired");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveErrors(request, msgs);
			return false;
		}
		// validate associated sample names
		if (StringUtils.isEmpty(sampleId)
				&& !validateAssociatedSamples(request, publicationBean)) {
			ActionMessage msg = new ActionMessage(
					"error.submitPublication.invalidSample");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveErrors(request, msgs);
			return false;
		}

		/**
		 * add current sample to associated samples if from sample publication
		 * page
		 */
		if (!StringUtils.isEmpty(sampleId)) {
			publicationBean.setFromSamplePage(true);
			Set<String> sampleNames = new HashSet<String>();
			SampleBean sampleBean = setupSample(theForm, request);
			sampleNames.add(sampleBean.getDomain().getName());
			/**
			 * If user chosen other samples, need to add this pub to those
			 * samples.
			 */
			String[] otherSamples = (String[]) theForm.get("otherSamples");
			if (otherSamples.length > 0) {
				sampleNames.addAll(Arrays.asList(otherSamples));
			}
			publicationBean.setSampleNames(sampleNames
					.toArray(new String[sampleNames.size()]));
		} else {
			publicationBean.setFromSamplePage(false);
		}

		publicationBean.setupDomain(Constants.FOLDER_PUBLICATION,
				user.getLoginName());
		service.savePublication(publicationBean);

		InitPublicationSetup.getInstance().persistPublicationDropdowns(request,
				publicationBean);

		if (!StringUtils.isEmpty(sampleId)) {
			SortedSet<String> publicationCategories = InitSetup.getInstance()
					.getDefaultAndOtherTypesByLookup(request,
							"publicationCategories", "publication", "category",
							"otherCategory", true);
			List<String> allPublicationTypes = new ArrayList<String>(
					publicationCategories);
			int ind = allPublicationTypes
					.indexOf(((Publication) publicationBean.getDomainFile())
							.getCategory()) + 1;
			request.setAttribute("onloadJavascript", "showSummary('" + ind
					+ "', " + allPublicationTypes.size() + ")");
		}
		return true;
	}

	private boolean validateAssociatedSamples(HttpServletRequest request,
			PublicationBean publicationBean) throws Exception {
		// sample service has already been created
		SampleService service = (SampleService) request.getSession()
				.getAttribute("sampleService");
		for (String sampleName : publicationBean.getSampleNames()) {
			if (!StringUtils.isEmpty(sampleName)) {
				SampleBean sampleBean = service.findSampleByName(sampleName);
				if (sampleBean == null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Handle delete request from Sample -> Publication -> Edit page.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeFromSample(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!validateToken(request)) {
			return mapping.findForward("publicationMessage");
		}
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		PublicationService service = this.setServicesInSession(request);
		PublicationBean publicationBean = (PublicationBean) theForm
				.get("publication");
		SampleBean sampleBean = this.setupSample(theForm, request);
		service.removePublicationFromSample(sampleBean.getDomain().getName(),
				(Publication) publicationBean.getDomainFile());
		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage("message.deletePublication",
				publicationBean.getDomainFile().getTitle());
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveMessages(request, msgs);
		return summaryEdit(mapping, form, request, response);
	}

	/**
	 * Delete a publication from Publication update form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!validateToken(request)) {
			return mapping.findForward("publicationMessage");
		}
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		PublicationService service = this.setServicesInSession(request);
		PublicationBean publicationBean = (PublicationBean) theForm
				.get("publication");

		// update data review status to "DELETED"
		updateReviewStatusTo(DataReviewStatusBean.DELETED_STATUS, request,
				publicationBean.getDomainFile().getId().toString(),
				publicationBean.getDomainFile().getTitle(), "publication");
		service.deletePublication((Publication) publicationBean.getDomainFile());
		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage("message.deletePublication",
				publicationBean.getDomainFile().getTitle());
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveMessages(request, msgs);
		resetToken(request);
		return mapping.findForward("success");
	}

	public ActionForward setupNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PublicationBean pubBean = new PublicationBean();
		String sampleId = request.getParameter("sampleId");
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		super.checkOpenAccessForm(theForm, request);
		theForm.set("sampleId", sampleId);
		// clear copy to otherSamples
		theForm.set("otherSamples", new String[0]);

		String type = request.getParameter("type");
		if (!StringUtils.isEmpty(type)) {
			((Publication) pubBean.getDomainFile()).setCategory(type);
		}
		theForm.set("publication", pubBean);
		InitPublicationSetup.getInstance().setPublicationDropdowns(request);
		ActionForward forward = mapping.getInputForward();
		if (!StringUtils.isEmpty(sampleId)) {
			InitSampleSetup.getInstance()
					.getOtherSampleNames(request, sampleId);
			forward = mapping.findForward("sampleSubmitPublication");
		}
		request.setAttribute("onloadJavascript",
				"updateSubmitFormBasedOnCategory()");
		request.getSession().removeAttribute("updatePublication");
		saveToken(request);
		return forward;
	}

	public ActionForward setupUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		super.checkOpenAccessForm(theForm, request);
		String publicationId = super.validateId(request, "publicationId");		
		PublicationService publicationService = this
				.setServicesInSession(request);
		PublicationBean pubBean = publicationService.findPublicationById(
				publicationId, true);
		theForm.set("publication", pubBean);		
		InitPublicationSetup.getInstance().setPublicationDropdowns(request);
		request.setAttribute("onloadJavascript",
				"updateSubmitFormBasedOnCategory();updateFormFields('"
						+ publicationId + "')");
		setUpSubmitForReviewButton(request, pubBean.getDomainFile().getId()
				.toString(), pubBean.getPublicStatus());
		request.getSession().setAttribute("updatePublication", "true");
		
		//detect whether request is related to sample
		String sampleId = request.getParameter("sampleId");
		if (sampleId==null) {
			sampleId=(String)request.getAttribute("sampleId");
		}
		saveToken(request);
		if (!StringUtils.isEmpty(sampleId)) {
			theForm.set("sampleId", sampleId);
			//clear copy other samples
			theForm.set("otherSamples", new String[0]); 
			InitSampleSetup.getInstance()
					.getOtherSampleNames(request, sampleId);
			return mapping.findForward("sampleSubmitPublication");
		} else {
			return mapping.findForward("publicationSubmitPublication");
		}
	}

	/**
	 * Handle summary report print request.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward summaryPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Marker that indicates page is for printing (hide tabs, links, etc).
		request.setAttribute("printView", Boolean.TRUE);

		PublicationSummaryViewBean summaryBean = (PublicationSummaryViewBean) request
				.getSession().getAttribute("publicationSummaryView");
		if (summaryBean == null) {
			// Prepare data.
			this.prepareSummary(mapping, form, request, response);
			summaryBean = (PublicationSummaryViewBean) request.getSession()
					.getAttribute("publicationSummaryView");
		}

		// Filter out categories that not selected.
		this.filterType(request, summaryBean);

		return mapping.findForward("summaryPrint");
	}

	/**
	 * Handle summary report view request.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward summaryView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Prepare data.
		this.prepareSummary(mapping, form, request, response);

		return mapping.findForward("summaryView");
	}

	/**
	 * Handle summary report edit request.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward summaryEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// if session is expired or the url is clicked on directly
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if (user == null) {
			return summaryView(mapping, form, request, response);
		}
		this.prepareSummary(mapping, form, request, response);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		super.checkOpenAccessForm(theForm, request);
		saveToken(request);
		return mapping.findForward("summaryEdit");
	}

	/**
	 * Handle summary report export request.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward summaryExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SampleBean sampleBean = (SampleBean) request.getSession().getAttribute(
				"theSample");
		PublicationSummaryViewBean summaryBean = (PublicationSummaryViewBean) request
				.getSession().getAttribute("publicationSummaryView");
		if (sampleBean == null || summaryBean == null) {
			// Prepare data.
			this.prepareSummary(mapping, form, request, response);
			sampleBean = (SampleBean) request.getSession().getAttribute(
					"theSample");
			summaryBean = (PublicationSummaryViewBean) request.getSession()
					.getAttribute("publicationSummaryView");
		}
		this.filterType(request, summaryBean);

		// Get sample name for constructing file name.
		String type = request.getParameter("type");
		String fileName = ExportUtils.getExportFileName(sampleBean.getDomain()
				.getName(), "PublicationSummaryView", type);
		ExportUtils.prepareReponseForExcel(response, fileName);
		PublicationExporter.exportSummary(summaryBean,
				response.getOutputStream());

		return null;
	}

	/**
	 * Shared function for summaryView(), summaryEdit(), summaryExport() and
	 * summaryPrint().
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	private void prepareSummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Remove previous result from session.
		HttpSession session = request.getSession();
		session.removeAttribute("publicationSummaryView");
		session.removeAttribute("theSample");

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String sampleId = theForm.getString("sampleId");
		PublicationService publicationService = this
				.setServicesInSession(request);
		SampleBean sampleBean = setupSample(theForm, request);
		InitSetup.getInstance().getDefaultAndOtherTypesByLookup(request,
				"publicationCategories", "publication", "category",
				"otherCategory", true);
		/*
		 * if (!StringUtils.isEmpty(location) &&
		 * !location.equals(Constants.LOCAL_SITE)) { String serviceUrl =
		 * InitSetup.getInstance().getGridServiceUrl( request, location);
		 * publicationService = new PublicationServiceRemoteImpl(serviceUrl); }
		 */
		List<PublicationBean> publications = publicationService
				.findPublicationsBySampleId(sampleId);
		PublicationSummaryViewBean summaryView = new PublicationSummaryViewBean(
				publications);

		// Save sample & publication bean in session for printing/exporting.
		session.setAttribute("publicationSummaryView", summaryView);
		session.setAttribute("theSample", sampleBean);

		if ("true".equals(request.getParameter("clearTab"))) {
			request.getSession().removeAttribute("onloadJavascript");
		}
	}

	public ActionForward printDetailView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PublicationService publicationService = this
				.setServicesInSession(request);
		/*
		 * if (!StringUtils.isEmpty(location) &&
		 * !location.equals(Constants.LOCAL_SITE)) { String serviceUrl =
		 * InitSetup.getInstance().getGridServiceUrl( request, location);
		 * publicationService = new PublicationServiceRemoteImpl(serviceUrl); }
		 */
		String publicationId = request.getParameter("publicationId");
		PublicationBean pubBean = publicationService.findPublicationById(
				publicationId, false);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		theForm.set("publication", pubBean);
		return mapping.findForward("publicationDetailPrintView");
	}

	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// save new entered other types
		InitPublicationSetup.getInstance().setPublicationDropdowns(request);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		super.checkOpenAccessForm(theForm, request);
		PublicationBean publicationBean = (PublicationBean) theForm
				.get("publication");
		// set empty year to null instead of the default 0
		Integer year = ((Publication) publicationBean.getDomainFile())
				.getYear();
		if (year != null && year == 0) {
			((Publication) publicationBean.getDomainFile()).setYear(null);
		}
		String selectedPublicationType = ((Publication) publicationBean
				.getDomainFile()).getCategory();
		if (selectedPublicationType != null) {
			SortedSet<String> types = (SortedSet<String>) request.getSession()
					.getAttribute("publicationCategories");
			if (types != null) {
				types.add(selectedPublicationType);
				request.getSession().setAttribute("publicationCategories",
						types);
			}
		}
		String selectedPublicationStatus = ((Publication) publicationBean
				.getDomainFile()).getStatus();
		if (selectedPublicationStatus != null) {
			SortedSet<String> statuses = (SortedSet<String>) request
					.getSession().getAttribute("publicationStatuses");
			if (statuses != null) {
				statuses.add(selectedPublicationStatus);
				request.getSession().setAttribute("publicationStatuses",
						statuses);
			}
		}
		theForm.set("publication", publicationBean);

		/**
		 * Set PubMedId from default value 0 to null for better displaying
		 * result.
		 */
		Publication pub = (Publication) publicationBean.getDomainFile();
		if (pub.getPubMedId() != null && pub.getPubMedId() == 0) {
			pub.setPubMedId(null);
		}
		// disable PubMed fields from parsing and toggle access name label
		if (pub.getPubMedId() != null) {
			request.setAttribute(
					"onloadJavascript",
					"updateSubmitFormBasedOnCategory();disableAutoFields(); toggleAccessNameLabel()");
		} else {
			request.setAttribute("onloadJavascript",
					"updateSubmitFormBasedOnCategory();enableAutoFields();toggleAccessNameLabel()");
		}
		return mapping.findForward("publicationSubmitPublication");
	}

	public ActionForward addAuthor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!validateToken(request)) {
			return mapping.findForward("publicationMessage");
		}
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		PublicationBean pbean = (PublicationBean) theForm.get("publication");
		pbean.addAuthor();

		return mapping.getInputForward();
	}

	private boolean validatePublicationFile(PublicationBean pubBean) {
		Publication publication = (Publication) pubBean.getDomainFile();
		// don't need the file or url if PubMed ID or DOI is entered
		if ((publication.getPubMedId() != null && publication.getPubMedId() != 0)
				|| !StringUtils.isEmpty(publication.getDigitalObjectId())) {
			return true;
		} else if (publication.getUriExternal()
				&& !StringUtils.isEmpty(pubBean.getExternalUrl())) {
			return true;
		} else if (!publication.getUriExternal()
				&& !StringUtils
						.isEmpty(pubBean.getUploadedFile().getFileName())) {
			return true;
		} else if (!StringUtils.isEmpty(publication.getUri())) {
			return true;
		}
		// doesn't require file if publication status is not "published"
		if (!publication.getStatus().equalsIgnoreCase("published")) {
			return true;
		}
		return false;
	}

	// private boolean validateResearchAreas(HttpServletRequest request,
	// String[] researchAreas) throws Exception {
	// ActionMessages msgs = new ActionMessages();
	// boolean noErrors = true;
	// if (researchAreas == null || researchAreas.length == 0) {
	// ActionMessage msg = new ActionMessage(
	// "submitDynaValidatorForm.publication.researchArea", "researchAreas");
	// msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
	// this.saveErrors(request, msgs);
	// noErrors = false;
	// } else {
	// System.out.println("validateResearchAreas =="
	// + Arrays.toString(researchAreas));
	// }
	// return noErrors;
	// }

	public ActionForward exportDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PublicationService publicationService = this
				.setServicesInSession(request);

		String publicationId = request.getParameter("publicationId");
		PublicationBean pubBean = publicationService.findPublicationById(
				publicationId, false);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		theForm.set("publication", pubBean);
		String title = pubBean.getDomainFile().getTitle();
		if (!StringUtils.isEmpty(title)) {
			title = title.substring(0, 10);
		}

		String fileName = this.getExportFileName(title, "detailView");
		ExportUtils.prepareReponseForExcel(response, fileName);
		PublicationExporter.exportDetail(pubBean, response.getOutputStream());

		return null;
	}

	private String getExportFileName(String titleName, String viewType) {
		List<String> nameParts = new ArrayList<String>();
		nameParts.add(titleName);
		nameParts.add("Publication");
		nameParts.add(viewType);
		nameParts.add(DateUtils.convertDateToString(Calendar.getInstance()
				.getTime()));
		String exportFileName = StringUtils.join(nameParts, "_");
		return exportFileName;
	}

	/**
	 * Shared function for summaryExport() and summaryPrint(). Filter out
	 * unselected types when user selected one type for print/export.
	 * 
	 * @param request
	 * @param compBean
	 */
	private void filterType(HttpServletRequest request,
			PublicationSummaryViewBean summaryBean) {
		// 1. Restore all data first as bean might be filtered before.
		SortedMap<String, List<PublicationBean>> dataMap = summaryBean
				.getCategory2Publications();
		summaryBean.setPublicationCategories(dataMap.keySet());

		// 2. Filter out categories that are not selected.
		String type = request.getParameter("type");
		if (!StringUtils.isEmpty(type)) {
			Set<String> cats = new HashSet<String>(1);
			cats.add(type);
			summaryBean.setPublicationCategories(cats);
		}
	}

	private PublicationService setServicesInSession(HttpServletRequest request)
			throws Exception {
		SecurityService securityService = super
				.getSecurityServiceFromSession(request);
		PublicationService publicationService = new PublicationServiceLocalImpl(
				securityService);
		request.getSession().setAttribute("publicationService",
				publicationService);
		SampleService sampleService = new SampleServiceLocalImpl(
				securityService);
		request.getSession().setAttribute("sampleService", sampleService);
		return publicationService;
	}

	public ActionForward saveAccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!validateToken(request)) {
			return mapping.findForward("publicationMessage");
		}
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		PublicationBean publication = (PublicationBean) theForm
				.get("publication");

		AccessibilityBean theAccess = publication.getTheAccess();
		if (!super.validateAccess(request, theAccess)) {
			return input(mapping, form, request, response);
		}
		PublicationService service = this.setServicesInSession(request);
		// if publication is new, save publication first
		if (publication.getDomainFile().getId() == null
				|| publication.getDomainFile().getId() != null
				&& publication.getDomainFile().getId() == 0) {
			this.savePublication(request, theForm);
		}
		// if publication is public, the access is not public, retract
		// public
		// privilege would be handled in the service method
		service.assignAccessibility(theAccess,
				(Publication) publication.getDomainFile());
		// update status to retracted if the access is not public and
		// publication is public
		if (theAccess.getGroupName().equals(AccessibilityBean.CSM_PUBLIC_GROUP)
				&& publication.getPublicStatus()) {
			updateReviewStatusTo(DataReviewStatusBean.RETRACTED_STATUS,
					request, publication.getDomainFile().getId().toString(),
					publication.getDomainFile().getTitle(), "publication");
		}
		// if access is public, pending review status, update review
		// status to public
		if (theAccess.getGroupName().equals(AccessibilityBean.CSM_PUBLIC_GROUP)) {
			this.switchPendingReviewToPublic(request, publication
					.getDomainFile().getId().toString());
		}

		this.setAccesses(request, publication);
		request.setAttribute("publicationId", publication.getDomainFile()
				.getId().toString());
		
		//check if sampleId exists in the form
		if (theForm.get("sampleId")!=null) {
			request.setAttribute("sampleId", theForm.get("sampleId"));
		}
		return setupUpdate(mapping, form, request, response);
	}

	protected void setAccesses(HttpServletRequest request,
			PublicationBean publicationBean) throws Exception {
		PublicationService service = (PublicationService) request.getSession()
				.getAttribute("publicationService");
		List<AccessibilityBean> groupAccesses = service
				.findGroupAccessibilities(publicationBean.getDomainFile()
						.getId().toString());
		List<AccessibilityBean> userAccesses = service
				.findUserAccessibilities(publicationBean.getDomainFile()
						.getId().toString());
		publicationBean.setUserAccesses(userAccesses);
		publicationBean.setGroupAccesses(groupAccesses);
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		publicationBean.setUser(user);
	}

	public ActionForward deleteAccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!validateToken(request)) {
			return mapping.findForward("publicationMessage");
		}
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		PublicationBean publication = (PublicationBean) theForm
				.get("publication");
		AccessibilityBean theAccess = publication.getTheAccess();
		PublicationService service = this.setServicesInSession(request);
		service.removeAccessibility(theAccess,
				(Publication) publication.getDomainFile());
		this.setAccesses(request, publication);
		request.setAttribute("publicationId", publication.getDomainFile()
				.getId().toString());
		
		//check if sampleId exists in the form
		if (theForm.get("sampleId")!=null) {
			request.setAttribute("sampleId", theForm.get("sampleId"));
		}
		return setupUpdate(mapping, form, request, response);
	}

	protected void removePublicAccess(DynaValidatorForm theForm,
			HttpServletRequest request) throws Exception {
		PublicationBean publication = (PublicationBean) theForm
				.get("publication");
		PublicationService service = this.setServicesInSession(request);
		service.removeAccessibility(AccessibilityBean.CSM_PUBLIC_ACCESS,
				(Publication) publication.getDomainFile());
	}

	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PublicationService service = this.setServicesInSession(request);
		return downloadFile(service, mapping, form, request, response);
	}
}
