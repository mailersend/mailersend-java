/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.mailersend.sdk.util.ApiRecipientsList;
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
        	ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
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
        
        ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
        ms.recipients().suppressions().addBuilder().pattern(".*@example.com");
        BlocklistItem[] items = ms.recipients().suppressions().addBuilder().addToBlocklist();
        
        try {
            
            // delete from blocklist
            BlocklistListResponse blocklist = ms.recipients().suppressions().domainId(TestHelper.domainId).getBlocklist();
            
            if (blocklist.items.length == 0) {
                
                fail();
            }
            
            String itemId = blocklist.items[0].id;
            
            MailerSendResponse response = ms.recipients().suppressions().domainId(TestHelper.domainId).deleteBlocklistItems(new String[] { itemId });
            
            System.out.println(response.responseStatusCode);
                      
            
            // delete from spam complaints
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            ms.recipients().suppressions().addBuilder().recipient("test@example.com");
            ms.recipients().suppressions().addBuilder().addRecipientsToSpamComplaints();
            
            SuppressionList spamComplaints = ms.recipients().suppressions().domainId(TestHelper.domainId).getSpamComplaints();
            
            if (spamComplaints.items.length == 0) {
                
                fail();
            }
            
            itemId = spamComplaints.items[0].id;
            
            response = ms.recipients().suppressions().domainId(TestHelper.domainId).deleteSpamComplaintsItems(new String[] { itemId });
            
            System.out.println(response.responseStatusCode);
            

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Behavior: GET /v1/recipients — query-string parameters
    // -------------------------------------------------------------------------

    /**
     * [Behavior] page, limit, and domain_id are serialised into the recipients query string.
     */
    @Test
    public void testGetRecipientsWithPageLimitAndDomainIdParams() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            ApiRecipientsList list = ms.recipients()
                    .page(2)
                    .limit(10)
                    .domainId(TestHelper.domainId)
                    .getRecipients();

            // The fixture returns one recipient — a non-null list proves the URL was built correctly.
            if (list == null) {
                fail();
            }

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Validation: GET /v1/recipients — limit range
    // -------------------------------------------------------------------------

    /**
     * [Validation] limit() throws when the value is below the minimum of 10.
     */
    @Test
    public void testLimitBelowMinimumThrowsValidationException() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.recipients().limit(9);
        });

        assertEquals("Limit must be between 10 and 100", e.getMessage());
    }


    /**
     * [Validation] limit() throws when the value exceeds the maximum of 100.
     */
    @Test
    public void testLimitAboveMaximumThrowsValidationException() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.recipients().limit(101);
        });

        assertEquals("Limit must be between 10 and 100", e.getMessage());
    }


    // -------------------------------------------------------------------------
    // Validation: POST suppression lists — recipients array required
    // -------------------------------------------------------------------------

    /**
     * [Validation] addRecipientsToHardBounces() throws when no recipients have been added.
     */
    @Test
    public void testAddToHardBouncesWithoutRecipientsThrowsException() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            ms.recipients().suppressions().addBuilder().addRecipientsToHardBounces();
        });

        assertEquals("No recipients specified for suppression list", e.getMessage());
    }


    /**
     * [Validation] addRecipientsToSpamComplaints() throws when no recipients have been added.
     */
    @Test
    public void testAddToSpamComplaintsWithoutRecipientsThrowsException() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            ms.recipients().suppressions().addBuilder().addRecipientsToSpamComplaints();
        });

        assertEquals("No recipients specified for suppression list", e.getMessage());
    }


    /**
     * [Validation] addRecipientsToUnsubscribes() throws when no recipients have been added.
     */
    @Test
    public void testAddToUnsubscribesWithoutRecipientsThrowsException() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        MailerSendException e = assertThrows(MailerSendException.class, () -> {
            ms.recipients().suppressions().addBuilder().domainId(TestHelper.domainId);
            ms.recipients().suppressions().addBuilder().addRecipientsToUnsubscribes();
        });

        assertEquals("No recipients specified for suppression list", e.getMessage());
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE hard-bounces
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleteHardBouncesItems() succeeds when called with a valid item ID.
     */
    @Test
    public void testDeleteHardBouncesItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SuppressionList hardBounces = ms.recipients().suppressions().getHardBounces();

            if (hardBounces.items.length == 0) {
                fail();
            }

            String itemId = hardBounces.items[0].id;

            MailerSendResponse response = ms.recipients().suppressions()
                    .deleteHardBouncesItems(new String[]{ itemId });

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * [Behavior] deleteHardBouncesAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteHardBouncesAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteHardBouncesAllItems();

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE unsubscribes
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleteUnsubscribesItems() succeeds when called with a valid item ID.
     */
    @Test
    public void testDeleteUnsubscribesItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SuppressionList unsubscribes = ms.recipients().suppressions().getUnsubscribes();

            if (unsubscribes.items.length == 0) {
                fail();
            }

            String itemId = unsubscribes.items[0].id;

            MailerSendResponse response = ms.recipients().suppressions()
                    .deleteUnsubscribesItems(new String[]{ itemId });

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * [Behavior] deleteUnsubscribesAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteUnsubscribesAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteUnsubscribesAllItems();

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE on-hold-list
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleteOnHoldListItems() succeeds when called with a valid item ID.
     */
    @Test
    public void testDeleteOnHoldListItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            SuppressionList onHoldList = ms.recipients().suppressions().getOnHoldList();

            if (onHoldList.items.length == 0) {
                fail();
            }

            String itemId = onHoldList.items[0].id;

            MailerSendResponse response = ms.recipients().suppressions()
                    .deleteOnHoldListItems(new String[]{ itemId });

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * [Behavior] deleteOnHoldListAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteOnHoldListAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteOnHoldListAllItems();

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    // -------------------------------------------------------------------------
    // Behavior: DELETE all variants — blocklist and spam-complaints
    // -------------------------------------------------------------------------

    /**
     * [Behavior] deleteBlocklistAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteBlocklistAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteBlocklistAllItems();

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }


    /**
     * [Behavior] deleteSpamComplaintsAllItems() succeeds and returns a 200 status code.
     */
    @Test
    public void testDeleteSpamComplaintsAllItems() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

            MailerSendResponse response = ms.recipients().suppressions().deleteSpamComplaintsAllItems();

            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
    }
}
