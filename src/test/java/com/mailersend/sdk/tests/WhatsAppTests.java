package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.whatsapp.WhatsAppPersonalization;
import com.mailersend.sdk.vcr.VcrRecorder;

public class WhatsAppTests {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("WhatsAppTests_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    @Test
    public void TestSendWhatsApp() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            String messageId = ms.whatsapp().builder()
                .from("12345678901")
                .addRecipient("19191234567")
                .templateId("template_id_123")
                .send();

            assertEquals("67f91abd69f79df391e9d78d", messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestSendWhatsAppWithPersonalization() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppPersonalization p1 = new WhatsAppPersonalization("19191234567")
                .setHeader(new String[]{"John"})
                .setBody(new String[]{"order #1234", "tomorrow"})
                .setButtons(new String[]{"https://example.com/track/1234"});

            WhatsAppPersonalization p2 = new WhatsAppPersonalization("19199876543")
                .setHeader(new String[]{"Jane"})
                .setBody(new String[]{"order #5678", "Friday"});

            String messageId = ms.whatsapp().builder()
                .from("12345678901")
                .addRecipient("19191234567")
                .addRecipient("19199876543")
                .templateId("template_id_123")
                .addPersonalization(p1)
                .addPersonalization(p2)
                .send();

            assertEquals("67f91b2c69f79df391e9d791", messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestSendWhatsAppWithoutMessageIdHeader() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            String messageId = ms.whatsapp().builder()
                .from("12345678901")
                .addRecipient("19191234567")
                .templateId("template_id_123")
                .send();

            assertEquals("67f91abd69f79df391e9d78d", messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestSendWhatsAppWithMediaHeader() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppPersonalization p1 = new WhatsAppPersonalization("19191234567")
                .setMediaHeader("https://www.example.com/invoices/12345.pdf", "invoice-12345.pdf")
                .setBody(new String[]{"John"});

            String messageId = ms.whatsapp().builder()
                .from("12345678901")
                .addRecipient("19191234567")
                .templateId("template_id_123")
                .addPersonalization(p1)
                .send();

            assertEquals("67f91c3d69f79df391e9d795", messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestSendWhatsAppWithNamedVariables() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            Map<String, String> header = new LinkedHashMap<String, String>();
            header.put("order_number", "12345");

            Map<String, String> body = new LinkedHashMap<String, String>();
            body.put("first_name", "John");
            body.put("order_date", "December 31, 2026");

            WhatsAppPersonalization p1 = new WhatsAppPersonalization("19191234567")
                .setNamedHeader(header)
                .setNamedBody(body);

            String messageId = ms.whatsapp().builder()
                .from("12345678901")
                .addRecipient("19191234567")
                .templateId("template_id_123")
                .addPersonalization(p1)
                .send();

            assertEquals("67f91c3d69f79df391e9d796", messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }

    /**
     * Tests that setHeader replaces a media header set earlier
     */
    @Test
    public void TestSendWhatsAppPositionalHeaderReplacesMediaHeader() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppPersonalization p1 = new WhatsAppPersonalization("19191234567")
                .setMediaHeader("https://www.example.com/invoices/12345.pdf")
                .setHeader(new String[]{"John"});

            String messageId = ms.whatsapp().builder()
                .from("12345678901")
                .addRecipient("19191234567")
                .templateId("template_id_123")
                .addPersonalization(p1)
                .send();

            assertEquals("67f91c3d69f79df391e9d797", messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }

    /**
     * Tests that setBody replaces named body variables set earlier
     */
    @Test
    public void TestSendWhatsAppPositionalBodyReplacesNamedBody() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            Map<String, String> body = new LinkedHashMap<String, String>();
            body.put("first_name", "John");

            WhatsAppPersonalization p1 = new WhatsAppPersonalization("19191234567")
                .setNamedBody(body)
                .setBody(new String[]{"John"});

            String messageId = ms.whatsapp().builder()
                .from("12345678901")
                .addRecipient("19191234567")
                .templateId("template_id_123")
                .addPersonalization(p1)
                .send();

            assertEquals("67f91c3d69f79df391e9d798", messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }

    /**
     * Tests that send() returns null when neither the header nor the body has a message id
     */
    @Test
    public void TestSendWhatsAppWithoutMessageId() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            String messageId = ms.whatsapp().builder()
                .from("12345678901")
                .addRecipient("19191234567")
                .templateId("template_id_123")
                .send();

            assertNull(messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }
}
