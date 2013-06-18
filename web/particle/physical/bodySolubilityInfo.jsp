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

<table class="topBorderOnly" cellspacing="0" cellpadding="3"
	width="100%" align="center" summary="" border="0">
	<tbody>
		<tr class="topBorder">
			<td class="formTitle" colspan="6">
				<div align="justify">
					Solubility Property
				</div>
			</td>
		</tr>
		<tr>
			<td class="leftLabel">
				<strong>Solvent </strong>
			</td>
			<td class="label">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:select property="solubility.solvent"
							onkeydown="javascript:fnKeyDownHandler(this, event);"
							onkeyup="javascript:fnKeyUpHandler_A(this, event); return false;"
							onkeypress="javascript:return fnKeyPressHandler_A(this, event);"
							onchange="fnChangeHandler_A(this, event);">
							<option value="">
								--?--
							</option>
							<html:options name="allSolventTypes" />
						</html:select>
					</c:when>
					<c:otherwise>
										${nanoparticleCharacterizationForm.map.solubility.solvent}&nbsp;
									</c:otherwise>
				</c:choose>
			</td>
			<td class="label">
				<strong>Is Soluble </strong>
			</td>
			<td class="rightLabel">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:select property="solubility.isSoluble">
							<option value=""></option>
							<html:options name="booleanChoices" />
						</html:select>
					</c:when>
					<c:otherwise>
											${nanoparticleCharacterizationForm.map.solubility.isSoluble}&nbsp;
									</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="leftLabel">
				<strong>Critical Concentration</strong>
			</td>
			<td class="label">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="solubility.criticalConcentration" />
						<html:select property="solubility.criticalConcentrationUnit">							
							<option value=""></option>
							<html:options name="allConcentrationUnits" />
						</html:select>
					</c:when>
					<c:otherwise>
										${nanoparticleCharacterizationForm.map.solubility.criticalConcentration}&nbsp;
										${nanoparticleCharacterizationForm.map.solubility.criticalConcentrationUnit}&nbsp;
									</c:otherwise>
				</c:choose>
			</td>
			<td class="rightLabel" colspan="2">
				&nbsp;
			</td>
		</tr>

	</tbody>
</table>
<br />
