package gov.nih.nci.cananolab.ui.particle;

/**
 * This class allows users to submit functionalizing entity data under sample composition.
 *  
 * @author pansu
 */

/* CVS $Id: FunctionalizingEntityAction.java,v 1.37 2008-05-30 12:16:52 pansu Exp $ */

import gov.nih.nci.cananolab.domain.common.LabFile;
import gov.nih.nci.cananolab.domain.particle.NanoparticleSample;
import gov.nih.nci.cananolab.domain.particle.samplecomposition.functionalization.FunctionalizingEntity;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.ParticleBean;
import gov.nih.nci.cananolab.dto.particle.composition.FunctionBean;
import gov.nih.nci.cananolab.dto.particle.composition.FunctionalizingEntityBean;
import gov.nih.nci.cananolab.service.common.FileService;
import gov.nih.nci.cananolab.service.particle.NanoparticleCompositionService;
import gov.nih.nci.cananolab.service.particle.impl.NanoparticleCompositionServiceLocalImpl;
import gov.nih.nci.cananolab.service.particle.impl.NanoparticleCompositionServiceRemoteImpl;
import gov.nih.nci.cananolab.ui.core.BaseAnnotationAction;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.util.CaNanoLabConstants;
import gov.nih.nci.cananolab.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class FunctionalizingEntityAction extends BaseAnnotationAction {
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		NanoparticleCompositionService compositionService = new NanoparticleCompositionServiceLocalImpl();
		ParticleBean particleBean = setupParticle(theForm, request, "local");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		FunctionalizingEntityBean entityBean = (FunctionalizingEntityBean) theForm
				.get("entity");
		// setup domainFile uri for fileBeans
		String internalUriPath = CaNanoLabConstants.FOLDER_PARTICLE
				+ "/"
				+ particleBean.getDomainParticleSample().getName()
				+ "/"
				+ StringUtils
						.getOneWordLowerCaseFirstLetter("Functionalizing Entity");
		entityBean.setupDomainEntity(InitSetup.getInstance()
				.getDisplayNameToClassNameLookup(
						request.getSession().getServletContext()), user
				.getLoginName(), internalUriPath);
		compositionService.saveFunctionalizingEntity(particleBean
				.getDomainParticleSample(), entityBean.getDomainEntity());
		// save file data to file system and set visibility
		saveFilesToFileSystem(entityBean.getFiles());

		InitCompositionSetup.getInstance()
				.persistFunctionalizingEntityDropdowns(request, entityBean);
		// save to other particles
		FileService service = new FileService();
		NanoparticleSample[] otherSamples = prepareCopy(request, theForm);
		if (otherSamples != null) {
			FunctionalizingEntity copy = entityBean.getDomainCopy();
			for (NanoparticleSample sample : otherSamples) {
				compositionService.saveFunctionalizingEntity(sample, copy);
				if (copy.getLabFileCollection() != null) {
					for (LabFile file : copy.getLabFileCollection()) {
						service.saveCopiedFileAndSetVisibility(file, user,
								particleBean.getDomainParticleSample()
										.getName(), sample.getName());
					}
				}
			}
		}

		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage(
				"message.addFunctionalizingEntity");
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveMessages(request, msgs);
		ActionForward forward = mapping.findForward("success");
		setupDataTree(particleBean, request);
		return forward;
	}

	/**
	 * Set up the input form for adding new nanoparticle entity
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.getSession().removeAttribute("functionalizingEntityForm");
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		setupParticle(theForm, request, "local");
		setLookups(request);
		return mapping.getInputForward();
	}

	private void setLookups(HttpServletRequest request) throws Exception {
		InitNanoparticleSetup.getInstance().setSharedDropdowns(request);
		InitCompositionSetup.getInstance().setFunctionalizingEntityDropdowns(
				request);
	}

	public ActionForward setupUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		setupParticle(theForm, request, "local");
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		String entityId = request.getParameter("dataId");
		NanoparticleCompositionService compService = new NanoparticleCompositionServiceLocalImpl();
		FunctionalizingEntityBean entityBean = compService
				.findFunctionalizingEntityById(entityId);
		compService.retrieveVisibility(entityBean, user);
		entityBean.updateType(InitSetup.getInstance()
				.getClassNameToDisplayNameLookup(session.getServletContext()));
		theForm.set("entity", entityBean);
		setLookups(request);
		// clear copy to otherParticles
		theForm.set("otherParticles", new String[0]);
		return mapping.getInputForward();
	}

	public ActionForward setupView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String location = request.getParameter("location");
		setupParticle(theForm, request, location);
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		String entityId = request.getParameter("dataId");
		NanoparticleCompositionService compService = null;
		if (location.equals("local")) {
			compService = new NanoparticleCompositionServiceLocalImpl();
		} else {
			String serviceUrl = InitSetup.getInstance().getGridServiceUrl(
					request, location);
			compService = new NanoparticleCompositionServiceRemoteImpl(
					serviceUrl);
		}
		FunctionalizingEntityBean entityBean = compService
				.findFunctionalizingEntityById(entityId);
		if (location.equals("local")) {
			compService.retrieveVisibility(entityBean, user);
		}
		entityBean.updateType(InitSetup.getInstance()
				.getClassNameToDisplayNameLookup(session.getServletContext()));
		theForm.set("entity", entityBean);
		return mapping.getInputForward();
	}

	public ActionForward addFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		FunctionalizingEntityBean entity = (FunctionalizingEntityBean) theForm
				.get("entity");
		entity.addFunction();
		InitCompositionSetup.getInstance()
				.persistFunctionalizingEntityDropdowns(request, entity);

		return mapping.getInputForward();
	}

	public ActionForward removeFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String indexStr = request.getParameter("compInd");
		int ind = Integer.parseInt(indexStr);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		FunctionalizingEntityBean entity = (FunctionalizingEntityBean) theForm
				.get("entity");
		entity.removeFunction(ind);
		InitCompositionSetup.getInstance()
				.persistFunctionalizingEntityDropdowns(request, entity);

		return mapping.getInputForward();
	}

	public ActionForward addFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		FunctionalizingEntityBean entity = (FunctionalizingEntityBean) theForm
				.get("entity");
		entity.addFile();
		InitCompositionSetup.getInstance()
				.persistFunctionalizingEntityDropdowns(request, entity);

		return mapping.getInputForward();
	}

	public ActionForward removeFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String indexStr = request.getParameter("compInd");
		int ind = Integer.parseInt(indexStr);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		FunctionalizingEntityBean entity = (FunctionalizingEntityBean) theForm
				.get("entity");
		entity.removeFile(ind);
		InitCompositionSetup.getInstance()
				.persistFunctionalizingEntityDropdowns(request, entity);

		return mapping.getInputForward();
	}

	public ActionForward addTarget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		FunctionalizingEntityBean entity = (FunctionalizingEntityBean) theForm
				.get("entity");

		String funcIndexStr = (String) request.getParameter("compInd");
		int funcIndex = Integer.parseInt(funcIndexStr);
		FunctionBean function = (FunctionBean) entity.getFunctions().get(
				funcIndex);

		function.addTarget();
		InitCompositionSetup.getInstance()
				.persistFunctionalizingEntityDropdowns(request, entity);

		return mapping.getInputForward();
	}

	public ActionForward removeTarget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String funcIndexStr = (String) request.getParameter("compInd");
		int funcIndex = Integer.parseInt(funcIndexStr);

		String targetIndexStr = (String) request.getParameter("childCompInd");
		int targetIndex = Integer.parseInt(targetIndexStr);
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		FunctionalizingEntityBean entity = (FunctionalizingEntityBean) theForm
				.get("entity");
		FunctionBean function = (FunctionBean) entity.getFunctions().get(
				funcIndex);
		function.removeTarget(targetIndex);
		InitCompositionSetup.getInstance()
				.persistFunctionalizingEntityDropdowns(request, entity);

		return mapping.getInputForward();
	}

	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * DynaValidatorForm theForm = (DynaValidatorForm) form;
		 * FunctionalizingEntityBean entity = (FunctionalizingEntityBean)
		 * theForm .get("entity"); // update editable dropdowns HttpSession
		 * session = request.getSession();
		 * InitNanoparticleSetup.getInstance().updateEditableDropdown(session,
		 * composition.getCharacterizationSource(), "characterizationSources");
		 * 
		 * PolymerBean polymer = (PolymerBean) theForm.get("polymer");
		 * updatePolymerEditable(session, polymer);
		 * 
		 * DendrimerBean dendrimer = (DendrimerBean) theForm.get("dendrimer");
		 * updateDendrimerEditable(session, dendrimer);
		 */
		return mapping.findForward("setup");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaValidatorForm theForm = (DynaValidatorForm) form;
		setLookups(request);
		NanoparticleCompositionService compositionService = new NanoparticleCompositionServiceLocalImpl();
		FunctionalizingEntityBean entityBean = (FunctionalizingEntityBean) theForm
				.get("entity");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		ParticleBean particleBean = setupParticle(theForm, request, "local");
		// setup domainFile uri for fileBeans
		String internalUriPath = CaNanoLabConstants.FOLDER_PARTICLE
				+ "/"
				+ particleBean.getDomainParticleSample().getName()
				+ "/"
				+ StringUtils
						.getOneWordLowerCaseFirstLetter("Functionalizing Entity");
		entityBean.setupDomainEntity(InitSetup.getInstance()
				.getDisplayNameToClassNameLookup(
						request.getSession().getServletContext()), user
				.getLoginName(), internalUriPath);
		boolean canDelete = compositionService
				.checkChemicalAssociationBeforeDelete(entityBean);
		ActionMessages msgs = new ActionMessages();
		if (canDelete) {
			compositionService.deleteFunctionalizingEntity(entityBean
					.getDomainEntity());
			ActionMessage msg = new ActionMessage(
					"message.deleteFunctionalizingEntity");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);
			setupDataTree(particleBean, request);
			return mapping.findForward("success");
		} else {
			ActionMessage msg = new ActionMessage(
					"error.deleteFunctionalizingEntityWithChemicalAssociation",
					entityBean.getClassName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveErrors(request, msgs);
			setupDataTree(particleBean, request);
			return mapping.getInputForward();
		}
	}

	protected boolean checkDelete(HttpServletRequest request,
			ActionMessages msgs, String id) throws Exception {
		NanoparticleCompositionService compService = new NanoparticleCompositionServiceLocalImpl();
		FunctionalizingEntityBean entityBean = compService
				.findFunctionalizingEntityById(id);
		if (!compService.checkChemicalAssociationBeforeDelete(entityBean)) {
			ActionMessage msg = new ActionMessage(
					"error.deleteFunctionalizingEntityWithChemicalAssociation",
					entityBean.getClassName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveErrors(request, msgs);
			return false;
		}
		return true;
	}
}
