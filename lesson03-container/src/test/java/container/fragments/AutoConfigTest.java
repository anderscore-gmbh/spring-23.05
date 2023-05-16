package container.fragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import container.fragments.components.CarConstructorWiring;

public class AutoConfigTest {

    @Configuration
    static class Config {

        @Bean
        Engine engine() {
            return new Engine("gas", 68);
        }

        @Bean
        Feature radio() {
            return new Feature("radio");
        }

        @Bean
        Feature aircon() {
            return new Feature("air-conditioning");
        }

        @Bean
        CarFieldWiring car1() {
            return new CarFieldWiring();
        }

        @Bean
        CarSetterWiring car2() {
            return new CarSetterWiring();
        }

        @Bean
        CarConstructorWiring car3(Engine engine, List<Feature> features) {
            return new CarConstructorWiring(engine, features);
        }
    }

    @Test
    void testFieldWiring() {
        check(CarFieldWiring.class);
    }

    @Test
    void testSetterWiring() {
        check(CarSetterWiring.class);
    }

    @Test
    void testConstructorWiring() {
        check(CarConstructorWiring.class);
    }

    private <T> void check(Class<T> carClass) {
        var ctx = new AnnotationConfigApplicationContext(Config.class);
        T car = ctx.getBean(carClass);
        System.out.printf("%20s: %s%n", carClass.getSimpleName(), car);
        assertThat(car.toString()).contains("gas (68 kw)").contains("radio").contains("air-conditioning");
    }
}
