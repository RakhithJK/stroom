/*
 * This file is generated by jOOQ.
 */
package stroom.activity.impl.db.jooq.tables.records;


import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;

import stroom.activity.impl.db.jooq.tables.Activity;


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
public class ActivityRecord extends UpdatableRecordImpl<ActivityRecord> implements Record8<Integer, Byte, Long, String, Long, String, String, String> {

    private static final long serialVersionUID = -314245986;

    /**
     * Setter for <code>stroom.ACTIVITY.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>stroom.ACTIVITY.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stroom.ACTIVITY.VER</code>.
     */
    public void setVer(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>stroom.ACTIVITY.VER</code>.
     */
    public Byte getVer() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>stroom.ACTIVITY.CRT_MS</code>.
     */
    public void setCrtMs(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>stroom.ACTIVITY.CRT_MS</code>.
     */
    public Long getCrtMs() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>stroom.ACTIVITY.CRT_USER</code>.
     */
    public void setCrtUser(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>stroom.ACTIVITY.CRT_USER</code>.
     */
    public String getCrtUser() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stroom.ACTIVITY.UPD_MS</code>.
     */
    public void setUpdMs(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>stroom.ACTIVITY.UPD_MS</code>.
     */
    public Long getUpdMs() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>stroom.ACTIVITY.UPD_USER</code>.
     */
    public void setUpdUser(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>stroom.ACTIVITY.UPD_USER</code>.
     */
    public String getUpdUser() {
        return (String) get(5);
    }

    /**
     * Setter for <code>stroom.ACTIVITY.USER_ID</code>.
     */
    public void setUserId(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>stroom.ACTIVITY.USER_ID</code>.
     */
    public String getUserId() {
        return (String) get(6);
    }

    /**
     * Setter for <code>stroom.ACTIVITY.JSON</code>.
     */
    public void setJson(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>stroom.ACTIVITY.JSON</code>.
     */
    public String getJson() {
        return (String) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, Byte, Long, String, Long, String, String, String> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Integer, Byte, Long, String, Long, String, String, String> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Activity.ACTIVITY.ID;
    }

    @Override
    public Field<Byte> field2() {
        return Activity.ACTIVITY.VER;
    }

    @Override
    public Field<Long> field3() {
        return Activity.ACTIVITY.CRT_MS;
    }

    @Override
    public Field<String> field4() {
        return Activity.ACTIVITY.CRT_USER;
    }

    @Override
    public Field<Long> field5() {
        return Activity.ACTIVITY.UPD_MS;
    }

    @Override
    public Field<String> field6() {
        return Activity.ACTIVITY.UPD_USER;
    }

    @Override
    public Field<String> field7() {
        return Activity.ACTIVITY.USER_ID;
    }

    @Override
    public Field<String> field8() {
        return Activity.ACTIVITY.JSON;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Byte component2() {
        return getVer();
    }

    @Override
    public Long component3() {
        return getCrtMs();
    }

    @Override
    public String component4() {
        return getCrtUser();
    }

    @Override
    public Long component5() {
        return getUpdMs();
    }

    @Override
    public String component6() {
        return getUpdUser();
    }

    @Override
    public String component7() {
        return getUserId();
    }

    @Override
    public String component8() {
        return getJson();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Byte value2() {
        return getVer();
    }

    @Override
    public Long value3() {
        return getCrtMs();
    }

    @Override
    public String value4() {
        return getCrtUser();
    }

    @Override
    public Long value5() {
        return getUpdMs();
    }

    @Override
    public String value6() {
        return getUpdUser();
    }

    @Override
    public String value7() {
        return getUserId();
    }

    @Override
    public String value8() {
        return getJson();
    }

    @Override
    public ActivityRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public ActivityRecord value2(Byte value) {
        setVer(value);
        return this;
    }

    @Override
    public ActivityRecord value3(Long value) {
        setCrtMs(value);
        return this;
    }

    @Override
    public ActivityRecord value4(String value) {
        setCrtUser(value);
        return this;
    }

    @Override
    public ActivityRecord value5(Long value) {
        setUpdMs(value);
        return this;
    }

    @Override
    public ActivityRecord value6(String value) {
        setUpdUser(value);
        return this;
    }

    @Override
    public ActivityRecord value7(String value) {
        setUserId(value);
        return this;
    }

    @Override
    public ActivityRecord value8(String value) {
        setJson(value);
        return this;
    }

    @Override
    public ActivityRecord values(Integer value1, Byte value2, Long value3, String value4, Long value5, String value6, String value7, String value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ActivityRecord
     */
    public ActivityRecord() {
        super(Activity.ACTIVITY);
    }

    /**
     * Create a detached, initialised ActivityRecord
     */
    public ActivityRecord(Integer id, Byte ver, Long crtMs, String crtUser, Long updMs, String updUser, String userId, String json) {
        super(Activity.ACTIVITY);

        set(0, id);
        set(1, ver);
        set(2, crtMs);
        set(3, crtUser);
        set(4, updMs);
        set(5, updUser);
        set(6, userId);
        set(7, json);
    }
}
