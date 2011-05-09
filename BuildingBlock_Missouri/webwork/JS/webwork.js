/**
 * 
 */

function generateSelectionCourses() {
	var drawerItemsNumber = drawer.model.getCurrentInstance()
			.getNumberOfItems();
	if (drawerItemsNumber > 25) {
		alert('Select 25 items or less or select all.');
		return false;
	}

	var drawerSelectedItems = drawer.model.getCurrentInstance().getItems();
	for ( var loop = 0; loop < drawerSelectedItems.length; loop++) {
		document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].value = drawerSelectedItems[loop].itemId;
		document.forms["courseAddForm"].selectedWebworkCoursesParam[loop].checked = true;
	}
	return true;
}
