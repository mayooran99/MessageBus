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
 * 25/09/2015	Initial implementation of MessageBus interface.
 *-----------------------------------------------------------------------------
 */
package com.messagebus.common;

import com.messagebus.common.Message;

/**
 * MessageBus interface used to connect MessageBus with the message bus
 * clients/message listeners.
 * 
 * @author prashanw
 * 
 */
public interface MessageBus {

	/**
	 * Subscribe function used to register components with the message bus.
	 * 
	 * @param catogeries
	 *            List of MessageCategories to be registered to.
	 * @param onMessage
	 *            MessageListner instance which is subscribing to the
	 *            categories.
	 * 
	 */
	public void subscribe(String[] catogeries, MessageListener onMessage);

	/**
	 * Constructor of the SendMessage class.
	 * 
	 * @param message
	 *            Instance of the Message to be sent.
	 * 
	 */
	public void sendMessage(Message message);
}
