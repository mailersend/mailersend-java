/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.recipients;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.util.ApiEmail;
import com.mailersend.sdk.util.DateParser;

/**
 * <p>Recipient class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class Recipient {


    @SerializedName("id")
    public String id;

    @SerializedName("email")
    public String email;
    
    @SerializedName("created_at")
    private String createdAtString;
    
    @SerializedName("updated_at")
    private String updatedAtString;
    
    @SerializedName("deleted_at")
    private String deletedAtString;
    
    @SerializedName("emails")
    public ApiEmail[] emails;

    @SerializedName("domain")
    public Domain domain;

    public Date createdAt;
    
    public Date updatedAt;
    
    public Date deletedAt;
    
    
    
    /**
     * Converts the retrieved dates to java.util.Date
     */
    protected void parseDates() {
        
        createdAt = DateParser.parse(createdAtString);
        updatedAt = DateParser.parse(updatedAtString);
        deletedAt = DateParser.parse(deletedAtString);

        if (domain != null) {

            domain.postDeserialize();
        }

        if (emails != null) {

            for (ApiEmail email : emails) {

                email.parseDates();
            }
        }
    }
    
}
