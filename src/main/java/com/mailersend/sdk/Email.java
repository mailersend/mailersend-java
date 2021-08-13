/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.email.attributes.Attachment;
import com.mailersend.sdk.email.attributes.Personalization;
import com.mailersend.sdk.email.attributes.Substitution;
import com.mailersend.sdk.email.attributes.Variable;

public class Email {

    @SerializedName("to")
    public ArrayList<Recipient> recipients = new ArrayList<Recipient>();

    @SerializedName("from")
    public Recipient from;

    @SerializedName("cc")
    public ArrayList<Recipient> cc = new ArrayList<Recipient>();

    @SerializedName("bcc")
    public ArrayList<Recipient> bcc = new ArrayList<Recipient>();

    @SerializedName("subject")
    public String subject;

    @SerializedName("text")
    public String text;

    @SerializedName("html")
    public String html;

    @SerializedName("template_id")
    public String templateId;

    @SerializedName("tags")
    public ArrayList<String> tags = new ArrayList<String>();
    
    public ArrayList<Attachment> attachments = new ArrayList<Attachment>();

    @SerializedName("variables")
    public ArrayList<Variable> templateVariables = new ArrayList<Variable>();

    @SerializedName("personalization")
    public ArrayList<Personalization> personalization = new ArrayList<Personalization>();
    
    
    // keeps the global personalizations
    protected transient HashMap<String, Object> allRecipientsPersonalization = new HashMap<String, Object>();
    
    // keeps the global variable substitutions
    protected transient HashMap<String, String> allRecipientsSubstitutions = new HashMap<String, String>();
    
    /**
     * Adds a recipient to the email
     * @param name
     * @param email
     */
    public void addRecipient(String name, String email) {
        
        Recipient recipient = new Recipient(name, email);
        this.recipients.add(recipient);
    }
    
    
    /**
     * Adds a recipient
     * @param recipient
     */
    public void AddRecipient(Recipient recipient) {
        
        this.recipients.add(recipient);
    }
    
    
    /**
     * Adds multiple recipients to the email
     * @param recipients
     */
    public void AddRecipients(Recipient[] recipients) {
        
        this.recipients.addAll( Arrays.asList( recipients ) );
    }
    
    
    /**
     * Adds a carbon copy recipient to the email
     * @param name
     * @param email
     */
    public void AddCc(String name, String email) {
        
        Recipient recipient = new Recipient(name, email);
        this.cc.add(recipient);
    }
    
    
    /**
     * Adds a carbon copy recipient to the email
     * @param recipient
     */
    public void AddCc(Recipient recipient) {
        
        this.cc.add(recipient);
    }
    
    
    /**
     * Adds a blind carbon copy recipient to the email
     * @param name
     * @param email
     */
    public void AddBcc(String name, String email) {
        
        Recipient recipient = new Recipient(name, email);
        this.bcc.add(recipient);
    }
    
    
    /**
     * Adds a blind carbon copy recipient to the email
     * @param recipient
     */
    public void AddBcc(Recipient recipient) {
        
        this.bcc.add(recipient);
    }
    
    
    /**
     * Sets the email from
     * @param name
     * @param email
     */
    public void setFrom(String name, String email) {
        
        Recipient recipient = new Recipient(name, email);
        this.from = recipient;
    }
    
    
    /**
     * Sets the email's subject
     * @param subject
     */
    public void setSubject(String subject) {
        
        this.subject = subject;
    }
    
    
    /**
     * Sets the email's HTML body
     * @param html
     */
    public void setHtml(String html) {
        
        this.html = html;
    }
    
    
    /**
     * Sets the email's plain text bofy
     * @param plain
     */
    public void setPlain(String plain) {
        
        this.text = plain;
    }
    
    
    /**
     * Sets the email's template id
     * @param templateId
     */
    public void setTemplateId(String templateId) {
        
        this.templateId = templateId;
    }
    
    
    /**
     * Adds a personalization for the given recipient
     * @param recipient
     * @param name
     * @param value
     */
    public void addPersonalization(Recipient recipient, String name, Object value) {
        
        // check if there is already a personalization for this recipient
        Personalization personalizationEntry = null;
        
        for (Personalization p : this.personalization) {
            
            if (p.email.equals(recipient.email)) {
                
                personalizationEntry = p;
                break;
            }
        }
        
        if (personalizationEntry != null) {
            
            // add the value to the existing personalization
            personalizationEntry.data.put(name, value);
        } else {
            
            // if the personalization doesn't exist, create it
            
            personalizationEntry = new Personalization();
            personalizationEntry.email = recipient.email;
            personalizationEntry.data.put(name, value);
            this.personalization.add(personalizationEntry);
        }
    }
    
    
    /**
     * Adds personalization to all recipients
     * @param name
     * @param value
     */
    public void addPersonalization(String name, Object value) {
        
        this.allRecipientsPersonalization.put(name, value);
    }
    
    
    /**
     * Adds a variable for the given recipient
     * @param recipient
     * @param variable
     * @param value
     */
    public void AddVariable(Recipient recipient, String variable, String value) {
        
        // check if there is already a variable object for this recipient
        Variable var = null;
        for (Variable v : this.templateVariables) {
            
            if (v.email.equals(recipient.email)) {
                
                var = v;
                break;
            }
        }
        
        if (var != null) {
            
            // add the substitution to the existing variable
            var.addSubstitution(new Substitution(variable, value));
        } else {
            
            // if the variable doesn't exist, create it
            var = new Variable();
            var.email = recipient.email;
            var.addSubstitution(new Substitution(variable, value));
        }
    }
    
    
    /**
     * Adds a variable to all recipients
     * @param name
     * @param value
     */
    public void AddVariable(String name, String value) {
        
        this.allRecipientsSubstitutions.put(name, value);
    }
    
    
    /**
     * Add a tag to the email
     * @param tag
     */
    public void AddTag(String tag) {
        
        tags.add(tag);
    }
    
    
    /**
     * Attach a file to the email
     * @param path
     * @throws IOException
     */
    public void attachFile(String path) throws IOException {
    
        Attachment attachment = new Attachment();
        attachment.AddAttachmentFromFile(path);
        
        this.attachments.add(attachment);
    }
    
    public void attachFile(File file) throws IOException {
        
        attachFile(file.getAbsolutePath());
    }
    
    
    /**
     * Adds each entry of the allRecipientsPersonalization hash map as a personalization for each recipient
     */
    private void preparePersonalizationForAllRecipients() {
        
        for (Recipient recipient : this.recipients) {
            
            for (String name : allRecipientsPersonalization.keySet()) {
                
                addPersonalization(recipient, name, allRecipientsPersonalization.get(name));
            }
        }
    }
    
    
    /**
     * Adds each entry of the allRecipientsSubstitutions hash map as a substitution for each recipient
     */
    private void prepareSubstitutionsForAllRecipients() {
        
        for (Recipient recipient : this.recipients) {
            
            for (String name : allRecipientsSubstitutions.keySet()) {
                
                addPersonalization(recipient, name, allRecipientsSubstitutions.get(name));
            }
        }
    }
    
    
    /**
     * Prepares the email for sending and returns it as a serialized JSON string
     * @return String
     */
    protected String serializeForSending() {
            
        preparePersonalizationForAllRecipients();
        prepareSubstitutionsForAllRecipients();
        
        Gson gson = new Gson();
        String json = gson.toJson(this);

        return json;
    }
}
