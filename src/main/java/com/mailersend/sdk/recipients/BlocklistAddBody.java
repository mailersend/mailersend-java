/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.recipients;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

class BlocklistAddBody {

    @SerializedName("domain_id")
    public String domainId;
    
    @SerializedName("recipients")
    public ArrayList<String> recipients = new ArrayList<String>();
    
    @SerializedName("patterns")
    public ArrayList<String> patterns = new ArrayList<String>();
    
    
    /**
     * Resets the values so that the object can be reused
     */
    public void reset() {
        
        domainId = null;
        recipients.clear();
        patterns.clear();
    }
}
