package com.eis.geoCalendar.network.SMS;

import com.eis.geoCalendar.gps.GPSPosition;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SMSStringEventTest {
    private static final String TEST_NUMBER = "+390000111222";
    private static final String TEST_DESCRIPTION = "Name: description";
    private static final SMSPeer TEST_PEER = new SMSPeer(TEST_NUMBER);

    private GPSPosition testPosition;
    private SMSStringEvent testEvent;
    private SMSNetworkEventUser testUser;


    @Before
    public void setUp() {
        testUser = new SMSNetworkEventUser(TEST_PEER);
        testPosition = mock(GPSPosition.class);
        //Doesn't need to mock methods
        testEvent = new SMSStringEvent(testPosition, TEST_DESCRIPTION, testUser);
    }

    @Test
    public void getContent() {
        assertEquals(TEST_DESCRIPTION, testEvent.getContent());
    }

    @Test
    public void getOwner() {
        assertEquals(testUser, testEvent.getOwner());
    }

    @Test
    public void getPosition() {
        assertEquals(testPosition, testEvent.getPosition());
    }
}