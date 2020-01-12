package com.eis.geoCalendar.events;

/**
 * Interface defining standard behaviour for an {@link Event} parser.
 *
 * @param <E> The type of Event the class parses.
 * @param <D> The data type to parse.
 * @author Riccardo De Zen
 */
public interface EventParser<E extends Event, D> {
    //TODO complete
    /**
     * Method to convert an {@link E} type {@link Event} to some {@link D} type Data.
     *
     * @param eventToParse The {@code Event} to convert.
     * @return The {@code D} type converted {@code Event}.
     * @throws IllegalArgumentException If the {@code Event} could not be converted.
     */
    D eventToData(E eventToParse) throws IllegalArgumentException;

    /**
     * Method to convert some {@link D} type Data
     * @param dataToParse
     * @return
     * @throws IllegalArgumentException
     */
    E dataToEvent(D dataToParse) throws IllegalArgumentException;

    boolean isEventParsable(E event);

    boolean isDataParsable(D data);
}
