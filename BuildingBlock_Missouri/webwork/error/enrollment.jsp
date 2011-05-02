<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

	The following exception occured ${pageContext.exception }
<pre>
<%
	// now display a stack trace of the exception
  	PrintWriter pw = new PrintWriter( out );
  	exception.printStackTrace( pw );
%>
</pre>