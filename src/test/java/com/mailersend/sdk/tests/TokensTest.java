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
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.tokens.Token;
import com.mailersend.sdk.tokens.TokenAdd;
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
     * Test token creation
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
            
            System.out.println(token.id);
            System.out.println(token.name);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Test token update (pause)
     */
    @Test
    public void updateToken() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            Token token = ms.tokens().updateToken(TestHelper.tokenIdToPause, true);
            
            System.out.println(token.name);
            System.out.println(token.status);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Test token deletion
     */
    @Test
    public void deleteToken() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            MailerSendResponse response = ms.tokens().deleteToken(TestHelper.tokenIdToDelete);

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a list of tokens - behavior: GET /token returns list
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

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test retrieving a single token - behavior: GET /token/{id} returns token with scopes
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

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * Test updating token name and status - behavior: PUT /token/{id}
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
     * Test that updating token without name or status throws an exception
     */
    @Test
    public void testUpdateTokenWithoutNameOrStatus() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.tokens().updateToken(TestHelper.tokenIdToPause, null, null);
        });

        assertEquals("At least one of name or status must be provided", ex.getMessage());
    }


    /**
     * Test that creating a token with a null name throws an exception
     */
    @Test
    public void testCreateTokenWithNullName() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.tokens().addBuilder()
                .domainId(TestHelper.domainId)
                .addScope(TokenScopes.emailFull)
                .addToken();
        });

        assertEquals("Token name cannot be null or empty", ex.getMessage());
    }


    /**
     * Test that creating a token with an empty name throws an exception
     */
    @Test
    public void testCreateTokenWithEmptyName() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.tokens().addBuilder()
                .name("   ")
                .domainId(TestHelper.domainId)
                .addScope(TokenScopes.emailFull)
                .addToken();
        });

        assertEquals("Token name cannot be null or empty", ex.getMessage());
    }


    /**
     * Test that creating a token with a name longer than 50 chars throws an exception
     */
    @Test
    public void testCreateTokenWithLongName() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.tokens().addBuilder()
                .name("A".repeat(51))
                .domainId(TestHelper.domainId)
                .addScope(TokenScopes.emailFull)
                .addToken();
        });

        assertEquals("Token name cannot be longer than 50 characters", ex.getMessage());
    }


    /**
     * Test that creating a token with no scopes throws an exception
     */
    @Test
    public void testCreateTokenWithNoScopes() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException ex = assertThrows(MailerSendException.class, () -> {
            ms.tokens().addBuilder()
                .name("Valid Name")
                .domainId(TestHelper.domainId)
                .addToken();
        });

        assertEquals("At least one scope is required", ex.getMessage());
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
