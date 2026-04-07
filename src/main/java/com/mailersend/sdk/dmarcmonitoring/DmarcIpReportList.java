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
 * Represents a paginated list of IP-specific DMARC reports.
 */
public class DmarcIpReportList extends PaginatedResponse {

    @SerializedName("data")
    public DmarcIpReport[] reports;
}
