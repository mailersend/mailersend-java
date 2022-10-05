/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.domains;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>DomainsList class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class DomainsList extends PaginatedResponse {

    
    @SerializedName("data")
    public Domain[] domains;
}
