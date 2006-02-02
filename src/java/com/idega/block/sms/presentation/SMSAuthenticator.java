/*
 * $Id: SMSAuthenticator.java,v 1.2 2006/02/02 13:15:41 tryggvil Exp $
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
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlMessages;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;
import com.idega.block.sms.business.PersonalIdValidator;
import com.idega.block.sms.business.SMSAuthenticationBean;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Page;
import com.idega.presentation.PresentationObjectTransitional;
import com.idega.presentation.ui.Label;
import com.idega.util.FacesUtil;


/**
 * <p>
 * User Interface component for generating a temporary password, sending it 
 * by SMS to the users registered mobile phone number and authenticating
 * the user into the system if it is valid.
 * </p>
 *  Last modified: $Date: 2006/02/02 13:15:41 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.2 $
 */
public class SMSAuthenticator extends PresentationObjectTransitional implements ActionListener {

	public static final String IW_BUNDLE_IDENTIFIER="com.idega.block.sms";
	
	public static final String ACTION_GENERATE_PASSWORD="GENERATE_PASSWORD";
	public static final String ACTION_LOGIN="LOGIN";
	
	public static final String VIEW_ENTER_PERSONALID="ENTER_PERSONALID";
	public static final String VIEW_ENTER_GENERATED_PASSWORD="ENTER_GENPASSWORD";
	public static final String VIEW_LOGIN_SUCCESSFUL="LOGIN_SUCCESSFUL";
	
	private String showView=VIEW_ENTER_PERSONALID;
	private String redirectUrlOnLogon=null;
	
	
	public String getRedirectUrlOnLogon() {
		return redirectUrlOnLogon;
	}

	
	public void setRedirectUrlOnLogon(String redirectUrlOnLogon) {
		this.redirectUrlOnLogon = redirectUrlOnLogon;
	}

