package aop.fragments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoBean {
    private static final Logger log = LoggerFactory.getLogger(DemoBean.class);

    private int counter;

    public int inc() {
        log.info("inc called");
        counter++;
        return counter;
    }
}
