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
 * <p>Java class for flow_labelType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flow_labelType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}qticomment" minOccurs="0"/&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="flow_label" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}flow_labelType"/&gt;
 *           &lt;element name="response_label" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}response_labelType"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" default="Block" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flow_labelType", propOrder = {
    "qticomment",
    "flowLabelOrResponseLabel"
})
public class FlowLabelType {

    protected QticommentType qticomment;
    @XmlElements({
        @XmlElement(name = "flow_label", type = FlowLabelType.class),
        @XmlElement(name = "response_label", type = ResponseLabelType.class)
    })
    protected List<Object> flowLabelOrResponseLabel;
    @XmlAttribute(name = "class")
    protected String clazz;

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
     * Gets the value of the flowLabelOrResponseLabel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flowLabelOrResponseLabel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlowLabelOrResponseLabel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlowLabelType }
     * {@link ResponseLabelType }
     * 
     * 
     */
    public List<Object> getFlowLabelOrResponseLabel() {
        if (flowLabelOrResponseLabel == null) {
            flowLabelOrResponseLabel = new ArrayList<Object>();
        }
        return this.flowLabelOrResponseLabel;
    }

    /**
     * Gets the value of the clazz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        if (clazz == null) {
            return "Block";
        } else {
            return clazz;
        }
    }

    /**
     * Sets the value of the clazz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

}
