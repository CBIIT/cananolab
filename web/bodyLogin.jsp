<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<table summary="" cellpadding="0" cellspacing="0" border="0"
		height="100%">
		<tr>
			<td valign="top" width="450">
				<img src="images/bannerhome.jpg" width="450">
				<table summary="" cellpadding="0" cellspacing="0" border="0"
					height="100%">
					<tr>
						<td class="welcomeTitle" height="20">
							WELCOME TO caNanoLab
						</td>
					</tr>
					<tr>
						<td class="welcomeContent" valign="top">
							Welcome to the
							<strong>cancer Nanotechnology Laboratory (caNanoLab)</strong>
							portal. caNanoLab is a data sharing portal designed for
							laboratories performing nanoparticle assays. caNanoLab provides
							support for the annotation of nanoparticles with
							characterizations resulting from physical and in vitro
							nanoparticle assays and the sharing of these characterizations in
							a secure fashion.
							<br><br>
					<div id="publicLinks">
						<ul>
							<li>
							<c:url var="protocolURL" value="searchProtocol.do">
								<c:param name="dispatch">setup</c:param>
							</c:url>
							<h4><a href="${protocolURL}">Protocols</a></h4>
							<h5>Provides access to publicly available nanotechnology protocols. Includes protocols for nanoparticle characterizations (physical, in vitro, in vivo), sample preparation, radio labeling, and safety.
</h5>
							</li>
							<li>
							<c:url var="charURL" value="searchNanoparticle.do">
								<c:param name="dispatch">setup</c:param>
							</c:url>
							<h4><a href="${charURL}">Nanoparticles</a></h4>
							<h5>Provides access to publicly available nanoparticle characterization data.  Includes information on particle composition, function, and physical and in vitro characterizations (in vivo coming soon).</h5>
							</li>
							<li>
							<c:url var="reportURL" value="searchReport.do">
								<c:param name="dispatch">setup</c:param>
							</c:url>
							<h4><a href="${reportURL}">Reports</a></h4>
							<h5>Provides access to publicly available reports detailing the results of nanoparticle characterization projects.</h5>
							</li>
						</ul></div>
						</td>
					</tr>
				</table>
				<!-- welcome ends -->
				<p>
					&nbsp;
				</p>
			</td>
			<td valign="top">

				<!-- sidebar begins -->
				<html:form action="/login">
				<table summary="" cellpadding="0" cellspacing="0" border="0"
					height="100%">

					<!-- login begins -->
					<tr>
						<td valign="top">
							<table summary="" cellpadding="2" cellspacing="0" border="0"
								class="sidebarSection" width="100%">
								<tr>
									<td class="sidebarTitle" height="20">
										USER LOGIN
									</td>
								</tr>
								<tr>
									<td class="sidebarContent">
										<jsp:include page="/bodyMessage.jsp" />
									</td>
								</tr>
								<tr>
									<td class="sidebarContent">
										<table cellpadding="2" cellspacing="0" border="0">
											<tr>
												<td class="sidebarLogin" align="right">
													<label for="loginID">
														LOGIN ID
													</label>
												</td>
												<td class="formFieldLogin">
													<html:text property="loginId" size="14" />
												</td>
											</tr>
											<tr>
												<td class="sidebarLogin" align="right">
													<label for="password">
														PASSWORD
													</label>
												</td>
												<td class="formFieldLogin"><html:password property="password" size="14" />
												</td>
											</tr>
											<tr>
												<td>
													&nbsp;
												</td>
												<td>
													<html:submit value="Login" />
													&nbsp;&nbsp;
													<a
														href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=welcome_login')"
														class="helpText">Help</a>
													<br>
													<br>
													<input type="button" value="Update Password"
														onClick="javascript:location.href='changePassword.jsp'">
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- login ends -->

					<!-- what's new begins -->
					<tr>
						<td valign="top">
							<table summary="" cellpadding="0" cellspacing="0" border="0"
								class="noBottomSidebarSection" width="100%">
								<tr>
									<td class="sidebarTitle" height="20">
										WHAT'S NEW
									</td>
								</tr>
								<tr>
									<td class="sidebarContent">
										<strong>caNanoLab 1.2.1 is now available!</strong>
										<br>
										caNanoLab 1.2.1 contains the following features:
										<br>
										<ul>
											<li>MySQL 5.0.x Database
											</li>
											<li>
												Sample and Aliquot Management
											</li>
											<li>
												Assay Run Creation
											</li>
											<li>
												Assay Result Management
											</li>
											<li>
												Nanoparticle Annotation Creation, Deletion and Search
											</li>
											<li>
												Report and Protocol Management
											</li>
											<li>
												Remote Grid Search for Reports and Physical Composition Data
											</li>
											<li>
												Role-based Security
											</li>
										</ul>
										<br>
									</td>
								</tr>
								<tr height="100%">
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr height="100%">
						<td class="loginMenuFill">&nbsp;</td>
					</tr>
				</table>
				</html:form>
			</td>
		</tr>
	</table>

