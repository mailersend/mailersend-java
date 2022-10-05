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
import com.mailersend.sdk.vcr.VcrRecorder;
import com.mailsend.sdk.emailverification.EmailVerificationList;
import com.mailsend.sdk.emailverification.EmailVerificationLists;
import com.mailsend.sdk.emailverification.ListVerificationResults;

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
}
