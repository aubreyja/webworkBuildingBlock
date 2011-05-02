<!-- This file was initially created by Daniel Arnold ( a 2008 intern at UoR )
	 This file was revised and further developed by Martin Georgiev ( a 2009 intern at UoR )  -->
	 
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
                 edu.rochester.webwork.devel.WebworkSOAP.*,
                 edu.rochester.webwork.devel.WebworkClient.*"
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
		boolean isError = false; 
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
					
			/////////////////////////////////////
			/////////////////////////////////////
			/////////////////////////////////////
		}
		catch (Throwable ex)
		{
			// Generate link and redirect the user if necessary
			if ( isError )
				response.sendRedirect( "/webapps/UofR-WeBWorK-Assignment-bb_bb60/ch1/selectWeBWorKSetOnDevel8035.jsp?content_id=" 
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
			
			//get the user from the context
			User sessionUser = ctx.getUser();
			String theUser = sessionUser.getUserName();
			
			// get the course from the context
			Course sessionCourse = ctx.getCourse();
			
			// get the course name from the course object
			String theBlackboardCourse = sessionCourse.getBatchUid();
			
			// Declare and instantiate SOAP classes; Get connected to the WeBWorK Server
			WebworkSOAPHandlerService handlerService = new WebworkSOAPHandlerServiceLocator();
			WebworkSOAPHandler soapHandler = handlerService.getWebworkSOAP();
			
			// get the name of the set from the URL
			String nameOfSelectedSet = request.getParameter( "setName" );
			
			// retrieve the number of problems in the selected set.
			String[] listONamesOffProblemsInThisAssignment = soapHandler.list_global_problems( authKey, request.getParameter( "selectedCourses" ), request.getParameter( "selectedSets" ) );
			
			// get the number of possible points on this assignment
			int numberOfPossiblePoints = weightOfProblem * ( listONamesOffProblemsInThisAssignment.length );
				
			//assign the homework set to the user in WeBWorK, so that when he/she comes back, he/she can complete it.
			//if( !soapHandler.exists_user_set( authKey, request.getParameter( "selectedCourses" ), theUser, request.getParameter( "selectedSets" ) ).equals( "1" ) )
			//	soapHandler.assign_set_to_user( authKey, request.getParameter( "selectedCourses" ), theUser, request.getParameter( "selectedSets" ) );
		
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
	   	    String defaultPage2 = PlugInUtil.getUri("UofR", "WeBWorK-Assignment", "ch1/webwork.jsp");	
		  defaultPage2 = defaultPage2 + "?selectedCourses=" + request.getParameter( "selectedCourses" ) + "&setName=" + request.getParameter( "selectedSets" ) + "&possiblePoints=" + numberOfPossiblePoints + "&selectedServer=" + request.getParameter("selectedServer") + "\">" + request.getParameter( "setName" ) + "</a>";
		  String assignmentNameAndUrl = "<a href=\"" + defaultPage2;
			// generate the link to be embedded in the name of the assignment so that it is accessible


			// create a link to the grading engine for the assignment
			String urlToGradingEngine = "<a href=\"/webapps/UofR-WeBWorK-Assignment-bb_bb60/ch1/doGradebookOnDevel8035.jsp?setName=" + request.getParameter("setName") + "&selectedCourses=" + request.getParameter("selectedCourses") + "&course_id=" + request.getParameter("course_id") + "&content_id=" + request.getParameter( "content_id" ) + "\">Do grading</a>" + "<br />";
			
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
				else
				{
					
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