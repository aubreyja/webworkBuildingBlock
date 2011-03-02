<!-- @author Martin Georgiev ( a 2009 intern at UoR )  -->

<!-- include necessary libraries. These libraries are used to process
     either blackboard methods, java methods or WeBWorK methods -->
<%@ page language="java" import="java.util.*,
								 blackboard.platform.session.*,
								 blackboard.data.user.*,
								 blackboard.persist.*,
								 java.rmi.RemoteException,
								 javax.xml.rpc.ServiceException,
								 edu.rochester.webwork.hosted2.WebworkSOAP.*"
						  pageEncoding="UTF-8" %>
						  
<!-- include tag libraries, necessary to put the web page into the Blackboard template -->
<%@ taglib uri="/bbUI" prefix="bbUI" %>
<%@ taglib uri="/bbData" prefix="bbData" %>

<%
	String iconUrl = "/images/ci/icons/clp_course_u.gif";
	String page_title = "Blackboard - WeBWorK Assignments Portal";
%>

<!-- create the context - later used to get the user and the user's data -->
<bbData:context id="ctx">
	<%
		try
		{
			
			// Get the course name; NOTE: the data is being transfered using the post method
			String theCourse = request.getParameter( "selectedCourses" );
			
			//Get the user instance via the user context coming from Blackboard
			User sessionUser = ctx.getUser();
			String theUser = sessionUser.getUserName();
		
			//$authKey - string with currently defided value by WeBWorK as given below 
			final String authKey = "123456789123456789";
		
			// Declare and instantiate SOAP classes; Get connected to WeBWorK
			WebworkSOAPHandlerService handlerService = new WebworkSOAPHandlerServiceLocator();
			WebworkSOAPHandler soapHandler = handlerService.getWebworkSOAP();
			
			// build the permission record
			WebworkSOAPClassesPermission record = new WebworkSOAPClassesPermission( theUser, "0" );
			
			try
			{
				//////////////// Check whether the user is in the course list //////////////
				// if the user is not listed in the course list, then the user is not authorized to assign sets to the course
				// raise the invalidUserFlag to account for the encountered error			
				String[] listOfUsersInTheWeBWorKCourse = soapHandler.list_users( authKey, theCourse );
				
				// declare and initialize the index of the student in the list of students which comes from the WeBWorK database
				int index = 0;
				
				// go over the list and try to find the current user
				for ( index = 0; index < listOfUsersInTheWeBWorKCourse.length; index++ )
				{
					// if we find him/her, break from the loop
					if ( listOfUsersInTheWeBWorKCourse[ index ].equals( theUser ) )
					{
						break;
					}
				}
				
				// if the user is not found in the list of users from the WeBWorK database, create the user. 
				if ( index == listOfUsersInTheWeBWorKCourse.length )
				{
					// the user does not exist in the course under WeBWorK
					WebworkSOAPClassesUser user = new WebworkSOAPClassesUser( theUser, sessionUser.getGivenName(), sessionUser.getFamilyName(), sessionUser.getEmailAddress(), sessionUser.getStudentId(), "C", null, null, null );
					soapHandler.add_user( authKey, theCourse, user );
					soapHandler.add_permission( authKey, theCourse, record );
					soapHandler.put_permission( authKey, theCourse, record );
				}
			}
			catch(Throwable ex)
			{
				// if we get an error while creating the user above, then the user is found to exist in the course list.
			}
			
			try
			{
				// try to assign the set to the user
				soapHandler.assign_set_to_user( authKey, theCourse, theUser, request.getParameter( "setName" ) );
			}
			catch(Throwable ex)
			{
				// we have encountered an error while trying to assign the set to the user. This means that the user has already been assigned this set.
				// This exception may occur when a user retakes the course or the instructor recreates the set
				// if we compare this set assignment with the set assignment on the devel:8035 server we will notice that the method exists_user_set is not available on this server.
			}
			
			// Obtain the key necessary to log the user into WeBWorK
			String key = soapHandler.login_user( authKey, theCourse, theUser );
	%>
		<bbUI:docTemplate title="<%=page_title%>">
			<!-- Start Breadcrumb Navigation -->
			<bbUI:breadcrumbBar environment="CTRL_PANEL" handle="control_panel">
				<bbUI:breadcrumb>Blackboard - WeBWorK Assignments Portal</bbUI:breadcrumb>
			</bbUI:breadcrumbBar>
			<!-- End Breadcrumb Navigation -->
			
			<bbUI:titleBar iconUrl="<%=iconUrl%>">
				<%=page_title%>
			</bbUI:titleBar>
			<%
				// Build the url which will be used later to log the user into the correct class in WeBWorK
				String url = "http://hosted2.webwork.rochester.edu/webwork2/" + theCourse + "/" + request.getParameter( "setName" ) + "?user=" + theUser + "&key=" + key;
			%>
			
			<p>Hello, <b> <%= sessionUser.getGivenName() + " " + sessionUser.getFamilyName()%></b><br />
			   You are currently logged in: <b><%=theCourse%></b> as: <b><%=sessionUser.getUserName()%></b></p>
			
			<p style="color: rgb(170, 170, 170);">If you are unable to view properly the frame below, please click: <a href = "<%=url %>" target="_blank">WeBWorK</a></p>

			<iframe src = "<%=url %>" width="100%" height="100%">
	  			<p>Your browser does not support iframes.</p>
	  			<p>Supported browsers: Mozilla Firefox 3.0.10 and above <br /> IE 7 and above</p>
			</iframe>
			</bbUI:docTemplate>
	<%
		} // end of the try clause
		catch ( Throwable ex)
		{
			// create the message to the user
			String message = "<font color=\"red\"><b>ATTENTION:</b></font> Cannot connect to the WeBWorK server. Please contact your course instructor.";
	%>
			<bbUI:docTemplate title="<%=page_title%>">
				<!-- Start Breadcrumb Navigation -->
				<bbUI:breadcrumbBar environment="CTRL_PANEL" handle="control_panel">
					<bbUI:breadcrumb>Blackboard - WeBWorK Assignments Portal</bbUI:breadcrumb>
				</bbUI:breadcrumbBar>
				<!-- End Breadcrumb Navigation -->
				
				<bbUI:titleBar iconUrl="<%=iconUrl%>">
					<%=page_title%>
				</bbUI:titleBar>
				
				<bbUI:instructionBar>
					<!-- notify the user for the status of the grading process -->
					<p><%=message%></p>		
				</bbUI:instructionBar>	
				
				<form><bbUI:button type="FORM_ACTION" name="back" alt="Back" action="LINK" targetUrl="javascript:history.go(-1);" /></form>
			</bbUI:docTemplate>
	<%
		} // end of catch clause
	%>
</bbData:context>