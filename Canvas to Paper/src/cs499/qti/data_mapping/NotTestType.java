//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.03.21 at 06:58:23 PM CDT 
//


package cs499.qti.data_mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for not_testType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="not_testType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="variable_test" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}variable_testType"/&gt;
 *         &lt;element name="and_test" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}and_testType"/&gt;
 *         &lt;element name="or_test" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}or_testType"/&gt;
 *         &lt;element name="not_test" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}not_testType"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "not_testType", propOrder = {
    "variableTest",
    "andTest",
    "orTest",
    "notTest"
})
public class NotTestType {

    @XmlElement(name = "variable_test")
    protected VariableTestType variableTest;
    @XmlElement(name = "and_test")
    protected AndTestType andTest;
    @XmlElement(name = "or_test")
    protected OrTestType orTest;
    @XmlElement(name = "not_test")
    protected NotTestType notTest;

    /**
     * Gets the value of the variableTest property.
     * 
     * @return
     *     possible object is
     *     {@link VariableTestType }
     *     
     */
    public VariableTestType getVariableTest() {
        return variableTest;
    }

    /**
     * Sets the value of the variableTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link VariableTestType }
     *     
     */
    public void setVariableTest(VariableTestType value) {
        this.variableTest = value;
    }

    /**
     * Gets the value of the andTest property.
     * 
     * @return
     *     possible object is
     *     {@link AndTestType }
     *     
     */
    public AndTestType getAndTest() {
        return andTest;
    }

    /**
     * Sets the value of the andTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AndTestType }
     *     
     */
    public void setAndTest(AndTestType value) {
        this.andTest = value;
    }

    /**
     * Gets the value of the orTest property.
     * 
     * @return
     *     possible object is
     *     {@link OrTestType }
     *     
     */
    public OrTestType getOrTest() {
        return orTest;
    }

    /**
     * Sets the value of the orTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrTestType }
     *     
     */
    public void setOrTest(OrTestType value) {
        this.orTest = value;
    }

    /**
     * Gets the value of the notTest property.
     * 
     * @return
     *     possible object is
     *     {@link NotTestType }
     *     
     */
    public NotTestType getNotTest() {
        return notTest;
    }

    /**
     * Sets the value of the notTest property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotTestType }
     *     
     */
    public void setNotTest(NotTestType value) {
        this.notTest = value;
    }

}
