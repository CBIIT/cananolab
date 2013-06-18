<%--L
   Copyright SAIC
   Copyright SAIC-Frederick

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/cananolab/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/sample/bodyHideSearchDetailView.jsp"%>
<table class="summaryViewNoGrid" width="99%" align="center"
	bgcolor="#F5F5f5">
	<c:if test="${!empty fn:trim(nanomaterialEntity.description)}">
		<tr>
			<td class="cellLabel" width="10%">
				Description
			</td>
			<td>
				<c:out value="${nanomaterialEntity.descriptionDisplayName}" escapeXml="false" />
			</td>
		</tr>
	</c:if>
		<c:choose>
		<c:when test="${!empty entityDetailPage}">
			<tr>
				<td class="cellLabel" width="10%">
					Properties
				</td>
				<td>
					<c:set var="nanomaterialEntity" value="${nanomaterialEntity}"
						scope="session" />
					<jsp:include page="${entityDetailPage}">
						<jsp:param name="summary" value="true" />
					</jsp:include>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:if test="${nanomaterialEntity.withProperties }">
				<tr>
					<td class="cellLabel" width="10%">
						Properties
					</td>
					<td colspan="2">
						<%
							String detailPage = gov.nih.nci.cananolab.ui.sample.InitCompositionSetup
												.getInstance().getDetailPage(
														(String) pageContext
																.getAttribute("entityType"),
														"nanomaterialEntity");
										pageContext.setAttribute("detailPage", detailPage);
						%>
						<c:set var="nanomaterialEntity" value="${nanomaterialEntity}"
							scope="session" />
						<jsp:include page="${detailPage}">
							<jsp:param name="summary" value="true" />
						</jsp:include>
					</td>
				</tr>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:if test="${! empty nanomaterialEntity.composingElements }">
		<tr>
			<td class="cellLabel" width="10%">
				Composing Elements
			</td>
			<td>
				<c:set var="entity" value="${nanomaterialEntity}" />
				<%@include file="bodyComposingElementView.jsp"%>
			</td>
		</tr>
	</c:if>
	<c:if test="${! empty nanomaterialEntity.files}">
		<tr>
			<td class="cellLabel" width="10%">
				Files
			</td>
			<td>
				<c:set var="files" value="${nanomaterialEntity.files }" />
				<c:set var="entityType" value="nanomaterial entity" />
				<c:set var="downloadAction" value="composition"/>
				<%@include file="../../bodyFileView.jsp"%>
			</td>
		</tr>
	</c:if>
</table>