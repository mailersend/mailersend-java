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
 * <p>DomainDnsRecords class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class DomainDnsRecords {

    @SerializedName("id")
    public String id;
    
    @SerializedName("spf")
    public DomainDnsAttribute spf;
    
    /**
     * @deprecated Use {@link #dkimMs1} and {@link #dkimMs2} instead.
     */
    @SerializedName("dkim")
    public DomainDnsAttribute dkim;

    @SerializedName("dkim_ms1")
    public DomainDnsAttribute dkimMs1;

    @SerializedName("dkim_ms2")
    public DomainDnsAttribute dkimMs2;
    
    @SerializedName("return_path")
    public DomainDnsAttribute returnPath;
    
    @SerializedName("custom_tracking")
    public DomainDnsAttribute customTracking;
    
    @SerializedName("inbound_routing")
    public DomainDnsPriorityAttribute inboundRouting;
}
