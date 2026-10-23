/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.smtpusers.SmtpUser;
import com.mailersend.sdk.smtpusers.SmtpUsersList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SmtpUsersTest {

    private static final Date SMTP_USER_CREATED_AT = Date.from(Instant.parse("2024-02-20T09:39:44Z"));

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("SmtpUsersTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }

    /**
     * Tests retrieval of SMTP users list
     */
    @Test
    public void testCanGetSmtpUsers() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmtpUsersList list = ms.smtpUsers().getSmtpUsers(TestHelper.domainId);

            assertEquals(200, list.responseStatusCode);
            assertEquals(1, list.smtpUsers.length);
            assertEquals("0wkg2zj19ovx5py7", list.smtpUsers[0].id);
            assertEquals(TestHelper.domainId, list.smtpUsers[0].domainId);
            assertEquals(SMTP_USER_CREATED_AT, list.smtpUsers[0].createdAt);
            assertEquals(1, list.meta.currentPage);
            assertNotNull(list.links);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieval of SMTP users list with pagination parameters
     */
    @Test
    public void testCanGetSmtpUsersWithPagination() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmtpUsersList list = ms.smtpUsers()
                .page(2)
                .limit(10)
                .getSmtpUsers(TestHelper.domainId);

            assertEquals(1, list.smtpUsers.length);
            assertEquals(2, list.meta.currentPage);
            assertEquals(10, list.meta.limit);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests creating an SMTP user
     */
    @Test
    public void testCanCreateSmtpUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmtpUser smtpUser = ms.smtpUsers().smtpUserBuilder()
                .name("Support")
                .enabled(true)
                .createSmtpUser(TestHelper.domainId);

            assertEquals("480xl1qpeorzeg65", smtpUser.id);
            assertEquals("mssp.G7hR2kQ9vN4pL8xW", smtpUser.password);
            assertEquals(TestHelper.domainId, smtpUser.domainId);
            assertEquals(SMTP_USER_CREATED_AT, smtpUser.createdAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests retrieval of a single SMTP user
     */
    @Test
    public void testCanGetSingleSmtpUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmtpUser smtpUser = ms.smtpUsers().getSmtpUser(TestHelper.domainId, TestHelper.smtpUserId);

            assertEquals(TestHelper.smtpUserId, smtpUser.id);
            assertEquals("Support SMTP", smtpUser.name);
            assertEquals(TestHelper.domainId, smtpUser.domainId);
            assertEquals(SMTP_USER_CREATED_AT, smtpUser.createdAt);
            assertNull(smtpUser.password);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests updating an SMTP user
     */
    @Test
    public void testCanUpdateSmtpUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SmtpUser smtpUser = ms.smtpUsers().smtpUserBuilder()
                .name("Support SMTP")
                .enabled(false)
                .updateSmtpUser(TestHelper.domainId, TestHelper.smtpUserId);

            assertEquals(TestHelper.smtpUserId, smtpUser.id);
            assertEquals(false, smtpUser.enabled);
            assertEquals(SMTP_USER_CREATED_AT, smtpUser.createdAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that the SMTP user builder does not carry the values of a previous request over to the next one
     */
    @Test
    public void testSmtpUserBuilderResetsBodyAfterSend() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install("{\"data\":{\"id\":\"480xl1qpeorzeg65\"}}", 201);

        ms.smtpUsers().smtpUserBuilder().name("Support").enabled(false).createSmtpUser(TestHelper.domainId);
        ms.smtpUsers().smtpUserBuilder().name("Second").createSmtpUser(TestHelper.domainId);

        assertEquals("POST", client.lastMethod());
        assertEquals("https://api.mailersend.com/v1/domains/" + TestHelper.domainId + "/smtp-users", client.lastUri());
        assertEquals("{\"name\":\"Second\"}", client.lastBody());
    }

    /**
     * Tests deleting an SMTP user
     */
    @Test
    public void testCanDeleteSmtpUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean deleted = ms.smtpUsers().deleteSmtpUser(TestHelper.domainId, TestHelper.smtpUserIdToDelete);

            assertTrue(deleted);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }

    /**
     * Tests that retrieving an SMTP user with an invalid ID throws a 404 exception
     */
    @Test
    public void testGetSmtpUserWithInvalidIdFails() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.smtpUsers().getSmtpUser(TestHelper.domainId, TestHelper.invalidSmtpUserId);
        });

        assertEquals(404, e.code);
    }

    /**
     * Tests that deleting an SMTP user with an invalid ID throws a 404 exception
     */
    @Test
    public void testDeleteSmtpUserWithInvalidIdFails() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.smtpUsers().deleteSmtpUser(TestHelper.domainId, TestHelper.invalidSmtpUserId);
        });

        assertEquals(404, e.code);
    }

    /**
     * Tests that an invalid token results in a 401 exception when listing SMTP users
     */
    @Test
    public void testInvalidTokenFailsWith401OnListSmtpUsers() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.invalidToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.smtpUsers().getSmtpUsers(TestHelper.invalidTokenDomainId);
        });

        assertEquals(401, e.code);
    }
}
