/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.senderidentities;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * Represents a paginated list of sender identities.
 *
 * @author mailersend
 */
public class SenderIdentitiesList extends PaginatedResponse {

    @SerializedName("data")
    public SenderIdentity[] identities;
}
