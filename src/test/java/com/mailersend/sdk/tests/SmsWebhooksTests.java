package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sms.webhooks.SmsWebhook;
import com.mailersend.sms.webhooks.SmsWebhookList;

public class SmsWebhooksTests {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("SmsWebhooksTest_" + info.getDisplayName());
	}
	
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
	
	@Test
	public void TestGetSmsWebhooks() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	SmsWebhookList list = ms.sms().webhooks().getWebhooks(TestHelper.smsPhoneNumberId);
        	
        	for (SmsWebhook webhook : list.webhooks) {
        		
        		System.out.println(webhook.id);
        	}
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
	
	
	@Test
	public void TestGetSingleSmsWebhook() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	SmsWebhookList list = ms.sms().webhooks().getWebhooks(TestHelper.smsPhoneNumberId);
        	
        	SmsWebhook webhook = ms.sms().webhooks().getWebhook(list.webhooks[0].id);
        	
        	System.out.println(webhook.id);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
	
	
	@Test
	public void TestCreateSmsWebhook() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	SmsWebhook webhook = ms.sms().webhooks().builder()
        	.addEvent("sms.sent")
        	.name("sms webhook")
        	.url("https://example.com")
        	.createWebhook(TestHelper.smsPhoneNumberId);
        	
        	System.out.print(webhook.id);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
	
	@Test
	public void TestUpdateSmsWebhook() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	SmsWebhookList list = ms.sms().webhooks().getWebhooks(TestHelper.smsPhoneNumberId);
        	
        	SmsWebhook webhook = ms.sms().webhooks().builder()
        	.name("sms updated webhook")
        	.updateWebhook(list.webhooks[0].id);
        	
        	System.out.print(webhook.name);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
	
	
	@Test
	public void TestDeleteSmsWebhook() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	SmsWebhookList list = ms.sms().webhooks().getWebhooks(TestHelper.smsPhoneNumberId);
        	
        	boolean result = ms.sms().webhooks().deleteWebhook(list.webhooks[0].id);
        	
        	assertTrue(result);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
}
