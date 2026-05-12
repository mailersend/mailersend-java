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
 * Response for single email verification (sync POST /v1/email-verification/verify).
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SingleEmailVerificationResponse extends MailerSendResponse {

    @SerializedName("status")
    public String status;
}
