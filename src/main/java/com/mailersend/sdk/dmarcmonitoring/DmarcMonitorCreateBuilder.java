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
 * Builder for creating a DMARC monitor.
 */
public class DmarcMonitorCreateBuilder {

    private MailerSend apiObjectReference;

    private DmarcMonitorCreateRequestBody createBody;

    /**
     * No instantiation from outside the SDK.
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected DmarcMonitorCreateBuilder(MailerSend apiObjectRef) {
        apiObjectReference = apiObjectRef;
        createBody = new DmarcMonitorCreateRequestBody();
    }

    /**
     * Creates a new DMARC monitor for the given domain.
     *
     * @param domainId the domain ID to monitor
     * @return the created {@link DmarcMonitor}
     * @throws MailerSendException if an error is returned from the API
     */
    public DmarcMonitor createMonitor(String domainId) throws MailerSendException {

        String endpoint = "/dmarc-monitoring";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        createBody.domainId = domainId;

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(createBody);

        // reset the body object's values so that it can be reused
        createBody.reset();

        DmarcMonitorResponse response = api.postRequest(endpoint, json, DmarcMonitorResponse.class);

        return response.monitor;
    }
}
