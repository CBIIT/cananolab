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

<html:form action="/nanoparticleShape">
	<table width="100%" align="center">
		<tr>
			<td>
				<h4>
					<br>
					Physical Characterization - Shape
				</h4>
			</td>
			<td align="right" width="15%">
				<a
					href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=nano_shape_help')"
					class="helpText">Help</a>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<h5 align="center">
					${nanoparticleShapeForm.map.particleName}
					(${nanoparticleShapeForm.map.particleType})
				</h5>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<jsp:include page="/bodyMessage.jsp?bundle=submit" />
				<jsp:include
					page="bodySharedCharacterizationSummary.jsp?formName=nanoparticleShapeForm" />
				<jsp:include
					page="bodySharedCharacterizationInstrument.jsp?formName=nanoparticleShapeForm" />
				<table class="topBorderOnly" cellspacing="0" cellpadding="3"
					width="100%" align="center" summary="" border="0">
					<tbody>
						<tr class="topBorder">
							<td class="formTitle" colspan="6">
								<div align="justify">
									Shape Property
								</div>
							</td>
						</tr>
						<tr>
							<td class="leftLabel">
								<strong>Type *</strong>
							</td>
							<td class="label">
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
											<html:options name="allShapeTypes" />
										</html:select>
									</c:when>
									<c:otherwise>
										${nanoparticleShapeForm.map.achar.type}&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							<td class="rightLabel" colspan="2">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="leftLabel">
								<strong>Minimum Dimension </strong>
							</td>
							<td class="label">
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<html:text property="achar.minDimension" />
										${nanoparticleShapeForm.map.achar.minDimensionUnit}&nbsp;
									</c:when>
									<c:otherwise>
										${nanoparticleShapeForm.map.achar.minDimension}&nbsp;
										${nanoparticleShapeForm.map.achar.minDimensionUnit}&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							<td class="label">
								<strong>Maximum Dimension </strong>
							</td>
							<td class="rightLabel">
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<html:text property="achar.maxDimension" />
										${nanoparticleShapeForm.map.achar.maxDimensionUnit}&nbsp;
									</c:when>
									<c:otherwise>
										${nanoparticleShapeForm.map.achar.maxDimension}&nbsp;
										${nanoparticleShapeForm.map.achar.maxDimensionUnit}&nbsp;
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
										${nanoparticleShapeForm.map.achar.numberOfDerivedBioAssayData}&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							<td class="rightLabel" colspan="2">
								&nbsp;
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<input type="button"
											onclick="javascript:updateCharts(this.form, 'nanoparticleShape')"
											value="Update Files">
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="completeLabel" colspan="4">
								<logic:iterate name="nanoparticleShapeForm"
									property="achar.derivedBioAssayDataList"
									id="derivedBioAssayData" indexId="chartInd">
									<table class="topBorderOnly" cellspacing="0" cellpadding="3"
										width="100%" align="center" summary="" border="0">
										<tbody>
											<tr class="topBorder">
												<td class="formSubTitle" colspan="4">
													<div align="justify">
														File ${chartInd+1}
													</div>
												</td>
											</tr>
											<jsp:include
												page="bodySharedCharacterizationFile.jsp?chartInd=${chartInd}&formName=nanoparticleShapeForm&actionName=nanoparticleShape" />
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
