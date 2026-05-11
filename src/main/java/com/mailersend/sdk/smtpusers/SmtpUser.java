/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.smtpusers;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>SmtpUser class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmtpUser extends MailerSendResponse {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("enabled")
    public Boolean enabled;

    @SerializedName("accessed_at")
    public String accessedAt;

    @SerializedName("server")
    public String server;

    @SerializedName("port")
    public int port;

    @SerializedName("domain_id")
    public String domainId;
}
