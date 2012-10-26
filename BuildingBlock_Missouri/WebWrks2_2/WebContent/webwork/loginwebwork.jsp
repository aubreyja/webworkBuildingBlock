<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" errorPage="error/jasper.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="loginMessage1" key="Login.Message[1]" />
<fmt:message var="loginMessage2" key="Login.Message[2]" />
<fmt:message var="loginMessage3" key="Login.Message[3]" />
<fmt:message var="buttonLabelSave" key="Login.Button.Save" />
<fmt:message var="buttonLabelCancel" key="Login.Button.Cancel" />
<fmt:message var="jsPreviousPage" key="JS.PreviousPage" />
<fmt:message var="currentPage" key="Page.Login" />
<fmt:message var="formName" key="Form.Login" />
<fmt:message var="successDisplay" key="Success.Display" />
<fmt:message var="failureDisplay" key="Failure.Display" />
<fmt:message var="successMessage" key="Success.Grades" />
<fmt:message var="failureMessage" key="Failure.Grades" />

<!--  Use Bean declarations  -->
<jsp:useBean id="assignmentInfo" class="edu.missouri.AssignmentInfo" scope="session">
</jsp:useBean>

<!--  Setting the blackboard utility class to session object for use in the building block -->
<jsp:useBean id="blackboardAccessorUtil" scope="session" class="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkAccessorUtil" scope="session" class="edu.missouri.WebworkUtil" />

<c:choose>

	<c:when test="${param.save != null}">
		<!-- Setting necessary info and capturing the grade value to blackboard DB. -->
		<jsp:setProperty name="blackboardAccessorUtil" property="assignmentInfo" value="${assignmentInfo}" />
		<jsp:setProperty name="blackboardAccessorUtil" property="writeGrades" value="true" />

		<!-- Based on grades value display success / failure message. -->
		<c:set var="gradesWritten" value="${(sessionScope.blackboardAccessorUtil.writeGrades eq true) ? true : false}" />
		<c:choose>
			<c:when test="${gradesWritten eq true}">
				<c:set var="redirectUrl" value="${sessionScope.blackboardAccessorUtil.courseContentsUrl}${successDisplay}${successMessage}" />
			</c:when>
			<c:otherwise>
				<c:set var="redirectUrl" value="${sessionScope.blackboardAccessorUtil.courseContentsUrl}${failureDisplay}${failureMessage}" />
			</c:otherwise>
		</c:choose>
		<c:remove var="blackboardAccessorUtil" scope="session" />
		<c:redirect url="${redirectUrl}"></c:redirect>
	</c:when>

	<c:otherwise>
		<bbNG:learningSystemPage ctxId="ctx" authentication="Y" checkGuest="${ (assignmentInfo.allowGuestViewers eq true)? 'Y' : 'N' }" disableEditToggle="false" title="webworkLogin">

			<!-- Setting the blackboard util the necessary properties. -->
			<jsp:setProperty name="blackboardAccessorUtil" property="blackboardUser" value="${ ctx.user }" />
			<jsp:setProperty name="blackboardAccessorUtil" property="courseNumber" param="course_id" />
			<jsp:setProperty name="blackboardAccessorUtil" property="contentNumber" param="content_id" />
			<jsp:setProperty name="blackboardAccessorUtil" property="requestUrl" value="${pageContext.request.requestURL }" />
			<jsp:setProperty name="blackboardAccessorUtil" property="requestUri" value="${pageContext.request.requestURI }" />

			<!-- Setting the assignment info the necessary properties.  --> 
			<jsp:setProperty name="assignmentInfo" property="assignmentUser" value="${blackboardAccessorUtil.blackboardUser.userName }" />
			<jsp:setProperty name="assignmentInfo" property="blackboardUser" value="${ ctx.user }" />
			<jsp:setProperty name="assignmentInfo" property="*" />
			<jsp:setProperty name="assignmentInfo" property="newAssignmentUrl" value="true" />

			<!-- Setting the webwork util the necessary properties.  -->
			<jsp:setProperty name="webworkAccessorUtil" property="blackboardUser" value="${ blackboardAccessorUtil.blackboardUser }" />
			<jsp:setProperty name="webworkAccessorUtil" property="webworkCourse" value="${ assignmentInfo.assignmentCourse }" />
			<jsp:setProperty name="webworkAccessorUtil" property="webworkCourseSet" value="${ assignmentInfo.assignmentSet }" />
			<jsp:setProperty name="webworkAccessorUtil" property="checkWebworkUserPermissions" value="true" />

			<c:set var="messageLabel" value="${ loginMessage1 }${ assignmentInfo.assignmentSet }, ${ assignmentInfo.assignmentUser }. ${ loginMessage2 } " />
			<!-- <bbNG:buttonPalette label="${ messageLabel }" url="${ assignmentInfo.assignmentUrl }"  /> -->
			
			<div style="text-align:center; width:100%; margin:20px 0 20px 0;">
				<a href="${ assignmentInfo.assignmentUrl }" target="new">${ messageLabel }</a>
			</div>
			
			
			<iframe src="${ assignmentInfo.assignmentUrl }" scrolling="auto" id="webworkAssignment" title="WebworkData" height="500 px" width="100%" marginheight="1 px"
				marginwidth="1 px" longdesc="" align="middle">
				<p>${loginMessage3}</p>
			</iframe>

			<!-- Buttons in the bottom of the page. -->
			<c:choose>
				<c:when test="${ blackboardAccessorUtil.courseInstructor }">
					<bbNG:buttonPalette label="Grade Blackboard users" url="${ blackboardAccessorUtil.buildingBlockURI }/grades.jsp" />
				</c:when>
				<c:otherwise>
					<bbNG:button url="${ jsPreviousPage }" label="${ buttonLabelCancel }" />
					<bbNG:button url="${ currentPage }?save=true" label="${ buttonLabelSave }" />
				</c:otherwise>
			</c:choose>

		</bbNG:learningSystemPage>
	</c:otherwise>
</c:choose>