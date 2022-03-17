/*
 * This file is generated by jOOQ.
 */
package cs499.data_classes.tables.records;


import cs499.data_classes.tables.Instructor;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InstructorRecord extends UpdatableRecordImpl<InstructorRecord> implements Record6<Integer, String, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>instructor.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>instructor.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>instructor.fname</code>.
     */
    public void setFname(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>instructor.fname</code>.
     */
    public String getFname() {
        return (String) get(1);
    }

    /**
     * Setter for <code>instructor.lname</code>.
     */
    public void setLname(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>instructor.lname</code>.
     */
    public String getLname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>instructor.title</code>.
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>instructor.title</code>.
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>instructor.email</code>.
     */
    public void setEmail(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>instructor.email</code>.
     */
    public String getEmail() {
        return (String) get(4);
    }

    /**
     * Setter for <code>instructor.token</code>.
     */
    public void setToken(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>instructor.token</code>.
     */
    public String getToken() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Integer, String, String> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Instructor.INSTRUCTOR.ID;
    }

    @Override
    public Field<String> field2() {
        return Instructor.INSTRUCTOR.FNAME;
    }

    @Override
    public Field<String> field3() {
        return Instructor.INSTRUCTOR.LNAME;
    }

    @Override
    public Field<String> field4() {
        return Instructor.INSTRUCTOR.TITLE;
    }

    @Override
    public Field<String> field5() {
        return Instructor.INSTRUCTOR.EMAIL;
    }

    @Override
    public Field<String> field6() {
        return Instructor.INSTRUCTOR.TOKEN;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getFname();
    }

    @Override
    public String component3() {
        return getLname();
    }

    @Override
    public String component4() {
        return getTitle();
    }

    @Override
    public String component5() {
        return getEmail();
    }

    @Override
    public String component6() {
        return getToken();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getFname();
    }

    @Override
    public String value3() {
        return getLname();
    }

    @Override
    public String value4() {
        return getTitle();
    }

    @Override
    public String value5() {
        return getEmail();
    }

    @Override
    public String value6() {
        return getToken();
    }

    @Override
    public InstructorRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public InstructorRecord value2(String value) {
        setFname(value);
        return this;
    }

    @Override
    public InstructorRecord value3(String value) {
        setLname(value);
        return this;
    }

    @Override
    public InstructorRecord value4(String value) {
        setTitle(value);
        return this;
    }

    @Override
    public InstructorRecord value5(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public InstructorRecord value6(String value) {
        setToken(value);
        return this;
    }

    @Override
    public InstructorRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached InstructorRecord
     */
    public InstructorRecord() {
        super(Instructor.INSTRUCTOR);
    }

    /**
     * Create a detached, initialised InstructorRecord
     */
    public InstructorRecord(Integer id, String fname, String lname, String title, String email, String token) {
        super(Instructor.INSTRUCTOR);

        setId(id);
        setFname(fname);
        setLname(lname);
        setTitle(title);
        setEmail(email);
        setToken(token);
    }
}
