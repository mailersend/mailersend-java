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
import com.mailersend.sdk.inboundroutes.InboundRoutes;
import com.mailersend.sdk.messages.Messages;
import com.mailersend.sdk.recipients.Recipients;
import com.mailersend.sdk.scheduledmessages.ScheduledMessages;
import com.mailersend.sdk.sms.Sms;
import com.mailersend.sdk.templates.Templates;
import com.mailersend.sdk.tokens.Tokens;
import com.mailersend.sdk.webhooks.Webhooks;
import com.mailsend.sdk.emailverification.EmailVerification;

/**
 * Main SDK Class
 *
 * @author john
 * @version $Id: $Id
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
    private InboundRoutes inboundRoutes = null;
    private ScheduledMessages scheduledMessages = null;
    private EmailVerification emailVerification = null;
    private Sms sms = null;
    
    /**
     * <p>Constructor for MailerSend.</p>
     */
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
        inboundRoutes = new InboundRoutes(this);
        scheduledMessages = new ScheduledMessages(this);
        emailVerification = new EmailVerification(this);
        sms = new Sms(this);
    }
    
    
    /**
     * Get the emails access object
     *
     * @return the Emails object
     */
    public Emails emails() {
        
        return emails;
    }
    
    
    /**
     * Get the activities access object
     *
     * @return the Activities object
     */
    public Activities activities() {
        
        return activities;
    }
    
    
    /**
     * Get the analytics access object
     *
     * @return the Analytics object
     */
    public Analytics analytics() {
        
        return analytics;
    }
    
    
    /**
     * Get the domains access object
     *
     * @return the Domains object
     */
    public Domains domains() {
        
        return domains;
    }
    
    
    /**
     * Get the messages access object
     *
     * @return the Messages object
     */
    public Messages messages() {
        
        return messages;
    }
    
    /**
     * Get the scheduled messages access object
     *
     * @return the Scheduled Messages object
     */
    public ScheduledMessages scheduledMessages() {
    	return scheduledMessages;
    }
    
    
    /**
     * Get the recipients access object
     *
     * @return the Recipients Object
     */
    public Recipients recipients() {
        
        return recipients;
    }
    

    /**
     * Get the tokens access object
     *
     * @return the Tokens object
     */
    public Tokens tokens() {
        
        return tokens;
    }
    
    
    /**
     * Get the webhooks access object
     *
     * @return the Webhooks object
     */
    public Webhooks webhooks() {
        
        return webhooks;
    }
    
    
    /**
     * Get the templates access object
     *
     * @return the Templates object
     */
    public Templates templates() {
        
        return templates;
    }
    
    
    /**
     * Get the inbound routes access object
     *
     * @return The Inbound Routes object
     */
    public InboundRoutes inboundRoutes() {
    	
    	return inboundRoutes;
    }
    
    /**
     * Get the email verification access object
     *
     * @return the Email Verification object
     */
    public EmailVerification emailVerification() {
    	
    	return emailVerification;
    }
    
    /**
     * Get the sms access object
     *
     * @return The SMS object
     */
    public Sms sms() {
    	return sms;
    }
    
    /**
     * Sets the MailerSend token
     *
     * @param token your MailerSend token
     */
    public void setToken(String token) {
        
        this.token = token;
    }
    
    
    /**
     * Returns the MailerSend token
     *
     * @return the MailerSend token
     */
    public String getToken() {
        
        return this.token;
    }
    
}
