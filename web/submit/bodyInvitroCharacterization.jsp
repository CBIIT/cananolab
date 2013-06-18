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

<html:form action="/${actionName}">
	<table width="100%" align="center">
		<tr>
			<td>
				<h4>
					<br>
					${pageTitle}
				</h4>
			</td>
			<td align="right" width="15%">
				<logic:equal name="formType" value="Toxicity">
					<a href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=toxicity_help')" class="helpText">Help</a>
				</logic:equal>
				<logic:equal name="formType" value="Cytotoxicity">
					<a href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=cytoxicity_help')" class="helpText">Help</a>
				</logic:equal>
				<logic:equal name="formType" value="Immunotoxicity">
					<a href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=immunotoxicity_help')" class="helpText">Help</a>
				</logic:equal>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<h5 align="center">
					<bean:write name="${formName}" property="particleName" />
					<bean:write name="${formName}" property="particleType" />
				</h5>
			</td>
		</tr>
		<tr>
			<td colspan="2">			
				<jsp:include page="/bodyMessage.jsp?bundle=submit" />
				<jsp:include page="bodySharedCharacterizationSummary.jsp?formName=${formName}" />
				<jsp:include page="bodySharedCharacterizationInstrument.jsp?formName=${formName}" />				
				<logic:present name="detailPage">
					<jsp:include page="${detailPage}">
						<jsp:param name="formName" value="${formName}"/>
					</jsp:include>
				</logic:present>
				<table class="topBorderOnly" cellspacing="0" cellpadding="3" width="100%" align="center" summary="" border="0">
					<tbody>
						<tr class="topBorder">
							<td class="formTitle" colspan="4">
								<div align="justify">
									Characterization File Information
								</div>
							</td>
						</tr>
						<tr>
							<td class="leftLabel">
								<strong>Number of Files</strong>
							</td>
							<td class="label">
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<html:text property="achar.numberOfDerivedBioAssayData" />
									</c:when>
									<c:otherwise>
										<bean:write name="${formName}" property="achar.numberOfDerivedBioAssayData" />&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							<td class="rightLabel" colspan="2">
								&nbsp;
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<input type="button" onclick="javascript:updateCharts(this.form, '${actionName}')" value="Update Files">
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="completeLabel" colspan="4">
								<logic:iterate name="${formName}" property="achar.derivedBioAssayDataList" id="derivedBioAssayData" indexId="chartInd">
									<table class="topBorderOnly" cellspacing="0" cellpadding="3" width="100%" align="center" summary="" border="0">
										<tbody>
											<tr class="topBorder">
												<td class="formSubTitle" colspan="4">
													<div align="justify">
														File ${chartInd+1}
													</div>
												</td>
											</tr>											
											<jsp:include page="bodySharedCharacterizationFile.jsp?chartInd=${chartInd}&formName=${formName}&actionName=${actionName}" />
											 <!-- 
											<tr>
												<td class="leftLabel">
													<strong>Number of Data Points</strong>
												</td>
												<td class="label">
													<c:choose>
														<c:when test="${canUserSubmit eq 'true'}">
															<html:text property="achar.derivedBioAssayDataList[${chartInd}].numberOfDataPoints" />
														</c:when>
														<c:otherwise>
															${derivedBioAssayData.numberOfDataPoints}&nbsp;
														</c:otherwise>
													</c:choose>
												</td>
												<td class="rightLabel" colspan="2">
													&nbsp;
													<c:choose>
														<c:when test="${canUserSubmit eq 'true'}">
															<input type="button" onclick="javascript:updateChartDataPoints(this.form, '${actionName}', ${chartInd})" value="Update Data Points">
														</c:when>
													</c:choose>
												</td>
											</tr>
											<tr>
												<td class="completeLabel" colspan="4">
													<jsp:include page="bodyDerivedBioAssayDatum.jsp">
														<jsp:param name="chartNum" value="${chartInd}" />
													</jsp:include>
												</td>
											</tr> 
											-->
										</tbody>
									</table>
									<br>
								</logic:iterate>
							</td>
						</tr>
				</table>
				<jsp:include page="bodySharedCharacterizationSubmit.jsp" />
			</td>
		</tr>
	</table>
</html:form>

<script language="JavaScript">
<!--//
  /* populate a hashtable containing condition type units */
  var conditionTypeUnits=new Array();    
  <c:forEach var="item" items="${allConditionTypeUnits}">
    var conditionUnits=new Array();
    <c:forEach var="conditionUnit" items="${allConditionTypeUnits[item.key]}" varStatus="count">  		
  		conditionUnits[${count.index}]='${conditionUnit}';
    </c:forEach>
    conditionTypeUnits['${item.key}'] = conditionUnits;
  </c:forEach> 
//-->
</script>

