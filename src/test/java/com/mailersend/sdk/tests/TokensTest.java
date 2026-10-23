/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.tokens.Token;
import com.mailersend.sdk.tokens.TokenAdd;
import com.mailersend.sdk.tokens.TokenAddBuilder;
import com.mailersend.sdk.tokens.TokenListResponse;
import com.mailersend.sdk.tokens.TokenScopes;
import com.mailersend.sdk.vcr.VcrRecorder;

public class TokensTest {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("TokensTest_" + info.getDisplayName());
	}

	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}


    /**
     * Test token creation - behavior: POST /token with name, domain_id and scopes
     */
    @Test
    public void testCreateToken() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            TokenAdd token = ms.tokens().addBuilder()
                .name("Test token")
                .domainId(TestHelper.domainId)
                .addScope(TokenScopes.activityFull)
                .addScope(TokenScopes.analyticsFull)
                .addToken();

            assertNotNull(token.id);
            assertNotNull(token.accessToken);
            assertEquals("Test token", token.name);
            assertEquals(2, token.scopes.length);
            assertEquals(TestHelper.domainId, token.domain.id);
            assertFalse(token.hasFull);
            assertFalse(token.hasOnlySending);
            assertEquals("mlsn.783b62", token.preview);
            assertNull(token.expiresAt);
            assertNotNull(token.createdAt);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that the add builder is reset after a token is created - behavior: a second
     * addToken() on the same builder no longer has a name
     */
    @Test
    public void testCreateTokenResetsBuilder() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        TokenAddBuilder builder = ms.tokens().addBuilder();

        try {

            builder.name("Test token")
                .domainId(TestHelper.domainId)
                .addScope(TokenScopes.activityFull)
                .addScope(TokenScopes.analyticsFull)
                .addToken();

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            builder.addToken();
        });

        assertEquals("Token name cannot be null or empty", ex.getMessage());
    }


    /**
     * Test token update (pause) - behavior: PUT /token/{id} with body {"status":"pause"}
     */
    @Test
    public void updateToken() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            Token token = ms.tokens().updateToken(TestHelper.tokenIdToPause, true);

            assertEquals(TestHelper.tokenIdToPause, token.id);
            assertEquals("pause", token.status);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test token update (unpause) - behavior: PUT /token/{id} with body {"status":"unpause"}
     */
    @Test
    public void testUnpauseToken() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            Token token = ms.tokens().updateToken(TestHelper.tokenIdToPause, false);

            assertEquals("unpause", token.status);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test token deletion - behavior: DELETE /token/{id} forwards the 200 status
     */
    @Test
    public void deleteToken() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            MailerSendResponse response = ms.tokens().deleteToken(TestHelper.tokenIdToDelete);

            assertEquals(200, response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a list of tokens - behavior: GET /token returns list, domain and pagination
     */
    @Test
    public void testGetTokens() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            TokenListResponse response = ms.tokens().getTokens();

            assertNotNull(response);
            assertNotNull(response.tokens);
            assertTrue(response.tokens.length > 0);
            assertNotNull(response.tokens[0].id);
            assertEquals(TestHelper.domainId, response.tokens[0].domain.id);
            assertEquals(1, response.meta.currentPage);
            assertNotNull(response.links.first);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test page() and limit() are sent - behavior: GET /token?page=2&limit=10
     */
    @Test
    public void testGetTokensWithPageAndLimit() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            TokenListResponse response = ms.tokens().page(2).limit(10).getTokens();

            assertEquals(200, response.responseStatusCode);
            assertEquals(2, response.meta.currentPage);
            assertEquals(TestHelper.tokenIdToPause, response.tokens[0].id);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a single token - behavior: GET /token/{id} returns token with scopes and domain
     */
    @Test
    public void testGetToken() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Token token = ms.tokens().getToken(TestHelper.tokenIdToPause);

            assertNotNull(token);
            assertNotNull(token.id);
            assertNotNull(token.scopes);
            assertTrue(token.scopes.length > 0);
            assertEquals(TestHelper.domainId, token.domain.id);
            assertEquals("test-sdk.com", token.domain.name);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test updating token name and status - behavior: PUT /token/{id} with body {"name":...,"status":"pause"}
     */
    @Test
    public void testUpdateTokenNameAndStatus() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Token token = ms.tokens().updateToken(TestHelper.tokenIdToPause, "Updated Name", "pause");

            assertNotNull(token);
            assertEquals("Updated Name", token.name);
            assertEquals("pause", token.status);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test updating only the token name - behavior: the fixture only matches the body {"name":"Updated Name"},
     * so status is not sent
     */
    @Test
    public void testUpdateTokenNameOnly() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Token token = ms.tokens().updateToken(TestHelper.tokenIdToPause, "Updated Name", null);

            assertEquals("Updated Name", token.name);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test updating only the token status - behavior: the fixture only matches the body {"status":"pause"},
     * so name is not sent
     */
    @Test
    public void testUpdateTokenStatusOnly() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            Token token = ms.tokens().updateToken(TestHelper.tokenIdToPause, null, "pause");

            assertEquals("pause", token.status);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that quotes and backslashes in the name are JSON-escaped - behavior: the fixture only matches
     * the body {"name":"Name with \"quotes\" and \\ backslash"}
     */
    @Test
    public void testUpdateTokenNameIsJsonEscaped() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        String name = "Name with \"quotes\" and \\ backslash";

        try {

            Token token = ms.tokens().updateToken(TestHelper.tokenIdToPause, name, null);

            assertEquals(name, token.name);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test that updateToken() rejects a missing name and status, a long name and an unknown status - validation
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidUpdateParams")
    public void testUpdateTokenRejectsInvalidParams(String label, String name, String status, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.tokens().updateToken(TestHelper.tokenIdToPause, name, status);
        });

        assertEquals(expectedMessage, ex.getMessage());
    }

    static Stream<Arguments> invalidUpdateParams() {
        return Stream.of(
            Arguments.of("name and status null", null, null, "At least one of name or status must be provided"),
            Arguments.of("name and status blank", "   ", "   ", "At least one of name or status must be provided"),
            Arguments.of("name longer than 50 characters", "A".repeat(51), null, "Token name cannot be longer than 50 characters"),
            Arguments.of("status not pause or unpause", null, "paused", "Token status must be either pause or unpause")
        );
    }


    /**
     * Test that addToken() rejects a missing or long name and missing scopes - validation
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidAddParams")
    public void testCreateTokenRejectsInvalidParams(String label, String name, String scope, String expectedMessage) {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            TokenAddBuilder builder = ms.tokens().addBuilder()
                .name(name)
                .domainId(TestHelper.domainId);

            if (scope != null) {
                builder.addScope(scope);
            }

            builder.addToken();
        });

        assertEquals(expectedMessage, ex.getMessage());
    }

    static Stream<Arguments> invalidAddParams() {
        return Stream.of(
            Arguments.of("null name", null, TokenScopes.emailFull, "Token name cannot be null or empty"),
            Arguments.of("blank name", "   ", TokenScopes.emailFull, "Token name cannot be null or empty"),
            Arguments.of("name longer than 50 characters", "A".repeat(51), TokenScopes.emailFull, "Token name cannot be longer than 50 characters"),
            Arguments.of("no scopes", "Valid Name", null, "At least one scope is required")
        );
    }


    /**
     * Test that adding an invalid scope throws an exception
     */
    @Test
    public void testCreateTokenWithInvalidScope() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.tokens().addBuilder()
                .name("Valid Name")
                .domainId(TestHelper.domainId)
                .addScope("invalid_scope_value")
                .addToken();
        });

        assertEquals("Scope is not valid", ex.getMessage());
    }

}
