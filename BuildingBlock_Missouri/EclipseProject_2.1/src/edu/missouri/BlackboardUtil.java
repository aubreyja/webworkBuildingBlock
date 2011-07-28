/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import blackboard.data.ValidationException;
import blackboard.data.user.User;
import blackboard.base.FormattedText;

import blackboard.persist.*;
import blackboard.persist.user.*;
import blackboard.persist.announcement.AnnouncementDbPersister;
import blackboard.persist.content.ContentDbLoader;
import blackboard.persist.content.ContentDbPersister;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.gradebook.LineitemDbLoader;
import blackboard.persist.gradebook.LineitemDbPersister;
import blackboard.persist.gradebook.ScoreDbLoader;
import blackboard.persist.gradebook.ScoreDbPersister;

import blackboard.data.gradebook.Lineitem;
import blackboard.data.gradebook.Score;
import blackboard.data.announcement.Announcement;
import blackboard.data.content.Content;
import blackboard.data.content.CourseDocument;
import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;

import blackboard.platform.announcement.service.AnnouncementManagerFactory;
import blackboard.platform.log.LogServiceFactory;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.platform.plugin.PlugInConstants;
import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.security.Entitlement;
import blackboard.platform.security.SecurityUtil;

import blackboard.servlet.data.activefilter.CriterionCategory;
import blackboard.servlet.data.activefilter.SearchCriteria;

/**
 * @author Srinivasan Devanathan
 * @category Blackboard Bean Class - Useful to write / get information from
 *           blackboard. Used in all jsp pages in the blackboard plugin.
 * @version 2.0
 */
public class BlackboardUtil extends BuildingBlockMethods {

	/* Private variables used within the util. Captured through request params */
	/**
	 * Variable to hold the request URL to redirect page.
	 */
	private String requestUrl = null;
	/**
	 * Variable to hold the request URI to redirect the page.
	 */
	private String requestUri = null;
	/**
	 * Variable to hold the course ID (Blackboard ID) info.
	 */
	private Id courseIdentity = null;
	/**
	 * Variable holding the content number info provided in the request param by
	 * Blackboard. Necessary to write content / modify / redirect etc.
	 */
	private String contentNumber = null;
	/**
	 * Variable holding the course number info provided in the request param by
	 * Blackboard. Necessary to write content / modify / redirect etc.
	 */
	private String courseNumber = null;
	/**
	 * Variable holding the course contents URL to which to redirect the page.
	 */
	private String courseContentsUrl = null;
	/**
	 * Flag to find if the user is an instructor or not.
	 */
	@SuppressWarnings("unused")
	private boolean courseInstructor = false;
	/**
	 * Array variable holding grades for all users in a course.
	 */
	private String[] webworkGrades = null;

	/**
	 * List of enrolled users in a course.
	 */
	private List<User> enrolledUserList;
	/**
	 * List of enrolled students (users - instructors) in a course.
	 */
	private List<String> enrolledStudentList;
	/**
	 * Blackboard user object for the current user to find the user name,
	 * student Id, etc, which might be required for student to be passed to
	 * create the webwork user. Also needed for instructors for verifying
	 * permissions in webwork etc.
	 */
	private User blackboardUser = null;
	/**
	 * Content object which is to be modified in modify.jsp.
	 */
	private Content contentModify = null;

	/* Static variables. */
	/**
	 * Variable holding last instantiated time for static variables (Used mainly
	 * for course list refresh in webwork.)
	 */
	private static Calendar refreshStaticVariables;
	/**
	 * Blackboard object used to load enrolled users list.
	 */
	private static UserDbLoader courseUserDb;
	/**
	 * Blackboard persist manager object to persist any info.
	 */
	private static BbPersistenceManager persistenceManager;
	/**
	 * Blackboard grade book loader object to load grade book items.
	 */
	private static LineitemDbLoader gradeLineItemDb = null;
	/**
	 * Blackboard grade book persister to persist the grades to Db.
	 */
	private static LineitemDbPersister gradePersister = null;
	/**
	 * Blackboard content loader to load / create the content object.
	 */
	private static ContentDbLoader contentDb = null;
	/**
	 * Blackboard content persister object to persist the content.
	 */
	private static ContentDbPersister contentPersister = null;
	/**
	 * Blackboard score loader object to load an user score.
	 */
	static ScoreDbLoader scoreLoader = null;
	/**
	 * Blackboard score persister object to persist the scores.
	 */
	static ScoreDbPersister scorePersister = null;
	/**
	 * Blackboard course membership loader to load the membership / roles.
	 */
	static CourseMembershipDbLoader courseMembershipLoader = null;

	/**
	 * The location of the present building block. Loaded using the handle,
	 * vendor, & block name specified in bb-manifest.xml & in
	 */
	static String buildingBlockURI = null;

