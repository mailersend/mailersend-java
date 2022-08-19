/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sdk.webhooks.Webhook;
import com.mailersend.sdk.webhooks.WebhookEvents;
import com.mailersend.sdk.webhooks.WebhooksList;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class WebhooksTest {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("WebhooksTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
    /**
     * Tests the creation of a webhook
     */
    @Test
    public void TestWebhookCreation() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            String webhookTestUrl = "https://example.com/test-".concat(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999)));
            
            Webhook webhook = ms.webhooks().builder()
                .name("Test webook")
                .url(webhookTestUrl)
                .addEvent(WebhookEvents.ACTIVITY_OPENED)
                .addEvent(WebhookEvents.ACTIVITY_CLICKED)
                .createWebhook(TestHelper.domainId);
            
            System.out.println(webhook.name);

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests updating a webhook
     */
    @Test
    public void TestUpdateWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);
            
            if (list.webhooks.length == 0) {
                
                fail();
            }
            
            Webhook webhook = ms.webhooks()
                    .builder()
                    .name("Updated webhook name 2")
                    .updateWebhook(list.webhooks[0].id);
                        
            System.out.println(webhook.name);

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests deleting a webhook
     */
    @Test
    public void TestDeleteWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);
            
            if (list.webhooks.length == 0) {
                
                fail();
            }
            
            MailerSendResponse response = ms.webhooks().deleteWebhook(list.webhooks[0].id);
            
            System.out.println(response.responseStatusCode);

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests retrieving a single webhook
     */
    @Test
    public void TestGetWebhook() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);
            
            if (list.webhooks.length == 0) {
                
                fail();
            }
            
            Webhook webhook = ms.webhooks().getWebhook(list.webhooks[0].id);
            
            System.out.println(webhook.name);

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests the retrieval of the webhooks
     */
    @Test
    public void TestWebhooksList() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
           WebhooksList list = ms.webhooks().getWebhooks(TestHelper.domainId);
           
           for (Webhook webhook : list.webhooks) {
               
               System.out.println(webhook.name);
           }

        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
}
