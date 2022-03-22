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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for sectionfeedbackType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sectionfeedbackType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="qticomment" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}qticommentType" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="material" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}materialType" maxOccurs="unbounded"/&gt;
 *           &lt;element name="flow_mat" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}flow_matType" maxOccurs="unbounded"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="view" default="All"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *             &lt;enumeration value="All"/&gt;
 *             &lt;enumeration value="Administrator"/&gt;
 *             &lt;enumeration value="AdminAuthority"/&gt;
 *             &lt;enumeration value="Assessor"/&gt;
 *             &lt;enumeration value="Author"/&gt;
 *             &lt;enumeration value="Candidate"/&gt;
 *             &lt;enumeration value="InvigilatorProctor"/&gt;
 *             &lt;enumeration value="Psychometrician"/&gt;
 *             &lt;enumeration value="Scorer"/&gt;
 *             &lt;enumeration value="Tutor"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="ident" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sectionfeedbackType", propOrder = {
    "qticomment",
    "material",
    "flowMat"
})
public class SectionfeedbackType {

    protected QticommentType qticomment;
    protected List<MaterialType> material;
    @XmlElement(name = "flow_mat")
    protected List<FlowMatType> flowMat;
    @XmlAttribute(name = "view")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String view;
    @XmlAttribute(name = "ident", required = true)
    protected String ident;
    @XmlAttribute(name = "title")
    protected String title;

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
     * Gets the value of the material property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the material property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaterial().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaterialType }
     * 
     * 
     */
    public List<MaterialType> getMaterial() {
        if (material == null) {
            material = new ArrayList<MaterialType>();
        }
        return this.material;
    }

    /**
     * Gets the value of the flowMat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flowMat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlowMat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FlowMatType }
     * 
     * 
     */
    public List<FlowMatType> getFlowMat() {
        if (flowMat == null) {
            flowMat = new ArrayList<FlowMatType>();
        }
        return this.flowMat;
    }

    /**
     * Gets the value of the view property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getView() {
        if (view == null) {
            return "All";
        } else {
            return view;
        }
    }

    /**
     * Sets the value of the view property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setView(String value) {
        this.view = value;
    }

    /**
     * Gets the value of the ident property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdent() {
        return ident;
    }

    /**
     * Sets the value of the ident property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdent(String value) {
        this.ident = value;
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
