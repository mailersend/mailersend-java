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
import com.google.gson.GsonBuilder;
import com.mailersend.sdk.exceptions.JsonResponseError;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;


/**
 * Handles all requests to the MailerSend API
 */
public class MailerSendApi {

    private final String endpointBase = "https://api.mailersend.com/v1";
    
    private String apiToken = "";
    
    private HttpClient client;
    
    private MailerSendResponse lastRequestResponse;
    
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
     * Does a GET request to the given endpoint of the MailerSend API
     * @param <T> The type of what the response will be deserialized to
     * @param endpoint The MailerSend API endpoint
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerSendException
     */
    public <T extends MailerSendResponse> T getRequest(String endpoint, Class<T> responseClass) throws MailerSendException {
        
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "applicateion/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .GET()
                .build();
        
        HttpResponse<String> responseObject = null;
        
        try {
            
            responseObject = this.client.send(request, BodyHandlers.ofString());
                        
        } catch (IOException | InterruptedException e) {

            MailerSendException ex = (MailerSendException) e;
            
            throw ex;
        }
        
        return this.handleApiResponse(responseObject, responseClass);
    }
    
    
    /**
     * Does a POST request to the given endpoint of the MailerSend API
     * @param <T> The type of what the response will be deserialized to
     * @param endpoint The MailerSend API endpoint
     * @param requestBody The body of the POST request
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerSendException
     */
    public <T extends MailerSendResponse> T postRequest(String endpoint, String requestBody, Class<T> responseClass) throws MailerSendException {
       
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "applicateion/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .POST(BodyPublishers.ofString(requestBody))
                .build();
        
        HttpResponse<String> responseObject = null;
        
        try {
            
            responseObject = this.client.send(request, BodyHandlers.ofString());
                        
        } catch (IOException | InterruptedException e) {

            MailerSendException ex = (MailerSendException) e;
            
            throw ex;
        }
        
        return this.handleApiResponse(responseObject, responseClass);
    }
    
    
    /**
     * Returns the status of the last request made to the API.
     * @return
     */
    public MailerSendResponse getLastRequestStatus() {
        
        return this.lastRequestResponse;
    }
    
    
    /**
     * Handles the response from the MailerSend API. It deserializes the JSON response into an object with the given type
     * @param <T> The type of what the response will be deserialized to
     * @param responseObject The HttpResponse object of the request
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerSendException
     */
    private <T extends MailerSendResponse> T handleApiResponse(HttpResponse<String> responseObject, Class<T> responseClass) throws MailerSendException {
        
        String stringResponse = "";
        
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
                .create();
        
        if (responseObject != null && responseObject.statusCode() != 200 && responseObject.statusCode() != 202) {
            
            stringResponse = responseObject.body().toString();
            
            JsonResponseError error = gson.fromJson(stringResponse, JsonResponseError.class);
            
            MailerSendException responseError = new MailerSendException(error.message);            
            
            responseError.errors = error.errors;
            responseError.code = responseObject.statusCode();
            
            throw responseError;
        }
        
        T response = null;
        
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
