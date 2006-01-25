/*
 * $Id: SMSAuthenticator.java,v 1.1 2006/01/25 14:27:54 tryggvil Exp $
 * Created on 23.1.2006 in project com.idega.block.sms
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.sms.presentation;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import com.idega.presentation.IWBaseComponent;


/**
 * <p>
 * TODO tryggvil Describe Type SMSAuthenticator
 * </p>
 *  Last modified: $Date: 2006/01/25 14:27:54 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class SMSAuthenticator extends IWBaseComponent {

	/**
	 * 
	 */
	public SMSAuthenticator() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.idega.presentation.IWBaseComponent#initializeComponent(javax.faces.context.FacesContext)
	 */
	protected void initializeComponent(FacesContext context) {
		//super.initializeComponent(context);
		UIComponent container = this;
		if(includeForm()){
			HtmlForm form = new HtmlForm();
			container.getChildren().add(form);
			container=form;
		}
		
		
	}

	/**
	 * <p>
	 * TODO tryggvil describe method includeForm
	 * </p>
	 * @return
	 */
	private boolean includeForm() {
		// TODO Auto-generated method stub
		return true;
	}
}
