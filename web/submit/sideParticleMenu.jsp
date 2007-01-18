<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- submenu begins -->
<c:choose>
	<c:when test="${!empty param.particleName}">
		<c:set var="particleName" value="${param.particleName}" scope="session" />
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${!empty param.particleType}">
		<c:set var="particleType" value="${param.particleType}" scope="session" />
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${canUserSubmit eq 'true'}">
		<c:set var="dispatchValue" value="setupUpdate" />
	</c:when>
	<c:otherwise>
		<c:set var="dispatchValue" value="setupView" />
	</c:otherwise>
</c:choose>
<table summary="" cellpadding="0" cellspacing="0" border="0" height="100%" width="250">
	<tr>
		<td class="subMenuPrimaryTitle" height="21">
			PARTICLE TREE
		</td>
	</tr>
	<tr>
		<td class="formMessage" height="100%">
			<table width="100%" height="95%" border="0" cellpadding="2" cellspacing="0">
				<tr>
					<td align="left" valign="top" class="formMessage">
						<ul>
							<li>
								<span class="largerText">General Information</span>
								<br>
								<br>
								<span class="indented"> <c:choose>
										<c:when test="${canUserSubmit eq 'true'}">
											<a href="nanoparticleGeneralInfo.do?dispatch=setupUpdate&particleType=${particleType}&particleName=${particleName}"">${particleName} (${particleType})</a>
										</c:when>
										<c:otherwise>
											<a href="nanoparticleGeneralInfo.do?dispatch=setupView&particleType=${particleType}&particleName=${particleName}"">${particleName} (${particleType})</a>
										</c:otherwise>
									</c:choose> </span>
								<br>
								<br>
							</li>
							<li>
								<span class="largerText">Function</span>
								<br>
								<br>
								<span class="indented"><strong>-Therapeutic</strong> &nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="nanoparticleFunction.do?dispatch=setup&page=0&particleType=${particleType}&particleName=${particleName}&submitType=Therapeutic"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aFunc" items="${funcTypeFuncs['Therapeutic']}">
									<%java.util.HashMap paramMapTf = new java.util.HashMap();
			paramMapTf.put("dispatch", pageContext
					.getAttribute("dispatchValue"));
			paramMapTf
					.put("particleName", session.getAttribute("particleName"));
			paramMapTf
					.put("particleType", session.getAttribute("particleType"));
			paramMapTf.put("functionId",
					((gov.nih.nci.calab.dto.function.FunctionBean) pageContext
							.getAttribute("aFunc")).getId());
			paramMapTf.put("submitType", "Therapeutic");
			pageContext.setAttribute("paramMapTf", paramMapTf);

			%>
									<span class="indented2"><html:link forward="function" name="paramMapTf">${aFunc.viewTitle}</html:link> </span>
									<br>
								</c:forEach>
								<br>
								<span class="indented"><strong>-Targeting</strong> &nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="nanoparticleFunction.do?dispatch=setup&page=0&particleType=${particleType}&particleName=${particleName}&submitType=Targeting"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aFunc" items="${funcTypeFuncs['Targeting']}">
									<%java.util.HashMap paramMapTt = new java.util.HashMap();
			paramMapTt.put("dispatch", pageContext
					.getAttribute("dispatchValue"));
			paramMapTt
					.put("particleName", session.getAttribute("particleName"));
			paramMapTt
					.put("particleType", session.getAttribute("particleType"));
			paramMapTt.put("functionId",
					((gov.nih.nci.calab.dto.function.FunctionBean) pageContext
							.getAttribute("aFunc")).getId());
			paramMapTt.put("submitType", "Targeting");
			pageContext.setAttribute("paramMapTt", paramMapTt);

			%>
									<span class="indented2"> <html:link forward="function" name="paramMapTt">${aFunc.viewTitle}</html:link> </span>
									<br>
								</c:forEach>
								<br>
								<span class="indented"><strong>-Diagnostic Imaging</strong>&nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="nanoparticleFunction.do?dispatch=setup&page=0&particleType=${particleType}&particleName=${particleName}&submitType=Imaging"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aFunc" items="${funcTypeFuncs['Imaging']}">
									<%java.util.HashMap paramMapTi = new java.util.HashMap();
			paramMapTi.put("dispatch", pageContext
					.getAttribute("dispatchValue"));
			paramMapTi
					.put("particleName", session.getAttribute("particleName"));
			paramMapTi
					.put("particleType", session.getAttribute("particleType"));
			paramMapTi.put("functionId",
					((gov.nih.nci.calab.dto.function.FunctionBean) pageContext
							.getAttribute("aFunc")).getId());
			paramMapTi.put("submitType", "Imaging");
			pageContext.setAttribute("paramMapTi", paramMapTi);

			%>
									<span class="indented2"> <html:link forward="function" name="paramMapTi">${aFunc.viewTitle}</html:link> </span>
									<br>
								</c:forEach>
								<br>
								<span class="indented"><strong>-Diagnostic Reporting</strong> &nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="nanoparticleFunction.do?dispatch=setup&page=0&particleType=${particleType}&particleName=${particleName}&submitType=Reporting"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aFunc" items="${funcTypeFuncs['Reporting']}">
									<%java.util.HashMap paramMapTr = new java.util.HashMap();
			paramMapTr.put("dispatch", pageContext
					.getAttribute("dispatchValue"));
			paramMapTr
					.put("particleName", session.getAttribute("particleName"));
			paramMapTr
					.put("particleType", session.getAttribute("particleType"));
			paramMapTr.put("functionId",
					((gov.nih.nci.calab.dto.function.FunctionBean) pageContext
							.getAttribute("aFunc")).getId());
			paramMapTr.put("submitType", "Reporting");
			pageContext.setAttribute("paramMapTr", paramMapTr);

			%>
									<span class="indented2"> <html:link forward="function" name="paramMapTr">${aFunc.viewTitle}</html:link> </span>
									<br>
								</c:forEach>
								<br>
							</li>
							<li>
								<span class="largerText">Characterization</span>
								<br>
								<br>
								<span class="indented"><strong>-Physical Characterization</strong> &nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="submitAction.do?submitType=physical"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aChar" items="${charTypeChars['physical']}">
									<%java.util.HashMap paramMap = new java.util.HashMap();
			paramMap.put("dispatch", pageContext.getAttribute("dispatchValue"));
			paramMap.put("particleName", session.getAttribute("particleName"));
			paramMap.put("particleType", session.getAttribute("particleType"));
			paramMap
					.put(
							"characterizationId",
							((gov.nih.nci.calab.dto.characterization.CharacterizationBean) pageContext
									.getAttribute("aChar")).getId());
			paramMap.put("submitType", "physical");
			pageContext.setAttribute("paramMap", paramMap);

			%>
									<span class="indented2"> <html:link forward="${aChar.name}" name="paramMap" title="${aChar.name}">${aChar.abbr}:${aChar.viewTitle}</html:link> </span>
									<br>
								</c:forEach>
								<br>
								<span class="indented"><strong>-In Vitro Characterization</strong></span>
								<br>
								<span class="indented2"><strong>-Toxicity</strong> &nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="submitAction.do?submitType=tox"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aChar" items="${charTypeChars['toxicity']}">
									<%java.util.HashMap toxicityParamMap = new java.util.HashMap();
			toxicityParamMap.put("dispatch", pageContext
					.getAttribute("dispatchValue"));
			toxicityParamMap.put("particleName", session
					.getAttribute("particleName"));
			toxicityParamMap.put("particleType", session
					.getAttribute("particleType"));
			toxicityParamMap
					.put(
							"characterizationId",
							((gov.nih.nci.calab.dto.characterization.CharacterizationBean) pageContext
									.getAttribute("aChar")).getId());
			toxicityParamMap.put("submitType", "tox");
			pageContext.setAttribute("toxicityParamMap", toxicityParamMap);

			%>
									<span class="indented3"> <html:link forward="${aChar.name}" name="toxicityParamMap" title="${aChar.name}">${aChar.abbr}:${aChar.viewTitle}</html:link></span>
									<br>
								</c:forEach>
								<span class="indented3"><strong>-Cytotoxicity</strong> &nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="submitAction.do?submitType=cytoTox"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aChar" items="${charTypeChars['cytoTox']}">
									<%java.util.HashMap cytotoxicityParamMap = new java.util.HashMap();
			cytotoxicityParamMap.put("dispatch", pageContext
					.getAttribute("dispatchValue"));
			cytotoxicityParamMap.put("particleName", session
					.getAttribute("particleName"));
			cytotoxicityParamMap.put("particleType", session
					.getAttribute("particleType"));
			cytotoxicityParamMap
					.put(
							"characterizationId",
							((gov.nih.nci.calab.dto.characterization.CharacterizationBean) pageContext
									.getAttribute("aChar")).getId());
			pageContext.setAttribute("cytotoxicityParamMap",
					cytotoxicityParamMap);
			cytotoxicityParamMap.put("submitType", "cytoTox");

			%>
									<span class="indented4"> <html:link forward="${aChar.name}" name="cytotoxicityParamMap" title="${aChar.name}">${aChar.abbr}:${aChar.viewTitle}</html:link> </span>
									<br>
								</c:forEach>
								<span class="indented3"><strong>-Immunotoxicity</strong></span>
								<br>
								<span class="indented4"><strong>-Blood Contact</strong> &nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="submitAction.do?submitType=bloodContactTox"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aChar" items="${charTypeChars['bloodContactTox']}">
									<%java.util.HashMap bloodContactParamMap = new java.util.HashMap();
			bloodContactParamMap.put("dispatch", pageContext
					.getAttribute("dispatchValue"));
			bloodContactParamMap.put("particleName", session
					.getAttribute("particleName"));
			bloodContactParamMap.put("particleType", session
					.getAttribute("particleType"));
			bloodContactParamMap
					.put(
							"characterizationId",
							((gov.nih.nci.calab.dto.characterization.CharacterizationBean) pageContext
									.getAttribute("aChar")).getId());
			pageContext.setAttribute("bloodContactParamMap",
					bloodContactParamMap);
			bloodContactParamMap.put("submitType", "bloodContactTox");

			%>
									<span class="indented5"><html:link forward="${aChar.name}" name="bloodContactParamMap" title="${aChar.name}">${aChar.abbr}:${aChar.viewTitle}</html:link></span>
									<br>
								</c:forEach>
								<br>
								<span class="indented4"><strong>-Immune Cell Function </strong>&nbsp;&nbsp;</span>
								<c:choose>
									<c:when test="${canUserSubmit eq 'true'}">
										<a href="submitAction.do?submitType=immuneCellFuncTox"> <em>add</em></a>
									</c:when>
								</c:choose>
								<br>
								<c:forEach var="aChar" items="${charTypeChars['immuneCellFuncTox']}">
									<%java.util.HashMap immuneCellParamMap = new java.util.HashMap();
			immuneCellParamMap.put("dispatch", pageContext
					.getAttribute("dispatchValue"));
			immuneCellParamMap.put("particleName", session
					.getAttribute("particleName"));
			immuneCellParamMap.put("particleType", session
					.getAttribute("particleType"));
			immuneCellParamMap
					.put(
							"characterizationId",
							((gov.nih.nci.calab.dto.characterization.CharacterizationBean) pageContext
									.getAttribute("aChar")).getId());
			pageContext.setAttribute("immuneCellParamMap", immuneCellParamMap);
			immuneCellParamMap.put("submitType", "immuneCellFuncTox");

		%>
									<span class="indented5"> <html:link forward="${aChar.name}" name="immuneCellParamMap" title="${aChar.name}">${aChar.abbr}:${aChar.viewTitle}</html:link> </span>
									<br>
								</c:forEach>
								<br>
							</li>
							<li>
								<span class="largerText">Other Associated Files &nbsp;&nbsp;</span>
								<c:forEach var="aReport" items="${particleAssociatedFiles}">									
									<span class="indented"> <a href="updateReportForParticle.do?page=0&dispatch=${dispatchValue}&submitType=none&fileId=${aReport.id}&fileType=${aReport.type}" title="${aReport.displayName}">${aReport.name}</a> </span>
									<br>
								</c:forEach>
								<br>
								<br>
							</li>
							<li>
								<span class="largerText">Reports</span>
								<br>
								<c:forEach var="aReport" items="${particleReports}">
									<span class="indented"> <a href="updateReportForParticle.do?page=0&dispatch=${dispatchValue}&submitType=none&fileId=${aReport.id}&fileType=${aReport.type}" title="${aReport.displayName}">${aReport.name}</a> </span>
									<br>
								</c:forEach>
							</li>
						</ul>
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
