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
 * <p>AnalyticsByDateResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class AnalyticsByDateResponse extends MailerSendResponse {

    @SerializedName("data")
    public AnalyticsByDateList response;
    
    
    /**
     * Does all the needed actions after deserialization
     */
    public void postDeserialize() {
        
        response.postDeserialize();
    }
}
