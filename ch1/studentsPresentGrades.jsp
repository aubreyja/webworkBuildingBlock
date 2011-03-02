<!--  @author Martin Georgiev ( a 2009 intern at UoR )    -->

<!-- APIs necessary for method processing -->
<%@page import="java.util.*,
				blackboard.base.*,
				blackboard.data.user.*,
				blackboard.data.course.*,
				blackboard.persist.*,
				blackboard.persist.course.*,
				blackboard.platform.*,
				edu.rochester.webwork.devel.WebworkSOAP.*,
                edu.rochester.webwork.devel.WebworkClient.*"
		errorPage="error.jsp"%>

<!-- tag libraries, necessary for proper page formatting -->	
<%@ taglib uri="/bbUI" prefix="bbUI" %>
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">
	<%
		//construct a string for proper message handling
		String message;
	
		// set error message flag
		boolean gradingAssignmentError = true; // set a default value; holds false when there grading is successful
		
		// create the URL to be embedded in the BACK button
		String URLtoBackUp = "/webapps/blackboard/content/listContentEditable.jsp?content_id=" + request.getParameter("content_id") + "&course_id=" + request.getParameter("course_id") + "&mode=quick";
		
		// get the user object from the context
		User sessionUser = ctx.getUser();
		
		// get the user id from the user object
		Id userId = sessionUser.getId();
		
		// get the course object from the context
		Course courseIdentity = ctx.getCourse();
		
		// get the name of the course
		String courseName = courseIdentity.getTitle();
		
		try
		{		
			// default authentication key set in the WeBWorK database
			final String authKey = "123456789123456789";
			
			// Declare and instantiate SOAP classes; Get connected to the WeBWorK Server
			WebworkSOAPHandlerService handlerService = new WebworkSOAPHandlerServiceLocator();
			WebworkSOAPHandler soapHandler = handlerService.getWebworkSOAP();
				
			// get the list of users from the WeBWorK database
			String [] listOfUsers = soapHandler.list_users ( authKey, request.getParameter( "selectedCourses" ));
			
			// get everybody's grades from the WeBWorK database
			String[] userGrades = soapHandler.grade_users_sets(authKey, request.getParameter("selectedCourses"), listOfUsers, request.getParameter("setName"));		
			
			message = "The assignment has been graded successfully!<p><font color=\"red\"><b>NOTE:</b></font> The grades listed below are present grades as recorded in the WeBWorK database! They may or may not have been transfered to the Blackboard database! To check the content of the Blackboard database, please go to the <em>Grade Center</em>.</p>";
			
			gradingAssignmentError = false;
	%>
			<!-- use the Blackboard doc template -->
			<bbUI:docTemplate title="Grade Assignment">
				<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
					<bbUI:breadcrumb>Grade Assignment</bbUI:breadcrumb>
				</bbUI:breadcrumbBar>
				
				<bbUI:titleBar >Grade Assignment</bbUI:titleBar>
				
				<bbUI:instructionBar>
						<!-- notify the user for the status of the grading process -->
						<p><%=message%></p>
						
						<%
							if (!gradingAssignmentError) // if the assignment has been graded successfully
						  	{
						%>
							<table border="1" align="center">
								<th style="font-size: 12px;">Student's User Name</th>
								<th style="font-size: 12px;">Student's Present Grade</th>
								<%
									for ( int index = 0; index < listOfUsers.length; index++ )
									{
								%>
										<tr align="center">
											<td align="center">
												<%=listOfUsers[index]%>
											</td>
											<td align="center">
												<%=userGrades[index]%>
											</td>
										</tr>
								<%
									}// end of for statement
								%>
							</table>
						<%	
						  	} // end of if statement
						%>
						<p></p>
						<!-- allow the user to go to the previous page by clicking on the Back button -->
						<form><bbUI:button type="FORM_ACTION" name="back" alt="Back" action="LINK" targetUrl="<%=URLtoBackUp%>" /></form>			
				</bbUI:instructionBar>		
			</bbUI:docTemplate>
	<%
		} // end of try clause				
		catch(Throwable exception)
		{
			message = "<font color=red><b>ATTENTION:</b></font> An error has been encountered while trying to grade the requested assignment.";
	%>
			<!-- use the Blackboard doc template -->
			<bbUI:docTemplate title="Grade Assignment">
				<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
					<bbUI:breadcrumb>Grade Assignment</bbUI:breadcrumb>
				</bbUI:breadcrumbBar>
				
				<bbUI:titleBar >Grade Assignment</bbUI:titleBar>
				
				<bbUI:instructionBar>
						<!-- notify the user for the status of the grading process -->
						<p><%=message%></p>
						
						<!-- allow the user to go to the previous page by clicking on the Back button -->
						<form><bbUI:button type="FORM_ACTION" name="back" alt="Back" action="LINK" targetUrl="<%=URLtoBackUp%>" /></form>			
				</bbUI:instructionBar>		
			</bbUI:docTemplate>
	<%
		} // end of catch clause
	%>
</bbData:context>