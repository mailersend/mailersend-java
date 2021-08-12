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

import com.google.gson.Gson;
import com.mailersend.sdk.exceptions.JsonResponseError;
import com.mailersend.sdk.exceptions.MailerSendResponseError;


public class MailerSendApi {

    private final String endpointBase = "https://api.mailersend.com/v1";
    
    private String apiToken = "";
    
    private HttpClient client;
    
    public MailerSendApi() {
        
        this.client = HttpClient.newHttpClient();
    }
    
    public void setToken(String token) {
        
        this.apiToken = token;
    }
    
    public <T> T postRequest(String endpoint, String requestBody, Class<T> responseClass) throws MailerSendResponseError {
        
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

            // TODO: throw an exception
            
            e.printStackTrace();
        }
        
        Gson gson = new Gson();
        
        if (responseObject != null && responseObject.statusCode() != 200) {
            
            JsonResponseError error = gson.fromJson(stringResponse, JsonResponseError.class);
            
            MailerSendResponseError responseError = new MailerSendResponseError(error.message);
            
            responseError.errors = error.errors;
            responseError.code = responseObject.statusCode();
            
            throw responseError;
        }
        
        
        if (!stringResponse.equals("")) {
            
            response = gson.fromJson(stringResponse, responseClass);
        }
        
        return response;
    }
}
