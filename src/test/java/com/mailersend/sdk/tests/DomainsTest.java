package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.domains.DomainDnsRecords;
import com.mailersend.sdk.domains.DomainVerificationStatus;
import com.mailersend.sdk.domains.Domains;
import com.mailersend.sdk.domains.DomainsList;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.ApiRecipientsList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class DomainsTest {

    private static final String API_URL = "https://api.mailersend.com/v1";

    private static final String DOMAINS_LIST_RESPONSE = "{\"data\":[],\"links\":{},\"meta\":{\"current_page\":1}}";

    private static final String SINGLE_DOMAIN_RESPONSE = "{\"data\":{\"id\":\"jpzkmgq7e5vl059v\",\"name\":\"test-sdk.com\"}}";

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("DomainsTest_" + info.getDisplayName());
	}

	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}

    /**
     * Tests domains retrieval
     */
    @Test
    public void domainsListTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            DomainsList list = ms.domains().getDomains();

            assertEquals(200, list.responseStatusCode);
            assertEquals(1, list.domains.length);
            assertEquals(TestHelper.domainId, list.domains[0].id);
            assertEquals("test-sdk.com", list.domains[0].name);
            assertEquals(Date.from(Instant.parse("2022-04-20T11:36:15Z")), list.domains[0].createdAt);
            assertEquals(Date.from(Instant.parse("2022-06-15T11:32:41Z")), list.domains[0].updatedAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests that page and limit are sent in the domains list query
     */
    @Test
    public void testGetDomainsWithPageAndLimit() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(DOMAINS_LIST_RESPONSE, 200);

        ms.domains().page(2).limit(10).getDomains();

        assertEquals("GET", client.lastMethod());
        assertEquals(API_URL + "/domains?page=2&limit=10", client.lastUri());
    }


    /**
     * Tests that the verified filter is sent as 1 or 0, the only forms of a boolean query value the API accepts
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("verifiedFilters")
    public void testGetDomainsWithVerifiedFilter(String label, Function<Domains, Domains> filter, String expectedQuery) throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(DOMAINS_LIST_RESPONSE, 200);

        filter.apply(ms.domains()).getDomains();

        assertEquals(API_URL + "/domains?page=1&limit=25" + expectedQuery, client.lastUri());
    }


    static Stream<Arguments> verifiedFilters() {

        return Stream.of(
            Arguments.of("only verified", (Function<Domains, Domains>) Domains::onlyVerified, "&verified=1"),
            Arguments.of("only unverified", (Function<Domains, Domains>) Domains::onlyUnverified, "&verified=0")
        );
    }


    /**
     * Tests that all() removes a previously set verified filter from the query
     */
    @Test
    public void testGetDomainsAllRemovesVerifiedFilter() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(DOMAINS_LIST_RESPONSE, 200);

        ms.domains().onlyVerified().all().getDomains();

        assertEquals(API_URL + "/domains?page=1&limit=25", client.lastUri());
    }


    /**
     * Tests the retrieval of a single domain
     */
    @Test
    public void singleDomainTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Domain domain = ms.domains().getDomain(TestHelper.domainId);

            assertEquals(TestHelper.domainId, domain.id);
            assertEquals("test-sdk.com", domain.name);
            assertEquals(Date.from(Instant.parse("2022-04-20T11:36:15Z")), domain.createdAt);
            assertEquals(Date.from(Instant.parse("2022-06-15T11:32:41Z")), domain.updatedAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests the retrival of recipients per domain
     */
    @Test
    public void receipientsPerDomainTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ApiRecipientsList list = ms.domains().getDomainRecipients(TestHelper.domainId);

            assertEquals(200, list.responseStatusCode);
            assertEquals(1, list.recipients.length);
            assertEquals("test@example.com", list.recipients[0].email);
            assertEquals(Date.from(Instant.parse("2022-06-07T17:48:05.474Z")), list.recipients[0].createdAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests that page and limit are sent in the domain recipients query
     */
    @Test
    public void testGetDomainRecipientsWithPageAndLimit() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(DOMAINS_LIST_RESPONSE, 200);

        ms.domains().page(2).limit(10).getDomainRecipients(TestHelper.domainId);

        assertEquals("GET", client.lastMethod());
        assertEquals(API_URL + "/domains/" + TestHelper.domainId + "/recipients?page=2&limit=10", client.lastUri());
    }


    /**
     * Tests that the domains list verified filter is not sent to the domain recipients endpoint, which does not accept it
     */
    @Test
    public void testGetDomainRecipientsOmitsVerifiedFilter() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(DOMAINS_LIST_RESPONSE, 200);

        ms.domains().onlyVerified().getDomainRecipients(TestHelper.domainId);

        assertEquals(API_URL + "/domains/" + TestHelper.domainId + "/recipients?page=1&limit=25", client.lastUri());
    }

    /**
     * Tests adding a domain
     */
    @Test
    public void addDomainTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Domain domain = ms.domains().addDomainBuilder().addDomain(TestHelper.domainToAdd);

            assertEquals("jy7zpl95rd045vx6", domain.id);
            assertEquals(TestHelper.domainToAdd, domain.name);
            assertEquals(Date.from(Instant.parse("2022-09-14T10:06:48Z")), domain.createdAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests that the optional subdomains are sent in the add domain body
     */
    @Test
    public void testAddDomainWithSubdomains() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(SINGLE_DOMAIN_RESPONSE, 201);

        ms.domains().addDomainBuilder()
            .returnPathSubdomain("rp")
            .customTrackingSubdomain("ct")
            .inboundRoutingSubdomain("ir")
            .addDomain(TestHelper.domainToAdd);

        assertEquals("POST", client.lastMethod());
        assertEquals(API_URL + "/domains", client.lastUri());
        assertEquals("{\"name\":\"domain-to-add.com\",\"return_path_subdomain\":\"rp\",\"custom_tracking_subdomain\":\"ct\",\"inbound_routing_subdomain\":\"ir\"}",
            client.lastBody());
    }


    /**
     * Tests that the add domain builder does not carry the subdomains of a previous request over to the next one
     */
    @Test
    public void testAddDomainResetsBodyAfterSend() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(SINGLE_DOMAIN_RESPONSE, 201);

        ms.domains().addDomainBuilder().returnPathSubdomain("rp").addDomain(TestHelper.domainToAdd);
        ms.domains().addDomainBuilder().addDomain("second-domain.com");

        assertEquals("{\"name\":\"second-domain.com\"}", client.lastBody());
    }


    /**
     * Tests deleting a domain
     */
    @Test
    public void deleteDomainTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean domainDeleted = ms.domains().deleteDomain(TestHelper.domainIdToDelete);

            assertTrue(domainDeleted);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests the domain dns records retrieval
     */
    @Test
    public void domainDnsRecordsTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

           DomainDnsRecords records = ms.domains().getDomainDnsRecords(TestHelper.domainId);

           assertEquals(TestHelper.domainId, records.id);
           assertEquals("test-sdk.com", records.spf.hostname);
           assertEquals("TXT", records.spf.type);
           assertEquals("ms1._domainkey.test-sdk.com", records.dkimMs1.hostname);
           assertNotNull(records.dkimMs2);
           assertNotNull(records.returnPath);
           assertNotNull(records.customTracking);
           assertNotNull(records.inboundRouting);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests domain verification
     */
    @Test
    public void verifyDomainTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

           DomainVerificationStatus status = ms.domains().verifyDomain(TestHelper.domainId);

           assertEquals(200, status.responseStatusCode);
           assertEquals("The domain is verified.", status.message);
           assertNotNull(status.status);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests updating a domain's settings
     */
    @Test
    public void updateDomainSettingsTest() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

           Domain domain = ms.domains().updateDomainSettingsBuilder()
                .customTrackingEnabled(true)
                .sendPaused(false)
                .updateDomain(TestHelper.domainId);

           assertEquals(TestHelper.domainId, domain.id);
           assertNotNull(domain.domainSettings);
           assertEquals(Date.from(Instant.parse("2022-04-20T11:36:15Z")), domain.createdAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests updating a domain's settings with all available fields set
     */
    @Test
    public void testUpdateDomainSettingsAllFields() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Domain domain = ms.domains().updateDomainSettingsBuilder()
                .sendPaused(true)
                .trackClicks(true)
                .trackOpens(true)
                .trackUnsubscribe(true)
                .trackUnsubscribeHtml("<p>Click here to <a href=\"{{unsubscribe}}\">unsubscribe</a></p>")
                .trackUnsubscribeHtmlEnabled(true)
                .trackUnsubscribePlain("Click here to unsubscribe: {{unsubscribe}}")
                .trackUnsubscribePlainEnabled(true)
                .trackContent(true)
                .customTrackingEnabled(true)
                .customTrackingSubdomain("email")
                .precedenceBulk(false)
                .ignoreDuplicatedRecipients(true)
                .updateDomain(TestHelper.domainId);

            assertNotNull(domain.domainSettings);
            assertEquals(true, domain.domainSettings.sendPaused);
            assertEquals(true, domain.domainSettings.trackClicks);
            assertEquals(true, domain.domainSettings.customTrackingEnabled);
            assertEquals(true, domain.domainSettings.ignoreDuplicatedRecipients);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests that the deprecated customnTrackingEnabled setter still sets custom_tracking_enabled
     */
    @Test
    public void testDeprecatedCustomnTrackingEnabledSetsCustomTrackingEnabled() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(SINGLE_DOMAIN_RESPONSE, 200);

        ms.domains().updateDomainSettingsBuilder()
            .customnTrackingEnabled(true)
            .updateDomain(TestHelper.domainId);

        assertEquals("PUT", client.lastMethod());
        assertEquals(API_URL + "/domains/" + TestHelper.domainId + "/settings", client.lastUri());
        assertEquals("{\"custom_tracking_enabled\":true}", client.lastBody());
    }


    /**
     * Tests that the settings builder does not carry the settings of a previous request over to the next one
     */
    @Test
    public void testUpdateDomainSettingsResetsBodyAfterSend() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(SINGLE_DOMAIN_RESPONSE, 200);

        ms.domains().updateDomainSettingsBuilder().sendPaused(true).updateDomain(TestHelper.domainId);
        ms.domains().updateDomainSettingsBuilder().trackClicks(false).updateDomain(TestHelper.domainId);

        assertEquals("{\"track_clicks\":false}", client.lastBody());
    }
}
