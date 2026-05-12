/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.recipients;

import com.google.gson.annotations.SerializedName;

/**
 * <p>SuppressionItem class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SuppressionItem {

    @SerializedName("id")
    public String id;
    
    @SerializedName("reason")
    public String reason;

    @SerializedName("readable_reason")
    public String readableReason;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("recipient")
    public Recipient recipient;
}
