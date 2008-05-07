<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="css/caLab.css">
		<script type="text/javascript" src="javascript/script.js"></script>
	</head>
	<body onload="window.print();self.close()">
		<table width="100%" align="center">
			<tr>
				<td colspan="2">
					<table width="100%" border="1" align="center" cellpadding="3"
						cellspacing="0" class="topBorderOnly" summary="">
						<tr>
							<th class="formTitle" colspan="2" align="center">
								${particleName}
							</th>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								View Title - Characterization Source
							</th>
							<td class="rightLabel">
								${ characterizationForm.map.achar.viewTitle} - ${
								characterizationForm.map.achar.characterizationSource}
							</td>
						</tr>
						<c:if
							test="${!empty characterizationForm.map.achar.description}">
							<tr>
								<th class="leftLabel" valign="top">
									Description
								</th>
								<td class="rightLabel">
									${characterizationForm.map.achar.description}
								</td>
							</tr>
						</c:if>
						<c:if
							test="${!empty characterizationForm.map.achar.protocolFileBean.domainFile.id}">
							<tr>
								<th class="leftLabel" valign="top">
									Protocol
								</th>
								<td class="rightLabel" valign="top">
									<c:choose>
										<c:when
											test="${characterizationForm.map.achar.protocolFileBean.hidden eq 'true'}">
									Private protocol
								</c:when>
										<c:otherwise>
							${characterizationForm.map.achar.protocolFileBean.displayName}&nbsp;
			${characterizationForm.map.achar.protocolFileBean.domainFile.uri}
								</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
						<c:if
							test="${!empty characterizationForm.map.achar.instrumentConfiguration && !empty characterizationForm.map.achar.instrumentConfiguration.instrument.type}">
							<tr>
								<th class="leftLabel" valign="top">
									Instrument
								</th>
								<td class="rightLabel" valign="top">
									${characterizationForm.map.achar.instrumentConfiguration.instrument.type}-
									${characterizationForm.map.achar.instrumentConfiguration.instrument.manufacturer}
									&nbsp;
									<c:if
										test="${!empty characterizationForm.map.achar.instrumentConfiguration.instrument.abbreviation}">
							(${characterizationForm.map.achar.instrumentConfiguration.instrument.abbreviation})
							</c:if>
									<c:if
										test="${!empty characterizationForm.map.achar.instrumentConfiguration.description}">
										<br>
										<br>
							${characterizationForm.map.achar.instrumentConfiguration.description}
							</c:if>
								</td>
							</tr>
						</c:if>
						<logic:iterate name="characterizationForm"
							property="achar.derivedBioAssayDataList" id="derivedBioAssayData"
							indexId="fileInd">
							<c:if test="${!empty derivedBioAssayData.labFileBean.domainFile.description}">
								<tr>
									<th class="leftLabel" valign="top">
										Characterization File #${fileInd+1} Description
									</th>
									<td class="rightLabel" valign="top">
										${derivedBioAssayData.labFileBean.domainFile.description}&nbsp;
									</td>
								</tr>
							</c:if>
							<c:if
								test="${!empty derivedBioAssayData && !empty derivedBioAssayData.labFileBean.domainFile.uri}">
								<tr>
									<th class="leftLabel" valign="top">
										Characterization File #${fileInd+1}
									</th>
									<td class="rightLabel" valign="top">
										<c:if test="${!empty derivedBioAssayData.labFileBean.domainFile.type}">
								${derivedBioAssayData.type}
								<br>
										</c:if>
										<c:choose>
											<c:when test="${derivedBioAssayData.labFileBean.hidden eq 'true'}">
									Private file
								</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${derivedBioAssayData.labFileBean.image eq 'true'}">
														<img
															src="${actionName}.do?dispatch=download&amp;fileId=${derivedBioAssayData.labFileBean.domainFile.id}"
															border="0">
													</c:when>
													<c:otherwise>${derivedBioAssayData.labFileBean.domainFile.title}
											</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:if>
							<c:if test="${!empty derivedBioAssayData.datumList}">
								<tr>
									<th class="completeLabel" align="left" colspan="2">
										Characterization Derived Data #1
										<br>
										<br>
										<table border="0" cellpadding="3"
											cellspacing="0">
											<tr>
												<logic:iterate id="datum"
													name="characterizationForm"
													property="achar.derivedBioAssayDataList[${fileInd}].datumList"
													indexId="datumInd">
													<th class="whiteBorderLessLabel">
														${datum.name}
														<c:if test="${!empty datum.unit}">(${datum.valueUnit})</c:if>
													</th>
												</logic:iterate>
											</tr>
											<tr>
												<logic:iterate id="datum"
													name="characterizationForm"
													property="achar.derivedBioAssayDataList[${fileInd}].datumList"
													indexId="datumInd">
													<td class="whiteBorderLessLabel">
														${datum.value}
													</td>
												</logic:iterate>
											</tr>
										</table>
									</th>
								</tr>
							</c:if>
							<p style="page-break-before: always"> 
						</logic:iterate>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
