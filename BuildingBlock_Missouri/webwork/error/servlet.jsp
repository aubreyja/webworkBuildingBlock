<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Exception types.  -->
<c:set var="scopeException" value="not found within scope" />

<!-- Selecting the exceptions.  -->
<c:choose>
	<c:when
		test="${fn:contains(pageContext.exception.message, scopeException )}">
		<c:set var="displayMessage"
			value="Scope object not found, click ok to redirect to the start."
			scope="page" />
	</c:when>
	<c:otherwise>
	<c:set var="displayMessage"
			value="Error not expected. Please send the exception to the admin. ${exception.message }"
			scope="page" />
	</c:otherwise>
</c:choose>

<bbNG:genericPage authentication="Y" title="Exception Occured">
	<bbNG:pageHeader
		instructions="You will be redirected to the first page.">
		<bbNG:pageTitleBar
			title="Following exception occured while executing the page." />
	</bbNG:pageHeader>
	
	${pageScope.displayMessage }
	
	The error is logged. Sorry for the inconvenience caused. Please try back from the beginning. 
	If the error persists please contact the system administrator with the error message. 
	
	<bbNG:okButton url="create.jsp" />

</bbNG:genericPage>