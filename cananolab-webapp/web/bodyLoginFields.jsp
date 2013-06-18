<%--L
   Copyright SAIC
   Copyright SAIC-Frederick

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/cananolab/LICENSE.txt for details.
L--%>

<tr>
	<td class="sidebarTitle">
		<a href="#" class="loginText" onClick="showhide('loginBlock');">Login</a>
		&nbsp;
		<!--
		<a href="#" class="loginText"
			onClick="javascript:location.href='changePassword.jsp';">Update
			Password</a>&nbsp;&nbsp;
		-->
		<a href="#" class="loginText"
			onClick="javascript:location.href='register.jsp';">Register</a>&nbsp;&nbsp;
		<a
			href="javascript:openHelpWindow('webHelp/index.html?single=true&amp;context=caNanoLab&amp;topic=welcome_login')"
			class="loginText">Help</a>
	</td>
</tr>
<tr>
	<td class="sidebarContent">
		No account is required to browse publicly available data.
	</td>
</tr>
<tr>
	<td>
		<table id="loginBlock" style="display: none">
			<tr>
				<td align="right" class="sidebarContent">
					LOGIN ID
				</td>
				<td>
					<input type="text" name="loginId" size="14" />
				</td>
			</tr>
			<tr>
				<td align="right" class="sidebarContent">
					PASSWORD
				</td>
				<td>
					<input type="password" name="password" size="14" />
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<html:submit value="Login" />
				</td>
			</tr>
		</table>
	</td>
</tr>
