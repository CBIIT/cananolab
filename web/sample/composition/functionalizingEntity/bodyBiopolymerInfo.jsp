<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:choose>
	<c:when test="${param.summary eq 'true'}">
		<table class="summaryViewLayer4" align="center" width="95%">
			<tr>
				<th>
					Type
				</th>
				<th>
					Isotype
				</th>
			</tr>
			<tr>
				<td>
					${functionalizingEntity.biopolymer.type}
				</td>
				<td>
					${functionalizingEntity.biopolymer.sequence}
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table width="100%" align="center" class="submissionView">
			<tr>
				<th colspan="4">
					Biopolymer Properties
				</th>
			</tr>
			<tr>
				<td class="cellLabel">
					Type*
				</td>
				<td colspan="2">
					<select name="functionalizingEntity.biopolymer.type"
						id="biopolymerType"
						onchange="javascript:callPrompt('Biopolymer Type', 'biopolymerType');">
						<option value=""></option>
						<option value="other">
							[Other]
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="cellLabel">
					Sequence
				</td>
				<td colspan="2">
					<textarea name="functionalizingEntity.biopolymer.sequence"
						cols="80" rows="3"></textarea>
				</td>
			</tr>
		</table>
		<br>
	</c:otherwise>
</c:choose>