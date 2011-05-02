<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error.jsp" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Beans used in this page -->
<jsp:useBean id="blackboardUtil" scope="session"
	type="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkUtil" scope="session"
	type="edu.missouri.WebworkUtil" />

<c:set var="previousPage" value="courses.jsp" scope="session" />
<c:set var="validPage" value="${blackboardUtil.courseContentsUrl }"
	scope="session" />

<bbNG:genericPage ctxId="ctx" title="WebworkCreate">

	<bbNG:pageHeader
		instructions="Mapping a webwork course to the blackboard.">
		<bbNG:pageTitleBar title="Selecting assignments from webwork courses" />
	</bbNG:pageHeader>

	<!-- Drawer form where submit occurs. Also the elements selected are noted here -->
	<bbNG:drawer onSubmit="generateSelectionCourses()"
		cancelUrl="${blackboardUtil.courseContentsUrl }"
		pickerSessionId="drwCourses" title="Total Selected Courses:"
		useWindowName="true">
		<bbNG:drawerColumn name="CourseCode" label="Course Code" />
		<bbNG:drawerColumn name="CourseName" label="Course Name" />
	</bbNG:drawer>

	<!-- The form where the items are listed. -->
	<bbNG:form id="courseAddForm" method="post" action="sets.jsp">
		<bbNG:dataCollection hasRequiredFields="true"
			showSubmitButtons="false">
			<bbNG:step title="Course Selection">
				<bbNG:dataElement isRequired="true"
					label="List of available courses" isVertical="true">
					<bbNG:inventoryList listId="courseContainer"
						className="java.lang.String"
						collection="${  webworkUtil.webworkCoursesList }"
						objectVar="eachCourse"
						emptyMsg="No Courses Available for you on Webwork"
						enableSelectEntireList="true" initialSortCol="CourseName"
						includePageParameters="true" shoppingCart="true" renderAjax="true">
						<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />

						<bbNG:listCheckboxElement name="selectedWebworkCoursesParam"
							showCheckbox="true" value="${ eachCourse }" />
						<bbNG:listElement label="Course" name="CourseName"
							isRowHeader="true">${eachCourse}</bbNG:listElement>
						<bbNG:listElement label="Course Code" name="CourseCode">
							To be implemented
						</bbNG:listElement>

					</bbNG:inventoryList>
				</bbNG:dataElement>
			</bbNG:step>
		</bbNG:dataCollection>
	</bbNG:form>

	<!-- The javascript block to select the items. TODO jsfile  -->
	<%-- 	<bbNG:jsFile href="JS/webwork.js" /> --%>
	<bbNG:jsBlock>
		<script type="text/javascript">
			function generateSelectionCourses() {
				var drawerItemsNumber = drawer.model.getCurrentInstance()
						.getNumberOfItems();
				if (drawerItemsNumber > 25) {
					alert('Select 25 items or less or select all.');
					return false;
				}

				var drawerSelectedItems = drawer.model.getCurrentInstance()
						.getItems();

				for ( var loop = 0; loop < document.forms["courseAddForm"].selectedWebworkCoursesParam.length; loop++) {
					document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].checked = false;
				}

				for ( var loop = 0; loop < drawerSelectedItems.length; loop++) {
					document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].value = drawerSelectedItems[loop].itemId;
					document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].checked = true;
				}
				document.forms["courseAddForm"].submit();
				return false;
			}
		</script>
	</bbNG:jsBlock>

</bbNG:genericPage>