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
 * <p>DomainVerificationAttributes class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class DomainVerificationAttributes {

    @SerializedName("dkim")
    public boolean dkim;
    
    @SerializedName("spf")
    public boolean spf;
    
    @SerializedName("mx")
    public boolean mx;
    
    @SerializedName("tracking")
    public boolean tracking;
    
    @SerializedName("cname")
    public boolean cname;
    
    @SerializedName("rp_cname")
    public boolean rpCname;
}
