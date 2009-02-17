<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<script type="text/javascript" src="javascript/script.js"></script>
	</head>
	<body onload="window.print();self.close()">
		<table width="100%" align="center">
			<tr>
				<td colspan="">
					<h4>
						<br>
						Publication
					</h4>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" align="center" cellpadding="3"
						cellspacing="0" class="topBorderOnly" summary="">
						<tr>
							<td class="formTitle" align="center" colspan="2">
								${fn:toUpperCase(param.location)} ${particleName}
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Publication Identifer
							</th>
							<td class="rightLabel">
								<c:choose>
									<c:when test="${publicationForm.map.file.domainFile.pubMedId != null &&
												publicationForm.map.file.domainFile.pubMedId != 0}">
										PMID: ${publicationForm.map.file.domainFile.pubMedId }
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${publicationForm.map.file.domainFile.digitalObjectId != null
												&& publicationForm.map.file.domainFile.digitalObjectId ne ''}">
												DOI: ${publicationForm.map.file.domainFile.digitalObjectId }
											</c:when>
											<c:otherwise>
												&nbsp;
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
									&nbsp;
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Publication Type
							</th>
							<td class="rightLabel">
								<bean:write name="publicationForm"
										property="file.domainFile.category" />
									&nbsp;
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Publication Status
							</th>
							<td class="rightLabel">
								<bean:write name="publicationForm"
										property="file.domainFile.status" />
									&nbsp;
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Research Category
							</th>
							<td class="rightLabel">
								<bean:write name="publicationForm"
									property="file.domainFile.researchArea" />
								&nbsp;
							</td>
						</tr>

						<tr>
							<th class="leftLabel" valign="top">
								Title
							</th>
							<td class="rightLabel">
								<bean:write name="publicationForm"
										property="file.domainFile.title" />
									&nbsp;
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Journal
							</th>
							<td class="rightLabel">
								<bean:write name="publicationForm"
										property="file.domainFile.journalName" />
								&nbsp;
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Authors
							</th>
							<td class="rightLabel">
								<c:if test="${!empty publicationForm.map.file.authors}">
									<c:forEach var="author"
										items="${publicationForm.map.file.authors}">
											${author.firstName}&nbsp;${author.lastName}&nbsp;${author.initial}<br>
									</c:forEach>
								</c:if>
								&nbsp;
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Year
							</th>
							<td class="rightLabel">
								<c:if test="${publicationForm.map.file.domainFile.year!=0}">
									<bean:write name="publicationForm"
											property="file.domainFile.year" />
								</c:if>
								&nbsp;
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Volume
							</th>
							<td class="rightLabel">
								<bean:write name="publicationForm"
										property="file.domainFile.volume" />
								&nbsp;
							</td>
						</tr>
						<tr>
							<th class="leftLabel" valign="top">
								Pages
							</th>
							<td class="rightLabel">
								<c:if test="${publicationForm.map.file.domainFile.startPage != null &&
												publicationForm.map.file.domainFile.startPage != 0}">
								<bean:write name="publicationForm"
										property="file.domainFile.startPage" /> - <bean:write name="publicationForm"
										property="file.domainFile.endPage" />
								</c:if>
								&nbsp;
							</td>
						</tr>

						<c:choose>
							<c:when
								test="${publicationForm.map.file.domainFile.pubMedId != null &&
												publicationForm.map.file.domainFile.pubMedId != 0}">
								<tr>
									<th class="leftLabel" valign="top">
										Abstract in
										<br>
										PubMed
									</th>
									<td class="rightLabel">
										<a target="_pubmed" href="http://www.ncbi.nlm.nih.gov/pubmed/${publicationForm.map.file.domainFile.pubMedId}">PMID:
											${publicationForm.map.file.domainFile.pubMedId }</a> &nbsp;
										&nbsp;
									</td>
								</tr>
							</c:when>
						</c:choose>

						<tr>
							<th class="leftLabel" valign="top">
								Description
							</th>
							<td class="rightLabel">
								<bean:write name="publicationForm"
										property="file.domainFile.description" />
									&nbsp;
							</td>
						</tr>

						<c:choose>
							<c:when
								test="${publicationForm.map.file.domainFile.pubMedId == null ||
										publicationForm.map.file.domainFile.pubMedId == 0}">
								<tr>
									<th class="leftLabel" valign="top">
										<c:choose>
											<c:when
												test="${submitReportForm.map.file.domainFile.uriExternal==true}">
								Publication URL&nbsp;
							</c:when>
											<c:otherwise>
								Download Publication&nbsp;
							</c:otherwise>
										</c:choose>
									</th>
									<td class="rightLabel">
										<a href="#" target="_file">
											${publicationForm.map.file.domainFile.uri}</a>&nbsp;
									</td>
								</tr>
							</c:when>
						</c:choose>
					</table>
				</td>
			</tr>
		</table>
</body>
</html>
