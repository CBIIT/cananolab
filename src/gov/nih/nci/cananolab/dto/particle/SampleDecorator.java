package gov.nih.nci.cananolab.dto.particle;

import gov.nih.nci.cananolab.exception.BaseException;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.SortableName;
import gov.nih.nci.cananolab.util.StringUtils;

import java.util.SortedSet;
import java.util.TreeSet;

import org.displaytag.decorator.TableDecorator;

/**
 * This decorator is used to for decorate different properties of a nanoparticle
 * to be shown properly in the view page using display tag lib.
 *
 * @author pansu
 *
 */
public class SampleDecorator extends TableDecorator {
	
	public SortableName getEditSampleURL() {
		SampleBean sample = (SampleBean) getCurrentRowObject();
		if (!Constants.LOCAL_SITE.equals(sample.getLocation())) {
			return this.getViewSampleURL();
		}
		String sampleId = sample.getDomain().getId().toString();
		String sampleName = sample.getDomain().getName();
		// String editSampleURL =
		// "submitSample.do?dispatch=setupUpdate&sampleId="
		// + sampleId+"&location=local";
		// String link = "<a href=" + editSampleURL + ">" + sampleName
		// + "</a>";

		StringBuilder sb = new StringBuilder("<a href=");
		sb.append("sample.do?dispatch=summaryEdit&sampleId=");
		sb.append(sampleId).append("&location=");
		sb.append(sample.getLocation()).append('>');
		sb.append(sampleName).append("</a>");
		String link = sb.toString();

		SortableName sortableLink = new SortableName(sampleName, link);
		return sortableLink;
	}

	public SortableName getViewSampleURL() {
		SampleBean sample = (SampleBean) getCurrentRowObject();
		String sampleId = sample.getDomain().getId().toString();
		String sampleName = sample.getDomain().getName();
		// String viewSampleURL = "submitSample.do?dispatch=setupView&sampleId="
		// + sampleId + "&location=" + sample.getLocation();
		// ;
		// String link = "<a href=" + viewSampleURL + ">" + sampleName
		// + "</a>";

		StringBuilder sb = new StringBuilder("<a href=");
		sb.append("sample.do?dispatch=summaryView&sampleId=");
		sb.append(sampleId).append("&location=");
		sb.append(sample.getLocation()).append('>');
		sb.append(sampleName).append("</a>");
		String link = sb.toString();
		
		SortableName sortableLink = new SortableName(sampleName, link);
		return sortableLink;
	}

	public String getKeywordStr() {
		SampleBean sample = (SampleBean) getCurrentRowObject();
		String keywordsStr = sample.getKeywordsStr();
		String[] strs = keywordsStr.split("\r\n");
		return StringUtils.join(strs, "<br>");
	}

	public String getCompositionStr() throws BaseException {
		SampleBean sample = (SampleBean) getCurrentRowObject();
		SortedSet<String> compEntityNames = new TreeSet<String>();
		if (sample.getFunctionalizingEntityClassNames() != null) {
			for (String name : sample.getFunctionalizingEntityClassNames()) {
				String displayName = InitSetup.getInstance().getDisplayName(
						name, this.getPageContext().getServletContext());
				if (displayName.length() == 0) {
					compEntityNames.add(name);
				} else {
					compEntityNames.add(displayName);
				}
			}
		}
		if (sample.getNanomaterialEntityClassNames() != null) {
			for (String name : sample.getNanomaterialEntityClassNames()) {
				String displayName = InitSetup.getInstance().getDisplayName(
						name, this.getPageContext().getServletContext());
				if (displayName.length() == 0) {
					compEntityNames.add(name);
				} else {
					compEntityNames.add(displayName);
				}
			}
		}
		return StringUtils.join(compEntityNames, "<br>");
	}

	public String getFunctionStr() throws BaseException {
		SampleBean sample = (SampleBean) getCurrentRowObject();
		SortedSet<String> functionNames = new TreeSet<String>();
		if (sample.getFunctionClassNames() != null) {
			for (String name : sample.getFunctionClassNames()) {
				String displayName = InitSetup.getInstance().getDisplayName(
						name, this.getPageContext().getServletContext());
				if (displayName.length() == 0) {
					functionNames.add(name);
				} else {
					functionNames.add(displayName);
				}
			}
		}
		return StringUtils.join(functionNames, "<br>");
	}

	public String getCharacterizationStr() throws BaseException {
		SampleBean sample = (SampleBean) getCurrentRowObject();
		SortedSet<String> charNames = new TreeSet<String>();
		if (sample.getCharacterizationClassNames() != null) {
			for (String name : sample.getCharacterizationClassNames()) {
				String displayName = InitSetup.getInstance().getDisplayName(
						name, this.getPageContext().getServletContext());
				charNames.add(displayName);
			}
		}
		return StringUtils.join(charNames, "<br>");
	}

	public String getPointOfContactName() throws BaseException {
		SampleBean sample = (SampleBean) getCurrentRowObject();
		return sample.getPocBean().getDisplayName();
	}
}
