<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when
		test="${displaytype == 'Report' ||
				displaytype == 'associated file' ||
				displaytype == 'report File'}">
		<c:set var="reportDisplay" value="display: block;" />
	</c:when>
	<c:otherwise>
		<c:set var="reportDisplay" value="display: none;" />
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when
		test="${hasCompositionData eq 'true' || canCreateNanoparticle eq 'true'}">
		
		<li class="controlList">
			<a href="#" class="subMenuSecondary">REPORTS</a>

			<ul class="sublist_4" style="${reportDisplay}">
				<c:forEach var="reportDisplayType" items="${reportTypes}">
					
					<li>
						<a href="#" class="sublist_4">${reportDisplayType}</a>
						<ul class="sublist_5" style="${reportDisplay}">
							<c:forEach var="dataLinkBean"
								items="${particleDataTree[reportDisplayType]}">
								<c:url var="url" value="${dataLinkBean.dataLink}.do">
									<c:param name="page" value="0" />
									<c:param name="dispatch" value="${dispatchValue}" />
									<c:param name="particleId" value="${particleId}" />
									<c:param name="dataId" value="${dataLinkBean.dataId}" />
									<c:param name="submitType"
										value="${reportDisplayType}" />
								</c:url>
								<li id="complist">
									<a href=${url } id="complink" class="sublist_5"><span
										class="data_anchar">>&nbsp;</span>${dataLinkBean.viewTitle}</a>
								</li>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
			</ul>
		</li>
	</c:when>
	<c:otherwise>
		<li class="nodatali">
			REPORTS
		</li>
	</c:otherwise>
</c:choose>
