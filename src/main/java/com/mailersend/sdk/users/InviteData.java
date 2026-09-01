/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.users;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the nested data object within an invite response,
 * which contains domain and template ID arrays.
 */
public class InviteData {

    @SerializedName("domains")
    public String[] domains;

    @SerializedName("templates")
    public String[] templates;
}
