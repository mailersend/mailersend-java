package com.mailersend.sdk.tests;

import static com.mailersend.sdk.tests.CapturingHttpClient.json;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.inboundroutes.CatchFilter;
import com.mailersend.sdk.inboundroutes.Filter;
import com.mailersend.sdk.inboundroutes.Forward;
import com.mailersend.sdk.inboundroutes.InboundFilter;
import com.mailersend.sdk.inboundroutes.InboundRoute;
import com.mailersend.sdk.inboundroutes.InboundRoutesList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class InboundRoutesTest {

    private static final String INBOUND_URL = "https://api.mailersend.com/v1/inbound";

    /** The inbound route of the docs, with dns_checked_at set */
    private static final String ROUTE = "{\"id\":\"v57gn2j67qlr6z4d\",\"name\":\"Test name\","
            + "\"address\":\"imxbfnpzn7cbhak9lef5@inbound.mailersend.net\",\"domain\":\"test.mailersend.com\","
            + "\"dns_checked_at\":\"2026-01-15T10:20:30.000000Z\",\"enabled\":true,"
            + "\"filters\":[{\"type\":\"catch_recipient\",\"key\":null,\"comparer\":\"equal\",\"value\":\"test\"},"
            + "{\"type\":\"match_all\",\"key\":null,\"comparer\":null,\"value\":null}],"
            + "\"forwards\":[{\"type\":\"webhook\",\"value\":\"https://yourapp.com/hook\",\"secret\":\"jYhafQeTiZgw0qWclkUA7cbqTG3Zfh2j\"}],"
            + "\"priority\":100,\"mxValues\":{\"priority\":\"10\",\"target\":\"inbound.mailersend.net\"},"
            + "\"catch_type\":\"one\",\"match_type\":\"one\",\"include_attachments\":false}";

    private static final String ROUTE_RESPONSE = "{\"data\":" + ROUTE + "}";

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException
    {
        VcrRecorder.useRecording("InboundRoutesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException
    {
        VcrRecorder.stopRecording();
    }

    @Test
    public void addInboundRouteTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Forward forward = new Forward();
            forward.type = "webhook";
            forward.value = "https://example-domain.com";
            forward.secret = "asdfgh";

            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .name("Test inbound name")
                .domainEnabled(false)
                .matchFilter("match_all")
                .forwards(new Forward[] { forward })
                .addRoute();

        } catch (MailerSendException ex) {
            fail();
        }
    }

    @Test
    public void inboundRoutesListTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            InboundRoutesList routes = ms.inboundRoutes().getRoutes();

            assertEquals(routes.responseStatusCode, 200);

            for (InboundRoute route : routes.routes) {
                System.out.println(route.id);
            }

        } catch (MailerSendException e) {

            fail();
        }
    }

    @Test
    public void singleInboundRouteTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            InboundRoute route = ms.inboundRoutes().getRoute(TestHelper.inboundRouteId);

            System.out.println(route.id);
            System.out.println(route.name);

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void updateInboundRouteTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Forward forward = new Forward();
            forward.type = "webhook";
            forward.value = "https://example.com";
            forward.secret = "asdfgh";

            InboundRoute route = ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .name("Updated route name")
                .domainEnabled(false)
                .matchFilter("match_all")
                .forwards(new Forward[] { forward })
                .updateRoute(TestHelper.inboundRouteId);

            assertEquals(route.name, "Updated route name");

        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void deleteInboundRouteTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            boolean result = ms.inboundRoutes().deleteRoute(TestHelper.inboundRouteId);

            assertTrue(result);
        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void inbound_domain_required_when_domain_enabled_is_true() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(true)
                // inboundDomain not set
                .matchFilter("match_all")
                .addRoute()
        );

        assertEquals("inbound_domain is required when domain_enabled is true", e.getMessage());
    }

    @Test
    public void inbound_domain_not_required_when_domain_enabled_is_false() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .matchFilter("match_all")
                .addRoute();
        } catch (MailerSendException e) {
            fail();
        }
    }

    @Test
    public void inbound_domain_supplied_with_domain_enabled_true_passes_validation() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(true)
                .inboundDomain("inbound.example.com")
                .matchFilter("match_all")
                .addRoute();
        } catch (MailerSendException e) {
            if (e.getMessage() != null && e.getMessage().equals("inbound_domain is required when domain_enabled is true")) {
                fail("Should not throw inbound_domain validation error when domain is supplied");
            }
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidPriorityValues")
    public void inbound_priority_range_validation(String label, int priority, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .inboundPriority(priority)
                .matchFilter("match_all")
                .addRoute()
        );

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidPriorityValues() {
        return Stream.of(
            Arguments.of("inbound_priority_must_not_be_negative", -1, "inbound_priority must be between 0 and 100"),
            Arguments.of("inbound_priority_must_not_exceed_100", 101, "inbound_priority must be between 0 and 100")
        );
    }

    @Test
    public void inbound_priority_boundary_values_are_valid() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        // 0 and 100 are boundary values — validation must not fire for them
        for (int boundary : new int[]{0, 100}) {
            final int p = boundary;
            try {
                ms.inboundRoutes().builder()
                    .domainId(TestHelper.domainId)
                    .domainEnabled(false)
                    .inboundPriority(p)
                    .matchFilter("match_all")
                    .addRoute();
            } catch (MailerSendException e) {
                if (e.getMessage() != null && e.getMessage().equals("inbound_priority must be between 0 and 100")) {
                    fail("Boundary value " + p + " should not fail priority validation");
                }
            }
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCatchFilterTypes")
    public void catch_filter_type_enum_validation(String label, String type, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CatchFilter cf = new CatchFilter();
        cf.type = type;

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchFilter(cf)
                .matchFilter("match_all")
                .addRoute()
        );

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidCatchFilterTypes() {
        return Stream.of(
            Arguments.of("catch_filter_type_invalid_value", "invalid_type", "catch_filter.type must be one of: catch_all, catch_recipient"),
            Arguments.of("catch_filter_type_null", null, "catch_filter.type must be one of: catch_all, catch_recipient"),
            Arguments.of("catch_filter_type_empty_string", "", "catch_filter.type must be one of: catch_all, catch_recipient")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validCatchFilterTypes")
    public void catch_filter_valid_type_does_not_throw(String label, String type) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CatchFilter cf = new CatchFilter();
        cf.type = type;

        try {
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchFilter(cf)
                .matchFilter("match_all")
                .addRoute();
        } catch (MailerSendException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("catch_filter.type must be")) {
                fail("Valid catch_filter.type '" + type + "' should not fail enum validation");
            }
        }
    }

    static Stream<Arguments> validCatchFilterTypes() {
        return Stream.of(
            Arguments.of("catch_all_is_valid", "catch_all"),
            Arguments.of("catch_recipient_is_valid", "catch_recipient")
        );
    }

    @Test
    public void catch_filter_filters_must_not_exceed_5_items() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        Filter[] filters = new Filter[6];
        for (int i = 0; i < 6; i++) {
            filters[i] = new Filter();
            filters[i].comparer = "equal";
            filters[i].value = "value" + i;
        }

        CatchFilter cf = new CatchFilter("catch_recipient", filters);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchFilter(cf)
                .matchFilter("match_all")
                .addRoute()
        );

        assertEquals("catch_filter.filters must not exceed 5 items", e.getMessage());
    }

    @Test
    public void catch_filter_5_items_is_at_the_limit_and_valid() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        Filter[] filters = new Filter[5];
        for (int i = 0; i < 5; i++) {
            filters[i] = new Filter();
            filters[i].comparer = "equal";
            filters[i].value = "value" + i;
        }

        CatchFilter cf = new CatchFilter("catch_recipient", filters);

        try {
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchFilter(cf)
                .matchFilter("match_all")
                .addRoute();
        } catch (MailerSendException e) {
            if (e.getMessage() != null && e.getMessage().equals("catch_filter.filters must not exceed 5 items")) {
                fail("5 filters should not fail the max-array-length validation");
            }
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCatchFilterComparers")
    public void catch_filter_comparer_enum_validation(String label, String comparer, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        Filter filter = new Filter();
        filter.comparer = comparer;
        filter.value = "test";

        CatchFilter cf = new CatchFilter("catch_recipient", new Filter[]{filter});

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchFilter(cf)
                .matchFilter("match_all")
                .addRoute()
        );

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidCatchFilterComparers() {
        return Stream.of(
            Arguments.of("catch_filter_comparer_invalid_value", "like", "catch_filter.filters.comparer must be one of: equal, not-equal, contains, not-contains, starts-with, ends-with, not-starts-with, not-ends-with"),
            Arguments.of("catch_filter_comparer_arbitrary_string", "EQUAL", "catch_filter.filters.comparer must be one of: equal, not-equal, contains, not-contains, starts-with, ends-with, not-starts-with, not-ends-with")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validComparers")
    public void catch_filter_valid_comparers_do_not_throw(String label, String comparer) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        Filter filter = new Filter();
        filter.comparer = comparer;
        filter.value = "test";

        CatchFilter cf = new CatchFilter("catch_recipient", new Filter[]{filter});

        try {
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchFilter(cf)
                .matchFilter("match_all")
                .addRoute();
        } catch (MailerSendException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("catch_filter.filters.comparer must be")) {
                fail("Valid comparer '" + comparer + "' should not fail enum validation");
            }
        }
    }

    static Stream<Arguments> validComparers() {
        return Stream.of(
            Arguments.of("comparer_equal", "equal"),
            Arguments.of("comparer_not_equal", "not-equal"),
            Arguments.of("comparer_contains", "contains"),
            Arguments.of("comparer_not_contains", "not-contains"),
            Arguments.of("comparer_starts_with", "starts-with"),
            Arguments.of("comparer_ends_with", "ends-with"),
            Arguments.of("comparer_not_starts_with", "not-starts-with"),
            Arguments.of("comparer_not_ends_with", "not-ends-with")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCatchTypes")
    public void catch_type_enum_validation(String label, String catchType, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchType(catchType)
                .matchFilter("match_all")
                .addRoute()
        );

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidCatchTypes() {
        return Stream.of(
            Arguments.of("catch_type_invalid_value", "both", "catch_type must be one of: all, one"),
            Arguments.of("catch_type_arbitrary_string", "ANY", "catch_type must be one of: all, one")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validCatchTypes")
    public void catch_type_valid_values_do_not_throw(String label, String catchType) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchType(catchType)
                .matchFilter("match_all")
                .addRoute();
        } catch (MailerSendException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("catch_type must be")) {
                fail("Valid catch_type '" + catchType + "' should not fail enum validation");
            }
        }
    }

    static Stream<Arguments> validCatchTypes() {
        return Stream.of(
            Arguments.of("catch_type_all", "all"),
            Arguments.of("catch_type_one", "one")
        );
    }

    @Test
    public void match_filter_key_required_when_type_is_match_header() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        InboundFilter filter = new InboundFilter("equal", "X-Custom-Header-Value");
        // key intentionally not set

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .matchFilter("match_header", new InboundFilter[]{filter})
                .addRoute()
        );

        assertEquals("match_filter.filters.key is required when match_filter.type is match_header", e.getMessage());
    }

    @Test
    public void match_filter_key_not_required_when_type_is_not_match_header() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        InboundFilter filter = new InboundFilter("equal", "sender@example.com");
        // key not set — should be fine for match_sender

        try {
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .matchFilter("match_sender", new InboundFilter[]{filter})
                .addRoute();
        } catch (MailerSendException e) {
            if (e.getMessage() != null && e.getMessage().equals("match_filter.filters.key is required when match_filter.type is match_header")) {
                fail("key should not be required for match_sender type");
            }
        }
    }

    @Test
    public void match_filter_key_supplied_with_match_header_passes_validation() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        InboundFilter filter = new InboundFilter("equal", "X-Custom-Header-Value", "X-Custom-Header");

        try {
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .matchFilter("match_header", new InboundFilter[]{filter})
                .addRoute();
        } catch (MailerSendException e) {
            if (e.getMessage() != null && e.getMessage().equals("match_filter.filters.key is required when match_filter.type is match_header")) {
                fail("Should not throw key validation error when key is supplied");
            }
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidMatchFilterComparers")
    public void match_filter_comparer_enum_validation(String label, String comparer, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        InboundFilter filter = new InboundFilter(comparer, "value", "X-Header");

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .matchFilter("match_header", new InboundFilter[]{filter})
                .addRoute()
        );

        assertEquals(expectedMessage, e.getMessage());
    }

    static Stream<Arguments> invalidMatchFilterComparers() {
        return Stream.of(
            Arguments.of("match_filter_comparer_invalid_value", "like", "match_filter.filters.comparer must be one of: equal, not-equal, contains, not-contains, starts-with, ends-with, not-starts-with, not-ends-with"),
            Arguments.of("match_filter_comparer_arbitrary_string", "CONTAINS", "match_filter.filters.comparer must be one of: equal, not-equal, contains, not-contains, starts-with, ends-with, not-starts-with, not-ends-with")
        );
    }

    @Test
    public void update_route_also_validates_inbound_domain_when_domain_enabled_is_true() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(true)
                .matchFilter("match_all")
                .updateRoute(TestHelper.inboundRouteId)
        );

        assertEquals("inbound_domain is required when domain_enabled is true", e.getMessage());
    }

    @Test
    public void update_route_also_validates_inbound_priority_range() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .inboundPriority(200)
                .matchFilter("match_all")
                .updateRoute(TestHelper.inboundRouteId)
        );

        assertEquals("inbound_priority must be between 0 and 100", e.getMessage());
    }

    @Test
    public void update_route_also_validates_catch_type_enum() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () ->
            ms.inboundRoutes().builder()
                .domainId(TestHelper.domainId)
                .domainEnabled(false)
                .catchType("invalid")
                .matchFilter("match_all")
                .updateRoute(TestHelper.inboundRouteId)
        );

        assertEquals("catch_type must be one of: all, one", e.getMessage());
    }


    /**
     * Tests that addRoute() posts every documented field to the inbound endpoint, without the
     * inbound address, the inbound subdomain and the forward secret and id that the API does not
     * accept, and parses the created route
     */
    @Test
    @SuppressWarnings("deprecation")
    public void addRouteSendsEveryDocumentedField() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(201, ROUTE_RESPONSE);

        Forward forward = new Forward();
        forward.id = "forward-id";
        forward.type = "webhook";
        forward.value = "https://yourapp.com/hook";
        forward.secret = "asdfgh";

        Filter catchFilter = new Filter();
        catchFilter.comparer = "equal";
        catchFilter.value = "test";

        InboundRoute route = ms.inboundRoutes().builder()
            .domainId(TestHelper.domainId)
            .name("Test name")
            .domainEnabled(true)
            .inboundDomain("inbound.test-sdk.com")
            .inboundAddress("ignored@inbound.test-sdk.com")
            .inboundSubdomain("ignored")
            .inboundPriority(10)
            .catchFilter(new CatchFilter("catch_recipient", new Filter[] { catchFilter }))
            .catchType("one")
            .matchFilter("match_header", new InboundFilter[] { new InboundFilter("equal", "yes", "X-Test") })
            .matchType("one")
            .forwards(new Forward[] { forward })
            .includeAttachments(false)
            .addRoute();

        assertEquals("POST", client.lastRequest().method());
        assertEquals(INBOUND_URL, client.lastUrl());
        assertEquals(json("{"
                + "'domain_id':'" + TestHelper.domainId + "',"
                + "'name':'Test name',"
                + "'domain_enabled':true,"
                + "'inbound_domain':'inbound.test-sdk.com',"
                + "'inbound_priority':10,"
                + "'catch_filter':{'type':'catch_recipient','filters':[{'comparer':'equal','value':'test'}]},"
                + "'catch_type':'one',"
                + "'match_filter':{'type':'match_header','filters':[{'comparer':'equal','value':'yes','key':'X-Test'}]},"
                + "'match_type':'one',"
                + "'forwards':[{'type':'webhook','value':'https://yourapp.com/hook'}],"
                + "'include_attachments':false"
                + "}"), client.lastBody());
        assertEquals("v57gn2j67qlr6z4d", route.id);
        assertEquals(Date.from(Instant.parse("2026-01-15T10:20:30Z")), route.dnsCheckedAt);
    }


    /**
     * Tests that updateRoute() puts the documented fields to the route's endpoint, without the
     * domain id that the update endpoint does not accept, and parses the updated route
     */
    @Test
    public void updateRouteSendsTheDocumentedFields() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, ROUTE_RESPONSE);

        Forward forward = new Forward();
        forward.type = "email";
        forward.value = "forward@test-sdk.com";

        InboundRoute route = ms.inboundRoutes().builder()
            .domainId(TestHelper.domainId)
            .name("Test name")
            .domainEnabled(true)
            .inboundDomain("inbound.test-sdk.com")
            .inboundPriority(10)
            .catchFilter(new CatchFilter("catch_all", null))
            .catchType("all")
            .matchFilter("match_sender", new InboundFilter[] { new InboundFilter("ends-with", "@test-sdk.com") })
            .matchType("all")
            .forwards(new Forward[] { forward })
            .includeAttachments(true)
            .updateRoute("v57gn2j67qlr6z4d");

        assertEquals("PUT", client.lastRequest().method());
        assertEquals(INBOUND_URL + "/v57gn2j67qlr6z4d", client.lastUrl());
        assertEquals(json("{"
                + "'name':'Test name',"
                + "'domain_enabled':true,"
                + "'inbound_domain':'inbound.test-sdk.com',"
                + "'inbound_priority':10,"
                + "'catch_filter':{'type':'catch_all'},"
                + "'catch_type':'all',"
                + "'match_filter':{'type':'match_sender','filters':[{'comparer':'ends-with','value':'@test-sdk.com'}]},"
                + "'match_type':'all',"
                + "'forwards':[{'type':'email','value':'forward@test-sdk.com'}],"
                + "'include_attachments':true"
                + "}"), client.lastBody());
        assertEquals(Date.from(Instant.parse("2026-01-15T10:20:30Z")), route.dnsCheckedAt);
    }


    /**
     * Tests that getRoute() requests the route's endpoint and parses the catch type, the match type,
     * the dns check date and the forward secret
     */
    @Test
    public void getRouteParsesTheRoute() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, ROUTE_RESPONSE);

        InboundRoute route = ms.inboundRoutes().getRoute("v57gn2j67qlr6z4d");

        assertEquals("GET", client.lastRequest().method());
        assertEquals(INBOUND_URL + "/v57gn2j67qlr6z4d", client.lastUrl());
        assertEquals("one", route.catchType);
        assertEquals("one", route.matchType);
        assertFalse(route.includeAttachments);
        assertEquals("jYhafQeTiZgw0qWclkUA7cbqTG3Zfh2j", route.forwards[0].secret);
        assertEquals(Date.from(Instant.parse("2026-01-15T10:20:30Z")), route.dnsCheckedAt);
    }
}
