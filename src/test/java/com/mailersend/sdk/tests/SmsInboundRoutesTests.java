package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRoute;
import com.mailersend.sdk.sms.inboundroutes.SmsInboundRouteList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class SmsInboundRoutesTests {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("SmsInboundRoutesTests_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
	@Test
	public void TestGetSmsInboundRoutes() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
        	SmsInboundRouteList routes = ms.sms().inboundRoutes().getSmsInboundRoutes();
        	
        	for (SmsInboundRoute route : routes.routes) {
        		
        		System.out.println(route.id);
        	}
        	
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}
	
    @Test
    public void TestUpdateSmsInboundRoute() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	SmsInboundRouteList routes = ms.sms().inboundRoutes().getSmsInboundRoutes();
            
        	SmsInboundRoute route = ms.sms().inboundRoutes().builder()
        			.smsNumberId(TestHelper.smsPhoneNumberId)
        			.name("Test inbound route updated")
        			.enabled(false)
        			.forwardUrl("https://example.com")
        			.filter("equal", "START")
        			.updateSmsInboundRoute(routes.routes[0].id);
        	
        	assertEquals("Test inbound route updated", route.name);
        
        } catch (MailerSendException e) {
            
            fail();
        }
    }
	
	
	@Test
	public void TestGetSingleSmsInboundRoute() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
        	SmsInboundRouteList routes = ms.sms().inboundRoutes().getSmsInboundRoutes();
        	
        	SmsInboundRoute route = ms.sms().inboundRoutes().getSmsInboundRoute(routes.routes[0].id);
        	
        	System.out.println(route.id);
        
        } catch (MailerSendException e) {
            
            fail();
        }
	}

    @Test
    public void TestAddSmsInboundRoute() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
        	SmsInboundRoute route = ms.sms().inboundRoutes().builder()
        			.smsNumberId(TestHelper.smsPhoneNumberId)
        			.name("Test inbound route")
        			.enabled(false)
        			.forwardUrl("https://example.com")
        			.filter("equal", "START")
        			.addSmsInboundRoute();
        	
        	System.out.println(route.id);

        } catch (MailerSendException e) {
            
            fail();
        }
    }
    
    @Test
    public void TestDeleteSmsInboundRoute() {
    	
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
        	SmsInboundRouteList routes = ms.sms().inboundRoutes().getSmsInboundRoutes();
        	
        	boolean result = ms.sms().inboundRoutes().deleteSmsInboundRoute(routes.routes[0].id);
        	
        	System.out.println(result);

        } catch (MailerSendException e) {
            
            fail();
        }
    }
}
