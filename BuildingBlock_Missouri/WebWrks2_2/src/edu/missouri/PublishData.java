/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import blackboard.data.gradebook.Lineitem;
import blackboard.data.gradebook.impl.OutcomeDefinition;
import blackboard.data.gradebook.impl.OutcomeDefinitionCategory;
import blackboard.persist.Id;

/**
 * @author Srinivasan Devanathan
 * @category PublishData Bean Class - Useful to capture user information & write
 *           it to blackboard. Used in all publish.jsp & modify.jsp pages in the
 *           blackboard building block.
 * @version 2.0
 * 
 */
public class PublishData extends BuildingBlockMethods {

	/**
	 * The date when the items are written to the blackboard. Useful in writing
	 * the blackboard grade book info.
	 */
	private Calendar datePublished;
	/**
	 * Flag indicating whether the current page of publish.jsp is for publishing
	 * failed assignments name. Usually assignments with name already in
	 * blackboard are not published.
	 */
	private boolean rePublishReset;

	/**
	 * The blackboard course Id for which to publish the assignments.
	 */
	private Id courseId;
	/**
	 * The name of assignment. Grade book & content name depends on this.
	 */
	private String[] name;
	/**
	 * The category type of the blackboard grade book. User entered value.
	 */
	private String[] type;
	/**
	 * The assignment grade value pulled from the webwork database.
	 */
	private float[] pointsPossible;
	/**
	 * The course name of the assignment in the webwork.
	 */
	private List<String> courseName;
	/**
	 * The set name of the assignment in the webwork.
	 */
	private List<String> setName;
	/**
	 * The comments for the assignment entered by the user.
	 */
	private String[] assignmentComments;
	/**
	 * The start date of the assignment captured from webwork.
	 */
	private String[] startDate;
	/**
	 * The end date of the assignment captured from webwork.
	 */
	private String[] endDate;

	/**
	 * TODO Partial credit date. Not functional in version 2.1
	 */
	private String[] partialDate;
	/**
	 * TODO Partial credit percentage. Not functional in version 2.1
	 */
	private int[] partialPercent;
	/**
	 * TODO Partial credit enable. Not functional in version 2.1
	 */
	private String[] enablePartial;

	/**
	 * The assignment availability information for blackboard. User choice.
	 */
	private boolean[] isAvailable;
	/**
	 * The assignment statistics information for blackboard. User choice.
	 */
	private boolean[] isTracked;
	/**
	 * The assignment announcement information for blackboard. sUser choice.
	 */
	private boolean[] createAnnouncement;

	/**
	 * The individual line item information for gradebook.
	 */
	private Lineitem individualItem;
	/**
	 * List of line items in the blackboard.
	 */
	private List<Lineitem> gradeBookItems;

	/**
	 * List of flag values indicating published details of each record in
	 * Blackboard.
	 */
	private List<Boolean> publishedRecord;

	/**
	 * The title same as the assignment name. Used in modifying a content.
	 */
	private String title;
	/**
	 * The comments for the assignment. Used in modifying a content info.
	 */
	private String bodytext;
	/**
	 * Flag indicating whether the content is added / modified.
	 */
	private boolean modifyRecord;
	/**
	 * 
	 */
	private String endTime;
	/**
	 * 
	 */
	private String startTime;
	
	private boolean courseAndSetNames;

	/**
	 * Assignment initial start dates captured from webwork. Used in identifying
	 * whether changes should be written back to webwork.
	 */
	private ArrayList<String> assignmentsInitialStartDate;
	/**
	 * Assignment initial end dates captured from webwork. Used in identifying
	 * whether changes should be written back to webwork.
	 */
	private ArrayList<String> assignmentsInitialEndDate;

	/**
	 * Constructor initializing date when the assignments are published, & the
	 * grade book items to empty array list. Should be extended for future
	 * initializations.
	 */
	public PublishData() {
		this.datePublished = Calendar.getInstance();
		this.gradeBookItems = new ArrayList<Lineitem>();
	}

	/**
	 * Retrieves each line item of grade book. Usually not used.
	 * 
	 * @return the individualItem
	 */
	public Lineitem getIndividualItem() {
		return individualItem;
	}

