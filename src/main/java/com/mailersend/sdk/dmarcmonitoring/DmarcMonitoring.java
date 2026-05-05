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

    // List monitors filters
    private String queryFilter = null;
    private String sortByFilter = null;
    private String orderFilter = null;

    // Report filters (aggregated + IP-specific)
    private String dateFromFilter = null;
    private String dateToFilter = null;
    private String searchFilter = null;
    private String categoryFilter = null;
    private String reportSourceFilter = null;

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
     * Filter monitors by a search query (max 255 chars).
     *
     * @param query a {@link java.lang.String} object.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring query(String query) {
        queryFilter = query;
        return this;
    }

    /**
     * Sort monitors by a field. Allowed values: {@code created_at}, {@code updated_at},
     * {@code dmarc_valid}, {@code spf_status}.
     *
     * @param sortBy a {@link java.lang.String} object.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring sortBy(String sortBy) {
        sortByFilter = sortBy;
        return this;
    }

    /**
     * Set the sort order. Allowed values: {@code asc}, {@code desc}.
     *
     * @param order a {@link java.lang.String} object.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring order(String order) {
        orderFilter = order;
        return this;
    }

    /**
     * Filter reports by start date.
     *
     * @param dateFrom a {@link java.lang.String} object.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring dateFrom(String dateFrom) {
        dateFromFilter = dateFrom;
        return this;
    }

    /**
     * Filter reports by end date.
     *
     * @param dateTo a {@link java.lang.String} object.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring dateTo(String dateTo) {
        dateToFilter = dateTo;
        return this;
    }

    /**
     * Filter reports by a search string (max 255 chars).
     *
     * @param search a {@link java.lang.String} object.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring search(String search) {
        searchFilter = search;
        return this;
    }

    /**
     * Filter reports by category.
     *
     * @param category a {@link java.lang.String} object.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring category(String category) {
        categoryFilter = category;
        return this;
    }

    /**
     * Filter reports by reporting organization / report source (max 255 chars).
     *
     * @param reportSource a {@link java.lang.String} object.
     * @return a {@link DmarcMonitoring} object.
     */
    public DmarcMonitoring reportSource(String reportSource) {
        reportSourceFilter = reportSource;
        return this;
    }

    /**
     * Gets a paginated list of DMARC monitors.
     *
     * @return a {@link DmarcMonitorsList} object.
     * @throws MailerSendException if an error is returned from the API
     */
    public DmarcMonitorsList getMonitors() throws MailerSendException {
        String endpoint = "/dmarc-monitoring".concat(prepareListParamsUrl());

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
        String endpoint = "/dmarc-monitoring/".concat(monitorId).concat("/report")
                .concat(prepareReportParamsUrl());

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
                .concat(prepareReportParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        return api.getRequest(endpoint, DmarcIpReportList.class);
    }

    /**
     * Gets the report sources for the given monitor.
     *
     * <p>{@code dateFrom} and {@code dateTo} are required — set them via {@link #dateFrom(String)}
     * and {@link #dateTo(String)} before calling this method.</p>
     *
     * @param monitorId the monitor ID
     * @return a {@link DmarcReportSourcesList} object.
     * @throws MailerSendException if an error is returned from the API
     */
    public DmarcReportSourcesList getReportSources(String monitorId) throws MailerSendException {
        if (dateFromFilter == null || dateToFilter == null) {
            throw new MailerSendException("dateFrom and dateTo are required for getReportSources.");
        }

        String endpoint = "/dmarc-monitoring/".concat(monitorId).concat("/report-sources")
                .concat(prepareReportSourcesParamsUrl());

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
     * Builds the query string for the list-monitors endpoint.
     * Includes: page, limit, query, sort_by, order.
     */
    private String prepareListParamsUrl() {
        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (queryFilter != null) {
            params.add("query=".concat(queryFilter));
        }

        if (sortByFilter != null) {
            params.add("sort_by=".concat(sortByFilter));
        }

        if (orderFilter != null) {
            params.add("order=".concat(orderFilter));
        }

        return buildQueryString(params);
    }

    /**
     * Builds the query string for aggregated-report and IP-report endpoints.
     * Includes: page, limit, date_from, date_to, search, category, report_source.
     */
    private String prepareReportParamsUrl() {
        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (dateFromFilter != null) {
            params.add("date_from=".concat(dateFromFilter));
        }

        if (dateToFilter != null) {
            params.add("date_to=".concat(dateToFilter));
        }

        if (searchFilter != null) {
            params.add("search=".concat(searchFilter));
        }

        if (categoryFilter != null) {
            params.add("category=".concat(categoryFilter));
        }

        if (reportSourceFilter != null) {
            params.add("report_source=".concat(reportSourceFilter));
        }

        return buildQueryString(params);
    }

    /**
     * Builds the query string for the report-sources endpoint.
     * Includes: date_from, date_to (both required).
     */
    private String prepareReportSourcesParamsUrl() {
        ArrayList<String> params = new ArrayList<String>();

        params.add("date_from=".concat(dateFromFilter));
        params.add("date_to=".concat(dateToFilter));

        return buildQueryString(params);
    }

    /**
     * Converts a list of key=value strings into a URL query string (starting with '?').
     */
    private String buildQueryString(ArrayList<String> params) {
        String requestParams = "";
        for (int i = 0; i < params.size(); i++) {
            String attrSep = i == 0 ? "?" : "&";
            requestParams = requestParams.concat(attrSep).concat(params.get(i));
        }
        return requestParams;
    }
}
