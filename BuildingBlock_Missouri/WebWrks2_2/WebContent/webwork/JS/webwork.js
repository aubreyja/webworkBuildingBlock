/**
 * @author  Srinivasan Devanathan 
 * @version 1.0
 * Webwork Javascript functions
 * 
 */

/**
 * Function to set values in the checkbox for the selections made by the User in
 * courses.jsp page.
 * 
 * @returns {Boolean}
 */
function generateSelectionCourses() {
	document.forms["courseAddForm"].submit();
	
	/*
	 * The functions commented due to two changes - All courses listed rather
	 * than only 25 limit on courses.jsp. JS not need for all courses selection ,
	 * as it was annoying for users to have the courses being changed when they
	 * select other course.
	 * 
	 */
//	var drawerItemsNumber = drawer.model.getCurrentInstance()
//			.getNumberOfItems();
//	var numberOfCourses = document
//			.getElementsByName("selectedWebworkCoursesParam").length;
//	if (numberOfCourses > 1) {
//		if (drawerItemsNumber > document.forms["courseAddForm"].selectedWebworkCoursesParam.length) {
//			alert('You cannot select more items than the number of items available on this page. \n You can either go back to the previous page maintain your previous selections or unselect the items from the previous page.');
//			return false;
//		}
//
//		var drawerSelectedItems = drawer.model.getCurrentInstance().getItems();
//
//		for ( var loop = 0; loop < document.forms["courseAddForm"].selectedWebworkCoursesParam.length; loop++) {
//			document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].checked = false;
//		}
//
//		for ( var loop = 0; loop < drawerSelectedItems.length; loop++) {
//			document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].value = drawerSelectedItems[loop].itemId;
//			document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].checked = true;
//		}
//		document.forms["courseAddForm"].submit();
//	} else {
//		if (drawerItemsNumber > 1) {
//			alert('You cannot select more items than the number of items available on this page. \n You can either go back to the previous page maintain your previous selections or unselect the items from the previous page.');
//			return false;
//		}
//
//		var drawerSelectedItems = drawer.model.getCurrentInstance().getItems();
//
//		document.forms["courseAddForm"].selectedWebworkCoursesParam.value = drawerSelectedItems[0].itemId;
//		document.forms["courseAddForm"].selectedWebworkCoursesParam.checked = true;
//		document.forms["courseAddForm"].submit();
//	}
}

/**
 * Function to set values in the checkbox for the selections made by the User in
 * sets.jsp page.
 * 
 * @returns {Boolean}
 */
function generateSelectionSets() {
	document.forms["setAddForm"].submit();
	
	/*
	 * The functions commented due to two changes - All sets listed rather
	 * than only 25 limit on sets.jsp. JS not need for all sets selection ,
	 * as it was annoying for users to have the courses being changed when they
	 * select other course.
	 * 
	 */

//	var drawerItemsNumber = drawer.model.getCurrentInstance()
//			.getNumberOfItems();
//	var numberOfSets = document.getElementsByName("selectedWebworkSetsParam").length;
//	if (numberOfSets > 1) {
//		if (drawerItemsNumber > document.forms["setAddForm"].selectedWebworkSetsParam.length) {
//			alert('You cannot select more items than the number of items available on this page. \n You can either go back to the previous page maintain your previous selections or unselect the items from the previous page.');
//			return false;
//		}
//
//		var drawerSelectedItems = drawer.model.getCurrentInstance().getItems();
//
//		for ( var loop = 0; loop < document.forms["setAddForm"].selectedWebworkSetsParam.length; loop++)
//			document.forms["setAddForm"].selectedWebworkSetsParam[loop].checked = false;
//
//		for ( var loop = 0; loop < drawerSelectedItems.length; loop++) {
//			document.forms["setAddForm"].selectedWebworkSetsParam[loop].value = drawerSelectedItems[loop].itemId;
//			document.forms["setAddForm"].selectedWebworkSetsParam[loop].checked = true;
//		}
//		document.forms["setAddForm"].submit();
//	} else {
//		if (drawerItemsNumber > 1) {
//			alert('You cannot select more items than the number of items available on this page. \n You can either go back to the previous page maintain your previous selections or unselect the items from the previous page.');
//			return false;
//		}
//
//		var drawerSelectedItems = drawer.model.getCurrentInstance().getItems();
//
//		document.forms["setAddForm"].selectedWebworkSetsParam.value = drawerSelectedItems[0].itemId;
//		document.forms["setAddForm"].selectedWebworkSetsParam.checked = true;
//		document.forms["setAddForm"].submit();
//	}
}

