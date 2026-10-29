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

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that a single SMS message can be retrieved by ID
     */
    @Test
    public void testGetSingleSmsMessage() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsMessage message = ms.sms().messages().getMessage("sms-msg-id-test-1");

            assertNotNull(message);
            assertEquals("sms-msg-id-test-1", message.id);

        } catch (MailerSendException e) {

            fail();
        }
    }


    /**
     * Tests that a single SMS message reads the list of SMS the API returns
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testGetSingleSmsMessageReadsTheSmsList() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient.install(200, "{\"data\":{\"id\":\"sms-msg-id-test-1\",\"from\":\"+18133657349\",\"to\":[\"+18332552485\"],\"text\":\"test sms\","
            + "\"sms\":[{\"id\":\"sms-id-1\",\"from\":\"+18133657349\",\"to\":\"+18332552485\",\"text\":\"test sms\",\"compiled_text\":\"test sms\","
            + "\"status\":\"failed\",\"error_type\":\"provider_error\",\"error_code\":30007,\"error_description\":\"Carrier violation\"}]}}");

        SmsMessage message = ms.sms().messages().getMessage("sms-msg-id-test-1");

        assertEquals(1, message.smsList.length);
        assertEquals("test sms", message.smsList[0].compiledText);
        assertEquals(Integer.valueOf(30007), message.smsList[0].errorCode);
        assertSame(message.smsList[0], message.sms);
    }
}
