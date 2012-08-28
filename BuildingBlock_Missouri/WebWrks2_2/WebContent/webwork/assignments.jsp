<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error/jasper.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="breadCrumbTitle1" key="BreadCrumb.Content" />
<fmt:message var="breadCrumbTitle2" key="BreadCrumb.Courses" />
<fmt:message var="breadCrumbTitle3" key="BreadCrumb.Sets" />
<fmt:message var="breadCrumbTitle4" key="BreadCrumb.Assignment" />
<fmt:message var="startPage" key="Page.Create" />
<fmt:message var="beforePage2" key="Page.Courses" />
<fmt:message var="currentPage" key="Page.Assignment" />
<fmt:message var="beforePage" key="Page.Sets" />
<fmt:message var="submitPage" key="Page.Publish" />
<fmt:message var="formName" key="Form.Assignment" />
<fmt:message var="pageHeaderInstructions" key="PageHeader.Assignment" />
<fmt:message var="pageTitle" key="PageTitleBar.Assignment" />
<fmt:message var="stepTitle" key="StepTitle.Assignment" />
<fmt:message var="dataElementLabel" key="DataElementLabel.Assignment" />
<fmt:message var="inventoryListId" key="InventoryListId.Assignment" />
<fmt:message var="inventoryListVar" key="InventoryListVar.Assignment" />
<fmt:message var="inventoryListDesc" key="InventoryListDescription.Assignment" />
<fmt:message var="inventoryListEmpty" key="InventoryListEmpty.Assignment" />
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
<fmt:message var="listElementDescription7" key="ListElemenDescription.Assignment[7]" />
<fmt:message var="listElementDescription9" key="ListElemenDescription.Assignment[9]" />
<fmt:message var="listElementDescription8" key="ListElemenDescription.Assignment[8]" />
<fmt:message var="listElementDescription10" key="ListElemenDescription.Assignment[10]" />
<fmt:message var="listElementDescription11" key="ListElemenDescription.Assignment[11]" />
<fmt:message var="chkYes" key="Checkbox.Label.Yes" />
<fmt:message var="chkNo" key="Checkbox.Label.No" />
<fmt:message var="datePattern" key="Date.Pattern" />

<!-- Redirect to the create page if the utils doesn't exist. -->
<c:if test="${empty sessionScope.blackboardUtil }">
	<jsp:forward page="${startPage}" />
</c:if>

<!-- Importing Beans used in this page -->
<jsp:useBean id="blackboardUtil" scope="session" type="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkUtil" scope="session" type="edu.missouri.WebworkUtil" />
<jsp:useBean id="webworkData" scope="session" class="edu.missouri.WebworkData" />
<jsp:useBean id="dateInfo" scope="page" class="java.util.Date" />

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
					<jsp:setProperty name="webworkData" property="selectedWebworkSetsParam" value="${ webworkData.listWebworkSetsForCourses }" />
					<!--  TODO do for entire list selection.  -->
				</c:when>
				<c:otherwise>
					<jsp:setProperty name="webworkData" property="*" />
					<jsp:setProperty name="webworkData" property="captureWebworkData" value="true" />
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:otherwise>
</c:choose>

<c:set var="previousPage" value="${currentPage}" scope="session" />

<!-- Testing purpose -->
<fmt:formatDate value="${dateInfo}" pattern="${datePattern}" var="formattedDate" scope="page" />

