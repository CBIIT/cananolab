<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when
		test="${displaytype == 'report' ||
				displaytype == 'associated file' ||
				displaytype == 'documents'}">
		<c:set var="reportDisplay" value="display: block;" />
	</c:when>
	<c:otherwise>
		<c:set var="reportDisplay" value="display: none;" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when
		test="${hasDocumentData eq 'true' || (canCreateNanoparticle eq 'true' && location eq 'local')}">
		<c:url var="submitUrl" value="chooseParticleDocument.do">
			<c:param name="particleId" value="${particleId}" />
			<c:param name="submitType" value="documents" />
			<c:param name="page" value="0" />
			<c:param name="dispatch" value="setup" />
			<c:param name="location" value="local" />
		</c:url>
		<c:url var="deleteUrl" value="submitPublication.do">
			<c:param name="particleId" value="${particleId}" />
			<c:param name="submitType" value="documents" />
			<c:param name="page" value="0" />
			<c:param name="dispatch" value="setupDeleteAll" />
			<c:param name="location" value="local" />
		</c:url>
		
		<li class="controlList">
			<c:url var="docurl" value="searchDocument.do">
						<c:param name="dispatch" value="setupDocumentView" />
						<c:param name="particleId" value="${particleId}" />
						<c:param name="location" value="${location}" />
						<c:param name="submitType" value="documents" />
					</c:url>
			<a href="${docurl}" class="subMenuSecondary">DOCUMENTS</a>
			<ul class="sublist_4_report" style="${reportDisplay}">
				<table class="${param.tableStyle}" ><tr class="titleRow">
					<c:choose>
						<c:when
							test="${canCreateDocument eq 'true' && location eq 'local'}">
							<td valign="top">
								<a href="${submitUrl}" class="addlink"><img
									src="images/btn_add.gif" border="0" /></a>
							</td>
						</c:when>	
					</c:choose>
					<c:choose>					
						<c:when
							test="${canUserDelete eq 'true' && location eq 'local' &&
								!empty particleDataTree['documents']}">
							<td>
								<a href="${deleteUrl}" class="addlink"><img
								src="images/btn_delete.gif" border="0"/></a>
							</td>
						</c:when>
					</c:choose>
				</tr></table>
<%--							</ul>--%>
<%--				<c:forEach var="reportDisplayType" items="${reportCategories}">					--%>
<%--					<li>--%>
<%--						<a href="#" class="sublist_4">${reportDisplayType}</a>--%>
<%--						<c:if test="${!empty particleDataTree[reportDisplayType] }">--%>
<%--						<ul class="sublist_5_report" style="${reportDisplay}">--%>
							<c:forEach var="dataLinkBean"
								items="${particleDataTree['documents']}">
								<c:url var="url" value="${dataLinkBean.dataLink}.do">
									<c:param name="page" value="0" />
									<c:param name="dispatch" value="detailView" />
									<c:param name="particleId" value="${particleId}" />
									<c:param name="publicationId" value="${dataLinkBean.dataId}" />
									<c:param name="reportId" value="${dataLinkBean.dataId}" />
									<c:param name="submitType"
										value="documents" />
									<c:param name="location" value="${location}" />
								</c:url>
								<li>
									<a href=${url } class="sublist_5_report"><span
										class="data_anchar">>&nbsp;</span>${dataLinkBean.viewTitle}</a>
								</li>
							</c:forEach>
<%--						</ul>--%>
<%--						</c:if>--%>
<%--					</li>--%>
<%--				</c:forEach>--%>
			</ul>
		</li>
	</c:when>
	<c:otherwise>
		<li class="nodatali">
			DOCUMENTS
		</li>
	</c:otherwise>
</c:choose>
