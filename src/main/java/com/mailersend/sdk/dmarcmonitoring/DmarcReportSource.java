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
 * Represents a DMARC report source entry.
 */
public class DmarcReportSource extends MailerSendResponse {

    @SerializedName("report_source")
    public String reportSource;

    @SerializedName("reports")
    public int reports;
}
