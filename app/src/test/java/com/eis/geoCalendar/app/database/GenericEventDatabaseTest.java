package com.eis.geoCalendar.app.database;

import android.content.Context;

import com.eis.geoCalendar.app.GenericEvent;
import com.eis.geoCalendar.database.GenericEventDatabase;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertSame;

@RunWith(RobolectricTestRunner.class)
public class GenericEventDatabaseTest {

    private static final TypeToken<GenericEvent<String>> EVENT_TYPE_TOKEN =
            new TypeToken<GenericEvent<String>>() {
    };
    private static final String DB_NAME = "test-db";

    private GenericEventDatabase<GenericEvent<String>> database;
    private Context context;

    @Before
    public void setup() {
        context = RuntimeEnvironment.systemContext;
    }

    @Test
    public void sameNameDatabasesAreSame() {
        database = GenericEventDatabase.getInstance(context, EVENT_TYPE_TOKEN, DB_NAME);
        GenericEventDatabase<GenericEvent<String>> anotherDatabase =
                GenericEventDatabase.getInstance(context, EVENT_TYPE_TOKEN, DB_NAME);
        assertSame(database, anotherDatabase);
    }
}
