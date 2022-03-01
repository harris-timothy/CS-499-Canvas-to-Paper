/*
 * This file is generated by jOOQ.
 */
package cs499.data_classes.tables;


import cs499.data_classes.DefaultSchema;
import cs499.data_classes.Indexes;
import cs499.data_classes.Keys;
import cs499.data_classes.tables.records.QuizRecord;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row10;
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
public class Quiz extends TableImpl<QuizRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>quiz</code>
     */
    public static final Quiz QUIZ = new Quiz();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QuizRecord> getRecordType() {
        return QuizRecord.class;
    }

    /**
     * The column <code>quiz.id</code>.
     */
    public final TableField<QuizRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>quiz.course_id</code>.
     */
    public final TableField<QuizRecord, Integer> COURSE_ID = createField(DSL.name("course_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>quiz.options</code>.
     */
    public final TableField<QuizRecord, byte[]> OPTIONS = createField(DSL.name("options"), SQLDataType.BLOB, this, "");

    /**
     * The column <code>quiz.assign_to</code>.
     */
    public final TableField<QuizRecord, byte[]> ASSIGN_TO = createField(DSL.name("assign_to"), SQLDataType.BLOB, this, "");

    /**
     * The column <code>quiz.due_date</code>.
     */
    public final TableField<QuizRecord, String> DUE_DATE = createField(DSL.name("due_date"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>quiz.available_from</code>.
     */
    public final TableField<QuizRecord, String> AVAILABLE_FROM = createField(DSL.name("available_from"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>quiz.until</code>.
     */
    public final TableField<QuizRecord, String> UNTIL = createField(DSL.name("until"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>quiz.rubric</code>.
     */
    public final TableField<QuizRecord, byte[]> RUBRIC = createField(DSL.name("rubric"), SQLDataType.BLOB, this, "");

    /**
     * The column <code>quiz.total_points</code>.
     */
    public final TableField<QuizRecord, BigDecimal> TOTAL_POINTS = createField(DSL.name("total_points"), SQLDataType.NUMERIC.nullable(false), this, "");

    /**
     * The column <code>quiz.canvas_id</code>.
     */
    public final TableField<QuizRecord, String> CANVAS_ID = createField(DSL.name("canvas_id"), SQLDataType.CLOB, this, "");

    private Quiz(Name alias, Table<QuizRecord> aliased) {
        this(alias, aliased, null);
    }

    private Quiz(Name alias, Table<QuizRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>quiz</code> table reference
     */
    public Quiz(String alias) {
        this(DSL.name(alias), QUIZ);
    }

    /**
     * Create an aliased <code>quiz</code> table reference
     */
    public Quiz(Name alias) {
        this(alias, QUIZ);
    }

    /**
     * Create a <code>quiz</code> table reference
     */
    public Quiz() {
        this(DSL.name("quiz"), null);
    }

    public <O extends Record> Quiz(Table<O> child, ForeignKey<O, QuizRecord> key) {
        super(child, key, QUIZ);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.IDX_QUIZ_CANVAS_ID);
    }

    @Override
    public UniqueKey<QuizRecord> getPrimaryKey() {
        return Keys.QUIZ__;
    }

    @Override
    public Quiz as(String alias) {
        return new Quiz(DSL.name(alias), this);
    }

    @Override
    public Quiz as(Name alias) {
        return new Quiz(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Quiz rename(String name) {
        return new Quiz(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Quiz rename(Name name) {
        return new Quiz(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, Integer, byte[], byte[], String, String, String, byte[], BigDecimal, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}