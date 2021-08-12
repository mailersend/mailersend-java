/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.exceptions;

import java.util.HashMap;

public class MailerSendResponseError extends MailerSendException {

    public String message = "";
    public HashMap<String, String[]> errors = new HashMap<String, String[]>();
    
    public MailerSendResponseError(String message) {
        
        super(message);
    }
    
}
