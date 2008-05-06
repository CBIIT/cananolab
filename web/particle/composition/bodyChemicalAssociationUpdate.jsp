<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
<!--//
function confirmDeletion()
{
	answer = confirm("Are you sure you want to delete the chemical association?")
	if (answer !=0)
	{
		this.document.forms[0].dispatch.value="delete";
		this.document.forms[0].submit(); 
		return true;
	}
}
//-->
</script>
<c:choose>
	<c:when
		test="${! empty chemicalAssociationForm.map.assoc.attachment.id }">
		<c:set var="style" value="" />
	</c:when>
	<c:otherwise>
		<c:set var="style" value="display:none" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when
		test="${! empty chemicalAssociationForm.map.assoc.associatedElementA.composingElement.id }">
		<c:set var="ceStyleA" value="" />
	</c:when>
	<c:otherwise>
		<c:set var="ceStyleA" value="display:none" />

	</c:otherwise>
</c:choose>
<c:choose>
	<c:when
		test="${! empty chemicalAssociationForm.map.assoc.associatedElementB.composingElement.id }">
		<c:set var="ceStyleB" value="" />
	</c:when>
	<c:otherwise>
		<c:set var="ceStyleB" value="display:none" />
	</c:otherwise>
</c:choose>
<html:form action="/chemicalAssociation" enctype="multipart/form-data">
	<table width="100%" align="center">
		<tr>
			<td>
				<h4>
					${particleName} Sample Composition - Chemical Association
				</h4>
			</td>
			<td align="right" width="15%">
				<a
					href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=composition_help')"
					class="helpText">Help</a>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<h5 align="center">
					<%--					Entity#1:Dendrimer--%>
				</h5>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<jsp:include page="/bodyMessage.jsp?bundle=particle" />
				<table width="100%" border="0" align="center" cellpadding="3"
					cellspacing="0" class="topBorderOnly" summary="" id="summary">
					<tr class="topBorder">
						<td class="formTitle" colspan="4">
							<div align="justify">
								Chemical Association Information
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftLabel">
							<strong>Association Type</strong>
						</td>
						<td class="label">
							<c:choose>
								<c:when test="${param.dispatch eq 'setup'}">
									<html:select styleId="assoType" property="assoc.type"
										onchange="javascript:callPrompt('Association Type', 'assoType');
											displayBondType();">
										<option value=""></option>
										<html:options name="chemicalAssociationTypes" />
										<option value="other">
											[Other]
										</option>
									</html:select>
								</c:when>
								<c:otherwise>
									${chemicalAssociationForm.map.assoc.type}
								</c:otherwise>
							</c:choose>
						</td>
						<td class="label" valign="top">
							&nbsp;
							<Strong id="bondTypeTitle" style="${style }">Bond Type</Strong>
						</td>
						<td class="rightLabel">
							&nbsp;
							<span id="bondTypeLine" style="${style}"><html:select
									styleId="bondType" property="assoc.attachment.bondType"
									onchange="javascript:callPrompt('Bond Type', 'bondType');">
									<option value=""></option>
									<html:options name="bondTypes" />
									<option value="other">
										[Other]
									</option>
								</html:select> </span>
						</td>
					</tr>
					<tr>
						<td class="completeLabel" colspan="4">
							<div id="assocEleBlockA" class="assocEleBlock">
								<ul>
									<li>
										<strong>Element</strong>
									</li>
									<li>
										<html:select styleId="compositionTypeA"
											property="assoc.associatedElementA.compositionType"
											onchange="getAssociatedElementOptions('compositionTypeA', 'entityTypeA', 'compEleA')">
											<option value=""></option>
											<html:options name="associationCompositionTypes" />
										</html:select>
									</li>
									<li>
										<span class="indented1" id="entityA"><html:select
												styleId="entityTypeA"
												property="assoc.associatedElementA.entityId"
												onchange="getAssociatedComposingElements('compositionTypeA', 'entityTypeA', 'compEleTypeA', 'compEleA');
														setEntityDisplayName('entityTypeA', 'entityDisplayA');">
												<c:if
													test="${! empty chemicalAssociationForm.map.assoc.domainAssociation.id }">
													<c:forEach var="dataLink" items="${entityListA}">
														<c:choose>
															<c:when
																test="${dataLink.dataId eq chemicalAssociationForm.map.assoc.associatedElementA.entityId }">
																<option value="${dataLink.dataId }" selected>
																	${dataLink.dataDisplayType}
																</option>
															</c:when>
															<c:otherwise>
																<option value="${dataLink.dataId }">
																	${dataLink.dataDisplayType}
																</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
											</html:select> </span>
										<html:hidden styleId="entityDisplayA"
											property="assoc.associatedElementA.entityDisplayName"
											value="${chemicalAssociationForm.map.assoc.associatedElementA.entityDisplayName}" />
									</li>
									<li>
										<span class="indented3" id="compEleA" style="${ceStyleA}">
											<html:select styleId="compEleTypeA"
												property="assoc.associatedElementA.composingElement.id">
												<c:if test="${!empty ceListA}">
													<c:forEach var="ce" items="${ceListA}">
														<c:choose>
															<c:when
																test="${ce.domainComposingElement.id eq chemicalAssociationForm.map.assoc.associatedElementA.composingElement.id }">
																<option value="${ce.domainComposingElement.id }"
																	selected>
																	${ce.displayName }
																</option>
															</c:when>
															<c:otherwise>
																<option value="${ce.domainComposingElement.id }">
																	${ce.displayName }
																</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
											</html:select> </span>
									</li>
								</ul>
							</div>
							<div id="assocEleLinkBlock" class="arrowBlock">
								<img src="images/arrow.small.left.gif" />
								<img src="images/arrow.small.right.gif" />
								<br>
								<strong>associated with</strong>
							</div>
							<div id="assocEleBlockB" class="assocEleBlock">
								<ul>
									<li>
										<strong>Element</strong>
									</li>
									<li>
										<html:select styleId="compositionTypeB"
											property="assoc.associatedElementB.compositionType"
											onchange="getAssociatedElementOptions('compositionTypeB', 'entityTypeB', 'compEleB')">
											<option value="" />
												<html:options name="associationCompositionTypes" />
										</html:select>
									</li>
									<li>
										<span class="indented1" id="entityB"><html:select
												styleId="entityTypeB"
												property="assoc.associatedElementB.entityId"
												onchange="getAssociatedComposingElements('compositionTypeB', 'entityTypeB', 'compEleTypeB', 'compEleB');
															setEntityDisplayName('entityTypeB', 'entityDisplayB');">
												<c:if
													test="${! empty chemicalAssociationForm.map.assoc.domainAssociation.id }">
													<c:forEach var="dataLink" items="${entityListB}">
														<c:choose>
															<c:when
																test="${dataLink.dataId eq chemicalAssociationForm.map.assoc.associatedElementB.entityId }">
																<option value="${dataLink.dataId }" selected>
																	${dataLink.dataDisplayType}
																</option>
															</c:when>
															<c:otherwise>
																<option value="${dataLink.dataId }">
																	${dataLink.dataDisplayType}
																</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
											</html:select> </span>
										<html:hidden styleId="entityDisplayB"
											property="assoc.associatedElementB.entityDisplayName"
											value="${chemicalAssociationForm.map.assoc.associatedElementB.entityDisplayName}" />
									</li>
									<li>
										<span class="indented3" id="compEleB" style="${ceStyleB}"><html:select
												styleId="compEleTypeB"
												property="assoc.associatedElementB.composingElement.id">

												<c:if test="${!empty ceListB}">
													<c:forEach var="ce" items="${ceListB}">
														<c:choose>
															<c:when
																test="${ce.domainComposingElement.id eq chemicalAssociationForm.map.assoc.associatedElementB.composingElement.id }">
																<option value="${ce.domainComposingElement.id }"
																	selected>
																	${ce.displayName }
																</option>
															</c:when>
															<c:otherwise>
																<option value="${ce.domainComposingElement.id }">
																	${ce.displayName }
																</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
											</html:select> </span>
									</li>
								</ul>
							</div>
						</td>
					</tr>

					<tr>
						<td class="leftLabel" valign="top">
							<strong>Association Description</strong>
						</td>
						<td class="rightLabel" colspan="3">
							<html:textarea property="assoc.description" rows="2" cols="60" />
						</td>
					</tr>
				</table>
				<%-- File Information --%>
				<br>
				<table class="topBorderOnly" cellspacing="0" cellpadding="3"
					width="100%" align="center" summary="" border="0">
					<tbody>
						<tr class="topBorder">
							<td class="formTitle" colspan="4">
								<div align="justify" id="peFileTitle">
									Chemical Association File Information
								</div>
							</td>
						</tr>
						<tr>
							<td class="completeLabel" colspan="4">
								<table border="0" width="100%">
									<tr>
										<td valign="bottom">
											<a href="#"
												onclick="javascript:addComponent(chemicalAssociationForm, 'chemicalAssociation', 'addFile'); return false;">
												<span class="addLink">Add File</span> </a>
										</td>
										<td id="fileTd">

											<logic:iterate name="chemicalAssociationForm"
												property="assoc.files" id="assocFile" indexId="fileInd">
												<jsp:include page="/particle/bodyLoadFileUpdate.jsp">
													<jsp:param name="fileInd" value="${fileInd}" />
													<jsp:param name="form" value="chemicalAssociationForm" />
													<jsp:param name="action" value="chemicalAssociation" />
													<jsp:param name="fileBean" value="assoc.files[${fileInd}]" />
													<jsp:param name="fileId"
														value="${chemicalAssociationForm.map.assoc.files[fileInd].domainFile.id}" />
													<jsp:param name="fileUri"
														value="${chemicalAssociationForm.map.assoc.files[fileInd].domainFile.uri}" />
													<jsp:param name="fileTitle"
														value="${chemicalAssociationForm.map.assoc.files[fileInd].domainFile.title}" />
													<jsp:param name="fileHidden"
														value="${chemicalAssociationForm.map.assoc.files[fileInd].hidden}" />
													<jsp:param name="fileImage"
														value="${chemicalAssociationForm.map.assoc.files[fileInd].image}" />
												</jsp:include>

												<br>
											</logic:iterate>
										</td>
									</tr>
								</table>
							</td>
						</tr>
				</table>

				<br>
				<table width="100%" border="0" align="center" cellpadding="3"
					cellspacing="0" class="topBorderOnly" summary="">
					<tr>
						<td width="30%">
							<span class="formMessage"> </span>
							<br>
							<c:choose>
								<c:when
									test="${param.dispatch eq 'setupUpdate' && canUserDelete eq 'true'}">
									<table height="32" border="0" align="left" cellpadding="4"
										cellspacing="0">
										<tr>
											<td height="32">
												<div align="left">
													<input type="button" value="Delete"
														onclick="confirmDeletion();">
												</div>
											</td>
										</tr>
									</table>
								</c:when>
							</c:choose>
							<table width="498" height="32" border="0" align="right"
								cellpadding="4" cellspacing="0">
								<tr>
									<td width="490" height="32">
										<div align="right">
											<div align="right">
												<input type="reset" value="Reset" onclick="">
												<input type="hidden" name="dispatch" value="create">
												<input type="hidden" name="page" value="2">
												<input type="hidden" name="submitType"
													value="${param.submitType}" />
												<html:hidden property="particleId"
													value="${param.particleId}" />
												<html:submit />
											</div>
										</div>
									</td>
								</tr>
							</table>
							<div align="right"></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>
