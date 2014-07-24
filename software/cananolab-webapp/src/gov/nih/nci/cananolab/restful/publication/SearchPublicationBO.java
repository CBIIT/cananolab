package gov.nih.nci.cananolab.restful.publication;

import gov.nih.nci.cananolab.dto.common.AccessibilityBean;
import gov.nih.nci.cananolab.dto.common.PublicationBean;
import gov.nih.nci.cananolab.exception.SecurityException;
import gov.nih.nci.cananolab.restful.core.BaseAnnotationBO;
import gov.nih.nci.cananolab.restful.sample.InitSampleSetup;
import gov.nih.nci.cananolab.restful.util.PublicationUtil;
import gov.nih.nci.cananolab.restful.util.SampleUtil;
import gov.nih.nci.cananolab.service.publication.PublicationService;
import gov.nih.nci.cananolab.service.publication.impl.PublicationServiceLocalImpl;
import gov.nih.nci.cananolab.service.security.SecurityService;
import gov.nih.nci.cananolab.service.security.UserBean;
import gov.nih.nci.cananolab.ui.form.SearchPublicationForm;
import gov.nih.nci.cananolab.util.ClassUtils;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class SearchPublicationBO extends BaseAnnotationBO {
	
	public List search(SearchPublicationForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		// get the page number from request
		int displayPage = getDisplayPage(request);
		// only on local service to improve performance
		this.setServiceInSession(request);
		List<PublicationBean> publicationBeans = null;
		// retrieve from session if it's not null and not the first page
		if (session.getAttribute("publicationSearchResults") != null
				&& displayPage > 0) {
			publicationBeans = new ArrayList<PublicationBean>((List) session
					.getAttribute("publicationSearchResults"));
		} else {
			publicationBeans = queryPublications(form, request);
			if (publicationBeans != null && !publicationBeans.isEmpty()) {
				session.setAttribute("publicationSearchResults",
						publicationBeans);
			} else {
			//	ActionMessages msgs = new ActionMessages();
			//	ActionMessage msg = new ActionMessage(
			//			"message.searchPublication.noresult");
			//	msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			//	saveMessages(request, msgs);
			//	return mapping.getInputForward();
			//	return publicationBeans;
			}
		}
		// load publicationBean details 25 at a time for displaying
		// pass in page and size
		List<PublicationBean> pubBeansPerPage = getPublicationsPerPage(
				publicationBeans, displayPage,
				Constants.DISPLAY_TAG_TABLE_SIZE, request);
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if (user != null) {
			loadUserAccess(request, pubBeansPerPage);
		}
		//set in sessionScope so user can go back to the result from the sample summary page
		request.getSession().setAttribute("publications", pubBeansPerPage);
		// get the total size of collection , required for display tag to
		// get the pagination to work
		//set in sessionScope so user can go back to the result from the sample summary page
		request.getSession()
				.setAttribute("resultSize",
						new Integer(publicationBeans.size()));
	//	return mapping.findForward("success");
		return pubBeansPerPage;
	}

	private void loadUserAccess(HttpServletRequest request,
			List<PublicationBean> publicationBeans) throws Exception {
		List<String> publicationIds = new ArrayList<String>();

		for (PublicationBean publicationBean : publicationBeans) {
			publicationIds.add(publicationBean.getDomainFile().getId()
					.toString());
		}
		SecurityService securityService = getSecurityServiceFromSession(request);
		Map<String, List<String>> privilegeMap = securityService
				.getPrivilegeMap(publicationIds);
		for (PublicationBean publicationBean : publicationBeans) {
			List<String> privileges = privilegeMap.get(publicationBean
					.getDomainFile().getId().toString());
			if (privileges.contains(AccessibilityBean.CSM_UPDATE_PRIVILEGE)) {
				publicationBean.setUserUpdatable(true);
			} else {
				publicationBean.setUserUpdatable(false);
			}
			if (privileges.contains(AccessibilityBean.CSM_DELETE_PRIVILEGE)) {
				publicationBean.setUserDeletable(true);
			} else {
				publicationBean.setUserDeletable(false);
			}
		}
	}

	private List<PublicationBean> getPublicationsPerPage(
			List<PublicationBean> publicationBeans, int page, int pageSize,
			HttpServletRequest request) throws Exception {
		List<PublicationBean> loadedPublicationBeans = new ArrayList<PublicationBean>();
		PublicationService service = null;
		if (request.getSession().getAttribute("publicationService") != null) {
			service = (PublicationService) request.getSession().getAttribute(
					"publicationService");
		} else {
			service = this.setServiceInSession(request);
		}
		for (int i = page * pageSize; i < (page + 1) * pageSize; i++) {
			if (i < publicationBeans.size()) {
				String publicationId = publicationBeans.get(i).getDomainFile()
						.getId().toString();
				PublicationBean pubBean = service.findPublicationById(
						publicationId, false);
				if (pubBean != null) {
					loadedPublicationBeans.add(pubBean);
				}
			}
		}
		return loadedPublicationBeans;
	}

	public List<PublicationBean> queryPublications(SearchPublicationForm form,
			HttpServletRequest request) throws Exception {
	//	DynaValidatorForm theForm = (DynaValidatorForm) form;
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		String title = "";
		// publication type
		String category = "";
		String[] keywords = new String[0];
		String pubMedId = "";
		String digitalObjectId = "";
		String[] authors = new String[0];
		String sampleName = "";
		String[] researchAreas = new String[0];
		String[] nanomaterialEntityTypes = new String[0];
		String[] functionalizingEntityTypes = new String[0];
		String[] functionTypes = new String[0];

	//	title = (String) theForm.get("title");
		title = form.getTitle();
		// strip wildcards from either end of title if entered
		title = StringUtils.stripWildcards(title);
		//String titleOperand = (String) theForm.get("titleOperand");
		String titleOperand = form.getTitleOperand();
		if (titleOperand.equals(Constants.STRING_OPERAND_CONTAINS)
				&& !StringUtils.isEmpty(title)) {
			title = "*" + title + "*";
		}
	//	category = (String) theForm.get("category");
		category = form.getCategory();
		String keywordsStr = form.getKeywordsStr();
		List<String> wordList = StringUtils.parseToWords(keywordsStr, "\r\n");
		if (wordList != null) {
			keywords = new String[wordList.size()];
			wordList.toArray(keywords);
		}
		pubMedId = form.getPubMedId();
		digitalObjectId = form.getDigitalObjectId();
		String authorsStr = form.getAuthorsStr();
		List<String> authorList = StringUtils.parseToWords(authorsStr, "\r\n");
		if (authorList != null) {
			authors = new String[authorList.size()];
			authorList.toArray(authors);
		}

		sampleName = form.getSampleName();
		sampleName = StringUtils.stripWildcards(sampleName);
		String nameOperand = form.getNameOperand();
		if (nameOperand.equals(Constants.STRING_OPERAND_CONTAINS)
				&& !StringUtils.isEmpty(sampleName)) {
			sampleName = "*" + sampleName + "*";
		}

		researchAreas = form.getResearchArea();
		// publicationOrReport = (String[]) theForm
		// .get("publicationOrReport");
		nanomaterialEntityTypes = form
				.getNanomaterialEntityTypes();
		functionalizingEntityTypes = form
				.getFunctionalizingEntityTypes();
		functionTypes = form.getFunctionTypes();
		List<String> nanomaterialEntityClassNames = new ArrayList<String>();
		List<String> otherNanomaterialEntityTypes = new ArrayList<String>();
		for (int i = 0; i < nanomaterialEntityTypes.length; i++) {
			String className = ClassUtils
					.getShortClassNameFromDisplayName(nanomaterialEntityTypes[i]);
			if (className.length() == 0) {
				className = "OtherNanomaterialEntity";
				otherNanomaterialEntityTypes.add(nanomaterialEntityTypes[i]);
			} else {
				nanomaterialEntityClassNames.add(className);
			}
		}
		List<String> functionalizingEntityClassNames = new ArrayList<String>();
		List<String> otherFunctionalizingTypes = new ArrayList<String>();
		for (int i = 0; i < functionalizingEntityTypes.length; i++) {
			String className = ClassUtils
					.getShortClassNameFromDisplayName(functionalizingEntityTypes[i]);
			if (className.length() == 0) {
				className = "OtherFunctionalizingEntity";
				otherFunctionalizingTypes.add(functionalizingEntityTypes[i]);
			} else {
				functionalizingEntityClassNames.add(className);
			}
		}

		List<String> functionClassNames = new ArrayList<String>();
		List<String> otherFunctionTypes = new ArrayList<String>();
		if (functionTypes != null) {
			for (int i = 0; i < functionTypes.length; i++) {
				String className = ClassUtils
						.getShortClassNameFromDisplayName(functionTypes[i]);
				if (className.length() == 0) {
					className = "OtherFunction";
					otherFunctionTypes.add(functionTypes[i]);
				} else {
					functionClassNames.add(className);
				}
			}
		}

		// Publication
		List<PublicationBean> publications = new ArrayList<PublicationBean>();
		PublicationService publicationService = null;
		publicationService = (PublicationService) (request.getSession()
				.getAttribute("publicationService"));

		List<String> publicationIds = publicationService.findPublicationIdsBy(
				title, category, sampleName, researchAreas, keywords, pubMedId,
				digitalObjectId, authors, nanomaterialEntityClassNames
						.toArray(new String[0]), otherNanomaterialEntityTypes
						.toArray(new String[0]),
				functionalizingEntityClassNames.toArray(new String[0]),
				otherFunctionalizingTypes.toArray(new String[0]),
				functionClassNames.toArray(new String[0]), otherFunctionTypes
						.toArray(new String[0]));
		for (String id : publicationIds) {
			PublicationBean pubBean = new PublicationBean(id);
			publications.add(pubBean);
		}
		return publications;
	}

	public Map<String, List<String>> setup(HttpServletRequest request)
			throws Exception {
		InitPublicationSetup.getInstance().setPublicationDropdowns(request);
		InitSampleSetup.getInstance().setLocalSearchDropdowns(request);

		InitPublicationSetup.getInstance().setDefaultResearchAreas(request);
		HttpSession session = request.getSession();
		session.removeAttribute("publicationSearchResults");
		return PublicationUtil.reformatLocalSearchDropdownsInSession(request.getSession());
	}

	public Boolean canUserExecutePrivateDispatch(UserBean user)
			throws SecurityException {
		if (user == null) {
			return false;
		}
		return true;
	}

	private PublicationService setServiceInSession(HttpServletRequest request)
			throws Exception {
		SecurityService securityService = super
				.getSecurityServiceFromSession(request);
		PublicationService publicationService = new PublicationServiceLocalImpl(
				securityService);
		request.getSession().setAttribute("publicationService",
				publicationService);
		return publicationService;
	}
}

