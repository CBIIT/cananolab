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
					Carbon Nanotube Properties
				</div>
			</td>
		</tr>
		<tr>
			<td class="leftLabel">
				<strong>Average Length</strong>
			</td>
			<td class="label">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.carbonNanotube.averageLength" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.carbonNanotube.averageLength}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
			<td class="label">
				<strong>Average Length Unit</strong>
			</td>
			<td class="label">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.carbonNanotube.averageLengthUnit" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.carbonNanotube.averageLengthUnit}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
			<td class="label">
				<strong>Chirality</strong>
			</td>
			<td class="rightLabel">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.carbonNanotube.chirality" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.carbonNanotube.chirality}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="leftLabel">
				<strong>Growth Diameter</strong>
			</td>
			<td class="label">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.carbonNanotube.diameter" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.carbonNanotube.diameter}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
			<td class="label">
				<strong>Growth Diameter Unit</strong>
			</td>
			<td class="label">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.carbonNanotube.diameterUnit" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.carbonNanotube.diameterUnit}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
			<td class="label">
				<strong>Wall Type</strong>
			</td>
			<td class="rightLabel">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.carbonNanotube.wallType" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.carbonNanotube.wallType}&nbsp;
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</tbody>
</table>
<br>