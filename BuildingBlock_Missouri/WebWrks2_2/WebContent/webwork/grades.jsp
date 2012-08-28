<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="error/jasper.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Tag libraries -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Importing Messages Bundle -->
<fmt:message var="jsPreviousPage" key="JS.PreviousPage" />
<fmt:message var="pageHeaderInstructions" key="PageHeader.Grades" />
<fmt:message var="pageTitle" key="PageTitleBar.Grades" />

<!--  Use Bean declarations. -->
<!-- Grades.jsp page useBeans has only types. It can thus only be called from loginwebwork.jsp and not any other way. -->
<jsp:useBean id="assignmentInfo" type="edu.missouri.AssignmentInfo" scope="session" />
<jsp:useBean id="blackboardAccessorUtil" scope="session" type="edu.missouri.BlackboardUtil" />
<jsp:useBean id="webworkAccessorUtil" scope="session" type="edu.missouri.WebworkUtil" />

<bbNG:learningSystemPage ctxId="ctx" authentication="Y" disableEditToggle="false" title="Instructor Grades" hideCourseMenu="false">

	<!-- Page Title and Header information. -->
	<bbNG:pageHeader instructions="${pageHeaderInstructions}">
		<bbNG:pageTitleBar title="${pageTitle }" />
	</bbNG:pageHeader>

	<!-- Setting properties for the student grades to be written. -->
	<jsp:setProperty name="blackboardAccessorUtil" property="assignmentInfo" value="${assignmentInfo}" />
	<jsp:setProperty name="blackboardAccessorUtil" property="studentsGrades" value="true" />

	<!-- Grade information list presented to the instructor / TA. -->
	<bbNG:inventoryList className="java.lang.String" objectVar="eachStudent" renderAjax="true" initialSortBy="ASCENDING" collection="${blackboardAccessorUtil.enrolledStudentList}" 
		description="Grade Information for the assignment." initialSortCol="" listId="studentGradesList" showDescriptionAsHeader="true" showAll="true" displayPagingControls="false" 
		emptyMsg="No Students are enrolled in the course.">

		<bbNG:listElement label="Name" name="studentName" isRowHeader="true">${eachStudent}</bbNG:listElement>
		<bbNG:listElement label="Grades" name="studentGrades">
			${blackboardAccessorUtil.studentsGradesScores[index] }
		</bbNG:listElement>

	</bbNG:inventoryList>

	<!-- Ok button to redirect to the previous page. -->
	<bbNG:okButton url="${jsPreviousPage}" />
</bbNG:learningSystemPage>