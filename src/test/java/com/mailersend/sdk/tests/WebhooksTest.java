/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sdk.webhooks.Webhook;
import com.mailersend.sdk.webhooks.WebhookEvents;
import com.mailersend.sdk.webhooks.WebhooksList;

import java.io.IOException;
import java.util.stream.Stream;
import java.util.concurrent.ThreadLocalRandom;

public class WebhooksTest {

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
     * Tests the creation of a webhook
     */
    @Test
    public void TestWebhookCreation() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            String webhookTestUrl = "https://example.com/test-webhook-creation-123";

            Webhook webhook = ms.webhooks().builder()
                .name("Test webook")
                .url(webhookTestUrl)
                .addEvent(WebhookEvents.ACTIVITY_OPENED)
                .addEvent(WebhookEvents.ACTIVITY_CLICKED)
                .createWebhook(TestHelper.domainId);

            System.out.println(webhook.name);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests updating a webhook
     */
    @Test
    public void TestUpdateWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);

            if (list.webhooks.length == 0) {

                fail();
            }

            Webhook webhook = ms.webhooks()
                    .builder()
                    .name("Updated webhook name 2")
                    .updateWebhook(list.webhooks[0].id);

            System.out.println(webhook.name);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests deleting a webhook
     */
    @Test
    public void TestDeleteWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);

            if (list.webhooks.length == 0) {

                fail();
            }

            boolean response = ms.webhooks().deleteWebhook(list.webhooks[0].id);

            System.out.println(response);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests retrieving a single webhook
     */
    @Test
    public void TestGetWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);

            if (list.webhooks.length == 0) {

                fail();
            }

            Webhook webhook = ms.webhooks().getWebhook(list.webhooks[0].id);

            System.out.println(webhook.name);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests the retrieval of the webhooks
     */
    @Test
    public void TestWebhooksList() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

           WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);

           for (Webhook webhook : list.webhooks) {

               System.out.println(webhook.name);
           }

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests that createWebhook() throws when name is not set
     */
    @Test
    public void testCreateWebhookWithEmptyName() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.webhooks().builder()
                .url("https://example.com/webhook")
                .addEvent(WebhookEvents.ACTIVITY_OPENED)
                .createWebhook(TestHelper.domainId);
        });

        assertEquals("Webhook name cannot be empty", e.getMessage());
    }


    /**
     * Tests that createWebhook() throws when url is not set
     */
    @Test
    public void testCreateWebhookWithEmptyUrl() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.webhooks().builder()
                .name("My Webhook")
                .addEvent(WebhookEvents.ACTIVITY_OPENED)
                .createWebhook(TestHelper.domainId);
        });

        assertEquals("Webhook URL cannot be empty", e.getMessage());
    }


    /**
     * Tests that createWebhook() throws when domainId is empty
     */
    @Test
    public void testCreateWebhookWithEmptyDomainId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.webhooks().builder()
                .name("My Webhook")
                .url("https://example.com/webhook")
                .addEvent(WebhookEvents.ACTIVITY_OPENED)
                .createWebhook("");
        });

        assertEquals("Domain ID cannot be empty", e.getMessage());
    }


    /**
     * Tests that createWebhook() throws when an invalid event is added
     */
    @Test
    public void testCreateWebhookWithInvalidEvent() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.webhooks().builder()
                .name("My Webhook")
                .url("https://example.com/webhook")
                .addEvent("invalid.event")
                .createWebhook(TestHelper.domainId);
        });

        assertEquals("Webhook event is not valid", e.getMessage());
    }


    /**
     * Tests that createWebhook() throws when no events are set
     */
    @Test
    public void testCreateWebhookWithNoEvents() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.webhooks().builder()
                .name("My Webhook")
                .url("https://example.com/webhook")
                .createWebhook(TestHelper.domainId);
        });

        assertEquals("At least one webhook event is required", e.getMessage());
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