/**
 * Function to set values / display / select all in the checkbox for the
 * selections made by the User in assignments.jsp page.
 * 
 * @returns {Boolean}
 */
function selectCheckbox() {
	for ( var loop = 0; loop < document.forms["assignmentsForm"].isAvailable.length; loop++) {
		if (loop % 2 != 0) {
			if (document.forms["assignmentsForm"].isAvailable[loop].disabled == true) {
				if (document.forms["assignmentsForm"].isAvailable[loop - 1].checked == false) {
					document.forms["assignmentsForm"].isAvailable[loop].checked = true;
					document.forms["assignmentsForm"].isAvailable[loop].disabled = false;
					document.forms["assignmentsForm"].isAvailable[loop - 1].disabled = true;
				}
			} else {
				if (document.forms["assignmentsForm"].isAvailable[loop].checked == false) {
					document.forms["assignmentsForm"].isAvailable[loop - 1].checked = true;
					document.forms["assignmentsForm"].isAvailable[loop].disabled = true;
					document.forms["assignmentsForm"].isAvailable[loop - 1].disabled = false;
				}
			}
			if (document.forms["assignmentsForm"].createAnnouncement[loop].disabled == true) {
				if (document.forms["assignmentsForm"].createAnnouncement[loop - 1].checked == false) {
					document.forms["assignmentsForm"].createAnnouncement[loop].checked = true;
					document.forms["assignmentsForm"].createAnnouncement[loop].disabled = false;
					document.forms["assignmentsForm"].createAnnouncement[loop - 1].disabled = true;
				}
			} else {
				if (document.forms["assignmentsForm"].createAnnouncement[loop].checked == false) {
					document.forms["assignmentsForm"].createAnnouncement[loop - 1].checked = true;
					document.forms["assignmentsForm"].createAnnouncement[loop].disabled = true;
					document.forms["assignmentsForm"].createAnnouncement[loop - 1].disabled = false;
				}
			}
			if (document.forms["assignmentsForm"].isTracked[loop].disabled == true) {
				if (document.forms["assignmentsForm"].isTracked[loop - 1].checked == false) {
					document.forms["assignmentsForm"].isTracked[loop].checked = true;
					document.forms["assignmentsForm"].isTracked[loop].disabled = false;
					document.forms["assignmentsForm"].isTracked[loop - 1].disabled = true;
				}
			} else {
				if (document.forms["assignmentsForm"].isTracked[loop].checked == false) {
					document.forms["assignmentsForm"].isTracked[loop - 1].checked = true;
					document.forms["assignmentsForm"].isTracked[loop].disabled = true;
					document.forms["assignmentsForm"].isTracked[loop - 1].disabled = false;
				}
			}
		}
	}
}

/**
 * Function to display error message when user attempts to change date on
 * assignments.jsp.
 * 
 */
function errorSelection() {
	window
			.alert("Are you sure you want to change the dates? THIS IS TEST VERSION, SET MIGHT EVEN BE DELETED."
					+ "If yes, change to a valid date. \n"
					+ "Know that you can also modify date later through Content -> Edit");
}

/**
 * Function to make changes to partialcredit and partial dates. When the user
 * selects the checkbox, the date and percent text box are enabled on
 * assignments.jsp
 */
