<!-- This file was initially created by Daniel Arnold ( a 2008 intern at UoR )
	 This file was revised and further developed by Martin Georgiev ( a 2009 intern at UoR ) -->

<!-- include necessary libraries. These libraries are used to process
     either blackboard methods, java methods or WeBWorK methods -->
<%@ page import="java.util.*,
                 java.text.*,
                 java.io.*,
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
                 blackboard.platform.plugin.*,
                 blackboard.platform.security.*,
	             blackboard.platform.session.*,
                 blackboard.platform.db.*"
	errorPage="error.jsp"%>

<!-- include tag libraries, necessary to put the web page into the Blackboard template -->	
<%@ taglib uri="/bbUI" prefix="bbUI"%>	
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context>
	<jsp:useBean id="courseDoc" scope="page" class="blackboard.data.content.Content" />

	<%
		////////// Retrieve Course Doc Related Information //////////
		// retrieve the Db persistence manager from the persistence service
		BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
		
		Id courseId = bbPm.generateId(Course.DATA_TYPE,request.getParameter("course_id"));
		Id courseDocId = bbPm.generateId(Content.DATA_TYPE, request.getParameter("content_id"));
		
		ContentDbLoader courseDocumentLoader = (ContentDbLoader) bbPm.getLoader(ContentDbLoader.TYPE );
		courseDoc = courseDocumentLoader.loadById( courseDocId );
		////////// Course Doc Related Information Has Been Retrieved //////////
		
		////////// build a redirect link to update assignment info ///////////
		// get the beginning index of the selectedServer string from the courseDoc.getTitle attribute
		int beginningIndexOfServerName = courseDoc.getTitle().indexOf( "selectedServer" );
		
		//get the end index of the selectedServer from the courseDoc.getTitle attribute
		int beginningIndexOfUnnecessaryChars = courseDoc.getTitle().indexOf( "\">" );
		
		// get the selectedServer string from the courseDoc.getTitle attribute
		String selectedServer = courseDoc.getTitle().substring( beginningIndexOfServerName, beginningIndexOfUnnecessaryChars );
		
		// get the number of the selectedServer
		int selectedServerNumber = Integer.parseInt( selectedServer.substring( selectedServer.length() - 1 ) );
		
		int assignmentLinkToDisregardInTheBeginning = 0; //hold the length of the string to disregard in the beginning of the courseDoc.getTitle attribute
		String defaultPage = PlugInUtil.getUri("UofR", "WeBWorK-Assignment", "ch1/webworkOnHosted2.jsp");	
		defaultPage = defaultPage + "?";
		String defaultPage2 = PlugInUtil.getUri("UofR", "WeBWorK-Assignment", "ch1/webwork.jsp");	
		defaultPage2 = defaultPage2 + "?";		
		if ( selectedServerNumber == 0 )
		{
			
			assignmentLinkToDisregardInTheBeginning = ( "<a href=\"" + defaultPage ).length();
		}
		else
		{
			// get the beginning index for the substring of setName and selectedCourse copy
			assignmentLinkToDisregardInTheBeginning = ( "<a href=\"" + defaultPage2 ).length();
		}
		
		
		// get the substring from the whole url; this substing will contain the selectedCourses data and the setName data as well as some unnecessary stuff at the end.
		String selectedCoursesAndSetNameUnprocessed = courseDoc.getTitle().substring( assignmentLinkToDisregardInTheBeginning );
		
		// get the position in the substring found above of the unnecessary stuff appended to the selectedCourses and setName data we need
		int beginningIndexOfUnnecessaryStuff = selectedCoursesAndSetNameUnprocessed.indexOf( "\">" );
		
		// now get the selectedCourses and setName data only; also append in the beginning the ampersand sign needed for proper concatenation of this string to the updateAssignment URL
		String selectedCoursesAndSetNameProcessed = "&" + selectedCoursesAndSetNameUnprocessed.substring( 0, beginningIndexOfUnnecessaryStuff );
		
		// get the assignment name - the name that is updated in the grade center; adjust the beginning index for the copy by adding 2 to account for the "> characters
		String assignmentName = "&assignmentName=" + selectedCoursesAndSetNameUnprocessed.substring( beginningIndexOfUnnecessaryStuff + 2, selectedCoursesAndSetNameUnprocessed.length() - 4 );
			
		// build the URL to redirect to
		String redirectToupdateAssignmentLink = "updateAssignment.jsp?course_id=" + request.getParameter("course_id") + "&content_id=" + request.getParameter("content_id") + selectedCoursesAndSetNameProcessed + assignmentName + "&selectedServer=" + selectedServerNumber;
		////////// The redirect link has been built ///////////
			
		
		////////// Does the instructor want to port the assignment? If yes, process the request! //////////
		if ( ( request.getParameter( "needsUpdate" ) != null ) && ( request.getParameter( "needsUpdate" ).equals( "true" ) ) )
		{
			response.sendRedirect( redirectToupdateAssignmentLink );	
		}
		else // The instructor is just modifying the assignment but not porting it!!! Process the request!!!
		{
			// set the availability of the assignment
			if ( request.getParameter( "isAvailable" ).equals( "true" ) )
				courseDoc.setIsAvailable( true );
			else
				courseDoc.setIsAvailable( false );
			
			// append the grading engine link to the comment of the assignment to build the new description of the assignment
			String newAssignmentDescription = courseDoc.getUrl() + request.getParameter( "text" );
						
			// format the new description of the assignment after the change
			FormattedText text = new FormattedText( newAssignmentDescription, FormattedText.Type.HTML );
			
			// set the new description of the assignment after the change
			courseDoc.setBody(text);
			
			// get the persister from the Blackboard database
			ContentDbPersister persister = (ContentDbPersister) bbPm.getPersister( ContentDbPersister.TYPE );
			
			// presist changes to the database
			persister.persist( courseDoc );
			///////// The assignment has been modified BUT not ported!!! /////////
		}
		
		// get the URL of the parent page
		String strReturnUrl = PlugInUtil.getEditableContentReturnURL(courseDoc.getParentId());
	%>
	
	<bbUI:docTemplate title="Content Modification">
		<bbUI:breadcrumbBar  handle="control_panel" isContent="true" >
			<bbUI:breadcrumb>Content Modification</bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		
		<bbUI:receipt type="SUCCESS" iconUrl="/images/ci/icons/tools_u.gif" title="Assignment Data Modification" recallUrl="<%=strReturnUrl%>">Assignment Data Modified Successfully!</bbUI:receipt>
		<br />
	</bbUI:docTemplate>	
</bbData:context>