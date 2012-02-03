/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blackboard.data.user.*;
import blackboard.platform.log.LogServiceFactory;
import blackboard.platform.plugin.PlugInException;

import edu.missouri.math.webwork.WebworkSOAP.*;

/**
 * @author Srinivasan Devanathan
 * @category WebworkUtil Bean Class - Useful to write / get information from
 *           webwork. Used in all jsp pages in the blackboard plugin.
 * @version 2.0
 */
public class WebworkUtil extends BuildingBlockMethods {

	/*
	 * Static Variables. Variables used from config file and frequently used
	 * ones.
	 */
	/**
	 * Static variable of web handler service object.
	 */
	private static WebworkSOAPHandlerService soapHandlerService = null;
	/**
	 * Static variable for web handler used to invoke webservice calls.
	 */
	public static WebworkSOAPHandler soapHandler = null;
	/**
	 * Static variable for Authorization key for webservice.
	 */
	public static String webworkSoapAuthKey = null;
	/**
	 * Static variable for getting the webwork site url.
	 */
	public static String webworkSiteUrl = null;
	/**
	 * Static variable for getting the courses search method.
	 */
	private static String webworkCoursesSearchMethod = null;
	/**
	 * Static variable for holding user status information required by webwork.
	 */
	private static String webworkUserStatus = null;
	/**
	 * Static variable holding the soap class permission required by webwork.
	 */
	private static String webworkSoapClassesPermission = null;
	/**
	 * Static variable for getting the instructor perm level from config
	 * settings.
	 */
	private static int webworkInstructorPermissionLevel;
	/**
	 * Static variable for having the assignment point factor information.
	 */
	private static int webworkAssignmentPointFactor;

	/**
	 * Local variable having permissions record with student level.
	 */
	private WebworkSOAPClassesPermission soapClassesPermission = null;
	@SuppressWarnings("unused")
	private WebworkSOAPClassesPassword createWebworkPassword = null;
	/**
	 * Local variable having the webwork User object to be created in webwork.
	 */
	private WebworkSOAPClassesUser webworkSOAPUser = null;
	/**
	 * Local variable having the blackboard user object.
	 */
	private User blackboardUser = null;

	/* Boolean variables to call from jsp pages. See each get and set methods. */
	/**
	 * Flag to process the publish of blackboard assignment.
	 */
	public boolean publishWebworkAssignment = false;
	/**
	 * Flag to check whether the user has proper permissions in webwork.
	 */
	public boolean checkWebworkUserPermissions = false;
	/**
	 * Flag to create permissions for a user in webwork.
	 */
	public boolean createPermissionsForUser = false;
	/**
	 * Flag to assign a webwork set to a user.
	 */
	public boolean assignSetToUser = false;
	/**
	 * Flag to assign set in webwork to all enrolled users in blackboard.
	 */
	public boolean assignSetToAllCourseUsers = false;
	/**
	 * Flag to calculate grade for a student user.
	 */
	public boolean calculateGrades = false;

	/**
	 * Static variable holding the all courses available in webwork.
	 */
	private static List<String> webworkAllCoursesList;

	/**
	 * Variable to get the information for a course in webwork.
	 */
	private String webworkCourse = Messages.getString("Default.Empty");
	/**
	 * Variable to get the information for a set in webwork.
	 */
	private String webworkCourseSet = Messages.getString("Default.Empty");
	/**
	 * Variable having the list of users for a course in webwork.
	 */
	private List<String> webworkCourseUsers;
	/**
	 * Variable holding the grades of all users in a course in webwork. In
	 * current version not implemented for any functionality.
	 */
	private String[] webworkAssignmentGrades;
	/**
	 * Variable holding the courses available for an instructor in webwork.
	 */
	private List<String> webworkCoursesList;
	/**
	 * Variable holding the sets available for the list of courses in webwork
	 * passed as a the parameter in the set method.
	 * 
	 * @see setWebworkCoursesSets
	 */
	private List<String> webworkCoursesSets;
	/**
	 * Variable holding the list of courses for each set being contained in the
	 * webworkCoursesSets variable.
	 */
	private List<String> selectedWebworkCourses;

	/**
	 * For search method of an admin course to find the courses available for an
	 * instructor, this variable contains the map of courses for the current
	 * user.
	 */
	private Map<String, String> webworkCoursesAdmin;
	/**
	 * For search method of advanced with a specific permission level, the
	 * variable holds the list of courses for which the current user has the
	 * specified perm level.
	 */
	private List<String> webworkPermissionLevels;

	/**
	 * Local variable having grade score for the assignment done by this user in
	 * blackboard - webwork. If user is instructor the value means nothing.
	 */
	private int gradePoints = 0;

	/**
	 * @static initializer block Calls the configChanged.
	 */
	static {
		configChanged();
	}

	/**
	 * Method is called on daily basis to reset / refresh the webworkCourses.
	 * 
	 * @synchronized
	 * @throws RemoteException
	 */
	public synchronized static void resetDaily() throws RemoteException {
		WebworkUtil.webworkAllCoursesList = null;
		WebworkUtil.getWebworkAllCoursesList();
	}

