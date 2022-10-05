package com.mailersend.sdk.util;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

/**
 * <p>PaginatedResponse class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class PaginatedResponse extends MailerSendResponse {

    @SerializedName("meta")
    public ResponseMeta meta;
    
    @SerializedName("links")
    public ResponseLinks links;
}
