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
 * <p>DomainDnsAttribute class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class DomainDnsAttribute {

    @SerializedName("hostname")
    public String hostname;
    
    @SerializedName("type")
    public String type;
    
    @SerializedName("value")
    public String value;
}
