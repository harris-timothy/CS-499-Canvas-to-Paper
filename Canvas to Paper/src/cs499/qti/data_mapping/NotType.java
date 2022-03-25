//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.03.21 at 06:58:23 PM CDT 
//


package cs499.qti.data_mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for notType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="and" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}andType"/&gt;
 *         &lt;element name="or" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}orType"/&gt;
 *         &lt;element name="not" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}notType"/&gt;
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
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notType", propOrder = {
    "and",
    "or",
    "not",
    "unanswered",
    "other",
    "varequal",
    "varlt",
    "varlte",
    "vargt",
    "vargte",
    "varsubset",
    "varinside",
    "varsubstring",
    "durequal",
    "durlt",
    "durlte",
    "durgt",
    "durgte"
})
public class NotType {

    protected AndType and;
    protected OrType or;
    protected NotType not;
    protected UnansweredType unanswered;
    protected String other;
    protected VarequalType varequal;
    protected VarltType varlt;
    protected VarlteType varlte;
    protected VargtType vargt;
    protected VargteType vargte;
    protected VarsubsetType varsubset;
    protected VarinsideType varinside;
    protected VarsubstringType varsubstring;
    protected DurequalType durequal;
    protected DurltType durlt;
    protected DurlteType durlte;
    protected DurgtType durgt;
    protected DurgteType durgte;

    /**
     * Gets the value of the and property.
     * 
     * @return
     *     possible object is
     *     {@link AndType }
     *     
     */
    public AndType getAnd() {
        return and;
    }

    /**
     * Sets the value of the and property.
     * 
     * @param value
     *     allowed object is
     *     {@link AndType }
     *     
     */
    public void setAnd(AndType value) {
        this.and = value;
    }

    /**
     * Gets the value of the or property.
     * 
     * @return
     *     possible object is
     *     {@link OrType }
     *     
     */
    public OrType getOr() {
        return or;
    }

    /**
     * Sets the value of the or property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrType }
     *     
     */
    public void setOr(OrType value) {
        this.or = value;
    }

    /**
     * Gets the value of the not property.
     * 
     * @return
     *     possible object is
     *     {@link NotType }
     *     
     */
    public NotType getNot() {
        return not;
    }

    /**
     * Sets the value of the not property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotType }
     *     
     */
    public void setNot(NotType value) {
        this.not = value;
    }

    /**
     * Gets the value of the unanswered property.
     * 
     * @return
     *     possible object is
     *     {@link UnansweredType }
     *     
     */
    public UnansweredType getUnanswered() {
        return unanswered;
    }

    /**
     * Sets the value of the unanswered property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnansweredType }
     *     
     */
    public void setUnanswered(UnansweredType value) {
        this.unanswered = value;
    }

    /**
     * Gets the value of the other property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOther(String value) {
        this.other = value;
    }

    /**
     * Gets the value of the varequal property.
     * 
     * @return
     *     possible object is
     *     {@link VarequalType }
     *     
     */
    public VarequalType getVarequal() {
        return varequal;
    }

    /**
     * Sets the value of the varequal property.
     * 
     * @param value
     *     allowed object is
     *     {@link VarequalType }
     *     
     */
    public void setVarequal(VarequalType value) {
        this.varequal = value;
    }

    /**
     * Gets the value of the varlt property.
     * 
     * @return
     *     possible object is
     *     {@link VarltType }
     *     
     */
    public VarltType getVarlt() {
        return varlt;
    }

    /**
     * Sets the value of the varlt property.
     * 
     * @param value
     *     allowed object is
     *     {@link VarltType }
     *     
     */
    public void setVarlt(VarltType value) {
        this.varlt = value;
    }

    /**
     * Gets the value of the varlte property.
     * 
     * @return
     *     possible object is
     *     {@link VarlteType }
     *     
     */
    public VarlteType getVarlte() {
        return varlte;
    }

    /**
     * Sets the value of the varlte property.
     * 
     * @param value
     *     allowed object is
     *     {@link VarlteType }
     *     
     */
    public void setVarlte(VarlteType value) {
        this.varlte = value;
    }

