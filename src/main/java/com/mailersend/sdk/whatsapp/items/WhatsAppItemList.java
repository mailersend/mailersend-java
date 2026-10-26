/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.items;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>WhatsAppItemList class.</p>
 *
 * Uses simple pagination: meta.total and meta.last_page are not returned,
 * so keep requesting the next page until links.next is null.
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppItemList extends PaginatedResponse {

    @SerializedName("data")
    public WhatsAppItem[] items;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (items != null) {
            for (WhatsAppItem i : items) {
                i.postDeserialize();
            }
        }
    }
}
