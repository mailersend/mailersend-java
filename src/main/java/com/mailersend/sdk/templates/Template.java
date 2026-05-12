/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Template class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Template {
    
    @SerializedName("id")
    public String id;
    
    @SerializedName("name")
    public String name;
    
    @SerializedName("type")
    public String type;
    
    @SerializedName("image_path")
    public String imagePath;

    @SerializedName("description")
    public String description;

    @SerializedName("tags")
    public String[] tags;

    @SerializedName("variables")
    public Map<String, Object> variables;

    public Date createdAt;

    @SerializedName("created_at")
    private String createdAtStr;

    public Date updatedAt;

    @SerializedName("updated_at")
    private String updatedAtStr;

    @SerializedName("category")
    public TemplateCategory category;
    
    @SerializedName("domain")
    public TemplateDomain domain;
    
    @SerializedName("template_stats")
    public TemplateStats stats;
    
    
    /**
     * Is called to perform any actions after the deserialization of the response
     * Do not call directly
     */
    protected void postDeserialize() {

        parseDates();

        if (domain != null) {

            domain.postDeserialize();
        }

        if (stats != null) {

            stats.postDeserialize();
        }
    }


    /**
     * Parses the string dates from the response into java.util.Date objects
     */
    protected void parseDates() {

        TemporalAccessor ta;
        Instant instant;

        if (createdAtStr != null && !createdAtStr.isBlank()) {

            ta = DateTimeFormatter.ISO_INSTANT.parse(createdAtStr);
            instant = Instant.from(ta);
            createdAt = Date.from(instant);
        }

        if (updatedAtStr != null && !updatedAtStr.isBlank()) {

            ta = DateTimeFormatter.ISO_INSTANT.parse(updatedAtStr);
            instant = Instant.from(ta);
            updatedAt = Date.from(instant);
        }

    }
}
