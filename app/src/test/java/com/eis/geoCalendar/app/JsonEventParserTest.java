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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Parameterized tests for JsonEventParser.
 *
 * @param <E> Event type, needed for parser and TypeToken in order to parameterize the Test on
 *           the Event class.
 */
@RunWith(Parameterized.class)
public class JsonEventParserTest<T, E extends Event<T>> {

    private static final String exampleContent = "Hello World!";
    private static final ExamplePOJO exampleComplexContent = new ExamplePOJO();
    private static final GPSPosition examplePosition = new GPSPosition(100.0, 50.0);
    private static final DateTime exampleDateTime = DateTime.now();

    private JsonEventParser<E> eventParser;
    private TypeToken<E> typeToken;
    private E currentEvent;

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
    public static Object[][] params() {
        GenericEvent<String> simpleGenericEvent = new GenericEvent<>(
                examplePosition,
                exampleContent
        );
        GenericEvent<ExamplePOJO> complexGenericEvent = new GenericEvent<>(
                examplePosition,
                exampleComplexContent
        );
        GenericTimedEvent<String> simpleGenericTimedEvent = new GenericTimedEvent<>(
                examplePosition,
                exampleContent,
                exampleDateTime
        );
        GenericTimedEvent<ExamplePOJO> complexGenericTimedEvent = new GenericTimedEvent<>(
                examplePosition,
                exampleComplexContent,
                exampleDateTime
        );
        TypeToken<GenericEvent<String>> simpleToken = new TypeToken<GenericEvent<String>>() {
        };
        TypeToken<GenericEvent<ExamplePOJO>> complexToken =
                new TypeToken<GenericEvent<ExamplePOJO>>() {
                };
        TypeToken<GenericTimedEvent<String>> simpleTimedToken =
                new TypeToken<GenericTimedEvent<String>>() {
                };
        TypeToken<GenericTimedEvent<ExamplePOJO>> complexTimedToken =
                new TypeToken<GenericTimedEvent<ExamplePOJO>>() {
                };
        Object[][] params = {
                {GenericEvent.class.getSimpleName(), simpleToken, simpleGenericEvent},
                {GenericEvent.class.getSimpleName(), complexToken, complexGenericEvent},
                {GenericTimedEvent.class.getSimpleName(), simpleTimedToken,
                        simpleGenericTimedEvent},
                {GenericTimedEvent.class.getSimpleName(), complexTimedToken,
                        complexGenericTimedEvent}
        };
        return params;
    }

    /**
     * Default constructor for the Test.
     *
     * @param typeToken The type of Event being tested.
     * @param className Used only by {@link JsonEventParserTest#params()} to set the Test name.
     */
    public JsonEventParserTest(String className, TypeToken<E> typeToken, E currentEvent) {
        this.eventParser = new JsonEventParser<>(typeToken);
        this.typeToken = typeToken;
        this.currentEvent = currentEvent;
    }

    @Before
    public void setEventParser() {
        eventParser = new JsonEventParser<>(typeToken);
    }

    @Test
    public void isEventParsableValidEvent() {
        assertTrue(eventParser.isEventParsable(currentEvent));
    }

    @Test
    public void eventParsing() {
        String data = eventParser.eventToData(currentEvent);
        E event = eventParser.dataToEvent(data);
        assertEquals(event, currentEvent);
    }
}
