<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="topBorderOnly" cellspacing="0" cellpadding="3"
	width="100%" align="center" summary="" border="0">
	<tbody>
		<tr>
			<td class="formTitle" colspan="2">
				<div align="justify">
					&nbsp;
				</div>
			</td>
		</tr>
		<c:choose>
			<c:when test="${!empty otherParticleNames}">
				<tr>
					<td class="leftLabel" valign="top" width="30%">
						<strong>Copy to other ${particleSource} nanoparticle sampless</strong>
					</td>
					<td class="rightLabel">
						<html:select property="otherParticles" size="5" multiple="true">
							<html:options collection="otherParticleNames" property="name"
								labelProperty="name" />
						</html:select>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td class="completeLabel" colspan="2">
						There are no other particles from source ${particleSource} to copy
						annotation to.
					</td>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
