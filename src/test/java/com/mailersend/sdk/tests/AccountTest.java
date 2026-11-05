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

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.account.AccountDetails;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;

public class AccountTest {

    private static final String ACCOUNT_RESPONSE = CapturingHttpClient.json("{'data':{"
        + "'name':'Acme Inc','status':'approved','plan_type':'starter','data_retention_days':7,"
        + "'emails':{'sent':12000,'received':430,'limit':-1},"
        + "'rate_limits':{'general':60,'email':120,'bulk_email':10,'activity':10},"
        + "'resources':{"
        + "'domains':{'used':3,'limit':10},'users':{'used':2,'limit':5},"
        + "'free_accountant_seat':{'used':0,'limit':0},'templates':{'used':41,'limit':250},"
        + "'smtp_users':{'used':1,'limit':50},'api_tokens':{'used':4,'limit':50},"
        + "'webhooks':{'used':2,'limit':50}},"
        + "'addons':{"
        + "'sms':{'sent':120,'received':30,'limit':-1},'whatsapp':{'sent':800,'limit':1000},"
        + "'phone_numbers':1,'domain_spaces':2,'data_retention_days':30,'dedicated_ips':0}"
        + "}}").toString();

    @BeforeEach
    public void setupEach(TestInfo info) throws IOException {
        VcrRecorder.useRecording("AccountTest_" + info.getDisplayName());
    }

    @AfterEach
    public void afterEach() throws IOException {
        VcrRecorder.stopRecording();
    }


    /**
     * Test that the account is requested with GET /account
     */
    @Test
    public void testGetAccountRequest() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(200, ACCOUNT_RESPONSE);

        ms.account().getAccount();

        assertEquals("GET", client.lastRequest().method());
        assertEquals("https://api.mailersend.com/v1/account", client.lastUrl());
        assertEquals("Bearer " + TestHelper.validToken, client.lastRequest().headers().firstValue("Authorization").orElse(null));
    }


    /**
     * Test that the whole account response is read
     */
    @Test
    public void testGetAccountReadsTheResponse() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient.install(200, ACCOUNT_RESPONSE);

        AccountDetails account = ms.account().getAccount();

        assertEquals("Acme Inc", account.name);
        assertEquals("approved", account.status);
        assertEquals("starter", account.planType);
        assertEquals(7, account.dataRetentionDays);

        assertEquals(12000, account.emails.sent);
        assertEquals(430, account.emails.received);
        assertEquals(-1, account.emails.limit);

        assertEquals(60, account.rateLimits.general);
        assertEquals(120, account.rateLimits.email);
        assertEquals(10, account.rateLimits.bulkEmail);
        assertEquals(10, account.rateLimits.activity);

        assertEquals(3, account.resources.domains.used);
        assertEquals(10, account.resources.domains.limit);
        assertEquals(2, account.resources.users.used);
        assertEquals(5, account.resources.users.limit);
        assertEquals(0, account.resources.freeAccountantSeat.used);
        assertEquals(0, account.resources.freeAccountantSeat.limit);
        assertEquals(41, account.resources.templates.used);
        assertEquals(250, account.resources.templates.limit);
        assertEquals(1, account.resources.smtpUsers.used);
        assertEquals(50, account.resources.smtpUsers.limit);
        assertEquals(4, account.resources.apiTokens.used);
        assertEquals(50, account.resources.apiTokens.limit);
        assertEquals(2, account.resources.webhooks.used);
        assertEquals(50, account.resources.webhooks.limit);

        assertEquals(120, account.addons.sms.sent);
        assertEquals(30, account.addons.sms.received);
        assertEquals(-1, account.addons.sms.limit);
        assertEquals(800, account.addons.whatsapp.sent);
        assertEquals(1000, account.addons.whatsapp.limit);
        assertEquals(1, account.addons.phoneNumbers);
        assertEquals(2, account.addons.domainSpaces);
        assertEquals(30, account.addons.dataRetentionDays);
        assertEquals(0, account.addons.dedicatedIps);
    }


    /**
     * Test that a missing WhatsApp add-on and an unset webhooks limit are read as null
     */
    @Test
    public void testGetAccountReadsNullValues() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient.install(200, ACCOUNT_RESPONSE
            .replace("\"whatsapp\":{\"sent\":800,\"limit\":1000}", "\"whatsapp\":null")
            .replace("\"webhooks\":{\"used\":2,\"limit\":50}", "\"webhooks\":{\"used\":2,\"limit\":null}"));

        AccountDetails account = ms.account().getAccount();

        assertNull(account.addons.whatsapp);
        assertNull(account.resources.webhooks.limit);
        assertEquals(2, account.resources.webhooks.used);
    }


    /**
     * Test that an invalid token fails with a 401
     */
    @Test
    public void testGetAccountWithInvalidTokenFails() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.invalidToken);

        CapturingHttpClient.install(401, "{\"message\":\"Unauthenticated.\"}");

        MailerSendException ex = assertThrows(MailerSendException.class, () -> ms.account().getAccount());

        assertEquals(401, ex.code);
    }
}
