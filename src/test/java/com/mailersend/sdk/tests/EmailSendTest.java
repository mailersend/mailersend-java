/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;

import static com.mailersend.sdk.util.EventTypes.QUEUED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.emails().send(email);
        });
        assertEquals(401, e.code);
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

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.emails().send(email);
        });
        assertEquals(422, e.code);
        assertTrue(e.errors.containsKey("personalization.0.data"));
    }
    
    
    /**
     * Simple email send
     */
    @Test
    public void TestSimpleSend() throws MailerSendException {
           	    	
        Email email = new Email();
        
        email.subject = TestHelper.subject;
        email.html = TestHelper.html;
        email.text = TestHelper.text;
        
        email.addRecipient(TestHelper.toName, TestHelper.toEmail);
        email.AddReplyTo(new Recipient(TestHelper.fromName, TestHelper.emailFrom));
        
        email.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        assertEquals(202, ms.emails().send(email).responseStatusCode);

    }
    
    
    /**
     * Test personalization from a POJO
     */
    @Test
    public void TestPojoPersonalization() throws MailerSendException {
        
        Email email = TestHelper.createBasicEmail(false);
        
        TestPojo pojo = new TestPojo();
        
        email.addPersonalization("pojo", pojo);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        assertEquals(202, ms.emails().send(email).responseStatusCode);
    }
    
    
    /**
     * Test email with CC
     */
    @Test
    public void TestCcSend() throws MailerSendException {
        
        Email email = TestHelper.createBasicEmail(false);
        
        email.AddCc(TestHelper.ccName, TestHelper.ccEmail);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        assertEquals(202, ms.emails().send(email).responseStatusCode);
    }
    
    
    /**
     * Test email with BCC
     */
    @Test
    public void TestBccSend() throws MailerSendException {
        
        Email email = TestHelper.createBasicEmail(false);
        
        email.AddBcc(TestHelper.bccName, TestHelper.bccEmail);
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        assertEquals(202, ms.emails().send(email).responseStatusCode);
    }
    
    
    /**
     * Test email with attachment
     */
    @Test
    public void TestEmailWithAttachment() throws IOException, MailerSendException {

        Email email = TestHelper.createBasicEmail(true);

        email.attachFile("LICENSE");

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        assertEquals(202, ms.emails().send(email).responseStatusCode);
    }
    
    
    /**
     * Test bulk email send
     */
    @Test
    public void TestSendBulkEmail() throws MailerSendException {
       
        Email email = TestHelper.createBasicEmail(true);
        Email email2 = TestHelper.createBasicEmail(true);
        
        email2.setHtml("<b>Test bulk</b>");
        email2.setPlain("Test bulk");

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        String bulkSendId = ms.emails().bulkSend(new Email[] { email, email2 });

        assertNotNull(bulkSendId, "Bulk send ID should not be null");
    }
    
    
    /**
     * Test retrieving the status for a bulk send
     */
    @Test
    public void TestBulkSendStatus() throws MailerSendException {
       
        Email email = TestHelper.createBasicEmail(true);
        Email email2 = TestHelper.createBasicEmail(true);
        
        email2.setHtml("<b>Test bulk</b>");
        email2.setPlain("Test bulk");

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        String bulkSendId = ms.emails().bulkSend(new Email[] { email, email2 });
        assertEquals(QUEUED, ms.emails().bulkSendStatus(bulkSendId).state);
    }
    
    @Test
    public void ScheduleEmailTest() throws MailerSendException {
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

        assertEquals(202, ms.emails().send(email).responseStatusCode);
    }
}
