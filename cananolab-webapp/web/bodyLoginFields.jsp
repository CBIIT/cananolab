<tr>
	<td class="sidebarTitle">
		<a href="#" class="loginText" onClick="showhide('loginBlock');">Login</a>
		&nbsp;		
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
					<label for="login ID">LOGIN ID</label>
				</td>
				<td>
					<input type="text" name="loginId" size="14" id="login ID"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="sidebarContent">
					<label for="password">PASSWORD</label>
				</td>
				<td>
					<input type="password" name="password" size="14" id="password" />
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
