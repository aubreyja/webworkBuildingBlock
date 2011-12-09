/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.io.*;
import java.util.Properties;

import blackboard.platform.servlet.InlineReceiptUtil;
import blackboard.platform.log.LogServiceFactory;
import blackboard.platform.plugin.PlugInException;
import blackboard.platform.plugin.PlugInUtil;

/**
 * @author Srinivasan Devanathan
 * @category ConfigFile Class - Utility class / Bean class which writes the
 *           settings information to config.properties file. Stores all
 *           necessary information required by the building block.
 * @version 2.0
 * 
 */
public class ConfigFile extends BuildingBlockMethods {

	/**
	 * The file to which the properties should be written.
	 */
	private static File configFile;

	/**
	 * Config prop - Site URL to be accessed to do webwork assignment.
	 */
	private String webServerSiteUrl = Messages.getString("Default.Empty");
	/**
	 * Config prop - Site server location to access webwork web-service.
	 */
	private String webServerLocation = Messages.getString("Default.Empty");
	/**
	 * Config prop - Webwork webservice RPC location url.
	 */
	private String webServerRpcLocation = Messages.getString("Default.Empty");
	/**
	 * Config Prop - SOAP Authentication Key.
	 */
	private String soapAuthKey = Messages.getString("Default.Empty");
	/**
	 * Config Prop - SOAP Classes Permission to be created.
	 */
	private String soapClassesPermission = Messages.getString("Default.Empty");
	/**
	 * Config Prop - SOAP User status which should be written to webwork.
	 */
	private String soapUserStatus = Messages.getString("Default.Empty");
	/**
	 * Config Prop - Webwork Work search course - to search for courses
	 * available for a user(instructor / TA)
	 */
	private String webworkSearchCourse = Messages.getString("Default.Empty");
	/**
	 * Config Prop - Webwork Instructor permission level on each course.
	 */
	private int webworkInstructorPermissionLevel = constantWebworkInstructorPermissionLevel;
	/**
	 * Config Prop - Webwork assignment point factor - default of 1.
	 */
	private int webworkAssignmentPointFactor = constantWebworkAssignmentPointFactor;
	/**
	 * Config Prop - Search method for webwork courses available to a user.
	 */
	private String webworkCoursesSearchMethod = Messages
			.getString("Default.Empty");
	/**
	 * Config Prop - Webwork Data security. The selected security type will be
	 * used to write to webwork. Right now only SHA-1 is only implemented.
	 */
	private String webworkDataSecurity = constantDefaultWebworkDataSecurity;
	/**
	 * Config Prop - Allow guest viewers to view the content.
	 */
	private boolean allowGuestViewers;

	/**
	 * Local flag to write the properties to config file.
	 */
	private boolean writeServerProperty = false;
	/**
	 * Local flag checking the status of writing properties.
	 */
	private boolean serverPropertyWritten = false;

	/**
	 * Variable denoting from which page the bean is accessed.
	 */
	private String locationOfBean = null;
	/**
	 * The success location to which the page to be redirected if status is
	 * success.
	 */
	private String successLocation = null;

	/**
	 * Success message to be displayed on redirection.
	 */
	private String redirectSuccessMessage = null;
	/**
	 * Failure message to be displayed on redirection.
	 */
	private String redirectFailureMessage = null;
	/**
	 * Request URL of the page.
	 */
	private String requestUrl = null;
	/**
	 * Request URI of the page.
	 */
	private String requestUri = null;
	/**
	 * Redirect URL to which the page should be redirected based on the status.
	 */
	private String redirectUrl = null;

	/**
	 * Static Initializer block. Initializes the static variables.
	 */
	static {
		try {
			ConfigFile.configFile = new File(PlugInUtil.getConfigDirectory(
					constantVendorName, constantBuildingBlockName),
					constantConfigFile);
		} catch (PlugInException e) {
			LogServiceFactory.getInstance().logError(
					constantConfigFileFailedLoading);
		}
	}

