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
<script type="text/javascript" src="javascript/editableDropDown.js"></script>
<bean:define id="thisForm" name="${param.formName}" />
<table width="100%" border="0" align="center" cellpadding="3"
	cellspacing="0" class="topBorderOnly" summary="">
	<tr>
	<tr class="topBorder">
		<td class="formTitle" colspan="4">
			<div align="justify">
				Summary
			</div>
		</td>
	</tr>
	<tr>
		<td class="leftLabel">
			<strong>Characterization Source* </strong>
		</td>
		<td class="label">
			<c:choose>
				<c:when test="${canUserSubmit eq 'true'}">
					<html:select property="achar.characterizationSource"
						onkeydown="javascript:fnKeyDownHandler(this, event);"
						onkeyup="javascript:fnKeyUpHandler_A(this, event); return false;"
						onkeypress="javascript:return fnKeyPressHandler_A(this, event);"
						onchange="fnChangeHandler_A(this, event);">
						<option value="">
							--?--
						</option>						
						<html:options name="characterizationSources" />
					</html:select>
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.characterizationSource}&nbsp;
					</c:otherwise>
			</c:choose>
		</td>
		<td class="label">
			<strong>View Title*</strong>
			<br>
			<em>(text will be truncated after 20 characters)</em>
		</td>
		<td class="rightLabel">
			<c:choose>
				<c:when test="${canUserSubmit eq 'true'}">
					<html:text property="achar.viewTitle" size="30" />
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.viewTitle}&nbsp;
					</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="leftLabel">
			<strong>Protocol Name </strong>
		</td>
		<td class="label">
			<c:choose>
				<c:when test="${canUserSubmit eq 'true'}">
					<html:text property="achar.characterizationProtocol.name" />
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.characterizationProtocol.name}&nbsp;
				</c:otherwise>
			</c:choose>
		</td>
		<td class="label">
			<strong>Protocol Version</strong>
		</td>
		<td class="rightLabel">
			<c:choose>
				<c:when test="${canUserSubmit eq 'true'}">
					<html:text property="achar.characterizationProtocol.version" />
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.characterizationProtocol.version}&nbsp;
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="leftLabel" valign="top">
			<strong>Description</strong>
		</td>
		<td class="rightLabel" colspan="3">
			<c:choose>
				<c:when test="${canUserSubmit eq 'true'}">
					<html:textarea property="achar.description" rows="3" cols="80" />
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.description}&nbsp;
					</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
<br>
