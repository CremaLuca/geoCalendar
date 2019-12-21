package com.eis.geoCalendar.app;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.TimedEvent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenericTimedEventManagerTest {

    private static final String DEFAULT_CONTENT = "Test content";
    private static final LocalDateTime DEFAULT_DATETIME = LocalDateTime.now();
    private static final GPSPosition DEFAULT_GPS_POSITION = new GPSPosition(33.12345675f, 15.22323232f);
    @Mock
    private TimedEvent<String> mockTimedEvent;
    private GenericTimedEventManager<TimedEvent<String>> testTimedEventManager;

    @Before
    public void setUp() throws Exception {
        setupMockTimedEvent();

        testTimedEventManager = new GenericTimedEventManager<>();
        testTimedEventManager.addEvent(mockTimedEvent);
    }

    private void setupMockTimedEvent() {
        when(mockTimedEvent.getPosition()).thenReturn(DEFAULT_GPS_POSITION);
        when(mockTimedEvent.getContent()).thenReturn(DEFAULT_CONTENT);
        when(mockTimedEvent.getTime()).thenReturn(DEFAULT_DATETIME);
    }


    @Test
    public void getEventsBeforeTime_mockedTimeEvent_isContained() {
        Assert.assertTrue(testTimedEventManager.getEventsBeforeTime(LocalDateTime.of(2100, 1, 1, 1, 1)).contains(mockTimedEvent));
    }

    @Test
    public void getEventsAfterTime_mockedTimeEvent_isContained() {
        Assert.assertTrue(testTimedEventManager.getEventsAfterTime(LocalDateTime.of(2000, 1, 1, 1, 1)).contains(mockTimedEvent));
    }

    @Test
    public void getEventsBetweenTime() {
        LocalDateTime beforeTime = LocalDateTime.of(2000, 1, 1, 1, 1);
        LocalDateTime afterTime = LocalDateTime.of(2100, 1, 1, 1, 1);
        Assert.assertTrue(testTimedEventManager.getEventsBetweenTime(beforeTime, afterTime).contains(mockTimedEvent));
    }

    @Test
    public void removeEvent_presentEvent_isTrue() {
        Assert.assertTrue(testTimedEventManager.removeEvent(mockTimedEvent));
    }

    @Test
    public void getEventsInRange_mockedTimeEvent_isContained() {
        Assert.assertTrue(testTimedEventManager.getEventsInRange(DEFAULT_GPS_POSITION, 5f).contains(mockTimedEvent));
    }

    @Test
    public void getClosestEvent_mockedTimeEvent_isContained() {
        Assert.assertEquals(mockTimedEvent, testTimedEventManager.getClosestEvent(DEFAULT_GPS_POSITION));
    }

    @Test
    public void getAllEvents() {
        Assert.assertTrue(testTimedEventManager.getAllEvents().contains(mockTimedEvent));
    }
}