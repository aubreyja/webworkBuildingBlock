<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>

<!-- Tag library necessary to extract the info from the context -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

<!-- Config File properties settings. Capturing the URL parameters -->
<jsp:useBean id="configUtil" class="edu.missouri.ConfigFile"
	scope="page">
	<jsp:setProperty name="configUtil" property="locationOfBean"
		value="admin/config.jsp" />
	<jsp:setProperty name="configUtil" property="requestUrl"
		value="${pageContext.request.requestURL }" />
	<jsp:setProperty name="configUtil" property="requestUri"
		value="${pageContext.request.requestURI }" />
</jsp:useBean>

<!-- Valid page url settings. -->
<c:set var="validPage" value="${configUtil.redirectUrl }"
	scope="session" />


<c:choose>

	<c:when test="${param.save != null}">
		<jsp:setProperty name="configUtil" property="*" />
		<jsp:setProperty name="configUtil" property="writeServerProperty"
			value="true" />
		<!-- ${configUtil.constantWebworkPluginsPage }" -->
		<c:redirect url="${configUtil.redirectUrl }" />
	</c:when>

	<c:otherwise>
		<bbNG:learningSystemPage authentication="Y" checkGuest="N"
			hideCourseMenu="false" title="Configuring Webwork Blakcboard plugin"
			ctxId="ctx">

			<bbNG:breadcrumbBar environment="SYS_ADMIN">
				<bbNG:breadcrumb href="${configUtil.constantWebworkPluginsPage }"
					title="Installed Tools" />
				<bbNG:breadcrumb title="Configure Webwork Assignment Tool" />
			</bbNG:breadcrumbBar>

			<bbNG:pageHeader>
				<bbNG:pageTitleBar title="Configuring Page for admin" />
				<!--iconUrl="/images/config.gif"   -->
			</bbNG:pageHeader>

			<bbNG:form id="configForm" action="config.jsp?save=true"
				method="post">
				<bbNG:dataCollection>

					<bbNG:step title="Web Server Locations"
						instructions="Enter the location of the webserver to hook up.">
						<bbNG:dataElement label="Enter webserver location."
							labelFor="webServerLocation">
							<bbNG:textElement name="webServerLocation" isVertical="true"
								isRequired="true" value="${configUtil.webServerLocation }"
								size="100" onchange="copyVariable();" />
						</bbNG:dataElement>

						<bbNG:dataElement label="Enter webserver rpc location."
							labelFor="webServerRpcLocation">

							<bbNG:radioElement name="secured" value="secured"></bbNG:radioElement>

							<c:set var="vadlidateRpcLocation">
								{'copy_variable':'copyVariable();'}
							</c:set>

							<bbNG:textElement name="webServerRpcLocation" isVertical="true"
								isRequired="true" value="${configUtil.webServerRpcLocation }"
								size="100" validation="${vadlidateRpcLocation }" />
						</bbNG:dataElement>

						<bbNG:dataElement
							label="Enter webserver Site URL Location. (This is where the content will be accessed from)"
							labelFor="webServerSiteUrl">
							<bbNG:textElement name="webServerSiteUrl" isVertical="true"
								isRequired="true" value="${configUtil.webServerSiteUrl }"
								size="100" />
						</bbNG:dataElement>

					</bbNG:step>

					<bbNG:step title="Authorization Key"
						instructions="The SOAP authorization key to connect to webservice.">
						<bbNG:dataElement label="Type a proper webserver SOAP Key."
							labelFor="soapAuthKey">
							<bbNG:textElement name="soapAuthKey" isVertical="true"
								isRequired="true" value="${configUtil.soapAuthKey }" size="25" />
						</bbNG:dataElement>

					</bbNG:step>

					<bbNG:step title="Optional Elements"
						instructions="The other optional elements for Webwork plugin.">

						<bbNG:dataElement label="Assignment Guest Users Visibility: "
							labelFor="allowGuestViewers" isRequired="false">
							<bbNG:radioElement name="allowGuestViewers" value="true"
								isSelected="${ (configUtil.allowGuestViewers eq true)? true : false}">
								True
							</bbNG:radioElement>
							<bbNG:radioElement name="allowGuestViewers" value="false"
								isSelected="${ (configUtil.allowGuestViewers eq false)? true : false}">
								False
							</bbNG:radioElement>
						</bbNG:dataElement>

					</bbNG:step>

					<bbNG:stepSubmit
						cancelUrl="${configUtil.constantWebworkPluginsPage }">
					</bbNG:stepSubmit>

				</bbNG:dataCollection>
			</bbNG:form>

			<bbNG:cssBlock>
				<script type="text/javascript" src="${configUtil.buildingBlockURI }/JS/jsuri.js"></script>
			</bbNG:cssBlock>
			<bbNG:jsFile href="${configUtil.buildingBlockURI }/JS/jsuri.js"/>
			<bbNG:jsBlock>
				<script type="text/javascript">
					function copyVariable() {
						document.forms["configForm"].soapAuthKey.value = new jsUri(
								'http://www.test.com').setHost('www.yahoo.com')
								.setProtocol('https');
						document.forms["configForm"].webServerSiteUrl.value = document.forms["configForm"].webServerLocation.value;
					}
				</script>
			</bbNG:jsBlock>

		</bbNG:learningSystemPage>
	</c:otherwise>
</c:choose>
