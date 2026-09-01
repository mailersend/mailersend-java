/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.dmarcmonitoring;

import com.google.gson.annotations.SerializedName;

class DmarcMonitorCreateRequestBody {

    @SerializedName("domain_id")
    public String domainId;

    public void reset() {
        domainId = null;
    }
}
