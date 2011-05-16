<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="breadCrumbTitle1" key="BreadCrumb.Content" />
<fmt:message var="breadCrumbTitle4" key="BreadCrumb.Publish" />
<fmt:message var="startPage" key="Page.Create" />
<fmt:message var="beforePage2" key="Page.Courses" />
<fmt:message var="currentPage" key="Page.Publish" />
<fmt:message var="beforePage" key="Page.Assignment" />
<fmt:message var="submitPage" key="Page.Publish" />
<fmt:message var="formName" key="Form.Publish" />
<fmt:message var="pageHeaderInstructions" key="PageHeader.Publish" />
<fmt:message var="pageTitle" key="PageTitleBar.Publish" />
<fmt:message var="stepTitle" key="StepTitle.Publish" />
<fmt:message var="dataElementLabel" key="DataElementLabel.Publish" />
<fmt:message var="inventoryListId" key="InventoryListId.Publish" />
<fmt:message var="inventoryListVar" key="InventoryListVar.Publish" />
<fmt:message var="inventoryListDesc"
	key="InventoryListDescription.Publish" />
<fmt:message var="inventoryListEmpty" key="InventoryListEmpty.Publish" />
<fmt:message var="listElementName1" key="ListElementName.Assignment[1]" />
<fmt:message var="listElementName2" key="ListElementName.Assignment[2]" />
<fmt:message var="listElementName3" key="ListElementName.Assignment[3]" />
<fmt:message var="listElementName4" key="ListElementName.Assignment[4]" />
<fmt:message var="listElementName5" key="ListElementName.Assignment[5]" />
<fmt:message var="listElementName6" key="ListElementName.Assignment[6]" />
<fmt:message var="listElementName7" key="ListElementName.Assignment[7]" />
<fmt:message var="listElementName8" key="ListElementName.Assignment[8]" />
<fmt:message var="listElementName9" key="ListElementName.Assignment[9]" />
<fmt:message var="listElementName10"
	key="ListElementName.Assignment[10]" />
<fmt:message var="listElementLabel1"
	key="ListElementLabel.Assignment[1]" />
<fmt:message var="listElementLabel2"
	key="ListElementLabel.Assignment[2]" />
<fmt:message var="listElementLabel3"
	key="ListElementLabel.Assignment[3]" />
<fmt:message var="listElementLabel4"
	key="ListElementLabel.Assignment[4]" />
<fmt:message var="listElementLabel5"
	key="ListElementLabel.Assignment[5]" />
<fmt:message var="listElementLabel6"
	key="ListElementLabel.Assignment[6]" />
<fmt:message var="listElementLabel7"
	key="ListElementLabel.Assignment[7]" />
<fmt:message var="listElementLabel8"
	key="ListElementLabel.Assignment[8]" />
<fmt:message var="listElementLabel9"
	key="ListElementLabel.Assignment[9]" />
<fmt:message var="listElementLabel10"
	key="ListElementLabel.Assignment[10]" />
<fmt:message var="listElementDescription1"
	key="ListElemenDescription.Assignment[1]" />
<fmt:message var="listElementDescription4"
	key="ListElemenDescription.Assignment[4]" />
<fmt:message var="listElementDescription5"
	key="ListElemenDescription.Assignment[5]" />
<fmt:message var="listElementDescription7"
	key="ListElemenDescription.Assignment[7]" />
<fmt:message var="listElementDescription9"
	key="ListElemenDescription.Assignment[9]" />
<fmt:message var="listElementDescription10"
	key="ListElemenDescription.Assignment[10]" />
<fmt:message var="datePattern" key="Date.Pattern" />

<!-- Redirect to the create page if the utils doesn't exist. -->
<c:if test="${empty sessionScope.blackboardUtil }">
	<jsp:forward page="${startPage}" />
</c:if>

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

<c:if test="${empty sessionScope.failure }">
	<!-- Config File properties settings. Capturing the URL parameters. -->
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
	<jsp:setProperty name="blackboardUtil"
		property="publishAssignmentsData" value="true" />
	<jsp:setProperty name="webworkUtil" property="publishWebworkAssignment"
		value="true" />
</c:if>

