/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.mailersend.sdk.util.ApiRecipient;
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
            
            for (ApiRecipient recipient : list.recipients) {
                
                System.out.println(recipient.id);
                System.out.println(recipient.email);
            }

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
            
            System.out.println(recipient.email);

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
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
            
            System.out.println(response.responseStatusCode);

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

        CapturingHttpClient client = CapturingHttpClient.install(200, "{\"data\":{\"id\":\"62a2120ba4920c48900f2761\",\"email\":\"john@mailerlite.com\",\"created_at\":\"2020-06-10T10:09:56.000000Z\",\"updated_at\":\"2020-06-10T10:10:13.000000Z\",\"deleted_at\":\"2020-06-10 10:10:13\"}}");

        Recipient recipient = ms.recipients().getRecipient("62a2120ba4920c48900f2761");

        assertEquals("GET", client.lastRequest().method());
        assertEquals("https://api.mailersend.com/v1/recipients/62a2120ba4920c48900f2761", client.lastUrl());
        assertEquals(Date.from(Instant.parse("2020-06-10T10:10:13Z")), recipient.deletedAt);
    }
}
