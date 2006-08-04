<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:form action="/createNanoparticle">
	<table width="100%" align="center">
		<tr>
			<td>
				<h3>
					<br>
					Create Nanoparticle Metadata
				</h3>
			</td>
			<td align="right" width="15%">
				<a href="javascript:openHelpWindow('webHelp/caLAB_0.5/index.html?single=true&amp;context=caLAB_0.5&amp;topic=create_nanoparticle')" class="helpText">Help</a>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<jsp:include page="/bodyMessage.jsp?bundle=submit" />
				<table class="topBorderOnly" cellspacing="0" cellpadding="3" width="100%" align="center" summary="" border="0">
					<tbody>
						<tr class="topBorder">
							<td class="formTitle" colspan="4">
								<div align="justify">
									Description
								</div>
							</td>
						</tr>
						<tr>
							<td class="leftLabel">
								<strong>Particle ID*</strong>
							</td>
							<td class="label"">
								<html:select property="particleName">
									<option value=""></option>
									<html:options name="allSampleNames" />
								</html:select>
							</td>
							<td class="label">
								<strong>Visibility*</strong>
							</td>
							<td class="rightLabel">
								<html:select property="visibilities" multiple="true" size="3">
									<option />
									<option value="CCNE_Researcher">
										CCNE Researchers
									</option>
									<option value="Public">
										Public
									</option>
									<option value="Vendor">
										Vendors
									</option>
								</html:select>
							</td>
						</tr>
					</tbody>
				</table>
				<br>
				<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="topBorderOnly" summary="">
					<tr>
						<td width="30%">
							<span class="formMessage"> </span>
							<br>
							<table width="498" height="32" border="0" align="right" cellpadding="4" cellspacing="0">
								<tr>
									<td width="490" height="32">
										<div align="right">
											<div align="right">
												<input type="reset" value="Reset" onclick="javascript:removeSelectOptions(document.createRunForm.assayName);resetSelect(document.createRunForm.sampleNames);removeSelectOptions(document.createRunForm.assignedAliquots);">
												<input type="hidden" name="dispatch" value="create">
												<input type="hidden" name="page" value="1">
												<html:submit />
											</div>
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
