package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sdk.emailverification.AsyncEmailVerificationResponse;
import com.mailersend.sdk.emailverification.EmailVerificationList;
import com.mailersend.sdk.emailverification.EmailVerificationLists;
import com.mailersend.sdk.emailverification.ListVerificationResults;
import com.mailersend.sdk.emailverification.SingleEmailVerificationResponse;

public class EmailVerificationTests {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("EmailVerificationTests_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
	
	@Test
	public void CreateVerificationListTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
			
        	EmailVerificationList list = ms.emailVerification().builder()
			.name("Test email verification")
			.addEmail("info@example.com")
			.addEmail("info1@example.com")
			.addEmail("info2@example.com")
			.create();
			
        	assertEquals(list.name, "Test email verification");
        	
        	System.out.println(list.id);
			
		} catch (MailerSendException e) {
			
			fail();
		}
	}
	
	@Test
	public void VerifyListTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
			
        	EmailVerificationList list = ms.emailVerification().verifyList(TestHelper.emailVerificationListId);
        	
        	assertEquals(list.id, TestHelper.emailVerificationListId);
        	
        	System.out.println(list.status.name);
        	
		} catch (MailerSendException e) {
			
			fail();
		}
	}
	
	@Test
	public void GetListTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
			
        	EmailVerificationList list = ms.emailVerification().getList(TestHelper.emailVerificationListId);
        	
        	assertEquals(list.id, TestHelper.emailVerificationListId);
        	
        	System.out.println(list.name);
        	
		} catch (MailerSendException e) {
			
			fail();
		}
	}
	
	
	@Test
	public void GetListResultsTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
			ListVerificationResults results = ms.emailVerification().verificationResults(TestHelper.emailVerificationListId);
			
			assertEquals(results.results.length, 3);
		} catch (MailerSendException e) {
			
			fail();
		}
	}
	
	
	@Test
	public void GetListsTest() {
       MailerSend ms = new MailerSend();
       ms.setToken(TestHelper.validToken);

       try {
		EmailVerificationLists lists = ms.emailVerification().getLists();

		assertEquals(lists.lists[0].source, "api");

	} catch (MailerSendException e) {

		fail();
	}
	}

	/**
	 * Behavior: POST /email-verification/verify returns the status field.
	 */
	@Test
	public void test_verify_email_returns_status() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			SingleEmailVerificationResponse response = ms.emailVerification().verifyEmail("test@example.com");

			assertNotNull(response);
			assertEquals("valid", response.status);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Validation: null email rejected before any HTTP call is made.
	 */
	@Test
	public void test_verify_email_with_null_throws_exception() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmail(null);
		});

		assertEquals("Email cannot be null or empty", ex.getMessage());
	}

	/**
	 * Validation: empty email rejected before any HTTP call is made.
	 */
	@Test
	public void test_verify_email_with_empty_string_throws_exception() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmail("");
		});

		assertEquals("Email cannot be null or empty", ex.getMessage());
	}

	/**
	 * Failure mode: invalid token results in a 401 exception for verifyEmail.
	 */
	@Test
	public void test_verify_email_with_invalid_token_throws_401() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.invalidToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmail("test@example.com");
		});

		assertEquals(401, ex.code);
	}

	/**
	 * Behavior: POST /email-verification/verify-async returns id, address, status, result, error.
	 */
	@Test
	public void test_verify_email_async_returns_response_fields() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			AsyncEmailVerificationResponse response = ms.emailVerification().verifyEmailAsync("test@example.com");

			assertNotNull(response);
			assertEquals("67c83bf24a5d02568029ee10", response.id);
			assertEquals("test@example.com", response.address);
			assertEquals("queued", response.status);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Validation: null email rejected before any HTTP call is made for async verify.
	 */
	@Test
	public void test_verify_email_async_with_null_throws_exception() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmailAsync(null);
		});

		assertEquals("Email cannot be null or empty", ex.getMessage());
	}

	/**
	 * Validation: empty email rejected before any HTTP call is made for async verify.
	 */
	@Test
	public void test_verify_email_async_with_empty_string_throws_exception() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmailAsync("");
		});

		assertEquals("Email cannot be null or empty", ex.getMessage());
	}

	/**
	 * Failure mode: invalid token results in a 401 exception for verifyEmailAsync.
	 */
	@Test
	public void test_verify_email_async_with_invalid_token_throws_401() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.invalidToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmailAsync("test@example.com");
		});

		assertEquals(401, ex.code);
	}

	/**
	 * Behavior: GET /email-verification/verify-async/{id} returns id, address, status, result, error.
	 */
	@Test
	public void test_get_verify_email_async_status_returns_response_fields() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			AsyncEmailVerificationResponse response = ms.emailVerification().getVerifyEmailAsyncStatus("abc123asyncstatusid");

			assertNotNull(response);
			assertEquals("abc123asyncstatusid", response.id);
			assertEquals("test@example.com", response.address);
			assertEquals("completed", response.status);
			assertEquals("unknown", response.result);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Validation: null id rejected before any HTTP call is made.
	 */
	@Test
	public void test_get_verify_email_async_status_with_null_throws_exception() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().getVerifyEmailAsyncStatus(null);
		});

		assertEquals("Verification ID cannot be null or empty", ex.getMessage());
	}

	/**
	 * Validation: empty id rejected before any HTTP call is made.
	 */
	@Test
	public void test_get_verify_email_async_status_with_empty_string_throws_exception() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().getVerifyEmailAsyncStatus("");
		});

		assertEquals("Verification ID cannot be null or empty", ex.getMessage());
	}

	/**
	 * Failure mode: invalid id results in a 404 exception for getVerifyEmailAsyncStatus.
	 */
	@Test
	public void test_get_verify_email_async_status_with_invalid_id_throws_404() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().getVerifyEmailAsyncStatus("invalid-async-id-404");
		});

		assertEquals(404, ex.code);
	}
}
