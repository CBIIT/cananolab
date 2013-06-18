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
<%@ page import="gov.nih.nci.cananolab.service.sample.helper.CompositionServiceHelper"%>

<table id="summarySection${index}" width="100%" align="center"
	style="display: block" class="summaryViewLayer2">
	<tr>
		<th align="left">
			functionalizing entity
		</th>
	</tr>
	<logic:iterate name="compositionForm"
		property="comp.functionalizingEntities" id="functionalizingEntity"
		indexId="ind">
	 	<%@include file="bodySingleFunctionalizingEntitySummaryView.jsp" %>
	</logic:iterate>
</table>
<div id="summarySeparator${index}">
	<br>
</div>
