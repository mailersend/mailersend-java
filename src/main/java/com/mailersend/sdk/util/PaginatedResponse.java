package com.mailersend.sdk.util;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.MailerSendResponse;

public class PaginatedResponse extends MailerSendResponse{

    @SerializedName("meta")
    private ResponseMeta meta;
    
    @SerializedName("links")
    private ResponseLinks links;
}
