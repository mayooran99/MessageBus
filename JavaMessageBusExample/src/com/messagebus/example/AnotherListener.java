package com.messagebus.example;

import com.messagebus.common.JavaMessageListener;
import com.messagebus.common.Message;
import com.messagebus.common.MessageBus;

public class AnotherListener extends JavaMessageListener {

	private int instanceId;
	private MessageBus messageBus;

	public AnotherListener(MessageBus messageBus, String initParam) {
		super(messageBus, initParam);
		this.instanceId = Integer.parseInt(initParam.split(",")[0]);
		this.messageBus = messageBus;
	}

	@Override
	public void onMessageReceived(Message message) {
		System.out.println("Message received by client : " + instanceId);
		System.out.println("Received message is : " + message.getData().toString());

	}

	@Override
	public boolean start() {
		System.out.println("Another listener started!! " + instanceId);
		messageBus.subscribe(new String[] { "Category" }, this);
		return true;
	}

	@Override
	public boolean stop() {
		System.out.println("Another listener stopped!! " + instanceId);
		return true;
	}

}
