/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Srinivasan Devanathan
 * @category Messages Class - Useful to get information from the properties
 *           files. Used throughout within the webwork package.
 * @version 2.0
 * 
 */
public class Messages {
	/**
	 * General bundle for properties specified in the package edu.missouri.
	 */
	private static final String BUNDLE_NAME = "edu.missouri.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Bundle for loading the label properties (display purpose)
	 */
	private static final String LABEL_BUNDLE_NAME = "label";
	private static final ResourceBundle RESOURCE_LABEL_BUNDLE = ResourceBundle.getBundle(LABEL_BUNDLE_NAME);

	/**
	 * Default constructor. No initialization until version 2.0. Can be modified
	 * to include all properties file with slight modifications.
	 */
	private Messages() {
	}

	/**
	 * Method used to get properties from the label.properties file. This file
	 * is mostly used by jsp pages to get information displayed to the user.
	 * Sometimes this information is needed for the package files too. For eg:
	 * writing the properties through config file etc.
	 * 
	 * @param key
	 *            Parameter key for which to find the value in the properties
	 *            file.
	 * @return parameter value found in the properties file.
	 * 
	 */
	public static String getLabelString(String key) {
		try {
			return RESOURCE_LABEL_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Method used to get properties from the messages.properties file. This
	 * file is used within the package edu.missouri. All parameters can be
	 * configured anytime by changing this file. This allows a generic method to
	 * modify the functioning of building block.
	 * 
	 * @param key
	 *            Parameter key for which to find the value in the properties
	 *            file.
	 * @return parameter value found in the properties file.
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

}
