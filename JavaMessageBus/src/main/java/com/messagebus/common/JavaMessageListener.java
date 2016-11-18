/**
 *
 * Created on 9/25/2015
 * @author prashanw
 * 
 * ----------------------------------------------------------------------------
 * Revision History
 *-----------------------------------------------------------------------------
 * DATE    		Description
 *-----------------------------------------------------------------------------
 * 25/09/2015	Initial implementation of MessageListener abstract class.
 *-----------------------------------------------------------------------------
 */
package com.messagebus.common;

/**
 * JavaMessageListener class is an abstract class which implements the
 * MessageListener interface. The Modules which require to connect to the
 * MessageBus are required to extend this class.
 * 
 * @author prashanw
 * @see MessageListener
 * 
 */
public abstract class JavaMessageListener implements MessageListener {

	/**
	 * The message bus through which the listener would establish communication.
	 */
	protected MessageBus messageBus;
	/*
	 * This the init param This is a csv parameter list and the first value is
	 * the instanceId of the message bus client/message listener.
	 */
	protected String initParam;
	/*
	 * This is the id assigned to each message bus client/message listener. This
	 * should be the first parameter of the initParam
	 * 
	 */
	protected int instanceId;

	/**
	 * Constructor of the JavaMessageListener class.
	 * 
	 * @param messageBus
	 *            (required) instance of the MessageBus which this module is
	 *            required to connect to.
	 * 
	 */
	public JavaMessageListener(MessageBus messageBus, String initParam) {
		if (messageBus == null) {
			throw new NullPointerException();
		}
		this.messageBus = messageBus;
		this.initParam = initParam;
		String[] params = initParam.split(",");
		if ((params.length >= 1) && !params[0].isEmpty()) {
			// assign the first parameter as the instanceId
			instanceId = Integer.parseInt(params[0]);
		}
	}

	/**
	 * Modules which extend the JavaMessageListener class are required to
	 * implement this start method. This function can be used to subscribe for
	 * messages from the MessageBus
	 * 
	 * @return true if successfully started message bus client/message listener.
	 */
	public abstract boolean start();

	/**
	 * Modules which extends the MessageListener class are required to implement
	 * this stop method. This function can be used to terminate the threads that
	 * have been started and do any cleanup operations.
	 *
	 * @return true if successfully stopped the message bus client/message
	 *         listener.
	 */
	public abstract boolean stop();

}
