package reactor.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;

public class HelloApiClient {
    private static final Logger log = LoggerFactory.getLogger(HelloApiClient.class);

    public static void main(String[] args) throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8080/api");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono<String> response = client.get()
                .uri("/hello")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToMono(String.class)
                .log()
                .doOnTerminate(countDownLatch::countDown);
        response.subscribe(log::info);
        countDownLatch.await();
    }
}
