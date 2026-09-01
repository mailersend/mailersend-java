/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import com.google.gson.annotations.SerializedName;

/**
 * Used by the template builder to prepare the API request body.
 * Do not use directly.
 */
class TemplateBuilderBody {

    @SerializedName("name")
    public String name;

    @SerializedName("html")
    public String html;

    @SerializedName("text")
    public String text;

    @SerializedName("subject")
    public String subject;

    @SerializedName("domain_id")
    public String domainId;

    @SerializedName("categories")
    public String[] categories;

    @SerializedName("tags")
    public String[] tags;

    @SerializedName("auto_generate")
    public Boolean autoGenerate;


    /**
     * <p>reset.</p>
     */
    public void reset() {

        name = null;
        html = null;
        text = null;
        subject = null;
        domainId = null;
        categories = null;
        tags = null;
        autoGenerate = null;
    }
}
