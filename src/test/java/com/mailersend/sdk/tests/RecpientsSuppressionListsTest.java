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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.recipients.BlocklistItem;
import com.mailersend.sdk.recipients.BlocklistListResponse;
import com.mailersend.sdk.recipients.SuppressionAddBuilder;
import com.mailersend.sdk.recipients.SuppressionItem;
import com.mailersend.sdk.recipients.SuppressionList;
import com.mailersend.sdk.recipients.Suppressions;
import com.mailersend.sdk.util.ApiRecipientsList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class RecpientsSuppressionListsTest {

    private static final String SUPPRESSIONS_URL = "https://api.mailersend.com/v1/suppressions";

    private static final String EMPTY_LIST_RESPONSE = "{\"data\":[],\"links\":{},\"meta\":{\"current_page\":1}}";


	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("RecpientsSuppressionListsTest_" + info.getDisplayName());
	}

	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}

    /**
     * Tests retrieving items from the suppression lists
     */
    @Test
    public void testGetFromSuppressionLists() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            BlocklistListResponse blocklist = ms.recipients().suppressions().getBlocklist();

            assertEquals(200, blocklist.responseStatusCode);
            assertEquals(".*@example.com", blocklist.items[0].pattern);
            assertEquals(Date.from(Instant.parse("2022-08-19T13:23:19.349Z")), blocklist.items[0].createdAt);

            SuppressionList hardBounces = ms.recipients().suppressions().getHardBounces();

            assertEquals("test@example.com", hardBounces.items[0].recipient.email);
            assertEquals(Date.from(Instant.parse("2022-06-07T17:48:05.474Z")), hardBounces.items[0].recipient.createdAt);

            SuppressionList spamComplaints = ms.recipients().suppressions().getSpamComplaints();

            assertEquals("629f8f551701d31cba0cf02f", spamComplaints.items[0].id);
            assertEquals("test@example.com", spamComplaints.items[0].recipient.email);

            SuppressionList unsubscribes = ms.recipients().suppressions().getUnsubscribes();

            assertEquals("629f8f56dd289029750bcc45", unsubscribes.items[0].id);
            assertEquals("test@example.com", unsubscribes.items[0].recipient.email);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests that page, limit and domain_id are sent in the query of every suppression list
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("suppressionLists")
    public void testGetSuppressionListWithPageLimitAndDomainId(String label, SuppressionCall call, String path) throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(EMPTY_LIST_RESPONSE, 200);

        call.request(ms.recipients().suppressions().page(2).limit(25).domainId(TestHelper.domainId));

        assertEquals("GET", client.lastMethod());
        assertEquals(SUPPRESSIONS_URL + path + "?page=2&limit=25&domain_id=" + TestHelper.domainId, client.lastUri());
    }


    static Stream<Arguments> suppressionLists() {

        return Stream.of(
            Arguments.of("blocklist", (SuppressionCall) Suppressions::getBlocklist, "/blocklist"),
            Arguments.of("hard bounces", (SuppressionCall) Suppressions::getHardBounces, "/hard-bounces"),
            Arguments.of("spam complaints", (SuppressionCall) Suppressions::getSpamComplaints, "/spam-complaints"),
            Arguments.of("unsubscribes", (SuppressionCall) Suppressions::getUnsubscribes, "/unsubscribes"),
            Arguments.of("on-hold list", (SuppressionCall) Suppressions::getOnHoldList, "/on-hold-list")
        );
    }


    /**
     * Tests that the on-hold list items carry the on hold until date and the email address
     */
    @Test
    public void testOnHoldListReadsOnHoldFields() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient.install("{\"data\":[{\"id\":\"64c760a33e2db0f6340f4710\",\"created_at\":\"2023-07-15T19:22:37.579000Z\","
            + "\"on_hold_until\":\"2023-07-18T19:22:37.579000Z\",\"email\":\"shanna.fadel@example.net\","
            + "\"recipient\":{\"id\":\"64c760a33e2db0f6340f470f\",\"email\":\"shanna.fadel@example.net\",\"created_at\":\"2023-07-15T19:22:37.579000Z\","
            + "\"updated_at\":\"2023-07-15T19:22:37.579000Z\",\"deleted_at\":\"\"}}],\"links\":{},\"meta\":{\"current_page\":1}}", 200);

        SuppressionList onHoldList = ms.recipients().suppressions().getOnHoldList();

        SuppressionItem item = onHoldList.items[0];

        assertEquals("2023-07-18T19:22:37.579000Z", item.onHoldUntil);
        assertEquals("shanna.fadel@example.net", item.email);
        assertEquals(Date.from(Instant.parse("2023-07-15T19:22:37.579Z")), item.recipient.createdAt);
        assertNull(item.readableReason);
    }


    /**
     * Tests adding items to the suppression lists
     */
    @Test
    public void testAddToSuppressionList() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            // blocklist
        	ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            ms.recipients().suppressions().addBuilder().pattern(".*@example.com");
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            BlocklistItem[] items = ms.recipients().suppressions().addBuilder().addToBlocklist();

            assertEquals(2, items.length);
            assertEquals(Date.from(Instant.parse("2022-09-14T10:06:48.514Z")), items[0].createdAt);


            // hard bounces
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            SuppressionList list = ms.recipients().suppressions().addBuilder().addRecipientsToHardBounces();

            assertEquals(201, list.responseStatusCode);
            assertEquals("test@example.com", list.items[0].recipient.email);


            // spam complaints
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            list = ms.recipients().suppressions().addBuilder().addRecipientsToSpamComplaints();

            assertEquals(201, list.responseStatusCode);
            assertEquals("test@example.com", list.items[0].recipient.email);


            // unsubscribes
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            list = ms.recipients().suppressions().addBuilder().addRecipientsToUnsubscribes();

            assertEquals(201, list.responseStatusCode);
            assertEquals("test@example.com", list.items[0].recipient.email);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests deleting items from the suppression lists
     * @throws MailerSendException
     */
    @Test
    public void testDeleteFromSuppressionList() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
        ms.recipients().suppressions().addBuilder().pattern(".*@example.com");
        ms.recipients().suppressions().addBuilder().addToBlocklist();

        try {

            // delete from blocklist
            BlocklistListResponse blocklist = ms.recipients().suppressions().domainId(TestHelper.domainId).getBlocklist();

            if (blocklist.items.length == 0) {

                fail();
            }

            String itemId = blocklist.items[0].id;

            MailerSendResponse response = ms.recipients().suppressions().domainId(TestHelper.domainId).deleteBlocklistItems(new String[] { itemId });

            assertEquals(200, response.responseStatusCode);


            // delete from spam complaints
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().addRecipientsToSpamComplaints();

            SuppressionList spamComplaints = ms.recipients().suppressions().domainId(TestHelper.domainId).getSpamComplaints();

            if (spamComplaints.items.length == 0) {

                fail();
            }

            itemId = spamComplaints.items[0].id;

            response = ms.recipients().suppressions().domainId(TestHelper.domainId).deleteSpamComplaintsItems(new String[] { itemId });

            assertEquals(200, response.responseStatusCode);


        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Behavior: GET /v1/recipients — query-string parameters
    // -------------------------------------------------------------------------

    /**
     * [Behavior] page, limit, and domain_id are serialised into the recipients query string.
     */
    @Test
    public void testGetRecipientsWithPageLimitAndDomainIdParams() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ApiRecipientsList list = ms.recipients()
                    .page(2)
                    .limit(10)
                    .domainId(TestHelper.domainId)
                    .getRecipients();

            assertEquals(200, list.responseStatusCode);
            assertEquals(1, list.recipients.length);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Validation: GET /v1/recipients — limit range
    // -------------------------------------------------------------------------

    /**
     * [Validation] limit() throws when the value is outside the 10 - 100 range.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("limitsOutOfRange")
    public void testRecipientsLimitOutOfRangeThrowsValidationException(String label, int limit) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.recipients().limit(limit);
        });

        assertEquals("Limit must be between 10 and 100", e.getMessage());
    }


    static Stream<Arguments> limitsOutOfRange() {

        return Stream.of(
            Arguments.of("limit below minimum", 9),
            Arguments.of("limit above maximum", 101)
        );
    }


    // -------------------------------------------------------------------------
    // Validation: POST suppression lists — recipients and domain id required
    // -------------------------------------------------------------------------

    /**
     * [Validation] adding recipients to a suppression list throws when the recipients or the domain id are missing.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidSuppressionAdds")
    public void testAddRecipientsToSuppressionListRejectsMissingParams(String label, String recipient, String domainId, SuppressionAdd add, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        SuppressionAddBuilder builder = ms.recipients().suppressions().addBuilder();

        if (recipient != null) {

            builder.recipient(recipient);
        }

        if (domainId != null) {

            builder.domainId(domainId);
        }

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            add.request(builder);
        });

        assertEquals(expectedMessage, e.getMessage());
    }


    static Stream<Arguments> invalidSuppressionAdds() {

        SuppressionAdd hardBounces = SuppressionAddBuilder::addRecipientsToHardBounces;
        SuppressionAdd spamComplaints = SuppressionAddBuilder::addRecipientsToSpamComplaints;
        SuppressionAdd unsubscribes = SuppressionAddBuilder::addRecipientsToUnsubscribes;

        return Stream.of(
            Arguments.of("hard bounces without recipients", null, TestHelper.domainId, hardBounces, "No recipients specified for suppression list"),
            Arguments.of("spam complaints without recipients", null, TestHelper.domainId, spamComplaints, "No recipients specified for suppression list"),
            Arguments.of("unsubscribes without recipients", null, TestHelper.domainId, unsubscribes, "No recipients specified for suppression list"),
            Arguments.of("hard bounces without domain id", "test@example.com", null, hardBounces, "No domain id specified"),
            Arguments.of("spam complaints without domain id", "test@example.com", null, spamComplaints, "No domain id specified"),
            Arguments.of("unsubscribes without domain id", "test@example.com", null, unsubscribes, "No domain id specified")
        );
    }


    /**
     * [Validation] addToBlocklist() throws when neither recipients nor patterns have been added.
     */
    @Test
    public void testAddToBlocklistWithoutRecipientsOrPatternsThrowsException() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            ms.recipients().suppressions().addBuilder().addToBlocklist();
        });

        assertEquals("No patterns or recipients specified for blocklist", e.getMessage());
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE suppression lists — request bodies
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleting items by id sends the ids, and the domain id only when one is set.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("deleteItemsRequests")
    public void testDeleteItemsBody(String label, String domainId, SuppressionDelete delete, String path, String expectedBody) throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("[]", 200);

        Suppressions suppressions = ms.recipients().suppressions();

        if (domainId != null) {

            suppressions.domainId(domainId);
        }

        MailerSendResponse response = delete.request(suppressions);

        assertEquals("DELETE", client.lastMethod());
        assertEquals(SUPPRESSIONS_URL + path, client.lastUri());
        assertEquals(expectedBody, client.lastBody());
        assertEquals(200, response.responseStatusCode);
    }


    static Stream<Arguments> deleteItemsRequests() {

        String[] ids = new String[] { "629f8f56dd289029750bcc45" };
        String withoutDomain = "{\"ids\":[\"629f8f56dd289029750bcc45\"]}";
        String withDomain = "{\"ids\":[\"629f8f56dd289029750bcc45\"],\"domain_id\":\"" + TestHelper.domainId + "\"}";

        return Stream.of(
            Arguments.of("blocklist", null, (SuppressionDelete) s -> s.deleteBlocklistItems(ids), "/blocklist", withoutDomain),
            Arguments.of("blocklist with domain id", TestHelper.domainId, (SuppressionDelete) s -> s.deleteBlocklistItems(ids), "/blocklist", withDomain),
            Arguments.of("hard bounces", null, (SuppressionDelete) s -> s.deleteHardBouncesItems(ids), "/hard-bounces", withoutDomain),
            Arguments.of("hard bounces with domain id", TestHelper.domainId, (SuppressionDelete) s -> s.deleteHardBouncesItems(ids), "/hard-bounces", withDomain),
            Arguments.of("spam complaints", null, (SuppressionDelete) s -> s.deleteSpamComplaintsItems(ids), "/spam-complaints", withoutDomain),
            Arguments.of("spam complaints with domain id", TestHelper.domainId, (SuppressionDelete) s -> s.deleteSpamComplaintsItems(ids), "/spam-complaints", withDomain),
            Arguments.of("unsubscribes", null, (SuppressionDelete) s -> s.deleteUnsubscribesItems(ids), "/unsubscribes", withoutDomain),
            Arguments.of("unsubscribes with domain id", TestHelper.domainId, (SuppressionDelete) s -> s.deleteUnsubscribesItems(ids), "/unsubscribes", withDomain),
            Arguments.of("on-hold list", null, (SuppressionDelete) s -> s.deleteOnHoldListItems(ids), "/on-hold-list", withoutDomain),
            Arguments.of("on-hold list with domain id", TestHelper.domainId, (SuppressionDelete) s -> s.deleteOnHoldListItems(ids), "/on-hold-list", withDomain)
        );
    }


    /**
     * [Behavior] deleting all items sends all, and the domain id only when one is set.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("deleteAllRequests")
    public void testDeleteAllItemsBody(String label, String domainId, SuppressionDelete delete, String path, String expectedBody) throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("[]", 200);

        Suppressions suppressions = ms.recipients().suppressions();

        if (domainId != null) {

            suppressions.domainId(domainId);
        }

        MailerSendResponse response = delete.request(suppressions);

        assertEquals("DELETE", client.lastMethod());
        assertEquals(SUPPRESSIONS_URL + path, client.lastUri());
        assertEquals(expectedBody, client.lastBody());
        assertEquals(200, response.responseStatusCode);
    }


    static Stream<Arguments> deleteAllRequests() {

        String withoutDomain = "{\"all\":true}";
        String withDomain = "{\"all\":true,\"domain_id\":\"" + TestHelper.domainId + "\"}";

        return Stream.of(
            Arguments.of("blocklist", null, (SuppressionDelete) Suppressions::deleteBlocklistAllItems, "/blocklist", withoutDomain),
            Arguments.of("blocklist with domain id", TestHelper.domainId, (SuppressionDelete) Suppressions::deleteBlocklistAllItems, "/blocklist", withDomain),
            Arguments.of("hard bounces", null, (SuppressionDelete) Suppressions::deleteHardBouncesAllItems, "/hard-bounces", withoutDomain),
            Arguments.of("hard bounces with domain id", TestHelper.domainId, (SuppressionDelete) Suppressions::deleteHardBouncesAllItems, "/hard-bounces", withDomain),
            Arguments.of("spam complaints", null, (SuppressionDelete) Suppressions::deleteSpamComplaintsAllItems, "/spam-complaints", withoutDomain),
            Arguments.of("spam complaints with domain id", TestHelper.domainId, (SuppressionDelete) Suppressions::deleteSpamComplaintsAllItems, "/spam-complaints", withDomain),
            Arguments.of("unsubscribes", null, (SuppressionDelete) Suppressions::deleteUnsubscribesAllItems, "/unsubscribes", withoutDomain),
            Arguments.of("unsubscribes with domain id", TestHelper.domainId, (SuppressionDelete) Suppressions::deleteUnsubscribesAllItems, "/unsubscribes", withDomain),
            Arguments.of("on-hold list", null, (SuppressionDelete) Suppressions::deleteOnHoldListAllItems, "/on-hold-list", withoutDomain),
            Arguments.of("on-hold list with domain id", TestHelper.domainId, (SuppressionDelete) Suppressions::deleteOnHoldListAllItems, "/on-hold-list", withDomain)
        );
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE hard-bounces
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleteHardBouncesItems() succeeds when called with a valid item ID.
     */
    @Test
    public void testDeleteHardBouncesItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SuppressionList hardBounces = ms.recipients().suppressions().getHardBounces();

            if (hardBounces.items.length == 0) {
                fail();
            }

            String itemId = hardBounces.items[0].id;

            MailerSendResponse response = ms.recipients().suppressions()
                    .deleteHardBouncesItems(new String[]{ itemId });

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * [Behavior] deleteHardBouncesAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteHardBouncesAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteHardBouncesAllItems();

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE unsubscribes
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleteUnsubscribesItems() succeeds when called with a valid item ID.
     */
    @Test
    public void testDeleteUnsubscribesItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SuppressionList unsubscribes = ms.recipients().suppressions().getUnsubscribes();

            if (unsubscribes.items.length == 0) {
                fail();
            }

            String itemId = unsubscribes.items[0].id;

            MailerSendResponse response = ms.recipients().suppressions()
                    .deleteUnsubscribesItems(new String[]{ itemId });

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * [Behavior] deleteUnsubscribesAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteUnsubscribesAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteUnsubscribesAllItems();

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE on-hold-list
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleteOnHoldListItems() succeeds when called with a valid item ID.
     */
    @Test
    public void testDeleteOnHoldListItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SuppressionList onHoldList = ms.recipients().suppressions().getOnHoldList();

            if (onHoldList.items.length == 0) {
                fail();
            }

            String itemId = onHoldList.items[0].id;

            MailerSendResponse response = ms.recipients().suppressions()
                    .deleteOnHoldListItems(new String[]{ itemId });

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * [Behavior] deleteOnHoldListAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteOnHoldListAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteOnHoldListAllItems();

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE all variants — blocklist and spam-complaints
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleteBlocklistAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteBlocklistAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteBlocklistAllItems();

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * [Behavior] deleteSpamComplaintsAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteSpamComplaintsAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteSpamComplaintsAllItems();

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    @FunctionalInterface
    interface SuppressionCall {

        Object request(Suppressions suppressions) throws MailerSendException;
    }


    @FunctionalInterface
    interface SuppressionAdd {

        Object request(SuppressionAddBuilder builder) throws MailerSendException;
    }


    @FunctionalInterface
    interface SuppressionDelete {

        MailerSendResponse request(Suppressions suppressions) throws MailerSendException;
    }
}
