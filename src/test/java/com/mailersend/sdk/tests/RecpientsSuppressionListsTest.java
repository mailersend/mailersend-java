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
import com.mailersend.sdk.recipients.BlocklistItem;
import com.mailersend.sdk.recipients.BlocklistListResponse;
import com.mailersend.sdk.recipients.SuppressionItem;
import com.mailersend.sdk.recipients.SuppressionList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class RecpientsSuppressionListsTest {

    
	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("RecpientsSuppressionListsTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
    /**
     * Tests retrieving items from the suppression lists
     */
    @Test
    public void TestGetFromSuppressionLists() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            BlocklistListResponse blocklist = ms.recipients().suppressions().getBlocklist();
            
            for (BlocklistItem item : blocklist.items) {
                
                System.out.println(item.id);
                System.out.println(item.pattern);
                System.out.println(item.type);
            }
            
            SuppressionList hardBounces = ms.recipients().suppressions().getHardBounces();
            
            for (SuppressionItem item : hardBounces.items) {
                
                System.out.println(item.id);
                System.out.println(item.recipient.email);
            }
            
            SuppressionList spamComplaints = ms.recipients().suppressions().getSpamComplaints();
            
            for (SuppressionItem item : spamComplaints.items) {
                
                System.out.println(item.id);
                System.out.println(item.recipient.email);
            }
            
            SuppressionList unsubscribes = ms.recipients().suppressions().getUnsubscribes();
            
            for (SuppressionItem item : unsubscribes.items) {
                
                System.out.println(item.id);
                System.out.println(item.recipient.email);
            }

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests adding items to the suppression lists
     */
    @Test
    public void TestAddToSuppressionList() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
            
            // blocklist
            ms.recipients().suppressions().addBuilder().pattern(".*@example.com");
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            BlocklistItem[] items = ms.recipients().suppressions().addBuilder().addToBlocklist();
            
            for (BlocklistItem item : items) {
                
                System.out.println(item.id);
            }
            
            
            // hard bounces
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            SuppressionList list = ms.recipients().suppressions().addBuilder().addRecipientsToHardBounces();
            
            for (SuppressionItem item : list.items) {
                
                System.out.println(item.id);
            }
            
            
            // spam complaints
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            list = ms.recipients().suppressions().addBuilder().addRecipientsToSpamComplaints();
            
            for (SuppressionItem item : list.items) {
                
                System.out.println(item.id);
            }
            
            
            // unsubscribes
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            list = ms.recipients().suppressions().addBuilder().addRecipientsToUnsubscribes();
            
            for (SuppressionItem item : list.items) {
                
                System.out.println(item.id);
            }
            
        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests deleting items from the suppression lists
     * @throws MailerSendException 
     */
    @Test
    public void TestDeleteFromSuppressionList() throws MailerSendException {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        ms.recipients().suppressions().addBuilder().pattern(".*@example.com");
        BlocklistItem[] items = ms.recipients().suppressions().addBuilder().addToBlocklist();
        
        try {
            
            // delete from blocklist
            BlocklistListResponse blocklist = ms.recipients().suppressions().getBlocklist();
            
            if (blocklist.items.length == 0) {
                
                fail();
            }
            
            String itemId = blocklist.items[0].id;
            
            MailerSendResponse response = ms.recipients().suppressions().deleteBlocklistItems(new String[] { itemId });
            
            System.out.println(response.responseStatusCode);
            
            
            // delete from hard bounces
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            ms.recipients().suppressions().addBuilder().addRecipientsToHardBounces();
            
            SuppressionList hardBounces = ms.recipients().suppressions().getHardBounces();
            
            if (hardBounces.items.length == 0) {
                
                fail();
            }
            
            itemId = hardBounces.items[0].id;
            
            response = ms.recipients().suppressions().deleteHardBouncesItems(new String[] { itemId });
            
            System.out.println(response.responseStatusCode);
            
            
            // delete from spam complaints
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().addRecipientsToSpamComplaints();
            
            SuppressionList spamComplaints = ms.recipients().suppressions().getSpamComplaints();
            
            if (spamComplaints.items.length == 0) {
                
                fail();
            }
            
            itemId = spamComplaints.items[0].id;
            
            response = ms.recipients().suppressions().deleteSpamComplaintsItems(new String[] { itemId });
            
            System.out.println(response.responseStatusCode);
            

            // delete from unsubscribes
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().addRecipientsToUnsubscribes();
            
            SuppressionList unsubscribes = ms.recipients().suppressions().getUnsubscribes();
            
            if (unsubscribes.items.length == 0) {
                
                fail();
            }
            
            itemId = unsubscribes.items[0].id;
            
            response = ms.recipients().suppressions().deleteUnsubscribesItems(new String[] { itemId });
            
            System.out.println(response.responseStatusCode);


        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
}
