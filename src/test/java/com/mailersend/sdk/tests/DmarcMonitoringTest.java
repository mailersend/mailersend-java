/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.dmarcmonitoring.DmarcAggregatedReport;
import com.mailersend.sdk.dmarcmonitoring.DmarcAggregatedReportList;
import com.mailersend.sdk.dmarcmonitoring.DmarcIpReport;
import com.mailersend.sdk.dmarcmonitoring.DmarcIpReportList;
import com.mailersend.sdk.dmarcmonitoring.DmarcMonitor;
import com.mailersend.sdk.dmarcmonitoring.DmarcMonitorsList;
import com.mailersend.sdk.dmarcmonitoring.DmarcReportSource;
import com.mailersend.sdk.dmarcmonitoring.DmarcReportSourcesList;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;

public class DmarcMonitoringTest {

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("DmarcMonitoringTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    /**
     * Tests listing DMARC monitors
     */
    @Test
    public void ListMonitorsTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcMonitorsList list = ms.dmarcMonitoring().getMonitors();

            for (DmarcMonitor monitor : list.monitors) {
                System.out.println(monitor.id);
                System.out.println(monitor.dmarcRecord);
                System.out.println(monitor.spfStatus);
            }

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests creating a DMARC monitor
     */
    @Test
    public void CreateMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcMonitor monitor = ms.dmarcMonitoring().createMonitorBuilder()
                    .createMonitor(TestHelper.dmarcDomainId);

            System.out.println(monitor.id);
            System.out.println(monitor.wantedDmarcRecord);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests updating a DMARC monitor
     */
    @Test
    public void UpdateMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcMonitor monitor = ms.dmarcMonitoring().updateMonitorBuilder()
                    .wantedDmarcRecord(TestHelper.dmarcWantedRecord)
                    .updateMonitor(TestHelper.dmarcMonitorId);

            System.out.println(monitor.id);
            System.out.println(monitor.wantedDmarcRecord);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests deleting a DMARC monitor
     */
    @Test
    public void DeleteMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean deleted = ms.dmarcMonitoring().deleteMonitor(TestHelper.dmarcMonitorId);

            System.out.println("Monitor deleted: " + deleted);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieving aggregated DMARC reports
     */
    @Test
    public void GetAggregatedReportTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcAggregatedReportList list = ms.dmarcMonitoring().getAggregatedReport(TestHelper.dmarcMonitorId);

            for (DmarcAggregatedReport report : list.reports) {
                System.out.println(report.ipAddress);
                System.out.println(report.totalVolume);
                System.out.println(report.passedDmarc);
            }

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieving IP-specific DMARC reports
     */
    @Test
    public void GetIpReportTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcIpReportList list = ms.dmarcMonitoring().getIpReport(TestHelper.dmarcMonitorId, TestHelper.dmarcMonitorIp);

            for (DmarcIpReport report : list.reports) {
                System.out.println(report.ipAddress);
                System.out.println(report.totalVolume);
                System.out.println(report.appliedPolicy);
            }

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieving DMARC report sources
     */
    @Test
    public void GetReportSourcesTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcReportSourcesList list = ms.dmarcMonitoring().getReportSources(TestHelper.dmarcMonitorId);

            for (DmarcReportSource source : list.sources) {
                System.out.println(source.reportSource);
                System.out.println(source.reports);
            }

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests marking an IP as favorite
     */
    @Test
    public void MarkIpAsFavoriteTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean marked = ms.dmarcMonitoring().markIpAsFavorite(TestHelper.dmarcMonitorId, TestHelper.dmarcMonitorIp);

            System.out.println("IP marked as favorite: " + marked);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests removing an IP from favorites
     */
    @Test
    public void RemoveIpFromFavoritesTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean removed = ms.dmarcMonitoring().removeIpFromFavorites(TestHelper.dmarcMonitorId, TestHelper.dmarcMonitorIp);

            System.out.println("IP removed from favorites: " + removed);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }
}
