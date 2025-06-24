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
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.emails.BulkSendStatus;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;


public class EmailSendTest {
	
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("EmailSendTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
	
    /**
     * Test token
     */
    @Test
    public void TestInvalidTokenFailsWith401() {
        
        Email email = new Email();
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.invalidToken);
        
        
        try {
            ms.emails().send(email);
        } catch (MailerSendException e) {

            assertEquals(e.code, 401);
            return;
        }
       
        // fail if it reaches here
        fail();
    }
    
    
    /**
     * Test wrong personalization
     */
    @Test
    public void TestInvalidPersonalization() {
        
        Email email = TestHelper.createBasicEmail(false);
        
        email.addPersonalization("invalid.pers1", "test personalization 1");
        email.addPersonalization("invalid.pers2", "test personalization 2");
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ms.emails().send(email);
            
        } catch (MailerSendException e) {
            
            assertEquals(e.code, 422);
            assertTrue(e.errors.containsKey("personalization.0.data"));
            
            return;
        }
        
        // fail if it reaches here
        fail();
    }
    
    
    /**
     * Simple email send
     */
    @Test
    public void TestSimpleSend() {
           	    	
        Email email = new Email();
        
        email.subject = TestHelper.subject;
        email.html = TestHelper.html;
        email.text = TestHelper.text;
        
        email.addRecipient(TestHelper.toName, TestHelper.toEmail);
        email.AddReplyTo(new Recipient(TestHelper.fromName, TestHelper.emailFrom));
        
        email.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            MailerSendResponse response = ms.emails().send(email);
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
    
    
    /**
     * Test personalization from a POJO
     */
    @Test
    public void TestPojoPersonalization() {
        
        Email email = TestHelper.createBasicEmail(false);
        
        TestPojo pojo = new TestPojo();
        
        email.addPersonalization("pojo", pojo);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        
        try {
            
            MailerSendResponse response = ms.emails().send(email);
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
        
    }
    
    
    /**
     * Test email with CC
     */
    @Test
    public void TestCcSend() {
        
        Email email = TestHelper.createBasicEmail(false);
        
        email.AddCc(TestHelper.ccName, TestHelper.ccEmail);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        
        try {
            
            ms.emails().send(email);
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
    
    
    /**
     * Test email with BCC
     */
    @Test
    public void TestBccSend() {
        
        Email email = TestHelper.createBasicEmail(false);
        
        email.AddBcc(TestHelper.bccName, TestHelper.bccEmail);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ms.emails().send(email);
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
    
    
    /**
     * Test email with attachment
     */
    @Test
    public void TestEmailWithAttachment() {
       
        Email email = TestHelper.createBasicEmail(true);
        
        try {
            email.attachFile("LICENSE");
            
            MailerSend ms = new MailerSend();
            ms.setToken(TestHelper.validToken);
            
            ms.emails().send(email);
            
        } catch (IOException | MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
    
    
    /**
     * Test bulk email send
     */
    @Test
    public void TestSendBulkEmail() {
       
        Email email = TestHelper.createBasicEmail(true);
        Email email2 = TestHelper.createBasicEmail(true);
        
        email2.setHtml("<b>Test bulk</b>");
        email2.setPlain("Test bulk");
        
        try {
                        
            MailerSend ms = new MailerSend();
            ms.setToken(TestHelper.validToken);
            
            String bulkSendId = ms.emails().bulkSend(new Email[] { email, email2 });
            
            System.out.println(bulkSendId);
            
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
    
    
    /**
     * Test retrieving the status for a bulk send
     */
    @Test
    public void TestBulkSendStatus() {
       
        Email email = TestHelper.createBasicEmail(true);
        Email email2 = TestHelper.createBasicEmail(true);
        
        email2.setHtml("<b>Test bulk</b>");
        email2.setPlain("Test bulk");
        
        try {
                        
            MailerSend ms = new MailerSend();
            ms.setToken(TestHelper.validToken);
            
            String bulkSendId = ms.emails().bulkSend(new Email[] { email, email2 });
            
            BulkSendStatus status = ms.emails().bulkSendStatus(bulkSendId);
            
            System.out.println(status.state);
            
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
    
    @Test
    public void ScheduleEmailTest() {
        Email email = new Email();
        
        email.subject = TestHelper.subject;
        email.html = TestHelper.html;
        email.text = TestHelper.text;
        
        email.addRecipient(TestHelper.toName, TestHelper.toEmail);
        email.AddReplyTo(new Recipient(TestHelper.fromName, TestHelper.emailFrom));
        
        email.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2024-08-03T00:00:00.875000Z");
        Date scheduleDate = Date.from(Instant.from(ta));
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(scheduleDate);
        calendar.add(Calendar.DATE, 1);
        
        email.setSendAt(calendar.getTime());
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            MailerSendResponse response = ms.emails().send(email);
        } catch (MailerSendException e) {
            
            // fail if any error is thrown
            fail();
        }
    }
}
