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
<c:choose>
	<c:when test="${param.summary eq 'true'}">
		<table class="summaryViewNoGrid" align="left">
			<tr>
				<th scope="col" class="cellLabel" scope="col">
					Type
				</th>
				<th scope="col" class="cellLabel" scope="col">
					Isotype
				</th>
				<th scope="col" class="cellLabel" scope="col">
					Species
				</th>
			</tr>
			<tr>
				<td>
					<c:out value="${functionalizingEntity.antibody.type}"/>
				</td>
				<td>
					<c:out value="${functionalizingEntity.antibody.isotype}"/>
				</td>
				<td>
					<c:out value="${functionalizingEntity.antibody.species}"/>
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table width="100%" align="center" class="submissionView" summary="layout">
			<tr>
				<th colspan="6">
					Antibody Properties
				</th>
			</tr>
			<tr>
				<td class="cellLabel">
					<label for="antibodyType">Type</label>
				</td>
				<td>
					<div id="antibodyTypePrompt">
						<select name="functionalizingEntity.antibody.type"
							id="antibodyType"
							onchange="javascript:callPrompt('Type', 'antibodyType', 'antibodyTypePrompt');">
							<option value="" />
								<c:forEach var="type" items="${antibodyTypes}">
									<c:choose>
										<c:when
											test="${type eq compositionForm.map.functionalizingEntity.antibody.type}">
											<option value="${type}" selected="selected">
												<c:out value="${type}"/>
											</option>
										</c:when>
										<c:otherwise>
											<option value="${type}">
												<c:out value="${type}"/>
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							<option value="other">
								[other]
							</option>
						</select>
					</div>
				</td>
				<td class="cellLabel">
					<label for="antibodyIsotype">Isotype</label>
				</td>
				<td>
					<div id="isotypePrompt">
						<select name="functionalizingEntity.antibody.isotype"
							id="antibodyIsotype"
							onchange="javascript:callPrompt('Isotype', 'antibodyIsotype', 'isotypePrompt');">
							<option value="" />
								<c:forEach var="type" items="${antibodyIsotypes}">
									<c:choose>
										<c:when
											test="${type eq compositionForm.map.functionalizingEntity.antibody.isotype}">
											<option value="${type}" selected="selected">
												<c:out value="${type}"/>
											</option>
										</c:when>
										<c:otherwise>
											<option value="${type}">
												<c:out value="${type}"/>
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							<option value="other">
								[other]
							</option>
						</select>
					</div>
				</td>
				<td class="cellLabel">
					<label for="antibodySpecies">Species</label>
				</td>
				<td>
					<div id="speciesPrompt">
						<select name="functionalizingEntity.antibody.species"
							id="antibodySpecies"
							onchange="javascript:callPrompt('Species', 'species', 'speciesPrompt');">
							<option value="" />
								<c:forEach var="species" items="${speciesTypes}">
									<c:choose>
										<c:when
											test="${species eq compositionForm.map.functionalizingEntity.antibody.species}">
											<option value="${species}" selected="selected">
												<c:out value="${species}"/>
											</option>
										</c:when>
										<c:otherwise>
											<option value="${species}">
												<c:out value="${species}"/>
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						</select>
					</div>
				</td>
			</tr>
		</table>
		<br>
	</c:otherwise>
</c:choose>