/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.dmarcmonitoring;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * Represents a paginated list of DMARC monitors.
 */
public class DmarcMonitorsList extends PaginatedResponse {

    @SerializedName("data")
    public DmarcMonitor[] monitors;
}
