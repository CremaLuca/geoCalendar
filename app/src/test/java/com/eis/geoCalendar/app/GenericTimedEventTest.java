package com.eis.geoCalendar.app;

import com.eis.geoCalendar.gps.GPSPosition;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class GenericTimedEventTest {

    private static final String DEFAULT_CONTENT = "Test content";
    private static final LocalDateTime DEFAULT_DATETIME = LocalDateTime.now();
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

    @Test
    public void getContent_constructorContent_isEquals() {
        Assert.assertEquals(testTimedEvent.getContent(), DEFAULT_CONTENT);
    }

    @Test
    public void getPosition_constructorGPSPosition_isEquals() {
        Assert.assertEquals(testTimedEvent.getPosition(), DEFAULT_GPS_POSITION);
    }
}