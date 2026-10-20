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
import com.mailersend.sdk.whatsapp.activities.WhatsAppActivity;
import com.mailersend.sdk.whatsapp.activities.WhatsAppActivityList;

public class WhatsAppActivitiesTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("WhatsAppActivitiesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    @Test
    public void TestGetWhatsAppActivities() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            WhatsAppActivityList list = ms.whatsapp().activities()
                .whatsappMessageId("67f91abd69f79df391e9d78d")
                .recipient("+48600000001")
                .addTypeFilter("delivered")
                .addTypeFilter("read")
                .dateFrom(1791936000)
                .dateTo(1792022400)
                .page(1)
                .limit(25)
                .getActivities();

            assertEquals(2, list.activities.length);

            WhatsAppActivity activity = list.activities[0];
            assertEquals("6acf9f214069f5eceb882bf1", activity.id);
            assertEquals("67f91abe2202f3705540234a", activity.whatsappItemId);
            assertEquals("67f91abd69f79df391e9d78d", activity.whatsappMessageId);
            assertEquals("3enl6x27wmrxrl2v", activity.whatsappAccountId);
            assertEquals("15550001234", activity.from);
            assertEquals("48600000001", activity.to);
            assertEquals("read", activity.type);
            assertNotNull(activity.createdAt);
            assertEquals("delivered", list.activities[1].type);
            assertNull(list.links.next);

        } catch (MailerSendException e) {
            fail();
        }
    }
}
