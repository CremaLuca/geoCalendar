package com.eis.geoCalendar.database;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Class meant to convert {@code Events} to and from a Json String.
 *
 * @param <E> The type of {@link Event} to convert.
 * @author Riccardo De Zen
 */
public class JsonEventParser<E extends Event> implements EventParser<E, String> {

    private static final String EVENT_PARSE_ERR = "The given Event could not be parsed.";
    private static final String DATA_PARSE_ERR = "The given data could not be parsed.";

    private TypeToken<E> eventType;
    private Gson gson = new Gson();

    public JsonEventParser(TypeToken<E> eventType) {
        this.eventType = eventType;
    }

    /**
     * Method to convert an {@link E} type {@link Event} to some {@link String} type data.
     *
     * @param eventToParse The {@code Event} to convert.
     * @return The {@code String} object containing the converted {@code Event}.
     * @throws IllegalArgumentException If the {@code Event} could not be converted.
     */
    @Override
    public String eventToData(E eventToParse) throws IllegalArgumentException {
        if (!isEventParsable(eventToParse))
            throw new IllegalArgumentException(EVENT_PARSE_ERR);
        return gson.toJson(eventToParse);
    }

    /**
     * Method to convert some {@link String} type data to an {@link E} type {@link Event}.
     *
     * @param stringToParse The String to convert.
     * @return The {@code Event} represented by {@code stringToParse}.
     * @throws IllegalArgumentException If the {@code String} could not be converted.
     */
    @Override
    public E dataToEvent(String stringToParse) throws IllegalArgumentException {
        if (!isDataParsable(stringToParse))
            throw new IllegalArgumentException(DATA_PARSE_ERR);
        return gson.fromJson(stringToParse, eventType.getType());
    }

    /**
     * Method detecting whether a given {@link Event} can be converted to a {@link String} or not.
     * Any Object is convertible with Gson. The only requirement is for {@code event} to be of
     * {@link E} type, which will always be the case unless a non generic instance of
     * {@link JsonEventParser} is created.
     *
     * @param event An {@code Event}.
     * @return {@code true} if {@code event} can be converted, {@code false} otherwise.
     */
    @Override
    public boolean isEventParsable(E event) {
        //Any Object can be parsed into a Json with Gson.
        return true;
    }

    /**
     * Method detecting whether some {@link String} can be converted to an {@link E} type
     * {@link Event} or not.
     *
     * @param stringData The {@code String} to check.
     * @return {@code true} if the data can be converted, {@code false} otherwise.
     */
    @Override
    public boolean isDataParsable(String stringData) {
        try {
            gson.fromJson(stringData, eventType.getType());
        } catch (JsonSyntaxException e) {
            return false;
        }
        return true;
    }
}
