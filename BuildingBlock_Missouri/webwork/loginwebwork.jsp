<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error/error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!--  Use Bean declarations  -->
<jsp:useBean id="assignmentInfo" class="edu.missouri.AssignmentInfo"
	scope="page">
</jsp:useBean>

<!--  Setting the blackboard utility class to session object for use in the building block -->
<jsp:useBean id="blackboardAccessorUtil" scope="page"
	class="edu.missouri.BlackboardUtil" />

<!--  Setting the bean properties -->
<bbNG:learningSystemPage ctxId="ctx" authentication="Y"
	checkGuest="${ (assignmentInfo.allowGuestViewers eq true)? 'Y' : 'N' }"
	disableEditToggle="false" title="webworkLogin"
	entitlement="course.content.VIEW">

	<jsp:setProperty property="blackboardUser"
		name="blackboardAccessorUtil" value="${ ctx.user }" />
	<jsp:setProperty property="courseNumber" name="blackboardAccessorUtil"
		param="course_id" />
	<jsp:setProperty property="contentNumber" name="blackboardAccessorUtil"
		param="content_id" />
	<jsp:setProperty property="assignmentUser" name="assignmentInfo"
		value="${blackboardAccessorUtil.blackboardUser.userName }" />
	<jsp:setProperty property="*" name="assignmentInfo" />

	<li>${assignmentInfo.assignmentLoginMessage }</li>
	<li>${assignmentInfo.constantAssignmentFramesMessage} <a
		href="${assignmentInfo.assignmentUrl }"> WebWork </a></li>

	<iframe src="${assignmentInfo.assignmentUrl }" scrolling="auto"
		id="webworkAssignment" title="WebworkData" height="500 px"
		width="100%" marginheight="1 px" marginwidth="1 px" longdesc=""
		align="middle">
		<p>${assignmentInfo.constantAssignmentFramesErrorMessage }</p>
	</iframe>

	<bbNG:okButton url="${blackboardAccessorUtil.courseContentsUrl }" />

</bbNG:learningSystemPage>