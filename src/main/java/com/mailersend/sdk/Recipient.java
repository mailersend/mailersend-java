/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import com.google.gson.annotations.SerializedName;

public class Recipient {

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
