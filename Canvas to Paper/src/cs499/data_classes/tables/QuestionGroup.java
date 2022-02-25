/*
 * This file is generated by jOOQ.
 */
package cs499.data_classes.tables;


import cs499.data_classes.DefaultSchema;
import cs499.data_classes.Indexes;
import cs499.data_classes.Keys;
import cs499.data_classes.tables.records.QuestionGroupRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuestionGroup extends TableImpl<QuestionGroupRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>question_group</code>
     */
    public static final QuestionGroup QUESTION_GROUP = new QuestionGroup();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QuestionGroupRecord> getRecordType() {
        return QuestionGroupRecord.class;
    }

    /**
     * The column <code>question_group.id</code>.
     */
    public final TableField<QuestionGroupRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>question_group.quiz_id</code>.
     */
    public final TableField<QuestionGroupRecord, Integer> QUIZ_ID = createField(DSL.name("quiz_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>question_group.name</code>.
     */
    public final TableField<QuestionGroupRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>question_group.number</code>.
     */
    public final TableField<QuestionGroupRecord, Integer> NUMBER = createField(DSL.name("number"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>question_group.canvas_id</code>.
     */
    public final TableField<QuestionGroupRecord, String> CANVAS_ID = createField(DSL.name("canvas_id"), SQLDataType.CLOB, this, "");

    private QuestionGroup(Name alias, Table<QuestionGroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private QuestionGroup(Name alias, Table<QuestionGroupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>question_group</code> table reference
     */
    public QuestionGroup(String alias) {
        this(DSL.name(alias), QUESTION_GROUP);
    }

    /**
     * Create an aliased <code>question_group</code> table reference
     */
    public QuestionGroup(Name alias) {
        this(alias, QUESTION_GROUP);
    }

    /**
     * Create a <code>question_group</code> table reference
     */
    public QuestionGroup() {
        this(DSL.name("question_group"), null);
    }

    public <O extends Record> QuestionGroup(Table<O> child, ForeignKey<O, QuestionGroupRecord> key) {
        super(child, key, QUESTION_GROUP);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.IDX_GROUP_CANVAS_ID);
    }

    @Override
    public UniqueKey<QuestionGroupRecord> getPrimaryKey() {
        return Keys.QUESTION_GROUP__;
    }

    @Override
    public QuestionGroup as(String alias) {
        return new QuestionGroup(DSL.name(alias), this);
    }

    @Override
    public QuestionGroup as(Name alias) {
        return new QuestionGroup(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public QuestionGroup rename(String name) {
        return new QuestionGroup(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public QuestionGroup rename(Name name) {
        return new QuestionGroup(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, String, Integer, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
