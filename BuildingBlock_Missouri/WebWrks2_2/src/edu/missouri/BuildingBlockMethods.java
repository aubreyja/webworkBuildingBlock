/**
 * Package Missouri.edu contains the necessary blakcboard plugin util classes.
 */
package edu.missouri;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import blackboard.data.user.User;
import blackboard.platform.BbServiceException;
import blackboard.platform.log.LogService;
import blackboard.platform.log.LogServiceFactory;

/**
 * Class which has abstract methods and implements the BuildingBlockConstants
 * interface. Webwork Building block classes extend this class generally.
 * 
 * @author Srinivasan Devanathan
 * @category BuildingBlockMethods Bean Class - Useful to have common methods
 *           shared between the package classes.
 * @version 2.0
 */
public abstract class BuildingBlockMethods implements BuildingBlockConstants {

	public static MessageDigest messageDigestForEncryption;

	static {
		try {
			messageDigestForEncryption = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
		}
	}

	
	/*protected static blackboard.platform.log.Log _DEBGUGLOG;
	protected static blackboard.platform.log.Log _ERRORLOG;

	static {
		try {
			LogServiceFactory.getInstance().defineNewFileLog("webwork_debug",
					"logs", LogService.Verbosity.DEBUG, false);
			_DEBGUGLOG = LogServiceFactory.getInstance().getConfiguredLog(
					"webwork_debug");
		} catch (BbServiceException e) {
			LogServiceFactory
					.getInstance()
					.logError(
							"Cannot create a new debug log file for webwork. Relocating to default log. ");
			_DEBGUGLOG = LogServiceFactory.getInstance().getDefaultLog();
		}
		try {
			LogServiceFactory.getInstance().defineNewFileLog("webwork_error",
					"logs", LogService.Verbosity.ERROR, false);
			_ERRORLOG = LogServiceFactory.getInstance().getConfiguredLog(
					"webwork_error");
		} catch (BbServiceException e) {
			LogServiceFactory
					.getInstance()
					.logError(
							"Cannot create a new error log file for webwork. Relocating to default log. ");
			_ERRORLOG = LogServiceFactory.getInstance().getDefaultLog();
		}
	}*/

	/**
	 * Abstract Class which contains default methods accessible to all classes
	 * in the package.
	 */
	public BuildingBlockMethods() {
	}

	/**
	 * Generates a login Key for the webwork course and the user. Returns the
	 * login key back to blackboard. Method is synchronized, so that only one
	 * user can get the login key at a time. Thus, one person login key does not
	 * messes up with the other person's key.
	 * 
	 * @return the LoginKey
	 */
	public static synchronized String getLoginKey(String course, String set, User blackboardUser) throws RemoteException {
		return WebworkUtil.soapHandler.login_user(WebworkUtil.webworkSoapAuthKey, course, blackboardUser);
	}

	/**
	 * 
	 * Generates an ecrypted hash value for the string generated. If security is
	 * needed this method is used, else it wont be used.
	 * 
	 * @param dataToEncrypt
	 *            String value which needs to be encrypted.
	 * @return String value of the bytes returned by the encrypted string.
	 */
	public static synchronized String getEncryptedValue(String dataToEncrypt) {
		byte[] encryptedBytes = new byte[40];
		try {
			messageDigestForEncryption.update(
					dataToEncrypt.getBytes("iso-8859-1"), 0,
					dataToEncrypt.length());
			encryptedBytes = messageDigestForEncryption.digest();
		} catch (UnsupportedEncodingException e) {
			return dataToEncrypt;
			/*
			 * If not able to encrypt send the same string back again for demo
			 * purpose.
			 */
		}
		return new String(encryptedBytes);
	}

	/**
	 * The Building block URI location to be accessed within package.
	 * 
	 * @return the buildingBlockURI String
	 */
	public String getBuildingBlockURI() {
		return BlackboardUtil.buildingBlockURI;
	}

	/**
	 * The system admin building block tools location.
	 * 
	 * @return the WebworkPluginsPage String
	 */
	public String getConstantWebworkPluginsPage() {
		return constantWebworkPluginsPage;
	}

	/**
	 * 
	 * @param messageToLog
	 */
	/*
	 * public static synchronized void setLogErrorMessage(String errorMessage) {
	 * _ERRORLOG.logError(errorMessage); }
	 *//**
	 * 
	 * @param debugMessage
	 */
	/*
	 * public static synchronized void setLogDebugMessage(String debugMessage) {
	 * _DEBGUGLOG.logDebug(debugMessage); }
	 */

}
