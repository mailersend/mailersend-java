/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipient {
    
    @Expose(serialize = false, deserialize = true)
    @SerializedName("id")
    public String id = null;
    
    @Expose(serialize = false, deserialize = true)
    @SerializedName("created_at")
    public Date createdAt = null;
    
    @Expose(serialize = false, deserialize = true)
    @SerializedName("updated_at")
    public Date updatedAt = null;
    
    @Expose(serialize = false, deserialize = true)
    @SerializedName("deleted_at")
    public Date deletedAt = null;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;
    
    
    /**
     * Simple constructor to set the properties
     * @param name
     * @param email
     */
    public Recipient(String name, String email) {
        
        this.name = name;
        this.email = email;
    }
    
    
    /**
     * Allow the recipient to be instantiated without forcing the name and email attributes
     */
    public Recipient() {
        
        // left empty on purpose
    }
}
