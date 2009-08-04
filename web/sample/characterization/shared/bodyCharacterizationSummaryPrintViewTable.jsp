<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:forEach var="type" items="${characterizationTypes}" varStatus="ind">
	<table id="summarySection${ind.count}" width="95%" align="center"
		style="display: block" class="summaryViewLayer2">
		<tr>
			<th align="left">
				${type} &nbsp;&nbsp;&nbsp;
			</th>
		</tr>
		<tr>
			<td>
				<c:forEach var="charBean"
					items="${characterizationSummaryView.type2Characterizations[type]}">
					<c:set var="charObj" value="${charBean.domainChar}" />
					<c:set var="charName" value="${charBean.characterizationName}" />
					<c:set var="charType" value="${charBean.characterizationType}"/>
					<table class="summaryViewLayer3" width="95%" align="center">
						<tr>
							<th align="left" width="20%">
								${charName}
							</th>
							<th align="right" colspan="2">
							</th>
						</tr>
						<c:choose>
							<c:when test="${!empty charObj.assayType}">
								<tr>
									<td class="cellLabel">
										Assay Type
									</td>
									<td colspan="2">
										${charObj.assayType}
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:if
									test="${'physico chemical characterization' eq charBean.characterizationType}">
									<tr>
										<td class="cellLabel">
											Assay Type
										</td>
										<td colspan="2">
											${charName}
										</td>
									</tr>
								</c:if>
							</c:otherwise>
						</c:choose>
						<c:if test="${!empty charBean.pocBean.displayName}">
							<tr>
								<td class="cellLabel">
									Point of Contact
								</td>
								<td colspan="2">
									${charBean.pocBean.displayName}
								</td>
							</tr>
						</c:if>
						<c:if test="${!empty charBean.dateString}">
							<tr>
								<td class="cellLabel">
									Characterization Date
								</td>
								<td colspan="2">
									${charBean.dateString}
								</td>
							</tr>
						</c:if>
						<c:if test="${!empty charBean.protocolBean.displayName}">
							<tr>
								<td class="cellLabel">
									Protocol
								</td>
								<td colspan="2">
									${charBean.protocolBean.displayName}
								</td>
							</tr>
						</c:if>
						<c:if test="${charBean.withProperties }">
							<tr>
								<td class="cellLabel">
									Properties
								</td>
								<td>
									<%
											String detailPage = gov.nih.nci.cananolab.ui.sample.InitCharacterizationSetup.getInstance().getDetailPage((String)pageContext.getAttribute("charType"), (String)pageContext.getAttribute("charName"));
											pageContext.setAttribute("detailPage", detailPage);
										%>
									<c:set var="charBean" value="${charBean}" scope="session" />
									<jsp:include page="${detailPage}">
										<jsp:param name="summary" value="true" />
									</jsp:include>
								</td>
							</tr>
						</c:if>
						<c:if test="${!empty fn:trim(charObj.designMethodsDescription)}">
							<tr>
								<td class="cellLabel">
									Design Description
								</td>
								<td colspan="2">
									<c:choose>
										<c:when
											test="${!empty fn:trim(charObj.designMethodsDescription)}">
											<c:out
												value="${fn:replace(charObj.designMethodsDescription, cr, '<br>')}"
												escapeXml="false" />
										</c:when>
										<c:otherwise>
												N/A
											</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						<c:if test="${!empty charBean.experimentConfigs}">
							<tr>
								<td class="cellLabel">
									Techniques and Instruments
								</td>
								<td colspan="2">
									<%@ include file="bodyExperimentConfigView.jsp"%>
								</td>
							</tr>
						</c:if>
						<c:if test="${!empty charBean.findings}">
							<tr>
								<td class="cellLabel">
									Characterization Results
								</td>
								<td colspan="2">
									<%@ include file="bodyFindingView.jsp"%>
								</td>
							</tr>
						</c:if>
						<c:if test="${!empty charBean.conclusion}">
							<tr>
								<td class="cellLabel">
									Analysis and Conclusion
								</td>
								<td colspan="2">
									${charBean.conclusion}
								</td>
							</tr>
						</c:if>
					</table>
					<br>
				</c:forEach>
				<br>
			</td>
		</tr>
	</table>
	<div id="summarySeparator${ind.count}">
		<br>
	</div>
</c:forEach>