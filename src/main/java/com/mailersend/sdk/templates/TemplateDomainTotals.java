/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.templates;

import com.google.gson.annotations.SerializedName;

/**
 * <p>TemplateDomainTotals class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class TemplateDomainTotals {
    
    @SerializedName("hard_bounced")
    public int hardBounced;
    
    @SerializedName("soft_bounced")
    public int softBounced;
    
    @SerializedName("sent")
    public int sent;
    
    @SerializedName("delivered")
    public int delivered;
}
