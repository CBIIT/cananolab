<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table width="100%" align="center">
	<jsp:include page="/bodyTitle.jsp">
		<jsp:param name="pageTitle" 
			value="${fn:toUpperCase(location)} Sample ${sampleForm.map.sampleBean.domain.name}" />
		<jsp:param name="topic" value="submit_sample_help" />
		<jsp:param name="glossaryTopic" value="glossary_help" />
	</jsp:include>
</table>
<jsp:include page="/bodyMessage.jsp?bundle=sample" />
<table width="100%" align="center" class="summaryViewLayer3">
	<tr>
		<th valign="top" align="left" colspan="2" width="1000px">
			Sample Information
		</th>
	</tr>
	<tr>
		<td class="cellLabel" width="20%">
			Sample Name
		</td>
		<td>
			<bean:write name="sampleForm" property="sampleBean.domain.name" />
		</td>
	</tr>
	<tr>
		<td class="cellLabel">
			Keywords
			<br>
			<i>(one keyword per line)</i>
		</td>
		<td>
			<c:forEach var="keyword"
				items="${sampleForm.map.sampleBean.keywordSet}">
							${keyword}
							<br>
			</c:forEach>
		</td>
	</tr>
	<tr>
		<td class="cellLabel">
			Point of Contact
		</td>
		<td>
			<c:set var="edit" value="false" />
			<%@ include file="bodyPointOfContactView.jsp"%>
		</td>
	</tr>
</table>