	/**
	 * @static webServerChanged Method called whenever any configuration is
	 *         changed in the admin/config.jsp page. This method is called on
	 *         daily basis, so that the static methods are refreshed on
	 *         periodical basis. Check for logs for any errors reported.
	 */
	public static boolean configChanged() {
		try {
			WebworkUtil.webworkSoapAuthKey = ConfigFile.getStaticSoapAuthKey()
					.equals(Messages.getString("Default.Empty")) ? constantwebworkSOAPAuthKey
					: ConfigFile.getStaticSoapAuthKey();
			WebworkUtil.webworkSiteUrl = ConfigFile.getStaticWebServerSiteUrl()
					.equals(Messages.getString("Default.Empty")) ? constantWebworkSiteURL
					: ConfigFile.getStaticWebServerSiteUrl();
			WebworkUtil.soapHandlerService = new WebworkSOAPHandlerServiceLocator();
			WebworkUtil.soapHandler = WebworkUtil.soapHandlerService
					.getWebworkSOAP();
			WebworkUtil.webworkCoursesSearchMethod = ConfigFile
					.getStaticWebworkCoursesSearchMethod().equals(
							Messages.getString("Default.Empty")) ? constantWebworkCoursesSearchMethod
					: ConfigFile.getStaticWebworkCoursesSearchMethod();
			WebworkUtil.webworkUserStatus = ConfigFile
					.getStaticSoapUserStatus().equals(
							Messages.getString("Default.Empty")) ? constantWebworkUserStatus
					: ConfigFile.getStaticSoapUserStatus();
			WebworkUtil.webworkSoapClassesPermission = ConfigFile
					.getStaticSoapClassesPermission().equals(
							Messages.getString("Default.Empty")) ? constantSoapClassesPermission
					: ConfigFile.getStaticSoapClassesPermission();
			WebworkUtil.webworkInstructorPermissionLevel = ConfigFile
					.getStaticWebworkInstructorPermissionLevel();
			WebworkUtil.webworkAssignmentPointFactor = ConfigFile
					.getStaticWebworkAssignmentPointFactor();

			/* Reset for the webwork courses list. */
			WebworkUtil.resetDaily();

		} catch (ServiceException se) {
			LogServiceFactory
					.getInstance()
					.logError(
							"Remote Server exception / Service Exception. Cannot initialize static variables."); // TODO
			return false;
		} catch (RemoteException re) {
			LogServiceFactory
					.getInstance()
					.logError(
							"Remote Server exception / Service Exception. Cannot initialize static variables.", re); // TODO
			return false;
		}
		return true;
	}

	/**
	 * Assignment points calculated by points factor (see config info)
	 * multiplied the problems in a set. This is the default assignment points.
	 * Instructor can change them in the assignments.jsp
	 * 
	 * @see ConfigFile - webworkAssignmentPointFactor.
	 * @synchronized
	 * @param courseName
	 *            Course Name in webwork for which the grade points should be
	 *            retrieved.
	 * @param setName
	 *            Set Name in webwork for which the grade points should be
	 *            retrieved.
	 * @return integer value representing the total grade points for the Webwork
	 *         assignment.
	 */
	public static synchronized int getAssignmentPoints(String courseName,
			String setName) {
		try {
			int points = 0;
			try {
				for (WebworkSOAPClassesGlobalProblem eachProblem : WebworkUtil.soapHandler
						.get_all_global_problems(
								WebworkUtil.webworkSoapAuthKey, courseName,
								setName))
					points += Integer.parseInt(eachProblem.getValue());
			} catch (NumberFormatException exc) {
				LogServiceFactory.getInstance().logError("Error setting value",
						exc);
			}
			return WebworkUtil.webworkAssignmentPointFactor * points;
		} catch (RemoteException re) {
			LogServiceFactory
					.getInstance()
					.logWarning(
							"Error in retreiving assignment Points - (Problems in a set)");
			return 0;
		}
	}

