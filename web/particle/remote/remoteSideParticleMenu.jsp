<%--L
   Copyright SAIC
   Copyright SAIC-Frederick

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/cananolab/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- submenu begins -->
<c:choose>
	<c:when test="${!empty param.particleName}">
		<c:set var="particleName" value="${param.particleName}"
			scope="session" />
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${!empty param.particleType}">
		<c:set var="particleType" value="${param.particleType}"
			scope="session" />
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${!empty param.gridNodeHost}">
		<c:set var="gridNodeHost" value="${param.gridNodeHost}" scope="session" />
	</c:when>
</c:choose>
<table summary="" cellpadding="0" cellspacing="0" border="0"
	height="100%" width="250">
	<tr>
		<td class="subMenuPrimaryTitle" height="21">
			PARTICLE TREE
		</td>
	</tr>
	<tr>
		<td class="formMessage" height="100%">
			<table width="100%" height="95%" border="0" cellpadding="2"
				cellspacing="0">
				<tr>
					<td align="left" valign="top" class="formMessage">
						<ul>
							<li>
								<span class="largerText">General Information</span>
								<br>
								<br>
								<span class="indented"> <a href="remoteNanoparticleGeneralInfo.do?dispatch=view&particleType=${particleType}&particleName=${particleName}&gridNodeHost=${gridNodeHost}">${particleName} (${particleType})</a> </span>
								<br>
								<br>
							</li>
							<li>
								<jsp:include page="remoteSideParticleFunctionMenu.jsp" />
							</li>
							<li>
								<jsp:include page="remoteSideParticleCharacterizationMenu.jsp" />
							</li>
							<li>
								<span class="largerText">Other Associated Files
									&nbsp;&nbsp;</span>
								<c:forEach var="aFile" items="${remoteParticleAssociatedFiles}">
									<span class="indented"> <a
										href="remoteSearchReport.do?dispatch=download&fileId=${aFile.id}&fileName=${aFile.name}&gridNodeHost=${gridNodeHost}"
										title="${aFile.displayName}">${aFile.name}</a> </span>
									<br>
								</c:forEach>
								<br>
								<br>
							</li>
							<li>
								<span class="largerText">Reports</span>
								<br>
								<c:forEach var="aReport" items="${remoteParticleReports}">
									<span class="indented"> <a
										href="remoteSearchReport.do?dispatch=download&fileId=${aReport.id}&fileName=${aReport.name}&gridNodeHost=${gridNodeHost}"
										title="${aReport.displayName}">${aReport.name}</a> </span>
									<br>
								</c:forEach>
							</li>						
						<p>
							&nbsp;
						</p>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="subMenuFooter" height="22">
			&nbsp;
		</td>
	</tr>
</table>
