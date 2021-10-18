/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tokens;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class TokenAddRequestBody {

    @SerializedName("name")
    public String name;
    
    @SerializedName("domain_id")
    public String domainId = null;
    
    ArrayList<String> scopes = new ArrayList<String>();
    
    
    /**
     * Resets the token add request body so that it can be reused
     */
    public void reset() {
        
        name = null;
        domainId = null;
        scopes.clear();
    }
}