	/**
	 * Method to write changes to the webwork server. Any changes like start
	 * date, end date, partial credit info, that is entered into blackboard
	 * either during the assigment creation in assignments.jsp or modified in
	 * modify.jsp has to be communicated to the webwork server, so that both
	 * these Db are in sync. Returns true if no excpetion. Method is
	 * synchronized so that the users don't mess up webwork Db.
	 * 
	 * @synchronized
	 * @param course
	 *            Mandatory course name for which to write the changes.
	 * @param user
	 *            Mandatory user name for whom to write the changes.
	 * @param set
	 *            Mandatory course name for which to write the changes.
	 * @param startDate
	 *            can be null or empty
	 * @param endDate
	 *            can be null or empty, sets the answer date to be endDate in
	 *            webwork.
	 * @param partialEndDate
	 *            can be null or empty
	 * @param partialCredit
	 *            can vary from 0 to 100.(0 - indicates not enabling partial
	 *            credit)
	 * @return boolean indicating of success / failure
	 * @throws ParseException
	 *             -- TODO
	 */
	public static synchronized boolean writeChanges(String user, String course,
			String set, String startDate, String endDate,
			String partialEndDate, int partialCredit) throws ParseException {
		WebworkSOAPClassesGlobalSet SET_DATA = null;
		if (user == null || course == null || set == null)
			return false;
		try {
			SET_DATA = WebworkUtil.soapHandler.get_global_set(
					webworkSoapAuthKey, course, set);
			LogServiceFactory.getInstance().logError(
					"Exception while writing to webwork server. " + course
							+ user + set + startDate + endDate);
			if (startDate != null
					&& !startDate.equals(Messages.getString("Default.Empty"))) {
				SET_DATA.setOpen_date(String.valueOf(((Date) constantDateFormat
						.parse(startDate)).getTime()));
			}
			if (endDate != null
					&& !endDate.equals(Messages.getString("Default.Empty"))) {
				SET_DATA.setDue_date(String.valueOf(((Date) constantDateFormat
						.parse(endDate)).getTime()));
				SET_DATA.setAnswer_date(String
						.valueOf(((Date) constantDateFormat.parse(endDate))
								.getTime()));
			}

			/*
			 * TODO set the partial Credit to a value. Also set enable correct
			 * value.
			 */
			/*
			 * if (partialEndDate != null && partialCredit != 0 &&
			 * !partialEndDate.equals(Messages .getString("Default.Empty"))) {
			 * // SET_DATA.setOpen_date(partialEndDate); //
			 * SET_DATA.setEnable_reduced_scoring("true");
			 * 
			 * }
			 */
			LogServiceFactory.getInstance().logError(
					"Exception while writing to webwork server. " + course
							+ user + set + startDate + endDate);

			WebworkUtil.soapHandler.put_global_set(webworkSoapAuthKey, course,
					SET_DATA);
			LogServiceFactory.getInstance().logError(
					"Written to webwork server. " + course + user + set
							+ startDate + endDate + "\n"
							+ SET_DATA.getOpen_date()
							+ SET_DATA.getAnswer_date() + "\n"
							+ SET_DATA.getClass().getName()
							+ SET_DATA.getClass().getSimpleName());
			// TODO remove the log here.
		} catch (RemoteException re) {
			LogServiceFactory.getInstance().logError(
					"Exception while writing to webwork server. " + course
							+ user + set + startDate + endDate, re);
			return false;
		}

		return true;
	}

	/**
	 * Method to get List of webwork courses available in the webwork server.
	 * Note: This is a static method, and the list is refreshed everyday. This
	 * signifies that, if any changes in the webwork for the list of courses in
	 * a single day, it will only be reflected the next day.
	 * 
	 * @return webworkAllCoursesList List of String representing courses
	 *         available in the webwork server.
	 * @throws RemoteException
	 *             Exception occurs when trying to get list of courses from the
	 *             webwork server.
	 */
	public static synchronized List<String> getWebworkAllCoursesList()
			throws RemoteException {
		if (WebworkUtil.webworkAllCoursesList == null
				|| WebworkUtil.webworkAllCoursesList.isEmpty())
			WebworkUtil.webworkAllCoursesList = Arrays
					.asList(WebworkUtil.soapHandler
							.list_courses(WebworkUtil.webworkSoapAuthKey));
		return webworkAllCoursesList;
	}

	/**
	 * Constructor. Nothing initialized. Class used to capture information back
	 * from webwork using web-service functions. Should be used mostly from
	 * instructor context, other than for grading assignments, assigning set
	 * permissions for students.
	 * 
	 * @constructor to initialize the class variables.
	 */
	public WebworkUtil() {
	}

	/**
	 * Blackboard user object, to recognize the user details, and write them to
	 * webwork database. This object is written into, through front-end in
	 * create.jsp
	 * 
	 * @return the blackboardUser Object having the blackboard user information.
	 */
	public User getBlackboardUser() {
		return blackboardUser;
	}

	/**
	 * Method used to set the blackboard user object for this class. This method
	 * also creates a SOAP class permission record based on the user name. Thus,
	 * we need to set the blackboard user in this method, for professors
	 * /students, before they try to do something / access any class methods.
	 * 
	 * @param blackboardUser
	 *            the blackboardUser to set
	 */
	public void setBlackboardUser(User blackboardUser) {
		this.blackboardUser = blackboardUser;
		this.soapClassesPermission = new WebworkSOAPClassesPermission(
				this.blackboardUser.getUserName(),
				WebworkUtil.webworkSoapClassesPermission);
	}

	/**
	 * This method returns the WebworkUser details. Doesn't return back the
	 * webwork user details. Returns the Object created by the plugin.
	 * 
	 * @return the webworkSOAPUser
	 */
	public WebworkSOAPClassesUser getWebworkSOAPUser() {
		return webworkSOAPUser;
	}

