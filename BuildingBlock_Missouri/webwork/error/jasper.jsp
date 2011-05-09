<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Exception types.  -->
<c:set var="propertyException" value="Property Editor not registered" />
<c:set var="propertyNotFound" value="PropertyNotFoundException" />

<c:out value=${pageScope.propertyException }" />

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
	<c:otherwise>
	</c:otherwise>
</c:choose>

<bbNG:genericPage authentication="Y" title="Exception Occured">
	<bbNG:pageHeader
		instructions="Click OK to be redirected to an appropriate valid page.">
		<bbNG:pageTitleBar
			title="Following exception occured while executing the page." />
	</bbNG:pageHeader>
	
	${pageScope.displayMessage }
	
	The error is logged. Sorry for the inconvenience caused. Please try back from the beginning. 
	If the error persists please contact the system administrator with the error message. 
	
	<bbNG:okButton url="${sessionScope.validPage }" />

</bbNG:genericPage>