/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;

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

    private static final String ipRangeMonitorId = "blocklist-ip-range-id-test";

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

            BlocklistMonitor monitor = list.monitors[0];

            assertEquals(TestHelper.blocklistMonitorId, monitor.id);
            assertEquals(0, monitor.blocklistedCount);
            assertFalse(monitor.blocklisted);
            assertEquals(Instant.parse("2023-05-01T10:00:00Z"), monitor.createdAt.toInstant());
            assertEquals(Instant.parse("2023-06-01T10:00:00Z"), monitor.lastCheck.toInstant());

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that page, limit, query, sort_by and order are sent, with the query url encoded
     */
    @Test
    public void getMonitorsWithFiltersTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitorsList list = ms.blocklistMonitoring()
                    .page(2)
                    .limit(10)
                    .query("my domain")
                    .sortBy("blocklisted")
                    .order("desc")
                    .getMonitors();

            assertEquals(TestHelper.blocklistMonitorId, list.monitors[0].id);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that the list filters are cleared after a request
     */
    @Test
    public void getMonitorsResetsFiltersAfterUseTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ms.blocklistMonitoring().query("example.com").getMonitors();

            BlocklistMonitorsList list = ms.blocklistMonitoring().getMonitors();

            assertEquals("blocklist-monitor-id-other", list.monitors[0].id);

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

            assertEquals(TestHelper.blocklistMonitorId, monitor.id);
            assertEquals(1, monitor.blocklistedCount);
            assertTrue(monitor.blocklisted);
            assertEquals("zen.spamhaus.org", monitor.hits[0].blocklistHost);
            assertEquals(Instant.parse("2026-03-20T08:00:00Z"), monitor.hits[0].listedAt.toInstant());

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieving an IP range monitor, whose children are post-processed too
     */
    @Test
    public void getMonitorWithChildrenTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitor monitor = ms.blocklistMonitoring().getMonitor(ipRangeMonitorId);

            BlocklistMonitor child = monitor.children[0];

            assertEquals(3, monitor.childrenCount);
            assertEquals(1, child.blocklistedCount);
            assertTrue(child.blocklisted);
            assertEquals(Instant.parse("2026-01-15T08:00:00Z"), child.createdAt.toInstant());

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests creating a blocklist monitor, whose response returns blocklisted as a boolean
     */
    @Test
    public void createMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitor monitor = ms.blocklistMonitoring().createMonitorBuilder()
                    .createMonitor(TestHelper.blocklistMonitorAddress);

            assertEquals(TestHelper.blocklistMonitorId, monitor.id);
            assertEquals(0, monitor.blocklistedCount);
            assertFalse(monitor.blocklisted);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that name, notify, notify_email and notify_address are sent on create
     */
    @Test
    public void createMonitorWithOptionsTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitor monitor = ms.blocklistMonitoring().createMonitorBuilder()
                    .name("My Domain")
                    .notify(true)
                    .notifyEmail("admin@example.com")
                    .notifyAddress("https://example.com/hook")
                    .createMonitor(TestHelper.blocklistMonitorAddress);

            assertEquals("My Domain", monitor.name);
            assertTrue(monitor.notify);

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

            assertEquals("updated-monitor", monitor.name);
            assertEquals(2, monitor.blocklistedCount);
            assertTrue(monitor.blocklisted);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that notify, notify_email and notify_address are sent on update
     */
    @Test
    public void updateMonitorWithNotificationsTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistMonitor monitor = ms.blocklistMonitoring().updateMonitorBuilder()
                    .notify(true)
                    .notifyEmail("alerts@example.com")
                    .notifyAddress("https://example.com/hook")
                    .updateMonitor(TestHelper.blocklistMonitorId);

            assertTrue(monitor.notify);
            assertEquals("alerts@example.com", monitor.forwards[0].value);

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

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }
}
