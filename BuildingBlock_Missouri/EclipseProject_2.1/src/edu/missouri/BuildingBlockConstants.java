/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.text.SimpleDateFormat;

/**
 * @author Srinivasan Devanathan
 * @category Interface BuildingBlockConstants - Interface implemented by all
 *           classes in the building block to have necessary constants. All
 *           constants can be found in messages.properties file. Change the
 *           constants in the properties file as needed by the future needs.
 * @version 2.0
 */
public interface BuildingBlockConstants {

	// BuildingBlock constants
	/**
	 * Vendor name used in bb-manifest.xml. Make sure both are the same.
	 */
	public final static String constantVendorName = Messages
			.getString("BuildingBlockConstants.VendorName");
	/**
	 * Building block name as found in bb-manifest.xml.
	 */
	public final static String constantBuildingBlockName = Messages
			.getString("BuildingBlockConstants.BuildingBlockName");
	/**
	 * Root folder for the building block. Right now - 'webwork'
	 */
	public final static String constantRootFolder = Messages
			.getString("BuildingBlockConstants.RootFolder");
	/**
	 * Javascript to be used within building block.
	 */
	public final static String constantJsUrl = Messages
			.getLabelString("Page.Javascript");
	/**
	 * The login page to be used by the building block.
	 */
	public final static String constantWebworkPageLink = Messages
			.getLabelString("Page.Login");
	/**
	 * The admin page in blackboard. Change for future needs.
	 */
	public final static String constantWebworkPluginsPage = Messages
			.getString("BuildingBlockConstants.BuildingBlockPluginsPage");

	// WebworkUtil Class constants
	/**
	 * SOAP Class permission default value - 0.
	 */
	public final static String constantSoapClassesPermission = Messages
			.getString("ConfigFile.Default.SOAPClassesPermission");
	/**
	 * SOAP Auth key to be accessed by default if not set in config.jsp
	 */
	public final static String constantwebworkSOAPAuthKey = Messages
			.getString("ConfigFile.Default.SOAPAuthKey");
	/**
	 * User Status in webwork by default if not set in config.jsp
	 */
	public final static String constantWebworkUserStatus = Messages
			.getString("ConfigFile.Default.WebworkUserStatus");
	/**
	 * User comments to write in webwork when creating a new user.
	 */
	public final static String constantWebworkUserComments = Messages
			.getString("BuildingBlockConstants.UserComments");
	/**
	 * Webwork search course by default if not set in config.jsp
	 */
	public final static String constantWebworkSearchCourse = Messages
			.getString("ConfigFile.Default.WebworkSearchCourse");
	/**
	 * Webwork courses search method type by default if not set in config.jsp
	 */
	public final static String constantWebworkCoursesSearchMethod = Messages
			.getString("ConfigFile.Default.WebworkCoursesSearchMethod");
	/**
	 * Instructor permission level by default if not set in config.jsp
	 */
	public final static int constantWebworkInstructorPermissionLevel = Integer
			.parseInt(Messages
					.getString("ConfigFile.Default.WebworkInstructorPermissionLevel"));
	/**
	 * Student permission level by default in webwork. Change as needed.
	 */
	public final static int constantWebworkStudentPermissionLevel = Integer
			.parseInt(Messages
					.getString("ConfigFile.Default.WebworkStudentPermissionLevel"));
	/**
	 * Assignment point factor by default if not set in config.jsp.
	 */
	public final static int constantWebworkAssignmentPointFactor = Integer
			.parseInt(Messages
					.getString("ConfigFile.Default.WebworkAssignmentPointFactor"));

	// BlackboardUtil Class constants
	/**
	 * Constant Hanlder as in bb-manifest.xml. Be sure to keep this the same.
	 */
	public final static String constantContentHandlerType = Messages
			.getString("BuildingBlockConstants.BuildingBlockContentHandlerType");
	/**
	 * First parameter in a request object in HTTP GET/POST.
	 */
	public final static String constantRequestFirstParam = Messages
			.getString("BuildingBlockConstants.RequestFirstParam");
	/**
	 * Second parameter in a request object in HTTP GET/POST.
	 */
	public final static String constantRequestSecondParam = Messages
			.getString("BuildingBlockConstants.RequestSecondParam");
	/**
	 * Course parameter set by the building block & which is needed for content.
	 */
	public final static String constantAssignmentInfoCourseParam = constantRequestFirstParam
			+ Messages
					.getString("BuildingBlockConstants.AssignmentInfoCourseParam");
	/**
	 * Set parameter set by the building block & which is needed for content.
	 */
	public final static String constantAssignmentInfoSetParam = constantRequestSecondParam
			+ Messages
					.getString("BuildingBlockConstants.AssignmentInfoSetParam");
	/**
	 * Content parameter set by the building block & which is needed.
	 */
	public final static String constantAssignmentInfoContentIdParam = constantRequestSecondParam
			+ Messages
					.getString("BuildingBlockConstants.AssignmentInfoContentIdParam");
	/**
	 * Course ID parameter set by the building block & which is needed.
	 */
	public final static String constantAssignmentInfoCourseIdParam = constantRequestSecondParam
			+ Messages
					.getString("BuildingBlockConstants.AssignmentInfoCourseIdParam");
	/**
	 * Grade book parameter set by the building block & which is needed.
	 */
	public final static String constantAssignmentGradeBookNameParam = constantRequestSecondParam
			+ Messages
					.getString("BuildingBlockConstants.AssignmentGradeBookNameParam");

