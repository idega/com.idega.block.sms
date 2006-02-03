package com.idega.block.sms.business;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import com.idega.core.contact.data.Phone;
import com.idega.core.contact.data.PhoneHome;
import com.idega.core.contact.data.PhoneType;
import com.idega.data.IDOLookup;
import com.idega.presentation.IWContext;
import com.idega.user.data.User;

/**
 * <p>
 * Backing bean for the SMSAuthenticator UI Component.
 * </p>
 *  Last modified: $Date: 2006/02/03 01:31:49 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class SMSAuthenticationSettingsBean implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -3309158076980079023L;

	public static final String BEAN_ID="SMSAuthenticationSettingsBean";
	
	//private User user;
	private String password;
	private String confirmPassword;
	private String mobileNumber;
	private boolean allowSMSAuthenticaton=false;

	public SMSAuthenticationSettingsBean(){
		load();
	}
	
	
	public User getUser() {
		User user = null;
		//if(user==null){
			IWContext iwc = IWContext.getInstance();
			user=iwc.getCurrentUser();
		//}
		return user;
	}
	
	
	public void load(){
		try {
				Collection phones = getUser().getPhones(Integer.toString(PhoneType.MOBILE_PHONE_ID));
				for (Iterator iter = phones.iterator(); iter.hasNext();) {
					Phone phone = (Phone) iter.next();
					mobileNumber = phone.getNumber();
					if(mobileNumber!=null&&!mobileNumber.equals("")){
						setAllowSMSAuthenticaton(true);
					}
				}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save(){
		if(getPassword().equals(getConfirmPassword())){
			try {
					Collection phones = getUser().getPhones(Integer.toString(PhoneType.MOBILE_PHONE_ID));
					boolean hadPhone=false;
					for (Iterator iter = phones.iterator(); iter.hasNext();) {
						Phone phone = (Phone) iter.next();
						phone.setNumber(getMobileNumber());
						phone.store();
						hadPhone=true;
					}
					
					if(!hadPhone){
						String mobile = getMobileNumber();
						if(mobile!=null&&!mobile.equals("")){
							//create the new phone
							PhoneHome pHome = (PhoneHome)IDOLookup.getHome(Phone.class);
							Phone phone = pHome.create();
							phone.setNumber(mobile);
							phone.setPhoneTypeId(PhoneType.MOBILE_PHONE_ID);
							phone.store();
							getUser().addPhone(phone);
						}
					}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public void reload(){
		//user=null;
		this.password=null;
		confirmPassword=null;
		mobileNumber=null;
		load();
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}


	
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	
	public String getPassword() {
		return password;
	}


	
	public void setPassword(String password) {
		this.password = password;
	}


	
	public String getConfirmPassword() {
		return confirmPassword;
	}


	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	

	public boolean isAllowSMSAuthenticaton() {
		return allowSMSAuthenticaton;
	}

	public void setAllowSMSAuthenticaton(boolean allowSMSAuthenticaton) {
		this.allowSMSAuthenticaton = allowSMSAuthenticaton;
	}
	
}
