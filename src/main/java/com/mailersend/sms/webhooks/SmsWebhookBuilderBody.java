/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sms.webhooks;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

class SmsWebhookBuilderBody {

    @SerializedName("url")
    public String url;
    
    @SerializedName("name")
    public String name;
    
    @SerializedName("events")
    public ArrayList<String> events = new ArrayList<String>();
    
    @SerializedName("enabled")
    public Boolean enabled = null;
    
    @SerializedName("sms_number_id")
    public String smsNumberId;
}
