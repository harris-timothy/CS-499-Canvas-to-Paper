//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.04 at 12:42:32 PM CDT 
//


package cs499.qti.data_mapping;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for decvarType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="decvarType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="varname" type="{http://www.w3.org/2001/XMLSchema}string" default="SCORE" /&gt;
 *       &lt;attribute name="vartype" default="Integer"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *             &lt;enumeration value="Integer"/&gt;
 *             &lt;enumeration value="String"/&gt;
 *             &lt;enumeration value="Decimal"/&gt;
 *             &lt;enumeration value="Scientific"/&gt;
 *             &lt;enumeration value="Boolean"/&gt;
 *             &lt;enumeration value="Enumerated"/&gt;
 *             &lt;enumeration value="Set"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="defaultval" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="minvalue" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="maxvalue" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="members" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="cutvalue" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "decvarType", propOrder = {
    "value"
})
public class DecvarType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "varname")
    protected String varname;
    @XmlAttribute(name = "vartype")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String vartype;
    @XmlAttribute(name = "defaultval")
    protected String defaultval;
    @XmlAttribute(name = "minvalue")
    protected String minvalue;
    @XmlAttribute(name = "maxvalue")
    protected String maxvalue;
    @XmlAttribute(name = "members")
    protected String members;
    @XmlAttribute(name = "cutvalue")
    protected String cutvalue;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the varname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVarname() {
        if (varname == null) {
            return "SCORE";
        } else {
            return varname;
        }
    }

    /**
     * Sets the value of the varname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVarname(String value) {
        this.varname = value;
    }

    /**
     * Gets the value of the vartype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVartype() {
        if (vartype == null) {
            return "Integer";
        } else {
            return vartype;
        }
    }

    /**
     * Sets the value of the vartype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVartype(String value) {
        this.vartype = value;
    }

    /**
     * Gets the value of the defaultval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultval() {
        return defaultval;
    }

    /**
     * Sets the value of the defaultval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultval(String value) {
        this.defaultval = value;
    }

    /**
     * Gets the value of the minvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinvalue() {
        return minvalue;
    }

    /**
     * Sets the value of the minvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinvalue(String value) {
        this.minvalue = value;
    }

    /**
     * Gets the value of the maxvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxvalue() {
        return maxvalue;
    }

    /**
     * Sets the value of the maxvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxvalue(String value) {
        this.maxvalue = value;
    }

    /**
     * Gets the value of the members property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMembers() {
        return members;
    }

    /**
     * Sets the value of the members property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMembers(String value) {
        this.members = value;
    }

    /**
     * Gets the value of the cutvalue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCutvalue() {
        return cutvalue;
    }

    /**
     * Sets the value of the cutvalue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCutvalue(String value) {
        this.cutvalue = value;
    }

}
