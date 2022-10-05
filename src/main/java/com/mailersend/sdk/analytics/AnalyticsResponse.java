/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.analytics;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>AnalyticsResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class AnalyticsResponse extends MailerSendResponse {

    @SerializedName("data")
    public AnalyticsList response;
    
    
    /**
     * Does all the needed actions after deserialization
     */
    public void postDeserialize() {
        
        response.postDeserialize();
    }
}
