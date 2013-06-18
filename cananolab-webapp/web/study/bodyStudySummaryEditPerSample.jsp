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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="StyleSheet" type="text/css" href="css/promptBox.css">
<script type="text/javascript" src="javascript/addDropDownOptions.js"></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>

<%--TODO: create online help topic for this page.--%>
<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="Study Efficacy of nanoparticle" />
	<jsp:param name="topic" value="study_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
</jsp:include>
<jsp:include page="/bodyMessage.jsp?bundle=study" />

<table id="summarySection${ind.count}" class="summaryViewNoGrid"
	width="100%" align="center">
	<tr>
		<th align="left">
			<a name="${type}" id="${type}"><span class="summaryViewHeading">Study</span>
			</a>&nbsp;&nbsp;
			<a href="study.do?dispatch=setupNew" class="addlink"><img
					align="middle" src="images/btn_add.gif" border="0" /></a>&nbsp;&nbsp;
		</th>
	</tr>
	<tr>
		<td>
			<table class="summaryViewNoGrid" width="100%" align="center"
				bgcolor="#dbdbdb">
				<tr>
					<th valign="top" align="left" height="6">
					</th>
				</tr>
				<c:forEach var="theStudy" items="${studies}">
				<tr>
					<td>
					<c:set var="edit" value="true"/>
					<%@ include file="bodyBareStudySummaryView.jsp" %>
					</td>
				</tr>
				<tr>
					<th valign="top" align="left" height="6">
					</th>
				</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>