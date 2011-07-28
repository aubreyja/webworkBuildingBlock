/**
 * WebworkSOAPHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.missouri.math.webwork.WebworkSOAP;

public interface WebworkSOAPHandler extends java.rmi.Remote {
	public java.lang.String hello() throws java.rmi.RemoteException;

	public java.lang.String[] list_courses(java.lang.String authenKey)
			throws java.rmi.RemoteException;

	public java.lang.String login_user(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String userID)
			throws java.rmi.RemoteException;

	public java.lang.String assign_set_to_user(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String userID,
			java.lang.String setID) throws java.rmi.RemoteException;

	public java.lang.String[] grade_users_sets(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String[] userIDs,
			java.lang.String setID) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet get_set_data(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String setID) throws java.rmi.RemoteException;

	public java.lang.String add_password(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPassword record)
			throws java.rmi.RemoteException;

	public java.lang.String put_password(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPassword record)
			throws java.rmi.RemoteException;

	public java.lang.String[] list_password(java.lang.String authenKey,
			java.lang.String courseName) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPassword[] get_passwords(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String[] userIDs) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPassword get_password(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String userID) throws java.rmi.RemoteException;

	public java.lang.String add_permission(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPermission record)
			throws java.rmi.RemoteException;

	public java.lang.String put_permission(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPermission record)
			throws java.rmi.RemoteException;

	public java.lang.String[] list_permissions(java.lang.String authenKey,
			java.lang.String courseName) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPermission[] get_permissions(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String[] userIDs) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPermission get_permission(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String userID) throws java.rmi.RemoteException;

	public java.lang.String add_key(java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesKey record)
			throws java.rmi.RemoteException;

	public java.lang.String put_key(java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesKey record)
			throws java.rmi.RemoteException;

	public java.lang.String[] list_keys(java.lang.String authenKey,
			java.lang.String courseName) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesKey[] get_keys(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String[] userIDs) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesKey get_key(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String userID) throws java.rmi.RemoteException;

	public java.lang.String add_user(java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUser record)
			throws java.rmi.RemoteException;

	public java.lang.String put_user(java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUser record)
			throws java.rmi.RemoteException;

	public java.lang.String[] list_users(java.lang.String authenKey,
			java.lang.String courseName) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUser get_user(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String userID) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUser[] get_users(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String[] userIDs) throws java.rmi.RemoteException;

	public java.lang.String delete_user(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String userID)
			throws java.rmi.RemoteException;

	public java.lang.String add_global_set(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet record)
			throws java.rmi.RemoteException;

	public java.lang.String put_global_set(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet record)
			throws java.rmi.RemoteException;

	public java.lang.String[] list_global_sets(java.lang.String authenKey,
			java.lang.String courseName) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet[] get_all_global_sets(
			java.lang.String authenKey, java.lang.String courseName)
			throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet[] get_global_sets(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String[] setIDs) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet get_global_set(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String setID) throws java.rmi.RemoteException;

	public java.lang.String delete_global_set(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String setID)
			throws java.rmi.RemoteException;

	public java.lang.String add_global_problem(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalProblem record)
			throws java.rmi.RemoteException;

	public java.lang.String put_global_problem(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalProblem record)
			throws java.rmi.RemoteException;

	public java.lang.String[] list_global_problems(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String setID)
			throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalProblem[] get_all_global_problems(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String setID) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalProblem get_global_problem(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String setID, java.lang.String problemID)
			throws java.rmi.RemoteException;

	public java.lang.String delete_global_problem(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String setID,
			java.lang.String problemID) throws java.rmi.RemoteException;

	public java.lang.String add_user_problem(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem record)
			throws java.rmi.RemoteException;

	public java.lang.String put_user_problem(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem record)
			throws java.rmi.RemoteException;

	public java.lang.String[] list_user_problems(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String userID,
			java.lang.String setID) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem[] get_all_user_problems(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String userID, java.lang.String setID)
			throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem[] get_user_problems(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String[] userProblemIDs) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem get_user_problem(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String userID, java.lang.String setID,
			java.lang.String problemID) throws java.rmi.RemoteException;

	public java.lang.String delete_user_problem(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String userID,
			java.lang.String setID, java.lang.String problemID)
			throws java.rmi.RemoteException;

	public java.lang.String add_user_set(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet record)
			throws java.rmi.RemoteException;

	public java.lang.String put_user_set(
			java.lang.String authenKey,
			java.lang.String courseName,
			edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet record)
			throws java.rmi.RemoteException;

	public java.lang.String[] list_user_sets(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String userID)
			throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet[] get_all_user_sets(
			java.lang.String authenKey, java.lang.String courseName)
			throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet[] get_user_sets(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String userSetIDs) throws java.rmi.RemoteException;

	public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet get_user_set(
			java.lang.String authenKey, java.lang.String courseName,
			java.lang.String userID, java.lang.String setID)
			throws java.rmi.RemoteException;

	public java.lang.String delete_user_set(java.lang.String authenKey,
			java.lang.String courseName, java.lang.String userID,
			java.lang.String setID) throws java.rmi.RemoteException;
}
