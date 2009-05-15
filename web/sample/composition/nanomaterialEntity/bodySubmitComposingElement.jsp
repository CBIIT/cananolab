<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="subSubmissionView" width="85%" align="center">
	<tr>
		<th colspan="4" align="left">
			Composing Element Info
		</th>
	</tr>
	<tr>
		<td class="cellLabel">
			Type*
		</td>
		<td>
			<div id="elementTypePrompt">
				<html:select styleId="elementType"
					property="nanomaterialEntity.theComposingElement.domain.type"
					onchange="javascript:callPrompt('Composing Element Type', 'elementType', 'elementTypePrompt');">
					<option />
						<c:choose>
							<c:when test="${entityType ne 'emulsion'}">
								<html:options name="composingElementTypes" />
							</c:when>
							<c:otherwise>
								<html:options name="emulsionComposingElementTypes" />
							</c:otherwise>
						</c:choose>
					<option value="other">
						[Other]
					</option>
				</html:select>
			</div>
		</td>
		<td class="cellLabel">
			Chemical Name*
		</td>
		<td>
			<html:text
				property="nanomaterialEntity.theComposingElement.domain.name"
				size="30" styleId="elementName" />
		</td>
	</tr>
	<tr>
		<td class="cellLabel">
			Molecular Formula Type
		</td>
		<td>
			<div id="molFormulaTypePrompt">
				<html:select styleId="molFormulaType"
					property="nanomaterialEntity.theComposingElement.domain.molecularFormulaType"
					onchange="javascript:callPrompt('Molecular Formula Type', 'molFormulaType', 'molFormulaTypePrompt');">
					<option value="" />
						<html:options name="ceMolecularFormulaTypes" />
					<option value="other">
						[Other]
					</option>
				</html:select>
			</div>
		</td>
		<td class="cellLabel">
			Molecular Formula
		</td>
		<td>
			<html:textarea
				property="nanomaterialEntity.theComposingElement.domain.molecularFormula"
				rows="2" cols="40" styleId="molFormula" />
		</td>
	</tr>
	<tr>
		<td class="cellLabel">
			Amount
		</td>
		<td>
			<html:text
				property="nanomaterialEntity.theComposingElement.domain.value"
				onkeydown="return filterFloatNumber(event)" size="30"
				styleId="elementValue" />
		</td>
		<td class="cellLabel">
			Amount Unit
		</td>
		<td valign="top">
			<div id="elementValueUnitPrompt">
				<html:select styleId="elementValueUnit"
					property="nanomaterialEntity.theComposingElement.domain.valueUnit"
					onchange="javascript:callPrompt('Unit', 'elementValueUnit', 'elementValueUnitPrompt');">
					<option value="" />
						<html:options name="composingElementUnits" />
					<option value="other">
						[Other]
					</option>
				</html:select>
			</div>
		</td>
	</tr>
	<tr>
		<td class="cellLabel">
			Inherent Function
		</td>
		<td colspan="3">
			<div id="functionSection" style="position: relative;">
				<a style="" id="addFunction"
					href="javascript:clearFunction();show('newFunction');">Add</a>
				<br><br>
				<table id="functionTable" class="summaryViewLayer4" width="85%"
					style="display: none;">
					<tbody id="functionRows">
						<tr id="patternHeader">
							<td width="25%" class="cellLabel">
								Function Type
							</td>
							<td width="25%" class="cellLabel">
								<div id="modalityHeader" style="display:none">Modality Type</div>
							</td>
							<td class="cellLabel">
								Description
							</td>
							<td>
							</td>
						</tr>
						<tr id="pattern" style="display: none;">
							<td>
								<span id="functionTypeValue">Function Type</span>
							</td>
							<td>
								<span id="functionModalityTypeValue" style="display:none">Modality Type</span>
							</td>
							<td>
								<span id="functionDescriptionValue">Description</span>
							</td>
							<td>
								<input class="noBorderButton" id="edit" type="button"
									value="Edit"
									onclick="editFunction(this.id); show('newFunction');" />
							</td>
						</tr>
					</tbody>
				</table>
				<table id="newFunction" style="display: none;" class="promptbox">
					<tbody>
						<tr>
							<html:hidden
								property="nanomaterialEntity.theComposingElement.theFunction.id"
								styleId="functionId" />
							<td class="cellLabel">
								Function Type
							</td>
							<td>
								<div id="functionTypePrompt">
									<html:select
										property="nanomaterialEntity.theComposingElement.theFunction.type"
										styleId="functionType"
										onchange="javascript:callPrompt('Function Type', 'functionType', 'functionTypePrompt');displayImageModality();">
										<option value=""></option>
										<html:options name="functionTypes" />
										<option value="other">
											[Other]
										</option>
									</html:select>
								</div>
							</td>
							<td class="cellLabel">
								<div id="modalityLabel" style="display: none">
									Imaging Modality Type
								</div>
							</td>
							<td>
								<div id="imagingModalityPrompt" style="display: none">
									<html:select
										property="nanomaterialEntity.theComposingElement.theFunction.imagingFunction.modality"
										onchange="javascript:callPrompt('Modality Type', 'imagingModality', 'imagingModalityPrompt');"
										styleId="imagingModality">
										<option value="" />
											<html:options name="modalityTypes" />
										<option value="other">
											[Other]
										</option>
									</html:select>
								</div>
							</td>
						</tr>
						<tr>
							<td class="cellLabel">
								Description
							</td>
							<td colspan="3">
								<html:textarea
									property="nanomaterialEntity.theComposingElement.theFunction.description"
									cols="50" rows="3" styleId="functionDescription" />
							</td>
						</tr>
						<tr>
							<td>
								<input style="display: none;" class="promptButton"
									id="deleteFunction" type="button" value="Remove"
									onclick="deleteFunction()" />
							</td>
							<td colspan="3">
								<div align="right">
									<input class="promptButton" type="button" value="Add"
										onclick="addFunction();show('functionTable');hide('newFunction');" />
									<input class="promptButton" type="button" value="Cancel"
										onclick="clearFunction();hide('newFunction');" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td class="cellLabel">
			Description
		</td>
		<td colspan="3">
			<html:textarea
				property="nanomaterialEntity.theComposingElement.domain.description"
				rows="3" cols="65" styleId="elementDescription" />
		</td>
	</tr>
	<tr>
		<td>
			<html:hidden property="nanomaterialEntity.theComposingElementIndex"
				styleId="hiddenComposingElementIndex" value="-1" />
			<input class="promptButton" type="button" value="Remove"
				onclick="removeComposingElement('${actionName}', ${fileForm})"
				id="deleteComposingElement" style="display: none;" />
		</td>
		<td colspan="3">
			<div align="right">
				<input class="promptButton" type="button" value="Add"
					onclick="addComposingElement('${actionName}', ${fileForm});" />
				<input class="promptButton" type="button" value="Cancel"
					onclick="clearComposingElement();hide('newComposingElement');" />
			</div>
		</td>
	</tr>
</table>
