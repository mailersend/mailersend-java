/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import com.google.gson.annotations.SerializedName;

public class Template {
    
    @SerializedName("id")
    public String id;
    
    @SerializedName("name")
    public String name;
    
    @SerializedName("type")
    public String type;
    
    @SerializedName("image_path")
    public String imagePath;
    
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
        
        if (domain != null) {
            
            domain.postDeserialize();
        }
        
        if (stats != null) {
        
            stats.postDeserialize();
        }
    }
}
