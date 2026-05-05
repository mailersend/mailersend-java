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
 * Wraps a paginated list of users from the API.
 */
public class UsersList extends PaginatedResponse {

    @SerializedName("data")
    public User[] users;


    /**
     * <p>postDeserialize.</p>
     */
    protected void postDeserialize() {

        if (users != null) {
            for (User user : users) {
                user.postDeserialize();
            }
        }
    }
}
