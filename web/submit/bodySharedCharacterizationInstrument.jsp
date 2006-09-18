<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="topBorderOnly" summary="">
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
				<c:when test="${canUserUpdateParticle eq 'true'}">
					<html:select property="achar.instrument.type">
						<html:options name="allInstrumentTypes" />
					</html:select>
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.instrument.type}&nbsp;
					</c:otherwise>
			</c:choose>
		</td>
		<td class="label">
			<strong>Instrument Manufacturer</strong>
		</td>
		<td class="rightLabel">
			<c:choose>
				<c:when test="${canUserUpdateParticle eq 'true'}">
					<html:text property="achar.instrument.manufacturer" />
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.instrument.manufacturer}&nbsp;
					</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="leftLabel">
			<strong>Instrument Abbreviation</strong>
		</td>
		<td class="rightLabel" colspan="3">
			<c:choose>
				<c:when test="${canUserUpdateParticle eq 'true'}">
					<html:text property="achar.instrument.abbreviation" />
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.instrument.abbreviation}&nbsp;
					</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="leftLabel" valign="top">
			<strong>Instrument Description</strong>
		</td>
		<td class="rightLabel" colspan="3">
			<c:choose>
				<c:when test="${canUserUpdateParticle eq 'true'}">
					<html:textarea property="achar.instrument.description" rows="3" />
				</c:when>
				<c:otherwise>
						${thisForm.map.achar.instrument.description}&nbsp;
					</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>
<br>
