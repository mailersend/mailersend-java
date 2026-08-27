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

/**
 * A single email as returned by the emails list endpoint
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailListItem {

    @SerializedName("id")
    public String id;

    /** The sender's email address */
    @SerializedName("from")
    public String from;

    /** The recipient's email address */
    @SerializedName("to")
    public String to;

    @SerializedName("subject")
    public String subject;

    /** Always null in a list item, use ms.emails().getEmail() to get the content of an email */
    @SerializedName("text")
    public String text;

    /** Always null in a list item, use ms.emails().getEmail() to get the content of an email */
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

    /** The tags the email was sent with, null when it was sent without tags */
    @SerializedName("tags")
    public String[] tags;

    /** Any of opened, clicked, unsubscribed or complained recorded for the email, empty when there was no interaction */
    @SerializedName("interaction")
    public String[] interaction;

    /** Only set when the status is rejected, null otherwise */
    @SerializedName("suppression_reason")
    public String suppressionReason;

    public Date createdAt;

    public Date updatedAt;

    /** The custom headers the email was sent with, null when there were none */
    @SerializedName("headers")
    public HashMap<String, String> headers;

    @SerializedName("created_at")
    private String createdAtString;

    @SerializedName("updated_at")
    private String updatedAtString;


    /**
     * Converts the retrieved dates to java.util.Date
     */
    protected void parseDates() {

        createdAt = EmailDates.parse(createdAtString);
        updatedAt = EmailDates.parse(updatedAtString);
    }
}
