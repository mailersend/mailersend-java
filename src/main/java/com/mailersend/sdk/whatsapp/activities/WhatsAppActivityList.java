/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.activities;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>WhatsAppActivityList class.</p>
 *
 * Uses simple pagination: meta.total and meta.last_page are not returned,
 * so keep requesting the next page until links.next is null.
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppActivityList extends PaginatedResponse {

    @SerializedName("data")
    public WhatsAppActivity[] activities;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (activities != null) {
            for (WhatsAppActivity a : activities) {
                a.postDeserialize();
            }
        }
    }
}
