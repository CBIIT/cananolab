<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" type="text/css" href="css/displaytag.css" />
<link rel="StyleSheet" type="text/css" href="css/promptBox.css">
<table width="100%" align="center">
	<jsp:include page="/bodyTitle.jsp">
		<jsp:param name="pageTitle" value="Publication Search Results" />
		<jsp:param name="topic" value="publications_search_results_help" />
		<jsp:param name="glossaryTopic" value="glossary_help" />
	</jsp:include>
	<tr>
		<td colspan="2">
			<c:choose>
				<c:when test="${!empty user && user.curator}">
					<c:set var="link" value="editPublicationURL" />
				</c:when>
				<c:otherwise>
					<c:set var="link" value="displayName" />
				</c:otherwise>
			</c:choose>
			<jsp:include page="/bodyMessage.jsp?bundle=publication" />
			<display:table name="sessionScope.publications" id="publication"
				requestURI="searchPublication.do" pagesize="25" class="displaytable"
				decorator="gov.nih.nci.cananolab.dto.common.PublicationDecorator">
				<c:if test="${!empty user && user.curator}">
					<display:column title="" property="editPublicationURL" />
				</c:if>
				<display:column title="Bibliography Info" property="displayName"
					sortable="true" />
				<display:column title="Publication<br>Type"
					property="publicationType" sortable="true" />
				<display:column title="Research<br>Category" property="researchArea"
					sortable="true" />
				<display:column title="Associated<br>Sample Names"
					property="sampleNames" sortable="true" />
				<display:column title="Description"
					property="descriptionDetail" sortable="true" />
				<display:column title="Created<br>Date"
					property="domainFile.createdDate" sortable="true"
					format="{0,date,MM-dd-yyyy}" />
				<display:column title="Publication<br>Status"
					property="domainFile.status" sortable="true" />
				<display:column title="Location" property="location" sortable="true" />
			</display:table>
		</td>
	</tr>
</table>

