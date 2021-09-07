/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class ActivityEmail {

    @SerializedName("id")
    public String id;
    
    @SerializedName("from")
    public String from;
    
    @SerializedName("subject")
    public String subject;
    
    @SerializedName("text")
    public String text;
    
    @SerializedName("html")
    public String html;
    
    @SerializedName("status")
    public String status;
    
    @SerializedName("tags")
    public String[] tags;
    
    @SerializedName("created_at")
    public Date createdAt;
    
    @SerializedName("updated_at")
    public Date updatedAt;
    
    @SerializedName("recipient")
    public Recipient recipient;
}
