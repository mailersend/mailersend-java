package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.Instant;

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

	private static final String smsInboundRouteId = "83yxj6lj5gdo2rme";

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
	public void testGetSmsInboundRoutes() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsInboundRouteList routes = ms.sms().inboundRoutes().getSmsInboundRoutes();

        	assertEquals(smsInboundRouteId, routes.routes[0].id);
        	assertEquals(Instant.parse("2022-09-14T08:03:50Z"), routes.routes[0].createdAt.toInstant());

        } catch (MailerSendException e) {

            fail();
        }
	}

	/**
	 * Tests that page, limit, sms_number_id and enabled are sent
	 */
	@Test
	public void testGetSmsInboundRoutesWithFilters() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsInboundRouteList routes = ms.sms().inboundRoutes()
        			.page(2)
        			.limit(10)
        			.smsNumberId(TestHelper.smsPhoneNumberId)
        			.enabled(true)
        			.getSmsInboundRoutes();

        	assertTrue(routes.routes[0].enabled);

        } catch (MailerSendException e) {

            fail();
        }
	}

    @Test
    public void testUpdateSmsInboundRoute() {

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
        	assertFalse(route.enabled);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that an update without enabled does not send enabled
     */
    @Test
    public void testUpdateSmsInboundRouteWithoutEnabled() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsInboundRoute route = ms.sms().inboundRoutes().builder()
        			.name("Test inbound route renamed")
        			.updateSmsInboundRoute(smsInboundRouteId);

        	assertEquals("Test inbound route renamed", route.name);
        	assertTrue(route.enabled);

        } catch (MailerSendException e) {

            fail();
        }
    }


	@Test
	public void testGetSingleSmsInboundRoute() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsInboundRouteList routes = ms.sms().inboundRoutes().getSmsInboundRoutes();

        	SmsInboundRoute route = ms.sms().inboundRoutes().getSmsInboundRoute(routes.routes[0].id);

        	assertEquals(smsInboundRouteId, route.id);
        	assertEquals(TestHelper.smsPhoneNumberId, route.smsNumber.id);

        } catch (MailerSendException e) {

            fail();
        }
	}

    @Test
    public void testAddSmsInboundRoute() {

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

        	assertEquals(smsInboundRouteId, route.id);
        	assertFalse(route.enabled);

        } catch (MailerSendException e) {

            fail();
        }
    }

    /**
     * Tests that a route created without enabled does not send enabled
     */
    @Test
    public void testAddSmsInboundRouteWithoutEnabled() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsInboundRoute route = ms.sms().inboundRoutes().builder()
        			.smsNumberId(TestHelper.smsPhoneNumberId)
        			.name("Test inbound route")
        			.forwardUrl("https://example.com")
        			.filter("equal", "START")
        			.addSmsInboundRoute();

        	assertEquals(smsInboundRouteId, route.id);
        	assertTrue(route.enabled);

        } catch (MailerSendException e) {

            fail();
        }
    }

    @Test
    public void testDeleteSmsInboundRoute() {

        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	SmsInboundRouteList routes = ms.sms().inboundRoutes().getSmsInboundRoutes();

        	boolean result = ms.sms().inboundRoutes().deleteSmsInboundRoute(routes.routes[0].id);

        	assertTrue(result);

        } catch (MailerSendException e) {

            fail();
        }
    }
}
