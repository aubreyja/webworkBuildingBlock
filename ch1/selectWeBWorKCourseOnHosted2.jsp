<!-- This file was initially created by Daniel Arnold ( a 2008 intern at UoR )
	 This file was revised and further developed by Martin Georgiev ( a 2009 intern at UoR )  -->
	 
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
                 blackboard.platform.plugin.*,
                 blackboard.platform.security.*,
	             blackboard.platform.session.*,
                 blackboard.platform.db.*,
             	 blackboard.servlet.data.*,
                 edu.rochester.webwork.hosted2.WebworkSOAP.*" 
		  errorPage="error.jsp"
%>
<!-- tag libraries, necessary for proper page formatting -->
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context>
	<%
		//$authKey - string with currently defided value by WeBWorK as given below	
		final String authKey = "123456789123456789";
		
		// Declare and instantiate SOAP classes; Get connected to the WeBWorK Server
		WebworkSOAPHandlerService handlerService = new WebworkSOAPHandlerServiceLocator();
		WebworkSOAPHandler soapHandler = handlerService.getWebworkSOAP();

		//Obtain the list of courses from the WeBWorK server
		String [] courses = soapHandler.list_courses( authKey );
		
		List<MultiSelectBean> coursesList = new ArrayList<MultiSelectBean>( courses.length );
		List<MultiSelectBean> selectedCourses = new ArrayList<MultiSelectBean>( 1 );
		
		for ( String theCourse : courses ) 
		{
			//construct a bean
			MultiSelectBean mBean = new MultiSelectBean();
			mBean.setLabel( theCourse );
			mBean.setValue( theCourse );
		//	mBean.setIsSelected(false);
			
			//add the bean to the coursesList
			coursesList.add( mBean );
		}
	%>
	
	<bbUI:docTemplate title="Select a WeBWorK Course">
		<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
			<bbUI:breadcrumb>Create New WeBWorK Assignment</bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		
		<bbUI:titleBar >Select a WeBWorK Course</bbUI:titleBar>
		
		<bbUI:instructionBar>
			<% 
				if ( ( request.getParameter( "error" ) != null ) && ( request.getParameter( "error" ).equals( "true" ) ) )  out.println( "<font color=red><b>ATTENTION:</b></font> Select one course only." ); 
			%>
		</bbUI:instructionBar>
		<form name="theform" action="selectWeBWorKSetOnHosted2.jsp" method="post">
			<input type="hidden" name="content_id" value="<%=request.getParameter("content_id")%>">
			<input type="hidden" name="course_id" value="<%=request.getParameter("course_id")%>">
			<input type="hidden" name="selectedServer" value="<%=request.getParameter("selectedServer")%>">
			<div align = "center">
				<bbUI:step title="Choose a Course" number="1">						
							<bbUI:multiSelect leftTitle="Available Courses" rightTitle="Selected Course" leftCollection="<%=coursesList%>" rightSelectName="selectedCourses" rightCollection="<%=selectedCourses%>" formName="theform"/>
				</bbUI:step>
				<bbUI:stepSubmit title="Submit" number="2" cancelUrl="javascript:history.go(-1);" />
			</div>
		</form>
	</bbUI:docTemplate>
</bbData:context>