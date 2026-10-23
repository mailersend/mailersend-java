package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.analytics.Analytics;
import com.mailersend.sdk.analytics.AnalyticsByDate;
import com.mailersend.sdk.analytics.AnalyticsByDateList;
import com.mailersend.sdk.analytics.AnalyticsList;
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

            assertEquals("days", list.groupBy);
            assertTrue(list.statistics.length > 0);
            assertEquals(new Date(1631145600L * 1000), list.statistics[0].statDate);

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

            assertTrue(list.statistics.length > 0);
            assertNotNull(list.statistics[0].statDate);

        } catch (MailerSendException e) {

            fail();
        }
    }


    /**
     * Tests that the group by value is sent as the group_by query parameter
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("groupByValues")
    public void testGetByDateWithGroupBy(String label, String groupBy) throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(BY_DATE_RESPONSE, 200);

        ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .getByDate(groupBy, new String[] {EventTypes.DELIVERED});

        assertEquals("GET", client.lastMethod());
        assertEquals(API_URL + "/analytics/date?group_by=" + groupBy + "&event[]=delivered&" + DATES_QUERY, client.lastUri());
    }


    static Stream<Arguments> groupByValues() {

        return Stream.of(
            Arguments.of("days", Analytics.GROUP_BY_DAYS),
            Arguments.of("weeks", Analytics.GROUP_BY_WEEKS),
            Arguments.of("months", Analytics.GROUP_BY_MONTHS),
            Arguments.of("years", Analytics.GROUP_BY_YEARS)
        );
    }


    /**
     * Tests that a group_by=value fragment, which callers had to pass before the overload took a plain value, is still sent once
     */
    @Test
    public void testGetByDateWithGroupByFragment() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(BY_DATE_RESPONSE, 200);

        ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .getByDate("group_by=weeks", new String[] {EventTypes.DELIVERED});

        assertEquals(API_URL + "/analytics/date?group_by=weeks&event[]=delivered&" + DATES_QUERY, client.lastUri());
    }


    /**
     * Tests that an empty events list is left out of the query
     */
    @Test
    public void testGetByDateWithoutEventsOmitsEvent() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(BY_DATE_RESPONSE, 200);

        ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .getByDate(new String[0]);

        assertEquals(API_URL + "/analytics/date?group_by=days&" + DATES_QUERY, client.lastUri());
    }


    /**
     * Tests that the suppressed counter is read from the analytics by date response
     */
    @Test
    public void testGetByDateReadsSuppressedCount() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient.install(BY_DATE_RESPONSE, 200);

        AnalyticsByDateList list = ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .getByDate(new String[] {EventTypes.SUPPRESSED});

        AnalyticsByDate stat = list.statistics[0];

        assertEquals(3, stat.suppressed);
        assertEquals(new Date(1704067200L * 1000), stat.statDate);
    }


    /**
     * Tests that getByDate rejects a request without both dates
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("missingDates")
    public void testGetByDateRejectsMissingDates(String label, Date dateFrom, Date dateTo) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.analytics()
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .getByDate(new String[] {EventTypes.DELIVERED});
        });

        assertEquals("Date from and Date to dates are required.", e.getMessage());
    }


    static Stream<Arguments> missingDates() {

        return Stream.of(
            Arguments.of("missing date from", null, DATE_TO),
            Arguments.of("missing date to", DATE_FROM, null)
        );
    }


    /**
     * Tests that every analytics endpoint sends the domain id, the dates and the url encoded tags
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("analyticsEndpoints")
    public void testAnalyticsQueryWithDomainIdAndEncodedTags(String label, AnalyticsCall call, String response, String expectedUrl) throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(response, 200);

        call.request(ms.analytics()
            .domainId(TestHelper.domainId)
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .tags(new String[] {"news letter", "a&b#1"}));

        assertEquals("GET", client.lastMethod());
        assertEquals(expectedUrl, client.lastUri());
    }


    static Stream<Arguments> analyticsEndpoints() {

        String query = "domain_id=" + TestHelper.domainId + "&" + DATES_QUERY + "&tags[]=news+letter&tags[]=a%26b%231";

        return Stream.of(
            Arguments.of("by date", (AnalyticsCall) a -> a.getByDate(new String[] {EventTypes.OPENED}), BY_DATE_RESPONSE,
                API_URL + "/analytics/date?group_by=days&event[]=opened&" + query),
            Arguments.of("opens by country", (AnalyticsCall) Analytics::getOpensByCountry, OPENS_RESPONSE,
                API_URL + "/analytics/country?" + query),
            Arguments.of("opens by user agent", (AnalyticsCall) Analytics::getOpensByUserAgent, OPENS_RESPONSE,
                API_URL + "/analytics/ua-name?" + query),
            Arguments.of("opens by user agent type", (AnalyticsCall) Analytics::getOpensByUserAgentType, OPENS_RESPONSE,
                API_URL + "/analytics/ua-type?" + query)
        );
    }


    /**
     * Tests that tag() adds to the tags already set
     */
    @Test
    public void testTagAddsToTheTags() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(OPENS_RESPONSE, 200);

        ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .tags(new String[] {"first"})
            .tag("second")
            .getOpensByCountry();

        assertEquals(API_URL + "/analytics/country?" + DATES_QUERY + "&tags[]=first&tags[]=second", client.lastUri());
    }


    /**
     * Tests that tags() replaces the tags already set
     */
    @Test
    public void testTagsReplacesTheTags() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(OPENS_RESPONSE, 200);

        ms.analytics()
            .dateFrom(DATE_FROM)
            .dateTo(DATE_TO)
            .tag("first")
            .tags(new String[] {"second"})
            .getOpensByCountry();

        assertEquals(API_URL + "/analytics/country?" + DATES_QUERY + "&tags[]=second", client.lastUri());
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

            assertEquals("GR", list.statistics[0].name);
            assertEquals(44, list.statistics[0].count);
            assertEquals(new Date(1631145600L * 1000), list.dateFrom);

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

            assertEquals("UNK", list.statistics[0].name);
            assertEquals(44, list.statistics[0].count);

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

            assertEquals("webmail", list.statistics[0].name);
            assertEquals(44, list.statistics[0].count);

        } catch (MailerSendException e) {

            fail();
        }
    }


    @FunctionalInterface
    interface AnalyticsCall {

        Object request(Analytics analytics) throws MailerSendException;
    }
}
