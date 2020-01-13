package com.eis.geoCalendar.app;

import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.eis.geoCalendar.database.JsonEventParser;
import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class JsonEventParserAndroidTest {

    private static final String exampleContent = "Hello World!";
    private static final ExamplePOJO exampleComplexContent = new ExamplePOJO();
    private static final GPSPosition examplePosition = new GPSPosition(100.0, 50.0);

    private JsonEventParser<GenericEvent> eventParser;
    private Class<GenericEvent> eventType = GenericEvent.class;
    private GenericEvent<?> simpleEvent;
    private GenericEvent<?> complexEvent;

    /**
     * Just a class to mimic a complex Pojo to be stored as nested data in the parsed Json.
     */
    private static class ExamplePOJO {
        public String publicField = "I'm public";
        private String privateField = "I'm protected";
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

    @Before
    public void init() {
        eventParser = new JsonEventParser<>(eventType);
        simpleEvent = new GenericEvent<>(
                examplePosition,
                exampleContent
        );
        complexEvent = new GenericEvent<>(
                examplePosition,
                exampleComplexContent
        );
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
        GenericEvent event = eventParser.dataToEvent(data);
        assertEquals(event.getContent(), simpleEvent.getContent());
        assertEquals(event.getPosition().getLatitude(),
                simpleEvent.getPosition().getLatitude(), 0);
        assertEquals(event.getPosition().getLongitude(),
                simpleEvent.getPosition().getLongitude(), 0);
    }

    @Test
    public void aComplexEventIsCorrectlyParsed() {
        String data = eventParser.eventToData(complexEvent);
        GenericEvent event = eventParser.dataToEvent(data);
        assertEquals(event.getContent(), complexEvent.getContent());
        assertEquals(event.getPosition().getLatitude(),
                complexEvent.getPosition().getLatitude(), 0);
        assertEquals(event.getPosition().getLongitude(),
                complexEvent.getPosition().getLongitude(), 0);
    }
}
