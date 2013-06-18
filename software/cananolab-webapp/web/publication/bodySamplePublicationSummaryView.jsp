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
<c:url var="printUrl" value="publication.do">
	<c:param name="dispatch" value="summaryPrint" />
	<c:param name="sampleId" value="${sampleId}" />
</c:url>
<c:url var="exportUrl" value="publication.do">
	<c:param name="dispatch" value="summaryExport" />
	<c:param name="sampleId" value="${sampleId}" />
</c:url>
<c:if test="${not empty theSample}">
	<jsp:include page="/bodyTitle.jsp">
		<jsp:param name="pageTitle"
			value="${fn:toUpperCase(location)} Sample ${theSample.domain.name} Publication" />
		<jsp:param name="topic" value="publications_all_tab_help" />
		<jsp:param name="glossaryTopic" value="glossary_help" />
		<jsp:param name="printLink" value="${printUrl}" />
		<jsp:param name="exportLink" value="${exportUrl}&type=all" />
	</jsp:include>
</c:if>
<jsp:include page="/bodyMessage.jsp?bundle=sample" />
<c:set var="publicationCategories"
	value="${publicationSummaryView.publicationCategories}" />
<c:if test="${empty printView}">
	<div class="shadetabs" id="summaryTabALL">
		<ul>
			<li class="selected">
				<a
					href="javascript:showSummary('ALL', ${fn:length(publicationCategories)})"
					title="All"><span>All</span> </a>
				<a href="${exportUrl}&type=all" id="exportUrlAll"
					style="display: none;"></a>
					
			</li>
			<li>
				<c:forEach var="type" items="${publicationCategories}"
					varStatus="ind">
					<a
						href="javascript:showSummary('${ind.count}', ${fn:length(publicationCategories)})"
						title="${type}"><span>${type}</span> </a>
					<a href="javascript:printPage('${printUrl}&type=${type}')"
						id="printUrl${ind.count}" style="display: none;"></a>
					<a href="${exportUrl}&type=${type}" id="exportUrl${ind.count}"
						style="display: none;"></a>
				</c:forEach>
			</li>
		</ul>
	</div>
	<c:forEach var="type" items="${publicationCategories}" varStatus="ind">
		<div class="shadetabs" id="summaryTab${ind.count}"
			style="display: none;">
			<ul>
				<li>
					<a
						href="javascript:showSummary('ALL', ${fn:length(publicationCategories)})"
						title="All"><span>All</span> </a>
				</li>
				<c:forEach var="type" items="${publicationCategories}"
					varStatus="ind2">
					<c:choose>
						<c:when test="${ind.count eq ind2.count }">
							<c:set var="selectedClass" value="selected" />
						</c:when>
						<c:otherwise>
							<c:set var="selectedClass" value="" />
						</c:otherwise>
					</c:choose>
					<li class="${selectedClass}">
						<a
							href="javascript:showSummary('${ind2.count}', ${fn:length(publicationCategories)})"
							title="${type}"><span><c:out value="${type}"/></span> </a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:forEach>
</c:if>
<table class="summaryViewNoTop" width="100%" summary="layout">
	<%--
	<c:if test="${! empty publicationCategories && empty printView}">
		<tr>
			<td>
				<a href="javascript:printPage('${printUrl}')" id="printLink">Print</a>&nbsp;&nbsp;
				<a href="${exportUrl}" id="exportLink">Export</a>
			</td>
		</tr>
	</c:if>
--%>
	<tr>
		<td>
			<c:forEach var="type" items="${publicationCategories}"
				varStatus="ind">
				<table id="summarySection${ind.count}" class="summaryViewNoGrid"
					align="center" width="99%">
					<tr>
						<th align="left" scope="col">
							<a name="${type}" id="${type}" class="anchorLink"><span
								class="summaryViewHeading"><c:out value="${type}"/></span> </a>
						</th>
					</tr>
					<tr>
						<td>
							<table width="99%" align="center" class="summaryViewNoGrid"
								bgcolor="#dbdbdb" summary="layout">
								<tr>
									<th valign="top" align="left" height="6">
									</th>
								</tr>
								<tr>
									<td>
										<c:forEach var="pubBean"
											items="${publicationSummaryView.category2Publications[type]}"
											varStatus="pubBeanInd">
											<c:set var="pubObj" value="${pubBean.domainFile}" />
											<table class="summaryViewNoGrid" width="99%" align="center"
												bgcolor="#F5F5f5">
												<tr>
													<th scope="row" class="cellLabel" width="10%" scope="row">
														Bibliography Info
													</th>
													<td>
														<c:out value="${pubBean.displayName}" escapeXml="false" />
														&nbsp;
													</td>
												</tr>
												<c:if test="${!empty pubObj.researchArea}">
													<tr>
														<th scope="row" class="cellLabel" width="10%" scope="row">
															Research Category
														</th>
														<td colspan="2">
															<c:out
																value="${fn:replace(pubObj.researchArea, ';', '<br>')}"
																escapeXml="false" />
															&nbsp;
														</td>
													</tr>
												</c:if>
												<c:if test="${!empty pubObj.description}">
													<tr>
														<th scope="row" class="cellLabel" width="10%" scope="row">
															Description
														</th>
														<td>
															<c:out value="${pubBean.description}" escapeXml="false" />
															&nbsp;
														</td>
													</tr>
												</c:if>
												<c:if test="${!empty pubBean.keywordsStr}">
													<tr>
														<th scope="row" class="cellLabel" width="10%" scope="row">
															Keywords
														</th>
														<td>
															<c:out value="${pubBean.keywordsDisplayName}"
																escapeXml="false" />
															&nbsp;
														</td>
													</tr>
												</c:if>
												<tr>
													<th scope="row" class="cellLabel" width="10%" scope="row">
														Publication Status
													</th>
													<td>
														<c:out value="${pubObj.status}" />
														&nbsp;
													</td>
												</tr>
											</table>
											<c:if
												test="${pubBeanInd.count<fn:length(publicationSummaryView.category2Publications[type])}">
												<br />
											</c:if>
										</c:forEach>
									</td>
								</tr>
								<tr>
									<th valign="top" align="left" height="6">
									</th>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<div id="summarySeparator${ind.count}">
					<br>
				</div>
			</c:forEach>
		</td>
	</tr>
</table>
