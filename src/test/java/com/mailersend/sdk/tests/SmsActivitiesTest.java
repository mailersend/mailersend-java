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
