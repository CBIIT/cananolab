<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table id="summarySection2" width="95%" align="center"
	style="display: block" class="summaryViewLayer2">
	<tr>
		<th align="left">
			Functionalizing Entity
		</th>
	</tr>
	<logic:iterate name="compositionForm"
		property="comp.functionalizingEntities" id="functionalizingEntity"
		indexId="ind">
		<c:set var="entityType" value="${functionalizingEntity.type}" />
		<c:if test="${!empty entityType}">
			<tr>
				<td>
					<div class="indented4">
						<table class="summaryViewLayer3" width="95%" align="center">
							<tr>
								<th valign="top" align="left" colspan="2" width="1000px">
									${entityType}
								</th>
							</tr>
							<c:if test="${!empty functionalizingEntity.name}">
								<tr>
									<td class="cellLabel">
										Name
									</td>
									<td>
										${functionalizingEntity.name}
									</td>
								</tr>
							</c:if>
							<c:if test="${!empty functionalizingEntity.value}">
								<tr>
									<td class="cellLabel">
										Amount
									</td>
									<td>
										${functionalizingEntity.value}
										${functionalizingEntity.valueUnit}
									</td>
								</tr>
							</c:if>
							<c:if
								test="${!empty functionalizingEntity.molecularFormulaDisplayName}">
								<tr>
									<td class="cellLabel">
										Molecular Formula
									</td>
									<td>
										${functionalizingEntity.molecularFormulaDisplayName}
									</td>
								</tr>
							</c:if>
							<c:if test="${functionalizingEntity.withProperties }">
								<tr>
									<td class="cellLabel">
										Properties
									</td>
									<td>
										<%
											String detailPage = gov.nih.nci.cananolab.ui.sample.InitCompositionSetup
																		.getInstance()
																		.getDetailPage(
																				application,
																				(String) pageContext
																						.getAttribute("entityType"),
																				"functionalizingEntity");
																pageContext.setAttribute("detailPage",
																		detailPage);
										%>
										<c:set var="functionalizingEntity"
											value="${functionalizingEntity}" scope="session" />
										<jsp:include page="${detailPage}">
											<jsp:param name="summary" value="true" />
										</jsp:include>
									</td>
								</tr>
							</c:if>
							<c:if test="${!empty functionalizingEntity.functions}">
								<tr>
									<td class="cellLabel">
										Function(s)
									</td>
									<td>
										<c:set var="entity" value="${functionalizingEntity }" />
										<%@ include file="bodyFunctionView.jsp"%>
									</td>
								</tr>
							</c:if>
							<c:if
								test="${!empty functionalizingEntity.activationMethodDisplayName}">
								<tr>
									<td class="cellLabel">
										Activation Method
									</td>
									<td>
										${functionalizingEntity.activationMethodDisplayName}"
									</td>
								</tr>
							</c:if>
							<c:if test="${!empty fn:trim(functionalizingEntity.description)}">
								<tr>
									<td class="cellLabel" width="20%">
										Description
									</td>
									<td>
										<c:out
											value="${fn:replace(functionalizingEntity.description, cr, '<br>')}"
											escapeXml="false" />
									</td>
								</tr>
							</c:if>
							<c:if test="${! empty functionalizingEntity.files}">
								<tr>
									<td class="cellLabel">
										Files
									</td>
									<td>
										<c:set var="files" value="${functionalizingEntity.files }" />
										<c:set var="entityType" value="functionalizing entity" />
										<%@include file="../bodyFileView.jsp"%>
									</td>
								</tr>
							</c:if>
						</table>
					</div>
				</td>
			</tr>
		</c:if>
	</logic:iterate>
</table>
<div id="summarySeparator2">
	<br>
</div>
