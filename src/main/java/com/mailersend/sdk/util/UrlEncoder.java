/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Encodes the values sent in request query strings
 *
 * @author mailersend
 * @version $Id: $Id
 */
public final class UrlEncoder {

    private UrlEncoder() {

        // intentionally left empty
    }


    /**
     * URL-encodes a query string value as UTF-8
     *
     * @param value the value to encode
     * @return the encoded value
     */
    public static String encode(String value) {

        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
