/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.sms;

import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

/**
 * <p>SmsPersonalization class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class SmsPersonalization {

    @SerializedName("phone_number")
    public String phoneNumber;

    @SerializedName("data")
    public HashMap<String, Object> data = new HashMap<String, Object>();
}
