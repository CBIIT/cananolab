<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<table class="topBorderOnly" cellspacing="0" cellpadding="3"
	width="100%" align="center" summary="" border="0" rules="none">
	<tbody>
		<c:choose>
			<c:when
				test="${!empty nanoparticleCharacterizationForm.map.achar.derivedBioAssayDataList[param.fileInd].datumList}">
				<tr class="formSubTitle">
					<td>
						<strong>Name*</strong>
					</td>
					<td>
						<strong>Statistics Type</strong>
					</td>
					<td>
						<strong>Value*</strong>
					</td>
					<td>
						<strong>Unit</strong>
					</td>
					<td>
						<strong>Data Category</strong>
					</td>
					<td></td>
				</tr>
			</c:when>
		</c:choose>
		<logic:iterate id="ddata" name="nanoparticleCharacterizationForm"
			property="achar.derivedBioAssayDataList[${param.fileInd}].datumList"
			indexId="dInd">
			<tr>
				<td class="leftLabel">
					<c:choose>
						<c:when test="${canCreateNanoparticle eq 'true'}">
							<html:select styleId="assayName${param.fileInd}${dInd}"
								property="achar.derivedBioAssayDataList[${param.fileInd}].datumList[${dInd}].name"
								onchange="javascript:callPrompt('Name', 'assayName' + ${param.fileInd} + ${dInd});">
								<html:options name="datumNames" />
								<option value=""></option>
								<option value="other">Other</option>
							</html:select>&nbsp; 	
						</c:when>
						<c:otherwise>
							${ddata.name}&nbsp;
						</c:otherwise>
					</c:choose>
				</td>
				<td class="label">
					<c:choose>
						<c:when test="${canCreateNanoparticle eq 'true'}">
							<html:select styleId="statisticsType${param.fileInd}${dInd}"
								property="achar.derivedBioAssayDataList[${param.fileInd}].datumList[${dInd}].statisticsType"
								onchange="javascript:callPrompt('Statistics Type', 'statisticsType' + ${param.fileInd} + ${dInd});">
								<html:options name="charMeasureTypes" />
								<option value="other">Other</option>
							</html:select>&nbsp; 						
						</c:when>
						<c:otherwise>
							${ddata.statisticsType}&nbsp;
						</c:otherwise>
					</c:choose>
				</td>
				<td class="label">
					<c:choose>
						<c:when test="${canCreateNanoparticle eq 'true'}">
							<html:text
								property="achar.derivedBioAssayDataList[${param.fileInd}].datumList[${dInd}].value"
								size="5" />&nbsp; 						
						</c:when>
						<c:otherwise>
							${ddata.value}&nbsp;
						</c:otherwise>
					</c:choose>
				</td>
				<td class="label">
					<c:choose>
						<c:when test="${canCreateNanoparticle eq 'true'}">
							<html:select styleId="unit${param.fileInd}${dInd}"
								property="achar.derivedBioAssayDataList[${param.fileInd}].datumList[${dInd}].unit"
								onchange="javascript:callPrompt('Unit', 'unit' + ${param.fileInd} + ${dInd});">
								<html:options name="charMeasureUnits" />
								<option value="other">Other</option>
							</html:select>&nbsp; 						
						</c:when>
						<c:otherwise>
							${ddata.unit}&nbsp;
						</c:otherwise>
					</c:choose>
				</td>
				<td class="label">
					<c:choose>
						<c:when test="${canCreateNanoparticle eq 'true'}">
							<html:select
								property="achar.derivedBioAssayDataList[${param.fileInd}].datumList[${dInd}].category">
								<option />
									<c:forEach var="category"
										items="${nanoparticleCharacterizationForm.map.achar.derivedBioAssayDataList[param.fileInd].categories}">
										<html:option value="${category}">${category}</html:option>
									</c:forEach>
							</html:select>&nbsp; 						
						</c:when>
						<c:otherwise>
							${ddata.category}&nbsp;
						</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
					<c:when test="${canCreateNanoparticle eq 'true'}">

						<td class="rightLabel">
							<a href="#" class="removeLink"
								onclick="javascript:removeChildComponent(nanoparticleCharacterizationForm, '${nanoparticleCharacterizationForm.map.achar.actionName}', ${param.fileInd}, ${dInd}, 'removeData')">remove</a>
						</td>
					</c:when>
					<c:otherwise>
						<td class="rightLabel"></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</logic:iterate>
	</tbody>
</table>
<br>


<script language="JavaScript">
<!--//
  function filterDatumCategories(fileInd, datumCount) {            
    var categorySelection=getElement(nanoparticleCharacterizationForm, 'achar.derivedBioAssayDataList['+fileInd+'].categories');
	var categories = new Array();
	for (var i = 0; i<categorySelection.options.length; i++) {
		if (categorySelection.options[i].selected) {
			categories.push(categorySelection[i].value);
		}
	}
	for (var i=0; i<datumCount; i++) {
	  var datumCategorySelection=getElement(nanoparticleCharacterizationForm, 'achar.derivedBioAssayDataList['+fileInd+'].datumList['+i+'].category');	    	  
	  datumCategorySelection.length=0;
   	  if (categories.length>1) {
		datumCategorySelection.options[0]=new Option("", "");
	    for (var j = 0; j < categories.length; j++) {
		  datumCategorySelection.options[j+1] = new Option(categories[j], categories[j]);
	    }
	  }	  
	  else {	  
		for (var j = 0; j < categories.length; j++) {
		  datumCategorySelection.options[j] = new Option(categories[j], categories[j]);
		}	
	  }	  
	}
  }
//-->
</script>
