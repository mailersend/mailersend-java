/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.analytics;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * <p>AnalyticsList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class AnalyticsList {

    
    @SerializedName("date_from")
    private String dateFromString;
    
    @SerializedName("date_to")
    private String dateToString;
    
    public Date dateFrom;
    
    public Date dateTo;
    
    
    @SerializedName("stats")
    public AnalyticsStatistic[] statistics;
    
    
    /**
     * Does all the needed actions after deserialization
     */
    public void postDeserialize() {
     
        parseDates();
    }
    
    
    /**
     * Converts the retrieved timestamps to java.util.Date
     */
    private void parseDates() {
        
        if (dateFromString != null && !dateFromString.isBlank()) {
            
            dateFrom = new Date(Long.parseLong(dateFromString) * 1000);
            
        }
        
        if (dateToString != null && !dateToString.isBlank()) {

            dateTo = new Date(Long.parseLong(dateToString) * 1000);
        }
    }
    
}
