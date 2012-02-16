<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="gov.nih.nci.cananolab.util.Constants"%>
<link rel="StyleSheet" type="text/css" href="css/promptBox.css">
<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="Site Preference" />
	<jsp:param name="topic" value="site_preference_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
</jsp:include>
<html:form action="/admin" enctype="multipart/form-data">
	<jsp:include page="/bodyMessage.jsp" />
	<table width="100%" align="center" class="submissionView" summary="layout">
		<tr>
			<td class="cellLabel"><label for="siteLogo">Upload Logo File</label></td>
			<td colspan="3"><html:file property="siteLogo" styleId="siteLogo"/><br /> <em>(Recommended
					image dimension: 304 x 83 pixels, maximum image size: <%=Constants.MAX_LOGO_SIZE%>
					bytes)</em></td>
		</tr>
		<tr>
			<td class="cellLabel"><label for="fileTitle">Site Name</label></td>
			<td colspan="3"><html:text styleId="fileTitle"
					property="siteName" size="50" /></td>
		</tr>
	</table>
	<br>
	<c:set var="updateId" value="bogus" />
	<c:set var="hiddenDispatch" value="update" />
	<c:set var="resetOnclick"
		value="javascript:location.href='admin.do?dispatch=setupNew'" />
	<c:set var="deleteOnclick"
		value="javascript:location.href='admin.do?dispatch=remove'" />
	<c:set var="deleteButtonName" value="Delete" />
	<%@include file="../bodySubmitButtons.jsp"%>
</html:form>