	/* Grade score variables */
	/**
	 * Variable to hold the current score information for the assignment.
	 */
	private Score scoreAssignment = null;
	/**
	 * Flag variable to indicate whether to write grades to blackboard.
	 */
	private boolean writeGrades = false;
	/**
	 * Flag variable to indicate write students grades (done from instructor
	 * context). Usually called from grades.jsp page.
	 */
	private boolean studentsGrades = false;
	/**
	 * Score values for each enrolled student to display to the instructor in
	 * grades.jsp page.
	 */
	private String[] studentsGradesScores;

	/* Package class files. Used to write data to blackboard. */
	/**
	 * Class object to capture info to modify the content info in modify.jsp
	 */
	private AssignmentInfo assignmentInfo = null;
	/**
	 * Publish Data object to capture the info about assignments and
	 */
	private PublishData publishData = null;
	private boolean publishAssignmentsData = false;

	/**
	 * @static initializer block Calls the configChanged as its the same. This
	 *         way, any changes in the config page, it can be handled through
	 *         configChanged method.
	 */
	static {
		configChanged();
	}

	/**
	 * @static configChanged Method called daily to update the list of webwork
	 *         courses. The static initializer method.
	 * 
	 */
	public static void configChanged() {

		try {
			/*
			 * URI location of our building block. The name, vendor name and
			 * root folder should be in messages.properties file. Check the file
			 * settings.
			 */
			buildingBlockURI = PlugInUtil.getUri(constantVendorName,
					constantBuildingBlockName, constantRootFolder);

			/* All persister and loaders from blackboard */
			courseMembershipLoader = CourseMembershipDbLoader.Default
					.getInstance();
			persistenceManager = PersistenceServiceFactory.getInstance()
					.getDbPersistenceManager();
			gradePersister = (LineitemDbPersister) persistenceManager
					.getPersister(LineitemDbPersister.TYPE);
			gradeLineItemDb = (LineitemDbLoader) persistenceManager
					.getLoader(LineitemDbLoader.TYPE);
			contentPersister = ContentDbPersister.Default.getInstance();
			contentDb = ContentDbLoader.Default.getInstance();
			courseUserDb = UserDbLoader.Default.getInstance();
			scoreLoader = ScoreDbLoader.Default.getInstance();
			scorePersister = ScoreDbPersister.Default.getInstance();

			/*
			 * A static calendar variable to set refresh daily the static
			 * variables. The checking is done in the constructor.
			 */
			refreshStaticVariables = Calendar.getInstance();
			LogServiceFactory.getInstance().logDebug(
					"Changing blackboard config.");
		} catch (Exception exc) {
			LogServiceFactory.getInstance().logError(
					"Exception during loading BlackboardUtil static function.");
		}
	}

	/**
	 * @constructor Constructor having initialization for daily changes check.
	 *              Everyday resets the courses list in the WebworkUtil class.
	 *              Check error / debug logs for output.
	 * 
	 */
	public BlackboardUtil() {
		super();
		Calendar whetherToRefresh = Calendar.getInstance();

		/*
		 * Checking two time objects. If the request made by a user (student /
		 * instructor) with the plugin is atleast a day more than the previous
		 * request, or any request after a day time with the static object,
		 * shoots up the if function and executes the static initalizer block.
		 */
		if (((long) (whetherToRefresh.getTimeInMillis() - refreshStaticVariables
				.getTimeInMillis()) / (24 * 60 * 60 * 1000)) > 0) {
			BlackboardUtil.refreshStaticVariables = Calendar.getInstance();
			try {
				WebworkUtil.resetDaily();
			} catch (RemoteException re) {
				LogServiceFactory.getInstance().logError(
						"Could not reset all courses static variable.");
			}
			LogServiceFactory.getInstance().logDebug(
					"Called the config changed method.");
		}
	}

	/**
	 * Method sets the enrolledUserList to the List of User. Usually loaded
	 * through loadbycourseid etc.
	 * 
	 * @param enrolledUserList
	 *            the List of enrolled users of course to set.
	 */
	public void setEnrolledUserList(List<User> enrolledUserList) {
		this.enrolledUserList = enrolledUserList;
	}

	/**
	 * Method gets a list of enrolled users in a course. This is used by the
	 * instructor in the grades page. Includes students, admins, instructors,
	 * TA's.
	 * 
	 * @return the enrolledUserList - List of enrolled users
	 * @throws PersistenceException
	 * @throws KeyNotFoundException
	 */
	public List<User> getEnrolledUserList() throws KeyNotFoundException,
			PersistenceException {
		if (this.enrolledUserList == null || this.enrolledUserList.isEmpty()) {
			this.setEnrolledUserList(courseUserDb
					.loadByCourseId(courseIdentity));
		}
		return enrolledUserList;
	}

	/**
	 * Method sets the enrolled student list of a course. Usually modified from
	 * enrolledUserList to get only student list. But this is List of String
	 * containing student name's as opposed to student User object.
	 * 
	 * @param enrolledStudentList
	 *            the enrolledStudentList to set
	 */
	public void setEnrolledStudentList(List<String> enrolledStudentList) {
		this.enrolledStudentList = enrolledStudentList;
	}

	/**
	 * Gets the list of enrolled student users for a course.
	 * 
	 * @return the enrolledStudentList
	 */
	public List<String> getEnrolledStudentList() {
		return enrolledStudentList;
	}

