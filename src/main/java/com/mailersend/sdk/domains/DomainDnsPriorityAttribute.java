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
 * <p>DomainDnsPriorityAttribute class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class DomainDnsPriorityAttribute extends DomainDnsAttribute {
    
    @SerializedName("priority")
    public String priority;
}
