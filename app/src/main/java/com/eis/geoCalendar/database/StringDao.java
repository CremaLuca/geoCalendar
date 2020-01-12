package com.eis.geoCalendar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Collection;
import java.util.List;

/**
 * {@code Dao} providing the operations required by
 * {@link com.eis.geoCalendar.events.EventDatabase}.
 *
 * @author Riccardo De Zen
 */
@Dao
public abstract class StringDao {

    private static final String TABLE_NAME = StringEntity.DEFAULT_NAME;
    private static final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;

    /**
     * Inserts a single {@link StringEntity}.
     * @param entity A StringEntity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertEntity(StringEntity entity);

    /**
     * Inserts multiple {@link StringEntity}.
     * @param entities Some {@code StringEntities}.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertEntities(Collection<StringEntity> entities);

    /**
     * Deletes a single {@link StringEntity}.
     * @param entity A StringEntity.
     */
    @Delete
    public abstract int deleteEntity(StringEntity entity);

    /**
     * Deletes multiple {@link StringEntity}.
     * @param entities Some {@code StringEntities}.
     */
    @Delete
    public abstract int deleteEntities(Collection<StringEntity> entities);

    /**
     * Returns all rows in the table.
     * @return An array containing all Entities in the table.
     */
    @Query(SELECT_ALL_QUERY)
    public abstract StringEntity[] getAllEntities();

}
