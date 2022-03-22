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
 * <p>Java class for presentationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="presentationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="qticomment" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}qticommentType" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="flow" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}flowType"/&gt;
 *           &lt;choice maxOccurs="unbounded"&gt;
 *             &lt;element name="material" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}materialType"/&gt;
 *             &lt;element name="response_lid" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}response_lidType"/&gt;
 *             &lt;element name="response_xy" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}response_xyType"/&gt;
 *             &lt;element name="response_str" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}response_strType"/&gt;
 *             &lt;element name="response_num" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}response_numType"/&gt;
 *             &lt;element name="response_grp" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}response_grpType"/&gt;
 *             &lt;element ref="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}response_extension"/&gt;
 *           &lt;/choice&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="label" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/&gt;
 *       &lt;attribute name="y0" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="x0" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="width" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="height" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "presentationType", propOrder = {
    "qticomment",
    "flow",
    "materialOrResponseLidOrResponseXy"
})
public class PresentationType {

    protected QticommentType qticomment;
    protected FlowType flow;
    @XmlElements({
        @XmlElement(name = "material", type = MaterialType.class),
        @XmlElement(name = "response_lid", type = ResponseLidType.class),
        @XmlElement(name = "response_xy", type = ResponseXyType.class),
        @XmlElement(name = "response_str", type = ResponseStrType.class),
        @XmlElement(name = "response_num", type = ResponseNumType.class),
        @XmlElement(name = "response_grp", type = ResponseGrpType.class),
        @XmlElement(name = "response_extension", type = ResponseExtensionType.class)
    })
    protected List<Object> materialOrResponseLidOrResponseXy;
    @XmlAttribute(name = "label")
    protected String label;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;
    @XmlAttribute(name = "y0")
    protected String y0;
    @XmlAttribute(name = "x0")
    protected String x0;
    @XmlAttribute(name = "width")
    protected String width;
    @XmlAttribute(name = "height")
    protected String height;

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
     * Gets the value of the flow property.
     * 
     * @return
     *     possible object is
     *     {@link FlowType }
     *     
     */
    public FlowType getFlow() {
        return flow;
    }

    /**
     * Sets the value of the flow property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlowType }
     *     
     */
    public void setFlow(FlowType value) {
        this.flow = value;
    }

    /**
     * Gets the value of the materialOrResponseLidOrResponseXy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the materialOrResponseLidOrResponseXy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMaterialOrResponseLidOrResponseXy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MaterialType }
     * {@link ResponseLidType }
     * {@link ResponseXyType }
     * {@link ResponseStrType }
     * {@link ResponseNumType }
     * {@link ResponseGrpType }
     * {@link ResponseExtensionType }
     * 
     * 
     */
    public List<Object> getMaterialOrResponseLidOrResponseXy() {
        if (materialOrResponseLidOrResponseXy == null) {
            materialOrResponseLidOrResponseXy = new ArrayList<Object>();
        }
        return this.materialOrResponseLidOrResponseXy;
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
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

    /**
     * Gets the value of the y0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getY0() {
        return y0;
    }

    /**
     * Sets the value of the y0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setY0(String value) {
        this.y0 = value;
    }

    /**
     * Gets the value of the x0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getX0() {
        return x0;
    }

    /**
     * Sets the value of the x0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setX0(String value) {
        this.x0 = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidth(String value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeight(String value) {
        this.height = value;
    }

}
