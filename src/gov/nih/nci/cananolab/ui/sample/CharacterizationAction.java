package gov.nih.nci.cananolab.ui.sample;

import gov.nih.nci.cananolab.dto.common.ExperimentConfigBean;
import gov.nih.nci.cananolab.dto.common.FileBean;
import gov.nih.nci.cananolab.dto.common.FindingBean;
import gov.nih.nci.cananolab.dto.common.Row;
import gov.nih.nci.cananolab.dto.common.TableCell;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.SampleBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationSummaryViewBean;
import gov.nih.nci.cananolab.service.sample.CharacterizationService;
import gov.nih.nci.cananolab.service.sample.helper.CharacterizationServiceHelper;
import gov.nih.nci.cananolab.service.sample.impl.CharacterizationServiceLocalImpl;
import gov.nih.nci.cananolab.service.sample.impl.CharacterizationServiceRemoteImpl;
import gov.nih.nci.cananolab.ui.core.BaseAnnotationAction;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.ui.protocol.InitProtocolSetup;
import gov.nih.nci.cananolab.util.ClassUtils;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.ExportUtils;
import gov.nih.nci.cananolab.util.SampleConstants;
import gov.nih.nci.cananolab.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

/**
 * Base action for characterization actions
 * 
 * @author pansu
 */
public class CharacterizationAction extends BaseAnnotationAction {

	// Partial URL for downloading characterization report file.
	public static final String DOWNLOAD_URL = "?dispatch=download&location=";

	/**
	 * Add or update the data to database
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean charBean = (CharacterizationBean) theForm
				.get("achar");
		InitCharacterizationSetup.getInstance()
				.persistCharacterizationDropdowns(request, charBean);

		saveCharacterization(request, theForm, charBean);

		ActionMessages msgs = new ActionMessages();
		// validate number by javascript filterFloatingNumber
		// validateNumber(request, charBean, msgs);
		ActionMessage msg = new ActionMessage("message.addCharacterization",
				charBean.getCharacterizationName());
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveMessages(request, msgs);
		// to preselect the same characterization type after returning to the
		// summary page
		List<String> allCharacterizationTypes = InitCharacterizationSetup
				.getInstance().getCharacterizationTypes(request);
		int ind = allCharacterizationTypes.indexOf(charBean
				.getCharacterizationType()) + 1;
		request.getSession().setAttribute(
				"onloadJavascript",
				"showSummary('" + ind + "', " + allCharacterizationTypes.size()
						+ ")");
		return summaryEdit(mapping, form, request, response);
	}

	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean charBean = (CharacterizationBean) theForm
				.get("achar");
		InitCharacterizationSetup.getInstance()
				.persistCharacterizationDropdowns(request, charBean);
		this.checkOpenForms(charBean, request);
		
		return mapping.findForward("inputForm");
	}

	/**
	 * Set up the input form for adding new characterization
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setupNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		setupInputForm(request, theForm);
		// reset characterizationBean
		CharacterizationBean charBean = new CharacterizationBean();
		theForm.set("achar", charBean);
		String charType = request.getParameter("charType");
		if (!StringUtils.isEmpty(charType)) {
			charBean.setCharacterizationType(charType);
			SortedSet<String> charNames = InitCharacterizationSetup
					.getInstance().getCharNamesByCharType(request,
							charBean.getCharacterizationType());
			request.getSession().setAttribute("charTypeChars", charNames);
		}
		this.checkOpenForms(charBean, request);
		return mapping.findForward("inputForm");
	}

	/**
	 * Set up drop-downs need for the input form
	 * 
	 * @param request
	 * @param theForm
	 * @throws Exception
	 */
	private void setupInputForm(HttpServletRequest request,
			DynaValidatorForm theForm) throws Exception {
		String sampleId = request.getParameter("sampleId");
		String charType = request.getParameter("charType");
		InitSampleSetup.getInstance().setSharedDropdowns(request);
		InitCharacterizationSetup.getInstance().setCharactierizationDropDowns(
				request, sampleId);
		InitExperimentConfigSetup.getInstance().setExperimentConfigDropDowns(
				request);
		if (!StringUtils.isEmpty(charType))
			InitProtocolSetup.getInstance().getProtocolsByChar(request,
					charType);
		InitCharacterizationSetup.getInstance().setCharacterizationDropdowns(
				request);
		// String detailPage = setupDetailPage(charBean);
		// request.getSession().setAttribute("characterizationDetailPage",
		// detailPage);

		// set up other samples with the same primary point of contact
		InitSampleSetup.getInstance().getOtherSampleNames(request, sampleId);
	}

