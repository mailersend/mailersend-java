/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

/**
 * Represents a successful email send via MailerSend
 */
public class MailerSendResponse {

    public String messageId = "";
    public int rateLimit;
    public int rateLimitRemaining;
}
