//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.03.21 at 06:58:23 PM CDT 
//


package cs499.qti.data_mapping;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for outcomes_feedback_testType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="outcomes_feedback_testType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="test_variable" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}test_variableType"/&gt;
 *         &lt;element name="displayfeedback" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}displayfeedbackType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "outcomes_feedback_testType", propOrder = {
    "testVariable",
    "displayfeedback"
})
public class OutcomesFeedbackTestType {

    @XmlElement(name = "test_variable", required = true)
    protected TestVariableType testVariable;
    @XmlElement(required = true)
    protected List<DisplayfeedbackType> displayfeedback;
    @XmlAttribute(name = "title")
    protected String title;

    /**
     * Gets the value of the testVariable property.
     * 
     * @return
     *     possible object is
     *     {@link TestVariableType }
     *     
     */
    public TestVariableType getTestVariable() {
        return testVariable;
    }

    /**
     * Sets the value of the testVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestVariableType }
     *     
     */
    public void setTestVariable(TestVariableType value) {
        this.testVariable = value;
    }

    /**
     * Gets the value of the displayfeedback property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the displayfeedback property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisplayfeedback().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DisplayfeedbackType }
     * 
     * 
     */
    public List<DisplayfeedbackType> getDisplayfeedback() {
        if (displayfeedback == null) {
            displayfeedback = new ArrayList<DisplayfeedbackType>();
        }
        return this.displayfeedback;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

}