	/**
	 * Method sets the blackboard user from the context available in the jsp
	 * page through ctxId attribute of all bbnG tags. User information is
	 * required for all kinds of information.
	 * 
	 * @param blackboardUser
	 *            the blackboardUser to set
	 */
	public void setBlackboardUser(User blackboardUser) {
		this.blackboardUser = blackboardUser;
	}

	/**
	 * Method gets the blackboard User object. See the set method for more
	 * details.
	 * 
	 * @return the blackboardUser
	 */
	public User getBlackboardUser() {
		return blackboardUser;
	}

	/**
	 * Method sets the courseInstructor boolean. Usually set method is not used.
	 * See get method for details.
	 * 
	 * @param courseInstructor
	 *            the courseInstructor to set
	 */
	public void setCourseInstructor(boolean courseInstructor) {
		this.courseInstructor = courseInstructor;
	}

	/**
	 * Returns whether the user is in instructor mode or not. Currently checks
	 * an entitlement - COURSE_CONTROL_PANEL_VIEW which is usually available for
	 * instructors / TA in blackboard. Might need to change in the future
	 * according to the Blackboard changes.
	 * 
	 * @return the courseInstructor
	 */
	public boolean getCourseInstructor() {
		/*
		 * Returns whether the user has the proper entitlement usually available
		 * for instructors / TA.
		 */
		return SecurityUtil.userHasEntitlement(this.blackboardUser.getId(),
				this.courseIdentity, new Entitlement(
						PlugInConstants.COURSE_CONTROL_PANEL_VIEW));
	}

	/**
	 * Method to set the scoreAssignment object. This resembles the score in the
	 * blackboard.
	 * 
	 * @param scoreAssignment
	 *            the scoreAssignment to set
	 */
	public void setScoreAssignment(Score scoreAssignment) {
		this.scoreAssignment = scoreAssignment;
	}

	/**
	 * Method to get the scoreAssignment object. This resembles the score in the
	 * blackboard.
	 * 
	 * @return the scoreAssignment
	 */
	public Score getScoreAssignment() {
		return scoreAssignment;
	}

	/**
	 * Method which sets the AssignmentInfo Object. Usually this occurs in the
	 * loginwebwork.jsp or grades.jsp page. As AssignmentInfo is the only object
	 * which has the gradebookname, assignment name etc, it has to be attached
	 * as an object to be written to the blackboard Db for each user.
	 * 
	 * @param assignmentInfo
	 *            the assignmentInfo to set
	 */
	public void setAssignmentInfo(AssignmentInfo assignmentInfo) {
		this.assignmentInfo = assignmentInfo;
	}

	/**
	 * Method which gets the AssignmentInfo Object. See setAssignmentInfo method
	 * for details.
	 * 
	 * @return the assignmentInfo
	 */
	public AssignmentInfo getAssignmentInfo() {
		return assignmentInfo;
	}

	/**
	 * Method returns the Id of the course that the user is in currently. This
	 * is mostly required to redirect / writing grades / publishing announcement
	 * etc. this is required for both students as well as instructors in
	 * blackboard. This is being generated from courseNumber captured from the
	 * request params which is detailed in setCourseNumber method Doc.
	 * 
	 * @return the courseIdentity
	 */
	public Id getCourseIdentity() {
		return courseIdentity;
	}

	/**
	 * Method sets the courseId to an Id object. See getCourseIdentity method
	 * for proper details.
	 * 
	 * @param courseIdentity
	 *            the courseIdentity to set
	 */
	public void setCourseIdentity(Id courseIdentity) {
		this.courseIdentity = courseIdentity;
	}

	/**
	 * Method sets the courseNumber from the request param. Also, the CourseId
	 * is generated from the number to keep the information, within the util.
	 * 
	 * @param courseNumber
	 *            the courseNumber to set
	 * @throws PersistenceException
	 */
	public void setCourseNumber(String courseNumber)
			throws PersistenceException {
		this.setCourseIdentity(BlackboardUtil.persistenceManager.generateId(
				Course.DATA_TYPE, courseNumber));
		this.courseNumber = courseNumber;

	}

	/**
	 * Method returns the course number captured from the request param. See the
	 * set method to get proper details.
	 * 
	 * @return the courseNumber
	 */
	public String getCourseNumber() {
		return courseNumber;
	}

	/**
	 * Method sets the contentModify to a Blackboard Content object. Usually
	 * used in modify.jsp to modify the content information. See get method doc
	 * for more information.
	 * 
	 * @param contentModify
	 */
	public void setContentModify(Content contentModify) {
		this.contentModify = contentModify;
	}

	/**
	 * Method gets the content Modify Blackboard Content object. This is usually
	 * loaded from the Content number parameter passed usually with the
	 * blackboard content objects. This is used with modify.jsp page for
	 * modifying th content information.
	 * 
	 * @return
	 * @throws KeyNotFoundException
	 * @throws PersistenceException
	 */
	public Content getContentModify() throws KeyNotFoundException,
			PersistenceException {
		this.setContentModify(BlackboardUtil.contentDb
				.loadById(BlackboardUtil.persistenceManager.generateId(
						CourseDocument.DATA_TYPE, this.contentNumber)));
		return contentModify;
	}

