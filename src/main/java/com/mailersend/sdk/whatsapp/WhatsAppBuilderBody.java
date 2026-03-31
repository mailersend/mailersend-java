/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * <p>WhatsAppBuilderBody class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppBuilderBody {

    @SerializedName("from")
    public String from;

    @SerializedName("to")
    public ArrayList<String> to = new ArrayList<String>();

    @SerializedName("template_id")
    public String templateId;

    @SerializedName("personalization")
    public ArrayList<WhatsAppPersonalization> personalization = null;
}
