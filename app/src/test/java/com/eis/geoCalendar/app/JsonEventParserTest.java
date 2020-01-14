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

    private static final String exampleContent = "Hello World!";
    private static final ExamplePOJO exampleComplexContent = new ExamplePOJO();
    private static final GPSPosition examplePosition = new GPSPosition(100.0, 50.0);
    private static final DateTime exampleDateTime = DateTime.now();

    /**
     * GenericEvent with some simple content, and the corresponding {@link TypeToken}.
     */
    private static final String simpleEventName = "GenericEvent<String>";
    private static final GenericEvent<String> simpleEvent = new GenericEvent<>(
            examplePosition,
            exampleContent
    );
    private static final TypeToken<GenericEvent<String>> simpleToken =
            new TypeToken<GenericEvent<String>>() {
            };

    /**
     * GenericEvent with some complex content, and the corresponding {@link TypeToken}.
     */
    private static final String complexEventName = "GenericEvent<ExamplePOJO>";
    private static final GenericEvent<ExamplePOJO> complexEvent = new GenericEvent<>(
            examplePosition,
            exampleComplexContent
    );
    private static final TypeToken<GenericEvent<ExamplePOJO>> complexToken =
            new TypeToken<GenericEvent<ExamplePOJO>>() {
            };

    /**
     * GenericTimedEvent with some simple content, and the corresponding {@link TypeToken}.
     */
    private static final String simpleTimedEventName = "GenericTimedEvent<String>";
    private static final GenericTimedEvent<String> simpleTimedEvent =
            new GenericTimedEvent<>(
                    examplePosition,
                    exampleContent,
                    exampleDateTime
            );
    private static final TypeToken<GenericTimedEvent<String>> simpleTimedToken =
            new TypeToken<GenericTimedEvent<String>>() {
            };

    /**
     * GenericTimedEvent with some complex content, and the corresponding {@link TypeToken}.
     */
    private static final String complexTimedEventName = "GenericTimedEvent<ExamplePOJO>";
    private static final GenericTimedEvent<ExamplePOJO> complexTimedEvent =
            new GenericTimedEvent<>(
                    examplePosition,
                    exampleComplexContent,
                    exampleDateTime
            );
    private static final TypeToken<GenericTimedEvent<ExamplePOJO>> complexTimedToken =
            new TypeToken<GenericTimedEvent<ExamplePOJO>>() {
            };


    private JsonEventParser<E> eventParser;
    private TypeToken<E> currentEventTypeToken;
    private E currentEvent;
    private boolean shouldAllowGeneric;

    /**
     * Just a class to mimic a complex Pojo to be stored as nested data in the parsed Json.
     */
    private static class ExamplePOJO {
        public String publicField = "I'm public";
        private String privateField = "I'm private";
        protected String protectedField = "I'm protected";

        @Override
        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof ExamplePOJO))
                return false;
            ExamplePOJO other = (ExamplePOJO) obj;
            return (
                    this.publicField.equals(other.publicField)
                            && this.privateField.equals(other.privateField)
                            && this.protectedField.equals(other.protectedField)
            );
        }
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {simpleEventName, simpleToken, simpleEvent, true},
                {complexEventName, complexToken, complexEvent, false},
                {simpleTimedEventName, simpleTimedToken, simpleTimedEvent, true},
                {complexTimedEventName, complexTimedToken,
                        complexTimedEvent, false}
        });
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
