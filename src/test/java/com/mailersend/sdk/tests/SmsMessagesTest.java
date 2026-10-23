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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.messages.SmsMessage;
import com.mailersend.sdk.sms.messages.SmsMessageList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SmsMessagesTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException
    {
        VcrRecorder.useRecording("SmsMessagesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException
    {
        VcrRecorder.stopRecording();
    }

    /**
     * Tests that a list of SMS messages can be retrieved and contains expected data
     */
    @Test
    public void testGetSmsMessages() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsMessageList list = ms.sms().messages().getMessages();

            assertNotNull(list.messages);
            assertEquals("sms-msg-id-test-1", list.messages[0].id);
            assertEquals(Instant.parse("2022-09-14T08:00:00Z"), list.messages[0].createdAt.toInstant());

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that page and limit are sent
     */
    @Test
    public void testGetSmsMessagesWithPagination() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsMessageList list = ms.sms().messages()
                    .page(2)
                    .limit(10)
                    .getMessages();

            assertEquals("sms-msg-id-test-1", list.messages[0].id);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that a single SMS message can be retrieved by ID with its list of SMS
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testGetSingleSmsMessage() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsMessage message = ms.sms().messages().getMessage("sms-msg-id-test-1");

            assertEquals("sms-msg-id-test-1", message.id);
            assertEquals(1, message.smsList.length);
            assertEquals("sms-id-1", message.smsList[0].id);
            assertEquals("test sms", message.smsList[0].compiledText);
            assertEquals(Instant.parse("2022-09-14T08:00:01Z"), message.smsList[0].createdAt.toInstant());
            assertSame(message.smsList[0], message.sms);

        } catch (MailerSendException e) {

            fail();
        }
    }
}
