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
 * <p>TemplateCategory class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class TemplateCategory {

    @SerializedName("id")
    public String id;
    
    @SerializedName("name")
    public String name;
}
