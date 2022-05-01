//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.04.05 at 04:00:55 PM CDT 
//


package cs499.qti.metadata_mapping;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all minOccurs="0"&gt;
 *         &lt;element name="topic_id" type="{http://www.w3.org/2001/XMLSchema}ID"/&gt;
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="assignment" type="{http://canvas.instructure.com/xsd/cccv1p0}assignmentType" minOccurs="0"/&gt;
 *         &lt;element name="posted_at" type="{http://canvas.instructure.com/xsd/cccv1p0}optional_dateTime" minOccurs="0"/&gt;
 *         &lt;element name="delayed_post_at" type="{http://canvas.instructure.com/xsd/cccv1p0}optional_dateTime" minOccurs="0"/&gt;
 *         &lt;element name="lock_at" type="{http://canvas.instructure.com/xsd/cccv1p0}optional_dateTime" minOccurs="0"/&gt;
 *         &lt;element name="position" type="{http://canvas.instructure.com/xsd/cccv1p0}optional_integer" minOccurs="0"/&gt;
 *         &lt;element name="pinned" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="require_initial_post" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="external_feed_identifierref" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="attachment_identifierref" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="workflow_state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="has_group_category" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="group_category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="allow_rating" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="module_locked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="only_graders_can_rate" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="sort_by_rating" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="todo_date" type="{http://canvas.instructure.com/xsd/cccv1p0}optional_dateTime" minOccurs="0"/&gt;
 *         &lt;element name="discussion_type" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="side_comment"/&gt;
 *               &lt;enumeration value="threaded"/&gt;
 *               &lt;enumeration value="flat"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="type" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;enumeration value="announcement"/&gt;
 *               &lt;enumeration value="topic"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/all&gt;
 *       &lt;attribute name="identifier" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "topicMeta")
public class TopicMeta {

    @XmlElement(name = "topic_id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String topicId;
    protected String title;
    protected AssignmentType assignment;
    @XmlElement(name = "posted_at")
    protected String postedAt;
    @XmlElement(name = "delayed_post_at")
    protected String delayedPostAt;
    @XmlElement(name = "lock_at")
    protected String lockAt;
    protected String position;
    protected Boolean pinned;
    @XmlElement(name = "require_initial_post")
    protected Boolean requireInitialPost;
    @XmlElement(name = "external_feed_identifierref")
    protected String externalFeedIdentifierref;
    @XmlElement(name = "attachment_identifierref")
    protected String attachmentIdentifierref;
    @XmlElement(name = "workflow_state")
    protected String workflowState;
    @XmlElement(name = "has_group_category")
    protected Boolean hasGroupCategory;
    @XmlElement(name = "group_category")
    protected String groupCategory;
    @XmlElement(name = "allow_rating")
    protected Boolean allowRating;
    @XmlElement(name = "module_locked")
    protected Boolean moduleLocked;
    @XmlElement(name = "only_graders_can_rate")
    protected Boolean onlyGradersCanRate;
    @XmlElement(name = "sort_by_rating")
    protected Boolean sortByRating;
    @XmlElement(name = "todo_date")
    protected String todoDate;
    @XmlElement(name = "discussion_type")
    protected String discussionType;
    protected String type;
    @XmlAttribute(name = "identifier", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String identifier;

    /**
     * Gets the value of the topicId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * Sets the value of the topicId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTopicId(String value) {
        this.topicId = value;
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

    /**
     * Gets the value of the assignment property.
     * 
     * @return
     *     possible object is
     *     {@link AssignmentType }
     *     
     */
    public AssignmentType getAssignment() {
        return assignment;
    }

    /**
     * Sets the value of the assignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssignmentType }
     *     
     */
    public void setAssignment(AssignmentType value) {
        this.assignment = value;
    }

    /**
     * Gets the value of the postedAt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostedAt() {
        return postedAt;
    }

    /**
     * Sets the value of the postedAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostedAt(String value) {
        this.postedAt = value;
    }

    /**
     * Gets the value of the delayedPostAt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelayedPostAt() {
        return delayedPostAt;
    }

    /**
     * Sets the value of the delayedPostAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelayedPostAt(String value) {
        this.delayedPostAt = value;
    }

    /**
     * Gets the value of the lockAt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLockAt() {
        return lockAt;
    }

    /**
     * Sets the value of the lockAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLockAt(String value) {
        this.lockAt = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the pinned property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPinned() {
        return pinned;
    }

    /**
     * Sets the value of the pinned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPinned(Boolean value) {
        this.pinned = value;
    }

    /**
     * Gets the value of the requireInitialPost property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRequireInitialPost() {
        return requireInitialPost;
    }

    /**
     * Sets the value of the requireInitialPost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRequireInitialPost(Boolean value) {
        this.requireInitialPost = value;
    }

    /**
     * Gets the value of the externalFeedIdentifierref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalFeedIdentifierref() {
        return externalFeedIdentifierref;
    }

    /**
     * Sets the value of the externalFeedIdentifierref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalFeedIdentifierref(String value) {
        this.externalFeedIdentifierref = value;
    }

    /**
     * Gets the value of the attachmentIdentifierref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachmentIdentifierref() {
        return attachmentIdentifierref;
    }

    /**
     * Sets the value of the attachmentIdentifierref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachmentIdentifierref(String value) {
        this.attachmentIdentifierref = value;
    }

    /**
     * Gets the value of the workflowState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflowState() {
        return workflowState;
    }

    /**
     * Sets the value of the workflowState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflowState(String value) {
        this.workflowState = value;
    }

    /**
     * Gets the value of the hasGroupCategory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasGroupCategory() {
        return hasGroupCategory;
    }

    /**
     * Sets the value of the hasGroupCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasGroupCategory(Boolean value) {
        this.hasGroupCategory = value;
    }

    /**
     * Gets the value of the groupCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupCategory() {
        return groupCategory;
    }

    /**
     * Sets the value of the groupCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupCategory(String value) {
        this.groupCategory = value;
    }

    /**
     * Gets the value of the allowRating property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowRating() {
        return allowRating;
    }

    /**
     * Sets the value of the allowRating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowRating(Boolean value) {
        this.allowRating = value;
    }

    /**
     * Gets the value of the moduleLocked property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isModuleLocked() {
        return moduleLocked;
    }

    /**
     * Sets the value of the moduleLocked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setModuleLocked(Boolean value) {
        this.moduleLocked = value;
    }

    /**
     * Gets the value of the onlyGradersCanRate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOnlyGradersCanRate() {
        return onlyGradersCanRate;
    }

    /**
     * Sets the value of the onlyGradersCanRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnlyGradersCanRate(Boolean value) {
        this.onlyGradersCanRate = value;
    }

    /**
     * Gets the value of the sortByRating property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSortByRating() {
        return sortByRating;
    }

    /**
     * Sets the value of the sortByRating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSortByRating(Boolean value) {
        this.sortByRating = value;
    }

    /**
     * Gets the value of the todoDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTodoDate() {
        return todoDate;
    }

    /**
     * Sets the value of the todoDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTodoDate(String value) {
        this.todoDate = value;
    }

    /**
     * Gets the value of the discussionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscussionType() {
        return discussionType;
    }

    /**
     * Sets the value of the discussionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscussionType(String value) {
        this.discussionType = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifier(String value) {
        this.identifier = value;
    }

}
