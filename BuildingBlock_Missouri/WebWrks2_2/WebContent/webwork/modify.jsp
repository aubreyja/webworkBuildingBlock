<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	session="true" errorPage="error/jasper.jsp"%>

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="listElementName8" key="ListElementName.Assignment[8]" />
<fmt:message var="pageHeaderInstructions" key="PageHeader.Modify" />
<fmt:message var="pageTitle" key="PageTitleBar.Modify" />
<fmt:message var="previousPage" key="JS.PreviousPage" />

<!--  Use Bean declarations  -->
<jsp:useBean id="configUtil" class="edu.missouri.ConfigFile" scope="page">
	<jsp:setProperty name="configUtil" property="locationOfBean" value="modify.jsp" />
</jsp:useBean>
<jsp:useBean id="blackboardModifyUtil" scope="session" class="edu.missouri.BlackboardUtil" />

<c:choose>
	<c:when test="${not empty param.save}">
		<!-- Setting the properties for saving the data. -->
		<jsp:useBean id="modifyData" class="edu.missouri.PublishData" scope="page" />
		<jsp:setProperty name="modifyData" property="*" />
		<jsp:setProperty name="modifyData" property="endDate" param="endDate_date" />
		<jsp:setProperty name="modifyData" property="endTime" param="endDate_time" />
		<jsp:setProperty name="modifyData" property="startDate" param="startDate_date" />
		<jsp:setProperty name="modifyData" property="startTime" param="startDate_time" />		
		<jsp:setProperty name="modifyData" property="title" param="titletext" />
		<jsp:setProperty name="modifyData" property="modifyRecord" value="true" />

		<!-- Setting the data inside blackboard for writing to blackboard DB. -->
		<jsp:setProperty name="blackboardModifyUtil" property="publishData" value="${modifyData}" />
		<jsp:setProperty name="blackboardModifyUtil" property="publishAssignmentsData" value="true" />

		<!-- ConfigFile properties for getting the redirect url and displaying success / failed msg. -->
		<jsp:setProperty name="configUtil" property="serverPropertyWritten" value="true" />
		<jsp:setProperty name="configUtil" property="successLocation" value="${blackboardModifyUtil.courseContentsUrl }" />

		<!-- Redirect accordingly to the success / failure status with app. msg. -->
		<c:if test="${blackboardModifyUtil.publishAssignmentsData eq true}">
			<c:redirect url="${configUtil.redirectUrl }" />
		</c:if>
	</c:when>
	<c:otherwise>
		<bbNG:genericPage ctxId="ctx" title="Modify Webwork content">

			<!--  Setting the blackboard utility class to session object for use in the building block. -->
			<jsp:setProperty name="blackboardModifyUtil" property="blackboardUser" value="${ ctx.user }" />
			<jsp:setProperty name="blackboardModifyUtil" property="courseNumber" param="course_id" />
			<jsp:setProperty name="blackboardModifyUtil" property="contentNumber" param="content_id" />
			<jsp:setProperty name="blackboardModifyUtil" property="contentNumber" param="content_id" />
			<jsp:setProperty name="blackboardModifyUtil" property="requestUrl" value="${pageContext.request.requestURL }" />
			<jsp:setProperty name="blackboardModifyUtil" property="requestUri" value="${pageContext.request.requestURI }" />

			<jsp:setProperty name="configUtil" property="successLocation" value="${blackboardModifyUtil.courseContentsUrl }" />

			<!-- Setting the title & valid page attributes. -->
			<c:set var="title" value="${blackboardModifyUtil.contentModify.title}" />
			<c:set var="validPage" scope="session" value="${blackboardModifyUtil.courseContentsUrl }" />

			<bbNG:pageHeader instructions="${pageHeaderInstructions}">
				<bbNG:pageTitleBar title="${pageTitle }" />
			</bbNG:pageHeader>

			<bbNG:form id="modifyForm" action="modify.jsp">
				<input type="hidden" name="save" value="true" />
				<bbNG:dataCollection hasRequiredFields="true">
					<bbNG:step title="Mandatory info">
						<bbNG:dataElement label="Enter Assignment Title:" labelFor="title" isVertical="true">
							<bbNG:textbox name="title" text="${title}" rows="3" helpText="Edit the title info. Do not change any parameter info, if not sure." />
						</bbNG:dataElement>
						<bbNG:dataElement label="Start Date:" isVertical="true" labelFor="startDate">
							<bbNG:datePicker baseFieldName="startDate" dateTimeValue="${blackboardModifyUtil.contentModify.startDate}" isRequired="true" showTime="true" 
								showDate="true" />
						</bbNG:dataElement>
						<bbNG:dataElement label="End Date:" isVertical="true" labelFor="endDate">
							<bbNG:datePicker baseFieldName="endDate" dateTimeValue="${blackboardModifyUtil.contentModify.endDate}" isRequired="true" showTime="true" 
								showDate="true" />
						</bbNG:dataElement>

					</bbNG:step>
					<bbNG:step title="Extra info">
						<bbNG:dataElement label="Availability:" isVertical="true">
							<bbNG:radioElement name="isAvailable" value="true" isSelected="${ (blackboardModifyUtil.contentModify.isAvailable eq true)? true : false}">
								True
							</bbNG:radioElement>
							<bbNG:radioElement name="isAvailable" value="false" isSelected="${ (blackboardModifyUtil.contentModify.isAvailable eq false)? true : false}">
								False
							</bbNG:radioElement>
						</bbNG:dataElement>
						<bbNG:dataElement label="Tracking details:" isVertical="true">
							<bbNG:radioElement name="isTracked" value="true" isSelected="${ (blackboardModifyUtil.contentModify.isTracked eq true)? true : false}">
								True
							</bbNG:radioElement>
							<bbNG:radioElement name="isTracked" value="false" isSelected="${ (blackboardModifyUtil.contentModify.isTracked eq false)? true : false}">
								False
							</bbNG:radioElement>
						</bbNG:dataElement>
						<bbNG:dataElement label="Comments:" isVertical="true" labelFor="body">
							<bbNG:textbox name="body" rows="1" isSpellcheckOnly="true" ftext="${blackboardModifyUtil.contentModify.body}" helpText="Enter the comments." />
						</bbNG:dataElement>
					</bbNG:step>

					<bbNG:stepSubmit cancelOnClick="${previousPage}" />
				</bbNG:dataCollection>
			</bbNG:form>

		</bbNG:genericPage>
	</c:otherwise>
</c:choose>