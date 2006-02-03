/*
 * $Id: SMSAuthenticationSettings.java,v 1.1 2006/02/03 01:31:49 tryggvil Exp $
 * Created on 23.1.2006 in project com.idega.block.sms
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.sms.presentation;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlMessages;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.apache.myfaces.custom.savestate.UISaveState;
import com.idega.block.sms.business.SMSAuthenticationSettingsBean;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.PresentationObjectTransitional;
import com.idega.presentation.ui.Label;
import com.idega.util.FacesUtil;


/**
 * <p>
 * User Interface component for setting the telephone number and
 * security password for the current user so that he can log in 
 * by a temporary password sent by SMS.
 * </p>
 *  Last modified: $Date: 2006/02/03 01:31:49 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.1 $
 */
public class SMSAuthenticationSettings extends PresentationObjectTransitional implements ActionListener {

	public static final String IW_BUNDLE_IDENTIFIER="com.idega.block.sms";
	public static final String ACTION_SAVE="SAVE";
	
	String formItemStyleClass="formitem";
	String buttonStyleClass="button";
	

	/**
	 * 
	 */
	public SMSAuthenticationSettings() {
		super();
		setId("smsauthenticatonsettings");
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.IWBaseComponent#initializeComponent(javax.faces.context.FacesContext)
	 */
	protected void initializeComponent(FacesContext context) {
		//super.initializeComponent(context);
		IWContext iwc = IWContext.getIWContext(context);
		
		IWBundle iwb = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
		//IWResourceBundle iwrb = iwb.getResourceBundle(iwc);
		UIComponent container = this;

		Layer mainContainer = new Layer();
		mainContainer.setStyleClass(getStyleClass());
		getChildren().add(mainContainer);

		HtmlForm form = new HtmlForm();
		mainContainer.getChildren().add(form);
		container = form;
		
		//if(includeForm()){
		//}
		
		//save the state of the bean because it is request bound
		UISaveState beanSaveState = new UISaveState();
		ValueBinding binding = context.getApplication().createValueBinding("#{"+SMSAuthenticationSettingsBean.BEAN_ID+"}");
		beanSaveState.setId("smsSettingsBeanState");
		beanSaveState.setValueBinding("value",binding);
		add(beanSaveState);
		
		HtmlMessages messages = new HtmlMessages();
		add(messages);
		
		Label allowSMSLabel = new Label();
		HtmlOutputText allowSMSText = iwb.getLocalizedText("allowSMS");
		allowSMSLabel.getChildren().add(allowSMSText);
		HtmlSelectBooleanCheckbox allowSMSInput = new HtmlSelectBooleanCheckbox();
		//Validator lengthValidator = new LengthValidator(12);
		//allowSMSInput.addValidator(lengthValidator);
		allowSMSInput.setValueBinding("value",context.getApplication().createValueBinding("#{"+SMSAuthenticationSettingsBean.BEAN_ID+".allowSMSAuthenticaton}"));
		allowSMSLabel.setFor(allowSMSInput);
		Layer allowSMSDiv = new Layer();
		allowSMSDiv.setStyleClass(formItemStyleClass);
		container.getChildren().add(allowSMSDiv);
		allowSMSDiv.getChildren().add(allowSMSLabel);
		allowSMSDiv.getChildren().add(allowSMSInput);
		
		Label mobileLabel = new Label();
		HtmlOutputText mobileText = iwb.getLocalizedText("mobileNumber");
		mobileLabel.getChildren().add(mobileText);
		HtmlInputText mobileInput = new HtmlInputText();
		//Validator lengthValidator = new LengthValidator(12);
		//mobileInput.addValidator(lengthValidator);
		mobileInput.setValueBinding("value",context.getApplication().createValueBinding("#{"+SMSAuthenticationSettingsBean.BEAN_ID+".mobileNumber}"));
		mobileLabel.setFor(mobileInput);
		Layer mobileDiv = new Layer();
		mobileDiv.setStyleClass(formItemStyleClass);
		container.getChildren().add(mobileDiv);
		mobileDiv.getChildren().add(mobileLabel);
		mobileDiv.getChildren().add(mobileInput);
	
		Label passwordLabel = new Label();
		HtmlOutputText passwordText = iwb.getLocalizedText("password");
		passwordLabel.getChildren().add(passwordText);
		HtmlInputSecret passwordInput = new HtmlInputSecret();
		passwordInput.setValueBinding("value",context.getApplication().createValueBinding("#{"+SMSAuthenticationSettingsBean.BEAN_ID+".password}"));
		passwordLabel.setFor(passwordInput);
		Layer passwordDiv = new Layer();
		passwordDiv.setStyleClass(formItemStyleClass);
		container.getChildren().add(passwordDiv);
		passwordDiv.getChildren().add(passwordLabel);
		passwordDiv.getChildren().add(passwordInput);
		
		Label confirmPasswordLabel = new Label();
		HtmlOutputText confirmPasswordText = iwb.getLocalizedText("confirm_password");
		confirmPasswordLabel.getChildren().add(confirmPasswordText);
		HtmlInputSecret confirmPasswordInput = new HtmlInputSecret();
		confirmPasswordInput.setValueBinding("value",context.getApplication().createValueBinding("#{"+SMSAuthenticationSettingsBean.BEAN_ID+".confirmPassword}"));
		confirmPasswordLabel.setFor(confirmPasswordInput);
		Layer confirmPasswordDiv = new Layer();
		confirmPasswordDiv.setStyleClass(formItemStyleClass);
		container.getChildren().add(confirmPasswordDiv);
		confirmPasswordDiv.getChildren().add(confirmPasswordLabel);
		confirmPasswordDiv.getChildren().add(confirmPasswordInput);
		
		HtmlCommandButton button = new HtmlCommandButton();
		button.setStyleClass(buttonStyleClass);
		button.setId(ACTION_SAVE);
		//button.setAction(context.getApplication().createMethodBinding("#{"+SMSAuthenticationBean.BEAN_ID+".generateOneTimePassword}",null));
		button.addActionListener(this);
		button = (HtmlCommandButton) iwb.getLocalizedUIComponent("save",button);
		
		container.getChildren().add(button);
	
		
	}


	public void processAction(ActionEvent event) throws AbortProcessingException {
		UIComponent button = event.getComponent();
		SMSAuthenticationSettingsBean bean = (SMSAuthenticationSettingsBean)FacesUtil.getBeanInstance(SMSAuthenticationSettingsBean.BEAN_ID);
		SMSAuthenticationSettings comp = (SMSAuthenticationSettings) button.findComponent("smsauthenticationsettings");
		if(comp==null){
			comp = (SMSAuthenticationSettings) FacesUtil.getFirstParentOfType(button,SMSAuthenticationSettings.class);
		}
		if(button.getId().equals(ACTION_SAVE)){
			//setShowView(VIEW_ENTER_GENERATED_PASSWORD);
			if(comp!=null){
			}
			bean.save();
		}
		
	}

	public void encodeChildren(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(context);
		
	}

	/**
	 * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		return super.saveState(ctx);
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		super.restoreState(ctx,state);
	}

	
	public String getButtonStyleClass() {
		return buttonStyleClass;
	}

	
	public void setButtonStyleClass(String buttonStyleClass) {
		this.buttonStyleClass = buttonStyleClass;
	}

	
	public String getFormItemStyleClass() {
		return formItemStyleClass;
	}

	
	public void setFormItemStyleClass(String formItemStyleClass) {
		this.formItemStyleClass = formItemStyleClass;
	}

}
