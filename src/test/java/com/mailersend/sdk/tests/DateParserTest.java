/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.util.DateParser;

public class DateParserTest {

    /**
     * Tests that both date formats the API returns are parsed, the space separated one as UTC
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("apiDates")
    public void testParseReadsApiDateFormats(String label, String value, Date expected) {

        assertEquals(expected, DateParser.parse(value));
    }


    static Stream<Arguments> apiDates() {

        return Stream.of(
            Arguments.of("iso 8601 with microseconds", "2022-04-20T11:36:15.123000Z", Date.from(Instant.parse("2022-04-20T11:36:15.123Z"))),
            Arguments.of("space separated", "2020-06-10 10:10:13", Date.from(Instant.parse("2020-06-10T10:10:13Z")))
        );
    }


    /**
     * Tests that a missing or unparseable date is returned as null instead of failing the response
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("unparseableDates")
    public void testParseReturnsNullForMissingOrUnparseableValues(String label, String value) {

        assertNull(DateParser.parse(value));
    }


    static Stream<Arguments> unparseableDates() {

        return Stream.of(
            Arguments.of("null", null),
            Arguments.of("empty string, as the API returns for deleted_at", ""),
            Arguments.of("unparseable", "yesterday")
        );
    }
}
