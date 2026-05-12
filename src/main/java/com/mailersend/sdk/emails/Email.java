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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @SerializedName("rcpt_to")
    public ArrayList<Recipient> rcptTo = new ArrayList<Recipient>();

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

    @SerializedName("list_unsubscribe")
    public String listUnsubscribe;

    @SerializedName("settings")
    public EmailSettings settings;

    @SerializedName("headers")
    public ArrayList<EmailHeader> headers = new ArrayList<EmailHeader>();

    @SerializedName("references")
    public ArrayList<String> references = new ArrayList<String>();

    @SerializedName("precedence_bulk")
    public Boolean precedenceBulk;
    
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
     * Adds a recipient to the email
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     */
    public void addRecipient(Recipient recipient) {

        this.recipients.add(recipient);
    }


    /**
     * Adds a recipient
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     * @deprecated Use {@link #addRecipient(Recipient)} instead.
     */
    public void AddRecipient(Recipient recipient) {

        addRecipient(recipient);
    }


    /**
     * Adds multiple recipients to the email
     *
     * @param recipients an array of {@link com.mailersend.sdk.Recipient} objects.
     */
    public void addRecipients(Recipient[] recipients) {

        this.recipients.addAll( Arrays.asList( recipients ) );
    }


    /**
     * Adds multiple recipients to the email
     *
     * @param recipients an array of {@link com.mailersend.sdk.Recipient} objects.
     * @deprecated Use {@link #addRecipients(Recipient[])} instead.
     */
    public void AddRecipients(Recipient[] recipients) {

        addRecipients(recipients);
    }


    /**
     * Adds a carbon copy recipient to the email
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void addCc(String name, String email) {

        Recipient recipient = new Recipient(name, email);
        this.cc.add(recipient);
    }


    /**
     * Adds a carbon copy recipient to the email
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     */
    public void addCc(Recipient recipient) {

        this.cc.add(recipient);
    }


    /**
     * Adds a carbon copy recipient to the email
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     * @deprecated Use {@link #addCc(String, String)} instead.
     */
    public void AddCc(String name, String email) {

        addCc(name, email);
    }


    /**
     * Adds a carbon copy recipient to the email
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     * @deprecated Use {@link #addCc(Recipient)} instead.
     */
    public void AddCc(Recipient recipient) {

        addCc(recipient);
    }


    /**
     * Adds a blind carbon copy recipient to the email
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void addBcc(String name, String email) {

        Recipient recipient = new Recipient(name, email);
        this.bcc.add(recipient);
    }


    /**
     * Adds a blind carbon copy recipient to the email
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     */
    public void addBcc(Recipient recipient) {

        this.bcc.add(recipient);
    }


    /**
     * Adds a blind carbon copy recipient to the email
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     * @deprecated Use {@link #addBcc(String, String)} instead.
     */
    public void AddBcc(String name, String email) {

        addBcc(name, email);
    }


    /**
     * Adds a blind carbon copy recipient to the email
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     * @deprecated Use {@link #addBcc(Recipient)} instead.
     */
    public void AddBcc(Recipient recipient) {

        addBcc(recipient);
    }


    /**
     * Sets the reply to parameter
     *
     * @param replyTo a {@link com.mailersend.sdk.Recipient} object.
     */
    public void addReplyTo(Recipient replyTo) {

        this.replyTo = replyTo;
    }


    /**
     * Sets the reply to parameter
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void addReplyTo(String name, String email) {

        this.replyTo = new Recipient(name, email);
    }


    /**
     * Sets the reply to parameter
     *
     * @param replyTo a {@link com.mailersend.sdk.Recipient} object.
     * @deprecated Use {@link #addReplyTo(Recipient)} instead.
     */
    public void AddReplyTo(Recipient replyTo) {

        addReplyTo(replyTo);
    }


    /**
     * Sets the reply to parameter
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     * @deprecated Use {@link #addReplyTo(String, String)} instead.
     */
    public void AddReplyTo(String name, String email) {

        addReplyTo(name, email);
    }


    /**
     * Adds an RCPT TO recipient for SMTP relay delivery.
     * When {@code to} is empty and {@code rcptTo} is provided, addresses are forwarded as BCC.
     *
     * @param name a {@link java.lang.String} object.
     * @param email a {@link java.lang.String} object.
     */
    public void addRcptTo(String name, String email) {

        Recipient recipient = new Recipient(name, email);
        this.rcptTo.add(recipient);
    }


    /**
     * Adds an RCPT TO recipient for SMTP relay delivery.
     * When {@code to} is empty and {@code rcptTo} is provided, addresses are forwarded as BCC.
     *
     * @param recipient a {@link com.mailersend.sdk.Recipient} object.
     */
    public void addRcptTo(Recipient recipient) {

        this.rcptTo.add(recipient);
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
     * Sets the list unsubscribe parameter
     * Accepts a single value that complies with RFC 8058
     * Note: This feature is available to Professional and Enterprise accounts only
     *
     * @param listUnsubscribe a {@link java.lang.String} object.
     */
    public void setListUnsubscribe(String listUnsubscribe) {
    	this.listUnsubscribe = listUnsubscribe;
    }

    /**
     * Sets the email tracking settings
     *
     * @param settings a {@link com.mailersend.sdk.emails.EmailSettings} object.
     */
    public void setSettings(EmailSettings settings) {
        this.settings = settings;
    }

    /**
     * Adds a custom email header
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public void addHeader(String name, String value) {
        this.headers.add(new EmailHeader(name, value));
    }

    /**
     * Adds a reference to the email
     *
     * @param reference a {@link java.lang.String} object.
     */
    public void addReference(String reference) {
        this.references.add(reference);
    }

    /**
     * Sets the precedence bulk flag
     *
     * @param precedenceBulk a boolean.
     */
    public void setPrecedenceBulk(boolean precedenceBulk) {
        this.precedenceBulk = precedenceBulk;
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
     * Adds a variable to all recipients
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public void addVariable(String name, String value) {

        this.allRecipientsSubstitutions.put(name, value);
    }


    /**
     * Adds a variable to all recipients
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @deprecated Use {@link #addVariable(String, String)} instead.
     */
    public void AddVariable(String name, String value) {

        addVariable(name, value);
    }


    /**
     * Add a tag to the email
     *
     * @param tag a {@link java.lang.String} object.
     */
    public void addTag(String tag) {

        tags.add(tag);
    }


    /**
     * Add a tag to the email
     *
     * @param tag a {@link java.lang.String} object.
     * @deprecated Use {@link #addTag(String)} instead.
     */
    public void AddTag(String tag) {

        addTag(tag);
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
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter().nullSafe())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter().nullSafe())
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

    /**
     * Simple adapter for {@link LocalDateTime} type in Gson serialization.
     *
     * To use this {@link TypeAdapter}, register it into a {@link Gson} serializer using a
     * {@link GsonBuilder}.
     */
    class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            out.value(value.toString());
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            return LocalDateTime.parse(in.nextString());
        }
    }

    /**
    * Simple adapter for {@link Instant} type in Gson serialization.
    *
    * To use this {@link Instant}, register it into a {@link Gson} serializer using a
    * {@link GsonBuilder}.
    */
    class InstantTypeAdapter extends TypeAdapter<Instant> {

        @Override
        public void write(JsonWriter out, Instant value) throws IOException {
          out.value(value.toString());
        }

        @Override
        public Instant read(JsonReader in) throws IOException {
          return Instant.parse(in.nextString());
        }
    }
}
