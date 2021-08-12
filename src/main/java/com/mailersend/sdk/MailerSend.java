/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.net.http.HttpClient;

import com.google.gson.Gson;
import com.mailersend.sdk.exceptions.MailerSendResponseError;
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
     * @throws MailerSendResponseError 
     */
    public void Send(Email email) throws MailerSendResponseError {
        
        String json = email.serializeForSending();
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(this.token);
        
        api.postRequest("/email", json, MailerSendResponseError.class);
    }
}
