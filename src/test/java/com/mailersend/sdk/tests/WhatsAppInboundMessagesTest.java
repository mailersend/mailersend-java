/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessage;
import com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessageList;

public class WhatsAppInboundMessagesTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("WhatsAppInboundMessagesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    @Test
    public void TestGetWhatsAppInboundMessages() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppInboundMessageList list = ms.whatsapp().inboundMessages()
                .whatsappAccountId("3enl6x27wmrxrl2v")
                .types(new String[]{"text", "image"})
                .dateFrom(1790000000)
                .dateTo(1790086400)
                .page(1)
                .limit(25)
                .getInboundMessages();

            assertEquals(2, list.inboundMessages.length);

            WhatsAppInboundMessage text = list.inboundMessages[0];
            assertEquals("text", text.type);
            assertEquals("Hello, I would like to check my order status.", text.text.body);
            assertEquals("wamid.HBgLMzcwNjMzMjI3OTcVAgARGBIwREE3N0EwN0EyNjhENjEwOEMA", text.context.messageId);
            assertNotNull(text.receivedAt);
            assertNull(text.attachment);

            WhatsAppInboundMessage image = list.inboundMessages[1];
            assertEquals("stored", image.attachment.status);
            assertEquals(102400L, image.attachment.size);
            assertNull(image.context);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestGetSingleWhatsAppInboundMessage() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppInboundMessage inboundMessage = ms.whatsapp().inboundMessages()
                .getInboundMessage("62f114a3165fe0d8db0288d1");

            assertEquals("62f114a3165fe0d8db0288d1", inboundMessage.id);
            assertEquals("interactive", inboundMessage.type);
            assertEquals("delivery_today", inboundMessage.listReply.id);
            assertNull(inboundMessage.interactive);

        } catch (MailerSendException e) {
            fail();
        }
    }
}
