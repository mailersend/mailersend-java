/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.util;

import com.google.gson.annotations.SerializedName;

/**
 * Keeps the meta information of a MailerSend response
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class ResponseMeta {

    @SerializedName("current_page")
    public int currentPage;
    
    @SerializedName("from")
    public int from;
    
    @SerializedName("path")
    public String path;
    
    @SerializedName("per_page")
    public int limit;
    
    @SerializedName("to")
    public int to;
    
    @SerializedName("last_page")
    public int lastPage;
}
