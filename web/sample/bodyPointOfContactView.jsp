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
<table align="left" class="invisibleTable">
	<tr>
		<td class="cellLabel">
			Primary Contact?
		</td>
		<td class="cellLabel">
			Contact Person
		</td>
		<td class="cellLabel">
			Organization
		</td>
		<td class="cellLabel">
			Role
		</td>
	</tr>
	<c:if
		test="${! empty sampleForm.map.sampleBean.primaryPOCBean.domain.id}">
		<c:set var="primaryPOC"
			value="${sampleForm.map.sampleBean.primaryPOCBean}" />
		<tr>
			<td>
				Yes
			</td>
			<td>
				${primaryPOC.personDisplayName}
			</td>
			<td>
				${primaryPOC.organizationDisplayName}
			</td>
			<td>
				${primaryPOC.domain.role}
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty sampleForm.map.sampleBean.otherPOCBeans}">
		<c:forEach var="poc"
			items="${sampleForm.map.sampleBean.otherPOCBeans}">
			<tr>
				<td>
					No
				</td>
				<td>
					${poc.personDisplayName}
				</td>
				<td>
					${poc.organizationDisplayName}
				</td>
				<td>
					${poc.domain.role}
				</td>
			</tr>
		</c:forEach>
	</c:if>
</table>