package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static final String API_URL = "https://api.mailersend.com/v1";

    private static final Date DATE_FROM = Date.from(Instant.parse("2024-01-01T00:00:00Z"));

    private static final Date DATE_TO = Date.from(Instant.parse("2024-01-31T00:00:00Z"));

    private static final String DATES_QUERY = "date_from=1704067200&date_to=1706659200";

    private static final String BY_DATE_RESPONSE = "{\"data\":{\"date_from\":\"1704067200\",\"date_to\":\"1706745599\",\"group_by\":\"days\",\"stats\":[{\"date\":\"1704067200\",\"suppressed\":3}]}}";

    private static final String OPENS_RESPONSE = "{\"data\":{\"date_from\":1704067200,\"date_to\":1706745599,\"stats\":[{\"name\":\"GB\",\"count\":2}]}}";


	
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
    public void testAnalyticsByDate() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            ta = DateTimeFormatter.ISO_INSTANT.parse("2022-09-09T00:00:00.875000Z");
            Date dateTo = Date.from(Instant.from(ta));
            
            AnalyticsByDateList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
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
    public void testAnalyticsByDateDomain() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            ta = DateTimeFormatter.ISO_INSTANT.parse("2022-09-09T00:00:00.875000Z");
            Date dateTo = Date.from(Instant.from(ta));
            
            AnalyticsByDateList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
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
    public void testAnalyticsOpensByCountry() {
        
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            ta = DateTimeFormatter.ISO_INSTANT.parse("2022-09-09T00:00:00.875000Z");
            Date dateTo = Date.from(Instant.from(ta));
            
            AnalyticsList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
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
    public void testAnalyticsOpensByUa() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            ta = DateTimeFormatter.ISO_INSTANT.parse("2022-09-09T00:00:00.875000Z");
            Date dateTo = Date.from(Instant.from(ta));
            
            AnalyticsList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
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
    public void testAnalyticsOpensByUaType() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T00:00:00.875000Z");
            Instant instant = Instant.from(ta);
            Date dateFrom = Date.from(instant);
            
            ta = DateTimeFormatter.ISO_INSTANT.parse("2022-09-09T00:00:00.875000Z");
            Date dateTo = Date.from(Instant.from(ta));
            
            AnalyticsList list = ms.analytics()
                    .dateFrom(dateFrom)
                    .dateTo(dateTo)
                    .getOpensByUserAgentType();
            
            System.out.println("\n\nOpens by user agent type:");
            
            for (AnalyticsStatistic stat : list.statistics) {
                
                System.out.println(stat.name + " - " + stat.count);
            }
            
        } catch (MailerSendException e) {
            
            fail();
        }
    }


    /**
     * Tests that a plain group by value is sent as the group_by query parameter
     */
    @Test
    public void testGetByDateWithGroupBy() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, BY_DATE_RESPONSE);

        ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .getByDate("weeks", new String[] {EventTypes.DELIVERED});

        assertEquals(API_URL + "/analytics/date?group_by=weeks&event[]=delivered&" + DATES_QUERY, client.lastUrl());
    }


    /**
     * Tests that a group_by=value fragment, which callers had to pass before the overload took a plain value, is still sent once
     */
    @Test
    public void testGetByDateWithGroupByFragment() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, BY_DATE_RESPONSE);

        ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .getByDate("group_by=weeks", new String[] {EventTypes.DELIVERED});

        assertEquals(API_URL + "/analytics/date?group_by=weeks&event[]=delivered&" + DATES_QUERY, client.lastUrl());
    }


    /**
     * Tests that the suppressed counter is read from the analytics by date response
     */
    @Test
    public void testGetByDateReadsSuppressedCount() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient.install(200, BY_DATE_RESPONSE);

        AnalyticsByDateList list = ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .getByDate(new String[] {EventTypes.SUPPRESSED});

        assertEquals(3, list.statistics[0].suppressed);
    }


    /**
     * Tests that the tags are url encoded
     */
    @Test
    public void testTagsAreUrlEncoded() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, OPENS_RESPONSE);

        ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .tags(new String[] {"news letter", "a&b#1"})
            .getOpensByCountry();

        assertEquals(API_URL + "/analytics/country?" + DATES_QUERY + "&tags[]=news+letter&tags[]=a%26b%231", client.lastUrl());
    }
}