	/**
	 * Method which sets the content number from the request params. This is
	 * usually required for all kinds of blackboard information like
	 * announcement creation, gradebook creation, content publishment etc.
	 * 
	 * @param contentNumber
	 *            the contentNumber to set
	 */
	public void setContentNumber(String contentNumber) {
		this.contentNumber = contentNumber;
	}

	/**
	 * Method gets the content number which is written from the request param.
	 * See set method doc for more details.
	 * 
	 * @return the contentNumber
	 */
	public String getContentNumber() {
		return contentNumber;
	}

	/**
	 * Method sets the PublishData object to BlackboardUtil. Usually this is
	 * required as all of the information user enters in the assignments.jsp
	 * page is captured into PublishData object. This is how, the information is
	 * written into Blackboard.
	 * 
	 * @param publishData
	 *            the publishData to set
	 */
	public void setPublishData(PublishData publishData) {
		this.publishData = publishData;
	}

	/**
	 * Method gets the PublishData object which is set in the publish.jsp page.
	 * See the set method for more details.
	 * 
	 * @return the publishData
	 */
	public PublishData getPublishData() {
		return publishData;
	}

	/**
	 * Method sets the course contents url to the string passed. (Usually the
	 * string is of blackboard url, generated from blacboard constant util
	 * class. See get method for more details.
	 * 
	 * @param courseContentsUrl
	 *            the courseContentsUrl to set
	 */
	public void setCourseContentsUrl(String courseContentsUrl) {
		this.courseContentsUrl = courseContentsUrl;
	}

	/**
	 * Method sets the course contents url variable to the course and the
	 * content return url of the blackboard. If both these numbers - course and
	 * content is not available then, an empty string is set as the url. Beware
	 * of this in the frontend pages. Usually used in redirections inside the
	 * plugin.
	 * 
	 * @return the courseContentsUrl
	 */
	public String getCourseContentsUrl() {
		/*
		 * Take the request URL and strip of the URI thus having the host ip
		 * address. Then add the plugin url for the course and content that the
		 * user is in.
		 */
		this.setCourseContentsUrl(contentNumber != null ? (courseNumber != null ? this.requestUrl
				.substring(0, this.requestUrl.indexOf(this.requestUri))
				+ PlugInUtil.getEditableContentReturnURL(contentNumber,
						courseNumber) : Messages.getString("Default.Empty"))
				: Messages.getString("Default.Empty"));
		return courseContentsUrl;
	}

	/**
	 * Method sets the webwork Grades to an array of grades received from
	 * Webwork. This is the raw grades received.
	 * 
	 * @param webworkGrades
	 *            the webworkGrades to set
	 */
	public void setWebworkGrades(String[] webworkGrades) {
		this.webworkGrades = webworkGrades;
	}

	/**
	 * Method returns the webworkGrades array variable. See set method for more
	 * details.
	 * 
	 * @return the webworkGrades
	 */
	public String[] getWebworkGrades() {
		return webworkGrades;
	}

	/**
	 * Method sets the requestUrl string to the string passed. Usually this is
	 * captured from the pageContext object.
	 * 
	 * @param requestUrl
	 *            the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * Method returns the request url captured through pageContext object.
	 * 
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * Method sets the requestUri string to the string passed. Usually this is
	 * captured from the pageContext object.
	 * 
	 * @param requestUri
	 *            the requestUri to set
	 */
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	/**
	 * Method returns the request uri captured through pageContext object.
	 * 
	 * @return the requestUri
	 */
	public String getRequestUri() {
		return requestUri;
	}

	/**
	 * Calls the generic method persistGradesToBlackboard which does the
	 * functionality. Usually this method is called from loginwebwork.jsp by a
	 * student to set his own grade. Thus, the Id of the student and his name is
	 * passed to the generic method to write the grades.
	 * 
	 * @param writeGrades
	 *            the writeGrades to set
	 * @throws RemoteException
	 * @throws ValidationException
	 */
	public void setWriteGrades(boolean writeGrades) throws RemoteException,
			ValidationException {
		if (writeGrades) {
			this.writeGrades = this
					.persistGradesToBlackboard(Arrays
							.asList(new Id[] { this.blackboardUser.getId() }),
							Arrays.asList(new String[] { this.blackboardUser
									.getUserName() }));
		}
	}

