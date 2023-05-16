package reactor.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.server.clock.ClockService;

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

    private String slowSynchronousGreeting() throws InterruptedException {
        log.info("Simulating synchronous call...");
        Thread.sleep(5000);
        return "Hello slow World!";
    }
}