	/**
	 * Method creates a new WebworkUser with the blackboard user details. The
	 * user in webwork is created if the user doesn't exists. Constant comment
	 * info can be edited in the properties file. It is necessary to create a
	 * new object of WebworkSOAPClassesUser, as this method tries to set the
	 * object rather than create a new object.
	 * 
	 * @param webworkSOAPUser
	 *            the new Webwork user with details to set.
	 */
	public void setWebworkSOAPUser(WebworkSOAPClassesUser webworkSOAPUser) {
		boolean dataEncryptionEnabled = false;
		if (ConfigFile.getStaticWebworkDataSecurity() != constantDefaultWebworkDataSecurity)
			dataEncryptionEnabled = true;
		webworkSOAPUser
				.setUser_id(dataEncryptionEnabled ? getEncryptedValue(blackboardUser
						.getUserName()) : blackboardUser.getUserName());
		webworkSOAPUser
				.setFirst_name(dataEncryptionEnabled ? getEncryptedValue(blackboardUser
						.getGivenName()) : blackboardUser.getGivenName());
		webworkSOAPUser
				.setLast_name(dataEncryptionEnabled ? getEncryptedValue(blackboardUser
						.getFamilyName()) : blackboardUser.getFamilyName());
		webworkSOAPUser
				.setStudent_id(dataEncryptionEnabled ? getEncryptedValue(blackboardUser
						.getStudentId()) : blackboardUser.getStudentId());
		webworkSOAPUser
				.setEmail_address(dataEncryptionEnabled ? getEncryptedValue(blackboardUser
						.getEmailAddress()) : blackboardUser.getEmailAddress());
		webworkSOAPUser.setStatus(WebworkUtil.webworkUserStatus);
		webworkSOAPUser.setSection(null);
		webworkSOAPUser.setRecitation(null);
		webworkSOAPUser.setComment(WebworkUtil.constantWebworkUserComments);
		this.webworkSOAPUser = webworkSOAPUser;
	}

	/**
	 * Method sets the class soapclasspermission object to the passed value.
	 * 
	 * @param soapClassesPermission
	 *            WebworkSOAPClassesPermission value to set.
	 */
	public void setSoapClassesPermission(
			WebworkSOAPClassesPermission soapClassesPermission) {
		this.soapClassesPermission = soapClassesPermission;
	}

	/**
	 * Method returns the soapClassPermission object. Usually set, when the
	 * blackboard user object is set. See the setBlackboardUser method for
	 * details.
	 * 
	 * @return the WebworkSOAPClassesPermission record.
	 */
	public WebworkSOAPClassesPermission getSoapClassesPermission() {
		return soapClassesPermission;
	}

	/**
	 * Method sets the webworkCourse. Since, based on the webwork course,
	 * problems, sets and assigning the set to set objects change, these objects
	 * are initialized to null. This is due to the way, the get / set methods of
	 * other variables. Mostly they assign a value to the variable if the value
	 * of the variable is empty / null.
	 * 
	 * @param webworkCourse
	 *            String value representing a webwork course.
	 */
	public void setWebworkCourse(String webworkCourse) {
		this.webworkCourse = webworkCourse;

		/* Re-initializing other related variables. */
		this.webworkCourseSet = null;
		this.webworkCoursesSets = null;
		this.assignSetToUser = false;
		this.webworkCourseUsers = null;
	}

	/**
	 * Method returns the course that the professor / student selected. See the
	 * set method for more details.
	 * 
	 * @return String value representing a webwork course.
	 */
	public String getWebworkCourse() {
		return webworkCourse;
	}

	/**
	 * Method sets the set information of a course. Based on the set, the
	 * problems, grade etc. can be queried to webwork. Resetting the set info,
	 * we have to re-initialize the other variables. For more details on this
	 * see the doc on setWebworkCourse method.
	 * 
	 * @param webworkCourseSet
	 *            String value representing a webwork set.
	 */
	public void setWebworkCourseSet(String webworkCourseSet) {
		this.webworkCourseSet = webworkCourseSet;

		/* Re-initializing other related variables. */
		this.assignSetToUser = false;
		this.webworkCourseUsers = null;

	}

	/**
	 * Method returns the set info of a webwork course. See set method for
	 * details.
	 * 
	 * @return String value representing a webwork set.
	 */
	public String getWebworkCourseSet() {
		return webworkCourseSet;
	}

	/**
	 * Method gets the list of course users available with webwork. If the user
	 * is not found in the list, we might have to create a new user / not allow
	 * the user to edit the course /set info etc.
	 * 
	 * @return the webworkCourseUsers List of String providing userName of the
	 *         users available with webwork.
	 * @throws RemoteException
	 *             Exception occurs when trying to get list of course users from
	 *             the webwork server.
	 */
	public List<String> getWebworkCourseUsers() throws RemoteException {
		if (this.webworkCourseUsers == null
				|| this.webworkCourseUsers.isEmpty())
			this.webworkCourseUsers = Arrays.asList(WebworkUtil.soapHandler
					.list_users(WebworkUtil.webworkSoapAuthKey,
							this.webworkCourse));
		return webworkCourseUsers;
	}

