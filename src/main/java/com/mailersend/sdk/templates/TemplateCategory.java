/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import com.google.gson.annotations.SerializedName;

public class TemplateCategory {

    @SerializedName("id")
    public String id;
    
    @SerializedName("name")
    public String name;
}
