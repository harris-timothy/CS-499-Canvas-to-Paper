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
 * <p>Java class for and_selectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="and_selectionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded"&gt;
 *         &lt;element name="selection_metadata" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}selection_metadataType"/&gt;
 *         &lt;element name="and_selection" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}and_selectionType"/&gt;
 *         &lt;element name="or_selection" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}or_selectionType"/&gt;
 *         &lt;element name="not_selection" type="{http://www.imsglobal.org/xsd/ims_qtiasiv1p2}not_selectionType"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "and_selectionType", propOrder = {
    "selectionMetadataOrAndSelectionOrOrSelection"
})
public class AndSelectionType {

    @XmlElements({
        @XmlElement(name = "selection_metadata", type = SelectionMetadataType.class),
        @XmlElement(name = "and_selection", type = AndSelectionType.class),
        @XmlElement(name = "or_selection", type = OrSelectionType.class),
        @XmlElement(name = "not_selection", type = NotSelectionType.class)
    })
    protected List<Object> selectionMetadataOrAndSelectionOrOrSelection;

    /**
     * Gets the value of the selectionMetadataOrAndSelectionOrOrSelection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selectionMetadataOrAndSelectionOrOrSelection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelectionMetadataOrAndSelectionOrOrSelection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SelectionMetadataType }
     * {@link AndSelectionType }
     * {@link OrSelectionType }
     * {@link NotSelectionType }
     * 
     * 
     */
    public List<Object> getSelectionMetadataOrAndSelectionOrOrSelection() {
        if (selectionMetadataOrAndSelectionOrOrSelection == null) {
            selectionMetadataOrAndSelectionOrOrSelection = new ArrayList<Object>();
        }
        return this.selectionMetadataOrAndSelectionOrOrSelection;
    }

}
