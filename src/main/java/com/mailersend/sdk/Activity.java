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

public class Activity {

    @SerializedName("id")
    public String id;
    
    @SerializedName("created_at")
    public Date createdAt;
    
    @SerializedName("updated_at")
    public Date updatedAt;
    
    @SerializedName("type")
    public String type;
    
    @SerializedName("email")
    public ActivityEmail email;
    
}