function changePartial() {
	var length = (document.forms["assignmentsForm"].isAvailable.length / 2);
	if (length > 1) {
		for ( var loop = 0; loop < length; loop++) {
			if (document.forms["assignmentsForm"].enablePartial[loop].checked == true) {
				document.forms["assignmentsForm"].partialDate[loop].disabled = false;
				document.forms["assignmentsForm"].partialPercent[loop].disabled = false;
				return true;
			} else {
				document.forms["assignmentsForm"].partialDate[loop].disabled = true;
				document.forms["assignmentsForm"].partialPercent[loop].disabled = true;
				return true;
			}
		}
	} else if (document.forms["assignmentsForm"].enablePartial.checked == true) {
		document.forms["assignmentsForm"].partialDate.disabled = false;
		document.forms["assignmentsForm"].partialPercent.disabled = false;
		return true;
	} else {
		document.forms["assignmentsForm"].partialDate.disabled = true;
		document.forms["assignmentsForm"].partialPercent.disabled = true;
		return true;
	}
}

/**
 * Function to validate assignments.jsp form.
 * 
 * @returns {Boolean}
 */
function validateForm() {
	var length = (document.forms["assignmentsForm"].isAvailable.length / 2);
	if (length > 1) {
		for ( var loop = 0; loop < length; loop++) {
			if (document.forms["assignmentsForm"].name[loop].value == "") {
				window.alert("Assignment name cannot be empty");
				document.forms["assignmentsForm"].name[loop].focus();
				return false;
			}
			if (document.forms["assignmentsForm"].pointsPossible[loop].value == 0) {
				window.alert("Grades can only have a non-zero value.");
				document.forms["assignmentsForm"].pointsPossible[loop].focus();
				return false;
			}
			if (document.forms["assignmentsForm"].startDate[loop].value == ""
					|| document.forms["assignmentsForm"].endDate[loop].value == "") {
				window.alert("Assignment Dates cannot be empty.");
				return false;
			}
		}
		//True until now, so can submit the form. Grades should be captured so turn it on.
		for ( var loop = 0; loop < length; loop++)
			document.forms["assignmentsForm"].pointsPossible[loop].disabled = false;
	} else {
		if (document.forms["assignmentsForm"].name.value == "") {
			window.alert("Assignment name cannot be empty");
			document.forms["assignmentsForm"].name.focus();
			return false;
		}
		if (document.forms["assignmentsForm"].pointsPossible.value == 0) {
			window.alert("Grades can only have a non-zero value.");
			document.forms["assignmentsForm"].pointsPossible.focus();
			return false;
		}
		if (document.forms["assignmentsForm"].startDate.value == ""
				|| document.forms["assignmentsForm"].endDate.value == "") {
			window.alert("Assignment Dates cannot be empty.");
			return false;
		}
		//True until now, so can submit the form. Grades should be captured so turn it on.
		document.forms["assignmentsForm"].pointsPossible.disabled = false;
	}
	return true;
}

