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
import com.mailersend.sdk.activities.attributes.Activity;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.ResponseLinks;
import com.mailersend.sdk.util.ResponseMeta;

public class Activities extends MailerSendResponse {

    @SerializedName("data")
    public Activity[] activities;
    
    @SerializedName("meta")
    private ResponseMeta meta;
    
    @SerializedName("links")
    private ResponseLinks links;
    
    protected transient MailerSend mailersendObj;
    
    protected transient Date dateFrom;
    protected transient Date dateTo;
    protected transient String[] events;
    protected transient String domainId;
    
    
    /**
     * Returns the current results page
     * @return
     */
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
    
    
    /**
     * Gets the previous activities page using the original filters
     * @return
     * @throws MailerSendException
     */
    public Activities previousPage() throws MailerSendException {
        
        if (this.mailersendObj != null) {
            
            int page = 1;
            int limit = 25;
            
            if (meta != null) {
                
                page = meta.currentPage - 1;
                limit = meta.limit;
            }
            
            
            if (page == 0) {
                
                page = 1;
            }
            
            return this.mailersendObj.getActivities(domainId, page, limit, dateFrom, dateTo, events);
        }
        
        return null;
    }
    
    
    /**
     * Protected constructor to prevent creating new instances from outside the SDK
     */
    protected Activities() {
        
        // intentionally left empty
    }
    
    
    /**
     * Is called to perform any actions after the deserialization of the response
     * to the /activities endpoint 
     * Do not call directly
     */
    protected void postDeserialize() {
        
        for (Activity activity : activities) {
            
            activity.postDeserialize();
        }
    }
}
