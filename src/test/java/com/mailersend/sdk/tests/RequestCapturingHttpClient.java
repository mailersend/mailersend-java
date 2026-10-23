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
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import com.mailersend.sdk.util.MailerSendHttpClientFactory;
import com.mailersend.sdk.vcr.HttpClientVcr;
import com.mailersend.sdk.vcr.HttpClientVcrResponse;

/**
 * Records the requests the SDK makes and answers each of them with a canned response, for the
 * tests that assert what the SDK sends instead of replaying a recorded fixture
 */
public class RequestCapturingHttpClient extends HttpClientVcr {

    private final List<HttpRequest> requests = new ArrayList<HttpRequest>();

    private final String responseBody;

    private final int responseStatusCode;


    private RequestCapturingHttpClient(String responseBody, int responseStatusCode) {

        this.responseBody = responseBody;
        this.responseStatusCode = responseStatusCode;
    }


    /**
     * Replaces the recording client with a capturing one that answers with the given response
     */
    public static RequestCapturingHttpClient install(String responseBody, int responseStatusCode) {

        RequestCapturingHttpClient client = new RequestCapturingHttpClient(responseBody, responseStatusCode);

        MailerSendHttpClientFactory.getInstance().setClient(client);

        return client;
    }


    public int requestCount() {

        return requests.size();
    }


    public HttpRequest lastRequest() {

        assertFalse(requests.isEmpty(), "no request was made");

        return requests.get(requests.size() - 1);
    }


    public String lastUri() {

        return lastRequest().uri().toString();
    }


    public String lastMethod() {

        return lastRequest().method();
    }


    /**
     * The body of the last request, or null when it was sent without one
     */
    public String lastBody() {

        return lastRequest().bodyPublisher().map(publisher -> {

            BodySubscriber<String> bodySubscriber = BodySubscribers.ofString(StandardCharsets.UTF_8);
            publisher.subscribe(new StringSubscriber(bodySubscriber));

            return bodySubscriber.getBody().toCompletableFuture().join();
        }).orElse(null);
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler) {

        requests.add(request);

        HttpClientVcrResponse response = new HttpClientVcrResponse();
        response.body = responseBody;
        response.headers = Map.of("content-type", List.of("application/json"));
        response.statusCode = responseStatusCode;

        return (HttpResponse<T>) response;
    }


    private static final class StringSubscriber implements Subscriber<ByteBuffer> {

        private final BodySubscriber<String> wrapped;

        private StringSubscriber(BodySubscriber<String> wrapped) {

            this.wrapped = wrapped;
        }

        @Override
        public void onSubscribe(Subscription subscription) {

            wrapped.onSubscribe(subscription);
        }

        @Override
        public void onNext(ByteBuffer item) {

            wrapped.onNext(List.of(item));
        }

        @Override
        public void onError(Throwable throwable) {

            wrapped.onError(throwable);
        }

        @Override
        public void onComplete() {

            wrapped.onComplete();
        }
    }
}
