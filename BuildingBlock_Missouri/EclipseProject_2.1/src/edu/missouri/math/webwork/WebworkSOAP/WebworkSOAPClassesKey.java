/**
 * WebworkSOAPClassesKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.missouri.math.webwork.WebworkSOAP;

import edu.missouri.ConfigFile;

public class WebworkSOAPClassesKey  implements java.io.Serializable {
    private java.lang.String user_id;

    private java.lang.String key_not_a_keyboard;

    private java.lang.String timestamp;

    public WebworkSOAPClassesKey() {
    }

    public WebworkSOAPClassesKey(
           java.lang.String user_id,
           java.lang.String key_not_a_keyboard,
           java.lang.String timestamp) {
           this.user_id = user_id;
           this.key_not_a_keyboard = key_not_a_keyboard;
           this.timestamp = timestamp;
    }


    /**
     * Gets the user_id value for this WebworkSOAPClassesKey.
     * 
     * @return user_id
     */
    public java.lang.String getUser_id() {
        return user_id;
    }


    /**
     * Sets the user_id value for this WebworkSOAPClassesKey.
     * 
     * @param user_id
     */
    public void setUser_id(java.lang.String user_id) {
        this.user_id = user_id;
    }


    /**
     * Gets the key_not_a_keyboard value for this WebworkSOAPClassesKey.
     * 
     * @return key_not_a_keyboard
     */
    public java.lang.String getKey_not_a_keyboard() {
        return key_not_a_keyboard;
    }


    /**
     * Sets the key_not_a_keyboard value for this WebworkSOAPClassesKey.
     * 
     * @param key_not_a_keyboard
     */
    public void setKey_not_a_keyboard(java.lang.String key_not_a_keyboard) {
        this.key_not_a_keyboard = key_not_a_keyboard;
    }


    /**
     * Gets the timestamp value for this WebworkSOAPClassesKey.
     * 
     * @return timestamp
     */
    public java.lang.String getTimestamp() {
        return timestamp;
    }


    /**
     * Sets the timestamp value for this WebworkSOAPClassesKey.
     * 
     * @param timestamp
     */
    public void setTimestamp(java.lang.String timestamp) {
        this.timestamp = timestamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WebworkSOAPClassesKey)) return false;
        WebworkSOAPClassesKey other = (WebworkSOAPClassesKey) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.user_id==null && other.getUser_id()==null) || 
             (this.user_id!=null &&
              this.user_id.equals(other.getUser_id()))) &&
            ((this.key_not_a_keyboard==null && other.getKey_not_a_keyboard()==null) || 
             (this.key_not_a_keyboard!=null &&
              this.key_not_a_keyboard.equals(other.getKey_not_a_keyboard()))) &&
            ((this.timestamp==null && other.getTimestamp()==null) || 
             (this.timestamp!=null &&
              this.timestamp.equals(other.getTimestamp())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getUser_id() != null) {
            _hashCode += getUser_id().hashCode();
        }
        if (getKey_not_a_keyboard() != null) {
            _hashCode += getKey_not_a_keyboard().hashCode();
        }
        if (getTimestamp() != null) {
            _hashCode += getTimestamp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WebworkSOAPClassesKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(ConfigFile.getWebServiceLocations("WebworkService.WebServerLocation"), "WebworkSOAPClassesKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key_not_a_keyboard");
        elemField.setXmlName(new javax.xml.namespace.QName("", "key_not_a_keyboard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
