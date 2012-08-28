<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true" errorPage="error/jasper.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="breadCrumbTitle1" key="BreadCrumb.Content" />
<fmt:message var="breadCrumbTitle2" key="BreadCrumb.Courses" />
<fmt:message var="breadCrumbTitle3" key="BreadCrumb.Sets" />
<fmt:message var="startPage" key="Page.Create" />
<fmt:message var="currentPage" key="Page.Sets" />
<fmt:message var="beforePage" key="Page.Courses" />
<fmt:message var="submitPage" key="Page.Assignment" />
<fmt:message var="formName" key="Form.Sets" />
<fmt:message var="pageHeaderInstructions" key="PageHeader.Sets" />
<fmt:message var="pageTitle" key="PageTitleBar.Sets" />
<fmt:message var="onSubmitFunction" key="OnSubmit.Sets" />
<fmt:message var="drawerTitle" key="DrawerTitle.Sets" />
<fmt:message var="drawerId" key="DrawerId.Sets" />
<fmt:message var="stepTitle" key="StepTitle.Sets" />
<fmt:message var="dataElementLabel" key="DataElementLabel.Sets" />
<fmt:message var="inventoryListId" key="InventoryListId.Sets" />
<fmt:message var="inventoryListVar" key="InventoryListVar.Sets" />
<fmt:message var="inventoryListDesc" key="InventoryListDescription.Sets" />
<fmt:message var="inventoryListEmpty" key="InventoryListEmpty.Sets" />
<fmt:message var="drawerColumn1" key="DrawerColumn.Sets[1]" />
<fmt:message var="drawerColumn2" key="DrawerColumn.Sets[2]" />
<fmt:message var="drawerColumn3" key="DrawerColumn.Sets[3]" />
<fmt:message var="drawerLabel1" key="DrawerColumnLabel.Sets[1]" />
<fmt:message var="drawerLabel2" key="DrawerColumnLabel.Sets[2]" />
<fmt:message var="drawerLabel3" key="DrawerColumnLabel.Sets[3]" />

<!-- Redirect to the create page if the utils doesn't exist. -->
<c:if test="${empty sessionScope.blackboardUtil }">
	<jsp:forward page="${startPage}" />
</c:if>

<!-- Importing Beans used in this page -->
<jsp:useBean id="blackboardUtil" scope="session" type="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkUtil" scope="session" type="edu.missouri.WebworkUtil" />
<jsp:useBean id="webworkData" scope="session" class="edu.missouri.WebworkData" />

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
					<jsp:setProperty name="webworkData" property="selectedWebworkCourses" value="${ sessionScope.webworkCourses }" />
				</c:when>
				<c:otherwise>
					<jsp:setProperty name="webworkData" property="*" />
				</c:otherwise>
			</c:choose>

			<!-- Initializing the new selected courses and sets. See the set method for details.  -->
			<jsp:setProperty name="webworkUtil" property="webworkCoursesSets" value="${webworkData.selectedCourses}" />
				
			<!-- For selected courses, capture the list of sets, and corresponding list of courses for each set. -->
			<jsp:setProperty name="webworkData" property="selectedCourses" value="${webworkUtil.selectedWebworkCourses}" />
			<jsp:setProperty property="listWebworkSetsForCourses" name="webworkData" value="${webworkUtil.webworkCoursesSets}" />
		</c:if>
	</c:otherwise>
</c:choose>

<c:set var="previousPage" value="${currentPage}" scope="session" />

<bbNG:genericPage ctxId="ctx">

	<!-- BreadCrumb to navigate within pages. -->
	<bbNG:breadcrumbBar environment="COURSE">
		<bbNG:breadcrumb title="${breadCrumbTitle1}" href="${blackboardUtil.courseContentsUrl }" />
		<bbNG:breadcrumb title="${breadCrumbTitle2}" href="${beforePage}" />
		<bbNG:breadcrumb title="${breadCrumbTitle3}" />
	</bbNG:breadcrumbBar>

	<bbNG:pageHeader instructions="${pageHeaderInstructions}">
		<bbNG:pageTitleBar title="${pageTitle }" />
	</bbNG:pageHeader>

	<!-- Drawer form is where submit button is clicked. Also the elements selected are noted here -->
	<bbNG:drawer onSubmit="${onSubmitFunction}" cancelUrl="${beforePage}" pickerSessionId="${drawerId}" title="${drawerTitle}" useWindowName="true">
		<bbNG:drawerColumn name="${drawerColumn1}" label="${drawerLabel1}" />
		<bbNG:drawerColumn name="${drawerColumn2}" label="${drawerLabel2}" />
		<bbNG:drawerColumn name="${drawerColumn3}" label="${drawerLabel3}" />
	</bbNG:drawer>

	<bbNG:form id="${formName}" method="post" action="${submitPage}">

		<bbNG:dataCollection hasRequiredFields="true" showSubmitButtons="false">

			<bbNG:step title="${stepTitle}">
				<bbNG:dataElement isRequired="true" label="${dataElementLabel}" isVertical="true">

					<bbNG:inventoryList listId="${inventoryListId}" className="java.lang.String" collection="${  webworkData.selectedCourses }" objectVar="eachCourse" 
						emptyMsg="${inventoryListEmpty}" description="${inventoryListDesc}" enableSelectEntireList="true" includePageParameters="true" shoppingCart="true" 
						showAll="true">
						
						<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />

						<bbNG:listCheckboxElement name="selectedWebworkSetsParam" showCheckbox="true" value="${index + offset}" />
						<bbNG:listElement label="${drawerLabel1}" name="${drawerColumn1}" isRowHeader="true" accessibilityLabelOnly="true">
							${index + offset}
						</bbNG:listElement>
						<bbNG:listElement label="${drawerLabel2}" name="${drawerColumn2}">
							${eachCourse}
						</bbNG:listElement>
						<bbNG:listElement label="${drawerLabel3}" name="${drawerColumn3}">
							${ webworkData.listWebworkSetsForCourses[index + offset] }
						</bbNG:listElement>

					</bbNG:inventoryList>

				</bbNG:dataElement>
			</bbNG:step>

		</bbNG:dataCollection>

	</bbNG:form>

	<!-- The javascript block to select the items.  -->
	<bbNG:jsFile href="${blackboardUtil.buildingBlockURI }/JS/webwork.js" />
</bbNG:genericPage>