/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.exceptions;

import java.util.HashMap;

public class JsonResponseError {
    
    public String message = "";
    public HashMap<String, String[]> errors = new HashMap<String, String[]>();
}
