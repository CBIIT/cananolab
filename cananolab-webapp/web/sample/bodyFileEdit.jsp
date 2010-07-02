<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="StyleSheet" type="text/css" href="css/promptBox.css">
<script type="text/javascript" src="javascript/addDropDownOptions.js"></script>
<table class="editTableWithGrid" align="center" width="95%">
	<tr>
		<td class="cellLabel" width="20%">
			File Type
		</td>
		<td width="33%" class="cellLabel">
			Title and Download Link
		</td>
		<td class="cellLabel">
			Keywords
		</td>
		<td class="cellLabel">
			Description
		</td>
		<td>
		</td>
	</tr>
	<c:forEach var="file" items="${files}">
		<tr>
			<td>
				${file.domainFile.type}
			</td>
			<td>
				<c:choose>
					<c:when test="${file.domainFile.uriExternal}">
						<a  style="white-space: normal;"
							href="composition.do?dispatch=download&amp;fileId=${file.domainFile.id}">
							${file.domainFile.uri}</a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${file.image eq 'true'}">
						 				${file.domainFile.title}
										<br>
								<a href="#"
									onclick="popImage(event, 'composition.do?dispatch=download&amp;fileId=${file.domainFile.id}', ${file.domainFile.id})"><img
										src="composition.do?dispatch=download&amp;fileId=${file.domainFile.id}"
										border="0" width="150"> </a>
							</c:when>
							<c:otherwise>
								<a  style="white-space: normal;"
									href="composition.do?dispatch=download&amp;fileId=${file.domainFile.id}">
									${file.domainFile.title}</a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${!empty fn:trim(file.keywordsStr)}">
						<c:out value="${fn:replace(file.keywordsStr, cr, '<br>')}"
							escapeXml="false" />
					</c:when>
					<c:otherwise>N/A
												</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${!empty fn:trim(file.domainFile.description)}">
						<c:out
							value="${fn:replace(file.domainFile.description, cr, '<br>')}"
							escapeXml="false" />
					</c:when>
					<c:otherwise>N/A
												</c:otherwise>
				</c:choose>
			</td>
			<c:if test="${editFile eq 'true'}">
				<td align="right">
					<a
						href="javascript:setTheFile('${fileParent}', ${file.domainFile.id});">Edit</a>&nbsp;
				</td>
			</c:if>
		</tr>
	</c:forEach>
</table>