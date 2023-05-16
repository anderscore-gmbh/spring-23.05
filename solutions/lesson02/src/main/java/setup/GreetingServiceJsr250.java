package setup;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GreetingServiceJsr250 {
    private static final Logger log = LoggerFactory.getLogger(GreetingServiceJsr250.class);

    public String getGreeting() {
        return "Hello World!";
    }

    @PostConstruct
    public void before() throws Exception {
        log.info("service created");
    }

    @PreDestroy
    public void after() throws Exception {
        log.info("service destroyed");
    }
}
