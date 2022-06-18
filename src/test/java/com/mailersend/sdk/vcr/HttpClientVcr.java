/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 * 
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.vcr;

import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.PushPromiseHandler;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

public class HttpClientVcr extends HttpClient {

	private HttpClient client;
	
	private VcrTape tape;
	
	/**
	 * Replacement for the HttpClient class so that we can record and replay the MailerSend API responses
	 */
	public HttpClientVcr()
	{
		// we keep an instance of HttpClient around to use if there isn't a recorded request
		client = HttpClient.newHttpClient();
	}
	
	/**
	 * Sets the recording tape
	 * @param tape
	 */
	public void setTape(VcrTape tape)
	{
		this.tape = tape;
	}
	
	/*
	 * All the Overrides below are needed by the HttpClient abstract class
	 */
	
	@Override
	public Optional<CookieHandler> cookieHandler() {
		
		return this.client.cookieHandler();
	}

	
	@Override
	public Optional<Duration> connectTimeout() {
		
		return this.client.connectTimeout();
	}

	
	@Override
	public Redirect followRedirects() {
		
		return this.client.followRedirects();
	}

	
	@Override
	public Optional<ProxySelector> proxy() {
		
		return this.client.proxy();
	}

	
	@Override
	public SSLContext sslContext() {
		
		return this.client.sslContext();
	}

	
	@Override
	public SSLParameters sslParameters() {
		
		return this.client.sslParameters();
	}

	
	@Override
	public Optional<Authenticator> authenticator() {
		
		return this.client.authenticator();
	}

	
	@Override
	public Version version() {
		
		return this.client.version();
	}

	
	@Override
	public Optional<Executor> executor() {
		
		return this.client.executor();
	}
	
	
    static final class StringSubscriber implements Subscriber<ByteBuffer> {
        final BodySubscriber<String> wrapped;
        StringSubscriber(BodySubscriber<String> wrapped) {
            this.wrapped = wrapped;
        }
        @Override
        public void onSubscribe(Subscription subscription) {
            wrapped.onSubscribe(subscription);
        }
        @Override
        public void onNext(ByteBuffer item) { wrapped.onNext(List.of(item)); }
        @Override
        public void onError(Throwable throwable) { wrapped.onError(throwable); }
        @Override
        public void onComplete() { wrapped.onComplete(); }
    }

    /**
     * Checks the tape for a recorded response of the request.
     * If it doesn't exist, it runs the request and adds the response to the tape
     * @param request
     * @param responseBodyHandler
     */
	@SuppressWarnings("unchecked")
	@Override
	public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler)
			throws IOException, InterruptedException {
			
		if (tape != null) {
			
			HttpClientVcrResponse vcrResponse = tape.getRecordedResponse(request);
			
			if (vcrResponse != null) {
				
				return (HttpResponse<T>) vcrResponse;
			}
		}
		
		HttpResponse<T> response = client.send(request, responseBodyHandler);
			
		this.tape.addRecordedResponse(request,  response);
		
		return response;
	}

	/*
	 * We don't use sendAsync in the MailerSend SDK, we keep these methods for completeness
	 */
	@Override
	public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler) {
		
		return this.client.sendAsync(request, responseBodyHandler);
	}

	
	@Override
	public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler,
			PushPromiseHandler<T> pushPromiseHandler) {
		
		return this.client.sendAsync(request,  responseBodyHandler, pushPromiseHandler);
	}
}
