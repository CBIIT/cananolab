<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="StyleSheet" type="text/css" href="css/promptBox.css">
<script type="text/javascript" src="javascript/addDropDownOptions.js"></script>
<table class="editTableWithGrid" align="center" width="95%">
	<tr>
		<th class="cellLabel" width="20%" scope="col">
			File Type
		</th>
		<th width="33%" class="cellLabel" scope="col">
			Title and Download Link
		</th>
		<th class="cellLabel" scope="col">
			Keywords
		</th>
		<th class="cellLabel" scope="col">
			Description
		</th>
		<th></th>
	</tr>
	<c:forEach var="file" items="${files}">
		<tr>
			<td>
				<c:out value="${file.domainFile.type}" />
			</td>
			<td>
				<c:choose>
					<c:when test="${file.domainFile.uriExternal}">
						<a style="white-space: normal;"
							href="${downloadAction}.do?dispatch=download&amp;fileId=${file.domainFile.id}">
							<c:out value="${file.domainFile.uri}" escapeXml="false" /> </a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${file.image eq 'true'}">
								<c:out value="${file.domainFile.title}" />
								<br>
								<a href="#"
									onclick="popImage(event, '${downloadAction}.do?dispatch=download&amp;fileId=${file.domainFile.id}', ${file.domainFile.id})"><img
										src="${downloadAction}.do?dispatch=download&amp;fileId=${file.domainFile.id}"
										border="0" width="150" alt="image ${file.domainFile.id}"> </a>
							</c:when>
							<c:otherwise>
								<a style="white-space: normal;"
									href="${downloadAction}.do?dispatch=download&amp;fileId=${file.domainFile.id}">
									<c:out value="${file.domainFile.title}" /> </a>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${!empty fn:trim(file.keywordsStr)}">
						<c:out value="${file.keywordsDisplayName}" escapeXml="false" />
					</c:when>
					<c:otherwise>N/A
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				<c:choose>
					<c:when test="${!empty fn:trim(file.domainFile.description)}">
						<c:out value="${file.description}" escapeXml="false" />
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
