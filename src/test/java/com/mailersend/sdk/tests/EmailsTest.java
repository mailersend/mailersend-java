/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.emails.EmailActivity;
import com.mailersend.sdk.emails.EmailHeader;
import com.mailersend.sdk.emails.EmailInfo;
import com.mailersend.sdk.emails.EmailInteraction;
import com.mailersend.sdk.emails.EmailListItem;
import com.mailersend.sdk.emails.EmailStatus;
import com.mailersend.sdk.emails.Emails;
import com.mailersend.sdk.emails.EmailsList;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.MailerSendHttpClientFactory;
import com.mailersend.sdk.vcr.HttpClientVcr;
import com.mailersend.sdk.vcr.HttpClientVcrResponse;
import com.mailersend.sdk.vcr.VcrRecorder;

/**
 * Tests the emails list and single email endpoints, ms.emails().getEmails() and
 * ms.emails().getEmail()
 */
public class EmailsTest {

    private static final String EMAILS_URL = "https://api.mailersend.com/v1/emails";

    private static final String EMAIL_URL = "https://api.mailersend.com/v1/email/";

    /** Fixed timestamps so that the request urls, and with them the fixture hashes, are stable */
    private static final long DATE_FROM = 1756256400L;

    private static final long DATE_TO = 1756342800L;

    /** The query part of the request url that the required filters below produce */
    private static final String REQUIRED_QUERY = "?domain_id=" + TestHelper.domainId
            + "&date_from=" + DATE_FROM + "&date_to=" + DATE_TO;

    private static final String EMAIL_ID = "6a8fa9b1902fab56e0ce50dd";

    private static final String SUPPRESSED_EMAIL_ID = "6a8fa9b1902fab56e0ce50ee";

    private static final String BARE_EMAIL_ID = "6a8fa9b1902fab56e0ce50cc";

