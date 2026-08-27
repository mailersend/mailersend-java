/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.util.Date;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.ApiRecipient;

/**
 * A single email retrieved from the API, together with its activity events.
 * Returned by ms.emails().getEmail()
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailInfo {

    @SerializedName("id")
    public String id;

    /** The sender's email address */
    @SerializedName("from")
    public String from;

    /** The recipient's email address, also available as recipient.email */
    @SerializedName("to")
    public String to;

    @SerializedName("subject")
    public String subject;

    /** Null when content tracking is disabled for the domain */
    @SerializedName("text")
    public String text;

    /** Null when content tracking is disabled for the domain */
    @SerializedName("html")
    public String html;

    /** The id of the template used, null when no template was used */
    @SerializedName("template_id")
    public String templateId;

    @SerializedName("domain_id")
    public String domainId;

    /** The id of the message that created this email */
    @SerializedName("message_id")
    public String messageId;

    /** The status of the email, one of queued, sent, rejected or delivered */
    @SerializedName("status")
    public String status;

    @SerializedName("tags")
    public String[] tags;

    /** Any of opened, clicked, unsubscribed or complained recorded for the email, empty when there was no interaction */
    @SerializedName("interaction")
    public String[] interaction;

    /** Only set when the status is rejected, null otherwise */
    @SerializedName("suppression_reason")
    public String suppressionReason;

    /** The recipient the email was addressed to */
    @SerializedName("recipient")
    public ApiRecipient recipient;

    /** The custom headers the email was sent with, null when there were none */
    @SerializedName("headers")
    public HashMap<String, String> headers;

    /**
     * The activity events of the email, newest first and capped at the 200 most recent ones.
     * Empty when no events were recorded. Present even when content tracking is disabled
     */
    @SerializedName("activity")
    public EmailActivity[] activity;

    public Date createdAt;

    public Date updatedAt;

    @SerializedName("created_at")
    private String createdAtString;

    @SerializedName("updated_at")
    private String updatedAtString;


    /**
     * Is called to perform any actions after the deserialization of the response
     * to the /email/{email_id} endpoint
     * Do not call directly
     */
    public void postDeserialize() {

        parseDates();

        if (recipient != null) {

            recipient.parseDates();
        }

        if (activity == null) {

            activity = new EmailActivity[0];

            return;
        }

        for (EmailActivity activityItem : activity) {

            activityItem.parseDates();
        }
    }


    /**
     * Converts the retrieved dates to java.util.Date
     */
    private void parseDates() {

        createdAt = EmailDates.parse(createdAtString);
        updatedAt = EmailDates.parse(updatedAtString);
    }
}
