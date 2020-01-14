package com.eis.geoCalendar.app;

import androidx.annotation.Nullable;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.Collection;

/**
 * Class used to retrieve some example Events during tests.
 *
 * @author Riccardo De Zen
 */
public class EventProvider {

    //Template for Pojo fields.
    private static final String POJO_FIELD_TEMPLATE = "I'm %s field example";
    /**
     * Example data used in the instantiation of example Events.
     */
    private static final String EXAMPLE_CONTENT = "Hello World!";

    private static final ExamplePOJO EXAMPLE_COMPLEX_CONTENT = new ExamplePOJO(
            String.format(POJO_FIELD_TEMPLATE, "public"),
            String.format(POJO_FIELD_TEMPLATE, "private"),
            String.format(POJO_FIELD_TEMPLATE, "protected")
    );

    private static final GPSPosition EXAMPLE_POSITION = new GPSPosition(100.0, 50.0);
    private static final DateTime EXAMPLE_DATE_TIME = DateTime.now();

    /**
     * {@link GenericEvent} with some simple content, and the corresponding {@link TypeToken}.
     */
    private static final String simpleEventName = "GenericEvent<String>";
    private static final GenericEvent<String> simpleEvent = new GenericEvent<>(
            EXAMPLE_POSITION,
            EXAMPLE_CONTENT
    );
    private static final TypeToken<GenericEvent<String>> simpleToken =
            new TypeToken<GenericEvent<String>>() {
            };
    private static final boolean simpleSupportsGenerification = true;

    /**
     * {@link GenericEvent} with some complex content, and the corresponding {@link TypeToken}.
     */
    private static final String complexEventName = "GenericEvent<ExamplePOJO>";
    private static final GenericEvent<ExamplePOJO> complexEvent = new GenericEvent<>(
            EXAMPLE_POSITION,
            EXAMPLE_COMPLEX_CONTENT
    );
    private static final TypeToken<GenericEvent<ExamplePOJO>> complexToken =
            new TypeToken<GenericEvent<ExamplePOJO>>() {
            };
    private static final boolean complexSupportsGenerification = true;

    /**
     * {@link GenericTimedEvent} with some simple content, and the corresponding {@link TypeToken}.
     */
    private static final String simpleTimedEventName = "GenericTimedEvent<String>";
    private static final GenericTimedEvent<String> simpleTimedEvent =
            new GenericTimedEvent<>(
                    EXAMPLE_POSITION,
                    EXAMPLE_CONTENT,
                    EXAMPLE_DATE_TIME
            );
    private static final TypeToken<GenericTimedEvent<String>> simpleTimedToken =
            new TypeToken<GenericTimedEvent<String>>() {
            };
    private static final boolean simpleTimedSupportsGenerification = true;

    /**
     * {@link GenericTimedEvent} with some complex content, and the corresponding {@link TypeToken}.
     */
    private static final String complexTimedEventName = "GenericTimedEvent<ExamplePOJO>";
    private static final GenericTimedEvent<ExamplePOJO> complexTimedEvent =
            new GenericTimedEvent<>(
                    EXAMPLE_POSITION,
                    EXAMPLE_COMPLEX_CONTENT,
                    EXAMPLE_DATE_TIME
            );
    private static final TypeToken<GenericTimedEvent<ExamplePOJO>> complexTimedToken =
            new TypeToken<GenericTimedEvent<ExamplePOJO>>() {
            };
    private static final boolean complexTimedSupportsGenerification = true;

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
            return (
                    this.publicField.equals(other.publicField)
                            && this.privateField.equals(other.privateField)
                            && this.protectedField.equals(other.protectedField)
            );
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
                {simpleEventName, simpleToken, simpleEvent, simpleSupportsGenerification},
                {complexEventName, complexToken, complexEvent, complexSupportsGenerification},
                {simpleTimedEventName, simpleTimedToken, simpleTimedEvent,
                        simpleTimedSupportsGenerification},
                {complexTimedEventName, complexTimedToken,
                        complexTimedEvent, complexTimedSupportsGenerification}
        });
    }

    /**
     * Method returning parameter sets for testing.
     *
     * @return A collection of parameter sets in the form:
     * - Test name: An appropriate name the test can choose to use.
     * - {@link TypeToken}: A {@code TypeToken} for the type of {@code Event} being tested.
     * - exampleEvent: An example {@code Event} matching the token.
     * - anotherExampleEvent: Another {@code Event} matching the token.
     * @see com.eis.geoCalendar.app.database.GenericEventDatabaseTest for an example usage.
     */
    public static Collection<Object[]> nameTokenExampleAnotherExample() {
        return Arrays.asList(new Object[][]{
                {simpleEventName, simpleToken, simpleEvent, simpleSupportsGenerification},
                {complexEventName, complexToken, complexEvent, complexSupportsGenerification},
                {simpleTimedEventName, simpleTimedToken, simpleTimedEvent,
                        simpleTimedSupportsGenerification},
                {complexTimedEventName, complexTimedToken,
                        complexTimedEvent, complexTimedSupportsGenerification}
        });
    }
}
