/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp.templates;

import com.google.gson.annotations.SerializedName;
import com.mailersend.sdk.util.PaginatedResponse;

/**
 * <p>WhatsAppTemplateList class.</p>
 *
 * Uses simple pagination: meta.total and meta.last_page are not returned,
 * so keep requesting the next page until links.next is null.
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsAppTemplateList extends PaginatedResponse {

    @SerializedName("data")
    public WhatsAppTemplate[] templates;

    /**
     * <p>postDeserialize.</p>
     */
    public void postDeserialize() {
        if (templates != null) {
            for (WhatsAppTemplate t : templates) {
                t.postDeserialize();
            }
        }
    }
}
