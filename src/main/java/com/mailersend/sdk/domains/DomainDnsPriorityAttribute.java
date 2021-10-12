/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import com.google.gson.annotations.SerializedName;

public class DomainDnsPriorityAttribute extends DomainDnsAttribute {
    
    @SerializedName("priority")
    public String priority;
}
