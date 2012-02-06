<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="summaryViewNoGrid" width="99%" align="center"
	bgcolor="#F5F5f5">
	<tr>
		<td></td>
		<td width="95%"></td>
		<td align="right">
			<a
				href="chemicalAssociation.do?dispatch=setupUpdate&sampleId=${sampleId}&dataId=${assoc.domainAssociation.id}">Edit</a>
		</td>
	</tr>
	<c:if test="${! empty assoc.attachment.id}">
		<tr>
			<td class="cellLabel" width="10%">
				Bond Type
			</td>
			<td colspan="2">
				<c:choose>
					<c:when test="${!empty assoc.attachment.bondType}">
												${assoc.attachment.bondType}
											</c:when>
					<c:otherwise>N/A
											</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="cellLabel" width="10%">
			Description
		</td>
		<td colspan="2">
			<c:choose>
				<c:when test="${!empty fn:trim(assoc.description)}">
					<c:out value="${assoc.description}" escapeXml="false" />
				</c:when>
				<c:otherwise>N/A
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="cellLabel" width="10%">
			Associated Elements
		</td>
		<td colspan="2">
			<table>
				<tr>
					<td width="250">
						<c:out value="${assoc.associatedElementA.compositionType}" />
						<c:out value="${assoc.associatedElementA.entityDisplayName}" />
						<c:choose>
							<c:when
								test="${! empty assoc.associatedElementA.composingElement.id }">
											composing element of type <c:out
									value="${assoc.associatedElementA.composingElement.type}" />
								<br>(name: <c:out
									value="${assoc.associatedElementA.composingElement.name}" />)
														</c:when>
							<c:otherwise>
								<br>(name: <c:out
									value="${assoc.associatedElementA.domainElement.name}" />)
															</c:otherwise>
						</c:choose>
					</td>
					<td style="border: 0; vertical-align: top; text-align: center;">
						<img src="images/arrow_left_right_gray.gif" id="assocImg" alt="associated with"/>
						<br>
						<strong>associated with</strong>
					</td>
					<td>
						<c:out value="${assoc.associatedElementB.compositionType}" />
						<c:out value="${assoc.associatedElementB.entityDisplayName}" />
						<c:choose>
							<c:when
								test="${! empty assoc.associatedElementB.composingElement.id }">

composing element of type <c:out
									value="${assoc.associatedElementB.composingElement.type}" />
								<br>(name: <c:out
									value="${assoc.associatedElementB.composingElement.name}" />)
														</c:when>
							<c:otherwise>
								<br> (name: <c:out
									value="${assoc.associatedElementB.domainElement.name}" />)
															</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="cellLabel" width="10%">
			Files
		</td>
		<td colspan="2">
			<c:choose>
				<c:when test="${! empty assoc.files}">
					<c:set var="files" value="${assoc.files }" />
					<c:set var="entityType" value="chemical association" />
					<c:set var="downloadAction" value="composition" />
					<%@include file="../bodyFileView.jsp"%>
				</c:when>
				<c:otherwise>
											N/A
											</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>



