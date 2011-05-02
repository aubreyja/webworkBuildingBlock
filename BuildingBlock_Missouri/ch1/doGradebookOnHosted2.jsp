<!--  @author Martin Georgiev ( a 2009 intern at UoR )    -->

<!-- APIs necessary for method processing -->
<%@page import="java.util.*,
				blackboard.base.*,
				blackboard.data.user.*,
				blackboard.data.role.*,
				blackboard.data.course.*,
				blackboard.data.gradebook.*,
				blackboard.persist.*,
				blackboard.persist.course.*,
				blackboard.persist.gradebook.*,
				blackboard.persist.gradebook.impl.*,
				blackboard.platform.*,
				edu.rochester.webwork.hosted2.WebworkSOAP.*"
		errorPage="error.jsp"%>

<!-- tag libraries, necessary for proper page formatting -->	
<%@ taglib uri="/bbUI" prefix="bbUI" %>
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">

<%
	//construct a string for proper message handling
	String message;

	// get the user object from the context
	User sessionUser = ctx.getUser();
	
	// get the user id from the user object
	Id userId = sessionUser.getId();
	
	//Retrieve the Db persistence manager from the persistence service
	BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();

	// get the course object from the context
	Course courseIdentity = ctx.getCourse();
	
	// get the course id from the course object
	Id courseId = courseIdentity.getId();
	
	// get the name of the course
	String courseName = courseIdentity.getBatchUid();
	
	// create a pointer to the lineitem database
	LineitemDbLoader lidl = (LineitemDbLoader)bbPm.getLoader(LineitemDbLoader.TYPE);
	
	// get a list of lineitems relative to the course
	BbList lineItems = lidl.loadByCourseId(courseId);
	
	// create a pointer to the score database loader
	ScoreDbLoader sdl = (ScoreDbLoader)bbPm.getLoader(ScoreDbLoader.TYPE);
	
	// create a pointer to the score database persister
	ScoreDbPersister scorePersister = (ScoreDbPersister) bbPm.getPersister( ScoreDbPersister.TYPE );
	
	// create a placeholder for the user's course membership
	CourseMembership crsMembership = null;	
	
	// create a pointer to the course membership information
	CourseMembershipDbLoader crsMembershipLoader = (CourseMembershipDbLoader)bbPm.getLoader(CourseMembershipDbLoader.TYPE);
	
	try
	{		
		// default authentication key set in the WeBWorK database
		final String authKey = "123456789123456789";
		
		// Declare and instantiate SOAP classes; Get connected to the WeBWorK Server
		WebworkSOAPHandlerService handlerService = new WebworkSOAPHandlerServiceLocator();
		WebworkSOAPHandler soapHandler = handlerService.getWebworkSOAP();
		
		// get the user's course membership information
	    crsMembership = crsMembershipLoader.loadByCourseAndUserId(courseId,userId);
		
	 	// get the user's role
		CourseMembership.Role crsMembershipRole = crsMembership.getRole();
	 	
	    // declare a non existant position as a default position
	    int positionOfAssignmentInDatabase = -1; 
		
		// create an iterator over the list of line items in the database
		Iterator lineItemIterator = lineItems.iterator();
		
		// iterate over the list and find the right assignment
		while ( lineItemIterator.hasNext() )
		{
			// get the current lineitem from the list of lineitems
			Lineitem currentLineItem = (Lineitem) lineItemIterator.next();
			
			// if this lineitem is the assignment we are looking for:
			if ( currentLineItem.getName().equals(request.getParameter("setName")))
			{
				// get the position of the lineitem in the list
				positionOfAssignmentInDatabase = lineItems.indexOf(currentLineItem);
				break;
			}
		}
	
		// check for the position of the assignment
		if (positionOfAssignmentInDatabase == -1 )
		{
			// the assignment we are looking for was not found in the Blackboard database
			throw new NullPointerException();
		}
		else //if we get here, the assignment we are interested in is present in the Blackboard database
		{
			if ( !crsMembershipRole.toFieldName().equals("INSTRUCTOR")) // and the user is not the instructor of the course
			{
				// create a placeholder for the score
				Score scoreSet;
				
				// get the lineitem of the assignment we are interested in
				Lineitem lin = (Lineitem) lineItems.get(positionOfAssignmentInDatabase);
							
				// check whether the assignment has already been graded
				try
				{
					// try to get the score of the assignment. If an error is thrown, the assignment has not been graded yet.
					scoreSet = sdl.loadByCourseMembershipIdAndLineitemId(crsMembership.getId(), lin.getId());
					
					// HERE IS WHERE WE HAVE TO UPDATE THE GRADE
					// NOTE THAT WE HAVE TO PULL THE GRADE THE WEBWORK DATABASE
					
					// get the list of users
					String [] listOfUsers = soapHandler.list_users ( authKey, request.getParameter( "selectedCourses" ));
					
					// initialize a default position for the current user. NOTE that if at the end, the default position is preserved, the user is not on the course list
					int indexOfUser = -1;
					// find the index of the current user in the list
					for ( int i = 0; i < listOfUsers.length; i++ )
					{
						String currentUser = listOfUsers[i];
						if ( sessionUser.getUserName().equals(currentUser))
						{
							indexOfUser = i;
							break;
						}
					}
					
					// get everybody's grades from the WeBWorK database
					String[] userGrades = soapHandler.grade_users_sets(authKey, request.getParameter("selectedCourses"), listOfUsers, request.getParameter("setName"));
					
					// get the current user's grade
					String currentUserGrade = userGrades[indexOfUser - 1];
					
					// update the grade in the Grade Center
					scoreSet.setGrade(currentUserGrade); 
				}
				catch(Throwable ex)
				{
					// create a new score for this assignment
					scoreSet = new Score();
					
					// HERE IS WHERE WE HAVE TO UPDATE THE GRADE
					// NOTE THAT WE HAVE TO PULL THE GRADE FROM THE WEBWORK DATABASE

					// get the list of users
					String [] listOfUsers = soapHandler.list_users ( authKey, request.getParameter( "selectedCourses" ));
					
					// initialize a default position for the current user. NOTE that if at the end, the default position is preserved, the user is not on the course list
					int indexOfUser = -1;
					
					// find the index of the current user in the list
					for ( int i = 0; i < listOfUsers.length; i++ )
					{
						String currentUser = listOfUsers[i];
						if ( sessionUser.getUserName().equals(currentUser))
						{
							indexOfUser = i;
							break;
						}
					}
					
					// get everybody's grades from the WeBWorK database
					String[] userGrades = soapHandler.grade_users_sets(authKey, request.getParameter("selectedCourses"), listOfUsers, request.getParameter("setName"));
					
					// get the current user's grade
					String currentUserGrade = userGrades[indexOfUser - 1];
					
					// update the grade in the Grade Center
					scoreSet.setGrade(currentUserGrade);
				}
				
				// set the course membership for the assignment
				scoreSet.setCourseMembershipId(crsMembership.getId());
				
				// associate the score with the assignment
				scoreSet.setLineitemId(lin.getId());
				
				// save the score in the Blackboard database
				scorePersister.persist(scoreSet);
				
				// confirm successful grading
				message = lin.getName() + " has been graded successfully! To check you grade, please go to the <em>My Grades</em> tab under <em>Course Tools</em>.";	
			}
			else // and the user is the instructor of the course
			{
				String studentsPresentGrades = "studentsPresentGradesOnHosted2.jsp?content_id=" + request.getParameter( "content_id" ) + "&course_id=" + request.getParameter("course_id") + "&selectedCourses=" + request.getParameter( "selectedCourses" ) + "&setName=" + request.getParameter("setName");
				message = "<font color=red><b>ATTENTION:</b></font> You are currently logged in as the instuctor of: <b>" + courseName + "</b>. If you are not the instructor, please log out immediately. If you are the instructor, you can check students' present grades: <a href=" + studentsPresentGrades + ">HERE!</a>";
			}
		}
	}					
	catch(Throwable exception)
	{
		message = "<font color=red><b>ATTENTION:</b></font> An error has been encountered while trying to grade the requested assignment. Please contact your course instructor.";
	}
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
		
				<!-- allow the student to go to the previous page by clicking on the Back button -->
				<form><bbUI:button type="FORM_ACTION" name="back" alt="Back" action="LINK" targetUrl="javascript:history.go(-1);" /></form>			
		</bbUI:instructionBar>		
	</bbUI:docTemplate>
</bbData:context>