	/**
	 * 
	 */
	public SMSAuthenticator() {
		super();
		setId("smsauthenticator");
		setID("smsauthenticator");
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.IWBaseComponent#initializeComponent(javax.faces.context.FacesContext)
	 */
	protected void initializeComponent(FacesContext context) {
		//super.initializeComponent(context);
		IWContext iwc = IWContext.getIWContext(context);
		
		IWBundle iwb = iwc.getIWMainApplication().getBundle(IW_BUNDLE_IDENTIFIER);
		IWResourceBundle iwrb = iwb.getResourceBundle(iwc);
		UIComponent container = this;

		Layer enterPidContainer = new Layer();
		enterPidContainer.setStyleClass(getStyleClass());
		getFacets().put(VIEW_ENTER_PERSONALID,enterPidContainer);

		HtmlForm form = new HtmlForm();
		enterPidContainer.getChildren().add(form);
		
		container = form;
		
		//if(includeForm()){
		//}
		
		HtmlMessages messages = new HtmlMessages();
		add(messages);
			
		Label personalIdLabel = new Label();
		HtmlOutputText personalIdText = iwb.getLocalizedText("personalId");
		personalIdLabel.getChildren().add(personalIdText);
		HtmlInputText personalIdInput = new HtmlInputText();
		Validator pidValidator = new PersonalIdValidator();
		Validator lengthValidator = new LengthValidator(12);
		personalIdInput.addValidator(pidValidator);
		personalIdInput.addValidator(lengthValidator);
		personalIdInput.setValueBinding("value",context.getApplication().createValueBinding("#{"+SMSAuthenticationBean.BEAN_ID+".userPersonalId}"));
		personalIdLabel.setFor(personalIdInput);
		Layer personalIdDiv = new Layer();
		container.getChildren().add(personalIdDiv);
		personalIdDiv.getChildren().add(personalIdLabel);
		personalIdDiv.getChildren().add(personalIdInput);
	
		Label passwordLabel = new Label();
		HtmlOutputText passwordText = iwb.getLocalizedText("password");
		
		passwordLabel.getChildren().add(passwordText);
		HtmlInputText passwordInput = new HtmlInputText();
		passwordInput.setValueBinding("value",context.getApplication().createValueBinding("#{"+SMSAuthenticationBean.BEAN_ID+".password}"));
		
		passwordLabel.setFor(passwordInput);
		Layer passwordDiv = new Layer();
		container.getChildren().add(passwordDiv);
		passwordDiv.getChildren().add(passwordLabel);
		passwordDiv.getChildren().add(passwordInput);
		
		HtmlCommandButton button = new HtmlCommandButton();
		button.setId(ACTION_GENERATE_PASSWORD);
		button.setAction(context.getApplication().createMethodBinding("#{"+SMSAuthenticationBean.BEAN_ID+".generateOneTimePassword}",null));
		button.addActionListener(this);
		button = (HtmlCommandButton) iwb.getLocalizedUIComponent("generate_onetime_password",button);
		
		container.getChildren().add(button);
		

		Layer enterGeneratedPasswordContainer = new Layer();
		enterGeneratedPasswordContainer.setStyleClass(getStyleClass());
		getFacets().put(VIEW_ENTER_GENERATED_PASSWORD,enterGeneratedPasswordContainer);
		
		HtmlForm form2 = new HtmlForm();
		enterGeneratedPasswordContainer.getChildren().add(form2);
		container =form2;
		
		Label generatedPasswordLabel = new Label();
		HtmlOutputText generatedPasswordText = iwb.getLocalizedText("type_in_your_one_time_password");
		
		generatedPasswordLabel.getChildren().add(generatedPasswordText);
		HtmlInputText generatedPasswordInput = new HtmlInputText();
		generatedPasswordInput.setValueBinding("value",context.getApplication().createValueBinding("#{"+SMSAuthenticationBean.BEAN_ID+".userTypedOneTimePassword}"));
		
		passwordLabel.setFor(generatedPasswordInput);
		Layer generatedPasswordDiv = new Layer();
		container.getChildren().add(generatedPasswordDiv);
		generatedPasswordDiv.getChildren().add(generatedPasswordLabel);
		generatedPasswordDiv.getChildren().add(generatedPasswordInput);
		
		HtmlCommandButton loginButton = new HtmlCommandButton();
		loginButton.setId(ACTION_LOGIN);
		//loginButton.setAction(context.getApplication().createMethodBinding("#{"+SMSAuthenticationBean.BEAN_ID+".validateGeneratedPassword}",null));
		loginButton.addActionListener(this);
		loginButton = (HtmlCommandButton) iwb.getLocalizedUIComponent("login",loginButton);
		
		container.getChildren().add(loginButton);
		
	}

	/**
	 * <p>
	 * TODO tryggvil describe method includeForm
	 * </p>
	 * @return
	 */
	private boolean includeForm() {
		return true;
	}

	public void processAction(ActionEvent event) throws AbortProcessingException {
		UIComponent button = event.getComponent();
		SMSAuthenticationBean bean = (SMSAuthenticationBean)FacesUtil.getBeanInstance(SMSAuthenticationBean.BEAN_ID);
		SMSAuthenticator comp = (SMSAuthenticator) button.findComponent("smsauthenticator");
		if(comp==null){
			comp = (SMSAuthenticator) FacesUtil.getFirstParentOfType(button,SMSAuthenticator.class);
		}
		if(button.getId().equals(ACTION_GENERATE_PASSWORD)){
			//setShowView(VIEW_ENTER_GENERATED_PASSWORD);
			if(comp!=null){
				if(bean.isUserCredentialsMatch()){
					comp.setShowView(VIEW_ENTER_GENERATED_PASSWORD);
				}
			}
		}
		else if(button.getId().equals(ACTION_LOGIN)){
			if(bean.validateOneTimePassword()){
				comp.setShowView(VIEW_LOGIN_SUCCESSFUL);
				String url = getRedirectUrlOnLogon();
				if(url!=null){
					Page page = comp.getParentPage();
					page.setToRedirect(url);
				}
			}
		}
		
	}

	public void encodeChildren(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		super.encodeChildren(context);
	
		UIComponent view = getFacet(getShowView());
		renderChild(context, view);
		
	}

	/**
	 * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object values[] = new Object[3];
		values[0] = super.saveState(ctx);
		values[1] = showView;
		values[2] = redirectUrlOnLogon;
		return values;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(ctx, values[0]);
		showView=(String)values[1];
		redirectUrlOnLogon=(String)values[2];
	}

	
	public String getShowView() {
		return showView;
	}

	
	public void setShowView(String showView) {
		this.showView = showView;
	}
}
