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
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" type="text/css" href="css/displaytag.css" />
<table width="100%" align="center">
	<tr>
		<td>
			<h3>
				<br>
				Nanoparticle Advanced Search Results
			</h3>
		</td>
		<td align="right" width="15%">
			<a href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=nano_search_results_help')" class="helpText">Help</a>&nbsp;&nbsp; 
			<a href="searchNanoparticleAdvanced.do?dispatch=setup" class="helpText">back</a>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<c:choose>
				<c:when test="${canUserSubmit eq 'true'}">
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
				<display:column title="Particle Classification" property="particleClassification" sortable="true" />
				<display:column title="Particle Type" property="sampleType" sortable="true" />
				<display:column title="Particle Functions" property="functionTypesStr" />
				<display:column title="Particle Characterizations" property="characterizationsStr" />
				<display:column title="Particle Keywords" property="keywordsStr" />
			</display:table>
		</td>
	</tr>
</table>

