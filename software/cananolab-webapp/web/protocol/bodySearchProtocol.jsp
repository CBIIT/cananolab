<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="javascript/ProtocolManager.js"></script>
<script type="text/javascript"
	src="/caNanoLab/dwr/interface/ProtocolManager.js"></script>
<script type='text/javascript' src='/caNanoLab/dwr/engine.js'></script>
<script type='text/javascript' src='/caNanoLab/dwr/util.js'></script>

<jsp:include page="/bodyTitle.jsp">
	<jsp:param name="pageTitle" value="Search Protocols" />
	<jsp:param name="topic" value="search_protocol_help" />
	<jsp:param name="glossaryTopic" value="glossary_help" />
</jsp:include>
<html:form action="searchProtocol" styleId="searchProtocolForm">
	<jsp:include page="/bodyMessage.jsp?bundle=protocol" />
	<table width="100%" align="center" class="submissionView" summary="layout">
		<tr>
			<td class="cellLabel" width="120">
				<label for="protocolType">Protocol Type</label>
			</td>
			<td>
				<html:select styleId="protocolType" property="protocolType">
					<option />
						<html:options name="protocolTypes" />
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="cellLabel" width="120">
				<label for="protocolName">Protocol Name</label>
			</td>
			<td>
				<table class="invisibleTable" summary="layout">
					<tr>
						<td><label for="nameOperand" style="display:none">Name Operand</label>
							<html:select property="nameOperand" styleId="nameOperand">
								<html:options collection="stringOperands" property="value"
									labelProperty="label" />
							</html:select>
						</td>
						<td>
							<html:text styleId="protocolName" property="protocolName" size="80" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="cellLabel" width="120">
				<label for="protocolAbbreviation">Protocol Abbreviation</label>
			</td>
			<td>
				<table class="invisibleTable" summary="layout">
					<tr>
						<td><label for="abbreviationOperand" style="display:none">Abbreviation Operand</label>
							<html:select property="abbreviationOperand"
								styleId="abbreviationOperand">
								<html:options collection="stringOperands" property="value"
									labelProperty="label" />
							</html:select>
						</td>
						<td>
							<html:text styleId="protocolAbbreviation" property="protocolAbbreviation" size="80" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="cellLabel" width="120">
				<label for="fileTitle">Protocol File Title</label>
			</td>
			<td>
				<table class="invisibleTable" summary="layout">
					<tr>
						<td><label for="titleOperand" style="display:none">Title Operand</label>
							<html:select property="titleOperand" styleId="titleOperand">
								<html:options collection="stringOperands" property="value"
									labelProperty="label" />
							</html:select>
						</td>
						<td>
							<html:text styleId="fileTitle" property="fileTitle" size="80" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<c:set var="dataType" value="protocol" />
	<c:set var="resetOnclick" value="this.form.reset();" />
	<c:set var="hiddenDispatch" value="search" />
	<c:set var="hiddenPage" value="1" />
	<%@include file="../bodySearchButtons.jsp"%>
</html:form>
<!--_____ main content ends _____-->