	/**
	 * Method which is used by the jsp's in the blackboard building block to get
	 * the list of courses, a professor / TA is allowed to access. Based on the
	 * search method enabled by the Configurations (Settings -> admin/config.jsp
	 * ) page of the building block, the list of courses are returned. All
	 * courses is a static variable, and this method, thus searches for a valid
	 * courses list for this user(who is accessing the plugin). Either checks
	 * the permission level of XX set through config page, or the user ID (any
	 * kind of verification parameter).
	 * 
	 * @synchronized As the other functions inside the function are
	 *               synchronized.
	 * @return the webworkCoursesList List of webwork courses valid for the
	 *         current user(instructor)
	 * @throws RemoteException
	 *             Exception occurs when trying to get list of courses from the
	 *             webwork server.
	 */
	public List<String> getWebworkCoursesList() throws RemoteException {
		/* If search type is 'None', list all courses */
		if (WebworkUtil.webworkCoursesSearchMethod.equals(Messages
				.getLabelString("Config.WebworkCoursesSearchMethod[3]"))) {
			return WebworkUtil.getWebworkAllCoursesList();
		}
		/*
		 * If search type is 'Basic', list all courses which is found through a
		 * specific course (usually admin) where the instructor for a course
		 * could be found using - instructor_course type. The course name is
		 * configurable using config.jsp page.
		 */
		ArrayList<String> tempCoursesList = new ArrayList<String>();
		if (WebworkUtil.webworkCoursesSearchMethod.equals(Messages
				.getLabelString("Config.WebworkCoursesSearchMethod[1]"))) {
			for (String eachWebworkCourse : WebworkUtil
					.getWebworkAllCoursesList()) {
				if (this.getWebworkCoursesAdmin().get(eachWebworkCourse) != null
						&& this.webworkCoursesAdmin.get(eachWebworkCourse)
								.equals(this.blackboardUser.getUserName()))
					tempCoursesList.add(eachWebworkCourse);
			}
			this.webworkCoursesList = tempCoursesList;

			return this.webworkCoursesList;
		}
		/*
		 * If search type is 'Advanced', list all courses for which a permission
		 * level of 10(default) is set for the current user. The Permission
		 * level is configurable using config.jsp page.
		 */
		if (WebworkUtil.webworkCoursesSearchMethod.equals(Messages
				.getLabelString("Config.WebworkCoursesSearchMethod[2]"))) {
			List<String> tempAllCoursesList = WebworkUtil
					.getWebworkAllCoursesList();
			List<String> tempCoursesPermissionLevel = this
					.getWebworkPermissionLevels();
			for (int loop = 0; loop < tempAllCoursesList.size(); loop++) {
				if (tempCoursesPermissionLevel
						.get(loop)
						.equals(String
								.valueOf(WebworkUtil.webworkInstructorPermissionLevel)))
					tempCoursesList.add(tempAllCoursesList.get(loop));
			}
			this.webworkCoursesList = tempCoursesList;

			return this.webworkCoursesList;
		}
		return this.webworkCoursesList;
	}

	/**
	 * Method which returns the list of String containing the sets for a course.
	 * See the set method for details.
	 * 
	 * @return the webworkCoursesSets
	 */
	public List<String> getWebworkCoursesSets() {
		return this.webworkCoursesSets;
	}

	/**
	 * Method sets a list of set information based on list of webworkCourses
	 * passed. See the paramter is list of courses. Based on the courses, this
	 * method access the webwork and gets the list of available sets for each
	 * courses. Also this method adds the course name based on the set info.
	 * That is, for each course, list of sets are added to webworkCoursesSets
	 * variable, and for each set, the course name is added to the
	 * selectedWebworkCourses variable. See selectedWebworkCourses methods for
	 * details.
	 * 
	 * @param webworkCourses
	 *            Gets list of courses to set the sets for these courses
	 * @throws RemoteException
	 *             Exception occurs when trying to get list of sets for the
	 *             courses from the webwork server.
	 */
	public void setWebworkCoursesSets(List<String> webworkCourses)
			throws RemoteException {
		this.webworkCoursesSets = new ArrayList<String>();
		this.selectedWebworkCourses = new ArrayList<String>();

		for (String course : webworkCourses) {

			ArrayList<String> tempContent = new ArrayList<String>();
			this.webworkCourse = course;
			if (this.getWebworkCourseUsers().contains(
					this.blackboardUser.getUserName())) {
				tempContent.addAll(Arrays.asList(WebworkUtil.soapHandler
						.list_global_sets(WebworkUtil.webworkSoapAuthKey,
								this.webworkCourse)));
			}
			/*
			 * else { this.setWebworkSOAPUser(new WebworkSOAPClassesUser());
			 * WebworkUtil.soapHandler.add_user( WebworkUtil.webworkSoapAuthKey,
			 * this.webworkCourse, this.getWebworkSOAPUser());
			 * setCreatePermissionsForUser(true);
			 * tempContent.addAll(Arrays.asList(WebworkUtil.soapHandler
			 * .list_global_sets(WebworkUtil.webworkSoapAuthKey,
			 * this.webworkCourse))); }
			 */
			/*
			 * Adding each set in the course to webworkCoursesSets. Adding the
			 * course name to each set name in the selectedWebworkCourses
			 */
			this.webworkCoursesSets.addAll(tempContent);
			for (@SuppressWarnings("unused")
			String sets : tempContent)
				this.selectedWebworkCourses.add(course);

		}

	}

