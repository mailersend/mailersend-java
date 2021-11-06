/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import com.mailersend.sdk.activities.Activities;
import com.mailersend.sdk.analytics.Analytics;
import com.mailersend.sdk.domains.Domains;
import com.mailersend.sdk.emails.Emails;
import com.mailersend.sdk.messages.Messages;
import com.mailersend.sdk.recipients.Recipients;
import com.mailersend.sdk.templates.Templates;
import com.mailersend.sdk.tokens.Tokens;
import com.mailersend.sdk.webhooks.Webhooks;

/**
 * Main SDK Class
 */
public class MailerSend {    
   
    protected String token;
    
    private Activities activities = null;
    private Analytics analytics = null;
    private Domains domains = null;
    private Messages messages = null;
    private Recipients recipients = null;
    private Tokens tokens = null;
    private Webhooks webhooks = null;
    private Templates templates = null;
    private Emails emails = null;
    
    
    public MailerSend() {
        
        emails = new Emails(this);
        activities = new Activities(this);
        analytics = new Analytics(this);
        domains = new Domains(this);
        messages = new Messages(this);
        recipients = new Recipients(this);
        tokens = new Tokens(this);
        webhooks = new Webhooks(this);
        templates = new Templates(this);
    }
    
    
    /**
     * Get the emails access object
     * @return
     */
    public Emails emails() {
        
        return emails;
    }
    
    
    /**
     * Get the activities access object
     * @return
     */
    public Activities activities() {
        
        return activities;
    }
    
    
    /**
     * Get the analytics access object
     * @return
     */
    public Analytics analytics() {
        
        return analytics;
    }
    
    
    /**
     * Get the domains access object
     * @return
     */
    public Domains domains() {
        
        return domains;
    }
    
    
    /**
     * Get the messages access object
     * @return
     */
    public Messages messages() {
        
        return messages;
    }
    
    
    /**
     * Get the recipients access object
     * @return
     */
    public Recipients recipients() {
        
        return recipients;
    }
    

    /**
     * Get the tokens access object
     * @return
     */
    public Tokens tokens() {
        
        return tokens;
    }
    
    
    /**
     * Get the webhooks access object
     * @return
     */
    public Webhooks webhooks() {
        
        return webhooks;
    }
    
    
    /**
     * Get the templates access object
     * @return
     */
    public Templates templates() {
        
        return templates;
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
    
}
