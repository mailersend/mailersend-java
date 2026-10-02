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
import com.mailersend.sdk.whatsapp.messages.WhatsAppMessage;
import com.mailersend.sdk.whatsapp.messages.WhatsAppMessageList;

public class WhatsAppMessagesTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("WhatsAppMessagesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    @Test
    public void TestGetWhatsAppMessages() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppMessageList list = ms.whatsapp().messages()
                .page(1)
                .limit(25)
                .getMessages();

            assertEquals(2, list.messages.length);
            assertEquals("67f91abd69f79df391e9d78d", list.messages[0].id);
            assertEquals("23zxk54v6gjy6v7m", list.messages[0].templateId);
            assertEquals("3enl6x27wmrxrl2v", list.messages[0].whatsappAccountId);
            assertNotNull(list.messages[0].createdAt);
            assertNull(list.messages[1].from);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestGetSingleWhatsAppMessage() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppMessage message = ms.whatsapp().messages().getMessage("67f91abd69f79df391e9d78d");

            assertEquals("67f91abd69f79df391e9d78d", message.id);
            assertEquals(2, message.recipients.length);
            assertEquals("read", message.recipients[0].status);
            assertNull(message.recipients[0].errorCode);
            assertEquals(4, message.recipients[0].activity.length);
            assertNotNull(message.recipients[0].activity[0].createdAt);
            assertEquals(131026, message.recipients[1].errorCode);

        } catch (MailerSendException e) {
            fail();
        }
    }
}
