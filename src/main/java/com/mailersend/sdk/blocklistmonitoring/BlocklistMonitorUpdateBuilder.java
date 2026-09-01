/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.blocklistmonitoring;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * Builder for updating a blocklist monitor.
 */
public class BlocklistMonitorUpdateBuilder {

    private MailerSend apiObjectReference;

    private BlocklistMonitorUpdateRequestBody updateBody;

    /**
     * No instantiation from outside the SDK.
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected BlocklistMonitorUpdateBuilder(MailerSend apiObjectRef) {
        apiObjectReference = apiObjectRef;
        updateBody = new BlocklistMonitorUpdateRequestBody();
    }

    /**
     * Set the new display name for the monitor.
     *
     * @param name the new display name
     * @return this builder
     */
    public BlocklistMonitorUpdateBuilder name(String name) {
        updateBody.name = name;
        return this;
    }

    /**
     * Enable or disable notifications.
     *
     * @param notify whether to send notifications
     * @return this builder
     */
    public BlocklistMonitorUpdateBuilder notify(boolean notify) {
        updateBody.notify = notify;
        return this;
    }

    /**
     * Set the email address to notify when a blocklist hit is detected.
     *
     * @param notifyEmail a valid email address
     * @return this builder
     */
    public BlocklistMonitorUpdateBuilder notifyEmail(String notifyEmail) {
        updateBody.notifyEmail = notifyEmail;
        return this;
    }

    /**
     * Set the webhook URL to notify when a blocklist hit is detected.
     *
     * @param notifyAddress a valid webhook URL
     * @return this builder
     */
    public BlocklistMonitorUpdateBuilder notifyAddress(String notifyAddress) {
        updateBody.notifyAddress = notifyAddress;
        return this;
    }

    /**
     * Updates the blocklist monitor with the given ID.
     *
     * @param monitorId the monitor ID to update
     * @return the updated {@link BlocklistMonitor}
     * @throws MailerSendException if an error is returned from the API
     */
    public BlocklistMonitor updateMonitor(String monitorId) throws MailerSendException {

        String endpoint = "/blocklist-monitoring/".concat(monitorId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(updateBody);

        // reset the body object's values so that it can be reused
        updateBody.reset();

        BlocklistMonitorResponse response = api.putRequest(endpoint, json, BlocklistMonitorResponse.class);

        return response.monitor;
    }
}
