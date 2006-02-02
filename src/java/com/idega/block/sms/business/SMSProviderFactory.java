/*
 * $Id: SMSProviderFactory.java,v 1.1 2006/02/02 13:15:41 tryggvil Exp $
 * Created on 31.1.2006 in project com.idega.block.sms
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.sms.business;

/**
 * <p>
 * Class to get the instance of the SMSProvider.
 * </p>
 *  Last modified: $Date: 2006/02/02 13:15:41 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public class SMSProviderFactory {
	
	public static SMSProvider getDefaultProvider(){
		//TODO: Remove hardcoding
		return new ASPSMSProvider();
	}
	
}
