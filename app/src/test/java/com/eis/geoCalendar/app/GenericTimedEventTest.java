package com.eis.geoCalendar.app;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GenericTimedEventTest {

    private static final String DEFAULT_CONTENT = "Test content";
    private static final DateTime DEFAULT_DATETIME = DateTime.now();
    private static final GPSPosition DEFAULT_GPS_POSITION = new GPSPosition(33.12345675f, 15.22323232f);

    private GenericTimedEvent<String> testTimedEvent;

    @Before
    public void setUp() throws Exception {
        testTimedEvent = new GenericTimedEvent<>(DEFAULT_GPS_POSITION, DEFAULT_CONTENT, DEFAULT_DATETIME);
    }

    @Test
    public void getTime_constructorDateTime_isEquals() {
        Assert.assertEquals(testTimedEvent.getTime(), DEFAULT_DATETIME);
    }

}