/**
 * Created on 9/25/2015
 * @author mayooranm
 * 
 * ----------------------------------------------------------------------------
 * Revision History
 *-----------------------------------------------------------------------------
 * DATE    		Description
 *-----------------------------------------------------------------------------
 * 25/09/2015	Initial implementation of MessageListner interface.
 *-----------------------------------------------------------------------------
 */
package com.messagebus.common;

import com.messagebus.common.Message;

/**
 * The listener interface for receiving message events.
 * The class that is interested in processing a message
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMessageListener<code> method. When
 * the message event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MessageEvent
 */
/**
 * @author mayooranM
 *
 */
public interface MessageListener {

	/**
	 * The method called when a message is received from the MessageBus.
	 *
	 * @param message
	 *            the message
	 */
	public void onMessageReceived(Message message);

}
