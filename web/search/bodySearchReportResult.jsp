<%--L
   Copyright SAIC
   Copyright SAIC-Frederick

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/cananolab/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" type="text/css" href="css/displaytag.css" />
<table width="100%" align="center">
	<tr>
		<td>
			<h3>
				<br>
				Nanoparticle Report Search Results
			</h3>
		</td>
		<td align="right" width="15%">
			<a href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=search_reports_results_help')" class="helpText">Help</a>&nbsp;&nbsp; <a href="searchReport.do?dispatch=setup" class="helpText">back</a>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<jsp:include page="/bodyMessage.jsp?bundle=search" />
			<c:choose>
				<c:when test="${canUserSubmit eq 'true'}">
					<c:set var="link" value="editReportURL" />
				</c:when>
				<c:otherwise>
					<c:set var="link" value="viewReportURL" />
				</c:otherwise>
			</c:choose>
			<display:table name="sessionScope.reports" id="report" requestURI="searchReport.do" pagesize="25" class="displaytable" decorator="gov.nih.nci.calab.dto.search.ReportDecorator">
				<display:column title="Report Title" property="${link}" sortable="true" />
				<display:column title="Report Type" property="type" sortable="true" />
				<display:column title="Report Description" property="description" sortable="true" />
			</display:table>
		</td>
	</tr>
</table>

