/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.blocklistmonitoring.BlocklistMonitor;
import com.mailersend.sdk.blocklistmonitoring.BlocklistMonitorsList;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;

public class BlocklistMonitoringTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("BlocklistMonitoringTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    /**
     * Tests listing blocklist monitors
     */
    @Test
    public void getMonitorsTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitorsList list = ms.blocklistMonitoring().getMonitors();

            assertNotNull(list);
            for (BlocklistMonitor monitor : list.monitors) {
                System.out.println(monitor.id);
                System.out.println(monitor.address);
            }

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieving a single blocklist monitor
     */
    @Test
    public void getMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitor monitor = ms.blocklistMonitoring().getMonitor(TestHelper.blocklistMonitorId);

            assertNotNull(monitor);
            System.out.println(monitor.id);
            System.out.println(monitor.address);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests creating a blocklist monitor
     */
    @Test
    public void createMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitor monitor = ms.blocklistMonitoring().createMonitorBuilder()
                    .createMonitor(TestHelper.blocklistMonitorAddress);

            assertNotNull(monitor);
            System.out.println(monitor.id);
            System.out.println(monitor.address);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests updating a blocklist monitor
     */
    @Test
    public void updateMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitor monitor = ms.blocklistMonitoring().updateMonitorBuilder()
                    .name("updated-monitor")
                    .updateMonitor(TestHelper.blocklistMonitorId);

            assertNotNull(monitor);
            System.out.println(monitor.id);
            System.out.println(monitor.name);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests deleting a blocklist monitor
     */
    @Test
    public void deleteMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean deleted = ms.blocklistMonitoring().deleteMonitor(TestHelper.blocklistMonitorId);

            assertTrue(deleted);
            System.out.println("Monitor deleted: " + deleted);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }
}
