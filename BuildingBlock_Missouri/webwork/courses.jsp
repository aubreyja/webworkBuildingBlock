<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="breadCrumbTitle1" key="BreadCrumb.Content" />
<fmt:message var="breadCrumbTitle2" key="BreadCrumb.Courses" />
<fmt:message var="startPage" key="Page.Create" />
<fmt:message var="currentPage" key="Page.Courses" />
<fmt:message var="submitPage" key="Page.Sets" />
<fmt:message var="formName" key="Form.Courses" />
<fmt:message var="pageHeaderInstructions" key="PageHeader.Courses" />
<fmt:message var="pageTitle" key="PageTitleBar.Courses" />
<fmt:message var="onSubmitFunction" key="OnSubmit.Courses" />
<fmt:message var="drawerTitle" key="DrawerTitle.Courses" />
<fmt:message var="drawerId" key="DrawerId.Courses" />
<fmt:message var="stepTitle" key="StepTitle.Courses" />
<fmt:message var="dataElementLabel" key="DataElementLabel.Courses" />
<fmt:message var="inventoryListId" key="InventoryListId.Courses" />
<fmt:message var="inventoryListVar" key="InventoryListVar.Courses" />
<fmt:message var="inventoryListDesc" key="InventoryListDescription.Sets" />
<fmt:message var="inventoryListEmpty" key="InventoryListEmpty.Courses" />
<fmt:message var="drawerColumn1" key="DrawerColumn.Courses[1]" />
<fmt:message var="drawerColumn2" key="DrawerColumn.Courses[2]" />
<fmt:message var="drawerLabel1" key="DrawerColumnLabel.Courses[1]" />
<fmt:message var="drawerLabel2" key="DrawerColumnLabel.Courses[2]" />

<!-- Redirect to the create page if the utils doesn't exist. -->
<c:if test="${empty sessionScope.blackboardUtil }">
	<jsp:forward page="${startPage}" />
</c:if>

<!-- Importing Beans used in this page -->
<jsp:useBean id="blackboardUtil" scope="session"
	type="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkUtil" scope="session"
	type="edu.missouri.WebworkUtil" />

<c:set var="previousPage" value="${currentPage}" scope="session" />
<c:set var="validPage" value="${blackboardUtil.courseContentsUrl }"
	scope="session" />

<bbNG:genericPage ctxId="ctx">

	<!-- BreadCrumb to navigate within pages. -->
	<bbNG:breadcrumbBar environment="COURSE" skipLastContentCrumb="true">
		<bbNG:breadcrumb title="${breadCrumbTitle1}"
			href="${blackboardUtil.courseContentsUrl}" />
		<bbNG:breadcrumb title="${breadCrumbTitle2}" />
	</bbNG:breadcrumbBar>

	<bbNG:pageHeader instructions="${pageHeaderInstructions}">
		<bbNG:pageTitleBar title="${pageTitle }" />
	</bbNG:pageHeader>

	<!-- Drawer form where submit occurs. Also the elements selected are noted here -->
	<bbNG:drawer onSubmit="${onSubmitFunction}"
		cancelUrl="${blackboardUtil.courseContentsUrl }"
		pickerSessionId="${drawerId}" title="${drawerTitle}"
		useWindowName="true">
		<bbNG:drawerColumn name="${drawerColumn1}" label="${drawerLabel1}" />
		<bbNG:drawerColumn name="${drawerColumn2}" label="${drawerLabel2}" />
	</bbNG:drawer>

	<!-- The form where the items are listed. -->
	<bbNG:form id="${formName}" method="post" action="${submitPage}">
		<bbNG:dataCollection hasRequiredFields="true"
			showSubmitButtons="false">
			<bbNG:step title="${stepTitle}">
				<bbNG:dataElement isRequired="true" label="${dataElementLabel}"
					isVertical="true">
					<bbNG:inventoryList listId="${inventoryListId}"
						className="java.lang.String"
						collection="${ webworkUtil.webworkCoursesList }"
						objectVar="eachCourse" emptyMsg="${inventoryListEmpty}"
						description="${inventoryListDesc}" enableSelectEntireList="true"
						includePageParameters="true" shoppingCart="true"
						initialSortCol="${drawerColumn1}" renderAjax="true" >
						<bbNG:listOptions allowShowAll="false" allowEditPaging="false" />

						<bbNG:listCheckboxElement name="selectedWebworkCoursesParam"
							showCheckbox="true" value="${ eachCourse }" />
						<bbNG:listElement label="${drawerLabel1}" name="${drawerColumn1}"
							isRowHeader="true">${eachCourse}</bbNG:listElement>
						<bbNG:listElement label="${drawerLabel2}" name="${drawerColumn2}">
							To be implemented
						</bbNG:listElement>

					</bbNG:inventoryList>
				</bbNG:dataElement>
			</bbNG:step>
		</bbNG:dataCollection>
	</bbNG:form>

	<!-- The javascript block to select the items. TODO jsfile  -->
	<bbNG:jsFile href="${blackboardUtil.buildingBlockURI }/JS/webwork.js" />

</bbNG:genericPage>