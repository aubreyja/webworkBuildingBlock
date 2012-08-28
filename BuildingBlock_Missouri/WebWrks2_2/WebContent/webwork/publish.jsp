<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error/jasper.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<fmt:message var="inventoryListDesc" key="InventoryListDescription.Publish" />
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
<fmt:message var="listElementName10" key="ListElementName.Assignment[10]" />
<fmt:message var="listElementName11" key="ListElementName.Assignment[11]" />
<fmt:message var="listElementLabel1" key="ListElementLabel.Assignment[1]" />
<fmt:message var="listElementLabel2" key="ListElementLabel.Assignment[2]" />
<fmt:message var="listElementLabel3" key="ListElementLabel.Assignment[3]" />
<fmt:message var="listElementLabel4" key="ListElementLabel.Assignment[4]" />
<fmt:message var="listElementLabel5" key="ListElementLabel.Assignment[5]" />
<fmt:message var="listElementLabel6" key="ListElementLabel.Assignment[6]" />
<fmt:message var="listElementLabel7" key="ListElementLabel.Assignment[7]" />
<fmt:message var="listElementLabel8" key="ListElementLabel.Assignment[8]" />
<fmt:message var="listElementLabel9" key="ListElementLabel.Assignment[9]" />
<fmt:message var="listElementLabel10" key="ListElementLabel.Assignment[10]" />
<fmt:message var="listElementLabel11" key="ListElementLabel.Assignment[11]" />
<fmt:message var="listElementDescription1" key="ListElemenDescription.Assignment[1]" />
<fmt:message var="listElementDescription4" key="ListElemenDescription.Assignment[4]" />
<fmt:message var="listElementDescription5" key="ListElemenDescription.Assignment[5]" />
<fmt:message var="listElementDescription7" key="ListElemenDescription.Assignment[7]" />
<fmt:message var="listElementDescription9" key="ListElemenDescription.Assignment[9]" />
<fmt:message var="listElementDescription10" key="ListElemenDescription.Assignment[10]" />
<fmt:message var="datePattern" key="Date.Pattern" />

<!-- Redirect to the create page if the utils doesn't exist. -->
<c:if test="${empty sessionScope.blackboardUtil }">
	<jsp:forward page="${startPage}" />
</c:if>

<!-- Importing Beans used in this page -->
<jsp:useBean id="blackboardUtil" scope="session" type="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkUtil" scope="session" type="edu.missouri.WebworkUtil" />
<jsp:useBean id="webworkData" scope="session" class="edu.missouri.WebworkData" />
<jsp:useBean id="publishData" scope="session" class="edu.missouri.PublishData">
	<jsp:setProperty name="publishData" property="*" />
</jsp:useBean>

<c:if test="${empty sessionScope.failure }">
	<!-- Config File properties settings. Capturing the URL parameters. -->
	<jsp:useBean id="configUtil" class="edu.missouri.ConfigFile" scope="page">
		<jsp:setProperty name="configUtil" property="locationOfBean" value="publish.jsp" />
		<jsp:setProperty name="configUtil" property="requestUrl" value="${blackboardUtil.requestUrl }" />
		<jsp:setProperty name="configUtil" property="requestUri" value="${blackboardUtil.requestUri }" />
		<jsp:setProperty name="configUtil" property="successLocation" value="${blackboardUtil.courseContentsUrl }" />
	</jsp:useBean>

	<!-- Setting the sets and courses MAP -->
	<jsp:setProperty name="publishData" property="courseName" value="${webworkData.selectedWebworkCoursesMap}" />
	<jsp:setProperty name="publishData" property="setName" value="${webworkData.selectedWebworkSetsMap}" />

	<!-- Setting the initial dates to capture if any dates changed in blackboard. -->
	<jsp:setProperty name="publishData" property="assignmentsInitialStartDate" value="${webworkData.selectedWebworkSetsOpenDate }" />
	<jsp:setProperty name="publishData" property="assignmentsInitialEndDate" value="${webworkData.selectedWebworkSetsDueDate }" />

</c:if>
<c:if test="${not empty param.save}">
	<!-- Recreating the entire data captured from the republished / failed form. -->
	<jsp:setProperty name="publishData" property="name" param="name" />
	<jsp:setProperty name="publishData" property="pointsPossible" param="pointsPossible" />
	<jsp:setProperty name="publishData" property="assignmentComments" param="assignmentComments" />
	<jsp:setProperty name="publishData" property="type" param="type" />
	<jsp:setProperty name="publishData" property="isAvailable" param="isAvailable" />
	<jsp:setProperty name="publishData" property="isTracked" param="isTracked" />
	<jsp:setProperty name="publishData" property="createAnnouncement" param="createAnnouncement" />
	<jsp:setProperty name="publishData" property="startDate" param="startDate" />
	<jsp:setProperty name="publishData" property="endDate" param="endDate" />
	<jsp:setProperty name="publishData" property="rePublishReset" value="true" />
