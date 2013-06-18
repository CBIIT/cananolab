<%--L
   Copyright SAIC
   Copyright SAIC-Frederick

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/cananolab/LICENSE.txt for details.
L--%>

<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/sample/bodyHideSearchDetailView.jsp"%>
<link rel="StyleSheet" type="text/css" href="css/main.css">
<link rel="StyleSheet" type="text/css" href="css/summaryView.css">

<table  width="95%" align="center" border="0" >
<tr>
<td><table class="gridTableData"  align="left">
	<tr><th colspan="3" align="center" >caNanoLab Availability Score: ${sampleBean.caNanoLabScore}<br/>
		MINChar Availability Score: ${sampleBean.mincharScore}<br/></th></tr>
	<tr>
		<td  class="cellLabel" align="center">
			caNanoLab
		</td>
		<td  class="cellLabel" align="center">
			MINChar
		</td>
		<td class="cellLabel" align="center">
			<c:out value="${sampleBean.domain.name}"/>
		</td>
	</tr>
	<tr>
		<td ></td>
		<td >agglomeration and/or aggregation </td>
		<td ></td>
	</tr>
	<tr>
		<td ></td>
		<td >crystal structure/crystallinity</td>
		<td ></td>
	</tr>	
	<tr>
		<td >
			General Sample Information
		</td>
		<td ></td>
		<td align="center">
			<img src="images/checkMark.jpg"  width=11%"/>
		</td>
	</tr>
	<tr>
		<td >
			Sample Composition
		</td>
		<td >
			chemical composition
		</td>
		<td align="center">
		<c:forEach var="data" items="${availableEntityNames}">
		<c:if test="${data eq 'sample composition'}" >
		<img src="images/icon_check.png" /></c:if>
		</c:forEach>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;&nbsp;nanomaterial entities
		</td>		
		<td>			
		</td>
		<td align="center">
		<c:forEach var="data" items="${availableEntityNames}">
		<c:if test="${data eq 'nanomaterial entities'}" >
		<img src="images/icon_check.png" /></c:if>
		</c:forEach>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;&nbsp;functionalizing entities
		</td>		
		<td>			
		</td>
		<td align="center">
		<c:forEach var="data" items="${availableEntityNames}">
		<c:if test="${data eq 'functionalizing entities'}" >
		<img src="images/icon_check.png" /></c:if>
		</c:forEach>
		</td>
	</tr>
	<tr>		
		<td>
			&nbsp;&nbsp;chemical associations
		</td>		
		<td>			
		</td>
		<td align="center">
		<c:forEach var="data" items="${availableEntityNames}">
		<c:if test="${data eq 'chemical associations'}" >
		<img src="images/icon_check.png" /></c:if>
		</c:forEach>
		</td>
	</tr>
	<c:forEach var="chem" items="${chemicalAssocs}">
	<tr>		
		<td>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${chem}"/>
		</td>		
		<td>
			<c:forEach var="nano2minchar" items="${caNano2MINChar}" >
		    		<c:set var="nano" value="${nano2minchar.key }"/>
					<c:set var="minchar" value="${nano2minchar.value}" />
					<c:if test="${nano eq chem}">${minchar}</c:if>
			</c:forEach>					
		</td>
		<td align="center">
		<c:forEach var="data" items="${availableEntityNames}">
		<c:if test="${data eq chem}" >
		<img src="images/icon_check.png" /></c:if>
		</c:forEach>
		</td>
	</tr>
	</c:forEach>
	<tr>
		<td>
			&nbsp;&nbsp;sample function
		</td>		
		<td>
			
		</td>
		<td align="center">
		<c:forEach var="data" items="${availableEntityNames}">
		<c:if test="${data eq 'sample function'}" >
		<img src="images/icon_check.png" /></c:if>
		</c:forEach>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			Physico-Chemical Characterization
		</td>
	</tr>
	
	<c:forEach var="char" items="${physicoChars}">
	<c:choose>
	<c:when test="${char eq 'surface'}">
		<tr>
			<td>
				&nbsp;&nbsp;<c:out value="${char}"/>
			</td>
			<td ></td>
			<td align="center">
				<c:forEach var="data" items="${availableEntityNames}">
					<c:if test="${data eq char}">
						<img src="images/icon_check.png" />
					</c:if>									
				</c:forEach>
			</td>
		</tr>
		<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;surface area</td>
		<td>
			<c:forEach var="nano2minchar" items="${caNano2MINChar}" >
		    		<c:set var="nano" value="${nano2minchar.key }"/>
					<c:set var="minchar" value="${nano2minchar.value}" />
					<c:if test="${nano eq 'surface area '}">${minchar}</c:if>
			</c:forEach>
		</td>
		<td align="center">
			<c:forEach var="data" items="${availableEntityNames}">
				<c:if test="${data eq 'surface area'}"> 
					<img src="images/icon_check.png" />
				</c:if>			
		</c:forEach>
		</td>	
		</tr>
		<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;surface charge</td>
		<td>
			<c:forEach var="nano2minchar" items="${caNano2MINChar}" >
		    		<c:set var="nano" value="${nano2minchar.key }"/>
					<c:set var="minchar" value="${nano2minchar.value}" />
					<c:if test="${'surface charge' == nano}">${minchar}</c:if>
			</c:forEach>
		</td>
		<td align="center">
			<c:forEach var="data" items="${availableEntityNames}">			
				<c:if test="${data == 'surface charge'}">
					<img src="images/icon_check.png" />
				</c:if>			
		</c:forEach>
		</td>	
		</tr>
		<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;zeta potential</td>
		<td>
			<c:forEach var="nano2minchar" items="${caNano2MINChar}" >
		    		<c:set var="nano" value="${nano2minchar.key }"/>
					<c:set var="minchar" value="${nano2minchar.value}" />
					<c:if test="${'zeta potential' == nano}">${minchar}</c:if>
			</c:forEach>
		</td>
		<td align="center">
			<c:forEach var="data" items="${availableEntityNames}">			
				<c:if test="${data == 'zeta potential'}">
					<img src="images/icon_check.png" />
				</c:if>			
		</c:forEach>
		</td>	
		</tr>
	</c:when>
		<c:otherwise>
		<tr>
		<td>&nbsp;&nbsp;<c:out value="${char}"/></td>
		<td>
			<c:forEach var="nano2minchar" items="${caNano2MINChar}" >
		    		<c:set var="nano" value="${nano2minchar.key }"/>
					<c:set var="minchar" value="${nano2minchar.value}" />
					<c:if test="${char == nano}">${minchar}</c:if>
			</c:forEach>
		</td>
		<td align="center">
			<c:forEach var="data" items="${availableEntityNames}">
				<c:if test="${data == char}">
					<img src="images/icon_check.png" />
				</c:if>			
		</c:forEach>
		</td>	
		</tr>			
		</c:otherwise>
		</c:choose>
	</c:forEach>	
	
	<tr>
		<td colspan="3">
			In Vitro Characterization
		</td>
	</tr>
	<c:forEach var="char" items="${invitroChars}">
		<tr>
		<td>&nbsp;&nbsp;<c:out value="${char}"/></td>
		<td>
			<c:forEach var="nano2minchar" items="${caNano2MINChar}" >
		    		<c:set var="nano" value="${nano2minchar.key }"/>
					<c:set var="minchar" value="${nano2minchar.value}" />
					<c:if test="${char == nano}">${minchar}</c:if>
			</c:forEach>
		</td>
		<td  align="center">
			<c:forEach var="data" items="${availableEntityNames}">
				<c:if test="${data == char}">
					<img src="images/icon_check.png" />
				</c:if>			
		</c:forEach>
		</td>	
		</tr>		
	</c:forEach>
	<tr>
		<td colspan="3">
			In Vivo Characterization
		</td>
	</tr>
	<c:forEach var="char" items="${invivoChars}">
		<tr>
		<td>&nbsp;&nbsp;<c:out value="${char}"/></td>
		<td>
			<c:forEach var="nano2minchar" items="${caNano2MINChar}" >
		    		<c:set var="nano" value="${nano2minchar.key }"/>
					<c:set var="minchar" value="${nano2minchar.value}" />
					<c:if test="${char == nano}">${minchar}</c:if>
			</c:forEach>
		</td>
		<td align="center">
			<c:forEach var="data" items="${availableEntityNames}">
				<c:if test="${data == char}">
					<img src="images/icon_check.png" />
				</c:if>			
		</c:forEach>
		</td>	
		</tr>	
	</c:forEach>	
	<tr>
		<td>
			Publications
		</td>
		<td></td>
		<td align="center">
			<c:forEach var="data" items="${availableEntityNames}">
				<c:if test="${data == 'publications'}">
					<img src="images/icon_check.png" />
				</c:if>			
		</c:forEach>
		</td>
	</tr>
</table>
</td>
</tr>
<tr><td>
<c:if test="${!empty updateSample && empty param.styleId}">	
<table class="invisibleTable" width="95%" align="center">
	<tr><td align="left"><input type="button" value="Delete"
					onclick="javascript:deleteDataAvailability('Data Availability for the sample', sampleForm, 'sample', 'deleteDataAvailability');" />
		</td>
		<td colspan="2" align="right"> 
			<div>			
				<input type="button" value="Update"
					onclick="javascript:updateDataAvailability(sampleForm, 'sample', 'updateDataAvailability');" />
				<input type="button" value="Cancel"
					onclick="javascript:hide('dataAvailability');show('editDataAvailability');" />	
			</div>		
		</td>				
	</tr>
</table>
</c:if>
</td>
</tr>
</table>