	/**
	 * Method sets the student grades. Usually called from an instructor
	 * context. Right now only used in grades.jsp. Calls the generic method
	 * persistGradesToBlackboard which does the functionality. Here the list of
	 * enrolled users are fetched for the course, and then only student users of
	 * the course are captured and sent to the generic method. This is to
	 * prevent instructors / TA / Admin to be excluded from the grades.
	 * 
	 * @param studentsGrades
	 *            boolean to indicate whether to get student grades and write
	 *            them.
	 * @throws PersistenceException
	 * @throws KeyNotFoundException
	 */
	public void setStudentsGrades(boolean studentsGrades)
			throws KeyNotFoundException, PersistenceException {
		if (studentsGrades) {
			ArrayList<Id> enrolledUserListId = new ArrayList<Id>();
			ArrayList<String> enrolledUserListName = new ArrayList<String>();

			/*
			 * Checks if the user is student, and then adds it to the temp list.
			 */
			for (User eachEnrolledUser : this.getEnrolledUserList()) {
				if (((CourseMembership) courseMembershipLoader
						.loadByCourseAndUserId(this.courseIdentity,
								eachEnrolledUser.getId())).getRole().equals(
						CourseMembership.Role.STUDENT)) {
					enrolledUserListId.add(eachEnrolledUser.getId());
					enrolledUserListName.add(eachEnrolledUser.getUserName());
				}
			}
			this.setEnrolledStudentList(enrolledUserListName);
			this.studentsGrades = this.persistGradesToBlackboard(
					enrolledUserListId, enrolledUserListName);
		}
	}

	/**
	 * Method which writes the grades to the Blackboard grades. First tries to
	 * load the score available in the gradebook center, if not creates a new
	 * score. Also the gradebookname is captured in the request param for each
	 * assignment. The grades are captured from the webwork Db using
	 * grade_user_sets webservice function. (If the function name / parameter
	 * changes change the details in this method too for proper functioning.)
	 * Sets the return boolean value to success of the method function.
	 * 
	 * @param userIdList
	 *            - List of user Id's to load the score info.
	 * @param userName
	 *            - List of user name's in blackboard to load and set score.
	 * @return whether all data written successfully or not.
	 */
	private synchronized boolean persistGradesToBlackboard(List<Id> userIdList,
			List<String> userNameList) {

		String[] struserNameList = null;

		boolean success = true;
		try {

			/*
			 * Loading the grade book line items having the course Id as parent
			 * Id.
			 */
			ArrayList<Lineitem> gradeBookOriginalLineItems = BlackboardUtil.gradeLineItemDb
					.loadByCourseId(courseIdentity);

			/*
			 * Creating an array from the list - since, .toArray() returns null
			 * sometimes. Web-work accepts arrays not list.
			 */
			struserNameList = new String[userNameList.size()];
			for (String userName : userNameList)
				struserNameList[userNameList.indexOf(userName)] = userName;

			/* Get the grades from the web-work */
			String[] tempGrades = WebworkUtil.soapHandler.grade_users_sets(
					WebworkUtil.webworkSoapAuthKey,
					this.assignmentInfo.getAssignmentCourse(), struserNameList,
					this.assignmentInfo.getAssignmentSet());

			/* Set the grades to an array variable in the util class */
			this.studentsGradesScores = new String[userIdList.size()];
			int index = 0;

			/*
			 * For each user, set the score in the blackboard to the value from
			 * web-work. If the score does'nt exist in the blackboard create a
			 * new one. If null is returned, just set a 0 to the score. Capture
			 * the grade information to the util array variable by reading from
			 * the blackboard score, this also turns out to be a cross check.
			 */
			for (Id eachUserId : userIdList) {
				try {

					Id courseMembershipId = ((CourseMembership) courseMembershipLoader
							.loadByCourseAndUserId(this.courseIdentity,
									eachUserId)).getId();

					for (Lineitem eachLineItem : gradeBookOriginalLineItems) {
						if (eachLineItem.getName().equals(
								this.assignmentInfo.getGradebookname())) {
							try {
								this.scoreAssignment = scoreLoader
										.loadByCourseMembershipIdAndLineitemId(
												courseMembershipId,
												eachLineItem.getId());
							} catch (Exception e) {
								this.scoreAssignment = new Score();
								this.scoreAssignment
										.setCourseMembershipId(courseMembershipId);
								this.scoreAssignment.setLineitemId(eachLineItem
										.getId());
							}

							try {
								if (Integer.parseInt(tempGrades[index]) > 0)
									this.scoreAssignment
											.setGrade(tempGrades[index]);
								else
									this.scoreAssignment.setGrade("0");
							} catch (NumberFormatException nfe) {
								this.scoreAssignment.setGrade("0");
							}

							this.studentsGradesScores[index++] = this.scoreAssignment
									.getGrade();
							BlackboardUtil.scorePersister
									.persist(scoreAssignment);

						}
					}
				} catch (KeyNotFoundException exc) {
					LogServiceFactory
							.getInstance()
							.logError(
									"Logging Key not found exception when reading course membership record.",
									exc);
					success = false;
					continue;
				} catch (PersistenceException pe) {
					LogServiceFactory
							.getInstance()
							.logError(
									"Logging persistence exception when reading course membership record.");
					success = false;
					continue;
				} catch (ValidationException ve) {
					LogServiceFactory
							.getInstance()
							.logError(
									"Logging validation exception when writing grades to blackboard.");
					success = false;
					continue;
				}
			}

			this.setWebworkGrades(tempGrades);

		} catch (KeyNotFoundException knfe) {
			LogServiceFactory
					.getInstance()
					.logError(
							"Logging Key not found exception when reading gradebook line items records.");
			success = false;
		} catch (PersistenceException pe) {
			LogServiceFactory
					.getInstance()
					.logError(
							"Logging persistence exception when reading gradebook line items records.");
			success = false;
		} catch (RemoteException re) {
			LogServiceFactory.getInstance().logError(
					"Logging remote exception when reading webwork grades");
			success = false;
		} catch (Exception exc) {
			LogServiceFactory.getInstance()
					.logError(
							"Logging other exception: \n"
									+ struserNameList.length, exc);
		}
		return success;
	}