<bbNG:genericPage ctxId="ctx">

	<!-- BreadCrumb to navigate within pages. -->
	<bbNG:breadcrumbBar environment="COURSE">
		<bbNG:breadcrumb title="${breadCrumbTitle1}" href="${blackboardUtil.courseContentsUrl }" />
		<bbNG:breadcrumb title="${breadCrumbTitle2}" href="${beforePage}" />
		<bbNG:breadcrumb title="${breadCrumbTitle3}" href="${beforePage2}" />
		<bbNG:breadcrumb title="${breadCrumbTitle4}" />
	</bbNG:breadcrumbBar>

	<bbNG:pageHeader instructions="${pageHeaderInstructions}">
		<bbNG:pageTitleBar title="${pageTitle }" />
	</bbNG:pageHeader>

	<bbNG:form id="${formName}" method="post" action="${submitPage }" onsubmit="return validateForm();">
		<bbNG:dataCollection hasRequiredFields="true">

			<bbNG:step title="${stepTitle}">
				<bbNG:dataElement isRequired="true" label="${dataElementLabel}" isVertical="true">
					<bbNG:inventoryList listId="${inventoryListId}" disableInteractivity="true" className="java.lang.String" collection="${  webworkData.selectedWebworkSets }"
						objectVar="eachAssignment" emptyMsg="${inventoryListEmpty}" description="${inventoryListDesc}" includePageParameters="true" showAll="true">
						
						<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />
						<bbNG:listElement name="${listElementName1}" label="${listElementLabel1}" isRowHeader="true">
							<bbNG:textElement name="name" value="${webworkData.listWebworkSetsForCourses[eachAssignment] }" isRequired="true" minLength="3" maxLength="25" size="25"
								helpText="${listElementDescription1}" />
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName2}" label="${listElementLabel2}">
							<bbNG:textElement name="courseNames" value="${webworkData.selectedCourses[eachAssignment]}" displayOnly="true" />
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName3}" label="${listElementLabel3}">
							<bbNG:textElement name="setNames" value="${webworkData.listWebworkSetsForCourses[eachAssignment] }" displayOnly="true" />
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName4}" label="${listElementLabel4}">
							<bbNG:textElement name="type" value="webwork" isRequired="true" minLength="3" maxLength="12" size="8" helpText="${listElementDescription4}" />
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName5}" label="${listElementLabel5}">
							<bbNG:dataElement isRequired="true" isVertical="true">
								<bbNG:checkboxElement name="isAvailable" value="true" optionLabel="${chkYes}" isSelected="true" onchange="selectCheckbox();" isVertical="true" />
								<bbNG:checkboxElement name="isAvailable" value="false" optionLabel="${chkNo}" isSelected="false" isDisabled="true" onchange="selectCheckbox();" 
									isVertical="true" />
							</bbNG:dataElement>
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName11}" label="${listElementLabel11}">
							<bbNG:dataElement isRequired="true" isVertical="true">
								<bbNG:checkboxElement name="isTracked" value="true" optionLabel="${chkYes}" isSelected="true" onchange="selectCheckbox();" isVertical="true" />
								<bbNG:checkboxElement name="isTracked" value="false" optionLabel="${chkNo}" isSelected="false" isDisabled="true" onchange="selectCheckbox();" 
									isVertical="true" />
							</bbNG:dataElement>
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName6}" label="${listElementLabel6}">
							<bbNG:checkboxElement name="createAnnouncement" value="true" optionLabel="${chkYes}" isSelected="true" onchange="selectCheckbox();" isVertical="true" />
							<bbNG:checkboxElement name="createAnnouncement" value="false" optionLabel="${chkNo}" isSelected="false" isDisabled="true" onchange="selectCheckbox();" 
								isVertical="true" />
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName7}" label="${listElementLabel7}"> 
							<bbNG:textElement name="pointsPossible" value="${webworkData.assignmentPoints[index] }" isRequired="true" minLength="1" maxLength="3" size="4" 
								isDisabled="true" type="integer" helpText="${listElementDescription7}" />
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName8}" label="${listElementLabel8}">
							<bbNG:textElement name="startDate" helpText="${listElementDescription8}" isRequired="true" value="${webworkData.selectedWebworkSetsOpenDate[index]}"
								maxLength="21" size="21" onclick="errorSelection();" />
						</bbNG:listElement>
						<bbNG:listElement name="${listElementName9}" label="${listElementLabel9}">
							<bbNG:textElement name="endDate" helpText="${listElementDescription9}" isRequired="true" value="${webworkData.selectedWebworkSetsDueDate[index]}"
								maxLength="21" size="21" onclick="errorSelection();" />
						</bbNG:listElement>
						<!-- Place for partial credit changes. TODO after function enabled in webwork. -->
						<%-- <bbNG:listElement name="partialCreditCheckList"
							label="Partial Credit">
							<bbNG:dataElement isRequired="true" isVertical="true">
								<bbNG:checkboxElement name="enablePartial" value="${index}"
									optionLabel="${chkYes}" isSelected="false" isVertical="true"
									onchange="changePartial();" />
								<bbNG:textElement name="partialPercent" value="50" type="integer"
									isDisabled="true" isVertical="false" size="3" minLength="1"
									maxLength="3" />% <!-- To display % next to text box. -->
							</bbNG:dataElement>
						</bbNG:listElement>
						<bbNG:listElement name="partialEndDateList"
							label="Partial Due Date">
							<bbNG:textElement name="partialDate" isRequired="false"
								isVertical="true" maxLength="11" size="12" isDisabled="true"
								onclick="errorSelection();" />
						</bbNG:listElement> --%>
						<bbNG:listElement name="${listElementName10}" label="${listElementLabel10}">
							<bbNG:textElement name="assignmentComments" value="Webwork Set for ${webworkData.listWebworkSetsForCourses[eachAssignment] }" isRequired="true" 
								minLength="1" maxLength="30" size="25" helpText="${listElementDescription10}" />
						</bbNG:listElement>

					</bbNG:inventoryList>

				</bbNG:dataElement>
			</bbNG:step>
			<bbNG:stepSubmit cancelUrl="${beforePage}" />
		</bbNG:dataCollection>
	</bbNG:form>

	<!-- The javascript block to select the items. TODO jsfile  -->
	<bbNG:jsFile href="${blackboardUtil.buildingBlockURI }/JS/webwork.js" />

</bbNG:genericPage>