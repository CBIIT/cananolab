<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table id="summarySection4" width="95%" align="center"
	style="display: block" class="summaryViewLayer2">
	<tr>
		<th align="left">
			Composition File
		</th>
	</tr>
	<c:if test="${!empty compositionForm.map.comp.files}">
		<logic:iterate name="compositionForm" property="comp.files" id="file"
			indexId="ind">
			<c:set var="fileType" value="${file.domainFile.type}" />
			<c:if test="${!empty fileType}">
				<tr>
					<td>
						<div class="indented4">
							<table class="summaryViewLayer3" width="95%" align="center">
								<tr>
									<th valign="top" align="left" width="1000px" colspan="2">
										${fileType}
									</th>
								</tr>
								<tr>
									<td class="cellLabel">
										Title and Download Link
									</td>
									<td>
										<c:choose>
											<c:when test="${file.domainFile.uriExternal}">
												<a
													href="composition.do?dispatch=download&amp;fileId=${file.domainFile.id}&amp;location=${location}">
													${file.domainFile.uri}</a>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${file.image eq 'true'}">
						 				${file.domainFile.title}
										<br>
														<a href="#"
															onclick="popImage(event, 'composition.do?dispatch=download&amp;fileId=${file.domainFile.id}&amp;location=${location}', ${file.domainFile.id}, 100, 100)"><img
																src="composition.do?dispatch=download&amp;fileId=${file.domainFile.id}&amp;location=${location}"
																border="0" width="150"> </a>
													</c:when>
													<c:otherwise>
														<a
															href="composition.do?dispatch=download&amp;fileId=${file.domainFile.id}&amp;location=${location}">
															${file.domainFile.title}</a>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<c:if test="${!empty fn:trim(file.keywordsStr)}">
									<tr>
										<td class="cellLabel" width="20%">
											Keywords
										</td>
										<td>

											<c:out value="${fn:replace(file.keywordsStr, cr, '<br>')}"
												escapeXml="false" />

										</td>
									</tr>
								</c:if>
								<c:if test="${!empty fn:trim(file.domainFile.description)}">
									<tr>
										<td class="cellLabel" width="20%">
											Description
										</td>
										<td>

											<c:out
												value="${fn:replace(file.domainFile.description, cr, '<br>')}"
												escapeXml="false" />
										</td>
									</tr>
								</c:if>
							</table>
						</div>
					</td>
				</tr>
			</c:if>
		</logic:iterate>
	</c:if>
</table>
<div id="summarySeparator4">
	<br>
</div>



