/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.templates;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * <p>WhatsAppTemplates class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppTemplates {

    private MailerSend apiObjectReference;

    private int pageFilter = 1;
    private int limitFilter = 25;
    private String whatsappAccountIdFilter;
    private String statusFilter;
    private String categoryFilter;
    private String languageFilter;

    /**
     * <p>Constructor for WhatsAppTemplates.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsAppTemplates(MailerSend ref) {
        apiObjectReference = ref;
    }

    /**
     * <p>page.</p>
     *
     * @param page a int.
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplates} object.
     */
    public WhatsAppTemplates page(int page) {
        pageFilter = page;
        return this;
    }

    /**
     * <p>limit.</p>
     *
     * @param limit a int.
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplates} object.
     */
    public WhatsAppTemplates limit(int limit) {
        limitFilter = limit;
        return this;
    }

    /**
     * Filters by the MailerSend sender ID the templates belong to
     *
     * @param whatsappAccountId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplates} object.
     */
    public WhatsAppTemplates whatsappAccountId(String whatsappAccountId) {
        whatsappAccountIdFilter = whatsappAccountId;
        return this;
    }

    /**
     * Filters by the template's review status, for example approved, pending or rejected.
     * Only approved templates can be sent.
     *
     * @param status a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplates} object.
     */
    public WhatsAppTemplates status(String status) {
        statusFilter = status;
        return this;
    }

    /**
     * Filters by category: UTILITY, AUTHENTICATION or MARKETING
     *
     * @param category a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplates} object.
     */
    public WhatsAppTemplates category(String category) {
        categoryFilter = category;
        return this;
    }

    /**
     * Filters by the template's language code, for example en_US
     *
     * @param language a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplates} object.
     */
    public WhatsAppTemplates language(String language) {
        languageFilter = language;
        return this;
    }

    /**
     * <p>getTemplates.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplateList} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppTemplateList getTemplates() throws MailerSendException {

        String endpoint = "/whatsapp/templates".concat(prepareParamsUrl());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        WhatsAppTemplateList response = api.getRequest(endpoint, WhatsAppTemplateList.class);

        response.postDeserialize();

        return response;
    }

    /**
     * <p>getTemplate.</p>
     *
     * @param templateId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.whatsapp.templates.WhatsAppTemplate} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     */
    public WhatsAppTemplate getTemplate(String templateId) throws MailerSendException {

        String endpoint = "/whatsapp/templates/".concat(templateId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        SingleWhatsAppTemplateResponse response = api.getRequest(endpoint, SingleWhatsAppTemplateResponse.class);

        response.template.postDeserialize();

        return response.template;
    }

    private String prepareParamsUrl() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));

        params.add("limit=".concat(String.valueOf(limitFilter)));

        if (whatsappAccountIdFilter != null) {
            params.add("whatsapp_account_id=".concat(urlEncode(whatsappAccountIdFilter)));
        }

        if (statusFilter != null) {
            params.add("status=".concat(urlEncode(statusFilter)));
        }

        if (categoryFilter != null) {
            params.add("category=".concat(urlEncode(categoryFilter)));
        }

        if (languageFilter != null) {
            params.add("language=".concat(urlEncode(languageFilter)));
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
