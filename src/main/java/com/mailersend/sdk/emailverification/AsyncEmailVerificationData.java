/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;

/**
 * The {@code data} object of an async email verification response.
 */
class AsyncEmailVerificationData {

    @SerializedName("id")
    public String id;

    @SerializedName("address")
    public String address;

    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public String result;

    @SerializedName("error")
    public String error;
}