	/**
	 * Static method to get the property of server RPC location of webwork.
	 * 
	 * @synchronized.
	 * @return String value of the URL representing RPC location. If none on the
	 *         config page, returns empty string.
	 */
	public synchronized static String getStaticWebServerRpcLocation() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[1]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return Messages.getString("Default.Empty");
		}
	}

	/**
	 * Static method to get the property of server SOAP location of webwork.
	 * This location should not be confused with the site URL location. This one
	 * is used to interact with the webservice.
	 * 
	 * @synchronized
	 * @return String representing web server location URL If none on the config
	 *         page, returns empty string.
	 */
	public synchronized static String getStaticWebServerLocation() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[0]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return Messages.getString("Default.Empty");
		}
	}

	/**
	 * Static method to get the property of SOAP Authorization key.
	 * 
	 * @synchronized
	 * @return String representing Authorization Key used in webservice. If none
	 *         on the config page, returns empty string.
	 */
	public synchronized static String getStaticSoapAuthKey() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[2]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return Messages.getString("Default.Empty");
		}
	}

	/**
	 * Static method to get the property of SOAP classes permission.
	 * 
	 * @synchronized
	 * @return String representing SOAP class permission. If none on the config
	 *         page, returns empty string.
	 */
	public synchronized static String getStaticSoapClassesPermission() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[6]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return Messages.getString("Default.Empty");
		}
	}

	/**
	 * Static method to get the property of User status in webwork.
	 * 
	 * @synchronized
	 * @return String representing user status prop. If none on the config page,
	 *         returns empty string.
	 */
	public synchronized static String getStaticSoapUserStatus() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[7]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return Messages.getString("Default.Empty");
		}
	}

	/**
	 * Static method to get the property allow guest viewers content visibility.
	 * 
	 * @synchronized
	 * @return String representing whether guest can view the content. If none
	 *         on the config page, returns empty string.
	 */
	public synchronized static boolean getStaticAllowGuestViewers() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[3]).equals("true") ? true
					: false;
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return false;
		}
	}

	/**
	 * Static method to get the property webserver Site URL of the webwork. This
	 * is where the webwork assignments will be accessed.
	 * 
	 * @synchronized
	 * @return String representing the site url entered in the config page. If
	 *         none on the config page, returns empty string.
	 */
	public synchronized static String getStaticWebServerSiteUrl() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[4]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return Messages.getString("Default.Empty");
		}
	}

	/**
	 * Static method to get the property of type of search method used in
	 * webwork. Usually three types of methods are available - Basic (Search on
	 * an admin / other specified course, for list of instructors of the
	 * course), Advanced - Search for a specified instructor permission level on
	 * the courses, None - List all courses.
	 * 
	 * @synchronized
	 * @return String representing
	 */
	public synchronized static String getStaticWebworkCoursesSearchMethod() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[5]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return Messages.getString("Default.Empty");
		}
	}

	/**
	 * Static method to get the property of search course. The course on which
	 * to search for instructors. Right now, the Admin is the this course, and
	 * every course in webwork has an entry in admin course as -
	 * 'Instructor_course'. Used for Basic search method.
	 * 
	 * @synchronized
	 * @return String representing
	 */
	public synchronized static String getStaticWebworkSearchCourse() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[8]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return Messages.getString("Default.Empty");
		}
	}

	/**
	 * Static method to get the property of instructor permission level which
	 * needs to be checked for each course, for the search method of advanced.
	 * Right now a permission level of 10 is used, which is configuable with
	 * this variable.
	 * 
	 * @synchronized
	 * @see getWebworkAssignmentPointFactor()
	 * @return int representing permission level for instructor on a webwork
	 *         course. Returns 10 by default, if none configured.
	 */
	public synchronized static int getStaticWebworkInstructorPermissionLevel() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return Integer.parseInt(p.getProperty(constantConfigField[9]));
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return constantWebworkInstructorPermissionLevel;
		}
	}

	/**
	 * Static method to get the property assignment point factor. It determines
	 * the weightage of each problem section in webwork for the grade in
	 * blackboard. A Default of 1 is used in blackboard. This is configurable on
	 * global manner meaning, it affects the grading of all webwork courses used
	 * in blackboard.
	 * 
	 * @synchronized
	 * @return int representing assignment point factor / weightage used in
	 *         blackboard. Returns by default 1.
	 */
	public synchronized static int getStaticWebworkAssignmentPointFactor() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return Integer.parseInt(p.getProperty(constantConfigField[10]));
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return constantWebworkAssignmentPointFactor;
		}
	}

	/* Method added for retrieving data encryption type */
	/**
	 * Static method to get the property of webwork data transmission encryption
	 * type.
	 * 
	 * @synchronized.
	 * @return String value representing the encryption type for data
	 *         transmission.
	 */
	public synchronized static String getStaticWebworkDataSecurity() {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(configFile);
			p.load(is);
			is.close();
			return p.getProperty(constantConfigField[11]);
		} catch (IOException e) {
			LogServiceFactory.getInstance().logError(
					(new StringBuilder())
							.append(constantConfigFileFailedLoading)
							.append(configFile.getAbsolutePath()).toString());
			return constantDefaultWebworkDataSecurity;
		}
	}

	/**
	 * Static method which is used as a generic method to get the property of
	 * web-service web-server properties - SOAP URL, RPC URL & webwork URL.
	 * Returns empty string if none of the three values.
	 * 
	 * @synchronized
	 * @return String representing
	 */
	public synchronized static String getWebServiceLocations(String key) {
		if (key.equals(Messages
				.getString("ConfigFile.WebServerLocationStringKey")))
			return getStaticWebServerLocation().equals(
					Messages.getString("Default.Empty")) ? constantWebServerLocation
					: getStaticWebServerLocation();

		if (key.equals(Messages
				.getString("ConfigFile.WebServerRpcLocationStringKey")))
			return getStaticWebServerRpcLocation().equals(
					Messages.getString("Default.Empty")) ? constantWebServerRpcLocation
					: getStaticWebServerRpcLocation();

		if (key.equals(Messages
				.getString("ConfigFile.WebServerSiteUrlStringKey")))
			return getStaticWebServerSiteUrl().equals(
					Messages.getString("Default.Empty")) ? constantWebworkSiteURL
					: getStaticWebServerSiteUrl();

		return Messages.getString("Default.Empty");
	}

	/**
	 * Creates a Config File class, which is used to access properties from
	 * config.properties and write properties to it. Webserver location, RPC
	 * location, Authorization key & guest viewers visibility are all maintained
	 * by this class.
	 */
	public ConfigFile() {
		this.successLocation = constantWebworkPluginsPage;
	}

	/**
	 * Gets the redirect location based on the success / failure of writing to a
	 * file / publish records. Accessed throughout the blackboard plugin. Proper
	 * location of the bean from which this method is being called should be set
	 * before trying to get the redirect URL. Right now config.jsp and
	 * publish.jsp pages uses this method.
	 * 
	 * @see locationOfBean
	 * @return String representing the redirect URL to which the page should be
	 *         redirected on the status.
	 */
	public String getRedirectUrl() {
		/* All valid positions are pulled from the constant. */
		for (int position = 0; position < constantConfigFilePages.length; position++)
			if (this.locationOfBean.equals(constantConfigFilePages[position])) {
				this.redirectSuccessMessage = constantSuccessMessages[position];
				this.redirectFailureMessage = constantFailureMessages[position];
			}
		/*
		 * If properties successfully written in this bean or in any other bean
		 * (in which case serverPropertyWritten should be set to true) then,
		 * proper valid messages are displayed, and the page redirected
		 * accordingly.
		 */
		if (this.serverPropertyWritten)
			redirectUrl = InlineReceiptUtil.addSuccessReceiptToUrl(
					this.successLocation, this.redirectSuccessMessage);
		else{
			if(this.locationOfBean.equals(constantConfigFilePages[0]))
				redirectUrl = InlineReceiptUtil.addErrorReceiptToUrl(
						this.successLocation, this.redirectFailureMessage);
			else
			redirectUrl = InlineReceiptUtil.addErrorReceiptToUrl(
					this.locationOfBean, this.redirectFailureMessage);
		}

		/*
		 * If the success location is equal to that of the config page's then
		 * the redirection should include the request URL rather than the plugin
		 * location. The following method strips off the URI from URL, meaning,
		 * it captures the ip, then adds the admin building blocks page in
		 * blackboard. Change the property
		 * 'BuildingBlockConstants.BuildingBlockPluginsPage' to direct to any
		 * changes within blackboard building blocks page, pointed by
		 * 'constantWebworkPluginsPage'.
		 */
		if (this.successLocation.equals(constantWebworkPluginsPage))
			redirectUrl = this.requestUrl.substring(0,
					this.requestUrl.indexOf(this.requestUri))
					+ redirectUrl;

		return redirectUrl;
	}

	/**
	 * Bean set property, assigns the value to the webwork site URL variable
	 * from the user entered value in the config.jsp page. This is the actual
	 * location from which the webwork assignments are accessed from.
	 * 
	 * @param webServerSiteUrl
	 *            String value to set the webwork Server URL.
	 */
	public void setWebServerSiteUrl(String webServerSiteUrl) {
		this.webServerSiteUrl = webServerSiteUrl;
	}

	/**
	 * Bean's get property, retrieves the webwork site URL value from the
	 * config.properties file. If nothing is set in config page, then the
	 * default value of 'WebworkService.WebworkSiteUrl' property from
	 * messages.properties is fetched through 'constantWebworkSiteURL' variable.
	 * Returns the webwork site URL Location from the static method.
	 * 
	 * @see getWebServiceLocations()
	 * @return the String value configured as the webwork site URL.
	 */
	public String getWebServerSiteUrl() {
		return getWebServiceLocations(Messages
				.getString("ConfigFile.WebServerSiteUrlStringKey"));
	}

	/**
	 * Bean set property, assigns the value to the webwork server web-service
	 * location from the user entered value in the config.jsp page. This is the
	 * actual location from which the SOAP methods are accessed from.
	 * 
	 * @param webServerSelection
	 *            String value to set for web-service SOAP location.
	 */
	public void setWebServerLocation(String webServerLocation) {
		this.webServerLocation = webServerLocation;
	}

	/**
	 * Bean's get property, retrieves the webwork server web-service location
	 * from the config.properties file. If nothing is set in config page, then
	 * the default value of 'WebworkService.WebServerLocation' property from
	 * messages.properties is fetched through 'constantWebServerLocation'
	 * variable. Returns the Web Server Location from the static method.
	 * 
	 * @see getWebServiceLocations()
	 * @return the webServerSelection String
	 */
	public String getWebServerLocation() {
		return getWebServiceLocations(Messages
				.getString("ConfigFile.WebServerLocationStringKey"));
	}

	/**
	 * Bean set property, assigns the value to the Web Server RPC location
	 * location from the user entered value in the config.jsp page. This is the
	 * actual location from which the SOAP methods are accessed from.
	 * 
	 * @param webServerRpcSelection
	 *            String value to set the Web Server RPC location.
	 */
	public void setWebServerRpcLocation(String webServerRpcLocation) {
		this.webServerRpcLocation = webServerRpcLocation;
	}

	/**
	 * Bean's get property, retrieves the Web Server RPC location from the
	 * config.properties file. If nothing is set in config page, then the
	 * default value of 'WebworkService.WebServerLocation' property from
	 * messages.properties is fetched through 'constantWebServerLocation'
	 * variable. Returns the Web Server Location from the static method.
	 * 
	 * @see getWebServiceLocations()
	 * @return the webServerRpcSelection String value representing the Web
	 *         Server RPC location
	 */
	public String getWebServerRpcLocation() {
		return getWebServiceLocations(Messages
				.getString("ConfigFile.WebServerRpcLocationStringKey"));
	}

	/**
	 * Bean set property, assigns the value to the soapAuthKey variable in the
	 * config.jsp page.
	 * 
	 * @param soapAuthKey
	 *            String Authorization key to set.
	 */
	public void setSoapAuthKey(String soapAuthKey) {
		this.soapAuthKey = soapAuthKey;
	}

	/**
	 * Bean's get property, retrieves the authorization key from the
	 * config.properties file, or 'ConfigFile.Default.SOAPAuthKey' property in
	 * messages.properties through 'constantwebworkSOAPAuthKey'.
	 * 
	 * @return String Authorization key value configured in this tool.
	 */
	public String getSoapAuthKey() {
		return getStaticSoapAuthKey().equals(
				Messages.getString("Default.Empty")) ? constantwebworkSOAPAuthKey
				: getStaticSoapAuthKey();
	}

	/**
	 * Bean set property, assigns the value to the soapClassesPermission
	 * variable from the user entered value in the config.jsp page.
	 * 
	 * @param soapClassesPermission
	 *            String value to set the variable.
	 */
	public void setSoapClassesPermission(String soapClassesPermission) {
		this.soapClassesPermission = soapClassesPermission;
	}

	/**
	 * Bean's get property, retrieves the Class permission value from the
	 * config.properties file, or 'ConfigFile.Default.SOAPClassesPermission'
	 * property in messages.properties through 'constantwebworkSOAPAuthKey'.
	 * 
	 * @return the soapClassesPermission
	 */
	public String getSoapClassesPermission() {
		return getStaticSoapClassesPermission().equals(
				Messages.getString("Default.Empty")) ? constantSoapClassesPermission
				: getStaticSoapClassesPermission();
	}

	/**
	 * Bean set property, assigns the value to the soapUserStatus variable from
	 * the user entered value in the config.jsp page.
	 * 
	 * @param soapUserStatus
	 *            String User status variable to be set.
	 */
	public void setSoapUserStatus(String soapUserStatus) {
		this.soapUserStatus = soapUserStatus;
	}

	/**
	 * Bean's get property, retrieves the User status value from the
	 * config.properties file, or 'ConfigFile.Default.WebworkUserStatus'
	 * property in messages.properties through 'constantWebworkUserStatus'.
	 * 
	 * @return the soapUserStatus String value which is to be used for new user
	 *         in webwork.
	 */
	public String getSoapUserStatus() {
		return getStaticSoapUserStatus().equals(
				Messages.getString("Default.Empty")) ? constantWebworkUserStatus
				: getStaticSoapUserStatus();
	}

	/**
	 * Bean set property, assigns the value to the instructor permission level
	 * variable from the user entered value in the config.jsp page.
	 * 
	 * @param webworkInstructorPermissionLevel
	 *            integer value for the permission level.
	 */
	public void setWebworkInstructorPermissionLevel(
			int webworkInstructorPermissionLevel) {
		this.webworkInstructorPermissionLevel = webworkInstructorPermissionLevel;
	}

	/**
	 * Bean's get property, retrieves the instructor permission level value from
	 * the config.properties file. If nothing is set in config page, then the
	 * default value of 10 is fetched from
	 * 'ConfigFile.Default.WebworkInstructorPermissionLevel' in
	 * messages.properties through 'constantWebworkInstructorPermissionLevel'
	 * 
	 * @see getStaticWebworkInstructorPermissionLevel
	 * @return the webworkInstructorPermissionLevel
	 */
	public int getWebworkInstructorPermissionLevel() {
		return getStaticWebworkInstructorPermissionLevel();
	}

	/**
	 * Bean set property, assigns the value to the assignment point factor
	 * variable from the user entered value in the config.jsp page.
	 * 
	 * @param webworkAssignmentPointFactor
	 *            int value set in the config.properties file. Default is 10.
	 */
	public void setWebworkAssignmentPointFactor(int webworkAssignmentPointFactor) {
		this.webworkAssignmentPointFactor = webworkAssignmentPointFactor;
	}

	/**
	 * Bean's get property, retrieves the instructor permission level value from
	 * the config.properties file. If nothing is set in config page, then the
	 * default value of 10 is fetched from
	 * 'ConfigFile.Default.WebworkInstructorPermissionLevel' in
	 * messages.properties through 'constantWebworkInstructorPermissionLevel'
	 * 
	 * @return the webworkAssignmentPointFactor integer value. Default is 1.
	 */
	public int getWebworkAssignmentPointFactor() {
		return getStaticWebworkAssignmentPointFactor();
	}

	/**
	 * Bean set property, assigns the value to the courses search method
	 * variable from the user entered value in the config.jsp page. Right now
	 * only three types available - Basic, Advanced & None.
	 * 
	 * @param webworkCoursesSearchMethod
	 *            String value to be set through config.jsp page.
	 */
	public void setWebworkCoursesSearchMethod(String webworkCoursesSearchMethod) {
		this.webworkCoursesSearchMethod = webworkCoursesSearchMethod;
	}

	/**
	 * Bean's get property, retrieves the courses search method value from the
	 * config.properties file. If nothing is set in config page, then the
	 * default value of 'Advanced' is fetched from
	 * 'ConfigFile.Default.WebworkCoursesSearchMethod' in messages.properties
	 * through 'constantWebworkCoursesSearchMethod'
	 * 
	 * @return String value configured for search type. Default is 'Advanced'.
	 *         One of three values - 'Basic', 'Advanced', 'None'.
	 */
	public String getWebworkCoursesSearchMethod() {
		return getStaticWebworkCoursesSearchMethod().equals(
				Messages.getString("Default.Empty")) ? constantWebworkCoursesSearchMethod
				: getStaticWebworkCoursesSearchMethod();
	}

	/**
	 * Bean's set property, assigns the user selected value for data encryption
	 * type. Can be any one of the two - Hash / None.
	 * 
	 * @param webworkDataSecurity
	 *            String datatype. Right now only two types allowed within the
	 *            plugin - Hash / None.
	 */
	public void setWebworkDataSecurity(String webworkDataSecurity) {
		this.webworkDataSecurity = webworkDataSecurity;
	}

	/**
	 * Bean's get property method to retrieve the data encryption type used
	 * within the plugin.
	 * 
	 * @return the static webworkDataSecurity read from file.
	 */
	public String getWebworkDataSecurity() {
		return getStaticWebworkDataSecurity();
	}

	/**
	 * Bean set property, assigns the value to the search course variable from
	 * the user entered value in the config.jsp page. The default is 'admin'.
	 * 
	 * @param webworkSearchCourse
	 *            The String search course in webwork passed through config.jsp
	 */
	public void setWebworkSearchCourse(String webworkSearchCourse) {
		this.webworkSearchCourse = webworkSearchCourse;
	}

	/**
	 * Bean's get property, retrieves the search course value from the
	 * config.properties file. If nothing is set in config page, then the
	 * default value of 'admin' is fetched from
	 * 'ConfigFile.Default.WebworkSearchCourse' in messages.properties through
	 * 'constantWebworkSearchCourse'
	 * 
	 * @return String value representing the search course configured.
	 */
	public String getWebworkSearchCourse() {
		return getStaticWebworkSearchCourse().equals(
				Messages.getString("Default.Empty")) ? constantWebworkSearchCourse
				: getStaticWebworkSearchCourse();
	}

	/**
	 * Bean set property, assigns the value to the allow guest viewers variable
	 * from the user entered value in the config.jsp page. The default is
	 * 'false'. Though this variable is configured in the admin page, it doesn't
	 * work the way it's expected. Future versions might have this implemented.
	 * 
	 * @param allowGuestViewers
	 *            boolean variable to be set through config.jsp page.
	 */
	public void setAllowGuestViewers(boolean allowGuestViewers) {
		this.allowGuestViewers = allowGuestViewers;
	}

	/**
	 * Bean's get property, retrieves the allow guest viewers value from the
	 * config.properties file. If nothing is set in config page, then the
	 * default value of 'false' is fetched.
	 * 
	 * @return the boolean value indicating if the guest are allowed to view the
	 *         content.
	 */
	public boolean getAllowGuestViewers() {
		return getStaticAllowGuestViewers();
	}

	/**
	 * Method which writes the properties captured from config.jsp page through
	 * request parameters to config.properties file. Sets the flag to the
	 * success of written information. When the information is written, the
	 * config file information is published to other classe's static variables.
	 * This way, when some one changes the config settings, the classes are
	 * synchronized to work with these changes.
	 * 
	 * @synchronized
	 * @param writeServerProperty
	 *            Boolean flag to indicate to write changes to config.properties
	 *            file.
	 */
	public synchronized void setWriteServerProperty(boolean writeServerProperty) {
		if (writeServerProperty) {
			try {
				Properties p = new Properties();
				p.setProperty(constantConfigField[0], this.webServerLocation);
				p.setProperty(constantConfigField[1], this.webServerRpcLocation);
				p.setProperty(constantConfigField[2], this.soapAuthKey);
				p.setProperty(constantConfigField[3],
						((Boolean) this.allowGuestViewers).toString());
				p.setProperty(constantConfigField[4], this.webServerSiteUrl);
				p.setProperty(constantConfigField[5],
						this.webworkCoursesSearchMethod);
				p.setProperty(constantConfigField[6],
						this.soapClassesPermission);
				p.setProperty(constantConfigField[7], this.soapUserStatus);
				p.setProperty(constantConfigField[8], this.webworkSearchCourse);
				p.setProperty(constantConfigField[9],
						String.valueOf(this.webworkInstructorPermissionLevel));
				p.setProperty(constantConfigField[10],
						String.valueOf(this.webworkAssignmentPointFactor));
				/* Webwork Data security type added in the next line. */
				p.setProperty(constantConfigField[11], this.webworkDataSecurity);
				OutputStream out = new FileOutputStream(ConfigFile.configFile);
				p.store(out, constantConfigFileComments);
				out.close();
			} catch (IOException e) {
				LogServiceFactory.getInstance().logError(
						(new StringBuilder())
								.append(constantConfigFileFailedSaving)
								.append(configFile.getAbsolutePath())
								.toString(), e);
				this.setServerPropertyWritten(false);
			}
			/*
			 * Changes should be propagated to other static variables. If all
			 * changes are success set the flag indicating the same, else set it
			 * to false.
			 */
			if (WebworkUtil.configChanged())
				this.setServerPropertyWritten(true);
			else
				this.setServerPropertyWritten(false);
		}
		this.writeServerProperty = writeServerProperty;
	}

	/**
	 * Bean's get property method, retrieving the flag info which indicates if
	 * the procedure of writing the properties to the file executed without any
	 * unexpected exceptions.
	 * 
	 * @return boolean flag indicating if the server properties function
	 *         executed without unexpected exception.
	 */
	public boolean getWriteServerProperty() {
		return writeServerProperty;
	}

	/**
	 * Bean's set property method, used to set the variable
	 * serverPropertyWritten, indicating success of writing all properties to
	 * the file, and that the properties are functioning fine with the
	 * web-service functions.
	 * 
	 * @param serverPropertyWritten
	 *            boolean value to write to the variable.
	 */
	public void setServerPropertyWritten(boolean serverPropertyWritten) {
		this.serverPropertyWritten = serverPropertyWritten;
	}

	/**
	 * Bean's get property method, retrieving the flag info which indicates if
	 * configuration entered by the user are fine for execution of building
	 * block.
	 * 
	 * @return serverPropertyWritten boolean value indicating success of
	 *         configuration.
	 */
	public boolean getServerPropertyWritten() {
		return serverPropertyWritten;
	}

	/**
	 * Method to set the request URI from the config.jsp method. This variable
	 * and Request URL is used to redirect after the configuration is saved.
	 * 
	 * @param requestUri
	 *            String value representing the Request URI.
	 */
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	/**
	 * Retrieves the Request URI capture through request params in the config
	 * page. Used in the page redirection.
	 * 
	 * @return String value representing the Request URI.
	 */
	public String getRequestUri() {
		return requestUri;
	}

	/**
	 * Method to set the request URI from the config.jsp method. This variable
	 * and Request URL is used to redirect after the configuration is saved.
	 * 
	 * @param requestUrl
	 *            String value representing the Request URL.
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * Retrieves the Request URI capture through request params in the config
	 * page. Used in the page redirection.
	 * 
	 * @return String value representing the Request URL.
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * Method to set the location of the bean from which the redirect page is to
	 * be retrieved.
	 * 
	 * @see getRedirectUrl()
	 * @param locationOfBean
	 *            String value representing where the bean is accessed.
	 */
	public void setLocationOfBean(String locationOfBean) {
		this.locationOfBean = locationOfBean;
	}

	/**
	 * Retrieves the locationOfBean variable representing the page from which
	 * this bean was accessed.
	 * 
	 * @see getRedirectUrl()
	 * @return String value representing where the bean is accessed.
	 */
	public String getLocationOfBean() {
		return locationOfBean;
	}

	/**
	 * Retrieves the Request URL captured through request params in the config
	 * page. Used in the page redirection.
	 * 
	 * @see getRedirectUrl()
	 * @return the successLocation String value representing the location of
	 *         page to redirect.
	 */
	public String getSuccessLocation() {
		return successLocation;
	}

	/**
	 * Method to set the success location to redirect after success in the
	 * current page.
	 * 
	 * @see getRedirectUrl()
	 * @param successLocation
	 *            Location of page to redirect. Set internally.
	 */
	public void setSuccessLocation(String successLocation) {
		this.successLocation = successLocation;
	}

	/**
	 * Method to set the redirect failure message. Usually called internally.
	 * Can be one among the 'BuildingBlockConstants.FailureMessages' messages in
	 * the messages.properties file, accessed through 'constantFailureMessages'
	 * variable.
	 * 
	 * @param redirectFailureMessage
	 *            String value representing the failure message.
	 */
	public void setRedirectFailureMessage(String redirectFailureMessage) {
		this.redirectFailureMessage = redirectFailureMessage;
	}

	/**
	 * Retrieves the redirect failure message. Can be one among the
	 * 'BuildingBlockConstants.FailureMessages' messages in the
	 * messages.properties file, accessed through 'constantFailureMessages'
	 * variable.
	 * 
	 * @return String value representing the failure message.
	 */
	public String getRedirectFailureMessage() {
		return redirectFailureMessage;
	}

	/**
	 * Method to set the redirect success message. Usually called internally.
	 * Can be one among the 'BuildingBlockConstants.SuccessMessages' messages in
	 * the messages.properties file, accessed through 'constantSuccessMessages'
	 * variable.
	 * 
	 * @param redirectSuccessMessage
	 *            String value representing the success message.
	 */
	public void setRedirectSuccessMessage(String redirectSuccessMessage) {
		this.redirectSuccessMessage = redirectSuccessMessage;
	}

	/**
	 * Retrieves the redirect success message. Can be one among the
	 * 'BuildingBlockConstants.SuccessMessages' messages in the
	 * messages.properties file, accessed through 'constantSuccessMessages'
	 * variable.
	 * 
	 * @return String value representing the success message.
	 */
	public String getRedirectSuccessMessage() {
		return redirectSuccessMessage;
	}
}
