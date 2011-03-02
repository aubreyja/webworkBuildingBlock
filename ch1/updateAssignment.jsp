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
                 edu.rochester.webwork.devel.WebworkSOAP.*,
                 edu.rochester.webwork.devel.WebworkClient.*"
	      errorPage="error.jsp"
%>      

<!-- tag libraries, necessary for proper page formatting -->	      
<%@ taglib uri="/bbUI" prefix="bbUI"%>	
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context id="ctx">
	<jsp:useBean id="courseDoc" scope="page" class="blackboard.data.content.Content" />
	<%
		String messageToUser = null; // a placeholder for the message to be displayed to the user after the porting process completes
		boolean assignmentNameCollision = true; // holds true if there is a collision while checking for unique name of the assignment to be ported; is true to begin with to kick off the process
		
		// get the name of the assignment to be ported; NOTE: this is a plain text name and not a URL; this is the name to be inserted into the Grade Center
		String nameNewAssignment = request.getParameter( "assignmentName" );
		
		while ( assignmentNameCollision )
		{
			////////// Retrieve Course Doc Related Information //////////
			//Retrieve the Db persistence manager from the persistence service
			BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
			
			// get the course object from the context
			Course courseIdentity = ctx.getCourse();
			
			// get the course id from the course object
			Id courseId = courseIdentity.getId();
			
			// get the courseDocId necessary to load the courseDoc of the current assignment; courseDoc is the placeholder for the assignment's data such as url, comment, possible points of assignment etc.
			Id courseDocId = bbPm.generateId(Content.DATA_TYPE, request.getParameter("content_id"));
			
			// create a pointer to the courseDocumentLoader in the database
			ContentDbLoader courseDocumentLoader = (ContentDbLoader) bbPm.getLoader(ContentDbLoader.TYPE );
			
			// get the courseDoc from the database
			courseDoc = courseDocumentLoader.loadById( courseDocId );
			//////////Course Doc Related Information Has Been Retrieved //////////
		
		
				////////// Check whether the assignment to be ported has a unique name ///////////
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
					
					// add the name of the assignment to the list of names
					listWithNameOfAssignments.add(currentLineItem.getName());
				}
				//////// All of the names of the current assignments present in the database have been added to the list of names
			
			try
			{
				// traverse the list and see whethet the name of the assignment already exists in the list
				for ( int index = 0; index < listWithNameOfAssignments.size(); index++ )
				{				
					if ( nameNewAssignment.equals(listWithNameOfAssignments.get( index ) ) )
					{
						// an assignment with the same name is found present in the database; Cannot create another assignment with the same name
						assignmentNameCollision = true; // there is a collision; the name of the assignment is not unique
						nameNewAssignment = nameNewAssignment + ".1";
						throw new Exception();
					}
				}	
				
				// reset the error flag for collision detection
				assignmentNameCollision = false;
				
				/////////// Update the name of the assignment for the courseDoc.getTitle() attribute //////////
				// get the index of the "> chars which are right before the beginning of the assignment name; add 2 to this number to account for the 2 chars being compared with the rest of the string
				int indexOfBeginningOfAssignmentName = courseDoc.getTitle().indexOf( "\">" ) + 2;
				
				// get the substring of the name of the assignment necessary to build the new name of the assignment
				String assignmentNameBeforeChange = courseDoc.getTitle().substring(0,indexOfBeginningOfAssignmentName );
				
				// build the new assignment name
				String assignmentNameAfterChange = assignmentNameBeforeChange + nameNewAssignment + "</a>";
				////////// The new name of the assignment has been created //////////
				
				
				//////// The assignment to be ported has been varified to have a unique name in the current course database; Go ahead and port the assignment
				// Update the assignment in the Grade Center
				LineitemDbPersister lineitemPersister = (LineitemDbPersister) bbPm.getPersister( LineitemDbPersister.TYPE );			
				
				//constructing a new LineItem
				Lineitem lineI = new Lineitem(); // builds a new line item
				lineI.setCourseId(courseId); // assiciates the line item with a course using the course ID
				lineI.setPointsPossible( Integer.parseInt( request.getParameter( "possiblePoints" ) ) ); // sets the maximum number of points a student can get on the current assignment
				lineI.setIsAvailable(true); // sets the status of this line item; if true, the line item is available
				lineI.setDateAdded(); // sets the date when this line item was added
				lineI.setName( nameNewAssignment ); // sets the name of the line item; this is the heading for this assignment which will be displayed in the grade center table
				lineI.setType("Assignment");
				lineItems.add(lineI); // adds the current line item to the list of all line items relative to the course we are dealing with
				lineitemPersister.persist(lineItems); // persist the current line item in the database	
				// The assignment has been updated in the Grade Center
				
		
				//////// Update the data of the assignment ////////
				// Get the length of the Grading Engine URL
				int lengthOfURL = courseDoc.getUrl().length();
						
				// Get the comment of the assignment only; 
				String commentAsStoredInDB = courseDoc.getBody().getText();
				
				// copy the text from the commentAsStoredInDB to itself, starting at the position, the Grading Engine URL ends
				String plainCommentOfAssignment = commentAsStoredInDB.substring(lengthOfURL);
				
				String urlToGradingEngine = null; // place holder for the URL of the grading engine 
				
				if ( request.getParameter( "selectedServer" ).equals( "0" ) )
				{
					// create a link to the new grading engine for the assignment
					urlToGradingEngine = "<a href=\"/webapps/UofR-WeBWorK-Assignment-bb_bb60/ch1/doGradebookOnHosted2.jsp?setName=" + nameNewAssignment + "&selectedCourses=" + request.getParameter("selectedCourses") + "&course_id=" + request.getParameter("course_id") + "&content_id=" + request.getParameter( "content_id" ) + "\">Do grading</a>" + "<br />";
				}
				else // the selected server is devel.webwork.rochester.edu:8035
				{
					// create a link to the new grading engine for the assignment
					urlToGradingEngine = "<a href=\"/webapps/UofR-WeBWorK-Assignment-bb_bb60/ch1/doGradebookOnDevel8035.jsp?setName=" + nameNewAssignment + "&selectedCourses=" + request.getParameter("selectedCourses") + "&course_id=" + request.getParameter("course_id") + "&content_id=" + request.getParameter( "content_id" ) + "\">Do grading</a>" + "<br />";
				}
				
				//Append the link to the gradebook at the beginning of the comment of the assignment
				String commentOfTheAssignment = urlToGradingEngine + plainCommentOfAssignment;
				
				// preformat the comment of the assignment before appending it to the assignment
				FormattedText text = new FormattedText( commentOfTheAssignment, FormattedText.Type.HTML );
				
				// update proper values for the assignment
				courseDoc.setTitle( assignmentNameAfterChange );
				courseDoc.setUrl(urlToGradingEngine);
				courseDoc.setBody( text );
				courseDoc.setIsAvailable( true );
				
				// Create a pointer to the database
				ContentDbPersister persister= (ContentDbPersister) bbPm.getPersister( ContentDbPersister.TYPE );
				
				// persist the changes into the database
				persister.persist( courseDoc );
				//////// The data of the assignment has been updated ////////	
				
				// if there is a collision between the name of the assignment we are porting and the name of another assignment already in the database, throw an exception
				if ( !nameNewAssignment.equals( request.getParameter( "assignmentName" ) ) )
				{
					//another assignment with the same name has been found.
					messageToUser = "<font color=\"red\"><b>ATTENTION:</b></font> The name of the assignment you are porting collided with the name of another assignment already in the database! <br /><br /><font color=\"red\"><b>NOTE:</b></font> The name of the assignment you are porting has been altered, so that porting can complete!<br /><br /><font color=\"red\"><b>NOTE: </b></font> The assignment has been ported successfully!" ;
				}
				else
					messageToUser = "Assignment has been ported successfully!";
			}
			catch(Exception ex)
			{
			}
			catch(Throwable ex)
			{
				messageToUser = "<font color=\"red\"><b>ATTENTION:</b></font> Database Error! The current assignment has not been ported in the Grade Center!";
			}
		}
		// redirect the user to the updateAssignmentCompleted page
		response.sendRedirect( "updateAssignmentCompleted.jsp?messageToUser=" + messageToUser + "&course_id=" + request.getParameter( "course_id" ) + "&content_id=" + request.getParameter( "content_id" ));
	%>
</bbData:context>