    /** 2026-08-27 03:06:25 UTC, the created_at and updated_at of the emails in the fixtures */
    private static final long CREATED_AT = 1787799985000L;

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {

        VcrRecorder.useRecording("EmailsTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {

        VcrRecorder.stopRecording();
    }


    /*
     * ----------------------------------------------------------------------------------
     * The request url and the filters
     * ----------------------------------------------------------------------------------
     */


    /**
     * Tests that getEmails() requests the emails endpoint with the domain id, the dates,
     * the page and the limit in the query
     */
    @Test
    public void testGetEmailsRequestsTheEmailsEndpoint() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingClient client = captureRequests();

        try {

            withRequiredFilters(ms).page(2).limit(50).getEmails();

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        assertEquals(1, client.requestedUris.size());

        assertEquals(EMAILS_URL + REQUIRED_QUERY + "&limit=50&page=2",
                client.lastRequestedUri().toString());
    }


    /**
     * Tests that the status and the interaction filters are serialized as repeated status[] and
     * interaction[] parameters. The API validates both as arrays, a scalar status=sent is a 422
     */
    @Test
    public void testStatusAndInteractionAreRepeatedArrayParams() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingClient client = captureRequests();

        try {

            withRequiredFilters(ms)
                    .status(EmailStatus.SENT, EmailStatus.DELIVERED)
                    .interaction(EmailInteraction.OPENED, EmailInteraction.CLICKED)
                    .getEmails();

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        String query = client.lastRequestedUri().getQuery();

        assertEquals("domain_id=" + TestHelper.domainId
                + "&date_from=" + DATE_FROM
                + "&date_to=" + DATE_TO
                + "&status[]=sent&status[]=delivered"
                + "&interaction[]=opened&interaction[]=clicked", query);

        assertTrue(query.contains("status[]=sent"));
        assertTrue(query.contains("status[]=delivered"));
        assertTrue(query.contains("interaction[]=opened"));
        assertTrue(query.contains("interaction[]=clicked"));

        // the values must not be comma joined into a single scalar parameter
        assertFalse(query.contains("status=sent"));
        assertFalse(query.contains("interaction=opened"));
        assertFalse(query.contains("sent,delivered"));
        assertFalse(query.contains("opened,clicked"));
        assertFalse(query.contains("%2C"));
        assertFalse(query.matches(".*[?&]status=.*"));
        assertFalse(query.matches(".*[?&]interaction=.*"));
    }


    /**
     * Tests that a single status and a single interaction are still sent as array parameters
     */
    @Test
    public void testASingleStatusIsStillAnArrayParam() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingClient client = captureRequests();

        try {

            withRequiredFilters(ms)
                    .status(EmailStatus.QUEUED)
                    .interaction(EmailInteraction.NO_INTERACTION)
                    .getEmails();

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        assertEquals(EMAILS_URL + REQUIRED_QUERY
                + "&status[]=queued&interaction[]=no_interaction",
                client.lastRequestedUri().toString());
    }


    /**
     * Tests that the optional filters are all sent, and url encoded, when they are set
     */
    @Test
    public void testOptionalFiltersArePresentWhenSet() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingClient client = captureRequests();

        try {

            withRequiredFilters(ms)
                    .limit(100)
                    .recipientEmail("rcpt+tag@example.org")
                    .messageId("6a8fa9b1902fab56e0ce50aa")
                    .templateId("7nxe3yjmeq28vp0k")
                    .subject("Welcome friend")
                    .tag("news letter")
                    .getEmails();

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        String uri = client.lastRequestedUri().toString();

        assertEquals(EMAILS_URL + REQUIRED_QUERY
                + "&limit=100"
                + "&recipient_email=rcpt%2Btag%40example.org"
                + "&message_id=6a8fa9b1902fab56e0ce50aa"
                + "&template_id=7nxe3yjmeq28vp0k"
                + "&subject=Welcome+friend"
                + "&tag=news+letter", uri);
    }


    /**
     * Tests that none of the optional filters are sent when they are not set
     */
    @Test
    public void testOptionalFiltersAreAbsentWhenNotSet() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingClient client = captureRequests();

        try {

            withRequiredFilters(ms).getEmails();

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        String uri = client.lastRequestedUri().toString();

        assertEquals(EMAILS_URL + REQUIRED_QUERY, uri);

        assertFalse(uri.contains("limit="));
        assertFalse(uri.contains("page="));
        assertFalse(uri.contains("status"));
        assertFalse(uri.contains("interaction"));
        assertFalse(uri.contains("recipient_email"));
        assertFalse(uri.contains("message_id"));
        assertFalse(uri.contains("template_id"));
        assertFalse(uri.contains("subject"));
        assertFalse(uri.contains("tag"));
    }


    /**
     * Tests that empty status and interaction filters add no parameters
     */
    @Test
    public void testEmptyStatusAndInteractionFiltersAddNoParams() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingClient client = captureRequests();

        try {

            withRequiredFilters(ms).status().interaction().getEmails();

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        assertEquals(EMAILS_URL + REQUIRED_QUERY, client.lastRequestedUri().toString());
    }


    /**
     * Tests that the date filters accept java.util.Date objects and convert them to
     * unix timestamps in seconds
     */
    @Test
    public void testDateFiltersAcceptDateObjects() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingClient client = captureRequests();

        try {

            ms.emails()
                    .domainId(TestHelper.domainId)
                    // the milliseconds are dropped when converting to seconds
                    .dateFrom(new Date(DATE_FROM * 1000 + 987))
                    .dateTo(new Date(DATE_TO * 1000))
                    .getEmails();

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        assertEquals(EMAILS_URL + REQUIRED_QUERY, client.lastRequestedUri().toString());
    }


    /**
     * Tests that the filters stay set on the shared Emails instance between calls
     */
    @Test
    public void testFiltersPersistBetweenCalls() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        // ms.emails() hands out the same object every time, which is what makes the filters persist
        assertSame(ms.emails(), ms.emails());

        RequestCapturingClient client = captureRequests();

        try {

            withRequiredFilters(ms).tag("newsletter").getEmails();

            // no filters are set again for the second call
            ms.emails().getEmails();

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        assertEquals(2, client.requestedUris.size());

        assertEquals(EMAILS_URL + REQUIRED_QUERY + "&tag=newsletter",
                client.requestedUris.get(0).toString());

        assertEquals(client.requestedUris.get(0).toString(), client.requestedUris.get(1).toString());

        // a new MailerSend object starts with no filters at all
        MailerSend other = new MailerSend();
        other.setToken(TestHelper.validToken);

        assertThrows(MailerSendException.class, () -> other.emails().getEmails());
    }


    /**
     * Tests that getEmails() requires a domain id
     */
    @Test
    public void testGetEmailsRequiresADomainId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
                ms.emails().dateFrom(DATE_FROM).dateTo(DATE_TO).getEmails());

