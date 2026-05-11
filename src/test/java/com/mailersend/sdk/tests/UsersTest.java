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
import com.mailersend.sdk.users.Invite;
import com.mailersend.sdk.users.InvitesList;
import com.mailersend.sdk.users.User;
import com.mailersend.sdk.users.UsersList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class UsersTest {

    private static final String USER_ID = "user-id-123";
    private static final String INVITE_ID = "invite-id-123";
    private static final String USER_EMAIL = "newuser@example.com";

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("UsersTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }


    /**
     * Test retrieving a list of users - behavior: GET /users
     */
    @Test
    public void TestGetUsers() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            UsersList list = ms.users().getUsers();

            assertNotNull(list);
            assertNotNull(list.users);
            assertTrue(list.users.length > 0);
            assertNotNull(list.users[0].id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a single user by ID - behavior: GET /users/{id}
     */
    @Test
    public void TestGetUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            User user = ms.users().getUser(USER_ID);

            assertNotNull(user);
            assertEquals(USER_ID, user.id);
            assertNotNull(user.email);
            assertNotNull(user.role);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test inviting a user - behavior: POST /users with all fields
     */
    @Test
    public void TestInviteUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Invite invite = ms.users().builder()
                .email(USER_EMAIL)
                .role("Admin")
                .inviteUser();

            assertNotNull(invite);
            assertNotNull(invite.id);
            assertEquals(USER_EMAIL, invite.email);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that inviting a user without email throws an exception - validation
     */
    @Test
    public void TestInviteUserWithoutEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.users().builder()
                .role("Admin")
                .inviteUser();
        });

        assertEquals("User email cannot be null or empty", ex.getMessage());
    }


    /**
     * Test that inviting a user without a role throws an exception - validation
     */
    @Test
    public void TestInviteUserWithoutRole() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.users().builder()
                .email(USER_EMAIL)
                .inviteUser();
        });

        assertEquals("User role cannot be null or empty", ex.getMessage());
    }


    /**
     * Test that inviting a Custom User without permissions throws an exception - validation
     */
    @Test
    public void TestInviteCustomUserWithoutPermissions() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.users().builder()
                .email(USER_EMAIL)
                .role("Custom User")
                .inviteUser();
        });

        assertEquals("Permissions are required for Custom User role", ex.getMessage());
    }


    /**
     * Test updating a user - behavior: PUT /users/{id}
     */
    @Test
    public void TestUpdateUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            User user = ms.users().builder()
                .role("Manager")
                .updateUser(USER_ID);

            assertNotNull(user);
            assertEquals(USER_ID, user.id);
            assertEquals("Manager", user.role);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that updating a user without an ID throws an exception - validation
     */
    @Test
    public void TestUpdateUserWithoutId() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.users().builder()
                .role("Manager")
                .updateUser(null);
        });

        assertEquals("User ID cannot be null or empty", ex.getMessage());
    }


    /**
     * Test that updating a Custom User without permissions throws an exception - validation
     */
    @Test
    public void TestUpdateCustomUserWithoutPermissions() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.users().builder()
                .role("Custom User")
                .updateUser(USER_ID);
        });

        assertEquals("Permissions are required for Custom User role", ex.getMessage());
    }


    /**
     * Test deleting a user - behavior: DELETE /users/{id} returns true
     */
    @Test
    public void TestDeleteUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean result = ms.users().deleteUser(USER_ID);

            assertTrue(result);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a list of invites - behavior: GET /invites
     */
    @Test
    public void TestGetInvites() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            InvitesList list = ms.users().getInvites();

            assertNotNull(list);
            assertNotNull(list.invites);
            assertTrue(list.invites.length > 0);
            assertNotNull(list.invites[0].id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a single invite by ID - behavior: GET /invites/{id}
     */
    @Test
    public void TestGetInvite() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Invite invite = ms.users().getInvite(INVITE_ID);

            assertNotNull(invite);
            assertEquals(INVITE_ID, invite.id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test resending an invite - behavior: POST /invites/{id}/resend
     */
    @Test
    public void TestResendInvite() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Invite invite = ms.users().resendInvite(INVITE_ID);

            assertNotNull(invite);
            assertEquals(INVITE_ID, invite.id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test cancelling (deleting) an invite - behavior: DELETE /invites/{id} returns true
     */
    @Test
    public void TestDeleteInvite() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            boolean result = ms.users().deleteInvite(INVITE_ID);

            assertTrue(result);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }
}
