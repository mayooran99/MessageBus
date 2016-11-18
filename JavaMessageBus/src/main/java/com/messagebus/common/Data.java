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
 * 25/09/2015	Initial creation of the source file. Implement 
 * 				Data class.
 *-----------------------------------------------------------------------------
 */
package com.messagebus.common;

/**
 * This class is used as the data storing class to send data through MessageBus.
 * Data is contained inside the Message class.
 * 
 * @author prashanw
 * @see MessageBus
 * 
 */
public class Data {

	/** The string value of the data */
	private String stringData;

	/**
	 * Constructor of the Data class.
	 * 
	 * @param data
	 *            (required) string data to create the Data class.
	 */
	public Data(String data) {
		this.stringData = data;
	}

	@Override
	public boolean equals(Object obj) {
		Boolean result;
		Data targetData = (Data) obj;
		if (this.stringData.equals(targetData.stringData)) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return this.stringData.hashCode();
	}

	@Override
	public String toString() {
		return this.stringData;
	}
}
