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

<link rel="StyleSheet" type="text/css" href="css/promptBox.css">
<script type="text/javascript" src="javascript/addDropDownOptions.js"></script>
<script type="text/javascript" src="javascript/CompositionManager.js"></script>
<script type='text/javascript'
	src='/caNanoLab/dwr/interface/CompositionManager.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<c:set var="fileParent" value="comp" />
<c:set var="theFile" value="${compositionForm.map.comp.theFile}" />
<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="${sampleName} Sample Composition - Composition File" />
	<jsp:param name="topic" value="compo_file_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
</jsp:include>
<html:form action="/compositionFile" enctype="multipart/form-data">
	<jsp:include page="/bodyMessage.jsp?bundle=sample" />
	<c:set var="fileForm" value="compositionForm" />
	<c:set var="theFile" value="${compositionForm.map.comp.theFile}" />
	<c:set var="actionName" value="compositionFile" />
	<%@include file="../bodySubmitFile.jsp"%>
	<br>
	<c:set var="type" value="composition file" />
	<c:set var="actionName" value="compositionFile" />
	<c:set var="formName" value="compositionForm" />
	<c:set var="dataId"
		value="${compositionForm.map.comp.theFile.domainFile.id}" />
	<%@include file="../bodySubmitButtons.jsp"%>
</html:form>