	/**
	 * Method returns the boolean varibale to check if the student grade is
	 * written to the Blackboard. Based on this value, accordingly, the student
	 * are given proper information.
	 * 
	 * @return the writeGrades
	 */
	public boolean getWriteGrades() {
		return writeGrades;
	}

	/**
	 * Method returns the boolean variable to check whether student grades are
	 * written successfully or not. This is the value returned from the generic
	 * method persistGradesToBlackboard.
	 * 
	 * @return the studentsGrades
	 */
	public boolean getStudentsGrades() {
		return studentsGrades;
	}

	/**
	 * Method sets the student grades to an array of string containing the
	 * grades from the blackboard Db.
	 * 
	 * @param studentsGradesScores
	 *            the studentsGradesScores to set
	 */
	public void setStudentsGradesScores(String[] studentsGradesScores) {
		this.studentsGradesScores = studentsGradesScores;
	}

	/**
	 * Method returns the list of student grades. This is the value of the
	 * blackboard grades, after writing the grades from webwork. This might not
	 * return some grades, when there were exceptions. Usually this is not the
	 * case.
	 * 
	 * @return the studentsGradesScores
	 */
	public String[] getStudentsGradesScores() {
		return studentsGradesScores;
	}

	/**
	 * This method adds the current new assignment to the grade book. Returns
	 * false if one of the records successfully not added. Returns true if all
	 * assignments selected in assignments.jsp are successfully added. Also if
	 * grade book addition is success the content item is also added into
	 * Blackboard DB. This is the main method which writes data presented in the
	 * assignments.jsp page to blackboard. Annoucement, tracking information
	 * etc. all are set in this method. Comments should clarify other required
	 * details.
	 * 
	 * @param publishAssignmentsData
	 *            the publishAssignmentsData to set
	 * @throws PersistenceException
	 * @throws ValidationException
	 * @throws ParseException
	 */
	public void setPublishAssignmentsData(boolean publishAssignmentsData)
			throws PersistenceException, ValidationException, ParseException {
		if (publishAssignmentsData) {

			// TEST
			SimpleDateFormat constantDateFormat2 = new SimpleDateFormat(
					Messages.getString("BlackboardUtil.DateFormat")
							+ " HH:mm aa");

			/* Setting a temp variable to capture any error occured */
			boolean failure = false;
			boolean alreadyPublished;
			if (this.publishData.getPublishedRecord() == null
					|| this.publishData.getPublishedRecord().isEmpty())
				alreadyPublished = false;
			else
				alreadyPublished = true;

			/* If it is to modify a content, or to create a new content. */
			if (!this.publishData.getModifyRecord()) {
				List<Lineitem> gradeBookOriginalLineItems = BlackboardUtil.gradeLineItemDb
						.loadByCourseId(courseIdentity);

				/* Local variable to capture which assignment failed. */
				List<Boolean> tempRecordsSaved = null;
				if (!alreadyPublished)
					tempRecordsSaved = new ArrayList<Boolean>();
				else
					tempRecordsSaved = this.publishData.getPublishedRecord();

				/* Course And Content Id's */
				Id generatedCourseId = persistenceManager.generateId(
						Course.DATA_TYPE, this.courseNumber);
				Id generatedParentId = persistenceManager.generateId(
						CourseDocument.DATA_TYPE, this.contentNumber);

				/* Calendar for startDate and endDate of assignment */
				Calendar calendarStartObject = Calendar.getInstance();
				Calendar calendarEndObject = Calendar.getInstance();

				for (int assignmentNumber = 0; assignmentNumber < this.publishData
						.getName().length; assignmentNumber++) {

					if (!alreadyPublished)
						tempRecordsSaved.add(false);

					if (!tempRecordsSaved.get(assignmentNumber)) {
						/* Course and Announcement objects. */
						CourseDocument contentCourseDocument = new CourseDocument();
						Announcement contentAnnouncement = new Announcement();

						/* Change comments to FormattedText as required by BB */
						FormattedText comments = new FormattedText(
								this.publishData.getAssignmentComments()[assignmentNumber],
								FormattedText.Type.HTML);

						/*
						 * Create the URL with message information and other
						 * params.
						 */
						String assignmentUrl = Messages
								.getString("BlackboardUtil.3")
								+ buildingBlockURI
								+ Messages.getString("BlackboardUtil.4")
								+ constantWebworkPageLink
								+ constantAssignmentInfoCourseParam
								+ this.publishData.getCourseName().get(
										assignmentNumber)
								+ constantAssignmentInfoSetParam
								+ this.publishData.getSetName().get(
										assignmentNumber)
								+ constantAssignmentInfoContentIdParam
								+ this.contentNumber
								+ constantAssignmentInfoCourseIdParam
								+ this.courseNumber
								+ constantAssignmentGradeBookNameParam
								+ this.publishData.getName()[assignmentNumber]
								+ Messages.getString("BlackboardUtil.5")
								+ this.publishData.getName()[assignmentNumber]
								+ Messages.getString("BlackboardUtil.6");

						/*
						 * ALL Content Information - user preference and normal
						 * ones. Dates are captured from the web-work.
						 */
						contentCourseDocument.setTitle(assignmentUrl);
						contentCourseDocument.setBody(comments);
						contentCourseDocument
								.setContentHandler(constantContentHandlerType);
						contentCourseDocument.setCourseId(generatedCourseId);
						contentCourseDocument.setParentId(generatedParentId);
						contentCourseDocument.setIsLesson(true);
						contentCourseDocument.setIsFolder(false);
						contentCourseDocument.setIsAvailable(this.publishData
								.getIsAvailable()[assignmentNumber]);
						contentCourseDocument.setIsTracked(this.publishData
								.getIsTracked()[assignmentNumber]);
						calendarStartObject
								.setTime(constantDateFormat2
										.parse(this.publishData.getStartDate()[assignmentNumber]));
						contentCourseDocument.setStartDate(calendarStartObject);
						calendarEndObject
								.setTime(constantDateFormat2
										.parse(this.publishData.getEndDate()[assignmentNumber]));
						contentCourseDocument.setEndDate(calendarEndObject);
						contentCourseDocument.setLaunchInNewWindow(true);

						/*
						 * Announcement Information and Link. The Announcment
						 * message is in message.properties. The dates and other
						 * critical information are displayed in the
						 * annoucement.
						 */
						if (this.publishData.getCreateAnnouncement()[assignmentNumber]) {

							/* Announcement information formatted in string */
							String arguments[] = {
									this.publishData.getCourseName().get(
											assignmentNumber)
											+ Messages
													.getString("BlackboardUtil.7")
											+ this.publishData.getSetName()
													.get(assignmentNumber),
									this.publishData.getStartDate()[assignmentNumber],
									this.publishData.getEndDate()[assignmentNumber] };
							String announcementInfo = String.format(
									constantAnnouncementPublishTextBody,
									(Object[]) arguments);

							/*
							 * Annoucement title selected from the name of
							 * assignment. End date set to the end date of the
							 * content. Other mandatory information are also
							 * presented in here.
							 */
							String announcementTitle = String.format(
									constantAnnouncementPublishTextTitle,
									(Object[]) new String[] { this.publishData
											.getName()[assignmentNumber] });
							Calendar announcementEndDate = Calendar
									.getInstance();
							announcementEndDate
									.setTime(constantDateFormat2.parse(this.publishData
											.getEndDate()[assignmentNumber]));

							contentAnnouncement.setTitle(announcementTitle);
							contentAnnouncement.setBody(new FormattedText(
									announcementInfo,
									FormattedText.Type.DEFAULT));
							contentAnnouncement.setCourseId(generatedCourseId);
							// contentAnnouncement.setRestrictionStartDate(announcementEndDate);
							contentAnnouncement
									.setType(Announcement.Type.COURSE);
							contentAnnouncement
									.setCreatorUserId(this.blackboardUser
											.getId());
						}

						/*
						 * Temp variable to check if the gradebook item with the
						 * same name exists. If exists, we have to refurbish the
						 * assignment with some other name of the assignment.
						 * This does'nt happen with 9.1 version though. If any
						 * record fails set the flag failure to refurbish the
						 * assignment name.
						 */
						boolean recordExists = false;

						for (Lineitem eachLineItem : gradeBookOriginalLineItems) {
							if (eachLineItem.getName().equals(
									this.publishData.getGradeBookItems()
											.get(assignmentNumber).getName())) {
								recordExists = true;
								failure = true;
							}
						}

						/* Log the value for loop through check. */
						if (recordExists)
							tempRecordsSaved.set(assignmentNumber, false);
						else
							tempRecordsSaved.set(assignmentNumber, true);

						/* Persist the single grade book item to Blackboard */
						if (tempRecordsSaved.get(assignmentNumber)) {
							try {
								BlackboardUtil.contentPersister
										.persist(contentCourseDocument);
								if (this.publishData.getCreateAnnouncement()[assignmentNumber])
									AnnouncementManagerFactory.getInstance()
											.saveAnnouncement(
													contentAnnouncement, null);
							} catch (ValidationException pe) {
								LogServiceFactory
										.getInstance()
										.logError(
												"Error cannot validate the content / annoucement written to blackboard.");
								failure = true;

							} catch (PersistenceException pe) {
								LogServiceFactory
										.getInstance()
										.logError(
												"Error cannot persist the content / announcement written to blackboard.");
								failure = true;
							}
							if (!failure)
								gradeBookOriginalLineItems.add(this.publishData
										.getGradeBookItems().get(
												assignmentNumber));
							LogServiceFactory
									.getInstance()
									.logError(
											"this is the type of the item: "
													+ this.getPublishData()
															.getType()[assignmentNumber]);
						}
					}
				}
				try {
					BlackboardUtil.gradePersister
							.persist(gradeBookOriginalLineItems);
				} catch (ValidationException pe) {
					LogServiceFactory
							.getInstance()
							.logError(
									"Error cannot validate the gradebook written to blackboard.");
					failure = true;

				} catch (PersistenceException pe) {
					LogServiceFactory
							.getInstance()
							.logError(
									"Error cannot persist the gradebook written to blackboard.");
					failure = true;
				}

				this.publishData.setPublishedRecord(tempRecordsSaved);
			}
			/*
			 * Modify Content information. Same except for some field changes
			 * like bodytext title etc. Also all items should refer to array
			 * index 0.
			 */
			else {
				Calendar calendarStartObject = Calendar.getInstance();
				Calendar calendarEndObject = Calendar.getInstance();

				Calendar oldStartObject = this.contentModify.getStartDate();
				Calendar oldEndObject = this.contentModify.getEndDate();

				/* Adding to initial date list */
				ArrayList<String> tempStartDate = new ArrayList<String>();
				ArrayList<String> tempEndDate = new ArrayList<String>();

				tempStartDate.add(constantDateFormat.format(this.contentModify
						.getStartDate().getTime()));
				this.publishData.setAssignmentsInitialStartDate(tempStartDate);

				tempEndDate.add(constantDateFormat.format(this.contentModify
						.getEndDate().getTime()));
				this.publishData.setAssignmentsInitialEndDate(tempEndDate);

				/* Modifying content information */
				calendarStartObject.setTime(constantDateFormat2
						.parse(this.publishData.getStartDate()[0]
								+ Messages.getString("Default.Space")
								+ this.publishData.getStartTime()));
				if (oldStartObject.compareTo(calendarStartObject) != 0)
					this.contentModify.setStartDate(calendarStartObject);

				calendarEndObject.setTime(constantDateFormat2
						.parse(this.publishData.getEndDate()[0]
								+ Messages.getString("Default.Space")
								+ this.publishData.getStartTime()));
				if (oldEndObject.compareTo(calendarEndObject) != 0)
					this.contentModify.setEndDate(calendarEndObject);

				this.contentModify.setIsAvailable(Boolean
						.valueOf(this.publishData.getIsAvailable()[0]));
				this.contentModify.setIsTracked(Boolean
						.valueOf(this.publishData.getIsTracked()[0]));
				this.contentModify.setBody(FormattedText
						.toFormattedText(this.publishData.getBodytext()));
				this.contentModify.setTitle(this.publishData.getTitle());

				BlackboardUtil.contentPersister.persist(this.contentModify);
			}

			writeWebworkChanges();

			this.publishAssignmentsData = !failure;
		}
	}