function validatePublishForm() {
	var length = (document.forms["assignmentsFailedForm"].isAvailable.length / 2);
	if (length > 1) {
		for ( var loop = 0; loop < length; loop++) {
			if (document.forms["assignmentsFailedForm"].name[loop].disabled == false) {
				if (document.forms["assignmentsFailedForm"].name[loop].value == "") {
					window.alert("Assignment name cannot be empty");
					document.forms["assignmentsFailedForm"].name[loop].focus();
					return false;
				}
				if (document.forms["assignmentsFailedForm"].pointsPossible[loop].value == 0) {
					window.alert("Grades can only have a non-zero value.");
					document.forms["assignmentsFailedForm"].pointsPossible[loop]
							.focus();
					return false;
				}
				if (document.forms["assignmentsFailedForm"].startDate[loop].value == ""
						|| document.forms["assignmentsFailedForm"].endDate[loop].value == "") {
					// ||
					// (document.forms["assignmentsFailedForm"].partialDate[loop].disabled
					// == false &&
					// document.forms["assignmentsForm"].partialDate[loop].value
					// == "")) {
					window.alert("Assignment Dates cannot be empty.");
					return false;
				}
				
			}
		}
		//No Error until now, so Submit form.
		for ( var loop = 0; loop < length; loop++) {
			if (document.forms["assignmentsFailedForm"].name[loop].disabled == true) {
				document.forms["assignmentsFailedForm"].name[loop].disabled = false;
				document.forms["assignmentsFailedForm"].pointsPossible[loop].disabled = false;
				document.forms["assignmentsFailedForm"].type[loop].disabled = false;
				document.forms["assignmentsFailedForm"].startDate[loop].disabled = false;
				document.forms["assignmentsFailedForm"].endDate[loop].disabled = false;
				document.forms["assignmentsFailedForm"].assignmentComments[loop].disabled = false;
			}
		}
		var length = (document.forms["assignmentsFailedForm"].isAvailable.length);
		for ( var loop = 0; loop < length; loop++) {
			if (document.forms["assignmentsFailedForm"].isAvailable[loop].checked == true)
				document.forms["assignmentsFailedForm"].isAvailable[loop].disabled = false;
			if (document.forms["assignmentsFailedForm"].isTracked[loop].checked == true)
				document.forms["assignmentsFailedForm"].isTracked[loop].disabled = false;
			if (document.forms["assignmentsFailedForm"].createAnnouncement[loop].checked == true)
				document.forms["assignmentsFailedForm"].createAnnouncement[loop].disabled = false;
		}
		return true;
	} else {
		if (document.forms["assignmentsFailedForm"].name.value == "") {
			window.alert("Assignment name cannot be empty");
			document.forms["assignmentsFailedForm"].name.focus();
			return false;
		}
		if (document.forms["assignmentsFailedForm"].pointsPossible.value == 0) {
			window.alert("Grades can only have a non-zero value.");
			document.forms["assignmentsFailedForm"].pointsPossible.focus();
			return false;
		}
		if (document.forms["assignmentsFailedForm"].startDate.value == ""
				|| document.forms["assignmentsFailedForm"].endDate.value == "") {
			// || (document.forms["assignmentsFailedForm"].partialDate.disabled
			// == false && document.forms["assignmentsForm"].partialDate.value
			// == "")) {
			window.alert("Assignment Dates cannot be empty.");
			return false;
		}
		if (document.forms["assignmentsFailedForm"].type.value == ""
				|| document.forms["assignmentsFailedForm"].type.value
						.toUpperCase() != "Assignment".toUpperCase()) {
			window.alert("Assignment is the only type available right now.");
			document.forms["assignmentsFailedForm"].type.focus();
			return false;
		}
		//True until now, so can submit the form. Grades should be captured so turn it on. 
		document.forms["assignmentsForm"].pointsPossible.disabled = false;
	}
	return true;
}

/**
 * Function to display the text box on the selection of radio button.
 * 
 */
function displayOtherElements() {
	var checked = document.forms["configForm"].webworkCoursesSearchMethod[0].checked;
	if (checked == true) {
		document.forms["configForm"].webworkSearchCourse.disabled = false;
		document.forms["configForm"].webworkInstructorPermissionLevel.disabled = true;
	}
	var checked = document.forms["configForm"].webworkCoursesSearchMethod[1].checked;
	if (checked == true) {
		document.forms["configForm"].webworkInstructorPermissionLevel.disabled = false;
		document.forms["configForm"].webworkSearchCourse.disabled = true;
	}
	var checked = document.forms["configForm"].webworkCoursesSearchMethod[2].checked;
	if (checked == true) {
		document.forms["configForm"].webworkInstructorPermissionLevel.disabled = true;
		document.forms["configForm"].webworkSearchCourse.disabled = true;
	}
}

/**
 * Function to change the webserver variables when one changes the SOAP.
 */
function copyVariable() {
	document.forms["configForm"].soapAuthKey.value = new jsUri(
			'http://www.test.com').setHost('www.yahoo.com')
			.setProtocol('https');
	document.forms["configForm"].webServerSiteUrl.value = document.forms["configForm"].webServerLocation.value;
}