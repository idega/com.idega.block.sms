/*
 * $Id: PersonalIdValidator.java,v 1.2 2006/03/02 14:53:22 mariso Exp $
 * Created on 2.2.2006 in project com.idega.block.sms
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.sms.business;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import com.idega.block.sms.presentation.SMSAuthenticationSettings;
import com.idega.business.IBOLookup;
import com.idega.idegaweb.IWBundle;
import com.idega.presentation.IWContext;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;


public class PersonalIdValidator implements Validator {

    public PersonalIdValidator()
    {
    }

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value)
        throws ValidatorException
    {
        if(facesContext == null)
            throw new NullPointerException("facesContext");
        if(uiComponent == null)
            throw new NullPointerException("uiComponent");
        if(value == null){
        	 throw new ValidatorException(new FacesMessage("empty pid"));
        }
        
        String sValue = value.toString();
        //if(!GenericValidator.isEmail(value.toString()))
        if(!isPersonalId(sValue))
        {
            Object args[] = {
                value.toString()
            };
            FacesContext ctx = FacesContext.getCurrentInstance();
            IWBundle iwb = IWContext.getIWContext(ctx).getIWMainApplication().getBundle(SMSAuthenticationSettings.IW_BUNDLE_IDENTIFIER);            
            throw new ValidatorException(new FacesMessage(iwb.getLocalizedString("password_not_correct",IWContext.getIWContext(ctx).getCurrentLocale())));
        } else
        {
            return;
        }
    }

    private boolean isPersonalId(String value) 
    {
        try
        {
            UserBusiness userbusiness = (UserBusiness) IBOLookup.getServiceInstance(IWContext.getInstance(),UserBusiness.class);        
            User user = userbusiness.getUser(value.trim());
            return (user!=null);
        } catch (Exception ex)
        {
            return false;
        }
	}

	public static final String VALIDATOR_ID = "se.idega.validator.PersonalId";
    public static final String PID_MESSAGE_ID = "se.idega.validator.PersonalId.INVALID";
}
