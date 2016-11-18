package com.messagebus.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.messagebus.common.Data;
import com.messagebus.common.Message;
import com.messagebus.common.MessageBus;
import com.messagebus.common.MessageListener;
import com.messagebus.core.JavaMessageBus;

public class MessageBusTest {
	@Test
	public void test_message_bus_constructor() {
		MessageBus myBus = new JavaMessageBus();
		assertNotNull(myBus);
	}

	@Test
	public void test_message_passing() throws Exception{
		MessageBus myBus = new JavaMessageBus();
		MessageListener myListner1 = new TestMessageListener();
		MessageListener myListner2 = new TestMessageListener();
		myBus.subscribe(new String[] { "ButtonBasic", "AppEvents" }, myListner1);
		myBus.subscribe(new String[] { "ButtonBasic", "AppEvents" }, myListner2);
		String category = "ButtonBasic";
		String displayName = "This left button is pressed.";
		String name = "leftbuttonpressed";
		String eventId = "ButtonPressed";
		int instanceId = 1111;
		Data myData = new Data("ABCDE");
		Message message = new Message(instanceId, Message.ANY_TARGET, displayName, category, eventId, myData);
		myBus.sendMessage(message);
		Thread.sleep(100);
		Message rxMessage1 = ((TestMessageListener) myListner1).GetRxMessage();
		assertEquals("The received message differ from sent.", message, rxMessage1);
		Message rxMessage2 = ((TestMessageListener) myListner2).GetRxMessage();
		assertEquals("The received message differ from sent.", message, rxMessage2);

		Data myData2 = new Data("AxxxBCDE");
		Message message2 = new Message(instanceId, Message.ANY_TARGET, displayName, category, eventId, myData2);
		myBus.sendMessage(message2);
		Message rxMessage3 = ((TestMessageListener) myListner1).GetRxMessage();
		assertNotEquals("The received message differ from sent.", message2, rxMessage1);
		Message rxMessage4 = ((TestMessageListener) myListner2).GetRxMessage();
		assertNotEquals("The received message differ from sent.", message2, rxMessage2);
	}

	@Test
	public void test_message_subcriptions() throws Exception{
		MessageBus myBus = new JavaMessageBus();
		MessageListener myButtonPressedListner = new TestMessageListener();
		MessageListener myBarcodeScannedListner = new TestMessageListener();
		myBus.subscribe(new String[] { "ButtonBasic", "AppEvents" }, myButtonPressedListner);
		myBus.subscribe(new String[] { "BarcodeBasic", "AppEvents" }, myBarcodeScannedListner);

		String category = "ButtonBasic";
		String displayName = "This left button is pressed.";
		String name = "leftbuttonpressed";
		int instanceId = 1111;
		String eventId = "ButtonsButtonPressed";
		Data myData = new Data("ABCDE");
		
		String category2 = "BarcodeBasic";
		String displayName2 = "Barcode basic event has been received.";
		String eventId2 = "BarcodeBasicEvent";
		Data myData2 = new Data("enablebarcode");

		Message messageButtonPress = new Message(instanceId, Message.ANY_TARGET, displayName, category, eventId,
				myData);
		myBus.sendMessage(messageButtonPress);

		Message messageEnableBarcode = new Message(instanceId,Message.ANY_TARGET,displayName2,category2,eventId2,myData2);
		myBus.sendMessage(messageEnableBarcode);
		
		Thread.sleep(100);
		
		Message buttonPressedRxMessage1 = ((TestMessageListener) myButtonPressedListner).GetRxMessage();
		assertEquals("The received un-subscribe message.", messageButtonPress, buttonPressedRxMessage1);

		Message BarcodeScannedRxMessage1 = ((TestMessageListener) myBarcodeScannedListner).GetRxMessage();
		assertNotEquals("The received message differ from sent.", messageButtonPress, BarcodeScannedRxMessage1);

		String categoryCashUsed = "AppEvents";
		String eventIdCashUsed = "BarcodeBarcodeScanned";

		Message messageKohlsCashUsed = new Message(instanceId, displayName, categoryCashUsed, eventIdCashUsed, myData);
		myBus.sendMessage(messageKohlsCashUsed);
		
		Thread.sleep(1);

		Message buttonPressedRxMessage2 = ((TestMessageListener) myButtonPressedListner).GetRxMessage();
		assertEquals("The received message.", messageKohlsCashUsed, buttonPressedRxMessage2);

		Message BarcodeScannedRxMessage2 = ((TestMessageListener) myBarcodeScannedListner).GetRxMessage();
		assertEquals("The received message.", messageKohlsCashUsed, BarcodeScannedRxMessage2);

		String eventIdUsageTime = "KubeUsageTime";
		String categoryUsageTime = "AppEvents";

		Message messageUsageTime = new Message(instanceId, displayName, categoryUsageTime, eventIdUsageTime, myData);
		myBus.sendMessage(messageUsageTime);
		
		Thread.sleep(1);

		Message buttonPressedRxMessage3 = ((TestMessageListener) myButtonPressedListner).GetRxMessage();
		assertNotNull("Invalid message received", buttonPressedRxMessage3);

		Message BarcodeScannedRxMessage3 = ((TestMessageListener) myBarcodeScannedListner).GetRxMessage();
		assertNotNull("Invalid message received", BarcodeScannedRxMessage3);

	}

    @Test
	public void test_message_null_subscriptions() throws Exception{
		MessageBus myBus;
		myBus = new JavaMessageBus();
		MessageListener myButtonPressedListner = new TestMessageListener();
		MessageListener myBarcodeScannedListner = new TestMessageListener();
		myBus.subscribe(new String[] { "ButtonsButtonPressed", "KohlsOfferKohlsCashUsed" }, myButtonPressedListner);
		myBus.subscribe(null, myBarcodeScannedListner);

		String category = "ButtonBasic";
		String eventId = "ButtonsButtonPressed";
		String displayName = "This left button is pressed.";
		String name = "leftbuttonpressed";
		int instanceId = 1111;
		Message messageUsageTime = new Message(instanceId, displayName, category, eventId, new Data("sdfsdfsdfsdf"));
		myBus.sendMessage(messageUsageTime);
	}

	@Test
	public void test_message_null_listners() {
		MessageBus myBus;
		myBus = new JavaMessageBus();
		MessageListener myButtonPressedListner = new TestMessageListener();
		MessageListener myBarcodeScannedListner = new TestMessageListener();
		myBus.subscribe(new String[] { "ButtonBasic", "AppEvents" }, myButtonPressedListner);
		myBus.subscribe(new String[] { "ButtonBasic", "AppEvents" }, null);

		String category = "ButtonBasic";
		String eventId = "ButtonsButtonPressed";
		String displayName = "This left button is pressed.";
		String name = "leftbuttonpressed";
		int instanceId = 1111;
		Message messageUsageTime = new Message(instanceId, displayName, category, eventId, new Data("sdfsdfsdfsdf"));
		myBus.sendMessage(messageUsageTime);
	}

}
