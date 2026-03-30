/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.dmarcmonitoring;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * Provides access to the DMARC Monitoring API endpoints.
 *
 * <p>Do not initialize directly. Access via {@code MailerSend.dmarcMonitoring()}.</p>
 */
public class DmarcMonitoring {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;

    private DmarcMonitorCreateBuilder createBuilder;
    private DmarcMonitorUpdateBuilder updateBuilder;

    /**
     * Do not initialize directly. This should only be accessed from MailerSend.dmarcMonitoring()
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public DmarcMonitoring(MailerSend ref) {
        apiObjectReference = ref;
        createBuilder = new DmarcMonitorCreateBuilder(ref);
        updateBuilder = new DmarcMonitorUpdateBuilder(ref);
    }

    /**
     * Get the create monitor builder.
     *
     * @return a {@link DmarcMonitorCreateBuilder} object.
     */
    public DmarcMonitorCreateBuilder createMonitorBuilder() {
        return createBuilder;
    }

    /**
     * Get the update monitor builder.
     *
     * @return a {@link DmarcMonitorUpdateBuilder} object.
     */
    public DmarcMonitorUpdateBuilder updateMonitorBuilder() {
        return updateBuilder;
    }

    /**
     * Set the page of the request.
     *
     * @param page a int.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring page(int page) {
        pageFilter = page;
        return this;
    }

    /**
     * Set the results limit (10 - 100).
     *
     * @param limit a int.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring limit(int limit) {
        limitFilter = limit;
        return this;
    }

    /**
     * Gets a paginated list of DMARC monitors.
     *
     * @return a {@link DmarcMonitorsList} object.
     * @throws MailerSendException if an error is returned from the API
     */
    public DmarcMonitorsList getMonitors() throws MailerSendException {
        String endpoint = "/dmarc-monitoring".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        return api.getRequest(endpoint, DmarcMonitorsList.class);
    }

    /**
     * Deletes the DMARC monitor with the given ID.
     *
     * @param monitorId the monitor ID to delete
     * @return true if deletion succeeded
     * @throws MailerSendException if an error is returned from the API
     */
    public boolean deleteMonitor(String monitorId) throws MailerSendException {
        String endpoint = "/dmarc-monitoring/".concat(monitorId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);

        return IntStream.of(new int[]{200, 204, 202}).anyMatch(x -> x == response.responseStatusCode);
    }

    /**
     * Gets aggregated DMARC reports for the given monitor.
     *
     * @param monitorId the monitor ID
     * @return a {@link DmarcAggregatedReportList} object.
     * @throws MailerSendException if an error is returned from the API
     */
    public DmarcAggregatedReportList getAggregatedReport(String monitorId) throws MailerSendException {
        String endpoint = "/dmarc-monitoring/".concat(monitorId).concat("/report").concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        return api.getRequest(endpoint, DmarcAggregatedReportList.class);
    }

    /**
     * Gets IP-specific DMARC reports for the given monitor and IP address.
     *
     * @param monitorId the monitor ID
     * @param ip        the IP address
     * @return a {@link DmarcIpReportList} object.
     * @throws MailerSendException if an error is returned from the API
     */
    public DmarcIpReportList getIpReport(String monitorId, String ip) throws MailerSendException {
        String endpoint = "/dmarc-monitoring/".concat(monitorId).concat("/report/").concat(ip)
                .concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        return api.getRequest(endpoint, DmarcIpReportList.class);
    }

    /**
     * Gets the report sources for the given monitor.
     *
     * @param monitorId the monitor ID
     * @return a {@link DmarcReportSourcesList} object.
     * @throws MailerSendException if an error is returned from the API
     */
    public DmarcReportSourcesList getReportSources(String monitorId) throws MailerSendException {
        String endpoint = "/dmarc-monitoring/".concat(monitorId).concat("/report-sources")
                .concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        return api.getRequest(endpoint, DmarcReportSourcesList.class);
    }

    /**
     * Marks the given IP address as a favorite for the specified monitor.
     *
     * @param monitorId the monitor ID
     * @param ip        the IP address to mark as favorite
     * @return true if the operation succeeded
     * @throws MailerSendException if an error is returned from the API
     */
    public boolean markIpAsFavorite(String monitorId, String ip) throws MailerSendException {
        String endpoint = "/dmarc-monitoring/".concat(monitorId).concat("/favorite/").concat(ip);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.putRequest(endpoint, "", MailerSendResponse.class);

        return IntStream.of(new int[]{200, 204, 202}).anyMatch(x -> x == response.responseStatusCode);
    }

    /**
     * Removes the given IP address from favorites for the specified monitor.
     *
     * @param monitorId the monitor ID
     * @param ip        the IP address to remove from favorites
     * @return true if the operation succeeded
     * @throws MailerSendException if an error is returned from the API
     */
    public boolean removeIpFromFavorites(String monitorId, String ip) throws MailerSendException {
        String endpoint = "/dmarc-monitoring/".concat(monitorId).concat("/favorite/").concat(ip);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);

        return IntStream.of(new int[]{200, 204, 202}).anyMatch(x -> x == response.responseStatusCode);
    }

    /**
     * Prepares the query part of the request URL.
     */
    private String prepareParamsUrl() {
        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        params.add("limit=".concat(String.valueOf(limitFilter)));

        String requestParams = "";
        for (int i = 0; i < params.size(); i++) {
            String attrSep = "&";
            if (i == 0) {
                attrSep = "?";
            }
            requestParams = requestParams.concat(attrSep).concat(params.get(i));
        }

        return requestParams;
    }
}
