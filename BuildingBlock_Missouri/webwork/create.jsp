<%@ page autoFlush="true" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Remove Session scoped attributes. -->
<c:if test="${sessionScope.blackboardUtil != null}">
	<c:remove var="blackboardUtil" scope="session" />
	<c:remove var="webworkUtil" scope="session" />
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
		<!--  Setting the webwork utility class to session object for use in the building block -->
		<%-- <jsp:setProperty property="sessionObject" name="blackboardUtil"
			value="${pageContext.session }" /> --%>
	</jsp:useBean>

	<!-- Setting the blackboard user to webworkUtil for necessary functioning. -->
	<jsp:useBean id="webworkUtil" scope="session"
		class="edu.missouri.WebworkUtilMissouri" />
		<!-- TODO only have this above statement. Remove the session and other stuff. -->
	<jsp:setProperty property="blackboardUser" name="webworkUtil"
		value="${ blackboardUtil.blackboardUser }" />

	<!-- Forwarding to the courses selection page. -->
	<jsp:forward page="courses.jsp"></jsp:forward>

</bbNG:genericPage>