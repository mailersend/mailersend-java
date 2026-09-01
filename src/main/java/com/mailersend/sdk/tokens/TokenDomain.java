/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tokens;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the domain object returned as part of a token creation response.
 *
 * @author mailersend
 */
public class TokenDomain {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;
}
