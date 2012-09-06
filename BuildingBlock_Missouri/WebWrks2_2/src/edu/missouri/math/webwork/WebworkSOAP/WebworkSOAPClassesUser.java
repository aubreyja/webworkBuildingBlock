/**
 * WebworkSOAPClassesUser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.missouri.math.webwork.WebworkSOAP;

import javax.xml.namespace.QName;

import blackboard.data.user.User;

public class WebworkSOAPClassesUser  implements java.io.Serializable {
    private String user_id;

    private String first_name;

    private String last_name;

    private String email_address;

    private String student_id;

    private String status;

    private String section;

    private String recitation;

    private String comment;

    public WebworkSOAPClassesUser() {
    	this.user_id = "";
    	this.first_name = "";
    	this.last_name = "";
    	this.email_address = "";
    	this.student_id = "";
    	this.status = "";
    	this.section = "";
    	this.recitation = "";
    	this.comment = "";
    }

    public WebworkSOAPClassesUser(String user_id, String first_name, String last_name, String email_address, String student_id, String status, String section, String recitation, 
    		String comment) {
    	this.user_id = user_id;
    	this.first_name = first_name;
    	this.last_name = last_name;
    	this.email_address = email_address;
    	this.student_id = student_id;
    	this.status = status;
    	this.section = section;
    	this.recitation = recitation;
    	this.comment = comment;
    }
    
    public WebworkSOAPClassesUser(User blackboardUser){
    	this.user_id = blackboardUser.getUserName();
    	this.first_name = blackboardUser.getGivenName();
    	this.last_name = blackboardUser.getFamilyName();
    	this.email_address = blackboardUser.getEmailAddress();
    	this.student_id = blackboardUser.getStudentId();
    	this.status = "";
    	this.section = "";
    	this.recitation = "";
    	this.comment = "";
    }


    /**
     * Gets the user_id value for this WebworkSOAPClassesUser.
     * 
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }


    /**
     * Sets the user_id value for this WebworkSOAPClassesUser.
     * 
     * @param user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    /**
     * Gets the first_name value for this WebworkSOAPClassesUser.
     * 
     * @return first_name
     */
    public String getFirst_name() {
        return first_name;
    }


    /**
     * Sets the first_name value for this WebworkSOAPClassesUser.
     * 
     * @param first_name
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    /**
     * Gets the last_name value for this WebworkSOAPClassesUser.
     * 
     * @return last_name
     */
    public String getLast_name() {
        return last_name;
    }


    /**
     * Sets the last_name value for this WebworkSOAPClassesUser.
     * 
     * @param last_name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    /**
     * Gets the email_address value for this WebworkSOAPClassesUser.
     * 
     * @return email_address
     */
    public String getEmail_address() {
        return email_address;
    }


    /**
     * Sets the email_address value for this WebworkSOAPClassesUser.
     * 
     * @param email_address
     */
    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }


    /**
     * Gets the student_id value for this WebworkSOAPClassesUser.
     * 
     * @return student_id
     */
    public String getStudent_id() {
        return student_id;
    }


    /**
     * Sets the student_id value for this WebworkSOAPClassesUser.
     * 
     * @param student_id
     */
    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }


    /**
     * Gets the status value for this WebworkSOAPClassesUser.
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this WebworkSOAPClassesUser.
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Gets the section value for this WebworkSOAPClassesUser.
     * 
     * @return section
     */
    public String getSection() {
        return section;
    }


    /**
     * Sets the section value for this WebworkSOAPClassesUser.
     * 
     * @param section
     */
    public void setSection(String section) {
        this.section = section;
    }


    /**
     * Gets the recitation value for this WebworkSOAPClassesUser.
     * 
     * @return recitation
     */
    public String getRecitation() {
        return recitation;
    }


    /**
     * Sets the recitation value for this WebworkSOAPClassesUser.
     * 
     * @param recitation
     */
    public void setRecitation(String recitation) {
        this.recitation = recitation;
    }


    /**
     * Gets the comment value for this WebworkSOAPClassesUser.
     * 
     * @return comment
     */
    public String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this WebworkSOAPClassesUser.
     * 
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WebworkSOAPClassesUser)) 
        	return false;
        WebworkSOAPClassesUser other = (WebworkSOAPClassesUser) obj;
        if (obj == null) 
        	return false;
        if (this == obj) 
        	return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.user_id==null && other.getUser_id()==null) || 
             (this.user_id!=null &&
              this.user_id.equals(other.getUser_id()))) &&
            ((this.first_name==null && other.getFirst_name()==null) || 
             (this.first_name!=null &&
              this.first_name.equals(other.getFirst_name()))) &&
            ((this.last_name==null && other.getLast_name()==null) || 
             (this.last_name!=null &&
              this.last_name.equals(other.getLast_name()))) &&
            ((this.email_address==null && other.getEmail_address()==null) || 
             (this.email_address!=null &&
              this.email_address.equals(other.getEmail_address()))) &&
            ((this.student_id==null && other.getStudent_id()==null) || 
             (this.student_id!=null &&
              this.student_id.equals(other.getStudent_id()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.section==null && other.getSection()==null) || 
             (this.section!=null &&
              this.section.equals(other.getSection()))) &&
            ((this.recitation==null && other.getRecitation()==null) || 
             (this.recitation!=null &&
              this.recitation.equals(other.getRecitation()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment())));
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
        if (getFirst_name() != null) {
            _hashCode += getFirst_name().hashCode();
        }
        if (getLast_name() != null) {
            _hashCode += getLast_name().hashCode();
        }
        if (getEmail_address() != null) {
            _hashCode += getEmail_address().hashCode();
        }
        if (getStudent_id() != null) {
            _hashCode += getStudent_id().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSection() != null) {
            _hashCode += getSection().hashCode();
        }
        if (getRecitation() != null) {
            _hashCode += getRecitation().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WebworkSOAPClassesUser.class, true);

    static {
        typeDesc.setXmlType(new QName("https://webwork.math.missouri.edu/WebworkSOAP", "WebworkSOAPClassesUser"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user_id");
        elemField.setXmlName(new QName("", "user_id"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("first_name");
        elemField.setXmlName(new QName("", "first_name"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("last_name");
        elemField.setXmlName(new QName("", "last_name"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email_address");
        elemField.setXmlName(new QName("", "email_address"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("student_id");
        elemField.setXmlName(new QName("", "student_id"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new QName("", "status"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("section");
        elemField.setXmlName(new QName("", "section"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recitation");
        elemField.setXmlName(new QName("", "recitation"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new QName("", "comment"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
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
           String mechType, 
           java.lang.Class _javaType,  
           QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType, 
           java.lang.Class _javaType,  
           QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
