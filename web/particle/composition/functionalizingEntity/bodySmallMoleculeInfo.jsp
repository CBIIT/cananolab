<table class="topBorderOnlyTable" cellspacing="0" cellpadding="3"
	width="100%" align="center" summary="" border="0">
	<tbody>
		<tr class="topBorder">
			<td class="formTitle" colspan="2">
				<div align="justify">
					Small Molecule Properties
				</div>
			</td>
		</tr>
		<tr>
			<td class="leftLabel">
				<strong>Alternate Name</strong>
			</td>
			<td class="rightLabel">
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">
						<html:text property="entity.smallMolecule.alternateName" />
					</c:when>
					<c:otherwise>
						${nanoparticleEntityForm.map.entity.smallMolecule.alternateName}
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</tbody>
</table>
<br>