<%@ page autoFlush="true" session="true"%>

<!-- Tag library necessary to extract the info from the context -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<c:out value="User in enrollment: ${ blackboardUtil.userInEnrollment }" />

<bbNG:inventoryList className="blackboard.data.user.User"
	collection="${  blackboardUtil.enrolledUserList }" objectVar="eachUser">
	<bbNG:listElement label="Name" name="UserName" isRowHeader="true">${eachUser.userName}</bbNG:listElement>
	<bbNG:listElement label="Family Name" name="FamilyName">${eachUser.givenName}</bbNG:listElement>
</bbNG:inventoryList>

<jsp:getProperty property="buildingBlockURI" name="blackboardUtil" />
<jsp:getProperty property="courseNumber" name="blackboardUtil" />

<bbNG:dataElement label="Text to translate" labelFor="toTranslate">
	<bbNG:textbox name="toTranslate" isSpellcheckOnly="true" maxLength="25"
		minLength="5" rows="2" text="${ blackboardUtil.userInEnrollment }" />
	<c:forEach var="loopUsers" items="${ blackboardUtil.enrolledUserList }">

		<c:out value="${ loopUsers.userName }" />

	</c:forEach>
	<bbNG:elementInstructions
		text="Enter the text that will be translated into the specified language." />
</bbNG:dataElement>

		<c:forEach var='parameter' items='${paramValues}'>
			<ul>
				<li>
					<b>
						<c:out value='${parameter.key}' />
					</b>
					:
				</li>

				<c:forEach var='value' items='${parameter.value}'>
					<c:out value='${value}' />
				</c:forEach>
				
			</ul>
		</c:forEach>
		
			
		
		<!-- Testing purpose -->
<jsp:getProperty property="userInEnrollment" name="blackboardUtil" />
<c:forEach items="${blackboardUtil.enrolledUserList }" var="eachUser">
	<c:out value="${eachUser }" />
	<br/>
</c:forEach>
<c:out value="${blackboardUtil.blackboardUser }" />


<c:set scope="page" var="loopVar" value="0"></c:set>

:: All Attributes :: 

	Page Scope Attributes
      <c:forEach items='${pageScope}' var='p'>
         <ul>
            <li>Parameter Name: <c:out value='${p.key}'/></li>
            <li>Parameter Value: <c:out value='${p.value}'/></li>
         </ul>
      </c:forEach>
      	Session Scope Attributes
      <c:forEach items='${sessionScope}' var='s'>
         <ul>
            <li>Parameter Name: <c:out value='${s.key}'/></li>
            <li>Parameter Value: <c:out value='${s.value}'/></li>
         </ul>
      </c:forEach>
      	Request Scope Attributes
      <c:forEach items='${requestScope}' var='r'>
         <ul>
            <li>Parameter Name: <c:out value='${r.key}'/></li>
            <li>Parameter Value: <c:out value='${r.value}'/></li>
         </ul>
      </c:forEach>
      	Application Scope Attributes
      <c:forEach items='${applicationScope}' var='a'>
         <ul>
            <li>Parameter Name: <c:out value='${a.key}'/></li>
            <li>Parameter Value: <c:out value='${a.value}'/></li>
         </ul>
      </c:forEach>




drawer.view.cancel(null, null)
drawer.view.refreshAndSubmit()