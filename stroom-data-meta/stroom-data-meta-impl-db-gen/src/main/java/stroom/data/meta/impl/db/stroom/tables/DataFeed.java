/*
 * This file is generated by jOOQ.
*/
package stroom.data.meta.impl.db.stroom.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import stroom.data.meta.impl.db.stroom.Indexes;
import stroom.data.meta.impl.db.stroom.Keys;
import stroom.data.meta.impl.db.stroom.Stroom;
import stroom.data.meta.impl.db.stroom.tables.records.DataFeedRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DataFeed extends TableImpl<DataFeedRecord> {

    private static final long serialVersionUID = -617315872;

    /**
     * The reference instance of <code>stroom.data_feed</code>
     */
    public static final DataFeed DATA_FEED = new DataFeed();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DataFeedRecord> getRecordType() {
        return DataFeedRecord.class;
    }

    /**
     * The column <code>stroom.data_feed.id</code>.
     */
    public final TableField<DataFeedRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>stroom.data_feed.name</code>.
     */
    public final TableField<DataFeedRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * Create a <code>stroom.data_feed</code> table reference
     */
    public DataFeed() {
        this(DSL.name("data_feed"), null);
    }

    /**
     * Create an aliased <code>stroom.data_feed</code> table reference
     */
    public DataFeed(String alias) {
        this(DSL.name(alias), DATA_FEED);
    }

    /**
     * Create an aliased <code>stroom.data_feed</code> table reference
     */
    public DataFeed(Name alias) {
        this(alias, DATA_FEED);
    }

    private DataFeed(Name alias, Table<DataFeedRecord> aliased) {
        this(alias, aliased, null);
    }

    private DataFeed(Name alias, Table<DataFeedRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Stroom.STROOM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DATA_FEED_NAME, Indexes.DATA_FEED_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DataFeedRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DATA_FEED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DataFeedRecord> getPrimaryKey() {
        return Keys.KEY_DATA_FEED_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DataFeedRecord>> getKeys() {
        return Arrays.<UniqueKey<DataFeedRecord>>asList(Keys.KEY_DATA_FEED_PRIMARY, Keys.KEY_DATA_FEED_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFeed as(String alias) {
        return new DataFeed(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFeed as(Name alias) {
        return new DataFeed(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DataFeed rename(String name) {
        return new DataFeed(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DataFeed rename(Name name) {
        return new DataFeed(name, null);
    }
}