	/**
	 * Sets the list of string of courses for each set information. Thus there
	 * is a one to one correspondence with this variable to that of
	 * webworkCoursesSets, which means for each variable in the set variable,
	 * there should be a corresponding course info available in this variable.
	 * Mostly used in the sets.jsp page.
	 * 
	 * @param selectedWebworkCourses
	 *            the selectedWebworkCourses to set
	 */
	public void setSelectedWebworkCourses(List<String> selectedWebworkCourses) {
		this.selectedWebworkCourses = selectedWebworkCourses;
	}

	/**
	 * Method returns the list of webwork course info. See the sets method, and
	 * variable doc involved for more details.
	 * 
	 * @return the selectedWebworkCourses
	 */
	public List<String> getSelectedWebworkCourses() {
		return selectedWebworkCourses;
	}

	/**
	 * Method usually called to set permissions, create user, and assign set to
	 * the user. Usually done when an assignment is published. Calls another set
	 * method - setCheckWebworkUserPermissions to do the request.
	 * 
	 * @param publishWebworkAssignment
	 *            the publishWebworkAssignment to set
	 * @throws RemoteException
	 */
	public void setPublishWebworkAssignment(boolean publishWebworkAssignment)
			throws RemoteException {
		if (publishWebworkAssignment == true) {
			if (WebworkUtil.webworkCoursesSearchMethod.equals(Messages
					.getLabelString("Config.WebworkCoursesSearchMethod[3]"))) {
				this.setCheckWebworkUserPermissions(true);
			}
		}
		this.publishWebworkAssignment = publishWebworkAssignment;
	}

	/**
	 * Returns the value for publishWebworkAssignment variable. See the set
	 * method, for the functioning of the variable.
	 * 
	 * @return the checkWebworkUserPermissions
	 */
	public boolean getPublishWebworkAssignment() {
		return this.publishWebworkAssignment;
	}

	/**
	 * Returns the value for checkWebworkUserPermissions variable. See the set
	 * method, for the functioning of the variable.
	 * 
	 * @return the checkWebworkUserPermissions
	 */
	public boolean getCheckWebworkUserPermissions() {
		return checkWebworkUserPermissions;
	}

	/**
	 * Method called twice usually - while creating assignments & during normal
	 * login to webwork. Called when plain list of webwork courses - without any
	 * search method is used. This method creates a webwork user, if one doesn't
	 * exist with the name of the current user & adds the set to the user, and
	 * creates a permission for normal access to the user. Care should be taken,
	 * as if the user is in instructor mode, this method might change the perm
	 * to student mode in webwork.
	 * 
	 * @param checkWebworkUserPermissions
	 *            the checkWebworkUserPermissions to set
	 * @throws RemoteException
	 */
	public void setCheckWebworkUserPermissions(
			boolean checkWebworkUserPermissions) throws RemoteException {
		if (checkWebworkUserPermissions) {
			if (!this.getWebworkCourseUsers().contains(
					this.blackboardUser.getUserName())) {
				this.setWebworkSOAPUser(new WebworkSOAPClassesUser());
				try {
					WebworkUtil.soapHandler.add_user(
							WebworkUtil.webworkSoapAuthKey, this.webworkCourse,
							this.getWebworkSOAPUser());
					LogServiceFactory.getInstance().logFatal("User Created");
				} catch (Exception exc) {
					LogServiceFactory
							.getInstance()
							.logFatal(
									"User already exists: Create failed even though list doesn't have the user");
				}
			}
			setCreatePermissionsForUser(true);
			setAssignSetToUser(true);
		}
		this.checkWebworkUserPermissions = checkWebworkUserPermissions;
	}

