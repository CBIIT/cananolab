/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.cananolab.dto.search;

import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.SortableName;

import org.displaytag.decorator.TableDecorator;

/**
 * This decorator is used to for decorate different properties of a sample
 * resulted from advanced search to be shown properly in the view page using
 * display tag lib.
 * 
 * @author pansu
 * 
 */
public class AdvancedSampleDecorator extends TableDecorator {

	public SortableName getEditSampleURL() {
		AdvancedSampleBean sample = (AdvancedSampleBean) getCurrentRowObject();
		if (!Constants.LOCAL_SITE.equals(sample.getLocation())) {
			return this.getViewSampleURL();
		}

		String sampleName = sample.getSampleName();
		StringBuilder sb = new StringBuilder("<a href=");
		sb.append("sample.do?dispatch=summaryEdit&page=0&sampleId=");
		sb.append(sample.getSampleId()).append("&location=");
		sb.append(sample.getLocation()).append('>');
		sb.append(sampleName).append("</a>");
		String link = sb.toString();

		SortableName sortableLink = new SortableName(sampleName, link);
		return sortableLink;
	}

	public SortableName getViewSampleURL() {
		AdvancedSampleBean sample = (AdvancedSampleBean) getCurrentRowObject();
		String sampleName = sample.getSampleName();
		StringBuilder sb = new StringBuilder("<a href=");
		sb.append("sample.do?dispatch=summaryView&page=0&sampleId=");
		sb.append(sample.getSampleId()).append("&location=");
		sb.append(sample.getLocation()).append('>');
		sb.append(sampleName).append("</a>");
		String link = sb.toString();

		SortableName sortableLink = new SortableName(sampleName, link);
		return sortableLink;
	}
}
