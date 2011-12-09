/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.text.SimpleDateFormat;
import java.util.*;

import blackboard.platform.log.LogServiceFactory;

import edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet;

/**
 * @author Srinivasan Devanathan
 * @category WebworkData Class - Used to store user selections in the first two
 *           pages - courses.jsp & sets.jsp of the building block. Also captures
 *           other information like assignment points, start date & end date
 *           from webwork to synchronize between blackboard and webwork.
 * @version 2.0
 * 
 */
public class WebworkData extends BuildingBlockMethods {

	/**
	 * Variable for capturing the request param of courses.
	 */
	private String[] selectedWebworkCoursesParam;
	/**
	 * Variable for capturing the request param of sets.
	 */
	private String[] selectedWebworkSetsParam;
	/**
	 * Variable of selected courses list.
	 */
	private List<String> selectedCourses;
	/**
	 * Variable holding list of sets for selected courses.
	 */
	private List<String> listWebworkSetsForCourses;
	/**
	 * Variable holding list of selected sets.
	 */
	private List<String> selectedWebworkSets;

	/**
	 * Variable to hold the due dates for the selected sets.
	 */
	private List<String> selectedWebworkSetsDueDate;
	/**
	 * Variable to hold the open dates for the selected sets.
	 */
	private List<String> selectedWebworkSetsOpenDate;
	/**
	 * Map variable containing selected sets mapping to the courses.
	 */
	private List<String> selectedWebworkSetsMap;
	/**
	 * Map variable containing selected courses mapping to the sets.
	 */
	private List<String> selectedWebworkCoursesMap;
	/**
	 * Flag which captures information about the selected sets.
	 */
	private boolean captureWebworkData;
	/**
	 * Variable containing the grade points for each assignment.
	 */
	private List<Integer> assignmentPoints;

	/**
	 * Instantiate an object to hold all user (instructor) information selected
	 * in the front-end in courses.jsp, sets.jsp.
	 */
	public WebworkData() {
	}

	/**
	 * Method used to capture the user selection of courses. When the selected
	 * courses are captured, they are converted to array list for proper display
	 * purpose. Usually called after courses.jsp in sets.jsp
	 * 
	 * @see selectedCourses
	 * @param selectedWebworkCourses
	 *            user selected courses from the request.
	 */
	public void setSelectedWebworkCoursesParam(
			String[] selectedWebworkCoursesParam) {
		this.selectedWebworkCoursesParam = selectedWebworkCoursesParam;
		/* Selected courses are converted to list for display purposes. */
		this.selectedCourses = Arrays.asList(selectedWebworkCoursesParam);
	}

	/**
	 * Return the user selected webwork courses list.
	 * 
	 * @return the selectedWebworkCourses
	 */
	public String[] getSelectedWebworkCoursesParam() {
		return selectedWebworkCoursesParam;
	}

	/**
	 * Method used to capture the user selection of assignment sets. When the
	 * selected sets are captured, they are converted to array list for proper
	 * display purpose. Usually called after sets.jsp in assignments.jsp
	 * 
	 * @param selectedWebworkSetsParam
	 *            the selectedWebworkSetsParam to set
	 */
	public void setSelectedWebworkSetsParam(String[] selectedWebworkSetsParam) {
		this.selectedWebworkSetsParam = selectedWebworkSetsParam;
		/* Selected sets are converted to list for display purposes. */
		this.selectedWebworkSets = Arrays.asList(selectedWebworkSetsParam);
	}

	/**
	 * Return the user selected webwork sets list.
	 * 
	 * @return the selectedWebworkSetsParam
	 */
	public String[] getSelectedWebworkSetsParam() {
		return selectedWebworkSetsParam;
	}

