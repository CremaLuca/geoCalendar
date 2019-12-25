package com.eis.geoCalendar.app;

import com.eis.geoCalendar.events.EventDatabase;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.eis.geoCalendar.timedEvents.TimedEvent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenericTimedEventManagerTest {


    private static final DateTime DEFAULT_DATETIME = DateTime.now();
    private static final DateTime A_LONG_TIME_AGO = new DateTime(DateTime.getTimeInMillis(2000, 1, 1, 1, 1));
    private static final DateTime LONG_TIME_FROM_NOW = new DateTime(DateTime.getTimeInMillis(2100, 1, 1, 1, 1));

    @Mock
    private TimedEvent<String> mockTimedEvent;

    private EventDatabase<TimedEvent<String>> mockEventDatabase;

    private GenericTimedEventManager<TimedEvent<String>> testTimedEventManager;

    @Before
    public void setUp() throws Exception {
        setupMockTimedEvent();
        mockEventDatabase = new MockEventDatabase<>();
        testTimedEventManager = new GenericTimedEventManager<>(mockEventDatabase);
    }

    private void setupMockTimedEvent() {
        //when(mockTimedEvent.getPosition()).thenReturn(GenericEventManagerTest.DEFAULT_GPS_POSITION);
        //when(mockTimedEvent.getContent()).thenReturn(DEFAULT_CONTENT);
        when(mockTimedEvent.getTime()).thenReturn(DEFAULT_DATETIME);
    }

    @Test
    public void getEventsBeforeTime_mockedTimeEvent_isContained() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertTrue(testTimedEventManager.getEventsBeforeTime(LONG_TIME_FROM_NOW).contains(mockTimedEvent));
    }

    @Test
    public void getEventsBeforeTime_mockedTimeEvent_isNotContained() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertEquals(new ArrayList<TimedEvent<String>>(), testTimedEventManager.getEventsBeforeTime(A_LONG_TIME_AGO));
    }

    @Test
    public void getEventsBeforeTime_emptyEvents_isEmptyArray() {
        Assert.assertEquals(new ArrayList<TimedEvent<String>>(), testTimedEventManager.getEventsBeforeTime(LONG_TIME_FROM_NOW));
    }

    @Test
    public void getEventsAfterTime_mockedTimeEvent_isContained() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertTrue(testTimedEventManager.getEventsAfterTime(A_LONG_TIME_AGO).contains(mockTimedEvent));
    }

    @Test
    public void getEventsAfterTime_mockedTimeEvent_isNotContained() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertEquals(new ArrayList<TimedEvent<String>>(), testTimedEventManager.getEventsAfterTime(LONG_TIME_FROM_NOW));
    }

    @Test
    public void getEventsAfterTime_emptyEvents_isEmptyArray() {
        Assert.assertEquals(new ArrayList<TimedEvent<String>>(), testTimedEventManager.getEventsAfterTime(A_LONG_TIME_AGO));
    }

    @Test
    public void getEventsBetweenTime_mockedTimeEvent_isContained() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertTrue(testTimedEventManager.getEventsBetweenTime(A_LONG_TIME_AGO, LONG_TIME_FROM_NOW).contains(mockTimedEvent));
    }

    @Test
    public void getEventsBetweenTime_emptyEvents_isEmptyArray() {
        Assert.assertEquals(new ArrayList<TimedEvent<String>>(), testTimedEventManager.getEventsBetweenTime(A_LONG_TIME_AGO, LONG_TIME_FROM_NOW));
    }

}