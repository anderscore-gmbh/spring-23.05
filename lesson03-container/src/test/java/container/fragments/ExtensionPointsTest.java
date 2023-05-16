package container.fragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class ExtensionPointsTest {

    @Configuration
    @Import(CarConfig.class)
    static class Config {

        // tag::post-proc[]
        @Bean
        static BeanPostProcessor postProcessor() {
            return new LoggingBeanPostProcessor();
        }
        // end::post-proc[]

        // tag::factory-post-proc[]
        @Bean
        static BeanFactoryPostProcessor factoryPostProcessor() {
            return new LoggingBeanFactoryPostProcessor();
        }
        // end::factory-post-proc[]

        // tag::factory-bean[]
        @Bean
        FactoryBean<Car> electricCar() {
            return new CarFactoryBean();
        }
        // end::factory-bean[]
    }

    @Autowired
    @Qualifier("electricCar")
    private Car electricCar;

    @Autowired
    private ApplicationContext ctx;

    @Test
    void test() {
        assertThat(electricCar.getEngine()).hasToString("electric (50 kw)");
    }
}
