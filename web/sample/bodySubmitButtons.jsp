<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width="100%" border="0" align="center" cellpadding="3"
	cellspacing="0">
	<tr>
		<td width="30%">
			<c:choose>
				<c:when
					test="${!empty dataId && canDelete eq 'true'}">
					<table height="32" border="0" align="left" cellpadding="4"
						cellspacing="0">
						<tr>
							<td height="32">
								<div align="left">
									<input type="button" value="Delete"
										onclick="deleteData('${type}', ${formName} '${actionName}')">
								</div>
							</td>
						</tr>
					</table>
				</c:when>
			</c:choose>
			<table width="498" height="32" border="0" align="right"
				cellpadding="4" cellspacing="0">
				<tr>
					<td width="490" height="32">
						<div align="right">
							<div align="right">
								<input type="reset" value="Reset"
									onclick="javascript:window.location.href='${origUrl}'">
								<input type="hidden" name="dispatch" value="create">
								<input type="hidden" name="page" value="2">
								<html:hidden property="sampleId" value="${sampleId}" />
								<html:hidden property="location" value="${location}" />
								<html:submit />
							</div>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>