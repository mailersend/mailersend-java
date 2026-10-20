/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.messages;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>WhatsAppMessages class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppMessages {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;
    private String whatsappAccountIdFilter;
    private String fromFilter;
    private String templateIdFilter;
    private Long dateFromFilter;
    private Long dateToFilter;

    /**
     * <p>Constructor for WhatsAppMessages.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsAppMessages(MailerSend ref) {
        apiObjectReference = ref;
    }

    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages page(int page) {
        pageFilter = page;
        return this;
    }

    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages limit(int limit) {
        limitFilter = limit;
        return this;
    }

    /**
     * Filters by the MailerSend sender ID the messages were sent from
     *
     * @param whatsappAccountId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages whatsappAccountId(String whatsappAccountId) {
        whatsappAccountIdFilter = whatsappAccountId;
        return this;
    }

    /**
     * Filters by the sender's phone number in international format
     *
     * @param from a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages from(String from) {
        fromFilter = from;
        return this;
    }

    /**
     * Filters by the ID of the template that was sent
     *
     * @param templateId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages templateId(String templateId) {
        templateIdFilter = templateId;
        return this;
    }

    /**
     * Only messages created strictly after this Unix timestamp (UTC) are returned
     *
     * @param dateFrom a long.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages dateFrom(long dateFrom) {
        dateFromFilter = dateFrom;
        return this;
    }

    /**
     * Only messages created strictly after this date are returned
     *
     * @param dateFrom a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages dateFrom(Date dateFrom) {
        dateFromFilter = dateFrom.getTime() / 1000;
        return this;
    }

    /**
     * Only messages created strictly before this Unix timestamp (UTC) are returned
     *
     * @param dateTo a long.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages dateTo(long dateTo) {
        dateToFilter = dateTo;
        return this;
    }

    /**
     * Only messages created strictly before this date are returned
     *
     * @param dateTo a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessages} object.
     */
    public WhatsAppMessages dateTo(Date dateTo) {
        dateToFilter = dateTo.getTime() / 1000;
        return this;
    }

    /**
     * <p>getMessages.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessageList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppMessageList getMessages() throws MailerSendException {

        String endpoint = "/whatsapp/messages".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        WhatsAppMessageList response = api.getRequest(endpoint, WhatsAppMessageList.class);

        response.postDeserialize();

        return response;
    }

    /**
     * <p>getMessage.</p>
     *
     * @param messageId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.messages.WhatsAppMessage} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppMessage getMessage(String messageId) throws MailerSendException {

        String endpoint = "/whatsapp/messages/".concat(messageId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleWhatsAppMessageResponse response = api.getRequest(endpoint, SingleWhatsAppMessageResponse.class);

        response.message.postDeserialize();

        return response.message;
    }

    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));

        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (whatsappAccountIdFilter != null) {
            params.add("whatsapp_account_id=".concat(urlEncode(whatsappAccountIdFilter)));
        }

        if (fromFilter != null) {
            params.add("from=".concat(urlEncode(fromFilter)));
        }

        if (templateIdFilter != null) {
            params.add("template_id=".concat(urlEncode(templateIdFilter)));
        }

        if (dateFromFilter != null) {
            params.add("date_from=".concat(String.valueOf(dateFromFilter)));
        }

        if (dateToFilter != null) {
            params.add("date_to=".concat(String.valueOf(dateToFilter)));
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

    private String urlEncode(String value) {

        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
