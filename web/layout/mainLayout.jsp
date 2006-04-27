<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<title><tiles:getAsString name="title" ignore="true"/></title>
<link rel="stylesheet" type="text/css" href="css/caLab.css" />
<link rel="stylesheet" type="text/css" href="css/menu.css" />
<link rel="StyleSheet" type="text/css" href="css/dtree.css" >
<script type="text/javascript" src="javascript/script.js"></script>
<script type="text/javascript" src="javascript/dtree.js"></script>
<script type="text/javascript" language="JavaScript">
<!--
	function s_show(){return false}
	function s_hide(){return false}
	if(window.event+''=='undefined')event=0
//-->
</script>
<script type="text/javascript" language="JavaScript1.2" src="javascript/s_loader.js"></script>
<script type="text/javascript" language="JavaScript1.2" src="javascript/s_arrays.js"></script>
<script type="text/javascript" language="JavaScript1.2" src="javascript/s_script_dom.js"></script>
<script type="text/javascript" language="JavaScript1.2" src="javascript/s_script_ns4.js"></script>
<script type="text/javascript" language="JavaScript1.2" src="javascript/s_script_old.js"></script>
<script type="text/javascript" language="JavaScript1.2" src="javascript/script.js"></script>

</head>
<tiles:importAttribute scope="session"/>
	<body>
		<table height="100%" cellspacing="0" cellpadding="0" width="100%" summary="" border="0" align="center">
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
						<table height="100%" cellspacing="0" cellpadding="0" summary="" border="0">
							<tbody>
								<tr>
									<td colspan="2" height="50">
										<%-- include caLAB header --%>
										<tiles:insert attribute="calabHeader" />
									</td>
								</tr>
								<tr>
									<td class="sideMenu" valign="top" width="190">
										<%-- include sidemenu on the left --%>
										<tiles:insert attribute="calabSidemenu" />
									</td>

									<td valign="top" width="100%">
										<table height="100%" cellspacing="0" cellpadding="0" width="100%" summary="" border="0">
											<tbody>
												<tr>
													<td class="mainMenu" width="100%" height="20">
														<%-- include caLAB main menu --%>
														<tiles:insert attribute="calabMainmenu" />
													</td>
												</tr>
												<tr>
													<td width="100%" valign="top">
														<%-- include caLAB main content --%>
														<tiles:insert attribute="calabContent" />
													</td>
												</tr>
												<tr>
													<td class="footerMenu" width="100%" height="20">
														<%-- include caLAB footer --%>
														<tiles:insert attribute="calabFooter" />
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