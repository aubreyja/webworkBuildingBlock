<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<jsp:useBean id="publishData" scope="session"
	class="edu.missouri.PublishData">
	<jsp:setProperty name="publishData" property="*" />
</jsp:useBean>

<!-- Config File properties settings. Capturing the URL parameters -->
<jsp:useBean id="configUtil" class="edu.missouri.ConfigFile"
	scope="page">
	<jsp:setProperty name="configUtil" property="locationOfBean"
		value="publish.jsp" />
	<jsp:setProperty name="configUtil" property="requestUrl"
		value="${pageContext.request.requestURL }" />
	<jsp:setProperty name="configUtil" property="requestUri"
		value="${pageContext.request.requestURI }" />
	<jsp:setProperty name="configUtil" property="successLocation"
		value="${blackboardUtil.courseContentsUrl }" />
</jsp:useBean>

<!-- Setting the sets and courses MAP -->
<jsp:setProperty name="publishData" property="courseName"
	value="${webworkData.selectedWebworkCoursesMap}" />
<jsp:setProperty name="publishData" property="setName"
	value="${webworkData.selectedWebworkSetsMap}" />

<!-- Setting each of the gradebook Line item. -->
<c:forEach items="${publishData.name}" var="eachAssignment"
	varStatus="loopVar">
	<jsp:useBean id="gradeLineItemLocal"
		class="blackboard.data.gradebook.Lineitem" scope="page" />
	<jsp:setProperty name="gradeLineItemLocal" property="name"
		value="${publishData.name[loopVar.count - 1]}" />
	<jsp:setProperty name="gradeLineItemLocal" property="type"
		value="${publishData.type[loopVar.count - 1]}" />
	<jsp:setProperty name="gradeLineItemLocal" property="isAvailable"
		value="${publishData.isAvailable[loopVar.count - 1]}" />
	<jsp:setProperty name="gradeLineItemLocal" property="courseId"
		value="${blackboardUtil.courseIdentity }" />
	<jsp:setProperty name="gradeLineItemLocal" property="pointsPossible"
		value="${publishData.pointsPossible[loopVar.count - 1]}" />
	<jsp:setProperty name="gradeLineItemLocal" property="dateAdded"
		value="${publishData.datePublished}" />
	<jsp:setProperty name="publishData" property="individualItem"
		value="${gradeLineItemLocal}" />
	<c:remove var="gradeLineItemLocal" scope="page" />
</c:forEach>

<!-- Publishing the data. See the method setPublishAssignmentsData for detail. -->
<jsp:setProperty name="blackboardUtil" property="publishData"
	value="${publishData}" />
<jsp:setProperty name="blackboardUtil" property="publishAssignmentsData"
	value="true" />

<bbNG:genericPage ctxId="ctx" title="publishedAssignment">

	<bbNG:pageHeader instructions="Blackboard data written">
		<bbNG:pageTitleBar title="Publishing to blackboard." />
	</bbNG:pageHeader>

	<!-- Setting the success flags everywhere. -->
	<c:set var="success" value="${blackboardUtil.publishAssignmentsData }"
		scope="page" />
	<jsp:setProperty name="configUtil" property="serverPropertyWritten"
		value="${blackboardUtil.publishAssignmentsData }" />
	<c:choose>

		<c:when test="${pageScope.success == false}">
			<bbNG:form id="assignmentsFailedForm" method="post"
				action="publish.jsp">
				<bbNG:dataCollection hasRequiredFields="true">

					<bbNG:step title="Refurbish bad Assignment Information">
						<bbNG:dataElement isRequired="true"
							label="Set all required information" isVertical="true">

							<bbNG:inventoryList listId="assignmentsList"
								className="java.lang.String"
								collection="${ publishData.publishedRecord }"
								objectVar="eachAssignmentSuccess"
								emptyMsg="The assignments are successfully published."
								includePageParameters="true"
								description="Inventory List of selected courses" showAll="true"
								renderAjax="true">
								<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />
								<c:if test="${eachAssignmentSuccess eq false }">
									<bbNG:listElement name="RecordIndex" isRowHeader="true"
										label="Index">
										${index} : ${eachAssignmentSuccess}
									</bbNG:listElement>
									<bbNG:listElement name="AssignmentSetName"
										label="Set Assignment Name">
										<bbNG:textElement name="name"
											value="${publishData.setName[index]}" isRequired="true"
											minLength="3" maxLength="25" size="25"
											helpText="Enter the Assignment Name for the selected set name and course name" />
									</bbNG:listElement>
									<bbNG:listElement name="CourseInfo" label="Course Name">
										<bbNG:textElement name="courseName"
											value="${publishData.courseName[index]}" displayOnly="true" />
									</bbNG:listElement>
									<bbNG:listElement name="SetInfo" label="Set Name">
										<bbNG:textElement name="setName"
											value="${publishData.setName[index]}" displayOnly="true" />
									</bbNG:listElement>
									<bbNG:listElement name="typeList" label="Blackboard Type">
										<bbNG:textElement name="type"
											value="${publishData.type[index]}" isRequired="true"
											minLength="3" maxLength="25" size="25" helpText="Default." />
									</bbNG:listElement>
									<bbNG:listElement name="isAvailableList"
										label="Availability (T/F)">
										<bbNG:textElement name="isAvailable"
											value="${publishData.isAvailable[index]}" isRequired="true"
											minLength="4" maxLength="5" size="5" helpText="Default." />
									</bbNG:listElement>
									<bbNG:listElement name="pointsPossibleList"
										label="Grade Points">
										<bbNG:textElement name="pointsPossible"
											value="${publishData.pointsPossible[index]}"
											isRequired="true" minLength="1" maxLength="3" size="4"
											type="float" helpText="Points" />
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
											value="${publishData.assignmentComments[index]}"
											isRequired="true" minLength="1" maxLength="25" size="25"
											helpText="Enter Comments" />
									</bbNG:listElement>
								</c:if>
							</bbNG:inventoryList>

						</bbNG:dataElement>
					</bbNG:step>
					<bbNG:stepSubmit cancelUrl="sets.jsp" hideNumber="true" />
				</bbNG:dataCollection>
			</bbNG:form>
		</c:when>
		<c:otherwise>
			<c:redirect url="${configUtil.redirectUrl }" />
		</c:otherwise>
	</c:choose>

</bbNG:genericPage>
