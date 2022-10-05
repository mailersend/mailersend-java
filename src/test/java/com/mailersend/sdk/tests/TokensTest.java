/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

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
    public void TestCreateToken() {
        
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
    public void UpdateToken() {
        
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
    public void DeleteToken() {
        
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
    
}
