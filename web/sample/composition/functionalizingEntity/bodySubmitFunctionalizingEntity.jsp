<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="StyleSheet" type="text/css" href="css/promptBox.css">
<script type="text/javascript" src="javascript/addDropDownOptions.js"></script>
<script type="text/javascript" src="javascript/CompositionManager.js"></script>
<script type='text/javascript'
	src='/caNanoLab/dwr/interface/CompositionManager.js'></script>
<script type="text/javascript"
	src="javascript/FunctionalizingEntityManager.js"></script>
<script type='text/javascript'
	src='/caNanoLab/dwr/interface/FunctionalizingEntityManager.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<table width="100%" align="center">
	<tr>
		<td>
			<h4>
				${sampleName} Sample Composition - Functionalizing Entity
				<c:if
					test="${!empty compositionForm.map.functionalizingEntity.domainEntity.id}">
						- ${compositionForm.map.functionalizingEntity.type}
						</c:if>
			</h4>
		</td>
		<td align="right" width="15%">
			<jsp:include page="/helpGlossary.jsp">
				<jsp:param name="topic" value="function_entity_help" />
				<jsp:param name="glossaryTopic" value="glossary_help" />
			</jsp:include>
		</td>
	</tr>
</table>
<html:form action="/functionalizingEntity" enctype="multipart/form-data">
	<jsp:include page="/bodyMessage.jsp?bundle=sample" />
	<table width="100%" align="center" class="submissionView">
		<tr>
			<th colspan="4">
				Summary
			</th>
		</tr>
		<c:if
			test="${empty compositionForm.map.functionalizingEntity.domainEntity.id}">
			<tr>
				<td class="cellLabel">
					Functionalizing Entity Type*
				</td>
				<td colspan="3">
					<div id="feTypePrompt">
						<html:select styleId="feType"
							property="functionalizingEntity.type"
							onchange="javascript:callPrompt('Functionalizing Entity Type', 'feType', 'feTypePrompt'); setEntityInclude('feType', 'functionalizingEntity');">
							<option value=""></option>
							<html:options name="functionalizingEntityTypes" />
							<option value="other">
								[Other]
							</option>
						</html:select>
					</div>
				</td>
			</tr>
		</c:if>
		<tr>
			<td class="cellLabel">
				Chemical Name*
			</td>
			<td colspan="4">
				<html:text property="functionalizingEntity.name" size="70" />
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				PubChem DataSource
			</td>
			<td>
				<div id="pubChemDataSourcePrompt">
					<html:select styleId="pubChemDataSource"
						property="functionalizingEntity.pubChemDataSourceName"
						onchange="javascript:callPrompt('PubChem DataSource', 'pubChemDataSource', 'pubChemDataSourcePrompt');">
						<option value="" />
							<html:options name="pubChemDataSources" />
					</html:select>
				</div>
			</td>
			<td class="cellLabel">
				PubChem Id
			</td>
			<td>
				<html:text property="functionalizingEntity.pubChemId"
					onkeydown="return filterInteger(event)" size="30"
					styleId="pubChemId" />
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				Amount
			</td>
			<td>
				<html:text property="functionalizingEntity.value"
					onkeydown="return filterFloatNumber(event)" />
			</td>
			<td class="cellLabel">
				Amount Unit
			</td>
			<td>
				<html:select styleId="feUnit"
					property="functionalizingEntity.valueUnit"
					onchange="javascript:callPrompt('Amount Unit', 'feUnit');">
					<option value=""></option>
					<html:options name="functionalizingEntityUnits" />
					<option value="other">
						[Other]
					</option>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				Molecular Formula Type
			</td>
			<td>
				<html:select styleId="mfType"
					property="functionalizingEntity.molecularFormulaType"
					onchange="javascript:callPrompt('Molecular Formula Type', 'mfType'); ">
					<option value=""></option>
					<html:options name="feMolecularFormulaTypes" />
					<option value="other">
						[Other]
					</option>
				</html:select>
				&nbsp;
			</td>
			<td class="cellLabel">
				Molecular Formula
			</td>
			<td>
				<html:textarea property="functionalizingEntity.molecularFormula"
					rows="2" cols="80" />
				&nbsp;
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				Activation Method
			</td>
			<td>
				<html:select styleId="feaMethod"
					property="functionalizingEntity.activationMethod.type"
					onchange="javascript:callPrompt('Activation Method', 'feaMethod');">
					<option value=""></option>
					<html:options name="activationMethods" />
					<option value="other">
						[Other]
					</option>
				</html:select>
			</td>
			<td class="cellLabel">
				Activation Effect
			</td>
			<td>
				<html:text
					property="functionalizingEntity.activationMethod.activationEffect"
					size="70" />
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				Description
			</td>
			<td colspan="3">
				<html:textarea property="functionalizingEntity.description" rows="2"
					cols="80" />
			</td>
		</tr>
	</table>
	<br>
	<c:if test="${!empty entityDetailPage}">
		<jsp:include page="${entityDetailPage}" />
	</c:if>
	<div id="entityInclude"></div>
	<table width="100%" align="center" class="submissionView">
		<tbody>
			<tr>
				<th colspan="2">
					Function Information
				</th>
			</tr>
			<tr>
				<td class="cellLabel" width="15%">
					Function
				</td>
				<td>
					<a style="display: block" id="addFunction"
						href="javascript:clearFunction();openSubmissionForm('Function');"><img
							align="top" src="images/btn_add.gif" border="0" /> </a>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<c:if
						test="${! empty compositionForm.map.functionalizingEntity.functions}">
						<c:set var="edit" value="true" />
						<c:set var="entity"
							value="${compositionForm.map.functionalizingEntity}" />
						<%@ include file="bodyFunctionView.jsp"%>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="newFunction" style="display: none;">
						<%@ include file="bodySubmitFunction.jsp"%>
					</div>
					&nbsp;
				</td>
			</tr>
		</tbody>
	</table>
	<br>
	<%--Functionalizing Entity File Information --%>
	<c:set var="fileParent" value="functionalizingEntity" />
	<a name="file">
		<table width="100%" align="center" class="submissionView">
			<tbody>
				<tr>
					<th colspan="2">
						Functionalizing Entity File
					</th>
				</tr>
				<tr>
					<td class="cellLabel" width="15%">
						File
					</td>
					<td>
						<a style="display: block" id="addFile"
							href="javascript:clearFile('${fileParent }'); openSubmissionForm('File');"><img
								align="top" src="images/btn_add.gif" border="0" /> </a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<c:if
							test="${! empty compositionForm.map.functionalizingEntity.files }">
							<c:set var="files"
								value="${compositionForm.map.functionalizingEntity.files}" />
							<c:set var="edit" value="true" />
							<%@ include file="../bodyFileView.jsp"%>
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div style="display: none" id="newFile">
							<c:set var="fileForm" value="compositionForm" />
							<c:set var="theFile"
								value="${compositionForm.map.functionalizingEntity.theFile}" />
							<c:set var="actionName" value="functionalizingEntity" />
							<%@include file="../../bodySubmitFile.jsp"%>
							&nbsp;
						</div>
					</td>
				</tr>
		</table> </a>
	<br>
	<jsp:include page="/sample/bodyAnnotationCopy.jsp" />
	<br>
	<c:set var="type" value="functionalizing entity" />
	<c:set var="actionName" value="functionalizingEntity" />
	<c:set var="formName" value="compositionForm" />
	<c:set var="dataId"
		value="${compositionForm.map.functionalizingEntity.domainEntity.id}" />
	<%@include file="../../bodySubmitButtons.jsp"%>

</html:form>