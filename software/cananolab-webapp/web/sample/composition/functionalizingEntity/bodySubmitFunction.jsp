<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="subSubmissionView" width="85%" align="center">
	<tr>
		<th colspan="4" align="left">
			Function Info
		</th>
	</tr>
	<tr>
		<td class="cellLabel">
			<label for="functionType">Type *</label>
		</td>
		<td>
			<div id="functionTypePrompt">
				<html:select property="functionalizingEntity.theFunction.type"
					styleId="functionType"
					onchange="javascript:callPrompt('Function Type', 'functionType', 'functionTypePrompt');displayImageModality();displayTargets();">
					<option value=""></option>
					<html:options name="functionTypes" />
					<option value="other">
						[other]
					</option>
				</html:select>
			</div>
		</td>
		<td class="cellLabel">
			<div id="modalityLabel" style="display: none">
				<label for="imagingModality">Imaging Modality Type</label>
			</div>
		</td>
		<td>
			<div id="imagingModalityPrompt" style="display: none">
				<html:select
					property="functionalizingEntity.theFunction.imagingFunction.modality"
					onchange="javascript:callPrompt('Modality Type', 'imagingModality', 'imagingModalityPrompt');"
					styleId="imagingModality">
					<option value="" />
						<html:options name="modalityTypes" />
					<option value="other">
						[other]
					</option>
				</html:select>
			</div>
		</td>
	</tr>
	<tr>
		<td class="cellLabel">
			<label for="functionDescription">Description</label>
		</td>
		<td colspan="3">
			<html:textarea
				property="functionalizingEntity.theFunction.description" cols="50"
				rows="3" styleId="functionDescription" />
			<html:hidden
				property="functionalizingEntity.theFunction.id"
				styleId="functionId" />
		</td>
	</tr>
	<tr>
		<td class="cellLabel">
			<div id="targetLabel" style="display: none">
				Target
			</div>
		</td>
		<td colspan="3">
			<div id="targetSection" style="position: relative; display: none">
				<a style="display: block" id="addTarget"
					href="javascript:confirmAddNew('Target', 'Target', 'clearTarget()');">Add</a>
				<br>
				<table id="targetTable" class="editTableWithGrid" width="85%" style="display: none;">
					<tbody id="targetRows">
						<tr id="patternHeader">
							<th width="25%" class="cellLabel" scope="col">
								Type
							</th>
							<th width="25%" class="cellLabel" scope="col">
								<span id="antigenSpeciesHeader" style="display: none">Species</span>
							</th>
							<th width="25%" class="cellLabel" scope="col">
								Name
							</th>
							<th class="cellLabel" scope="col">
								Description
							</th>
							<th>
							</th>
						</tr>
						<tr id="pattern" style="display: none;">
							<td>
								<span id="targetTypeValue">Type</span>
							</td>
							<td>
								<span id="antigenSpeciesValue" style="display: none">Species</span>
							</td>
							<td>
								<span id="targetNameValue">Name</span>
							</td>
							<td>
								<span id="targetDescriptionValue">Description</span>
							</td>
							<td>
								<input class="noBorderButton" id="edit" type="button" value="Edit"
									onclick="editTarget(this.id);" />
							</td>
						</tr>
					</tbody>
				</table>
				<br>
				<html:hidden property="functionalizingEntity.theFunction.theTarget.id"
					styleId="targetId" />
				<table id="newTarget" style="display: none;" class="promptbox" width="85%">
					<tbody>
						<tr>
							<td class="cellLabel" width="15%">
								<label for="targetType">Type *</label>
							</td>
							<td>
								<div id="targetTypePrompt">
									<html:select
										property="functionalizingEntity.theFunction.theTarget.type"
										styleId="targetType"
										onchange="javascript:callPrompt('Target Type', 'targetType', 'targetTypePrompt');displaySpecies();">
										<option value=""></option>
										<html:options name="targetTypes" />
										<option value="other">
											[other]
										</option>
									</html:select>
								</div>
							</td>
						</tr>
						<tr>
							<td class="cellLabel" width="15%">
								<div id="antigenSpeciesLabel" style="display: none">
									<label for="antigenSpecies">Species</label>
								</div>
							</td>
							<td>
								<div id="antigenSpeciesPrompt" style="display: none">
									<html:select
										property="functionalizingEntity.theFunction.theTarget.antigen.species"
										styleId="antigenSpecies">
										<option value=""></option>
										<html:options name="speciesTypes" />
									</html:select>
								</div>
							</td>
						</tr>
						<tr>
							<td class="cellLabel" width="15%">
								<label for="targetName">Name</label>
							</td>
							<td>
								<html:text
									property="functionalizingEntity.theFunction.theTarget.name"
									styleId="targetName" />
							</td>
						</tr>
						<tr>
							<td class="cellLabel" width="15%">
								<label for="targetDescription">Description</label>
							</td>
							<td>
								<html:textarea
									property="functionalizingEntity.theFunction.theTarget.description"
									cols="50" rows="3" styleId="targetDescription" />
							</td>
						</tr>
						<tr>
							<td width="15%">
								<input style="display: none;" class="promptButton"
									id="deleteTarget" type="button" value="Remove"
									onclick="deleteTheTarget()" />
							</td>
							<td>
								<div align="right">
									<input class="promptButton" type="button" value="Save"
										onclick="addTarget();show('targetTable');closeSubmissionForm('Target');" />
									<input class="promptButton" type="button" value="Cancel"
										onclick="clearTarget();closeSubmissionForm('Target');" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<c:if test="${theSample.userDeletable eq 'true'}">
				<input type="button" value="Remove"
					onclick="removeFunction('${validate}', 'functionalizingEntity')"
					id="deleteFunction" style="display: none;" />
			</c:if>
		</td>
		<td colspan="3">
			<div align="right">
				<input type="button" value="Save"
					onclick="addFunction('${validate}', 'functionalizingEntity');show('targetTable');closeSubmissionForm('Function');" />
				<input type="button" value="Cancel"
					onclick="clearFunction();closeSubmissionForm('Function');" />
			</div>
		</td>
	</tr>
</table>