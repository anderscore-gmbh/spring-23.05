package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class SpringAopSamplesTest {

    private static final Logger log = LoggerFactory.getLogger(SpringAopSamplesTest.class);

    @Configuration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    static class Config {

        @Bean
        DemoAspect demoAspect() {
            return new DemoAspect();
        }

        @Bean
        DemoService demoService() {
            return new DemoService();
        }
    }

    @Aspect
    static class DemoAspect {

        @Pointcut("execution(public * *(..))")
        private void anyPublicMethod() {
        }

        @Pointcut("execution(int aop.SpringAopSamplesTest.*Service.*(..))")
        private void serviceMethod() {
        }

        @Pointcut("execution(int *(int, int))")
        private void anyBinaryIntOperator() {
        }

        @Pointcut("execution(* *(int, ..)) && args(firstValue, ..)")
        private void withAtLeastOneIntegerParameter(int firstValue) {
        }

        @Pointcut("execution(String *.greet(String)) && args(name)")
        private void anyGreetMethod(String name) {
        }

        @Pointcut("within(aop.SpringAopSamplesTest.DemoService)")
        private void withinDemoService() {
        }

        @Pointcut("@within(service)")
        private void annotatedByService(Service service) {
        }

        @Pointcut("anyPublicMethod() && @annotation(tag)")
        private void taggedServiceMethod(Tag tag) {
        }

        @Before("annotatedByService(service)")
            // TODO: Vor jeder Service-Methode ausführen
        void logMethodEntry(JoinPoint jp, Service service) {
            log.info("entering " + jp.getSignature() + " on " + jp.getTarget());
        }

        @AfterReturning("taggedServiceMethod(tag)")
            // TODO: Nach jeder Service-Methode, die mit Tag annotiert ist, ausführen
        void logMethodExit(Tag tag) {
            log.info("exiting method tagged " + tag.value());
        }

        @Around("anyGreetMethod(name)")
            // TODO: Ersetzen Sie den greet-Aufruf durch diese Implementierung
        String wrapGreetMethodCall(ProceedingJoinPoint jp, String name) throws Throwable {
            return "Goodbye " + name + "!";
        }

        @Around("withAtLeastOneIntegerParameter(firstValue)")
            // TODO: Einen Around-Advise, der den 1. Parameter und das Ergebnis loggt, implementieren
        int wrapMethodCall(ProceedingJoinPoint jp, int firstValue) throws Throwable {
            Integer result = (Integer) jp.proceed();
            log.info("call with first value " + firstValue + " returned " + result);
            return result;
        }
    }

    @Service
    public static class DemoService {

        @Tag("addition")
        public int add(int v1, int v2) {
            return v1 + v2;
        }

        public int add(int v1, int v2, int v3) {
            return v1 + v2 + v3;
        }

        @Tag("subtraction")
        public int sub(int v1, int v2) {
            return v1 - v2;
        }

        public String greet(String name) {
            return "Hello " + name + "!";
        }
    }

    @Inject
    private DemoService demoService;

    @Test
    public void test() {
        assertThat(demoService.add(3, 5)).isEqualTo(8);
        assertThat(demoService.add(3, 5, 7)).isEqualTo(15);
        assertThat(demoService.sub(7, 2)).isEqualTo(5);
        assertThat(demoService.greet("World")).isEqualTo("Goodbye World!");
    }
}