/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import com.google.gson.annotations.SerializedName;

public class DomainDnsRecords {

    @SerializedName("id")
    public String id;
    
    @SerializedName("spf")
    public DomainDnsAttribute spf;
    
    @SerializedName("dkim")
    public DomainDnsAttribute dkim;
    
    @SerializedName("return_path")
    public DomainDnsAttribute returnPath;
    
    @SerializedName("custom_tracking")
    public DomainDnsAttribute customTracking;
    
    @SerializedName("inbound_routing")
    public DomainDnsPriorityAttribute inboundRouting;
}
