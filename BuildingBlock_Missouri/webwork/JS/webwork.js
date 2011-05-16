/**
 * Author Srinivasan Devanathan Webwork Javascript functions
 */
/**
 * Function to set values in the checkbox for the selections made by the User in
 * courses.jsp page.
 * 
 * @returns {Boolean}
 */
function generateSelectionCourses() {
	var drawerItemsNumber = drawer.model.getCurrentInstance()
			.getNumberOfItems();
	if (drawerItemsNumber > document.forms["courseAddForm"].selectedWebworkCoursesParam.length) {
		alert('You cannot select more items than the number of items available on this page. \n You can either go back to the previous page maintain your previous selections or unselect the items from the previous page.');
		return false;
	}

	var drawerSelectedItems = drawer.model.getCurrentInstance().getItems();

	for ( var loop = 0; loop < document.forms["courseAddForm"].selectedWebworkCoursesParam.length; loop++) {
		document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].checked = false;
	}

	for ( var loop = 0; loop < drawerSelectedItems.length; loop++) {
		document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].value = drawerSelectedItems[loop].itemId;
		document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].checked = true;
	}
	document.forms["courseAddForm"].submit();
	return false;
}

/**
 * Function to set values in the checkbox for the selections made by the User in
 * sets.jsp page.
 * 
 * @returns {Boolean}
 */
function generateSelectionSets() {
	var drawerItemsNumber = drawer.model.getCurrentInstance()
			.getNumberOfItems();
	if (drawerItemsNumber > document.forms["setAddForm"].selectedWebworkSetsParam.length) {
		alert('You cannot select more items than the number of items available on this page. \n You can either go back to the previous page maintain your previous selections or unselect the items from the previous page.');
		return false;
	}

	var drawerSelectedItems = drawer.model.getCurrentInstance().getItems();

	for ( var loop = 0; loop < document.forms["setAddForm"].selectedWebworkSetsParam.length; loop++)
		document.forms["setAddForm"].selectedWebworkSetsParam[loop].checked = false;

	for ( var loop = 0; loop < drawerSelectedItems.length; loop++) {
		document.forms["setAddForm"].selectedWebworkSetsParam[loop].value = drawerSelectedItems[loop].itemId;
		document.forms["setAddForm"].selectedWebworkSetsParam[loop].checked = true;
	}
	document.forms["setAddForm"].submit();
	return false;
}
