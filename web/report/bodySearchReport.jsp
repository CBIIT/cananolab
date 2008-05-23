<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="javascript/ReportManager.js"></script>
<script type="text/javascript" src="/caNanoLab/dwr/interface/ReportManager.js"></script>
<script type="text/javascript" src="/caNanoLab/dwr/interface/NanoparticleSampleManager.js"></script>
<script type='text/javascript' src='/caNanoLab/dwr/engine.js'></script>
<script type='text/javascript' src='/caNanoLab/dwr/util.js'></script>
<html:form action="searchReport">
	<table align="center" width="100%">
		<tr>
			<td>
				<h3>					
					Search Reports
				</h3>
			</td>
			<td align="right" width="30%">
				<%--<a href="advancedNanoparticleSearch.do" class="helpText">Advanced Search</a> &nbsp; &nbsp; --%>
				<jsp:include page="/webHelp/helpGlossary.jsp">
					<jsp:param name="topic" value="search_reports_help" />
					<jsp:param name="glossaryTopic" value="glossary_help" />
				</jsp:include>	
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<jsp:include page="/bodyMessage.jsp?bundle=report" />
				<table width="100%" border="0" align="center" cellpadding="3"
					cellspacing="0" summary="">
					<tr class="topBorder">
						<td class="formTitle">
							<div align="justify">
								Search Criteria
							</div>
						</td>
						<td class="formTitle" colspan="5">
							<div align="justify">
								* for searching wildcards
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftLabel" valign="top">
							<strong>Search Location</strong>
						</td>
						<td class="rightLabel" colspan="5">
							<strong><html:select property="searchLocations"
								styleId="searchLocations"
								onchange="javascript:setReportDropdowns()"	
									multiple="true" size="3">
									<option value="local" selected>
										Local
									</option>
									<option value="caNanoLab-NCL_PROD">
										caNanoLab-NCL_PROD
									</option>
									<option value="caNanoLab-PROD">
										caNanoLab-PROD
									</option>
									<option value="caNanoLab-WUSTL">
										caNanoLab-WUSTL
									</option>
								</html:select> </strong>
						</td>
					</tr>
					<tr>
						<td class="leftLabel">
							<strong>Report Title</strong>
						</td>
						<td class="rightLabel" colspan="5">
							<html:text property="reportTitle" size="60" />
						</td>
					</tr>
					<tr>
						<td class="leftLabel">
							<strong>Report Category</strong>
						</td>
						<td class="rightLabel" colspan="5">
							<html:select styleId="reportCategory" property="reportCategory">
								<option value="" />
									<html:options name="reportCategories" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td class="leftLabel" valign="top">
							<strong>Composition<br> Nanoparticle Entity</strong>
						</td>
						<td class="label">
							<strong><html:select property="nanoparticleEntityTypes"
									styleId="nanoparticleEntityTypes" multiple="true" size="4">
									<html:options name="nanoparticleEntityTypes" />
								</html:select> </strong>
						</td>
						<td class="label" valign="top">
							<strong>Composition <br>Functionalizing Entity</strong>
						</td>
						<td class="label" valign="top">
							<strong><html:select styleId="functionalizingEntityTypes"
									property="functionalizingEntityTypes" multiple="true" size="3">
									<html:options name="functionalizingEntityTypes" />
								</html:select> </strong>
						</td>
						<td class="label" valign="top">
							<strong>Function</strong>
						</td>
						<td class="rightLabel" valign="top">
							<strong><html:select property="functionTypes" styleId="functionTypes"
									multiple="true" size="3">
									<html:options name="functionTypes" />
								</html:select> </strong>
						</td>
					</tr>
				</table>
				<br>
				<table width="100%" border="0" align="center" cellpadding="3"
					cellspacing="0" class="topBorderOnly" summary="">
					<tr>
						<td>
							<span class="formMessage"> </span>
							<br>
							<table border="0" align="right" cellpadding="4" cellspacing="0">
								<tr>
									<td>
										<div align="right">
											<input type="button" value="Reset"
												onClick="javascript:location.reload()">
											<input type="hidden" name="dispatch" value="search">
											<input type="hidden" name="page" value="1">
											<html:submit value="Search" />
										</div>
									</td>
								</tr>
							</table>
							<div align="right"></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>
<!--_____ main content ends _____-->
