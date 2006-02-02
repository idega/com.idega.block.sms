/*
 * $Id: PersonalIdValidator.java,v 1.1 2006/02/02 13:15:41 tryggvil Exp $
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
            //throw new ValidatorException(MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR, "org.apache.myfaces.Email.INVALID", args));
            throw new ValidatorException(new FacesMessage("Pid invalid"));
        } else
        {
            return;
        }
    }

    private boolean isPersonalId(String value) {
    		if(value.equals("")){
    			return false;
    		}
    		else{
    			return true;
    		}
	}

	public static final String VALIDATOR_ID = "se.idega.validator.PersonalId";
    public static final String PID_MESSAGE_ID = "se.idega.validator.PersonalId.INVALID";
}
