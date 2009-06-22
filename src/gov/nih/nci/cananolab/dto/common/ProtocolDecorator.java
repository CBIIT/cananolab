package gov.nih.nci.cananolab.dto.common;

import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.SortableName;

import java.io.UnsupportedEncodingException;

import org.displaytag.decorator.TableDecorator;

/**
 * This decorator is used to for decorate different properties of a protocol
 * file to be shown properly in the view page using display tag lib.
 *
 * @author pansu
 *
 */
public class ProtocolDecorator extends TableDecorator {
	public SortableName getEditURL() {
		ProtocolBean protocol = (ProtocolBean) getCurrentRowObject();
		FileBean file = protocol.getFileBean();
		if (!file.getLocation().equals(Constants.LOCAL_SITE)) {
			return getViewName();
		}
		StringBuilder sb = new StringBuilder("<a href=");
		sb.append("submitProtocol.do?dispatch=setupUpdate&protocolId=");
		sb.append(protocol.getDomain().getId());
		sb.append("&location=");
		sb.append(file.getLocation()).append('>');
		sb.append(protocol.getDomain().getName());
		sb.append("</a>");
		String link = sb.toString();
		SortableName sortableLink = new SortableName(protocol.getDomain()
				.getName(), link);
		return sortableLink;
	}

	public SortableName getViewName() {
		ProtocolBean protocol = (ProtocolBean) getCurrentRowObject();
		String protocolName = protocol.getDomain().getName();
		SortableName sortableLink = new SortableName(protocolName);
		return sortableLink;
	}

	public SortableName getDownloadURL() throws UnsupportedEncodingException {
		SortableName sortableLink = null;
		ProtocolBean protocol = (ProtocolBean) getCurrentRowObject();
		FileBean file=protocol.getFileBean();
		if (file.getDomainFile().getName() != null) {
			// String downloadURL = "searchProtocol.do?dispatch=download"
			// + "&fileId=" + file.getDomainFile().getId() + "&location="
			// + file.getLocation();
			// String link = "<a href=" + downloadURL + ">"
			// + file.getDomainFile().getName() + "</a>";
			StringBuilder sb = new StringBuilder("<a href=");
			sb.append("searchProtocol.do?dispatch=download&fileId=");
			sb.append(file.getDomainFile().getId());
			sb.append("&location=");
			sb.append(file.getLocation());
			sb.append(">");
			sb.append(file.getDomainFile().getName());
			sb.append("</a>");
			String link = sb.toString();
			sortableLink = new SortableName(file.getDomainFile().getName(),
					link);
		} else {
			sortableLink = new SortableName("");
		}
		return sortableLink;
	}
}
