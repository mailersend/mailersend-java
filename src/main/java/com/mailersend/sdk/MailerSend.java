/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.util.ArrayList;
import java.util.Date;

import com.mailersend.sdk.activities.Activities;
import com.mailersend.sdk.analytics.Analytics;
import com.mailersend.sdk.domains.Domains;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * Main SDK Class
 */
public class MailerSend {

    protected String token;
    private Recipient defaultFrom = null;
      
    
    public Activities activities = null;
    public Analytics analytics = null;
    public Domains domains = null;
    
    public MailerSend() {
        
        activities = new Activities(this);
        analytics = new Analytics(this);
        domains = new Domains(this);
    }
    
    /**
     * Sets the MailerSend token
     * @param token
     */
    public void setToken(String token) {
        
        this.token = token;
    }
    
    
    /**
     * Returns the MailerSend token
     * @return
     */
    public String getToken() {
        
        return this.token;
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
    public MailerSendResponse send(Email email) throws MailerSendException {
        
        String json = email.serializeForSending();
        
        MailerSendApi api = new MailerSendApi();
        api.setToken(this.token);
       
        MailerSendResponse response = api.postRequest("/email", json, MailerSendResponse.class);
        
        return response;
    }
}
