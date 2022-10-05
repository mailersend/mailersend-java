/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.util.List;
import java.util.Map;

/**
 * Represents a successful email send via MailerSend
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class MailerSendResponse {

    public int responseStatusCode;
    public String messageId = "";
    public int rateLimit;
    public int rateLimitRemaining;
    
    public Map<String, List<String>> headers;
}
