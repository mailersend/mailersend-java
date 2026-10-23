/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.users;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Serializable body for create/update user (invite) requests.
 */
class UserRequestBody {

    @SerializedName("email")
    public String email = null;

    @SerializedName("role")
    public String role = null;

    @SerializedName("permissions")
    public ArrayList<String> permissions = null;

    @SerializedName("templates")
    public ArrayList<String> templates = null;

    @SerializedName("domains")
    public ArrayList<String> domains = null;

    @SerializedName("requires_periodic_password_change")
    public Boolean requiresPeriodicPasswordChange = null;


    /**
     * Resets all fields so that the object can be reused.
     */
    public void reset() {

        email = null;
        role = null;
        permissions = null;
        templates = null;
        domains = null;
        requiresPeriodicPasswordChange = null;
    }
}