	/**
	 * Sets each line item. When a line item is added, the information is added
	 * to the list of line items for the grade book.
	 * 
	 * @param individualItem
	 *            the blackboard line item object to set
	 */
	public void setIndividualItem(Lineitem individualItem) {
		this.individualItem = individualItem;
		this.gradeBookItems.add(individualItem);
	}

	/**
	 * Retrieves the list of line items to be persisted to the grade book. Used
	 * when writing information to the blackboard.
	 * 
	 * @return the gradeBookItems
	 */
	public List<Lineitem> getGradeBookItems() {
		return gradeBookItems;
	}

	/**
	 * Method used to set the list of grade book items. When passed with null, a
	 * new array list is created. Usually done when re-furbishing the bad /
	 * failed assignments.
	 * 
	 * @param gradeBookItems
	 *            the gradeBookItems to set
	 */
	public void setGradeBookItems(List<Lineitem> gradeBookItems) {
		if (gradeBookItems == null || gradeBookItems.isEmpty())
			this.gradeBookItems = new ArrayList<Lineitem>();
		else
			this.gradeBookItems = gradeBookItems;
	}

	/**
	 * @param courseId
	 *            the courseId to set
	 */
	public void setCourseId(Id courseId) {
		this.courseId = courseId;
		this.writeGradeBookItems();
	}

	/**
	 * @return the courseId
	 */
	public Id getCourseId() {
		return courseId;
	}

	/**
	 * Retrieves the array of name of the assignment. User entered information.
	 * 
	 * @return the name
	 */
	public String[] getName() {
		return name;
	}

	/**
	 * Method used to set the name variable.
	 * 
	 * @see variable name.
	 * @param names
	 *            the name to set
	 */
	public void setName(String[] name) {
		this.name = name;
	}

	/**
	 * Retrieves the type of the assignment. Right now only valid one is -
	 * 'Assignment'.
	 * 
	 * @return the type
	 */
	public String[] getType() {
		return type;
	}

	/**
	 * Method used to set the type variable.
	 * 
	 * @see variable type.
	 * @param type
	 *            the type to set
	 */
	public void setType(String[] type) {
		this.type = type;
	}

	/**
	 * Method used to set the set name from the class webworkData.
	 * 
	 * @param setName
	 *            the setName to set
	 */
	public void setSetName(List<String> setName) {
		this.setName = setName;
	}

	/**
	 * Retrieves the list of set name in webwork for the assignments.
	 * 
	 * @return the setName
	 */
	public List<String> getSetName() {
		return setName;
	}

	/**
	 * Method used to the set the variable courseName which is an array list
	 * 
	 * @param courseName
	 *            the courseName to set
	 */
	public void setCourseName(List<String> courseName) {
		this.courseName = courseName;
	}

	/**
	 * Retrieves the list of course name in the webwork for the selected
	 * assignments.
	 * 
	 * @return the courseName
	 */
	public List<String> getCourseName() {
		return courseName;
	}

	public void setCourseAndSetNames(boolean courseAndSetNames){
		if(courseAndSetNames){
			String[] temp = title.split("[?&=]");
			
			List<String> clist = new ArrayList<String>();
			List<String> slist = new ArrayList<String>();
			clist.add(temp[3]);
			slist.add(temp[5]);
	
			this.courseName = clist;
			this.setName = slist;
		}
	}
	
	/**
	 * Retrieves the availability information entered for each assignment for
	 * use in blackboard.
	 * 
	 * @return the isAvailable
	 */
	public boolean[] getIsAvailable() {
		return isAvailable;
	}