    /**
     * Gets the value of the vargt property.
     * 
     * @return
     *     possible object is
     *     {@link VargtType }
     *     
     */
    public VargtType getVargt() {
        return vargt;
    }

    /**
     * Sets the value of the vargt property.
     * 
     * @param value
     *     allowed object is
     *     {@link VargtType }
     *     
     */
    public void setVargt(VargtType value) {
        this.vargt = value;
    }

    /**
     * Gets the value of the vargte property.
     * 
     * @return
     *     possible object is
     *     {@link VargteType }
     *     
     */
    public VargteType getVargte() {
        return vargte;
    }

    /**
     * Sets the value of the vargte property.
     * 
     * @param value
     *     allowed object is
     *     {@link VargteType }
     *     
     */
    public void setVargte(VargteType value) {
        this.vargte = value;
    }

    /**
     * Gets the value of the varsubset property.
     * 
     * @return
     *     possible object is
     *     {@link VarsubsetType }
     *     
     */
    public VarsubsetType getVarsubset() {
        return varsubset;
    }

    /**
     * Sets the value of the varsubset property.
     * 
     * @param value
     *     allowed object is
     *     {@link VarsubsetType }
     *     
     */
    public void setVarsubset(VarsubsetType value) {
        this.varsubset = value;
    }

    /**
     * Gets the value of the varinside property.
     * 
     * @return
     *     possible object is
     *     {@link VarinsideType }
     *     
     */
    public VarinsideType getVarinside() {
        return varinside;
    }

    /**
     * Sets the value of the varinside property.
     * 
     * @param value
     *     allowed object is
     *     {@link VarinsideType }
     *     
     */
    public void setVarinside(VarinsideType value) {
        this.varinside = value;
    }

    /**
     * Gets the value of the varsubstring property.
     * 
     * @return
     *     possible object is
     *     {@link VarsubstringType }
     *     
     */
    public VarsubstringType getVarsubstring() {
        return varsubstring;
    }

    /**
     * Sets the value of the varsubstring property.
     * 
     * @param value
     *     allowed object is
     *     {@link VarsubstringType }
     *     
     */
    public void setVarsubstring(VarsubstringType value) {
        this.varsubstring = value;
    }

    /**
     * Gets the value of the durequal property.
     * 
     * @return
     *     possible object is
     *     {@link DurequalType }
     *     
     */
    public DurequalType getDurequal() {
        return durequal;
    }

    /**
     * Sets the value of the durequal property.
     * 
     * @param value
     *     allowed object is
     *     {@link DurequalType }
     *     
     */
    public void setDurequal(DurequalType value) {
        this.durequal = value;
    }

    /**
     * Gets the value of the durlt property.
     * 
     * @return
     *     possible object is
     *     {@link DurltType }
     *     
     */
    public DurltType getDurlt() {
        return durlt;
    }

    /**
     * Sets the value of the durlt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DurltType }
     *     
     */
    public void setDurlt(DurltType value) {
        this.durlt = value;
    }

    /**
     * Gets the value of the durlte property.
     * 
     * @return
     *     possible object is
     *     {@link DurlteType }
     *     
     */
    public DurlteType getDurlte() {
        return durlte;
    }

    /**
     * Sets the value of the durlte property.
     * 
     * @param value
     *     allowed object is
     *     {@link DurlteType }
     *     
     */
    public void setDurlte(DurlteType value) {
        this.durlte = value;
    }

    /**
     * Gets the value of the durgt property.
     * 
     * @return
     *     possible object is
     *     {@link DurgtType }
     *     
     */
    public DurgtType getDurgt() {
        return durgt;
    }

    /**
     * Sets the value of the durgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DurgtType }
     *     
     */
    public void setDurgt(DurgtType value) {
        this.durgt = value;
    }

    /**
     * Gets the value of the durgte property.
     * 
     * @return
     *     possible object is
     *     {@link DurgteType }
     *     
     */
    public DurgteType getDurgte() {
        return durgte;
    }

    /**
     * Sets the value of the durgte property.
     * 
     * @param value
     *     allowed object is
     *     {@link DurgteType }
     *     
     */
    public void setDurgte(DurgteType value) {
        this.durgte = value;
    }

}