	/**
	 * Method usually called internally when the courses list are captured from
	 * the request parameters.
	 * 
	 * @see setSelectedWebworkCoursesParam
	 * @param selectedCourses
	 *            list of courses to set.
	 */
	public void setSelectedCourses(List<String> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	/**
	 * Retrieves the array list of user selected courses for proper display.
	 * 
	 * @return the selectedCourses
	 */
	public List<String> getSelectedCourses() {
		return selectedCourses;
	}

	/**
	 * Method usually called internally when the sets list are captured from the
	 * request parameters.
	 * 
	 * @param selectedWebworkSets
	 *            the selectedWebworkSets to set
	 */
	public void setSelectedWebworkSets(List<String> selectedWebworkSets) {
		this.selectedWebworkSets = selectedWebworkSets;
	}

	/**
	 * Retrieves the array list of user selected sets for proper display.
	 * 
	 * @return the selectedWebworkSets Method returns a new ArrayList if empty /
	 *         null.
	 */
	public List<String> getSelectedWebworkSets() {
		return selectedWebworkSets;
	}

	/**
	 * Method usually called from sets.jsp page when the sets list are captured
	 * from the WebworkUtil class. For the user selections on course, the proper
	 * list of sets are assigned in this method.
	 * 
	 * @see setSelectedWebworkSetsParam
	 * @param listWebworkSetsForCourses
	 *            list of user selected webwork sets.
	 */
	public void setListWebworkSetsForCourses(
			List<String> listWebworkSetsForCourses) {
		this.listWebworkSetsForCourses = listWebworkSetsForCourses;
	}

	/**
	 * Retrieves the array list of sets available in webwork for user selected
	 * courses.
	 * 
	 * @return the listWebworkSetsForCourses
	 */
	public List<String> getListWebworkSetsForCourses() {
		return listWebworkSetsForCourses;
	}

	/**
	 * Method usually called internally in setCaptureWebworkData method. The
	 * points for each assignment is got from webwork.
	 * 
	 * @param assignmentPoints
	 *            the assignmentPoints to set
	 */
	public void setAssignmentPoints(List<Integer> assignmentPoints) {
		this.assignmentPoints = assignmentPoints;
	}

	/**
	 * Retrieves the assignment points for each set pulled back from webwork.
	 * 
	 * @return the assignmentPoints
	 */
	public List<Integer> getAssignmentPoints() {
		return assignmentPoints;
	}

	/**
	 * Method usually called internally in setCaptureWebworkData method. The map
	 * of courses for sets selected by the user in the sets.jsp page is set
	 * using this method.
	 * 
	 * @param selectedWebworkCourseForSets
	 *            the selectedWebworkCourseForSets to set
	 */
	public void setSelectedWebworkCoursesMap(
			List<String> selectedWebworkCoursesMap) {
		this.selectedWebworkCoursesMap = selectedWebworkCoursesMap;
	}

	/**
	 * Retrieves the list of courses for user selected sets in sets.jsp page.
	 * 
	 * @return the selectedWebworkCourseForSets
	 */
	public List<String> getSelectedWebworkCoursesMap() {
		return selectedWebworkCoursesMap;
	}

	/**
	 * Method usually called internally in setCaptureWebworkData method. The
	 * list of sets for sets selected by the user in the sets.jsp page is set
	 * using this method.
	 * 
	 * @param selectedWebworkSetsMap
	 *            the selectedWebworkSetsMap to set
	 */
	public void setSelectedWebworkSetsMap(List<String> selectedWebworkSetsMap) {
		this.selectedWebworkSetsMap = selectedWebworkSetsMap;
	}

	/**
	 * Retrieves the list of sets for user selected sets in sets.jsp page.
	 * 
	 * @return the selectedWebworkSetsMap
	 */
	public List<String> getSelectedWebworkSetsMap() {
		return selectedWebworkSetsMap;
	}

	/**
	 * Method usually called internally in setCaptureWebworkData method. The
	 * list of due dates for sets selected by the user in the pulled back from
	 * webwork.
	 * 
	 * @param selectedWebworkSetsDueDate
	 *            the selectedWebworkSetsDueDate to set
	 */
	public void setSelectedWebworkSetsDueDate(
			List<String> selectedWebworkSetsDueDate) {
		this.selectedWebworkSetsDueDate = selectedWebworkSetsDueDate;
	}

	/**
	 * Retrieves the list of due dates for user selected sets pulled back from
	 * webwork.
	 * 
	 * @return the selectedWebworkSetsDueDate
	 */
	public List<String> getSelectedWebworkSetsDueDate() {
		return selectedWebworkSetsDueDate;
	}

	/**
	 * Method usually called internally in setCaptureWebworkData method. The
	 * list of start dates for sets selected by the user in the pulled back from
	 * webwork.
	 * 
	 * @param selectedWebworkSetsOpenDate
	 *            the selectedWebworkSetsOpenDate to set
	 */
	public void setSelectedWebworkSetsOpenDate(
			List<String> selectedWebworkSetsOpenDate) {
		this.selectedWebworkSetsOpenDate = selectedWebworkSetsOpenDate;
	}

	/**
	 * Retrieves the list of start dates for user selected sets pulled back from
	 * webwork.
	 * 
	 * @return the selectedWebworkSetsOpenDate
	 */
	public List<String> getSelectedWebworkSetsOpenDate() {
		return selectedWebworkSetsOpenDate;
	}

	/**
	 * Retrieves the flag which holds the information whether the data is
	 * captured from webwork. Usually should return true, when true is set to
	 * captureWebworkData showing that proper information from webwork is
	 * captured.
	 * 
	 * @return the captureWebworkData
	 */
	public boolean getCaptureWebworkData() {
		return captureWebworkData;
	}

	/**
	 * Method used to capture information like assignment points, due date(end
	 * date), open date(start date) from the webwork and present it to the
	 * instructor / TA when assigning the content in assignmet.jsp page. Thus
	 * this method pulls information from webwork,(if not available, instructor
	 * has to set dates which will be written back to webwork) and thus
	 * synchronizes information between webwork & blackboard.
	 * 
	 * @param captureWebworkData
	 *            Flag to capture information from webwork.
	 */
	public void setCaptureWebworkData(boolean captureWebworkData) {
		if (captureWebworkData) {

			/* Create new array list to store necessary information. */
			ArrayList<String> tempSelectedCoursesMap = new ArrayList<String>();
			ArrayList<String> tempSelectedSetsMap = new ArrayList<String>();
			ArrayList<Integer> tempAssignmentPoints = new ArrayList<Integer>();

			ArrayList<String> tempSelectedSetsDueDate = new ArrayList<String>();
			ArrayList<String> tempSelectedSetsOpenDate = new ArrayList<String>();

			for (String eachSelectedSet : this.getSelectedWebworkSetsParam()) {

				/*
				 * Capture the user selections for course and set and assign
				 * them to map variables for proper display in assignment.jsp
				 * page
				 */
				String tempSelectedSet = this.getListWebworkSetsForCourses()
						.get(Integer.valueOf(eachSelectedSet));
				String tempSelectedCourse = this.getSelectedCourses().get(
						Integer.valueOf(eachSelectedSet));

				tempSelectedSetsMap.add(tempSelectedSet);
				tempSelectedCoursesMap.add(tempSelectedCourse);

				/* Pull the grade points from webwork for each assignment */
				tempAssignmentPoints.add((Integer) WebworkUtil
						.getAssignmentPoints(tempSelectedCourse,
								tempSelectedSet));

				/* Temp variable to capture the date information from webwork. */
				String returnDate = null;
				//SimpleDateFormat constantDateFormat2 = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mma z");
				//TEST
                     SimpleDateFormat constantDateFormat2 = new SimpleDateFormat(
						Messages.getString("BlackboardUtil.DateFormat") + " HH:mm aa");

				/*
				 * Capture the end date from webwork , if null / can't format
				 * assign empty string & log the exception. Since empty string
				 * is not accepted in assignments.jsp page, instructor has to
				 * set a date, which will be written back to webwork.
				 */
				try {
					returnDate = ((WebworkSOAPClassesGlobalSet) WebworkUtil.soapHandler
							.get_global_set(WebworkUtil.webworkSoapAuthKey,
									tempSelectedCourse, tempSelectedSet))
							.getDue_date();

					tempSelectedSetsDueDate.add(constantDateFormat2
							.format(new java.util.Date(
									Long.valueOf(returnDate) * 1000)));
				} catch (Exception exc) {
					LogServiceFactory.getInstance().logFatal(
							"Cannot get / format the webwork assignment start date : \n"
									+ ((returnDate == null) ? "Date is null"
											: returnDate), exc);
					tempSelectedSetsDueDate.add(constantDateFormat2
							.format(Calendar.getInstance().getTime()));
				}

				/*
				 * Capture the start date from webwork , if null / can't format
				 * assign today's date & log the exception.
				 */
				try {
					returnDate = ((WebworkSOAPClassesGlobalSet) WebworkUtil.soapHandler
							.get_global_set(WebworkUtil.webworkSoapAuthKey,
									tempSelectedCourse, tempSelectedSet))
							.getOpen_date();
					tempSelectedSetsOpenDate.add(constantDateFormat2
							.format(new java.util.Date(
									Long.valueOf(returnDate) * 1000)));
				} catch (Exception exc) {
					LogServiceFactory.getInstance().logFatal(
							"Cannot get / format the webwork assignment end date:\n"
									+ ((returnDate == null) ? "Date is null"
											: returnDate), exc);
					tempSelectedSetsOpenDate.add(constantDateFormat2
							.format(Calendar.getInstance().getTime()));
				}
			}

			/* Set appropriate variables with the temp values */
			this.setSelectedWebworkCoursesMap(tempSelectedCoursesMap);
			this.setSelectedWebworkSetsMap(tempSelectedSetsMap);
			this.setAssignmentPoints(tempAssignmentPoints);
			this.setSelectedWebworkSetsDueDate(tempSelectedSetsDueDate);
			this.setSelectedWebworkSetsOpenDate(tempSelectedSetsOpenDate);
		}
		/* If success, set the variable. Usually set in anyways. */
		this.captureWebworkData = captureWebworkData;
	}
}
