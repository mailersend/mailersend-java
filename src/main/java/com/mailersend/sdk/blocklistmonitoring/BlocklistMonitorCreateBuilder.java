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
 * Builder for creating a blocklist monitor.
 */
public class BlocklistMonitorCreateBuilder {

    private MailerSend apiObjectReference;

    private BlocklistMonitorCreateRequestBody createBody;

    /**
     * No instantiation from outside the SDK.
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected BlocklistMonitorCreateBuilder(MailerSend apiObjectRef) {
        apiObjectReference = apiObjectRef;
        createBody = new BlocklistMonitorCreateRequestBody();
    }

    /**
     * Set the display name for the monitor.
     *
     * @param name the display name (defaults to the address value if not set)
     * @return this builder
     */
    public BlocklistMonitorCreateBuilder name(String name) {
        createBody.name = name;
        return this;
    }

    /**
     * Enable or disable notifications when a blocklist hit is detected.
     *
     * @param notify whether to send notifications
     * @return this builder
     */
    public BlocklistMonitorCreateBuilder notify(boolean notify) {
        createBody.notify = notify;
        return this;
    }

    /**
     * Set the email address to notify when a blocklist hit is detected.
     *
     * @param notifyEmail a valid email address
     * @return this builder
     */
    public BlocklistMonitorCreateBuilder notifyEmail(String notifyEmail) {
        createBody.notifyEmail = notifyEmail;
        return this;
    }

    /**
     * Set the webhook URL to notify when a blocklist hit is detected.
     *
     * @param notifyAddress a valid webhook URL
     * @return this builder
     */
    public BlocklistMonitorCreateBuilder notifyAddress(String notifyAddress) {
        createBody.notifyAddress = notifyAddress;
        return this;
    }

    /**
     * Creates a new blocklist monitor for the given address.
     *
     * @param address the domain name, IP address, or CIDR block to monitor
     * @return the created {@link BlocklistMonitor}
     * @throws MailerSendException if an error is returned from the API
     */
    public BlocklistMonitor createMonitor(String address) throws MailerSendException {

        String endpoint = "/blocklist-monitoring";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        createBody.address = address;

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(createBody);

        // reset the body object's values so that it can be reused
        createBody.reset();

        BlocklistMonitorResponse response = api.postRequest(endpoint, json, BlocklistMonitorResponse.class);

        return response.monitor;
    }
}
