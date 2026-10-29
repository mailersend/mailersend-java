/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.smtpusers;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.util.DateParser;

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

    @SerializedName("domain")
    public String domainId;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date createdAt;


    /**
     * Is called to perform any actions after the deserialization of the response
     * Do not call directly
     */
    public void postDeserialize() {

        createdAt = DateParser.parse(createdAtStr);
    }
}
