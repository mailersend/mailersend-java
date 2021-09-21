package com.mailersend.sdk;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class AnalyticsList {

    
    @SerializedName("date_from")
    private String dateFromString;
    
    @SerializedName("date_to")
    private String dateToString;
    
    public Date dateFrom;
    
    public Date dateTo;
    
    
    @SerializedName("stats")
    public AnalyticsStatistic[] statistics;
    
    protected void postDeserialize() {
     
        parseDates();
    }
    
    /**
     * Converts the retrieved dates to java.util.Date
     */
    private void parseDates() {
        
        TemporalAccessor ta;
        Instant instant;
        
        if (dateFromString != null && !dateFromString.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(dateFromString);
            instant = Instant.from(ta);
            dateFrom = Date.from(instant);
        }
        
        if (dateToString != null && !dateToString.isBlank()) {
            
            ta = DateTimeFormatter.ISO_INSTANT.parse(dateToString);
            instant = Instant.from(ta);
            dateTo = Date.from(instant);
        }
    }
    
}
