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
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.activities.SmsActivityList;
import com.mailersend.sdk.sms.activities.SmsMessageActivity;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SmsActivitiesTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException
    {
        VcrRecorder.useRecording("SmsActivitiesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException
    {
        VcrRecorder.stopRecording();
    }

    /**
     * Tests that a list of SMS activities can be retrieved
     */
    @Test
    public void testGetSmsActivities() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsActivityList list = ms.sms().activities().getActivities();

            assertNotNull(list.smsActivities);
            assertEquals("sent", list.smsActivities[0].status);
            assertEquals(Instant.parse("2022-09-14T08:00:00Z"), list.smsActivities[0].createdAt.toInstant());

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that SMS activity for a specific message can be retrieved
     */
    @Test
    public void testGetSmsMessageActivity() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsMessageActivity activity = ms.sms().activities().getMessageActivity("sms-msg-id-test-1");

            assertNotNull(activity);
            assertEquals("sms-msg-id-test-1", activity.id);
            assertNotNull(activity.smsActivity);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that the status filter is included in the request URL when set
     */
    @Test
    public void testGetActivitiesWithStatusFilter() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsActivityList list = ms.sms().activities()
                    .addStatusFilter("sent")
                    .getActivities();

            assertNotNull(list.smsActivities);
            assertEquals("sent", list.smsActivities[0].status);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that every added status is sent as a status[] value
     */
    @Test
    public void testGetActivitiesWithMultipleStatusFilters() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsActivityList list = ms.sms().activities()
                    .addStatusFilter("sent")
                    .addStatusFilter("delivered")
                    .getActivities();

            assertEquals("delivered", list.smsActivities[1].status);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that clearStatusFilter removes the added statuses from the request URL
     */
    @Test
    public void testGetActivitiesWithClearedStatusFilter() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsActivityList list = ms.sms().activities()
                    .addStatusFilter("failed")
                    .clearStatusFilter()
                    .getActivities();

            assertEquals("sent", list.smsActivities[0].status);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that date_from and date_to are sent as Unix timestamps
     */
    @Test
    public void testGetActivitiesWithDateRange() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsActivityList list = ms.sms().activities()
                    .dateFrom(Date.from(Instant.parse("2024-01-01T00:00:00Z")))
                    .dateTo(Date.from(Instant.parse("2024-01-31T23:59:59Z")))
                    .getActivities();

            assertEquals("sent", list.smsActivities[0].status);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that page and limit are sent
     */
    @Test
    public void testGetActivitiesWithPagination() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsActivityList list = ms.sms().activities()
                    .page(2)
                    .limit(10)
                    .getActivities();

            assertEquals("sent", list.smsActivities[0].status);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that the sms_number_id filter is included in the request URL when set
     */
    @Test
    public void testGetActivitiesWithSmsNumberIdFilter() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmsActivityList list = ms.sms().activities()
                    .smsNumberId(TestHelper.smsPhoneNumberId)
                    .getActivities();

            assertNotNull(list.smsActivities);

        } catch (MailerSendException e) {

            fail();
        }
    }
}
