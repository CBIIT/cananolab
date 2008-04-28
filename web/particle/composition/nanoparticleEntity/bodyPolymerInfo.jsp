<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="topBorderOnly" cellspacing="0" cellpadding="3"
	width="100%" align="center" summary="" border="0">
	<tbody>
		<tr class="topBorder">
			<td class="formTitle" colspan="6">
				<div align="justify">
					Polymer Properties
				</div>
			</td>
		</tr>
		<tr>
			<td class="leftLabel">
				<strong>Initiator</strong>
			</td>
			<td class="label">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.polymer.initiator" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.polymer.initiator}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
			<td class="label">
				<strong>Cross Link Degree</strong>
			</td>
			<td class="label">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.polymer.crosslinkDegree" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.polymer.crosslinkDegree}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
			<td class="label">
				<strong>Is Cross Linked</strong>
			</td>
			<td class="rightLabel">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.polymer.crosslinked" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.polymer.crosslinked}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</tbody>
</table>
<br>