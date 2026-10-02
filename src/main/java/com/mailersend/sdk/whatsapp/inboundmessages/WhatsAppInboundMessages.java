/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.inboundmessages;

import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>WhatsAppInboundMessages class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppInboundMessages {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;
    private String whatsappAccountIdFilter;
    private String[] typesFilter;
    private Integer dateFromFilter;
    private Integer dateToFilter;

    /**
     * <p>Constructor for WhatsAppInboundMessages.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsAppInboundMessages(MailerSend ref) {
        apiObjectReference = ref;
    }

    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages} object.
     */
    public WhatsAppInboundMessages page(int page) {
        pageFilter = page;
        return this;
    }

    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages} object.
     */
    public WhatsAppInboundMessages limit(int limit) {
        limitFilter = limit;
        return this;
    }

    /**
     * Filters by the MailerSend sender ID of the phone number that received the messages
     *
     * @param whatsappAccountId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages} object.
     */
    public WhatsAppInboundMessages whatsappAccountId(String whatsappAccountId) {
        whatsappAccountIdFilter = whatsappAccountId;
        return this;
    }

    /**
     * Filters by message type. Multiple values are combined with OR.
     * Possible values: text, image, audio, video, document, sticker, location, contacts,
     * interactive, button, order, reaction, system, unknown, unsupported
     *
     * @param types an array of {@link java.lang.String} objects.
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages} object.
     */
    public WhatsAppInboundMessages types(String[] types) {
        typesFilter = types;
        return this;
    }

    /**
     * Only messages received strictly after this Unix timestamp (UTC) are returned
     *
     * @param dateFrom a int.
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages} object.
     */
    public WhatsAppInboundMessages dateFrom(int dateFrom) {
        dateFromFilter = dateFrom;
        return this;
    }

    /**
     * Only messages received strictly before this Unix timestamp (UTC) are returned
     *
     * @param dateTo a int.
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessages} object.
     */
    public WhatsAppInboundMessages dateTo(int dateTo) {
        dateToFilter = dateTo;
        return this;
    }

    /**
     * <p>getInboundMessages.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessageList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppInboundMessageList getInboundMessages() throws MailerSendException {

        String endpoint = "/whatsapp/inbound-messages".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        WhatsAppInboundMessageList response = api.getRequest(endpoint, WhatsAppInboundMessageList.class);

        response.postDeserialize();

        return response;
    }

    /**
     * <p>getInboundMessage.</p>
     *
     * @param inboundMessageId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.inboundmessages.WhatsAppInboundMessage} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppInboundMessage getInboundMessage(String inboundMessageId) throws MailerSendException {

        String endpoint = "/whatsapp/inbound-messages/".concat(inboundMessageId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleWhatsAppInboundMessageResponse response = api.getRequest(endpoint, SingleWhatsAppInboundMessageResponse.class);

        response.inboundMessage.postDeserialize();

        return response.inboundMessage;
    }

    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));

        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (whatsappAccountIdFilter != null) {
            params.add("whatsapp_account_id=".concat(whatsappAccountIdFilter));
        }

        if (typesFilter != null) {
            for (String t : typesFilter) {
                params.add("type[]=".concat(t));
            }
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
}