	/**
	 * Method used to the set the variable isAvailable.
	 * 
	 * @param isAvailable
	 *            the isAvailable to set
	 */
	public void setIsAvailable(boolean[] isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * Retrieves the statistical information entered for each assignment for use
	 * in blackboard.
	 * 
	 * @param isTracked
	 *            the isTracked to set
	 */
	public void setIsTracked(boolean[] isTracked) {
		this.isTracked = isTracked;
	}

	/**
	 * Method used to the set the variable isTracked.
	 * 
	 * @return the isTracked
	 */
	public boolean[] getIsTracked() {
		return isTracked;
	}

	/**
	 * Method used to the set the variable createAnnouncement.
	 * 
	 * @param createAnnouncement
	 */
	public void setCreateAnnouncement(boolean[] createAnnouncement) {
		this.createAnnouncement = createAnnouncement;
	}

	/**
	 * Retrieves the announcement feature for each assignment for creating in
	 * blackboard.
	 * 
	 * @return
	 */
	public boolean[] getCreateAnnouncement() {
		return createAnnouncement;
	}

	/**
	 * Method used to set the comments entered by the user for the assignment.
	 * 
	 * @param assignmentComments
	 *            the assignmentComments to set
	 */
	public void setAssignmentComments(String[] assignmentComments) {
		this.assignmentComments = assignmentComments;
	}

	/**
	 * Retrieves the comments entered for each assignment by the user.
	 * 
	 * @return the assignmentComments
	 */
	public String[] getAssignmentComments() {
		return assignmentComments;
	}

	/**
	 * Method used to set the datePublished variable.
	 * 
	 * @param datePublished
	 *            the datePublished to set
	 */
	public void setDatePublished(Calendar datePublished) {
		this.datePublished = datePublished;
	}

	/**
	 * Retrieves the date which is to be written to grade book.
	 * 
	 * @return the datePublished
	 */
	public Calendar getDatePublished() {
		return datePublished;
	}

	/**
	 * Method used to set the flags for each assignment informing if the item is
	 * written to the blackboard.
	 * 
	 * @param publishedRecord
	 *            the publishedRecord to set
	 */
	public void setPublishedRecord(List<Boolean> publishedRecord) {
		this.publishedRecord = publishedRecord;
	}

	/**
	 * Retrieves the list of flags for each assignment informing if the items
	 * has been written to the blackboard.
	 * 
	 * @return the publishedRecord
	 */
	public List<Boolean> getPublishedRecord() {
		return publishedRecord;
	}

	/**
	 * Method sets the assignment points as set by the user.
	 * 
	 * @param pointsPossible
	 *            the pointsPossible to set
	 */
	public void setPointsPossible(float[] pointsPossible) {
		this.pointsPossible = pointsPossible;
	}

	/**
	 * Retrieves the point information for assignment as set by the user.
	 * 
	 * @return the pointsPossible
	 */
	public float[] getPointsPossible() {
		return pointsPossible;
	}

	/**
	 * Method used to set the end date of each assignment.
	 * 
	 * @param endDate
	 */
	public void setEndDate(String[] endDate) {
		this.endDate = endDate;
	}

	/**
	 * Retrieves the end dates for each assignment.
	 * 
	 * @return
	 */
	public String[] getEndDate() {
		return endDate;
	}

	/**
	 * Method used to set the start date of each assignment.
	 * 
	 * @param startDate
	 */
	public void setStartDate(String[] startDate) {
		this.startDate = startDate;
	}

	/**
	 * Retrieves the end dates for each assignment.
	 * 
	 * @return
	 */
	public String[] getStartDate() {
		return startDate;
	}

	/**
	 * Method used to set the initial start dates of each assignment. This info
	 * is compared with that of the date information entered in the blackboard.
	 * 
	 * @param assignmentsInitialStartDate
	 *            the assignmentsInitialStartDate to set
	 */
	public void setAssignmentsInitialStartDate(
			ArrayList<String> assignmentsInitialStartDate) {
		this.assignmentsInitialStartDate = assignmentsInitialStartDate;
	}

	/**
	 * Retrieves the list of initial start dates for each assignment pulled from
	 * the webwork. If there are changes with the initial and final dates, the
	 * information is written back to the webwork.
	 * 
	 * @return the assignmentsInitialStartDate
	 */
	public ArrayList<String> getAssignmentsInitialStartDate() {
		return assignmentsInitialStartDate;
	}

	/**
	 * Method used to set the initial end dates of each assignment. This info is
	 * compared with that of the date information entered in the blackboard.
	 * 
	 * @param assignmentsInitialEndDate
	 *            the assignmentsInitialEndDate to set
	 */
	public void setAssignmentsInitialEndDate(
			ArrayList<String> assignmentsInitialEndDate) {
		this.assignmentsInitialEndDate = assignmentsInitialEndDate;
	}

	/**
	 * Retrieves the list of initial end dates for each assignment pulled from
	 * the webwork. If there are changes with the initial and final dates, the
	 * information is written back to the webwork.
	 * 
	 * @return the assignmentsInitialEndDate
	 */
	public ArrayList<String> getAssignmentsInitialEndDate() {
		return assignmentsInitialEndDate;
	}

	/**
	 * Method used to set the publish reset flag which details if the assignment
	 * are to be republished for any errors with the previous publish. (Mostly
	 * the name of the assignment should be different from the previous names in
	 * the grade book.)
	 * 
	 * @param rePublishReset
	 *            the rePublishReset to set
	 */
	public void setRePublishReset(boolean rePublishReset) {
		if (rePublishReset)
			this.setGradeBookItems(null);
		this.rePublishReset = rePublishReset;
	}

	/**
	 * Retrieves the flag information of publish reset.
	 * 
	 * @see setRePublishReset
	 * @return the rePublishReset
	 */
	public boolean getRePublishReset() {
		return rePublishReset;
	}

	/*
	 * Methods for modify.jsp
	 */

	/**
	 * Method used to set the flag for modifying content information rather than
	 * adding the info to blackboard.
	 * 
	 * @param modifiedRecord
	 *            the modifiedRecord to set
	 */
	public void setModifyRecord(boolean modifyRecord) {
		this.modifyRecord = modifyRecord;
	}

	/**
	 * Retrieve info about whether this object is used for modifying content
	 * info / adding content info to blackboard.
	 * 
	 * @return the modifiedRecord
	 */
	public boolean getModifyRecord() {
		return modifyRecord;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * Method used to set the comments for the modifying assignment.
	 * 
	 * @param bodytext
	 *            the bodytext to set
	 */
	public void setBodytext(String bodytext) {
		this.bodytext = bodytext;
	}

	/**
	 * Retrieves the comments for the assignment after modification.
	 * 
	 * @return the bodytext
	 */
	public String getBodytext() {
		return bodytext;
	}

	/**
	 * Method used to set the title information for the assignment.
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Retrieves the title information for the assignment.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * Methods for partial credit feature. This functionality is not yet enabled
	 * in webwork & blackboard building block as of version 2.1.
	 */

	/**
	 * Method used to set the partial date from which partial credit starts
	 * counting.
	 * 
	 * @param partialDate
	 *            the partialDate to set
	 */
	public void setPartialDate(String[] partialDate) {
		this.partialDate = partialDate;
	}

	/**
	 * Retrieves the partial date from which partial credit starts counting.
	 * 
	 * @return the partialDate
	 */
	public String[] getPartialDate() {
		return partialDate;
	}

	/**
	 * Method used to set the partial percentage for the partial credit feature.
	 * 
	 * @param partialPercent
	 *            the partialPercent to set
	 */
	public void setPartialPercent(int[] partialPercent) {
		this.partialPercent = partialPercent;
	}

	/**
	 * Retrieves the partial percentage for the partial credit feature.
	 * 
	 * @return the partialPercent
	 */
	public int[] getPartialPercent() {
		return partialPercent;
	}

	/**
	 * Method used to set the partial credit enable.
	 * 
	 * @param enablePartial
	 *            the enablePartial to set
	 */
	public void setEnablePartial(String[] enablePartial) {
		this.enablePartial = enablePartial;
	}

	/**
	 * Retrieves whether the partial credit feature is enabled or not.
	 * 
	 * @return the enablePartial
	 */
	public String[] getEnablePartial() {
		return enablePartial;
	}

	/**
	 * Private method to write the grade book item with information in this
	 * class. This method is called from setCourseId method. 
	 * 
	 * @return void
	 */
	private void writeGradeBookItems() {
		for (int itemNumber = 0; itemNumber < this.name.length; itemNumber++) {
			OutcomeDefinitionCategory category = new OutcomeDefinitionCategory(
					this.type[itemNumber]);
			OutcomeDefinition definition = new OutcomeDefinition();
			definition.setCategory(category);
			Lineitem gradeBookItem = new Lineitem(definition);
			gradeBookItem.setIsAvailable(this.isAvailable[itemNumber]);
			gradeBookItem.setPointsPossible(this.pointsPossible[itemNumber]);
			gradeBookItem.setDateAdded(this.datePublished);
			gradeBookItem.setCourseId(this.courseId);
			gradeBookItem.setName(this.getName()[itemNumber]);
			gradeBookItem.setType(this.getType()[itemNumber]);
			this.gradeBookItems.add(gradeBookItem);
		}
	}

}