	/**
	 * Method checks if the permissions exist for the user in the webwork user.
	 * If not it will try to create one, and add & put_permission. Check logs
	 * for any exception. Be careful in using this method, as it might change
	 * the permission of an instructor to student, when called in irregular
	 * context.
	 * 
	 * @param createPermissionsForUser
	 *            the boolean attribute indicating whether the method needs to
	 *            execute or not.
	 */
	public void setCreatePermissionsForUser(boolean createPermissionsForUser) {
		if (createPermissionsForUser == true) {
			try {
				WebworkSOAPClassesPermission tempPermission = null;
				try {
					tempPermission = WebworkUtil.soapHandler.get_permission(
							webworkSoapAuthKey, webworkCourse,
							this.blackboardUser.getUserName());
				} catch (Exception exc) {
					LogServiceFactory.getInstance().logFatal(
							"Creating a new Permission level: Failed for"
									+ this.webworkCourse
									+ this.blackboardUser.getUserName());
				}
				if (tempPermission != null
						&& tempPermission.getPermission() != null) {
					LogServiceFactory.getInstance().logInfo(
							"Permission :" + tempPermission.getPermission());
				} else if (tempPermission == null
						|| tempPermission.getPermission() == null) {
					WebworkUtil.soapHandler.add_permission(
							WebworkUtil.webworkSoapAuthKey, webworkCourse,
							this.getSoapClassesPermission());

					WebworkUtil.soapHandler.put_permission(
							WebworkUtil.webworkSoapAuthKey, webworkCourse,
							this.getSoapClassesPermission());
				}

			} catch (Exception exc) {
				LogServiceFactory.getInstance().logError(
						"Null pointer exception", exc);
			}
		}
		this.createPermissionsForUser = createPermissionsForUser;
	}

	/**
	 * Method sets the password for the user. Need to apply in the future
	 * version. TODO method.
	 * 
	 * @param createWebworkPassword
	 *            the createWebworkPassword to set
	 */
	@SuppressWarnings("unused")
	private void setCreateWebworkPassword(
			WebworkSOAPClassesPassword createWebworkPassword) {
		this.createWebworkPassword = createWebworkPassword;

	}

	/**
	 * Method which is used to assign a particular webwork set to the specified
	 * user(current user). The method checks if the user already has the
	 * permission, if not it will try to create one. The boolean attribute can
	 * be checked for the value passed, to know if the function execution is
	 * fine or not.
	 * 
	 * @param assignSetToUser
	 *            the boolean attribute indicating whether to assign the set or
	 *            not.
	 */
	public void setAssignSetToUser(boolean assignSetToUser) {
		if (assignSetToUser == true) {
			try {
				if (WebworkUtil.soapHandler.get_user_set(
						WebworkUtil.webworkSoapAuthKey, this.webworkCourse,
						this.blackboardUser.getUserName(),
						this.webworkCourseSet) == null)
					WebworkUtil.soapHandler.assign_set_to_user(
							WebworkUtil.webworkSoapAuthKey, this.webworkCourse,
							this.blackboardUser.getUserName(),
							this.webworkCourseSet);
				else
					LogServiceFactory.getInstance().logFatal("Set is not null");
			} catch (Exception exc) {
				LogServiceFactory.getInstance().logFatal(
						"Logging assign_set_to_user: Failed for,"
								+ this.blackboardUser.getUserName()
								+ this.webworkCourse + this.webworkCourseSet,
						exc);
			}
		}
		this.assignSetToUser = assignSetToUser;
	}

	/**
	 * Method is used to assign a specified user name to a set. This happens
	 * from the instructor context. Right now not implemented. Method is for
	 * future use only.
	 * 
	 * @param userName
	 *            - the String object of a user name which is to be passed.
	 * @throws RemoteException
	 *             Exception which might be thrown if anything went wrong in
	 *             webservice.
	 */
	private void setAssignSetToUser(String userName) throws RemoteException {
		WebworkUtil.soapHandler.assign_set_to_user(
				WebworkUtil.webworkSoapAuthKey, webworkCourse, userName,
				webworkCourseSet);
	}

	/**
	 * Method which assigns a boolean attribute assignSetToAllCourseUsers the
	 * value passed. For future use only. Method might be used by an instructor
	 * to assign all enrolled students in the blackboard to be assigned to a
	 * particular set in webwork. Not implemented right now.
	 * 
	 * @param assignSetToAllCourseUsers
	 *            the boolean attribute indicating, whether to assign all course
	 *            users enrolled in the blackboard course to the webwork.
	 */
	public void setAssignSetToAllCourseUsers(boolean assignSetToAllCourseUsers) {
		this.assignSetToAllCourseUsers = assignSetToAllCourseUsers;
	}

	/**
	 * Method which returns the boolean attribute value
	 * assignSetToAllCourseUsers. See the set method for details.
	 * 
	 * @return the assignSetToAllCourseUsers
	 */
	public boolean getAssignSetToAllCourseUsers() {
		return assignSetToAllCourseUsers;
	}

	/**
	 * Method which writes an array of grades to the variable
	 * webworkAssignmentGrades.
	 * 
	 * @param webworkAssignmentGrades
	 *            Array of String representing the webwork grades.
	 */
	public void setWebworkAssignmentGrades(String[] webworkAssignmentGrades) {
		this.webworkAssignmentGrades = webworkAssignmentGrades;
	}

	/**
	 * Method which retrieves the array of grades from the webwork. See the set
	 * method for details.
	 * 
	 * @return the webworkAssignmentGrades - an array of String representing the
	 *         grades.
	 */
	public String[] getWebworkAssignmentGrades() {
		return webworkAssignmentGrades;
	}