</c:if>
<c:if test="${(empty sessionScope.failure) or (not empty param.save) }">

	<!-- Setting each of the gradebook Line item. -->
	<jsp:setProperty name="publishData" property="courseId" value="${blackboardUtil.courseIdentity }" />
<%-- 	<c:forEach items="${publishData.name}" var="eachAssignment" varStatus="loopVar">
		<jsp:useBean id="gradeLineItemLocal" class="blackboard.data.gradebook.Lineitem" scope="page" />
		<jsp:setProperty name="gradeLineItemLocal" property="name" value="${publishData.name[loopVar.count - 1]}" />
		<jsp:setProperty name="gradeLineItemLocal" property="type" value="${publishData.type[loopVar.count - 1]}" />
		<jsp:setProperty name="gradeLineItemLocal" property="isAvailable" value="${publishData.isAvailable[loopVar.count - 1]}" />
		<jsp:setProperty name="gradeLineItemLocal" property="courseId" value="${blackboardUtil.courseIdentity }" />
		<jsp:setProperty name="gradeLineItemLocal" property="pointsPossible" value="${publishData.pointsPossible[loopVar.count - 1]}" />
		<jsp:setProperty name="gradeLineItemLocal" property="dateAdded" value="${publishData.datePublished}" />
		<jsp:setProperty name="publishData" property="individualItem" value="${gradeLineItemLocal}" />
		<c:remove var="gradeLineItemLocal" scope="page" />
	</c:forEach> --%>

	<!-- Publishing the data. See the method setPublishAssignmentsData for detail. -->
	<jsp:setProperty name="blackboardUtil" property="publishData" value="${publishData}" />
	<jsp:setProperty name="blackboardUtil" property="publishAssignmentsData" value="true" />
	<!-- Used only for listing all webwork courses, without any search method. 
			See WebworkUtil - set method for publishWebworkAssignment -->
	<jsp:setProperty name="webworkUtil" property="publishWebworkAssignment" value="true" />
	<c:set var="success" value="true" scope="page" />
</c:if>

