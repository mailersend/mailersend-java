/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emailverification;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * Response for async email verification (POST /v1/email-verification/verify-async
 * and GET /v1/email-verification/verify-async/{id}).
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class AsyncEmailVerificationResponse extends MailerSendResponse {

    @SerializedName("data")
    private AsyncEmailVerificationData data;

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


    /**
     * Is called to perform any actions after the deserialization of the response.
     * Do not call directly.
     */
    protected void postDeserialize() {

        if (data != null) {
            id = data.id;
            address = data.address;
            status = data.status;
            result = data.result;
            error = data.error;
        }
    }
}
