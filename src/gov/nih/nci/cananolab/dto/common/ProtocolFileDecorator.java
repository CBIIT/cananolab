package gov.nih.nci.cananolab.dto.common;

import gov.nih.nci.cananolab.domain.common.ProtocolFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.displaytag.decorator.TableDecorator;

/**
 * This decorator is used to for decorate different properties of a protocol
 * file to be shown properly in the view page using display tag lib.
 * 
 * @author pansu
 * 
 */
public class ProtocolFileDecorator extends TableDecorator {
	public SortableName getEditURL() {
		ProtocolFileBean file = (ProtocolFileBean) getCurrentRowObject();
		// replace space with special char
		String fileId = file.getDomainFile().getId().toString();
		String editProtocolURL = "submitProtocol.do?dispatch=setupUpdate&fileId="
				+ fileId;
		String link = "<a href="
				+ editProtocolURL
				+ ">"
				+ ((ProtocolFile) (file.getDomainFile())).getProtocol()
						.getName() + "</a>";
		SortableName sortableLink = new SortableName(((ProtocolFile) (file
				.getDomainFile())).getProtocol().getName(), link);
		return sortableLink;
	}

	public SortableName getViewURL() {
		ProtocolFileBean file = (ProtocolFileBean) getCurrentRowObject();
		// replace space with special char
		String fileId = file.getDomainFile().getId().toString();
		String editProtocolURL = "submitProtocol.do?dispatch=setupView&fileId="
				+ fileId;
		String link = "<a href="
				+ editProtocolURL
				+ ">"
				+ ((ProtocolFile) (file.getDomainFile())).getProtocol()
						.getName() + "</a>";
		SortableName sortableLink = new SortableName(((ProtocolFile) (file
				.getDomainFile())).getProtocol().getName(), link);
		return sortableLink;
	}

	public SortableName getRemoteDownloadURL()
			throws UnsupportedEncodingException {
		ProtocolFileBean file = (ProtocolFileBean) getCurrentRowObject();
		String fileName = URLEncoder.encode(file.domainFile.getName(), "UTF-8");
		// String gridNode = URLEncoder.encode(file.getGridNode(), "UTF-8");
		String downloadURL = "remoteSearchReport.do?dispatch=download&gridNodeHost="
				// + gridNode
				+ "&fileId="
				+ file.getDomainFile().getId()
				+ "&fileName="
				+ fileName;
		String link = "<a href=" + downloadURL + ">" + file.getDomainFile().getTitle() + "</a>";
		SortableName sortableLink = new SortableName(file.getDomainFile().getTitle(), link);
		return sortableLink;
	}
}
