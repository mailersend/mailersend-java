/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import com.mailersend.sdk.Email;

public class TestHelper {

    public static final String emailFrom = "from@email.com";
    public static final String fromName = "From Name";
    
    public static final String toEmail = "to@email.com";
    public static final String toName = "To Name";
    
    public static final String ccEmail = "test@ccemail.com";
    public static final String ccName = "CC Email";
    
    public static final String bccEmail = "test@bccemail.com";
    public static final String bccName = "BCC Email";
    
    public static final String subject = "Subject";
    public static final String text = "This is the text content";
    public static final String html = "<p>This is the HTML <strong>content</strong></p>";
    
    public static final String templateId = "a MailerSend template id";
    
    public static final String invalidToken = "an invalid token";
    public static final String validToken = "your MailerSend API token";
    
    public static final String domainId = "A valid domain id for your account";
    
    public static final String domainIdToDelete = "a domain id that will be deleted";
    
    public static final String domainToAdd = "a valid and resolvable domain to add via the sdk";
    
    public static final String tokenIdToPause = "id of token to pause";
    
    public static final String tokenIdToDelete = "id of token to delete";
    
    /**
     * Creates a basic email with the above configuration
     * @return
     */
    public static Email createBasicEmail(boolean withContent) {
        
        Email email = new Email();
        
        email.setFrom(fromName, emailFrom);
        email.addRecipient(toName, toEmail);
        
        email.setSubject(subject);
        
        if (withContent) {
            
            email.setHtml(html);
            email.setPlain(text);
        } else {
        
            email.setTemplateId(templateId);
        }
        
        return email;
    }
}
