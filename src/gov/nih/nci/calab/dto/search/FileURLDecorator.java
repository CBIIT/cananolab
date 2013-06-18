/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.search;

import gov.nih.nci.calab.service.util.SpecialCharReplacer;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * This decorator is used to for display tag to correctly display dynamic links on files
 * @author pansu
 *
 */
public class FileURLDecorator implements DisplaytagColumnDecorator {

	public Object decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2)
			throws DecoratorException {
		WorkflowResultBean workflow = (WorkflowResultBean) arg0;
		String assayType = workflow.getAssay().getAssayType();
		String assayName = workflow.getAssay().getAssayName();
		String runName = workflow.getRun().getName();

		SpecialCharReplacer specialCharReplacer = new SpecialCharReplacer();
		assayType = specialCharReplacer.getReplacedString(assayType);
		assayName = specialCharReplacer.getReplacedString(assayName);
		runName = specialCharReplacer.getReplacedString(runName);

		String fileURL = "downloadSearchedFile.do?method=downloadFile&fileName="
				+ workflow.getFile().getFilename()
				+ "&runId="
				+ workflow.getRun().getId()
				+ "&runName="
				+ runName
				+ "&inout="
				+ workflow.getFile().getInoutType()
				+ "&assayType="
				+ assayType
				+ "&assayName=" + assayName;

		String link = "<a href=" + fileURL + ">"
				+ workflow.getFile().getShortFilename() + "<br>"
				+ workflow.getFile().getTimePrefix() + "</a>";

		return link;
	}
}
