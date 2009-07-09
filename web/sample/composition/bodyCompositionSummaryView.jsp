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
					${fn:toUpperCase(location)} Sample
					${theSample.domain.name} Composition
				</h4>
			</td>
			<td align="right" width="15%">
				<jsp:include page="/helpGlossary.jsp">
					<jsp:param name="topic" value="composition_all_tab_help" />
					<jsp:param name="glossaryTopic" value="glossary_help" />
				</jsp:include>
			</td>
		</tr>
	</table>
</c:if>
<c:set var="compositionSections"
	value="${compositionForm.map.comp.compositionSections}" />
<c:if test="${empty printView}">
	<div class="shadetabs" id="summaryTabALL">
		<ul>
			<li class="selected">
				<a	href="javascript:showSummary('ALL', ${fn:length(compositionSections)})"
					title="All"><span>All</span></a>
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
				<a href="javascript:printPage('${printUrl}')" id="printUrlAll" style="display: none;"></a>
				<a href="${exportUrl}" id="exportUrlAll" style="display: none;"></a>
			</li>
			<c:forEach var="type" items="${compositionSections}" varStatus="ind">
				<li>
					<a	href="javascript:showSummary('${ind.count}', ${fn:length(compositionSections)})"
						title="${type}"> <span>${type}</span> </a>
					<a href="javascript:printPage('${printUrl}&type=${type}')" id="printUrl${ind.count}" style="display: none;"></a>
					<a href="${exportUrl}&type=${type}" id="exportUrl${ind.count}" style="display: none;"></a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<c:forEach var='item' begin='1' end='4'>
		<div class="shadetabs" id="summaryTab${item}" style="display: none;">
			<ul>
				<li>
					<a	href="javascript:showSummary('ALL', ${fn:length(compositionSections)})"
						title="All"><span>All</span> </a>
				</li>
				<c:forEach var="type" items="${compositionSections}" varStatus="ind">
					<c:choose>
						<c:when test="${item eq ind.count}">
							<c:set var="selectedClass" value="selected" />
						</c:when>
						<c:otherwise>
							<c:set var="selectedClass" value="" />
						</c:otherwise>
					</c:choose>
					<li class="${selectedClass}">
						<a	href="javascript:showSummary('${ind.count}', ${fn:length(compositionSections)})"
							title="${type}"> <span>${type}</span> </a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:forEach>
</c:if>
<table class="summaryViewLayer1" width="100%">
	<c:if test="${! empty compositionSections && empty printView}">
		<tr>
			<td>
				<a href="javascript:printPage('${printUrl}')" id="printLink">Print</a>&nbsp;&nbsp;
				<a href="${exportUrl}" id="exportLink">Export</a>
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty compositionForm.map.comp.nanomaterialEntities}">
		<tr>
			<td>
				<jsp:include
					page="nanomaterialEntity/bodyNanomaterialEntitySummaryView.jsp">
					<jsp:param name="sampleId" value="${param.sampleId}" />
				</jsp:include>
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty compositionForm.map.comp.functionalizingEntities}">
		<tr>
			<td>
				<jsp:include
					page="functionalizingEntity/bodyFunctionalizingEntitySummaryView.jsp">
					<jsp:param name="sampleId" value="${param.sampleId}" />
				</jsp:include>
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty compositionForm.map.comp.chemicalAssociations}">
		<tr>
			<td>
				<jsp:include page="bodyChemicalAssociationSummaryView.jsp">
					<jsp:param name="sampleId" value="${param.sampleId}" />
				</jsp:include>
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty compositionForm.map.comp.files}">
		<tr>
			<td>
				<jsp:include page="bodyCompositionFileSummaryView.jsp">
					<jsp:param name="sampleId" value="${param.sampleId}" />
				</jsp:include>
			</td>
		</tr>
	</c:if>
</table>