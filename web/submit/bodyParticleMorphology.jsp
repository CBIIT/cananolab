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
<script type="text/javascript" src="javascript/editableDropDown.js"></script>

<html:form action="/nanoparticleMorphology">
	<table width="100%" align="center">
		<tr>
			<td>
				<h4>
					<br>
					Physical Characterization - Morphology
				</h4>
			</td>
			<td align="right" width="15%">
				<a
					href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=nano_morphology_help')"
					class="helpText">Help</a>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<h5 align="center">
					${nanoparticleMorphologyForm.map.particleName}
					(${nanoparticleMorphologyForm.map.particleType})
				</h5>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<jsp:include page="/bodyMessage.jsp?bundle=submit" />
				<jsp:include
					page="bodySharedCharacterizationSummary.jsp?formName=nanoparticleMorphologyForm" />
				<jsp:include
					page="bodySharedCharacterizationInstrument.jsp?formName=nanoparticleMorphologyForm" />
				<table class="topBorderOnly" cellspacing="0" cellpadding="3"
					width="100%" align="center" summary="" border="0">
					<tbody>
						<tr class="topBorder">
							<td class="formTitle" colspan="6">
								<div align="justify">
									Morphology Property
								</div>
							</td>
						</tr>
						<tr>
							<td class="leftLabel">
								<strong>Type *</strong>
							</td>
							<td class="rightLabel">
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<html:select property="achar.type"
											onkeydown="javascript:fnKeyDownHandler(this, event);"
											onkeyup="javascript:fnKeyUpHandler_A(this, event); return false;"
											onkeypress="javascript:return fnKeyPressHandler_A(this, event);"
											onchange="fnChangeHandler_A(this, event);">
											<option value="">
												--?--
											</option>
											<html:options name="allMorphologyTypes" />
										</html:select>
									</c:when>
									<c:otherwise>
										${nanoparticleMorphologyForm.map.achar.type}&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
				<br />
				<%-- size characterization specific --%>
				<table class="topBorderOnly" cellspacing="0" cellpadding="3"
					width="100%" align="center" summary="" border="0">
					<tbody>
						<tr class="topBorder">
							<td class="formTitle" colspan="4">
								<div align="justify">
									Characterization Information
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
						${nanoparticleMorphologyForm.map.achar.numberOfDerivedBioAssayData}&nbsp;
					</c:otherwise>
								</c:choose>
							</td>
							<td class="rightLabel" colspan="2">
								&nbsp;
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<input type="button"
											onclick="javascript:updateCharts(this.form, 'nanoparticleMorphology')"
											value="Update Files">
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="completeLabel" colspan="4">
								<logic:iterate name="nanoparticleMorphologyForm"
									property="achar.derivedBioAssayDataList"
									id="derivedBioAssayData" indexId="chartInd">
									<table class="topBorderOnly" cellspacing="0" cellpadding="3"
										width="100%" align="center" summary="" border="0">
										<tbody>
											<tr class="topBorder">
												<td class="formSubTitle" colspan="4">
													<div align="justify">
														File ${status.index+1}
													</div>
												</td>
											</tr>											
											<jsp:include
												page="bodySharedCharacterizationFile.jsp?chartInd=${chartInd}&formName=nanoparticleMorphologyForm&actionName=nanoparticleMorphology" />
										</tbody>
									</table>
									<br>
								</logic:iterate>
							</td>
						</tr>
				</table>
				<%-- end of size characterization specific --%>
				<jsp:include page="bodySharedCharacterizationSubmit.jsp" />
			</td>
		</tr>
	</table>
</html:form>
