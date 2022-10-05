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
    public void TestRecipientsRetrievall() {
        
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
    public void TestSingleRecipientRetrieval() {
        
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
    public void TestDeleteRecipient() {
        
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
    
}
