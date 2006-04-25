<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<h2>
	View Aliquot
</h2>
<html:errors />
<logic:messagesPresent message="true">
	<ul>
		<font color="red"> <html:messages id="msg" message="true" bundle="workflow">
				<li>
					<bean:write name="msg" />
				</li>
			</html:messages> </font>
	</ul>
</logic:messagesPresent>
<blockquote>
	<html:form action="/preMaskAliquot">
		<table class="topBorderOnly" cellspacing="0" cellpadding="3" width="100%" align="center" summary="" border="0">
			<tbody>
				<tr class="topBorder">
					<td class="dataTablePrimaryLabel" width="30%">
						<div align="justify">
							<em>ALIQUOT <bean:write name="aliquot" property="aliquotName" /></em>
						</div>
					</td>
				</tr>
				<tr>
					<td class="formLabel">
						<div align="justify">
							<strong>Container Type </strong> <span class="formFieldWhite"> <logic:present name="aliquot" property="container.containerType">
									<bean:write name="aliquot" property="container.containerType" />
								</logic:present> <logic:notPresent name="aliquot" property="container.containerType">
									<bean:write name="aliquot" property="container.otherContainerType" />
								</logic:notPresent> </span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="formLabelWhite style1">
						<div align="left">
							<strong>Quantity </strong><span class="formFieldWhite"><bean:write name="aliquot" property="container.quantity" /></span> <span class="formFieldWhite"> <bean:write name="aliquot" property="container.quantityUnit" /></span> &nbsp; <strong>Concentration
							</strong><span class="formFieldWhite"><bean:write name="aliquot" property="container.concentration" /></span> <span class="formFieldWhite"> <bean:write name="aliquot" property="container.concentrationUnit" /> </span>&nbsp; <strong> Volume </strong><span
								class="formFieldWhite"><bean:write name="aliquot" property="container.volume" /></span><span class="formFieldWhite"> <bean:write name="aliquot" property="container.volumeUnit" /> </span> &nbsp;&nbsp;&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td class="formLabel">
						<div align="justify">
							<strong>Diluents/Solvent </strong>
							<bean:write name="aliquot" property="container.solvent" />
							&nbsp; &nbsp; &nbsp; <strong>How Created</strong>
							<bean:write name="aliquot" property="howCreated" />
							&nbsp; &nbsp; &nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td class="formLabelWhite style1">
						<div align="justify">
							<strong>Storage Conditions </strong><span class="formField"><bean:write name="aliquot" property="container.storageCondition" /></span>
						</div>

						<div align="justify"></div>
					</td>
				</tr>
				<tr>
					<td class="formLabel">
						<div align="left">
							<strong>Storage Location<br> <br> Room&nbsp; </strong>
							<bean:write name="aliquot" property="container.storageLocation.room" />
							&nbsp;<strong> Freezer&nbsp; </strong>
							<bean:write name="aliquot" property="container.storageLocation.freezer" />
							&nbsp;<strong>Shelf </strong>&nbsp;
							<bean:write name="aliquot" property="container.storageLocation.shelf" />
							&nbsp;<strong> Box </strong>&nbsp;
							<bean:write name="aliquot" property="container.storageLocation.box" />
							&nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<td class="formLabelWhite">
						<div align="left">
							<strong>General Comments</strong>
							<br>
							<span class="formFieldWhite"> <bean:write name="aliquot" property="container.containerComments" /></span>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<br>
		<table border="0" width="100%" align="center" cellpadding="3" cellspacing="0" class="topBorderOnly" summary="">
			<tr>
				<td width="30%">
					<span class="formMessage"> Aliquoted by: <bean:write name="aliquot" property="creator" /> Aliquoted Date: <bean:write name="aliquot" property="creationDate" /></span>
					<br>
					<br>
					<div align="right"></div>
				</td>
			</tr>
			<logic:equal name="aliquot" property="maskStatus" value="Active" >
			<tr>
				<td height="32">
					<div align="right">			
						<html:hidden name="aliquot" property="aliquotId" />
						<input type="hidden" name="aliquotName" value=<bean:write name="aliquot" property="aliquotName" /> />
						<html:submit value="Mask Aliquot" />
					</div>
				</td>
			</tr>
			</logic:equal>
		</table>
	</html:form>

	<p>
		&nbsp;
	</p>
	<p>
		&nbsp;
	</p>
	<p>
		&nbsp;
	</p>
</blockquote>

