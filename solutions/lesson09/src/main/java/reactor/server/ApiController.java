package reactor.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.server.clock.ClockEvent;
import reactor.server.clock.ClockEventListener;
import reactor.server.clock.ClockService;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ClockService clockService;

    @GetMapping("/hello")
    public Mono<String> hello() {
        log.info("requested: /hello");
        return Mono.just("Hello World!");
    }

    @GetMapping("/slow")
    public Mono<String> slowHello() {
        log.info("requested: /slow");
        Mono<String> blockingWrapper = Mono.fromCallable(this::slowSynchronousGreeting)
                .subscribeOn(Schedulers.boundedElastic());
        return blockingWrapper;
    }

    private String slowSynchronousGreeting() throws InterruptedException {
        log.info("Simulating synchronous call...");
        Thread.sleep(5000);
        return "Hello slow World!";
    }

    @GetMapping("/countdown/{start}")
    public Flux<Integer> countdown(@PathVariable int start) {
        log.info("requested: /countdown/" + start);
        Flux<Integer> range = Flux.range(0, start + 1);
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<Tuple2<Integer, Long>> combined = range.zipWith(interval);
        Flux<Integer> countdown = combined.map(tuple -> start - tuple.getT1()).log();
        return countdown;
    }

    @GetMapping("/clock")
    public Flux<ClockEvent> clock() {
        log.info("requested: /clock");
        Flux<ClockEvent> clockFlux = Flux.create(sink -> {
            clockService.addListener(new ClockEventListener() {
                @Override
                public void handleClockEvent(ClockEvent event) {
                    if (sink.isCancelled()) {
                        clockService.removeListener(this);
                        log.info("cancelled");
                    } else {
                        sink.next(event);
                    }
                }
            });
        });
        return clockFlux.log();
    }
}
