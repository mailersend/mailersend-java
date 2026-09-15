/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * An activity event of a single email
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailActivity {

    /** The id of the event, can be passed to ms.activities().getSingleActivity() for the full event */
    @SerializedName("id")
    public String id;

    /** The event type, check com.mailersend.sdk.util.EventTypes for the possible values */
    @SerializedName("type")
    public String type;

    /** Only present on suppressed events, one of on_hold, hard_bounced, unsubscribed, spam_complained or blocklisted */
    @SerializedName("suppression_reason")
    public String suppressionReason;

    public Date createdAt;

    @SerializedName("created_at")
    private String createdAtString;


    /**
     * Converts the retrieved dates to java.util.Date
     */
    protected void parseDates() {

        createdAt = EmailDates.parse(createdAtString);
    }
}
