<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table id="summarySection1" width="95%" align="center"
	style="display: block" class="summaryViewLayer2">
	<tr>
		<th align="left">
			Nanomaterial Entity &nbsp;&nbsp;&nbsp;
			<a
				href="nanomaterialEntity.do?dispatch=setupNew&sampleId=${sampleId}"
				class="addlink"><img align="middle" src="images/btn_add.gif"
					border="0" /></a> &nbsp;&nbsp;&nbsp;
			<%-- 
			<c:if test="${!empty compositionForm.map.comp.nanomaterialEntities}">
				<a
					href="/nanopmaterialEntity.do?dispatch=delete&sampleId=${sampleId}"
					class="addlink"><img align="middle" src="images/btn_delete.gif"
						border="0" /></a>
			</c:if>
			--%>
		</th>
	</tr>
	<tr>
		<td>
			<c:choose>
				<c:when
					test="${!empty compositionForm.map.comp.nanomaterialEntities}">
					<logic:iterate name="compositionForm"
						property="comp.nanomaterialEntities" id="nanomaterialEntity"
						indexId="ind">
						<c:set var="entityType" value="${nanomaterialEntity.type}" />
						<c:if test="${!empty entityType}">
							<table class="summaryViewLayer3" width="95%" align="center">
								<tr>
									<th valign="top" align="left" width="1000px">
										${entityType}
									</th>
									<th valign="top" align="right">
										<a
											href="nanomaterialEntity.do?dispatch=setupUpdate&sampleId=${sampleId}&dataId=${nanomaterialEntity.domainEntity.id}">Edit</a>
									</th>
								</tr>
								<tr>
									<td class="cellLabel" width="20%">
										Description
									</td>
									<td>
										<c:choose>
											<c:when
												test="${!empty fn:trim(nanomaterialEntity.description)}">
												<c:out
													value="${fn:replace(nanomaterialEntity.description, cr, '<br>')}"
													escapeXml="false" />
											</c:when>
											<c:otherwise>N/A
												</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<c:if test="${nanomaterialEntity.withProperties }">
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
																								"nanomaterialEntity");
																				pageContext.setAttribute("detailPage",
																						detailPage);
												%>
											<c:set var="nanomaterialEntity" value="${nanomaterialEntity}"
												scope="session" />
											<jsp:include page="${detailPage}">
												<jsp:param name="summary" value="true" />
											</jsp:include>
										</td>
									</tr>
								</c:if>
								<tr>
									<td class="cellLabel">
										Composing Elements
									</td>
									<td>
										<c:set var="entity" value="${nanomaterialEntity}" />
										<%@include file="bodyComposingElementView.jsp"%>
									</td>
								</tr>
								<tr>
									<td class="cellLabel">
										Files
									</td>
									<td>
										<c:choose>
											<c:when test="${! empty nanomaterialEntity.files}">
												<c:set var="files" value="${nanomaterialEntity.files }" />
												<c:set var="entityType" value="nanomaterial entity" />
												<%@include file="../bodyFileView.jsp"%>
											</c:when>
											<c:otherwise>
					N/A
					</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>

						</c:if>
					</logic:iterate>
				</c:when>
				<c:otherwise>
					<div class="indented4">
						N/A
					</div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
<div id="summarySeparator1">
	<br>
</div>



