/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
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
import com.mailersend.sdk.senderidentities.SenderIdentitiesList;
import com.mailersend.sdk.senderidentities.SenderIdentity;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SenderIdentitiesTest {

    private static final String IDENTITY_ID = "ident-id-123";
    private static final String IDENTITY_EMAIL = "test@example.com";

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("SenderIdentitiesTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }


    /**
     * Test retrieving a list of sender identities - behavior: GET /identities
     */
    @Test
    public void testGetIdentities() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentitiesList list = ms.senderIdentities().getIdentities();

            assertNotNull(list);
            assertNotNull(list.identities);
            assertTrue(list.identities.length > 0);
            assertNotNull(list.identities[0].id);
            assertNotNull(list.identities[0].email);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that every list filter is sent and query values are URL-encoded - behavior:
     * GET /identities?page=2&limit=10&domain_id=...&query=test%2B1%40example.com&order_by=email&order=desc
     */
    @Test
    public void testGetIdentitiesWithFilters() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentitiesList list = ms.senderIdentities()
                .page(2)
                .limit(10)
                .domainId(TestHelper.domainId)
                .query("test+1@example.com")
                .orderBy("email")
                .order("desc")
                .getIdentities();

            assertEquals(200, list.responseStatusCode);
            assertEquals(2, list.meta.currentPage);
            assertEquals("test+1@example.com", list.identities[0].email);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a single sender identity by ID - behavior: GET /identities/{id}
     */
    @Test
    public void testGetIdentity() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentity identity = ms.senderIdentities().getIdentity(IDENTITY_ID);

            assertNotNull(identity);
            assertEquals(IDENTITY_ID, identity.id);
            assertEquals(IDENTITY_EMAIL, identity.email);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a sender identity by email - behavior: GET /identities/email/{email}
     */
    @Test
    public void testGetIdentityByEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentity identity = ms.senderIdentities().getIdentityByEmail(IDENTITY_EMAIL);

            assertNotNull(identity);
            assertEquals(IDENTITY_EMAIL, identity.email);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test creating a sender identity - behavior: POST /identities with domain_id, email and name
     */
    @Test
    public void testCreateIdentity() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentity identity = ms.senderIdentities().builder()
                .domainId(TestHelper.domainId)
                .email(IDENTITY_EMAIL)
                .name("Test Identity")
                .createIdentity();

            assertNotNull(identity);
            assertNotNull(identity.id);
            assertEquals(IDENTITY_EMAIL, identity.email);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test creating a sender identity sends reply_to_email, reply_to_name, add_note and personal_note -
     * behavior: POST /identities with all accepted fields
     */
    @Test
    public void testCreateIdentityWithAllFields() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentity identity = ms.senderIdentities().builder()
                .domainId(TestHelper.domainId)
                .email(IDENTITY_EMAIL)
                .name("Test Identity")
                .replyToEmail("reply@example.com")
                .replyToName("Reply Name")
                .addNote(true)
                .personalNote("Please confirm this sender")
                .createIdentity();

            assertEquals("ident-id-456", identity.id);
            assertEquals("reply@example.com", identity.replyToEmail);
            assertEquals("Reply Name", identity.replyToName);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that createIdentity() rejects a missing domain ID or email - validation
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidCreateParams")
    public void testCreateIdentityRejectsInvalidParams(String label, String domainId, String email, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.senderIdentities().builder()
                .domainId(domainId)
                .email(email)
                .name("Test Identity")
                .createIdentity();
        });

        assertEquals(expectedMessage, ex.getMessage());
    }

    static Stream<Arguments> invalidCreateParams() {
        return Stream.of(
            Arguments.of("missing domain id", null, IDENTITY_EMAIL, "Domain ID cannot be empty"),
            Arguments.of("blank domain id", "   ", IDENTITY_EMAIL, "Domain ID cannot be empty"),
            Arguments.of("missing email", TestHelper.domainId, null, "Email cannot be empty"),
            Arguments.of("blank email", TestHelper.domainId, "   ", "Email cannot be empty")
        );
    }


    /**
     * Test updating a sender identity by ID - behavior: PUT /identities/{id}
     */
    @Test
    public void testUpdateIdentity() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentity identity = ms.senderIdentities().builder()
                .name("Updated Identity")
                .replyToEmail("reply@example.com")
                .replyToName("Reply Name")
                .updateIdentity(IDENTITY_ID);

            assertNotNull(identity);
            assertEquals("Updated Identity", identity.name);
            assertEquals("reply@example.com", identity.replyToEmail);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that create-only fields set on the shared builder are not sent on update - behavior:
     * the fixture only matches the body with name, reply_to_email and reply_to_name
     */
    @Test
    public void testUpdateIdentityExcludesCreateOnlyFields() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentity identity = ms.senderIdentities().builder()
                .domainId(TestHelper.domainId)
                .email(IDENTITY_EMAIL)
                .addNote(true)
                .personalNote("Please confirm this sender")
                .name("Updated Identity")
                .replyToEmail("reply@example.com")
                .replyToName("Reply Name")
                .updateIdentity(IDENTITY_ID);

            assertEquals("Updated Identity", identity.name);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test updating a sender identity by email - behavior: PUT /identities/email/{email}
     */
    @Test
    public void testUpdateIdentityByEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentity identity = ms.senderIdentities().builder()
                .name("Updated Identity")
                .replyToEmail("reply@example.com")
                .replyToName("Reply Name")
                .updateIdentityByEmail(IDENTITY_EMAIL);

            assertNotNull(identity);
            assertEquals("Updated Identity", identity.name);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that updateIdentity() rejects a missing identity ID - validation
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidIdentityIds")
    public void testUpdateIdentityRejectsMissingId(String label, String identityId) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.senderIdentities().builder()
                .name("Updated Identity")
                .updateIdentity(identityId);
        });

        assertEquals("Identity ID cannot be empty", ex.getMessage());
    }

    static Stream<Arguments> invalidIdentityIds() {
        return Stream.of(
            Arguments.of("null identity id", null),
            Arguments.of("blank identity id", "   ")
        );
    }


    /**
     * Test that updateIdentityByEmail() rejects a missing email - validation
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidIdentityEmails")
    public void testUpdateIdentityByEmailRejectsMissingEmail(String label, String email) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.senderIdentities().builder()
                .name("Updated Identity")
                .updateIdentityByEmail(email);
        });

        assertEquals("Email cannot be empty", ex.getMessage());
    }

    static Stream<Arguments> invalidIdentityEmails() {
        return Stream.of(
            Arguments.of("null email", null),
            Arguments.of("blank email", "   ")
        );
    }


    /**
     * Test deleting a sender identity by ID - behavior: DELETE /identities/{id} returns true on 204
     */
    @Test
    public void testDeleteIdentity() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean result = ms.senderIdentities().deleteIdentity(IDENTITY_ID);

            assertTrue(result);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test deleting a sender identity by email - behavior: DELETE /identities/email/{email} returns true on 202
     */
    @Test
    public void testDeleteIdentityByEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean result = ms.senderIdentities().deleteIdentityByEmail(IDENTITY_EMAIL);

            assertTrue(result);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test resending confirmation for a sender identity - behavior: POST /identities/{id}/resend
     */
    @Test
    public void testResendConfirmation() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SenderIdentity identity = ms.senderIdentities().resendConfirmation(IDENTITY_ID);

            assertNotNull(identity);
            assertEquals(IDENTITY_ID, identity.id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }
}
