/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.blocklistmonitoring;

import com.google.gson.annotations.SerializedName;

class BlocklistMonitorCreateRequestBody {

    @SerializedName("address")
    public String address;

    @SerializedName("name")
    public String name;

    @SerializedName("notify")
    public Boolean notify;

    @SerializedName("notify_email")
    public String notifyEmail;

    @SerializedName("notify_address")
    public String notifyAddress;

    public void reset() {
        address = null;
        name = null;
        notify = null;
        notifyEmail = null;
        notifyAddress = null;
    }
}
