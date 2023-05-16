package reactor.client;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.server.clock.ClockEvent;

import java.util.concurrent.CountDownLatch;

public class ClockApiClient {
    private static final Logger log = LoggerFactory.getLogger(ClockApiClient.class);

    private static final int MAX_COUNT = 10;

    static class ClockEventSubscriber extends BaseSubscriber<ClockEvent> {
        private int count;

        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            log.info("subscribed");
            request(1);
        }

        @Override
        protected void hookOnNext(ClockEvent message) {
            log.info("received: " + message);
            count++;
            if (count < MAX_COUNT) {
                request(1);
            } else {
                log.info("cancelling...");
                cancel();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8080/api");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<ClockEvent> response = client.get()
                .uri("/clock", 10)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(ClockEvent.class)
                .log()
                .doOnCancel(countDownLatch::countDown);
        response.subscribe(new ClockEventSubscriber());
        countDownLatch.await();
    }
}
