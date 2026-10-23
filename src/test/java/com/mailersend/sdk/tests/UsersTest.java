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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
    public void testGetUsers() {

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
     * Test page() and limit() are sent - behavior: GET /users?page=2&limit=10
     */
    @Test
    public void testGetUsersWithPageAndLimit() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            UsersList list = ms.users().page(2).limit(10).getUsers();

            assertEquals(200, list.responseStatusCode);
            assertEquals(2, list.meta.currentPage);
            assertEquals(USER_ID, list.users[0].id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a single user by ID - behavior: GET /users/{id}
     */
    @Test
    public void testGetUser() {

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
     * Test inviting a user - behavior: POST /users with body {"email":...,"role":"Admin"}.
     * No permissions, templates or domains were added, so none are sent: the fixture only
     * matches that body. The response is the invite itself, not wrapped in data.
     */
    @Test
    public void testInviteUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Invite invite = ms.users().builder()
                .email(USER_EMAIL)
                .role("Admin")
                .inviteUser();

            assertEquals(200, invite.responseStatusCode);
            assertEquals(INVITE_ID, invite.id);
            assertEquals(USER_EMAIL, invite.email);
            assertEquals("Admin", invite.role);
            assertNotNull(invite.createdAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test inviting a Custom User sends permissions, templates, domains and
     * requires_periodic_password_change - behavior: POST /users
     */
    @Test
    public void testInviteCustomUserWithAllFields() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Invite invite = ms.users().builder()
                .email("designer@example.com")
                .role("Custom User")
                .addPermission("read-own-templates")
                .addPermission("manage-template")
                .addTemplate("template-id")
                .addDomain("domain-id")
                .requiresPeriodicPasswordChange(true)
                .inviteUser();

            assertEquals("invite-id-456", invite.id);
            assertEquals("Custom User", invite.role);
            assertArrayEquals(new String[] {"read-own-templates", "manage-template"}, invite.permissions);
            assertArrayEquals(new String[] {"template-id"}, invite.data.templates);
            assertArrayEquals(new String[] {"domain-id"}, invite.data.domains);
            assertTrue(invite.requiresPeriodicPasswordChange);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that inviteUser() rejects a missing email or role, and a Custom User without permissions - validation
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidInviteParams")
    public void testInviteUserRejectsInvalidParams(String label, String email, String role, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.users().builder()
                .email(email)
                .role(role)
                .inviteUser();
        });

        assertEquals(expectedMessage, ex.getMessage());
    }

    static Stream<Arguments> invalidInviteParams() {
        return Stream.of(
            Arguments.of("missing email", null, "Admin", "User email cannot be null or empty"),
            Arguments.of("blank email", "   ", "Admin", "User email cannot be null or empty"),
            Arguments.of("missing role", USER_EMAIL, null, "User role cannot be null or empty"),
            Arguments.of("blank role", USER_EMAIL, "   ", "User role cannot be null or empty"),
            Arguments.of("custom user without permissions", USER_EMAIL, "Custom User", "Permissions are required for Custom User role")
        );
    }


    /**
     * Test updating a user - behavior: PUT /users/{id} with body {"role":"Manager"}.
     * The response is the user itself, not wrapped in data.
     */
    @Test
    public void testUpdateUser() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            User user = ms.users().builder()
                .role("Manager")
                .updateUser(USER_ID);

            assertEquals(200, user.responseStatusCode);
            assertEquals(USER_ID, user.id);
            assertEquals("Manager", user.role);
            assertNotNull(user.updatedAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that an email set on the shared builder is not sent on update - behavior:
     * the fixture only matches the body {"role":"Manager"}
     */
    @Test
    public void testUpdateUserExcludesEmail() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            User user = ms.users().builder()
                .email(USER_EMAIL)
                .role("Manager")
                .updateUser(USER_ID);

            assertEquals(USER_ID, user.id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test updating a Custom User sends permissions, templates, domains and
     * requires_periodic_password_change - behavior: PUT /users/{id}
     */
    @Test
    public void testUpdateCustomUserWithAllFields() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            User user = ms.users().builder()
                .role("Custom User")
                .addPermission("read-own-templates")
                .addTemplate("template-id")
                .addDomain("domain-id")
                .requiresPeriodicPasswordChange(false)
                .updateUser(USER_ID);

            assertEquals("Custom User", user.role);
            assertArrayEquals(new String[] {"read-own-templates"}, user.permissions);
            assertEquals("domain-id", user.domains[0].id);
            assertEquals("template-id", user.templates[0].id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that updateUser() rejects a missing user ID or role, and a Custom User without permissions - validation
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidUpdateParams")
    public void testUpdateUserRejectsInvalidParams(String label, String userId, String role, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.users().builder()
                .role(role)
                .updateUser(userId);
        });

        assertEquals(expectedMessage, ex.getMessage());
    }

    static Stream<Arguments> invalidUpdateParams() {
        return Stream.of(
            Arguments.of("missing user id", null, "Manager", "User ID cannot be null or empty"),
            Arguments.of("blank user id", "   ", "Manager", "User ID cannot be null or empty"),
            Arguments.of("missing role", USER_ID, null, "User role cannot be null or empty"),
            Arguments.of("custom user without permissions", USER_ID, "Custom User", "Permissions are required for Custom User role")
        );
    }


    /**
     * Test deleting a user - behavior: DELETE /users/{id} returns true
     */
    @Test
    public void testDeleteUser() {

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
    public void testGetInvites() {

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
     * Test page() and limit() are sent - behavior: GET /invites?page=2&limit=10
     */
    @Test
    public void testGetInvitesWithPageAndLimit() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            InvitesList list = ms.users().page(2).limit(10).getInvites();

            assertEquals(200, list.responseStatusCode);
            assertEquals(2, list.meta.currentPage);
            assertEquals(INVITE_ID, list.invites[0].id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a single invite by ID - behavior: GET /invites/{id}
     */
    @Test
    public void testGetInvite() {

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
    public void testResendInvite() {

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
    public void testDeleteInvite() {

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
