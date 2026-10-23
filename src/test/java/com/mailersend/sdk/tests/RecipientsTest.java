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
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.recipients.Recipient;
import com.mailersend.sdk.util.ApiRecipientsList;
import com.mailersend.sdk.vcr.VcrRecorder;


public class RecipientsTest {
	
	
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("RecipientsTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	

    /**
     * Gets a lists of recipients
     */
    @Test
    public void testRecipientsRetrievall() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ApiRecipientsList list = ms.recipients().getRecipients();
            
            assertEquals(200, list.responseStatusCode);
            assertEquals("62a2120ba4920c48900f2761", list.recipients[0].id);
            assertEquals("john@mailerlite.com", list.recipients[0].email);
            assertEquals(Date.from(Instant.parse("2022-06-09T15:30:19.265Z")), list.recipients[0].createdAt);
            assertNull(list.recipients[0].deletedAt);

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Gets a single recipient
     */
    @Test
    public void testSingleRecipientRetrieval() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ApiRecipientsList list = ms.recipients().getRecipients();

            if (list.recipients.length == 0) {
                
                fail();
            }
            
            String recipientId = list.recipients[0].id;
            
            Recipient recipient = ms.recipients().getRecipient(recipientId);
            
            assertEquals("john@mailerlite.com", recipient.email);
            assertEquals(Date.from(Instant.parse("2022-06-09T15:30:19.265Z")), recipient.createdAt);
            assertNull(recipient.deletedAt);
            assertEquals(Date.from(Instant.parse("2022-08-19T13:08:56.358Z")), recipient.emails[0].createdAt);
            assertEquals("john@mailerlite.com", recipient.emails[0].to);
            assertEquals(TestHelper.templateId, recipient.emails[0].templateId);
            assertEquals(TestHelper.domainId, recipient.emails[0].domainId);
            assertEquals("opened", recipient.emails[0].interaction[0]);
            assertEquals(Date.from(Instant.parse("2022-04-20T11:36:15Z")), recipient.domain.createdAt);

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }


    /**
     * Tests that the deleted_at of a soft-deleted recipient, which the API returns as "2020-06-10 10:10:13", is parsed as UTC
     */
    @Test
    public void testSingleRecipientParsesSpaceSeparatedDeletedAt() throws MailerSendException {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        RequestCapturingHttpClient client = RequestCapturingHttpClient.install(
            "{\"data\":{\"id\":\"62a2120ba4920c48900f2761\",\"email\":\"john@mailerlite.com\",\"created_at\":\"2020-06-10T10:09:56.000000Z\",\"updated_at\":\"2020-06-10T10:10:13.000000Z\",\"deleted_at\":\"2020-06-10 10:10:13\"}}",
            200);

        Recipient recipient = ms.recipients().getRecipient("62a2120ba4920c48900f2761");

        assertEquals("GET", client.lastMethod());
        assertEquals("https://api.mailersend.com/v1/recipients/62a2120ba4920c48900f2761", client.lastUri());
        assertEquals(Date.from(Instant.parse("2020-06-10T10:10:13Z")), recipient.deletedAt);
    }
    
    
    /**
     * Deletes a recipient
     */
    @Test
    public void testDeleteRecipient() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ApiRecipientsList list = ms.recipients().getRecipients();

            if (list.recipients.length == 0) {
                
                fail();
            }
            
            String recipientId = list.recipients[0].id;
            
            MailerSendResponse response = ms.recipients().deleteRecipient(recipientId);
            
            assertEquals(204, response.responseStatusCode);

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
}
