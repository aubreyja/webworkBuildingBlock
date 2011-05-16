<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error/error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- errorPage="error/jasper.jsp" -->
<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="loginMessage1" key="Login.Message[1]" />
<fmt:message var="loginMessage2" key="Login.Message[2]" />
<fmt:message var="loginMessage3" key="Login.Message[3]" />
<fmt:message var="jsPreviousPage" key="JS.PreviousPage" />
<fmt:message var="currentPage" key="Page.Login" />
<fmt:message var="formName" key="Form.Login" />
<fmt:message var="successDisplay" key="Success.Display" />
<fmt:message var="failureDisplay" key="Failure.Display" />
<fmt:message var="successMessage" key="Success.Grades" />
<fmt:message var="failureMessage" key="Failure.Grades" />

<!--  Use Bean declarations  -->
<jsp:useBean id="assignmentInfo" class="edu.missouri.AssignmentInfo"
	scope="page">
</jsp:useBean>

<!--  Setting the blackboard utility class to session object for use in the building block -->
<jsp:useBean id="blackboardAccessorUtil" scope="session"
	class="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkUtil" scope="page"
	class="edu.missouri.WebworkUtil" />

<c:choose>

	<c:when test="${param.save != null}">
		<jsp:setProperty name="blackboardAccessorUtil" property="writeGrades"
			value="true" />
		<jsp:setProperty name="blackboardAccessorUtil"
			property="assignmentInfo" value="${assignmentInfo}" />

		<c:set var="gradesWritten"
			value="${(sessionScope.blackboardAccessorUtil.writeGrades eq true) ? true : false}" />
		<%-- 	<c:out value="${gradesWritten}"></c:out>
		<c:out
			value="${sessionScope.blackboardAccessorUtil.courseContentsUrl}" />
		<c:out value="${sessionScope.blackboardAccessorUtil.courseNumber}" />
		<c:out value="${sessionScope.blackboardAccessorUtil.contentNumber}" />
		<c:forEach
			items="${sessionScope.blackboardAccessorUtil.webworkGrades}"
			var="eachItem">
			<c:out value="${eachItem}" />
		</c:forEach> --%>
		<c:choose>
			<c:when test="${gradesWritten eq true}">
				<c:set var="redirectUrl"
					value="${sessionScope.blackboardAccessorUtil.courseContentsUrl}${successDisplay}${successMessage}" />
			</c:when>
			<c:otherwise>
				<c:set var="redirectUrl"
					value="${sessionScope.blackboardAccessorUtil.courseContentsUrl}${failureDisplay}${failureMessage}" />
			</c:otherwise>
		</c:choose>
		<c:remove var="blackboardAccessorUtil" scope="session" />
		<c:redirect url="${redirectUrl}"></c:redirect>
	</c:when>

	<c:otherwise>
		<bbNG:learningSystemPage ctxId="ctx" authentication="Y"
			checkGuest="${ (assignmentInfo.allowGuestViewers eq true)? 'Y' : 'N' }"
			disableEditToggle="false" title="webworkLogin">

			<!-- Setting the blackboard util the necessary properties. -->
			<jsp:setProperty name="blackboardAccessorUtil"
				property="blackboardUser" value="${ ctx.user }" />
			<jsp:setProperty name="blackboardAccessorUtil"
				property="courseNumber" param="course_id" />
			<jsp:setProperty name="blackboardAccessorUtil"
				property="contentNumber" param="content_id" />
			<jsp:setProperty name="blackboardAccessorUtil" property="requestUrl"
				value="${pageContext.request.requestURL }" />
			<jsp:setProperty name="blackboardAccessorUtil" property="requestUri"
				value="${pageContext.request.requestURI }" />

			<!-- Setting the assignment info the necessary properties.  -->
			<jsp:setProperty name="assignmentInfo" property="assignmentUser"
				value="${blackboardAccessorUtil.blackboardUser.userName }" />
			<jsp:setProperty name="assignmentInfo" property="*" />

			<!-- Setting the webwork util the necessary properties.  -->
			<jsp:setProperty name="webworkUtil" property="blackboardUser"
				value="${ blackboardAccessorUtil.blackboardUser }" />
			<jsp:setProperty name="webworkUtil" property="webworkCourse"
				value="${ assignmentInfo.assignmentCourse }" />
			<jsp:setProperty name="webworkUtil" property="webworkCourseSet"
				value="${ assignmentInfo.assignmentSet }" />
			<jsp:setProperty name="webworkUtil"
				property="publishWebworkAssignment" value="true" />

			<li>${loginMessage1}${assignmentInfo.assignmentSet}
				,${assignmentInfo.assignmentUser }.  ${loginMessage2} <a
				href="${assignmentInfo.assignmentUrl }"> WebWork </a></li>

			<iframe src="${assignmentInfo.assignmentUrl }" scrolling="auto"
				id="webworkAssignment" title="WebworkData" height="500 px"
				width="100%" marginheight="1 px" marginwidth="1 px" longdesc=""
				align="middle">
				<p>${loginMessage3}</p>
			</iframe>

			<!-- Buttons in the bottom of the page. -->
			<bbNG:button url="${jsPreviousPage}" label="Cancel" />
			<bbNG:button url="${currentPage}?save=true" label="Save" />

		</bbNG:learningSystemPage>
	</c:otherwise>
</c:choose>