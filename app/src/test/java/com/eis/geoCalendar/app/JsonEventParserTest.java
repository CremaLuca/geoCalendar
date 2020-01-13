package com.eis.geoCalendar.app;

import androidx.annotation.Nullable;

import com.eis.geoCalendar.database.JsonEventParser;
import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Parameterized tests for JsonEventParser.
 *
 * @param <E>
 */
@RunWith(Parameterized.class)
public class JsonEventParserTest<E extends Event> {

    private static final String exampleContent = "Hello World!";
    private static final ExamplePOJO exampleComplexContent = new ExamplePOJO();
    private static final GPSPosition examplePosition = new GPSPosition(100.0, 50.0);
    private static final DateTime exampleDateTime = DateTime.now();

    private JsonEventParser<E> eventParser;
    private Class<E> eventType;
    private E simpleEvent;
    private E complexEvent;

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

    @Parameterized.Parameters(name = "{index}: {1}")
    public static Object[][] params() {
        GenericEvent<?> simpleGenericEvent = new GenericEvent<>(
                examplePosition,
                exampleContent
        );
        GenericEvent<?> complexGenericEvent = new GenericEvent<>(
                examplePosition,
                exampleComplexContent
        );
        GenericTimedEvent<?> simpleGenericTimedEvent = new GenericTimedEvent<>(
                examplePosition,
                exampleContent,
                exampleDateTime
        );
        GenericTimedEvent<?> complexGenericTimedEvent = new GenericTimedEvent<>(
                examplePosition,
                exampleComplexContent,
                exampleDateTime
        );
        Object[][] params = {
                {GenericEvent.class, GenericEvent.class.getSimpleName(), simpleGenericEvent,
                        complexGenericEvent},
                {GenericTimedEvent.class, GenericTimedEvent.class.getSimpleName(),
                        simpleGenericTimedEvent, complexGenericTimedEvent}
        };
        return params;
    }

    /**
     * Default constructor for the Test.
     *
     * @param eventType The type of Event being tested.
     * @param className Used only by {@link JsonEventParserTest#params()} to set the Test name.
     */
    public JsonEventParserTest(Class<E> eventType, String className, E simpleEvent,
                               E complexEvent) {
        this.eventParser = new JsonEventParser<>(eventType);
        this.eventType = eventType;
        this.simpleEvent = simpleEvent;
        this.complexEvent = complexEvent;
    }

    @Before
    public void setEventParser() {
        eventParser = new JsonEventParser<>(eventType);
    }

    @Test
    public void aSimpleEventCanBeParsed() {
        assertTrue(eventParser.isEventParsable(simpleEvent));
    }

    @Test
    public void aComplexEventCanBeParsed() {
        assertTrue(eventParser.isEventParsable(complexEvent));
    }

    @Test
    public void aSimpleEventIsCorrectlyParsed() {
        String data = eventParser.eventToData(simpleEvent);
        E event = eventParser.dataToEvent(data);
        assertEquals(event, simpleEvent);
    }

    @Test
    public void aComplexEventIsCorrectlyParsed() {
        String data = eventParser.eventToData(complexEvent);
        System.out.println(data);
        E event = eventParser.dataToEvent(data);
        System.out.println(event.getContent());
        assertEquals(event, complexEvent);
    }
}