	/**
	 * Method which sets the boolean attribute calculateGrades.
	 * 
	 * @param calculateGrades
	 *            the calculateGrades to set
	 */
	public void setCalculateGrades(boolean calculateGrades) {
		this.calculateGrades = calculateGrades;
	}

	/**
	 * Method sets the grades to webworkAssignmentGrades for list of webwork
	 * course users & grade points for each user. Also the grade point for the
	 * particular user is set to gradePoints variable.
	 * 
	 * @return the calculateGrades boolean flag indicating
	 * @throws RemoteException
	 */
	public boolean getCalculateGrades() throws RemoteException {
		try {
			this.setWebworkAssignmentGrades(WebworkUtil.soapHandler
					.grade_users_sets(WebworkUtil.webworkSoapAuthKey,
							webworkCourse,
							(String[]) this.webworkCourseUsers.toArray(),
							this.webworkCourseSet));
			this.setGradePoints(((Integer
					.parseInt(this.webworkAssignmentGrades[this.webworkCourseUsers
							.indexOf(this.blackboardUser.getUserName())]))));
		} catch (RemoteException re) {
			// TODO Log appropriate message.
			this.setCalculateGrades(false);
		}
		return calculateGrades;
	}

	/**
	 * Method which sets the grade of this user to a particular value. See the
	 * get method for more details.
	 * 
	 * @param gradePoints
	 *            the grade score value to set
	 */
	public void setGradePoints(int gradePoints) {
		this.gradePoints = gradePoints;
	}

	/**
	 * Method retrieves the score of this user in the set and course. See the
	 * getCalculateGrades method for details on how the information is
	 * extracted.
	 * 
	 * @return the gradePoints integer value representing grade score.
	 * @throws RemoteException
	 *             while accessing webservice.
	 */
	public int getGradePoints() throws RemoteException {
		if (this.gradePoints == 0)
			this.getCalculateGrades();
		return gradePoints;
	}

	/**
	 * Method is used for courses search of type 1 in webwork. This method
	 * searches for the username in a course like admin / some other course,
	 * where you have a list of courses for a particular user listed by -
	 * 'user_course_info' type. Method can be rewritten / configured for future
	 * changes. The method is configurable for the course to search on, which is
	 * in the config page.
	 * 
	 * @return the webworkCoursesAdmin Map<CourseNames,InstructorNames>
	 *         variable.
	 * @throws RemoteException
	 *             while accessing webwork web-service.
	 */
	public Map<String, String> getWebworkCoursesAdmin() throws RemoteException {
		if (this.webworkCoursesAdmin == null
				|| this.webworkCoursesAdmin.isEmpty()) {

			ArrayList<String> tempArrayList = null;

			synchronized (tempArrayList) {
				tempArrayList = new ArrayList<String>(
						Arrays.asList(WebworkUtil.soapHandler.list_users(
								WebworkUtil.webworkSoapAuthKey,
								constantWebworkSearchCourse)));
			}

			this.webworkCoursesAdmin = new HashMap<String, String>();

			for (String eachWebworkCourse : tempArrayList)
				if (eachWebworkCourse.contains("_")) {
					String instructorForWebworkCourse = eachWebworkCourse
							.substring(0, eachWebworkCourse.indexOf('_') + 1);
					if (instructorForWebworkCourse.equals(this.blackboardUser
							.getUserName()))
						this.webworkCoursesAdmin.put(eachWebworkCourse
								.substring(eachWebworkCourse.indexOf('_') + 1,
										eachWebworkCourse.length()),
								instructorForWebworkCourse);
				}
		}

		return this.webworkCoursesAdmin;
	}

	/**
	 * Method gets a list of permission for this user for all courses available
	 * in the webwork. This method is used for searching webwork courses of type
	 * 2. Method checks for a permission level of 10(right now) or so, and
	 * assigns the course to a list of string specifying that the professor /
	 * student has the instructor permission for the course in the webwork.
	 * 
	 * @synchronized
	 * @return the webworkPermissionLevels String list containing permission
	 *         level for instructor in each course.
	 * @throws RemoteException
	 *             while accessing webwork web-service.
	 */
	public synchronized List<String> getWebworkPermissionLevels()
			throws RemoteException {
		if (this.webworkPermissionLevels == null
				|| this.webworkPermissionLevels.isEmpty()) {
			ArrayList<String> tempPermissionLevel = new ArrayList<String>();
			for (String eachCourseList : WebworkUtil.getWebworkAllCoursesList()) {
				try {
					tempPermissionLevel.add((WebworkUtil.soapHandler
							.get_permission(WebworkUtil.webworkSoapAuthKey,
									eachCourseList,
									this.blackboardUser.getUserName())
							.getPermission()));
				} catch (Exception exc) {
					tempPermissionLevel
							.add(WebworkUtil.webworkSoapClassesPermission);

					LogServiceFactory.getInstance().logFatal(
							"Some exception with getPermissions for course: "
									+ eachCourseList, exc);
				}
			}
			this.webworkPermissionLevels = tempPermissionLevel;
		}
		return webworkPermissionLevels;
	}
}
