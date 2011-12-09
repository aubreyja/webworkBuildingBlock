/**
 * WebworkSOAPHandlerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.missouri.math.webwork.WebworkSOAP;

public interface WebworkSOAPHandlerService extends javax.xml.rpc.Service {
    public java.lang.String getWebworkSOAPAddress();

    public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandler getWebworkSOAP() throws javax.xml.rpc.ServiceException;

    public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandler getWebworkSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
