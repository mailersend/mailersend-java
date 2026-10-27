/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.items;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>WhatsAppItems class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppItems {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;
    private String recipientFilter;
    private String whatsappMessageIdFilter;
    private ArrayList<String> statusFilter = new ArrayList<String>();
    private String whatsappAccountIdFilter;
    private String fromFilter;
    private String templateIdFilter;
    private Long dateFromFilter;
    private Long dateToFilter;

    /**
     * <p>Constructor for WhatsAppItems.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsAppItems(MailerSend ref) {
        apiObjectReference = ref;
    }

    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems page(int page) {
        pageFilter = page;
        return this;
    }

    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems limit(int limit) {
        limitFilter = limit;
        return this;
    }

    /**
     * Filters by the recipient's phone number in international format, WhatsApp username or BSUID
     *
     * @param recipient a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems recipient(String recipient) {
        recipientFilter = recipient;
        return this;
    }

    /**
     * Filters by the ID of the message the items belong to
     *
     * @param whatsappMessageId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems whatsappMessageId(String whatsappMessageId) {
        whatsappMessageIdFilter = whatsappMessageId;
        return this;
    }

    /**
     * Adds a status to filter by: queued, sent, delivered, read or failed.
     * Multiple statuses are combined with OR.
     *
     * @param status a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems addStatusFilter(String status) {
        statusFilter.add(status);
        return this;
    }

    /**
     * <p>clearStatusFilter.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems clearStatusFilter() {
        statusFilter.clear();
        return this;
    }

    /**
     * Filters by the MailerSend sender ID the messages were sent from
     *
     * @param whatsappAccountId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems whatsappAccountId(String whatsappAccountId) {
        whatsappAccountIdFilter = whatsappAccountId;
        return this;
    }

    /**
     * Filters by the sender's phone number in international format
     *
     * @param from a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems from(String from) {
        fromFilter = from;
        return this;
    }

    /**
     * Filters by the ID of the template that was sent
     *
     * @param templateId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems templateId(String templateId) {
        templateIdFilter = templateId;
        return this;
    }

    /**
     * Only items created strictly after this Unix timestamp (UTC) are returned
     *
     * @param dateFrom a long.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems dateFrom(long dateFrom) {
        dateFromFilter = dateFrom;
        return this;
    }

    /**
     * Only items created strictly after this date are returned
     *
     * @param dateFrom a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems dateFrom(Date dateFrom) {
        dateFromFilter = dateFrom.getTime() / 1000;
        return this;
    }

    /**
     * Only items created strictly before this Unix timestamp (UTC) are returned
     *
     * @param dateTo a long.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems dateTo(long dateTo) {
        dateToFilter = dateTo;
        return this;
    }

    /**
     * Only items created strictly before this date are returned
     *
     * @param dateTo a {@link java.util.Date} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItems} object.
     */
    public WhatsAppItems dateTo(Date dateTo) {
        dateToFilter = dateTo.getTime() / 1000;
        return this;
    }

    /**
     * <p>getItems.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItemList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppItemList getItems() throws MailerSendException {

        String endpoint = "/whatsapp/items".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        WhatsAppItemList response = api.getRequest(endpoint, WhatsAppItemList.class);

        response.postDeserialize();

        return response;
    }

    /**
     * Retrieves a single item, together with its recipient and every status it moved through
     *
     * @param itemId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.items.WhatsAppItem} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppItem getItem(String itemId) throws MailerSendException {

        String endpoint = "/whatsapp/items/".concat(itemId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleWhatsAppItemResponse response = api.getRequest(endpoint, SingleWhatsAppItemResponse.class);

        response.item.postDeserialize();

        return response.item;
    }

    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));

        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (recipientFilter != null) {
            params.add("recipient=".concat(urlEncode(recipientFilter)));
        }

        if (whatsappMessageIdFilter != null) {
            params.add("whatsapp_message_id=".concat(urlEncode(whatsappMessageIdFilter)));
        }

        for (String s : statusFilter) {
            params.add("status[]=".concat(urlEncode(s)));
        }

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
