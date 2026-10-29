/*************************************************
 * MailerSend Java SDK
 * https://github.com/mailersend/mailersend-java
 *
 * @author MailerSend <support@mailersend.com>
 * https://mailersend.com
 **************************************************/
package com.mailersend.sdk.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mailersend.sdk.util.MailerSendHttpClientFactory;
import com.mailersend.sdk.vcr.HttpClientVcr;
import com.mailersend.sdk.vcr.HttpClientVcrResponse;

/**
 * Records the requests the SDK makes and answers each of them with a canned response, for the
 * tests that assert the request method, url and body. A replayed fixture only proves that the
 * request hash matched, so it cannot show which fields were serialized
 */
class CapturingHttpClient extends HttpClientVcr {

    private final List<HttpRequest> requests = new ArrayList<HttpRequest>();

    private final List<String> bodies = new ArrayList<String>();

    private final int statusCode;

    private final String responseBody;

    private final Map<String, List<String>> responseHeaders;


    private CapturingHttpClient(int statusCode, String responseBody, Map<String, List<String>> responseHeaders) {

        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.responseHeaders = responseHeaders;
    }


    /**
     * Replaces the recording client with a capturing one that answers every request with the given response
     */
    static CapturingHttpClient install(int statusCode, String responseBody) {

        return install(statusCode, responseBody, Map.of("content-type", List.of("application/json")));
    }


    /**
     * Replaces the recording client with a capturing one that answers every request with the given response
     */
    static CapturingHttpClient install(int statusCode, String responseBody, Map<String, List<String>> responseHeaders) {

        CapturingHttpClient client = new CapturingHttpClient(statusCode, responseBody, responseHeaders);

        MailerSendHttpClientFactory.getInstance().setClient(client);

        return client;
    }


    /**
     * Parses a JSON string written with single quotes, so that the expected bodies stay readable
     */
    static JsonElement json(String singleQuotedJson) {

        return JsonParser.parseString(singleQuotedJson.replace('\'', '"'));
    }


    HttpRequest lastRequest() {

        assertFalse(requests.isEmpty(), "no request was made");

        return requests.get(requests.size() - 1);
    }


    String lastUrl() {

        return lastRequest().uri().toString();
    }


    JsonElement lastBody() {

        assertFalse(bodies.isEmpty(), "no request was made");

        return JsonParser.parseString(bodies.get(bodies.size() - 1));
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler) {

        requests.add(request);
        bodies.add(request.bodyPublisher().map(CapturingHttpClient::readBody).orElse(null));

        HttpClientVcrResponse response = new HttpClientVcrResponse();
        response.body = responseBody;
        response.headers = responseHeaders;
        response.statusCode = statusCode;

        return (HttpResponse<T>) response;
    }


    private static String readBody(HttpRequest.BodyPublisher publisher) {

        BodySubscriber<String> subscriber = BodySubscribers.ofString(StandardCharsets.UTF_8);

        publisher.subscribe(new Flow.Subscriber<ByteBuffer>() {

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(subscription);
            }

            @Override
            public void onNext(ByteBuffer item) {
                subscriber.onNext(List.of(item));
            }

            @Override
            public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                subscriber.onComplete();
            }
        });

        return subscriber.getBody().toCompletableFuture().join();
    }
}
