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

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.mailersend.sdk.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

public class EmailSendTest {
   
    @Test
    public void TestInvalidTokenFailsWith401() {
        
        Email email = new Email();
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.invalidToken);
        
        
        try {
            ms.send(email);
        } catch (MailerSendException e) {

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
            
            ms.send(email);
            
        } catch (MailerSendException e) {
            
            assertEquals(e.code, 422);
            assertTrue(e.errors.containsKey("personalization.0.data"));
            
            return;
        }
        
        // fail if it reaches here
        fail();
    }
    
    
    @Test
    public void TestPojoPersonalization() {
        
        Email email = TestHelper.createBasicEmail(false);
        
        TestPojo pojo = new TestPojo();
        
        email.addPersonalization("pojo", pojo);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        
        try {
            
            MailerSendResponse response = ms.send(email);
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
        
    }
    
    
    @Test
    public void TestCcSend() {
        
        Email email = TestHelper.createBasicEmail(false);
        
        email.AddCc(TestHelper.ccName, TestHelper.ccEmail);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        
        try {
            
            ms.send(email);
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
    
    @Test
    public void TestBccSend() {
        
        Email email = TestHelper.createBasicEmail(false);
        
        email.AddBcc(TestHelper.bccName, TestHelper.bccEmail);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ms.send(email);
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
    
    
    @Test
    public void TestEmailWithAttachment() {
       
        Email email = TestHelper.createBasicEmail(true);
        
        try {
            email.attachFile("LICENSE");
            
            MailerSend ms = new MailerSend();
            ms.setToken(TestHelper.validToken);
            
            ms.send(email);
            
        } catch (IOException | MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
}
