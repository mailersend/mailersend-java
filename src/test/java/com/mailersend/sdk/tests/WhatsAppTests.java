package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

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

            assertNotNull(messageId);

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

            assertNotNull(messageId);

        } catch (MailerSendException e) {
            fail();
        }
    }
}