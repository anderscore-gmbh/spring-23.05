package setup.fragments;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class LoggingDemoTest {

    @Test
    void test() {
        org.apache.logging.log4j.Logger log4jLogger = LogManager.getLogger(LoggingDemoTest.class);
        org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger(LoggingDemoTest.class);
        Log jclLogger = LogFactory.getLog(LoggingDemoTest.class);
        Logger julLogger = Logger.getLogger(LoggingDemoTest.class.getName());

        slf4jLogger.info("SLF4J works");
        log4jLogger.info("LOG4J2 works");
        jclLogger.info("JCL works");
        julLogger.info("JUL works");
    }

}
