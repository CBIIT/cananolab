package gov.nih.nci.cananolab.ui.sample;

import gov.nih.nci.cananolab.exception.BaseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import javax.servlet.ServletException;

import org.apache.axis.utils.StringUtils;
import org.apache.struts.util.LabelValueBean;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class DWRCharacterizationManager {
	public String[] getCharacterizationOptions(String characterizationType)
			throws Exception {
		WebContext wctx = WebContextFactory.get();
		SortedSet<String> charNames = InitCharacterizationSetup.getInstance()
				.getCharNamesByCharType(wctx.getHttpServletRequest(),
						characterizationType);
		String[] charNameArray = new String[charNames.size()];
		charNames.toArray(charNameArray);
		return charNameArray;
	}

	public List<LabelValueBean> getDecoratedCharacterizationOptions(
			String characterizationType) throws Exception {
		if (StringUtils.isEmpty(characterizationType)) {
			return null;
		}
		WebContext wctx = WebContextFactory.get();
		SortedSet<String> charNames = InitCharacterizationSetup.getInstance()
				.getCharNamesByCharType(wctx.getHttpServletRequest(),
						characterizationType);
		List<LabelValueBean> charNamesWithAssayTypes = new ArrayList<LabelValueBean>();
		for (String charName : charNames) {
			SortedSet<String> assayTypes = InitCharacterizationSetup
					.getInstance().getAssayTypesByCharName(
							wctx.getHttpServletRequest(), charName);
			if (!assayTypes.isEmpty()) {
				LabelValueBean labelValue = new LabelValueBean(charName,
						charName);
				charNamesWithAssayTypes.add(labelValue);
				for (String assayType : assayTypes) {
					LabelValueBean labelValueWithAssay = new LabelValueBean(
							" --" + assayType, assayType);
					charNamesWithAssayTypes.add(labelValueWithAssay);
				}
			} else {
				SortedSet<String> datumNames = InitCharacterizationSetup
						.getInstance().getDatumNamesByCharName(
								wctx.getHttpServletRequest(),
								characterizationType, charName, null);
				//do not include if char name doesn't have any predefined datum names
				if (datumNames != null && !datumNames.isEmpty()) {
					LabelValueBean labelValue = new LabelValueBean(charName,
							charName);
					charNamesWithAssayTypes.add(labelValue);
				}
			}
		}
		return charNamesWithAssayTypes;
	}

	public String[] getAssayTypeOptions(String characterizationName)
			throws Exception {
		WebContext wctx = WebContextFactory.get();
		SortedSet<String> assayTypes = InitCharacterizationSetup.getInstance()
				.getAssayTypesByCharName(wctx.getHttpServletRequest(),
						characterizationName);
		return assayTypes.toArray(new String[assayTypes.size()]);
	}

	public String getCharacterizationDetailPage(String charType, String charName)
			throws ServletException, IOException, BaseException {
		try {
			WebContext wctx = WebContextFactory.get();
			String includePage = InitCharacterizationSetup.getInstance()
					.getDetailPage(charType, charName);
			if (includePage != null) {
				String content = wctx.forwardToString(includePage);
				return content;
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}
}
