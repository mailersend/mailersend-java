/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.recipients;

import com.google.gson.annotations.SerializedName;

public class SuppressionItem {

    @SerializedName("id")
    public String id;
    
    @SerializedName("reason")
    public String reason;
    
    @SerializedName("recipient")
    public Recipient recipient;
}
