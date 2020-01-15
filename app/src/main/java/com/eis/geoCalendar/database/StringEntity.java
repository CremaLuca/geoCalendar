package com.eis.geoCalendar.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    public static final String VALUE_COLUMN_NAME = "value";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = VALUE_COLUMN_NAME)
    private String value;

    /**
     * The default Constructor, taking in a String value.
     *
     * @param value The value for this Entity.
     */
    public StringEntity(@NonNull String value) {
        this.value = value;
    }

    /**
     * @return The value for this Entity.
     */
    @NonNull
    public String getValue() {
        return value;
    }

    /**
     * Sets a new value for the key.
     *
     * @param newValue The new key for this Entity.
     */
    public void setValue(@NonNull String newValue) {
        this.value = newValue;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof StringEntity))
            return false;
        StringEntity other = (StringEntity) obj;
        return this.getValue().equals(other.getValue());
    }
}
