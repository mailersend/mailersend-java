/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.users;

import java.util.ArrayList;
import java.util.stream.IntStream;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * Provides access to the Users and Invites endpoints of the MailerSend API.
 *
 * <p>Do not instantiate directly — access via {@code MailerSend.users()}.</p>
 */
public class Users {

    private final MailerSend apiObjectReference;

    private final UserBuilder userBuilder;

    private int pageFilter = 1;
    private int limitFilter = 25;


    /**
     * Do not initialize directly. This should only be accessed from MailerSend.users().
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Users(MailerSend apiObjectRef) {

        apiObjectReference = apiObjectRef;
        userBuilder = new UserBuilder(apiObjectRef);
    }


    /**
     * Set the page of the request.
     *
     * @param page the page number (min: 1)
     * @return this Users object (for chaining)
     */
    public Users page(int page) {

        pageFilter = page;

        return this;
    }


    /**
     * Set the results limit (10 - 100).
     *
     * @param limit the number of results per page
     * @return this Users object (for chaining)
     */
    public Users limit(int limit) {

        limitFilter = limit;

        return this;
    }


    /**
     * Returns the builder used to create or update users.
     *
     * @return a {@link UserBuilder} object
     */
    public UserBuilder builder() {

        return userBuilder;
    }


    // -------------------------------------------------------------------------
    // Users
    // -------------------------------------------------------------------------

    /**
     * Gets a paginated list of account users.
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link UsersList} object
     */
    public UsersList getUsers() throws MailerSendException {

        String endpoint = "/users".concat(preparePaginationParams());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        UsersList response = api.getRequest(endpoint, UsersList.class);

        response.postDeserialize();

        return response;
    }


    /**
     * Gets a single account user by ID.
     *
     * @param userId the ID of the user to retrieve
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return a {@link User} object
     */
    public User getUser(String userId) throws MailerSendException {

        String endpoint = "/users/".concat(userId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        UserResponse response = api.getRequest(endpoint, UserResponse.class);

        response.user.postDeserialize();

        return response.user;
    }


    /**
     * Deletes a user from the account.
     *
     * @param userId the ID of the user to delete
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return {@code true} if the deletion was successful
     */
    public boolean deleteUser(String userId) throws MailerSendException {

        String endpoint = "/users/".concat(userId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);

        return IntStream.of(new int[]{200, 204, 202}).anyMatch(x -> x == response.responseStatusCode);
    }


    // -------------------------------------------------------------------------
    // Invites
    // -------------------------------------------------------------------------

    /**
     * Gets a paginated list of pending invites.
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return an {@link InvitesList} object
     */
    public InvitesList getInvites() throws MailerSendException {

        String endpoint = "/invites".concat(preparePaginationParams());

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        InvitesList response = api.getRequest(endpoint, InvitesList.class);

        response.postDeserialize();

        return response;
    }


    /**
     * Gets a single invite by ID.
     *
     * @param inviteId the ID of the invite to retrieve
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return an {@link Invite} object
     */
    public Invite getInvite(String inviteId) throws MailerSendException {

        String endpoint = "/invites/".concat(inviteId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        InviteResponse response = api.getRequest(endpoint, InviteResponse.class);

        response.invite.postDeserialize();

        return response.invite;
    }


    /**
     * Resends a pending invite.
     *
     * @param inviteId the ID of the invite to resend
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return the updated {@link Invite} object
     */
    public Invite resendInvite(String inviteId) throws MailerSendException {

        String endpoint = "/invites/".concat(inviteId).concat("/resend");

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        InviteResponse response = api.postRequest(endpoint, "{}", InviteResponse.class);

        response.invite.postDeserialize();

        return response.invite;
    }


    /**
     * Cancels (deletes) a pending invite.
     *
     * @param inviteId the ID of the invite to cancel
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return {@code true} if the cancellation was successful
     */
    public boolean deleteInvite(String inviteId) throws MailerSendException {

        String endpoint = "/invites/".concat(inviteId);

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        MailerSendResponse response = api.deleteRequest(endpoint, MailerSendResponse.class);

        return IntStream.of(new int[]{200, 204, 202}).anyMatch(x -> x == response.responseStatusCode);
    }


    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Builds the query string for paginated list requests.
     */
    private String preparePaginationParams() {

        ArrayList<String> params = new ArrayList<String>();

        params.add("page=".concat(String.valueOf(pageFilter)));
        params.add("limit=".concat(String.valueOf(limitFilter)));

        StringBuilder requestParams = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {

            requestParams.append(i == 0 ? "?" : "&").append(params.get(i));
        }

        return requestParams.toString();
    }
}
