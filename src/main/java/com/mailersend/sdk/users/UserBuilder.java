/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;

/**
 * Builder for creating or updating account users (invites).
 *
 * <p>Usage example — invite a new user:</p>
 * <pre>
 *   Invite invite = ms.users().builder()
 *       .email("user@example.com")
 *       .role("Admin")
 *       .inviteUser();
 * </pre>
 *
 * <p>Usage example — update an existing user:</p>
 * <pre>
 *   User user = ms.users().builder()
 *       .role("Manager")
 *       .updateUser("user-id");
 * </pre>
 */
public class UserBuilder {

    private final MailerSend apiObjectReference;

    private final UserRequestBody builderBody = new UserRequestBody();


    /**
     * No instantiation from outside the SDK.
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    protected UserBuilder(MailerSend apiObjectRef) {

        apiObjectReference = apiObjectRef;
    }


    /**
     * Set the email address of the user to invite.
     *
     * @param email the email address
     * @return this builder
     */
    public UserBuilder email(String email) {

        builderBody.email = email;

        return this;
    }


    /**
     * Set the role of the user.
     *
     * <p>Valid values: {@code Admin}, {@code Manager}, {@code Designer},
     * {@code Accountant}, {@code Custom User}.</p>
     *
     * @param role the role name
     * @return this builder
     */
    public UserBuilder role(String role) {

        builderBody.role = role;

        return this;
    }


    /**
     * Add a permission for the user (required when role is {@code Custom User}).
     *
     * @param permission the permission name (e.g. {@code "read-suppressions"})
     * @return this builder
     */
    public UserBuilder addPermission(String permission) {

        builderBody.permissions.add(permission);

        return this;
    }


    /**
     * Add a template ID to restrict the user's template access.
     *
     * @param templateId the template ID
     * @return this builder
     */
    public UserBuilder addTemplate(String templateId) {

        builderBody.templates.add(templateId);

        return this;
    }


    /**
     * Add a domain ID to restrict the user's domain access.
     *
     * @param domainId the domain ID
     * @return this builder
     */
    public UserBuilder addDomain(String domainId) {

        builderBody.domains.add(domainId);

        return this;
    }


    /**
     * Set whether the user is required to change their password periodically.
     *
     * @param required {@code true} to require periodic password changes
     * @return this builder
     */
    public UserBuilder requiresPeriodicPasswordChange(boolean required) {

        builderBody.requiresPeriodicPasswordChange = required;

        return this;
    }


    /**
     * Invites (creates) a new user in the account.
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return the created {@link Invite} object
     */
    public Invite inviteUser() throws MailerSendException {

        if (builderBody.email == null || builderBody.email.isBlank()) {

            throw new MailerSendException("User email cannot be null or empty");
        }

        if (builderBody.role == null || builderBody.role.isBlank()) {

            throw new MailerSendException("User role cannot be null or empty");
        }

        if ("Custom User".equals(builderBody.role) && builderBody.permissions.isEmpty()) {

            throw new MailerSendException("Permissions are required for Custom User role");
        }

        String endpoint = "/users";

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(builderBody);

        builderBody.reset();

        InviteResponse response = api.postRequest(endpoint, json, InviteResponse.class);

        response.invite.postDeserialize();

        return response.invite;
    }


    /**
     * Updates an existing account user.
     *
     * @param userId the ID of the user to update
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return the updated {@link User} object
     */
    public User updateUser(String userId) throws MailerSendException {

        if (userId == null || userId.isBlank()) {

            throw new MailerSendException("User ID cannot be null or empty");
        }

        if (builderBody.role == null || builderBody.role.isBlank()) {

            throw new MailerSendException("User role cannot be null or empty");
        }

        if ("Custom User".equals(builderBody.role) && builderBody.permissions.isEmpty()) {

            throw new MailerSendException("Permissions are required for Custom User role");
        }

        String endpoint = "/users/".concat(userId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();

        String json = gson.toJson(builderBody);

        builderBody.reset();

        UserResponse response = api.putRequest(endpoint, json, UserResponse.class);

        response.user.postDeserialize();

        return response.user;
    }
}
