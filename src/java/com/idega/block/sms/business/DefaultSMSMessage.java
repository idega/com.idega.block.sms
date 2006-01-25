/*
 * $Id: DefaultSMSMessage.java,v 1.1 2006/01/25 14:27:54 tryggvil Exp $
 * Created on 30.11.2005 in project com.idega.block.sms
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.sms.business;


/**
 * <p>
 * Default implementation of the SMSMessage interface.
 * </p>
 *  Last modified: $Date: 2006/01/25 14:27:54 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class DefaultSMSMessage implements SMSMessage {

	private String message;
	private String phoneNumber;

	/**
	 * 
	 */
	public DefaultSMSMessage() {
		super();
	}
	

	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	
	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * @return Returns the phoneNumber.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	
	/**
	 * @param phoneNumber The phoneNumber to set.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
