/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.webhooks;

/**
 * <p>WebhookEvents class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WebhookEvents {

    /** Constant <code>ACTIVITY_SENT="activity.sent"</code> */
    public static String ACTIVITY_SENT = "activity.sent";
    
    /** Constant <code>ACTIVITY_DELIVERED="activity.delivered"</code> */
    public static String ACTIVITY_DELIVERED = "activity.delivered";
    
    /** Constant <code>ACTIVITY_SOFT_BOUNCED="activity.soft_bounced"</code> */
    public static String ACTIVITY_SOFT_BOUNCED = "activity.soft_bounced";
    
    /** Constant <code>ACTIVITY_HARD_BOUNCED="activity.hard_bounced"</code> */
    public static String ACTIVITY_HARD_BOUNCED = "activity.hard_bounced";

    /** Constant <code>ACTIVITY_DEFERRED="activity.deferred"</code> */
    public static String ACTIVITY_DEFERRED = "activity.deferred";

    /** Constant <code>ACTIVITY_OPENED="activity.opened"</code> */
    public static String ACTIVITY_OPENED = "activity.opened";

    /** Constant <code>ACTIVITY_OPENED_UNIQUE="activity.opened_unique"</code> */
    public static String ACTIVITY_OPENED_UNIQUE = "activity.opened_unique";
    
    /** Constant <code>ACTIVITY_CLICKED="activity.clicked"</code> */
    public static String ACTIVITY_CLICKED = "activity.clicked";

    /** Constant <code>ACTIVITY_CLICKED_UNIQUE="activity.clicked_unique"</code> */
    public static String ACTIVITY_CLICKED_UNIQUE = "activity.clicked_unique";
    
    /** Constant <code>ACTIVITY_UNSUBSCRIBED="activity.unsubscribed"</code> */
    public static String ACTIVITY_UNSUBSCRIBED = "activity.unsubscribed";
    
    /** Constant <code>ACTIVITY_SPAM_COMPLAINT="activity.spam_complaint"</code> */
    public static String ACTIVITY_SPAM_COMPLAINT = "activity.spam_complaint";

    /** Constant <code>ACTIVITY_SURVEY_OPENED="activity.survey_opened"</code> */
    public static String ACTIVITY_SURVEY_OPENED = "activity.survey_opened";

    /** Constant <code>ACTIVITY_SURVEY_SUBMITTED="activity.survey_submitted"</code> */
    public static String ACTIVITY_SURVEY_SUBMITTED = "activity.survey_submitted";

    /** Constant <code>IDENTITY_VERIFIED="identity.verified"</code> */
    public static String IDENTITY_VERIFIED = "sender_identity.verified";

    /** Constant <code>MAINTENANCE_START="maintenance.start"</code> */
    public static String MAINTENANCE_START = "maintenance.start";

    /** Constant <code>MAINTENANCE_END="maintenance.end"</code> */
    public static String MAINTENANCE_END = "maintenance.end";

    /** Constant <code>INBOUND_FORWARD_FAILED="inbound_forward.failed"</code> */
    public static String INBOUND_FORWARD_FAILED = "inbound_forward.failed";

    /** Constant <code>EMAIL_SINGLE_VERIFIED="email_single.verified"</code> */
    public static String EMAIL_SINGLE_VERIFIED = "email_single.verified";

    /** Constant <code>EMAIL_LIST_VERIFIED="email_list.verified"</code> */
    public static String EMAIL_LIST_VERIFIED = "email_list.verified";

    /** Constant <code>BULK_EMAIL_COMPLETED="bulk_email.completed"</code> */
    public static String BULK_EMAIL_COMPLETED = "bulk_email.completed";

    /** Constant <code>RECIPIENT_ON_HOLD_ADDED="recipient.on_hold_added"</code> */
    public static String RECIPIENT_ON_HOLD_ADDED = "recipient.on_hold_added";

    /** Constant <code>RECIPIENT_ON_HOLD_REMOVED="recipient.on_hold_removed"</code> */
    public static String RECIPIENT_ON_HOLD_REMOVED = "recipient.on_hold_removed";

    /** Constant <code>events</code> */
    protected static String[] events = {
            "activity.sent",
            "activity.delivered",
            "activity.soft_bounced",
            "activity.hard_bounced",
            "activity.deferred",
            "activity.opened",
            "activity.opened_unique",
            "activity.clicked",
            "activity.clicked_unique",
            "activity.unsubscribed",
            "activity.spam_complaint",
            "activity.survey_opened",
            "activity.survey_submitted",
            "sender_identity.verified",
            "maintenance.start",
            "maintenance.end",
            "inbound_forward.failed",
            "email_single.verified",
            "email_list.verified",
            "bulk_email.completed",
            "recipient.on_hold_added",
            "recipient.on_hold_removed"
    };
}
