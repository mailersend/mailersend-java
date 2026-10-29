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
import com.mailersend.sdk.util.DateParser;

/**
 * <p>BlocklistDomain class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class BlocklistDomain {

    @SerializedName("id")
    public String id;
    
    @SerializedName("name")
    public String name;
    
    @SerializedName("created_at")
    private String createdAtString;
    
    @SerializedName("updated_at")
    private String updatedAtString;
    
    public Date createdAt;
    
    public Date updatedAt;
    
    
    /**
     * Converts the retrieved dates to java.util.Date
     */
    protected void parseDates() {
        
        createdAt = DateParser.parse(createdAtString);
        updatedAt = DateParser.parse(updatedAtString);
    }
}
