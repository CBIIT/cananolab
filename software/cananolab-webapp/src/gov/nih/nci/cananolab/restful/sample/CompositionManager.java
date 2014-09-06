package gov.nih.nci.cananolab.restful.sample;

import gov.nih.nci.cananolab.dto.common.FileBean;
import gov.nih.nci.cananolab.dto.particle.composition.BaseCompositionEntityBean;
import gov.nih.nci.cananolab.dto.particle.composition.ChemicalAssociationBean;
import gov.nih.nci.cananolab.dto.particle.composition.ComposingElementBean;
import gov.nih.nci.cananolab.dto.particle.composition.CompositionBean;
import gov.nih.nci.cananolab.dto.particle.composition.FunctionalizingEntityBean;
import gov.nih.nci.cananolab.dto.particle.composition.NanomaterialEntityBean;
import gov.nih.nci.cananolab.exception.BaseException;
import gov.nih.nci.cananolab.service.sample.impl.CompositionServiceLocalImpl;
import gov.nih.nci.cananolab.service.security.SecurityService;
import gov.nih.nci.cananolab.service.security.UserBean;
import gov.nih.nci.cananolab.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.validator.DynaValidatorForm;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class CompositionManager {

	private CompositionServiceLocalImpl compService;

	private CompositionServiceLocalImpl getService() {
		WebContext wctx = WebContextFactory.get();
		SecurityService securityService = (SecurityService) wctx.getSession()
				.getAttribute("securityService");
		compService = new CompositionServiceLocalImpl(securityService);
		return compService;
	}

	public String getEntityIncludePage(String entityType, String parent)
			throws ServletException, IOException, BaseException {
		try {
			WebContext wctx = WebContextFactory.get();
			String includePage = InitCompositionSetup.getInstance()
					.getDetailPage(entityType, parent);
			String content = wctx.forwardToString(includePage);
			return content;
		} catch (Exception e) {
			return "";
		}
	}

	public FileBean getFileById(String type, String id) throws Exception {
		WebContext wctx = WebContextFactory.get();
		UserBean user = (UserBean) wctx.getSession().getAttribute("user");
		if (user == null) {
			return null;
		}
		FileBean fileBean = getService().findFileById(id);
		DynaValidatorForm compositionForm = (DynaValidatorForm) (WebContextFactory
				.get().getSession().getAttribute("compositionForm"));
		if (type.equals("nanomaterialEntity")) {
			NanomaterialEntityBean entity = (NanomaterialEntityBean) compositionForm
					.get("nanomaterialEntity");
			entity.setTheFile(fileBean);
		} else if (type.equals("functionalizingEntity")) {
			FunctionalizingEntityBean entity = (FunctionalizingEntityBean) compositionForm
					.get("functionalizingEntity");
			entity.setTheFile(fileBean);
		} else if (type.equals("comp")) {
			CompositionBean comp = (CompositionBean) compositionForm
					.get("comp");
			comp.setTheFile(fileBean);
		} else {
			ChemicalAssociationBean assoc = (ChemicalAssociationBean) compositionForm
					.get("assoc");
			assoc.setTheFile(fileBean);
		}
		return fileBean;
	}

	public FileBean resetTheFile(String type) throws Exception {
		DynaValidatorForm compositionForm = (DynaValidatorForm) (WebContextFactory
				.get().getSession().getAttribute("compositionForm"));
		if (compositionForm == null) {
			return null;
		}
		FileBean fileBean = new FileBean();

		if (type.equals("nanomaterialEntity")) {
			NanomaterialEntityBean entity = (NanomaterialEntityBean) compositionForm
					.get("nanomaterialEntity");
			entity.setTheFile(fileBean);
		} else if (type.equals("functionalizingEntity")) {
			FunctionalizingEntityBean entity = (FunctionalizingEntityBean) compositionForm
					.get("functionalizingEntity");
			entity.setTheFile(fileBean);
		} else if (type.equals("comp")) {
			CompositionBean comp = (CompositionBean) compositionForm
					.get("comp");
			comp.setTheFile(fileBean);
		} else {
			ChemicalAssociationBean assoc = (ChemicalAssociationBean) compositionForm
					.get("assoc");
			assoc.setTheFile(fileBean);
		}
		return fileBean;
	}

	public List<BaseCompositionEntityBean> getAssociatedElementOptions(
			String compositionType, HttpServletRequest request) {
		if (StringUtils.isEmpty(compositionType)) {
			return new ArrayList<BaseCompositionEntityBean>();
		}
		List<BaseCompositionEntityBean> entities = null;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if (user == null) {
			return null;
		}
		if (compositionType.equals("nanomaterial entity")) {
			entities = (List<BaseCompositionEntityBean>) request.getSession()
					.getAttribute("sampleMaterialEntities");

		} else if (compositionType.equals("functionalizing entity")) {
			entities = (List<BaseCompositionEntityBean>) request.getSession()
					.getAttribute("sampleFunctionalizingEntities");
		}

		return entities;
	}

	public List<ComposingElementBean> getComposingElementsByNanomaterialEntityId(
			String id, HttpServletRequest request) throws Exception {
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if (user == null) {
			return null;
		}
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		SecurityService securityService = (SecurityService) request
				.getSession().getAttribute("securityService");
		CompositionServiceLocalImpl compService = new CompositionServiceLocalImpl(securityService);
		NanomaterialEntityBean entityBean = compService
				.findNanomaterialEntityById(id);
		List<ComposingElementBean> composingElements = entityBean
				.getComposingElements();
		return composingElements;
	}
}


