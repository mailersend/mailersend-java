/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import com.google.gson.annotations.SerializedName;

/**
 * <p>EmailSettings class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class EmailSettings {

    @SerializedName("track_clicks")
    public Boolean trackClicks;

    @SerializedName("track_opens")
    public Boolean trackOpens;

    @SerializedName("track_content")
    public Boolean trackContent;
}
