/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

class TemplateResponse extends MailerSendResponse{

    @SerializedName("data")
    public Template template;
    
}
