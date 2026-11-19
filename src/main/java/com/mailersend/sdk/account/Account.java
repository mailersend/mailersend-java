/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.account;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendApi;
import com.mailersend.sdk.exceptions.MailerSendException;

/**
 * Provides access to the Account endpoint of the MailerSend API.
 *
 * <p>Do not instantiate directly — access via {@code MailerSend.account()}.</p>
 */
public class Account {

    private final MailerSend apiObjectReference;


    /**
     * Do not initialize directly. This should only be accessed from MailerSend.account().
     *
     * @param apiObjectRef a {@link com.mailersend.sdk.MailerSend} object.
     */
    public Account(MailerSend apiObjectRef) {

        apiObjectReference = apiObjectRef;
    }


    /**
     * Gets the plan, usage, rate limits and resource limits of the account the API token belongs to.
     *
     * @throws com.mailersend.sdk.exceptions.MailerSendException
     * @return an {@link AccountDetails} object
     */
    public AccountDetails getAccount() throws MailerSendException {

        MailerSendApi api = new MailerSendApi();
        api.setToken(apiObjectReference.getToken());

        AccountResponse response = api.getRequest("/account", AccountResponse.class);

        return response.account;
    }
}
