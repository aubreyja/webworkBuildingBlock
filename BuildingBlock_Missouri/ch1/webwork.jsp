<!-- This file was initially created by Daniel Arnold ( a 2008 intern at UoR )
	 This file was revised and further developed by Martin Georgiev ( a 2009 intern at UoR )  -->

<!-- include necessary libraries. These libraries are used to process
     either blackboard methods, java methods or WeBWorK methods -->
<%@ page language="java" import="java.util.*,
								 blackboard.platform.session.*,
								 blackboard.data.user.*,
								 blackboard.persist.*,
								 java.rmi.RemoteException,
								 javax.xml.rpc.ServiceException,
								 edu.rochester.webwork.devel.WebworkSOAP.*,
								 edu.rochester.webwork.devel.WebworkClient.*"
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

			// check to see if the user exists in the course; If s/he does not, add him/her to the course list
			if ( !soapHandler.exists_user( authKey, theCourse, theUser ).equals( "1" ) )
			{
				// the user does not exist in the course under WeBWorK
				WebworkSOAPClassesUser user = new WebworkSOAPClassesUser( theUser, sessionUser.getGivenName(), sessionUser.getFamilyName(), sessionUser.getEmailAddress(), sessionUser.getStudentId(), "C", null, null, null );
				soapHandler.add_user( authKey, theCourse, user );
				soapHandler.add_permission( authKey, theCourse, record );
				soapHandler.put_permission( authKey, theCourse, record );
			}
			
			// Obtain the key necessary to log the user into WeBWorK
			String key = soapHandler.login_user( authKey, theCourse, theUser );
			
			// Check whether the user has been assigned the set. If not, assign it to him/her
			if( !soapHandler.exists_user_set( authKey, theCourse, theUser, request.getParameter( "setName" ) ).equals( "1" ) )
				soapHandler.assign_set_to_user( authKey, theCourse, theUser, request.getParameter( "setName" ) );
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
				String url = "http://devel.webwork.rochester.edu:8035/webwork2/" + theCourse + "/" + request.getParameter( "setName" ) + "?user=" + theUser + "&key=" + key;
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