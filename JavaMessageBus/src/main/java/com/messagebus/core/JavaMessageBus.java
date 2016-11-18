/**
 *
 * Created on 9/25/2015
 * @author mayooranm
 * 
 * ----------------------------------------------------------------------------
 * Revision History
 *-----------------------------------------------------------------------------
 * DATE    		Description
 *-----------------------------------------------------------------------------
 * 25/09/2015	Initial implementation of JavaMessageBus class.
 *-----------------------------------------------------------------------------
 */
package com.messagebus.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.messagebus.common.Message;
import com.messagebus.common.MessageBus;
import com.messagebus.common.MessageListener;

/**
 * JavaMessageBus class is the concrete implementation of the MessageBus
 * interface. Routes the messages to the relevant message bus clients.
 * 
 * @author prashanw,mayooran
 * @see MessageBus
 * 
 */
public class JavaMessageBus implements MessageBus {

	Thread th;
	Message message;
	Map<MessageListener, BlockingQueue<Message>> messageListenerMessageQueueMap = new HashMap<MessageListener, BlockingQueue<Message>>();
	int messageQueueSizePerPlugin = 10;
	ScheduledExecutorService execService = Executors.newScheduledThreadPool(10);
	Boolean isAlive = true;

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(JavaMessageBus.class.getName());

	/**
	 * Internal MessageListenerItem class used to store each message listener
	 * and respective subscribed message categories.
	 * 
	 * @see MessageBus
	 * @see MessageListner
	 * 
	 */
	private class MessageListenerItem {
		private MessageListener ml;
		private String[] categories;

		/**
		 * Constructor of the MessageListenerItem class.
		 * 
		 * @param messageListner
		 *            instance of the MessageListner interface.
		 * @param catogeries
		 *            list of MessageCategories registered.
		 */
		public MessageListenerItem(MessageListener messageListner, String[] catogeries) {
			this.categories = catogeries;
			this.ml = messageListner;
		}

		public MessageListener getMl() {
			return ml;
		}

		/**
		 * This function sends the given message to the registered message
		 * listener, if the listener is registered to the given message category.
		 * 
		 * @param Message
		 *            (required) to be send.
		 */
		public void sendMessage(Message message) {
			if (categories == null) {
				return;
			}
			if (message == null) {
				return;
			}
			if (message.getCategory() == null) {
				return;
			}
			for (String messageCategory : categories) {
				if (messageCategory.equals(message.getCategory()) && this.ml != null) {

					this.ml.onMessageReceived(message);
					break;
				}
			}
		}

	}

	private List<MessageListenerItem> messageListnerItemList;

	/**
	 * The constructor.
	 * 
	 */
	public JavaMessageBus() {
		messageListnerItemList = new ArrayList<>();

	}

	/**
	 * Sets the queue size per message bus client/message listener.
	 *
	 * @param size
	 *            the new queue size per plugin
	 */
	public void setqueueSizePerPlugin(int size) {
		this.messageQueueSizePerPlugin = size;
	}

	/*
	 * Method used to subscribe to specific category of events of the
	 * MessageBus.
	 */
	@Override
	public synchronized void subscribe(String[] catogeries, MessageListener onMessage) {
		final MessageListener messageListener = onMessage;
		final MessageListenerItem messageListenerItem = new MessageListenerItem(onMessage, catogeries);
		this.messageListnerItemList.add(messageListenerItem);
		BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<Message>(this.messageQueueSizePerPlugin);
		this.messageListenerMessageQueueMap.put(onMessage, messageQueue);
		execService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				processMessages(messageListener, messageListenerItem);
			}
		}, 0, 1, TimeUnit.NANOSECONDS);

	}

	/**
	 * Process the messages that have been put in queues unique to each message bus client/listener
	 * 
	 * @param onMessage
	 * @param messageListenerItem
	 */
	private void processMessages(MessageListener onMessage, MessageListenerItem messageListenerItem) {

		BlockingQueue<Message> queueToBeProcessed = messageListenerMessageQueueMap.get(onMessage);
		Message messageToBeBroadcasted = queueToBeProcessed.poll();
		while (messageToBeBroadcasted != null) {
			messageListenerItem.sendMessage(messageToBeBroadcasted);
			messageToBeBroadcasted = queueToBeProcessed.poll();
		}
	}

	/*
	 * The method used to send messages to the MessageBus.
	 */
	@Override
	public void sendMessage(Message message) {

		for (Iterator<MessageListenerItem> iterator = messageListnerItemList.iterator(); iterator.hasNext();) {
			MessageListenerItem messageListnerItem = (MessageListenerItem) iterator.next();
			BlockingQueue<Message> queueToBeInserted = messageListenerMessageQueueMap.get(messageListnerItem.getMl());
			try {
				queueToBeInserted.put(message);
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE,
						"Interrupted while inserting to message queue of : " + messageListnerItem.getClass() + e);
			}
		}
	}

	/**
	 * Close any message bus threads running for the clients and do the clean up
	 * activities
	 */
	public void terminateMessageBus() {
		isAlive = false;
		execService.shutdown();
	}
}
