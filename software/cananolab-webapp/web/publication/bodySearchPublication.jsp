<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="javascript/PublicationManager.js"></script>
<script type="text/javascript"
	src="/caNanoLab/dwr/interface/PublicationManager.js"></script>
<script type="text/javascript"
	src="/caNanoLab/dwr/interface/SampleManager.js"></script>
<script type='text/javascript' src='/caNanoLab/dwr/engine.js'></script>
<script type='text/javascript' src='/caNanoLab/dwr/util.js'></script>
<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="Search Publications" />
	<jsp:param name="topic" value="search_publications_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
</jsp:include>
<html:form action="searchPublication">
	<jsp:include page="/bodyMessage.jsp?bundle=publication" />	
	<table width="100%" align="center" class="submissionView" summary="layout">
		<tr>
			<td class="cellLabel" width="15%">
				<label for="publicationCategory">Publication Type</label>
			</td>
			<td colspan="2">
				<html:select property="category" styleId="publicationCategory"
					onchange="javascript:updateSearchFormBasedOnCategory();">
					<option value="" />
						<html:options name="publicationCategories" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				<label for="researchArea">Research Category</label>
			</td>
			<td colspan="2">
				<c:forEach var="data" items="${publicationResearchAreas}">
					<html:multibox styleId="researchArea" property="researchArea">
												${data}
											</html:multibox>${data}
										</c:forEach>
			</td>
		</tr>
		<tr id="pubMedRow">
			<td class="cellLabel">
				<label for="pubMedId">PubMed ID</label>
			</td>
			<td colspan="2">
				<html:text styleId="pubMedId" property="pubMedId" size="30" />
				<em>exact match</em>
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				<label for="digitalObjectId">Digital Object ID</label>
			</td>
			<td colspan="2">
				<html:text property="digitalObjectId" size="30" styleId="digitalObjectId" />
				<em>exact match</em>
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				<label for="title">Publication Title</label>
			</td>
			<td colspan="2">
				<table class="invisibleTable" summary="layout">
					<tr>
						<td><label for="titleOperand" style="display:none">Title Operand</label>
							<html:select property="titleOperand" styleId="titleOperand">
								<html:options collection="stringOperands" property="value"
									labelProperty="label" />
							</html:select>
						</td>
						<td align="left">
							<html:text property="title" size="80" styleId="title" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				<label for="authorsStr">Authors</label>
			</td>
			<td colspan="2">
				<html:textarea property="authorsStr" cols="77" rows="3"
					styleId="authorsStr" />
				<br />
				<em>enter one author per line</em>
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				<label for="keywordsStr">Keywords</label>
			</td>
			<td colspan="2">
				<html:textarea property="keywordsStr" cols="77" rows="3"
					styleId="keywordsStr" />
				<br />
				<em>enter one keyword per line</em>
			</td>
		</tr>
	</table>
	<br>
	<table width="100%" align="center" class="submissionView" summary="layout">
		<tr>
			<td class="cellLabel">
				<label for="sampleName">Sample Name</label>
			</td>
			<td valign="top" colspan="5">
				<html:text property="sampleName" size="80" styleId="sampleName" />
				<em>exact match</em>
			</td>
		</tr>
		<tr>
			<td class="cellLabel">
				<label for="nanomaterialEntityTypes">Composition
				<br>
				Nanomaterial Entity</label>
			</td>
			<td>
				<html:select property="nanomaterialEntityTypes"
					styleId="nanomaterialEntityTypes" multiple="true" size="4">
					<html:options name="nanomaterialEntityTypes" />
				</html:select>
			</td>
			<td class="cellLabel">
				<label for="functionalizingEntityTypes">Composition
				<br>
				Functionalizing Entity</label>
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
				<html:select property="functionTypes" styleId="functionTypes"
					multiple="true" size="3">
					<html:options name="functionTypes" />
				</html:select>
			</td>
		</tr>
	</table>
	<br>
	<c:set var="dataType" value="publication" />
	<c:set var="resetOnclick" value="this.form.reset();" />
	<c:set var="searchOnclick" value="searchPublication();" />
	<%@include file="../bodySearchButtons.jsp"%>
</html:form>
<!--_____ main content ends _____-->
