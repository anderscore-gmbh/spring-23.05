package config.fragments;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppConfigTest {

    @Test
    void testContextLoads() {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    void testPropertySource() {
        Map<String, Object> map = new HashMap<>();
        map.put("service.port", "8090");

        // tag::regsrc[]
        SimpleMapPropertySource propertySource = new SimpleMapPropertySource("simple-map", map);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(); // <1>
        ctx.getEnvironment().getPropertySources().addLast(propertySource); // <2>
        ctx.register(AppConfig.class);
        ctx.refresh(); // <3>
        // end::regsrc[]
    }

}
