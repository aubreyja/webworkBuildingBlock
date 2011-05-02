<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<c:choose>
	<c:when
		test="${fn:contains(pageContext.exception.message, 'authentication.BbSecurityException')}">
		<bbNG:receipt title="User not Enrolled" type="FAIL"
			recallUrl="javascript:history.go(-1);"></bbNG:receipt>
	</c:when>
	<c:otherwise>
	The following exception occured ${pageContext.exception }
<pre>
<%
	// now display a stack trace of the exception
  	PrintWriter pw = new PrintWriter( out );
  	exception.printStackTrace( pw );
%>
</pre>
	</c:otherwise>
</c:choose>

