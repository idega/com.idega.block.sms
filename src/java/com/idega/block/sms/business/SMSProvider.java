/*
 * $Id: SMSProvider.java,v 1.1 2006/01/25 14:27:54 tryggvil Exp $
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
 * Declaration of a Messaging provider that accepts an SMSMessage and
 * sends it to the target phoneNumber.
 * </p>
 *  Last modified: $Date: 2006/01/25 14:27:54 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public interface SMSProvider {

	public abstract String send(SMSMessage smsMessage) throws SMSMessagingFailure;
}