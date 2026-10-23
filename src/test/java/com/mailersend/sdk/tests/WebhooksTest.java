/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static com.mailersend.sdk.tests.CapturingHttpClient.json;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sdk.webhooks.Webhook;
import com.mailersend.sdk.webhooks.WebhookEvents;
import com.mailersend.sdk.webhooks.WebhooksBuilder;
import com.mailersend.sdk.webhooks.WebhooksList;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

public class WebhooksTest {

    private static final String WEBHOOKS_URL = "https://api.mailersend.com/v1/webhooks";

    private static final String WEBHOOK_ID = "pzkmgq7ow2l059vx";

    /** The create and update response of the docs, without the domain */
    private static final String WEBHOOK_RESPONSE = "{\"data\":{\"id\":\"pzkmgq7ow2l059vx\","
            + "\"url\":\"https://www.example.com/webhook\",\"events\":[\"activity.sent\",\"activity.delivered\"],"
            + "\"name\":\"Webhook\",\"version\":2,\"enabled\":true,\"editable\":true,"
            + "\"secret\":\"NSKNbRpNZoItpYpMiDcqfO7A1kzi1Ikk\","
            + "\"created_at\":\"2026-01-15T10:20:30.000000Z\",\"updated_at\":\"2026-01-15T10:20:30.000000Z\"}}";

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("WebhooksTest_" + info.getDisplayName());
	}

	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}

    /**
     * Tests the creation of a webhook, and that the dates of the created webhook are parsed
     */
    @Test
    public void testWebhookCreation() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        String webhookTestUrl = "https://example.com/test-webhook-creation-123";

        Webhook webhook = ms.webhooks().builder()
            .name("Test webook")
            .url(webhookTestUrl)
            .addEvent(WebhookEvents.ACTIVITY_OPENED)
            .addEvent(WebhookEvents.ACTIVITY_CLICKED)
            .createWebhook(TestHelper.domainId);

        assertEquals("yzkq340272gd7961", webhook.id);
        assertEquals(webhookTestUrl, webhook.url);
        assertArrayEquals(new String[] { WebhookEvents.ACTIVITY_OPENED, WebhookEvents.ACTIVITY_CLICKED }, webhook.events);
        assertEquals(Date.from(Instant.parse("2022-10-05T06:58:05Z")), webhook.createdAt);
    }


    /**
     * Tests that createWebhook() posts every documented field to the webhooks endpoint and
     * parses the created webhook
     */
    @Test
    public void testCreateWebhookSendsEveryField() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(201, WEBHOOK_RESPONSE);

        Webhook webhook = ms.webhooks().builder()
            .url("https://www.example.com/webhook")
            .name("Webhook")
            .addEvent(WebhookEvents.ACTIVITY_SENT)
            .addEvent(WebhookEvents.ACTIVITY_DELIVERED)
            .enabled(true)
            .editable(true)
            .version(2)
            .createWebhook(TestHelper.domainId);

        assertEquals("POST", client.lastRequest().method());
        assertEquals(WEBHOOKS_URL, client.lastUrl());
        assertEquals(json("{"
                + "'url':'https://www.example.com/webhook',"
                + "'name':'Webhook',"
                + "'events':['activity.sent','activity.delivered'],"
                + "'enabled':true,"
                + "'domain_id':'" + TestHelper.domainId + "',"
                + "'editable':true,"
                + "'version':2"
                + "}"), client.lastBody());
        assertEquals(WEBHOOK_ID, webhook.id);
        assertEquals(2, webhook.version);
        assertEquals(Date.from(Instant.parse("2026-01-15T10:20:30Z")), webhook.createdAt);
        assertEquals(Date.from(Instant.parse("2026-01-15T10:20:30Z")), webhook.updatedAt);
    }


    /**
     * Tests that createWebhook() accepts the events that the docs list and older SDK versions rejected
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("addedEvents")
    public void testCreateWebhookAcceptsTheEvent(String event) throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(201, WEBHOOK_RESPONSE);

        ms.webhooks().builder()
            .url("https://www.example.com/webhook")
            .name("Webhook")
            .addEvent(event)
            .createWebhook(TestHelper.domainId);

        assertEquals(json("['" + event + "']"), client.lastBody().getAsJsonObject().get("events"));
    }

    static Stream<String> addedEvents() {
        return Stream.of(
            WebhookEvents.ACTIVITY_SUPPRESSED,
            WebhookEvents.INBOUND_MESSAGE_REJECTED,
            WebhookEvents.EMAIL_LIST_CREATED
        );
    }


    /**
     * Tests updating a webhook
     */
    @Test
    public void testUpdateWebhook() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);

        Webhook webhook = ms.webhooks()
                .builder()
                .name("Updated webhook name 2")
                .updateWebhook(list.webhooks[0].id);

        assertEquals("Updated webhook name 2", webhook.name);
        assertEquals(Date.from(Instant.parse("2022-08-19T13:29:56Z")), webhook.updatedAt);
    }


    /**
     * Tests that updateWebhook() puts the documented fields to the webhook's endpoint, without
     * the editable flag and the domain id that the update endpoint does not accept
     */
    @Test
    public void testUpdateWebhookSendsTheDocumentedFields() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, WEBHOOK_RESPONSE);

        Webhook webhook = ms.webhooks().builder()
            .url("https://www.example.com/webhook")
            .name("Webhook")
            .addEvent(WebhookEvents.ACTIVITY_SENT)
            .enabled(false)
            .editable(true)
            .version(2)
            .updateWebhook(WEBHOOK_ID);

        assertEquals("PUT", client.lastRequest().method());
        assertEquals(WEBHOOKS_URL + "/" + WEBHOOK_ID, client.lastUrl());
        assertEquals(json("{"
                + "'url':'https://www.example.com/webhook',"
                + "'name':'Webhook',"
                + "'events':['activity.sent'],"
                + "'enabled':false,"
                + "'version':2"
                + "}"), client.lastBody());
        assertEquals(Date.from(Instant.parse("2026-01-15T10:20:30Z")), webhook.updatedAt);
    }


    /**
     * Tests that updateWebhook() leaves the events out when none were added, so that the webhook keeps its events
     */
    @Test
    public void testUpdateWebhookWithoutEventsLeavesThemOut() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, WEBHOOK_RESPONSE);

        ms.webhooks().builder()
            .name("Webhook")
            .updateWebhook(WEBHOOK_ID);

        assertEquals(json("{'name':'Webhook'}"), client.lastBody());
    }


    /**
     * Tests deleting a webhook
     */
    @Test
    public void testDeleteWebhook() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);

        assertTrue(ms.webhooks().deleteWebhook(list.webhooks[0].id));
    }


    /**
     * Tests retrieving a single webhook
     */
    @Test
    public void testGetWebhook() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);

        Webhook webhook = ms.webhooks().getWebhook(list.webhooks[0].id);

        assertEquals("pr9084zk1m4w63dn", webhook.id);
        assertEquals("https://example.com/test-804321", webhook.url);
        assertEquals("Test webook", webhook.name);
        assertEquals(Date.from(Instant.parse("2022-08-19T13:29:45Z")), webhook.createdAt);
    }


    /**
     * Tests the retrieval of the webhooks
     */
    @Test
    public void testWebhooksList() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);

        assertEquals("pr9084zk1m4w63dn", list.webhooks[0].id);
        assertEquals(Date.from(Instant.parse("2022-08-19T13:29:45Z")), list.webhooks[0].createdAt);
    }


    /**
     * Tests that getWebhooks() sends the page, the limit and the domain id in the query
     */
    @Test
    public void testGetWebhooksSendsThePageLimitAndDomainId() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, "{\"data\":[],\"links\":{},\"meta\":{}}");

        ms.webhooks().page(2).limit(50).getWebhooks(TestHelper.domainId);

        assertEquals("GET", client.lastRequest().method());
        assertEquals(WEBHOOKS_URL + "?page=2&limit=50&domain_id=" + TestHelper.domainId, client.lastUrl());
    }


    /**
     * Tests that createWebhook() rejects a request without the required fields or with an unknown event
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCreateRequests")
    public void testCreateWebhookRejectsInvalidInput(String label, String name, String url, String event,
            String domainId, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        WebhooksBuilder builder = ms.webhooks().builder().name(name).url(url);

        if (event != null) {
            builder.addEvent(event);
        }

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            builder.createWebhook(domainId);
        });

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidCreateRequests() {
        return Stream.of(
            Arguments.of("name not set", null, "https://example.com/webhook", WebhookEvents.ACTIVITY_OPENED, TestHelper.domainId, "Webhook name cannot be empty"),
            Arguments.of("url not set", "My Webhook", null, WebhookEvents.ACTIVITY_OPENED, TestHelper.domainId, "Webhook URL cannot be empty"),
            Arguments.of("domain id empty", "My Webhook", "https://example.com/webhook", WebhookEvents.ACTIVITY_OPENED, "", "Domain ID cannot be empty"),
            Arguments.of("no events", "My Webhook", "https://example.com/webhook", null, TestHelper.domainId, "At least one webhook event is required"),
            Arguments.of("unknown event", "My Webhook", "https://example.com/webhook", "invalid.event", TestHelper.domainId, "Webhook event is not valid")
        );
    }


    /**
     * Tests that updateWebhook() rejects a missing webhook id and an unknown event
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidUpdateRequests")
    public void testUpdateWebhookRejectsInvalidInput(String label, String webhookId, String event, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        WebhooksBuilder builder = ms.webhooks().builder().name("My Webhook");

        if (event != null) {
            builder.addEvent(event);
        }

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            builder.updateWebhook(webhookId);
        });

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidUpdateRequests() {
        return Stream.of(
            Arguments.of("webhook id empty", "", null, "Webhook ID cannot be empty"),
            Arguments.of("webhook id null", null, null, "Webhook ID cannot be empty"),
            Arguments.of("unknown event", WEBHOOK_ID, "invalid.event", "Webhook event is not valid")
        );
    }


    /**
     * Tests that getWebhooks() throws when domainId is empty
     */
    @Test
    public void testGetWebhooksWithEmptyDomainId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.webhooks().getWebhooks("");
        });

        assertEquals("Domain ID cannot be empty", e.getMessage());
    }


    /**
     * Tests that limit() and page() reject invalid values (parameterized)
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidPaginationParams")
    public void testGetWebhooksInvalidPagination(String label, int limit, int page, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            if (label.contains("limit")) {
                ms.webhooks().limit(limit);
            } else {
                ms.webhooks().page(page);
            }
        });

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidPaginationParams() {
        return Stream.of(
            Arguments.of("limit too low", 5, 1, "Limit must be between 10 and 100"),
            Arguments.of("limit too high", 101, 1, "Limit must be between 10 and 100"),
            Arguments.of("page too low", 10, 0, "Page must be at least 1")
        );
    }


    /**
     * Tests that version() throws when version is not 1 or 2
     */
    @Test
    public void testWebhookVersionInvalid() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.webhooks().builder().version(3);
        });

        assertEquals("Webhook version must be 1 or 2", e.getMessage());
    }
}
