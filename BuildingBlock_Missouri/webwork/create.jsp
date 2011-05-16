<%@ page autoFlush="true" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

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

<bbNG:genericPage ctxId="ctx" title="WebworkCreate">

	<!--  Setting the blackboard utility class to session object for use in the building block -->
	<jsp:useBean id="blackboardUtil" scope="session"
		class="edu.missouri.BlackboardUtil">
		<jsp:setProperty property="blackboardUser" name="blackboardUtil"
			value="${ ctx.user }" />
		<jsp:setProperty property="courseIdentity" name="blackboardUtil"
			value="${ ctx.course.id }" />
		<jsp:setProperty property="courseNumber" name="blackboardUtil"
			param="course_id" />
		<jsp:setProperty property="contentNumber" name="blackboardUtil"
			param="content_id" />
		<jsp:setProperty name="blackboardUtil" property="requestUrl"
			value="${pageContext.request.requestURL }" />
		<jsp:setProperty name="blackboardUtil" property="requestUri"
			value="${pageContext.request.requestURI }" />
		<!--  Setting the webwork utility class to session object for use in the building block -->
		<%-- <jsp:setProperty property="sessionObject" name="blackboardUtil"
			value="${pageContext.session }" /> --%>
	</jsp:useBean>

	<!-- Setting the blackboard user to webworkUtil for necessary functioning. -->
	<jsp:useBean id="webworkUtil" scope="session"
		class="edu.missouri.WebworkUtil" />
	<!-- TODO only have this above statement. Remove the session and other stuff. -->
	<jsp:setProperty property="blackboardUser" name="webworkUtil"
		value="${ blackboardUtil.blackboardUser }" />

	<%-- 	<!-- Session scoped attributes which are static and can be used by all pages. -->
	<c:set var="buildingBlockUri" value="${blackboardUtil.buildingBlockUri }" scope="session"></c:set>
 --%>

	<!-- Forwarding to the courses selection page. -->
	<jsp:forward page="courses.jsp"></jsp:forward>

</bbNG:genericPage>