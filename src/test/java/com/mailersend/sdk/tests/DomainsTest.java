package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.domains.Domain;
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
            
            DomainsList list = ms.domains.getDomains();
            
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
            
            Domain domain = ms.domains.getDomain(TestHelper.domainId);
            
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
            
            Domain domain = ms.domains.addDomainBuilder.addDomain(TestHelper.domainToAdd);
            
            System.out.println(domain.id);
            System.out.println(domain.name);
            
        } catch (MailerSendException e) {
            
            e.printStackTrace();
            fail();
        }
    }
}
