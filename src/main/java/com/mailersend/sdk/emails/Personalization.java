/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Personalization class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Personalization {

    @SerializedName("email")
    public String email;

    @SerializedName("data")
    public HashMap<String, Object> data = new HashMap<String, Object>();
}
