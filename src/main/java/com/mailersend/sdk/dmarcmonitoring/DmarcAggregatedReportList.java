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
 * Represents a paginated list of aggregated DMARC reports.
 */
public class DmarcAggregatedReportList extends PaginatedResponse {

    @SerializedName("data")
    public DmarcAggregatedReport[] reports;
}
