package com.eis.geoCalendar.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventDatabase;
import com.google.gson.reflect.TypeToken;

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
 * [PREFIX] + [{@link GenericEventDatabase#eventType}] + [db-name]
 * <p>
 * This means the databases for Event type A will be separated from Event type B.
 *
 * @param <E> The type of event this Database stores.
 * @author Riccardo De Zen
 * @author Giorgia Bortoletti
 */

public class GenericEventDatabase<E extends Event> implements EventDatabase<E> {

    /**
     * The following String ensures a combination of {@link TypeToken} and database name won't
     * collide. Because it cannot be part of a {@link Class}' name.
     */
    private static final String SEPARATOR = "-";
    //Prefix used to reduce chances of collisions with any other class' Room Databases.
    private static final String DB_NAME_PREFIX = "geoCalendar-database";
    //String to be formatted with the appropriate parameters.
    private static final String DB_NAME_TEMPLATE =
            DB_NAME_PREFIX + SEPARATOR + "%1$s" + SEPARATOR + "%2$s";

    //Map containing all the active instances for the actual Room Database.
    private static Map<String, RoomEventDatabase> physicalInstances = new ArrayMap<>();
    //Map containing all the active instances for this Database class.
    private static Map<String, GenericEventDatabase> activeInstances = new ArrayMap<>();

    private RoomEventDatabase physicalDatabase;
    private TypeToken<E> eventType;
    private JsonEventParser<E> parser;
    private String name;

    @Database(entities = StringEntity.class, version = 1)
    public static abstract class RoomEventDatabase extends RoomDatabase {
        abstract StringDao access();
    }

    /**
     * Only available constructor.
     *
     * @param context   The calling Context, needed to instantiate the Database.
     * @param eventType A reference to the Class this Database stores.
     * @param name      The name for this Database.
     */
    private GenericEventDatabase(Context context, TypeToken<E> eventType, String name) {
        this.physicalDatabase = getDatabase(context, DB_NAME_PREFIX + name);
        this.eventType = eventType;
        this.name = name;
        this.parser = new JsonEventParser<>(eventType);
    }

    /**
     * Method to retrieve an instance of the Database.
     * The suppressed warning refers to the cast in the return statement. Such a cast can be
     * considered safe (as of now) because this class ensures the instance with a certain key
     * actually contains the appropriate Event type.
     *
     * @param context   The calling Context, needed to instantiate the Database.
     * @param eventType The stored {@code Event} class' {@link TypeToken}.
     * @param name      The name for the Database.
     * @param <E>       The stored {@code Event} type.
     * @return The existing appropriate instance, if possible, or a newly created appropriate
     * instance.
     * @see GenericEventDatabase#generateName(TypeToken, String) For more info on why the cast is
     * safe.
     */
    @SuppressWarnings("unchecked")
    public static <E extends Event> GenericEventDatabase<E> getInstance(Context context,
                                                                        TypeToken<E> eventType,
                                                                        String name) {
        String fullName = generateName(eventType, name);
        if (activeInstances.get(fullName) != null) {
            //This would throw a warning
            return (GenericEventDatabase<E>) activeInstances.get(fullName);
        }
        GenericEventDatabase<E> newInstance = new GenericEventDatabase<>(
                context,
                eventType,
                fullName
        );
        activeInstances.put(fullName, newInstance);
        return newInstance;
    }

    /**
     * Method to generate the full name for the database.
     * Considering a class name cannot contain the character '-' (see
     * {@link GenericEventDatabase#SEPARATOR}), it is borderline impossible for this method to
     * assign colliding names to databases containing different Event classes.
     * Because of this, instances can be safely cast to their specific subtype.
     *
     * @param token    The type of Event stored.
     * @param baseName The base name, given by the user.
     * @return The full name, in the form [This class' defined prefix]+[Event Type]+[Given name].
     */
    private static <E extends Event> String generateName(TypeToken<E> token, String baseName) {
        return String.format(DB_NAME_TEMPLATE, token.toString(), baseName);
    }

    /**
     * Method to get the database with a certain name, instantiating one if necessary.
     *
     * @param context The calling Context.
     * @param name    The name for the Database.
     * @return The appropriate instance for a certain Database.
     */
    private RoomEventDatabase getDatabase(Context context, String name) {
        if (physicalInstances.get(name) != null)
            return physicalInstances.get(name);
        RoomEventDatabase newInstance = Room.databaseBuilder(
                context,
                RoomEventDatabase.class,
                name)
                .allowMainThreadQueries()
                .build();
        physicalInstances.put(name, newInstance);
        return newInstance;
    }

    /**
     * Saves an event in memory.
     *
     * @param event The event.
     * @return {@code true} if the event was parsable and has been saved, {@code false} otherwise.
     */
    @Override
    public boolean saveEvent(@NonNull final E event) {
        if (!parser.isEventParsable(event))
            return false;
        String dataFromEvent = parser.eventToData(event);
        physicalDatabase.access().insertEntity(new StringEntity(dataFromEvent));
        return contains(event);
    }

    /**
     * Saves a list of events in memory.
     *
     * @param events The list of events.
     * @return A map that associates to every event the result of the insertion.
     */
    @Override
    public Map<E, Boolean> saveEvents(@NonNull final Collection<E> events) {
        Map<E, Boolean> insertionResults = new ArrayMap<>();
        for (E event : events) {
            insertionResults.put(event, saveEvent(event));
        }
        return insertionResults;
    }

    /**
     * Removes an event from memory.
     * Despite the Database being able to store different types of Events, only Events matching
     * the type {@link E} will be queried.
     *
     * @param event The event.
     * @return {@code true} if the event was present and has been removed, {@code false} otherwise.
     */
    @Override
    public boolean removeEvent(@NonNull final E event) {
        String dataFromEvent = parser.eventToData(event);
        physicalDatabase.access().deleteEntity(new StringEntity(dataFromEvent));
        return !contains(event);
    }

    /**
     * Removes a list of events from memory.
     * Despite the Database being able to store different types of Events, only Events matching
     * the type {@link E} will be queried.
     *
     * @param events The list of events.
     * @return A map that associates to every event if it was present and has been removed or not.
     */
    @Override
    public Map<E, Boolean> removeEvents(@NonNull final Collection<E> events) {
        Map<E, Boolean> removedEvents = new ArrayMap<>();
        for (E event : events) {
            removedEvents.put(event, removeEvent(event));
        }
        return removedEvents;
    }

    /**
     * Retrieves all saved events from memory.
     * Despite the Database being able to store different types of Events, only Events matching
     * the type {@link E} will be queried.
     *
     * @return An {@link ArrayList} of saved events.
     */
    @Override
    public ArrayList<E> getSavedEvents() {
        StringEntity[] allStringEntities = physicalDatabase.access().getAllEntities();
        ArrayList<E> listEvents = new ArrayList<>();
        for (StringEntity stringEntity : allStringEntities)
            listEvents.add(parser.dataToEvent(stringEntity.getValue()));
        return listEvents;
    }

    /**
     * Retrieves presence of event.
     *
     * @param event The event to find.
     * @return {@code true} if the event is parsable and present, {@code false} otherwise.
     */
    @Override
    public boolean contains(@NonNull final E event) {
        if (!parser.isEventParsable(event))
            return false;
        String dataFromEvent = parser.eventToData(event);
        return (physicalDatabase.access().getCountWhere(dataFromEvent) >= 1);
    }

    /**
     * Method to get the number of Events in the database.
     * @return The number of Events in the database.
     */
    @Override
    public int count(){
        return physicalDatabase.access().count();
    }
}
