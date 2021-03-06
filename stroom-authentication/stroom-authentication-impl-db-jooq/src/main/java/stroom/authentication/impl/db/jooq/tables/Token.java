/*
 * This file is generated by jOOQ.
 */
package stroom.authentication.impl.db.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row12;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import stroom.authentication.impl.db.jooq.Indexes;
import stroom.authentication.impl.db.jooq.Keys;
import stroom.authentication.impl.db.jooq.Stroom;
import stroom.authentication.impl.db.jooq.tables.records.TokenRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Token extends TableImpl<TokenRecord> {

    private static final long serialVersionUID = 1683526280;

    /**
     * The reference instance of <code>stroom.token</code>
     */
    public static final Token TOKEN = new Token();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TokenRecord> getRecordType() {
        return TokenRecord.class;
    }

    /**
     * The column <code>stroom.token.id</code>.
     */
    public final TableField<TokenRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>stroom.token.version</code>.
     */
    public final TableField<TokenRecord, Integer> VERSION = createField(DSL.name("version"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.token.create_time_ms</code>.
     */
    public final TableField<TokenRecord, Long> CREATE_TIME_MS = createField(DSL.name("create_time_ms"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>stroom.token.create_user</code>.
     */
    public final TableField<TokenRecord, String> CREATE_USER = createField(DSL.name("create_user"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stroom.token.update_time_ms</code>.
     */
    public final TableField<TokenRecord, Long> UPDATE_TIME_MS = createField(DSL.name("update_time_ms"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>stroom.token.update_user</code>.
     */
    public final TableField<TokenRecord, String> UPDATE_USER = createField(DSL.name("update_user"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stroom.token.fk_account_id</code>.
     */
    public final TableField<TokenRecord, Integer> FK_ACCOUNT_ID = createField(DSL.name("fk_account_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.token.fk_token_type_id</code>.
     */
    public final TableField<TokenRecord, Integer> FK_TOKEN_TYPE_ID = createField(DSL.name("fk_token_type_id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stroom.token.data</code>.
     */
    public final TableField<TokenRecord, String> DATA = createField(DSL.name("data"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>stroom.token.expires_on_ms</code>.
     */
    public final TableField<TokenRecord, Long> EXPIRES_ON_MS = createField(DSL.name("expires_on_ms"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>stroom.token.comments</code>.
     */
    public final TableField<TokenRecord, String> COMMENTS = createField(DSL.name("comments"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>stroom.token.enabled</code>.
     */
    public final TableField<TokenRecord, Boolean> ENABLED = createField(DSL.name("enabled"), org.jooq.impl.SQLDataType.BIT.nullable(false), this, "");

    /**
     * Create a <code>stroom.token</code> table reference
     */
    public Token() {
        this(DSL.name("token"), null);
    }

    /**
     * Create an aliased <code>stroom.token</code> table reference
     */
    public Token(String alias) {
        this(DSL.name(alias), TOKEN);
    }

    /**
     * Create an aliased <code>stroom.token</code> table reference
     */
    public Token(Name alias) {
        this(alias, TOKEN);
    }

    private Token(Name alias, Table<TokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private Token(Name alias, Table<TokenRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Token(Table<O> child, ForeignKey<O, TokenRecord> key) {
        super(child, key, TOKEN);
    }

    @Override
    public Schema getSchema() {
        return Stroom.STROOM;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.TOKEN_PRIMARY, Indexes.TOKEN_TOKEN_FK_ACCOUNT_ID, Indexes.TOKEN_TOKEN_FK_TOKEN_TYPE_ID);
    }

    @Override
    public Identity<TokenRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TOKEN;
    }

    @Override
    public UniqueKey<TokenRecord> getPrimaryKey() {
        return Keys.KEY_TOKEN_PRIMARY;
    }

    @Override
    public List<UniqueKey<TokenRecord>> getKeys() {
        return Arrays.<UniqueKey<TokenRecord>>asList(Keys.KEY_TOKEN_PRIMARY);
    }

    @Override
    public List<ForeignKey<TokenRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TokenRecord, ?>>asList(Keys.TOKEN_FK_ACCOUNT_ID, Keys.TOKEN_FK_TOKEN_TYPE_ID);
    }

    public Account account() {
        return new Account(this, Keys.TOKEN_FK_ACCOUNT_ID);
    }

    public TokenType tokenType() {
        return new TokenType(this, Keys.TOKEN_FK_TOKEN_TYPE_ID);
    }

    @Override
    public TableField<TokenRecord, Integer> getRecordVersion() {
        return VERSION;
    }

    @Override
    public Token as(String alias) {
        return new Token(DSL.name(alias), this);
    }

    @Override
    public Token as(Name alias) {
        return new Token(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Token rename(String name) {
        return new Token(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Token rename(Name name) {
        return new Token(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<Integer, Integer, Long, String, Long, String, Integer, Integer, String, Long, String, Boolean> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
