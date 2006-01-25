/*
 * $Id: SMSMessagingFailure.java,v 1.1 2006/01/25 14:27:54 tryggvil Exp $
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
 * TODO tryggvil Describe Type SMSMessagingFailure
 * </p>
 *  Last modified: $Date: 2006/01/25 14:27:54 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class SMSMessagingFailure extends Exception {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 8169708217419898752L;

	/**
	 * 
	 */
	public SMSMessagingFailure() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public SMSMessagingFailure(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SMSMessagingFailure(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public SMSMessagingFailure(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
