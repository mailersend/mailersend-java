package com.mailersend.sdk;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.analytics.AnalyticsByDate;

public class AnalyticsByDateList {

    @SerializedName("date_from")
    private String dateFromString;
    
    @SerializedName("date_to")
    private String dateToString;
    
    public Date dateFrom;
    
    public Date dateTo;
    
    @SerializedName("group_by")
    public String groupBy;
    
    
    @SerializedName("stats")
    public AnalyticsByDate[] statistics;
    
    protected void postDeserialize() {
        
        parseDates();
        
        for (AnalyticsByDate a : statistics) {
            
            a.postDeserialize();
        }
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