<bbNG:genericPage ctxId="ctx">

	<!-- BreadCrumb to navigate within pages. -->
	<bbNG:breadcrumbBar environment="COURSE">
		<bbNG:breadcrumb title="${breadCrumbTitle1}"
			href="${blackboardUtil.courseContentsUrl }" />
		<bbNG:breadcrumb title="${breadCrumbTitle4}" />
	</bbNG:breadcrumbBar>

	<bbNG:pageHeader instructions="${pageHeaderInstructions}">
		<bbNG:pageTitleBar title="${pageTitle }" />
	</bbNG:pageHeader>

	<!-- Setting the success flags everywhere. -->
	<c:if test="${empty sessionScope.failure }">
		<c:set var="success" value="${blackboardUtil.publishAssignmentsData }"
			scope="page" />
		<jsp:setProperty name="configUtil" property="serverPropertyWritten"
			value="${blackboardUtil.publishAssignmentsData }" />
	</c:if>

	<c:choose>

		<c:when test="${not empty sessionScope.failure }">
			<c:remove var="failure" scope="session" />

			<bbNG:form id="${formName}" method="post" action="${submitPage}">

				<bbNG:dataCollection hasRequiredFields="true">

					<bbNG:step title="${stepTitle}">
						<bbNG:dataElement isRequired="true" label="${dataElementLabel}"
							isVertical="true">

							<bbNG:inventoryList listId="${inventoryListId}"
								className="java.lang.String"
								collection="${ publishData.publishedRecord }"
								objectVar="eachAssignmentSuccess"
								emptyMsg="${inventoryListEmpty}"
								description="${inventoryListDesc}" includePageParameters="true"
								showAll="true" renderAjax="true">
								<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />

								<!-- Check for bad assignment publishment alone. -->
								<c:if test="${eachAssignmentSuccess eq false }">
									<bbNG:listElement name="RecordIndex" isRowHeader="true"
										label="Index">
										${index} : ${eachAssignmentSuccess}
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName1}"
										label="${listElementLabel1}">
										<bbNG:textElement name="name"
											value="${publishData.setName[index]}" isRequired="true"
											minLength="3" maxLength="25" size="25"
											helpText="${listElementDescription1}" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName2}"
										label="${listElementLabel2}">
										<bbNG:textElement name="courseName"
											value="${publishData.courseName[index]}" displayOnly="true" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName3}"
										label="${listElementLabel3}">
										<bbNG:textElement name="setName"
											value="${publishData.setName[index]}" displayOnly="true" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName4}"
										label="${listElementLabel4}">
										<bbNG:textElement name="type"
											value="${publishData.type[index]}" isRequired="true"
											minLength="3" maxLength="25" size="25"
											helpText="${listElementDescription4}" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName5}"
										label="${listElementLabel5}">
										<bbNG:textElement name="isAvailable"
											value="${publishData.isAvailable[index]}" isRequired="true"
											minLength="4" maxLength="5" size="5"
											helpText="${listElementDescription5}" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName6}"
										label="${listElementLabel6}">
										<bbNG:radioElement name="createAnnouncement" value="true"
											isSelected="${publishData.createAnnouncement[index] eq true ? true : false }"
											isVertical="true" optionLabel="True" />
										<bbNG:radioElement name="createAnnouncement" value="false"
											isSelected="${publishData.createAnnouncement[index] eq false ? true : false }"
											isVertical="true" optionLabel="False" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName7}"
										label="${listElementLabel7}">
										<bbNG:textElement name="pointsPossible"
											value="${publishData.pointsPossible[index]}"
											isRequired="true" minLength="1" maxLength="3" size="4"
											type="float" helpText="${listElementDescription7}" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName8}"
										label="${listElementLabel8}">
										<bbNG:datePicker helpText="${listElementDescription8}"
											baseFieldName="startDate" isRequired="true" showTime="false"
											showDate="true" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName9}"
										label="${listElementLabel9}">
										<bbNG:datePicker helpText="${listElementDescription9}"
											baseFieldName="endDate" isRequired="true" showTime="false"
											showDate="true" />
									</bbNG:listElement>
									<bbNG:listElement name="${listElementName10}"
										label="${listElementLabel10}">
										<bbNG:textElement name="assignmentComments"
											value="${publishData.assignmentComments[index]}"
											isRequired="true" minLength="1" maxLength="25" size="25"
											helpText="${listElementDescription10}" />
									</bbNG:listElement>
								</c:if>
							</bbNG:inventoryList>

						</bbNG:dataElement>
					</bbNG:step>
					<bbNG:stepSubmit cancelUrl="${beforePage}" hideNumber="true" />
				</bbNG:dataCollection>
			</bbNG:form>
		</c:when>
		<c:otherwise>
			<!-- Setting the redirection page to an attribute. -->
			<c:set var="redirectPage" value="${configUtil.redirectUrl }"
				scope="page" />

			<!-- Removing session and page scoped attributes. -->
			<c:remove var="webworkData" scope="session" />
			<c:remove var="publishData" scope="session" />
			<c:remove var="configUtil" scope="page" />
			<c:remove var="blackboardUtil" scope="session" />
			<c:remove var="webworkUtil" scope="session" />

			<c:if test="${pageScope.success == false}">
				<c:set var="failure" value="true" scope="session" />
			</c:if>
			<c:redirect url="${pageScope.redirectPage }" />
		</c:otherwise>
	</c:choose>

</bbNG:genericPage>
