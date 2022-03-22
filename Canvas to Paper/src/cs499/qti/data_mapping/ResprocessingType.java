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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resprocessingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resprocessingType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="qticomment" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}qticommentType" minOccurs="0"/&gt;
 *         &lt;element name="outcomes" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}outcomesType"/&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="respcondition" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}respconditionType"/&gt;
 *           &lt;element ref="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}itemproc_extension"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="scoremodel" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resprocessingType", propOrder = {
    "qticomment",
    "outcomes",
    "respconditionOrItemprocExtension"
})
public class ResprocessingType {

    protected QticommentType qticomment;
    @XmlElement(required = true)
    protected OutcomesType outcomes;
    @XmlElements({
        @XmlElement(name = "respcondition", type = RespconditionType.class),
        @XmlElement(name = "itemproc_extension", type = ItemprocExtensionType.class)
    })
    protected List<Object> respconditionOrItemprocExtension;
    @XmlAttribute(name = "scoremodel")
    protected String scoremodel;

    /**
     * Gets the value of the qticomment property.
     * 
     * @return
     *     possible object is
     *     {@link QticommentType }
     *     
     */
    public QticommentType getQticomment() {
        return qticomment;
    }

    /**
     * Sets the value of the qticomment property.
     * 
     * @param value
     *     allowed object is
     *     {@link QticommentType }
     *     
     */
    public void setQticomment(QticommentType value) {
        this.qticomment = value;
    }

    /**
     * Gets the value of the outcomes property.
     * 
     * @return
     *     possible object is
     *     {@link OutcomesType }
     *     
     */
    public OutcomesType getOutcomes() {
        return outcomes;
    }

    /**
     * Sets the value of the outcomes property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutcomesType }
     *     
     */
    public void setOutcomes(OutcomesType value) {
        this.outcomes = value;
    }

    /**
     * Gets the value of the respconditionOrItemprocExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the respconditionOrItemprocExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRespconditionOrItemprocExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RespconditionType }
     * {@link ItemprocExtensionType }
     * 
     * 
     */
    public List<Object> getRespconditionOrItemprocExtension() {
        if (respconditionOrItemprocExtension == null) {
            respconditionOrItemprocExtension = new ArrayList<Object>();
        }
        return this.respconditionOrItemprocExtension;
    }

    /**
     * Gets the value of the scoremodel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScoremodel() {
        return scoremodel;
    }

    /**
     * Sets the value of the scoremodel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScoremodel(String value) {
        this.scoremodel = value;
    }

}
