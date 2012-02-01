<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" type="text/css" href="css/displaytag.css" />
<script type='text/javascript' src='/caNanoLab/dwr/engine.js'></script>
<script type='text/javascript' src='/caNanoLab/dwr/util.js'></script>
<script type='text/javascript' src='javascript/SampleManager.js'></script>
<script type="text/javascript"
	src="/caNanoLab/dwr/interface/SampleManager.js"></script>
<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="Sample Search Results" />
	<jsp:param name="topic" value="sample_search_results_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
	<jsp:param name="other" value="Back" />
	<jsp:param name="otherLink" value="searchSample.do?dispatch=setup" />
</jsp:include>
<table width="100%" align="center">
	<tr>
		<td colspan="2">
			<jsp:include page="/bodyMessage.jsp?bundle=sample" />
			<display:table name="samples" id="sample"
				requestURI="searchSample.do" pagesize="25" class="displaytable"
				partialList="true" size="resultSize"
    			decorator="gov.nih.nci.cananolab.dto.particle.SampleDecorator">
				<display:column title="" property="detailURL" headerScope="col"/>
				<display:column title="Sample Name" property="sampleName"
					sortable="true" escapeXml="true" headerScope="col"/>
				<display:column title="Primary<br>Point Of Contact"
					property="pointOfContactName" sortable="true" escapeXml="true" headerScope="col"/>
				<display:column title="Composition" property="compositionStr"
					sortable="true"  headerScope="col"/>
				<display:column title="Functions" property="functionStr" headerScope="col"/>
				<display:column title="Characterizations"
					property="characterizationStr" headerScope="col"/>
				<display:column title="Data Availability" headerScope="col">
				    <c:choose>
						<c:when test="${sample.dataAvailabilityMetricsScore ==  'N/A' }">${sample.dataAvailabilityMetricsScore}
						</c:when>
						<c:otherwise>
							<div id="details${sample.domain.id}" style="position: relative">
								<a id="detailLink${sample.domain.id}"
											href="#"
											onclick="showDetailView('${sample.domain.id}', 'sample.do?dispatch=dataAvailabilityView&sampleId=${sample.domain.id}'); return false;">
											<c:out value="${sample.dataAvailabilityMetricsScore}"/></a>
									<img src="images/ajax-loader.gif" border="0" class="counts"	id="loaderImg${sample.domain.id}" style="display: none">
									<table
										id="detailView${sample.domain.id}"
										style="display: none; position: absolute; left: -550px; top: -150px; z-index: 10; font-size: 7px; background-color: #FFFFFF"
										 class="promptbox" border="0">
										<tr>
											<td>
												<div
													id="content${sample.domain.id}"></div>
											</td>
										</tr>
									</table>
							</div>
						</c:otherwise>
					</c:choose>
				</display:column>
			</display:table>
		</td>
	</tr>
</table>
