<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="resultLink" value="sampleResults.do" />
<c:if test="${!empty param.from and param.from eq 'advanced'}">
	<c:set var="resultLink" value="advancedSampleResults.do" />
</c:if>

<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle"
		value="Sample ${sampleForm.map.sampleBean.domain.name}" />
	<jsp:param name="topic" value="submit_sample_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
	<jsp:param name="other" value="Back"/>
	<jsp:param name="otherLink" value="javascript:gotoPage('${resultLink}')"/>
</jsp:include>
<jsp:include page="/bodyMessage.jsp?bundle=sample" />

<table class="summaryViewNoGrid" width="100%" align="center"
	bgcolor="#dbdbdb" summary="layout">
	<tr>
		<th valign="top" align="left" height="6">
		</th>
	</tr>
	<tr>
		<td>
			<jsp:include page="bodyBareSampleSummaryView.jsp" />
		</td>
	</tr>
	<tr>
		<th valign="top" align="left" height="6">
		</th>
	</tr>
</table>
