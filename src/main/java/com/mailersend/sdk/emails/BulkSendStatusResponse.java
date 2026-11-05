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

/**
 * Wraps the BulkSendStatus returned by the bulk email status endpoint.
 */
class BulkSendStatusResponse extends MailerSendResponse {

    @SerializedName("data")
    public BulkSendStatus status;
}
