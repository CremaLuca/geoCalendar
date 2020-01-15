package com.eis.geoCalendar.app;

import androidx.annotation.Nullable;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.Collection;

/**
 * Utility class used to retrieve some example Events during tests.
 *
 * @author Riccardo De Zen
 */
public class EventTestHelper {

    //Template for Pojo fields.
    private static final String POJO_FIELD_TEMPLATE = "I'm %s field example";


    /**
     * Example data used in the instantiation of example Events.
     */
    private static final String EXAMPLE_CONTENT = "Hello World!";
    private static final GPSPosition EXAMPLE_POSITION = new GPSPosition(100.0, 50.0);
    private static final DateTime EXAMPLE_DATE_TIME = DateTime.now();

    private static final ExamplePOJO EXAMPLE_COMPLEX_CONTENT = new ExamplePOJO(
            String.format(POJO_FIELD_TEMPLATE, "public"),
            String.format(POJO_FIELD_TEMPLATE, "private"),
            String.format(POJO_FIELD_TEMPLATE, "protected")
    );

    /**
     * {@link GenericEvent} with some simple content, and the corresponding {@link TypeToken}.
     */
    private static final String SIMPLE_EVENT_NAME = "GenericEvent<String>";
    private static final GenericEvent<String> SIMPLE_EVENT = new GenericEvent<>(
            EXAMPLE_POSITION,
            EXAMPLE_CONTENT
    );
    private static final TypeToken<GenericEvent<String>> SIMPLE_TOKEN =
            new TypeToken<GenericEvent<String>>() {
            };
    private static final boolean SIMPLE_SUPPORTS_GENERIFICATION = true;

    /**
     * {@link GenericEvent} with some complex content, and the corresponding {@link TypeToken}.
     */
    private static final String COMPLEX_EVENT_NAME = "GenericEvent<ExamplePOJO>";
    private static final GenericEvent<ExamplePOJO> COMPLEX_EVENT = new GenericEvent<>(
            EXAMPLE_POSITION,
            EXAMPLE_COMPLEX_CONTENT
    );
    private static final TypeToken<GenericEvent<ExamplePOJO>> COMPLEX_TOKEN =
            new TypeToken<GenericEvent<ExamplePOJO>>() {
            };
    private static final boolean COMPLEX_SUPPORTS_GENERIFICATION = true;

    /**
     * {@link GenericTimedEvent} with some simple content, and the corresponding {@link TypeToken}.
     */
    private static final String SIMPLE_TIMED_EVENT_NAME = "GenericTimedEvent<String>";
    private static final GenericTimedEvent<String> SIMPLE_TIMED_EVENT =
            new GenericTimedEvent<>(
                    EXAMPLE_POSITION,
                    EXAMPLE_CONTENT,
                    EXAMPLE_DATE_TIME
            );
    private static final TypeToken<GenericTimedEvent<String>> SIMPLE_TIMED_TOKEN =
            new TypeToken<GenericTimedEvent<String>>() {
            };
    private static final boolean SIMPLE_TIMED_SUPPORTS_GENERIFICATION = true;

    /**
     * {@link GenericTimedEvent} with some complex content, and the corresponding {@link TypeToken}.
     */
    private static final String COMPLEX_TIMED_EVENT_NAME = "GenericTimedEvent<ExamplePOJO>";
    private static final GenericTimedEvent<ExamplePOJO> COMPLEX_TIMED_EVENT =
            new GenericTimedEvent<>(
                    EXAMPLE_POSITION,
                    EXAMPLE_COMPLEX_CONTENT,
                    EXAMPLE_DATE_TIME
            );
    private static final TypeToken<GenericTimedEvent<ExamplePOJO>> COMPLEX_TIMED_TOKEN =
            new TypeToken<GenericTimedEvent<ExamplePOJO>>() {
            };
    private static final boolean COMPLEX_TIMED_SUPPORTS_GENERIFICATION = true;

    /**
     * Just a class to mimic a complex Pojo to be stored as nested data in the parsed Json.
     */
    public static class ExamplePOJO {
        public String publicField;
        private String privateField;
        protected String protectedField;

        public ExamplePOJO(String publicField, String privateField, String protectedField) {
            this.publicField = publicField;
            this.privateField = privateField;
            this.protectedField = protectedField;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof ExamplePOJO))
                return false;
            ExamplePOJO other = (ExamplePOJO) obj;
            return this.publicField.equals(other.publicField) &&
                    this.privateField.equals(other.privateField) &&
                    this.protectedField.equals(other.protectedField);
        }
    }

    /**
     * Method returning parameter sets for testing.
     *
     * @return A collection of parameter sets in the form:
     * - Test name: An appropriate name the test can choose to use.
     * - {@link TypeToken}: A {@code TypeToken} for the type of {@code Event} being tested.
     * - exampleEvent: An example {@code Event} matching the token.
     * - supportsGenerification: Whether the given {@code Event} type should allow raw typing
     * serialization.
     * @see JsonEventParserTest#params() for an example usage.
     */
    public static Collection<Object[]> nameTokenExampleGeneric() {
        return Arrays.asList(new Object[][]{
                {SIMPLE_EVENT_NAME, SIMPLE_TOKEN, SIMPLE_EVENT, SIMPLE_SUPPORTS_GENERIFICATION},
                {COMPLEX_EVENT_NAME, COMPLEX_TOKEN, COMPLEX_EVENT, COMPLEX_SUPPORTS_GENERIFICATION},
                {SIMPLE_TIMED_EVENT_NAME, SIMPLE_TIMED_TOKEN, SIMPLE_TIMED_EVENT,
                        SIMPLE_TIMED_SUPPORTS_GENERIFICATION},
                {COMPLEX_TIMED_EVENT_NAME, COMPLEX_TIMED_TOKEN,
                        COMPLEX_TIMED_EVENT, COMPLEX_TIMED_SUPPORTS_GENERIFICATION}
        });
    }
}
