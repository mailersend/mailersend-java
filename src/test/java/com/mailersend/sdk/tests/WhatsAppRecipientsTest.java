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
import com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipient;
import com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipientList;

public class WhatsAppRecipientsTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("WhatsAppRecipientsTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    @Test
    public void TestGetWhatsAppRecipients() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppRecipientList list = ms.whatsapp().recipients()
                .status("active")
                .page(1)
                .limit(25)
                .getRecipients();

            assertEquals(1, list.recipients.length);
            assertEquals("48600000001", list.recipients[0].phoneNumber);
            assertEquals("US.13491208655302741918", list.recipients[0].bsuid);
            assertNull(list.recipients[0].username);
            assertNotNull(list.recipients[0].createdAt);
            assertNull(list.links.next);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestGetSingleWhatsAppRecipient() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppRecipient recipient = ms.whatsapp().recipients().getRecipient("67f91abe2202f37055402301");

            assertEquals("67f91abe2202f37055402301", recipient.id);
            assertEquals(1, recipient.messages.length);
            assertEquals("67f91abd69f79df391e9d78d", recipient.messages[0].whatsappMessageId);
            assertEquals("Order confirmation", recipient.messages[0].templateName);
            assertNotNull(recipient.messages[0].createdAt);

        } catch (MailerSendException e) {
            fail();
        }
    }
}
