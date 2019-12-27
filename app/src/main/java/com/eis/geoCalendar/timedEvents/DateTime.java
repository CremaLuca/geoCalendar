package com.eis.geoCalendar.timedEvents;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that represents a date. Adapter for {@link Date}
 *
 * @author Luca Crema
 * @since 23/12/2019
 */
public class DateTime extends Date {

    protected static DateTime MIN_DATE = new DateTime(0);
    protected static DateTime MAX_DATE = new DateTime(Long.MAX_VALUE);
    /**
     * Constructor of a date given a time in {@code long} format
     *
     * @see System#currentTimeMillis()
     */
    public DateTime(long date) {
        super(date);
    }

    /**
     * Returns a {@code long} representation of the given date
     *
     * @param year  must be greater than 1990
     * @param month must be between 1-12
     * @param day   must be between 1-31 depending on the month
     * @param hrs   must be between 1-24
     * @param min   must be between 0-60
     */
    public static long getTimeInMillis(int year, int month, int day, int hrs, int min) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month, day, hrs, min);
        return c.getTimeInMillis();
    }

    /**
     * @return current date and time
     */
    public static DateTime now() {
        return new DateTime(System.currentTimeMillis());
    }


}
