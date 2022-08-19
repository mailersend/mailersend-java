package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.analytics.AnalyticsByDate;
import com.mailersend.sdk.analytics.AnalyticsByDateList;
import com.mailersend.sdk.analytics.AnalyticsList;
import com.mailersend.sdk.analytics.AnalyticsStatistic;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.EventTypes;
import com.mailersend.sdk.vcr.VcrRecorder;

public class AnalyticsTest {

	
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("AnalyticsTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
    
    /**
     * Gets analytics by date using date filters
     */
    @Test
    public void TestAnalyticsByDate() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            AnalyticsByDateList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(new Date())
                    .getByDate(new String[] {EventTypes.DELIVERED, EventTypes.OPENED, EventTypes.CLICKED});
            
            System.out.println("\n\nAnalytics by date:");
            for (AnalyticsByDate dayStat : list.statistics) {
                
                System.out.println(dayStat.statDate.toString());
                System.out.println(dayStat.delivered);
                System.out.println(dayStat.opened);
                System.out.println(dayStat.clicked);
                                
            }
            
        } catch (MailerSendException e) {
            
            fail();
        }
    }
    
    
    /**
     * Tests analytics by date for a domain
     */
    @Test
    public void TestAnalyticsByDateDomain() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            AnalyticsByDateList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(new Date())
                    .domainId(TestHelper.domainId)
                    .getByDate(new String[] {EventTypes.DELIVERED, EventTypes.OPENED, EventTypes.CLICKED});
            
            System.out.println("\n\nAnalytics by date for domain:");
            for (AnalyticsByDate dayStat : list.statistics) {
                
                System.out.println(dayStat.statDate.toString());
                System.out.println(dayStat.delivered);
                System.out.println(dayStat.opened);
                System.out.println(dayStat.clicked);
                                
            }
            
        } catch (MailerSendException e) {
            
            fail();
        }
    }
    
    
    /**
     * Tests opens by country
     */
    @Test
    public void TestAnalyticsOpensByCountry() {
        
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            AnalyticsList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(new Date())
                    .domainId(TestHelper.domainId)
                    .getOpensByCountry();
            
            System.out.println("\n\nOpens by country:");
            
            for (AnalyticsStatistic stat : list.statistics) {
                
                System.out.println(stat.name + " - " + stat.count);
            }
            
        } catch (MailerSendException e) {
            
            fail();
        }
    }
    
    
    /**
     * Tests opens by user agent
     */
    @Test
    public void TestAnalyticsOpensByUa() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            AnalyticsList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(new Date())
                    .getOpensByUserAgent();
            
            System.out.println("\n\nOpens by user agent:");
            
            for (AnalyticsStatistic stat : list.statistics) {
                
                System.out.println(stat.name + " - " + stat.count);
            }
            
        } catch (MailerSendException e) {
            
            fail();
        }
    }
    
    /**
     * Tests opens by reading environment
     */
    @Test
    public void TestAnalyticsOpensByUaType() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            AnalyticsList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(new Date())
                    .getOpensByUserAgenType();
            
            System.out.println("\n\nOpens by user agent type:");
            
            for (AnalyticsStatistic stat : list.statistics) {
                
                System.out.println(stat.name + " - " + stat.count);
            }
            
        } catch (MailerSendException e) {
            
            fail();
        }
    }
}
