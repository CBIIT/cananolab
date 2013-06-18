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
<script language="JavaScript">
	<!-- //
		var cal1 = new calendar2(document.getElementById('charDate'));
	    cal1.year_scroll = true;
		cal1.time_comp = false;
		cal1.context = '${pageContext.request.contextPath}';
  	//-->
</script>

<%--TODO: create online help topic for this page.--%>
<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="Search Study" />
	<jsp:param name="topic" value="submit_study_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
</jsp:include>
<html:form action="searchStudy">
	<jsp:include page="/bodyMessage.jsp?bundle=study" />
	<table width="100%" align="center" class="submissionView">
		<tr>
			<td width="15%" class="cellLabel">
				Study Name
			</td>
			<td>
				<html:select property="studyNameOperand" styleId="studyNameOperand">
					<html:options collection="stringOperands" property="value"
						labelProperty="label" />
				</html:select>
			</td>
			<td >
				<html:text property="studyName" size="60" />
				<!-- input type="text" size="80" value="Efficacy of nanoparticle"/-->
			</td>
		</tr>

		<tr>
			<td class="cellLabel" width="15%">
				Study Point of Contact
			</td>
			<td >
				<!-- input type="text" size="100"/-->

				<html:select property="pocOperand" styleId="pocOperand">
					<html:options collection="stringOperands" property="value"
						labelProperty="label" />
				</html:select>
			</td>
			<td>
					<html:text property="studyPointOfContact" size="60" />
					<br />
					<em>searching organization name or person name</em>
			</td>
		</tr>
		<tr>
			<td width="15%" class="cellLabel">
				Study Type
			</td>
			<td colspan="2">
				<html:select styleId="studyType"
					property="studyType" >
					<html:option value=""/>
					<html:options name="studyTypes" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td width="15%" class="cellLabel">
				Study Design Type
			</td>
			<td colspan="2">
				<html:select styleId="studyDesignType"
					property="studyDesignType" >
					<html:option value=""/>
					<html:options name="studyDesignTypes" />
				</html:select>				
			</td>
		</tr>
		<tr>
			<td width="15%" class="cellLabel">
				Animal Study?
			</td>
			<td colspan="2">
				<html:radio styleId="option1" property="isAnimalStudy" value="true" />
				Yes
				<html:radio styleId="option2" property="isAnimalStudy" value="false" />
				No
			</td>
		</tr>

		<tr>
			<td width="15%" class="cellLabel">
				Sample Name
			</td>
			<td>
				<html:select property="sampleNameOperand" styleId="sampleNameOperand">
					<html:options collection="stringOperands" property="value"
						labelProperty="label" />
				</html:select>
			</td>
			<td >
				<html:text property="sampleName" size="60" />
				<!-- input type="text" size="80" value="Efficacy of nanoparticle"/-->
			</td>
		</tr>
		<tr>
			<td width="15%" class="cellLabel">
				Disease
			</td>
			<td>
				<html:select property="diseaseOperand" styleId="diseaseOperand">
					<html:options collection="stringOperands" property="value"
						labelProperty="label" />
				</html:select>
			</td>
			<td >
				<html:text property="disease" size="60" />
				<!-- input type="text" size="80" value="Efficacy of nanoparticle"/-->
			</td>
		</tr>
		<tr>
			<td width="15%" class="cellLabel">
				Keywords
			</td>
			<td colspan="2">
				<html:textarea property="text" rows="3" cols="57" />
				<br>
				<em>searching study descriptions and outcomes</em>
				<br>
				<em>enter one keyword per line</em>
			</td>
		</tr>
		<tr>
			<td width="15%" class="cellLabel">
				Study Owner
			</td>
			<td>
				<html:select property="ownerOperand" styleId="ownerOperand">
					<html:options collection="stringOperands" property="value"
						labelProperty="label" />
				</html:select>
			</td>
			<td >
				<html:text property="studyOwner" size="60" />
				<!-- input type="text" size="80" value="Efficacy of nanoparticle"/-->
			</td>
		</tr>
	</table>
	<br>
	<table width="100%" border="0" align="center" cellpadding="3"
		cellspacing="0" class="topBorderOnly" summary="">
		<tr>
			<td width="30%">
				<table width="498" height="15" border="0" align="right"
					cellpadding="4" cellspacing="0">
					<tr>
						<td width="490" height="15">
							<div align="right">
								<div align="right">
									<input type="reset" value="Clear"
										onclick="javascript:location.href='searchStudy.do?dispatch=setup&page=0'">
									<input type="hidden" name="dispatch" value="search">
									<input type="hidden" name="page" value="2">
									<html:submit value="Search"/>
								</div>
							</div>
						</td>
					</tr>
				</table>
				<div align="right"></div>
			</td>
		</tr>
	</table>
</html:form>
