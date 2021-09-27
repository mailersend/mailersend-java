package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
import com.mailersend.sdk.domains.DomainDnsAttribute;
import com.mailersend.sdk.domains.DomainDnsRecords;
import com.mailersend.sdk.domains.DomainsList;
import com.mailersend.sdk.exceptions.MailerSendException;

public class DomainsTest {

    
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
     * Tests adding a domain
     */
    @Test
    public void AddDomainTest() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            Domain domain = ms.domains().addDomainBuilder.addDomain(TestHelper.domainToAdd);
            
            System.out.println(domain.id);
            System.out.println(domain.name);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
    
    
    @Test
    public void DomainDnsTest() {
        
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
    
    private void printDomainDnsAttribute(DomainDnsAttribute attribute) {
        
        System.out.println(attribute.hostname);
        System.out.println(attribute.type);
        System.out.println(attribute.value);
    }
}
