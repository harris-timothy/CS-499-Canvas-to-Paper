/*
 * This file is generated by jOOQ.
 */
package cs499.data_classes.tables;


import cs499.data_classes.DefaultSchema;
import cs499.data_classes.Keys;
import cs499.data_classes.tables.records.ReferenceMaterialRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
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
public class ReferenceMaterial extends TableImpl<ReferenceMaterialRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>reference_material</code>
     */
    public static final ReferenceMaterial REFERENCE_MATERIAL = new ReferenceMaterial();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ReferenceMaterialRecord> getRecordType() {
        return ReferenceMaterialRecord.class;
    }

    /**
     * The column <code>reference_material.id</code>.
     */
    public final TableField<ReferenceMaterialRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>reference_material.name</code>.
     */
    public final TableField<ReferenceMaterialRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>reference_material.description</code>.
     */
    public final TableField<ReferenceMaterialRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>reference_material.content</code>.
     */
    public final TableField<ReferenceMaterialRecord, byte[]> CONTENT = createField(DSL.name("content"), SQLDataType.BLOB, this, "");

    private ReferenceMaterial(Name alias, Table<ReferenceMaterialRecord> aliased) {
        this(alias, aliased, null);
    }

    private ReferenceMaterial(Name alias, Table<ReferenceMaterialRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>reference_material</code> table reference
     */
    public ReferenceMaterial(String alias) {
        this(DSL.name(alias), REFERENCE_MATERIAL);
    }

    /**
     * Create an aliased <code>reference_material</code> table reference
     */
    public ReferenceMaterial(Name alias) {
        this(alias, REFERENCE_MATERIAL);
    }

    /**
     * Create a <code>reference_material</code> table reference
     */
    public ReferenceMaterial() {
        this(DSL.name("reference_material"), null);
    }

    public <O extends Record> ReferenceMaterial(Table<O> child, ForeignKey<O, ReferenceMaterialRecord> key) {
        super(child, key, REFERENCE_MATERIAL);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<ReferenceMaterialRecord> getPrimaryKey() {
        return Keys.REFERENCE_MATERIAL__;
    }

    @Override
    public ReferenceMaterial as(String alias) {
        return new ReferenceMaterial(DSL.name(alias), this);
    }

    @Override
    public ReferenceMaterial as(Name alias) {
        return new ReferenceMaterial(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ReferenceMaterial rename(String name) {
        return new ReferenceMaterial(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ReferenceMaterial rename(Name name) {
        return new ReferenceMaterial(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, String, byte[]> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
