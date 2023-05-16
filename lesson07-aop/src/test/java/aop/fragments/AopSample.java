package aop.fragments;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.inject.Inject;

@SpringJUnitConfig(AopSampleConfig.class)
public class AopSample {
    private static final Logger log = LoggerFactory.getLogger(AopSample.class);

    @Inject
    private DemoBean bean;

    @Test
    public void test() {
        for (int i = 0; i < 3; i++) {
            log.info("counter: " + bean.inc());
        }
    }
}
