/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>Email class.</p>
 *
 * @author john
 * @version $Id: $Id
 */
public class Email {
    
    @SerializedName("to")
    public ArrayList<Recipient> recipients = new ArrayList<Recipient>();

    @SerializedName("from")
    public Recipient from;

    @SerializedName("cc")
    public ArrayList<Recipient> cc = new ArrayList<Recipient>();

    @SerializedName("bcc")
    public ArrayList<Recipient> bcc = new ArrayList<Recipient>();
    
    @SerializedName("reply_to")
    public Recipient replyTo;

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
    
    public Date sendAt;
        
    // keeps the global personalizations
    protected transient HashMap<String, Object> allRecipientsPersonalization = new HashMap<String, Object>();
    
    // keeps the global variable substitutions
    protected transient HashMap<String, String> allRecipientsSubstitutions = new HashMap<String, String>();
    
    @SerializedName("send_at")
    protected String sendAtStamp;
    
    @SerializedName("in_reply_to")
    public String inReplyTo;
    
    /**
     * Adds a recipient to the email
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void addRecipient(String name, String email) {
        
        Recipient recipient = new Recipient(name, email);
        this.recipients.add(recipient);
    }
    
    
    /**
     * Adds a recipient
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     */
    public void AddRecipient(Recipient recipient) {
        
        this.recipients.add(recipient);
    }
    
    
    /**
     * Adds multiple recipients to the email
     *
     * @param recipients an array of {@link com.mailersend.sdk.Recipient} objects.
     */
    public void AddRecipients(Recipient[] recipients) {
        
        this.recipients.addAll( Arrays.asList( recipients ) );
    }
    
    
    /**
     * Adds a carbon copy recipient to the email
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void AddCc(String name, String email) {
        
        Recipient recipient = new Recipient(name, email);
        this.cc.add(recipient);
    }
    
    
    /**
     * Adds a carbon copy recipient to the email
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     */
    public void AddCc(Recipient recipient) {
        
        this.cc.add(recipient);
    }
    
    
    /**
     * Adds a blind carbon copy recipient to the email
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void AddBcc(String name, String email) {
        
        Recipient recipient = new Recipient(name, email);
        this.bcc.add(recipient);
    }
    
    
    /**
     * Adds a blind carbon copy recipient to the email
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     */
    public void AddBcc(Recipient recipient) {
        
        this.bcc.add(recipient);
    }
    
    
    /**
     * Sets the reply to parameter
     *
     * @param replyTo a {@link com.mailersend.sdk.Recipient} object.
     */
    public void AddReplyTo(Recipient replyTo) {
        
        this.replyTo = replyTo;
    }
    
    
    /**
     * Sets the reply to parameter
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void AddReplyTo(String name, String email) {
        
        this.replyTo = new Recipient(name, email);
    }
    
    
    /**
     * Sets the email from
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void setFrom(String name, String email) {
        
        Recipient recipient = new Recipient(name, email);
        this.from = recipient;
    }
    
    
    /**
     * Sets the email's subject
     *
     * @param subject a {@link java.lang.String} object.
     */
    public void setSubject(String subject) {
        
        this.subject = subject;
    }
    
    
    /**
     * Sets the email's HTML body
     *
     * @param html a {@link java.lang.String} object.
     */
    public void setHtml(String html) {
        
        this.html = html;
    }
    
    
    /**
     * Sets the email's plain text bofy
     *
     * @param plain a {@link java.lang.String} object.
     */
    public void setPlain(String plain) {
        
        this.text = plain;
    }
    
    
    /**
     * Sets the email's template id
     *
     * @param templateId a {@link java.lang.String} object.
     */
    public void setTemplateId(String templateId) {
        
        this.templateId = templateId;
    }
    
    
    /**
     * Adds a personalization for the given recipient
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
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
     * Set the send at date
     *
     * @param sendAt a {@link java.util.Date} object.
     */
    public void setSendAt(Date sendAt) {
    	this.sendAt = sendAt;
    }
    
    /**
     * Set in reply to
     *
     * @param inReplyTo a {@link java.lang.String} object.
     */
    public void setInReplyTo(String inReplyTo) {
    	this.inReplyTo = inReplyTo;
    }
    
    
    /**
     * Adds personalization to all recipients
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.Object} object.
     */
    public void addPersonalization(String name, Object value) {
        
        this.allRecipientsPersonalization.put(name, value);
    }
    
    
    /**
     * Adds a variable for the given recipient
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     * @param variable a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
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
            this.templateVariables.add(var);
        }
    }
    
    
    /**
     * Adds a variable to all recipients
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public void AddVariable(String name, String value) {
        
        this.allRecipientsSubstitutions.put(name, value);
    }
    
    
    /**
     * Add a tag to the email
     *
     * @param tag a {@link java.lang.String} object.
     */
    public void AddTag(String tag) {
        
        tags.add(tag);
    }
    
    
    /**
     * Attach a file to the email
     *
     * @param path a {@link java.lang.String} object.
     * @throws java.io.IOException
     */
    public void attachFile(String path) throws IOException {
    
        Attachment attachment = new Attachment();
        attachment.AddAttachmentFromFile(path);
        
        this.attachments.add(attachment);
    }
    
    /**
     * <p>attachFile.</p>
     *
     * @param file a {@link java.io.File} object.
     * @throws java.io.IOException if any.
     */
    public void attachFile(File file) throws IOException {
        
        attachFile(file.getAbsolutePath());
    }
    
    
    /**
     * Adds each entry of the allRecipientsPersonalization hash map as a personalization for each recipient
     */
    protected void preparePersonalizationForAllRecipients() {
        
        for (Recipient recipient : this.recipients) {
            
            for (String name : allRecipientsPersonalization.keySet()) {
                
                addPersonalization(recipient, name, allRecipientsPersonalization.get(name));
            }
        }
    }
    
    
    /**
     * Adds each entry of the allRecipientsSubstitutions hash map as a substitution for each recipient
     */
    protected void prepareSubstitutionsForAllRecipients() {
        
        for (Recipient recipient : this.recipients) {
            
            for (String name : allRecipientsSubstitutions.keySet()) {
                
                addPersonalization(recipient, name, allRecipientsSubstitutions.get(name));
            }
        }
    }
    
    
    /**
     * Prepares the email for sending and returns it as a serialized JSON string
     *
     * @return String
     */
    public String serializeForSending() {
            
        preparePersonalizationForAllRecipients();
        prepareSubstitutionsForAllRecipients();
        
        if (sendAt != null) {
        	sendAtStamp = String.valueOf(sendAt.getTime() / 1000);
        }
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter().nullSafe())
                .create();
        
        String json = gson.toJson(this);

        return json;
    }

    /**
    * Simple adapter for {@link LocalDate} type in Gson serialization.
    *
    * To use this {@link TypeAdapter}, register it into a {@link Gson} serializer using a
    * {@link GsonBuilder}.
    */
    class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
          out.value(value.toString());
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
          return LocalDate.parse(in.nextString());
        }
    }
}
