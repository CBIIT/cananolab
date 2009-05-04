<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:choose>
	<c:when test="${param.summary eq 'true'}">
		<table class="summaryViewLayer4" align="center" width="95%">
			<tr>
				<th>
					Type
				</th>
				<th>
					Isotype
				</th>
				<th>
					Species
				</th>
			</tr>
			<tr>
				<td>
					${functionalizingEntity.antibody.type}
				</td>
				<td>
					${functionalizingEntity.antibody.isotype}
				</td>
				<td>
					${functionalizingEntity.antibody.species}
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table width="100%" align="center" class="submissionView">
			<tr>
				<th colspan="6">
					Antibody Properties
				</th>
			</tr>
			<tr>
				<td class="cellLabel">
					Type
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
											<option value="${type}" selected>
												${type}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${type}">
												${type}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							<option value="other">
								[Other]
							</option>
						</select>
					</div>
				</td>
				<td>
					Isotype
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
											<option value="${type}" selected>
												${type}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${type}">
												${type}
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							<option value="other">
								[Other]
							</option>
						</select>
					</div>
				</td>
				<td>
					Species
				</td>
				<td>
					<div id="speciesPrompt">
						<select name="functionalizingEntity.antibody.species"
							id="antibodySpecies"
							onchange="javascript:callPrompt('Species', 'species', 'speciesPrompt');">
							<option value="" />
								<c:forEach var="species" items="${antibodySpecies}">
									<c:choose>
										<c:when
											test="${species eq compositionForm.map.functionalizingEntity.antibody.species}">
											<option value="${species}" selected>
												${species}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${species}">
												${species}
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