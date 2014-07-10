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

<table class="summaryViewNoGrid" width="99%" align="center"
	bgcolor="#F5F5f5">
	<c:if
		test="${! empty assoc.attachment.id && ! empty assoc.attachment.bondType}">
		<tr>
			<th scope="row" class="cellLabel" width="10%">
				Bond Type
			</th>
			<td>
				<c:out value="${assoc.attachment.bondType}"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty fn:trim(assoc.description)}">
		<tr>
			<th scope="row" class="cellLabel" width="10%">
				Description
			</th>
			<td>
				<c:out value="${assoc.description}" escapeXml="false" />
			</td>
		</tr>
	</c:if>
	<tr>
		<th scope="row" class="cellLabel" width="10%">
			Associated Elements
		</th>
		<td>
			<table>
				<tr>
					<th scope="row" width="250">
						<c:out value="${assoc.associatedElementA.compositionType}"/>
						<c:out value="${assoc.associatedElementA.entityDisplayName}"/>
						<c:choose>
							<c:when
								test="${! empty assoc.associatedElementA.composingElement.id }">
											composing element of type <c:out value="${assoc.associatedElementA.composingElement.type}"/>
											<br>(name: <c:out value="${assoc.associatedElementA.composingElement.name}"/>)
														</c:when>
							<c:otherwise>
								<br>(name: <c:out value="${assoc.associatedElementA.domainElement.name}"/>)
															</c:otherwise>
						</c:choose>
					</th>
					<td style="border: 0; vertical-align: top; text-align: center;">
						<img src="images/arrow_left_right_gray.gif" id="assocImg" alt="${assoc.domainAssociation.id} association"/>
						<br>
						<strong>associated with</strong>
					</td>
					<td>
						<c:out value="${assoc.associatedElementB.compositionType}"/>
						<c:out value="${assoc.associatedElementB.entityDisplayName}"/>
						<c:choose>
							<c:when
								test="${! empty assoc.associatedElementB.composingElement.id }">

composing element of type <c:out value="${assoc.associatedElementB.composingElement.type}"/> <br>(name: <c:out value="${assoc.associatedElementB.composingElement.name}"/>)
														</c:when>
							<c:otherwise>
								<br> (name: <c:out value="${assoc.associatedElementB.domainElement.name}"/>)
															</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<c:if test="${! empty assoc.files}">
		<tr>
			<th scope="row" class="cellLabel" width="10%">
				Files
			</th>
			<td>
				<c:set var="files" value="${assoc.files }" />
				<c:set var="entityType" value="chemical association" />
				<c:set var="downloadAction" value="composition"/>
				<%@include file="../bodyFileView.jsp"%>
			</td>
		</tr>
	</c:if>
</table>

