//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.04 at 12:42:32 PM CDT 
//


package cs499.qti.data_mapping;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for altmaterialType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="altmaterialType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="qticomment" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}qticommentType" minOccurs="0"/&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="mattext" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}mattextType"/&gt;
 *           &lt;element name="matemtext" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}matemtextType"/&gt;
 *           &lt;element name="matimage" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}matimageType"/&gt;
 *           &lt;element name="mataudio" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}mataudioType"/&gt;
 *           &lt;element name="matvideo" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}matvideoType"/&gt;
 *           &lt;element name="matapplet" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}matappletType"/&gt;
 *           &lt;element name="matapplication" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}matapplicationType"/&gt;
 *           &lt;element name="matref" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}matrefType"/&gt;
 *           &lt;element ref="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}matbreak"/&gt;
 *           &lt;element ref="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}mat_extension"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "altmaterialType", propOrder = {
    "qticomment",
    "mattextOrMatemtextOrMatimage"
})
public class AltmaterialType {

    protected QticommentType qticomment;
    @XmlElements({
        @XmlElement(name = "mattext", type = MattextType.class),
        @XmlElement(name = "matemtext", type = MatemtextType.class),
        @XmlElement(name = "matimage", type = MatimageType.class),
        @XmlElement(name = "mataudio", type = MataudioType.class),
        @XmlElement(name = "matvideo", type = MatvideoType.class),
        @XmlElement(name = "matapplet", type = MatappletType.class),
        @XmlElement(name = "matapplication", type = MatapplicationType.class),
        @XmlElement(name = "matref", type = MatrefType.class),
        @XmlElement(name = "matbreak", type = MatbreakType.class),
        @XmlElement(name = "mat_extension", type = MatExtensionType.class)
    })
    protected List<Object> mattextOrMatemtextOrMatimage;
    @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
    protected String lang;

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
     * Gets the value of the mattextOrMatemtextOrMatimage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the mattextOrMatemtextOrMatimage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMattextOrMatemtextOrMatimage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MattextType }
     * {@link MatemtextType }
     * {@link MatimageType }
     * {@link MataudioType }
     * {@link MatvideoType }
     * {@link MatappletType }
     * {@link MatapplicationType }
     * {@link MatrefType }
     * {@link MatbreakType }
     * {@link MatExtensionType }
     * 
     * 
     */
    public List<Object> getMattextOrMatemtextOrMatimage() {
        if (mattextOrMatemtextOrMatimage == null) {
            mattextOrMatemtextOrMatimage = new ArrayList<Object>();
        }
        return this.mattextOrMatemtextOrMatimage;
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

}
