/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * Main SDK Class
 */
public class MailerSend {

    private String token;
    private Recipient defaultFrom = null;
      
    
    /**
     * Sets the MailerSend token
     * @param token
     */
    public void setToken(String token) {
        
        this.token = token;
    }
    
    
    /**
     * Sets the default from
     * @param from
     */
    public void setDefaultFrom(Recipient from) {
        
        this.defaultFrom = from;
    }
    
    
    /**
     * Creates a new email
     * @return
     */
    public Email createEmail() {
        
        Email newEmail = new Email();
        newEmail.from = this.defaultFrom;
        
        return newEmail;
    }
    
    
    /**
     * Sends the given email
     * @param email
     */
    public void Send(Email email) {
        
        
       
    }
}
