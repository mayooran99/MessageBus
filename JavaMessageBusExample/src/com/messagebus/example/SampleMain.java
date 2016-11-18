package com.messagebus.example;

import com.messagebus.common.Data;
import com.messagebus.common.Message;
import com.messagebus.core.JavaMessageBus;

public class SampleMain {

	private JavaMessageBus messageBus;
	private SampleListener listener1;
	private AnotherListener listener2;

	public static void main(String[] args) {
		SampleMain main = new SampleMain();
		main.init();
		main.close();
	}

	public void init() {
		messageBus = new JavaMessageBus();
		messageBus.setqueueSizePerPlugin(10);

		listener1 = new SampleListener(messageBus, "100");
		boolean result1 = listener1.start();

		listener2 = new AnotherListener(messageBus, "101");
		boolean result2 = listener2.start();

		if (result1 && result2) {
			listener1.sendMessageToSomeone();
		}

	}

	public void close() {
		listener1.stop();
		listener2.stop();
		messageBus.terminateMessageBus();
	}
}
