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

<c:if test="${not empty theSample}">
	<jsp:include page="/bodyTitle.jsp">
		<jsp:param name="pageTitle" 
			value="${fn:toUpperCase(location)} Sample ${theSample.domain.name} Characterization" />
		<jsp:param name="topic" value="char_all_tab_help" />
		<jsp:param name="glossaryTopic" value="glossary_help" />
	</jsp:include>
</c:if>
<jsp:include page="/bodyMessage.jsp?bundle=sample" />
<div class="shadetabs" id="summaryTabALL">
	<ul>
		<li class="selected">
			<a	href="javascript:showSummary('ALL', ${fn:length(characterizationTypes)})"
				title="All"><span>All</span></a>
			<c:url var="printUrl" value="characterization.do">
				<c:param name="dispatch" value="summaryPrint" />
				<c:param name="sampleId" value="${sampleId}" />
				<c:param name="location" value="${location}" />
			</c:url>
			<c:url var="exportUrl" value="characterization.do">
				<c:param name="dispatch" value="summaryExport" />
				<c:param name="sampleId" value="${sampleId}" />
				<c:param name="location" value="${location}" />
			</c:url>
			<a href="javascript:printPage('${printUrl}')" id="printUrlAll" style="display: none;"></a>
			<a href="${exportUrl}" id="exportUrlAll" style="display: none;"></a>
		</li>
		<c:forEach var="type" items="${characterizationTypes}" varStatus="ind">
			<li>
				<a
					href="javascript:showSummary('${ind.count}', ${fn:length(characterizationTypes)})"
					title="${type}"><span>${type}</span>
				</a>
				<a href="javascript:printPage('${printUrl}&type=${type}')"
					id="printUrl${ind.count}" style="display: none;"></a>
				<a href="${exportUrl}&type=${type}" id="exportUrl${ind.count}"
					style="display: none;"></a>

			</li>
		</c:forEach>
	</ul>
</div>
<c:forEach var="type" items="${characterizationTypes}" varStatus="ind">
	<div class="shadetabs" id="summaryTab${ind.count}"
		style="display: none;">
		<ul>
			<li>
				<a	href="javascript:showSummary('ALL',${fn:length(characterizationTypes)})"
					title="All"><span>All</span></a>
			</li>
			<c:forEach var="type" items="${characterizationTypes}" varStatus="ind2">
				<c:choose>
					<c:when test="${ind.count eq ind2.count }">
						<c:set var="selectedClass" value="selected" />
					</c:when>
					<c:otherwise>
						<c:set var="selectedClass" value="" />
					</c:otherwise>
				</c:choose>
				<li class="${selectedClass}">
					<a	href="javascript:showSummary('${ind2.count}', ${fn:length(characterizationTypes)})"
						title="${type}"><span>${type}</span></a>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:forEach>
<table class="summaryViewLayer1" width="100%">
	<c:if test="${! empty characterizationTypes}">
		<tr>
			<td>
				<a href="javascript:printPage('${printUrl}')" id="printLink">Print</a>&nbsp;&nbsp;
				<a href="${exportUrl}" id="exportLink">Export</a>
			</td>
		</tr>
	</c:if>
	<tr>
		<td>
			<jsp:include page="shared/bodyCharacterizationSummaryPrintViewTable.jsp" />
		</td>
	</tr>
</table>