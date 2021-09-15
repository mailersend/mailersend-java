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
 * Keeps the links from a returned MailerSend response (e.g. Activities)
 */
public class ResponseLinks {

    @SerializedName("first")
    public String first;
    
    @SerializedName("last")
    public String last;
    
    @SerializedName("prev")
    public String prev;
    
    @SerializedName("next")
    public String next;
}
