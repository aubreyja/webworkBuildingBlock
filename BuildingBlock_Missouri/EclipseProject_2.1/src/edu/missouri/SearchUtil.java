/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivasan Devanathan
 * @category SearchUtil Class - Used to search within specified type, and return
 *           the search result. Right now used within courses.jsp page in the
 *           building block. Can be modified for any future needs.
 * @version 2.0
 * 
 * */
public final class SearchUtil extends BuildingBlockMethods {

	/**
	 * The course variable to search for. User Input.
	 */
	private String searchCourse = Messages.getString("Default.Empty");
	/**
	 * The operator from the search box selected by the user.
	 */
	private String searchOperator = Messages.getString("Default.Empty");
	/**
	 * The search type on. Right now only 'Course Name'.
	 */
	private String searchKey = Messages.getString("Default.Empty");
	/**
	 * List of courses / available info on which to search.
	 */
	private List<String> searchCoursesList;

	/**
	 * Parameters from bbng:Search Bar. Request params not the string in the
	 * drop-down. Check with bbNG documentation(though its not clear). Needs to
	 * be modified with future version changes in the blackboard.
	 */
	private static String[] searchOperatorStrings = { "Contains", "Equals",
			"StartsWith", "NotBlank" };

	/**
	 * Default Constructor. No initializations done as of now.
	 */
	public SearchUtil() {
	}

	/**
	 * Method used to set the variable searchCourse.
	 * 
	 * @param searchCourse
	 *            the searchCourse to set
	 */
	public void setSearchCourse(String searchCourse) {
		this.searchCourse = searchCourse;
	}

	/**
	 * Method used to return the variable searchCourse.
	 * 
	 * @return the searchCourse
	 */
	public String getSearchCourse() {
		return searchCourse;
	}

	/**
	 * Method used to set the variable searchCoursesList on which to search a
	 * user specified course.
	 * 
	 * @param searchCoursesList
	 *            the searchCoursesList to set
	 */
	public void setSearchCoursesList(List<String> searchCoursesList) {
		this.searchCoursesList = searchCoursesList;
	}

	/**
	 * Method used to search the user query on the list of valid courses. Used
	 * in courses.jsp page, to search for any user specified courses. Can be
	 * used in other pages, if implemented with two variables -
	 * searchCoursesList,searchCourse. If no search is specified, it returns the
	 * entire list on which to search. So set the entire list at firt to have
	 * proper values returned. Searches for four methods - contains, starts
	 * with, equals, not blank, right now available with bbNG tags.
	 * 
	 * @return the searchCoursesList - list of courses containing the search
	 *         query.
	 * @see searchQueryInList - Generic method which is called to perform the
	 *      function.
	 * 
	 */
	public List<String> getSearchCoursesList() {
		return this.searchQueryInList(searchCourse, searchCoursesList);
	}

	/**
	 * Method used to set the variable searchOperator. This is set using the
	 * searchBox parameters. For eg: check on courses.jsp, on to see how the
	 * request param is captured.
	 * 
	 * @param searchOperator
	 *            the searchOperator to set
	 */
	public void setSearchOperator(String searchOperator) {
		this.searchOperator = searchOperator;
	}

	/**
	 * @return the searchOperator
	 */
	public String getSearchOperator() {
		return searchOperator;
	}

	/**
	 * @param searchKey
	 *            the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}

	/**
	 * Method used to search the user query on the list of valid values. Generic
	 * method, which can be used in any search, if implemented with two
	 * variables - query ,listOfValues. If no search is specified, it returns
	 * the entire list - listOfValues on which to search. So set the entire list
	 * at first to have proper values returned. Searches for four methods -
	 * contains, starts with, equals, not blank, right now available with bbNG
	 * tags.
	 * 
	 * @param query
	 *            - Search query
	 * @param listOfValues
	 *            - List in which to search the query for.
	 * @return listOfSearchResults - List of search results containing the
	 *         query.
	 */

	private List<String> searchQueryInList(String query,
			List<String> listOfValues) {
		if (listOfValues == null || listOfValues.isEmpty())
			return new ArrayList<String>();
		else {
			/* If nothing to search return the entire list */
			if (query == null
					|| query.equals(Messages.getString("Default.Empty")))
				return listOfValues;
			else {
				ArrayList<String> tempCoursesList = new ArrayList<String>();
				/*
				 * If search operator is contains, search if the string is in
				 * the list by indexOf. Comparing by converting to lowercase. If
				 * case sensitive remove case conversion. Also trimmed for white
				 * spaces.
				 */
				if (this.searchOperator.trim().equalsIgnoreCase(
						searchOperatorStrings[0])) {
					for (String eachCourse : listOfValues)
						if (eachCourse.toLowerCase().indexOf(
								query.toLowerCase().trim()) > -1)
							tempCoursesList.add(eachCourse);
					return tempCoursesList;
				}
				/*
				 * If search operator is equals, convert the search to lower
				 * case and trim and then compare the values. If case conversion
				 * and white space trimming are unnecessary they should be
				 * removed.
				 */
				else if (this.searchOperator.trim().equalsIgnoreCase(
						searchOperatorStrings[1])) {
					for (String eachCourse : listOfValues)
						if (eachCourse.toLowerCase().equalsIgnoreCase(
								query.toLowerCase().trim()))
							tempCoursesList.add(eachCourse);
					return tempCoursesList;
				}
				/*
				 * If search operator is starts with, check for the string
				 * starting with in the list. As previous remove unnecessary
				 * case conversion and white space trimming.
				 */
				else if (this.searchOperator.trim().equalsIgnoreCase(
						searchOperatorStrings[2])) {
					for (String eachCourse : listOfValues)
						if (eachCourse.toLowerCase().startsWith(
								query.toLowerCase().trim()))
							tempCoursesList.add(eachCourse);
					return tempCoursesList;
				}
				/*
				 * If search operator is not blank, return the entire list.
				 * Change as needed.
				 */
				else
					return listOfValues;
			}
		}
	}

}
