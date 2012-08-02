<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' src='javascript/addDropDownOptions.js'></script>
<script type='text/javascript' src='/caNanoLab/dwr/engine.js'></script>
<script type='text/javascript' src='/caNanoLab/dwr/util.js'></script>

<link rel="StyleSheet" type="text/css" href="css/promptBox.css">

<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="Review By Curator" />
	<jsp:param name="topic" value="review_data_pending_release_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
</jsp:include>
<table width="100%" align="center" class="submissionView">
	<tr>
		<th class="cellLabel">
			Data Pending Review
		</th>
	</tr>
	<c:choose>
		<c:when test="${!empty dataPendingReview}">
			<tr>
				<td>
					<table class="editTableWithGrid" width="95%" align="center">
						<tr>
							<th scope="col">
								Data Type
							</th>
							<th scope="col">
								Data Name
							</th>
							<th></th>
						</tr>
						<c:forEach var="dataReview" items="${dataPendingReview}">
							<tr valign="top">
								<td>
									<c:out value="${dataReview.dataType}"/>
								</td>
								<td>
									<c:out value="${dataReview.dataName}"/>
								</td>
								<td>
									<div align="right">
									<a href="${dataReview.reviewLink}">Edit</a>&nbsp;
									</div>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<td>
					None
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
</table>