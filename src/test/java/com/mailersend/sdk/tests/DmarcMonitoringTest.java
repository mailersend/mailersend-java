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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.dmarcmonitoring.DmarcAggregatedReport;
import com.mailersend.sdk.dmarcmonitoring.DmarcAggregatedReportList;
import com.mailersend.sdk.dmarcmonitoring.DmarcIpReport;
import com.mailersend.sdk.dmarcmonitoring.DmarcIpReportList;
import com.mailersend.sdk.dmarcmonitoring.DmarcMonitor;
import com.mailersend.sdk.dmarcmonitoring.DmarcMonitorsList;
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
    public void listMonitorsTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcMonitorsList list = ms.dmarcMonitoring().getMonitors();

            DmarcMonitor monitor = list.monitors[0];

            assertEquals(TestHelper.dmarcMonitorId, monitor.id);
            assertEquals("found", monitor.spfStatus);
            assertEquals(Instant.parse("2023-05-01T10:00:00Z"), monitor.createdAt.toInstant());
            assertEquals(Instant.parse("2023-06-01T10:00:00Z"), monitor.dmarcRecordCheckedAt.toInstant());

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that page, limit, query, sort_by and order are sent, with the query url encoded
     */
    @Test
    public void listMonitorsWithFiltersTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcMonitorsList list = ms.dmarcMonitoring()
                    .page(2)
                    .limit(10)
                    .query("example org")
                    .sortBy("dmarc_valid")
                    .order("asc")
                    .getMonitors();

            assertEquals(TestHelper.dmarcMonitorId, list.monitors[0].id);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests creating a DMARC monitor
     */
    @Test
    public void createMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcMonitor monitor = ms.dmarcMonitoring().createMonitorBuilder()
                    .createMonitor(TestHelper.dmarcDomainId);

            assertEquals(TestHelper.dmarcMonitorId, monitor.id);
            assertEquals(TestHelper.dmarcDomainId, monitor.domain.id);
            assertEquals(Instant.parse("2023-05-01T10:00:00Z"), monitor.createdAt.toInstant());

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests updating a DMARC monitor
     */
    @Test
    public void updateMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcMonitor monitor = ms.dmarcMonitoring().updateMonitorBuilder()
                    .wantedDmarcRecord(TestHelper.dmarcWantedRecord)
                    .updateMonitor(TestHelper.dmarcMonitorId);

            assertEquals(TestHelper.dmarcWantedRecord, monitor.wantedDmarcRecord);
            assertEquals(Instant.parse("2023-06-15T10:00:00Z"), monitor.updatedAt.toInstant());

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests deleting a DMARC monitor
     */
    @Test
    public void deleteMonitorTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean deleted = ms.dmarcMonitoring().deleteMonitor(TestHelper.dmarcMonitorId);

            assertTrue(deleted);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieving aggregated DMARC reports
     */
    @Test
    public void getAggregatedReportTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcAggregatedReportList list = ms.dmarcMonitoring().getAggregatedReport(TestHelper.dmarcMonitorId);

            DmarcAggregatedReport report = list.reports[0];

            assertEquals(TestHelper.dmarcMonitorIp, report.ipAddress);
            assertEquals("100%", report.passedDmarc);
            assertTrue(report.isFavorite);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that every report filter is sent, with the Y-m-d H:i:s dates and the search url encoded
     */
    @Test
    public void getAggregatedReportWithFiltersTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcAggregatedReportList list = ms.dmarcMonitoring()
                    .page(2)
                    .limit(10)
                    .dateFrom("2024-01-01 00:00:00")
                    .dateTo("2024-01-31 23:59:59")
                    .search("example org")
                    .category("category-id-test")
                    .reportSource("google.com")
                    .getAggregatedReport(TestHelper.dmarcMonitorId);

            assertEquals(TestHelper.dmarcMonitorIp, list.reports[0].ipAddress);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieving IP-specific DMARC reports
     */
    @Test
    public void getIpReportTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcIpReportList list = ms.dmarcMonitoring().getIpReport(TestHelper.dmarcMonitorId, TestHelper.dmarcMonitorIp);

            DmarcIpReport report = list.reports[0];

            assertEquals(TestHelper.dmarcMonitorIp, report.ipAddress);
            assertFalse(report.alignedSpf);
            assertTrue(report.alignedDkim);
            assertEquals("pass", report.spfAuth[0].result);
            assertEquals(Instant.parse("2025-11-05T00:00:00Z"), report.createdAt.toInstant());

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that ISO 8601 dates with an offset are url encoded on the IP report request
     */
    @Test
    public void getIpReportWithDateRangeTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcIpReportList list = ms.dmarcMonitoring()
                    .dateFrom("2024-01-01T00:00:00+02:00")
                    .dateTo("2024-01-31T00:00:00+02:00")
                    .getIpReport(TestHelper.dmarcMonitorId, TestHelper.dmarcMonitorIp);

            assertEquals(TestHelper.dmarcMonitorIp, list.reports[0].ipAddress);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieving DMARC report sources
     */
    @Test
    public void getReportSourcesTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcReportSourcesList list = ms.dmarcMonitoring()
                    .dateFrom("1704067200")
                    .dateTo("1706745599")
                    .getReportSources(TestHelper.dmarcMonitorId);

            assertEquals("google.com", list.sources[0].reportSource);
            assertEquals(42, list.sources[0].reports);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that the status filter is sent on the report sources request, with the dates url encoded
     */
    @Test
    public void getReportSourcesWithStatusTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DmarcReportSourcesList list = ms.dmarcMonitoring()
                    .dateFrom("2024-01-01 00:00:00")
                    .dateTo("2024-01-31 23:59:59")
                    .status("accepted")
                    .getReportSources(TestHelper.dmarcMonitorId);

            assertEquals("google.com", list.sources[0].reportSource);
            assertEquals(38, list.sources[0].reports);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that getReportSources() throws when a required date is not set
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("missingReportSourcesDates")
    public void getReportSourcesRequiresDates(String label, String dateFrom, String dateTo, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () ->
                ms.dmarcMonitoring()
                        .dateFrom(dateFrom)
                        .dateTo(dateTo)
                        .getReportSources(TestHelper.dmarcMonitorId)
        );

        assertEquals(expectedMessage, ex.getMessage());
    }

    static Stream<Arguments> missingReportSourcesDates() {
        return Stream.of(
                Arguments.of("missing dateFrom", null, "1706745599", "dateFrom and dateTo are required for getReportSources."),
                Arguments.of("missing dateTo", "1704067200", null, "dateFrom and dateTo are required for getReportSources."),
                Arguments.of("missing dateFrom and dateTo", null, null, "dateFrom and dateTo are required for getReportSources.")
        );
    }

    /**
     * Tests marking an IP as favorite
     */
    @Test
    public void markIpAsFavoriteTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean marked = ms.dmarcMonitoring().markIpAsFavorite(TestHelper.dmarcMonitorId, TestHelper.dmarcMonitorIp);

            assertTrue(marked);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests removing an IP from favorites
     */
    @Test
    public void removeIpFromFavoritesTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean removed = ms.dmarcMonitoring().removeIpFromFavorites(TestHelper.dmarcMonitorId, TestHelper.dmarcMonitorIp);

            assertTrue(removed);

        } catch (MailerSendException e) {
            e.printStackTrace();
            fail();
        }
    }
}
