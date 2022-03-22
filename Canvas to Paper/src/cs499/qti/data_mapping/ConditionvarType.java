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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for conditionvarType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="conditionvarType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element name="not" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}notType"/&gt;
 *         &lt;element name="and" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}andType"/&gt;
 *         &lt;element name="or" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}orType"/&gt;
 *         &lt;element name="unanswered" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}unansweredType"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}other"/&gt;
 *         &lt;element name="varequal" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}varequalType"/&gt;
 *         &lt;element name="varlt" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}varltType"/&gt;
 *         &lt;element name="varlte" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}varlteType"/&gt;
 *         &lt;element name="vargt" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}vargtType"/&gt;
 *         &lt;element name="vargte" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}vargteType"/&gt;
 *         &lt;element name="varsubset" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}varsubsetType"/&gt;
 *         &lt;element name="varinside" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}varinsideType"/&gt;
 *         &lt;element name="varsubstring" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}varsubstringType"/&gt;
 *         &lt;element name="durequal" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}durequalType"/&gt;
 *         &lt;element name="durlt" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}durltType"/&gt;
 *         &lt;element name="durlte" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}durlteType"/&gt;
 *         &lt;element name="durgt" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}durgtType"/&gt;
 *         &lt;element name="durgte" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}durgteType"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}var_extension"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conditionvarType", propOrder = {
    "notOrAndOrOr"
})
public class ConditionvarType {

    @XmlElements({
        @XmlElement(name = "not", type = NotType.class),
        @XmlElement(name = "and", type = AndType.class),
        @XmlElement(name = "or", type = OrType.class),
        @XmlElement(name = "unanswered", type = UnansweredType.class),
        @XmlElement(name = "other", type = String.class),
        @XmlElement(name = "varequal", type = VarequalType.class),
        @XmlElement(name = "varlt", type = VarltType.class),
        @XmlElement(name = "varlte", type = VarlteType.class),
        @XmlElement(name = "vargt", type = VargtType.class),
        @XmlElement(name = "vargte", type = VargteType.class),
        @XmlElement(name = "varsubset", type = VarsubsetType.class),
        @XmlElement(name = "varinside", type = VarinsideType.class),
        @XmlElement(name = "varsubstring", type = VarsubstringType.class),
        @XmlElement(name = "durequal", type = DurequalType.class),
        @XmlElement(name = "durlt", type = DurltType.class),
        @XmlElement(name = "durlte", type = DurlteType.class),
        @XmlElement(name = "durgt", type = DurgtType.class),
        @XmlElement(name = "durgte", type = DurgteType.class),
        @XmlElement(name = "var_extension", type = VarExtensionType.class)
    })
    protected List<Object> notOrAndOrOr;

    /**
     * Gets the value of the notOrAndOrOr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notOrAndOrOr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotOrAndOrOr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NotType }
     * {@link AndType }
     * {@link OrType }
     * {@link UnansweredType }
     * {@link String }
     * {@link VarequalType }
     * {@link VarltType }
     * {@link VarlteType }
     * {@link VargtType }
     * {@link VargteType }
     * {@link VarsubsetType }
     * {@link VarinsideType }
     * {@link VarsubstringType }
     * {@link DurequalType }
     * {@link DurltType }
     * {@link DurlteType }
     * {@link DurgtType }
     * {@link DurgteType }
     * {@link VarExtensionType }
     * 
     * 
     */
    public List<Object> getNotOrAndOrOr() {
        if (notOrAndOrOr == null) {
            notOrAndOrOr = new ArrayList<Object>();
        }
        return this.notOrAndOrOr;
    }

}
