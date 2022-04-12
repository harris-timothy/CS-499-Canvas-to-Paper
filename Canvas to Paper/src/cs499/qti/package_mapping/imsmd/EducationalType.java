//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.06 at 01:47:15 AM CDT 
//


package cs499.qti.package_mapping.imsmd;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for educationalType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="educationalType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}interactivitytype" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}learningresourcetype" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}interactivitylevel" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}semanticdensity" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}intendedenduserrole" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}context" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}typicalagerange" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}difficulty" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}typicallearningtime" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}description" minOccurs="0"/&gt;
 *         &lt;element ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}language" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;group ref="{http://www.imsglobal.org/xsd/imsmd_v1p2}grp.any"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "educationalType", propOrder = {
    "content"
})
public class EducationalType {

    @XmlElementRefs({
        @XmlElementRef(name = "interactivitytype", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "learningresourcetype", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "interactivitylevel", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "semanticdensity", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "intendedenduserrole", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "context", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "typicalagerange", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "difficulty", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "typicallearningtime", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "description", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "language", namespace = "http://www.imsglobal.org/xsd/imsmd_v1p2", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    @XmlAnyElement(lax = true)
    protected List<Object> content;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link InteractivitytypeType }{@code >}
     * {@link JAXBElement }{@code <}{@link LearningresourcetypeType }{@code >}
     * {@link JAXBElement }{@code <}{@link InteractivitylevelType }{@code >}
     * {@link JAXBElement }{@code <}{@link SemanticdensityType }{@code >}
     * {@link JAXBElement }{@code <}{@link IntendedenduserroleType }{@code >}
     * {@link JAXBElement }{@code <}{@link ContextType }{@code >}
     * {@link JAXBElement }{@code <}{@link TypicalagerangeType }{@code >}
     * {@link JAXBElement }{@code <}{@link DifficultyType }{@code >}
     * {@link JAXBElement }{@code <}{@link TypicallearningtimeType }{@code >}
     * {@link JAXBElement }{@code <}{@link DescriptionType }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link Object }
     * {@link String }
     * 
     * 
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

}
