/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;

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
     * Does a DELETE request to the given endpoint of the MailerSend API
     * @param <T> The type of what the response will be deserialized to
     * @param endpoint The MailerSend API endpoint
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerSendException
     */
    public <T extends MailerSendResponse> T deleteRequest(String endpoint, Class<T> responseClass) throws MailerSendException {
        
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "applicateion/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .DELETE()
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
     * Does a DELETE request to the given endpoint of the MailerSend API
     * @param <T> The type of what the response will be deserialized to
     * @param endpoint The MailerSend API endpoint
     * @param requestBody The body of the DELETE request
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerSendException
     */
    public <T extends MailerSendResponse> T deleteRequest(String endpoint, String requestBody, Class<T> responseClass) throws MailerSendException {
        
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "applicateion/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .method("DELETE", BodyPublishers.ofString(requestBody))
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
     * Does a PUT request to the given endpoint of the MailerSend API
     * @param <T> The type of what the response will be deserialized to
     * @param endpoint The MailerSend API endpoint
     * @param requestBody The body of the PUT request
     * @param responseClass The class of the response object
     * @return T
     * @throws MailerSendException
     */
    public <T extends MailerSendResponse> T putRequest(String endpoint, String requestBody, Class<T> responseClass) throws MailerSendException {
       
        HttpRequest request = HttpRequest.newBuilder(URI.create(this.endpointBase.concat(endpoint)))
                .header("Content-type", "applicateion/json")
                .header("Authorization", "Bearer ".concat(this.apiToken))
                .PUT(BodyPublishers.ofString(requestBody))
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
        
        int[] successCodes = {200, 201, 202, 204};
        
        boolean isSuccess = false;
        
        for (int code : successCodes) {
            
            if (code == responseObject.statusCode()) {
                
                isSuccess = true;
                break;
            }
        }
        
        if (responseObject != null && !isSuccess) {
            
            stringResponse = responseObject.body().toString();
            
            JsonResponseError error = gson.fromJson(stringResponse, JsonResponseError.class);
            
            MailerSendException responseError = new MailerSendException(error.message);            
            
            responseError.errors = error.errors;
            responseError.code = responseObject.statusCode();
            
            throw responseError;
        }
        
        stringResponse = responseObject.body().toString();
        
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
        
        try {
            // only email sends will have a message id header
            response.messageId = responseObject.headers().firstValue("x-message-id").get();
        } catch (Exception e) {
            
            // left empty on purpose
        }
        
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
        
        response.responseStatusCode = responseObject.statusCode();
        
        return response;
    }
}
