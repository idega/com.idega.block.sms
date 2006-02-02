package com.idega.block.sms.business;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import javax.faces.context.FacesContext;
import com.idega.block.sms.presentation.SMSAuthenticator;
import com.idega.business.IBOLookup;
import com.idega.core.accesscontrol.business.LoginBusinessBean;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneType;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;
import com.idega.util.StringHandler;

/**
 * <p>
 * Backing bean for the SMSAuthenticator UI Component.
 * </p>
 *  Last modified: $Date: 2006/02/02 13:15:41 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class SMSAuthenticationBean {

	public static final String BEAN_ID="SMSAuthenticationBean";
	
	private String userPersonalId;
	private String password;
	private String generatedOneTimePassword;
	private String userTypedOneTimePassword;
	private boolean userCredentialsMatch=false;
	private User user;
	private String mobilePhoneNumber=null;
	
	public String getUserPersonalId() {
		return userPersonalId;
	}

	public void setUserPersonalId(String userPersonalId) {
		this.userPersonalId = userPersonalId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public void generateOneTimePassword(){
		
		IWContext iwc = IWContext.getInstance();
		Locale locale = iwc.getCurrentLocale();
		
		IWBundle bundle = iwc.getIWMainApplication().getBundle(SMSAuthenticator.IW_BUNDLE_IDENTIFIER);
		IWResourceBundle iwrb = bundle.getResourceBundle(locale);
		
		try {
			if(isUserCredentialsMatch()){
				Collection phones = getUser().getPhones(Integer.toString(PhoneType.MOBILE_PHONE_ID));
				for (Iterator iter = phones.iterator(); iter.hasNext();) {
					Phone phone = (Phone) iter.next();
					mobilePhoneNumber = phone.getNumber();
				}
				
				SMSProvider smsProvider = SMSProviderFactory.getDefaultProvider();
				SMSMessage message = new DefaultSMSMessage();
				if(mobilePhoneNumber!=null){
					generatedOneTimePassword = StringHandler.getRandomStringNonAmbiguous(6);
					message.setPhoneNumber(mobilePhoneNumber);
					String[] formatValues = {generatedOneTimePassword};
					String sMessage = iwrb.getLocalizedAndFormattedString("user_sms_message","Your one-time-password is: {0}",formatValues);
					message.setMessage(sMessage);
					smsProvider.send(message);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean validateOneTimePassword(){
		boolean validation = getGeneratedOneTimePassword().equals(getUserTypedOneTimePassword());
		
		FacesContext context = FacesContext.getCurrentInstance();
		IWContext iwc = IWContext.getIWContext(context);
		
		LoginBusinessBean loginBean = LoginBusinessBean.getLoginBusinessBean(iwc);
		
		if(validation){
			try {
				loginBean.logInByPersonalID(iwc,getUserPersonalId());
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return validation;
		
	}

	public String getGeneratedOneTimePassword() {
		return generatedOneTimePassword;
	}

	public void setGeneratedOneTimePassword(String generatedOneTimePassword) {
		this.generatedOneTimePassword = generatedOneTimePassword;
	}

	public String getUserTypedOneTimePassword() {
		return userTypedOneTimePassword;
	}

	public void setUserTypedOneTimePassword(String userTypedOneTimePassword) {
		this.userTypedOneTimePassword = userTypedOneTimePassword;
	}

	
	public boolean isUserCredentialsMatch() {

		IWContext iwc = IWContext.getInstance();
		Locale locale = iwc.getCurrentLocale();
		
		IWBundle bundle = iwc.getIWMainApplication().getBundle(SMSAuthenticator.IW_BUNDLE_IDENTIFIER);
		IWResourceBundle iwrb = bundle.getResourceBundle(locale);
		
		try {
			if(user==null){
				UserBusiness userbusiness = (UserBusiness) IBOLookup.getServiceInstance(iwc,UserBusiness.class);
				
				user = userbusiness.getUser(getUserPersonalId());
				if(user!=null){
					userCredentialsMatch=true;
				}
				else{
					userCredentialsMatch=false;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return userCredentialsMatch;
	}

	
	public void setUserCredentialsMatch(boolean userCredentialsMatch) {
		this.userCredentialsMatch = userCredentialsMatch;
	}

	
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	
	public User getUser() {
		return user;
	}

	
	public void setUser(User user) {
		this.user = user;
	}
	
}
