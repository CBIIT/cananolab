<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" type="text/css" href="css/displaytag.css" />
<table width="100%" align="center">
	<tr>
		<td>
			<h3>
				<br>
				Protocol Search Results
			</h3>
		</td>
		<td align="right" width="15%">
			<a
				href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=protocol_search_results_help')"
				class="helpText">Help</a>&nbsp;&nbsp;
			<a href="searchProtocol.do?dispatch=setup" class="helpText">Back</a>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<jsp:include page="/bodyMessage.jsp?bundle=protocol" />
			<c:choose>
				<c:when test="${canCreateProtocol eq 'true'}">
					<c:set var="link" value="editURL" />
				</c:when>
				<c:otherwise>
					<c:set var="link" value="viewURL" />
				</c:otherwise>
			</c:choose>
			<display:table name="sessionScope.protocolFiles" id="protocolFile"
				requestURI="searchProtocol.do" pagesize="25" class="displaytable"
				decorator="gov.nih.nci.cananolab.dto.common.ProtocolFileDecorator">
				<!-- display:column title="Protocol Name" property="${link}" sortable="true" /-->
				<display:column title="Protocol Name" property="${link}"
					sortable="true" />
				<display:column title="Protocol Type"
					property="domainFile.protocol.type" sortable="true" />
				<display:column title="Version" property="domainFile.version"
					sortable="false" />
				<display:column title="File Title" property="domainFile.title"
					sortable="true" />
				<display:column title="Description"
					property="domainFile.description" sortable="false" />
				<display:column title="Protocol Submit Date"
					property="domainFile.createdDate" sortable="true"
					format="{0,date,MM-dd-yyyy}" />
				<display:column title="Location">Local</display:column>
			</display:table>
		</td>
	</tr>
</table>

