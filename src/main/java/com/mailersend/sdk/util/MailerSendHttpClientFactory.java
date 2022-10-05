/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.util;

import java.net.http.HttpClient;

/**
 * We use a factory for the HttpClient that the SDk uses so that we can switch clients when needed
 * e.g. when running tests we use the HttpClientVcr to record and replay the API responses
 *
 * @author mailersend
 * @version $Id: $Id
 */
public class MailerSendHttpClientFactory {
	
	private static MailerSendHttpClientFactory instance = null;
	
	private HttpClient client;
	
	/**
	 * <p>Getter for the field <code>instance</code>.</p>
	 *
	 * @return a {@link com.mailersend.sdk.util.MailerSendHttpClientFactory} object.
	 */
	public static MailerSendHttpClientFactory getInstance()
	{
		if (instance == null) {
			instance = new MailerSendHttpClientFactory();
		}
		
		return instance;
	}
	
	
	/**
	 * <p>createClient.</p>
	 *
	 * @return a HttpClient object.
	 */
	public HttpClient createClient()
	{
		if (client != null) {
			
			return client;
		}
		
		return HttpClient.newHttpClient();
	}
	
	/**
	 * <p>Setter for the field <code>client</code>.</p>
	 *
	 * @param client a HttpClient object.
	 */
	public void setClient(HttpClient client)
	{
		this.client = client;
	}

}
