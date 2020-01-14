package com.eis.geoCalendar.app.database;

import com.eis.geoCalendar.database.GenericEventDatabase;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class EventDatabasePoolTest {

    private static final String DEFAULT_DB_NAME = "test-db";
    private static final String ALTERNATIVE_DB_NAME = "another-db";

    /**
     * Two same type and same name databases are the same.
     */
    @Test
    public void sameNameDatabasesAreSame() {
        database = GenericEventDatabase.getInstance(context, currentEventTypeToken, DEFAULT_DB_NAME);
        GenericEventDatabase<E> anotherDatabase =
                GenericEventDatabase.getInstance(context, currentEventTypeToken, DEFAULT_DB_NAME);
        assertSame(database, anotherDatabase);
    }

    /**
     *
     */
    @Test
    public void differentNameDatabasesAreDifferent() {
        database = GenericEventDatabase.getInstance(context, currentEventTypeToken, DEFAULT_DB_NAME);
        GenericEventDatabase<E> anotherDatabase =
                GenericEventDatabase.getInstance(context, currentEventTypeToken, DEFAULT_DB_NAME);
        assertSame(database, anotherDatabase);
    }
}
