package com.eis.geoCalendar.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * Class testing {@link AbstractEventDatabase}.
 * Due to the fact the class is generalized to hold any kind of String (through
 * {@link StringEntity}), nothing related to Events will be tested here.
 *
 * @author Riccardo De Zen
 */
@Config(sdk = 28)
@RunWith(RobolectricTestRunner.class)
public class RoomEventDatabaseTest {

    private static final String DEFAULT_DB_NAME = "Default DB";
    private static final String EXAMPLE = "Hello World!";
    private static final StringEntity EXAMPLE_ENTITY = new StringEntity(EXAMPLE);
    private static final StringEntity[] EXAMPLE_ARRAY = new StringEntity[]{
            new StringEntity(EXAMPLE),
            new StringEntity(EXAMPLE + "2"),
            new StringEntity(EXAMPLE + "3")
    };
    private static final List<StringEntity> EXAMPLE_COLLECTION = Arrays.asList(EXAMPLE_ARRAY);

    private AbstractEventDatabase database;

    @Before
    public void setup() {
        database = AbstractEventDatabase.getInMemoryInstance(RuntimeEnvironment.systemContext,
                DEFAULT_DB_NAME);
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
        try {
            //Getting the field named "activeInstances" (the map with all active databases)
            Field instance = AbstractEventDatabase.class.getDeclaredField("activeInstances");
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
     * Testing whether two instances with the same name are actually the same instance.
     */
    @Test
    public void poolSameName() {
        AbstractEventDatabase anotherDatabase =
                AbstractEventDatabase.getInMemoryInstance(RuntimeEnvironment.systemContext,
                        DEFAULT_DB_NAME);
        assertEquals(database, anotherDatabase);
    }

    /**
     * Testing whether two instances with a different name are different instances.
     */
    @Test
    public void poolDifferentName() {
        AbstractEventDatabase anotherDatabase =
                AbstractEventDatabase.getInMemoryInstance(RuntimeEnvironment.systemContext,
                        "Another name");
        assertNotEquals(database, anotherDatabase);
    }

    /**
     * Testing whether the database actually starts empty.
     */
    @Test
    public void startsEmpty() {
        final int expectedStartCount = 0;
        assertEquals(expectedStartCount, database.access().count());
    }

    /**
     * Testing single insertion.
     */
    @Test
    public void canAddOne() {
        final int expectedCount = 1;
        database.access().insertEntity(new StringEntity(EXAMPLE));
        assertEquals(expectedCount, database.access().count());
    }

    /**
     * Testing the insertion is actually correct, through
     * {@link AbstractStringDao#contains(String)}.
     */
    @Test
    public void canAddOneAndItsCorrect() {
        database.access().insertEntity(EXAMPLE_ENTITY);
        assertTrue(database.access().contains(EXAMPLE));
    }

    /**
     * Testing multiple insertions.
     */
    @Test
    public void canAddMultiple() {
        final int expectedCount = 3;
        database.access().insertEntities(EXAMPLE_COLLECTION);
        assertEquals(expectedCount, database.access().count());
    }

    /**
     * Testing multiple insertions are actually correct, through
     * {@link AbstractStringDao#getAllEntities()}.
     */
    @Test
    public void canAddMultipleAndTheyAreCorrect() {
        database.access().insertEntities(EXAMPLE_COLLECTION);
        StringEntity[] allEntities = database.access().getAllEntities();
        assertEquals(EXAMPLE_COLLECTION.size(), allEntities.length);
        for (int i = 0; i < EXAMPLE_COLLECTION.size(); i++) {
            if (!EXAMPLE_COLLECTION.get(i).equals(allEntities[i]))
                fail();
        }
    }

    /**
     * Testing one removal.
     */
    @Test
    public void canRemoveOne() {
        final int expectedCount = 0;
        database.access().insertEntity(EXAMPLE_ENTITY);
        database.access().deleteEntity(EXAMPLE_ENTITY);
        assertEquals(expectedCount, database.access().count());
    }

    /**
     * Testing multiple removals.
     */
    @Test
    public void canRemoveMultiple() {
        final int expectedCount = 0;
        database.access().insertEntities(EXAMPLE_COLLECTION);
        database.access().deleteEntities(EXAMPLE_COLLECTION);
        assertEquals(expectedCount, database.access().count());
    }

    /**
     * Testing if {@link AbstractStringDao#getCountWhere(String)} finds a single entity.
     * Due to how {@link StringEntity} is structured, the database will only contain one entity
     * with a certain value. It cannot contain duplicates.
     */
    @Test
    public void getCountWhereFindsOne() {
        final int expectedOccurrencies = 1;
        database.access().insertEntity(EXAMPLE_ENTITY);
        assertEquals(expectedOccurrencies, database.access().getCountWhere(EXAMPLE));
    }
}
