package com.eis.geoCalendar.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Class acting as a holder for a Long key, String value pair in a database.
 * The class is meant to hold non-null String values only.
 *
 * @author Riccardo De Zen
 */
@Entity(tableName = StringEntity.DEFAULT_NAME)
class StringEntity {

    @Ignore
    public static final String DEFAULT_NAME = "main_table";
    @Ignore
    public static final String KEY_COLUMN_NAME = "key";
    @Ignore
    public static final String VALUE_COLUMN_NAME = "value";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = KEY_COLUMN_NAME)
    private long key;

    @ColumnInfo(name = VALUE_COLUMN_NAME)
    private String value;

    /**
     * Constructor.
     *
     * @param key   The key for this Entity. If {@code key} is {@code 0} it is automatically
     *              assigned by the database when the entity is inserted.
     * @param value The value for this Entity.
     */
    public StringEntity(long key, @NonNull String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * A Constructor which doesn't use a specific key. The {@code key} column is set to be
     * automatically generated if {@code 0} ({@code int/long}) or {@code null} ({@code Integer
     * /Long}) is found when inserting the object in the database. This is why we set it to 0.
     *
     * @param value The value for this Entity.
     */
    public StringEntity(@NonNull String value) {
        key = 0;
        this.value = value;
    }

    /**
     * @return The key for this Entity.
     */
    public long getKey() {
        return key;
    }

    /**
     * Sets a new value for the key.
     * @param newKey The new key for this Entity.
     */
    public void setKey(long newKey) {
        this.key = newKey;
    }

    /**
     * @return The value for this Entity.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets a new value for the key.
     * @param newValue The new key for this Entity.
     */
    public void setValue(String newValue) {
        this.value = newValue;
    }
}
