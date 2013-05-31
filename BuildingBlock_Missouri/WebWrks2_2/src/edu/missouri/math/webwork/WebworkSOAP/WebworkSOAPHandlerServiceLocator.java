/**
 * WebworkSOAPHandlerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.missouri.math.webwork.WebworkSOAP;

import edu.missouri.ConfigFile;

public class WebworkSOAPHandlerServiceLocator extends org.apache.axis.client.Service implements edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandlerService {

    public WebworkSOAPHandlerServiceLocator() {
    }


    public WebworkSOAPHandlerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebworkSOAPHandlerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebworkSOAP
    private java.lang.String WebworkSOAP_address = "http://webwork.math.missouri.edu/webwork2_rpc";

    public java.lang.String getWebworkSOAPAddress() {
        return WebworkSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebworkSOAPWSDDServiceName = "WebworkSOAP";

    public java.lang.String getWebworkSOAPWSDDServiceName() {
        return WebworkSOAPWSDDServiceName;
    }

    public void setWebworkSOAPWSDDServiceName(java.lang.String name) {
        WebworkSOAPWSDDServiceName = name;
    }

    public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandler getWebworkSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebworkSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebworkSOAP(endpoint);
    }

    public edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandler getWebworkSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPSoapBindingStub _stub = new edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPSoapBindingStub(portAddress, this);
            _stub.setPortName(getWebworkSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebworkSOAPEndpointAddress(java.lang.String address) {
        WebworkSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPHandler.class.isAssignableFrom(serviceEndpointInterface)) {
                edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPSoapBindingStub _stub = new edu.missouri.math.webwork.WebworkSOAP.WebworkSOAPSoapBindingStub(new java.net.URL(WebworkSOAP_address), this);
                _stub.setPortName(getWebworkSOAPWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WebworkSOAP".equals(inputPortName)) {
            return getWebworkSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(ConfigFile.getWebServiceLocations("WebworkService.WebServerLocation"), "WebworkSOAPHandlerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(ConfigFile.getWebServiceLocations("WebworkService.WebServerLocation"), "WebworkSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WebworkSOAP".equals(portName)) {
            setWebworkSOAPEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
