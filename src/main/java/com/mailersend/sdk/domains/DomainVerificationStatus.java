/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>DomainVerificationStatus class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class DomainVerificationStatus extends MailerSendResponse {

    @SerializedName("message")
    public String message;
    
    @SerializedName("data")
    public DomainVerificationAttributes status;
}
