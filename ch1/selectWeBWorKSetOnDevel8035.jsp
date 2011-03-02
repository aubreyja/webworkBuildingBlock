<!-- This file was initially created by Daniel Arnold ( a 2008 intern at UoR )
	 This file was revised and further developed by Martin Georgiev ( a 2009 intern at UoR ) -->
	 
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
             	 edu.rochester.webwork.devel.WebworkSOAP.*,
		         edu.rochester.webwork.devel.WebworkClient.*" 
	      errorPage="error.jsp"
%>
<!-- tag libraries, necessary for proper page formatting -->
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">
	<jsp:useBean id="courseDoc" scope="page" class="blackboard.data.content.CourseDocument" />
	
	<%
	    // Initialize error flags
	    boolean setNameError = false; // holds true if incorrect name of a set is being used
	    boolean setError = false;
	    boolean dbError = false; // holds true if a db error is encountered while trying to pull out the homework sets for the selected course from WeBWorK
	    boolean duplicateNameError = false; // holds true if we gave a name to the set we want to assign identical to another one already in the list of assignments
	    boolean invalidUserFlag = false; // shows whether the user is authorized to assign homework sets or not
		
	    try // NOTE: we may encounter a DB error while trying to retrieve the available assignments for the selected course. We should handle somehow this error.
		{
			// if no course is selected on the previous screen, send the user back and notify him for the error
			if ( request.getParameter( "selectedCourses" ) == null )
				response.sendRedirect( "selectWeBWorKCourseOnDevel8035.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&error=true" );
			
			// if more than one course is selected from the database, send the user back to the previous screen and notify him/her to select one course only
			String[] selCourses = request.getParameterValues("selectedCourses");
			if ( selCourses.length > 1 )
				response.sendRedirect( "selectWeBWorKCourseOnDevel8035.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&error=true" );
	
			// Set error flags if this isn't the first time we're at this page
			if ( request.getParameter( "setNameError" ) != null && request.getParameter( "setNameError" ).equals( "true" ) )
				setNameError = true;
			if ( request.getParameter( "setError" ) != null && request.getParameter( "setError" ).equals( "true" ) )
				setError = true;
			if ( request.getParameter( "duplicateNameError" ) != null && request.getParameter( "duplicateNameError" ).equals( "true" ) )
				duplicateNameError = true;
			
			// get the user object from the context
		  	User sessionUser = ctx.getUser();
		
			// get the user name from the user object
			String theUser = sessionUser.getUserName();
			
			// get the course object from the context
			Course courseId = ctx.getCourse();
			
			// get the course name from the course object
			String theBlackboardCourse = courseId.getBatchUid();
			
			// get the name of the course we are assigning homework to
			String theCourse = request.getParameter( "selectedCourses" );
		  
			// default authentication key set in the WeBWorK database
			final String authKey = "123456789123456789";
			
			// Declare and instantiate SOAP classes; Get connected to the WeBWorK Server
			WebworkSOAPHandlerService handlerService = new WebworkSOAPHandlerServiceLocator();
			WebworkSOAPHandler soapHandler = handlerService.getWebworkSOAP();
			
			// if the user is not listed in the course list, then the user is not authorized to assign sets to the course
			// raise the invalidUserFlag to account for the encountered error			
			if ( !soapHandler.exists_user( authKey, theCourse, theUser ).equals( "1" ) )
				invalidUserFlag = true;
			
			// get a list with all of the homework sets avaiable for this course from WeBWorK
			String [] sets = soapHandler.list_global_sets( authKey, theCourse );
		
			// get a list containing all of the available homework sets in the course	
			List<MultiSelectBean> setList = new ArrayList<MultiSelectBean>( sets.length );
		
			// @selectedSet - a placeholder for the selected homework set to be assigned to the students taking the course
			List<MultiSelectBean> selectedSet = new ArrayList<MultiSelectBean>( 1 );
			
			// add the homework sets from the WeBWorK database to a datastucture that can be used in Blackboard
			for ( String theSet : sets ) 
			{
				MultiSelectBean mBean = new MultiSelectBean();
				mBean.setLabel( theSet );
				mBean.setValue( theSet );
				setList.add( mBean );
			}

	%>
			<bbUI:docTemplate title="New WeBWorK Assignment">
				<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
					<bbUI:breadcrumb>New WeBWorK Assignment</bbUI:breadcrumb>
				</bbUI:breadcrumbBar>
				
				<bbUI:titleBar >New Assignment</bbUI:titleBar>
				
				<bbUI:instructionBar>
				<%	
					if ( setNameError ) out.println( "<font color=red><b>ATTENTION:</b></font> Type in a name for the set." );
				   	if ( setError ) out.println( "<font color=red><b>ATTENTION:</b></font> Select one set only." ); 
					if ( duplicateNameError ) out.println ( "<font color=red><b>ATTENTION:</b></font> A set with the same name as the one you are trying to use already exists in the databse. Please use another name for the set you want to assign.<p><font color=\"red\"><b>NOTE: </b></font> If you DO want to use this name for the current assignment, you have to go to the <em>Assignments</em> page and delete the assignment (if present) having the same name. Then, you have to go to the <em>Grade Center</em> and delete the grades column for the respective assignment.</p><p><font color=\"red\"><b>NOTE: </b></font> When you delete an assignment and its associated grades column, all of the grades are lost and <font color=\"red\">cannot be recovered!</font></p>" );
				   	
					if ( invalidUserFlag ) 
					{
						String urlToAuthorizationPage = "enrollInCourseOnDevel8035.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedCourses=" + request.getParameter( "selectedCourses" );
						out.println("<font color=red><b>ATTENTION:</b></font> You are not authorized to assign homework sets in this course!If you think that this message is an error, please click: <a href=" + urlToAuthorizationPage + ">Authorize me!</a>");

				%>
						<br />
						<br />
						<form><bbUI:button type="FORM_ACTION" name="back" alt="Back" action="LINK" targetUrl="javascript:history.go(-1);" /></form>	
				<%
					}// end of if statement
				%>
							
				</bbUI:instructionBar>
				
				<%
					if ( !invalidUserFlag ) 
					{
						// create the url pointing to the selectWeBWorKServer page; if the user clicks on this url, he/she will be able to choose another course from the WeBWorK database to assiciate with the current Blackboard course
						String revertDbInfo = "selectWeBWorKServer.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id");
				%>		
					<form name="theform" action="assignWeBWorKSetOnDevel8035.jsp" method="post">
						<input type="hidden" name="content_id" value="<%=request.getParameter("content_id")%>">
						<input type="hidden" name="course_id" value="<%=request.getParameter("course_id")%>">
						<input type="hidden" name="selectedCourses" value="<%=request.getParameter( "selectedCourses" )%>">
						<input type="hidden" name="selectedServer" value="<%=request.getParameter("selectedServer")%>">
			
						<p>Assigning a homework set in: <b><%=theBlackboardCourse %></b></p> 
						
						<p><font color="red"><b>NOTE:</b></font> If you want to assign a homework set which is part of another WeBWorK course, please click: <a href="<%=revertDbInfo%>">choose another WeBWorK course</a>!</p>
						<div align = "center">
							  <bbUI:step title="Choose a Set" number="1">
								  <bbUI:dataElement label="Set Name:">
							 	  		<input type="text" name="setName">
							 	  </bbUI:dataElement>
								  
								  <bbUI:multiSelect leftTitle="Available WeBWorK Sets" rightTitle="Selected Set" leftCollection="<%=setList%>" rightSelectName="selectedSets" rightCollection="<%=selectedSet%>" formName="theform"/>
						 		  <br />
								  
								  <bbUI:dataElement label="Comment">
								  		<textarea name="comment" cols="82" rows="10" required = "true"></textarea>
								  </bbUI:dataElement>
							  </bbUI:step>
							<bbUI:stepSubmit title="Submit" number="2" />
						</div>
					</form>
				<%
					} // end of if statement
				%>
			</bbUI:docTemplate>
	
	<%
		}// end of the try block
		catch(Throwable ex) // if a db error is encountered while trying to retrieve the list of available assignments for the selected course, notify the user for the error and terminate.
		{
	%>
			<bbUI:docTemplate title="New WeBWorK Assignment">
				<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
					<bbUI:breadcrumb>New WeBWorK Assignment</bbUI:breadcrumb>
				</bbUI:breadcrumbBar>
				
				<bbUI:titleBar >Set Assignment</bbUI:titleBar>
				
				<bbUI:instructionBar>
	<%		
					out.println("<font color=red><b>ATTENTION:</b></font> A database error encountered while trying to load the available assignments for the selected course. Please try again later. If the error persists, please contact your system administrator.");
	%>
					<br />
					<br />
					<form><bbUI:button type="FORM_ACTION" name="back" alt="Back" action="LINK" targetUrl="javascript:history.go(-1);" /></form>	
				</bbUI:instructionBar>	
			</bbUI:docTemplate>
	<%
		} //  end of the catch clause
	%>
</bbData:context>