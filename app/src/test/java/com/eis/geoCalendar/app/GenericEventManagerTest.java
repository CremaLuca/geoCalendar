package com.eis.geoCalendar.app;

import com.eis.geoCalendar.events.Event;
import com.eis.geoCalendar.events.EventDatabase;
import com.eis.geoCalendar.gps.GPSPosition;
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
public class GenericEventManagerTest {

    public static final String DEFAULT_CONTENT = "Test content";
    public static final GPSPosition DEFAULT_GPS_POSITION =
            new GPSPosition().updateLocation(33.12345675f, 15.22323232f);
    public static final GPSPosition DISTANT_GPS_POSITION =
            new GPSPosition().updateLocation(15.32132312f, 33.23232323f);

    @Mock
    private Event<String> mockTimedEvent;

    private EventDatabase<Event<String>> mockEventDatabase;

    private GenericEventManager<Event<String>> testTimedEventManager;

    @Before
    public void setUp() throws Exception {
        setupMockEvent();
        mockEventDatabase = new MockEventDatabase<>();
        testTimedEventManager = new GenericEventManager<>(mockEventDatabase);
    }

    private void setupMockEvent() {
        when(mockTimedEvent.getPosition()).thenReturn(DEFAULT_GPS_POSITION);
        //when(mockTimedEvent.getContent()).thenReturn(DEFAULT_CONTENT);
    }

    @Test
    public void removeEvent_mockedTimeEvent_isTrue() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertTrue(testTimedEventManager.removeEvent(mockTimedEvent));
    }

    @Test
    public void removeEvent_emptyEvents_isFalse() {
        Assert.assertFalse(testTimedEventManager.removeEvent(mockTimedEvent));
    }

    @Test
    public void getEventsInRange_mockedTimeEvent_isContained() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertTrue(testTimedEventManager.getEventsInRange(DEFAULT_GPS_POSITION, 5f).contains(mockTimedEvent));
    }

    @Test
    public void getEventsInRange_mockedTimeEvent_isNotContained() {
        testTimedEventManager.addEvent(mockTimedEvent);
        //Assert.assertFalse(testTimedEventManager.getEventsInRange(DISTANT_GPS_POSITION, 5f).contains(mockTimedEvent));
        //TODO: Un-comment this when GPSPosition will be implemented
    }

    @Test
    public void getEventsInRange_emptyEvents_isEmptyArray() {
        Assert.assertEquals(new ArrayList<TimedEvent<String>>(), testTimedEventManager.getEventsInRange(DEFAULT_GPS_POSITION, 5f));
    }

    @Test
    public void getClosestEvent_mockedTimeEvent_isContained() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertEquals(mockTimedEvent, testTimedEventManager.getClosestEvent(DEFAULT_GPS_POSITION));
    }

    @Test
    public void getClosestEvent_emptyEvents_isNull() {
        Assert.assertNull(testTimedEventManager.getClosestEvent(DEFAULT_GPS_POSITION));
    }

    @Test
    public void getAllEvents() {
        testTimedEventManager.addEvent(mockTimedEvent);
        Assert.assertTrue(testTimedEventManager.getAllEvents().contains(mockTimedEvent));
    }
}