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
import com.mailersend.sdk.smtpusers.SmtpUser;
import com.mailersend.sdk.smtpusers.SmtpUsersList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SmtpUsersTest {

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

            assertNotNull(list);
            assertNotNull(list.smtpUsers);

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

            assertNotNull(list);
            assertNotNull(list.smtpUsers);

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

            assertNotNull(smtpUser);
            assertNotNull(smtpUser.id);

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

            assertNotNull(smtpUser);
            assertNotNull(smtpUser.id);
            assertNotNull(smtpUser.name);

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

            assertNotNull(smtpUser);
            assertNotNull(smtpUser.id);
            assertEquals(false, smtpUser.enabled);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
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
