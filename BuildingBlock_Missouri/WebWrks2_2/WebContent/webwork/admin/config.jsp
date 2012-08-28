<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	session="true" errorPage="error/jasper.jsp"%>

<!-- Tag library necessary to extract the info from the context -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Config File properties settings. Capturing the URL parameters -->
<jsp:useBean id="configUtil" class="edu.missouri.ConfigFile" scope="page">
	<jsp:setProperty name="configUtil" property="locationOfBean" value="admin/config.jsp" />
	<jsp:setProperty name="configUtil" property="requestUrl" value="${pageContext.request.requestURL }" />
	<jsp:setProperty name="configUtil" property="requestUri" value="${pageContext.request.requestURI }" />
</jsp:useBean>

<!-- Valid page url settings. -->
<c:set var="validPage" value="${configUtil.redirectUrl }" scope="session" />

<c:choose>

	<c:when test="${param.save != null}">
		<jsp:setProperty name="configUtil" property="*" />
		<jsp:setProperty name="configUtil" property="writeServerProperty" value="true" />
		<!-- ${configUtil.constantWebworkPluginsPage }" -->
		<c:redirect url="${configUtil.redirectUrl }" />
	</c:when>

	<c:otherwise>
		<bbNG:learningSystemPage authentication="Y" checkGuest="N" hideCourseMenu="false" title="Configuring Webwork Blakcboard plugin" ctxId="ctx">

			<bbNG:breadcrumbBar environment="SYS_ADMIN">
				<bbNG:breadcrumb href="${configUtil.constantWebworkPluginsPage }" title="Installed Tools" />
				<bbNG:breadcrumb title="Configure Webwork Assignment Tool" />
			</bbNG:breadcrumbBar>

			<bbNG:pageHeader>
				<bbNG:pageTitleBar title="Configuring Page for admin" />
				<!--iconUrl="/images/config.gif"   -->
			</bbNG:pageHeader>

			<bbNG:form id="configForm" action="config.jsp?save=true" method="post">
				<bbNG:dataCollection>

					<bbNG:step title="Web Server Locations" instructions="Enter the location of the webwork webserver to hook up.">
						<bbNG:dataElement label="Enter webserver location." labelFor="webServerLocation" isRequired="true">
							<bbNG:textElement name="webServerLocation" isVertical="true" isRequired="true" value="${configUtil.webServerLocation }" size="100" 
								onchange="copyVariable();" />
						</bbNG:dataElement>

						<bbNG:dataElement label="Enter webserver RPC location." labelFor="webServerRpcLocation" isRequired="true">
							<bbNG:textElement name="webServerRpcLocation" isVertical="true" isRequired="true" value="${configUtil.webServerRpcLocation }" size="100" />
						</bbNG:dataElement>

						<bbNG:dataElement label="Enter webserver Site URL Location." labelFor="webServerSiteUrl" isRequired="true">
							<bbNG:textElement name="webServerSiteUrl" isVertical="true" isRequired="true" value="${configUtil.webServerSiteUrl }" size="100"
								helpText="(This is where the assignment content will be accessed from.)" />
						</bbNG:dataElement>

					</bbNG:step>

					<bbNG:step title="Permission & Keys" instructions="The Webservice SOAP authorization key, Class Permission, user status.">
						<bbNG:dataElement label="Enter the SOAP Authorization Key." labelFor="soapAuthKey" isRequired="true">
							<bbNG:textElement name="soapAuthKey" isVertical="true" minLength="1" isPassword="false" isRequired="true" value="${configUtil.soapAuthKey }" size="25" />
						</bbNG:dataElement>

						<bbNG:dataElement label="Enter the SOAP Classes Permission." labelFor="soapClassesPermission" isRequired="true">
							<bbNG:textElement name="soapClassesPermission" isVertical="true" minLength="1" isPassword="true" isRequired="true" 
								value="${configUtil.soapClassesPermission }" size="10" />
						</bbNG:dataElement>

						<bbNG:dataElement label="Enter the SOAP User Status." labelFor="soapUserStatus" isRequired="true">
							<bbNG:textElement name="soapUserStatus" isVertical="true" minLength="1" isPassword="true" isRequired="true" value="${configUtil.soapUserStatus }" 
								size="10" />
						</bbNG:dataElement>

					</bbNG:step>

					<!-- Added for webwork data security.  -->
					<bbNG:step title="Data Security" instructions="Security type to be used for data transmission to webwork.">
						<bbNG:dataElement label="Enter the SOAP Authorization Key." labelFor="soapAuthKey" isRequired="true">
							<!-- SHA Hash -->
							<bbNG:radioElement name="webworkDataSecurity" value="Hash" helpText="SHA Hash encryption is applied for data transmission." 
								isSelected="${ (configUtil.webworkDataSecurity eq 'Hash')? true : false}">
								
								Hash
							</bbNG:radioElement>

							<!-- Default: none -->
							<bbNG:radioElement name="webworkDataSecurity" value="None" helpText="Data not encrypted over transmission."
								isSelected="${ (configUtil.webworkDataSecurity eq 'None')? true : false}">
								
								None
							</bbNG:radioElement>

						</bbNG:dataElement>

					</bbNG:step>
					<!-- End of added section. -->

					<bbNG:step title="Optional Elements" instructions="The other optional elements for Webwork plugin.">
						<bbNG:dataElement label="Webwork Courses Search Method: " labelFor="webworkCoursesSearchMethod" isRequired="false">
							<bbNG:radioElement name="webworkCoursesSearchMethod" value="Basic" isVertical="true" helpText="Searches for users in the webwork admin course."
								onClick="displayOtherElements();" isSelected="${ (configUtil.webworkCoursesSearchMethod eq 'Basic')? true : false}">
								
								Basic  
								<bbNG:textElement name="webworkSearchCourse" minLength="1" value="${configUtil.webworkSearchCourse }" size="10" isDisabled="true"
									helpText="Enter the search course in the webwork database." />
							</bbNG:radioElement>
							<bbNG:radioElement name="webworkCoursesSearchMethod" value="Advanced" isVertical="true" helpText="Searches for permission level of the each course."
								onClick="displayOtherElements();" isSelected="${ (configUtil.webworkCoursesSearchMethod eq 'Advanced')? true : false}">
								
								Advanced  
								<bbNG:textElement name="webworkInstructorPermissionLevel" minLength="1" maxLength="2" value="${configUtil.webworkInstructorPermissionLevel }"
									size="5" isDisabled="true" helpText="Enter the permission level of the instructor." />
							</bbNG:radioElement>

							<bbNG:radioElement name="webworkCoursesSearchMethod" value="None" isVertical="true" 
								helpText="List all available webwork courses. (Must be used with care, instructors can see other instructors webwork courses & assignments)"
								onClick="displayOtherElements();" isSelected="${ (configUtil.webworkCoursesSearchMethod eq 'None')? true : false}">
								
								None
							</bbNG:radioElement>
						</bbNG:dataElement>

						<bbNG:dataElement label="Guest Users Content Visibility: " labelFor="allowGuestViewers" isRequired="false">
							<bbNG:radioElement name="allowGuestViewers" value="true" isSelected="${ (configUtil.allowGuestViewers eq true)? true : false}">
								True
							</bbNG:radioElement>
							<bbNG:radioElement name="allowGuestViewers" value="false" isSelected="${ (configUtil.allowGuestViewers eq false)? true : false}">
								False
							</bbNG:radioElement>
						</bbNG:dataElement>

						<bbNG:dataElement label="Webwork Assignment Point Factor: " labelFor="webworkAssignmentPointFactor" isRequired="false">
							<bbNG:textElement name="webworkAssignmentPointFactor" minLength="1" maxLength="5" value="${configUtil.webworkAssignmentPointFactor}" size="5"
								type="integer" isVertical="true" helpText="Enter the Assignment Point Factor, ie. WebworkProblems weightage." />
						</bbNG:dataElement>
					</bbNG:step>

					<bbNG:stepSubmit cancelUrl="${configUtil.constantWebworkPluginsPage }">
					</bbNG:stepSubmit>

				</bbNG:dataCollection>
			</bbNG:form>

			<bbNG:jsFile href="${configUtil.buildingBlockURI }/JS/webwork.js" />

		</bbNG:learningSystemPage>
	</c:otherwise>
</c:choose>
