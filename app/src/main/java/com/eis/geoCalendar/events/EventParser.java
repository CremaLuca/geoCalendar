package com.eis.geoCalendar.events;

/**
 * Interface defining standard behaviour for an {@link Event} parser.
 *
 * @param <E> The type of Event the class parses.
 * @param <D> The data type to parse.
 * @author Riccardo De Zen
 */
public interface EventParser<E extends Event, D> {
    /**
     * Method to convert an {@link E} type {@link Event} to some {@link D} type data.
     *
     * @param eventToParse The {@code Event} to convert.
     * @return The {@code D} type converted {@code Event}.
     * @throws IllegalArgumentException If the {@code Event} could not be converted.
     */
    D eventToData(E eventToParse) throws IllegalArgumentException;

    /**
     * Method to convert some {@link D} type data to an {@link E} type {@link Event}.
     *
     * @param dataToParse The data to convert.
     * @return The {@code E} type converted data.
     * @throws IllegalArgumentException If the data could not be converted.
     */
    E dataToEvent(D dataToParse) throws IllegalArgumentException;

    /**
     * Method detecting whether a given {@link Event} can be converted to data or not.
     *
     * @param event An {@code Event}.
     * @return {@code true} if {@code event} can be converted, {@code false} otherwise.
     */
    boolean isEventParsable(E event);

    /**
     * Method detecting whether some data can be converted to an {@link E} type {@link Event} or
     * not.
     *
     * @param data The data to check.
     * @return {@code true} if the data can be converted, {@code false} otherwise.
     */
    boolean isDataParsable(D data);
}
