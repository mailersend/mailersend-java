/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.activities;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>WhatsAppActivities class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppActivities {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;
    private String whatsappItemIdFilter;
    private String whatsappMessageIdFilter;
    private String recipientFilter;
    private ArrayList<String> typeFilter = new ArrayList<String>();
    private Long dateFromFilter;
    private Long dateToFilter;

    /**
     * <p>Constructor for WhatsAppActivities.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsAppActivities(MailerSend ref) {
        apiObjectReference = ref;
    }

    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities page(int page) {
        pageFilter = page;
        return this;
    }

    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities limit(int limit) {
        limitFilter = limit;
        return this;
    }

    /**
     * Filters by the ID of the item the activities belong to
     *
     * @param whatsappItemId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities whatsappItemId(String whatsappItemId) {
        whatsappItemIdFilter = whatsappItemId;
        return this;
    }

    /**
     * Filters by the ID of the message the activities belong to
     *
     * @param whatsappMessageId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities whatsappMessageId(String whatsappMessageId) {
        whatsappMessageIdFilter = whatsappMessageId;
        return this;
    }

    /**
     * Filters by the recipient's phone number in international format, WhatsApp username or BSUID
     *
     * @param recipient a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities recipient(String recipient) {
        recipientFilter = recipient;
        return this;
    }

    /**
     * Adds a type to filter by: queued, sent, delivered, read or failed.
     * Multiple types are combined with OR.
     *
     * @param type a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities addTypeFilter(String type) {
        typeFilter.add(type);
        return this;
    }

    /**
     * <p>clearTypeFilter.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities clearTypeFilter() {
        typeFilter.clear();
        return this;
    }

    /**
     * Only activities created strictly after this Unix timestamp (UTC) are returned
     *
     * @param dateFrom a long.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities dateFrom(long dateFrom) {
        dateFromFilter = dateFrom;
        return this;
    }

    /**
     * Only activities created strictly after this date are returned
     *
     * @param dateFrom a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities dateFrom(Date dateFrom) {
        dateFromFilter = dateFrom.getTime() / 1000;
        return this;
    }

    /**
     * Only activities created strictly before this Unix timestamp (UTC) are returned
     *
     * @param dateTo a long.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities dateTo(long dateTo) {
        dateToFilter = dateTo;
        return this;
    }

    /**
     * Only activities created strictly before this date are returned
     *
     * @param dateTo a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivities} object.
     */
    public WhatsAppActivities dateTo(Date dateTo) {
        dateToFilter = dateTo.getTime() / 1000;
        return this;
    }

    /**
     * <p>getActivities.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.activities.WhatsAppActivityList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppActivityList getActivities() throws MailerSendException {

        String endpoint = "/whatsapp/activities".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        WhatsAppActivityList response = api.getRequest(endpoint, WhatsAppActivityList.class);

        response.postDeserialize();

        return response;
    }

    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));

        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (whatsappItemIdFilter != null) {
            params.add("whatsapp_item_id=".concat(urlEncode(whatsappItemIdFilter)));
        }

        if (whatsappMessageIdFilter != null) {
            params.add("whatsapp_message_id=".concat(urlEncode(whatsappMessageIdFilter)));
        }

        if (recipientFilter != null) {
            params.add("recipient=".concat(urlEncode(recipientFilter)));
        }

        for (String t : typeFilter) {
            params.add("type[]=".concat(urlEncode(t)));
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
