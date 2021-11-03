package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.templates.Template;
import com.mailersend.sdk.templates.TemplateItem;
import com.mailersend.sdk.templates.TemplatesList;

public class TemplatesTest {

    
    /**
     * Tests retrieving a list of templates
     */
    @Test
    public void TestGetTemplates() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemplatesList list = ms.templates().getTemplates();
            
            for (TemplateItem item : list.templates) {
                
                System.out.println(item.id);
                System.out.println(item.name);
            }
            
        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }
        
        
    }
    
    
    /**
     * Tests the retrieval of a single template
     */
    @Test
    public void TestGetSingleTemplate() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemplatesList list = ms.templates().getTemplates();
            
            if (list.templates.length == 0) {
                
                fail();
            }
            
            Template template = ms.templates().getTemplate(list.templates[0].id);
            
            System.out.println(template.id);
            System.out.println(template.name);
            System.out.println(template.imagePath);
            
        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }    
    }
    
    
    /**
     * Tests deleting a template
     */
    @Test
    public void TestDeleteTemplate() {
        
        MailerSend ms = new MailerSend();
        ms.setToken(TestHelper.validToken);
        
        try {
            
            TemplatesList list = ms.templates().getTemplates();
            
            if (list.templates.length == 0) {
                
                fail();
            }
            
            MailerSendResponse response = ms.templates().deleteTemplate(list.templates[0].id);
            
            System.out.println(response.responseStatusCode);
            
        } catch (MailerSendException e) {

            e.printStackTrace();
            fail();
        }  
    }
    
}
