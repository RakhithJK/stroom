/*
 * This file is generated by jOOQ.
 */
package stroom.explorer.impl.db.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import stroom.explorer.impl.db.jooq.tables.ExplorerNode;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ExplorerNodeRecord extends UpdatableRecordImpl<ExplorerNodeRecord> implements Record5<Integer, String, String, String, String> {

    private static final long serialVersionUID = -629006862;

    /**
     * Setter for <code>stroom.explorer_node.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>stroom.explorer_node.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stroom.explorer_node.type</code>.
     */
    public void setType(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>stroom.explorer_node.type</code>.
     */
    public String getType() {
        return (String) get(1);
    }

    /**
     * Setter for <code>stroom.explorer_node.uuid</code>.
     */
    public void setUuid(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>stroom.explorer_node.uuid</code>.
     */
    public String getUuid() {
        return (String) get(2);
    }

    /**
     * Setter for <code>stroom.explorer_node.name</code>.
     */
    public void setName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>stroom.explorer_node.name</code>.
     */
    public String getName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stroom.explorer_node.tags</code>.
     */
    public void setTags(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>stroom.explorer_node.tags</code>.
     */
    public String getTags() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, String, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ExplorerNode.EXPLORER_NODE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ExplorerNode.EXPLORER_NODE.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ExplorerNode.EXPLORER_NODE.UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ExplorerNode.EXPLORER_NODE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ExplorerNode.EXPLORER_NODE.TAGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getTags();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getTags();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExplorerNodeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExplorerNodeRecord value2(String value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExplorerNodeRecord value3(String value) {
        setUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExplorerNodeRecord value4(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExplorerNodeRecord value5(String value) {
        setTags(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExplorerNodeRecord values(Integer value1, String value2, String value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ExplorerNodeRecord
     */
    public ExplorerNodeRecord() {
        super(ExplorerNode.EXPLORER_NODE);
    }

    /**
     * Create a detached, initialised ExplorerNodeRecord
     */
    public ExplorerNodeRecord(Integer id, String type, String uuid, String name, String tags) {
        super(ExplorerNode.EXPLORER_NODE);

        set(0, id);
        set(1, type);
        set(2, uuid);
        set(3, name);
        set(4, tags);
    }
}