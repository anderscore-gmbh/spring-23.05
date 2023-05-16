package aop.fragments;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// tag::aspect[]
@Aspect // <1>
public class DemoAspect {
    private static final Logger log = LoggerFactory.getLogger(DemoAspect.class);

    @Pointcut("execution(public * *(..))") // <2>
    public void anyPublicMethod() {
    }

    @Before("anyPublicMethod()") // <3>
    public void logMethodCall(JoinPoint jp) { // <4>
        log.info("public method {} called", jp.getSignature());
    }
}
// end::aspect[]