<bbNG:genericPage ctxId="ctx">

	<!-- BreadCrumb to navigate within pages. -->
	<bbNG:breadcrumbBar environment="COURSE">
		<bbNG:breadcrumb title="${breadCrumbTitle1}" href="${blackboardUtil.courseContentsUrl }" />
		<bbNG:breadcrumb title="${breadCrumbTitle4}" />
	</bbNG:breadcrumbBar>

	<bbNG:pageHeader instructions="${pageHeaderInstructions}">
		<bbNG:pageTitleBar title="${pageTitle }" />
	</bbNG:pageHeader>

	<!-- Setting the success flags everywhere. -->
	<c:if test="${empty sessionScope.failure }">
		<c:set var="success" value="${blackboardUtil.publishAssignmentsData }" scope="page" />
		<jsp:setProperty name="configUtil" property="serverPropertyWritten" value="${blackboardUtil.publishAssignmentsData }" />
	</c:if>
	<c:if test="${pageScope.success == false}">
		<c:set var="failure" value="true" scope="session" />
		<c:set var="success" value="true" scope="page" />
		<c:redirect url="${configUtil.redirectUrl }" />
	</c:if>

	<c:choose>

		<c:when test="${not empty sessionScope.failure }">
			<c:remove var="failure" scope="session" />
			<jsp:setProperty name="publishData" property="publishedRecord" value="${blackboardUtil.publishData.publishedRecord }" />
			<bbNG:form id="${formName}" method="post" action="${submitPage}?save=true" onsubmit="return validatePublishForm();">
				<!-- ${submitPage}?save=true -->

				<bbNG:dataCollection hasRequiredFields="true">

					<bbNG:step title="${stepTitle}">
						<bbNG:dataElement isRequired="true" label="${dataElementLabel}" isVertical="true">
							<bbNG:inventoryList listId="${inventoryListId}" className="java.lang.Boolean" collection="${ publishData.publishedRecord }" includePageParameters="true"
								objectVar="eachAssignmentSuccess" emptyMsg="${inventoryListEmpty}" description="${inventoryListDesc}" showAll="true" renderAjax="true">
								
								<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />

								<!-- Check for bad assignment publishment alone. -->

								<bbNG:listElement name="${listElementName1}" isRowHeader="true" label="${listElementLabel1}">
									<bbNG:textElement name="name" isDisabled="${eachAssignmentSuccess}" value="${publishData.name[index]}" isRequired="true"
										minLength="3" maxLength="25" size="25" helpText="${listElementDescription1}" />
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName2}" label="${listElementLabel2}">
									<bbNG:textElement name="courseName" value="${publishData.courseName[index]}" displayOnly="true" />
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName3}" label="${listElementLabel3}">
									<bbNG:textElement name="setName" value="${publishData.setName[index]}" displayOnly="true" />
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName4}" label="${listElementLabel4}">
									<bbNG:textElement name="type" isDisabled="${eachAssignmentSuccess}" value="${publishData.type[index]}" isRequired="true"
										minLength="3" maxLength="25" size="25" helpText="${listElementDescription4}" />
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName5}" label="${listElementLabel5}">
									<bbNG:dataElement isRequired="true" isVertical="true">
										<bbNG:checkboxElement name="isAvailable" value="true" optionLabel="${chkYes}" isSelected="${publishData.isAvailable[index]}"
											isDisabled="${(not publishData.isTracked[index]) or eachAssignmentSuccess}" onchange="selectCheckbox();" isVertical="true" />
										<bbNG:checkboxElement name="isAvailable" value="false" optionLabel="${chkNo}" isSelected="${not publishData.isAvailable[index]}"
											isDisabled="${(publishData.isTracked[index]) or eachAssignmentSuccess}" onchange="selectCheckbox();" isVertical="true" />
									</bbNG:dataElement>
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName11}" label="${listElementLabel11}">
									<bbNG:dataElement isRequired="true" isVertical="true">
										<bbNG:checkboxElement name="isTracked" value="true" optionLabel="${chkYes}" isSelected="${publishData.isTracked[index]}"
											isDisabled="${(not publishData.isTracked[index])or eachAssignmentSuccess}" onchange="selectCheckbox();" isVertical="true" />
										<bbNG:checkboxElement name="isTracked" value="false" optionLabel="${chkNo}" isSelected="${not publishData.isTracked[index]}"
											isDisabled="${(publishData.isTracked[index]) or eachAssignmentSuccess}" onchange="selectCheckbox();" isVertical="true" />
									</bbNG:dataElement>
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName6}" label="${listElementLabel6}">
									<bbNG:checkboxElement name="createAnnouncement" value="true" optionLabel="${chkYes}" isSelected="${publishData.createAnnouncement[index]}"
										isDisabled="${(not publishData.createAnnouncement[index]) or eachAssignmentSuccess}" onchange="selectCheckbox();" isVertical="true" />
									<bbNG:checkboxElement name="createAnnouncement" value="false" optionLabel="${chkNo}" isSelected="${not publishData.createAnnouncement[index]}"
										isDisabled="${(publishData.createAnnouncement[index]) or eachAssignmentSuccess}" onchange="selectCheckbox();" isVertical="true" />
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName7}" label="${listElementLabel7}">
									<bbNG:textElement name="pointsPossible" isDisabled="${eachAssignmentSuccess}" value="${publishData.pointsPossible[index]}" isRequired="true"
										minLength="1" maxLength="3" size="4" type="float" helpText="${listElementDescription7}" />
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName8}" label="${listElementLabel8}">
									<bbNG:textElement name="startDate" isDisabled="${eachAssignmentSuccess}" helpText="${listElementDescription8}" isRequired="true"
										value="${publishData.startDate[index]}" maxLength="11" size="12" onclick="errorSelection();" />
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName9}" label="${listElementLabel9}">
									<bbNG:textElement name="endDate" isDisabled="${eachAssignmentSuccess}" helpText="${listElementDescription9}" isRequired="true"
										value="${publishData.endDate[index]}" maxLength="11" size="12" onclick="errorSelection();" />
								</bbNG:listElement>
								<bbNG:listElement name="${listElementName10}" label="${listElementLabel10}"> 
									<bbNG:textElement name="assignmentComments" isDisabled="${eachAssignmentSuccess}" value="${publishData.assignmentComments[index]}"
										isRequired="true" minLength="1" maxLength="25" size="25" helpText="${listElementDescription10}" />
								</bbNG:listElement>
							</bbNG:inventoryList>

						</bbNG:dataElement>
					</bbNG:step>
					<bbNG:stepSubmit cancelUrl="${beforePage}" hideNumber="true" />
				</bbNG:dataCollection>
			</bbNG:form>
		</c:when>
		<c:otherwise>
			<!-- Setting the redirection page to an attribute. -->
			<c:set var="redirectPage" value="${configUtil.redirectUrl }" scope="page" />

			<!-- Removing session and page scoped attributes. -->
			<c:remove var="webworkData" scope="session" />
			<c:remove var="publishData" scope="session" />
			<c:remove var="configUtil" scope="page" />
			<c:remove var="blackboardUtil" scope="session" />
			<c:remove var="webworkUtil" scope="session" />

			<c:redirect url="${pageScope.redirectPage }" />
		</c:otherwise>
	</c:choose>

	<!-- The javascript block to select the items. TODO jsfile  -->
	<bbNG:jsFile href="${blackboardUtil.buildingBlockURI }/JS/webwork.js" />
</bbNG:genericPage>
