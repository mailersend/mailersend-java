/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * <p>TemplateBuilder class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class TemplateBuilder {

    private MailerSend apiObjectReference;

    private TemplateBuilderBody builderBody;


    /**
     * No instantiation from outside the sdk.
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected TemplateBuilder(MailerSend ref) {

        apiObjectReference = ref;
        builderBody = new TemplateBuilderBody();
    }


    /**
     * Set the template name (max 50 characters).
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.templates.TemplateBuilder} object.
     */
    public TemplateBuilder name(String name) {

        builderBody.name = name;

        return this;
    }


    /**
     * Set the HTML body of the template.
     *
     * @param html a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.templates.TemplateBuilder} object.
     */
    public TemplateBuilder html(String html) {

        builderBody.html = html;

        return this;
    }


    /**
     * Set the plain text version of the template.
     *
     * @param text a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.templates.TemplateBuilder} object.
     */
    public TemplateBuilder text(String text) {

        builderBody.text = text;

        return this;
    }


    /**
     * Set the subject of the template.
     *
     * @param subject a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.templates.TemplateBuilder} object.
     */
    public TemplateBuilder subject(String subject) {

        builderBody.subject = subject;

        return this;
    }


    /**
     * Set the domain ID to associate with the template.
     *
     * @param domainId a {@link java.lang.String} object.
     * @return a {@link com.mailersend.sdk.templates.TemplateBuilder} object.
     */
    public TemplateBuilder domainId(String domainId) {

        builderBody.domainId = domainId;

        return this;
    }


    /**
     * Set the category IDs to associate with the template.
     *
     * @param categories an array of category ID strings.
     * @return a {@link com.mailersend.sdk.templates.TemplateBuilder} object.
     */
    public TemplateBuilder categories(String[] categories) {

        builderBody.categories = categories;

        return this;
    }


    /**
     * Set the tags for the template (max 5 items, each max 191 chars).
     *
     * @param tags an array of tag strings.
     * @return a {@link com.mailersend.sdk.templates.TemplateBuilder} object.
     */
    public TemplateBuilder tags(String[] tags) {

        builderBody.tags = tags;

        return this;
    }


    /**
     * Set whether plain text should be auto-generated from the HTML.
     *
     * @param autoGenerate a boolean.
     * @return a {@link com.mailersend.sdk.templates.TemplateBuilder} object.
     */
    public TemplateBuilder autoGenerate(boolean autoGenerate) {

        builderBody.autoGenerate = autoGenerate;

        return this;
    }


    /**
     * Creates a new template via POST /v1/templates.
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     * @return a {@link com.mailersend.sdk.templates.Template} object.
     */
    public Template createTemplate() throws MailerSendException {

        String endpoint = "/templates";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(builderBody);

        builderBody.reset();

        TemplateResponse response = api.postRequest(endpoint, json, TemplateResponse.class);

        if (response.template != null) {

            response.template.postDeserialize();
        }

        return response.template;
    }


    /**
     * Updates an existing template via PUT /v1/templates/{templateId}.
     * Only templates created via API (origin=api) can be updated.
     *
     * @param templateId a {@link java.lang.String} object.
     * @throws com.mailersend.sdk.exceptions.MailerSendException if any.
     * @return a {@link com.mailersend.sdk.templates.Template} object.
     */
    public Template updateTemplate(String templateId) throws MailerSendException {

        String endpoint = "/templates/" + templateId;

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(builderBody);

        builderBody.reset();

        TemplateResponse response = api.putRequest(endpoint, json, TemplateResponse.class);

        if (response.template != null) {

            response.template.postDeserialize();
        }

        return response.template;
    }
}
