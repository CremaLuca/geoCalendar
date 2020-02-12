package com.eis.geoCalendar.database;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.eis.geoCalendar.app.GenericEvent;
import com.eis.geoCalendar.app.GenericTimedEvent;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Class used to test actual Object Pool behaviour only for {@link EventDatabasePool}.
 *
 * @author Riccardo De Zen
 */
@Config(sdk = 28)
@RunWith(RobolectricTestRunner.class)
public class EventDatabasePoolTest {

    private static final String DEFAULT_DB_NAME = "test-db";
    private static final String ALTERNATIVE_DB_NAME = "another-db";

    private static final TypeToken<GenericEvent> EXAMPLE_TOKEN =
            new TypeToken<GenericEvent>() {
            };

    private static final TypeToken<GenericTimedEvent> TIMED_TOKEN =
            new TypeToken<GenericTimedEvent>() {
            };

    private Context context;

    /**
     * Method setting up the Context to run the database on.
     */
    @Before
    public void setupContext() {
        context = ApplicationProvider.getApplicationContext();
    }

    /**
     * Test confirming same type and name result in same instance.
     */
    @Test
    public void sameNameSameTypeAreSame() {
        assertEquals(
                EventDatabasePool.getInstance(
                        context,
                        EXAMPLE_TOKEN,
                        DEFAULT_DB_NAME
                ),
                EventDatabasePool.getInstance(
                        context,
                        EXAMPLE_TOKEN,
                        DEFAULT_DB_NAME
                )
        );
    }

    /**
     * Test confirming different type and same name result in different instance.
     */
    @Test
    public void sameNameDifferentTypeAreDifferent() {
        assertNotEquals(
                EventDatabasePool.getInstance(
                        context,
                        EXAMPLE_TOKEN,
                        DEFAULT_DB_NAME
                ),
                EventDatabasePool.getInstance(
                        context,
                        TIMED_TOKEN,
                        DEFAULT_DB_NAME
                )
        );
    }

    /**
     * Test confirming same type and different name result in different instance.
     */
    @Test
    public void differentNameSameTypeAreDifferent() {
        assertNotEquals(
                EventDatabasePool.getInstance(
                        context,
                        EXAMPLE_TOKEN,
                        DEFAULT_DB_NAME
                ),
                EventDatabasePool.getInstance(
                        context,
                        EXAMPLE_TOKEN,
                        ALTERNATIVE_DB_NAME
                )
        );
    }

    /**
     * Test confirming same type and name result in different instance.
     */
    @Test
    public void differentNameDifferentTypeAreDifferent() {
        assertNotEquals(
                EventDatabasePool.getInstance(
                        context,
                        EXAMPLE_TOKEN,
                        DEFAULT_DB_NAME
                ),
                EventDatabasePool.getInstance(
                        context,
                        TIMED_TOKEN,
                        ALTERNATIVE_DB_NAME
                )
        );
    }
}
