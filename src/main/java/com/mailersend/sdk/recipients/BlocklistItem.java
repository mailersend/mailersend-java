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
 * <p>BlocklistItem class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class BlocklistItem {

    @SerializedName("id")
    public String id;
    
    @SerializedName("type")
    public String type;
    
    @SerializedName("pattern")
    public String pattern;
    
    @SerializedName("domain")
    public BlocklistDomain domain;
    
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
        
        if (domain != null) {
        
            domain.parseDates();
        }
    }
}
