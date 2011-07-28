/**
 * WebworkSOAPClassesGlobalProblem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.missouri.math.webwork.WebworkSOAP;

import edu.missouri.ConfigFile;

public class WebworkSOAPClassesGlobalProblem  implements java.io.Serializable {
    private java.lang.String set_id;

    private java.lang.String problem_id;

    private java.lang.String source_file;

    private java.lang.String value;

    private java.lang.String max_attempts;

    public WebworkSOAPClassesGlobalProblem() {
    }

    public WebworkSOAPClassesGlobalProblem(
           java.lang.String set_id,
           java.lang.String problem_id,
           java.lang.String source_file,
           java.lang.String value,
           java.lang.String max_attempts) {
           this.set_id = set_id;
           this.problem_id = problem_id;
           this.source_file = source_file;
           this.value = value;
           this.max_attempts = max_attempts;
    }


    /**
     * Gets the set_id value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @return set_id
     */
    public java.lang.String getSet_id() {
        return set_id;
    }


    /**
     * Sets the set_id value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @param set_id
     */
    public void setSet_id(java.lang.String set_id) {
        this.set_id = set_id;
    }


    /**
     * Gets the problem_id value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @return problem_id
     */
    public java.lang.String getProblem_id() {
        return problem_id;
    }


    /**
     * Sets the problem_id value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @param problem_id
     */
    public void setProblem_id(java.lang.String problem_id) {
        this.problem_id = problem_id;
    }


    /**
     * Gets the source_file value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @return source_file
     */
    public java.lang.String getSource_file() {
        return source_file;
    }


    /**
     * Sets the source_file value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @param source_file
     */
    public void setSource_file(java.lang.String source_file) {
        this.source_file = source_file;
    }


    /**
     * Gets the value value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @return value
     */
    public java.lang.String getValue() {
        return value;
    }


    /**
     * Sets the value value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @param value
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }


    /**
     * Gets the max_attempts value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @return max_attempts
     */
    public java.lang.String getMax_attempts() {
        return max_attempts;
    }


    /**
     * Sets the max_attempts value for this WebworkSOAPClassesGlobalProblem.
     * 
     * @param max_attempts
     */
    public void setMax_attempts(java.lang.String max_attempts) {
        this.max_attempts = max_attempts;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WebworkSOAPClassesGlobalProblem)) return false;
        WebworkSOAPClassesGlobalProblem other = (WebworkSOAPClassesGlobalProblem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.set_id==null && other.getSet_id()==null) || 
             (this.set_id!=null &&
              this.set_id.equals(other.getSet_id()))) &&
            ((this.problem_id==null && other.getProblem_id()==null) || 
             (this.problem_id!=null &&
              this.problem_id.equals(other.getProblem_id()))) &&
            ((this.source_file==null && other.getSource_file()==null) || 
             (this.source_file!=null &&
              this.source_file.equals(other.getSource_file()))) &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue()))) &&
            ((this.max_attempts==null && other.getMax_attempts()==null) || 
             (this.max_attempts!=null &&
              this.max_attempts.equals(other.getMax_attempts())));
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
        if (getSet_id() != null) {
            _hashCode += getSet_id().hashCode();
        }
        if (getProblem_id() != null) {
            _hashCode += getProblem_id().hashCode();
        }
        if (getSource_file() != null) {
            _hashCode += getSource_file().hashCode();
        }
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        if (getMax_attempts() != null) {
            _hashCode += getMax_attempts().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WebworkSOAPClassesGlobalProblem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(ConfigFile.getWebServiceLocations("WebworkService.WebServerLocation"), "WebworkSOAPClassesGlobalProblem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("set_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "set_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("problem_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "problem_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source_file");
        elemField.setXmlName(new javax.xml.namespace.QName("", "source_file"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("max_attempts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "max_attempts"));
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
