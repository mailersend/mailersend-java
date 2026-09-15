/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.whatsapp;

import com.mailersend.sdk.MailerSend;

/**
 * <p>WhatsApp class.</p>
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class WhatsApp {

    private MailerSend apiObjectReference;
    private WhatsAppBuilder builder;

    /**
     * <p>Constructor for WhatsApp.</p>
     *
     * @param ref a {@link com.mailersend.sdk.MailerSend} object.
     */
    public WhatsApp(MailerSend ref) {
        apiObjectReference = ref;
        builder = new WhatsAppBuilder(ref);
    }

    /**
     * <p>builder.</p>
     *
     * @return a {@link com.mailersend.sdk.whatsapp.WhatsAppBuilder} object.
     */
    public WhatsAppBuilder builder() {
        return builder;
    }
}
