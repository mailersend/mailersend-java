/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.recipients;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>BlocklistListResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class BlocklistListResponse extends PaginatedResponse {

    @SerializedName("data")
    public BlocklistItem[] items;
    
    
    /**
     * Is called to perform any actions after the deserialization of the response
     * Do not call directly
     */
    public void postDeserialize() {
        
        if (items == null) {
            
            return;
        }
        
        for (BlocklistItem item : items) {
            
            item.parseDates();
        }
    }
}
