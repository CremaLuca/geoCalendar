package com.eis.geoCalendar.app;

import androidx.annotation.Nullable;

import com.eis.geoCalendar.database.JsonEventParser;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Parameterized tests for JsonEventParser.
 *
 * @param <E> Event type, needed for parser and TypeToken in order to parameterize the Test on
 *            the Event class.
 */
@RunWith(Parameterized.class)
public class JsonEventParserTest<E extends Event> {

    private JsonEventParser<E> eventParser;
    private TypeToken<E> currentEventTypeToken;
    private E currentEvent;
    private boolean shouldAllowGeneric;

    /**
     * Method returning params for the tests. They are stored in {@link EventProvider}.
     * @see EventProvider#nameTokenExampleGeneric() For parameter explanation.
     * @return A collection of parameter sets suited for these tests.
     */
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> params() {
        return EventProvider.nameTokenExampleGeneric();
    }

    /**
     * Constructor for the Test.
     *
     * @param currentEventTypeToken The type of {@link Event} being tested.
     * @param testName              Used only by {@link JsonEventParserTest#params()} to set the
     *                              Test name.
     * @param currentEvent          The {@link Event} being currently used to test the parser.
     * @param shouldAllowGeneric    The {@link Event} contains such a data type that it should
     *                              allow converting from a String even with a Raw TypeToken.
     */
    public JsonEventParserTest(String testName, TypeToken<E> currentEventTypeToken,
                               E currentEvent, boolean shouldAllowGeneric) {
        this.eventParser = new JsonEventParser<>(currentEventTypeToken);
        this.currentEventTypeToken = currentEventTypeToken;
        this.currentEvent = currentEvent;
        this.shouldAllowGeneric = shouldAllowGeneric;
    }

    /**
     * Setup method to create the appropriately typed {@link JsonEventParser}.
     */
    @Before
    public void setEventParser() {
        eventParser = new JsonEventParser<>(currentEventTypeToken);
    }

    /**
     * Test to ensure an {@link Event} can be parsed.
     * The parser should allow any Event of the correct type to be parsed. Since the Event passed
     * when creating the test instance is always of the same type of the parser, this test is
     * fairly trivial.
     */
    @Test
    public void isEventParsableValidEvent() {
        assertTrue(eventParser.isEventParsable(currentEvent));
    }

    /**
     * Test to ensure an {@link Event} can be correctly parsed back and forth.
     * No test is provided for both conversions because the parser entirely rests upon the
     * Gson library, so the one way conversion can always be considered correct.
     */
    @Test
    public void eventParsingSameTypeResultsInEquals() {
        String data = eventParser.eventToData(currentEvent);
        E event = eventParser.dataToEvent(data);
        assertEquals(event, currentEvent);
    }

    /**
     * If a generic {@link Event} is converted to Json with some {@link TypeToken}, and then
     * parsed with a more restrictive one, the data will be of the most suitable container type
     * ({@code String}, {@code Integer}, {@code Map}...). This may result in a {@code false}
     * result for {@code equals}, but if the original contained type was a simple type like
     * {@link String}, the equation should still be {@code true}.
     */
    @Test
    public void eventParsingDifferentTypeResultsInNotEquals() {
        String data = eventParser.eventToData(currentEvent);
        //Getting a more generic type.
        TypeToken<GenericEvent> rawTypeToken = TypeToken.get(GenericEvent.class);
        JsonEventParser<GenericEvent> moreGenericParser = new JsonEventParser<>(rawTypeToken);
        GenericEvent moreGenericEvent = moreGenericParser.dataToEvent(data);
        /*
         * If the current Event should be parsed even by a raw type, then equals should return true,
         * otherwise it should return false. This is a negated XOR.
         */
        assertFalse(shouldAllowGeneric ^ currentEvent.equals(moreGenericEvent));
    }
}
