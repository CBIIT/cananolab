<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${not empty theSample}">
	<table width="100%" align="center">
		<tr>
			<td>
				<h4>
					${fn:toUpperCase(location)} Sample ${theSample.domain.name}
					Publication
				</h4>
			</td>
			<td align="right" width="15%">
				<jsp:include page="/helpGlossary.jsp">
					<jsp:param name="topic" value="publications_all_tab_help" />
					<jsp:param name="glossaryTopic" value="glossary_help" />
				</jsp:include>
			</td>
		</tr>
	</table>
</c:if>
<jsp:include page="/bodyMessage.jsp?bundle=sample" />
<div class="shadetabs" id="summaryTabALL">
	<ul>
		<li class="selected">
			<a
				href="javascript:showSummary('ALL', ${fn:length(publicationCategories)})"
				title="All"><span>All</span> </a>
			<c:url var="printUrl" value="${actionName}">
				<c:param name="dispatch" value="summaryPrint" />
				<c:param name="sampleId" value="${sampleId}" />
				<c:param name="location" value="${location}" />
			</c:url>
			<c:url var="exportUrl" value="${actionName}">
				<c:param name="dispatch" value="summaryExport" />
				<c:param name="sampleId" value="${sampleId}" />
				<c:param name="location" value="${location}" />
			</c:url>
			<a href="javascript:printPage('${printUrl}')" id="printUrlAll"
				style="display: none;"></a>
			<a href="${exportUrl}" id="exportUrlAll" style="display: none;"></a>
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
		<li>
			<a href="publication.do?dispatch=setupNew&sampleId=${sampleId}"><span>Other</span>
			</a>
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
						title="${type}"><span>${type}</span> </a>
				</li>
			</c:forEach>
			<li>
				<a href="publication.do?dispatch=setupNew&sampleId=${sampleId}"><span>Other</span>
				</a>
			</li>
		</ul>
	</div>
</c:forEach>
<table class="summaryViewLayer1" width="100%">
	<c:if test="${! empty publicationCategories && empty printView}">
		<tr>
			<td>
				<a href="javascript:printPage('${printUrl}')" id="printLink">Print</a>&nbsp;&nbsp;
				<a href="${exportUrl}" id="exportLink">Export</a>
			</td>
		</tr>
	</c:if>
	<tr>
		<td>
			<c:forEach var="type" items="${publicationCategories}"
				varStatus="ind">
				<table id="summarySection${ind.count}" class="smalltable3"
					cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th align="left">
							<a name="${type}" id="${type}">${type}</a> &nbsp;&nbsp;&nbsp;
							<a
								href="publication.do?dispatch=setupNew&sampleId=${sampleId}&type=${type}"
								class="addlink"><img align="middle" src="images/btn_add.gif"
									border="0" /></a>&nbsp;&nbsp;
							<%--
							<c:if test="${! empty publicationSummaryView.category2Publications[type]}">
								<a><img align="middle" src="images/btn_delete.gif"
										border="0" /> </a>
							</c:if>
							--%>
						</th>
					</tr>
					<tr>
						<td>
							<c:choose>
								<c:when
									test="${! empty publicationSummaryView.category2Publications[type]}">
									<div class="indented4">
										<table class="summarytable" width="90%" border="0"
											cellpadding="0" cellspacing="0" summary="">
											<tr>
												<th>
													Bibliography Info
												</th>
												<th>
													Research Category
												</th>
												<th>
													Description
												</th>
												<th>
													Publication Status
												</th>
												<th width="5%">
													&nbsp;
												</th>
											</tr>
											<c:forEach var="pubBean"
												items="${publicationSummaryView.category2Publications[type]}"
												varStatus="ind">
												<c:set var="pubObj" value="${pubBean.domainFile}" />
												<tr>
													<td valign="top">
														${pubBean.displayName}&nbsp;
													</td>
													<td valign="top">
														<c:out
															value="${fn:replace(pubObj.researchArea, ';', '<br>')}"
															escapeXml="false" />
														&nbsp;
													</td>
													<td valign="top">
														<c:if test="${!empty pubObj.description}">
															<div id="descriptionSection" style="position: relative;">
																<a style="display: block" id="viewDetail" href="#"
																	onmouseOver="javascript: show('publicationDescription${ind.count}');"
																	onmouseOut="javascript: hide('publicationDescription${ind.count}');">View</a>
																<table id="publicationDescription${ind.count}"
																	style="display: none; position: absolute; left: -510px; top: -50px; width: 500px; z-index: 5; border-width: 1px; border-color: #cccccc; border-style: solid; background-color: #FFFFFF;"
																	class="promptbox">
																	<tr>
																		<td>
																			${pubObj.description}
																		</td>
																	</tr>
																</table>
															</div>
														</c:if>
													</td>
													<td valign="top">
														<c:out value="${pubObj.status}" />
														&nbsp;
													</td>
													<td valign="top">
														<c:url var="pubUrl" value="publication.do">
															<c:param name="sampleId" value="${sampleId}" />
															<c:param name="dispatch" value="setupUpdate" />
															<c:param name="publicationId" value="${pubObj.id}" />
															<c:param name="location" value="${location}" />
														</c:url>
														<a href="${pubUrl}">Edit</a>
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</c:when>
								<c:otherwise>
									<div class="indented4">
										<table class="summarytable" cellpadding="0" cellspacing="0"
											border="0" width="100%">
											<tr>
												<td colspan="2" valign="top" align="left"
													class="borderlessLabel">
													N/A
												</td>
											</tr>
										</table>
									</div>
								</c:otherwise>
							</c:choose>
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
