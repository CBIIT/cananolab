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
<link rel="StyleSheet" type="text/css" href="css/promptBox.css">
<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="Publication Search Results" />
	<jsp:param name="topic" value="publications_search_results_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
</jsp:include>
<table width="100%" align="center">
	<tr>
		<td colspan="2">
			<jsp:include page="/bodyMessage.jsp?bundle=publication" />
			<c:set var="defaultSortColumn" value="6" />
			<c:if test="${!empty user && user.curator}">
				<c:set var="defaultSortColumn" value="7" />
			</c:if>
			<display:table name="publications" id="publication"
				requestURI="searchPublication.do" pagesize="25" class="displaytable"
				partialList="true" size="resultSize"
				decorator="gov.nih.nci.cananolab.dto.common.PublicationDecorator"
				defaultsort="${defaultSortColumn}">
				<u> </u>
				<display:column title="" property="detailURL" />
				<display:column title="Bibliography Info" property="displayName"
					sortable="true" />
				<display:column title="Publication<br>Type"
					property="publicationType" sortable="true" escapeXml="true" />
				<display:column title="Research<br>Category" property="researchArea"
					sortable="true"/>
				<display:column title="Associated Sample Names"
					property="sampleNames" sortable="true" />
				<display:column title="Description" property="descriptionDetail"
					sortable="true" />
				<display:column title="Created<br>Date"
					property="domainFile.createdDate" sortable="true"
					format="{0,date,MM-dd-yyyy}" />
				<display:column title="Publication<br>Status"
					property="domainFile.status" sortable="true" escapeXml="true" />
			</display:table>
		</td>
	</tr>
</table>

