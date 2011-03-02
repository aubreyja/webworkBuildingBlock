<!-- @author Martin Georgiev ( a 2009 intern at UoR )  -->
	 
<!-- APIs necessary for method processing -->
<%@ page import="java.util.*,
                 java.text.*,
                 java.io.*,
                 blackboard.data.*,
                 blackboard.data.user.*,
                 blackboard.data.course.*,
                 blackboard.data.content.*,
                 blackboard.data.gradebook.*,
                 blackboard.persist.*,
                 blackboard.persist.course.*,
                 blackboard.persist.content.*,
                 blackboard.persist.gradebook.*,
                 blackboard.db.*,
                 blackboard.base.*,
                 blackboard.platform.*,
                 blackboard.platform.db.*,
                 blackboard.platform.log.*,
                 blackboard.platform.plugin.*,
                 blackboard.platform.session.*,
                 blackboard.platform.security.*,
                 blackboard.platform.persistence.*,
                 edu.rochester.webwork.hosted2.WebworkSOAP.*"
	      errorPage="error.jsp"
%>      

<!-- tag libraries, necessary for proper page formatting -->	      
<%@ taglib uri="/bbUI" prefix="bbUI"%>	
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context id='ctx'>
	<jsp:useBean id="courseDoc" scope="page" class="blackboard.data.content.CourseDocument" />
	<%
		//Initialize error flags
		boolean setNameError = false; // holds true if the name of the set to be assigned is illegal
		boolean setError = false; // holds true if the user is trying to assign more than one set at a time.
		boolean isError = false; // holds true when there is an error of some kind and the user should be sent back to the previous page
		boolean duplicateNameError = false; // holds true if the name of the set we want to assign already exists in the database
		int numOfSetNames = 0; // set default value of 0 for the number of courses selected
		final String authKey = "123456789123456789"; // default value of the authentication key set in the WeBWorK database
		final int weightOfProblem = 1; // a default value for the weight of each problem in the set
		
		try
		{		
			// check whether a set has been selected
			if ( request.getParameter( "selectedSets" ) == null || request.getParameter( "selectedSets" ).equals( "" ) ) 
			{
				// raise error flags
				setError = true;
				isError = true;
				throw new Exception();
			}
			
			//Get the number of sets, the user is trying to assign for homework
			numOfSetNames = request.getParameterValues( "selectedSets" ).length;
			
			//if the number of sets the user is trying to assign as homework is greater than one, raise the flag - illegal operation. ONLY one set can be assigned at a time.
			if ( numOfSetNames > 1 )
			{
				isError = true;
				setError = true;
				throw new Exception();
			}
			
			// check whether the assignment name is null or empty
			if ( request.getParameter( "setName" ) == null || request.getParameter( "setName" ).equals( "" ) ) 
			{
				setNameError = true;
				isError = true;
				throw new Exception();
			}						
			
			/////////////////////////////////////////////////
			// check whether the assignment has a unique name
			/////////////////////////////////////////////////
			
			//Retrieve the Db persistence manager from the persistence service
			BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
			
			// get the course object from the context
			Course courseIdentity = ctx.getCourse();
			
			// get the course id from the course object
			Id courseId = courseIdentity.getId();
			
			// create a pointer to the lineitem database
			LineitemDbLoader lidl = (LineitemDbLoader)bbPm.getLoader(LineitemDbLoader.TYPE);
		
			// get a list of lineitems relative to the our course
			BbList lineItems = lidl.loadByCourseId(courseId);
		
			// create an iterator over the list of line items in the database
			Iterator lineItemIterator = lineItems.iterator();
			
			// create a placeholder for the names of the assignments
			ArrayList<String> listWithNameOfAssignments = new ArrayList<String>();
			
			// iterate over the list and add the names of all of the available assignments to the list
			while ( lineItemIterator.hasNext() )
			{
				// get the current lineitem from the list of lineitems
				Lineitem currentLineItem = (Lineitem) lineItemIterator.next();
				
				listWithNameOfAssignments.add(currentLineItem.getName());
			}
			
			// get the name of the new assignment
			String nameNewAssignment = request.getParameter( "setName" );
			
			// traverse the list and see whethet the name of the assignment already exists in the list
			for ( int index = 0; index < listWithNameOfAssignments.size(); index++ )
			{
				if ( nameNewAssignment.equals(listWithNameOfAssignments.get( index ) ) )
				{
					// there is already an assignment in the DB with this name
					// raise error flags
					isError = true;
					duplicateNameError = true;
					throw new Exception();
				}
			}
					
			//////////////////////////////////////////////////////////
			//////The assignment is found to have a unique name///////
			//////////////////////////////////////////////////////////
		}
		catch (Throwable ex)
		{
			// Generate a link and redirect the user if necessary
			if ( isError )
				response.sendRedirect( "/webapps/UofR-WeBWorK-Assignment-bb_bb60/ch1/selectWeBWorKSetOnHosted2.jsp?content_id=" 
						+ request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedCourses=" + request.getParameter( "selectedCourses" ) + "&setNameError=" + ( setNameError ? "true" : "false" ) + "&setError=" + ( setError ? "true" : "false" ) + "&duplicateNameError=" + (duplicateNameError ? "true" : "false") + "&selectedServer=" + request.getParameter("selectedServer"));
		}
		
		if (!isError)
		{
			PlugInManager pluginMngr = (PlugInManager) BbServiceManager.lookupService(blackboard.platform.plugin.PlugInManager.class);
			
			// retrieve the Db persistence manager from the persistence service
			BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
			
			Id courseId = bbPm.generateId(Course.DATA_TYPE,request.getParameter("course_id"));
			Id folderId = bbPm.generateId(CourseDocument.DATA_TYPE,request.getParameter("content_id"));
			
			ContentDbLoader courseDocumentLoader = (ContentDbLoader) bbPm.getLoader( ContentDbLoader.TYPE );
			
			//get the user object from the context
			User sessionUser = ctx.getUser();
			
			// get the user name from the user object
			String theUser = sessionUser.getUserName();
			
			// get the course from the context; This is the Blackboard Course
			
			// Get the course name from the WeBWorK database; NOTE: the data is being transfered using the post method
			String theCourse = request.getParameter( "selectedCourses" );
			
			Course sessionCourse = ctx.getCourse();
			
			// get the course name from the course object
			String theBlackboardCourse = sessionCourse.getBatchUid();
			
			// Declare and instantiate SOAP classes; Get connected to the WeBWorK Server
			WebworkSOAPHandlerService handlerService = new WebworkSOAPHandlerServiceLocator();
			WebworkSOAPHandler soapHandler = handlerService.getWebworkSOAP();
			
			// build the permission record
			WebworkSOAPClassesPermission record = new WebworkSOAPClassesPermission( theUser, "0" );
			
			// get the name of the set from the URL
			String nameOfSelectedSet = request.getParameter( "setName" );
			
			// retrieve the number of problems in the selected set.
			String[] listONamesOffProblemsInThisAssignment = soapHandler.list_global_problems( authKey, request.getParameter( "selectedCourses" ), request.getParameter( "selectedSets" ) );
			
			// get the number of possible points on this assignment; the number of possible points is basically equal to the number of problems in the set
			int numberOfPossiblePoints = weightOfProblem * ( listONamesOffProblemsInThisAssignment.length );
			
			// get the list of users enrolled in this course
			String[] listOfUsersInTheWeBWorKCourse = soapHandler.list_users( authKey, theCourse );
			
			try			
			{
				////////////////Check whether the user is in the course list //////////////
				// if the user is not listed in the course list, then the user is not authorized to assign sets to the course
				// raise the invalidUserFlag to account for the encountered error			
				// declare and initialize the index of the student in the list of students which comes from the WeBWorK database
				int index = 0;
				
				// go over the list and try to find the current user
				for ( index = 0; index < listOfUsersInTheWeBWorKCourse.length; index++ )
				{
					// if we find him/her, break from the loop
					if ( listOfUsersInTheWeBWorKCourse[ index ].equals( theUser ) )
						break;
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
				// user has already been given permission to access the homework set.
				// This error occurs when the user is not listed in the course list under WeBWorK but s/he has permissions to access a homework set
				// The might happen when a student retakes the course.
			}
			
			//assign the homework set to the user in WeBWorK, so that when he/she comes back, he/she can complete it.
			try
			{
				// try to assign the set to the users
				for ( int index = 0; index < listOfUsersInTheWeBWorKCourse.length; index++ )
					soapHandler.assign_set_to_user( authKey, request.getParameter( "selectedCourses" ), listOfUsersInTheWeBWorKCourse[index], request.getParameter( "selectedSets" ) );
			}
			catch(Throwable ex)
			{
				// we have encountered an error while trying to assign the set to the user. This means that the user has already been assigned this set.
				// This exception may occur when a user retakes the course or the instructor recreates the set
				// if we compare this set assignment with the set assignment on the devel:8035 server we will notice that the method exists_user_set is not available on this server.
			}
			
			//Get a list of students enrolled in the course
			String [] listOfUsers = soapHandler.list_users ( authKey, request.getParameter( "selectedCourses" ));
		
			// Update the assignment in the Grade Center
			LineitemDbPersister lineitemPersister = (LineitemDbPersister) bbPm.getPersister( LineitemDbPersister.TYPE );
			LineitemDbLoader lidl = (LineitemDbLoader)bbPm.getLoader(LineitemDbLoader.TYPE);
			BbList lineItems = lidl.loadByCourseId(courseId); // get a list of line items relative to this course
			
			//constructing a new LineItem
			Lineitem lineI = new Lineitem(); // builds a new line item
			lineI.setCourseId(courseId); // assiciates the line item with a course using the course ID
			lineI.setPointsPossible( numberOfPossiblePoints ); // sets the maximum number of points a student can get on the current assignment
			lineI.setIsAvailable(true); // sets the status of this line item; if true, the line item is available
			lineI.setDateAdded(); // sets the date when this line item was added
			lineI.setName(request.getParameter( "setName" )); // sets the name of the line item; this is the heading for this assignment which will be displayed in the grade center table
			lineI.setType("Assignment");
			lineItems.add(lineI); // adds the current line item to the list of all line items relative to the course we are dealing with
			lineitemPersister.persist(lineItems); // persist the current line item in the database	
			// The assignment has been updated in the Grade Center

			// generate the link to be embedded in the name of the assignment so that it is accessible
			String defaultPage = PlugInUtil.getUri("UofR", "WeBWorK-Assignment", "ch1/webworkOnHosted2.jsp");
			
			defaultPage = defaultPage + "?selectedCourses=" + request.getParameter( "selectedCourses" ) + "&setName=" + request.getParameter( "selectedSets" ) + "&possiblePoints=" + numberOfPossiblePoints + "&selectedServer=" + request.getParameter("selectedServer");
			String assignmentNameAndUrl = "<a href=\"" + defaultPage + "\">" + request.getParameter( "setName" ) + "</a>";
		
 
			// create a link to the grading engine for the assignment
		//	String urlToGradingEngine = "<a href=\"/webapps/UofR-WeBWorK-Assignment-bb_bb60/ch1/doGradebookOnHosted2.jsp?setName=" + request.getParameter("setName") + "&selectedCourses=" + request.getParameter("selectedCourses") + "&course_id=" + request.getParameter("course_id") + "&content_id=" + request.getParameter( "content_id" ) + "\">Do grading</a>" + "<br />";
	// pywr 1/2011
			String urlToGradingEngine = "<a href=\"/webapps/UofR-WeBWorK-Assignment-bb_bb60/ch1/doGradebookOnHosted2.jsp?setName=" + request.getParameter("selectedSets")  + "&selectedCourses=" + request.getParameter("selectedCourses") + "&course_id=" + request.getParameter("course_id") + "&content_id=" + request.getParameter( "content_id" ) + "\">Do grading</a>" + "<br />";
					
			
			//Append the link to the gradebook at the beginning of the comment of the assignment
			String commentOfTheAssignment = urlToGradingEngine + request.getParameter( "comment" );
			
			// preformat the comment of the assignment before appending it to the assignment
			FormattedText text = new FormattedText( commentOfTheAssignment, FormattedText.Type.HTML );
			
			// set proper values for the assignment
			courseDoc.setTitle( assignmentNameAndUrl );
			courseDoc.setUrl(urlToGradingEngine);
			courseDoc.setBody( text );
			courseDoc.setContentHandler( "resource/x-B2WS-type1" );
			courseDoc.setCourseId( courseId );
			courseDoc.setParentId( folderId );
			courseDoc.setIsLesson( true );
			
			// enroll changes
			ContentDbPersister persister= (ContentDbPersister) bbPm.getPersister( ContentDbPersister.TYPE );
			persister.persist( courseDoc );
			
			try
			{
				/////// Update dbConnection.txt File ///////
				
				// set the vendor name same as the vendor name listed in the bb-manifest.xml file
				String myvendorname = "UofR";
				
				// set the building block extension name same as it is listed in the bb-manifest.xml file
				String myextensionname = "WeBWorK-Assignment";
				
				// create a new PlugInUtil
				PlugInUtil p = new PlugInUtil();
				
				// get the config file directory for our building block
				File buildingBlockConfigDirectory = p.getConfigDirectory(myvendorname,myextensionname);
				
				// calculate the length of the config file directory and deduct 6 to account for the word config appended at the end of the directory
				int buildingBlockDirectoryLenght = ( ( buildingBlockConfigDirectory.toString() ).length() ) - 6;
				
				// get the building block webapp directory address
				String buildingBlockWebAppDir = (buildingBlockConfigDirectory.toString()).substring(0, buildingBlockDirectoryLenght);
				
				// now append the webapp directory and the content handler (ch1) directory at the end of the building block directory to get the full path of the directory holding the dbConnection file
				String buildingBlockDbConnectionDirectory = buildingBlockWebAppDir + "webapp\\ch1";
				
				// create a pointer to the dbConnection file
				File dbConnectFile = new File( buildingBlockDbConnectionDirectory, "dbConnection.txt" );	
				
				// create a file pointer to a temp file
				File tempFile = new File( buildingBlockDbConnectionDirectory, "tempFile.txt" );
				
				// create a buffered reader on the file of interest
				BufferedReader readFromMainFile = new BufferedReader( new FileReader( dbConnectFile ) );		

				// create FileOutputStream
				FileOutputStream fosOnTempFile = new FileOutputStream(tempFile, true);
				
				// create DataOutputStream
				DataOutputStream dosOnTempFile = new DataOutputStream(fosOnTempFile);
				
				// build the string to be written to the file
				String newCourseServerCourseRelation = theBlackboardCourse + " " + request.getParameter("selectedServer") + " " + request.getParameter("selectedCourses") + "\r\n" ;
				
				/////// Read From The dbConnectFile  and copy every line but the line for the current course to the temp file ///////
				
				// create a placeholder for a line
				String nextLine;
				
				// get the next line if it exists
				while( ( nextLine = readFromMainFile.readLine() ) != null ) 
				{			
					// tokenize the line using the space character as a delimeter; Each line should have three tokens only in the order: BB_Course -> WW_Server -> WW_Course; The BB_Course is a pivot key
					StringTokenizer tokens = new StringTokenizer( nextLine, " " );
					
					// if the first token in the line is the same as the current blackboard course
					String currentCourseNameFromTheDb = tokens.nextToken();
					
					if ( !currentCourseNameFromTheDb.equals( theBlackboardCourse ) )
					{
						// build the current entry
						String currentEntry = nextLine + "\r\n";
						
						// write the new data to the file
						dosOnTempFile.writeBytes(currentEntry);
					}
				}
				
				// append the current Blackboard course entry to the temp file
				dosOnTempFile.writeBytes( newCourseServerCourseRelation );
				
				// close the DataOutputStream on the temp file
				dosOnTempFile.flush();
				dosOnTempFile.close();
				
				// close the FileOutputStream on the temp file
				fosOnTempFile.close();
				
				// close the reader of the main file
				readFromMainFile.close();
				
				/////// File Reading Completed ///////	
				
				/////// Transfer data back to the original file ///////
				
				// create a buffered reader on the temp file
				BufferedReader readFromTemp = new BufferedReader( new FileReader( tempFile ) );		

				// create FileOutputStream
				FileOutputStream fosOnMainFile = new FileOutputStream(dbConnectFile);
				
				// create DataOutputStream
				DataOutputStream dosOnMainFile = new DataOutputStream(fosOnMainFile);
				
				// get the next line if it exists
				while( ( nextLine = readFromTemp.readLine() ) != null ) 
				{			
						// write the data to the main file
						dosOnMainFile.writeBytes(nextLine);
						dosOnMainFile.writeBytes("\r\n");
				}
				
				// close the DataOutputStream on the main file
				dosOnMainFile.flush();
				dosOnMainFile.close();
				
				// close the FileOutputStream on the main file
				fosOnMainFile.close();
				
				// close the reader of the temp file
				readFromTemp.close();
				
				// delete the temp file
				tempFile.delete();
				
				/////// Transfer completed /////// 
				
				/////// dbConnection.txt file has been updated ///////
			}
			catch(Throwable ex)
			{
				// An error was encountered while trying to read/write to the dbConnection.txt file
				// the user selection of WeBWorK server - WeBWorK Course was not saved for future reference
			}
			
			
			String strReturnUrl = PlugInUtil.getEditableContentReturnURL(courseDoc.getParentId());
	%>
	<bbUI:docTemplate title="Success">
		<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
			<bbUI:breadcrumb>New WeBWorK Assignment</bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		<bbUI:receipt type="SUCCESS" title="WeBWorK Set Assigned" recallUrl="<%=strReturnUrl%>">The WeBWorK problem set has been successfully assigned to: <% for( String studentUser: listOfUsers ) { out.println( "<br /><b>" + studentUser + "</b>" ); } %></bbUI:receipt>
		<br>
	</bbUI:docTemplate>
	<%		
		}//end of if statement, checking for correct set name and set selection 
	%>
</bbData:context>