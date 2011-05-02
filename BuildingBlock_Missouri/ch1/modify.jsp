<!-- This file was initially created by Daniel Arnold ( a 2008 intern at UoR )
	 This file was revised and further developed by Martin Georgiev ( a 2009 intern at UoR )   -->

<!-- include necessary libraries. These libraries are used to process
     either blackboard methods, java methods or WeBWorK methods -->
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
                 blackboard.platform.plugin.*,
                 blackboard.platform.security.*,
	             blackboard.platform.session.*,
                 blackboard.platform.db.*" 
	     errorPage="error.jsp"
%>
<!-- include tag libraries, necessary to put the web page into the Blackboard template -->
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context id='ctx'>
	<%
		//////////Retrieve Course Doc Related Information //////////
		//Retrieve the Db persistence manager from the persistence service
		BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
		Container bbContainer = bbPm.getContainer();
		
		Id contentId = new PkId( bbContainer, CourseDocument.COURSE_DOCUMENT_DATA_TYPE, request.getParameter("content_id") );
		
		ContentDbLoader courseDocumentLoader = (ContentDbLoader) bbPm.getLoader( ContentDbLoader.TYPE );
		Content courseDoc = courseDocumentLoader.loadById( contentId );
		//////////Course Doc Related Information Has Been Retrieved //////////
		
		////////// Retrieve the assignment related info //////////
		// Get the length of the Grading Engine URL
		int lengthOfURL = courseDoc.getUrl().length();
	
		// Get the comment of the assignment only; DO NOT include the URL to the grading engine!!!
		String commentAsStoredInDB = courseDoc.getBody().getText();
		
		// copy the text from the commentAsStoredInDB to itself, starting at the position, the Grading Engine URL ends
		String plainCommentOfAssignment = commentAsStoredInDB.substring( lengthOfURL );
		
		//format the plain comment text
		FormattedText text = new FormattedText( plainCommentOfAssignment, FormattedText.Type.HTML );
		
		// update the new comment
		courseDoc.setBody(text);
	
		// Comment of the assignment has been modified and it is ready to be displayed to the user
		////////// The assignment related info has been retrieved from the database //////////
	%>
	
	<bbUI:docTemplate title="Add HTML Block">
		<bbUI:breadcrumbBar  handle="control_panel" isContent="true" >
		  <bbUI:breadcrumb>Modify Assignment Data</bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		
		<bbUI:titleBar iconUrl="/images/ci/icons/tools_u.gif">Modify Assignment Data</bbUI:titleBar>
		
		<form action="modify_proc.jsp" method="post">
			<!-- append content_id and course_id to the URL of the next page before redirecting the user to it -->
			<input type="hidden" name="content_id" value="<%=request.getParameter("content_id")%>">
			<input type="hidden" name="course_id" value="<%=request.getParameter("course_id")%>">
				<div align ="center">
					<bbUI:step title="Assignment Information" number="1">
						<table>
								<!-- assignment description -->				 	
							 	<tr align="center">
							 		<td align="center">
							 			<p align="left"><b>Text:</b></p>
							 		</td>
							 		
									<td align="center">
								  		<textarea name="text" cols="40" rows="10"><%=courseDoc.getBody().getText() %></textarea>
								  	</td>
								</tr>
	
								<!-- determine the assignment availability -->
								<tr align="center">
							 		<td align="center">
							 			<p align="left"><b>Available:</b></p>
							  		</td>
							  		
							  		<td align="left">	
							  			&nbsp<input type="Radio" name="isAvailable" value = "true" <% if ( courseDoc.getIsAvailable() ) {  %> checked ="checked" <% } %> >Yes 
							  			<input type="Radio" name="isAvailable" value = "false" <% if ( !courseDoc.getIsAvailable() ) {  %> checked ="checked" <% } %>  >No 	
									</td>
								</tr>
							
								<!-- determine whether to port the assignment or not -->	
								<tr align="center">
							 		<td align="center">
							  			<p align="left"><b>Port:</b></p>
							  		</td>
							  		
							  		<td align="left">
							  			&nbsp<input type="Radio" name="needsUpdate" value = "true" >Yes 
							  			<input type="Radio" name="needsUpdate" value = "false" checked="checked" >No
							  		</td>
							  	</tr>	
					  	</table>
					</bbUI:step>
					<bbUI:stepSubmit title="Submit" number="2" cancelUrl="javascript:history.go(-1);" />
				</div>
		</form>
	</bbUI:docTemplate>						  
</bbData:context>