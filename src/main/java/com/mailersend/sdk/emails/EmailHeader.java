/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import com.google.gson.annotations.SerializedName;

/**
 * <p>EmailHeader class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailHeader {

    @SerializedName("name")
    public String name;

    @SerializedName("value")
    public String value;

    /**
     * <p>Constructor for EmailHeader.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public EmailHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
