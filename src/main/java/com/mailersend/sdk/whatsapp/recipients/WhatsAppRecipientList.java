/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.recipients;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>WhatsAppRecipientList class.</p>
 *
 * Uses simple pagination: meta.total and meta.last_page are not returned,
 * so keep requesting the next page until links.next is null.
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppRecipientList extends PaginatedResponse {

    @SerializedName("data")
    public WhatsAppRecipient[] recipients;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (recipients != null) {
            for (WhatsAppRecipient r : recipients) {
                r.postDeserialize();
            }
        }
    }
}
