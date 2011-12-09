<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Exception types.  -->
<c:set var="propertyException" value="Property Editor not registered" />
<c:set var="propertyNotFound" value="PropertyNotFoundException" />
<c:set var="serverException" value="RemoteException" />
<c:set var="scopeException" value="not found within scope" />

<!-- Importing Messages Bundle -->
<fmt:message var="pageHeaderInstructions" key="PageHeader.Error" />
<fmt:message var="pageTitle" key="PageTitleBar.Error" />
<fmt:message var="previousPage" key="JS.ValidPage" />

<!-- Selecting the exceptions.  -->
<c:choose>
	<c:when
		test="${fn:contains(pageContext.exception.message, scopeException )}">
		<c:set var="displayMessage"
			value="Scope object not found. Session might have expired."
			scope="page" />
	</c:when>
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
			value="EL cannot find the specified properties. Tell the developer to check the 
					UseBean declaration and the properties for it."
			scope="page" />
	</c:when>
	<c:when
		test="${fn:contains(pageContext.exception.message, java.lang.reflect.InvocationTargetException )}">
		<c:set var="displayMessage"
			value="Something wrong happened with the invoking certain functions / webservice.
					The detail message is available in the log file."
			scope="page" />
	</c:when>
	<c:when
		test="${fn:contains(pageContext.exception.message, serverException )}">
		<c:set var="displayMessage"
			value="Some network problem. Please try again later." scope="page" />
	</c:when>
	<c:otherwise>
		<c:set var="displayMessage"
			value="Unknown Error message: ${pageContext.exception.message}"
			scope="page" />
	</c:otherwise>
</c:choose>

<bbNG:genericPage authentication="Y" title="Exception Occured.">

	<bbNG:pageHeader instructions="${pageHeaderInstructions}">
		<bbNG:pageTitleBar title="${pageTitle }" />
	</bbNG:pageHeader>

	<bbNG:jspBlock>
		${pageScope.displayMessage }
		<br />
		The error is logged. Sorry for the inconvenience caused.
		<br />
		If the error persists please contact the system administrator with the error message.
		<br />
	</bbNG:jspBlock>

	<c:if test="${empty sessionScope.validPage }">
		<c:set var="validPage" value="${previousPage}" />
	</c:if>

	<bbNG:okButton url="${sessionScope.validPage }" />

	<bbNG:collapsibleList isDynamic="false" id="exceptionInfo">
		<bbNG:collapsibleListItem id="exceptionInfo0"
			title="Message:" expandOnPageLoad="true">
			<c:out value="${pageContext.exception.message }" />
		</bbNG:collapsibleListItem>
		<bbNG:collapsibleListItem id="exceptionInfo1"
			title="Detailed Exception:">
			<c:forEach items="${pageContext.errorData.throwable.stackTrace}"
				var="element">
				<c:out value="${element}" />
				<br />
			</c:forEach>
		</bbNG:collapsibleListItem>
		<bbNG:collapsibleListItem id="exceptionInfo2" title="Detailed Cause:">
			<c:forEach
				items="${pageContext.errorData.throwable.cause.stackTrace}"
				var="element">
				<c:out value="${element}" />
				<br />
			</c:forEach>
		</bbNG:collapsibleListItem>
		<bbNG:collapsibleListItem id="exceptionInfo3" title="Root Cause:">
			<c:forEach
				items="${pageContext.errorData.throwable.cause.cause.stackTrace}"
				var="element">
				<c:out value="${element}" />
				<br />
			</c:forEach>
		</bbNG:collapsibleListItem>
		<bbNG:collapsibleListItem id="exceptionInfo4" title="Primary Cause:">
			<c:forEach
				items="${pageContext.errorData.throwable.cause.cause.cause.stackTrace}"
				var="element">
				<c:out value="${element}" />
				<br />
			</c:forEach>
			<h3></h3>
			<c:forEach
				items="${pageContext.errorData.throwable.cause.cause.cause.cause.stackTrace}"
				var="element">
				<c:out value="${element}" />
				<br />
			</c:forEach>
			<h3></h3>
			<c:forEach
				items="${pageContext.errorData.throwable.cause.cause.cause.cause.cause.stackTrace}"
				var="element">
				<c:out value="${element}" />
				<br />
			</c:forEach>
		</bbNG:collapsibleListItem>
	</bbNG:collapsibleList>

</bbNG:genericPage>