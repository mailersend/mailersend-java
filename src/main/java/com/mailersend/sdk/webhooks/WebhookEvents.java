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
    
    /** Constant <code>ACTIVITY_OPENED="activity.opened"</code> */
    public static String ACTIVITY_OPENED = "activity.opened";
    
    /** Constant <code>ACTIVITY_CLICKED="activity.clicked"</code> */
    public static String ACTIVITY_CLICKED = "activity.clicked";
    
    /** Constant <code>ACTIVITY_UNSUBSCRIBED="activity.unsubscribed"</code> */
    public static String ACTIVITY_UNSUBSCRIBED = "activity.unsubscribed";
    
    /** Constant <code>ACTIVITY_SPAM_COMPLAINT="activity.spam_compaint"</code> */
    public static String ACTIVITY_SPAM_COMPLAINT = "activity.spam_compaint";
    
    /** Constant <code>events</code> */
    protected static String[] events = {
            "activity.sent",
            "activity.delivered",
            "activity.soft_bounced",
            "activity.hard_bounced",
            "activity.opened",
            "activity.clicked",
            "activity.unsubscribed",
            "activity.spam_complaint"
    };
}