	/**
	 * Set up the input form for editing existing characterization
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setupUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String charId = request.getParameter("charId");
		if (charId == null) {
			charId = (String) request.getAttribute("charId");
		}
		CharacterizationService charService = new CharacterizationServiceLocalImpl();
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		CharacterizationBean charBean = charService.findCharacterizationById(
				charId, user);
		// setup dropdown for existing characterization
		InitCharacterizationSetup.getInstance().getCharNamesByCharType(request,
				charBean.getCharacterizationType());
		InitCharacterizationSetup.getInstance().getAssayTypesByCharName(
				request, charBean.getCharacterizationName());
		InitCharacterizationSetup.getInstance().getDatumNamesByCharName(
				request, charBean.getCharacterizationType(),
				charBean.getCharacterizationName(), charBean.getAssayType());

		request.setAttribute("achar", charBean);
		theForm.set("achar", charBean);
		setupInputForm(request, theForm);
		String detailPage = null;
		if (charBean.isWithProperties()) {
			detailPage = setupDetailPage(charBean);
		}
		request.setAttribute("characterizationDetailPage", detailPage);
		this.checkOpenForms(charBean, request);
		return mapping.findForward("inputForm");
	}

	private String setupDetailPage(CharacterizationBean charBean) {
		String includePage = null;
		if (charBean.getClassName().equals("PhysicalState")
				|| charBean.getClassName().equals("Shape")
				|| charBean.getClassName().equals("Solubility")
				|| charBean.getClassName().equals("Surface")) {
			includePage = "physical/body" + charBean.getClassName()
					+ "Info.jsp";
		} else if (charBean.getClassName().equals("Cytotoxicity")
				|| charBean.getClassName().equals("EnzymeInduction")
				|| charBean.getClassName().equals("Transfection")) {
			includePage = "invitro/body" + charBean.getClassName() + "Info.jsp";
		}
		return includePage;
	}

	private void setupDomainChar(HttpServletRequest request,
			DynaValidatorForm theForm, CharacterizationBean charBean)
			throws Exception {
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if (charBean.getClassName() == null
				|| charBean.getClassName().length() == 0) {
			String className = ClassUtils
					.getShortClassNameFromDisplayName(charBean
							.getCharacterizationName());
			charBean.setClassName(className);
		}
		charBean.setupDomain(user.getLoginName());
	}

	private void saveCharacterization(HttpServletRequest request,
			DynaValidatorForm theForm, CharacterizationBean charBean)
			throws Exception {

		SampleBean sampleBean = setupSample(theForm, request,
				Constants.LOCAL_SITE);
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		setupDomainChar(request, theForm, charBean);
		CharacterizationService charService = new CharacterizationServiceLocalImpl();
		charService.saveCharacterization(sampleBean, charBean, user);

		// save to other samples
		SampleBean[] otherSampleBeans = prepareCopy(request, theForm,
				sampleBean);
		if (otherSampleBeans != null) {
			Boolean copyData = (Boolean) theForm.get("copyData");
			charService.copyAndSaveCharacterization(charBean, sampleBean,
					otherSampleBeans, copyData, user);
		}
		sampleBean = setupSample(theForm, request, Constants.LOCAL_SITE);
		request.setAttribute("sampleId", sampleBean.getDomain().getId());
		request.setAttribute(Constants.LOCATION, Constants.LOCAL_SITE);
	}

	private void deleteCharacterization(HttpServletRequest request,
			DynaValidatorForm theForm, CharacterizationBean charBean,
			String createdBy) throws Exception {
		charBean.setupDomain(createdBy);
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		CharacterizationService charService = new CharacterizationServiceLocalImpl();
		charService.deleteCharacterization(charBean.getDomainChar(), user);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean charBean = (CharacterizationBean) theForm
				.get("achar");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		deleteCharacterization(request, theForm, charBean, user.getLoginName());
		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage("message.deleteCharacterization");
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveMessages(request, msgs);
		ActionForward forward = mapping.findForward("success");
		return forward;
	}

	/**
	 * summaryEdit() handles Edit request for Characterization Summary view.
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
		// Prepare data.
		this.prepareSummary(mapping, form, request, response);

		// "actionName" is for constructing the Print/Export URL.
		request.setAttribute("actionName", request.getRequestURL().toString());

		return mapping.findForward("summaryEdit");
	}

	/**
	 * summaryView() handles View request for Characterization Summary report.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 *             if error occurred.
	 */
	public ActionForward summaryView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Prepare data.
		this.prepareSummary(mapping, form, request, response);
		this.prepareCharacterizationTypes(mapping, form, request, response);

