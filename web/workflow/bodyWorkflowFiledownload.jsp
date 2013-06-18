<%--L
   Copyright SAIC
   Copyright SAIC-Frederick

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/cananolab/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<center>
	<table width="80%" align="center">
		<tr>
			<td width="10%">
				&nbsp;
			</td>
			<td>
				<br>
				<h3>
					Download Files
				</h3>
			</td>
			<td align="right" width="10%">
				<a href="javascript:openHelpWindow('webHelp/caLAB_0.5/index.html?single=true&amp;context=caLAB_0.5&amp;topic=file_download')" class="helpText">Help</a>
			</td>
	</table>
	<jsp:include page="/workflow/bodyWorkflowInfo.jsp" />
	<logic:notPresent name="filesToDownload">
		<font color="blue">There are no files to download.</font>
	</logic:notPresent>
	<logic:present name="filesToDownload">
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3" class="formTitle" align="center">
					Uploaded
					<bean:write name="fileDownloadForm" property="inout" />
					Files
				</td>
			<tr>
			<tr>
				<td class="formTitle">
					File Name
				</td>
				<td class="formTitle">
					Uploaded Date
				</td>
				<td class="formTitle" align="right">
					Download Action
				</td>
			</tr>
			<c:set var="rowNum" value="-1" />
			<logic:iterate id="file" name="filesToDownload">
				<c:set var="rowNum" value="${rowNum+1}" />
				<c:choose>
					<c:when test="${rowNum % 2 == 0}">
						<c:set var="style" value="formFieldGrey" />
						<c:set var="style0" value="leftBorderedFormFieldGrey" />
					</c:when>
					<c:otherwise>
						<c:set var="style" value="formFieldWhite" />
						<c:set var="style0" value="leftBorderedFormFieldWhite" />
					</c:otherwise>
				</c:choose>
				<tr>
					<td class="${style0}">
						<bean:write name="file" property="filename" />
					</td>
					<td class="${style}">
						<bean:write name="file" property="createdDate" />
					</td>

					<c:url var="downloadFileURL" value="/fileDownload.do">
						<c:param name="runId" value="${param.runId}" />
						<c:param name="method" value="downloadFile" />
						<c:param name="fileName" value="${file.filename}" />
						<c:param name="runName" value="${param.runName}" />
						<c:param name="assayName" value="${param.assayName}" />
						<c:param name="assayType" value="${param.assayType}" />
						<c:param name="inout" value="${param.inout}" />
					</c:url>
					<td align="right" class="${style}">
						<b><a href="${downloadFileURL}">Download</a> </b>
					</td>
				</tr>
			</logic:iterate>
			<tr>
				<c:url var="downloadAllFileURL" value="/fileDownload.do">
					<c:param name="runId" value="${param.runId}" />
					<c:param name="method" value="downloadFile" />
					<c:param name="fileName" value="ALL_FILES.zip" />
					<c:param name="runName" value="${param.runName}" />
					<c:param name="assayName" value="${param.assayName}" />
					<c:param name="assayType" value="${param.assayType}" />
					<c:param name="inout" value="${param.inout}" />
				</c:url>
				<td align="right" class="${style0}" colspan="3">
					<b><a href="${downloadAllFileURL}">Download All</a></b>
				</td>
			</tr>
		</table>
	</logic:present>
</center>
