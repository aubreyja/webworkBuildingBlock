<!-- @author Martin Georgiev ( a 2009 intern at UoR )  -->
	 
<!-- APIs necessary for method processing -->
<%@ page import="java.util.*"
		  errorPage="error.jsp"
%>
<!-- tag libraries, necessary for proper page formatting -->
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<bbData:context>	
	<bbUI:docTemplate title="Select a WeBWorK Server">
		<bbUI:breadcrumbBar environment="CTRL_PANEL"  handle="control_panel" isContent="true" >
			<bbUI:breadcrumb>Create New WeBWorK Assignment</bbUI:breadcrumb>
		</bbUI:breadcrumbBar>
		
		<bbUI:titleBar >Select a WeBWorK Server</bbUI:titleBar>
	
		<form name="theform" action="processWeBWorKServer.jsp" method="post">
			<input type="hidden" name="content_id" value="<%=request.getParameter("content_id")%>">
			<input type="hidden" name="course_id" value="<%=request.getParameter("course_id")%>">
			<div align = "center">
				<bbUI:step title="Choose a WeBWorK Server" number="1">
					<br />
					<table align="center">
						<tr>
							<td>
								<input type="radio" name="selectedServer" value="0"><b> hosted2.webwork.rochester.edu</b>
							</td>
						</tr>
				<!-- 	<tr>
							<td>
								<input type="radio" name="selectedServer" value="1"><b> devel.webwork.rochester.edu:8002</b>
							</td>
						</tr> -->
						<tr>
							<td>
								<input type="radio" name="selectedServer" value="2"><b> devel.webwork.rochester.edu:8035</b>
							</td>
						</tr>
					</table>
					<br />
				</bbUI:step>
				<bbUI:stepSubmit title="Submit" number="2" cancelUrl="javascript:history.go(-1);" />
			</div>
		</form>
	</bbUI:docTemplate>
</bbData:context>