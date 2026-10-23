package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.activities.ActivitiesList;
import com.mailersend.sdk.activities.Activity;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.EventTypes;
import com.mailersend.sdk.vcr.VcrRecorder;

public class ActivitiesTest {

    private static final String ACTIVITY_URL = "https://api.mailersend.com/v1/activity/" + TestHelper.domainId;

    private static final Date DATE_FROM = Date.from(Instant.parse("2024-01-01T00:00:00Z"));

    private static final Date DATE_TO = Date.from(Instant.parse("2024-01-31T00:00:00Z"));

    private static final String DATES_QUERY = "&date_from=1704067200&date_to=1706659200";

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("ActivitiesTest_" + info.getDisplayName());
	}

	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}


    /**
     * Tests that getActivities rejects missing dates and a date range that does not move forward
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidDates")
    public void testGetActivitiesRejectsInvalidDates(String label, Date dateFrom, Date dateTo, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.activities().getActivities(TestHelper.domainId, 1, 25, dateFrom, dateTo, null);
        });

        assertEquals(expectedMessage, e.getMessage());
    }


    static Stream<Arguments> invalidDates() {

        return Stream.of(
            Arguments.of("missing date from", null, DATE_TO, "Date from and Date to dates are required."),
            Arguments.of("missing date to", DATE_FROM, null, "Date from and Date to dates are required."),
            Arguments.of("date from after date to", DATE_TO, DATE_FROM, "From date cannot be after to date."),
            Arguments.of("date from equal to date to", DATE_FROM, DATE_FROM, "From date cannot be after to date.")
        );
    }


    /**
     * Tests the retrieval of activities
     */
    @Test
    public void testGetActivities() {


        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 25, DATE_FROM, DATE_TO, null);

            assertEquals(200, activities.responseStatusCode);
            assertEquals(2, activities.activities.length);
            assertEquals(1, activities.getCurrentPage());
            assertEquals(Date.from(Instant.parse("2024-01-15T10:09:09.604Z")), activities.activities[0].createdAt);
            assertEquals(Date.from(Instant.parse("2024-01-15T10:08:56.358Z")), activities.activities[0].email.createdAt);

        } catch (MailerSendException e) {

            fail();
        }
    }


    /**
     * Tests that the one argument overload requests the first page of the last 7 days
     */
    @Test
    public void testGetActivitiesWithoutDatesUsesTheLast7Days() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("{\"data\":[],\"links\":{},\"meta\":{\"current_page\":1}}", 200);

        long before = System.currentTimeMillis() / 1000;
        ms.activities().getActivities(TestHelper.domainId);
        long after = System.currentTimeMillis() / 1000;

        URI uri = client.lastRequest().uri();
        Map<String, String> query = queryParams(uri);

        assertEquals("GET", client.lastMethod());
        assertEquals("/v1/activity/" + TestHelper.domainId, uri.getPath());
        assertEquals("1", query.get("page"));
        assertEquals("25", query.get("limit"));

        long dateTo = Long.parseLong(query.get("date_to"));
        long dateFrom = Long.parseLong(query.get("date_from"));

        assertTrue(dateTo >= before && dateTo <= after);
        assertEquals(7 * 24 * 60 * 60, dateTo - dateFrom);
    }


    /**
     * Tests that every event of the events filter is sent as its own event[] parameter
     */
    @Test
    public void testGetActivitiesWithMultipleEvents() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("{\"data\":[],\"links\":{},\"meta\":{\"current_page\":1}}", 200);

        ms.activities().getActivities(TestHelper.domainId, 1, 25, DATE_FROM, DATE_TO, new String[] { EventTypes.OPENED, EventTypes.CLICKED });

        assertEquals(ACTIVITY_URL + "?page=1&limit=25" + DATES_QUERY + "&event[]=opened&event[]=clicked", client.lastUri());
    }


    /**
     * Tests that a negative page and limit are left out of the query
     */
    @Test
    public void testGetActivitiesOmitsNegativePageAndLimit() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("{\"data\":[],\"links\":{},\"meta\":{\"current_page\":1}}", 200);

        ms.activities().getActivities(TestHelper.domainId, -1, -1, DATE_FROM, DATE_TO, null);

        assertEquals(ACTIVITY_URL + "?" + DATES_QUERY.substring(1), client.lastUri());
    }


    /**
     * Tests the pagination of the activities
     */
    @Test
    public void testActivitiesPagination() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 10, DATE_FROM, DATE_TO, null);

            assertTrue(activities.activities.length > 0);

            ActivitiesList secondPage = ms.activities().getActivities(TestHelper.domainId, 2, 10, DATE_FROM, DATE_TO, null);

            assertTrue(secondPage.activities.length > 0);
            assertEquals(2, secondPage.getCurrentPage());

            assertNotEquals(secondPage.activities[0].id, activities.activities[0].id);

        } catch (MailerSendException e) {

          fail();
        }
    }


    /**
     * Tests that nextPage requests the page after the current one with the original filters
     */
    @Test
    public void testNextPageRequestsTheFollowingPage() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("{\"data\":[],\"links\":{},\"meta\":{\"current_page\":2,\"per_page\":10}}", 200);

        ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 2, 10, DATE_FROM, DATE_TO, new String[] { EventTypes.OPENED });
        activities.nextPage();

        assertEquals(ACTIVITY_URL + "?page=3&limit=10" + DATES_QUERY + "&event[]=opened", client.lastUri());
    }


    /**
     * Tests that previousPage requests the page before the current one with the original filters
     */
    @Test
    public void testPreviousPageRequestsThePrecedingPage() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("{\"data\":[],\"links\":{},\"meta\":{\"current_page\":2,\"per_page\":10}}", 200);

        ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 2, 10, DATE_FROM, DATE_TO, null);
        activities.previousPage();

        assertEquals(ACTIVITY_URL + "?page=1&limit=10" + DATES_QUERY, client.lastUri());
    }


    /**
     * Tests that previousPage on the first page requests the first page again instead of page 0
     */
    @Test
    public void testPreviousPageOnTheFirstPageRequestsTheFirstPage() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("{\"data\":[],\"links\":{},\"meta\":{\"current_page\":1,\"per_page\":10}}", 200);

        ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 10, DATE_FROM, DATE_TO, null);
        activities.previousPage();

        assertEquals(2, client.requestCount());
        assertEquals(ACTIVITY_URL + "?page=1&limit=10" + DATES_QUERY, client.lastUri());
    }


    /**
     * Tests the event activity filter
     */
    @Test
    public void testActivitiesFilterByEvent() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 100, DATE_FROM, DATE_TO, new String[] { EventTypes.OPENED });

            assertTrue(activities.activities.length > 0);

            for (Activity activity : activities.activities) {

                assertEquals(EventTypes.OPENED, activity.type);
            }

        } catch (MailerSendException e) {

          fail();
        }
    }


    /**
     * Tests that the activities can be filtered by the suppressed event
     * Fixed dates are used so that the recorded request hash (url + method + body) stays stable
     * and the fixture can be replayed without hitting the API
     */
    @Test
    public void testActivitiesFilterBySuppressedEvent() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 100, DATE_FROM, DATE_TO, new String[] { EventTypes.SUPPRESSED });

            assertTrue(activities.activities.length > 0);

            for (Activity activity : activities.activities) {

                assertEquals(EventTypes.SUPPRESSED, activity.type);
            }

        } catch (MailerSendException e) {

          fail();
        }
    }


    /**
     * Tests that the suppression_reason response field is deserialized for suppressed activities
     * and is absent for activities of any other type
     */
    @Test
    public void testActivitySuppressionReason() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 25, DATE_FROM, DATE_TO, null);

            assertTrue(activities.activities.length > 1);

            Activity suppressed = activities.activities[0];
            assertEquals(EventTypes.SUPPRESSED, suppressed.type);
            assertEquals("hard_bounced", suppressed.suppressionReason);

            Activity delivered = activities.activities[1];
            assertEquals(EventTypes.DELIVERED, delivered.type);
            assertNull(delivered.suppressionReason);

        } catch (MailerSendException e) {

          fail();
        }
    }


    /**
     * Tests the retrieval of a single activity
     */
    @Test
    public void testSingleActivity() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Activity activity = ms.activities().getSingleActivity("65a4f0b5b1ffd57ddb0b5eec");

            assertEquals("65a4f0b5b1ffd57ddb0b5eec", activity.id);
            assertEquals(EventTypes.CLICKED, activity.type);
            assertEquals(Date.from(Instant.parse("2024-01-15T10:09:09.604Z")), activity.createdAt);

            assertEquals("65a4f0a86f6d7fb43c0c498b", activity.email.id);
            assertEquals("sender@test-sdk.com", activity.email.from);
            assertEquals(TestHelper.toEmail, activity.email.to);
            assertEquals(TestHelper.templateId, activity.email.templateId);
            assertEquals(TestHelper.domainId, activity.email.domainId);
            assertEquals("65a4f0a86f6d7fb43c0c4990", activity.email.messageId);
            assertEquals("clicked", activity.email.interaction[1]);
            assertNull(activity.email.suppressionReason);
            assertEquals("X-Order-Id", activity.email.headers[0].name);
            assertEquals("8f2c14", activity.email.headers[0].value);
            assertNull(activity.email.tags);
            assertEquals(Date.from(Instant.parse("2024-01-15T10:08:56.358Z")), activity.email.createdAt);
            assertEquals(TestHelper.toEmail, activity.email.recipient.email);

        } catch (MailerSendException e) {

          fail();
        }
    }


    /**
     * Tests the conversion of an activity to an email ready to be sent
     */
    @Test
    public void testActivityEmailConversion() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 10, DATE_FROM, DATE_TO, null);

            assertTrue(activities.activities.length > 0);

            Activity activity = activities.activities[0];

            Email email = activity.email.toEmail();

            assertEquals(activity.email.subject, email.subject);
            assertEquals(activity.email.from, email.from.email);
            assertEquals(activity.email.text, email.text);
            assertEquals(activity.email.html, email.html);
            assertEquals("welcome", email.tags.get(0));
            assertEquals(TestHelper.toEmail, email.recipients.get(0).email);

        } catch (MailerSendException e) {

            fail();
        }
    }


    /**
     * Tests that an activity email without tags or a recipient converts to an email without them
     */
    @Test
    public void testActivityEmailConversionWithoutTagsOrRecipient() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient.install("{\"data\":{\"id\":\"65a4f0b5b1ffd57ddb0b5eec\",\"type\":\"sent\",\"email\":{\"id\":\"65a4f0a86f6d7fb43c0c498b\",\"from\":\"sender@test-sdk.com\",\"subject\":\"Subject\",\"tags\":null}}}", 200);

        Activity activity = ms.activities().getSingleActivity("65a4f0b5b1ffd57ddb0b5eec");

        Email email = activity.email.toEmail();

        assertEquals("Subject", email.subject);
        assertTrue(email.tags.isEmpty());
        assertTrue(email.recipients.isEmpty());
    }


    private static Map<String, String> queryParams(URI uri) {

        Map<String, String> params = new HashMap<String, String>();

        for (String pair : uri.getRawQuery().split("&")) {

            String[] parts = pair.split("=", 2);
            params.put(parts[0], parts.length > 1 ? parts[1] : "");
        }

        return params;
    }
}
