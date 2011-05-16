<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error/error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="loginMessage1" key="Login.Message[1]" />
<fmt:message var="loginMessage2" key="Login.Message[2]" />
<fmt:message var="loginMessage3" key="Login.Message[3]" />
<fmt:message var="jsPreviousPage" key="JS.PreviousPage" />

<!--  Use Bean declarations  -->
<jsp:useBean id="assignmentInfo" class="edu.missouri.AssignmentInfo"
	scope="page" />
<jsp:useBean id="blackboardAccessorUtil" scope="page"
	class="edu.missouri.BlackboardUtil" />
