<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="javascript/editableDropDown.js"></script>
<script type='text/javascript' src='javascript/InstrumentManager.js'></script>
<script type='text/javascript' src='dwr/interface/InstrumentManager.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>

<table border="0" align="center" cellpadding="3" cellspacing="0"
	class="topBorderOnly" summary="">
	<tr>
	<tr class="topBorder">
		<td class="formTitle" colspan="4">
			<div align="justify">
				Instrument Information
			</div>
		</td>
	</tr>
	<tr>
		<td class="leftLabel">
			<strong>Instrument Type </strong>
		</td>
		<td class="label">
			<c:choose>
				<c:when test="${canCreateNanoparticle eq 'true'}">
					<html:select styleId="instrumentType"
						property="achar.instrumentConfiguration.instrument.type"
						onchange="javascript:callPrompt('Instrument Type', 'instrumentType');">
						<option value=""></option>
						<html:options name="instrumentTypes" />
						<option value="other">
							[Other]
						</option>
					</html:select>
				</c:when>
				<c:otherwise>
					${characterizationForm.map.achar.instrumentConfiguration.instrument.type}&nbsp;
				</c:otherwise>
			</c:choose>
		</td>
		<c:choose>
			<c:when test="${canCreateNanoparticle eq 'true'}">
				<td class="rightLabel" width="20%">
					<span id="instrumentAbbr"><b>Abbreviation: </b>${characterizationForm.map.achar.instrumentConfiguration.instrument.abbreviation}</span>&nbsp;
				</td>
			</c:when>
			<c:otherwise>
				<td class="rightLabel" width="20%">
					<c:if
						test="${!empty characterizationForm.map.achar.instrumentConfiguration.instrument.abbreviation}">
						<b>Abbreviation: </b>${characterizationForm.map.achar.instrumentConfiguration.instrument.abbreviation}
					</c:if>
					&nbsp;
				</td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td class="leftLabel">
			<strong>Instrument Manufacturer </strong>
		</td>
		<td class="rightLabel" colspan="3">
			<c:choose>
				<c:when test="${canCreateNanoparticle eq 'true'}">
					<html:select styleId="instrumentManufacturer"
						property="achar.instrumentConfiguration.instrument.manufacturer"
						onchange="javascript:callPrompt('Instrument Manufacturer', 'instrumentManufacturer');">
						<option value=""></option>
						<html:options name="manufacturers" />
						<option value="other">
							[Other]
						</option>
					</html:select>
				</c:when>
				<c:otherwise>
						${characterizationForm.map.achar.instrumentConfiguration.instrument.manufacturer}&nbsp;
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
				<c:when test="${canCreateNanoparticle eq 'true'}">
					<html:textarea property="achar.instrumentConfiguration.description"
						rows="3" cols="80" />
				</c:when>
				<c:otherwise> 
						${characterizationForm.map.achar.instrumentConfiguration.description}&nbsp; 
					</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
<br>

