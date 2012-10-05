/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.rmi.RemoteException;

import blackboard.data.user.User;
import blackboard.platform.plugin.PlugInException;

/**
 * @author Srinivasan Devanathan
 * @category AssignmentInfo Bean Class - Useful to get information from the
 *           request params and login to the webwork. Used in all
 *           loginwebwork.jsp page in the blackboard plugin.
 * @version 2.0
 * 
 *          Class used by most of the users at a time. Served as session info
 *          object for a specific webwork content assignment.
 * 
 */
public class AssignmentInfo extends BuildingBlockMethods {

	/**
	 * Variable to hold the session key of webwork launched through blackboard
	 * buidling block.
	 */
	private String loginKey = null;
	/**
	 * Course name in webwork captured through request param of the content
	 * item.
	 */
	private String assignmentCourse = null;
	/**
	 * Set name in webwork captured through request param of the content item.
	 */
	private String assignmentSet = null;
	/**
	 * The user name of the current user. Used in many places.
	 */
	private String assignmentUser = null;
	/**
	 * The url of the webwork page set according to other variables.
	 */
	private String assignmentUrl = null;
	/**
	 * Gradebook name in blackboard to which grades should be written.
	 */
	private String gradebookname = null;
	
	private boolean newAssignmentUrl;
	
	private User blackboardUser;

	/**
	 * Constructor - Default. No initializations made.
	 */
	public AssignmentInfo() {
	}

	/**
	 * Method used to set the login key of the webwork session to the loginKey
	 * variable of the class. See set method for details.
	 * 
	 * @param loginKey
	 *            the loginKey to set
	 */
	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	/**
	 * Method used to get the login key for the current user, and the specific
	 * assignment set. The method uses BlackboardBuildingMethods - getLoginKey
	 * method.
	 * 
	 * @return the loginKey - String representing the valid session in the
	 *         webwork.
	 * @throws RemoteException
	 *             Exception which is thrown if login key cannot be received
	 *             from webwork, or any other webservice error.
	 */
	public String getLoginKey() throws RemoteException {
		this.setLoginKey(BuildingBlockMethods.getLoginKey(assignmentCourse, assignmentSet, blackboardUser));
		return loginKey;
	}

	/**
	 * Method which sets the request parameter into class variable which
	 * represents the course info in webwork for the current content item in
	 * blackboard. Bean's set property with * is used for this in
	 * loginwebwork.jsp.
	 * 
	 * @param assignmentCourse
	 *            the assignmentCourse to set
	 */
	public void setAssignmentCourse(String assignmentCourse) {
		this.assignmentCourse = assignmentCourse;
	}

	/**
	 * Method which returns the course for the current content item in the
	 * webwork. See set method for details.
	 * 
	 * @return the assignmentCourse
	 */
	public String getAssignmentCourse() {
		return assignmentCourse;
	}

	/**
	 * Method which sets the request parameter into class variable which
	 * represents the set info in webwork for the current content item in
	 * blackboard. Bean's set property with * is used for this in
	 * loginwebwork.jsp.
	 * 
	 * @param assignmentSet
	 *            the assignmentSet to set
	 */
	public void setAssignmentSet(String assignmentSet) {
		this.assignmentSet = assignmentSet;
	}

	/**
	 * See the set method for detials.
	 * 
	 * @return the assignmentSet
	 */
	public String getAssignmentSet() {
		return assignmentSet;
	}

	/**
	 * Method which sets the class variable to the user set through set property
	 * method in loginwebwork.jsp.
	 * 
	 * @param assignmentUser
	 *            the assignmentUser to set
	 */
	public void setAssignmentUser(String assignmentUser) {
		this.assignmentUser = assignmentUser;
	}

	/**
	 * Method which returns current user's name. See the set method for details.
	 * 
	 * @return the assignmentUser
	 */
	public String getAssignmentUser() {
		return assignmentUser;
	}

	/**
	 * Method which sets the class variable. See the set method for details.
	 * 
	 * @param assignmentUrl
	 *            the assignmentUrl to set
	 */
	public void setAssignmentUrl(String assignmentUrl) {
		this.assignmentUrl = assignmentUrl;
	}

	/**
	 * Method which creates a url based on the webwork server url, and the
	 * present content item's course, set info from the request param, and the
	 * login key for the present user. The url in webwork server is of the form
	 * - http://webworksite/course/set?user=username&key=Key
	 * 
	 * @return the assignmentUrl
	 * @throws RemoteException
	 */
	public String getAssignmentUrl() {
		return this.assignmentUrl;
	}
	
	public void setNewAssignmentUrl(boolean newAssignmentUrl) throws RemoteException {
		if(newAssignmentUrl)
		{
			String key = this.getLoginKey();
			this.setAssignmentUrl(WebworkUtil.webworkSiteUrl + this.assignmentCourse + "/" + this.assignmentSet + constantWebworkSiteUserParam + 
				this.assignmentUser + constantWebworkSiteKeyParam + key);
		}
		this.newAssignmentUrl = newAssignmentUrl;
	}

	/**
	 * Method which gets the boolean attribute of whether guest viewers can view
	 * the content item. Right now all of them can view the content item.
	 * 
	 * @return the allowGuestViewers
	 * @throws PlugInException
	 */
	public boolean getAllowGuestViewers() throws PlugInException {
		return ConfigFile.getStaticAllowGuestViewers();
	}

	/**
	 * Method which sets the class variable from the request param. The variable
	 * is used to find the gradebook name, so that the grades can be written to
	 * that name. This is used for both instructor as well as the students.
	 * 
	 * @param gradebookname
	 *            the gradebookname to set
	 */
	public void setGradebookname(String gradebookname) {
		this.gradebookname = gradebookname;
	}

	/**
	 * Method returns the gradebookname set through the request param. See the
	 * set method for more details.
	 * 
	 * @return the gradebookname
	 */
	public String getGradebookname() {
		return gradebookname;
	}

	public void setBlackboardUser(User blackboardUser){
		this.blackboardUser = blackboardUser;
	}
	
	public User getBlackboardUser(){
		return blackboardUser;
	}
}
