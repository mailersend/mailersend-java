/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.messages;

import java.util.ArrayList;

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
