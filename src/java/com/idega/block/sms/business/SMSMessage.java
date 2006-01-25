/*
 * $Id: SMSMessage.java,v 1.1 2006/01/25 14:27:54 tryggvil Exp $
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
 * Interface used by the SMSProvider to hold the information for a single SMS that
 * can be sent.
 * </p>
 *  Last modified: $Date: 2006/01/25 14:27:54 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public interface SMSMessage {

	/**
	 * @return Returns the message.
	 */
	public abstract String getMessage();

	/**
	 * @param message
	 *            The message to set.
	 */
	public abstract void setMessage(String message);

	/**
	 * @return Returns the phoneNumber.
	 */
	public abstract String getPhoneNumber();

	/**
	 * @param phoneNumber
	 *            The phoneNumber to set.
	 */
	public abstract void setPhoneNumber(String phoneNumber);
}