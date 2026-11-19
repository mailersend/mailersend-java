/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.account;

import com.google.gson.annotations.SerializedName;

/**
 * WhatsApp messages sent in the add-on cycle, against the messages the add-on includes.
 */
public class AccountWhatsAppUsage {

    @SerializedName("sent")
    public int sent;

    @SerializedName("limit")
    public int limit;
}
