package container.fragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

public class NullableTest {

    @Configuration
    static class Config {

        // tag::x[]
        @Bean
        Car car(@Nullable Engine engine) {
            var car = new Car();
            car.setEngine(engine);
            return car;
        }
        // end::x[]
    }

    @Test
    void test() {
        var ctx = new AnnotationConfigApplicationContext(Config.class);
        Car car = ctx.getBean("car", Car.class);
        assertThat(car.getEngine()).isNull();
    }
}
