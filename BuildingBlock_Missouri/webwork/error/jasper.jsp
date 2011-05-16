<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Exception types.  -->
<c:set var="propertyException" value="Property Editor not registered" />
<c:set var="propertyNotFound" value="PropertyNotFoundException" />

<!-- Selecting the exceptions.  -->
<c:choose>
	<c:when
		test="${fn:contains(pageContext.exception.message, propertyException )}">
		<c:set var="displayMessage"
			value="EL is not good enough to capture different properties. Check the properties of 
					useBean with the parameters properties"
			scope="page" />
	</c:when>
	<c:when
		test="${fn:contains(pageContext.exception.message, propertyNotFound )}">
		<c:set var="displayMessage"
			value="EL cannot find the specified properties. Check UseBean and the property registered."
			scope="page" />
	</c:when>
	<c:when
		test="${fn:contains(pageContext.exception.message, java.lang.reflect.InvocationTargetException )}">
		<c:set var="displayMessage"
			value="Something wrong happened with the webservice functions. The detail message is available in the log file."
			scope="page" />
	</c:when>

	<c:otherwise>
		<c:set var="displayMessage"
			value="Unknown Error message: ${pageContext.exception.message}"
			scope="page" />

	</c:otherwise>
</c:choose>

<bbNG:genericPage authentication="Y" title="Exception Occured">
	<bbNG:pageHeader
		instructions="Click OK to be redirected to an appropriate valid page.">
		<bbNG:pageTitleBar
			title="Following exception occured while executing the page." />
	</bbNG:pageHeader>

	<bbNG:jspBlock>
		${pageScope.displayMessage }
		<br />
		The error is logged. Sorry for the inconvenience caused. Please try back from the beginning. 
		If the error persists please contact the system administrator with the error message. 
		<br />
		Detailed Exception: <br/>
		<c:forEach items="${pageContext.errorData.throwable.stackTrace}" var="element">
			<c:out value="${element}" />
			<br/>
		</c:forEach>
		Detailed Cause: <br/>
		<c:forEach items="${pageContext.errorData.throwable.cause.stackTrace}" var="element">
			<c:out value="${element}" />
			<br/>
		</c:forEach>
		Root Cause: <br/>
		<c:forEach items="${pageContext.errorData.throwable.cause.cause.stackTrace}" var="element">
			<c:out value="${element}" />
			<br/>
		</c:forEach>
		Primary Cause: <br/>
		<c:forEach items="${pageContext.errorData.throwable.cause.cause.cause.stackTrace}" var="element">
			<c:out value="${element}" />
			<br/>
		</c:forEach>
		<c:forEach items="${pageContext.errorData.throwable.cause.cause.cause.cause.stackTrace}" var="element">
			<c:out value="${element}" />
			<br/>
		</c:forEach>
		<c:forEach items="${pageContext.errorData.throwable.cause.cause.cause.cause.cause.stackTrace}" var="element">
			<c:out value="${element}" />
			<br/>
		</c:forEach>
		

	</bbNG:jspBlock>
	<bbNG:okButton url="${sessionScope.validPage }" />

</bbNG:genericPage>