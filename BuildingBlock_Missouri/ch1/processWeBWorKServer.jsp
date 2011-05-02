<!-- @author Martin Georgiev ( a 2009 intern at UoR )  -->
	 
<!-- APIs necessary for method processing -->
<%@ page import="java.util.*" errorPage="error.jsp" %>

<!-- tag libraries, necessary for proper page formatting -->
<%@ taglib uri = "/bbUI" prefix = "bbUI" %>
<%@ taglib uri = "/bbData" prefix = "bbData" %>

<%
	if ( 0 == Integer.parseInt( request.getParameter( "selectedServer" ) ) )
	{
		// redirect the trafic to the applicable pages
		response.sendRedirect( "selectWeBWorKCourseOnHosted2.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedServer=0");
	}
	else if ( 1 == Integer.parseInt( request.getParameter( "selectedServer" ) ) )
	{
		// redirect the trafic to the applicable pages
		response.sendRedirect( "selectWeBWorKCourseOnDevel8002.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedServer=1");
	}
	else if ( 2 == Integer.parseInt( request.getParameter( "selectedServer" ) ) )
	{
		// redirect the trafic to the applicable pages
		response.sendRedirect( "selectWeBWorKCourseOnDevel8035.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedServer=2");
	}
	else
	{
%>
		<bbUI:docTemplate title="Select a WeBWorK Server">
			<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
				<bbUI:breadcrumb>Create New WeBWorK Assignment</bbUI:breadcrumb>
			</bbUI:breadcrumbBar>
			
			<bbUI:titleBar >Select a WeBWorK Server</bbUI:titleBar>
			<bbUI:instructionBar>
				<%out.println( "<font color = \"red\"><b>ATTENTION:</b></font> The requested server is not an authorized server!" ); %>
			</bbUI:instructionBar>
		</bbUI:docTemplate>
<%	
	} // end of else if statements
%>