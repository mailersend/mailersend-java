/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.ResponseLinks;
import com.mailersend.sdk.util.ResponseMeta;

public class Activities extends MailerSendResponse {

    @SerializedName("data")
    public Activity[] activities;
    
    
    private ResponseMeta meta;
    
    private ResponseLinks links;
    
    protected transient MailerSend mailersendObj;
    
    protected transient LocalDateTime dateFrom;
    protected transient LocalDateTime dateTo;
    protected transient String[] events;
    protected transient String domainId;
    
    public int getCurrentPage() {
        
        if (meta != null) {
            
            return meta.currentPage;
        }
        
        return 0;
    }
    
    
    /**
     * Gets the next activities page using the original filters
     * @return
     * @throws MailerSendException
     */
    public Activities nextPage() throws MailerSendException {
        
        if (this.mailersendObj != null) {
            
            int page = 1;
            int limit = 25;
            
            if (meta != null) {
                
                page = meta.currentPage + 1;
                limit = meta.limit;
            }
            
            return this.mailersendObj.getActivities(domainId, page, limit, dateFrom, dateTo, events);
        }
        
        return null;
    }
}
