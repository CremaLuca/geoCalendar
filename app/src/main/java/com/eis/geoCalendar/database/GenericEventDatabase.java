package com.eis.geoCalendar.database;

import android.content.Context;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * A class defining a Database holding {@link Event} type Objects.
 * The Database uses <a href="https://github.com/google/gson">Gson</a>
 * to parse the Events to a Json String and vice-versa in order to store them without defining a
 * schema.
 * <p>
 * To keep the class generic, a reference to the stored Event type's class is needed when
 * instantiating.
 * <p>
 * The class is structured as an Object Pool, where instances are created with the following
 * primary key:
 * {@link GenericEventDatabase#DB_NAME_PREFIX} + [db-name]
 * <p>
 * Due to limitations in defining Database schemas with generics, no reference to the Stored type
 * is kept inside the key, the User is responsible for using responsibly the instances.
 * Following what is stated above, the Database can store multiple types of Events and access
 * both those types at the same time.
 *
 * @param <E> The type of event this Database stores.
 * @author Riccardo De Zen
 */

public class GenericEventDatabase<E extends Event> implements EventDatabase<E> {

    private static final String DB_NAME_PREFIX = "geoCalendar-database-";

    //Map containing all the active instances for the Database.
    private static final Map<String, RoomEventDatabase> instances = new ArrayMap<>();

    private RoomEventDatabase physicalDatabase;
    private Class<E> eventClass;
    private JSONEventParser<E> parser;
    private String name;

    @Database(entities = StringEntity.class, version = 1)
    public static abstract class RoomEventDatabase extends RoomDatabase {
        abstract StringDao access();
    }

    /**
     * Only available constructor.
     *
     * @param context    The calling Context, needed to instantiate the Database.
     * @param eventClass A reference to the Class this Database stores.
     * @param name       The name for this Database.
     */
    public GenericEventDatabase(Context context, Class<E> eventClass, String name) {
        this.physicalDatabase = getDatabase(context, DB_NAME_PREFIX + name);
        this.eventClass = eventClass;
        this.name = name;
        this.parser = new JSONEventParser<>(eventClass);
    }

    /**
     * Method to get the database with a certain name, instantiating one if necessary.
     *
     * @param context The calling Context.
     * @param name    The name for the Database.
     * @return The appropriate instance for a certain Database.
     */
    private RoomEventDatabase getDatabase(Context context, String name) {
        if (instances.get(name) != null)
            return instances.get(name);
        RoomEventDatabase newInstance = Room.databaseBuilder(
                context,
                RoomEventDatabase.class,
                name)
                .allowMainThreadQueries()
                .build();
        instances.put(name, newInstance);
        return newInstance;
    }

    /**
     * Saves an event in memory.
     *
     * @param event The event.
     */
    public void saveEvent(@NonNull final E event) {

    }

    /**
     * Saves a list of events in memory.
     *
     * @param events The list of events.
     */
    public void saveEvents(@NonNull final Collection<E> events) {

    }

    /**
     * Removes an event from memory.
     * Despite the Database being able to store different types of Events, only Events matching
     * the type {@link E} will be queried.
     *
     * @param event The event.
     * @return {@code true} if the event was present and has been removed, {@code false} otherwise.
     */
    public boolean removeEvent(@NonNull final E event) {
        return false;
    }

    /**
     * Removes a list of events from memory.
     * Despite the Database being able to store different types of Events, only Events matching
     * the type {@link E} will be queried.
     *
     * @param events The list of events.
     * @return A map that associates to every event if it was present and has been removed or not.
     */
    public Map<E, Boolean> removeEvents(@NonNull final Collection<E> events) {
        return null;
    }

    /**
     * Retrieves all saved events from memory.
     * Despite the Database being able to store different types of Events, only Events matching
     * the type {@link E} will be queried.
     *
     * @return An {@link ArrayList} of saved events.
     */
    public ArrayList<E> getSavedEvents() {
        return null;
    }
}