		// "actionName" is for constructing the Print/Export URL.
		request.setAttribute("actionName", request.getRequestURL().toString());

		return mapping.findForward("summaryView");
	}

	/**
	 * summaryPrint() handles Print request for Characterization Summary report.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 *             if error occurred.
	 */
	public ActionForward summaryPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Prepare data.
		this.prepareSummary(mapping, form, request, response);
		this.prepareCharacterizationTypes(mapping, form, request, response);

		// Filter out un-selected types.
		String type = request.getParameter("type");
		if (!StringUtils.isEmpty(type)) {
			List<String> characterizationTypes = (List<String>) request
					.getAttribute("characterizationTypes");
			characterizationTypes.clear();
			characterizationTypes.add(type);
		}
		return mapping.findForward("summaryPrintView");
	}

	/**
	 * Shared function for summaryView(), summaryPrint() and summaryEdit().
	 * Prepare CharacterizationBean based on SampleId.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 *             if error occurred.
	 */
	private void prepareSummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String sampleId = theForm.getString(SampleConstants.SAMPLE_ID);
		String location = theForm.getString(Constants.LOCATION);
		setupSample(theForm, request, location);
		CharacterizationService service = null;
		if (Constants.LOCAL_SITE.equals(location)) {
			service = new CharacterizationServiceLocalImpl();
		} else {
			String serviceUrl = InitSetup.getInstance().getGridServiceUrl(
					request, location);
			service = new CharacterizationServiceRemoteImpl(serviceUrl);
		}
		List<CharacterizationBean> charBeans = service
				.findCharacterizationsBySampleId(sampleId, user);
		CharacterizationSummaryViewBean summaryView = new CharacterizationSummaryViewBean(
				charBeans);
		request.setAttribute("characterizationSummaryView", summaryView);
		InitCharacterizationSetup.getInstance().setCharactierizationDropDowns(
				request, sampleId);
		if (request.getParameter("clearTab") != null
				&& request.getParameter("clearTab").equals("true")) {
			request.getSession().removeAttribute("onloadJavascript");
		}
	}

	/**
	 * Shared function for summaryView() and summaryPrint(). Keep submitted
	 * characterization types in the correct display order. Should be called
	 * after calling prepareSummary().
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 *             if error occurred.
	 */
	private void prepareCharacterizationTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CharacterizationSummaryViewBean summaryView = (CharacterizationSummaryViewBean) request
				.getAttribute("characterizationSummaryView");

		// Keep submitted characterization types in the correct display order
		List<String> allCharacterizationTypes = new ArrayList<String>(
				(List<? extends String>) request.getSession().getAttribute(
						"characterizationTypes"));
		List<String> characterizationTypes = new ArrayList<String>();
		for (String charType : allCharacterizationTypes) {
			if (summaryView.getCharacterizationTypes().contains(charType)
					&& !characterizationTypes.contains(charType)) {
				characterizationTypes.add(charType);
			}
		}
		request.setAttribute("characterizationTypes", characterizationTypes);
	}

	/**
	 * Export Characterization Summary report.
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
		// Prepare data.
		this.prepareSummary(mapping, form, request, response);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String location = theForm.getString(Constants.LOCATION);
		SampleBean sampleBean = setupSample(theForm, request, location);
		CharacterizationSummaryViewBean charSummaryBean = (CharacterizationSummaryViewBean) request
				.getAttribute("characterizationSummaryView");
		Map<String, SortedSet<CharacterizationBean>> charBeanMap = charSummaryBean
				.getType2Characterizations();
		SortedSet<CharacterizationBean> charBeans = null;

		// Filter out un-selected types.
		String type = request.getParameter("type");
		if (!StringUtils.isEmpty(type)) {
			charBeans = charBeanMap.get(type);
			if (charBeans != null) {
				charBeanMap.clear();
				charBeanMap.put(type, charBeans);
			}
		}

		String fileName = ExportUtils.getExportFileName(sampleBean.getDomain()
				.getName(), "CharacterizationSummaryView", type);
		ExportUtils.prepareReponseForExcell(response, fileName);

		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURL().toString());
		sb.append(DOWNLOAD_URL);
		sb.append(request.getParameter(location));

		CharacterizationServiceHelper.exportSummary(charSummaryBean, sb
				.toString(), response.getOutputStream());

		return null;
	}

	public ActionForward saveExperimentConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		ExperimentConfigBean configBean = achar.getTheExperimentConfig();
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		configBean.setupDomain(user.getLoginName());
		CharacterizationService service = new CharacterizationServiceLocalImpl();
		service.saveExperimentConfig(configBean, user);
		achar.addExperimentConfig(configBean);
		InitCharacterizationSetup.getInstance()
				.persistCharacterizationDropdowns(request, achar);
		InitExperimentConfigSetup.getInstance()
				.persistExperimentConfigDropdowns(request, configBean);
		// also save characterization
		saveCharacterization(request, theForm, achar);
		this.checkOpenForms(achar, request);
		return mapping.findForward("inputForm");
	}

	public ActionForward getFinding(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String theFindingId = request.getParameter("findingId");
		CharacterizationService service = new CharacterizationServiceLocalImpl();
		FindingBean findingBean = service.findFindingById(theFindingId, user);
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		achar.setTheFinding(findingBean);
		request.setAttribute("anchor", "submitFinding");
		this.checkOpenForms(achar, request);
		return mapping.findForward("inputForm");
	}

	public ActionForward resetFinding(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		FindingBean theFinding = new FindingBean();
		// theFinding.setNumberOfColumns(1);
		// theFinding.setNumberOfRows(1);
		// theFinding.updateMatrix(theFinding.getNumberOfColumns(), theFinding
		// .getNumberOfRows());
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		achar.setTheFinding(theFinding);
		request.setAttribute("anchor", "submitFinding");
		this.checkOpenForms(achar, request);
		return mapping.findForward("inputForm");
	}

	public ActionForward saveFinding(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		FindingBean findingBean = achar.getTheFinding();
		if (this.validateCellNotEmpty(findingBean)) {
			String theFindingId = (String) request.getAttribute("theFindingId");
			if (!StringUtils.isEmpty(theFindingId)) {
				findingBean.getDomain().setId(new Long(theFindingId));
			}
			UserBean user = (UserBean) request.getSession()
					.getAttribute("user");
			findingBean.setupDomain(user.getLoginName());
			CharacterizationService service = new CharacterizationServiceLocalImpl();
			service.saveFinding(findingBean, user);
			achar.addFinding(findingBean);
			InitCharacterizationSetup.getInstance()
					.persistCharacterizationDropdowns(request, achar);

			// also save characterization
			saveCharacterization(request, theForm, achar);
			request.setAttribute("anchor", "result");
			this.checkOpenForms(achar, request);
			// return to setupUpdate to retrieve the data matrix in the correct
			// form from database
			// after saving to database.
			request.setAttribute("charId", achar.getDomainChar().getId()
					.toString());
			return setupUpdate(mapping, form, request, response);
		} else {
			ActionMessages messages = new ActionMessages();
			ActionMessage msg = new ActionMessage("achar.theFinding.emptyCell");
			messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveErrors(request, messages);
			return mapping.getInputForward();
		}
	}

	/**
	 * Return true if every finding cell has value entered, false otherwise.
	 * 
	 * @param findingBean
	 * @return true if every finding cell has value entered, false otherwise.
	 */
	private boolean validateCellNotEmpty(FindingBean findingBean) {
		if (findingBean != null) {
			int rowNum = findingBean.getNumberOfRows();
			List<Row> rows = findingBean.getRows();
			if (rows == null || rows.size() != rowNum) {
				return false;
			}
			for (Row row : rows) {
				List<TableCell> cells = row.getCells();
				if (cells != null) {
					for (TableCell cell : cells) {
						if (StringUtils.isEmpty(cell.getValue())) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public ActionForward addFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		FindingBean findingBean = achar.getTheFinding();
		FileBean theFile = findingBean.getTheFile();
		int theFileIndex = findingBean.getTheFileIndex();
		// create a new copy before adding to finding
		FileBean newFile = theFile.copy();
		SampleBean sampleBean = setupSample(theForm, request,
				Constants.LOCAL_SITE);
		// setup domainFile uri for fileBeans
		String internalUriPath = Constants.FOLDER_PARTICLE
				+ "/"
				+ sampleBean.getDomain().getName()
				+ "/"
				+ StringUtils.getOneWordLowerCaseFirstLetter(achar
						.getCharacterizationName());
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		newFile.setupDomainFile(internalUriPath, user.getLoginName(), 0);
		findingBean.addFile(newFile, theFileIndex);
		request.setAttribute("anchor", "submitFinding");
		this.checkOpenForms(achar, request);
		
		/**
		 * Set marker in form for skipping finding matrix validation .
		 */
		theForm.set("fileSupplied", Boolean.TRUE.toString());
		
		return mapping.findForward("inputForm");
	}

	public ActionForward removeFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		FindingBean findingBean = achar.getTheFinding();
		int theFileIndex = findingBean.getTheFileIndex();
		findingBean.removeFile(theFileIndex);
		findingBean.setTheFile(new FileBean());
		request.setAttribute("anchor", "submitFinding");
		this.checkOpenForms(achar, request);
		return mapping.findForward("inputForm");
	}

	public ActionForward drawMatrix(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		request.setAttribute("anchor", "result");
		FindingBean findingBean = achar.getTheFinding();

		/**
		 * Set number of column/row in form for validation.
		 */
		theForm.set("numberOfColumns", Integer.valueOf(findingBean
				.getNumberOfColumns()));
		theForm.set("numberOfRows", Integer.valueOf(findingBean
				.getNumberOfRows()));

		if (request.getParameter("removeColumn") != null) {
			int columnToRemove = Integer.parseInt(request
					.getParameter("removeColumn"));
			findingBean.removeColumn(columnToRemove);
			return mapping.findForward("inputForm");
		} else if (request.getParameter("removeRow") != null) {
			int rowToRemove = Integer.parseInt(request
					.getParameter("removeRow"));
			findingBean.removeRow(rowToRemove);
			this.checkOpenForms(achar, request);
			return mapping.findForward("inputForm");
		}
		int existingNumberOfColumns = findingBean.getColumnHeaders().size();
		int existingNumberOfRows = findingBean.getRows().size();
		if (existingNumberOfColumns > findingBean.getNumberOfColumns()) {
			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage(
					"message.addCharacterization.removeMatrixColumn");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);
			findingBean.setNumberOfColumns(existingNumberOfColumns);
			return mapping.getInputForward();
		}
		if (existingNumberOfRows > findingBean.getNumberOfRows()) {
			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage(
					"message.addCharacterization.removeMatrixRow");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);
			findingBean.setNumberOfRows(existingNumberOfRows);
			return mapping.getInputForward();
		}
		findingBean.updateMatrix(findingBean.getNumberOfColumns(), findingBean
				.getNumberOfRows());
		request.setAttribute("anchor", "submitFinding");
		this.checkOpenForms(achar, request);
		return mapping.findForward("inputForm");
	}

	public ActionForward deleteFinding(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		FindingBean dataSetBean = achar.getTheFinding();
		CharacterizationService service = new CharacterizationServiceLocalImpl();
		service.deleteFinding(dataSetBean.getDomain(), user);
		achar.removeFinding(dataSetBean);
		InitCharacterizationSetup.getInstance()
				.persistCharacterizationDropdowns(request, achar);
		request.setAttribute("anchor", "result");
		this.checkOpenForms(achar, request);
		return mapping.findForward("inputForm");
	}

	public ActionForward deleteExperimentConfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		CharacterizationBean achar = (CharacterizationBean) theForm
				.get("achar");
		ExperimentConfigBean configBean = achar.getTheExperimentConfig();
		CharacterizationService service = new CharacterizationServiceLocalImpl();
		service.deleteExperimentConfig(configBean.getDomain(), user);
		achar.removeExperimentConfig(configBean);
		InitCharacterizationSetup.getInstance()
				.persistCharacterizationDropdowns(request, achar);
		InitExperimentConfigSetup.getInstance()
				.persistExperimentConfigDropdowns(request, configBean);
		// also save characterization
		saveCharacterization(request, theForm, achar);
		this.checkOpenForms(achar, request);
		return mapping.findForward("inputForm");
	}

	private void checkOpenForms(CharacterizationBean achar,
			HttpServletRequest request) {
		String dispatch = request.getParameter("dispatch");
		String browserDispatch = getBrowserDispatch(request);
		HttpSession session = request.getSession();
		Boolean openFile = false, openExperimentConfig = false, openFinding = false;
		if (dispatch.equals("input") && browserDispatch.equals("addFile")) {
			openFile = true;
		}
		session.setAttribute("openFile", openFile);
		if (dispatch.equals("input")
				&& browserDispatch.equals("saveExperimentConfig")
				|| (!StringUtils.isEmpty(achar.getTheExperimentConfig()
						.getTechniqueDisplayName()) && !dispatch
						.equals("saveExperimentConfig"))) {
			openExperimentConfig = true;
		}
		session.setAttribute("openExperimentConfig", openExperimentConfig);
		if (dispatch.equals("input")
				&& (browserDispatch.equals("saveFinding") || browserDispatch
						.equals("addFile")) || dispatch.equals("addFile")
				|| dispatch.equals("removeFile")
				|| dispatch.equals("drawMatrix")
				|| dispatch.equals("getFinding")
				|| dispatch.equals("resetFinding")
				|| !achar.getTheFinding().getFiles().isEmpty()) {
			openFinding = true;
		}
		session.setAttribute("openFinding", openFinding);
		
		/**
		 * If user entered customized Char Type/Name then failed validation,
		 * we should show and select the entered value when redirected.
		 */
		String currentCharType = achar.getCharacterizationType();
		if (!StringUtils.isEmpty(currentCharType)) {
			Collection<String> charTypes = 
				(Collection<String>) request.getSession().getAttribute("characterizationTypes");
			if (charTypes != null && !charTypes.contains(currentCharType)) {
				request.setAttribute("currentCharType", currentCharType);
			}
		}
		String currentCharName = achar.getCharacterizationName();
		if (!StringUtils.isEmpty(currentCharName)) {
			Collection<String> charNames = 
				(Collection<String>) request.getSession().getAttribute("charTypeChars");
			if (charNames != null && !charNames.contains(currentCharName)) {
				request.setAttribute("currentCharName", currentCharName);
			}
		}
	}
}
