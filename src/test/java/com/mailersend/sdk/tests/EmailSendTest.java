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
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.gson.JsonArray;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.emails.BulkSendStatus;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;

import static com.mailersend.sdk.tests.CapturingHttpClient.json;
import static com.mailersend.sdk.util.EventTypes.QUEUED;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void testInvalidTokenFailsWith401() {
        
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
    public void testInvalidPersonalization() {
        
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
    public void testSimpleSend() throws MailerSendException {
           	    	
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
    public void testPojoPersonalization() throws MailerSendException {
        
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
    public void testCcSend() throws MailerSendException {
        
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
    public void testBccSend() throws MailerSendException {
        
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
    public void testEmailWithAttachment() throws IOException, MailerSendException {

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
    public void testSendBulkEmail() throws MailerSendException {
       
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
    public void testBulkSendStatus() throws MailerSendException {
       
        Email email = TestHelper.createBasicEmail(true);
        Email email2 = TestHelper.createBasicEmail(true);
        
        email2.setHtml("<b>Test bulk</b>");
        email2.setPlain("Test bulk");

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        String bulkSendId = ms.emails().bulkSend(new Email[] { email, email2 });
        assertEquals(QUEUED, ms.emails().bulkSendStatus(bulkSendId).state);
    }
    
    /**
     * Tests that sending an email missing a required field fails with 422
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("missingRequiredFieldEmails")
    public void testSendEmailMissingRequiredFieldFails(String label, Email email) {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.emails().send(email);
        });
        assertEquals(422, e.code);
    }

    static Stream<Arguments> missingRequiredFieldEmails() {
        Email withoutFrom = new Email();
        withoutFrom.addRecipient(TestHelper.toName, TestHelper.toEmail);
        withoutFrom.setSubject(TestHelper.subject);
        withoutFrom.setHtml(TestHelper.html);
        withoutFrom.setPlain(TestHelper.text);

        Email withoutTo = new Email();
        withoutTo.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        withoutTo.setSubject(TestHelper.subject);
        withoutTo.setHtml(TestHelper.html);
        withoutTo.setPlain(TestHelper.text);

        Email withoutSubject = new Email();
        withoutSubject.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        withoutSubject.addRecipient(TestHelper.toName, TestHelper.toEmail);
        withoutSubject.setHtml(TestHelper.html);
        withoutSubject.setPlain(TestHelper.text);

        Email withoutBody = new Email();
        withoutBody.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        withoutBody.addRecipient(TestHelper.toName, TestHelper.toEmail);
        withoutBody.setSubject(TestHelper.subject);

        return Stream.of(
            Arguments.of("missing from", withoutFrom),
            Arguments.of("missing to", withoutTo),
            Arguments.of("missing subject", withoutSubject),
            Arguments.of("missing body", withoutBody)
        );
    }


    /**
     * Test send email with custom headers returns 202
     */
    @Test
    public void testSendEmailWithHeaders() throws MailerSendException {
        Email email = new Email();
        email.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        email.addRecipient(TestHelper.toName, TestHelper.toEmail);
        email.setSubject(TestHelper.subject);
        email.setHtml(TestHelper.html);
        email.setPlain(TestHelper.text);
        email.addHeader("X-Custom-Header", "custom-value");

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        assertEquals(202, ms.emails().send(email).responseStatusCode);
    }


    /**
     * Test send email with precedence_bulk set to true returns 202
     */
    @Test
    public void testSendEmailWithPrecedenceBulk() throws MailerSendException {
        Email email = new Email();
        email.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        email.addRecipient(TestHelper.toName, TestHelper.toEmail);
        email.setSubject(TestHelper.subject);
        email.setHtml(TestHelper.html);
        email.setPlain(TestHelper.text);
        email.setPrecedenceBulk(true);

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        assertEquals(202, ms.emails().send(email).responseStatusCode);
    }


    /**
     * Test send email with list_unsubscribe returns 202
     */
    @Test
    public void testSendEmailWithListUnsubscribe() throws MailerSendException {
        Email email = new Email();
        email.setFrom(TestHelper.fromName, TestHelper.emailFrom);
        email.addRecipient(TestHelper.toName, TestHelper.toEmail);
        email.setSubject(TestHelper.subject);
        email.setHtml(TestHelper.html);
        email.setPlain(TestHelper.text);
        email.setListUnsubscribe("https://unsubscribe.example.com");

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        assertEquals(202, ms.emails().send(email).responseStatusCode);
    }


    /**
     * Test bulk send status response has all expected fields
     */
    @Test
    public void testBulkSendStatusHasAllFields() throws MailerSendException {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        com.mailersend.sdk.emails.BulkSendStatus status = ms.emails().bulkSendStatus("known-bulk-send-id");

        assertNotNull(status.state);
        assertNotNull(status.id);
        assertTrue(status.id.length() > 0);
        assertTrue(status.totalRecipientsCount >= 0);
        assertNotNull(status.createdAt);
    }


    @Test
    public void scheduleEmailTest() throws MailerSendException {
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


    /**
     * Test bulk send status keeps each message id at the position of its email in the request
     * when the API keys the ids because some emails were not sent
     */
    @Test
    public void testBulkSendStatusKeepsThePositionOfKeyedMessageIds() throws MailerSendException {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        BulkSendStatus status = ms.emails().bulkSendStatus("keyed-bulk-send-id");

        assertArrayEquals(new String[] { null, "61487a14608b1d0b4d506633", null, "61487a14608b1d0b4d506644" }, status.messagesId);
    }


    /**
     * Test bulk send status of an unknown bulk send fails with the API's 404
     */
    @Test
    public void testBulkSendStatusWithAnUnknownIdFailsWith404() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.emails().bulkSendStatus("unknown-bulk-send-id");
        });
        assertEquals(404, e.code);
        assertEquals("Resource not found.", e.getMessage());
    }


    /**
     * Tests that send() leaves the lists that were never filled out of the request. The API
     * rejects an empty references list on the Sandbox plan
     */
    @Test
    public void testSendLeavesOutEmptyLists() throws MailerSendException {

        Email email = TestHelper.createBasicEmail(true);

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(202, "", Map.of());

        ms.emails().send(email);

        assertEquals(json("{"
                + "'from':{'name':'From Name','email':'sender@test-sdk.com'},"
                + "'to':[{'name':'To Name','email':'java@mailersend.com'}],"
                + "'subject':'Subject',"
                + "'text':'This is the text content',"
                + "'html':'<p>This is the HTML <strong>content</strong></p>'"
                + "}"), client.lastBody());
    }


    /**
     * Tests that bulkSend() sends the send_at of each email as a unix timestamp
     */
    @Test
    public void testBulkSendSendsTheSendAtOfEachEmail() throws MailerSendException {

        Email email = TestHelper.createBasicEmail(true);
        email.setSendAt(new Date(SEND_AT * 1000));

        Email email2 = TestHelper.createBasicEmail(true);
        email2.setSendAt(new Date((SEND_AT + 3600) * 1000));

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        CapturingHttpClient client = CapturingHttpClient.install(202,
                "{\"message\":\"The bulk email is being processed.\",\"bulk_email_id\":\"614470d1588b866d0454f3e2\"}",
                Map.of());

        ms.emails().bulkSend(new Email[] { email, email2 });

        JsonArray body = client.lastBody().getAsJsonArray();

        assertEquals(String.valueOf(SEND_AT), body.get(0).getAsJsonObject().get("send_at").getAsString());
        assertEquals(String.valueOf(SEND_AT + 3600), body.get(1).getAsJsonObject().get("send_at").getAsString());
        assertFalse(body.get(0).getAsJsonObject().has("sendAt"));
    }


    /** 2025-08-27 01:00:00 UTC */
    private static final long SEND_AT = 1756256400L;
}
