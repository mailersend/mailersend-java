package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.activities.ActivitiesList;
import com.mailersend.sdk.activities.Activity;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.EventTypes;
import com.mailersend.sdk.vcr.VcrRecorder;

public class ActivitiesTest {

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
     * Tests that the date of the dateFrom filter can't be after the dateTo filter
     */
    @Test
    public void testDateFromAfterDateTo() {
        
        TemporalAccessor ta;
        Instant instant;
        
        ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-09T06:06:05.875000Z");
        instant = Instant.from(ta);
        Date dateFrom = Date.from(instant);
        
        ta = DateTimeFormatter.ISO_INSTANT.parse("2021-09-08T06:06:05.875000Z");
        instant = Instant.from(ta);
        Date dateTo = Date.from(instant);
        
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 25, dateFrom, dateTo, null);
            
        } catch (MailerSendException e) {

          return;
        }
        
        fail();
    }
    
    
    /**
     * Tests the retrieval of activities
     * Make sure your account has some activities
     */
    @Test
    public void testGetActivities() {


        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Calendar cal = Calendar.getInstance();
            Date dateTo = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date dateFrom = cal.getTime();

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 25, dateFrom, dateTo, null);

            assertTrue(activities.activities.length > 0);

        } catch (MailerSendException e) {

            fail();
        }
    }
    
    
    /**
     * Tests the pagination of the activities
     * Make sure your account has more than 10 activities
     */
    @Test
    public void testActivitiesPagination() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Calendar cal = Calendar.getInstance();
            Date dateTo = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date dateFrom = cal.getTime();

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 10, dateFrom, dateTo, null);

            assertTrue(activities.activities.length > 0);

            ActivitiesList secondPage = ms.activities().getActivities(TestHelper.domainId, 2, 10, dateFrom, dateTo, null);

            assertTrue(secondPage.activities.length > 0);

            assertNotEquals(secondPage.activities[0].id, activities.activities[0].id);

        } catch (MailerSendException e) {

          fail();
        }
    }
    
    
    /**
     * Tests the event activity filter
     * Make sure you have some activities with status opened
     */
    @Test
    public void testActivitiesFilterByEvent() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Calendar cal = Calendar.getInstance();
            Date dateTo = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date dateFrom = cal.getTime();

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 100, dateFrom, dateTo, new String[] { EventTypes.OPENED });

            assertTrue(activities.activities.length > 0);

            for (Activity activity : activities.activities) {

                assertTrue(activity.type.equals(EventTypes.OPENED));
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

            Date dateFrom = Date.from(Instant.parse("2024-01-01T00:00:00Z"));
            Date dateTo = Date.from(Instant.parse("2024-01-31T00:00:00Z"));

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 100, dateFrom, dateTo, new String[] { EventTypes.SUPPRESSED });

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

            Date dateFrom = Date.from(Instant.parse("2024-01-01T00:00:00Z"));
            Date dateTo = Date.from(Instant.parse("2024-01-31T00:00:00Z"));

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 25, dateFrom, dateTo, null);

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
     * Tests the contents of a single activity
     */
    @Test
    public void testSingleActivity() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Calendar cal = Calendar.getInstance();
            Date dateTo = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date dateFrom = cal.getTime();

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 10, dateFrom, dateTo, null);

            assertTrue(activities.activities.length > 0);

            Activity activity = activities.activities[0];

            assertTrue(activity.id != null && !activity.id.isBlank());
            assertTrue(activity.type != null && !activity.type.isBlank());

            assertTrue(activity.email != null);
            assertTrue(activity.email.from != null && !activity.email.from.isBlank());
            assertTrue(activity.email.subject != null && !activity.email.subject.isBlank());
            assertTrue(activity.email.id != null && !activity.email.id.isBlank());
            assertTrue(activity.email.status != null && !activity.email.status.isBlank());
            assertTrue(activity.email.createdAt != null);

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

            Calendar cal = Calendar.getInstance();
            Date dateTo = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date dateFrom = cal.getTime();

            ActivitiesList activities = ms.activities().getActivities(TestHelper.domainId, 1, 10, dateFrom, dateTo, null);

            assertTrue(activities.activities.length > 0);

            Activity activity = activities.activities[0];

            Email email = activity.email.toEmail();

            assertEquals(email.subject, activity.email.subject);
            assertEquals(email.from.email, activity.email.from);
            assertEquals(email.text, activity.email.text);
            assertEquals(email.html, activity.email.html);

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

        CapturingHttpClient client = CapturingHttpClient.install(200, "{\"data\":[],\"links\":{},\"meta\":{\"current_page\":1}}");

        long before = System.currentTimeMillis() / 1000;
        ms.activities().getActivities(TestHelper.domainId);
        long after = System.currentTimeMillis() / 1000;

        URI uri = client.lastRequest().uri();
        Map<String, String> query = queryParams(uri);

        assertEquals("GET", client.lastRequest().method());
        assertEquals("/v1/activity/" + TestHelper.domainId, uri.getPath());
        assertEquals("1", query.get("page"));
        assertEquals("25", query.get("limit"));

        long dateTo = Long.parseLong(query.get("date_to"));
        long dateFrom = Long.parseLong(query.get("date_from"));

        assertTrue(dateTo >= before && dateTo <= after);
        assertEquals(7 * 24 * 60 * 60, dateTo - dateFrom);
    }


    /**
     * Tests that an activity email without tags converts to an email without tags
     */
    @Test
    public void testActivityEmailConversionWithoutTags() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient.install(200, "{\"data\":{\"id\":\"65a4f0b5b1ffd57ddb0b5eec\",\"type\":\"sent\",\"email\":{\"id\":\"65a4f0a86f6d7fb43c0c498b\",\"from\":\"sender@test-sdk.com\",\"subject\":\"Subject\",\"tags\":null,\"recipient\":{\"email\":\"java@mailersend.com\"}}}}");

        Activity activity = ms.activities().getSingleActivity("65a4f0b5b1ffd57ddb0b5eec");

        Email email = activity.email.toEmail();

        assertEquals("Subject", email.subject);
        assertTrue(email.tags.isEmpty());
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
