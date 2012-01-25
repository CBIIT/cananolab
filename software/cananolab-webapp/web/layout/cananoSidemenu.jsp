<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<table height="100%" width="150" cellspacing="0" cellpadding="0"
	summary="layout" border="0">
	<tbody>
		<tr>
			<td>
				<table height="100%" width="150" cellspacing="0" cellpadding="0"
					border="0">
					<tr>
						<th class="subMenuPrimaryTitle" height="22" scope="col">
							QUICK LINKS <!-- anchor to skip sub menu --> <img height="1"
							alt="Skip Menu" src="images/shim.gif" width="1" border="0">
						</th>
					</tr>
					<jsp:include page="/html/cananoBaseSidemenu.html" />
					<tr>
						<td class="subMenuFill" height="5">&nbsp;</td>
					</tr>
					<c:if test="${showVisitorCount}">
						<tr>
							<td class="subMenuSecondaryTitleFill" height="20">Visitor
								Count</td>
						</tr>
						<tr>
							<td class="subMenuSecondaryTitleFill" height="20"><c:choose>
									<c:when test="${fn:length(countString) > 1}">
										<%--Do NOT reformat code style below, otherwise it will introduce gaps between counter images--%>
										<c:forEach var="index" begin="0"
											end="${fn:length(countString) - 1}">
											<c:set var="counterImg"
												value="${fn:substring(countString, index, index + 1)}" />
											<img alt="Visitor Counter"
												src="images/visitorCounter${counterImg}.png" />
										</c:forEach>
									</c:when>
									<c:otherwise>
										<img alt="Visitor Counter"
											src="images/visitorCounter${countString}.png" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="subMenuCommentText" height="30"><em>Since
									${counterStartDate}</em>
							</td>
						</tr>
					</c:if>
					<c:if test="${!empty user}">
						<tr>
							<td class="subMenuCommentText" height="20">Logged in as <i>${user.loginName}</i>
								<c:if test="${!empty user.groupNames}">
									<br>Associated Groups:<br />
									<c:forEach var="group" items="${user.groupNames}">
										<span class="indented1"><i>${group}</i> </span>
										<br />
									</c:forEach>
								</c:if>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="subMenuFill" height="100%">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</tbody>
</table>