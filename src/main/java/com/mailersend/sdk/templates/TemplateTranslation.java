/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.DateParser;

/**
 * <p>TemplateTranslation class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class TemplateTranslation {

    @SerializedName("language")
    public String language;

    @SerializedName("language_name")
    public String languageName;

    @SerializedName("status")
    public String status;

    @SerializedName("total_email_sent")
    public int totalEmailSent;

    public Date lastEmailSentAt;

    @SerializedName("last_email_sent_at")
    private String lastEmailSentAtStr;

    public Date translatedAt;

    @SerializedName("translated_at")
    private String translatedAtStr;

    public Date createdAt;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date updatedAt;

    @SerializedName("updated_at")
    private String updatedAtStr;


    /**
     * Is called to perform any actions after the deserialization of the response
     * Do not call directly
     */
    protected void postDeserialize() {

        lastEmailSentAt = DateParser.parse(lastEmailSentAtStr);
        translatedAt = DateParser.parse(translatedAtStr);
        createdAt = DateParser.parse(createdAtStr);
        updatedAt = DateParser.parse(updatedAtStr);
    }
}
