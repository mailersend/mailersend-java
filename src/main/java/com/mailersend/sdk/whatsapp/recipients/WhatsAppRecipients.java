/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.recipients;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>WhatsAppRecipients class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppRecipients {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;
    private String statusFilter;

    /**
     * <p>Constructor for WhatsAppRecipients.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsAppRecipients(MailerSend ref) {
        apiObjectReference = ref;
    }

    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipients} object.
     */
    public WhatsAppRecipients page(int page) {
        pageFilter = page;
        return this;
    }

    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipients} object.
     */
    public WhatsAppRecipients limit(int limit) {
        limitFilter = limit;
        return this;
    }

    /**
     * Filters the recipients by status: active, invalid, suppressed or blocked
     *
     * @param status a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipients} object.
     */
    public WhatsAppRecipients status(String status) {
        statusFilter = status;
        return this;
    }

    /**
     * <p>getRecipients.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipientList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppRecipientList getRecipients() throws MailerSendException {

        String endpoint = "/whatsapp/recipients".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        WhatsAppRecipientList response = api.getRequest(endpoint, WhatsAppRecipientList.class);

        response.postDeserialize();

        return response;
    }

    /**
     * <p>getRecipient.</p>
     *
     * @param recipientId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.recipients.WhatsAppRecipient} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppRecipient getRecipient(String recipientId) throws MailerSendException {

        String endpoint = "/whatsapp/recipients/".concat(recipientId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleWhatsAppRecipientResponse response = api.getRequest(endpoint, SingleWhatsAppRecipientResponse.class);

        response.recipient.postDeserialize();

        return response.recipient;
    }

    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));

        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (statusFilter != null) {
            params.add("status=".concat(statusFilter));
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
