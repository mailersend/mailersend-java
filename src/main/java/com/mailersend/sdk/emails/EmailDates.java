/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.emails;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * Parses the date strings returned by the emails endpoints
 */
final class EmailDates {

    private static final DateTimeFormatter SPACE_SEPARATED_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private EmailDates() {

        // intentionally left empty
    }


    /**
     * Converts an API date string to a java.util.Date. Returns null for empty or unparseable values
     *
     * @param value the date string as returned by the API
     * @return the parsed date or null
     */
    static Date parse(String value) {

        if (value == null || value.isBlank()) {

            return null;
        }

        try {

            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(value);

            return Date.from(Instant.from(ta));
        } catch (Exception e) {

            // the API may also return dates as "2020-06-04 12:00:00", assumed to be UTC
            try {

                LocalDateTime localDateTime = LocalDateTime.parse(value, SPACE_SEPARATED_FORMATTER);

                return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
            } catch (Exception ex) {

                return null;
            }
        }
    }
}
