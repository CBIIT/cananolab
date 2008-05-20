<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
<!--//
function confirmDeletion(deleteName, deleteMsg)
{
	var checkedCount = 0;
	var i = 0;
	if (deleteName.value!=null){
		if (deleteName.checked){
			checkedCount = 1;
		}
	}else if (deleteName.length>0){
		for (i=0; i<deleteName.length; i++){
			if (deleteName[i].checked){
				checkedCount++;
				break;
			}
		}	
	}
	if (checkedCount==0){
		alert('Please select at least one '+deleteMsg+' to delete');
		return false;
	}else{
		answer = confirm('Are you sure you want to delete the '+deleteMsg+'?')		
		if (answer !=0)
		{
			this.document.forms[0].dispatch.value="deleteAll";
			this.document.forms[0].submit(); 
			return true;
		}
	}
}
//-->
</script>
<html:form action="/${actionName}">
	<table width="100%" align="center" border="0">
		<tr>
			<td>
				<h3>
					<br>
					Delete ${submitType} Characterizations
					<br>
				</h3>
			</td>
			<td align="right" width="20%">
				<jsp:include page="/webHelp/helpGlossary.jsp">
					<jsp:param name="topic" value="delete_characterization_help" />
					<jsp:param name="glossaryTopic" value="glossary_help" />
				</jsp:include>	
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<jsp:include page="/bodyMessage.jsp?bundle=particle" />
				<table class="topBorderOnly" cellspacing="0" cellpadding="3"
					width="100%" align="center" summary="" border="0">
					<tbody>
						<tr>
							<td class="formTitle" colspan="2">
								Please select characterizations you wish to delete:
							</td>
						</tr>
						<logic:iterate name="characterizationsToDelete" id="chara"
							indexId="charInd">
							<tr>
								<td class="leftBorderedFormFieldWhite">
									<html:multibox property="charIdsToDelete">
										${chara.id}
									</html:multibox>
									<bean:write name="chara" property="identificationName" />
								</td>
							</tr>
						</logic:iterate>
					</tbody>
				</table>
				<br>
				<table width="100%" border="0" align="center" cellpadding="3"
					cellspacing="0" class="topBorderOnly" summary="">
					<tr>
						<td width="30%">
							<span class="formMessage"> </span>
							<br>
							<table width="498" height="32" border="0" align="right"
								cellpadding="4" cellspacing="0">
								<tr>

									<td width="490" height="32">
										<div align="right">
											<div align="right">
												<input type="reset" value="Reset" onclick="" />
												<input type="hidden" name="dispatch" value="deleteAll" />
												<c:choose>
													<c:when test="${!empty param.particleId }">
														<html:hidden property="particleId"
															value="${param.particleId }" />
													</c:when>
													<c:otherwise>
														<html:hidden property="particleId" />
													</c:otherwise>
												</c:choose>
												<input type="hidden" name="submitType" value="${submitType}" />
												<input type="hidden" name="diaplayType"
													value="${param.displayType}" />
												<input type="button" value="Delete"
													onclick="confirmDeletion(charIdsToDelete, 'characterization');" />
												<input type="hidden" name="page" value="2">
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

