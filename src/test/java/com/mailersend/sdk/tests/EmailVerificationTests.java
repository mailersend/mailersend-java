package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailersend.sdk.emailverification.AsyncEmailVerificationResponse;
import com.mailersend.sdk.emailverification.EmailVerification;
import com.mailersend.sdk.emailverification.EmailVerificationBuilder;
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

	/**
	 * Behavior: POST /email-verification with name and emails; the response dates are parsed.
	 */
	@Test
	public void createVerificationListTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	EmailVerificationList list = ms.emailVerification().builder()
			.name("Test email verification")
			.addEmail("info@example.com")
			.addEmail("info1@example.com")
			.addEmail("info2@example.com")
			.create();

        	assertEquals("Test email verification", list.name);
        	assertEquals("2351ndgwvrlzqx8k", list.id);
        	assertNotNull(list.createdAt);
        	assertNotNull(list.updatedAt);

		} catch (MailerSendException e) {

			fail();
		}
	}

	/**
	 * Behavior: POST /email-verification with list_id, emails added via addEmails() and verify.
	 */
	@Test
	public void testCreateVerificationListWithListIdAndVerify() {
		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			EmailVerificationList list = ms.emailVerification().builder()
				.listId(TestHelper.emailVerificationListId)
				.addEmails(new String[] {"info3@example.com", "info4@example.com"})
				.verify(true)
				.create();

			assertEquals(TestHelper.emailVerificationListId, list.id);
			assertEquals("verifying", list.status.name);
			assertNotNull(list.verificationStarted);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Behavior: the shared builder sends only the emails added since the last create().
	 * The fixture only matches a second body of {"name":"Second list","emails":["info3@example.com"]}.
	 */
	@Test
	public void testBuilderResetsEmailsAfterCreate() {
		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			EmailVerificationBuilder builder = ms.emailVerification().builder();

			builder.name("Test email verification")
				.addEmail("info@example.com")
				.addEmail("info1@example.com")
				.addEmail("info2@example.com")
				.create();

			EmailVerificationList second = builder.name("Second list")
				.addEmail("info3@example.com")
				.create();

			assertEquals("Second list", second.name);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void verifyListTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	EmailVerificationList list = ms.emailVerification().verifyList(TestHelper.emailVerificationListId);

        	assertEquals(TestHelper.emailVerificationListId, list.id);
        	assertEquals("verifying", list.status.name);

		} catch (MailerSendException e) {

			fail();
		}
	}

	/**
	 * Behavior: GET /email-verification/{id} with no query string by default.
	 */
	@Test
	public void getListTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {

        	EmailVerificationList list = ms.emailVerification().getList(TestHelper.emailVerificationListId);

        	assertEquals(TestHelper.emailVerificationListId, list.id);
        	assertNull(list.emails);

		} catch (MailerSendException e) {

			fail();
		}
	}

	/**
	 * Behavior: GET /email-verification/{id}?detailed=true&page=2&limit=10 returns the paginated emails
	 * and the not_verified statistic.
	 */
	@Test
	public void testGetListDetailed() {
		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			EmailVerificationList list = ms.emailVerification()
				.detailed(true)
				.getListPage(2)
				.getListLimit(10)
				.getList(TestHelper.emailVerificationListId);

			assertEquals(1, list.statistics.notVerified);
			assertEquals(2, list.emails.results.length);
			assertEquals("info@example.com", list.emails.results[0].address);
			assertEquals("valid", list.emails.results[0].result);
			assertEquals(2, list.emails.pagination.currentPage);
			assertEquals(10, list.emails.pagination.perPage);
			assertEquals(12, list.emails.pagination.total);
			assertNull(list.emails.links.next);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Behavior: detailed(false) sends ?detailed=false and leaves out page and limit.
	 * The fixture only matches /email-verification/{id}?detailed=false.
	 */
	@Test
	public void testGetListNotDetailed() {
		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			EmailVerificationList list = ms.emailVerification()
				.detailed(false)
				.getListPage(2)
				.getListLimit(10)
				.getList(TestHelper.emailVerificationListId);

			assertEquals(TestHelper.emailVerificationListId, list.id);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Behavior: detailed, page and limit are reset after getList(), so the second call has no query string.
	 */
	@Test
	public void testGetListResetsFiltersAfterCall() {
		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			EmailVerification emailVerification = ms.emailVerification();

			emailVerification.detailed(true).getList(TestHelper.emailVerificationListId);

			EmailVerificationList second = emailVerification.getList(TestHelper.emailVerificationListId);

			assertNull(second.emails);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}


	/**
	 * Behavior: GET /email-verification/{id}/results?page=1&limit=25 by default.
	 */
	@Test
	public void getListResultsTest() {
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);

        try {
			ListVerificationResults results = ms.emailVerification().verificationResults(TestHelper.emailVerificationListId);

			assertEquals(3, results.results.length);
		} catch (MailerSendException e) {

			fail();
		}
	}

	/**
	 * Behavior: GET /email-verification/{id}/results?page=2&limit=10&results[]=valid&results[]=typo.
	 */
	@Test
	public void testGetListResultsWithFilters() {
		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {
			ListVerificationResults results = ms.emailVerification()
				.resultsPage(2)
				.resultsLimit(10)
				.results(new String[] {"valid", "typo"})
				.verificationResults(TestHelper.emailVerificationListId);

			assertEquals(2, results.results.length);
			assertEquals("typo", results.results[1].result);
			assertEquals(2, results.meta.currentPage);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Behavior: page, limit and results are reset after verificationResults(), so the second call
	 * uses page=1&limit=25 and no results[] filter.
	 */
	@Test
	public void testGetListResultsResetsFiltersAfterCall() {
		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {
			EmailVerification emailVerification = ms.emailVerification();

			emailVerification.resultsPage(2)
				.resultsLimit(10)
				.results(new String[] {"valid"})
				.verificationResults(TestHelper.emailVerificationListId);

			ListVerificationResults second = emailVerification.verificationResults(TestHelper.emailVerificationListId);

			assertEquals(3, second.results.length);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}


	@Test
	public void getListsTest() {
       MailerSend ms = new MailerSend();
       ms.setToken(TestHelper.validToken);

       try {
		EmailVerificationLists lists = ms.emailVerification().getLists();

		assertEquals("api", lists.lists[0].source);

	} catch (MailerSendException e) {

		fail();
	}
	}

	/**
	 * Behavior: GET /email-verification?page=2&limit=10.
	 */
	@Test
	public void testGetListsWithPageAndLimit() {
		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {
			EmailVerificationLists lists = ms.emailVerification().page(2).limit(10).getLists();

			assertEquals(2, lists.meta.currentPage);
			assertEquals(TestHelper.emailVerificationListId, lists.lists[0].id);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Behavior: POST /email-verification/verify returns the status field.
	 */
	@Test
	public void testVerifyEmailReturnsStatus() {

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
	 * Validation: a null or empty email is rejected before any HTTP call is made.
	 */
	@ParameterizedTest(name = "{0}")
	@MethodSource("missingEmails")
	public void testVerifyEmailRejectsMissingEmail(String label, String email) {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmail(email);
		});

		assertEquals("Email cannot be null or empty", ex.getMessage());
	}

	static Stream<Arguments> missingEmails() {
		return Stream.of(
			Arguments.of("null email", null),
			Arguments.of("empty email", "")
		);
	}

	/**
	 * Failure mode: invalid token results in a 401 exception for verifyEmail.
	 */
	@Test
	public void testVerifyEmailWithInvalidTokenThrows401() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.invalidToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmail("test@example.com");
		});

		assertEquals(401, ex.code);
	}

	/**
	 * Behavior: POST /email-verification/verify-async returns 201 and the fields inside data.
	 */
	@Test
	public void testVerifyEmailAsyncReturnsResponseFields() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		try {

			AsyncEmailVerificationResponse response = ms.emailVerification().verifyEmailAsync("test@example.com");

			assertEquals(201, response.responseStatusCode);
			assertEquals("67c83bf24a5d02568029ee10", response.id);
			assertEquals("test@example.com", response.address);
			assertEquals("queued", response.status);
			assertNull(response.result);
			assertNull(response.error);

		} catch (MailerSendException e) {

			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Validation: a null or empty email is rejected before any HTTP call is made for async verify.
	 */
	@ParameterizedTest(name = "{0}")
	@MethodSource("missingEmails")
	public void testVerifyEmailAsyncRejectsMissingEmail(String label, String email) {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmailAsync(email);
		});

		assertEquals("Email cannot be null or empty", ex.getMessage());
	}

	/**
	 * Failure mode: invalid token results in a 401 exception for verifyEmailAsync.
	 */
	@Test
	public void testVerifyEmailAsyncWithInvalidTokenThrows401() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.invalidToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().verifyEmailAsync("test@example.com");
		});

		assertEquals(401, ex.code);
	}

	/**
	 * Behavior: GET /email-verification/verify-async/{id} returns the fields inside data.
	 */
	@Test
	public void testGetVerifyEmailAsyncStatusReturnsResponseFields() {

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
	 * Validation: a null or empty id is rejected before any HTTP call is made.
	 */
	@ParameterizedTest(name = "{0}")
	@MethodSource("missingVerificationIds")
	public void testGetVerifyEmailAsyncStatusRejectsMissingId(String label, String id) {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().getVerifyEmailAsyncStatus(id);
		});

		assertEquals("Verification ID cannot be null or empty", ex.getMessage());
	}

	static Stream<Arguments> missingVerificationIds() {
		return Stream.of(
			Arguments.of("null id", null),
			Arguments.of("empty id", "")
		);
	}

	/**
	 * Failure mode: invalid id results in a 404 exception for getVerifyEmailAsyncStatus.
	 */
	@Test
	public void testGetVerifyEmailAsyncStatusWithInvalidIdThrows404() {

		MailerSend ms = new MailerSend();
		ms.setToken(TestHelper.validToken);

		MailerSendException ex = assertThrows(MailerSendException.class, () -> {
			ms.emailVerification().getVerifyEmailAsyncStatus("invalid-async-id-404");
		});

		assertEquals(404, ex.code);
	}
}
