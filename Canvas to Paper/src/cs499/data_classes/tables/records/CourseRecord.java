/*
 * This file is generated by jOOQ.
 */
package cs499.data_classes.tables.records;


import cs499.data_classes.tables.Course;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CourseRecord extends UpdatableRecordImpl<CourseRecord> implements Record4<Integer, String, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>course.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>course.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>course.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>course.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>course.api_id</code>.
     */
    public void setApiId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>course.api_id</code>.
     */
    public Integer getApiId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>course.instructor_id</code>.
     */
    public void setInstructorId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>course.instructor_id</code>.
     */
    public Integer getInstructorId() {
        return (Integer) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, Integer, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, String, Integer, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Course.COURSE.ID;
    }

    @Override
    public Field<String> field2() {
        return Course.COURSE.NAME;
    }

    @Override
    public Field<Integer> field3() {
        return Course.COURSE.API_ID;
    }

    @Override
    public Field<Integer> field4() {
        return Course.COURSE.INSTRUCTOR_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public Integer component3() {
        return getApiId();
    }

    @Override
    public Integer component4() {
        return getInstructorId();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public Integer value3() {
        return getApiId();
    }

    @Override
    public Integer value4() {
        return getInstructorId();
    }

    @Override
    public CourseRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public CourseRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public CourseRecord value3(Integer value) {
        setApiId(value);
        return this;
    }

    @Override
    public CourseRecord value4(Integer value) {
        setInstructorId(value);
        return this;
    }

    @Override
    public CourseRecord values(Integer value1, String value2, Integer value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CourseRecord
     */
    public CourseRecord() {
        super(Course.COURSE);
    }

    /**
     * Create a detached, initialised CourseRecord
     */
    public CourseRecord(Integer id, String name, Integer apiId, Integer instructorId) {
        super(Course.COURSE);

        setId(id);
        setName(name);
        setApiId(apiId);
        setInstructorId(instructorId);
    }
}
