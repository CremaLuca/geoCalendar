package com.eis.geoCalendar.app;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.geoCalendar.timedEvents.DateTime;
import com.eis.geoCalendar.timedEvents.TimedEvent;
import com.eis.geoCalendar.timedEvents.TimedEventDatabase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenericTimedEventManagerTest {

    private static final String DEFAULT_CONTENT = "Test content";
    private static final DateTime DEFAULT_DATETIME = DateTime.now();
    private static final DateTime A_LONG_TIME_AGO = new DateTime(DateTime.getTimeInMillis(2000, 1, 1, 1, 1));
    private static final DateTime LONG_TIME_FROM_NOW = new DateTime(DateTime.getTimeInMillis(2100, 1, 1, 1, 1));
    private static final GPSPosition DEFAULT_GPS_POSITION = new GPSPosition(33.12345675f, 15.22323232f);
    private static final GPSPosition DISTANT_GPS_POSITION = new GPSPosition(15.32132312f, 33.23232323f);
    @Mock
    private TimedEvent<String> mockTimedEvent;

    private TimedEventDatabase<TimedEvent<String>> mockTimedEventDatabase;

    private GenericTimedEventManager<TimedEvent<String>> testTimedEventManager;

    @Before
    public void setUp() throws Exception {
        setupMockTimedEvent();
        mockTimedEventDatabase = new MockTimedEventDatabase<>();
        testTimedEventManager = new GenericTimedEventManager<>(mockTimedEventDatabase);
    }

    private void setupMockTimedEvent() {
        when(mockTimedEvent.getPosition()).thenReturn(DEFAULT_GPS_POSITION);
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

    class MockTimedEventDatabase<E extends TimedEvent> implements TimedEventDatabase<E> {

        ArrayList<E> events;

        MockTimedEventDatabase() {
            events = new ArrayList<>();
        }

        @Override
        public void saveEvent(@NonNull E event) {
            this.events.add(event);
        }

        @Override
        public void saveEvents(@NonNull Collection<E> events) {
            this.events.addAll(events);
        }

        @Override
        public boolean removeEvent(@NonNull E event) {
            return this.events.remove(event);
        }

        @Override
        public Map<E, Boolean> removeEvents(@NonNull Collection<E> events) {
            Map<E, Boolean> returnMap = new HashMap<>();
            Object[] eventsArray = events.toArray();
            for (int i = 0; i < events.size(); i++) {
                returnMap.put((E) eventsArray[i], events.remove(eventsArray[i]));
            }
            return returnMap;
        }

        @Override
        public ArrayList<E> getSavedEvents() {
            return events;
        }
    }
}