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
 * <p>Java class for flow_matType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="flow_matType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}qticomment" minOccurs="0"/&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="flow_mat" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}flow_matType"/&gt;
 *           &lt;element name="material" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}materialType"/&gt;
 *           &lt;element name="material_ref" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}material_refType"/&gt;
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
@XmlType(name = "flow_matType", propOrder = {
    "qticomment",
    "flowMatOrMaterialOrMaterialRef"
})
public class FlowMatType {

    protected QticommentType qticomment;
    @XmlElements({
        @XmlElement(name = "flow_mat", type = FlowMatType.class),
        @XmlElement(name = "material", type = MaterialType.class),
        @XmlElement(name = "material_ref", type = MaterialRefType.class)
    })
    protected List<Object> flowMatOrMaterialOrMaterialRef;
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
     * Gets the value of the flowMatOrMaterialOrMaterialRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flowMatOrMaterialOrMaterialRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlowMatOrMaterialOrMaterialRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlowMatType }
     * {@link MaterialType }
     * {@link MaterialRefType }
     * 
     * 
     */
    public List<Object> getFlowMatOrMaterialOrMaterialRef() {
        if (flowMatOrMaterialOrMaterialRef == null) {
            flowMatOrMaterialOrMaterialRef = new ArrayList<Object>();
        }
        return this.flowMatOrMaterialOrMaterialRef;
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
