package com.eis.geoCalendar.app.database;

import com.eis.geoCalendar.app.EventProvider;
import com.eis.geoCalendar.database.GenericEventDatabase;
import com.eis.geoCalendar.events.Event;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Test class for {@link GenericEventDatabase}.
 * Checking only single instance database functionality. For Object Pool feature see
 * {@link EventDatabasePoolTest}.
 *
 * @param <E> The {@link Event} type we are testing the database for.
 * @author Riccardo De Zen.
 */
@RunWith(ParameterizedRobolectricTestRunner.class)
public class GenericEventDatabaseTest<E extends Event> {

    private static final String DEFAULT_DB_NAME = "test-db";

    private GenericEventDatabase<E> database;
    private TypeToken<E> currentEventTypeToken;
    private E currentEvent;
    private boolean shouldAllowGeneric;

    /**
     * Method returning params for the tests. They are stored in {@link EventProvider}.
     *
     * @return A collection of parameter sets suited for these tests.
     * @see EventProvider#nameTokenExampleGeneric() For parameter explanation.
     */
    @ParameterizedRobolectricTestRunner.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> params() {
        return EventProvider.nameTokenExampleGeneric();
    }

    /**
     * Constructor for the Test.
     *
     * @param currentEventTypeToken The type of {@link Event} being tested.
     * @param testName              Used only by {@link GenericEventDatabaseTest#params()} to set
     *                              the Test name.
     * @param currentEvent          The {@link Event} being currently used to test the parser.
     * @param shouldAllowGeneric    The {@link Event} contains such a data type that it should
     *                              allow converting from a String even with a Raw TypeToken.
     */
    public GenericEventDatabaseTest(String testName, TypeToken<E> currentEventTypeToken,
                                    E currentEvent, boolean shouldAllowGeneric) {
        this.currentEventTypeToken = currentEventTypeToken;
        this.currentEvent = currentEvent;
        this.shouldAllowGeneric = shouldAllowGeneric;
    }

    @Before
    public void setup() {
        database = GenericEventDatabase.getInstance(
                RuntimeEnvironment.systemContext,
                currentEventTypeToken,
                DEFAULT_DB_NAME
        );
    }

    /**
     * Test checking whether the Database actually starts as empty (In the Test environment the
     * Database has just been born, of course it should persist data when used on a real device).
     */
    @Test
    public void databaseStartsEmpty() {
        final int expectedCount = 0;
        assertEquals(expectedCount, database.count());
    }

    /**
     * Test to verify an item can be added.
     */
    @Test
    public void canAdd(){
        final int expectedCount = 1;
        database.saveEvent(currentEvent);
        assertEquals(expectedCount, database.count());
    }

    @Test
    public void contains(){
        assertTrue(database.contains(currentEvent));
    }
}
