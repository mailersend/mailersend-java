/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.Gson;
import com.mailersend.sdk.exceptions.JsonResponseError;
import com.mailersend.sdk.exceptions.MailerSendException;


/**
 * Handles all requests to the MailerSend API
 */
public class MailerSendApi {

    private final String endpointBase = "https://api.mailersend.com/v1";
    
    private String apiToken = "";
    
    private HttpClient client;
    
    
    /**
     * Constructor, initializes the HttpClient
     */
    public MailerSendApi() {
        
        this.client = HttpClient.newHttpClient();
    }
    
    
    /**
     * Sets the MailerSend API token
     * @param token
     */
    public void setToken(String token) {
        
        this.apiToken = token;
    }
    
    
    /**
     * Does a POST request to the given endpoint of the MailerSend API
     * @param <T> The type of that the response will be deserialized to
     * @param endpoint The MailerSend API endpoint
     * @param requestBody The body of the POST request
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerSendException
     */
    public <T extends MailerSendResponse> T postRequest(String endpoint, String requestBody, Class<T> responseClass) throws MailerSendException {
        
        String stringResponse = "";
       
        T response = null;
        
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "applicateion/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .POST(BodyPublishers.ofString(requestBody))
                .build();
        
        HttpResponse<String> responseObject = null;
        
        try {
            
            responseObject = this.client.send(request, BodyHandlers.ofString());
            
            stringResponse = responseObject.body().toString();
            
        } catch (IOException | InterruptedException e) {

            MailerSendException ex = (MailerSendException) e;
            
            throw ex;
        }
        
        Gson gson = new Gson();
                
        if (responseObject != null && responseObject.statusCode() != 200 && responseObject.statusCode() != 202) {
            
            JsonResponseError error = gson.fromJson(stringResponse, JsonResponseError.class);
            
            MailerSendException responseError = new MailerSendException(error.message);            
            
            responseError.errors = error.errors;
            responseError.code = responseObject.statusCode();
            
            throw responseError;
        }
        
        
        if (!stringResponse.equals("")) {
            
            response = gson.fromJson(stringResponse, responseClass);
        } else {
            
            try {
                
                response = responseClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                
                MailerSendException ex = (MailerSendException) e;
                
                throw ex;
            }
        }
        
        // get the response headers
        response.messageId = responseObject.headers().firstValue("x-message-id").get();
        
        try {
            
            response.rateLimit = Integer.parseInt(responseObject.headers().firstValue("x-ratelimit-limit").get());
        } catch (NumberFormatException e) {
            
            // left empty on purpose
        }
        
        try {
            
            response.rateLimitRemaining = Integer.parseInt(responseObject.headers().firstValue("x-ratelimit-remaining").get());
        } catch (NumberFormatException e) {
            
            // left empty on purpose
        }
        
        return response;
    }
}
