/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.dmarcmonitoring;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * Builder for updating a DMARC monitor.
 */
public class DmarcMonitorUpdateBuilder {

    private MailerSend apiObjectReference;

    private DmarcMonitorUpdateRequestBody updateBody;

    /**
     * No instantiation from outside the SDK.
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected DmarcMonitorUpdateBuilder(MailerSend apiObjectRef) {
        apiObjectReference = apiObjectRef;
        updateBody = new DmarcMonitorUpdateRequestBody();
    }

    /**
     * Set the desired DMARC record value.
     *
     * @param wantedDmarcRecord the DMARC record string to set
     * @return this builder
     */
    public DmarcMonitorUpdateBuilder wantedDmarcRecord(String wantedDmarcRecord) {
        updateBody.wantedDmarcRecord = wantedDmarcRecord;
        return this;
    }

    /**
     * Updates the DMARC monitor with the given ID.
     *
     * @param monitorId the monitor ID to update
     * @return the updated {@link DmarcMonitor}
     * @throws MailerSendException if an error is returned from the API
     */
    public DmarcMonitor updateMonitor(String monitorId) throws MailerSendException {

        String endpoint = "/dmarc-monitoring/".concat(monitorId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(updateBody);

        // reset the body object's values so that it can be reused
        updateBody.reset();

        DmarcMonitorResponse response = api.putRequest(endpoint, json, DmarcMonitorResponse.class);

        return response.monitor;
    }
}
