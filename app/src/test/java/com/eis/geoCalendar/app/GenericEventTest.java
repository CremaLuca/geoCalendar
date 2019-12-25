package com.eis.geoCalendar.app;

import com.eis.geoCalendar.gps.GPSPosition;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenericEventTest {

    private static String DEFAULT_CONTENT = "Content";
    private static GPSPosition DEFAULT_POSITION = new GPSPosition(22.333332f, 13.323333f);
    private GenericEvent<String> testTimedEvent;

    @Before
    public void setUp() throws Exception {
        testTimedEvent = new GenericEvent<>(DEFAULT_POSITION, DEFAULT_CONTENT);
    }

    @Test
    public void getContent_isEquals() {
        Assert.assertEquals(DEFAULT_CONTENT, testTimedEvent.getContent());
    }

    @Test
    public void getPosition_isEquals() {
        Assert.assertEquals(DEFAULT_POSITION, testTimedEvent.getPosition());
    }
}