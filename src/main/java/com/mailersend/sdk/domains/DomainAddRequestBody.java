/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;


import com.google.gson.annotations.SerializedName;


/**
 * Used by the domain add builder to prepare the API request.
 * Do not use directly
 */
class DomainAddRequestBody {

    @SerializedName("name")
    public String name;
    
    @SerializedName("return_path_subdomain")
    public String returnPathSubdomainValue = null;
    
    @SerializedName("custom_tracking_subdomain")
    public String customTrackingSubdomainValue = null;
    
    @SerializedName("inbound_routing_subdomain")
    public String inboundRoutingSubdomainValue = null;
    
    
    /**
     * <p>reset.</p>
     */
    public void reset() {
        
        name = null;
        returnPathSubdomainValue = null;
        customTrackingSubdomainValue = null;
        inboundRoutingSubdomainValue = null;
    }
    
}
