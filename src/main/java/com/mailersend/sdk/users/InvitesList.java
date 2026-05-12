/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.users;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * Wraps a paginated list of invites from the API.
 */
public class InvitesList extends PaginatedResponse {

    @SerializedName("data")
    public Invite[] invites;


    /**
     * <p>postDeserialize.</p>
     */
    protected void postDeserialize() {

        if (invites != null) {
            for (Invite invite : invites) {
                invite.postDeserialize();
            }
        }
    }
}
