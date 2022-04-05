//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.05 at 01:46:08 PM CDT 
//


package cs499.qti.package_mapping.lomresource;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 *                 The Educational complexType is the container for the information that describes the key educational or pedagogic characteristics of this learning object. This is pedagogical informtion essential to those involved in achieving a quality learning experience. The audience for this metadata includes teachers, managers, authors and learners. CC Resource Profile: Only single context and intendedUserRole elements are permitted.
 *             
 * 
 * <p>Java class for Educational.Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Educational.Type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="context" type="{http://ltsc.ieee.org/xsd/imsccv1p1/LOM/resource}Context.Type" minOccurs="0"/&gt;
 *         &lt;element name="intendedEndUserRole" type="{http://ltsc.ieee.org/xsd/imsccv1p1/LOM/resource}IntendedEndUserRole.Type" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Educational.Type", propOrder = {
    "context",
    "intendedEndUserRole"
})
public class EducationalType {

    protected ContextType context;
    @XmlElement(required = true)
    protected List<IntendedEndUserRoleType> intendedEndUserRole;

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link ContextType }
     *     
     */
    public ContextType getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContextType }
     *     
     */
    public void setContext(ContextType value) {
        this.context = value;
    }

    /**
     * Gets the value of the intendedEndUserRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the intendedEndUserRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntendedEndUserRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntendedEndUserRoleType }
     * 
     * 
     */
    public List<IntendedEndUserRoleType> getIntendedEndUserRole() {
        if (intendedEndUserRole == null) {
            intendedEndUserRole = new ArrayList<IntendedEndUserRoleType>();
        }
        return this.intendedEndUserRole;
    }

}
