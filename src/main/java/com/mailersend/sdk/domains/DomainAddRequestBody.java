package com.mailersend.sdk.domains;


import com.google.gson.annotations.SerializedName;


class DomainAddRequestBody {

    @SerializedName("name")
    public String name;
    
    @SerializedName("return_path_subdomain")
    public String returnPathSubdomainValue = null;
    
    @SerializedName("custom_tracking_subdomain")
    public String customTrackingSubdomainValue = null;
    
    @SerializedName("inbound_routing_subdomain")
    public String inboundRoutingSubdomainValue = null;
    
    
    public void reset() {
        
        name = null;
        returnPathSubdomainValue = null;
        customTrackingSubdomainValue = null;
        inboundRoutingSubdomainValue = null;
    }
    
}
