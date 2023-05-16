package aop.fragments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// tag::config[]
@Configuration
@EnableAspectJAutoProxy // <1>
public class AopSampleConfig {

    @Bean
    DemoAspect aspect() { // <2>
        return new DemoAspect();
    }
    // end::config[]

    @Bean
    DemoBean bean() {
        return new DemoBean();
    }
}

