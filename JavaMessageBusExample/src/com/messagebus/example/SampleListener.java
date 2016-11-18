package com.messagebus.example;

import com.messagebus.common.Data;
import com.messagebus.common.JavaMessageListener;
import com.messagebus.common.Message;
import com.messagebus.common.MessageBus;

public class SampleListener extends JavaMessageListener {

	private MessageBus messageBus;
	private int instanceId;

	public SampleListener(MessageBus messageBus, String initParam) {
		super(messageBus, initParam);
		this.messageBus = messageBus;
		this.instanceId = Integer.parseInt(initParam.split(",")[0]);
	}

	@Override
	public void onMessageReceived(Message message) {
		System.out.println("Message received by client : " + instanceId);
		System.out.println("Received Message is : " + message.getData().toString());

	}

	@Override
	public boolean start() {
		System.out.println("Sucessfully started the listener "+instanceId);
		messageBus.subscribe(new String[] { "Category" }, this);
		return true;
	}

	@Override
	public boolean stop() {
		System.out.println("Successfully stopped the listener "+instanceId);
		return false;
	}

	public void sendMessageToSomeone() {
		System.out.println("called sendMessageToSomeone");
		messageBus.sendMessage(
				new Message(instanceId, Message.ANY_TARGET, "DisplayName", "Category", "EventID", new Data("data")));
	}

}
