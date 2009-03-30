package gov.nih.nci.cananolab.ui.sample;

import java.util.SortedSet;

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

	public String[] getAssayTypeOptions(String characterizationName)
			throws Exception {
		WebContext wctx = WebContextFactory.get();
		SortedSet<String> assayTypes = InitCharacterizationSetup.getInstance()
				.getAssayTypesByCharName(wctx.getHttpServletRequest(),
						characterizationName);
		return assayTypes.toArray(new String[assayTypes.size()]);
	}
}
