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
 * Serializable body for token update requests.
 */
class TokenUpdateRequestBody {

    @SerializedName("name")
    public String name = null;

    @SerializedName("status")
    public String status = null;
}
