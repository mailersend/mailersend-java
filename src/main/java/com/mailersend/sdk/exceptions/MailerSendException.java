/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.exceptions;

public class MailerSendException extends Exception {

    public int code;
    
    public MailerSendException(String message) {
        
        super(message);
    }
}
