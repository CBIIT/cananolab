package gov.nih.nci.calab.dto.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.displaytag.decorator.TableDecorator;

/**
 * This decorator is used to for decorate different properties of a report to be
 * shown properly in the view page using display tag lib.
 * 
 * @author pansu
 * 
 */
public class ProtocolDecorator extends TableDecorator {
	public SortableName getEditProtocolURL() {
		LabFileBean file = (LabFileBean) getCurrentRowObject();
		// replace space with special char
		String fileId = file.getId();
		String editProtocolURL = "updateProtocol.do?dispatch=setupUpdate&fileId="
				+ fileId;
		String link = "<a href=" + editProtocolURL + ">"
				+ ((ProtocolFileBean) file).getProtocolBean().getName()
				+ "</a>";
		SortableName sortableLink = new SortableName(((ProtocolFileBean) file)
				.getProtocolBean().getName(), link);
		return sortableLink;
	}

	public SortableName getViewProtocolURL() {
		LabFileBean file = (LabFileBean) getCurrentRowObject();
		// replace space with special char
		String fileId = file.getId();
		String editProtocolURL = "updateProtocol.do?dispatch=setupView&fileId="
				+ fileId;
		String link = "<a href=" + editProtocolURL + ">"
				+ ((ProtocolFileBean) file).getProtocolBean().getName()
				+ "</a>";
		SortableName sortableLink = new SortableName(((ProtocolFileBean) file)
				.getProtocolBean().getName(), link);
		return sortableLink;
	}

	public SortableName getRemoteDownloadURL() throws UnsupportedEncodingException {
		LabFileBean file = (LabFileBean) getCurrentRowObject();
		String fileName = URLEncoder.encode(file.getName(), "UTF-8");
		String gridNode = URLEncoder.encode(file.getGridNode(), "UTF-8");
		String downloadURL = "remoteSearchReport.do?dispatch=download&gridNodeHost="
				+ gridNode
				+ "&fileId="
				+ file.getId()
				+ "&fileName="
				+ fileName;
		String link = "<a href=" + downloadURL + ">" + file.getTitle() + "</a>";
		SortableName sortableLink = new SortableName(file.getTitle(), link);
		return sortableLink;
	}
}
