package gov.nih.nci.calab.ui.core;

import gov.nih.nci.calab.dto.common.UserBean;
import gov.nih.nci.calab.dto.inventory.AliquotBean;
import gov.nih.nci.calab.dto.inventory.ContainerBean;
import gov.nih.nci.calab.dto.inventory.ContainerInfoBean;
import gov.nih.nci.calab.dto.inventory.SampleBean;
import gov.nih.nci.calab.dto.workflow.AssayBean;
import gov.nih.nci.calab.dto.workflow.RunBean;
import gov.nih.nci.calab.exception.InvalidSessionException;
import gov.nih.nci.calab.service.common.LookupService;
import gov.nih.nci.calab.service.security.UserService;
import gov.nih.nci.calab.service.util.CalabComparators;
import gov.nih.nci.calab.service.util.CalabConstants;
import gov.nih.nci.calab.service.util.StringUtils;
import gov.nih.nci.calab.service.workflow.ExecuteWorkflowService;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This class sets up session level or servlet context level variables to be
 * used in various actions during the setup of query forms.
 * 
 * @author pansu
 * 
 */
public class InitSessionSetup {
	private static LookupService lookupService;

	private static UserService userService;

	private InitSessionSetup() throws Exception {
		lookupService = new LookupService();
		userService = new UserService(CalabConstants.CSM_APP_NAME);
	}

	public static InitSessionSetup getInstance() throws Exception {
		return new InitSessionSetup();
	}

	public void setCurrentRun(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String runId = (String) request.getParameter("runId");

		if (runId == null && session.getAttribute("currentRun") == null) {
			throw new InvalidSessionException(
					"The session containing a run doesn't exists.");
		} else if (runId == null && session.getAttribute("currentRun") != null) {
			RunBean currentRun = (RunBean) session.getAttribute("currentRun");
			runId = currentRun.getId();
		}
		if (session.getAttribute("currentRun") == null
				|| session.getAttribute("newRunCreated") != null) {

			ExecuteWorkflowService executeWorkflowService = new ExecuteWorkflowService();
			RunBean runBean = executeWorkflowService.getCurrentRun(runId);
			session.setAttribute("currentRun", runBean);
		}
		session.removeAttribute("newRunCreated");
	}

	public void clearRunSession(HttpSession session) {
		session.removeAttribute("currentRun");
	}

	public void setAllUsers(HttpSession session) throws Exception {
		if ((session.getAttribute("newUserCreated") != null)
				|| (session.getServletContext().getAttribute("allUsers") == null)) {
			List allUsers = userService.getAllUsers();
			session.getServletContext().setAttribute("allUsers", allUsers);
		}
		session.removeAttribute("newUserCreated");
	}

	public void setSampleSourceUnmaskedAliquots(HttpSession session)
			throws Exception {
		// set map between sample source and samples that have unmasked aliquots
		if (session.getAttribute("sampleSourceSamplesWithUnmaskedAliquots") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			Map<String, SortedSet<SampleBean>> sampleSourceSamples = lookupService
					.getSampleSourceSamplesWithUnmaskedAliquots();
			session.setAttribute("sampleSourceSamplesWithUnmaskedAliquots",
					sampleSourceSamples);
			List<String> sources = new ArrayList<String>(sampleSourceSamples
					.keySet());
			session.setAttribute("allSampleSourcesWithUnmaskedAliquots",
					sources);
		}
		setAllSampleUnmaskedAliquots(session);
		session.removeAttribute("newAliquotCreated");
	}

	public void clearSampleSourcesWithUnmaskedAliquotsSession(
			HttpSession session) {
		session.removeAttribute("allSampleSourcesWithUnmaskedAliquots");
	}

	public void setAllSampleUnmaskedAliquots(HttpSession session)
			throws Exception {
		// set map between samples and unmasked aliquots
		if (session.getAttribute("allUnmaskedSampleAliquots") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			Map<String, SortedSet<AliquotBean>> sampleAliquots = lookupService
					.getUnmaskedSampleAliquots();
			List<String> sampleNames = new ArrayList<String>(sampleAliquots
					.keySet());
			Collections.sort(sampleNames,
					new CalabComparators.SortableNameComparator());
			session.setAttribute("allUnmaskedSampleAliquots", sampleAliquots);
			session.setAttribute("allSampleNamesWithAliquots", sampleNames);
		}
		session.removeAttribute("newAliquotCreated");
	}

	public void clearSampleUnmaskedAliquotsSession(HttpSession session) {
		session.removeAttribute("allUnmaskedSampleAliquots");
		session.removeAttribute("allSampleNamesWithAliquots");
	}

	public void setAllAssayTypeAssays(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allAssayTypes") == null) {
			List assayTypes = lookupService.getAllAssayTypes();
			session.getServletContext().setAttribute("allAssayTypes",
					assayTypes);
		}

