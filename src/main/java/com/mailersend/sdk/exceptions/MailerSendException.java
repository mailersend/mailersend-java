/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.exceptions;

import java.util.HashMap;

/**
 * Is thrown when an error is returned from the MailerSend API
 */
public class MailerSendException extends Exception {

    public int code; // the response code
    
    public String message = "";
    
    public String responseBody = null;
    
    public HashMap<String, String[]> errors = new HashMap<String, String[]>();
    
    public MailerSendException(String message) {
        
        super(message);
    }

}