        assertEquals("A domain id is required.", e.getMessage());

        MailerSendException blank = assertThrows(MailerSendException.class, () ->
                ms.emails().domainId("  ").dateFrom(DATE_FROM).dateTo(DATE_TO).getEmails());

        assertEquals("A domain id is required.", blank.getMessage());
    }


    /**
     * Tests that getEmails() requires both dates
     */
    @Test
    public void testGetEmailsRequiresBothDates() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
                ms.emails().domainId(TestHelper.domainId).dateFrom(DATE_FROM).getEmails());

        assertEquals("Date from and Date to dates are required.", e.getMessage());

        MailerSendException nullDate = assertThrows(MailerSendException.class, () ->
                ms.emails().domainId(TestHelper.domainId)
                        .dateFrom(DATE_FROM)
                        .dateTo((Date) null)
                        .getEmails());

        assertEquals("Date from and Date to dates are required.", nullDate.getMessage());
    }


    /**
     * Tests that the date of the dateFrom filter can't be after or equal to the dateTo filter
     */
    @Test
    public void testDateFromAfterDateToThrows() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException after = assertThrows(MailerSendException.class, () ->
                ms.emails().domainId(TestHelper.domainId)
                        .dateFrom(DATE_TO)
                        .dateTo(DATE_FROM)
                        .getEmails());

        assertEquals("From date cannot be after to date.", after.getMessage());

        MailerSendException same = assertThrows(MailerSendException.class, () ->
                ms.emails().domainId(TestHelper.domainId)
                        .dateFrom(DATE_FROM)
                        .dateTo(DATE_FROM)
                        .getEmails());

        assertEquals("From date cannot be after to date.", same.getMessage());
    }


    /**
     * Tests that getEmail() requests the singular email endpoint
     */
    @Test
    public void testGetEmailRequestsTheSingularEmailEndpoint() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingClient client = captureRequests();
        client.responseBody = "{\"data\":{\"id\":\"" + EMAIL_ID + "\"}}";

        try {

            ms.emails().getEmail(EMAIL_ID);

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }

        assertEquals("https://api.mailersend.com/v1/email/" + EMAIL_ID,
                client.lastRequestedUri().toString());

        // the endpoint is singular, the list endpoint is the plural one
        assertFalse(client.lastRequestedUri().getPath().startsWith("/v1/emails"));
    }


    /*
     * ----------------------------------------------------------------------------------
     * The emails list response
     * ----------------------------------------------------------------------------------
     */


    /**
     * Tests that a populated results page deserializes into an EmailsList
     */
    @Test
    public void testGetEmailsParsesThePopulatedEnvelope() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailsList emails = withRequiredFilters(ms).getEmails();

            assertNotNull(emails.emails);
            assertEquals(1, emails.emails.length);

            EmailListItem email = emails.emails[0];

            assertEquals(EMAIL_ID, email.id);
            assertEquals("sender@example.com", email.from);
            assertEquals("rcpt@example.org", email.to);
            assertEquals("Welcome", email.subject);

            // the list endpoint never returns the content of an email
            assertNull(email.text);
            assertNull(email.html);

            assertEquals("7nxe3yjmeq28vp0k", email.templateId);
            assertEquals("7nxe3yjmeq28vp0k", email.domainId);
            assertEquals("6a8fa9b1902fab56e0ce50aa", email.messageId);
            assertEquals(EmailStatus.SENT, email.status);

            assertNotNull(email.tags);
            assertEquals(1, email.tags.length);
            assertEquals("newsletter", email.tags[0]);

            assertNotNull(email.interaction);
            assertEquals(1, email.interaction.length);
            assertEquals(EmailInteraction.OPENED, email.interaction[0]);

            assertNull(email.suppressionReason);

            // the email was sent without custom headers
            assertNull(email.headers);

            assertNotNull(email.createdAt);
            assertEquals(CREATED_AT, email.createdAt.getTime());
            assertNotNull(email.updatedAt);
            assertEquals(CREATED_AT, email.updatedAt.getTime());

            // the meta of the response
            assertNotNull(emails.meta);
            assertEquals(1, emails.meta.currentPage);
            assertEquals(EMAILS_URL + "?page=1", emails.meta.currentPageUrl);
            assertEquals(1, emails.meta.from);
            assertEquals(EMAILS_URL, emails.meta.path);
            assertEquals(10, emails.meta.limit);
            assertEquals(3, emails.meta.to);

            // the endpoint pages without a count, so it returns no last_page and meta.lastPage
            // stays at the default of 0. Do not use it to detect the end of the results
            assertEquals(0, emails.meta.lastPage);

            // the links of the response
            assertNotNull(emails.links);
            assertEquals(EMAILS_URL + "?page=1", emails.links.first);
            assertNull(emails.links.last);
            assertNull(emails.links.prev);
            assertNull(emails.links.next);

            assertEquals(1, emails.getCurrentPage());
            assertFalse(emails.hasNext());
            assertFalse(emails.hasPrevious());
            assertNull(emails.next());
            assertNull(emails.previous());

            assertEquals(200, emails.responseStatusCode);

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /**
     * Tests that EmailDates parses both of the date formats it accepts.
     *
     * The second email of this fixture holds its dates in the database format,
     * "2026-08-27 03:06:25", on purpose. The API itself returns the ISO-8601 format that the first
     * email holds, and so does every other fixture of this test class. Do not "fix" this one,
     * it is what covers the fallback of EmailDates
     */
    @Test
    public void testEmailDatesParseBothApiFormats() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailsList emails = withRequiredFilters(ms).getEmails();

            assertEquals(2, emails.emails.length);

            // "2026-08-27T03:06:25.000000Z", the format the API returns
            assertEquals(CREATED_AT, emails.emails[0].createdAt.getTime());
            assertEquals(CREATED_AT, emails.emails[0].updatedAt.getTime());

            // "2026-08-27 03:06:25", the database format, assumed to be UTC
            assertEquals(CREATED_AT, emails.emails[1].createdAt.getTime());
            assertEquals(CREATED_AT, emails.emails[1].updatedAt.getTime());

            assertEquals(emails.emails[0].createdAt, emails.emails[1].createdAt);

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /**
     * Tests that the custom headers of an email are parsed. The API returns them as an array of
     * name and value objects, the same shape that Email.addHeader() sends them in
     *
     * The fixture holds the response of the API verbatim
     */
    @Test
    public void testEmailListItemParsesCustomHeaders() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailsList emails = withRequiredFilters(ms).getEmails();

            EmailListItem email = emails.emails[0];

            assertNotNull(email.headers);
            assertEquals(1, email.headers.length);

            EmailHeader header = email.headers[0];

            assertEquals("X-Custom", header.name);
            assertEquals("foo", header.value);

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /**
     * Tests that an empty results page deserializes into an EmailsList
     */
    @Test
    public void testGetEmailsParsesTheEmptyEnvelope() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailsList emails = withRequiredFilters(ms).page(2).getEmails();

            assertNotNull(emails.emails);
            assertEquals(0, emails.emails.length);

            assertEquals(2, emails.getCurrentPage());
            assertEquals(2, emails.meta.currentPage);

            // the API returns from and to as null on an empty page, which lands on the int default
            assertEquals(0, emails.meta.from);
            assertEquals(0, emails.meta.to);
            assertEquals(10, emails.meta.limit);

            assertNull(emails.links.next);
            assertEquals(EMAILS_URL + "?page=1", emails.links.prev);

            assertFalse(emails.hasNext());
            assertTrue(emails.hasPrevious());
            assertNull(emails.next());

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /**
     * Tests that next() requests the following page and returns null once there is none
     */
    @Test
    public void testNextReturnsTheFollowingPage() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailsList firstPage = withRequiredFilters(ms).getEmails();

            assertEquals(1, firstPage.getCurrentPage());
            assertEquals(EMAIL_ID, firstPage.emails[0].id);

            // there is a next page even though the endpoint returns no last_page,
            // hasNext() reads links.next
            assertEquals(0, firstPage.meta.lastPage);
            assertTrue(firstPage.hasNext());
            assertFalse(firstPage.hasPrevious());

            EmailsList secondPage = firstPage.next();

            assertNotNull(secondPage);
            assertEquals(2, secondPage.getCurrentPage());
            assertEquals(1, secondPage.emails.length);
            assertEquals("6a8fa9b1902fab56e0ce50ff", secondPage.emails[0].id);

            assertTrue(secondPage.hasPrevious());
            assertFalse(secondPage.hasNext());

            // the last page has no next page
            assertNull(secondPage.next());

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /**
     * Tests that previous() requests the preceding page and returns null once there is none
     */
    @Test
    public void testPreviousReturnsThePrecedingPage() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailsList secondPage = withRequiredFilters(ms).page(2).getEmails();

            assertEquals(2, secondPage.getCurrentPage());
            assertEquals("6a8fa9b1902fab56e0ce50ff", secondPage.emails[0].id);
            assertTrue(secondPage.hasPrevious());

            EmailsList firstPage = secondPage.previous();

            assertNotNull(firstPage);
            assertEquals(1, firstPage.getCurrentPage());
            assertEquals(1, firstPage.emails.length);
            assertEquals(EMAIL_ID, firstPage.emails[0].id);

            assertFalse(firstPage.hasPrevious());
            assertTrue(firstPage.hasNext());

            // the first page has no previous page
            assertNull(firstPage.previous());

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /*
     * ----------------------------------------------------------------------------------
     * The single email response
     * ----------------------------------------------------------------------------------
     */


    /**
     * Tests that a single email deserializes into an EmailInfo, together with its recipient
     * and its activity events
     */
    @Test
    public void testGetEmailParsesTheSingleEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailInfo email = ms.emails().getEmail(EMAIL_ID);

            assertNotNull(email);
            assertEquals(EMAIL_ID, email.id);
            assertEquals("sender@example.com", email.from);
            assertEquals("rcpt@example.org", email.to);
            assertEquals("Welcome", email.subject);

            // unlike the list endpoint, the single email endpoint returns the content
            assertEquals("Hello there", email.text);
            assertEquals("<p>Hello there</p>", email.html);

            assertEquals("7nxe3yjmeq28vp0k", email.templateId);
            assertEquals("7nxe3yjmeq28vp0k", email.domainId);
            assertEquals("6a8fa9b1902fab56e0ce50aa", email.messageId);
            assertEquals(EmailStatus.SENT, email.status);
            assertEquals("newsletter", email.tags[0]);
            assertEquals(EmailInteraction.OPENED, email.interaction[0]);
            assertNull(email.suppressionReason);

            // the custom headers the email was sent with, in the order the API returns them
            assertNotNull(email.headers);
            assertEquals(2, email.headers.length);
            assertEquals("X-Custom", email.headers[0].name);
            assertEquals("foo", email.headers[0].value);
            assertEquals("X-Entity-Ref-ID", email.headers[1].name);
            assertEquals("abc-123", email.headers[1].value);

            assertEquals(CREATED_AT, email.createdAt.getTime());
            assertEquals(CREATED_AT, email.updatedAt.getTime());

            // the recipient of the email
            assertNotNull(email.recipient);
            assertEquals("6a8fa9b1902fab56e0ce50bb", email.recipient.id);
            assertEquals("rcpt@example.org", email.recipient.email);
            assertEquals(1787220672000L, email.recipient.createdAt.getTime());
            assertEquals(1787307072000L, email.recipient.updatedAt.getTime());
            assertNull(email.recipient.deletedAt);

            // the activity events of the email, newest first
            assertNotNull(email.activity);
            assertEquals(3, email.activity.length);

            EmailActivity opened = email.activity[0];
            assertEquals("6a8fa9b1902fab56e0ce5003", opened.id);
            assertEquals("opened", opened.type);
            assertEquals(1787800020000L, opened.createdAt.getTime());
            assertNull(opened.suppressionReason);

            assertEquals("delivered", email.activity[1].type);
            assertEquals(1787799990000L, email.activity[1].createdAt.getTime());

            assertEquals("sent", email.activity[2].type);
            assertEquals(CREATED_AT, email.activity[2].createdAt.getTime());

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /**
     * Tests that a rejected email parses its suppression reason, both on the email and on the
     * suppressed event
     */
    @Test
    public void testGetEmailParsesASuppressedEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailInfo email = ms.emails().getEmail(SUPPRESSED_EMAIL_ID);

            assertEquals(SUPPRESSED_EMAIL_ID, email.id);
            assertEquals(EmailStatus.REJECTED, email.status);
            assertEquals("hard_bounced", email.suppressionReason);
            assertEquals("blocked@example.org", email.to);

            // no template was used, and no tags or custom headers were sent
            assertNull(email.templateId);
            assertNull(email.tags);
            assertNull(email.headers);

            assertNotNull(email.interaction);
            assertEquals(0, email.interaction.length);

            assertEquals(2, email.activity.length);
            assertEquals("suppressed", email.activity[0].type);
            assertEquals("hard_bounced", email.activity[0].suppressionReason);
            assertEquals(CREATED_AT, email.activity[0].createdAt.getTime());

            assertEquals("queued", email.activity[1].type);
            assertNull(email.activity[1].suppressionReason);

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /**
     * Tests that an email without activity events and without a recipient does not blow up
     */
    @Test
    public void testGetEmailWithoutActivityReturnsAnEmptyArray() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            EmailInfo email = ms.emails().getEmail(BARE_EMAIL_ID);

            assertEquals(BARE_EMAIL_ID, email.id);
            assertEquals(EmailStatus.QUEUED, email.status);

            assertNotNull(email.activity);
            assertEquals(0, email.activity.length);

            assertNull(email.recipient);
            assertNull(email.tags);
            assertNull(email.interaction);
            assertNull(email.text);
            assertNull(email.html);

            assertEquals(CREATED_AT, email.createdAt.getTime());

        } catch (MailerSendException e) {

            fail(e.getMessage());
        }
    }


    /*
     * ----------------------------------------------------------------------------------
     * Helpers
     * ----------------------------------------------------------------------------------
     */


    /**
     * Sets the filters that getEmails() requires
     */
    private Emails withRequiredFilters(MailerSend ms) {

        return ms.emails()
                .domainId(TestHelper.domainId)
                .dateFrom(DATE_FROM)
                .dateTo(DATE_TO);
    }


    /**
     * Replaces the recording client with one that captures the request urls, for the tests that
     * assert what the SDK requests instead of how it parses the response
     */
    private RequestCapturingClient captureRequests() {

        RequestCapturingClient client = new RequestCapturingClient();

        MailerSendHttpClientFactory.getInstance().setClient(client);

        return client;
    }


    /**
     * Records the urls the SDK requests and answers each of them with a canned response
     */
    private static class RequestCapturingClient extends HttpClientVcr {

        private final List<URI> requestedUris = new ArrayList<URI>();

        private String responseBody = "{\"data\":[],\"links\":{},\"meta\":{\"current_page\":1}}";

        private URI lastRequestedUri() {

            assertFalse(requestedUris.isEmpty(), "no request was made");

            return requestedUris.get(requestedUris.size() - 1);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler) {

            requestedUris.add(request.uri());

            HttpClientVcrResponse response = new HttpClientVcrResponse();
            response.body = responseBody;
            response.headers = Map.of("content-type", List.of("application/json"));
            response.statusCode = 200;

            return (HttpResponse<T>) response;
        }
    }
}
