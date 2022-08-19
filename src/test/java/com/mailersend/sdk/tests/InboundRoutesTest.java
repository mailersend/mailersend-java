package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.inboundroutes.Forward;
import com.mailersend.sdk.inboundroutes.InboundRoute;
import com.mailersend.sdk.inboundroutes.InboundRoutesList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class InboundRoutesTest {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("InboundRoutesTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
	
	@Test
	public void AddInboundRouteTest() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	Forward forward = new Forward();
        	forward.type = "webhook";
        	forward.value = "https://example-domain.com";
        	forward.secret = "asdfgh";
        	
        	ms.inboundRoutes().builder()
        		.domainId(TestHelper.domainId)
        		.name("Test inbound name")
        		.domainEnabled(false)
        		.matchFilter("match_all")
        		.forwards(new Forward[] { forward })
        		.addRoute();
        	
        } catch (MailerSendException ex) {
        	fail();
        }
	}
	
	@Test
	public void InboundRoutesListTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
			InboundRoutesList routes = ms.inboundRoutes().getRoutes();
			
			assertEquals(routes.responseStatusCode, 200);
			
			for (InboundRoute route : routes.routes) {
				System.out.println(route.id);
			}
			
		} catch (MailerSendException e) {
			
			fail();
		}
	}
	
	@Test
	public void SingleInboundRouteTest() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
			InboundRoute route = ms.inboundRoutes().getRoute(TestHelper.inboundRouteId);
			
			System.out.println(route.id);
			System.out.println(route.name);
			
		} catch (MailerSendException e) {
			fail();
		}
	}
	
	@Test
	public void UpdateInboundRouteTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
        	
        	Forward forward = new Forward();
        	forward.type = "webhook";
        	forward.value = "https://example.com";
        	forward.secret = "asdfgh";
        	
			InboundRoute route = ms.inboundRoutes().builder()
				.domainId(TestHelper.domainId)
				.name("Updated route name")
	    		.domainEnabled(false)
	    		.matchFilter("match_all")
	    		.forwards(new Forward[] { forward })
				.updateRoute(TestHelper.inboundRouteId);
			
			assertEquals(route.name, "Updated route name");
			
		} catch (MailerSendException e) {
			fail();
		}
	}
	
	@Test
	public void DeleteInboundRouteTest() {
		
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
			boolean result = ms.inboundRoutes().deleteRoute(TestHelper.inboundRouteId);
			
			assertTrue(result);
		} catch (MailerSendException e) {
			fail();
		}
	}
}
