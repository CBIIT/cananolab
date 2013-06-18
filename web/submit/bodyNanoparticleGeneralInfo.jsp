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

<table width="100%" align="center">
	<tr>
		<td>
			<h3>
				<br>
				${particle.sampleName} General Information
			</h3>
		</td>
		<td align="right" width="15%">
			<a href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=nano_general_info_help')" class="helpText">Help</a>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<jsp:include page="/bodyMessage.jsp?bundle=submit" />
			<table class="topBorderOnly" cellspacing="0" cellpadding="3" width="100%" align="center" summary="" border="0">
				<tbody>
					<tr class="topBorder">
						<td class="formTitle" colspan="2">
							<div align="justify">
								General Information
							</div>
						</td>
					</tr>
					<tr>
						<td class="leftLabel">
							<strong>Particle Type</strong>
						</td>
						<td class="rightLabel">
							${particle.sampleType}
						</td>
					</tr>
					<tr>
						<td class="leftLabel">
							<strong>Particle ID</strong>
						</td>
						<td class="rightLabel">
							${particle.sampleName}
						</td>
					</tr>
					<tr>
						<td class="leftLabel" valign="top">
							<strong>Keywords <em>(one per line)</em></strong>
						</td>
						<td class="rightLabel">
							<c:forEach var="keyword" items="${particle.keywords}">
								<c:out value="${keyword}" />
								<br>
							</c:forEach>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="leftLabel" valign="top">
							<strong>Visibility</strong>
						</td>
						<td class="rightLabel">
							<c:forEach var="visibility" items="${particle.visibilityGroups}">
								<c:out value="${visibility}" />&nbsp;
								<br>
							</c:forEach>&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
			<br>
			&nbsp;
		</td>
	<tr>
	<TR>
		<TD class=welcomeTitle width=901 height=20 colspan="2">
		
			<STRONG>DISCLAIMER</STRONG>
		</TD>
	</TR>
	<TR>
		<TD class=welcomeContent vAlign=top align="left" colspan="2">
			Critical to the interpretation of the characterization of a nanoparticle is the understanding of the conditions (Stressors, solvents, etc.) under which the nanoparticle characterization occurred and the instruments used in performing the
			characterization. As such, it is highly recommended that prior to reviewing specific characterizations, that the final characterization report for this particle be read in its entirety.
			<br>
			<br>
			Additionally, the Characterizations provided by each source are based on test results only. The characterization providers make no attempt to draw conclusions regarding the results of the characterization.
			<br>
			<br>
		</TD>
	</TR>
	<TR>
		<TD class=welcomeTitle width=901 height=20 colspan="2">
			<STRONG>CHARACTERIZATION REPORT</STRONG>
		</TD>
	</TR>
	<TR>
		<TD class=welcomeContent vAlign=top align="left" colspan="2">
			The Final Characterization Report(s) for this particle is
			<c:choose>
				<c:when test="${empty particleReports}">																		
								currently not availabe.
					</c:when>
				<c:otherwise>
					<c:forEach var="aReport" items="${particleReports}">
						<bean:define id="fileId" name='aReport' property='id' type="java.lang.String" />
						<html:hidden name="aReport" property="id" value="${fileId}" indexed="true" />
						<span class="indented"> <a href="searchReport.do?dispatch=download&amp;fileId=${fileId}">${aReport.name}</a> </span>
											&nbsp;&nbsp;
						</c:forEach>
				</c:otherwise>
			</c:choose>
		</TD>
	</TR>
</TABLE>

