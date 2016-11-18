package com.messagebus.test;

import com.messagebus.common.Message;
import com.messagebus.common.MessageListener;

public class TestMessageListener implements MessageListener {

	private Message message;

	@Override
	public void onMessageReceived(Message message) {
		this.message = message;
	}

	public Message GetRxMessage() {
		message = new Message(message.getInstanceId(), message.getTargetInstanceId(), message.getDisplayName(),
				message.getCategory(), message.getEventId(), message.getData());
		return message;
	}
}
