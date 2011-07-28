package edu.missouri.math.webwork.WebworkSOAP;

public class WebworkSOAPHandlerProxy implements edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandler {
  private String _endpoint = null;
  private edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandler webworkSOAPHandler = null;
  
  public WebworkSOAPHandlerProxy() {
    _initWebworkSOAPHandlerProxy();
  }
  
  public WebworkSOAPHandlerProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebworkSOAPHandlerProxy();
  }
  
  private void _initWebworkSOAPHandlerProxy() {
    try {
      webworkSOAPHandler = (new edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandlerServiceLocator()).getWebworkSOAP();
      if (webworkSOAPHandler != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)webworkSOAPHandler)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)webworkSOAPHandler)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webworkSOAPHandler != null)
      ((javax.xml.rpc.Stub)webworkSOAPHandler)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandler getWebworkSOAPHandler() {
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler;
  }
  
  public java.lang.String hello() throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.hello();
  }
  
  public java.lang.String[] list_courses(java.lang.String authenKey) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_courses(authenKey);
  }
  
  public java.lang.String login_user(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.login_user(authenKey, courseName, userID);
  }
  
  public java.lang.String assign_set_to_user(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.assign_set_to_user(authenKey, courseName, userID, setID);
  }
  
  public java.lang.String[] grade_users_sets(java.lang.String authenKey, java.lang.String courseName, java.lang.String[] userIDs, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.grade_users_sets(authenKey, courseName, userIDs, setID);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet get_set_data(java.lang.String authenKey, java.lang.String courseName, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_set_data(authenKey, courseName, setID);
  }
  
  public java.lang.String add_password(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPassword record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.add_password(authenKey, courseName, record);
  }
  
  public java.lang.String put_password(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPassword record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.put_password(authenKey, courseName, record);
  }
  
  public java.lang.String[] list_password(java.lang.String authenKey, java.lang.String courseName) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_password(authenKey, courseName);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPassword[] get_passwords(java.lang.String authenKey, java.lang.String courseName, java.lang.String[] userIDs) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_passwords(authenKey, courseName, userIDs);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPassword get_password(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_password(authenKey, courseName, userID);
  }
  
  public java.lang.String add_permission(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPermission record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.add_permission(authenKey, courseName, record);
  }
  
  public java.lang.String put_permission(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPermission record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.put_permission(authenKey, courseName, record);
  }
  
  public java.lang.String[] list_permissions(java.lang.String authenKey, java.lang.String courseName) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_permissions(authenKey, courseName);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPermission[] get_permissions(java.lang.String authenKey, java.lang.String courseName, java.lang.String[] userIDs) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_permissions(authenKey, courseName, userIDs);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesPermission get_permission(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_permission(authenKey, courseName, userID);
  }
  
  public java.lang.String add_key(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesKey record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.add_key(authenKey, courseName, record);
  }
  
  public java.lang.String put_key(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesKey record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.put_key(authenKey, courseName, record);
  }
  
  public java.lang.String[] list_keys(java.lang.String authenKey, java.lang.String courseName) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_keys(authenKey, courseName);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesKey[] get_keys(java.lang.String authenKey, java.lang.String courseName, java.lang.String[] userIDs) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_keys(authenKey, courseName, userIDs);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesKey get_key(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_key(authenKey, courseName, userID);
  }
  
  public java.lang.String add_user(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUser record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.add_user(authenKey, courseName, record);
  }
  
  public java.lang.String put_user(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUser record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.put_user(authenKey, courseName, record);
  }
  
  public java.lang.String[] list_users(java.lang.String authenKey, java.lang.String courseName) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_users(authenKey, courseName);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUser get_user(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_user(authenKey, courseName, userID);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUser[] get_users(java.lang.String authenKey, java.lang.String courseName, java.lang.String[] userIDs) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_users(authenKey, courseName, userIDs);
  }
  
  public java.lang.String delete_user(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.delete_user(authenKey, courseName, userID);
  }
  
  public java.lang.String add_global_set(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.add_global_set(authenKey, courseName, record);
  }
  
  public java.lang.String put_global_set(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.put_global_set(authenKey, courseName, record);
  }
  
  public java.lang.String[] list_global_sets(java.lang.String authenKey, java.lang.String courseName) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_global_sets(authenKey, courseName);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet[] get_all_global_sets(java.lang.String authenKey, java.lang.String courseName) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_all_global_sets(authenKey, courseName);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet[] get_global_sets(java.lang.String authenKey, java.lang.String courseName, java.lang.String[] setIDs) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_global_sets(authenKey, courseName, setIDs);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalSet get_global_set(java.lang.String authenKey, java.lang.String courseName, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_global_set(authenKey, courseName, setID);
  }
  
  public java.lang.String delete_global_set(java.lang.String authenKey, java.lang.String courseName, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.delete_global_set(authenKey, courseName, setID);
  }
  
  public java.lang.String add_global_problem(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalProblem record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.add_global_problem(authenKey, courseName, record);
  }
  
  public java.lang.String put_global_problem(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalProblem record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.put_global_problem(authenKey, courseName, record);
  }
  
  public java.lang.String[] list_global_problems(java.lang.String authenKey, java.lang.String courseName, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_global_problems(authenKey, courseName, setID);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalProblem[] get_all_global_problems(java.lang.String authenKey, java.lang.String courseName, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_all_global_problems(authenKey, courseName, setID);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesGlobalProblem get_global_problem(java.lang.String authenKey, java.lang.String courseName, java.lang.String setID, java.lang.String problemID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_global_problem(authenKey, courseName, setID, problemID);
  }
  
  public java.lang.String delete_global_problem(java.lang.String authenKey, java.lang.String courseName, java.lang.String setID, java.lang.String problemID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.delete_global_problem(authenKey, courseName, setID, problemID);
  }
  
  public java.lang.String add_user_problem(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.add_user_problem(authenKey, courseName, record);
  }
  
  public java.lang.String put_user_problem(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.put_user_problem(authenKey, courseName, record);
  }
  
  public java.lang.String[] list_user_problems(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_user_problems(authenKey, courseName, userID, setID);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem[] get_all_user_problems(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_all_user_problems(authenKey, courseName, userID, setID);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem[] get_user_problems(java.lang.String authenKey, java.lang.String courseName, java.lang.String[] userProblemIDs) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_user_problems(authenKey, courseName, userProblemIDs);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserProblem get_user_problem(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID, java.lang.String setID, java.lang.String problemID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_user_problem(authenKey, courseName, userID, setID, problemID);
  }
  
  public java.lang.String delete_user_problem(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID, java.lang.String setID, java.lang.String problemID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.delete_user_problem(authenKey, courseName, userID, setID, problemID);
  }
  
  public java.lang.String add_user_set(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.add_user_set(authenKey, courseName, record);
  }
  
  public java.lang.String put_user_set(java.lang.String authenKey, java.lang.String courseName, edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet record) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.put_user_set(authenKey, courseName, record);
  }
  
  public java.lang.String[] list_user_sets(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.list_user_sets(authenKey, courseName, userID);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet[] get_all_user_sets(java.lang.String authenKey, java.lang.String courseName) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_all_user_sets(authenKey, courseName);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet[] get_user_sets(java.lang.String authenKey, java.lang.String courseName, java.lang.String userSetIDs) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_user_sets(authenKey, courseName, userSetIDs);
  }
  
  public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPClassesUserSet get_user_set(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.get_user_set(authenKey, courseName, userID, setID);
  }
  
  public java.lang.String delete_user_set(java.lang.String authenKey, java.lang.String courseName, java.lang.String userID, java.lang.String setID) throws java.rmi.RemoteException{
    if (webworkSOAPHandler == null)
      _initWebworkSOAPHandlerProxy();
    return webworkSOAPHandler.delete_user_set(authenKey, courseName, userID, setID);
  }
  
  
}