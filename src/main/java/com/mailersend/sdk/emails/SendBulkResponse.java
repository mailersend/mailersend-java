/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class SendBulkResponse extends MailerSendResponse {

    @SerializedName("message")
    public String message;
    
    @SerializedName("bulk_email_id")
    public String bulkSendId;
}
