/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.vcr;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mailersend.sdk.util.JsonSerializationDeserializationStrategy;
import com.mailersend.sdk.vcr.HttpClientVcr.StringSubscriber;

public class VcrTape {

	private Path tapePath;
	
	private HashMap<String, HttpClientVcrResponse> recordedResponses = new HashMap<String, HttpClientVcrResponse>();
	
	private boolean hasNewRecordings = false;
	
	/**
	 * Searches the recorded responses for a response matching the hash of the given request
	 * @param request
	 * @return
	 */
	public HttpClientVcrResponse getRecordedResponse(HttpRequest request)
	{
		String requestHash = this.getRequestHash(request);
		
		if (this.recordedResponses.containsKey(requestHash)) {
			
			return this.recordedResponses.get(requestHash);
		}
		
		return null;
	}
	
	/**
	 * Adds a response to the tape.
	 * Each response is identified by its request hash. See getRequestHash for how the request hash is generated.
	 * @param <T>
	 * @param request
	 * @param response
	 */
	public <T> void addRecordedResponse(HttpRequest request, HttpResponse<T> response)
	{
		Map<String, List<String>> headers = response.headers().map();
		
		HttpClientVcrResponse resp = new HttpClientVcrResponse();
		resp.body = response.body().toString();
		resp.headers = headers;
		resp.statusCode = response.statusCode();
		
		String requestHash = this.getRequestHash(request);
		
		this.recordedResponses.put(requestHash, resp);
		this.hasNewRecordings = true;
	}
	
	/**
	 * Loads the tape in the given path and deserializes the tape contents into the recorded responses hashmap
	 * @param path
	 * @throws IOException
	 */
	public void loadTape(String path) throws IOException {
		
		this.tapePath = Path.of(path);
		
		if (Files.exists(this.tapePath)) {
			String fixture = Files.readString(this.tapePath);
	        Gson gson = new GsonBuilder()
	                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
	                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
	                .create();
	        
	        Type recordingsType = new TypeToken<HashMap<String, HttpClientVcrResponse>>(){}.getType();
	        
	        this.recordedResponses = gson.fromJson(fixture, recordingsType);
	        
	        if (this.recordedResponses == null) {
	        	this.recordedResponses = new HashMap<String, HttpClientVcrResponse>();
	        }
		}
	}
	
	/**
	 * Serializes and saves the recorded responses into the tape file 
	 * @throws IOException
	 */
	public void saveTape() throws IOException
	{
		if (hasNewRecordings) {
			
	        Gson gson = new GsonBuilder()
	                .addSerializationExclusionStrategy(new JsonSerializationDeserializationStrategy(false))
	                .addDeserializationExclusionStrategy(new JsonSerializationDeserializationStrategy(true))
	                .create();
	        String fixture = gson.toJson(this.recordedResponses);
	        
	        BufferedWriter writer = new BufferedWriter(new FileWriter(this.tapePath.toString()));
	        writer.write(fixture);
	        writer.close();
		}
	}
	
	/**
	 * Calculates the hash of a request according to the formula sha1({request url}{request method}{body contents})
	 * @param request
	 * @return
	 */
	private String getRequestHash(HttpRequest request)
	{
		
		String url = request.uri().toString();
		String method = request.method();
		
		Optional<String> body = request.bodyPublisher().map(p -> {
			var bodySubscriber = BodySubscribers.ofString(StandardCharsets.UTF_8);
			var flowSubscriber = new StringSubscriber(bodySubscriber);
			p.subscribe(flowSubscriber);
			return bodySubscriber.getBody().toCompletableFuture().join();
		});
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(url);
		stringBuilder.append(method);
		stringBuilder.append(body);
		
		return this.sha1Hash(stringBuilder.toString());
	}
	
	/**
	 * Returns the sha1 hash of the given string
	 * @param value
	 * @return
	 */
	private String sha1Hash(String value)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(value.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}
	
	/**
	 * Converts a byte array to a hex string
	 * @param hash
	 * @return
	 */
	private String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}
