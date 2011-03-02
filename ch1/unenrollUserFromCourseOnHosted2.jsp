<!-- @author Martin Georgiev ( a 2009 intern at UoR ) -->
	 
<!-- APIs necessary for method processing -->
<%@ page import="java.util.*,
                 java.text.*,
                 blackboard.data.*,
                 blackboard.persist.*,
                 blackboard.data.course.*,
                 blackboard.data.user.*,
                 blackboard.persist.course.*,
                 blackboard.data.content.*,
                 blackboard.persist.content.*,
                 blackboard.db.*,
                 blackboard.base.*,
                 blackboard.platform.*,
                 blackboard.platform.log.*,
                 blackboard.platform.persistence.*,
                 blackboard.platform.security.*,
	             blackboard.platform.session.*,
                 blackboard.platform.db.*,
             	 blackboard.servlet.data.*,
                 edu.rochester.webwork.hosted2.WebworkSOAP.*" 
	      errorPage="error.jsp"
%>
<!-- tag libraries, necessary for proper page formatting -->
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">
	<%	
		//get the user object from the context
		User sessionUser = ctx.getUser();
		
		// get the user name from the user object
		String theUser = sessionUser.getUserName();
		
		// get the course object from the Blackboard database
		Course courseId = ctx.getCourse();
		
		// get the course name from the course object
		String theBlackboardCourse = courseId.getBatchUid();
	  
		// default authentication key set in the WeBWorK database
		final String authKey = "123456789123456789";
		
		// Declare and instantiate SOAP classes; Get connected to the WeBWorK Server
		WebworkSOAPHandlerService handlerService = new WebworkSOAPHandlerServiceLocator();
		WebworkSOAPHandler soapHandler = handlerService.getWebworkSOAP();
		
		// get the name of the course we are assigning homework to
		String theCourse = request.getParameter( "selectedCourses" );
		
//		String[] listOfUsersInTheWeBWorKCourse = soapHandler.list_users( authKey, theCourse );
//		for ( int i = 0; i < listOfUsersInTheWeBWorKCourse.length; i++ ) 
//			out.println( "user: " + listOfUsersInTheWeBWorKCourse[ i ] );
		
		soapHandler.delete_user( authKey, theCourse, theUser );
		
//		String[] listOfUsersInTheWeBWorKCourseAfterDelete = soapHandler.list_users( authKey, theCourse );
//		for ( int i = 0; i < listOfUsersInTheWeBWorKCourseAfterDelete.length; i++ ) 
//			out.println( "user: " + listOfUsersInTheWeBWorKCourseAfterDelete[ i ] );
	%>
	<bbUI:docTemplate title="Delete User">
		<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
			<bbUI:breadcrumb>Authorize an Instructor</bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		
		<bbUI:titleBar >Authorize an Instructor</bbUI:titleBar>
		
		<bbUI:instructionBar>
		<%
			out.println("<font color=red><b>ATTENTION:</b></font> You have been deleted from: <b>" + request.getParameter("selectedCourses") + "</b>. Thank you very much for being honest!");
			
			// build the url for the back button
			String URLtoBackUp = "/webapps/blackboard/content/listContentEditable.jsp?content_id=" + request.getParameter("content_id") + "&course_id=" + request.getParameter("course_id") + "&mode=quick";
		%>
			<br />
			<br />
			<form><bbUI:button type="FORM_ACTION" name="back" alt="Back" action="LINK" targetUrl="<%=URLtoBackUp%>" /></form>						
		</bbUI:instructionBar>
	</bbUI:docTemplate>
</bbData:context>