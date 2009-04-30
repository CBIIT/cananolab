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
					Initiator
				</th>
				<th>
					Cross Link Degree
				</th>
				<th>
					Is Cross Linked
				</th>
			</tr>
			<tr>
				<td>
					${entity.polymer.initiator}
				</td>
				<td>
					${entity.polymer.crossLinkDegree}
				</td>
				<td>
					${entity.polymer.crossLinked}
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table width="100%" align="center" class="submissionView">
			<tr>
				<th colspan="6">
					Polymer Properties
				</th>
			</tr>
			<tr>
				<td class="cellLabel">
					Initiator
				</td>
				<td class="cellLabel">
					<input type="text" name="entity.polymer.initiator"
						value="${nanomaterialEntityForm.map.entity.polymer.initiator}" />
				</td>
				<td class="cellLabel">
					Cross Link Degree
				</td>
				<td class="cellLabel">
					<input type="text" name="entity.polymer.crossLinkDegree"
						onkeydown="return filterFloatNumber(event)"
						value="${nanomaterialEntityForm.map.entity.polymer.crossLinkDegree}" />
				</td>
				<td class="cellLabel">
					Is Cross Linked
				</td>
				<td class="cellLabel">
					<select name="entity.polymer.crossLinked">
						<option value="">
						</option>
						<c:choose>
							<c:when
								test="${nanomaterialEntityForm.map.entity.polymer.crossLinked eq 'true'}">
								<option value="1" selected>
									Yes
								</option>
							</c:when>
							<c:otherwise>
								<option value="1">
									Yes
								</option>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when
								test="${nanomaterialEntityForm.map.entity.polymer.crossLinked eq 'false'}">
								<option value="0" selected>
									No
								</option>
							</c:when>
							<c:otherwise>
								<option value="">
									No
								</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
		</table>
		<br>
	</c:otherwise>
</c:choose>