	/**
	 * Method returns the boolean value which signifies whether all data are
	 * published successfully or not. See the get method for more details.
	 * 
	 * @return the publishAssignmentsData
	 */
	public boolean getPublishAssignmentsData() {
		return publishAssignmentsData;
	}

	/**
	 * Method write changes to wrbwork server. Any change in the start date of
	 * the assignment, end date of the assignment, partial credit enabling, this
	 * is written over to the webwork.
	 */
	public boolean writeWebworkChanges() {
		for (int loopVar = 0; loopVar < this.publishData.getStartDate().length; loopVar++) {
			if (this.publishData.getAssignmentsInitialStartDate().isEmpty()
					|| this.publishData.getAssignmentsInitialEndDate()
							.isEmpty()
					|| !this.publishData.getAssignmentsInitialStartDate()
							.get(loopVar)
							.equals(this.publishData.getStartDate()[loopVar])
					|| !this.publishData.getAssignmentsInitialEndDate()
							.get(loopVar)
							.equals(this.publishData.getEndDate()[loopVar])) {
				try {
					return WebworkUtil.writeChanges(this.blackboardUser
							.getUserName(), this.publishData.getCourseName()
							.get(loopVar),
							this.publishData.getSetName().get(loopVar),
							this.publishData.getStartDate()[loopVar],
							this.publishData.getEndDate()[loopVar], null, 0);
				} catch (ParseException e) {
					return false;
				}
			}
		}
		return false;

		// TODO Partial credit feature, after enabled in webwork.
		/*
		 * int index = 0; if (this.publishData.getEnablePartial() != null) for
		 * (String eachPartialEnable : this.publishData.getEnablePartial())
		 * WebworkUtil.writeChanges( this.blackboardUser.getUserName(),
		 * this.publishData.getCourseName().get(
		 * Integer.parseInt(eachPartialEnable)),
		 * this.publishData.getSetName().get(
		 * Integer.parseInt(eachPartialEnable)), null, null,
		 * this.publishData.getPartialDate()[index],
		 * this.publishData.getPartialPercent()[index++]);
		 */
	}
}
