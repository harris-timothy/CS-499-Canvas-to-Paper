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
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for matapplicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="matapplicationType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="apptype" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="uri" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="embedded" type="{http://www.w3.org/2001/XMLSchema}string" default="base64" /&gt;
 *       &lt;attribute name="entityref" type="{http://www.w3.org/2001/XMLSchema}ENTITY" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "matapplicationType", propOrder = {
    "value"
})
public class MatapplicationType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "apptype")
    protected String apptype;
    @XmlAttribute(name = "label")
    protected String label;
    @XmlAttribute(name = "uri")
    protected String uri;
    @XmlAttribute(name = "embedded")
    protected String embedded;
    @XmlAttribute(name = "entityref")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "ENTITY")
    protected String entityref;

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
     * Gets the value of the apptype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApptype() {
        return apptype;
    }

    /**
     * Sets the value of the apptype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApptype(String value) {
        this.apptype = value;
    }

    /**
     * Gets the value of the label property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the embedded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmbedded() {
        if (embedded == null) {
            return "base64";
        } else {
            return embedded;
        }
    }

    /**
     * Sets the value of the embedded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmbedded(String value) {
        this.embedded = value;
    }

    /**
     * Gets the value of the entityref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityref() {
        return entityref;
    }

    /**
     * Sets the value of the entityref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityref(String value) {
        this.entityref = value;
    }

}
