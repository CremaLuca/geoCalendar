package com.eis.geoCalendar.database;

import com.eis.geoCalendar.app.GenericEvent;
import com.eis.geoCalendar.events.EventDatabase;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.google.gson.reflect.TypeToken;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Test class for {@link EventDatabasePool}.
 * Checking only single instance database functionality. For Object Pool features see
 * {@link EventDatabasePoolTest}.
 * Due to the fact {@link EventDatabasePool} is mostly mirrors {@link RoomEventDatabase} methods,
 * the tests mirror that same class' tests as well.
 *
 * @author Riccardo De Zen.
 */
@RunWith(RobolectricTestRunner.class)
public class GenericEventDatabasePoolTest {

    /**
     * Example data used in the instantiation of example Events.
     */
    private static final String EXAMPLE_CONTENT = "Hello World!";
    private static final String ANOTHER_EXAMPLE_CONTENT = "Hello from the other side";
    private static final GPSPosition EXAMPLE_POSITION = new GPSPosition(100.0, 50.0);
    private static final DateTime EXAMPLE_DATE_TIME = DateTime.now();
    private static final String DEFAULT_DB_NAME = "test-db";

    private static final GenericEvent<String> SIMPLE_EVENT = new GenericEvent<>(
            EXAMPLE_POSITION,
            EXAMPLE_CONTENT
    );
    private static final GenericEvent<String> ANOTHER_SIMPLE_EVENT = new GenericEvent<>(
            EXAMPLE_POSITION,
            ANOTHER_EXAMPLE_CONTENT
    );
    private static final TypeToken<GenericEvent<String>> EVENT_TOKEN =
            new TypeToken<GenericEvent<String>>() {
            };
    private static final List<GenericEvent<String>> SOME_EVENTS = new ArrayList<>();

    private EventDatabase<GenericEvent<String>> database;

    @BeforeClass
    public static void fillList() {
        SOME_EVENTS.add(SIMPLE_EVENT);
        SOME_EVENTS.add(ANOTHER_SIMPLE_EVENT);
    }

    @Before
    public void setDatabase() {
        database = EventDatabasePool.getInMemoryInstance(
                RuntimeEnvironment.systemContext,
                EVENT_TOKEN,
                DEFAULT_DB_NAME
        );
    }

    /**
     * Just a facade call to allow actual clearing method to be static, to be used in any other
     * tests that need it.
     */
    @After
    public void clearInstances() {
        clearActiveInstances();
    }

    /**
     * This method is necessary, due to an issue with MySQL, where static instances of the
     * database cannot be reset between tests automatically.
     */
    public static void clearActiveInstances() {
        RoomEventDatabaseTest.clearActiveInstances();
        try {
            //Getting the field named "activeInstances" (the map with all active databases)
            Field instance = EventDatabasePool.class.getDeclaredField("activeInstances");
            //Making it accessible
            instance.setAccessible(true);
            //Casting it to a Map
            Map<?, ?> privateField = (Map<?, ?>) instance.get(null);
            //Making it empty
            if (privateField != null)
                privateField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing whether the database actually starts empty.
     */
    @Test
    public void startsEmpty() {
        final int expectedStartCount = 0;
        assertEquals(expectedStartCount, database.count());
    }

    /**
     * Testing single insertion.
     */
    @Test
    public void canAddOne() {
        final int expectedCount = 1;
        database.saveEvent(SIMPLE_EVENT);
        assertEquals(expectedCount, database.count());
    }

    /**
     * Testing the insertion is actually correct, through {@link StringDao#contains(String)}.
     */
    @Test
    public void canAddOneAndItsCorrect() {
        database.saveEvent(SIMPLE_EVENT);
        assertTrue(database.contains(SIMPLE_EVENT));
    }

    /**
     * Testing multiple insertions.
     */
    @Test
    public void canAddMultiple() {
        database.saveEvents(SOME_EVENTS);
        final int expectedCount = SOME_EVENTS.size();
        assertEquals(expectedCount, database.count());
    }

    /**
     * Testing multiple insertions are actually correct, through {@link StringDao#getAllEntities()}.
     */
    @Test
    public void canAddMultipleAndTheyAreCorrect() {
        database.saveEvents(SOME_EVENTS);
        List<GenericEvent<String>> containedEvents = database.getSavedEvents();
        assertEquals(SOME_EVENTS.size(), containedEvents.size());
        for (int i = 0; i < SOME_EVENTS.size(); i++) {
            assertEquals(containedEvents.get(i), SOME_EVENTS.get(i));
        }
    }

    /**
     * Testing one removal.
     */
    @Test
    public void canRemoveOne() {
        final int expectedCount = 0;
        database.saveEvent(SIMPLE_EVENT);
        database.removeEvent(SIMPLE_EVENT);
        assertEquals(expectedCount, database.count());
    }

    /**
     * Testing multiple removals.
     */
    @Test
    public void canRemoveMultiple() {
        final int expectedCount = 0;
        database.saveEvents(SOME_EVENTS);
        database.removeEvents(SOME_EVENTS);
        assertEquals(expectedCount, database.count());
    }
}
