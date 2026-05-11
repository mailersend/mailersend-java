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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

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
    public void TestGetIdentities() {

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
     * Test retrieving a single sender identity by ID - behavior: GET /identities/{id}
     */
    @Test
    public void TestGetIdentity() {

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
    public void TestGetIdentityByEmail() {

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
     * Test creating a sender identity - behavior: POST /identities with all fields
     */
    @Test
    public void TestCreateIdentity() {

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
     * Test that creating an identity without domain ID throws an exception - validation
     */
    @Test
    public void TestCreateIdentityWithoutDomainId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.senderIdentities().builder()
                .email(IDENTITY_EMAIL)
                .name("Test Identity")
                .createIdentity();
        });

        assertEquals("Domain ID cannot be empty", ex.getMessage());
    }


    /**
     * Test that creating an identity without email throws an exception - validation
     */
    @Test
    public void TestCreateIdentityWithoutEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.senderIdentities().builder()
                .domainId(TestHelper.domainId)
                .name("Test Identity")
                .createIdentity();
        });

        assertEquals("Email cannot be empty", ex.getMessage());
    }


    /**
     * Test updating a sender identity by ID - behavior: PUT /identities/{id}
     * Only name, reply_to_email, reply_to_name should be sent
     */
    @Test
    public void TestUpdateIdentity() {

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
     * Test updating a sender identity by email - behavior: PUT /identities/email/{email}
     */
    @Test
    public void TestUpdateIdentityByEmail() {

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
     * Test that updating an identity with a blank ID throws an exception - validation
     */
    @Test
    public void TestUpdateIdentityWithoutId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.senderIdentities().builder()
                .name("Updated Identity")
                .updateIdentity("   ");
        });

        assertEquals("Identity ID cannot be empty", ex.getMessage());
    }


    /**
     * Test that updating an identity by email with a blank email throws an exception - validation
     */
    @Test
    public void TestUpdateIdentityByEmailWithoutEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.senderIdentities().builder()
                .name("Updated Identity")
                .updateIdentityByEmail("   ");
        });

        assertEquals("Email cannot be empty", ex.getMessage());
    }


    /**
     * Test deleting a sender identity by ID - behavior: DELETE /identities/{id} returns true
     */
    @Test
    public void TestDeleteIdentity() {

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
     * Test deleting a sender identity by email - behavior: DELETE /identities/email/{email} returns true
     */
    @Test
    public void TestDeleteIdentityByEmail() {

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
    public void TestResendConfirmation() {

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
