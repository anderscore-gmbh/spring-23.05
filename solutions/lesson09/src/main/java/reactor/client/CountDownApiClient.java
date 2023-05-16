package reactor.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

public class CountDownApiClient {
    private static final Logger log = LoggerFactory.getLogger(CountDownApiClient.class);

    public static void main(String[] args) throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8080/api");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<Integer> response = client.get()
                .uri("/countdown/{start}", 10)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Integer.class)
                .log()
                .doOnComplete(countDownLatch::countDown);
        response.subscribe(count -> log.info("received: " + count));
        countDownLatch.await();
    }
}
