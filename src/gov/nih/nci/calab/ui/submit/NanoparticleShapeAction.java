/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.ui.submit;

/**
 * This class sets up input form for Shape characterization. 
 *  
 * @author pansu
 */

/* CVS $Id: NanoparticleShapeAction.java,v 1.12.2.2 2007-06-29 14:56:12 pansu Exp $ */

import gov.nih.nci.calab.domain.nano.characterization.Characterization;
import gov.nih.nci.calab.domain.nano.characterization.physical.Shape;
import gov.nih.nci.calab.dto.characterization.DerivedBioAssayDataBean;
import gov.nih.nci.calab.dto.characterization.physical.ShapeBean;
import gov.nih.nci.calab.dto.common.LabFileBean;
import gov.nih.nci.calab.dto.common.UserBean;
import gov.nih.nci.calab.service.submit.SubmitNanoparticleService;
import gov.nih.nci.calab.ui.core.BaseCharacterizationAction;
import gov.nih.nci.calab.ui.core.InitSessionSetup;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

public class NanoparticleShapeAction extends BaseCharacterizationAction {
	private static Logger logger = Logger
			.getLogger(NanoparticleShapeAction.class);

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
		ActionForward forward = null;

		DynaValidatorForm theForm = (DynaValidatorForm) form;
		String particleType = (String) theForm.get("particleType");
		String particleName = (String) theForm.get("particleName");
		ShapeBean shapeChar = (ShapeBean) theForm.get("achar");

		if (shapeChar.getId() == null || shapeChar.getId() == "") {

			shapeChar.setId((String) theForm.get("characterizationId"));

		}

		int fileNumber = 0;
		for (DerivedBioAssayDataBean obj : shapeChar
				.getDerivedBioAssayDataList()) {
			LabFileBean fileBean = (LabFileBean) request.getSession()
					.getAttribute("characterizationFile" + fileNumber);
			if (fileBean != null) {
				logger.info("************set fileBean to " + fileNumber);
				obj.setFile(fileBean);
			}
			fileNumber++;
		}

		// set createdBy and createdDate for the composition
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		Date date = new Date();
		shapeChar.setCreatedBy(user.getLoginName());
		shapeChar.setCreatedDate(date);

		SubmitNanoparticleService service = new SubmitNanoparticleService();
		service.addParticleShape(particleType, particleName, shapeChar);
		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage("message.addParticleShape");
		msgs.add("message", msg);
		saveMessages(request, msgs);
		forward = mapping.findForward("success");
		super.postCreate(request, theForm);
		return forward;
	}

	protected void clearMap(HttpSession session, DynaValidatorForm theForm,
			ActionMapping mapping) throws Exception {
		String particleType = (String) theForm.get("particleType");
		String particleName = (String) theForm.get("particleName");

		// clear session data from the input forms
		theForm.getMap().clear();

		theForm.set("particleName", particleName);
		theForm.set("particleType", particleType);
		theForm.set("achar", new ShapeBean());

		cleanSessionAttributes(session);
		// for (Enumeration e = session.getAttributeNames();
		// e.hasMoreElements();) {
		// String element = (String) e.nextElement();
		// if (element.startsWith(CaNanoLabConstants.CHARACTERIZATION_FILE)) {
		// session.removeAttribute(element);
		// }
		// }
	}

	protected void initSetup(HttpServletRequest request,
			DynaValidatorForm theForm) throws Exception {
		super.initSetup(request, theForm);

		HttpSession session = request.getSession();
		InitSessionSetup.getInstance().setAllShapeDistributionGraphTypes(
				session);
		InitSessionSetup.getInstance().setAllShapeTypes(session);
	}

	@Override
	protected void setFormCharacterizationBean(DynaValidatorForm theForm,
			Characterization aChar) throws Exception {
		ShapeBean shape = new ShapeBean((Shape) aChar);
		theForm.set("achar", shape);
	}

	@Override
	protected void setLoadFileRequest(HttpServletRequest request) {
		request.setAttribute("characterization", "shape");
		request.setAttribute("loadFileForward", "shapeInputForm");
	}
}
