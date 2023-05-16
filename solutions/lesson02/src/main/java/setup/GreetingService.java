package setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class GreetingService implements InitializingBean, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(GreetingService.class);

    public String getGreeting() {
        return "Hello World!";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("service created");
    }

    @Override
    public void destroy() throws Exception {
        log.info("service destroyed");
        System.out.println("service destroyed");
    }
}
