/**
 * WebworkSOAPClassesGlobalSet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.missouri.math.webwork.WebworkSOAP;

import javax.xml.namespace.QName;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class WebworkSOAPClassesGlobalSet  implements java.io.Serializable {
    private String set_id;
    private String set_header;
    private String hardcopy_header;
    private String open_date;
    private String due_date;
    private String answer_date;
    private String visible;
    private String enable_reduced_scoring;
    private String assignment_type;
    private String attempts_per_version;
    private String time_interval;
    private String versions_per_interval;
    private String version_time_limit;
    private String version_creation_time;
    private String problem_randorder;
    private String version_last_attempt_time;
    private String problems_per_page;
    
    private String hide_score;

    private String hide_score_by_problem;

    private String hide_work;
    
    private String time_limit_cap;

    private String restrict_ip;
    
    private String relax_restrict_ip;
    
    private String restricted_login_proctor;
    
    

    public WebworkSOAPClassesGlobalSet() {
    }

    public WebworkSOAPClassesGlobalSet(String set_id, String set_header, String hardcopy_header, String open_date, String due_date, String answer_date, String visible,
		String enable_reduced_scoring, String assignment_type, String attempts_per_version, String time_interval, String versions_per_interval, String version_time_limit,
		String version_creation_time, String problem_randorder, String version_last_attempt_time, String problems_per_page) {
    	
		this.set_id = set_id;
		this.set_header = set_header;
		this.hardcopy_header = hardcopy_header;
		this.open_date = open_date;
		this.due_date = due_date;
		this.answer_date = answer_date;
		this.visible = visible;
		this.enable_reduced_scoring = enable_reduced_scoring;
		this.assignment_type = assignment_type;
		this.attempts_per_version = attempts_per_version;
		this.time_interval = time_interval;
		this.versions_per_interval = versions_per_interval;
		this.version_time_limit = version_time_limit;
		this.version_creation_time = version_creation_time;
		this.problem_randorder = problem_randorder;
		this.version_last_attempt_time = version_last_attempt_time;
		this.problems_per_page = problems_per_page;
		this.hide_score = "N";
		this.hide_score_by_problem = "N";
		this.hide_work = "N";
		this.time_limit_cap = "0";
		this.restrict_ip = "No";
		this.relax_restrict_ip = "No";
		this.restricted_login_proctor = "No";
    }

    public void setOtherStuff()
    {
        this.hide_score = "N";
        this.hide_score_by_problem = "N";
        this.hide_work = "N";
        this.time_limit_cap = "0";
        this.restrict_ip = "No";
        this.relax_restrict_ip = "No";
        this.restricted_login_proctor = "No";
    }

    /**
     * Gets the set_id value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return set_id
     */
    public String getSet_id() {
        return set_id;
    }


    /**
     * Sets the set_id value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param set_id
     */
    public void setSet_id(String set_id) {
        this.set_id = set_id;
    }


    /**
     * Gets the set_header value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return set_header
     */
    public String getSet_header() {
        return set_header;
    }


    /**
     * Sets the set_header value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param set_header
     */
    public void setSet_header(String set_header) {
        this.set_header = set_header;
    }


    /**
     * Gets the hardcopy_header value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return hardcopy_header
     */
    public String getHardcopy_header() {
        return hardcopy_header;
    }


    /**
     * Sets the hardcopy_header value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param hardcopy_header
     */
    public void setHardcopy_header(String hardcopy_header) {
        this.hardcopy_header = hardcopy_header;
    }


    /**
     * Gets the open_date value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return open_date
     */
    public String getOpen_date() {
        return open_date;
    }


    /**
     * Sets the open_date value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param open_date
     */
    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }


    /**
     * Gets the due_date value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return due_date
     */
    public String getDue_date() {
        return due_date;
    }


    /**
     * Sets the due_date value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param due_date
     */
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }


    /**
     * Gets the answer_date value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return answer_date
     */
    public String getAnswer_date() {
        return answer_date;
    }


    /**
     * Sets the answer_date value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param answer_date
     */
    public void setAnswer_date(String answer_date) {
        this.answer_date = answer_date;
    }


    /**
     * Gets the visible value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return visible
     */
    public String getVisible() {
        return visible;
    }


    /**
     * Sets the visible value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param visible
     */
    public void setVisible(String visible) {
        this.visible = visible;
    }


    /**
     * Gets the enable_reduced_scoring value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return enable_reduced_scoring
     */
    public String getEnable_reduced_scoring() {
        return enable_reduced_scoring;
    }


    /**
     * Sets the enable_reduced_scoring value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param enable_reduced_scoring
     */
    public void setEnable_reduced_scoring(String enable_reduced_scoring) {
        this.enable_reduced_scoring = enable_reduced_scoring;
    }


    /**
     * Gets the assignment_type value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return assignment_type
     */
    public String getAssignment_type() {
        return assignment_type;
    }


    /**
     * Sets the assignment_type value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param assignment_type
     */
    public void setAssignment_type(String assignment_type) {
        this.assignment_type = assignment_type;
    }


    /**
     * Gets the attempts_per_version value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return attempts_per_version
     */
    public String getAttempts_per_version() {
        return attempts_per_version;
    }


    /**
     * Sets the attempts_per_version value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param attempts_per_version
     */
    public void setAttempts_per_version(String attempts_per_version) {
        this.attempts_per_version = attempts_per_version;
    }


    /**
     * Gets the time_interval value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return time_interval
     */
    public String getTime_interval() {
        return time_interval;
    }


    /**
     * Sets the time_interval value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param time_interval
     */
    public void setTime_interval(String time_interval) {
        this.time_interval = time_interval;
    }


    /**
     * Gets the versions_per_interval value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return versions_per_interval
     */
    public String getVersions_per_interval() {
        return versions_per_interval;
    }


    /**
     * Sets the versions_per_interval value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param versions_per_interval
     */
    public void setVersions_per_interval(String versions_per_interval) {
        this.versions_per_interval = versions_per_interval;
    }


    /**
     * Gets the version_time_limit value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return version_time_limit
     */
    public String getVersion_time_limit() {
        return version_time_limit;
    }


    /**
     * Sets the version_time_limit value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param version_time_limit
     */
    public void setVersion_time_limit(String version_time_limit) {
        this.version_time_limit = version_time_limit;
    }


    /**
     * Gets the version_creation_time value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return version_creation_time
     */
    public String getVersion_creation_time() {
        return version_creation_time;
    }


    /**
     * Sets the version_creation_time value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param version_creation_time
     */
    public void setVersion_creation_time(String version_creation_time) {
        this.version_creation_time = version_creation_time;
    }


    /**
     * Gets the problem_randorder value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return problem_randorder
     */
    public String getProblem_randorder() {
        return problem_randorder;
    }


    /**
     * Sets the problem_randorder value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param problem_randorder
     */
    public void setProblem_randorder(String problem_randorder) {
        this.problem_randorder = problem_randorder;
    }


    /**
     * Gets the version_last_attempt_time value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return version_last_attempt_time
     */
    public String getVersion_last_attempt_time() {
        return version_last_attempt_time;
    }


    /**
     * Sets the version_last_attempt_time value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param version_last_attempt_time
     */
    public void setVersion_last_attempt_time(String version_last_attempt_time) {
        this.version_last_attempt_time = version_last_attempt_time;
    }


    /**
     * Gets the problems_per_page value for this WebworkSOAPClassesGlobalSet.
     * 
     * @return problems_per_page
     */
    public String getProblems_per_page() {
        return problems_per_page;
    }


    /**
     * Sets the problems_per_page value for this WebworkSOAPClassesGlobalSet.
     * 
     * @param problems_per_page
     */
    public void setProblems_per_page(String problems_per_page) {
        this.problems_per_page = problems_per_page;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof WebworkSOAPClassesGlobalSet)) 
        	return false;
        
        WebworkSOAPClassesGlobalSet other = (WebworkSOAPClassesGlobalSet) obj;
        
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
            ((this.set_id==null && other.getSet_id()==null) || 
             (this.set_id!=null &&
              this.set_id.equals(other.getSet_id()))) &&
            ((this.set_header==null && other.getSet_header()==null) || 
             (this.set_header!=null &&
              this.set_header.equals(other.getSet_header()))) &&
            ((this.hardcopy_header==null && other.getHardcopy_header()==null) || 
             (this.hardcopy_header!=null &&
              this.hardcopy_header.equals(other.getHardcopy_header()))) &&
            ((this.open_date==null && other.getOpen_date()==null) || 
             (this.open_date!=null &&
              this.open_date.equals(other.getOpen_date()))) &&
            ((this.due_date==null && other.getDue_date()==null) || 
             (this.due_date!=null &&
              this.due_date.equals(other.getDue_date()))) &&
            ((this.answer_date==null && other.getAnswer_date()==null) || 
             (this.answer_date!=null &&
              this.answer_date.equals(other.getAnswer_date()))) &&
            ((this.visible==null && other.getVisible()==null) || 
             (this.visible!=null &&
              this.visible.equals(other.getVisible()))) &&
            ((this.enable_reduced_scoring==null && other.getEnable_reduced_scoring()==null) || 
             (this.enable_reduced_scoring!=null &&
              this.enable_reduced_scoring.equals(other.getEnable_reduced_scoring()))) &&
            ((this.assignment_type==null && other.getAssignment_type()==null) || 
             (this.assignment_type!=null &&
              this.assignment_type.equals(other.getAssignment_type()))) &&
            ((this.attempts_per_version==null && other.getAttempts_per_version()==null) || 
             (this.attempts_per_version!=null &&
              this.attempts_per_version.equals(other.getAttempts_per_version()))) &&
            ((this.time_interval==null && other.getTime_interval()==null) || 
             (this.time_interval!=null &&
              this.time_interval.equals(other.getTime_interval()))) &&
            ((this.versions_per_interval==null && other.getVersions_per_interval()==null) || 
             (this.versions_per_interval!=null &&
              this.versions_per_interval.equals(other.getVersions_per_interval()))) &&
            ((this.version_time_limit==null && other.getVersion_time_limit()==null) || 
             (this.version_time_limit!=null &&
              this.version_time_limit.equals(other.getVersion_time_limit()))) &&
            ((this.version_creation_time==null && other.getVersion_creation_time()==null) || 
             (this.version_creation_time!=null &&
              this.version_creation_time.equals(other.getVersion_creation_time()))) &&
            ((this.problem_randorder==null && other.getProblem_randorder()==null) || 
             (this.problem_randorder!=null &&
              this.problem_randorder.equals(other.getProblem_randorder()))) &&
            ((this.version_last_attempt_time==null && other.getVersion_last_attempt_time()==null) || 
             (this.version_last_attempt_time!=null &&
              this.version_last_attempt_time.equals(other.getVersion_last_attempt_time()))) &&
            ((this.problems_per_page==null && other.getProblems_per_page()==null) || 
             (this.problems_per_page!=null &&
              this.problems_per_page.equals(other.getProblems_per_page())));
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
        if (getSet_header() != null) {
            _hashCode += getSet_header().hashCode();
        }
        if (getHardcopy_header() != null) {
            _hashCode += getHardcopy_header().hashCode();
        }
        if (getOpen_date() != null) {
            _hashCode += getOpen_date().hashCode();
        }
        if (getDue_date() != null) {
            _hashCode += getDue_date().hashCode();
        }
        if (getAnswer_date() != null) {
            _hashCode += getAnswer_date().hashCode();
        }
        if (getVisible() != null) {
            _hashCode += getVisible().hashCode();
        }
        if (getEnable_reduced_scoring() != null) {
            _hashCode += getEnable_reduced_scoring().hashCode();
        }
        if (getAssignment_type() != null) {
            _hashCode += getAssignment_type().hashCode();
        }
        if (getAttempts_per_version() != null) {
            _hashCode += getAttempts_per_version().hashCode();
        }
        if (getTime_interval() != null) {
            _hashCode += getTime_interval().hashCode();
        }
        if (getVersions_per_interval() != null) {
            _hashCode += getVersions_per_interval().hashCode();
        }
        if (getVersion_time_limit() != null) {
            _hashCode += getVersion_time_limit().hashCode();
        }
        if (getVersion_creation_time() != null) {
            _hashCode += getVersion_creation_time().hashCode();
        }
        if (getProblem_randorder() != null) {
            _hashCode += getProblem_randorder().hashCode();
        }
        if (getVersion_last_attempt_time() != null) {
            _hashCode += getVersion_last_attempt_time().hashCode();
        }
        if (getProblems_per_page() != null) {
            _hashCode += getProblems_per_page().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static TypeDesc typeDesc = new TypeDesc(WebworkSOAPClassesGlobalSet.class, true);

    static {
        typeDesc.setXmlType(new QName("https://webwork.math.missouri.edu/WebworkSOAP", "WebworkSOAPClassesGlobalSet"));
        ElementDesc elemField = new ElementDesc();
        elemField.setFieldName("set_id");
        elemField.setXmlName(new QName("", "set_id"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("set_header");
        elemField.setXmlName(new QName("", "set_header"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("hardcopy_header");
        elemField.setXmlName(new QName("", "hardcopy_header"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("open_date");
        elemField.setXmlName(new QName("", "open_date"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("due_date");
        elemField.setXmlName(new QName("", "due_date"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("answer_date");
        elemField.setXmlName(new QName("", "answer_date"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("visible");
        elemField.setXmlName(new QName("", "visible"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("enable_reduced_scoring");
        elemField.setXmlName(new QName("", "enable_reduced_scoring"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("assignment_type");
        elemField.setXmlName(new QName("", "assignment_type"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("attempts_per_version");
        elemField.setXmlName(new QName("", "attempts_per_version"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("time_interval");
        elemField.setXmlName(new QName("", "time_interval"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("versions_per_interval");
        elemField.setXmlName(new QName("", "versions_per_interval"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("version_time_limit");
        elemField.setXmlName(new QName("", "version_time_limit"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("version_creation_time");
        elemField.setXmlName(new QName("", "version_creation_time"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("problem_randorder");
        elemField.setXmlName(new QName("", "problem_randorder"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("version_last_attempt_time");
        elemField.setXmlName(new QName("", "version_last_attempt_time"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new ElementDesc();
        elemField.setFieldName("problems_per_page");
        elemField.setXmlName(new QName("", "problems_per_page"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
        return new BeanSerializer(_javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
        return new BeanDeserializer(_javaType, _xmlType, typeDesc);
    }

}
