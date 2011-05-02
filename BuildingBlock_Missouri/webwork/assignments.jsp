<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Beans used in this page -->
<jsp:useBean id="blackboardUtil" scope="session"
	type="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkUtil" scope="session"
	type="edu.missouri.WebworkUtil" />
<jsp:useBean id="webworkData" scope="session"
	class="edu.missouri.WebworkData" />
<jsp:useBean id="dateInfo" class="java.util.Date" scope="page" />

<c:choose>
	<c:when test="${ param.startIndex != null }">
		<c:set var="offset" value="${ param.startIndex }" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="offset" value="0" scope="page"></c:set>
		<c:if test="${sessionScope.previousPage eq 'sets.jsp' }">
			<!-- Creating Webwork Data Bean based on the selection from courses page -->
			<c:choose>
				<c:when test="paramValues['selectAllFromList'] == true">
					<jsp:setProperty name="webworkData"
						property="selectedWebworkSetsParam"
						value="${ webworkData.listWebworkSetsForCourses }" />
					<!--  TODO do for entire list selection.  -->
				</c:when>
				<c:otherwise>
					<jsp:setProperty name="webworkData" property="*" />
					<jsp:setProperty name="webworkData" property="captureWebworkData"
						value="true" />
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:otherwise>
</c:choose>

<c:set var="previousPage" value="assignments.jsp" scope="session" />

<!-- Testing purpose -->
<fmt:formatDate value="${dateInfo}" pattern="MM.dd.yyyy"
	var="formattedDate" scope="page" />

<bbNG:genericPage ctxId="ctx" title="Create Assignments">

	<bbNG:pageHeader
		instructions="Mapping the set names of webwork to assignment names in blackboard.">
		<bbNG:pageTitleBar title="Set the Assignment information." />
	</bbNG:pageHeader>

	<bbNG:form id="setAddForm" method="post" action="publish.jsp">
		<bbNG:dataCollection hasRequiredFields="true">

			<bbNG:step title="Assignment Information">
				<bbNG:dataElement isRequired="true"
					label="Set all required information" isVertical="true">

					<bbNG:inventoryList listId="assignmentsList"
						className="java.lang.String"
						collection="${  webworkData.selectedWebworkSets }"
						objectVar="eachAssignment"
						emptyMsg="Report about the error to System Administrator."
						includePageParameters="true"
						description="Inventory List of selected courses" showAll="true"
						renderAjax="true">
						<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />

						<bbNG:listElement name="RecordIndex" isRowHeader="true"
							label="Index">
							${index}
						</bbNG:listElement>
						<bbNG:listElement name="AssignmentSetName"
							label="Set Assignment Name">
							<bbNG:textElement name="name"
								value="${webworkData.listWebworkSetsForCourses[eachAssignment] }"
								isRequired="true" minLength="3" maxLength="25" size="25"
								helpText="Enter the Assignment Name for the selected set name and course name" />
						</bbNG:listElement>
						<bbNG:listElement name="CourseInfo" label="Course Name">
							<bbNG:textElement name="courseNames"
								value="${webworkData.selectedCourses[eachAssignment]}"
								displayOnly="true" />
						</bbNG:listElement>
						<bbNG:listElement name="SetInfo" label="Set Name">
							<bbNG:textElement name="setNames"
								value="${webworkData.listWebworkSetsForCourses[eachAssignment] }"
								displayOnly="true" />
						</bbNG:listElement>
						<bbNG:listElement name="typeList" label="Blackboard Type">
							<bbNG:textElement name="type" value="Assignment"
								isRequired="true" minLength="3" maxLength="25" size="25"
								helpText="Default." />
						</bbNG:listElement>
						<bbNG:listElement name="isAvailableList"
							label="Availability (T/F)">
							<bbNG:radioElement name="isAvailable" value="true"
								isSelected="true" isVertical="true" optionLabel="True" />
							<bbNG:radioElement name="isAvailable" value="false"
								isSelected="false" isVertical="true" optionLabel="False" />
						</bbNG:listElement>
						<bbNG:listElement name="AnnouncmenetList" label="Annoucement">
							<bbNG:radioElement name="createAnnouncement" value="true"
								isSelected="true" isVertical="true" optionLabel="True" />
							<bbNG:radioElement name="createAnnouncement" value="false"
								isSelected="false" isVertical="true" optionLabel="False" />
						</bbNG:listElement>
						<bbNG:listElement name="pointsPossibleList" label="Grade Points">
							<bbNG:textElement name="pointsPossible" value="25"
								isRequired="true" minLength="1" maxLength="3" size="4"
								type="integer" helpText="Points" />
						</bbNG:listElement>
						<bbNG:listElement name="startDateList" label="Start Date">
							<bbNG:datePicker baseFieldName="startDate" isRequired="true"
								showTime="false" showDate="true" />
						</bbNG:listElement>
						<bbNG:listElement name="endDateList" label="End Date">
							<bbNG:datePicker baseFieldName="endDate" isRequired="true"
								showTime="false" showDate="true" />
						</bbNG:listElement>
						<bbNG:listElement name="commentsList" label="Comments">
							<bbNG:textElement name="assignmentComments"
								value="Webwork Set for ${webworkData.listWebworkSetsForCourses[eachAssignment] }"
								isRequired="true" minLength="1" maxLength="25" size="25"
								helpText="Enter Comments" />
						</bbNG:listElement>

					</bbNG:inventoryList>

				</bbNG:dataElement>
			</bbNG:step>
			<bbNG:stepSubmit cancelUrl="sets.jsp" hideNumber="true" />
		</bbNG:dataCollection>
	</bbNG:form>

</bbNG:genericPage>