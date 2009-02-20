<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title><tiles:getAsString name="title" ignore="true" />
		</title>
		<meta name="keywords"
			content="nano informatics, nanotechnology model, caNanoLab, nanotechnology, nanoparticle, cancer, information model, portal, data portal, data repository, caBIG, caGRID, NCL, nano characterization, nanoparticle composition, Cancer Nanotechnology Excellence">
		<meta name="description"
			content="caNanoLab is a data sharing portal designed to facilitate information sharing in the biomedical nanotechnology research community to expedite and validate the use of nanotechnology in biomedicine">
		<link rel="icon" href="images/favicon.ico" />
		<link rel="shortcut icon" href="images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<%-- doesn't work in FireFox
		<c:if test="${!empty printLinkURL}">
			<link rel="alternate" name="printlink" media="print"
				href="${printLinkURL}">
		</c:if>
		--%>
		<script type="text/javascript" src="javascript/script.js"></script>
	</head>
	<tiles:importAttribute scope="session" />
	<tiles:importAttribute name="onloadJavascript" />
	<c:choose>
		<c:when test="${! empty onloadJavascript}">
			<body onload="${onloadJavascript}">
		</c:when>
		<c:otherwise>
			<body>
		</c:otherwise>
	</c:choose>
	<body>
		<table height="100%" cellspacing="0" cellpadding="0" width="100%"
			summary="" border="0" align="center">
			<!-- nci hdr begins -->
			<tbody>
				<tr>
					<td>
						<%-- include NCI header --%>
						<tiles:insert attribute="nciHeader" />
					</td>
				</tr>
				<tr>
					<td valign="top" height="100%">
						<table height="100%" cellspacing="0" cellpadding="0" summary=""
							border="0">
							<tbody>
								<tr>
									<td colspan="2" height="50">
										<%-- include caNanoLab header --%>
										<tiles:insert attribute="cananoHeader" />
									</td>
								</tr>
								<tr>
									<td class="sideMenu" valign="top" width="250">
										<%-- include sidemenu on the left --%>
										<tiles:insert attribute="cananoSidemenu" />
									</td>

									<td valign="top" width="100%">
										<table height="100%" cellspacing="0" cellpadding="0"
											width="100%" summary="" border="0">
											<tbody>
												<tr>
													<td class="mainMenu" width="100%" height="20">
														<%-- include caNanoLab main menu --%>
														<tiles:insert attribute="cananoMainmenu" />
													</td>
												</tr>
												<tr>
													<td width="800" valign="top">
														<%-- include caNanoLab main content --%>
														<table border="0" width="100%">
															<tr>
																<td width="15" height="15">
																	&nbsp;
																</td>
																<td>
																	&nbsp;
																</td>
																<td width="15">
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td width="15">
																	&nbsp;
																</td>
																<td valign="top">
																	<%--main content starts --%>
																	<tiles:insert attribute="cananoContent" />
																</td>
																<td width="15">
																	&nbsp;
																</td>
															</tr>
															<tr>
																<td width="15" height="15">
																	&nbsp;
																</td>
																<td>
																	&nbsp;
																</td>
																<td width="15">
																	&nbsp;
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="footerMenu" width="100%" height="20">
														<%-- include caNanoLab footer --%>
														<tiles:insert attribute="cananoFooter" />
													</td>
												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<%-- include NCI footer --%>
						<tiles:insert attribute="nciFooter" />
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>
