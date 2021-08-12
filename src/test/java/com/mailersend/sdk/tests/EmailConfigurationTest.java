/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mailersend.sdk.*;
import com.mailersend.sdk.email.attributes.Personalization;

public class EmailConfigurationTest {
        
    /**
     * Tests the from name and email
     */
    @Test
    public void TestEmailFromConfiguration() {
        
        Email email = TestHelper.createBasicEmail(true);
        
        email.setFrom(TestHelper.fromName, TestHelper.emailFrom);

        assertEquals(email.from.name, TestHelper.fromName);
        assertEquals(email.from.email, TestHelper.emailFrom);
    }
    
    
    /**
     * Tests that a single recipient can be added
     */
    @Test
    public void TestSingleRecipientConfiguration() {
        
        Email email = TestHelper.createBasicEmail(true);
        
        assertEquals(email.recipients.size(), 1);
        
        assertEquals(email.recipients.get(0).name, TestHelper.toName);
        
        assertEquals(email.recipients.get(0).email, TestHelper.toEmail);
    }
    
    
    /**
     * Tests that multiple recipients can be added
     */
    @Test
    public void TestMultipleRecipientsConfiguration() {
        
        String secondRecipientName = "Test Recipient 2";
        String secondRecipientEmail = "test@recipient2.com";
        
        Email email = TestHelper.createBasicEmail(true);
        
        email.addRecipient(secondRecipientName, secondRecipientEmail);
        
        assertEquals(email.recipients.size(), 2);
        
        assertEquals(email.recipients.get(1).name, secondRecipientName);
        assertEquals(email.recipients.get(1).email, secondRecipientEmail);
    }
    
    
    /**
     * Tests that the subject, and html and plain bodies are added to the email
     */
    @Test
    public void TestEmailContentsConfiguration() {
        
        Email email = TestHelper.createBasicEmail(true);
        
        assertEquals(email.subject, TestHelper.subject);
        assertEquals(email.html, TestHelper.html);
        assertEquals(email.text, TestHelper.text);
    }
    
    
    /**
     * Tests the email personalization
     */
    @Test
    public void TestEmailPersonalization() {
        
        String personalizationName = "test_personalization";
        String personalizationValue = "test_personalization_value";
        
        Email email = new Email();
        
        Recipient recipient = new Recipient(TestHelper.toName, TestHelper.toEmail);
        
        email.addPersonalization(recipient, personalizationName, personalizationValue);
        
        assertEquals(email.personalization.size(), 1);
        
        Personalization p = email.personalization.get(0);
        
        assertEquals(p.email, recipient.email);
        
        assertEquals(p.data.size(), 1);
        
        assertEquals(p.data.get(personalizationName), personalizationValue);
        
        
        // add another personalization and test it too
        String secondPName = "test_personalization_2";
        String secondPValue = "test_personalization_value_2";
        
        email.addPersonalization(recipient, secondPName, secondPValue);
        
        assertEquals(email.personalization.size(), 1);
        
        Personalization p2 = email.personalization.get(0);
        
        assertEquals(p2.email, recipient.email);
        
        assertEquals(p2.data.size(), 2);
        
        assertEquals(p2.data.get(secondPName), secondPValue);
    }
    
    
    /**
     * Tests that different recipients can have different personalization
     */
    @Test
    public void TestMultipleRecipientsPersonalization() {
        
        String personalizationName = "test_personalization";
        String personalizationValue = "test_personalization_value";
        
        String secondPName = "test_personalization_2";
        String secondPValue = "test_personalization_value_2";
        
        String recipient2Name = "Test recipient 2 name";
        String recipient2Email = "Test recipient 2 email";
        
        Email email = new Email();
        
        Recipient firstRecipient = new Recipient(TestHelper.toName, TestHelper.toEmail);
        email.AddRecipient(firstRecipient);
        
        Recipient secondRecipient = new Recipient(recipient2Name, recipient2Email);
        email.AddRecipient(secondRecipient);
        
        email.addPersonalization(firstRecipient, personalizationName, personalizationValue);
        email.addPersonalization(secondRecipient, secondPName, secondPValue);
        
        assertEquals(email.personalization.size(), 2);
        
        Personalization p1 = email.personalization.get(0);
        
        assertEquals(p1.email, firstRecipient.email);
        assertEquals(p1.data.size(), 1);
        assertEquals(p1.data.get(personalizationName), personalizationValue);
        
        Personalization p2 = email.personalization.get(1);
        
        assertEquals(p2.email, secondRecipient.email);
        assertEquals(p2.data.size(), 1);
        assertEquals(p2.data.get(secondPName), secondPValue);
    }
}
