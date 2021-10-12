/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.analytics;

import com.google.gson.annotations.SerializedName;

public class AnalyticsStatistic {

    @SerializedName("name")
    public String name;
    
    @SerializedName("count")
    public int count;
}
