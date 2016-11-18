package com.messagebus.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.messagebus.common.Data;
import com.messagebus.common.Message;

public class MessageTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test_message_construction() {
        // "Category" : "RFID", "Data" : [ "33141878c8504180000027da" ],
        // "DisplayName" : "RFIDScanned", "InstanceID" : "id", "Name"
        String category = "Button";
        String displayName = "This left button is pressed.";
        int instanceId = 1111;
        Data myData = new Data("ABCDE");
        String eventId = "ButtonsButtonPressed";
        Message mess = new Message(instanceId, displayName, category, eventId, myData);
        assertEquals(mess.getData(), myData);
        assertEquals(mess.getInstanceId(), instanceId);
        assertEquals(mess.getDisplayName(), displayName);
        assertEquals(mess.getCategory(), category);
    }

    @Test
    public void test_message_construction_nocategory() {
        // "Category" : "RFID", "Data" : [ "33141878c8504180000027da" ],
        // "DisplayName" : "RFIDScanned", "InstanceID" : "id", "Name"
        String category = null;
        String displayName = "This left button is pressed.";
        String name = "leftbuttonpressed";
        int instanceId = 1111;
        String eventId = null;
        Data myData = new Data("ABCDE");
        boolean exceptionRx = false;
        try {
            Message mess = new Message(instanceId, displayName, category, eventId, myData);
        } catch (NullPointerException ex) {
            exceptionRx = true;
        }
        assertTrue("Exception should throw for null category", exceptionRx);
    }

}
