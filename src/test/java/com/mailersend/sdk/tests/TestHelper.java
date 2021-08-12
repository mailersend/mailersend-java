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

    public static final String emailFrom = "your@email.com";
    public static final String fromName = "Your Name";
    
    public static final String toEmail = "your@client.com";
    public static final String toName = "Your Client";
    
    public static final String subject = "Subject";
    public static final String text = "This is the text content";
    public static final String html = "<p>This is the HTML <strong>content</strong></p>";
    
    public static final String templateId = "123";
    
    public static final String invalidToken = "an invalid token";
    public static final String validToken = "...";
    
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
        }
        
        email.setTemplateId(templateId);
        
        return email;
    }
}
