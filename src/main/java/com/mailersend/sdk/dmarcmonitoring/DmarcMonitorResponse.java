/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.dmarcmonitoring;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * Wraps a single DmarcMonitor returned by create/update endpoints.
 */
class DmarcMonitorResponse extends MailerSendResponse {

    @SerializedName("data")
    public DmarcMonitor monitor;
}
