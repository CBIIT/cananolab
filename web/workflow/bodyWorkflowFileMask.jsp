<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<center>
	<table width="90%" align="center">
		<tr>
			<td width="10%">
				&nbsp;
			</td>
			<td>
				<br>
				<h3>
					Mask Files
				</h3>
			</td>
			<td align="right" width="10%">
				<a href="javascript:openHelpWindow('webHelp/caLAB_0.5/index.html?single=true&amp;context=caLAB_0.5&amp;topic=mask_files')" class="helpText">Help</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
	</table>
	<jsp:include page="/workflow/bodyWorkflowInfo.jsp" />
	<logic:notPresent name="filesToMask">
		<font color="blue">There are no files to mask.</font>
	</logic:notPresent>
	<logic:present name="filesToMask">
		<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td colspan="3" class="formTitle" align="center">
					Uploaded
					<bean:write name="maskFileForm" property="inout" />
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
					Mask Action
				</td>
			</tr>
			<c:set var="rowNum" value="-1" />
			<logic:iterate id="file" name="filesToMask">
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
					<c:url var="maskFileURL" value="/preMaskFile.do">
						<c:param name="runId" value="${param.runId}" />
						<c:param name="method" value="file" />
						<c:param name="inout" value="${param.inout}" />
						<c:param name="fileId" value="${file.id}" />
						<c:param name="fileName" value="${file.filename}" />
						<c:param name="runName" value="${param.runName}" />
						<c:param name="assayName" value="${param.assayName}" />
						<c:param name="assayType" value="${param.assayType}" />
						<c:param name="menuType" value="${param.menuType}" />
					</c:url>
					<td align="right" class="${style}">
						<b><a href="${maskFileURL}">Mask </a> </b>
					</td>
				</tr>
			</logic:iterate>
		</table>
	</logic:present>
</center>
