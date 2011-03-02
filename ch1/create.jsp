<!-- @author Martin Georgiev ( a 2009 intern at UoR )  -->
	 
<!-- APIs necessary for method processing -->
<%@ page import="java.util.*,
				 java.io.*,   
                 blackboard.data.course.*,              
                 blackboard.platform.plugin.*"
%>

<!-- Tag library necessary to extract the info from the context -->
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">
<%
	try 
	{	
		///////Create a File ///////
		
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
		File dbConnectFile = new File(buildingBlockDbConnectionDirectory, "dbConnection.txt");	
		
		// if the file does not exist
		if (!dbConnectFile.exists()) 
		{
			// create the file
			dbConnectFile.createNewFile();
		}
		/////// The File Has Been Either Created Or Found To Exist///////		

		/////// Get Course Specific Info ///////
		
		// get the course object from the context
		Course courseId = ctx.getCourse();
		
		// get the course name from the course object
		String theBlackboardCourse = courseId.getBatchUid();
		out.println(theBlackboardCourse);
		/////// Course Specific Info Has Been Retrieved ///////
		
		/////// Read From The dbConnection File ///////
			
		// create a buffered reader on the file of interest
		BufferedReader in = new BufferedReader( new FileReader( dbConnectFile ) );		

		// create a placeholder for a line
		String nextLine;
		
		// create a placeholder for the WW-server name
		String wwServerName = "";
		
		// create a placeholder for the WW_Course name
		String wwCourseName = "";
		
		// error flag for course info found; if true, the current Blackboard Course has an associated WW server and WW course selected
		boolean courseInfoFound = false;
		
		// get the next line if it exists
		while( ( nextLine = in.readLine() ) != null ) 
		{			
			// tokenize the line using the space character as a delimeter; Each line should have three tokens only in the order: BB_Course -> WW_Server -> WW_Course; The BB_Course is a pivot key
			StringTokenizer tokens = new StringTokenizer( nextLine, " " );
			// if the first token in the line is the same as the current blackboard course
			if ( tokens.nextToken().equals( theBlackboardCourse ) )
			{
				// get the WW_Server name
				wwServerName = tokens.nextToken();
				
				// get the WW_Course name
				wwCourseName = tokens.nextToken();
				
				// update the courseInfoFound error flag; the necessary info has been found; the ww server selection and ww course selection steps can be skipped.
				courseInfoFound = true;
			}
		}
		/////// File Reading Completed ///////	
		
		// if the course is found, jump to the set selection page
		if ( ( courseInfoFound ) && ( 2 == Integer.parseInt(wwServerName) ) )
			response.sendRedirect( "selectWeBWorKSetOnDevel8035.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedServer=" + wwServerName + "&selectedCourses=" + wwCourseName);
		else if ( ( courseInfoFound ) && ( 1 == Integer.parseInt(wwServerName) ) )
			response.sendRedirect( "selectWeBWorKSetOnDevel8002.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedServer=" + wwServerName + "&selectedCourses=" + wwCourseName);
		else if ( ( courseInfoFound ) && ( 0 == Integer.parseInt(wwServerName) ) )
			response.sendRedirect( "selectWeBWorKSetOnHosted2.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedServer=" + wwServerName + "&selectedCourses=" + wwCourseName);
		else // lead the user through the full process of selecting the WW server and the WW course for the current Blackboard Course
			response.sendRedirect( "selectWeBWorKServer.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id"));
	}
	catch (IOException e)
	{
	    // an error has been encountered; try leading the user through the full process of selecting the WW server and the WW course to see whether that will resolve the issue.
		response.sendRedirect( "selectWeBWorKServer.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id"));
	}
%>
</bbData:context>