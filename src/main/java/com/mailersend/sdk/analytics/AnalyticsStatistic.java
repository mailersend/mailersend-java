/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.analytics;

import com.google.gson.annotations.SerializedName;

/**
 * <p>AnalyticsStatistic class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class AnalyticsStatistic {

    @SerializedName("name")
    public String name;
    
    @SerializedName("count")
    public int count;
}
