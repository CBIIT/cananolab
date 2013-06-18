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
<%
	String actionPath = request.getAttribute(
			org.apache.struts.Globals.ORIGINAL_URI_KEY).toString();
	pageContext.setAttribute("actionPath", actionPath);
%>
<link rel="StyleSheet" type="text/css" href="css/sidemenu.css">
<c:choose>
	<c:when test="${!empty theStudy}">
		<c:set var="sampleName" value="${theStudy.domain.name}"
			scope="session" />
		<c:set var="sampleId" value="${theStudy.domain.id}" scope="session" />
		<c:set var="location" value="${theStudy.location}" scope="session" />
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${!empty user && user.curator && location eq applicationOwner}">
		<c:set var="dispatch" value="summaryEdit" />
	</c:when>
	<c:otherwise>
		<c:set var="dispatch" value="summaryView" />
	</c:otherwise>
</c:choose>
<table summary="" cellpadding="0" cellspacing="0" border="0"
	height="100%" width="150">
	<tr>
		<td class="subMenuPrimaryTitle" height="21">
			NAVIGATION TREE
		</td>
	</tr>
	<tr>
		<td class="subMenuFill" height="5">
			&nbsp;
		</td>
	</tr>
	<tr>
		<c:url var="studyUrl" value="study.do">
			<c:param name="dispatch" value="studyEdit" />
			<c:param name="location" value="${location}" />
			<c:param name="page" value="0" />
			<c:param name="tab" value="ALL"/>
		</c:url>
		<td class="subMenuPrimaryTitle" height="60" onmouseover="showCursor()"
			onmouseout="hideCursor()" onclick="gotoPage('${studyUrl}')"
			height="20">
			<a class="pname">${location} Study:<br><br>Efficacy of nanoparticle</a>
		</td>
	</tr>
	<tr>
		<c:choose>
			<c:when
				test="${theSample.hasComposition|| !empty user && user.curator}">
				<c:url var="compUrl" value="composition.do">
					<c:param name="dispatch" value="${dispatch}" />
					<c:param name="sampleId" value="${sampleId}" />
					<c:param name="location" value="${location}" />
					<c:param name="page" value="0" />
					<c:param name="tab" value="ALL"/>
				</c:url>
				<c:choose>
					<c:when
						test="${actionPath eq '/composition.do' || actionPath eq '/nanomaterialEntity.do' || actionPath eq '/functionalizingEntity.do' ||actionPath eq '/chemicalAssociation.do' ||actionPath eq '/compositionFile.do'}">
						<td class="subMenuSecondaryTitleSelected"
							onmouseover="changeMenuStyle(this,'subMenuSecondaryTitleOver'), showCursor()"
							onmouseout="changeMenuStyle(this,'subMenuSecondaryTitleSelected'), hideCursor()"
							onclick="gotoPage('${compUrl}')" height="20">
							<a class="subMenuSecondary">COMPOSITION</a>
						</td>
					</c:when>
					<c:otherwise>
						<td class="subMenuSecondaryTitle"
							onmouseover="changeMenuStyle(this,'subMenuSecondaryTitleOver'), showCursor()"
							onmouseout="changeMenuStyle(this,'subMenuSecondaryTitle'), hideCursor()"
							onclick="gotoPage('${compUrl}')" height="20">
							<a class="subMenuSecondary">COMPOSITION</a>
						</td>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<td class="subMenuSecondaryTitle" height="20">
					<i>COMPOSITION</i>
				</td>
			</c:otherwise>
		</c:choose>


		<c:url var="studyInfoUrl" value="studySample.do">
			<c:param name="dispatch" value="summaryEdit" />
			<c:param name="location" value="${location}" />
			<c:param name="page" value="0" />
			<c:param name="tab" value="ALL"/>
		</c:url>
		<td class="subMenuSecondaryTitle"
			onmouseover="changeMenuStyle(this,'subMenuSecondaryTitleOver'), showCursor()"
			onmouseout="changeMenuStyle(this,'subMenuSecondaryTitle'), hideCursor()"
			onclick="gotoPage('${studyInfoUrl}')" height="20">
			<a class="subMenuSecondary">SAMPLE</a>
		</td>
	</tr>
	<tr>
		<c:url var="animalInfoUrl" value="studyAnimalModel.do">
			<c:param name="dispatch" value="summaryEdit" />
			<c:param name="location" value="${location}" />
			<c:param name="page" value="0" />
			<c:param name="tab" value="ALL"/>
		</c:url>
		<td class="subMenuSecondaryTitle"
			onmouseover="changeMenuStyle(this,'subMenuSecondaryTitleOver'), showCursor()"
			onmouseout="changeMenuStyle(this,'subMenuSecondaryTitle'), hideCursor()"
			onclick="gotoPage('${animalInfoUrl}')" height="20">
			<a class="subMenuSecondary">ANIMAL INFORMATION</a>
		</td>
	</tr>
	<tr>
		<c:url var="characterizationUrl" value="study.do">
			<c:param name="dispatch" value="charSummary" />
			<c:param name="location" value="${location}" />
			<c:param name="page" value="0" />
			<c:param name="tab" value="ALL"/>
		</c:url>
		<td class="subMenuSecondaryTitle"
			onmouseover="changeMenuStyle(this,'subMenuSecondaryTitleOver'), showCursor()"
			onmouseout="changeMenuStyle(this,'subMenuSecondaryTitle'), hideCursor()"
			onclick="gotoPage('${characterizationUrl}')" height="20">
			<a class="subMenuSecondary">CHARACTERIZATION</a>
		</td>
	</tr>
	<tr>
		<c:url var="ProtocolUrl" value="studyProtocol.do">
			<c:param name="dispatch" value="summaryEdit" />
			<c:param name="location" value="${location}" />
			<c:param name="page" value="0" />
			<c:param name="tab" value="ALL"/>
		</c:url>
		<td class="subMenuSecondaryTitle"
			onmouseover="changeMenuStyle(this,'subMenuSecondaryTitleOver'), showCursor()"
			onmouseout="changeMenuStyle(this,'subMenuSecondaryTitle'), hideCursor()"
			onclick="gotoPage('${ProtocolUrl}')" height="20">
			<a class="subMenuSecondary">PROTOCOL</a>
		</td>
	</tr>
	<tr>
		<c:url var="publicationUrl" value="studyPublication.do">
			<c:param name="dispatch" value="summaryEdit" />
			<c:param name="location" value="${location}" />
			<c:param name="page" value="0" />
			<c:param name="tab" value="ALL"/>
		</c:url>
		<td class="subMenuSecondaryTitle"
			onmouseover="changeMenuStyle(this,'subMenuSecondaryTitleOver'), showCursor()"
			onmouseout="changeMenuStyle(this,'subMenuSecondaryTitle'), hideCursor()"
			onclick="gotoPage('${publicationUrl}')" height="20">
			<a class="subMenuSecondary">PUBLICATION</a>
		</td>
	</tr>
	<tr>
		<td class="subMenuFill" height="50">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td>
		<jsp:include page="/html/cananoBaseSidemenu.html"/>
		</td>
	</tr>
</table>
