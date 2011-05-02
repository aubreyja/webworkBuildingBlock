<!-- @author Martin Georgiev ( a 2009 intern at UoR ) -->

<!-- include necessary libraries -->
<%@ page import="java.util.*,
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
		//////////Retrieve Course Doc Related Information //////////
		// retrieve the Db persistence manager from the persistence service
		BbPersistenceManager bbPm = BbServiceManager.getPersistenceService().getDbPersistenceManager();
		
		Id courseId = bbPm.generateId(Course.DATA_TYPE,request.getParameter("course_id"));
		Id courseDocId = bbPm.generateId(Content.DATA_TYPE, request.getParameter("content_id"));
		
		ContentDbLoader courseDocumentLoader = (ContentDbLoader) bbPm.getLoader(ContentDbLoader.TYPE );
		courseDoc = courseDocumentLoader.loadById( courseDocId );
		////////// Course Doc Related Information Has Been Retrieved //////////

		// get the message to the user
		String message = request.getParameter( "messageToUser" );
			
		// get the url to the parent page
		String strReturnUrl = PlugInUtil.getEditableContentReturnURL(courseDoc.getParentId());
	%>
		
	<bbUI:docTemplate title="Update Assignment">
		<bbUI:breadcrumbBar  handle="control_panel" isContent="true" >
			<bbUI:breadcrumb>Update Assignment</bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		
		<bbUI:titleBar >Update Assignment</bbUI:titleBar>
				
		<bbUI:instructionBar>
				<!-- notify the user for the status of the grading process -->
				<p><%=message%></p>		
		</bbUI:instructionBar>
		<form><bbUI:button type="FORM_ACTION" name="back" alt="back" action="LINK" targetUrl="<%=strReturnUrl%>" /></form>	
		<br />
	</bbUI:docTemplate>	
</bbData:context>