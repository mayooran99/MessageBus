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
 * 25/09/2015	Initial implementation of Message class.
 *-----------------------------------------------------------------------------
 */
package com.messagebus.common;

/**
 * Message class is the unit used to send messages between the MessageBus and
 * the connected modules. Note that the messages are broadcast to any member who
 * has subscribed to the particular message category. The targetInstanceId is
 * used to target any specific message bus client/message listener, but other
 * message listeners are capable of listening to those messages if they are
 * subscribed. The static final value of ANY_TARGET can be used to target all
 * the message bus clients
 * 
 * @author prashanw
 * @see MessageCategory
 * 
 */
public class Message {

	public static final int ANY_TARGET = 1;

	private Data data;
	private int instanceId;
	private int targetInstanceId;
	private String displayName;
	private String category;
	private String eventId;

	/**
	 * Constructor of the Message. This constructor use targetInstanceId as 1
	 * indicating any target(That is all message bus clients).
	 * 
	 * @param instanceId
	 *            of the connected component. This value is assigned in the
	 *            Message bus. The instance ids 0 to 99 are system reserved. The
	 *            instance id of message bus core is 0. The instance id 1 used
	 *            to define any instance id. Specifically used in defining the
	 *            targetInstanceId when the target could be any message bus
	 *            client.
	 * @param displayName
	 *            Display name of the Message
	 * @param category
	 *            MessageCategory of the Message
	 * @param eventId
	 *            Event Id of the Message. There can be many events under a
	 *            certain message category. Data to be sent are grouped under
	 *            events
	 * @param data
	 *            Data of the message
	 * @see Data
	 * @see MessageCategory
	 */
	public Message(int instanceId, String displayName, String category, String eventId, Data data) {

		if (category == null) {
			throw new NullPointerException();
		}
		this.targetInstanceId = ANY_TARGET;
		this.data = data;
		this.instanceId = instanceId;
		this.displayName = displayName;
		this.category = category;
		this.eventId = eventId;
	}

	/**
	 * Constructor of the Message.
	 * 
	 * @param instanceId
	 *            of the connected component. This value is assigned in the
	 *            Message bus The instance ids 0 to 99 are system reserved. The
	 *            instance id of message bus core is 0. The instance id 1 used
	 *            to define any instance id. Specifically used in defining the
	 *            targetInstanceId when the target could be any message bus
	 *            client.
	 * @param targetInstanceId
	 *            used to define the interested target of the message. The valid
	 *            instance ids are the message bus client ids. The id 1 used to
	 *            define any target.
	 * @param displayName
	 *            Display name of the Message
	 * @param category
	 *            MessageCategory of the Message
	 * @param eventId
	 *            Event Id of the Message. There can be many events under a
	 *            certain message category. Data to be sent are grouped under
	 *            events
	 * @param data
	 *            Data of the message
	 * @see Data
	 * @see MessageCategory
	 */
	public Message(int instanceId, int targetInstanceId, String displayName, String category, String eventId,
			Data data) {

		if (category == null) {
			throw new NullPointerException();
		}
		this.targetInstanceId = targetInstanceId;
		this.data = data;
		this.instanceId = instanceId;
		this.displayName = displayName;
		this.category = category;
		this.eventId = eventId;
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result = false;
		Message targetMessage = (Message) obj;
		if (targetMessage == null) {
			result = false;
		}
		if (this.data.equals(targetMessage.getData())) {
			if (this.instanceId == targetMessage.getInstanceId() && this.eventId.equals(targetMessage.getEventId())
					&& this.displayName.equals(targetMessage.getDisplayName())
					&& this.category.equals(targetMessage.getCategory())) {
				result = true;
			}
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return data.hashCode() + instanceId + eventId.hashCode() + displayName.hashCode() + category.hashCode();
	}

	/**
	 * Returns the Data of the message
	 * 
	 * @return returns the Data of the message
	 * @see Data
	 */
	public Data getData() {
		return this.data;
	}

	/**
	 * Returns the instanceID of the message generated component
	 * 
	 * @return returns the integer value instenceId of generated component
	 */
	public int getInstanceId() {
		return this.instanceId;
	}

	/**
	 * Returns the targetInstanceID of the message generated component
	 * 
	 * @return returns the integer targerInstenceId of generated component
	 */
	public int getTargetInstanceId() {
		return this.targetInstanceId;
	}

	/**
	 * Returns the display name of the message
	 * 
	 * @return returns the String display name of the message
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Returns the MessageCategory of the message
	 * 
	 * @return returns the MessageCategory of the message
	 */
	public String getCategory() {
		return this.category;
	}

	public String getEventId() {
		return eventId;
	}

}