	/**
	 * Announcement title in blackboard for each assignment.
	 */
	public final static String constantAnnouncementPublishTextTitle = Messages
			.getString("BuildingBlockConstants.AnnouncementPublishTextTitle");
	/**
	 * Announcement body in blackboard for each assignment.
	 */
	public final static String constantAnnouncementPublishTextBody = Messages
			.getString("BuildingBlockConstants.AnnouncementPublishTextBody");

	// Assignment Info Class constants
	/**
	 * Constant HREF link to be used to create a HTTP link.
	 */
	public final static String constantAssignmentHrefLink = Messages
			.getString("BuildingBlockConstants.HrefLink");

	// Web work Site information
	/**
	 * Webwork web server site URL to be used by default if not configured.
	 */
	public final static String constantWebworkSiteURL = Messages
			.getString("WebworkService.WebworkSiteUrl");
	/**
	 * First parameter required by webwork. Change in future as needs permit.
	 */
	public final static String constantWebworkSiteUserParam = constantRequestFirstParam
			+ Messages.getString("BuildingBlockConstants.WebworkSiteUserParam");
	/**
	 * Second parameter required by webwork. Change in future as needs permit.
	 */
	public final static String constantWebworkSiteKeyParam = constantRequestSecondParam
			+ Messages.getString("BuildingBlockConstants.WebworkSiteKeyParam");

	// Webservice Location
	/**
	 * Webwork web service URL to be used by default if not configured.
	 */
	public final static String constantWebServerLocation = Messages
			.getString("WebworkService.WebServerLocation");
	/**
	 * Webwork web service RPC URL to be used by default if not configured.
	 */
	public final static String constantWebServerRpcLocation = Messages
			.getString("WebworkService.WebworkRPCLocation");

	// Admin Config File Properties
	/**
	 * Default webwork data security type to be used in the plugin.
	 */
	public static final String constantDefaultWebworkDataSecurity = Messages
			.getString("ConfigFile.Default.WebworkDataSecurity");
	/**
	 * Config properties file name to be used to find / create file.
	 */
	public static final String constantConfigFile = Messages
			.getString("BuildingBlockConstants.ConfigFile");
	/**
	 * Fields in config file to write / get info about properties.
	 */
	public static final String[] constantConfigField = {
			Messages.getString("BuildingBlockConstants.ConfigField[0]"),
			Messages.getString("BuildingBlockConstants.ConfigField[1]"),
			Messages.getString("BuildingBlockConstants.ConfigField[2]"),
			Messages.getString("BuildingBlockConstants.ConfigField[3]"),
			Messages.getString("BuildingBlockConstants.ConfigField[4]"),
			Messages.getString("BuildingBlockConstants.ConfigField[5]"),
			Messages.getString("BuildingBlockConstants.ConfigField[6]"),
			Messages.getString("BuildingBlockConstants.ConfigField[7]"),
			Messages.getString("BuildingBlockConstants.ConfigField[8]"),
			Messages.getString("BuildingBlockConstants.ConfigField[9]"),
			Messages.getString("BuildingBlockConstants.ConfigField[10]"),
			Messages.getString("BuildingBlockConstants.ConfigField[11]") };
	/* Last line added for adding data security in webwork. */
	/**
	 * Comments to be written in the config file.
	 */
	public static final String constantConfigFileComments = Messages
			.getString("BuildingBlockConstants.ConfigFileComments");
	/**
	 * Message to display when the config file could not be loaded.
	 */
	public static final String constantConfigFileFailedLoading = Messages
			.getString("BuildingBlockConstants.ConfigFileFailedLoading");
	/**
	 * Message to display when the config file could not be saved.
	 */
	public static final String constantConfigFileFailedSaving = Messages
			.getString("BuildingBlockConstants.ConfigFileFailedSaving");
	/**
	 * Allowing guest users to view the content. Flags available.
	 */
	public static final String[] constantConfigFileAllowGuestUsers = { "true",
			"false" };

	// Total pages Sections of configUtil parameters
	/**
	 * Pages available within building block which needs to be identified by
	 * Bean's get / set properties. Add as per future needs. Not all pages are
	 * added in this constant.
	 */
	public static final String[] constantConfigFilePages = {
			Messages.getLabelString("Page.Config"),
			Messages.getLabelString("Page.Publish"),
			Messages.getLabelString("Page.Login"),
			Messages.getLabelString("Page.Modify") };
	// Receipt tool Messages
	/**
	 * Success messages to be displayed on page redirection based on the pages
	 * specified above.
	 */
	public static final String[] constantSuccessMessages = {
			Messages.getString("BuildingBlockConstants.SuccessMessages[1]"),
			Messages.getString("BuildingBlockConstants.SuccessMessages[2]"),
			Messages.getString("BuildingBlockConstants.SuccessMessages[3]"),
			Messages.getString("BuildingBlockConstants.SuccessMessages[4]") };
	/**
	 * Failure messages to be displayed on page redirection based on the pages
	 * specified above.
	 */
	public static final String[] constantFailureMessages = {
			Messages.getString("BuildingBlockConstants.FailureMessages[1]"),
			Messages.getString("BuildingBlockConstants.FailureMessages[2]"),
			Messages.getString("BuildingBlockConstants.SuccessMessages[3]"),
			Messages.getString("BuildingBlockConstants.SuccessMessages[4]") };

	/**
	 * Date format variable which is used to format / parse date within this
	 * util. Used frequently within this bulding block. Check the format in
	 * 'BlackboardUtil.DateFormat'.
	 */
	public static final SimpleDateFormat constantDateFormat = new SimpleDateFormat(
			Messages.getString("BlackboardUtil.DateFormat"));

}
