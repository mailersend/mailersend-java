package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.util.stream.Stream;

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
import com.mailersend.sms.webhooks.SmsWebhook;
import com.mailersend.sms.webhooks.SmsWebhookList;

public class SmsWebhooksTests {

	private static final String smsWebhookId = "jpr9084zjgw63dnx";

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("SmsWebhooksTest_" + info.getDisplayName());
	}


	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}


	@Test
	public void testGetSmsWebhooks() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhookList list = ms.sms().webhooks().getWebhooks(TestHelper.smsPhoneNumberId);

        	assertEquals(smsWebhookId, list.webhooks[0].id);
        	assertArrayEquals(new String[] {"sms.sent"}, list.webhooks[0].events);

        } catch (MailerSendException e) {

            fail();
        }
	}


	/**
	 * Tests that page and limit are sent with the sms_number_id
	 */
	@Test
	public void testGetSmsWebhooksWithPagination() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhookList list = ms.sms().webhooks()
        			.page(2)
        			.limit(10)
        			.getWebhooks(TestHelper.smsPhoneNumberId);

        	assertEquals(smsWebhookId, list.webhooks[0].id);

        } catch (MailerSendException e) {

            fail();
        }
	}


	@Test
	public void testGetSingleSmsWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhookList list = ms.sms().webhooks().getWebhooks(TestHelper.smsPhoneNumberId);

        	SmsWebhook webhook = ms.sms().webhooks().getWebhook(list.webhooks[0].id);

        	assertEquals(smsWebhookId, webhook.id);
        	assertEquals("sms webhook", webhook.name);

        } catch (MailerSendException e) {

            fail();
        }
	}


	@Test
	public void testCreateSmsWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhook webhook = ms.sms().webhooks().builder()
        	.addEvent("sms.sent")
        	.name("sms webhook")
        	.url("https://example.com")
        	.createWebhook(TestHelper.smsPhoneNumberId);

        	assertEquals(smsWebhookId, webhook.id);
        	assertEquals(Instant.parse("2022-09-14T08:32:16Z"), webhook.createdAt.toInstant());

        } catch (MailerSendException e) {

            fail();
        }
	}


	/**
	 * Tests that enabled is sent on create
	 */
	@Test
	public void testCreateSmsWebhookWithEnabled() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhook webhook = ms.sms().webhooks().builder()
        	.addEvent("sms.sent")
        	.name("sms webhook")
        	.url("https://example.com")
        	.enabled(false)
        	.createWebhook(TestHelper.smsPhoneNumberId);

        	assertEquals(smsWebhookId, webhook.id);

        } catch (MailerSendException e) {

            fail();
        }
	}


	/**
	 * Tests that an update without events does not send an empty events list
	 */
	@Test
	public void testUpdateSmsWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhookList list = ms.sms().webhooks().getWebhooks(TestHelper.smsPhoneNumberId);

        	SmsWebhook webhook = ms.sms().webhooks().builder()
        	.name("sms updated webhook")
        	.updateWebhook(list.webhooks[0].id);

        	assertEquals("sms updated webhook", webhook.name);
        	assertArrayEquals(new String[] {"sms.sent"}, webhook.events);
        	assertEquals(Instant.parse("2022-09-14T08:49:18Z"), webhook.updatedAt.toInstant());

        } catch (MailerSendException e) {

            fail();
        }
	}


	/**
	 * Tests that url, name, events and enabled are sent on update
	 */
	@Test
	public void testUpdateSmsWebhookWithAllFields() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhook webhook = ms.sms().webhooks().builder()
        	.url("https://example.com/updated")
        	.name("sms updated webhook")
        	.addEvent("sms.delivered")
        	.enabled(false)
        	.updateWebhook(smsWebhookId);

        	assertArrayEquals(new String[] {"sms.delivered"}, webhook.events);
        	assertFalse(webhook.enabled);

        } catch (MailerSendException e) {

            fail();
        }
	}


	/**
	 * Tests that clearEvents removes the added events from the request
	 */
	@Test
	public void testUpdateSmsWebhookAfterClearEvents() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhook webhook = ms.sms().webhooks().builder()
        	.addEvent("sms.failed")
        	.clearEvents()
        	.name("sms updated webhook")
        	.updateWebhook(smsWebhookId);

        	assertArrayEquals(new String[] {"sms.sent"}, webhook.events);

        } catch (MailerSendException e) {

            fail();
        }
	}


	@ParameterizedTest(name = "{0}")
	@MethodSource("invalidCreateWebhookParams")
	public void createWebhookRejectsMissingFields(String label, String name, String url, String smsNumberId, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
        	ms.sms().webhooks().builder()
        	.name(name)
        	.url(url)
        	.addEvent("sms.sent")
        	.createWebhook(smsNumberId);
        });

        assertEquals(expectedMessage, e.getMessage());
	}

	static Stream<Arguments> invalidCreateWebhookParams() {
		return Stream.of(
			Arguments.of("missing name", null, "https://example.com", TestHelper.smsPhoneNumberId, "Webhook name cannot be empty"),
			Arguments.of("blank name", " ", "https://example.com", TestHelper.smsPhoneNumberId, "Webhook name cannot be empty"),
			Arguments.of("missing url", "sms webhook", null, TestHelper.smsPhoneNumberId, "Webhook URL cannot be empty"),
			Arguments.of("blank url", "sms webhook", " ", TestHelper.smsPhoneNumberId, "Webhook URL cannot be empty"),
			Arguments.of("missing sms number id", "sms webhook", "https://example.com", null, "Sms number ID cannot be empty"),
			Arguments.of("blank sms number id", "sms webhook", "https://example.com", " ", "Sms number ID cannot be empty")
		);
	}


	@ParameterizedTest(name = "{0}")
	@MethodSource("invalidWebhookIds")
	public void updateWebhookRejectsMissingId(String label, String webhookId, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
        	ms.sms().webhooks().builder()
        	.name("sms updated webhook")
        	.updateWebhook(webhookId);
        });

        assertEquals(expectedMessage, e.getMessage());
	}

	static Stream<Arguments> invalidWebhookIds() {
		return Stream.of(
			Arguments.of("missing webhook id", null, "Webhook ID cannot be empty"),
			Arguments.of("blank webhook id", " ", "Webhook ID cannot be empty")
		);
	}


	@Test
	public void testDeleteSmsWebhook() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsWebhookList list = ms.sms().webhooks().getWebhooks(TestHelper.smsPhoneNumberId);

        	boolean result = ms.sms().webhooks().deleteWebhook(list.webhooks[0].id);

        	assertTrue(result);

        } catch (MailerSendException e) {

            fail();
        }
	}
}
