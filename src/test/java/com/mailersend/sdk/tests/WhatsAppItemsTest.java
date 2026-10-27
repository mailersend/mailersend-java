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
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sdk.whatsapp.items.WhatsAppItem;
import com.mailersend.sdk.whatsapp.items.WhatsAppItemList;

public class WhatsAppItemsTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("WhatsAppItemsTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    @Test
    public void TestGetWhatsAppItems() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppItemList list = ms.whatsapp().items()
                .recipient("+48600000002")
                .addStatusFilter("failed")
                .addStatusFilter("read")
                .dateFrom(new Date(1791936000000L))
                .dateTo(1792022400)
                .page(1)
                .limit(25)
                .getItems();

            assertEquals(1, list.items.length);

            WhatsAppItem item = list.items[0];
            assertEquals("67f91abe2202f3705540234b", item.id);
            assertEquals("67f91abd69f79df391e9d78d", item.whatsappMessageId);
            assertEquals("3enl6x27wmrxrl2v", item.whatsappAccountId);
            assertEquals("15550001234", item.from);
            assertEquals("48600000002", item.to);
            assertEquals("failed", item.status);
            assertEquals("23zxk54v6gjy6v7m", item.templateId);
            assertEquals("Order confirmation", item.templateName);
            assertEquals(131026, item.errorCode);
            assertEquals("Message undeliverable", item.errorMessage);
            assertNotNull(item.createdAt);
            assertNull(item.recipient);
            assertNull(item.activity);
            assertNull(list.links.last);
            assertNull(list.links.next);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void TestGetSingleWhatsAppItem() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppItem item = ms.whatsapp().items().getItem("67f91abe2202f3705540234a");

            assertEquals("67f91abe2202f3705540234a", item.id);
            assertEquals("read", item.status);
            assertNull(item.errorCode);

            assertEquals("67f91abe2202f37055402301", item.recipient.id);
            assertEquals("48600000001", item.recipient.phoneNumber);
            assertNull(item.recipient.username);
            assertEquals("active", item.recipient.status);
            assertNotNull(item.recipient.createdAt);

            assertEquals(4, item.activity.length);
            assertEquals("queued", item.activity[0].type);
            assertEquals("read", item.activity[3].type);
            assertNotNull(item.activity[0].createdAt);

        } catch (MailerSendException e) {
            fail();
        }
    }
}
