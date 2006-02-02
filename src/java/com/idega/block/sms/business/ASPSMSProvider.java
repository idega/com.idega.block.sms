/*
 * $Id: ASPSMSProvider.java,v 1.2 2006/02/02 13:15:41 tryggvil Exp $ Created on 30.11.2005
 * in project com.idega.block.sms
 * 
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 * 
 * This software is the proprietary information of Idega hf. Use is subject to
 * license terms.
 */
package com.idega.block.sms.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;
import com.idega.idegaweb.IWMainApplication;

/**
 * <p>
 * Implementation of the SMSProvider Interface for the http://www.aspsms.com/
 * service provider.<br/>
 * This service can be configured with the following application properties:
 * <code>com.aspsms.serviceurl</code>,<code>com.aspsms.userkey</code>,
 * <code>com.aspsms.password</code> and <code>com.aspsms.originator</code>.
 * 
 * </p>
 * Last modified: $Date: 2006/02/02 13:15:41 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.2 $
 */
public class ASPSMSProvider implements SMSProvider {

	Logger log = Logger.getLogger(ASPSMSProvider.class.getName());
	
	private String serviceUrl = "http://xml2.aspsms.com:5061/xmlsvr.asp";
	// insert required userkey,password and originator
	private String userkey = null;//"43JTRQ7WNZ64";
	private String password = null;//"tryggv1agura";
	private String originator = null;//"AguraIT";
	
	public static final String PROPERTY_KEY_SERVICE_URL="com.aspsms.serviceurl";
	public static final String PROPERTY_KEY_USERKEY="com.aspsms.userkey";
	public static final String PROPERTY_KEY_PASSWORD="com.aspsms.password";
	public static final String PROPERTY_KEY_ORIGINATOR="com.aspsms.originator";
	
	public ASPSMSProvider() {
		this(IWMainApplication.getDefaultIWMainApplication());
	}
	
	/**
	 * 
	 */
	public ASPSMSProvider(IWMainApplication iwma) {
		super();
		if(iwma!=null){
			initialize(iwma);
		}
		else{
			log.warning("IWMainApplication instance is null, cannot read properties");
		}
	}

	/**
	 * <p>
	 * TODO tryggvil describe method initialize
	 * </p>
	 * @param iwma
	 */
	private void initialize(IWMainApplication iwma) {
		String pServiceUrl = iwma.getSettings().getProperty(PROPERTY_KEY_SERVICE_URL);
		if(pServiceUrl!=null){
			setServiceUrl(pServiceUrl);
		}
		String pUserkey = iwma.getSettings().getProperty(PROPERTY_KEY_USERKEY);
		if(pUserkey!=null){
			setUserkey(pUserkey);
		}
		String pPasswd = iwma.getSettings().getProperty(PROPERTY_KEY_PASSWORD);
		if(pPasswd!=null){
			setPassword(pPasswd);
		}
		String pOriginator = iwma.getSettings().getProperty(PROPERTY_KEY_ORIGINATOR);
		if(pOriginator!=null){
			setOriginator(pOriginator);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.idega.block.sms.business.SMSProvider#send(com.idega.block.sms.business.SMSMessage)
	 */
	public String send(SMSMessage smsMessage) throws SMSMessagingFailure {
		ASPSMSMessage aspMessage;
		if(smsMessage instanceof ASPSMSMessage){
			aspMessage =(ASPSMSMessage)smsMessage;
		}
		else{
			aspMessage = new ASPSMSMessage(smsMessage);
			aspMessage.setAccountPasword(getPassword());
			aspMessage.setAccountUserKey(getUserkey());
			aspMessage.setMessageOriginator(getOriginator());
		}
		
		String content = aspMessage.getXML();
		//InetAddress inetAddr = null;
		String xmlResult = "";
		try {
			URL aspsmsURL = new URL(serviceUrl);
			URLConnection aspsmsCon = aspsmsURL.openConnection();
			aspsmsCon.setRequestProperty("Content-Type", "text/xml");
			aspsmsCon.setDoOutput(true);
			aspsmsCon.setDoInput(true);
			PrintWriter out = new PrintWriter(aspsmsCon.getOutputStream());
			char[] buffer = new char[1024 * 10];
			buffer = content.toCharArray();
			out.write(buffer, 0, content.length());
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(aspsmsCon.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				xmlResult = xmlResult + inputLine;
				//System.out.println(inputLine);
			}
			in.close();
		}
		catch (Exception ex) {
			//System.out.println(ex.getMessage());
			throw new SMSMessagingFailure(ex);
		}
		return xmlResult;
	}

	public static void main(String[] args) {
		ASPSMSProvider testSMS = new ASPSMSProvider();
		testSMS.setUserkey("43JTRQ7WNZ64");
		testSMS.setPassword("tryggv1agura");
		testSMS.setOriginator("AguraIT");
		
		String handynumber = "003546954451";
		String message = "testskilabod";
		//System.out.println(testSMS.userkey);
		SMSMessage smsMessage = new DefaultSMSMessage();
		smsMessage.setMessage(message);
		smsMessage.setPhoneNumber(handynumber);
		
		try {
			String result = testSMS.send(smsMessage);
			System.out.println(result);
		}
		catch (SMSMessagingFailure e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * @return Returns the originator.
	 */
	public String getOriginator() {
		return originator;
	}

	
	/**
	 * @param originator The originator to set.
	 */
	public void setOriginator(String originator) {
		this.originator = originator;
	}

	
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		if(password==null){
			throw new RuntimeException("No password set");
		}
		return password;
	}

	
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	
	/**
	 * @return Returns the userkey.
	 */
	public String getUserkey() {
		if(userkey==null){
			throw new RuntimeException("No userkey set");
		}
		return userkey;
	}

	
	/**
	 * @param userkey The userkey to set.
	 */
	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

	
	/**
	 * @return Returns the serviceUrl.
	 */
	public String getServiceUrl() {
		return serviceUrl;
	}

	
	/**
	 * @param serviceUrl The serviceUrl to set.
	 */
	public void setServiceUrl(String xmlURL) {
		this.serviceUrl = xmlURL;
	}
}
