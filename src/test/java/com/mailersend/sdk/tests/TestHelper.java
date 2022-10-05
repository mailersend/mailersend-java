/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import com.mailersend.sdk.emails.Email;

public class TestHelper {

    public static final String emailFrom = "sender@test-sdk.com";
    public static final String fromName = "From Name";
    
    public static final String toEmail = "java@mailersend.com";
    public static final String toName = "To Name";
    
    public static final String ccEmail = "cc@test-sdk.com";
    public static final String ccName = "CC Email";
    
    public static final String bccEmail = "bcc@test-sdk.com";
    public static final String bccName = "BCC Email";
    
    public static final String subject = "Subject";
    public static final String text = "This is the text content";
    public static final String html = "<p>This is the HTML <strong>content</strong></p>";
    
    public static final String templateId = "neqvygm021wl0p7w";
    
    public static final String invalidToken = "invalid_mailersend_token";
    public static final String validToken = "valid_mailersend_token";
    
    public static final String domainId = "jpzkmgq7e5vl059v";
    
    public static final String domainIdToDelete = "o65qngk21mogwr12";
    
    public static final String domainToAdd = "domain-to-add.com";
    
    public static final String tokenIdToPause = "865133ae500de599223a523d764877d7d3a4581ab24661adb044e787692cd6a5503e55fb8c700a03";
    
    public static final String tokenIdToDelete = "865133ae500de599223a523d764877d7d3a4581ab24661adb044e787692cd6a5503e55fb8c700a03";
    
    public static final String inboundRouteId = "83yxj6ljoq4do2rm";
    
    public static final String emailVerificationListId = "7z3m5jgroogdpyo6";
    
    public static final String smsPhoneNumberId = "3yxj6lj9x14do2rm";
    
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
