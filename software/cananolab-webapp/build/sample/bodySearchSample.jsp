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
<script type="text/javascript" src="javascript/SampleManager.js"></script>
<script type="text/javascript"
	src="/caNanoLab/dwr/interface/SampleManager.js"></script>
<script type='text/javascript' src='/caNanoLab/dwr/engine.js'></script>
<script type='text/javascript' src='/caNanoLab/dwr/util.js'></script>
<script type="text/javascript"
	src="javascript/CharacterizationManager.js"></script>
<script type="text/javascript"
	src="/caNanoLab/dwr/interface/CharacterizationManager.js"></script>

<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle"
		value="Sample Search" />
	<jsp:param name="topic" value="search_sample_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
	<jsp:param name="other" value="Advanced Search" />
	<jsp:param name="otherLink"
		value="advancedSampleSearch.do?dispatch=setup" />
</jsp:include>
<html:form action="searchSample" styleId="searchSampleForm">
	<jsp:include page="/bodyMessage.jsp?bundle=sample" />
	<table width="100%" align="center" class="submissionView" summary="layout">
		<tr>
			<td class="cellLabel" width="110">
				<label for="keywords">Keywords</label>
			</td>
			<td>
				<html:textarea property="text" rows="3" cols="60" styleId="keywords"/>
				<br>
				<em>searching characterization keywords, publication keywords
					and text in characterization descriptions</em>
				<br>
				<em>enter one keyword per line</em>
			</td>
		</tr>
	</table>
	<br />
	<table width="100%" align="center" class="submissionView" summary="layout">
		<tr>
			<td>
				<table class="invisibleTable" summary="layout">
					<tr>
						<td class="cellLabel" width="100">
							<label for="sampleName">Sample Name</label>
						</td>
						<td><label for="nameOperand" style="display:none">name operand</label>
							<html:select property="nameOperand" styleId="nameOperand">
								<html:options collection="stringOperands" property="value"
									labelProperty="label" />
							</html:select>
						</td>
						<td>
							<html:text property="sampleName" size="60" styleId="sampleName"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table class="invisibleTable" summary="layout">
					<tr>
						<td class="cellLabel" width="100">
							<label for="samplePOC">Sample Point of Contact</label>
						</td>
						<td><label for="pocOperand" style="display:none">POC Operand</label>
							<html:select property="pocOperand" styleId="pocOperand">
								<html:options collection="stringOperands" property="value"
									labelProperty="label" />
							</html:select>
						</td>
						<td>
							<html:text property="samplePointOfContact" size="60" styleId="samplePOC"/>
							<br />
							<em>searching organization name or person name</em>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table class="invisibleTable" summary="layout">
					<tr>
						<td class="cellLabel" width="100">
							<label for="nanomaterialEntityTypes">Nanomaterial Entity</label>
						</td>
						<td>
							<html:select styleId="nanomaterialEntityTypes"
								property="nanomaterialEntityTypes" multiple="true" size="4">
								<html:options name="nanomaterialEntityTypes" />
							</html:select>
						</td>
						<td class="cellLabel">
							<label for="functionalizingEntityTypes">Functionalizing Entity</label>
						</td>
						<td>
							<html:select styleId="functionalizingEntityTypes"
								property="functionalizingEntityTypes" multiple="true" size="3">
								<html:options name="functionalizingEntityTypes" />
							</html:select>
						</td>
						<td class="cellLabel">
							<label for="functionTypes">Function</label>
						</td>
						<td>
							<html:select styleId="functionTypes" property="functionTypes"
								multiple="true" size="3">
								<html:options name="functionTypes" />
							</html:select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table class="invisibleTable">
					<tr>
						<td class="cellLabel" width="100">
							<label for="charType">Characterization Type</label>
							<label for="characterizationType" style="display:none">hidden char type</label>
							<html:hidden styleId="characterizationType"
								property="characterizationType" />
						</td>
						<td>
							<html:select property="characterizationType" styleId="charType"
								onchange="javascript:setCharacterizationOptionsByCharType()">
								<option value="" />
									<html:options name="characterizationTypes" />
							</html:select>
						</td>
						<td class="cellLabel">
							<label for="charName">Characterization</label>
						</td>
						<td colspan="3">
							<html:select property="characterizations" styleId="charName"
								multiple="true" size="4">
								<%--<c:forEach var="achar"
										items="${searchSampleForm.map.characterizations}">
										<html:option value="${achar}">${achar}</html:option>
									</c:forEach>--%>
							</html:select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<c:set var="dataType" value="sample" />
	<c:set var="resetOnclick"
		value="javascript: location.href = 'searchSample.do?dispatch=setup&page=0'" />
	<c:set var="hiddenDispatch" value="search" />
	<c:set var="hiddenPage" value="1" />
	<%@include file="../bodySearchButtons.jsp"%>

</html:form>