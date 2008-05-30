package gov.nih.nci.cananolab.ui.report;

import gov.nih.nci.cananolab.dto.common.ReportBean;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.exception.CaNanoLabSecurityException;
import gov.nih.nci.cananolab.service.common.FileService;
import gov.nih.nci.cananolab.service.common.impl.FileServiceLocalImpl;
import gov.nih.nci.cananolab.service.report.ReportService;
import gov.nih.nci.cananolab.service.report.impl.ReportServiceLocalImpl;
import gov.nih.nci.cananolab.service.report.impl.ReportServiceRemoteImpl;
import gov.nih.nci.cananolab.ui.core.BaseAnnotationAction;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.ui.particle.InitCompositionSetup;

import java.util.ArrayList;
import java.util.List;

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
 * This class searches nanoparticle report based on user supplied criteria
 * 
 * @author pansu
 */

/* CVS $Id: SearchReportAction.java,v 1.11 2008-05-30 17:00:08 pansu Exp $ */

public class SearchReportAction extends BaseAnnotationAction {

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = null;
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		DynaValidatorForm theForm = (DynaValidatorForm) form;

		String reportTitle = "";
		String reportCategory = "";
		String[] nanoparticleEntityTypes = new String[0];
		String[] functionalizingEntityTypes = new String[0];
		String[] functionTypes = new String[0];
		String[] searchLocations = new String[0];

		if (theForm.get("searchLocations") != null) {
			searchLocations = (String[]) theForm.getStrings("searchLocations");
		} else {
			String gridNodeHostStr = (String) request
					.getParameter("searchLocations");
			if (gridNodeHostStr != null) {
				searchLocations = gridNodeHostStr.split("~");
			}
		}

		if (theForm != null) {
			reportTitle = (String) theForm.get("reportTitle");
			reportCategory = (String) theForm.get("reportCategory");
			nanoparticleEntityTypes = (String[]) theForm
					.get("nanoparticleEntityTypes");
			functionalizingEntityTypes = (String[]) theForm
					.get("functionalizingEntityTypes");
			functionTypes = (String[]) theForm.get("functionTypes");
			searchLocations = (String[]) theForm.get("searchLocations");
		}

		// convert nanoparticle entity display names into short class names
		// String[] nanoparticleEntityClassNames = new
		// String[nanoparticleEntityTypes.length];
		// for (int i = 0; i < nanoparticleEntityTypes.length; i++) {
		// nanoparticleEntityClassNames[i] = InitSetup.getInstance()
		// .getObjectName(nanoparticleEntityTypes[i],
		// session.getServletContext());
		// }
		List<String> nanoparticleEntityClassNames = new ArrayList<String>();
		List<String> otherNanoparticleEntityTypes = new ArrayList<String>();
		for (int i = 0; i < nanoparticleEntityTypes.length; i++) {
			String className = InitSetup.getInstance().getObjectName(
					nanoparticleEntityTypes[i], session.getServletContext());
			if (className.length() == 0) {
				className = "OtherNanoparticleEntity";
				otherNanoparticleEntityTypes.add(nanoparticleEntityTypes[i]);
			} else {
				nanoparticleEntityClassNames.add(className);
			}
		}

		// convert functionalizing entity display names into short class names
		// String[] functionalizingEntityClassNames = new
		// String[functionalizingEntityTypes.length];
		// for (int i = 0; i < functionalizingEntityTypes.length; i++) {
		// functionalizingEntityClassNames[i] = InitSetup.getInstance()
		// .getObjectName(functionalizingEntityTypes[i],
		// session.getServletContext());
		// }
		List<String> functionalizingEntityClassNames = new ArrayList<String>();
		List<String> otherFunctionalizingTypes = new ArrayList<String>();
		for (int i = 0; i < functionalizingEntityTypes.length; i++) {
			String className = InitSetup.getInstance().getObjectName(
					functionalizingEntityTypes[i], session.getServletContext());
			if (className.length() == 0) {
				className = "OtherFunctionalizingEntity";
				otherFunctionalizingTypes.add(functionalizingEntityTypes[i]);
			} else {
				functionalizingEntityClassNames.add(className);
			}
		}

		// convert function display names into short class names
		// String[] functionClassNames = new String[functionTypes.length];
		// for (int i = 0; i < functionTypes.length; i++) {
		// functionClassNames[i] = InitSetup.getInstance().getObjectName(
		// functionTypes[i], session.getServletContext());
		// }
		List<String> functionClassNames = new ArrayList<String>();
		List<String> otherFunctionTypes = new ArrayList<String>();
		for (int i = 0; i < functionTypes.length; i++) {
			String className = InitSetup.getInstance().getObjectName(
					functionTypes[i], session.getServletContext());
			if (className.length() == 0) {
				className = "OtherFunction";
				otherFunctionTypes.add(functionTypes[i]);
			} else {
				functionClassNames.add(className);
			}
		}
		List<ReportBean> foundReports = new ArrayList<ReportBean>();
		ReportService service = null;
		for (String location : searchLocations) {
			if (location.equals("local")) {
				service = new ReportServiceLocalImpl();
			} else {
				String serviceUrl = InitSetup.getInstance().getGridServiceUrl(
						request, location);
				service = new ReportServiceRemoteImpl(serviceUrl);
			}
			List<ReportBean> reports = service.findReportsBy(reportTitle,
					reportCategory, nanoparticleEntityClassNames
							.toArray(new String[0]),
					otherNanoparticleEntityTypes.toArray(new String[0]),
					functionalizingEntityClassNames.toArray(new String[0]),
					otherFunctionalizingTypes.toArray(new String[0]),
					functionClassNames.toArray(new String[0]),
					otherFunctionTypes.toArray(new String[0]));
			for (ReportBean report : reports) {
				report.setLocation(location);
			}
			if (location.equals("local")) {
				List<ReportBean> filteredReports = new ArrayList<ReportBean>();
				// retrieve visibility
				FileService fileService = new FileServiceLocalImpl();
				for (ReportBean report : reports) {
					fileService.retrieveVisibility(report, user);
					if (!report.isHidden()) {
						filteredReports.add(report);
					}
				}
				foundReports.addAll(filteredReports);
			} else {
				foundReports.addAll(reports);
			}
		}

		if (foundReports != null && !foundReports.isEmpty()) {
			request.getSession().setAttribute("reports", foundReports);
			forward = mapping.findForward("success");
		} else {
			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage(
					"message.searchReport.noresult");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);
			forward = mapping.getInputForward();
		}
		return forward;
	}

	public ActionForward setup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InitReportSetup.getInstance().setReportDropdowns(request);
		InitCompositionSetup.getInstance().getNanoparticleEntityTypes(request);
		InitCompositionSetup.getInstance().getFunctionalizingEntityTypes(
				request);
		InitCompositionSetup.getInstance().getFunctionTypes(request);

		String gridNodeHostStr = (String) request
				.getParameter("searchLocations");
		if (gridNodeHostStr != null && gridNodeHostStr.length() > 0) {
			String[] selectedLocations = gridNodeHostStr.split("~");
			DynaValidatorForm theForm = (DynaValidatorForm) form;
			theForm.set("searchLocations", selectedLocations);
		}

		return mapping.getInputForward();
	}

	public boolean loginRequired() {
		return false;
	}

	public boolean canUserExecute(UserBean user)
			throws CaNanoLabSecurityException {
		return true;
	}
}
