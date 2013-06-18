<%--L
   Copyright SAIC
   Copyright SAIC-Frederick

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/cananolab/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/${protocolActionName}" enctype="multipart/form-data">
	<table width="100%" align="center">
		<tr>
			<td>
				<h3>
					<br>
					Protocol File
				</h3>
			</td>
			<td align="right" width="15%">
				<a href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=nano_protocol_help')" class="helpText">Help</a>
				<logic:equal name="protocolActionName" value="updateProtocol">
				&nbsp;&nbsp;<a href="protocolResults.do" class="helpText">Back</a>
				</logic:equal>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<jsp:include page="/bodyMessage.jsp?bundle=submit" />
				<table class="topBorderOnly" cellspacing="0" cellpadding="3" width="100%" align="center" summary="" border="0">
					<tbody>
						<tr class="topBorder">
							<td class="formTitle" colspan="4">
								<div align="justify">
									File Information
								</div>
							</td>
						</tr>
						<tr>
							<td class="leftLabel">
								<strong>Protocol Type</strong>
							</td>
							<td class="rightLabel" colspan="3">
								<bean:write name="updateProtocolForm" property="protocolType" />
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="leftLabel">
								<strong>Protocol Name</strong>
							</td>
							<td class="rightLabel" colspan="3">
								<bean:write name="updateProtocolForm" property="protocolName" />
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="leftLabel">
								<strong>Protocol Version</strong>
							</td>
							<td class="rightLabel" colspan="3">
								<bean:write name="updateProtocolForm" property="file.version" />
								&nbsp;
							</td>
						</tr>
						<c:choose>
							<c:when test="${canUserSubmit eq 'true'}">
							<tr>
									<td class="leftLabel">
										<strong>Protocol File</strong>
									</td>
									<td class="rightLabel">
										<c:choose>
											<c:when test="${not empty filename}">
												<!-- <strong>Uploaded File:&nbsp; &nbsp; </strong> c:out value="${filename}"/> &nbsp; &nbsp; -->
												<a href="searchProtocol.do?dispatch=download&amp;fileId=<bean:write name="updateProtocolForm" property="file.id" />"> /protocol/<bean:write name="updateProtocolForm" property="file.name" /></a>&nbsp; &nbsp;
											</c:when>
											<c:otherwise>No file uploaded.</c:otherwise>
										</c:choose>
										<%-- <html:file property="uploadedFile" /> &nbsp; &nbsp; --%>
									</td>
								</tr>
								<tr>
									<td class="leftLabel">
										<strong>File Title</strong>
									</td>
									<td class="rightLabel"">
										<html:text property="file.title" size="80" />
										<html:hidden property="file.id" />
										<html:hidden property="file.uri" />
									</td>
								</tr>
								<tr>
									<td class="leftLabel">
										<strong>File Description</strong>
									</td>
									<td class="rightLabel"><html:textarea property="file.description" rows="3" cols="80" />
									</td>
								</tr>
								<tr>
									<td class="leftLabel">
										<strong>Visibility</strong>
									</td>
									<td class="rightLabel">
										<html:select property="file.visibilityGroups" multiple="true" size="6">
											<html:options name="allVisibilityGroups" />
										</html:select>
										<br>
										<i>(${applicationOwner}_Researcher and ${applicationOwner}_PI are defaults if none of above is selected.)</i>
									</td>
								</tr>
							</c:when>
							<c:otherwise>
							<tr>
									<td class="leftLabel">
										<strong>Protocol File</strong>
									</td>
									<td class="rightLabel">
										<c:choose>
											<c:when test="${not empty filename}">
												<!-- <strong>Uploaded File:&nbsp; &nbsp; </strong> c:out value="${filename}"/> &nbsp; &nbsp; -->
												<a href="searchProtocol.do?dispatch=download&amp;fileId=<bean:write name="updateProtocolForm" property="file.id" />"> /protocol/<bean:write name="updateProtocolForm" property="file.name" /></a>&nbsp; &nbsp;
											</c:when>
											<c:otherwise>No file uploaded.</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td class="leftLabel">
										<strong>Title</strong>
									</td>
									<td class="rightLabel" colspan="3">
										<bean:write name="updateProtocolForm" property="file.title" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="leftLabel">
										<strong>Description</strong>
									</td>
									<td class="rightLabel" colspan="3">
										<bean:write name="updateProtocolForm" property="file.description" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<td class="leftLabel" valign="top">
										<strong>Visibility</strong>
									</td>
									<td class="rightLabel" colspan="3">
										<bean:write name="updateProtocolForm" property="file.visibilityStr" filter="false" />
										&nbsp;
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<br>
			</td>
		</tr>
	</table>
	<c:choose>
		<c:when test="${canUserSubmit eq 'true'}">
			<br>
			<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="topBorderOnly" summary="">
				<tr>
					<td width="30%">
						<span class="formMessage"> </span>
						<br>
						<table width="498" height="32" border="0" align="right" cellpadding="4" cellspacing="0">
							<tr>
								<td width="490" height="32">
									<div align="right">
										<div align="right">
											<input type="reset" value="Reset">
											<input type="hidden" name="dispatch" value="update">
											<input type="hidden" name="page" value="1">
											<html:submit />
										</div>
									</div>
								</td>
							</tr>
						</table>
						<div align="right"></div>
					</td>
				</tr>
			</table>
		</c:when>
	</c:choose>
</html:form>
