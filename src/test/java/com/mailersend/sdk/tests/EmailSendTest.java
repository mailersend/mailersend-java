/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendResponseError;

public class EmailSendTest {

    
    @Test
    public void TestInvalidTokenFailsWith401() {
        
        Email email = new Email();
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.invalidToken);
        
        
        try {
            ms.Send(email);
        } catch (MailerSendResponseError e) {

            assertEquals(e.code, 401);
            return;
        }
       
        // fail if it reaches here
        fail();
    }
    
    @Test
    public void TestInvalidPersonalization() {
        
        Email email = TestHelper.createBasicEmail(false);
        
        email.addPersonalization("invalid.pers1", "test personalization 1");
        email.addPersonalization("invalid.pers2", "test personalization 2");
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ms.Send(email);
            
        } catch (MailerSendResponseError e) {
            
            assertEquals(e.code, 422);
            assertTrue(e.errors.containsKey("personalization.0.data"));
            
            return;
        }
        
        // fail if it reaches here
        fail();
    }
}
