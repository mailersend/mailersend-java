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
 */
public class MailerSendHttpClientFactory {
	
	private static MailerSendHttpClientFactory instance = null;
	
	private HttpClient client;
	
	public static MailerSendHttpClientFactory getInstance()
	{
		if (instance == null) {
			instance = new MailerSendHttpClientFactory();
		}
		
		return instance;
	}
	
	
	public HttpClient createClient()
	{
		if (client != null) {
			
			return client;
		}
		
		return HttpClient.newHttpClient();
	}
	
	public void setClient(HttpClient client)
	{
		this.client = client;
	}

}
