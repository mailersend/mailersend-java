/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

/**
 * The possible email statuses, used by the status filter of the emails list endpoint
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailStatus {

    /** Constant <code>QUEUED="queued"</code> */
    public static final String QUEUED = "queued";

    /** Constant <code>SENT="sent"</code> */
    public static final String SENT = "sent";

    /** Constant <code>REJECTED="rejected"</code> */
    public static final String REJECTED = "rejected";

    /** Constant <code>DELIVERED="delivered"</code> */
    public static final String DELIVERED = "delivered";
}
