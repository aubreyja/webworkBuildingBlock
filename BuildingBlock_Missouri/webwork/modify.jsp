<%@ page autoFlush="true" session="true"%>

<!-- Tag library necessary to extract the info from the context -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<bbNG:genericPage ctxId="ctx" title="Modify Webwork content">

	<!--  Setting the blackboard utility class to session object for use in the building block -->
	<jsp:useBean id="blackboardUtil" scope="page"
		class="edu.missouri.BlackboardUtil">
		<jsp:setProperty property="blackboardUser" name="blackboardUtil"
			value="${ ctx.user }" />
		<jsp:setProperty property="courseIdentity" name="blackboardUtil"
			value="${ ctx.course.id }" />
		<jsp:setProperty property="courseNumber" name="blackboardUtil"
			param="course_id" />
		<jsp:setProperty property="contentNumber" name="blackboardUtil"
			param="content_id" />
	</jsp:useBean>
	<jsp:useBean id="contentModify" scope="page"
		class="blackboard.data.content.Content">
		<jsp:setProperty name="contentModify"
			property="${blackboardUtil.contentModify }" />
	</jsp:useBean>
	<c:set var="closeBrace" value="\">" />
	<c:set var="title" value="${contentModify.title }" />
	<c:set var="assignmentName" value="" />

	<bbNG:pageHeader instructions="Change any information you want to edit">
		<bbNG:pageTitleBar title="Modify Content Information" />
	</bbNG:pageHeader>
	<bbNG:form id="mashupForm" action="success.jsp">
		<input type="hidden" name="embedHtml" id="embedHtml" value="success" />
		<bbNG:dataCollection>
			<bbNG:step title="Mandatory info">
				<bbNG:dataElement label="Enter Assignment Name:" labelFor="name">
					<bbNG:textElement name="name" value="${ fn:substringAfter(title,closeBrace) }"
						isRequired="true" minLength="3" maxLength="25" size="25"
						helpText="Enter the Assignment Name for the selected set name and course name" />
				</bbNG:dataElement>
				<bbNG:dataElement label="Enter Assignment Name:" labelFor="name">
					<bbNG:textElement name="type" value="${publishData.type[index]}"
						isRequired="true" minLength="3" maxLength="25" size="25"
						helpText="Default." />
				</bbNG:dataElement>
			</bbNG:step>
			<bbNG:step title="Extra info">
				<bbNG:dataElement label="Other details:" isVertical="true">
					<bbNG:textElement name="courseName"
						value="${publishData.courseName[index]}" displayOnly="true" />
					<bbNG:textElement name="setName"
						value="${publishData.setName[index]}" displayOnly="true" />
					<bbNG:elementInstructions
						text="Enter all the mandatory information." />
				</bbNG:dataElement>
			</bbNG:step>
			<bbNG:step title="Attributes info">
				<bbNG:dataElement label="Text to translate" labelFor="toTranslate">
					<bbNG:textElement name="name" value="${publishData.setName[index]}"
						isRequired="true" minLength="3" maxLength="25" size="25"
						helpText="Enter the Assignment Name for the selected set name and course name" />

					<bbNG:elementInstructions
						text="Enter all the mandatory information." />
				</bbNG:dataElement>
			</bbNG:step>

			<bbNG:stepSubmit cancelOnClick="javascript:history.go(-1);" />
		</bbNG:dataCollection>
	</bbNG:form>

</bbNG:genericPage>