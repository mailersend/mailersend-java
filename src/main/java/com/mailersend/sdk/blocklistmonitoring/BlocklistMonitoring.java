/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.blocklistmonitoring;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * Provides access to the Blocklist Monitoring API endpoints.
 *
 * <p>Do not initialize directly. Access via {@code MailerSend.blocklistMonitoring()}.</p>
 */
public class BlocklistMonitoring {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;
    private String queryFilter = null;
    private String sortByFilter = null;
    private String orderFilter = null;

    private BlocklistMonitorCreateBuilder createBuilder;
    private BlocklistMonitorUpdateBuilder updateBuilder;

    /**
     * Do not initialize directly. This should only be accessed from MailerSend.blocklistMonitoring()
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public BlocklistMonitoring(MailerSend ref) {
        apiObjectReference = ref;
        createBuilder = new BlocklistMonitorCreateBuilder(ref);
        updateBuilder = new BlocklistMonitorUpdateBuilder(ref);
    }

    /**
     * Get the create monitor builder.
     *
     * @return a {@link BlocklistMonitorCreateBuilder} object.
     */
    public BlocklistMonitorCreateBuilder createMonitorBuilder() {
        return createBuilder;
    }

    /**
     * Get the update monitor builder.
     *
     * @return a {@link BlocklistMonitorUpdateBuilder} object.
     */
    public BlocklistMonitorUpdateBuilder updateMonitorBuilder() {
        return updateBuilder;
    }

    /**
     * Set the page of the request.
     *
     * @param page a int.
     * @return a {@link BlocklistMonitoring} object.
     */
    public BlocklistMonitoring page(int page) {
        pageFilter = page;
        return this;
    }

    /**
     * Set the results limit (1 - 100).
     *
     * @param limit a int.
     * @return a {@link BlocklistMonitoring} object.
     */
    public BlocklistMonitoring limit(int limit) {
        limitFilter = limit;
        return this;
    }

    /**
     * Filter monitors by name or address.
     *
     * @param query the search string (max 255 chars)
     * @return a {@link BlocklistMonitoring} object.
     */
    public BlocklistMonitoring query(String query) {
        queryFilter = query;
        return this;
    }

    /**
     * Set the field to sort results by.
     *
     * @param sortBy one of: name, address, created_at, updated_at, blocklisted
     * @return a {@link BlocklistMonitoring} object.
     */
    public BlocklistMonitoring sortBy(String sortBy) {
        sortByFilter = sortBy;
        return this;
    }

    /**
     * Set the sort order.
     *
     * @param order asc or desc
     * @return a {@link BlocklistMonitoring} object.
     */
    public BlocklistMonitoring order(String order) {
        orderFilter = order;
        return this;
    }

    /**
     * Gets a paginated list of blocklist monitors.
     *
     * @return a {@link BlocklistMonitorsList} object.
     * @throws MailerSendException if an error is returned from the API
     */
    public BlocklistMonitorsList getMonitors() throws MailerSendException {
        String endpoint = "/blocklist-monitoring".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        // reset pagination filters after use
        pageFilter = 1;
        limitFilter = 25;
        queryFilter = null;
        sortByFilter = null;
        orderFilter = null;

        return api.getRequest(endpoint, BlocklistMonitorsList.class);
    }

    /**
     * Gets a single blocklist monitor by its ID.
     *
     * @param monitorId the monitor ID
     * @return a {@link BlocklistMonitor} object.
     * @throws MailerSendException if an error is returned from the API
     */
    public BlocklistMonitor getMonitor(String monitorId) throws MailerSendException {
        String endpoint = "/blocklist-monitoring/".concat(monitorId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        BlocklistMonitorResponse response = api.getRequest(endpoint, BlocklistMonitorResponse.class);

        return response.monitor;
    }

    /**
     * Deletes the blocklist monitor with the given ID.
     * If the monitor is a parent (IP range), all child monitors are deleted automatically.
     *
     * @param monitorId the monitor ID to delete
     * @return true if deletion succeeded
     * @throws MailerSendException if an error is returned from the API
     */
    public boolean deleteMonitor(String monitorId) throws MailerSendException {
        String endpoint = "/blocklist-monitoring/".concat(monitorId);

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

        if (queryFilter != null && !queryFilter.isBlank()) {
            params.add("query=".concat(queryFilter));
        }

        if (sortByFilter != null && !sortByFilter.isBlank()) {
            params.add("sort_by=".concat(sortByFilter));
        }

        if (orderFilter != null && !orderFilter.isBlank()) {
            params.add("order=".concat(orderFilter));
        }

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