		if (session.getServletContext().getAttribute("allAssayTypeAssays") == null) {
			Map<String, SortedSet<AssayBean>> assayTypeAssays = lookupService
					.getAllAssayTypeAssays();
			List<String> assayTypes = new ArrayList<String>(assayTypeAssays
					.keySet());
			session.getServletContext().setAttribute("allAssayTypeAssays",
					assayTypeAssays);

			session.getServletContext().setAttribute("allAvailableAssayTypes",
					assayTypes);
		}
	}

	public void setAllSampleSources(HttpSession session) throws Exception {

		if (session.getAttribute("allSampleSources") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List sampleSources = lookupService.getAllSampleSources();
			session.setAttribute("allSampleSources", sampleSources);
		}
		// clear the new sample created flag
		session.removeAttribute("newSampleCreated");
	}

	public void clearSampleSourcesSession(HttpSession session) {
		session.removeAttribute("allSampleSources");
	}

	public void setAllSampleContainerTypes(HttpSession session)
			throws Exception {
		if (session.getAttribute("allSampleContainerTypes") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List containerTypes = lookupService.getAllSampleContainerTypes();
			session.setAttribute("allSampleContainerTypes", containerTypes);
		}
		// clear the new sample created flag
		session.removeAttribute("newSampleCreated");
	}

	public void clearSampleContainerTypesSession(HttpSession session) {
		session.removeAttribute("allSampleContainerTypes");
	}

	public void setAllSampleTypes(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allSampleTypes") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List sampleTypes = lookupService.getAllSampleTypes();
			session.getServletContext().setAttribute("allSampleTypes",
					sampleTypes);
		}
		// clear the new sample created flag
		session.removeAttribute("newSampleCreated");
	}

	public void clearSampleTypesSession(HttpSession session) {
		session.removeAttribute("allSampleTypes");
	}

	public void setAllSampleSOPs(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allSampleSOPs") == null) {
			List sampleSOPs = lookupService.getAllSampleSOPs();
			session.getServletContext().setAttribute("allSampleSOPs",
					sampleSOPs);
		}
	}

	public void setAllSampleContainerInfo(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("sampleContainerInfo") == null) {
			ContainerInfoBean containerInfo = lookupService
					.getSampleContainerInfo();
			session.getServletContext().setAttribute("sampleContainerInfo",
					containerInfo);
		}
	}

	public void setCurrentUser(HttpSession session) throws Exception {

		// get user and date information
		String creator = "";
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");
			creator = user.getLoginName();
		}
		String creationDate = StringUtils.convertDateToString(new Date(),
				CalabConstants.DATE_FORMAT);
		session.setAttribute("creator", creator);
		session.setAttribute("creationDate", creationDate);
	}

	public void setAllAliquotContainerTypes(HttpSession session)
			throws Exception {
		if (session.getAttribute("allAliquotContainerTypes") == null
				|| session.getAttribute("newAliquotCreated") != null) {
			List containerTypes = lookupService.getAllAliquotContainerTypes();
			session.setAttribute("allAliquotContainerTypes", containerTypes);
		}
	}

	public void clearAliquotContainerTypesSession(HttpSession session) {
		session.removeAttribute("allAliquotContainerTypes");
	}

	public void setAllAliquotContainerInfo(HttpSession session)
			throws Exception {
		if (session.getServletContext().getAttribute("aliquotContainerInfo") == null) {
			ContainerInfoBean containerInfo = lookupService
					.getAliquotContainerInfo();
			session.getServletContext().setAttribute("aliquotContainerInfo",
					containerInfo);
		}
	}

	public void setAllAliquotCreateMethods(HttpSession session)
			throws Exception {
		if (session.getServletContext().getAttribute("aliquotCreateMethods") == null) {
			List methods = lookupService.getAliquotCreateMethods();
			session.getServletContext().setAttribute("aliquotCreateMethods",
					methods);
		}
	}

	public void setAllSampleContainers(HttpSession session) throws Exception {
		if (session.getAttribute("allSampleContainers") == null
				|| session.getAttribute("newSampleCreated") != null) {
			Map<String, SortedSet<ContainerBean>> sampleContainers = lookupService
					.getAllSampleContainers();
			List<String> sampleNames = new ArrayList<String>(sampleContainers
					.keySet());
			Collections.sort(sampleNames,
					new CalabComparators.SortableNameComparator());

			session.setAttribute("allSampleContainers", sampleContainers);
			session.setAttribute("allSampleNames", sampleNames);
		}
		session.removeAttribute("newSampleCreated");
	}

	public void clearSampleContainersSession(HttpSession session) {
		session.removeAttribute("allSampleContainers");
		session.removeAttribute("allSampleNames");
	}

	public void setAllSourceSampleIds(HttpSession session) throws Exception {
		if (session.getAttribute("allSourceSampleIds") == null
				|| session.getAttribute("newSampleCreated") != null) {
			List sourceSampleIds = lookupService.getAllSourceSampleIds();
			session.setAttribute("allSourceSampleIds", sourceSampleIds);
		}
		// clear the new sample created flag
		session.removeAttribute("newSampleCreated");
	}

	public void clearSourceSampleIdsSession(HttpSession session) {
		session.removeAttribute("allSourceSampleIds");
	}

	public void clearWorkflowSession(HttpSession session) {
		clearRunSession(session);
		clearSampleSourcesWithUnmaskedAliquotsSession(session);
		clearSampleUnmaskedAliquotsSession(session);
		session.removeAttribute("httpFileUploadSessionData");
	}

	public void clearSearchSession(HttpSession session) {
		clearSampleTypesSession(session);
		clearSampleContainerTypesSession(session);
		clearAliquotContainerTypesSession(session);
		clearSourceSampleIdsSession(session);
		clearSampleSourcesSession(session);
		session.removeAttribute("aliquots");
		session.removeAttribute("sampleContainers");
	}

	public void clearInventorySession(HttpSession session) {
		clearSampleTypesSession(session);
		clearSampleContainersSession(session);
		clearSampleContainerTypesSession(session);
		clearSampleUnmaskedAliquotsSession(session);
		clearAliquotContainerTypesSession(session);
		session.removeAttribute("createSampleForm");
		session.removeAttribute("createAliquotForm");
		session.removeAttribute("aliquotMatrix");
	}

	public static LookupService getLookupService() {
		return lookupService;
	}

	public static UserService getUserService() {
		return userService;
	}

	public boolean canUserExecuteClass(HttpSession session, Class classObj)
			throws CSException {
		UserBean user = (UserBean) session.getAttribute("user");
		// assume the part of the package name containing the function domain
		// is the same as the protection element defined in CSM
		String[] nameStrs = classObj.getName().split("\\.");
		String domain = nameStrs[nameStrs.length - 2];
		return userService.checkExecutePermission(user, domain);
	}

	public void setAllParticleTypeParticles(HttpSession session)
			throws Exception {
		if (session.getAttribute("allParticleTypeParticles") == null
				|| session.getAttribute("newParticleCreated") != null) {
			Map<String, SortedSet<String>> particleTypeParticles = lookupService
					.getAllParticleTypeParticles();
			List<String> particleTypes = new ArrayList<String>(
					particleTypeParticles.keySet());
			Collections.sort(particleTypes);

			session.setAttribute("allParticleTypeParticles",
					particleTypeParticles);
			session.setAttribute("allParticleTypes", particleTypes);
		}
		session.removeAttribute("newParticleCreated");
	}

	public void setAllVisibilityGroups(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allVisibilityGroups") == null) {
			List<String> groupNames = userService.getAllVisibilityGroups();
			session.getServletContext().setAttribute("allVisibilityGroups",
					groupNames);
		}
	}

	public void setParticleMenu(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allParticleFunctions") == null) {
			String[] functions = lookupService.getAllParticleFunctions();
			session.getServletContext().setAttribute("allParticleFunctions",
					functions);
		}
		if (session.getServletContext().getAttribute(
				"allParticleCharacterizationTypeCharacterzations") == null) {
			Map<String, String[]> charTypeChars = lookupService
					.getAllParticleCharacterizationTypeCharacterizations();
			String[] charTypes = lookupService.getAllCharacterizationTypes();
			session.getServletContext().setAttribute(
					"allParticleCharacterizationTypes", charTypes);
			session.getServletContext().setAttribute(
					"allParticleCharacterizationTypeCharacterizations",
					charTypeChars);
		}
	}

	public void setAllDendrimerCores(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allDendrimerCores") == null) {
			String[] dendrimerCores = lookupService.getAllDendrimerCores();
			session.getServletContext().setAttribute("allDendrimerCores",
					dendrimerCores);
		}
	}
	
	public void setAllDendrimerSurfaceGroupNames(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allDendrimerSurfaceGroupNames") == null) {
			String[] surfaceGroupNames = lookupService.getAllDendrimerSurfaceGroupNames();
			session.getServletContext().setAttribute("allDendrimerSurfaceGroupNames",
					surfaceGroupNames);
		}
	}
	
	public void setAllMetalCompositions(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allMetalCompositions") == null) {
			String[] compositions = lookupService.getAllMetalCompositions();
			session.getServletContext().setAttribute("allMetalCompositions",
					compositions);
		}
	}

	public void setAllPolymerInitiators(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allPolymerInitiators") == null) {
			String[] initiators = lookupService.getAllPolymerInitiators();
			session.getServletContext().setAttribute("allPolymerInitiators",
					initiators);
		}
	}
	
	public void setAllParticleFunctionTypes(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allParticleFunctionTypes") == null) {
			String[] functions = lookupService.getAllParticleFunctions();
			session.getServletContext().setAttribute("allParticleFunctionTypes",
					functions);
		}
	}
	
	public void setAllParticleSources(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allParticleSources") == null) {
			List<String> sources = lookupService.getAllParticleSources();
			session.getServletContext().setAttribute("allParticleSources",
					sources);
		}
	}
	
	public void setAllParticleCharacterizationTypes(HttpSession session) throws Exception {
		if (session.getServletContext().getAttribute("allCharacterizationTypes") == null) {
			String[] charTypes = lookupService.getAllCharacterizationTypes();
			session.getServletContext().setAttribute("allCharacterizationTypes",
					charTypes);
		}
	}
}
