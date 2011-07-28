<%@ page autoFlush="true" session="true" errorPage="error/jasper.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Importing Messages Bundle -->
<fmt:message var="jsPreviousPage" key="JS.PreviousPage" />

<!-- Remove Session scoped attributes. -->
<c:if test="${sessionScope.blackboardUtil != null}">
	<c:remove var="blackboardUtil" scope="session" />
	<c:remove var="webworkUtil" scope="session" />
	<c:if test="${sessionScope.webworkData != null }">
		<c:remove var="webworkData" scope="session" />
	</c:if>
	<c:if test="${sessionScope.publishData != null }">
		<c:remove var="publishData" scope="session" />
	</c:if>
</c:if>

<!-- Setting valid page for session to the content page (in case of error). -->
<c:set scope="session" var="validPage" value="${jsPreviousPage}" />

<bbNG:genericPage ctxId="ctx" title="WebworkCreate">

	<!--  Setting the blackboard utility class to session object for use in the building block -->
	<jsp:useBean id="blackboardUtil" scope="session"
		class="edu.missouri.BlackboardUtil">
		<jsp:setProperty name="blackboardUtil" property="blackboardUser"
			value="${ ctx.user }" />
		<jsp:setProperty name="blackboardUtil" property="courseIdentity"
			value="${ ctx.course.id }" />
		<jsp:setProperty name="blackboardUtil" property="courseNumber"
			param="course_id" />
		<jsp:setProperty name="blackboardUtil" property="contentNumber"
			param="content_id" />
		<jsp:setProperty name="blackboardUtil" property="requestUrl"
			value="${pageContext.request.requestURL }" />
		<jsp:setProperty name="blackboardUtil" property="requestUri"
			value="${pageContext.request.requestURI }" />
	</jsp:useBean>

	<!-- Setting the blackboard user to webworkUtil for necessary functioning. -->
	<jsp:useBean id="webworkUtil" scope="session"
		class="edu.missouri.WebworkUtil" />
	<jsp:setProperty name="webworkUtil" property="blackboardUser"
		value="${ blackboardUtil.blackboardUser }" />

	<!-- Setting application attributes for caching request. -->
	<c:if test="${empty sessionScope.webworkCourses}">
		<c:set var="webworkCourses" scope="session"
			value="${webworkUtil.webworkCoursesList}" />
	</c:if>

	<!-- Forwarding to the courses selection page. -->
	<jsp:forward page="courses.jsp" />

</bbNG:genericPage>