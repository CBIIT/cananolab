<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" type="text/css" href="css/displaytag.css" />
<table width="100%" align="center">
	<tr>
		<td>
			<h3>
				<br>
				Nanoparticle Search Results
			</h3>
		</td>
		<td align="right" width="15%">
			<a href="javascript:openHelpWindow('webHelp/caLAB_0.5/index.html?single=true&amp;context=caLAB_0.5&amp;topic=sample_search_results')" class="helpText">Help</a>&nbsp;&nbsp; <a href="searchSample.do?dispatch=setup&page=0&rememberSearch=true"
				class="helpText">back</a>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<c:choose>
				<c:when test="${canUserUpdateParticle eq 'true'}">
					<c:set var="particleURL" value="editParticleURL" />
				</c:when>
				<c:otherwise>
					<c:set var="particleURL" value="viewParticleURL" />
				</c:otherwise>
			</c:choose>
			<jsp:include page="/bodyMessage.jsp?bundle=search" />
			<display:table name="particles" id="particle" requestURI="searchNanoparticle.do" pagesize="25" class="displaytable" decorator="gov.nih.nci.calab.dto.search.NanoparticleDecorator">
				<display:column title="Particle ID" property="${particleURL}" sortable="true" />
				<display:column title="Particle Source" property="sampleSource" sortable="true" />
				<display:column title="Particle Category" property="particleCategory" sortable="true" />
				<display:column title="Particle Type" property="sampleType" sortable="true" />
				<display:column title="Particle Functions" property="functionTypesStr" />
				<display:column title="Particle Characterization Types" property="characterizationTypesStr" />
				<display:column title="Particle Keywords" property="keywordsStr" />
			</display:table>
			<%--
				<div align="right">
					<input type="hidden" name="dispatch" value="search">
					<input type="hidden" name="page" value="1">
					<html:submit value="View Details" />
				</div>
				--%>
		</td>
	</tr>
</table>

