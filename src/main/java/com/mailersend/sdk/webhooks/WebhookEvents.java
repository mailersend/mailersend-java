/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.webhooks;

public class WebhookEvents {

    public static String ACTIVITY_SENT = "activity.sent";
    
    public static String ACTIVITY_DELIVERED = "activity.delivered";
    
    public static String ACTIVITY_SOFT_BOUNCED = "activity.soft_bounced";
    
    public static String ACTIVITY_HARD_BOUNCED = "activity.hard_bounced";
    
    public static String ACTIVITY_OPENED = "activity.opened";
    
    public static String ACTIVITY_CLICKED = "activity.clicked";
    
    public static String ACTIVITY_UNSUBSCRIBED = "activity.unsubscribed";
    
    public static String ACTIVITY_SPAM_COMPLAINT = "activity.spam_compaint";
    
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
