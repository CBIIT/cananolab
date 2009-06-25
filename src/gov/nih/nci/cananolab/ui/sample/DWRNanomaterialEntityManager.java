package gov.nih.nci.cananolab.ui.sample;

import gov.nih.nci.cananolab.domain.particle.ComposingElement;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.composition.ComposingElementBean;
import gov.nih.nci.cananolab.dto.particle.composition.FunctionBean;
import gov.nih.nci.cananolab.dto.particle.composition.NanomaterialEntityBean;
import gov.nih.nci.cananolab.service.sample.helper.CompositionServiceHelper;
import gov.nih.nci.cananolab.ui.core.InitSetup;

import org.apache.struts.validator.DynaValidatorForm;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.impl.DefaultWebContextBuilder;

/**
 * Work with DWR to set up drop-downs required in the nanomaterial entity page
 *
 * @author pansu
 *
 */
public class DWRNanomaterialEntityManager {
	private CompositionServiceHelper helper = new CompositionServiceHelper();

	public DWRNanomaterialEntityManager() {
	}

	public ComposingElementBean addInherentFunction(FunctionBean function) {
		DynaValidatorForm compositionForm = (DynaValidatorForm) (WebContextFactory
				.get().getSession().getAttribute("compositionForm"));
		NanomaterialEntityBean entity = (NanomaterialEntityBean) compositionForm
				.get("nanomaterialEntity");
		entity.getTheComposingElement().addFunction(function);
		return entity.getTheComposingElement();
	}

	public ComposingElementBean deleteInherentFunction(FunctionBean function) {
		DynaValidatorForm compositionForm = (DynaValidatorForm) (WebContextFactory
				.get().getSession().getAttribute("compositionForm"));
		NanomaterialEntityBean entity = (NanomaterialEntityBean) compositionForm
				.get("nanomaterialEntity");
		entity.getTheComposingElement().removeFunction(function);
		return entity.getTheComposingElement();
	}

	public ComposingElementBean getComposingElementById(String id)
			throws Exception {
		DynaValidatorForm compositionForm = (DynaValidatorForm) (WebContextFactory
				.get().getSession().getAttribute("compositionForm"));
		NanomaterialEntityBean entity = (NanomaterialEntityBean) compositionForm
				.get("nanomaterialEntity");
		WebContext wctx = WebContextFactory.get();
		UserBean user = (UserBean) wctx.getSession().getAttribute("user");
		ComposingElement composingElement = helper.findComposingElementById(id, user);
		ComposingElementBean composingElementBean = new ComposingElementBean(
				composingElement);
		// update function type based mapping stored in session
		DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
		org.directwebremoting.WebContext webContext = dwcb.get();
		for (FunctionBean functionBean : composingElementBean
				.getInherentFunctions()) {
			functionBean.updateType(InitSetup.getInstance()
					.getClassNameToDisplayNameLookup(
							webContext.getServletContext()));
		}
		entity.setTheComposingElement(composingElementBean);
		return composingElementBean;
	}

	public ComposingElementBean resetTheComposingElement() {
		DynaValidatorForm compositionForm = (DynaValidatorForm) (WebContextFactory
				.get().getSession().getAttribute("compositionForm"));
		NanomaterialEntityBean entity = (NanomaterialEntityBean) compositionForm
				.get("nanomaterialEntity");
		ComposingElementBean elementBean = new ComposingElementBean();
		entity.setTheComposingElement(elementBean);
		return elementBean;
	}
}
