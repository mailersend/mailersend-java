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
    public ArrayList<String> permissions = new ArrayList<String>();

    @SerializedName("templates")
    public ArrayList<String> templates = new ArrayList<String>();

    @SerializedName("domains")
    public ArrayList<String> domains = new ArrayList<String>();

    @SerializedName("requires_periodic_password_change")
    public Boolean requiresPeriodicPasswordChange = null;


    /**
     * Resets all fields so that the object can be reused.
     */
    public void reset() {

        email = null;
        role = null;
        permissions.clear();
        templates.clear();
        domains.clear();
        requiresPeriodicPasswordChange = null;
    }
}
