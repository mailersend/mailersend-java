/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.blocklistmonitoring;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * Wraps a single BlocklistMonitor returned by get, create, and update endpoints.
 */
class BlocklistMonitorResponse extends MailerSendResponse {

    @SerializedName("data")
    public BlocklistMonitor monitor;
}
