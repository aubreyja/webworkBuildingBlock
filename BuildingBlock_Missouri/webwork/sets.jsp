<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>
<%@ page import="blackboard.servlet.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Beans used in this page -->
<jsp:useBean id="blackboardUtil" scope="session"
	type="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkUtil" scope="session"
	type="edu.missouri.WebworkUtil" />
<jsp:useBean id="webworkData" scope="session"
	class="edu.missouri.WebworkData" />

<c:choose>
	<c:when test="${ param.startIndex != null }">
		<c:set var="offset" value="${ param.startIndex }" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<!-- Setting the bean properties for the first time request -->
		<c:if test="${ sessionScope.previousPage eq 'courses.jsp'}">
			<!-- Creating Webwork Data Bean based on the selection from courses page -->
			<c:choose>
				<c:when test="paramValues['selectAllFromList'] == true">
					<jsp:setProperty name="webworkData"
						property="selectedWebworkCourses"
						value="${ webworkUtil.webworkCoursesList }" />
				</c:when>
				<c:otherwise>
					<jsp:setProperty name="webworkData" property="*" />
				</c:otherwise>
			</c:choose>

			<!-- Initializing the new selected courses and sets  -->
			<jsp:setProperty name="webworkUtil" property="webworkCoursesSets"
				value="${webworkData.selectedCourses}" />
			<jsp:setProperty name="webworkData" property="selectedCourses"
				value="${webworkUtil.selectedWebworkCourses}" />
			<jsp:setProperty property="listWebworkSetsForCourses"
				name="webworkData" value="${webworkUtil.webworkCoursesSets}" />
		</c:if>
	</c:otherwise>
</c:choose>

<c:set var="previousPage" value="sets.jsp" scope="session" />

<bbNG:genericPage ctxId="ctx" title="WebworkCreate">

	<bbNG:pageHeader
		instructions="Mapping the sets available for the courses selected.">
		<bbNG:pageTitleBar title="Select sets from the courses" />
	</bbNG:pageHeader>

	<!-- Drawer form is where submit button is clicked. Also the elements selected are noted here -->
	<bbNG:drawer onSubmit="generateSelectionSets()" cancelUrl="courses.jsp"
		pickerSessionId="drwSets" title="Total Selected Sets:"
		useWindowName="true">
		<bbNG:drawerColumn name="RecordIndex" label="Record Index" />
		<bbNG:drawerColumn name="CourseName" label="Course Name" />
		<bbNG:drawerColumn name="SetName" label="Set Name" />
	</bbNG:drawer>

	<bbNG:form id="setAddForm" method="post" action="assignments.jsp">

		<bbNG:dataCollection hasRequiredFields="true"
			showSubmitButtons="false">

			<bbNG:step title="Select the set">
				<bbNG:dataElement isRequired="true" label="List of available sets"
					isVertical="true">

					<bbNG:inventoryList listId="coursesList"
						className="java.lang.String"
						collection="${  webworkData.selectedCourses }"
						objectVar="eachCourse"
						emptyMsg="No Sets available for the courses Selected"
						includePageParameters="true"
						description="Inventory List of selected courses"
						enableSelectEntireList="true" shoppingCart="true">
						<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />

						<bbNG:listCheckboxElement name="selectedWebworkSetsParam"
							showCheckbox="true" value="${index + offset}" />
						<bbNG:listElement label="Record" name="RecordIndex"
							isRowHeader="true">
							${index + offset}
						</bbNG:listElement>
						<bbNG:listElement label="Course" name="CourseName">${eachCourse}</bbNG:listElement>
						<bbNG:listElement label="Set" name="SetName">
							${ webworkData.listWebworkSetsForCourses[index + offset] }
						</bbNG:listElement>

					</bbNG:inventoryList>

				</bbNG:dataElement>
			</bbNG:step>

		</bbNG:dataCollection>

	</bbNG:form>

	<!-- The javascript block to select the items. TODO jsfile  -->
	<bbNG:jsFile href="JS/webwork.js" />
	<bbNG:jsBlock>
		<script type="text/javascript">
			function generateSelectionSets() {
				var drawerItemsNumber = drawer.model.getCurrentInstance()
						.getNumberOfItems();
				if (drawerItemsNumber > 25) {
					alert('Select 25 items or less or select all.');
					return false;
				}

				var drawerSelectedItems = drawer.model.getCurrentInstance()
						.getItems();

				for ( var loop = 0; loop < document.forms["setAddForm"].selectedWebworkSetsParam.length; loop++)
					document.forms["setAddForm"].selectedWebworkSetsParam[loop].checked = false;

				for ( var loop = 0; loop < drawerSelectedItems.length; loop++) {
					document.forms["setAddForm"].selectedWebworkSetsParam[loop].value = drawerSelectedItems[loop].itemId;
					document.forms["setAddForm"].selectedWebworkSetsParam[loop].checked = true;
				}
				document.forms["setAddForm"].submit();
				return false;
			}
		</script>
	</bbNG:jsBlock>
</bbNG:genericPage>