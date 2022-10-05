package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.domains.DomainDnsAttribute;
import com.mailersend.sdk.domains.DomainDnsRecords;
import com.mailersend.sdk.domains.DomainVerificationStatus;
import com.mailersend.sdk.domains.DomainsList;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.ApiRecipient;
import com.mailersend.sdk.util.ApiRecipientsList;
import com.mailersend.sdk.vcr.VcrRecorder;

public class DomainsTest {

	@BeforeEach
	public void setupEach(TestInfo info) throws IOException
	{
		VcrRecorder.useRecording("DomainsTest_" + info.getDisplayName());
	}
	
	@AfterEach
	public void afterEach() throws IOException
	{
		VcrRecorder.stopRecording();
	}
    
    /**
     * Tests domains retrieval
     */
    @Test
    public void DomainsListTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            DomainsList list = ms.domains().getDomains();
            
            for (Domain domain : list.domains) {
                
                System.out.println(domain.id);
                System.out.println(domain.name);
            }
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests the retrieval of a single domain
     */
    @Test
    public void SingleDomainTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            Domain domain = ms.domains().getDomain(TestHelper.domainId);
            
            System.out.println(domain.id);
            System.out.println(domain.name);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests the retrival of recipients per domain
     */
    @Test
    public void ReceipientsPerDomainTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            ApiRecipientsList list = ms.domains().getDomainRecipients(TestHelper.domainId);
           
            for (ApiRecipient recipient : list.recipients) {
                
                System.out.println(recipient.email);
            }
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    /**
     * Tests adding a domain
     */
    @Test
    public void AddDomainTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            Domain domain = ms.domains().addDomainBuilder().addDomain(TestHelper.domainToAdd);
            
            System.out.println(domain.id);
            System.out.println(domain.name);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests deleting a domain
     */
    @Test
    public void DeleteDomainTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            boolean domainDeleted = ms.domains().deleteDomain(TestHelper.domainIdToDelete);
            
            System.out.println("Domain deleted: ".contains(String.valueOf(domainDeleted)));
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests the domain dns records retrieval
     */
    @Test
    public void DomainDnsRecordsTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
           DomainDnsRecords records = ms.domains().getDomainDnsRecords(TestHelper.domainId);
           
           printDomainDnsAttribute(records.spf);
           printDomainDnsAttribute(records.dkim);
           printDomainDnsAttribute(records.customTracking);
           printDomainDnsAttribute(records.returnPath);
           printDomainDnsAttribute(records.inboundRouting);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests domain verification
     */
    @Test
    public void VerifyDomainTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
           DomainVerificationStatus status = ms.domains().verifyDomain(TestHelper.domainId);
           
           System.out.println(status.message);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Tests updating a domain's settings
     */
    @Test
    public void UpdateDomainSettingsTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
           Domain domain = ms.domains().updateDomainSettingsBuilder()
                .customnTrackingEnabled(true)
                .sendPaused(false)
                .updateDomain(TestHelper.domainId);
           
           System.out.println(domain.domainSettings.customTrackingEnabled);
           System.out.println(domain.domainSettings.sendPaused);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    /**
     * Simple helper method to print the DomainDnsAttribute properties
     * @param attribute
     */
    private void printDomainDnsAttribute(DomainDnsAttribute attribute) {
        
        System.out.println(attribute.hostname);
        System.out.println(attribute.type);
        System.out.println(attribute.value);
    }
}
