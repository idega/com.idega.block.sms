/*
 * $Id: ASPSMSMessage.java,v 1.2 2006/04/22 09:14:41 laddi Exp $ Created on 30.11.2005
 * in project com.idega.block.sms
 * 
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package com.idega.block.sms.business;

/**
 * <p>
 * This is the SMSMessage implementation for the http://www.aspsms.com provider
 * and used by the ASPSMSProvider implementation.
 * </p>
 * Last modified: $Date: 2006/04/22 09:14:41 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.2 $
 */
public class ASPSMSMessage extends DefaultSMSMessage{

	private String deliverySuccessfulURL = "";
	private int flashingSMS = 0;
	private int referencenumber = 0;
	private String deliveryUnsuccessfulURL = "";
	private String accountPasword;
	private String accountUserKey;
	private String messageOriginator;

	/**
	 * @param smsMessage
	 */
	public ASPSMSMessage(SMSMessage smsMessage) {
		setMessage(smsMessage.getMessage());
		setPhoneNumber(smsMessage.getPhoneNumber());
	}

	public String getXML() {
		String content = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n" + "<aspsms>\r\n" + "<Userkey>"
				+ getAccountUserKey() + "</Userkey>\r\n" + "<Password>" + getAccountPasword() + "</Password>\r\n"
				+ "<Originator>" + getMessageOriginator() + "</Originator>\r\n" + "<Recipient>\r\n" + "<PhoneNumber>"
				+ getPhoneNumber() + "</PhoneNumber>\r\n" + "<TransRefNumber>" + getReferencenumber()
				+ "</TransRefNumber>\r\n" + "</Recipient>\r\n" + "<MessageData>" + getMessage() + "</MessageData>\r\n"
				+ "<FlashingSMS>" + getFlashingSMS() + "</FlashingSMS>\r\n" + "<URLDeliveryNotification>"
				+ getDeliverySuccessfulURL() + "id=</URLDeliveryNotification>\r\n" + "<URLNonDeliveryNotification>"
				+ getDeliveryUnsuccessfulURL() + "id=</URLNonDeliveryNotification>\r\n"
				+ "<Action>SendTextSMS</Action>\r\n" + "</aspsms>\r\n";
		return content;
	}

	/**
	 * @return Returns the deliverySuccessfulURL.
	 */
	public String getDeliverySuccessfulURL() {
		return this.deliverySuccessfulURL;
	}

	/**
	 * @param deliverySuccessfulURL
	 *            The deliverySuccessfulURL to set.
	 */
	public void setDeliverySuccessfulURL(String deliverySuccessfulURL) {
		this.deliverySuccessfulURL = deliverySuccessfulURL;
	}

	/**
	 * @return Returns the deliveryUnsuccessfulURL.
	 */
	public String getDeliveryUnsuccessfulURL() {
		return this.deliveryUnsuccessfulURL;
	}

	/**
	 * @param deliveryUnsuccessfulURL
	 *            The deliveryUnsuccessfulURL to set.
	 */
	public void setDeliveryUnsuccessfulURL(String deliveryUnsuccessfulURL) {
		this.deliveryUnsuccessfulURL = deliveryUnsuccessfulURL;
	}

	/**
	 * @return Returns the flashingSMS.
	 */
	public int getFlashingSMS() {
		return this.flashingSMS;
	}

	/**
	 * @param flashingSMS
	 *            The flashingSMS to set.
	 */
	public void setFlashingSMS(int flashingSMS) {
		this.flashingSMS = flashingSMS;
	}

	/**
	 * @return Returns the referencenumber.
	 */
	public int getReferencenumber() {
		return this.referencenumber;
	}

	/**
	 * @param referencenumber
	 *            The referencenumber to set.
	 */
	public void setReferencenumber(int referencenumber) {
		this.referencenumber = referencenumber;
	}

	/**
	 * @return Returns the accountPasword.
	 */
	public String getAccountPasword() {
		return this.accountPasword;
	}

	/**
	 * @param accountPasword
	 *            The accountPasword to set.
	 */
	public void setAccountPasword(String accountPasword) {
		this.accountPasword = accountPasword;
	}

	/**
	 * @return Returns the accountUserKey.
	 */
	public String getAccountUserKey() {
		return this.accountUserKey;
	}

	/**
	 * @param accountUserKey
	 *            The accountUserKey to set.
	 */
	public void setAccountUserKey(String accountUserKey) {
		this.accountUserKey = accountUserKey;
	}

	/**
	 * @return Returns the messageOriginator.
	 */
	public String getMessageOriginator() {
		return this.messageOriginator;
	}

	/**
	 * @param messageOriginator
	 *            The messageOriginator to set.
	 */
	public void setMessageOriginator(String messageOriginator) {
		this.messageOriginator = messageOriginator;
	}
}
